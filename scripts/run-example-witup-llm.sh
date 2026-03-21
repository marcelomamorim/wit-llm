#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
EXAMPLES_SRC_DIR="$ROOT_DIR/examples/java-src"
EXAMPLES_CLASSES_DIR="$ROOT_DIR/generated/examples-classes"

CLASS_NAME="com.example.witupllmdemo.ExampleTransferService"
MODEL="qwen2.5-coder:7b"
NUM_THREAD=2
CPU_LIMIT_PERCENT=60
DRY_RUN=false
SKIP_MODEL_PULL=false
SKIP_CPU_LIMIT=false
OVERVIEW_FILE="$ROOT_DIR/examples/project-overview.txt"
OLLAMA_URL="http://localhost:11434"

ANALYSIS_OUTPUT="$ROOT_DIR/generated/example-witup-analysis.json"
PROMPT_OUTPUT="$ROOT_DIR/generated/example-unit-test-prompt.txt"
DOC_OUTPUT="$ROOT_DIR/generated/example-witup-unit-tests.md"

usage() {
  cat <<EOF
Usage:
  $0 [options]

Options:
  --class-name <fqcn>       Java class to analyse (default: $CLASS_NAME)
  --model <name>            Ollama model (default: $MODEL)
  --num-thread <n>          num_thread passed to Ollama request (default: $NUM_THREAD)
  --cpu-limit <1..100>      macOS cpulimit percentage for Ollama process (default: $CPU_LIMIT_PERCENT)
  --overview-file <path>    Extra context file for prompt (default: $OVERVIEW_FILE)
  --dry-run                 Skip Ollama generation and export only analysis + prompt
  --skip-model-pull         Do not run 'ollama pull' even if model is missing
  --skip-cpu-limit          Do not call macOS CPU limiter script
  --help                    Show this help
EOF
}

require_cmd() {
  local cmd="$1"
  if ! command -v "$cmd" >/dev/null 2>&1; then
    echo "Missing command: $cmd" >&2
    exit 1
  fi
}

parse_args() {
  while [[ $# -gt 0 ]]; do
    case "$1" in
      --class-name)
        CLASS_NAME="${2:-}"
        shift 2
        ;;
      --model)
        MODEL="${2:-}"
        shift 2
        ;;
      --num-thread)
        NUM_THREAD="${2:-}"
        shift 2
        ;;
      --cpu-limit)
        CPU_LIMIT_PERCENT="${2:-}"
        shift 2
        ;;
      --overview-file)
        OVERVIEW_FILE="${2:-}"
        shift 2
        ;;
      --dry-run)
        DRY_RUN=true
        shift 1
        ;;
      --skip-model-pull)
        SKIP_MODEL_PULL=true
        shift 1
        ;;
      --skip-cpu-limit)
        SKIP_CPU_LIMIT=true
        shift 1
        ;;
      --help|-h)
        usage
        exit 0
        ;;
      *)
        echo "Unknown argument: $1" >&2
        usage
        exit 1
        ;;
    esac
  done
}

validate_inputs() {
  if ! [[ "$NUM_THREAD" =~ ^[1-9][0-9]*$ ]]; then
    echo "--num-thread must be a positive integer" >&2
    exit 1
  fi

  if ! [[ "$CPU_LIMIT_PERCENT" =~ ^[0-9]+$ ]] || (( CPU_LIMIT_PERCENT < 1 || CPU_LIMIT_PERCENT > 100 )); then
    echo "--cpu-limit must be an integer from 1 to 100" >&2
    exit 1
  fi

  if [[ ! -d "$EXAMPLES_SRC_DIR" ]]; then
    echo "Examples source dir not found: $EXAMPLES_SRC_DIR" >&2
    exit 1
  fi

  if [[ ! -f "$OVERVIEW_FILE" ]]; then
    echo "Overview file not found: $OVERVIEW_FILE" >&2
    exit 1
  fi
}

compile_examples() {
  echo "[1/5] Compiling example classes..."
  mkdir -p "$EXAMPLES_CLASSES_DIR"

  local source_list
  source_list="$(mktemp)"
  find "$EXAMPLES_SRC_DIR" -name "*.java" | sort > "$source_list"

  if [[ ! -s "$source_list" ]]; then
    echo "No example .java files found under $EXAMPLES_SRC_DIR" >&2
    rm -f "$source_list"
    exit 1
  fi

  javac --release 21 -d "$EXAMPLES_CLASSES_DIR" @"$source_list"
  rm -f "$source_list"
}

compile_project() {
  echo "[2/5] Compiling witup-llm project..."
  mvn -q -f "$ROOT_DIR/pom.xml" -Dmaven.repo.local="$ROOT_DIR/.m2" -DskipTests compile
}

wait_for_ollama() {
  local retries=60
  local delay=1

  for ((i=1; i<=retries; i++)); do
    if curl -fsS "$OLLAMA_URL/api/tags" >/dev/null 2>&1; then
      return 0
    fi
    sleep "$delay"
  done

  echo "Ollama did not become ready at $OLLAMA_URL within $retries seconds" >&2
  exit 1
}

start_ollama_if_needed() {
  echo "[3/5] Ensuring Ollama is running..."
  require_cmd ollama
  require_cmd curl

  if ! pgrep -x ollama >/dev/null 2>&1; then
    echo "Starting 'ollama serve' in background..."
    mkdir -p "$ROOT_DIR/generated"
    nohup ollama serve > "$ROOT_DIR/generated/ollama-serve.log" 2>&1 &
  fi

  wait_for_ollama
}

limit_ollama_cpu_if_needed() {
  if [[ "$SKIP_CPU_LIMIT" == true ]]; then
    return 0
  fi

  if [[ "$(uname -s)" != "Darwin" ]]; then
    echo "CPU limiter step skipped: this script is currently configured for macOS only."
    return 0
  fi

  if command -v cpulimit >/dev/null 2>&1; then
    "$ROOT_DIR/scripts/macos-limit-ollama.sh" "$CPU_LIMIT_PERCENT"
  else
    echo "cpulimit not found. Install with: brew install cpulimit"
    echo "Continuing with request-level limit only (--num-thread $NUM_THREAD)."
  fi
}

pull_model_if_needed() {
  if [[ "$SKIP_MODEL_PULL" == true ]]; then
    return 0
  fi

  if ! ollama list | awk 'NR>1 {print $1}' | grep -Fxq "$MODEL"; then
    echo "Model '$MODEL' not found locally. Pulling..."
    ollama pull "$MODEL"
  fi
}

run_witup_llm() {
  echo "[4/5] Running witup-llm..."

  local args=(
    --class-path "$EXAMPLES_CLASSES_DIR"
    --class-name "$CLASS_NAME"
    --model "$MODEL"
    --num-thread "$NUM_THREAD"
    --ollama-url "$OLLAMA_URL"
    --overview-file "$OVERVIEW_FILE"
    --analysis-output "$ANALYSIS_OUTPUT"
    --prompt-output "$PROMPT_OUTPUT"
    --output "$DOC_OUTPUT"
  )

  if [[ "$DRY_RUN" == true ]]; then
    args+=(--dry-run)
  fi

  local exec_args
  exec_args="$(printf '%q ' "${args[@]}")"
  exec_args="${exec_args% }"

  mvn -q -f "$ROOT_DIR/pom.xml" -Dmaven.repo.local="$ROOT_DIR/.m2" -DskipTests exec:java -Dexec.args="$exec_args"
}

print_summary() {
  echo "[5/5] Done."
  echo "Class analysed: $CLASS_NAME"
  echo "Analysis JSON:  $ANALYSIS_OUTPUT"
  echo "Prompt file:    $PROMPT_OUTPUT"
  if [[ "$DRY_RUN" == true ]]; then
    echo "Dry-run mode was enabled: markdown generation was skipped."
  else
    echo "Generated file: $DOC_OUTPUT"
  fi
}

main() {
  parse_args "$@"
  validate_inputs
  require_cmd javac
  require_cmd mvn

  compile_examples
  compile_project

  if [[ "$DRY_RUN" == false ]]; then
    start_ollama_if_needed
    limit_ollama_cpu_if_needed
    pull_model_if_needed
  else
    echo "[3/5] Dry-run mode: skipping Ollama startup and model pull."
  fi

  run_witup_llm
  print_summary
}

main "$@"

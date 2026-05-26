#!/usr/bin/env bash

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
RUN_DIR="${RUN_DIR:-}"
RUNTIME_CONFIG="${RUNTIME_CONFIG:-$ROOT_DIR/generated/configs/rodada-artigo.runtime.json}"
GENERATION_MODEL="${GENERATION_MODEL:-openai_main}"
JDK_ROOT="${JDK_ROOT:-$ROOT_DIR/generated/repos/jdk}"
RESPONSES_JSONL="${RESPONSES_JSONL:-$RUN_DIR/responses_openai_batch_generation.jsonl}"
ERRORS_JSONL="${ERRORS_JSONL:-$RUN_DIR/errors_openai_batch_generation.jsonl}"
WORKERS="${WORKERS:-$(getconf _NPROCESSORS_ONLN 2>/dev/null || sysctl -n hw.ncpu 2>/dev/null || printf '4')}"
BIN="$ROOT_DIR/bin/witup"

log() {
  printf '[jdk-global/evaluate] %s\n' "$*"
}

if [[ -z "$RUN_DIR" ]]; then
  printf 'erro: RUN_DIR é obrigatório.\n' >&2
  exit 2
fi

log "RUN_DIR=$RUN_DIR"
log "generation_model=$GENERATION_MODEL"
log "project=jdk"
log "experimental_unit=global_project_impact"
log "method_level_analysis=secondary"
log "workers=$WORKERS"
log "runtime_config=$RUNTIME_CONFIG"
log "jdk_root=$JDK_ROOT"
log "responses=$RESPONSES_JSONL"
log "errors=$ERRORS_JSONL"
log "optional_build_command=${JDK_GLOBAL_BUILD_COMMAND:-<skipped>}"
log "optional_test_command=${JDK_GLOBAL_TEST_COMMAND:-<skipped>}"
log "optional_coverage_command=${JDK_GLOBAL_COVERAGE_COMMAND:-<skipped>}"
log "optional_mutation_command=${JDK_GLOBAL_MUTATION_COMMAND:-<skipped>}"

if [[ ! -x "$BIN" ]]; then
  log "binário ausente; compilando $BIN"
  (cd "$ROOT_DIR" && go build -o "$BIN" ./cmd/witup)
fi

"$BIN" avaliar-estudo-jdk-global \
  --config "$RUNTIME_CONFIG" \
  --generation-model "$GENERATION_MODEL" \
  --jdk-root "$JDK_ROOT" \
  --run-dir "$RUN_DIR" \
  --responses "$RESPONSES_JSONL" \
  --errors "$ERRORS_JSONL" \
  --workers "$WORKERS"

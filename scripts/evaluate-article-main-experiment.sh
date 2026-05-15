#!/usr/bin/env bash

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
RUN_DIR="${RUN_DIR:-}"
RUN_STAMP="${RUN_STAMP:-$(date -u +%Y%m%dT%H%M%SZ)}"
EXPERIMENT_ROOT="${EXPERIMENT_ROOT:-$ROOT_DIR/generated/experiments/wit-expath-regression-study}"
GENERATION_MODEL="${GENERATION_MODEL:-openai_main}"
RUNTIME_CONFIG="${RUNTIME_CONFIG:-$RUN_DIR/rodada-artigo.runtime.json}"
RESPONSES_JSONL="${RESPONSES_JSONL:-$RUN_DIR/responses_openai_batch_generation.jsonl}"
ERRORS_JSONL="${ERRORS_JSONL:-$RUN_DIR/errors_openai_batch_generation.jsonl}"
MANIFEST="${MANIFEST:-$EXPERIMENT_ROOT/preparation/statistical-manifest.csv}"
BIN="${BIN:-$ROOT_DIR/bin/witup}"
MAVEN_REPO_LOCAL="${MAVEN_REPO_LOCAL:-$ROOT_DIR/generated/m2-repo}"
MAVEN_PROFILE_ARGS="${MAVEN_PROFILE_ARGS:--P!java14+ -Denforcer.skip=true}"

configure_java17() {
  if [[ -x /usr/libexec/java_home ]]; then
    local java17_home
    if java17_home="$(/usr/libexec/java_home -v 17 2>/dev/null)" && [[ -n "$java17_home" ]]; then
      export JAVA_HOME="$java17_home"
      export PATH="$JAVA_HOME/bin:$PATH"
      printf '[article-main/evaluate] JAVA_HOME=%s\n' "$JAVA_HOME"
      return 0
    fi
  fi
  if [[ -n "${JAVA_HOME:-}" && -x "$JAVA_HOME/bin/java" ]]; then
    export PATH="$JAVA_HOME/bin:$PATH"
    printf '[article-main/evaluate] JAVA_HOME=%s\n' "$JAVA_HOME"
    return 0
  fi
  printf '[article-main/evaluate] aviso: Java 17 não detectado automaticamente; usando java disponível no PATH\n'
}

if [[ -z "$RUN_DIR" ]]; then
  printf 'erro: informe RUN_DIR com a execução acadêmica a avaliar.\n' >&2
  exit 1
fi

configure_java17
export MAVEN_REPO_LOCAL MAVEN_PROFILE_ARGS

printf '[article-main/evaluate] RUN_DIR=%s\n' "$RUN_DIR"
printf '[article-main/evaluate] RUN_STAMP=%s\n' "$RUN_STAMP"
printf '[article-main/evaluate] generation_model=%s\n' "$GENERATION_MODEL"
printf '[article-main/evaluate] backend=batch endpoint=/v1/responses\n'
printf '[article-main/evaluate] maven_repo_local=%s\n' "$MAVEN_REPO_LOCAL"
printf '[article-main/evaluate] maven_profile_args=%s\n' "$MAVEN_PROFILE_ARGS"
printf '[article-main/evaluate] runtime_config=%s\n' "$RUNTIME_CONFIG"
printf '[article-main/evaluate] manifest=%s\n' "$MANIFEST"
printf '[article-main/evaluate] responses=%s\n' "$RESPONSES_JSONL"
printf '[article-main/evaluate] errors=%s\n' "$ERRORS_JSONL"
printf '[article-main/evaluate] coleta Batch concluída em: %s\n' "$RUN_DIR"
printf '[article-main/evaluate] este script é intencionalmente não pago e não chama a OpenAI.\n'

if [[ ! -x "$BIN" ]]; then
  (cd "$ROOT_DIR" && go build -o "$BIN" ./cmd/witup)
fi

"$BIN" avaliar-batch-segunda-fase \
  --config "$RUNTIME_CONFIG" \
  --generation-model "$GENERATION_MODEL" \
  --responses "$RESPONSES_JSONL" \
  --errors "$ERRORS_JSONL" \
  --output-dir "$RUN_DIR" \
  --run-stamp "$RUN_STAMP"

"$BIN" consolidar-estatisticas-primeira-rodada \
  --manifest "$MANIFEST" \
  --summary "$RUN_DIR/results_${RUN_STAMP}_paired_summary.csv" \
  --metrics "$RUN_DIR/results_${RUN_STAMP}_paired_metrics.csv" \
  --comparison "$RUN_DIR/results_${RUN_STAMP}_paired_comparison.csv" \
  --output-dir "$RUN_DIR/statistics"

cp "$RUN_DIR/statistics/phase-two-statistics.md" "$RUN_DIR/analysis_${RUN_STAMP}_statistical_inference.md"
cp "$RUN_DIR/statistics/phase-two-statistics.csv" "$RUN_DIR/analysis_${RUN_STAMP}_statistical_inference.csv"

printf '[article-main/evaluate] summary=%s\n' "$RUN_DIR/results_${RUN_STAMP}_paired_summary.csv"
printf '[article-main/evaluate] metrics=%s\n' "$RUN_DIR/results_${RUN_STAMP}_paired_metrics.csv"
printf '[article-main/evaluate] comparison=%s\n' "$RUN_DIR/results_${RUN_STAMP}_paired_comparison.csv"
printf '[article-main/evaluate] statistics=%s\n' "$RUN_DIR/analysis_${RUN_STAMP}_statistical_inference.md"
printf '[article-main/evaluate] dashboard=%s\n' "$RUN_DIR/dashboard_${RUN_STAMP}_wit_expath_regression.html"

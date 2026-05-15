#!/usr/bin/env bash

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
RUN_STAMP="${RUN_STAMP:-$(date -u +%Y%m%dT%H%M%SZ)}"
EXPERIMENT_ROOT="${EXPERIMENT_ROOT:-$ROOT_DIR/generated/experiments/jdk-global-impact-study}"
RUN_DIR="${RUN_DIR:-$EXPERIMENT_ROOT/${RUN_STAMP}_jdk_global_impact_batch_gpt54mini}"
RUNTIME_CONFIG="${RUNTIME_CONFIG:-$ROOT_DIR/generated/configs/rodada-artigo.runtime.json}"
GENERATION_MODEL="${GENERATION_MODEL:-openai_main}"
JDK_ROOT="${JDK_ROOT:-$ROOT_DIR/generated/repos/jdk}"
JDK_WIT_ANALYSIS="${JDK_WIT_ANALYSIS:-$ROOT_DIR/resources/wit-replication-package/data/output/jdk/wit_filtered.json}"
METHOD_COUNT="${METHOD_COUNT:-30}"
WORKERS="${WORKERS:-$(getconf _NPROCESSORS_ONLN 2>/dev/null || sysctl -n hw.ncpu 2>/dev/null || printf '4')}"
REQUESTS_JSONL="${REQUESTS_JSONL:-$RUN_DIR/requests_${RUN_STAMP}_openai_batch_generation.jsonl}"
BIN="$ROOT_DIR/bin/witup"

log() {
  printf '[jdk-global/prepare] %s\n' "$*"
}

log_context() {
  log "RUN_DIR=$RUN_DIR"
  log "RUN_STAMP=$RUN_STAMP"
  log "generation_model=$GENERATION_MODEL"
  log "backend=batch endpoint=/v1/responses"
  log "project=jdk"
  log "repository_url=https://github.com/openjdk/jdk.git"
  log "wit_commit=da75f3c4ad5bdf25167a3ed80e51f567ab3dbd01"
  log "experimental_unit=global_project_impact"
  log "method_level_analysis=secondary"
  log "method_count=$METHOD_COUNT"
  log "workers=$WORKERS"
  log "runtime_config=$RUNTIME_CONFIG"
  log "jdk_root=$JDK_ROOT"
  log "wit_analysis=$JDK_WIT_ANALYSIS"
  log "requests_jsonl=$REQUESTS_JSONL"
}

mkdir -p "$RUN_DIR"
log_context

if [[ ! -x "$BIN" ]]; then
  log "binário ausente; compilando $BIN"
  (cd "$ROOT_DIR" && go build -o "$BIN" ./cmd/witup)
fi

"$BIN" preparar-estudo-jdk-global \
  --config "$RUNTIME_CONFIG" \
  --generation-model "$GENERATION_MODEL" \
  --jdk-root "$JDK_ROOT" \
  --wit-analysis "$JDK_WIT_ANALYSIS" \
  --output-dir "$RUN_DIR" \
  --requests "$REQUESTS_JSONL" \
  --method-count "$METHOD_COUNT" \
  --workers "$WORKERS"

printf 'Para submeter a rodada paga via Batch, execute:\n'
printf '  RUN_DIR=%q RUNTIME_CONFIG=%q GENERATION_MODEL=%q REQUESTS_JSONL=%q CONFIRMAR_EXECUCAO_PAGA=sim %q\n' \
  "$RUN_DIR" "$RUNTIME_CONFIG" "$GENERATION_MODEL" "$REQUESTS_JSONL" "$ROOT_DIR/scripts/submit-article-main-batch.sh"

#!/usr/bin/env bash

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
RUN_STAMP="${RUN_STAMP:-$(date -u +%Y%m%dT%H%M%SZ)}"
EXPERIMENT_ROOT="${EXPERIMENT_ROOT:-$ROOT_DIR/generated/experiments/wit-expath-regression-study}"
RUN_DIR="${RUN_DIR:-$EXPERIMENT_ROOT/${RUN_STAMP}_article_main_batch_gpt54mini_strict1call}"
RUNTIME_CONFIG="${RUNTIME_CONFIG:-$ROOT_DIR/generated/configs/rodada-artigo.runtime.json}"
GENERATION_MODEL="${GENERATION_MODEL:-openai_main}"
REQUESTS_JSONL="${REQUESTS_JSONL:-$RUN_DIR/requests_${RUN_STAMP}_openai_batch_generation.jsonl}"
PREFLIGHT_LOG="$RUN_DIR/preflight_${RUN_STAMP}_article_main.log"
BIN="$ROOT_DIR/bin/witup"
EXPECTED_PROJECTS="${EXPECTED_PROJECTS:-6}"
EXPECTED_SLICES="${EXPECTED_SLICES:-120}"
EXPECTED_REQUESTS="${EXPECTED_REQUESTS:-240}"

log() {
  printf '[article-main/prepare] %s\n' "$*"
}

log_context() {
  log "RUN_DIR=$RUN_DIR"
  log "RUN_STAMP=$RUN_STAMP"
  log "generation_model=$GENERATION_MODEL"
  log "backend=batch endpoint=/v1/responses"
  log "expected_projects=$EXPECTED_PROJECTS expected_slices=$EXPECTED_SLICES expected_requests=$EXPECTED_REQUESTS"
  log "runtime_config=$RUNTIME_CONFIG"
  log "requests_jsonl=$REQUESTS_JSONL"
  log "preflight_log=$PREFLIGHT_LOG"
}

mkdir -p "$RUN_DIR"
log_context

if [[ ! -f "$RUNTIME_CONFIG" ]]; then
  log "configuração runtime não encontrada; preparando configuração acadêmica a partir do harness estatístico existente"
  ROUND_DIR="$EXPERIMENT_ROOT/preparation" \
    RUNTIME_CONFIG="$RUNTIME_CONFIG" \
    SLICES_PER_PROJECT="${SLICES_PER_PROJECT:-20}" \
    OPENAI_MODEL="${OPENAI_MODEL:-gpt-5.4-mini}" \
    OPENAI_EXECUTION_BACKEND="batch" \
    OPENAI_ENDPOINT="/v1/responses" \
    OPENAI_BATCH_COMPLETION_WINDOW="24h" \
    "$ROOT_DIR/scripts/preparar-primeira-rodada-estatistica.sh"
fi

log "executando preflight sem chamada paga"
"$BIN" preflight-segunda-fase --config "$RUNTIME_CONFIG" --check-build | tee "$PREFLIGHT_LOG"

log "gerando JSONL de requests Batch sem submeter à OpenAI"
"$BIN" preparar-batch-segunda-fase \
  --config "$RUNTIME_CONFIG" \
  --generation-model "$GENERATION_MODEL" \
  --requests "$REQUESTS_JSONL"

log "requests Batch: $REQUESTS_JSONL"
log "preflight log: $PREFLIGHT_LOG"
printf 'Para submeter a rodada paga via Batch, execute:\n'
printf '  RUN_DIR=%q RUNTIME_CONFIG=%q GENERATION_MODEL=%q REQUESTS_JSONL=%q CONFIRMAR_EXECUCAO_PAGA=sim %q\n' \
  "$RUN_DIR" "$RUNTIME_CONFIG" "$GENERATION_MODEL" "$REQUESTS_JSONL" "$ROOT_DIR/scripts/submit-article-main-batch.sh"

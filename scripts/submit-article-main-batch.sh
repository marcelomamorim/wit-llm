#!/usr/bin/env bash

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
RUN_STAMP="${RUN_STAMP:-$(date -u +%Y%m%dT%H%M%SZ)}"
RUN_DIR="${RUN_DIR:-$ROOT_DIR/generated/experiments/wit-expath-regression-study/${RUN_STAMP}_article_main_batch_gpt54mini_strict1call}"
RUNTIME_CONFIG="${RUNTIME_CONFIG:-$ROOT_DIR/generated/configs/rodada-artigo.runtime.json}"
GENERATION_MODEL="${GENERATION_MODEL:-openai_main}"
REQUESTS_JSONL="${REQUESTS_JSONL:-$RUN_DIR/requests_${RUN_STAMP}_openai_batch_generation.jsonl}"
BATCH_METADATA="${BATCH_METADATA:-$RUN_DIR/batch_${RUN_STAMP}_openai_submission.json}"
BIN="$ROOT_DIR/bin/witup"
EXPECTED_PROJECTS="${EXPECTED_PROJECTS:-6}"
EXPECTED_SLICES="${EXPECTED_SLICES:-120}"
EXPECTED_REQUESTS="${EXPECTED_REQUESTS:-240}"

log() {
  printf '[article-main/submit-batch] %s\n' "$*"
}

log_context() {
  log "RUN_DIR=$RUN_DIR"
  log "RUN_STAMP=$RUN_STAMP"
  log "generation_model=$GENERATION_MODEL"
  log "backend=batch endpoint=/v1/responses"
  log "expected_projects=$EXPECTED_PROJECTS expected_slices=$EXPECTED_SLICES expected_requests=$EXPECTED_REQUESTS"
  log "runtime_config=$RUNTIME_CONFIG"
  log "requests_jsonl=$REQUESTS_JSONL"
  log "batch_metadata=$BATCH_METADATA"
}

log_context

if [[ "${CONFIRMAR_EXECUCAO_PAGA:-}" != "sim" ]]; then
  log "submissão paga bloqueada"
  printf 'Para submeter à OpenAI Batch API, execute novamente com CONFIRMAR_EXECUCAO_PAGA=sim.\n'
  exit 0
fi

if [[ -z "${OPENAI_API_KEY:-}" ]]; then
  printf 'erro: OPENAI_API_KEY é obrigatória para submeter o Batch.\n' >&2
  exit 1
fi

if [[ ! -f "$REQUESTS_JSONL" ]]; then
  printf 'erro: requests JSONL não encontrado: %s\n' "$REQUESTS_JSONL" >&2
  exit 1
fi

mkdir -p "$RUN_DIR"
log "submetendo JSONL à Batch API"
"$BIN" submeter-openai-batch \
  --config "$RUNTIME_CONFIG" \
  --model "$GENERATION_MODEL" \
  --requests "$REQUESTS_JSONL" \
  --output "$BATCH_METADATA"

log "metadados: $BATCH_METADATA"

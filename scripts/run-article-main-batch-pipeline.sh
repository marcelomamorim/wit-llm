#!/usr/bin/env bash

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
RUN_STAMP="${RUN_STAMP:-$(date -u +%Y%m%dT%H%M%SZ)}"
EXPERIMENT_ROOT="${EXPERIMENT_ROOT:-$ROOT_DIR/generated/experiments/wit-expath-regression-study}"
RUN_DIR="${RUN_DIR:-$EXPERIMENT_ROOT/${RUN_STAMP}_article_main_batch_gpt54mini_strict1call}"
RUNTIME_CONFIG="${RUNTIME_CONFIG:-$ROOT_DIR/generated/configs/rodada-artigo.runtime.json}"
GENERATION_MODEL="${GENERATION_MODEL:-openai_main}"
REQUESTS_JSONL="${REQUESTS_JSONL:-$RUN_DIR/requests_${RUN_STAMP}_openai_batch_generation.jsonl}"
BATCH_METADATA="${BATCH_METADATA:-$RUN_DIR/batch_${RUN_STAMP}_openai_submission.json}"
BATCH_ID="${BATCH_ID:-}"

RUN_PREPARE="${RUN_PREPARE:-sim}"
RUN_SUBMIT="${RUN_SUBMIT:-sim}"
RUN_COLLECT="${RUN_COLLECT:-sim}"
RUN_EVALUATE="${RUN_EVALUATE:-sim}"
WAIT_FOR_COMPLETION="${WAIT_FOR_COMPLETION:-sim}"

EXPECTED_PROJECTS="${EXPECTED_PROJECTS:-6}"
EXPECTED_SLICES="${EXPECTED_SLICES:-120}"
EXPECTED_REQUESTS="${EXPECTED_REQUESTS:-240}"
MAVEN_REPO_LOCAL="${MAVEN_REPO_LOCAL:-$ROOT_DIR/generated/m2-repo}"
MAVEN_PROFILE_ARGS="${MAVEN_PROFILE_ARGS:--P!java14+ -Denforcer.skip=true}"

log() {
  printf '[article-main/pipeline] %s\n' "$*"
}

extract_batch_id() {
  local metadata="$1"
  python3 - "$metadata" <<'PY'
import json, sys
path = sys.argv[1]
try:
    with open(path, "r", encoding="utf-8") as handle:
        payload = json.load(handle)
    print(payload.get("batch_id") or payload.get("id") or "")
except Exception:
    print("")
PY
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
  log "batch_id=${BATCH_ID:-<from metadata after submit>}"
  log "run_prepare=$RUN_PREPARE run_submit=$RUN_SUBMIT run_collect=$RUN_COLLECT run_evaluate=$RUN_EVALUATE"
}

mkdir -p "$RUN_DIR"
log_context

if [[ "$RUN_PREPARE" == "sim" ]]; then
  RUN_STAMP="$RUN_STAMP" \
    RUN_DIR="$RUN_DIR" \
    RUNTIME_CONFIG="$RUNTIME_CONFIG" \
    GENERATION_MODEL="$GENERATION_MODEL" \
    REQUESTS_JSONL="$REQUESTS_JSONL" \
    EXPECTED_PROJECTS="$EXPECTED_PROJECTS" \
    EXPECTED_SLICES="$EXPECTED_SLICES" \
    EXPECTED_REQUESTS="$EXPECTED_REQUESTS" \
    MAVEN_REPO_LOCAL="$MAVEN_REPO_LOCAL" \
    MAVEN_PROFILE_ARGS="$MAVEN_PROFILE_ARGS" \
    "$ROOT_DIR/scripts/prepare-article-main-experiment.sh"
fi

if [[ "$RUN_SUBMIT" == "sim" && -z "$BATCH_ID" ]]; then
  RUN_STAMP="$RUN_STAMP" \
    RUN_DIR="$RUN_DIR" \
    RUNTIME_CONFIG="$RUNTIME_CONFIG" \
    GENERATION_MODEL="$GENERATION_MODEL" \
    REQUESTS_JSONL="$REQUESTS_JSONL" \
    BATCH_METADATA="$BATCH_METADATA" \
    CONFIRMAR_EXECUCAO_PAGA="${CONFIRMAR_EXECUCAO_PAGA:-}" \
    EXPECTED_PROJECTS="$EXPECTED_PROJECTS" \
    EXPECTED_SLICES="$EXPECTED_SLICES" \
    EXPECTED_REQUESTS="$EXPECTED_REQUESTS" \
    "$ROOT_DIR/scripts/submit-article-main-batch.sh"

  BATCH_ID="$(extract_batch_id "$BATCH_METADATA")"
  if [[ -z "$BATCH_ID" ]]; then
    printf 'erro: não consegui extrair batch_id de %s\n' "$BATCH_METADATA" >&2
    exit 1
  fi
  log "batch_id_extraido=$BATCH_ID"
fi

if [[ "$RUN_COLLECT" == "sim" ]]; then
  if [[ -z "$BATCH_ID" ]]; then
    printf 'erro: BATCH_ID é obrigatório para coleta. Informe BATCH_ID=... ou rode com RUN_SUBMIT=sim.\n' >&2
    exit 1
  fi
  RUN_DIR="$RUN_DIR" \
    RUNTIME_CONFIG="$RUNTIME_CONFIG" \
    GENERATION_MODEL="$GENERATION_MODEL" \
    BATCH_ID="$BATCH_ID" \
    WAIT_FOR_COMPLETION="$WAIT_FOR_COMPLETION" \
    EXPECTED_PROJECTS="$EXPECTED_PROJECTS" \
    EXPECTED_SLICES="$EXPECTED_SLICES" \
    EXPECTED_REQUESTS="$EXPECTED_REQUESTS" \
    "$ROOT_DIR/scripts/collect-article-main-batch.sh"
fi

if [[ "$RUN_EVALUATE" == "sim" ]]; then
  RUN_STAMP="$RUN_STAMP" \
    RUN_DIR="$RUN_DIR" \
    RUNTIME_CONFIG="$RUNTIME_CONFIG" \
    GENERATION_MODEL="$GENERATION_MODEL" \
    MAVEN_REPO_LOCAL="$MAVEN_REPO_LOCAL" \
    MAVEN_PROFILE_ARGS="$MAVEN_PROFILE_ARGS" \
    "$ROOT_DIR/scripts/evaluate-article-main-experiment.sh"
fi

log "pipeline concluído"

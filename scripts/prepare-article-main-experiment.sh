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
ARTICLE_REPREPARE="${ARTICLE_REPREPARE:-sim}"
MAVEN_REPO_LOCAL="${MAVEN_REPO_LOCAL:-$ROOT_DIR/generated/m2-repo}"
MAVEN_PROFILE_ARGS="${MAVEN_PROFILE_ARGS:--P!java14+ -Denforcer.skip=true}"

log() {
  printf '[article-main/prepare] %s\n' "$*"
}

configure_java17() {
  if [[ -x /usr/libexec/java_home ]]; then
    local java17_home
    if java17_home="$(/usr/libexec/java_home -v 17 2>/dev/null)" && [[ -n "$java17_home" ]]; then
      export JAVA_HOME="$java17_home"
      export PATH="$JAVA_HOME/bin:$PATH"
      log "JAVA_HOME=$JAVA_HOME"
      return 0
    fi
  fi
  if [[ -n "${JAVA_HOME:-}" && -x "$JAVA_HOME/bin/java" ]]; then
    export PATH="$JAVA_HOME/bin:$PATH"
    log "JAVA_HOME=$JAVA_HOME"
    return 0
  fi
  log "aviso: Java 17 não detectado automaticamente; usando java disponível no PATH"
}

log_context() {
  log "RUN_DIR=$RUN_DIR"
  log "RUN_STAMP=$RUN_STAMP"
  log "generation_model=$GENERATION_MODEL"
  log "backend=batch endpoint=/v1/responses"
  log "expected_projects=$EXPECTED_PROJECTS expected_slices=$EXPECTED_SLICES expected_requests=$EXPECTED_REQUESTS"
  log "maven_repo_local=$MAVEN_REPO_LOCAL"
  log "maven_profile_args=$MAVEN_PROFILE_ARGS"
  log "runtime_config=$RUNTIME_CONFIG"
  log "requests_jsonl=$REQUESTS_JSONL"
  log "preflight_log=$PREFLIGHT_LOG"
}

mkdir -p "$RUN_DIR"
configure_java17
export MAVEN_REPO_LOCAL MAVEN_PROFILE_ARGS
log_context

if [[ "$ARTICLE_REPREPARE" == "sim" || ! -f "$RUNTIME_CONFIG" ]]; then
  log "preparando configuração acadêmica a partir do harness estatístico existente"
  ROUND_DIR="$EXPERIMENT_ROOT/preparation" \
    RUNTIME_CONFIG="$RUNTIME_CONFIG" \
    SLICES_PER_PROJECT="${SLICES_PER_PROJECT:-20}" \
    OPENAI_MODEL="${OPENAI_MODEL:-gpt-5.4-mini}" \
    OPENAI_EXECUTION_BACKEND="batch" \
    OPENAI_ENDPOINT="/v1/responses" \
    OPENAI_BATCH_COMPLETION_WINDOW="24h" \
    MAVEN_REPO_LOCAL="$MAVEN_REPO_LOCAL" \
    MAVEN_PROFILE_ARGS="$MAVEN_PROFILE_ARGS" \
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

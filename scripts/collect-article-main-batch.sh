#!/usr/bin/env bash

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
RUN_DIR="${RUN_DIR:-}"
RUNTIME_CONFIG="${RUNTIME_CONFIG:-$ROOT_DIR/generated/configs/rodada-artigo.runtime.json}"
GENERATION_MODEL="${GENERATION_MODEL:-openai_main}"
BATCH_ID="${BATCH_ID:-}"
BIN="$ROOT_DIR/bin/witup"
HEARTBEAT_INTERVAL_SECONDS="${HEARTBEAT_INTERVAL_SECONDS:-5}"
BATCH_POLL_INTERVAL_SECONDS="${BATCH_POLL_INTERVAL_SECONDS:-30}"
WAIT_FOR_COMPLETION="${WAIT_FOR_COMPLETION:-sim}"
EXPECTED_PROJECTS="${EXPECTED_PROJECTS:-6}"
EXPECTED_SLICES="${EXPECTED_SLICES:-120}"
EXPECTED_REQUESTS="${EXPECTED_REQUESTS:-240}"

log() {
  printf '[article-main/collect-batch] %s\n' "$*"
}

heartbeat_loop() {
  local started_at="$1"
  local status_file="$2"
  while true; do
    local now elapsed status
    now="$(date +%s)"
    elapsed="$((now - started_at))s"
    status="unknown"
    if [[ -f "$status_file" ]]; then
      status="$(cat "$status_file")"
    fi
    printf 'heartbeat etapa=batch_collect elapsed=%s projeto=all progresso=0/%s status=%s batch_id=%s\n' "$elapsed" "$EXPECTED_REQUESTS" "$status" "$BATCH_ID"
    sleep "$HEARTBEAT_INTERVAL_SECONDS"
  done
}

extract_status() {
  local metadata="$1"
  python3 - "$metadata" <<'PY'
import json, sys
path = sys.argv[1]
try:
    with open(path, "r", encoding="utf-8") as handle:
        print(json.load(handle).get("status") or "unknown")
except Exception:
    print("unknown")
PY
}

if [[ -z "$RUN_DIR" || -z "$BATCH_ID" ]]; then
  printf 'erro: informe RUN_DIR e BATCH_ID.\n' >&2
  exit 1
fi

mkdir -p "$RUN_DIR"
STATUS_FILE="$RUN_DIR/batch_collect_status.txt"
METADATA_FILE="$RUN_DIR/openai_batch_metadata.json"
printf 'starting\n' > "$STATUS_FILE"

log "RUN_DIR=$RUN_DIR"
log "generation_model=$GENERATION_MODEL"
log "backend=batch endpoint=/v1/responses"
log "expected_projects=$EXPECTED_PROJECTS expected_slices=$EXPECTED_SLICES expected_requests=$EXPECTED_REQUESTS"
log "runtime_config=$RUNTIME_CONFIG"
log "batch_id=$BATCH_ID"
log "heartbeat_interval=${HEARTBEAT_INTERVAL_SECONDS}s poll_interval=${BATCH_POLL_INTERVAL_SECONDS}s wait_for_completion=$WAIT_FOR_COMPLETION"
log "metadata=$METADATA_FILE"
log "responses=$RUN_DIR/responses_openai_batch_generation.jsonl"
log "errors=$RUN_DIR/errors_openai_batch_generation.jsonl"

STARTED_AT="$(date +%s)"
heartbeat_loop "$STARTED_AT" "$STATUS_FILE" &
HEARTBEAT_PID="$!"
trap 'kill "$HEARTBEAT_PID" 2>/dev/null || true' EXIT

while true; do
  "$BIN" coletar-openai-batch \
    --config "$RUNTIME_CONFIG" \
    --model "$GENERATION_MODEL" \
    --batch-id "$BATCH_ID" \
    --output-dir "$RUN_DIR"

  STATUS="$(extract_status "$METADATA_FILE")"
  printf '%s\n' "$STATUS" > "$STATUS_FILE"
  case "$STATUS" in
    completed|failed|expired|cancelled|cancelling)
      break
      ;;
  esac
  if [[ "$WAIT_FOR_COMPLETION" != "sim" ]]; then
    break
  fi
  sleep "$BATCH_POLL_INTERVAL_SECONDS"
done

kill "$HEARTBEAT_PID" 2>/dev/null || true
trap - EXIT
log "status_final=$(cat "$STATUS_FILE")"

#!/usr/bin/env bash
# poll-openai-batch.sh  (host-side)
#
# Faz polling de um batch OpenAI até atingir status terminal:
#   completed, failed, expired ou cancelled.
#
# Quando completed, `witup coletar-openai-batch` já baixa os arquivos:
#   $OUTPUT_DIR/responses_openai_batch_generation.jsonl
#   $OUTPUT_DIR/errors_openai_batch_generation.jsonl  (se houver erros)
#   $OUTPUT_DIR/openai_batch_metadata.json
#
# Variáveis obrigatórias:
#   BATCH_ID        : ID do batch OpenAI (ex: batch_abc123)
#   RUNTIME_CONFIG  : caminho para o runtime.json com credenciais
#   OUTPUT_DIR      : diretório onde os arquivos serão salvos
#
# Variáveis opcionais:
#   MODEL_KEY            : chave do modelo no runtime.json (default: openai_main)
#   POLL_INTERVAL        : segundos entre consultas (default: 60)
#   POLL_TIMEOUT_HOURS   : horas máximas de espera antes de abortar (default: 24)
#   WITUP                : caminho para o binário witup (default: auto-detect)
#
# Uso:
#   BATCH_ID=batch_abc123 \
#   RUNTIME_CONFIG=generated/configs/jdk-pilot.runtime.json \
#   OUTPUT_DIR=generated/experiments/jdk-pilot/pilot-20260606T000000Z \
#     ./scripts/poll-openai-batch.sh

set -euo pipefail

BATCH_ID="${BATCH_ID:-}"
RUNTIME_CONFIG="${RUNTIME_CONFIG:-}"
OUTPUT_DIR="${OUTPUT_DIR:-}"
MODEL_KEY="${MODEL_KEY:-openai_main}"
POLL_INTERVAL="${POLL_INTERVAL:-60}"
POLL_TIMEOUT_HOURS="${POLL_TIMEOUT_HOURS:-24}"

log()  { printf '[poll-batch] %s\n' "$*"; }
err()  { printf '[poll-batch] ERRO: %s\n' "$*" >&2; exit 1; }

[[ -n "${BATCH_ID}" ]]       || err "BATCH_ID é obrigatório."
[[ -n "${RUNTIME_CONFIG}" ]] || err "RUNTIME_CONFIG é obrigatório."
[[ -n "${OUTPUT_DIR}" ]]     || err "OUTPUT_DIR é obrigatório."
[[ -f "${RUNTIME_CONFIG}" ]] || err "arquivo não encontrado: ${RUNTIME_CONFIG}"

# ── Localizar o binário witup ─────────────────────────────────────────────────
if [[ -n "${WITUP:-}" ]]; then
  WITUP_BIN="${WITUP}"
else
  ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
  WITUP_BIN="${ROOT_DIR}/bin/witup"
  if [[ ! -x "${WITUP_BIN}" ]]; then
    log "Compilando witup..."
    mkdir -p "${ROOT_DIR}/bin"
    (cd "${ROOT_DIR}" && go build -o "${WITUP_BIN}" ./cmd/witup/)
  fi
fi

[[ -x "${WITUP_BIN}" ]] || err "binário witup não encontrado em ${WITUP_BIN}"

mkdir -p "${OUTPUT_DIR}"

# ── Polling loop ─────────────────────────────────────────────────────────────
POLL_MAX_ITERS=$(( POLL_TIMEOUT_HOURS * 3600 / POLL_INTERVAL ))
ITER=0

log "Iniciando polling do batch ${BATCH_ID}"
log "  config  : ${RUNTIME_CONFIG}"
log "  output  : ${OUTPUT_DIR}"
log "  intervalo: ${POLL_INTERVAL}s | timeout: ${POLL_TIMEOUT_HOURS}h | max_iters: ${POLL_MAX_ITERS}"

while true; do
  ITER=$(( ITER + 1 ))

  if [[ "${ITER}" -gt "${POLL_MAX_ITERS}" ]]; then
    err "Timeout após ${POLL_TIMEOUT_HOURS}h (${POLL_MAX_ITERS} tentativas). Batch ainda não completou."
  fi

  log "[iter ${ITER}/${POLL_MAX_ITERS}] consultando batch..."

  # Coletar resposta do witup — saída contém linha "Status : <valor>"
  set +e
  COLLECT_OUT=$(
    "${WITUP_BIN}" coletar-openai-batch \
      --config "${RUNTIME_CONFIG}" \
      --model "${MODEL_KEY}" \
      --batch-id "${BATCH_ID}" \
      --output-dir "${OUTPUT_DIR}" 2>&1
  )
  COLLECT_EXIT=$?
  set -e

  log "${COLLECT_OUT}"

  # Extrair status da saída (compatível com BSD grep do macOS e GNU grep)
  STATUS=$(echo "${COLLECT_OUT}" | grep -i "^Status" | sed 's/.*:[[:space:]]*//' | tr -d '[:space:]' | head -1)
  [[ -z "${STATUS}" ]] && STATUS="unknown"
  log "  → status: ${STATUS}"

  case "${STATUS}" in
    completed)
      log ""
      log "Batch ${BATCH_ID} COMPLETADO."
      log "  Respostas : ${OUTPUT_DIR}/responses_openai_batch_generation.jsonl"
      log "  Metadados : ${OUTPUT_DIR}/openai_batch_metadata.json"
      exit 0
      ;;
    failed)
      err "Batch ${BATCH_ID} FALHOU (status=failed). Verifique ${OUTPUT_DIR}/openai_batch_metadata.json."
      ;;
    expired)
      err "Batch ${BATCH_ID} EXPIROU (status=expired). Reenviamento necessário."
      ;;
    cancelled)
      err "Batch ${BATCH_ID} foi CANCELADO (status=cancelled)."
      ;;
    validating|in_progress|finalizing)
      log "  Aguardando ${POLL_INTERVAL}s antes da próxima consulta..."
      sleep "${POLL_INTERVAL}"
      ;;
    *)
      if [[ "${COLLECT_EXIT}" -ne 0 ]]; then
        log "  AVISO: erro na consulta (exit=${COLLECT_EXIT}) — tentando novamente em ${POLL_INTERVAL}s"
        sleep "${POLL_INTERVAL}"
      else
        log "  Status desconhecido '${STATUS}' — aguardando ${POLL_INTERVAL}s"
        sleep "${POLL_INTERVAL}"
      fi
      ;;
  esac
done

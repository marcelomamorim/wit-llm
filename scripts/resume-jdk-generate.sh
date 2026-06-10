#!/usr/bin/env bash
# resume-jdk-generate.sh
#
# Retoma a geração do run pilot-20260610T024432Z após queda de rede:
#   1. Baixa respostas do wit-context_chunk001 (já completado na OpenAI)
#   2. Submete e aguarda wit-context_chunk002
#   3. Mescla os 4 chunks de respostas
#   4. Materializa variantes
#   5. Gera ZIP

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "${ROOT_DIR}"

RUN_STAMP="pilot-20260610T024432Z"
PILOT_DIR="${ROOT_DIR}/generated/experiments/jdk-pilot/${RUN_STAMP}"
RUNTIME_CONFIG="${ROOT_DIR}/generated/configs/jdk-pilot.runtime.json"
JDK_ROOT="${ROOT_DIR}/generated/repos/jdk"
WITUP="${ROOT_DIR}/bin/witup"
MODEL_KEY="openai_main"
POLL_INTERVAL="${POLL_INTERVAL:-60}"
POLL_TIMEOUT_HOURS="${POLL_TIMEOUT_HOURS:-24}"

BATCH_WIT_CHUNK001="batch_6a28dc3e98988190873ac7d85ee17a55"
REQUESTS_WIT_CHUNK002="${PILOT_DIR}/chunks/requests_wit-context_chunk002.jsonl"

log()  { printf '\n[resume] %s\n' "$*"; }
err()  { printf '[resume] ERRO: %s\n' "$*" >&2; exit 1; }
step() { printf '\n[resume] ══════════════════════════════\n[resume] %s\n[resume] ══════════════════════════════\n' "$*"; }

[[ -n "${OPENAI_API_KEY:-}" ]] || err "OPENAI_API_KEY não está setada."

# ── Passo 1: Baixar wit-context_chunk001 (já completado) ─────────────────────
step "1/5 — Baixar wit-context_chunk001 (batch já completado)"
mkdir -p "${PILOT_DIR}/poll_wit-context_chunk001"
"${WITUP}" coletar-openai-batch \
  --config      "${RUNTIME_CONFIG}" \
  --model       "${MODEL_KEY}" \
  --batch-id    "${BATCH_WIT_CHUNK001}" \
  --output-dir  "${PILOT_DIR}/poll_wit-context_chunk001"

cp "${PILOT_DIR}/poll_wit-context_chunk001/responses_openai_batch_generation.jsonl" \
   "${PILOT_DIR}/responses_chunks/responses_wit-context_chunk001.jsonl"
log "✓ wit-context_chunk001: $(wc -l < "${PILOT_DIR}/responses_chunks/responses_wit-context_chunk001.jsonl" | tr -d ' ') respostas"

# ── Passo 2: Submeter + aguardar wit-context_chunk002 ────────────────────────
step "2/5 — Submeter wit-context_chunk002"
[[ -f "${REQUESTS_WIT_CHUNK002}" ]] || err "arquivo não encontrado: ${REQUESTS_WIT_CHUNK002}"

BATCH_META="${PILOT_DIR}/batch_meta_wit-context_chunk002.json"
"${WITUP}" submeter-openai-batch \
  --config   "${RUNTIME_CONFIG}" \
  --model    "${MODEL_KEY}" \
  --requests "${REQUESTS_WIT_CHUNK002}" \
  --output   "${BATCH_META}"

BATCH_ID=$(python3 -c "import json; d=json.load(open('${BATCH_META}')); print(d.get('batch_id',''))")
echo "${BATCH_ID}" > "${PILOT_DIR}/batch_id_wit-context_chunk002.txt"
log "Batch ID: ${BATCH_ID}"

mkdir -p "${PILOT_DIR}/poll_wit-context_chunk002"
BATCH_ID="${BATCH_ID}" \
RUNTIME_CONFIG="${RUNTIME_CONFIG}" \
OUTPUT_DIR="${PILOT_DIR}/poll_wit-context_chunk002" \
MODEL_KEY="${MODEL_KEY}" \
POLL_INTERVAL="${POLL_INTERVAL}" \
POLL_TIMEOUT_HOURS="${POLL_TIMEOUT_HOURS}" \
WITUP="${WITUP}" \
  "${ROOT_DIR}/scripts/poll-openai-batch.sh"

cp "${PILOT_DIR}/poll_wit-context_chunk002/responses_openai_batch_generation.jsonl" \
   "${PILOT_DIR}/responses_chunks/responses_wit-context_chunk002.jsonl"
log "✓ wit-context_chunk002: $(wc -l < "${PILOT_DIR}/responses_chunks/responses_wit-context_chunk002.jsonl" | tr -d ' ') respostas"

# ── Passo 3: Mesclar os 4 chunks ─────────────────────────────────────────────
step "3/5 — Mesclar respostas (4 chunks)"
RESPONSES_JSONL="${PILOT_DIR}/responses_openai_batch_generation.jsonl"

# ordem: direct-tests primeiro, depois wit-context (para manter consistência)
cat \
  "${PILOT_DIR}/responses_chunks/responses_direct-tests_chunk001.jsonl" \
  "${PILOT_DIR}/responses_chunks/responses_direct-tests_chunk002.jsonl" \
  "${PILOT_DIR}/responses_chunks/responses_wit-context_chunk001.jsonl" \
  "${PILOT_DIR}/responses_chunks/responses_wit-context_chunk002.jsonl" \
  > "${RESPONSES_JSONL}"

RESP_COUNT=$(wc -l < "${RESPONSES_JSONL}" | tr -d ' ')
log "Respostas mescladas: ${RESPONSES_JSONL} (${RESP_COUNT} linhas)"

# ── Passo 4: Materializar variantes ──────────────────────────────────────────
step "4/5 — Materializar variantes"
"${WITUP}" avaliar-estudo-jdk-global \
  --config           "${RUNTIME_CONFIG}" \
  --generation-model "${MODEL_KEY}" \
  --jdk-root         "${JDK_ROOT}" \
  --run-dir          "${PILOT_DIR}" \
  --responses        "${RESPONSES_JSONL}"

for variant in wit-context direct-tests; do
  dir="${PILOT_DIR}/variants/${variant}/test/jdk/witup/generated"
  if [[ -d "${dir}" ]]; then
    count=$(find "${dir}" -name "*.java" | wc -l | tr -d ' ')
    log "  ${variant}: ${count} arquivo(s) .java"
  else
    log "  ${variant}: (sem diretório)"
  fi
done

# ── Passo 5: Zipar variants/ ──────────────────────────────────────────────────
step "5/5 — Zipar variants/"
ZIP_FILE="${PILOT_DIR}/variants-generated.zip"
(cd "${PILOT_DIR}" && zip -r "${ZIP_FILE}" variants/)
log "ZIP: ${ZIP_FILE} ($(du -sh "${ZIP_FILE}" | cut -f1))"

log ""
log "════════════════════════════════════════"
log "Retomada concluída!"
log "  ZIP: ${ZIP_FILE}"
log "  Próximo: upload para S3 + relançar CodeBuild com JTREG_CONCURRENCY=32"
log "════════════════════════════════════════"

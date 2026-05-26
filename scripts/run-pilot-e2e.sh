#!/usr/bin/env bash
# run-pilot-e2e.sh
# Pipeline end-to-end do piloto JDK 21 + gpt-4.1-nano.
# Executa: WIT → config → prepare → batch submit → batch collect → evaluate
#
# Pré-requisitos:
#   - docker compose build evaluator   (imagem witup-llm/evaluator:latest)
#   - export OPENAI_API_KEY=sk-...
#
# Variáveis customizáveis:
#   METHOD_COUNT     : métodos por cenário (default: 20)
#   EXPERIMENT_DIR   : pasta em generated/experiments/ (default: jdk21-pilot)
#   WIT_SCOPE        : subpasta do JDK 21 para WIT (default: java.lang)
#   WIT_OUTPUT_NAME  : nome do output WIT (default: jdk21-pilot)
#   SKIP_WIT         : "sim" para pular etapa WIT se já existir (default: não)
#   SKIP_PREPARE     : "sim" para pular preparação (default: não)
#   WAIT_BATCH       : "sim" para aguardar batch completar (default: sim)
#
# Uso:
#   export OPENAI_API_KEY=sk-...
#   bash scripts/run-pilot-e2e.sh

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

# Mantém o Mac acordado durante todo o pipeline
if command -v caffeinate &>/dev/null; then
  caffeinate -i -w $$ &
  CAFFEINATE_PID=$!
  trap 'kill "${CAFFEINATE_PID}" 2>/dev/null || true' EXIT
fi
RUN_STAMP="${RUN_STAMP:-$(date -u +%Y%m%dT%H%M%SZ)_pilot_gpt41nano}"
EXPERIMENT_DIR="${EXPERIMENT_DIR:-jdk21-pilot}"
METHOD_COUNT="${METHOD_COUNT:-20}"
WIT_OUTPUT_NAME="${WIT_OUTPUT_NAME:-jdk21-pilot}"
WIT_SCOPE="${WIT_SCOPE:-src/java.base/share/classes}"
SKIP_WIT="${SKIP_WIT:-não}"
SKIP_PREPARE="${SKIP_PREPARE:-não}"
WAIT_BATCH="${WAIT_BATCH:-sim}"
RUNTIME_CONFIG_FILE="${RUNTIME_CONFIG_FILE:-jdk21-pilot.runtime.json}"
GENERATION_MODEL="${GENERATION_MODEL:-gpt-4.1-nano}"

RUN_DIR="${ROOT_DIR}/generated/experiments/${EXPERIMENT_DIR}/${RUN_STAMP}"
# do-post-filter.py grava wit_filtered.json no mesmo dir do wit.json (subpasta do symlink)
WIT_FILTERED="$(find "${ROOT_DIR}/generated/wit-output/${WIT_OUTPUT_NAME}" -name "wit_filtered.json" 2>/dev/null | head -1 || true)"
WIT_FILTERED="${WIT_FILTERED:-${ROOT_DIR}/generated/wit-output/${WIT_OUTPUT_NAME}/wit_filtered.json}"
REQUESTS_JSONL="${RUN_DIR}/requests_${RUN_STAMP}_openai_batch_generation.jsonl"
BATCH_METADATA="${RUN_DIR}/batch_${RUN_STAMP}_openai_submission.json"
BIN="${ROOT_DIR}/bin/witup"

log() {
  printf '\n[pilot-e2e] ══ %s ══\n' "$*"
}

step() {
  printf '[pilot-e2e] → %s\n' "$*"
}

check_api_key() {
  if [[ -z "${OPENAI_API_KEY:-}" ]]; then
    printf 'erro: OPENAI_API_KEY não definida.\n' >&2
    printf '  Execute: export OPENAI_API_KEY=sk-...\n' >&2
    exit 1
  fi
}

check_evaluator_image() {
  if ! docker image inspect witup-llm/evaluator:latest >/dev/null 2>&1; then
    printf 'erro: imagem witup-llm/evaluator:latest não encontrada.\n' >&2
    printf '  Execute primeiro: docker compose build evaluator\n' >&2
    exit 1
  fi
}

compile_witup() {
  if [[ ! -x "${BIN}" ]]; then
    step "Compilando binário witup..."
    (cd "${ROOT_DIR}" && go build -o "${BIN}" ./cmd/witup)
  fi
}

# ── Validações iniciais ────────────────────────────────────────────────────────
check_api_key
check_evaluator_image
compile_witup

mkdir -p "${RUN_DIR}"

log "Configuração do piloto"
printf '  RUN_STAMP        = %s\n' "${RUN_STAMP}"
printf '  EXPERIMENT_DIR   = %s\n' "${EXPERIMENT_DIR}"
printf '  RUN_DIR          = %s\n' "${RUN_DIR}"
printf '  METHOD_COUNT     = %s\n' "${METHOD_COUNT}"
printf '  WIT_OUTPUT_NAME  = %s\n' "${WIT_OUTPUT_NAME}"
printf '  WIT_SCOPE        = %s\n' "${WIT_SCOPE}"
printf '  RUNTIME_CONFIG   = %s\n' "${RUNTIME_CONFIG_FILE}"
printf '  GENERATION_MODEL = %s\n' "${GENERATION_MODEL}"

# ── Fase 1: WIT ───────────────────────────────────────────────────────────────
log "Fase 1/5: WIT estático"

if [[ "${SKIP_WIT}" =~ ^(sim|1|yes|true)$ && -f "${WIT_FILTERED}" ]]; then
  step "SKIP_WIT=sim e ${WIT_FILTERED} existe — pulando WIT."
else
  step "Rodando WIT sobre ${WIT_SCOPE} (pode demorar 5-30 min)..."
  WIT_SCOPE="${WIT_SCOPE}" \
  WIT_OUTPUT_NAME="${WIT_OUTPUT_NAME}" \
  WIT_MAX_MEMORY="${WIT_MAX_MEMORY:-5g}" \
    docker compose run --rm wit-pilot

  step "Pós-processamento WIT (do-post-filter)..."
  WIT_OUTPUT_NAME="${WIT_OUTPUT_NAME}" \
    docker compose run --rm wit-post-process
fi

if [[ ! -f "${WIT_FILTERED}" ]]; then
  printf 'erro: wit_filtered.json não encontrado em %s\n' "${WIT_FILTERED}" >&2
  exit 1
fi

WIT_COUNT=$(python3 -c "import json; d=json.load(open('${WIT_FILTERED}')); print(len(d))" 2>/dev/null || echo "?")
step "WIT: ${WIT_COUNT} métodos com exception paths em ${WIT_FILTERED}"

# ── Fase 2: Preparação (gera JSONL) ───────────────────────────────────────────
log "Fase 2/5: Preparação — gerar requisições LLM"

if [[ "${SKIP_PREPARE}" =~ ^(sim|1|yes|true)$ && -f "${REQUESTS_JSONL}" ]]; then
  step "SKIP_PREPARE=sim e ${REQUESTS_JSONL} existe — pulando prepare."
else
  step "Preparando ${METHOD_COUNT} métodos × 2 cenários (via container)..."
  RUN_STAMP="${RUN_STAMP}" \
  EXPERIMENT_DIR="${EXPERIMENT_DIR}" \
  RUNTIME_CONFIG_FILE="${RUNTIME_CONFIG_FILE}" \
  GENERATION_MODEL="${GENERATION_MODEL}" \
  WIT_OUTPUT_NAME="${WIT_OUTPUT_NAME}" \
  METHOD_COUNT="${METHOD_COUNT}" \
    docker compose run --rm prepare
fi

REQ_COUNT=$(wc -l < "${REQUESTS_JSONL}" 2>/dev/null || echo "?")
step "Geradas ${REQ_COUNT} requisições em ${REQUESTS_JSONL}"

# ── Fase 3: Submeter batch OpenAI ─────────────────────────────────────────────
log "Fase 3/5: Submeter batch OpenAI"

step "Submetendo batch (backend=batch, endpoint=/v1/responses)..."
CONFIRMAR_EXECUCAO_PAGA=sim \
RUN_DIR="${RUN_DIR}" \
RUNTIME_CONFIG="${ROOT_DIR}/generated/configs/${RUNTIME_CONFIG_FILE}" \
GENERATION_MODEL="${GENERATION_MODEL}" \
REQUESTS_JSONL="${REQUESTS_JSONL}" \
BATCH_METADATA="${BATCH_METADATA}" \
  "${ROOT_DIR}/scripts/submit-article-main-batch.sh"

BATCH_ID=$(python3 -c "
import json, sys
try:
  d = json.load(open('${BATCH_METADATA}'))
  print(d.get('batch_id') or d.get('id') or '')
except Exception as e:
  print('', end='')
" 2>/dev/null || echo "")

if [[ -z "${BATCH_ID}" ]]; then
  printf 'erro: não consegui extrair batch_id de %s\n' "${BATCH_METADATA}" >&2
  exit 1
fi
step "Batch submetido: ${BATCH_ID}"

# ── Fase 4: Aguardar e coletar batch ──────────────────────────────────────────
log "Fase 4/5: Aguardar e coletar batch"

RESPONSES_JSONL="${RUN_DIR}/responses_openai_batch_generation.jsonl"
ERRORS_JSONL="${RUN_DIR}/errors_openai_batch_generation.jsonl"

if [[ "${WAIT_BATCH}" == "sim" ]]; then
  step "Aguardando batch ${BATCH_ID} completar (polling a cada 60s)..."
  while true; do
    STATUS=$(python3 -c "
import urllib.request, json, os
req = urllib.request.Request(
  'https://api.openai.com/v1/batches/${BATCH_ID}',
  headers={'Authorization': 'Bearer ' + os.environ['OPENAI_API_KEY']}
)
with urllib.request.urlopen(req) as r:
  d = json.loads(r.read())
print(d.get('status','unknown'))
" 2>/dev/null || echo "error")
    printf '[pilot-e2e] Batch status: %s\n' "${STATUS}"
    if [[ "${STATUS}" == "completed" || "${STATUS}" == "failed" || "${STATUS}" == "expired" || "${STATUS}" == "cancelled" ]]; then
      break
    fi
    sleep 60
  done

  if [[ "${STATUS}" != "completed" ]]; then
    printf 'aviso: batch terminou com status=%s. Verifique manualmente.\n' "${STATUS}" >&2
  fi
fi

step "Coletando resultados do batch..."
RUN_DIR="${RUN_DIR}" \
BATCH_ID="${BATCH_ID}" \
BATCH_METADATA="${BATCH_METADATA}" \
RESPONSES_JSONL="${RESPONSES_JSONL}" \
ERRORS_JSONL="${ERRORS_JSONL}" \
  "${ROOT_DIR}/scripts/collect-article-main-batch.sh"

# ── Fase 5: Avaliação ─────────────────────────────────────────────────────────
log "Fase 5/5: Avaliar testes gerados"

step "Executando avaliação (compilação + jtreg + métricas)..."
RUN_DIR="${RUN_DIR}" \
RUNTIME_CONFIG="${ROOT_DIR}/generated/configs/${RUNTIME_CONFIG_FILE}" \
GENERATION_MODEL="${GENERATION_MODEL}" \
  "${ROOT_DIR}/scripts/evaluate-jdk-global-impact-experiment.sh"

# ── Resumo final ──────────────────────────────────────────────────────────────
log "Piloto concluído!"
printf '  RUN_DIR: %s\n' "${RUN_DIR}"
printf '  Resultados:\n'
ls "${RUN_DIR}/" 2>/dev/null | sed 's/^/    /'

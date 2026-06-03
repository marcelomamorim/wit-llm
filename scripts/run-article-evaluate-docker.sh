#!/usr/bin/env bash
# run-article-evaluate-docker.sh  (host-side)
#
# Lança o serviço article-evaluate via docker compose.
# Executa a avaliação completa: compilação, Surefire, JaCoCo e PIT
# sobre os testes gerados pelo batch LLM.
#
# Uso:
#   RESPONSES_JSONL=~/Downloads/batch_XXX_output.jsonl \
#     ./scripts/run-article-evaluate-docker.sh
#
# Variáveis obrigatórias:
#   RESPONSES_JSONL : caminho (host) para o arquivo JSONL de respostas do batch
#
# Variáveis opcionais:
#   ERRORS_JSONL    : caminho (host) para o arquivo JSONL de erros do batch
#   RUN_STAMP       : timestamp da rodada (default: auto)
#   GENERATION_MODEL: chave do modelo no runtime.json (default: openai_main)

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

log() { printf '[article-evaluate/host] %s\n' "$*"; }
err() { printf '[article-evaluate/host] ERRO: %s\n' "$*" >&2; exit 1; }

RESPONSES_JSONL="${RESPONSES_JSONL:-}"
ERRORS_JSONL="${ERRORS_JSONL:-}"
RUN_STAMP="${RUN_STAMP:-$(date -u +%Y%m%dT%H%M%SZ)}"
GENERATION_MODEL="${GENERATION_MODEL:-openai_main}"

[[ -n "${RESPONSES_JSONL}" ]] \
  || err "RESPONSES_JSONL é obrigatório. Ex: RESPONSES_JSONL=~/Downloads/batch_output.jsonl $0"
[[ -f "${RESPONSES_JSONL}" ]] \
  || err "arquivo não encontrado: ${RESPONSES_JSONL}"

# Verificar que o setup já foi executado
REPOS_CHECK="${ROOT_DIR}/generated/repos"
WIT_CHECK="${ROOT_DIR}/generated/wit-baselines"
if [[ ! -d "${REPOS_CHECK}/commons-io" || ! -d "${REPOS_CHECK}/joda-time" ]]; then
  log "Projetos Java não encontrados em ${REPOS_CHECK}."
  log "Execute primeiro: ./scripts/run-article-setup-docker.sh"
  exit 1
fi
if [[ ! -f "${WIT_CHECK}/commons-io/wit_filtered.json" ]]; then
  log "Baselines WIT não encontrados em ${WIT_CHECK}."
  log "Execute primeiro: ./scripts/run-article-setup-docker.sh"
  exit 1
fi

# Resolver caminhos absolutos e calcular diretório para volume
RESPONSES_ABS="$(realpath "${RESPONSES_JSONL}")"
BATCH_DIR="$(dirname "${RESPONSES_ABS}")"
RESPONSES_IN_CONTAINER="/data/batch-input/$(basename "${RESPONSES_ABS}")"

ERRORS_ARGS=()
ERRORS_IN_CONTAINER=""
if [[ -n "${ERRORS_JSONL}" && -f "${ERRORS_JSONL}" ]]; then
  ERRORS_ABS="$(realpath "${ERRORS_JSONL}")"
  ERRORS_DIR="$(dirname "${ERRORS_ABS}")"
  if [[ "${ERRORS_DIR}" != "${BATCH_DIR}" ]]; then
    log "Aviso: ERRORS_JSONL e RESPONSES_JSONL estão em diretórios diferentes."
    log "       Apenas o diretório de RESPONSES_JSONL será montado."
  fi
  ERRORS_IN_CONTAINER="/data/batch-input/$(basename "${ERRORS_ABS}")"
  ERRORS_ARGS=(-e "ERRORS_JSONL=${ERRORS_IN_CONTAINER}")
fi

log "RESPONSES_JSONL (host): ${RESPONSES_ABS}"
log "RUN_STAMP: ${RUN_STAMP}"
log "GENERATION_MODEL: ${GENERATION_MODEL}"
log ""
log "Iniciando avaliação..."

docker compose -f "${ROOT_DIR}/docker-compose.yml" run --rm \
  -e "RESPONSES_JSONL=${RESPONSES_IN_CONTAINER}" \
  "${ERRORS_ARGS[@]}" \
  -e "RUN_STAMP=${RUN_STAMP}" \
  -e "GENERATION_MODEL=${GENERATION_MODEL}" \
  -e "BATCH_DIR=${BATCH_DIR}" \
  --volume "${BATCH_DIR}:/data/batch-input:ro" \
  article-evaluate

log ""
log "Resultados em: ${ROOT_DIR}/generated/results/article-eval/"

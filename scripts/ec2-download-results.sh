#!/usr/bin/env bash
# ec2-download-results.sh — roda NO MAC
#
# Baixa os resultados do jcov-baseline da EC2 para o diretório local do run.
#
# Uso:
#   EC2_HOST=ec2-user@<ip-publico> \
#   RUN_STAMP=pilot-20260607T041241Z \
#     ./scripts/ec2-download-results.sh

set -euo pipefail

EC2_HOST="${EC2_HOST:-}"
RUN_STAMP="${RUN_STAMP:-}"
EXPERIMENT_DIR="${EXPERIMENT_DIR:-jdk-pilot}"

[[ -n "${EC2_HOST}" ]]  || { echo "Erro: EC2_HOST obrigatório"; exit 1; }
[[ -n "${RUN_STAMP}" ]] || { echo "Erro: RUN_STAMP obrigatório"; exit 1; }

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
RUN_DIR="${ROOT_DIR}/generated/experiments/${EXPERIMENT_DIR}/${RUN_STAMP}"

log() { printf '\n[ec2-download] %s\n' "$*"; }

log "Baixando resultados jcov-baseline de ${EC2_HOST}..."

rsync -avz --progress \
  "${EC2_HOST}:/home/ec2-user/wit-llm/generated/experiments/${EXPERIMENT_DIR}/${RUN_STAMP}/jcov-baseline/" \
  "${RUN_DIR}/jcov-baseline/"

log "Download concluído em ${RUN_DIR}/jcov-baseline/"
log ""
log "Próximo passo — rodar jcov para wit-context e direct-tests:"
log "  docker compose run --rm \\"
log "    -e EXPERIMENT_DIR=${EXPERIMENT_DIR} \\"
log "    -e RUN_STAMP=${RUN_STAMP} \\"
log "    run-jcov"

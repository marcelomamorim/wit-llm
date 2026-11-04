#!/usr/bin/env bash
# codebuild-download-results.sh — roda NO MAC
#
# Baixa os resultados do jcov-baseline do S3 para o diretório local.
#
# Uso:
#   RUN_STAMP=pilot-20260607T041241Z \
#   S3_BUCKET=witup-jcov-123456789012 \
#   AWS_REGION=us-east-1 \
#     ./scripts/codebuild-download-results.sh

set -euo pipefail

RUN_STAMP="${RUN_STAMP:-}"
S3_BUCKET="${S3_BUCKET:-}"
AWS_REGION="${AWS_REGION:-us-east-1}"
EXPERIMENT_DIR="${EXPERIMENT_DIR:-jdk-pilot}"

[[ -n "${RUN_STAMP}" ]]  || { echo "Erro: RUN_STAMP obrigatório"; exit 1; }
[[ -n "${S3_BUCKET}" ]]  || { echo "Erro: S3_BUCKET obrigatório (ex: witup-jcov-123456789012)"; exit 1; }

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
RUN_DIR="${ROOT_DIR}/generated/experiments/${EXPERIMENT_DIR}/${RUN_STAMP}"

log() { printf '\n[codebuild-download] %s\n' "$*"; }

log "Baixando jcov-baseline de s3://${S3_BUCKET}/${EXPERIMENT_DIR}/${RUN_STAMP}/jcov-baseline/"

aws s3 sync \
  "s3://${S3_BUCKET}/${EXPERIMENT_DIR}/${RUN_STAMP}/jcov-baseline" \
  "${RUN_DIR}/jcov-baseline" \
  --region "${AWS_REGION}"

log "Download concluído em ${RUN_DIR}/jcov-baseline/"

# Verificar se o jcov-result.xml foi gerado
if [[ -f "${RUN_DIR}/jcov-baseline/jcov-result.xml" ]]; then
  size=$(du -sh "${RUN_DIR}/jcov-baseline/jcov-result.xml" | cut -f1)
  log "jcov-result.xml: ${size} ✓"
else
  log "AVISO: jcov-result.xml não encontrado — o build pode ter falhado."
fi

log ""
log "Próximo passo — rodar jcov para wit-context e direct-tests:"
log "  docker compose run --rm \\"
log "    -e EXPERIMENT_DIR=${EXPERIMENT_DIR} \\"
log "    -e RUN_STAMP=${RUN_STAMP} \\"
log "    run-jcov"

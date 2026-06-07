#!/usr/bin/env bash
# ec2-sync-and-run.sh — roda NO MAC
#
# Sincroniza o run atual para a EC2 e dispara o jcov-baseline com alta concorrência.
# Reutiliza os 965 testes já executados (jtreg pula .jtr existentes).
#
# Uso:
#   EC2_HOST=ec2-user@<ip-publico> \
#   RUN_STAMP=pilot-20260607T041241Z \
#     ./scripts/ec2-sync-and-run.sh
#
# Pré-requisitos:
#   - ec2-setup.sh já executado na EC2
#   - Chave SSH configurada (~/.ssh/config ou -i flag)
#   - JTREG_CONCURRENCY ajustável (default: 24 para c7i.8xlarge)

set -euo pipefail

EC2_HOST="${EC2_HOST:-}"
RUN_STAMP="${RUN_STAMP:-}"
JTREG_CONCURRENCY="${JTREG_CONCURRENCY:-24}"
EXPERIMENT_DIR="${EXPERIMENT_DIR:-jdk-pilot}"

[[ -n "${EC2_HOST}" ]]  || { echo "Erro: EC2_HOST obrigatório (ex: ec2-user@1.2.3.4)"; exit 1; }
[[ -n "${RUN_STAMP}" ]] || { echo "Erro: RUN_STAMP obrigatório"; exit 1; }

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
RUN_DIR="${ROOT_DIR}/generated/experiments/${EXPERIMENT_DIR}/${RUN_STAMP}"

log() { printf '\n[ec2-sync] %s\n' "$*"; }

log "Sincronizando run ${RUN_STAMP} para ${EC2_HOST}..."
log "  Origem : ${RUN_DIR}"
log "  Destino: ${EC2_HOST}:/home/ec2-user/wit-llm/generated/experiments/${EXPERIMENT_DIR}/${RUN_STAMP}"

# Criar diretório remoto
ssh "${EC2_HOST}" "mkdir -p /home/ec2-user/wit-llm/generated/experiments/${EXPERIMENT_DIR}/${RUN_STAMP}"

# Sincronizar apenas o necessário:
#   - variants/        (testes materializados)
#   - jcov-baseline/work/ (testes já executados — jtreg vai pular)
#   - preparation_*.json, manifest_*.csv (metadados)
rsync -avz --progress \
  --include="variants/***" \
  --include="jcov-baseline/***" \
  --include="preparation_jdk_global_impact.json" \
  --include="manifest_jdk_global_methods.csv" \
  --exclude="*" \
  "${RUN_DIR}/" \
  "${EC2_HOST}:/home/ec2-user/wit-llm/generated/experiments/${EXPERIMENT_DIR}/${RUN_STAMP}/"

log "Sync concluído. Disparando jcov-baseline na EC2..."
log "  JTREG_CONCURRENCY=${JTREG_CONCURRENCY}"

# Rodar jcov-baseline na EC2 em background com nohup
# Logs em ~/jcov-baseline.log
ssh "${EC2_HOST}" "
  cd /home/ec2-user/wit-llm
  nohup sudo docker compose run --rm \
    -e EXPERIMENT_DIR=${EXPERIMENT_DIR} \
    -e RUN_STAMP=${RUN_STAMP} \
    -e JTREG_CONCURRENCY=${JTREG_CONCURRENCY} \
    jcov-baseline \
  > ~/jcov-baseline.log 2>&1 &
  echo \"PID: \$!\"
  echo 'Rodando em background. Acompanhe com:'
  echo '  ssh ${EC2_HOST} tail -f ~/jcov-baseline.log'
"

log "Pronto! Para acompanhar:"
log "  ssh ${EC2_HOST} tail -f ~/jcov-baseline.log"
log ""
log "Para baixar os resultados quando terminar:"
log "  EC2_HOST=${EC2_HOST} RUN_STAMP=${RUN_STAMP} ./scripts/ec2-download-results.sh"

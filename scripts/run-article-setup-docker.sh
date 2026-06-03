#!/usr/bin/env bash
# run-article-setup-docker.sh  (host-side)
#
# Lança o serviço article-setup via docker compose.
# Clona os 7 projetos Java, pré-aquece Maven e gera baselines WIT.
# Resultado persiste em ./generated/ para ser reutilizado na avaliação.
#
# Uso:
#   ./scripts/run-article-setup-docker.sh
#
# Variáveis opcionais:
#   FORCE_RECLONE     : "sim" para re-clonar mesmo se já existir
#   SKIP_MAVEN_WARMUP : "sim" para pular pré-aquecimento Maven
#   JODA_TIME_COMMIT  : ref do joda-time  (default: v2.12.7)
#   LOG4J2_COMMIT     : ref do log4j2     (default: rel/2.23.1)
#   WITUP_COMMIT      : commit do witup a compilar (default: main)

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

log() { printf '[article-setup/host] %s\n' "$*"; }

log "Construindo imagem witup-llm/article-evaluator:latest (pode demorar na primeira vez)..."
docker compose -f "${ROOT_DIR}/docker-compose.yml" build article-setup

log "Iniciando setup do ambiente de avaliação..."
docker compose -f "${ROOT_DIR}/docker-compose.yml" run --rm \
  -e FORCE_RECLONE="${FORCE_RECLONE:-nao}" \
  -e SKIP_MAVEN_WARMUP="${SKIP_MAVEN_WARMUP:-nao}" \
  -e JODA_TIME_COMMIT="${JODA_TIME_COMMIT:-v2.12.7}" \
  -e LOG4J2_COMMIT="${LOG4J2_COMMIT:-rel/2.23.1}" \
  article-setup

log "Setup concluído. Execute agora:"
log "  RESPONSES_JSONL=~/Downloads/<batch_output>.jsonl \\"
log "    ./scripts/run-article-evaluate-docker.sh"

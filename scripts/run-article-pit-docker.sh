#!/usr/bin/env bash
# run-article-pit-docker.sh  (host-side)
#
# Lança o serviço article-pit via docker compose.
# Compila individualmente, poda testes com falha, executa JaCoCo + PIT
# sobre os testes gerados pelo batch LLM.
#
# Requer:
#   - article-setup já executado  (projetos clonados + Maven aquecido)
#   - article-evaluate já executado (testes materializados em generated/results/article-eval/)
#
# Uso:
#   ./scripts/run-article-pit-docker.sh

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

log() { printf '[article-pit/host] %s\n' "$*"; }

log "Construindo imagem witup-llm/article-evaluator:latest (pode demorar na primeira vez)..."
docker compose -f "${ROOT_DIR}/docker-compose.yml" build article-setup

log "Iniciando avaliação PIT + JaCoCo..."
docker compose -f "${ROOT_DIR}/docker-compose.yml" run --rm article-pit

log "Avaliação PIT concluída."
log "Resultados em: ${ROOT_DIR}/generated/results/pit-eval/"

#!/usr/bin/env bash
# run-jcov-pilot.sh  (host-side)
#
# Mede cobertura JCov (branch) para o piloto JDK:
#   1. baseline: testes originais de com/sun/crypto/provider/Cipher/AES
#   2. wit-context: testes gerados com WIT
#   3. direct-tests: testes gerados sem WIT
#
# Usa o mesmo wrapper seletivo do run-jcov-docker.sh:
# injeta o agente JCov apenas quando jtreg executa MainWrapper (teste real).
#
# Uso:
#   EXPERIMENT_DIR=jdk-pilot \
#   RUN_STAMP=pilot-20260603T010806Z \
#     ./scripts/run-jcov-pilot.sh

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
EXPERIMENT_DIR="${EXPERIMENT_DIR:-jdk-pilot}"
RUN_STAMP="${RUN_STAMP:-}"
JTREG_CONCURRENCY="${JTREG_CONCURRENCY:-1}"

[[ -n "${RUN_STAMP}" ]] || { echo "ERRO: RUN_STAMP é obrigatório" >&2; exit 1; }

log() { printf '[jcov-pilot] %s\n' "$*"; }

log "EXPERIMENT_DIR=${EXPERIMENT_DIR}"
log "RUN_STAMP=${RUN_STAMP}"

docker compose -f "${ROOT_DIR}/docker-compose.yml" run --rm \
  -v "${ROOT_DIR}/scripts:/data/scripts:ro" \
  -e EXPERIMENT_DIR="${EXPERIMENT_DIR}" \
  -e RUN_STAMP="${RUN_STAMP}" \
  -e JTREG_CONCURRENCY="${JTREG_CONCURRENCY}" \
  witup-llm/evaluator:latest \
  bash /data/scripts/run-jcov-pilot-docker.sh 2>&1 || \
docker run --rm \
  -v "${ROOT_DIR}/generated:/data/generated" \
  -v "${ROOT_DIR}/scripts:/data/scripts:ro" \
  -e EXPERIMENT_DIR="${EXPERIMENT_DIR}" \
  -e RUN_STAMP="${RUN_STAMP}" \
  -e JTREG_CONCURRENCY="${JTREG_CONCURRENCY}" \
  witup-llm/evaluator:latest \
  bash /data/scripts/run-jcov-pilot-docker.sh

log "JCov piloto concluído."
log "Resultados: ${ROOT_DIR}/generated/experiments/${EXPERIMENT_DIR}/${RUN_STAMP}/jcov-results/"

#!/usr/bin/env bash
# Executa avaliar-estudo-jdk-global dentro do container Docker.
# Variáveis de ambiente esperadas (definidas no docker-compose.yml):
#   EXPERIMENT_DIR      : subdiretório em generated/experiments/
#   RUN_STAMP           : timestamp do run
#   RUNTIME_CONFIG_FILE : arquivo de config runtime (ex: rodada-artigo.runtime.json)
#   GENERATION_MODEL    : chave do modelo (ex: openai_main)

set -euo pipefail

EXPERIMENT_DIR="${EXPERIMENT_DIR:-jdk21-pilot}"
RUN_STAMP="${RUN_STAMP:-run}"
RUNTIME_CONFIG_FILE="${RUNTIME_CONFIG_FILE:-jdk21-pilot.runtime.json}"
GENERATION_MODEL="${GENERATION_MODEL:-openai_main}"

RUN_DIR="/data/generated/experiments/${EXPERIMENT_DIR}/${RUN_STAMP}"

exec witup avaliar-estudo-jdk-global \
    --config    "/data/generated/configs/${RUNTIME_CONFIG_FILE}" \
    --generation-model "${GENERATION_MODEL}" \
    --jdk-root  /opt/openjdk-src \
    --run-dir   "${RUN_DIR}" \
    --responses "${RUN_DIR}/responses_openai_batch_generation.jsonl" \
    --errors    "${RUN_DIR}/errors_openai_batch_generation.jsonl"

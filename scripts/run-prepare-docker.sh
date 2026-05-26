#!/usr/bin/env bash
# run-prepare-docker.sh
# Roda dentro do container evaluator.
# Executa witup preparar-estudo-jdk-global com o JDK source em /opt/openjdk-src.
#
# Variáveis de ambiente esperadas (passadas pelo docker compose run):
#   RUNTIME_CONFIG_FILE  : nome do arquivo de config em /data/generated/configs/
#   GENERATION_MODEL     : chave do modelo
#   WIT_OUTPUT_NAME      : nome da pasta de output do WIT
#   RUN_STAMP            : timestamp da rodada
#   EXPERIMENT_DIR       : subpasta em generated/experiments/
#   METHOD_COUNT         : número de métodos

set -euo pipefail

RUNTIME_CONFIG_FILE="${RUNTIME_CONFIG_FILE:-jdk21-pilot.runtime.json}"
GENERATION_MODEL="${GENERATION_MODEL:-gpt-4.1-nano}"
WIT_OUTPUT_NAME="${WIT_OUTPUT_NAME:-jdk21-pilot}"
RUN_STAMP="${RUN_STAMP:-run}"
EXPERIMENT_DIR="${EXPERIMENT_DIR:-jdk21-pilot}"
METHOD_COUNT="${METHOD_COUNT:-20}"

CONFIG_PATH="/data/generated/configs/${RUNTIME_CONFIG_FILE}"
JDK_ROOT="/opt/openjdk-src"
OUTPUT_DIR="/data/generated/experiments/${EXPERIMENT_DIR}/${RUN_STAMP}"
WIT_JSON=$(find "/data/generated/wit-output/${WIT_OUTPUT_NAME}" -name "wit_filtered.json" | head -1)

if [[ -z "${WIT_JSON}" ]]; then
  echo "erro: wit_filtered.json não encontrado em /data/generated/wit-output/${WIT_OUTPUT_NAME}" >&2
  exit 1
fi

mkdir -p "${OUTPUT_DIR}"

echo "[prepare] Config       : ${CONFIG_PATH}"
echo "[prepare] JDK root     : ${JDK_ROOT}"
echo "[prepare] WIT filtered : ${WIT_JSON}"
echo "[prepare] Output dir   : ${OUTPUT_DIR}"
echo "[prepare] Método count : ${METHOD_COUNT}"

witup preparar-estudo-jdk-global \
  --config "${CONFIG_PATH}" \
  --generation-model "${GENERATION_MODEL}" \
  --jdk-root "${JDK_ROOT}" \
  --wit-analysis "${WIT_JSON}" \
  --output-dir "${OUTPUT_DIR}" \
  --method-count "${METHOD_COUNT}"

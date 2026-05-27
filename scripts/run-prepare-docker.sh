#!/usr/bin/env bash
# run-prepare-docker.sh
# Roda dentro do container evaluator.
# Executa witup preparar-estudo-jdk-global com o JDK source em /opt/openjdk-src.
#
# Variáveis de ambiente esperadas (passadas pelo docker compose run):
#   RUNTIME_CONFIG_FILE  : nome do arquivo de config em /data/generated/configs/
#   GENERATION_MODEL     : chave do modelo
#   WIT_OUTPUT_NAME      : nome da pasta de output do WIT (compat. legada)
#   RUN_STAMP            : timestamp da rodada
#   EXPERIMENT_DIR       : subpasta em generated/experiments/
#   METHOD_COUNT         : número de métodos (0 = todos)

set -euo pipefail

RUNTIME_CONFIG_FILE="${RUNTIME_CONFIG_FILE:-jdk21-pilot.runtime.json}"
GENERATION_MODEL="${GENERATION_MODEL:-openai_main}"
WIT_OUTPUT_NAME="${WIT_OUTPUT_NAME:-jdk21-pilot}"
RUN_STAMP="${RUN_STAMP:-run}"
EXPERIMENT_DIR="${EXPERIMENT_DIR:-jdk-global-impact-study}"
METHOD_COUNT="${METHOD_COUNT:-0}"

CONFIG_PATH="/data/generated/configs/${RUNTIME_CONFIG_FILE}"
JDK_ROOT="/opt/openjdk-src"
OUTPUT_DIR="/data/generated/experiments/${EXPERIMENT_DIR}/${RUN_STAMP}"

# Localizar wit_filtered.json — dois caminhos possíveis:
#   1. Baked-in na imagem (novo Docker: figshare CC BY 4.0)
#   2. Montado via volume (compat. legada: generated/wit-output/)
WIT_JSON_BAKED="/data/resources/wit-replication-package/data/output/jdk/wit_filtered.json"
WIT_JSON_GENERATED=$(find "/data/generated/wit-output/${WIT_OUTPUT_NAME}" -name "wit_filtered.json" 2>/dev/null | head -1 || true)

if [ -f "${WIT_JSON_BAKED}" ]; then
  WIT_JSON="${WIT_JSON_BAKED}"
  echo "[prepare] WIT source   : baked-in (figshare CC BY 4.0)"
elif [ -n "${WIT_JSON_GENERATED}" ]; then
  WIT_JSON="${WIT_JSON_GENERATED}"
  echo "[prepare] WIT source   : volume montado (legado)"
else
  echo "erro: wit_filtered.json não encontrado." >&2
  echo "  Tentado: ${WIT_JSON_BAKED}" >&2
  echo "  Tentado: /data/generated/wit-output/${WIT_OUTPUT_NAME}/wit_filtered.json" >&2
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

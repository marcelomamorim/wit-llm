#!/usr/bin/env bash
# run-jcov-merge.sh
#
# Mescla todos os jcov-result.xml dos chunks do baseline em um único XML final.
# Executa dentro do container (tem acesso ao jcov.jar).
#
# Variáveis de ambiente:
#   EXPERIMENT_DIR : default jdk-global-impact-study
#   RUN_STAMP      : obrigatório

set -euo pipefail

EXPERIMENT_DIR="${EXPERIMENT_DIR:-jdk-global-impact-study}"
RUN_STAMP="${RUN_STAMP:-}"

[[ -n "${RUN_STAMP}" ]] || { echo "erro: RUN_STAMP é obrigatório" >&2; exit 1; }

JCOV_JAR=/opt/jcov/JCOV_BUILD/jcov_3.0/jcov.jar
BASE="/data/generated/experiments/${EXPERIMENT_DIR}/${RUN_STAMP}"
CHUNKS_DIR="${BASE}/jcov-baseline-chunks"
OUTPUT="${BASE}/jcov-baseline/jcov-result.xml"

mkdir -p "$(dirname "${OUTPUT}")"

# Coletar todos os XMLs dos chunks
XMLS=()
for xml in "${CHUNKS_DIR}"/*/jcov-result.xml; do
  [[ -f "${xml}" ]] && XMLS+=("${xml}")
done

printf '[jcov-merge] chunks encontrados: %d\n' "${#XMLS[@]}"
for x in "${XMLS[@]}"; do printf '  %s\n' "${x}"; done

[[ "${#XMLS[@]}" -gt 0 ]] || { echo "erro: nenhum jcov-result.xml encontrado em ${CHUNKS_DIR}" >&2; exit 1; }

printf '[jcov-merge] mesclando → %s\n' "${OUTPUT}"
# -boe skip: ignora arquivos com erros de proxy ($ProxyN) em vez de abortar
java -jar "${JCOV_JAR}" Merger \
  -boe skip \
  -output "${OUTPUT}" \
  "${XMLS[@]}" || true

# Verificar se o XML foi gerado mesmo com erros de proxy
if [[ ! -f "${OUTPUT}" ]]; then
  echo "erro: merge falhou — ${OUTPUT} não foi gerado" >&2
  exit 1
fi

ls -lh "${OUTPUT}"
printf '[jcov-merge] merge concluído.\n'

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

CLEAN_XMLS=()
TMPDIR_XMLS=$(mktemp -d)

printf '[jcov-merge] pré-filtrando proxy classes dos chunks...\n'
for xml in "${XMLS[@]}"; do
  chunk_name=$(basename "$(dirname "${xml}")")
  clean_xml="${TMPDIR_XMLS}/${chunk_name}-clean.xml"
  # Remove blocos <class name="com/sun/proxy/$Proxy..."> ... </class>
  python3 - "${xml}" "${clean_xml}" <<'PYEOF'
import sys, re

src, dst = sys.argv[1], sys.argv[2]
with open(src, 'r', encoding='utf-8', errors='replace') as f:
    content = f.read()

# Remove class elements for dynamic proxy classes
content = re.sub(
    r'<class[^>]*name="com/sun/proxy/\$Proxy[^"]*"[^>]*/?>(?:.*?</class>)?',
    '',
    content,
    flags=re.DOTALL
)

with open(dst, 'w', encoding='utf-8') as f:
    f.write(content)
PYEOF
  CLEAN_XMLS+=("${clean_xml}")
done

printf '[jcov-merge] mesclando → %s\n' "${OUTPUT}"
java -jar "${JCOV_JAR}" Merger \
  -boe skip \
  -output "${OUTPUT}" \
  "${CLEAN_XMLS[@]}" || true

rm -rf "${TMPDIR_XMLS}"

# Verificar se o XML foi gerado
if [[ ! -f "${OUTPUT}" ]]; then
  echo "erro: merge falhou — ${OUTPUT} não foi gerado" >&2
  exit 1
fi

ls -lh "${OUTPUT}"
printf '[jcov-merge] merge concluído.\n'

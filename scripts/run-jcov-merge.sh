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

printf '[jcov-merge] pré-filtrando classes dinâmicas dos chunks...\n'
for xml in "${XMLS[@]}"; do
  chunk_name=$(basename "$(dirname "${xml}")")
  clean_xml="${TMPDIR_XMLS}/${chunk_name}-clean.xml"
  python3 - "${xml}" "${clean_xml}" "${chunk_name}" <<'PYEOF'
import sys
import xml.etree.ElementTree as ET

src, dst, chunk_name = sys.argv[1], sys.argv[2], sys.argv[3]

DYNAMIC_PREFIXES = (
    'com/sun/proxy/$Proxy',
    'jdk/internal/reflect/Generated',
    'sun/reflect/Generated',
)

tree = ET.parse(src)
root = tree.getroot()

# Detecta namespace do root (pode ser ausente nos chunks brutos do JCov)
tag = root.tag
ns_prefix = tag.split('}')[0][1:] if tag.startswith('{') else ''
if ns_prefix:
    ET.register_namespace('', ns_prefix)
    ET.register_namespace('xsi', 'http://www.w3.org/2001/XMLSchema-instance')
    p_tag = f'{{{ns_prefix}}}package'
    c_tag = f'{{{ns_prefix}}}class'
else:
    p_tag = 'package'
    c_tag = 'class'

removed = 0
for pkg in root.iter(p_tag):
    to_remove = [
        cls for cls in pkg.findall(c_tag)
        if any(cls.get('name', '').startswith(p) for p in DYNAMIC_PREFIXES)
    ]
    for cls in to_remove:
        pkg.remove(cls)
        removed += 1

print(f'  [{chunk_name}] removidas {removed} classes dinâmicas (ns={ns_prefix or "none"})', flush=True)
tree.write(dst, encoding='unicode', xml_declaration=True)
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

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

# Chunks excluídos do baseline:
# chunk-1c  = java/lang/instrument (tier3/svc — fora de tier1+tier2)
# chunk-4b  = javax/net/ssl core (68.4% falha — testes exigem infra TLS não disponível no CodeBuild)
# chunk-4c  = javax/net/ssl DTLS+TLSv1x (74.6% falha — idem)
# chunk-6b  = httpclient/http2 (42.1% falha — HTTP/2 requer servidor ativo)
EXCLUDE_CHUNKS="${EXCLUDE_CHUNKS:-chunk-1c,chunk-4b,chunk-4c,chunk-6b}"

# Coletar todos os XMLs dos chunks (excluindo tier3)
XMLS=()
for xml in "${CHUNKS_DIR}"/*/jcov-result.xml; do
  [[ -f "${xml}" ]] || continue
  chunk_id=$(basename "$(dirname "${xml}")")
  if echo ",${EXCLUDE_CHUNKS}," | grep -q ",${chunk_id},"; then
    printf '[jcov-merge] EXCLUINDO chunk tier3: %s\n' "${chunk_id}"
    continue
  fi
  XMLS+=("${xml}")
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

# Pacotes 100% dinâmicos (todos os seus membros são gerados em runtime)
FULLY_DYNAMIC_PACKAGES = {
    'com.sun.proxy',   # java.lang.reflect.Proxy — todos são $ProxyN
}
# Prefixos de classes dinâmicas geradas em runtime (em qualquer pacote)
DYNAMIC_CLASS_PREFIXES = ('$Proxy', 'Generated')
# Classes estáticas com estrutura inconsistente entre chunks
INCONSISTENT_CLASSES = {
    ('sun.security.provider', 'SeedGenerator'),
    ('javax.naming.spi', 'NamingManager'),
}

tree = ET.parse(src)
root = tree.getroot()

# Detecta namespace do root
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
    pname = pkg.get('name', '')
    to_remove = [
        cls for cls in pkg.findall(c_tag)
        if pname in FULLY_DYNAMIC_PACKAGES
        or any(cls.get('name', '').startswith(p) for p in DYNAMIC_CLASS_PREFIXES)
        or (pname, cls.get('name', '')) in INCONSISTENT_CLASSES
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

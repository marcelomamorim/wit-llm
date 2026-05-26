#!/usr/bin/env bash
# run-wit-post-process-docker.sh
# Roda dentro do container evaluator.
# Executa do-post-filter.py sobre o wit.json produzido pelo WIT.
#
# Variáveis de ambiente:
#   WIT_OUTPUT_NAME : nome da pasta de output do WIT (default: jdk21-pilot)

set -euo pipefail

WIT_OUTPUT_NAME="${WIT_OUTPUT_NAME:-jdk21-pilot}"
WIT_DIR="/data/generated/wit-output/${WIT_OUTPUT_NAME}"

log() { printf '[post-filter] %s\n' "$*"; }

# Localiza wit.json — pode estar num subdiretório (WIT usa o nome do symlink)
WIT_JSON=$(find "${WIT_DIR}" -name "wit.json" | head -1)

if [[ -z "${WIT_JSON}" ]]; then
  log "ERRO: wit.json não encontrado em ${WIT_DIR}" >&2
  log "Conteúdo do diretório:" >&2
  ls -lR "${WIT_DIR}" 2>/dev/null || true
  exit 1
fi

OUT_DIR="$(dirname "${WIT_JSON}")"

log "Filtrando: ${WIT_JSON}"
log "Output dir: ${OUT_DIR}"

# do-post-filter.py recebe apenas sys.argv[1] = path do wit.json
# e grava wit_filtered.json no mesmo diretório
python3 /opt/wit/do-post-filter.py "${WIT_JSON}"

log "Concluído."
ls -lh "${OUT_DIR}/" 2>/dev/null || true

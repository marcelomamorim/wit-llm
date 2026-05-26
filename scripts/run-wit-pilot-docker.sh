#!/usr/bin/env bash
# run-wit-pilot-docker.sh
# Roda dentro do container evaluator.
# Executa WIT sobre um subconjunto do JDK 21 (java.lang por padrão) com -Xmx5g.
#
# Variáveis de ambiente:
#   WIT_SCOPE       : caminho relativo dentro de /opt/openjdk-src (default: src/java.base/share/classes/java/lang)
#   WIT_OUTPUT_NAME : nome da pasta de output                     (default: jdk21-pilot)
#   WIT_MAX_MEMORY  : memória heap do WIT                         (default: 5g)

set -euo pipefail

WIT_SCOPE="${WIT_SCOPE:-src/java.base/share/classes}"
WIT_OUTPUT_NAME="${WIT_OUTPUT_NAME:-jdk21-pilot}"
WIT_MAX_MEMORY="${WIT_MAX_MEMORY:-5g}"

JDK_SRC="/opt/openjdk-src"
WIT_PY="/opt/wit/run-wit.py"
WIT_JAR_PATH="/opt/wit/wit.jar"
OUTPUT_DIR="/data/generated/wit-output/${WIT_OUTPUT_NAME}"
SCOPE_PATH="${JDK_SRC}/${WIT_SCOPE}"

log() {
  printf '[wit-pilot] %s\n' "$*"
}

log "WIT scope: ${SCOPE_PATH}"
log "Output dir: ${OUTPUT_DIR}"
log "Max memory: ${WIT_MAX_MEMORY}"

# Verificar que o caminho existe
if [[ ! -d "${SCOPE_PATH}" ]]; then
  printf 'erro: escopo WIT não encontrado: %s\n' "${SCOPE_PATH}" >&2
  printf 'Diretórios disponíveis em %s/src:\n' "${JDK_SRC}" >&2
  ls "${JDK_SRC}/src" 2>/dev/null || true
  exit 1
fi

mkdir -p "${OUTPUT_DIR}"

# Nota: WIT não segue symlinks corretamente — passar o caminho real diretamente.
# O nome do projeto no output será o último componente do SCOPE_PATH (ex: "classes").

log "Iniciando WIT (pode demorar 5-30 min)..."

# Patch inline: corrige caminhos hardcoded e memória
PATCHED_RUNNER="/tmp/run-wit-patched.py"
sed \
  -e "s/-Xmx[0-9]*g/-Xmx${WIT_MAX_MEMORY}/g" \
  -e "s|JAR_NAME = \"wit.jar\"|JAR_NAME = \"${WIT_JAR_PATH}\"|g" \
  -e 's|POST_FILTERING_PY_SCRIPT = "do-post-filter.py"|POST_FILTERING_PY_SCRIPT = "/opt/wit/do-post-filter.py"|g' \
  -e 's|cmd = \["python", POST_FILTERING_PY_SCRIPT|cmd = ["python3", POST_FILTERING_PY_SCRIPT|g' \
  "${WIT_PY}" > "${PATCHED_RUNNER}"
chmod +x "${PATCHED_RUNNER}"

WIT_CLASSES_PER_RUN="${WIT_CLASSES_PER_RUN:-50}"

WIT_JAR="${WIT_JAR_PATH}" \
WIT_OUTPUT_PATH="${OUTPUT_DIR}" \
  python3 "${PATCHED_RUNNER}" \
    --root-path-symbol-solving \
    --skip-basic-measurements \
    --classes-per-execution="${WIT_CLASSES_PER_RUN}" \
    --single-project \
    "${SCOPE_PATH}"

log "WIT concluído. Output em: ${OUTPUT_DIR}"
ls -lh "${OUTPUT_DIR}/" 2>/dev/null || true

#!/usr/bin/env bash

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$ROOT_DIR"

log() {
  printf '[validar-codex] %s\n' "$*"
}

log "formatando Go"
gofmt -w ./cmd ./internal

log "executando testes Go"
env \
  -u OPENAI_API_KEY \
  -u OPENAI_API_KEY_LOCAL \
  -u OPENAI_BASE_URL \
  GOCACHE="${GOCACHE:-$ROOT_DIR/.gocache}" \
  go test ./...

log "executando go vet"
env \
  -u OPENAI_API_KEY \
  -u OPENAI_API_KEY_LOCAL \
  -u OPENAI_BASE_URL \
  GOCACHE="${GOCACHE:-$ROOT_DIR/.gocache}" \
  go vet ./...

log "validando go.mod/go.sum"
GOCACHE="${GOCACHE:-$ROOT_DIR/.gocache}" go mod tidy -diff

log "validando scripts shell"
if compgen -G "scripts/*.sh" >/dev/null; then
  bash -n scripts/*.sh
fi

log "validando JSON versionado"
python3 -m json.tool pipeline.example.json >/dev/null
python3 -m json.tool pipelines/fase-dois-commons-io-filtrado.json >/dev/null
python3 -m json.tool pipelines/fase-dois-guava-commons.json >/dev/null
python3 -m json.tool schemas/pipeline.schema.json >/dev/null

log "validando documentacao MkDocs"
if command -v mkdocs >/dev/null 2>&1; then
  MKDOCS_BIN="$(command -v mkdocs)"
elif [ -x "$ROOT_DIR/.venv-docs/bin/mkdocs" ]; then
  MKDOCS_BIN="$ROOT_DIR/.venv-docs/bin/mkdocs"
else
  cat >&2 <<'EOF'
erro: mkdocs nao encontrado.
Instale as dependencias de documentacao com:
  python3 -m venv .venv-docs
  .venv-docs/bin/python -m pip install -r requirements-docs.txt
EOF
  exit 1
fi
"$MKDOCS_BIN" build --strict --site-dir /tmp/witup-llm-site-check

log "validacao barata concluida"

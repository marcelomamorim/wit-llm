#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_ROOT="$(cd "${SCRIPT_DIR}/.." && pwd)"
CONFIRMAR=0
REMOVER_CACHE=1

while [[ $# -gt 0 ]]; do
  case "$1" in
    --confirmar)
      CONFIRMAR=1
      shift
      ;;
    --manter-cache)
      REMOVER_CACHE=0
      shift
      ;;
    *)
      printf '[limpar-projeto] erro: argumento desconhecido %s\n' "$1" >&2
      printf '[limpar-projeto] uso: %s [--confirmar] [--manter-cache]\n' "$0" >&2
      exit 2
      ;;
  esac
done

ALVOS=(
  "${REPO_ROOT}/generated"
  "${REPO_ROOT}/historico"
  "${REPO_ROOT}/bin/witup"
)

if [[ "${REMOVER_CACHE}" == "1" ]]; then
  ALVOS+=("${REPO_ROOT}/.gocache")
fi

printf '[limpar-projeto] os caminhos abaixo serão removidos:\n'
for alvo in "${ALVOS[@]}"; do
  printf '  - %s\n' "${alvo}"
done

if [[ "${CONFIRMAR}" != "1" ]]; then
  printf '[limpar-projeto] execução interrompida por segurança. Rode novamente com --confirmar.\n'
  exit 2
fi

for alvo in "${ALVOS[@]}"; do
  if [[ -e "${alvo}" ]]; then
    rm -rf "${alvo}"
    printf '[limpar-projeto] removido: %s\n' "${alvo}"
  else
    printf '[limpar-projeto] ausente, nada a remover: %s\n' "${alvo}"
  fi
done

mkdir -p "${REPO_ROOT}/generated" "${REPO_ROOT}/historico"
touch "${REPO_ROOT}/historico/.gitkeep"
printf '[limpar-projeto] projeto reinicializado com sucesso.\n'
printf '[limpar-projeto] artefatos antigos removidos e diretórios de trabalho recriados.\n'

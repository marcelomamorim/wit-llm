#!/usr/bin/env bash

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
RUN_DIR="${RUN_DIR:-}"
RUN_STAMP="${RUN_STAMP:-$(date -u +%Y%m%dT%H%M%SZ)}"
GENERATION_MODEL="${GENERATION_MODEL:-openai_main}"

if [[ -z "$RUN_DIR" ]]; then
  printf 'erro: informe RUN_DIR com a execução acadêmica a avaliar.\n' >&2
  exit 1
fi

printf '[article-main/evaluate] RUN_DIR=%s\n' "$RUN_DIR"
printf '[article-main/evaluate] RUN_STAMP=%s\n' "$RUN_STAMP"
printf '[article-main/evaluate] generation_model=%s\n' "$GENERATION_MODEL"
printf '[article-main/evaluate] backend=batch endpoint=/v1/responses\n'
printf '[article-main/evaluate] coleta Batch concluída em: %s\n' "$RUN_DIR"
printf '[article-main/evaluate] próximo passo: materializar generation.json por custom_id e executar métricas locais.\n'
printf '[article-main/evaluate] este script é intencionalmente não pago e não chama a OpenAI.\n'

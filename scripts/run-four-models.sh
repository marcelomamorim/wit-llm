#!/usr/bin/env bash
# APIs diretas: preparação offline e geração explicitamente separadas.
set -euo pipefail
ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
ACTION="${1:-dry-run}"
CONFIG="${RUNTIME_CONFIG:-$ROOT_DIR/pipelines/quatro-modelos-diretos.json}"
JDK_ROOT="${JDK_ROOT:-$ROOT_DIR/jdk-src}"
WIT_ANALYSIS="${JDK_WIT_ANALYSIS:-$ROOT_DIR/sast2026-artifact/wit-expaths/wit_filtered.json}"
RUN_ROOT="${RUN_ROOT:-$ROOT_DIR/generated/experiments/four-models/pilot-01}"
METHOD_COUNT="${METHOD_COUNT:-20}"
MODEL_KEYS="${MODEL_KEYS:-gpt_reasoning gpt_standard gemini sonnet}"
BIN="$ROOT_DIR/bin/witup"
export WITUP_PROJECT_KEY="${WITUP_PROJECT_KEY:-jdk-four-models}"
export WITUP_PROMPT_ORACLE="${WITUP_PROMPT_ORACLE:-1}"
export WITUP_PROMPT_GOLDEN="${WITUP_PROMPT_GOLDEN:-0}"
case "$ACTION" in prepare|dry-run|generate|materialize) ;; *) echo 'Uso: run-four-models.sh prepare|dry-run|generate|materialize' >&2; exit 2;; esac
mkdir -p "$ROOT_DIR/bin"
(cd "$ROOT_DIR" && go build -o "$BIN" ./cmd/witup)
reference_manifest=""
for model in $MODEL_KEYS; do
  run="$RUN_ROOT/$model"
  case "$ACTION" in
    prepare)
      if [[ -e "$run/requests.jsonl" ]]; then
        echo "[$model] preparação existente; use outro RUN_ROOT para uma nova rodada"
      else
        "$BIN" preparar-estudo-jdk-global --config "$CONFIG" --generation-model "$model" \
          --jdk-root "$JDK_ROOT" --wit-analysis "$WIT_ANALYSIS" --output-dir "$run" \
          --requests "$run/requests.jsonl" --method-count "$METHOD_COUNT" --workers 4
      fi
      manifest="$run/manifest_jdk_global_methods.csv"
      if [[ -n "$reference_manifest" ]]; then cmp "$reference_manifest" "$manifest"; fi
      reference_manifest="$manifest"
      ;;
    dry-run)
      "$BIN" executar-lote-modelo --config "$CONFIG" --model "$model" \
        --requests "$run/requests.jsonl" --output "$run/responses.jsonl" --dry-run
      ;;
    generate)
      "$BIN" executar-lote-modelo --config "$CONFIG" --model "$model" \
        --requests "$run/requests.jsonl" --output "$run/responses.jsonl" --limit "${REQUEST_LIMIT:-0}" \
        --interval "${REQUEST_INTERVAL:-1s}"
      ;;
    materialize)
      "$BIN" executar-lote-modelo --config "$CONFIG" --model "$model" \
        --requests "$run/requests.jsonl" --output "$run/responses.jsonl" --dry-run --require-complete
      "$BIN" avaliar-estudo-jdk-global --config "$CONFIG" --generation-model "$model" \
        --jdk-root "$JDK_ROOT" --run-dir "$run" --responses "$run/responses.jsonl" --workers 4
      ;;
  esac
done

#!/usr/bin/env bash
# Submete as variantes em chunks, parando quando a OpenAI recusar por limite
# de tokens enfileirados. Registra tudo para retomar depois.
set -uo pipefail
cd "$(dirname "$0")"; R="$PWD"
CHUNK="${CHUNK:-2500}"
set -a; . ~/.witup-openai.env; set +a
LOG="submissoes-$(date +%Y%m%d-%H%M%S).log"
REG="$R/generated/experiments/jdk-variantes/submissoes.tsv"
touch "$REG"
exec > >(tee -a "$LOG") 2>&1
echo "### início $(date)"

for k in "$@"; do
  d="$R/generated/experiments/jdk-variantes/$k"
  [ -s "$d/requests.jsonl" ] || { echo "[$k] sem requests.jsonl, pulando"; continue; }
  cfg=v1-pilot.runtime.json
  case "$k" in jdk-v2) cfg=v2.runtime.json;; jdk-v3*) cfg=v3.runtime.json;; esac
  mkdir -p "$d/chunks"
  [ -z "$(ls "$d/chunks" 2>/dev/null)" ] && split -l "$CHUNK" -d -a 3 "$d/requests.jsonl" "$d/chunks/c"
  for c in "$d"/chunks/c*; do
    nome="$k/$(basename "$c")"
    grep -q "^${nome}	" "$REG" && { echo "[$nome] já submetido"; continue; }
    echo "[$nome] submetendo ($(wc -l < "$c") requests)..."
    meta="$c.meta.json"
    if "$R/bin/witup" submeter-openai-batch --config "$R/generated/configs/$cfg" \
         --model openai_main --requests "$c" --output "$meta" >"$c.log" 2>&1; then
      bid=$(python3 -c "import json;print(json.load(open('$meta'))['batch_id'])" 2>/dev/null)
      printf '%s\t%s\t%s\n' "$nome" "$bid" "$(date -u +%FT%TZ)" >> "$REG"
      echo "  OK -> $bid"
    else
      echo "  RECUSADO:"; tail -2 "$c.log" | sed 's/^/    /'
      echo "### parando — limite atingido. Retome depois rodando este script de novo."
      exit 0
    fi
  done
done
echo "### fim $(date) — todas as variantes submetidas"

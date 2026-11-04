#!/usr/bin/env bash
set -uo pipefail
cd "$(dirname "$0")"; R="$PWD"
gerar() { # chave golden oracle config
  local k=$1 g=$2 o=$3 cfg=$4
  local out="$R/generated/experiments/jdk-variantes/$k"
  [ -s "$out/requests.jsonl" ] && { echo "[$k] já existe, pulando"; return; }
  rm -rf "$out"; mkdir -p "$out"
  WITUP_PROJECT_KEY="$k" WITUP_PROMPT_GOLDEN="$g" WITUP_PROMPT_ORACLE="$o" \
  "$R/bin/witup" preparar-estudo-jdk-global \
    --config "$R/generated/configs/$cfg" --generation-model openai_main \
    --jdk-root "$R/jdk-src" --wit-analysis "$R/sast2026-artifact/wit-expaths/wit_filtered.json" \
    --output-dir "$out" --requests "$out/requests.jsonl" \
    --method-count 9999 --workers 4 >"$out/prepare.log" 2>&1
  echo "[$k] $(wc -l < "$out/requests.jsonl" 2>/dev/null || echo FALHOU) linhas"
}
gerar jdk-v1  0 1 v1-pilot.runtime.json   # oráculo
gerar jdk-v2  0 0 v2.runtime.json         # modelo mini
gerar jdk-v4  1 0 v1-pilot.runtime.json   # few-shot (MODELO OBRIGATÓRIO)
gerar jdk-v3a 0 0 v3.runtime.json         # T=0.7 amostra A
gerar jdk-v3b 0 0 v3.runtime.json         # T=0.7 amostra B
gerar jdk-v3c 0 0 v3.runtime.json         # T=0.7 amostra C
echo "=== concluído ==="

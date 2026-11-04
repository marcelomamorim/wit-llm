#!/usr/bin/env bash
# Reenfileira chunks conforme a fila da OpenAI esvazia.
# Limite: ~20M tokens enfileirados POR MODELO (~2 chunks de 2500).
# Prioridade: V1 (experimento principal) > V2 > V4 > V3a/b/c
set -uo pipefail
cd "$(dirname "$0")"; R="$PWD"
set -a; . ~/.witup-openai.env; set +a
D="$R/generated/experiments/jdk-variantes"
REG="$D/submissoes.tsv"; OK="$D/concluidos.tsv"; touch "$OK"
LOG="$R/daemon-$(date +%Y%m%d-%H%M%S).log"
exec > >(tee -a "$LOG") 2>&1
MAX="${MAX_POR_MODELO:-2}"
ORDEM="jdk-v1 jdk-v2 jdk-v4 jdk-v3a jdk-v3b jdk-v3c"
modelo_de() { case "$1" in jdk-v2) echo mini;; *) echo nano;; esac; }
cfg_de()    { case "$1" in jdk-v2) echo v2.runtime.json;; jdk-v3*) echo v3.runtime.json;; *) echo v1-pilot.runtime.json;; esac; }

echo "### daemon $(date) — max $MAX por modelo"
while :; do
  # --- UMA varredura de estados por ciclo (evita rate limit) ---
  EST="$(mktemp)"; voo_nano=0; voo_mini=0
  while IFS=$'\t' read -r nome bid _; do
    grep -qx "$nome" "$OK" && { printf '%s\tcompleted\n' "$nome" >> "$EST"; continue; }
    s=$(curl -s --max-time 20 "https://api.openai.com/v1/batches/$bid" \
        -H "Authorization: Bearer $OPENAI_API_KEY" \
        | python3 -c "import sys,json;print(json.load(sys.stdin).get('status','?'))" 2>/dev/null)
    printf '%s\t%s\n' "$nome" "$s" >> "$EST"
    case "$s" in
      completed) echo "$nome" >> "$OK"; echo "[$(date +%H:%M)] ✅ $nome";;
      validating|in_progress|finalizing)
        if [ "$(modelo_de "${nome%%/*}")" = nano ]; then voo_nano=$((voo_nano+1)); else voo_mini=$((voo_mini+1)); fi;;
    esac
  done < "$REG"

  pend=0
  for k in $ORDEM; do
    [ -d "$D/$k/chunks" ] || continue
    for c in "$D/$k"/chunks/c*; do
      case "$c" in *.meta.json|*.log) continue;; esac
      nome="$k/$(basename "$c")"
      grep -qx "$nome" "$OK" && continue
      est=$(awk -F'\t' -v n="$nome" '$1==n{print $2}' "$EST" | tail -1)
      case "${est:-}" in validating|in_progress|finalizing) pend=$((pend+1)); continue;; esac
      pend=$((pend+1))
      m=$(modelo_de "$k")
      if [ "$m" = nano ]; then [ "$voo_nano" -ge "$MAX" ] && continue; else [ "$voo_mini" -ge "$MAX" ] && continue; fi
      echo "[$(date +%H:%M)] → $nome ($m)"
      if "$R/bin/witup" submeter-openai-batch --config "$R/generated/configs/$(cfg_de "$k")" \
           --model openai_main --requests "$c" --output "$c.meta.json" >"$c.log" 2>&1; then
        nb=$(python3 -c "import json;print(json.load(open('$c.meta.json'))['batch_id'])" 2>/dev/null)
        printf '%s\t%s\t%s\n' "$nome" "$nb" "$(date -u +%FT%TZ)" >> "$REG"
        echo "    $nb"
        if [ "$m" = nano ]; then voo_nano=$((voo_nano+1)); else voo_mini=$((voo_mini+1)); fi
      else
        echo "    recusado (tentará no próximo ciclo)"
      fi
    done
  done
  rm -f "$EST"
  [ "$pend" -eq 0 ] && { echo "### TUDO CONCLUÍDO $(date)"; break; }
  echo "[$(date +%H:%M)] pendentes=$pend  em voo: nano=$voo_nano mini=$voo_mini — aguardando 5 min"
  sleep 300
done

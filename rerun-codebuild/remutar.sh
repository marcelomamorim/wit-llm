#!/usr/bin/env bash
# Refaz todas as mutações com -encoding UTF-8 no javac dos testes.
# Sem isso, teste com acento é contado como "não compilou" — e o braço WIT
# tem muito mais acento, o que enviesava a comparação sistematicamente.
set -uo pipefail
cd "$(dirname "$0")"; R="$PWD"
IMG=cloudarchlab/witup-evaluator:amd64
LOG="$R/remutar-$(date +%Y%m%d-%H%M%S).log"
exec > >(tee -a "$LOG") 2>&1
for d in mutacao mutacao-v1 mutacao-v2 mutacao-v3a mutacao-v4b; do
  [ -f "$R/$d/rodar.sh" ] || continue
  echo "[$(date '+%H:%M')] $d"
  docker run --rm --platform linux/amd64 -m 4g -v "$R/$d:/m" "$IMG" \
    bash /m/rodar.sh > "$R/$d/rodar.log" 2>&1
  awk -F, -v L="$d" 'NR>1{c[$2"|"$7]++}
    END{printf "   não compila: direct=%d wit=%d\n", c["direct|-"], c["wit|-"]}' "$R/$d/resultado.csv"
done
echo "[$(date '+%H:%M')] ### fim"

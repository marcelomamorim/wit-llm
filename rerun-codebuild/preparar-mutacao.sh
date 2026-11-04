#!/usr/bin/env bash
# Monta um diretório de mutação para uma variante: copia a infraestrutura comum
# (Mutador, alvos, classes originais) e seleciona, de cada braço, o teste cujo
# hash corresponde a cada site de exceção.
set -uo pipefail
cd "$(dirname "$0")"; R="$PWD"
K="$1"                                   # jdk-v2 | jdk-v3a | ...
D="$R/mutacao-${K#jdk-}"
SRC="$R/mutacao"                         # infraestrutura de referência
V="$R/../generated/experiments/jdk-variantes/$K/materialized/variants"

[ -d "$V" ] || { echo "variante sem testes materializados: $V"; exit 1; }
mkdir -p "$D/testes/direct" "$D/testes/wit" "$D/mut" "$D/work"
for f in Mutador.java alvos.csv rodar.sh; do cp "$SRC/$f" "$D/"; done
cp -R "$SRC/classes" "$D/" 2>/dev/null
cp -R "$SRC/orig"    "$D/" 2>/dev/null

n=0
while IFS=, read -r cls cdot meth desc off hash pair; do
  [ "$cls" = "cls" ] && continue
  for arm in direct wit; do
    cen=$([ "$arm" = direct ] && echo direct-tests || echo wit-context)
    t=$(find "$V/$cen" -name "*_P$hash.java" 2>/dev/null | head -1)
    [ -n "$t" ] && cp "$t" "$D/testes/$arm/" 2>/dev/null
  done
  n=$((n+1))
done < "$D/alvos.csv"

echo "  $K → $D"
echo "    sites: $n   testes direct: $(ls "$D/testes/direct"/*.java 2>/dev/null|wc -l|tr -d ' ')   wit: $(ls "$D/testes/wit"/*.java 2>/dev/null|wc -l|tr -d ' ')"

#!/usr/bin/env bash
: "${WITUP_S3_BUCKET:?Defina WITUP_S3_BUCKET para o bucket do experimento}"
# Encadeia o trabalho restante para rodar sem supervisão.
# Reentrante: pula o que já tem resultado. Nada aqui pede aprovação —
# tudo roda em container local ou lê do S3 com credenciais já configuradas.
set -uo pipefail
cd "$(dirname "$0")"; R="$PWD"
LOG="$R/noite-$(date +%Y%m%d-%H%M%S).log"
exec > >(tee -a "$LOG") 2>&1
IMG=cloudarchlab/witup-evaluator:amd64
diz(){ echo "[$(date '+%d/%m %H:%M')] $*"; }

diz "### início"

# 1. espera a mutação já em curso (V3a e V2) terminar
if pgrep -f 'mutacao-v3a|mutacao-v2' >/dev/null || pgrep -f rodar.sh >/dev/null; then
  diz "aguardando mutação V3a/V2 em curso"
  while pgrep -f rodar.sh >/dev/null; do sleep 120; done
  diz "mutação V3a/V2 concluída"
fi

# 2. refaz o V4 com o pareamento correto (o anterior casou 179 vs 34)
if [ ! -s mutacao-v4b/resultado.csv ]; then
  diz "preparando V4 com pareamento pelo pairing-manifest"
  python3 preparar-mutacao.py jdk-v4 mutacao-v4b || diz "falhou preparar V4"
  diz "rodando mutação V4"
  docker run --rm --platform linux/amd64 -m 4g -v "$R/mutacao-v4b:/m" "$IMG" \
    bash /m/rodar.sh > mutacao-v4b/rodar.log 2>&1
  diz "V4 concluído"
fi

# 3. quadro consolidado de mutação
diz "── taxa de mutantes mortos ──"
printf "%-10s %12s %12s %10s\n" "" "direct" "wit" "wit-dir"
for d in mutacao mutacao-v1 mutacao-v2 mutacao-v3a mutacao-v4b mutacao-v4; do
  [ -s "$d/resultado.csv" ] || continue
  lbl=$(echo "$d" | sed 's/mutacao-/V/; s/^mutacao$/baseline/')
  awk -F, -v L="$lbl" 'NR>1 && ($7=="MATOU"||$7=="sobreviveu"){t[$2]++; if($7=="MATOU") k[$2]++}
    END{ if(t["direct"]>0&&t["wit"]>0){dd=100*k["direct"]/t["direct"]; ww=100*k["wit"]/t["wit"];
      printf "%-10s %6d/%-5d %6d/%-5d %+9.1fpp\n", L, k["direct"],t["direct"], k["wit"],t["wit"], ww-dd}}' "$d/resultado.csv"
done

# 4. reteste: espera os 8 shards, baixa e compara teste a teste
diz "aguardando os 8 shards do reteste"
ini=$(date +%s)
until [ "$(aws s3 ls s3://${WITUP_S3_BUCKET}/reteste-v4/paired-results/ --recursive 2>/dev/null | grep -c 'jtr.tgz')" -ge 8 ] \
   || [ $(( $(date +%s)-ini )) -gt 14400 ]; do sleep 180; done
n=$(aws s3 ls s3://${WITUP_S3_BUCKET}/reteste-v4/paired-results/ --recursive 2>/dev/null | grep -c 'jtr.tgz')
diz "shards prontos: $n/8"
if [ "$n" -ge 1 ]; then
  mkdir -p reteste/exec1 reteste/exec2
  for v in direct-tests wit-context; do for s in 00 01 02 03; do
    [ -s "reteste/exec1/$v-$s.tgz" ] || aws s3 cp "s3://${WITUP_S3_BUCKET}/jdk-v4-20261004/paired-results/$v/$s/jtr.tgz" "reteste/exec1/$v-$s.tgz" --quiet 2>/dev/null
    [ -s "reteste/exec2/$v-$s.tgz" ] || aws s3 cp "s3://${WITUP_S3_BUCKET}/reteste-v4/paired-results/$v/$s/jtr.tgz"      "reteste/exec2/$v-$s.tgz" --quiet 2>/dev/null
  done; done
  diz "── variância de execução, teste a teste ──"
  python3 comparar-reteste.py reteste/exec1 reteste/exec2
fi

diz "### fim"

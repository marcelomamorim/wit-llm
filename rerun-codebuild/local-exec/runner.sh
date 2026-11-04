#!/usr/bin/env bash
# Executa shards localmente (jtreg + JCov) e sobe o resultado para o S3,
# no mesmo layout dos runs do CodeBuild. Custo de compute: zero.
set -uo pipefail
cd "$(dirname "$0")/.."; R="$PWD"
BUCKET="${WITUP_S3_BUCKET:?Defina WITUP_S3_BUCKET para o bucket do experimento}"
PAR="${PARALELO:-2}"
LOG="$R/local-exec/runner-$(date +%Y%m%d-%H%M%S).log"
exec > >(tee -a "$LOG") 2>&1
REG="$R/local-exec/feitos.tsv"; touch "$REG"

rodar(){ # variante stamp cenario shard
  local K=$1 S=$2 V=$3 N=$4 tag="$K/$V/$N"
  grep -qx "$tag" "$REG" && return 0
  local W="$R/local-exec/w/$K-$V-$N"; rm -rf "$W"; mkdir -p "$W/in" "$W/out"
  aws s3 cp "s3://$BUCKET/$S/inputs/paired-shard-$N.zip" "$W/in/" --quiet || return 1
  cp "$R/local-exec/rodar-shard-jcov.sh" "$W/in/"
  docker run --rm --platform linux/amd64 -m 3g \
    -v "$W/in:/in:ro" -v "$W/out:/out" -v "$R/local-exec/wjdk:/wjdk:ro" \
    -e JCOV_VARIANT="$V" -e SHARD_ID="$N" -e JTREG_CONCURRENCY=3 \
    cloudarchlab/witup-evaluator:amd64 bash /in/rodar-shard-jcov.sh > "$W/run.log" 2>&1
  local D="s3://$BUCKET/$S/paired-results/$V/$N"
  if [ -s "$W/out/jcov-result.xml" ]; then
    aws s3 cp "$W/out/jcov-result.xml" "$D/jcov/jcov-result.xml" --quiet
    [ -s "$W/out/jtr-$V-$N.tgz" ] && aws s3 cp "$W/out/jtr-$V-$N.tgz" "$D/jtr.tgz" --quiet
    aws s3 cp "$W/run.log" "$D/run.log" --quiet
    echo "$tag" >> "$REG"; echo "[$(date +%H:%M)] ✅ $tag"
  else
    echo "[$(date +%H:%M)] ❌ $tag — sem XML"; tail -2 "$W/run.log" | sed 's/^/      /'
  fi
  rm -rf "$W"
}

echo "### início $(date) — paralelo=$PAR"
for K in "$@"; do
  S=$(cat "$R/../generated/experiments/jdk-variantes/$K/.run_stamp" 2>/dev/null) || continue
  echo "=== $K (stamp=$S) ==="
  for V in direct-tests wit-context; do
    for N in $(seq -w 0 23); do
      while [ "$(jobs -rp | wc -l)" -ge "$PAR" ]; do sleep 10; done
      rodar "$K" "$S" "$V" "$N" &
    done
  done
  wait
  echo "=== $K concluído: $(grep -c "^$K/" "$REG") /48 ==="
done
echo "### fim $(date)"

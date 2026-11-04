#!/usr/bin/env bash
: "${WITUP_S3_BUCKET:?Defina WITUP_S3_BUCKET para o bucket do experimento}"
# Baixa os 24 XMLs por cenário, mescla com o JCov Merger e roda RepGen.
set -uo pipefail
cd "$(dirname "$0")"; R="$PWD"
IMG=cloudarchlab/witup-evaluator:amd64
for K in "$@"; do
  S=$(cat "../generated/experiments/jdk-variantes/$K/.run_stamp")
  W="$R/merge-$K"; mkdir -p "$W/out"
  for v in direct-tests wit-context; do
    mkdir -p "$W/$v"
    for n in $(seq -w 0 23); do
      [ -s "$W/$v/$n.xml" ] && continue
      aws s3 cp "s3://${WITUP_S3_BUCKET}/$S/paired-results/$v/$n/jcov/jcov-result.xml" "$W/$v/$n.xml" --quiet
    done
    echo "[$K/$v] baixados: $(ls $W/$v/*.xml 2>/dev/null | wc -l | tr -d ' ')/24"
  done
  # baseline
  [ -s "$W/baseline.xml" ] || aws s3 cp "s3://${WITUP_S3_BUCKET}/$S/inputs/jcov-baseline.xml" "$W/baseline.xml" --quiet
  docker run --rm --platform linux/amd64 -v "$W:/w" "$IMG" bash -c '
    set -e; J=/opt/test-jdk/bin/java; JAR=/opt/jcov/JCOV_BUILD/jcov_3.0/jcov.jar
    for v in direct-tests wit-context; do
      echo "== merge $v =="
      $J -jar $JAR Merger -o /w/out/$v.xml /w/$v/*.xml 2>&1 | tail -2
      echo "== merge baseline+$v =="
      $J -jar $JAR Merger -o /w/out/baseline+$v.xml /w/baseline.xml /w/out/$v.xml 2>&1 | tail -2
      $J -jar $JAR RepGen -format text -o /w/out/rep-$v /w/out/baseline+$v.xml 2>&1 | tail -1
    done
    echo "== baseline sozinho =="
    $J -jar $JAR RepGen -format text -o /w/out/rep-baseline /w/baseline.xml 2>&1 | tail -1
  '
  echo "[$K] merge concluído"
done

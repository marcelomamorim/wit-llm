#!/usr/bin/env bash
# Fecha os buracos deixados pelo driver: espera ele terminar e então relança
# qualquer shard que não tenha jcov-result.xml no S3, com retry de verdade.
set -uo pipefail
cd "$(dirname "$0")"; R="$PWD"
B=witup-jcov-results
faltando(){
  for K in jdk-v4 jdk-v3a; do
    S=$(cat "$R/generated/experiments/jdk-variantes/$K/.run_stamp")
    presentes=$(aws s3 ls "s3://$B/$S/paired-results/" --recursive 2>/dev/null \
                | grep -o 'paired-results/[^/]*/[0-9][0-9]/jcov/jcov-result.xml' \
                | sed 's|paired-results/||; s|/jcov/jcov-result.xml||')
    for v in direct-tests wit-context; do
      for n in $(seq -w 0 23); do
        echo "$presentes" | grep -qx "$v/$n" || echo "$K $v $n"
      done
    done
  done
}
echo "### varredura iniciada $(date +%H:%M)"
while pgrep -f driver-variantes.sh >/dev/null; do sleep 120; done
echo "### driver terminou $(date +%H:%M) — varrendo"
for ciclo in 1 2 3 4 5 6 7 8; do
  f=$(faltando); n=$(echo "$f" | grep -c . )
  echo "[$(date +%H:%M)] ciclo $ciclo — faltando $n"
  [ "$n" -eq 0 ] && { echo "### completo"; break; }
  echo "$f" | while read -r K v s; do
    [ -z "${s:-}" ] && continue
    bash "$R/lancar-shard.sh" "$K" "$v" "$s"
  done
  sleep 600
done
echo "### varredura fim $(date +%H:%M)"

#!/usr/bin/env bash
# Refaz o merge do JCov do experimento do artigo (paired-20260706-full02) com
# as duas correções: pacote com.sun.proxy removido e Merger em modo ESTRITO.
#
# O -boe skip descarta arquivo problemático e termina com sucesso, o que já
# custou 10 shards em silêncio nos merges das variantes. Aqui ele sai: se
# houver conflito, o merge falha e a gente fica sabendo.
set -uo pipefail
cd "$(dirname "$0")"; R="$PWD"
D="$R/merge-original"
IMG=cloudarchlab/witup-evaluator:amd64
LOG="$R/remerge-original-$(date +%Y%m%d-%H%M%S).log"
exec > >(tee -a "$LOG") 2>&1
diz(){ echo "[$(date '+%H:%M')] $*"; }

for v in direct-tests wit-context; do
  n=$(find "$D/$v" -name '*.xml' 2>/dev/null | wc -l | tr -d ' ')
  [ "$n" -eq 24 ] || { diz "ABORTA: $v tem $n/24 XML"; exit 1; }
done
[ -s "$D/baseline.xml" ] || { diz "ABORTA: sem baseline.xml"; exit 1; }

diz "removendo com.sun.proxy dos 48 XML"
python3 tirar-proxy.py 'merge-original/direct-tests/*.xml' 'merge-original/wit-context/*.xml'

diz "merge ESTRITO (sem -boe skip)"
mkdir -p "$D/out"
docker run --rm --platform linux/amd64 -m 6g -v "$D:/w" "$IMG" bash -c '
  set -e; J=/opt/test-jdk/bin/java; JAR=/opt/jcov/JCOV_BUILD/jcov_3.0/jcov.jar
  for v in direct-tests wit-context; do
    echo "== merge $v =="
    $J -jar $JAR Merger -o /w/out/$v.xml /w/$v/*.xml 2>&1 | grep -viE "^(INFO|FINE)" | head -8
    echo "== merge baseline+$v =="
    $J -jar $JAR Merger -o /w/out/baseline+$v.xml /w/baseline.xml /w/out/$v.xml 2>&1 | grep -viE "^(INFO|FINE)" | head -8
    $J -jar $JAR RepGen -format text -o /w/out/rep-$v /w/out/baseline+$v.xml 2>&1 | tail -1
  done
  echo "== baseline sozinho =="
  $J -jar $JAR RepGen -format text -o /w/out/rep-baseline /w/baseline.xml 2>&1 | tail -1
'
diz "descartes (tem de ser 0): $(grep -c 'were skipped' "$LOG" || echo 0)"
diz "── cobertura restrita aos métodos-alvo ──"
python3 cobertura-restrita.py manifest.csv \
  "$D/out/rep-baseline" "$D/out/rep-direct-tests" "$D/out/rep-wit-context"
diz "### fim"

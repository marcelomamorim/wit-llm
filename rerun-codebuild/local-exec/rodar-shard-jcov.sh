#!/usr/bin/env bash
# Shard local COM JCov — replica o wrapper seletivo do run-jcov-docker.sh
set -uo pipefail
V="${JCOV_VARIANT:-wit-context}"; SH_ID="${SHARD_ID:-02}"
JCOV_JAR=/opt/jcov/JCOV_BUILD/jcov_3.0/jcov.jar
RUN=/data/generated/experiments/localjcov/$V-$SH_ID; mkdir -p "$RUN"; cd "$RUN"
unzip -q -o /in/paired-shard-$SH_ID.zip -d "$RUN"
[ "$V" = direct-tests ] && rm -rf "$RUN/variants/wit-context" || rm -rf "$RUN/variants/direct-tests"
VT="$RUN/variants/$V/test"
cp /opt/openjdk-src/test/jdk/TEST.ROOT "$VT/jdk/TEST.ROOT"
cp /opt/openjdk-src/test/jdk/TEST.groups "$VT/jdk/TEST.groups"
ln -sf /opt/openjdk-src/test/lib "$VT/lib"; ln -sf /opt/openjdk-src/test/jtreg-ext "$VT/jtreg-ext"

# wrapper montado do host (sem copiar o JDK a cada shard)
W=/wjdk
OUT="$RUN/jcov-result.xml"
export JCOV_OUT="$OUT"
echo "[$(date +%T)] jtreg+JCov iniciando ($(find "$VT" -name '*.java' | wc -l) testes)"
/opt/jtreg/bin/jtreg -jdk:"$W" -w:"$RUN/w" -r:"$RUN/r" -va \
  -conc:"${JTREG_CONCURRENCY:-2}" -timeout:4 "$VT/jdk/witup/generated" > "$RUN/jtreg.log" 2>&1
echo "[$(date +%T)] jtreg fim"
ls -lh "$OUT" 2>/dev/null | awk '{print "  jcov-result.xml: "$5}' || echo "  SEM XML"
find "$RUN/w" -name '*.jtr' | wc -l | xargs echo "  .jtr:"
cp "$OUT" /out/ 2>/dev/null
cd "$RUN" && find w -name '*.jtr' -print0 | tar czf /out/jtr-$V-$SH_ID.tgz --null -T - 2>/dev/null
ls -lh /out/jtr-$V-$SH_ID.tgz 2>/dev/null | awk '{print "  tarball jtr: "$5}'
echo "[$(date +%T)] CONCLUIDO"

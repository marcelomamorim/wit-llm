#!/usr/bin/env bash
# Executa um shard localmente: jtreg + JCov, sem AWS.
set -uo pipefail
V="${JCOV_VARIANT:-wit-context}"; SH_ID="${SHARD_ID:-02}"
RUN=/data/generated/experiments/local/$V-$SH_ID
mkdir -p "$RUN"; cd "$RUN"
unzip -q -o /in/paired-shard-$SH_ID.zip -d "$RUN"
[ "$V" = direct-tests ] && rm -rf "$RUN/variants/wit-context" || rm -rf "$RUN/variants/direct-tests"
VT="$RUN/variants/$V/test"
cp /opt/openjdk-src/test/jdk/TEST.ROOT "$VT/jdk/TEST.ROOT"
cp /opt/openjdk-src/test/jdk/TEST.groups "$VT/jdk/TEST.groups"
ln -sf /opt/openjdk-src/test/lib "$VT/lib"; ln -sf /opt/openjdk-src/test/jtreg-ext "$VT/jtreg-ext"
echo "[$(date +%H:%M:%S)] jtreg iniciando ($(find "$VT" -name '*.java' | wc -l) testes)"
/opt/jtreg/bin/jtreg -jdk:/opt/test-jdk -w:"$RUN/w" -r:"$RUN/r" -va \
  -conc:"${JTREG_CONCURRENCY:-4}" -timeout:2 "$VT/jdk/witup/generated" > "$RUN/jtreg.log" 2>&1
echo "[$(date +%H:%M:%S)] jtreg fim — $(grep -c '' <<<"$(find "$RUN/w" -name '*.jtr')") jtr"
tail -2 "$RUN/jtreg.log"
echo "[$(date +%H:%M:%S)] empacotando .jtr"
cd "$RUN" && find w -name '*.jtr' -print0 | tar czf /out/jtr-$V-$SH_ID.tgz --null -T -
ls -lh /out/jtr-$V-$SH_ID.tgz | awk '{print "  tarball: "$5}'
echo "[$(date +%H:%M:%S)] CONCLUIDO"

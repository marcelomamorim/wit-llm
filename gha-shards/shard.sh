#!/usr/bin/env bash
# Executa um shard pareado (jtreg + JCov) dentro da imagem do avaliador.
# Equivale a local-exec/rodar-shard-jcov.sh, mas monta o shadow-JDK aqui dentro
# em vez de recebê-lo montado do host — no CI não existe host para preparar.
set -uo pipefail
V="${JCOV_VARIANT:?}"; SH_ID="${SHARD_ID:?}"
JCOV_JAR=/opt/jcov/JCOV_BUILD/jcov_3.0/jcov.jar
RUN="/work/$V-$SH_ID"; mkdir -p "$RUN"; cd "$RUN"

unzip -q -o "/in/paired-shard-$SH_ID.zip" -d "$RUN"
if [ "$V" = direct-tests ]; then rm -rf "$RUN/variants/wit-context"; else rm -rf "$RUN/variants/direct-tests"; fi
VT="$RUN/variants/$V/test"
cp /opt/openjdk-src/test/jdk/TEST.ROOT   "$VT/jdk/TEST.ROOT"
cp /opt/openjdk-src/test/jdk/TEST.groups "$VT/jdk/TEST.groups"
ln -sf /opt/openjdk-src/test/lib       "$VT/lib"
ln -sf /opt/openjdk-src/test/jtreg-ext  "$VT/jtreg-ext"

# Shadow-JDK: bin/java é um wrapper que injeta o agente do JCov SÓ quando o
# jtreg lança o teste real (MainWrapper). Injetar sempre quebra a sonda inicial
# do jtreg com exit 5, e -javaoptions: tem o mesmo efeito.
W=/wjdk
cp -a /opt/test-jdk "$W"
OUT="$RUN/jcov-result.xml"
cat > "$W/bin/java" <<'WRAP'
#!/bin/bash
REAL=/opt/test-jdk/bin/java
JAR=/opt/jcov/JCOV_BUILD/jcov_3.0/jcov.jar
for a in "$@"; do
  if [ "$a" = "com.sun.javatest.regtest.agent.MainWrapper" ]; then
    exec "$REAL" "-javaagent:$JAR=file=${JCOV_OUT:-/out/jcov-result.xml},type=branch,merge=merge,include=java/,include=javax/,include=sun/,include=com/sun/,include=jdk/,native=off" "$@"
  fi
done
exec "$REAL" "$@"
WRAP
chmod +x "$W/bin/java"
export JCOV_OUT="$OUT"

echo "[$(date +%T)] jtreg+JCov iniciando ($(find "$VT" -name '*.java' | wc -l) testes)"
/opt/jtreg/bin/jtreg -jdk:"$W" -w:"$RUN/w" -r:"$RUN/r" -va \
  -conc:"${JTREG_CONCURRENCY:-4}" -timeout:4 "$VT/jdk/witup/generated" > "$RUN/jtreg.log" 2>&1
echo "[$(date +%T)] jtreg fim"

ls -lh "$OUT" 2>/dev/null | awk '{print "  jcov-result.xml: "$5}' || echo "  SEM XML"
find "$RUN/w" -name '*.jtr' | wc -l | xargs echo "  .jtr:"
cp "$OUT" /out/ 2>/dev/null
cd "$RUN" && find w -name '*.jtr' -print0 | tar czf "/out/jtr-$V-$SH_ID.tgz" --null -T - 2>/dev/null
tail -40 "$RUN/jtreg.log" > /out/jtreg-tail.log 2>/dev/null
echo "[$(date +%T)] CONCLUIDO"

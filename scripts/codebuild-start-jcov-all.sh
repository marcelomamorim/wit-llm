#!/usr/bin/env bash
# codebuild-start-jcov-all.sh
#
# Dispara todos os builds JCov no CodeBuild (via AWS CloudShell ou CLI).
# Builds 1–6 (baseline chunks) rodam em paralelo.
# Builds 7–8 (generated) rodam em paralelo após upload do ZIP.
# Build 9 (merge) deve ser disparado manualmente após 1–6 completarem.
#
# Uso:
#   RUN_STAMP=pilot-20260607T041241Z bash codebuild-start-jcov-all.sh
#
# Para o merge (rodar após 1-6 completarem):
#   RUN_STAMP=pilot-20260607T041241Z MERGE_ONLY=sim bash codebuild-start-jcov-all.sh

set -euo pipefail

PROJECT="wit-llm-jcov-baseline"
RUN_STAMP="${RUN_STAMP:-pilot-20260607T041241Z}"
CONCURRENCY="${JTREG_CONCURRENCY:-1}"
MERGE_ONLY="${MERGE_ONLY:-nao}"

log() { printf '[start-jcov] %s\n' "$*"; }

start_build() {
  local desc="$1"; shift
  local vars=("$@")
  local override_vars=()
  for v in "${vars[@]}"; do
    IFS='=' read -r name value <<< "${v}"
    override_vars+=("name=${name},value=${value},type=PLAINTEXT")
  done
  local ids
  ids=$(aws codebuild start-build \
    --project-name "${PROJECT}" \
    --timeout-in-minutes-override 45 \
    --environment-variables-override "${override_vars[@]}" \
    --query "build.id" --output text)
  log "✓ ${desc}: ${ids}"
}

if [[ "${MERGE_ONLY}" =~ ^(sim|yes|1)$ ]]; then
  log "=== Build 9: Merge do baseline ==="
  start_build "merge" \
    "JCOV_MODE=merge" \
    "RUN_STAMP=${RUN_STAMP}" \
    "EXPERIMENT_DIR=jdk-pilot"
  log "Merge disparado. Aguarde completar e então baixe os resultados do S3."
  exit 0
fi

log "=== Disparando builds baseline (chunks 1–6) em paralelo ==="

# chunk-1: tier1 part1 — java/lang (sem management/instrument) + sun/invoke + sun/misc + sun/reflect
start_build "baseline chunk-1 (java/lang tier1)" \
  "JCOV_MODE=baseline-chunk" \
  "CHUNK_ID=chunk-1" \
  "JCOV_TEST_PATHS=java/lang,sun/invoke,sun/misc,sun/reflect" \
  "RUN_STAMP=${RUN_STAMP}" \
  "EXPERIMENT_DIR=jdk-pilot" \
  "JTREG_CONCURRENCY=${CONCURRENCY}"

# chunk-2: tier1 part2 — java/util subsets (sem collections/concurrent/stream — já inclusos via java/util/concurrent etc)
start_build "baseline chunk-2 (java/util tier1)" \
  "JCOV_MODE=baseline-chunk" \
  "CHUNK_ID=chunk-2" \
  "JCOV_TEST_PATHS=java/util/AbstractCollection,java/util/AbstractList,java/util/AbstractMap,java/util/ArrayList,java/util/Arrays,java/util/BitSet,java/util/Collections,java/util/Comparator,java/util/HashMap,java/util/HashSet,java/util/LinkedHashMap,java/util/LinkedList,java/util/Optional,java/util/PriorityQueue,java/util/TreeMap,java/util/Vector,java/util/concurrent,java/util/function,java/util/stream,sun/util" \
  "RUN_STAMP=${RUN_STAMP}" \
  "EXPERIMENT_DIR=jdk-pilot" \
  "JTREG_CONCURRENCY=${CONCURRENCY}"

# chunk-3: tier1 part3 — java/math + java/nio/Buffer + com/sun/jdi/* + tools/pack200 + com/sun/crypto/provider/Cipher + sun/nio/cs/ISO8859x.java
start_build "baseline chunk-3 (math+nio+jdi tier1)" \
  "JCOV_MODE=baseline-chunk" \
  "CHUNK_ID=chunk-3" \
  "JCOV_TEST_PATHS=java/math,java/nio/Buffer,com/sun/jdi,tools/pack200,com/sun/crypto/provider/Cipher,sun/nio/cs/ISO8859x.java" \
  "RUN_STAMP=${RUN_STAMP}" \
  "EXPERIMENT_DIR=jdk-pilot" \
  "JTREG_CONCURRENCY=${CONCURRENCY}"

# chunk-4: tier2 part1 — security (sem cipher que já está no chunk-3)
start_build "baseline chunk-4 (security tier2)" \
  "JCOV_MODE=baseline-chunk" \
  "CHUNK_ID=chunk-4" \
  "JCOV_TEST_PATHS=java/security,javax/crypto,javax/xml/crypto,com/oracle/security/ucrypto,com/sun/crypto,javax/security,com/sun/jarsigner,com/sun/security,com/sun/org/apache/xml/internal/security,sun/security,javax/net,com/sun/net/ssl" \
  "RUN_STAMP=${RUN_STAMP}" \
  "EXPERIMENT_DIR=jdk-pilot" \
  "JTREG_CONCURRENCY=${CONCURRENCY}"

# chunk-5: tier2 part2 — tools + io + nio + sun/nio (sem Buffer e ISO8859x já no chunk-3)
start_build "baseline chunk-5 (tools+io+nio tier2)" \
  "JCOV_MODE=baseline-chunk" \
  "CHUNK_ID=chunk-5" \
  "JCOV_TEST_PATHS=tools,java/io,java/nio,sun/nio,sun/tools/java,sun/tools/jrunscript" \
  "RUN_STAMP=${RUN_STAMP}" \
  "EXPERIMENT_DIR=jdk-pilot" \
  "JTREG_CONCURRENCY=${CONCURRENCY}"

# chunk-6: tier2 part3 — net + sql + javax/* + text + time
start_build "baseline chunk-6 (net+sql+text+time tier2)" \
  "JCOV_MODE=baseline-chunk" \
  "CHUNK_ID=chunk-6" \
  "JCOV_TEST_PATHS=java/net,com/sun/net/httpserver,sun/net,java/sql,javax/sql,javax/transaction,javax/rmi,javax/naming,javax/script,javax/smartcardio,javax/xml,com/sun/jndi,java/text,sun/text,java/time,java/util/Arrays/TimSortStackSize2.java" \
  "RUN_STAMP=${RUN_STAMP}" \
  "EXPERIMENT_DIR=jdk-pilot" \
  "JTREG_CONCURRENCY=${CONCURRENCY}"

log ""
log "=== Disparando builds generated (wit-context + direct-tests) em paralelo ==="

start_build "generated wit-context" \
  "JCOV_MODE=generated" \
  "JCOV_VARIANT=wit-context" \
  "RUN_STAMP=${RUN_STAMP}" \
  "EXPERIMENT_DIR=jdk-pilot" \
  "JTREG_CONCURRENCY=${CONCURRENCY}"

start_build "generated direct-tests" \
  "JCOV_MODE=generated" \
  "JCOV_VARIANT=direct-tests" \
  "RUN_STAMP=${RUN_STAMP}" \
  "EXPERIMENT_DIR=jdk-pilot" \
  "JTREG_CONCURRENCY=${CONCURRENCY}"

log ""
log "════════════════════════════════════════"
log "8 builds disparados!"
log ""
log "Aguarde todos completarem (~45 min) e então rode o merge:"
log ""
log "  RUN_STAMP=${RUN_STAMP} MERGE_ONLY=sim bash codebuild-start-jcov-all.sh"
log "════════════════════════════════════════"

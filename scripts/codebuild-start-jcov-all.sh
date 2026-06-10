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
  local tmp
  tmp=$(mktemp /tmp/codebuild_input_XXXXXX.json)
  # Gera JSON via python3 sem heredoc (evita bug de parsing com curl | bash)
  python3 -c "
import json, sys
project = sys.argv[1]
env_vars = []
for arg in sys.argv[2:]:
    name, value = arg.split('=', 1)
    env_vars.append({'name': name, 'value': value, 'type': 'PLAINTEXT'})
print(json.dumps({'projectName': project, 'timeoutInMinutesOverride': 45, 'environmentVariablesOverride': env_vars}))
" "${PROJECT}" "$@" > "${tmp}"
  local ids
  ids=$(aws codebuild start-build \
    --cli-input-json "file://${tmp}" \
    --query "build.id" --output text)
  rm -f "${tmp}"
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

log "=== Disparando builds baseline (chunks 1–9) em paralelo ==="

# chunk-1a: java/lang core (sem invoke/instrument que são pesados)
start_build "baseline chunk-1a (java/lang core)" \
  "JCOV_MODE=baseline-chunk" \
  "CHUNK_ID=chunk-1a" \
  "JCOV_TEST_PATHS=java/lang/annotation,java/lang/ref,java/lang/reflect,java/lang/runtime,java/lang/String,java/lang/StringBuffer,java/lang/StringBuilder,java/lang/Thread,java/lang/ClassLoader,java/lang/Class,java/lang/Boolean,java/lang/Byte,java/lang/Character,java/lang/Double,java/lang/Float,java/lang/Integer,java/lang/Long,java/lang/Short,java/lang/Math,java/lang/StrictMath,java/lang/Enum,java/lang/ProcessBuilder,sun/invoke,sun/misc,sun/reflect" \
  "RUN_STAMP=${RUN_STAMP}" \
  "EXPERIMENT_DIR=jdk-pilot" \
  "JTREG_CONCURRENCY=${CONCURRENCY}"

# chunk-1b: java/lang/invoke + java/lang/instrument (pesados)
start_build "baseline chunk-1b (java/lang invoke+instrument)" \
  "JCOV_MODE=baseline-chunk" \
  "CHUNK_ID=chunk-1b" \
  "JCOV_TEST_PATHS=java/lang/invoke,java/lang/instrument" \
  "RUN_STAMP=${RUN_STAMP}" \
  "EXPERIMENT_DIR=jdk-pilot" \
  "JTREG_CONCURRENCY=${CONCURRENCY}"

# chunk-2: tier1 part2 — java/util
start_build "baseline chunk-2 (java/util tier1)" \
  "JCOV_MODE=baseline-chunk" \
  "CHUNK_ID=chunk-2" \
  "JCOV_TEST_PATHS=java/util/AbstractCollection,java/util/AbstractList,java/util/AbstractMap,java/util/ArrayList,java/util/Arrays,java/util/BitSet,java/util/Collections,java/util/Comparator,java/util/HashMap,java/util/HashSet,java/util/LinkedHashMap,java/util/LinkedList,java/util/Optional,java/util/PriorityQueue,java/util/TreeMap,java/util/Vector,java/util/concurrent,java/util/function,java/util/stream,sun/util" \
  "RUN_STAMP=${RUN_STAMP}" \
  "EXPERIMENT_DIR=jdk-pilot" \
  "JTREG_CONCURRENCY=${CONCURRENCY}"

# chunk-3: tier1 part3
start_build "baseline chunk-3 (math+nio+jdi tier1)" \
  "JCOV_MODE=baseline-chunk" \
  "CHUNK_ID=chunk-3" \
  "JCOV_TEST_PATHS=java/math,java/nio/Buffer,com/sun/jdi,tools/pack200,com/sun/crypto/provider/Cipher,sun/nio/cs/ISO8859x.java" \
  "RUN_STAMP=${RUN_STAMP}" \
  "EXPERIMENT_DIR=jdk-pilot" \
  "JTREG_CONCURRENCY=${CONCURRENCY}"

# chunk-4a: security sem SSL (java/security + crypto)
start_build "baseline chunk-4a (security crypto)" \
  "JCOV_MODE=baseline-chunk" \
  "CHUNK_ID=chunk-4a" \
  "JCOV_TEST_PATHS=java/security,javax/crypto,javax/xml/crypto,com/oracle/security/ucrypto,com/sun/crypto,javax/security,com/sun/jarsigner,com/sun/security,com/sun/org/apache/xml/internal/security,sun/security,com/sun/net/ssl" \
  "RUN_STAMP=${RUN_STAMP}" \
  "EXPERIMENT_DIR=jdk-pilot" \
  "JTREG_CONCURRENCY=${CONCURRENCY}"

# chunk-4b: javax/net/ssl (isolado — muitos testes lentos)
start_build "baseline chunk-4b (javax/net/ssl)" \
  "JCOV_MODE=baseline-chunk" \
  "CHUNK_ID=chunk-4b" \
  "JCOV_TEST_PATHS=javax/net/ssl,javax/net" \
  "RUN_STAMP=${RUN_STAMP}" \
  "EXPERIMENT_DIR=jdk-pilot" \
  "JTREG_CONCURRENCY=${CONCURRENCY}"

# chunk-5: tools + io + nio
start_build "baseline chunk-5 (tools+io+nio tier2)" \
  "JCOV_MODE=baseline-chunk" \
  "CHUNK_ID=chunk-5" \
  "JCOV_TEST_PATHS=tools,java/io,java/nio,sun/nio,sun/tools/java,sun/tools/jrunscript" \
  "RUN_STAMP=${RUN_STAMP}" \
  "EXPERIMENT_DIR=jdk-pilot" \
  "JTREG_CONCURRENCY=${CONCURRENCY}"

# chunk-6a: java/net/httpclient (isolado — muito pesado)
start_build "baseline chunk-6a (java/net/httpclient)" \
  "JCOV_MODE=baseline-chunk" \
  "CHUNK_ID=chunk-6a" \
  "JCOV_TEST_PATHS=java/net/httpclient" \
  "RUN_STAMP=${RUN_STAMP}" \
  "EXPERIMENT_DIR=jdk-pilot" \
  "JTREG_CONCURRENCY=${CONCURRENCY}"

# chunk-6b: resto do net + sql + text + time + jndi
start_build "baseline chunk-6b (net-basic+sql+text+time)" \
  "JCOV_MODE=baseline-chunk" \
  "CHUNK_ID=chunk-6b" \
  "JCOV_TEST_PATHS=java/net/Authenticator,java/net/BindException,java/net/CookieHandler,java/net/DatagramPacket,java/net/DatagramSocket,java/net/DatagramSocketImpl,com/sun/net/httpserver,sun/net,java/sql,javax/sql,javax/transaction,javax/rmi,javax/naming,javax/script,javax/smartcardio,javax/xml,com/sun/jndi,java/text,sun/text,java/time,java/util/Arrays/TimSortStackSize2.java" \
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
log "11 builds disparados!"
log ""
log "Aguarde todos completarem (~45 min) e então rode o merge:"
log ""
log "  RUN_STAMP=${RUN_STAMP} MERGE_ONLY=sim bash codebuild-start-jcov-all.sh"
log "════════════════════════════════════════"

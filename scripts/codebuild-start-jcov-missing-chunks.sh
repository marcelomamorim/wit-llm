#!/usr/bin/env bash
# codebuild-start-jcov-missing-chunks.sh
#
# Dispara apenas os 6 chunks novos que completam tier1+tier2.
# Os 18 chunks existentes já estão no S3 — não precisam ser re-rodados.
#
# Uso (no CloudShell AWS):
#   RUN_STAMP=pilot-20260607T041241Z bash codebuild-start-jcov-missing-chunks.sh
#
# Após todos completarem, rodar o merge:
#   RUN_STAMP=pilot-20260607T041241Z MERGE_ONLY=sim bash codebuild-start-jcov-all.sh

set -euo pipefail

PROJECT="wit-llm-jcov-baseline"
RUN_STAMP="${RUN_STAMP:-pilot-20260607T041241Z}"
CONCURRENCY="${JTREG_CONCURRENCY:-1}"

log() { printf '[start-missing] %s\n' "$*"; }

start_build() {
  local desc="$1"; shift
  local tmp
  tmp=$(mktemp /tmp/codebuild_input_XXXXXX.json)
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

log "=== Disparando 6 chunks faltantes de tier1+tier2 ==="
log "    (18 chunks existentes preservados no S3)"
log ""

# 1. jdk/internal/* + jdk/lambda + jdk/modules + vm  (parte de :jdk_lang)
start_build "chunk-t1-jdk-internal" \
  "JCOV_MODE=baseline-chunk" \
  "CHUNK_ID=chunk-t1-jdk-internal" \
  "JCOV_TEST_PATHS=jdk/internal/reflect,jdk/internal/loader,jdk/internal/misc,jdk/internal/ref,jdk/internal/jimage,jdk/internal/math,jdk/lambda,jdk/modules,vm" \
  "RUN_STAMP=${RUN_STAMP}" \
  "EXPERIMENT_DIR=jdk-pilot" \
  "JTREG_CONCURRENCY=${CONCURRENCY}"

# 2. :jdk_util completo  (tier1_part2 — chunk-2 cobria só parte)
start_build "chunk-t1-util-remaining" \
  "JCOV_MODE=baseline-chunk" \
  "CHUNK_ID=chunk-t1-util-remaining" \
  "JCOV_TEST_PATHS=:jdk_util" \
  "RUN_STAMP=${RUN_STAMP}" \
  "EXPERIMENT_DIR=jdk-pilot" \
  "JTREG_CONCURRENCY=${CONCURRENCY}"

# 3. :jdk_svc_sanity  (tier1_part3)
start_build "chunk-t1-svc-sanity" \
  "JCOV_MODE=baseline-chunk" \
  "CHUNK_ID=chunk-t1-svc-sanity" \
  "JCOV_TEST_PATHS=:jdk_svc_sanity" \
  "RUN_STAMP=${RUN_STAMP}" \
  "EXPERIMENT_DIR=jdk-pilot" \
  "JTREG_CONCURRENCY=${CONCURRENCY}"

# 4. jdk_other + jdk_text + jdk_time  (tier2_part2)
start_build "chunk-t2-other" \
  "JCOV_MODE=baseline-chunk" \
  "CHUNK_ID=chunk-t2-other" \
  "JCOV_TEST_PATHS=java/sql,javax/sql,javax/transaction,javax/rmi,javax/naming,javax/script,javax/smartcardio,javax/xml,com/sun/jndi,java/text,sun/text,java/time,java/util/Arrays/TimSortStackSize2.java" \
  "RUN_STAMP=${RUN_STAMP}" \
  "EXPERIMENT_DIR=jdk-pilot" \
  "JTREG_CONCURRENCY=${CONCURRENCY}"

# 5. jdk/nio + jdk/internal/jrtfs + sun/tools  (tier2_part2)
start_build "chunk-t2-nio-jdk" \
  "JCOV_MODE=baseline-chunk" \
  "CHUNK_ID=chunk-t2-nio-jdk" \
  "JCOV_TEST_PATHS=jdk/nio,jdk/internal/jrtfs,sun/tools/java,sun/tools/jrunscript" \
  "RUN_STAMP=${RUN_STAMP}" \
  "EXPERIMENT_DIR=jdk-pilot" \
  "JTREG_CONCURRENCY=${CONCURRENCY}"

# 6. java/net básico + sun/net + jdk/net  (tier2_part3, exceto httpclient já coberto)
start_build "chunk-t2-net-basic" \
  "JCOV_MODE=baseline-chunk" \
  "CHUNK_ID=chunk-t2-net-basic" \
  "JCOV_TEST_PATHS=java/net/Authenticator,java/net/BindException,java/net/CookieHandler,java/net/DatagramPacket,java/net/DatagramSocket,java/net/DatagramSocketImpl,java/net/HttpCookie,java/net/InetAddress,java/net/MulticastSocket,java/net/NetworkInterface,java/net/ProxySelector,java/net/ResponseCache,java/net/Socket,java/net/SocketOption,java/net/SocketPermission,java/net/URI,java/net/URL,java/net/URLClassLoader,java/net/URLConnection,java/net/ipv6,java/net/spi,java/net/ftp,com/sun/net/httpserver,sun/net,jdk/net" \
  "RUN_STAMP=${RUN_STAMP}" \
  "EXPERIMENT_DIR=jdk-pilot" \
  "JTREG_CONCURRENCY=${CONCURRENCY}"

log ""
log "════════════════════════════════════════"
log "6 chunks disparados! Aguarde ~45min e rode o merge:"
log ""
log "  RUN_STAMP=${RUN_STAMP} MERGE_ONLY=sim bash codebuild-start-jcov-all.sh"
log ""
log "O merge vai combinar os 18 chunks existentes + 6 novos = 24 chunks."
log "════════════════════════════════════════"

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

# chunk-1b: java/lang/invoke (pesado sozinho)
start_build "baseline chunk-1b (java/lang/invoke)" \
  "JCOV_MODE=baseline-chunk" \
  "CHUNK_ID=chunk-1b" \
  "JCOV_TEST_PATHS=java/lang/invoke" \
  "RUN_STAMP=${RUN_STAMP}" \
  "EXPERIMENT_DIR=jdk-pilot" \
  "JTREG_CONCURRENCY=${CONCURRENCY}"

# chunk-1c: java/lang/instrument (pesado sozinho)
start_build "baseline chunk-1c (java/lang/instrument)" \
  "JCOV_MODE=baseline-chunk" \
  "CHUNK_ID=chunk-1c" \
  "JCOV_TEST_PATHS=java/lang/instrument" \
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

# chunk-4b: ssl core (sem DTLS/TLSv1x que são os mais lentos)
start_build "baseline chunk-4b (ssl-core)" \
  "JCOV_MODE=baseline-chunk" \
  "CHUNK_ID=chunk-4b" \
  "JCOV_TEST_PATHS=javax/net/ssl/ALPN,javax/net/ssl/FixingJavadocs,javax/net/ssl/HttpsURLConnection,javax/net/ssl/sanity,javax/net/ssl/ServerName,javax/net/ssl/SSLEngine,javax/net/ssl/SSLEngineResult,javax/net/ssl/SSLParameters,javax/net/ssl/SSLServerSocket,javax/net/ssl/SSLSession,javax/net/ssl/Stapling,javax/net/ssl/templates,javax/net/ssl/TLS,javax/net/ssl/finalize,javax/net/ssl/ciphersuites,javax/net/ssl/etc,javax/net/ssl/GetInstance.java,javax/net/ssl/Fix5070632.java,javax/net" \
  "RUN_STAMP=${RUN_STAMP}" \
  "EXPERIMENT_DIR=jdk-pilot" \
  "JTREG_CONCURRENCY=${CONCURRENCY}"

# chunk-4c: ssl DTLS + TLSv1x (os mais lentos)
start_build "baseline chunk-4c (ssl-dtls-tlsv1x)" \
  "JCOV_MODE=baseline-chunk" \
  "CHUNK_ID=chunk-4c" \
  "JCOV_TEST_PATHS=javax/net/ssl/DTLS,javax/net/ssl/DTLSv10,javax/net/ssl/TLSCommon,javax/net/ssl/TLSv1,javax/net/ssl/TLSv11,javax/net/ssl/TLSv12,javax/net/ssl/interop" \
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

# chunk-6a: httpclient root (testes .java direto em java/net/httpclient/)
start_build "baseline chunk-6a (httpclient-root)" \
  "JCOV_MODE=baseline-chunk" \
  "CHUNK_ID=chunk-6a" \
  "JCOV_TEST_PATHS=java/net/httpclient/security,java/net/httpclient/offline,java/net/httpclient/AbstractThrowingPushPromises.java,java/net/httpclient/BasicAuthTest.java,java/net/httpclient/BufferingSubscriberCancelTest.java,java/net/httpclient/BufferingSubscriberErrorCompleteTest.java,java/net/httpclient/BufferingSubscriberTest.java,java/net/httpclient/ConnectExceptionTest.java,java/net/httpclient/ConnectTimeoutNoProxyAsync.java,java/net/httpclient/ConnectTimeoutNoProxySync.java,java/net/httpclient/DigestEchoClient.java,java/net/httpclient/FlowAdaptersCompileOnly.java,java/net/httpclient/HeadersTest.java,java/net/httpclient/HeadersTest1.java,java/net/httpclient/HeadersTest2.java,java/net/httpclient/HttpClientBuilderTest.java,java/net/httpclient/HttpHeadersOf.java,java/net/httpclient/HttpRequestBuilderTest.java,java/net/httpclient/HttpResponseInputStreamTest.java,java/net/httpclient/ImmutableHeaders.java,java/net/httpclient/InterruptedBlockingSend.java,java/net/httpclient/LineAdaptersCompileOnly.java,java/net/httpclient/LineStreamsAndSurrogatesTest.java,java/net/httpclient/LineSubscribersAndSurrogatesTest.java,java/net/httpclient/MessageHeadersTest.java,java/net/httpclient/MethodsTest.java,java/net/httpclient/MultiAuthTest.java,java/net/httpclient/ProxyAuthDisabledSchemes.java,java/net/httpclient/ProxyAuthTest.java,java/net/httpclient/RequestBuilderTest.java,java/net/httpclient/RetryPost.java,java/net/httpclient/ShortRequestBody.java,java/net/httpclient/SmallTimeout.java,java/net/httpclient/SplitResponse.java,java/net/httpclient/examples" \
  "RUN_STAMP=${RUN_STAMP}" \
  "EXPERIMENT_DIR=jdk-pilot" \
  "JTREG_CONCURRENCY=${CONCURRENCY}"

# chunk-6b: httpclient/http2
start_build "baseline chunk-6b (httpclient-http2)" \
  "JCOV_MODE=baseline-chunk" \
  "CHUNK_ID=chunk-6b" \
  "JCOV_TEST_PATHS=java/net/httpclient/http2" \
  "RUN_STAMP=${RUN_STAMP}" \
  "EXPERIMENT_DIR=jdk-pilot" \
  "JTREG_CONCURRENCY=${CONCURRENCY}"

# chunk-6c: httpclient/websocket + whitebox + testes lentos restantes
start_build "baseline chunk-6c (httpclient-websocket+whitebox)" \
  "JCOV_MODE=baseline-chunk" \
  "CHUNK_ID=chunk-6c" \
  "JCOV_TEST_PATHS=java/net/httpclient/websocket,java/net/httpclient/whitebox,java/net/httpclient/ssltest,java/net/httpclient/BasicRedirectTest.java,java/net/httpclient/CancelledResponse.java,java/net/httpclient/ConcurrentResponses.java,java/net/httpclient/ConnectTimeoutHandshakeAsync.java,java/net/httpclient/ConnectTimeoutHandshakeSync.java,java/net/httpclient/CookieHeaderTest.java,java/net/httpclient/CustomRequestPublisher.java,java/net/httpclient/CustomResponseSubscriber.java,java/net/httpclient/DependentActionsTest.java,java/net/httpclient/DependentPromiseActionsTest.java,java/net/httpclient/DigestEchoClientSSL.java,java/net/httpclient/EncodedCharsInURI.java,java/net/httpclient/EscapedOctetsInURI.java,java/net/httpclient/ExpectContinue.java,java/net/httpclient/FlowAdapterPublisherTest.java,java/net/httpclient/FlowAdapterSubscriberTest.java,java/net/httpclient/HandshakeFailureTest.java,java/net/httpclient/HeadTest.java,java/net/httpclient/HttpsTunnelTest.java,java/net/httpclient/ImmutableFlowItems.java,java/net/httpclient/InvalidInputStreamSubscriptionRequest.java,java/net/httpclient/InvalidSSLContextTest.java,java/net/httpclient/InvalidSubscriptionRequest.java,java/net/httpclient/LineBodyHandlerTest.java,java/net/httpclient/MappingResponseSubscriber.java,java/net/httpclient/MaxStreams.java,java/net/httpclient/NoBodyPartOne.java,java/net/httpclient/NoBodyPartTwo.java,java/net/httpclient/NonAsciiCharsInURI.java,java/net/httpclient/ProxyAuthDisabledSchemesSSL.java,java/net/httpclient/ProxyTest.java,java/net/httpclient/RedirectMethodChange.java,java/net/httpclient/RedirectWithCookie.java,java/net/httpclient/RequestBodyTest.java,java/net/httpclient/ResponseBodyBeforeError.java,java/net/httpclient/ResponsePublisher.java,java/net/httpclient/RetryWithCookie.java,java/net/httpclient/ServerCloseTest.java,java/net/httpclient/ShortResponseBody.java,java/net/httpclient/ShortResponseBodyWithRetry.java,java/net/httpclient/SmokeTest.java,java/net/httpclient/SpecialHeadersTest.java" \
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

# ── Novos chunks para completar tier1+tier2 ──────────────────────────────────

# chunk-t1-jdk-internal: jdk/internal/* + jdk/lambda + jdk/modules + vm
# (parte de :jdk_lang que estava faltando no chunk-1a)
start_build "baseline chunk-t1-jdk-internal (jdk/internal+lambda+modules+vm)" \
  "JCOV_MODE=baseline-chunk" \
  "CHUNK_ID=chunk-t1-jdk-internal" \
  "JCOV_TEST_PATHS=jdk/internal/reflect,jdk/internal/loader,jdk/internal/misc,jdk/internal/ref,jdk/internal/jimage,jdk/internal/math,jdk/lambda,jdk/modules,vm" \
  "RUN_STAMP=${RUN_STAMP}" \
  "EXPERIMENT_DIR=jdk-pilot" \
  "JTREG_CONCURRENCY=${CONCURRENCY}"

# chunk-t1-util-remaining: java/util restante não coberto pelo chunk-2
# (usa group specifier :jdk_util para garantir cobertura completa)
start_build "baseline chunk-t1-util-remaining (:jdk_util completo)" \
  "JCOV_MODE=baseline-chunk" \
  "CHUNK_ID=chunk-t1-util-remaining" \
  "JCOV_TEST_PATHS=:jdk_util" \
  "RUN_STAMP=${RUN_STAMP}" \
  "EXPERIMENT_DIR=jdk-pilot" \
  "JTREG_CONCURRENCY=${CONCURRENCY}"

# chunk-t1-svc-sanity: :jdk_svc_sanity (tier1_part3)
# (jdi sanity + jfr sanity — testes rápidos de sanidade)
start_build "baseline chunk-t1-svc-sanity (:jdk_svc_sanity)" \
  "JCOV_MODE=baseline-chunk" \
  "CHUNK_ID=chunk-t1-svc-sanity" \
  "JCOV_TEST_PATHS=:jdk_svc_sanity" \
  "RUN_STAMP=${RUN_STAMP}" \
  "EXPERIMENT_DIR=jdk-pilot" \
  "JTREG_CONCURRENCY=${CONCURRENCY}"

# chunk-t2-other: jdk_other + jdk_text + jdk_time (tier2_part2)
# java/sql, javax/*, java/text, java/time, com/sun/jndi
start_build "baseline chunk-t2-other (sql+xml+naming+text+time)" \
  "JCOV_MODE=baseline-chunk" \
  "CHUNK_ID=chunk-t2-other" \
  "JCOV_TEST_PATHS=java/sql,javax/sql,javax/transaction,javax/rmi,javax/naming,javax/script,javax/smartcardio,javax/xml,com/sun/jndi,java/text,sun/text,java/time,java/util/Arrays/TimSortStackSize2.java" \
  "RUN_STAMP=${RUN_STAMP}" \
  "EXPERIMENT_DIR=jdk-pilot" \
  "JTREG_CONCURRENCY=${CONCURRENCY}"

# chunk-t2-nio-jdk: jdk/nio + jdk/internal/jrtfs + sun/tools (tier2_part2)
start_build "baseline chunk-t2-nio-jdk (jdk/nio+jrtfs+sun/tools)" \
  "JCOV_MODE=baseline-chunk" \
  "CHUNK_ID=chunk-t2-nio-jdk" \
  "JCOV_TEST_PATHS=jdk/nio,jdk/internal/jrtfs,sun/tools/java,sun/tools/jrunscript" \
  "RUN_STAMP=${RUN_STAMP}" \
  "EXPERIMENT_DIR=jdk-pilot" \
  "JTREG_CONCURRENCY=${CONCURRENCY}"

# chunk-t2-net-basic: java/net básico (exceto httpclient) + sun/net + jdk/net (tier2_part3)
start_build "baseline chunk-t2-net-basic (java/net+sun/net+jdk/net)" \
  "JCOV_MODE=baseline-chunk" \
  "CHUNK_ID=chunk-t2-net-basic" \
  "JCOV_TEST_PATHS=java/net/Authenticator,java/net/BindException,java/net/CookieHandler,java/net/DatagramPacket,java/net/DatagramSocket,java/net/DatagramSocketImpl,java/net/HttpCookie,java/net/HttpURLConnection,java/net/InetAddress,java/net/Inet4Address,java/net/Inet6Address,java/net/InetSocketAddress,java/net/MulticastSocket,java/net/NetworkInterface,java/net/ProxySelector,java/net/ResponseCache,java/net/ServerSocket,java/net/Socket,java/net/SocketOption,java/net/SocketPermission,java/net/Socks,java/net/URI,java/net/URL,java/net/URLClassLoader,java/net/URLConnection,java/net/URLDecoder,java/net/URLEncoder,java/net/URLPermission,java/net/ipv6tests,java/net/spi,com/sun/net/httpserver,sun/net,jdk/net" \
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
log "14 builds disparados! (9 baseline + 2 generated + 1 merge)"
log ""
log "Aguarde todos completarem (~45 min) e então rode o merge:"
log ""
log "  RUN_STAMP=${RUN_STAMP} MERGE_ONLY=sim bash codebuild-start-jcov-all.sh"
log "════════════════════════════════════════"

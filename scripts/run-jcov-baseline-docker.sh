#!/usr/bin/env bash
# run-jcov-baseline-docker.sh
# Mede cobertura de branch JCov do baseline (tier1+tier2) dentro do container.
#
# JDK OBRIGATÓRIA: da75f3c4 (JDK 11+28) via TEST_JDK=/opt/test-jdk na imagem.
# O script aborta se TEST_JDK não for JDK 11.
#
# Saída persistida em bind mount ./generated:/data/generated:
#   generated/experiments/${EXPERIMENT_DIR}/${RUN_STAMP}/jcov-baseline/
#     jcov-result.xml   — cobertura de branch do tier1+tier2
#     report/           — relatório jtreg
#     work/             — arquivos .jtr por teste
#
# Variáveis de ambiente:
#   EXPERIMENT_DIR  : default jdk-global-impact-study
#   RUN_STAMP       : obrigatório

set -euo pipefail

EXPERIMENT_DIR="${EXPERIMENT_DIR:-jdk-global-impact-study}"
RUN_STAMP="${RUN_STAMP:-}"

if [[ -z "$RUN_STAMP" ]]; then
  printf 'erro: RUN_STAMP é obrigatório.\n' >&2
  exit 1
fi

JCOV_JAR=/opt/jcov/JCOV_BUILD/jcov_3.0/jcov.jar
TEST_JDK=/opt/test-jdk
JDK_SRC=/opt/openjdk-src
OUT="/data/generated/experiments/${EXPERIMENT_DIR}/${RUN_STAMP}/jcov-baseline"

# Verificação crítica: JDK deve ser da75f3c4 (JDK 11)
JDK_VERSION=$("${TEST_JDK}/bin/java" -version 2>&1 | head -1)
printf '[jcov-baseline] TEST_JDK version: %s\n' "$JDK_VERSION"
if ! echo "${JDK_VERSION}" | grep -q '"11'; then
  printf 'ERRO CRÍTICO: TEST_JDK não é JDK 11! (%s)\n' "$JDK_VERSION" >&2
  printf 'A JDK usada DEVE ser da75f3c4 (JDK 11+28) sem exceções.\n' >&2
  exit 2
fi
printf '[jcov-baseline] OK: JDK 11 confirmada.\n'

if [ ! -f "${JCOV_JAR}" ]; then
  printf 'ERRO: JCov jar não encontrado em %s\n' "${JCOV_JAR}" >&2
  exit 1
fi

mkdir -p "${OUT}"
printf '[jcov-baseline] OUT=%s\n' "${OUT}"
printf '[jcov-baseline] tiers=tier1+tier2 concurrency=1\n'

# Wrapper seletivo: injeta JCov apenas no MainWrapper (testes reais)
WRAPPER=/tmp/wrapper-jdk-baseline
rm -rf "${WRAPPER}"
cp -a "${TEST_JDK}/." "${WRAPPER}/"
cat > "${WRAPPER}/bin/java" <<WRAP
#!/bin/bash
REAL=${TEST_JDK}/bin/java
AGENT="-javaagent:${JCOV_JAR}=file=${OUT}/jcov-result.xml,type=branch,merge=merge,include=java/,include=javax/,include=sun/,include=com/sun/,include=jdk/,native=off"
for arg in "\$@"; do
  if [[ "\$arg" == "com.sun.javatest.regtest.agent.MainWrapper" ]]; then
    exec "\${REAL}" \${AGENT} "\$@"
  fi
done
exec "\${REAL}" "\$@"
WRAP
chmod +x "${WRAPPER}/bin/java"

printf '[jcov-baseline] iniciando jtreg tier1+tier2 com JCov...\n'

/opt/jtreg/bin/jtreg \
  -jdk:"${WRAPPER}" \
  -r:"${OUT}/report" \
  -w:"${OUT}/work" \
  -agentvm -automatic -ignore:quiet \
  -verbose:summary -retain:fail,error \
  -concurrency:1 -timeoutFactor:2 \
  "${JDK_SRC}/test/jdk:tier1" \
  "${JDK_SRC}/test/jdk:tier2"

printf '[jcov-baseline] concluído.\n'
if [ -f "${OUT}/jcov-result.xml" ]; then
  ls -lh "${OUT}/jcov-result.xml"
  printf '[jcov-baseline] jcov-result.xml gerado com sucesso.\n'
else
  printf 'AVISO: jcov-result.xml não encontrado em %s\n' "${OUT}" >&2
fi

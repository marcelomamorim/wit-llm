#!/usr/bin/env bash
# run-jcov-baseline-chunk.sh
#
# Versão particionada do run-jcov-baseline-docker.sh.
# Roda JCov em um subconjunto de paths do JDK (um chunk do baseline).
#
# Variáveis de ambiente:
#   EXPERIMENT_DIR  : default jdk-global-impact-study
#   RUN_STAMP       : obrigatório
#   CHUNK_ID        : identificador do chunk (ex: chunk-1) — obrigatório
#   JCOV_TEST_PATHS : paths jtreg separados por vírgula (ex: "java/lang,java/util")
#                     relativos a ${JDK_SRC}/test/jdk
#   JTREG_CONCURRENCY: default 1

set -euo pipefail

EXPERIMENT_DIR="${EXPERIMENT_DIR:-jdk-global-impact-study}"
RUN_STAMP="${RUN_STAMP:-}"
CHUNK_ID="${CHUNK_ID:-}"
JCOV_TEST_PATHS="${JCOV_TEST_PATHS:-}"
JTREG_CONCURRENCY="${JTREG_CONCURRENCY:-1}"

[[ -n "${RUN_STAMP}" ]]      || { echo "erro: RUN_STAMP é obrigatório" >&2; exit 1; }
[[ -n "${CHUNK_ID}" ]]       || { echo "erro: CHUNK_ID é obrigatório" >&2; exit 1; }
[[ -n "${JCOV_TEST_PATHS}" ]] || { echo "erro: JCOV_TEST_PATHS é obrigatório" >&2; exit 1; }

JCOV_JAR=/opt/jcov/JCOV_BUILD/jcov_3.0/jcov.jar
TEST_JDK=/opt/test-jdk
JDK_SRC=/opt/openjdk-src
OUT="/data/generated/experiments/${EXPERIMENT_DIR}/${RUN_STAMP}/jcov-baseline-chunks/${CHUNK_ID}"

# Verificação crítica: JDK deve ser JDK 11
JDK_VERSION=$("${TEST_JDK}/bin/java" -version 2>&1 | head -1)
printf '[jcov-chunk] TEST_JDK version: %s\n' "${JDK_VERSION}"
if ! echo "${JDK_VERSION}" | grep -q '"11'; then
  printf 'ERRO: TEST_JDK não é JDK 11!\n' >&2; exit 2
fi

mkdir -p "${OUT}"
printf '[jcov-chunk] CHUNK_ID=%s\n' "${CHUNK_ID}"
printf '[jcov-chunk] OUT=%s\n' "${OUT}"
printf '[jcov-chunk] concurrency=%s\n' "${JTREG_CONCURRENCY}"
printf '[jcov-chunk] paths=%s\n' "${JCOV_TEST_PATHS}"

# Wrapper seletivo
WRAPPER=/tmp/wrapper-jdk-${CHUNK_ID}
rm -rf "${WRAPPER}"
cp -a "${TEST_JDK}/." "${WRAPPER}/"
JCOV_RESULT="${OUT}/jcov-result.xml"
cat > "${WRAPPER}/bin/java" <<WRAP
#!/bin/bash
REAL=${TEST_JDK}/bin/java
AGENT="-javaagent:${JCOV_JAR}=file=${JCOV_RESULT},type=branch,merge=merge,include=java/,include=javax/,include=sun/,include=com/sun/,include=jdk/,native=off"
for arg in "\$@"; do
  if [[ "\$arg" == "com.sun.javatest.regtest.agent.MainWrapper" ]]; then
    exec "\${REAL}" \${AGENT} "\$@"
  fi
done
exec "\${REAL}" "\$@"
WRAP
chmod +x "${WRAPPER}/bin/java"

# Converter JCOV_TEST_PATHS (vírgula) em argumentos jtreg
# Suporta dois formatos:
#   :group_name  -> ${JDK_SRC}/test/jdk:group_name  (respeita TEST.groups, tier exato)
#   path/subdir  -> ${JDK_SRC}/test/jdk/path/subdir  (todos os testes do diretório)
JTREG_ARGS=()
IFS=',' read -ra PATHS <<< "${JCOV_TEST_PATHS}"
for p in "${PATHS[@]}"; do
  p="${p// /}"  # trim spaces
  [[ -z "${p}" ]] && continue
  if [[ "${p}" == :* ]]; then
    # group specifier: :tier1_part1 -> test/jdk:tier1_part1
    JTREG_ARGS+=("${JDK_SRC}/test/jdk${p}")
  else
    JTREG_ARGS+=("${JDK_SRC}/test/jdk/${p}")
  fi
done

printf '[jcov-chunk] iniciando jtreg (%d paths)...\n' "${#JTREG_ARGS[@]}"

set +e
/opt/jtreg/bin/jtreg \
  -jdk:"${WRAPPER}" \
  -r:"${OUT}/report" \
  -w:"${OUT}/work" \
  -agentvm -automatic -ignore:quiet \
  -verbose:summary -retain:fail,error \
  -concurrency:"${JTREG_CONCURRENCY}" -timeoutFactor:2 \
  "${JTREG_ARGS[@]}"
JTREG_EXIT=$?
set -e

printf '[jcov-chunk] jtreg encerrado (exit=%d)\n' "${JTREG_EXIT}"

# Mesclar arquivos UUID residuais
# UUID files são gerados quando múltiplos JVMs tentam escrever no mesmo XML simultaneamente.
# Se o merge falhar (ex: arquivo corrompido com proxy class), descarta os UUID files e
# mantém o jcov-result.xml principal, que já contém a cobertura acumulada.
UUID_FILES=$(find "${OUT}" -maxdepth 1 -name "jcov-result.xml.*" ! -name "*.lock" 2>/dev/null || true)
if [[ -n "${UUID_FILES}" ]]; then
  printf '[jcov-chunk] encontrados arquivos UUID residuais — tentando merge...\n'
  set +e
  java -jar "${JCOV_JAR}" Merger \
    -output "${JCOV_RESULT}" \
    -boe skip \
    "${JCOV_RESULT}" ${UUID_FILES} 2>&1 | tail -5
  MERGE_EXIT=$?
  set -e
  if [[ "${MERGE_EXIT}" -ne 0 ]]; then
    printf '[jcov-chunk] AVISO: merge falhou (exit=%d) — descartando UUID files e mantendo XML principal\n' "${MERGE_EXIT}"
  else
    printf '[jcov-chunk] merge OK\n'
  fi
  rm -f ${UUID_FILES}
fi

if [[ -f "${JCOV_RESULT}" ]]; then
  ls -lh "${JCOV_RESULT}"
  printf '[jcov-chunk] jcov-result.xml gerado com sucesso.\n'

  # Extrair métricas completas
  ANALYZE_SCRIPT="/data/scripts/analyze_jcov.py"
  if [ ! -f "${ANALYZE_SCRIPT}" ]; then
    printf '[jcov-chunk] Baixando analyze_jcov.py...\n'
    curl -sf https://raw.githubusercontent.com/marcelomamorim/wit-llm/main/scripts/analyze_jcov.py \
      -o "${ANALYZE_SCRIPT}"
    chmod +x "${ANALYZE_SCRIPT}"
  fi
  python3 "${ANALYZE_SCRIPT}" \
    "${JCOV_RESULT}" \
    --variant "${CHUNK_ID}" \
    --output "${OUT}/summary.json"
  printf '[jcov-chunk] summary.json escrito.\n'
else
  printf 'AVISO: jcov-result.xml não encontrado.\n' >&2
  exit 1
fi

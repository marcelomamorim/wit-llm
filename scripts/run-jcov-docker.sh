#!/usr/bin/env bash
# run-jcov-docker.sh
# Mede cobertura de branch JCov dos testes gerados — agente dinâmico seletivo.
#
# Problema do JDK 11 + JCov:
#   JDK 11 armazena classes em lib/modules (JIMAGE binário), não em .class avulsos.
#   "jcov Instr" não consegue instrumentar o JIMAGE → 0 classes → sem cobertura.
#
#   Usar -javaagent: via -javaoptions: do jtreg causa falha no probe interno do
#   jtreg (GetJDKProperties), pois o agente é injetado no probe também → exit 5.
#
# Solução: wrapper seletivo para bin/java
#   O wrapper injeta o agente JCov SOMENTE quando jtreg executa testes reais
#   (detectado pela presença de "MainWrapper" nos argumentos). Para o probe e
#   compilação, passa os argumentos direto para o java real sem agente.
#
# JDK OBRIGATÓRIA: da75f3c4 (JDK 11+28) via TEST_JDK=/opt/test-jdk
#
# Variáveis de ambiente:
#   EXPERIMENT_DIR      : default jdk-global-impact-study
#   RUN_STAMP           : timestamp do run
#   JTREG_CONCURRENCY   : default 1 (JCov com merge funciona melhor em serial)
#   JTREG_TIMEOUT_FACTOR: default 2

set -euo pipefail

EXPERIMENT_DIR="${EXPERIMENT_DIR:-jdk-global-impact-study}"
RUN_STAMP="${RUN_STAMP:-run}"

RUN_DIR="/data/generated/experiments/${EXPERIMENT_DIR}/${RUN_STAMP}"
VARIANTS_ROOT="${RUN_DIR}/variants"
JCOV_RESULTS_DIR="${RUN_DIR}/jcov-results"

JTREG="${JTREG_HOME:-/opt/jtreg}/bin/jtreg"
TEST_JDK="${TEST_JDK:-/opt/test-jdk}"
JCOV_HOME="${JCOV_HOME:-/opt/jcov/JCOV_BUILD/jcov_3.0}"
JCOV_JAR="${JCOV_HOME}/jcov.jar"
CONCURRENCY="${JTREG_CONCURRENCY:-1}"
TIMEOUT_FACTOR="${JTREG_TIMEOUT_FACTOR:-2}"

log() { printf '[jcov] %s\n' "$*"; }

# ── Verificação crítica: JDK deve ser da75f3c4 (JDK 11) ────────────────────────
JDK_VERSION=$("${TEST_JDK}/bin/java" -version 2>&1 | head -1)
log "TEST_JDK version: ${JDK_VERSION}"
if ! echo "${JDK_VERSION}" | grep -q '"11'; then
  log "ERRO CRÍTICO: TEST_JDK não é JDK 11! (${JDK_VERSION})"
  log "A JDK usada DEVE ser da75f3c4 (JDK 11+28) sem exceções."
  exit 2
fi
log "OK: JDK 11 confirmada."

if [ ! -f "${JCOV_JAR}" ]; then
  log "ERRO: JCov jar não encontrado em ${JCOV_JAR}"
  exit 1
fi

mkdir -p "${JCOV_RESULTS_DIR}"

log "RUN_DIR=${RUN_DIR}"
log "TEST_JDK=${TEST_JDK}"
log "JCOV_JAR=${JCOV_JAR}"
log "CONCURRENCY=${CONCURRENCY}"
log "TIMEOUT_FACTOR=${TIMEOUT_FACTOR}x"

# ── Criar wrapper-jdk com java seletivo ────────────────────────────────────────
# Criado uma vez e reutilizado pelas duas variantes.
WRAPPER_JDK="/tmp/wrapper-jdk-jcov"

if [ ! -d "${WRAPPER_JDK}/bin" ]; then
  log ""
  log "=== Criando wrapper-jdk seletivo ==="
  rm -rf "${WRAPPER_JDK}"

  # Copiar JDK completa para o wrapper-jdk
  # (necessário para jtreg encontrar javac, jshell, etc.)
  log "Copiando ${TEST_JDK} → ${WRAPPER_JDK} (pode demorar ~30s)..."
  cp -a "${TEST_JDK}/." "${WRAPPER_JDK}/"

  log "Wrapper JDK criado em ${WRAPPER_JDK}"
else
  log "Wrapper JDK já existe — reutilizando ${WRAPPER_JDK}"
fi

run_jcov_variant() {
  local variant_name="$1"
  local variant_root="${VARIANTS_ROOT}/${variant_name}"
  local generated_dir="${variant_root}/test/jdk/witup/generated"

  log ""
  log "=== variante: ${variant_name} ==="

  if [ ! -d "${generated_dir}" ]; then
    log "SKIP — sem testes gerados em ${generated_dir}"
    return
  fi

  local test_count
  test_count=$(find "${generated_dir}" -name "*.java" | wc -l | tr -d ' ')
  log "testes encontrados: ${test_count}"

  local jcov_out_dir="${JCOV_RESULTS_DIR}/${variant_name}"
  mkdir -p "${jcov_out_dir}"

  local jcov_result_file="${jcov_out_dir}/jcov-result.xml"
  local work_dir="${variant_root}/jtreg-work-jcov"
  local report_dir="${variant_root}/jtreg-report-jcov"
  local log_file="${jcov_out_dir}/jcov-run.log"
  mkdir -p "${work_dir}" "${report_dir}"

  rm -f "${jcov_result_file}"

  log "resultado JCov: ${jcov_result_file}"

  # ── Wrapper seletivo para bin/java ──────────────────────────────────────────
  # Injeta agente JCov SOMENTE quando jtreg executa testes via MainWrapper.
  # Probe (GetJDKProperties) e compilações (javac) NÃO recebem o agente.
  local java_wrapper="${WRAPPER_JDK}/bin/java"

  # Calcular excludes ANTES do heredoc (variáveis expandidas pelo shell externo)
  # JCOV_EXCLUDE_INTRINSICS=on (default) exclui @HotSpotIntrinsicCandidate que
  # causam SIGBUS no ARM64. Usar =off apenas para diagnóstico.
  local intrinsic_excludes=""
  if [ "${JCOV_EXCLUDE_INTRINSICS:-on}" = "on" ]; then
    intrinsic_excludes=",exclude=java/lang/Object,exclude=java/lang/Class,exclude=java/lang/System,exclude=java/lang/Thread,exclude=java/lang/Double,exclude=java/lang/Float,exclude=java/lang/reflect/Array,exclude=java/util/zip/CRC32"
    log "JCOV_EXCLUDE_INTRINSICS=on — excluindo 8 classes @HotSpotIntrinsicCandidate (anti-SIGBUS)"
  else
    log "JCOV_EXCLUDE_INTRINSICS=off — sem excludes (pode causar SIGBUS no ARM64)"
  fi

  cat > "${java_wrapper}" <<WRAPPER
#!/bin/bash
# Wrapper seletivo JCov — injeta agente apenas para MainWrapper (testes reais)
REAL_JAVA="${TEST_JDK}/bin/java"
JCOV_AGENT="-javaagent:${JCOV_JAR}=file=${jcov_result_file},type=branch,merge=merge,include=java/,include=javax/,include=sun/,include=com/sun/,include=jdk/,native=off${intrinsic_excludes}"

for arg in "\$@"; do
  if [[ "\$arg" == "com.sun.javatest.regtest.agent.MainWrapper" ]]; then
    exec "\${REAL_JAVA}" \${JCOV_AGENT} "\$@"
  fi
done
exec "\${REAL_JAVA}" "\$@"
WRAPPER
  chmod +x "${java_wrapper}"

  log "Estratégia: wrapper seletivo (agente só em MainWrapper)"
  log "  wrapper: ${java_wrapper}"

  # ── Executar jtreg ─────────────────────────────────────────────────────────
  set +e
  "${JTREG}" \
    -jdk:"${WRAPPER_JDK}" \
    -w:"${work_dir}" \
    -r:"${report_dir}" \
    -conc:"${CONCURRENCY}" \
    -timeout:"${TIMEOUT_FACTOR}" \
    -verbose:fail \
    "${generated_dir}" 2>&1 | tee "${log_file}"
  local jtreg_exit=$?
  set -e

  log "jtreg encerrado (exit=${jtreg_exit})"

  if [ -f "${jcov_result_file}" ]; then
    # ── Mesclar arquivos UUID residuais antes de gerar o relatório ──────────────
    # JCov merge=merge cria arquivos {result}.{UUID} quando há conflito de lock.
    # Esses arquivos precisam ser mesclados explicitamente no arquivo principal.
    local uuid_files
    uuid_files=$(find "$(dirname "${jcov_result_file}")" \
                   -maxdepth 1 -name "$(basename "${jcov_result_file}").*" \
                   ! -name "*.lock" 2>/dev/null)
    if [ -n "${uuid_files}" ]; then
      log "Mesclando arquivos UUID residuais do JCov..."
      set +e
      java -jar "${JCOV_JAR}" Merger \
        -output "${jcov_result_file}" \
        "${jcov_result_file}" ${uuid_files} 2>&1 | tail -3
      local merge_exit=$?
      set -e
      if [ "${merge_exit}" -eq 0 ]; then
        log "Merge OK — removendo arquivos UUID"
        rm -f ${uuid_files}
      else
        log "AVISO: merge falhou (exit=${merge_exit}) — usando ${jcov_result_file} sem merge"
      fi
    fi

    local size
    size=$(du -sh "${jcov_result_file}" | cut -f1)
    log "jcov-result.xml final: ${size}"

    # Gerar relatório HTML
    log "gerando relatório JCov (RepGen)..."
    set +e
    java -jar "${JCOV_JAR}" RepGen \
      -output "${jcov_out_dir}/jcov-html-report" \
      "${jcov_result_file}" 2>&1 | tail -5
    set -e

    # Extrair cobertura via Python
    # JCov XML usa atributo 'count' (não 'covered') nos elementos de branch:
    #   methenter, catch, case, cond, default, fall, tg
    # Coberto = count > 0; Não coberto = count == 0
    local covered uncovered total coverage_pct
    read covered uncovered < <(python3 -c "
import xml.etree.ElementTree as ET
BRANCH_TAGS = {'methenter', 'catch', 'case', 'cond', 'default', 'fall', 'tg'}
try:
    root = ET.parse('${jcov_result_file}').getroot()
    covered = 0
    uncovered = 0
    for el in root.iter():
        tag = el.tag.split('}')[-1] if '}' in el.tag else el.tag
        if tag in BRANCH_TAGS and 'count' in el.attrib:
            if int(el.get('count', '0')) > 0:
                covered += 1
            else:
                uncovered += 1
    print(covered, uncovered)
except:
    print(0, 0)
" 2>/dev/null || echo "0 0")
    total=$(( covered + uncovered ))
    coverage_pct=0
    [ "${total}" -gt 0 ] && coverage_pct=$(( covered * 100 / total ))
    log "${variant_name}: covered=${covered} uncovered=${uncovered} total=${total} branch_coverage=${coverage_pct}%"

    cat > "${jcov_out_dir}/summary.json" <<EOJSON
{
  "variant": "${variant_name}",
  "jcov_result_file": "${jcov_result_file}",
  "covered_branches": ${covered},
  "uncovered_branches": ${uncovered},
  "total_branches": ${total},
  "branch_coverage_pct": ${coverage_pct},
  "jtreg_exit": ${jtreg_exit}
}
EOJSON
    log "summary.json escrito"
  else
    log "ATENÇÃO: jcov-result.xml NÃO gerado para ${variant_name}"
    log "  Verifique ${log_file} para detalhes"

    # Diagnóstico: verificar se o wrapper foi invocado com MainWrapper
    log "  Diagnóstico: últimas 5 linhas do log:"
    tail -5 "${log_file}" | while IFS= read -r line; do log "    ${line}"; done

    cat > "${jcov_out_dir}/summary.json" <<EOJSON
{
  "variant": "${variant_name}",
  "error": "jcov-result.xml not generated",
  "jtreg_exit": ${jtreg_exit}
}
EOJSON
  fi
}

# ── Main ──────────────────────────────────────────────────────────────────────
# JCOV_VARIANT: se definido, roda apenas a variante especificada (ex: "wit-context")
if [ -n "${JCOV_VARIANT:-}" ]; then
  run_jcov_variant "${JCOV_VARIANT}"
else
  run_jcov_variant "direct-tests"
  run_jcov_variant "wit-context"
fi

log ""
log "=== RESUMO JCov ==="
for v in direct-tests wit-context; do
  summary="${JCOV_RESULTS_DIR}/${v}/summary.json"
  if [ -f "${summary}" ]; then
    cat "${summary}"
  fi
done

#!/usr/bin/env bash
# run-jcov-pilot-docker.sh  (executa DENTRO do container)
#
# Mede cobertura JCov (branch) para o piloto JDK com subconjunto reduzido:
#   - baseline: com/sun/crypto/provider/Cipher/AES (testes originais)
#   - wit-context: testes gerados na variante wit-context
#   - direct-tests: testes gerados na variante direct-tests
#
# Saída: generated/experiments/${EXPERIMENT_DIR}/${RUN_STAMP}/jcov-results/
#   baseline/summary.json
#   wit-context/summary.json
#   direct-tests/summary.json
#   comparison.json

set -euo pipefail

EXPERIMENT_DIR="${EXPERIMENT_DIR:-jdk-pilot}"
RUN_STAMP="${RUN_STAMP:-}"
[[ -n "${RUN_STAMP}" ]] || { echo "erro: RUN_STAMP obrigatório" >&2; exit 1; }

JCOV_JAR=/opt/jcov/JCOV_BUILD/jcov_3.0/jcov.jar
TEST_JDK=/opt/test-jdk
JTREG=/opt/jtreg/bin/jtreg
CONCURRENCY="${JTREG_CONCURRENCY:-1}"

RUN_DIR="/data/generated/experiments/${EXPERIMENT_DIR}/${RUN_STAMP}"
VARIANTS_ROOT="${RUN_DIR}/variants"
RESULTS_DIR="${RUN_DIR}/jcov-results"

log()  { printf '[jcov-pilot] %s\n' "$*"; }
warn() { printf '[jcov-pilot] AVISO: %s\n' "$*"; }

# ── Verificação JDK 11 ────────────────────────────────────────────────────────
JDK_VERSION=$("${TEST_JDK}/bin/java" -version 2>&1 | head -1)
log "TEST_JDK: ${JDK_VERSION}"
echo "${JDK_VERSION}" | grep -q '"11' || { echo "ERRO: precisa JDK 11" >&2; exit 2; }

mkdir -p "${RESULTS_DIR}"

# ── Criar wrapper seletivo (injeta JCov apenas em MainWrapper) ────────────────
make_wrapper() {
  local result_xml="$1"
  local wrapper_dir
  wrapper_dir=$(mktemp -d /tmp/jcov-wrapper-XXXXXX)
  cp -a "${TEST_JDK}/." "${wrapper_dir}/"
  cat > "${wrapper_dir}/bin/java" << WRAP
#!/bin/bash
REAL=${TEST_JDK}/bin/java
AGENT="-javaagent:${JCOV_JAR}=file=${result_xml},type=branch,merge=merge,include=java/,include=javax/,include=sun/,include=com/sun/,include=jdk/,native=off"
for arg in "\$@"; do
  if [[ "\$arg" == "com.sun.javatest.regtest.agent.MainWrapper" ]]; then
    exec "\${REAL}" \${AGENT} "\$@"
  fi
done
exec "\${REAL}" "\$@"
WRAP
  chmod +x "${wrapper_dir}/bin/java"
  echo "${wrapper_dir}"
}

# ── Extrair métricas de cobertura do jcov-result.xml ─────────────────────────
# Retorna: b_cov b_unc m_cov m_unc l_cov l_unc
# branch: cond/case/default/fall/tg/catch/br/goto  |  method: methenter
# line: aproximado pelos atributos sl/line/pos dos elementos
extract_coverage() {
  local xml="$1"
  [[ -f "${xml}" ]] || { echo "0 0 0 0 0 0"; return; }
  python3 - "${xml}" << 'PYEOF'
import sys
import xml.etree.ElementTree as ET
BRANCH_TAGS = {'cond', 'case', 'default', 'fall', 'tg', 'catch', 'br', 'goto'}
METHOD_TAGS = {'methenter'}
ALL_TAGS = BRANCH_TAGS | METHOD_TAGS
try:
    root = ET.parse(sys.argv[1]).getroot()
    b_cov = b_unc = m_cov = m_unc = 0
    line_cov = set()
    line_unc = set()
    for el in root.iter():
        tag = el.tag.split('}')[-1] if '}' in el.tag else el.tag
        if tag not in ALL_TAGS or 'count' not in el.attrib:
            continue
        count = int(el.get('count', '0'))
        ln_raw = el.get('sl') or el.get('line') or el.get('pos', '')
        try:
            ln = int(ln_raw)
        except (ValueError, TypeError):
            ln = None
        if tag in METHOD_TAGS:
            if count > 0: m_cov += 1
            else: m_unc += 1
        else:
            if count > 0: b_cov += 1
            else: b_unc += 1
        if ln is not None:
            if count > 0: line_cov.add(ln)
            else: line_unc.add(ln)
    l_cov = len(line_cov)
    l_unc = len(line_unc - line_cov)
    print(b_cov, b_unc, m_cov, m_unc, l_cov, l_unc)
except Exception:
    print(0, 0, 0, 0, 0, 0)
PYEOF
}

# ── Função: rodar jtreg com JCov e retornar summary.json ────────────────────
run_jcov() {
  local variant_name="$1"
  local test_path="$2"        # caminho para diretório de testes
  local out_dir="${RESULTS_DIR}/${variant_name}"
  local xml="${out_dir}/jcov-result.xml"
  mkdir -p "${out_dir}"

  log "=== ${variant_name} ==="
  log "  test_path: ${test_path}"

  local wrapper
  wrapper=$(make_wrapper "${xml}")
  trap "rm -rf '${wrapper}'" RETURN

  local work="${out_dir}/jtreg-work"
  local report="${out_dir}/jtreg-report"
  mkdir -p "${work}" "${report}"

  set +e
  # Modo othervm (padrão): cada teste roda em JVM separada via MainWrapper
  # → o wrapper seletivo injeta o agente JCov apenas nos testes reais.
  # -agentvm NÃO deve ser usado aqui: nesse modo MainWrapper não é chamado.
  "${JTREG}" \
    -jdk:"${wrapper}" \
    -r:"${report}" \
    -w:"${work}" \
    -verbose:summary -retain:fail,error \
    -concurrency:"${CONCURRENCY}" -timeoutFactor:2 \
    -javacoption:-encoding -javacoption:UTF-8 \
    "${test_path}" 2>&1 | tee "${out_dir}/jtreg-run.log"
  local jtreg_exit=$?
  set -e

  # Contar pass/fail
  local pass fail
  pass=$(grep -oP "passed:\s+\K[0-9]+" "${out_dir}/jtreg-run.log" 2>/dev/null | tail -1 || echo 0)
  fail=$(grep -oP "failed:\s+\K[0-9]+" "${out_dir}/jtreg-run.log" 2>/dev/null | tail -1 || echo 0)
  local total=$(( pass + fail ))
  local pass_rate=0
  [ "${total}" -gt 0 ] && pass_rate=$(( pass * 100 / total ))

  # Extrair cobertura expandida (branch + method + line)
  local b_cov b_unc m_cov m_unc l_cov l_unc
  read b_cov b_unc m_cov m_unc l_cov l_unc < <(extract_coverage "${xml}")
  local total_br=$(( b_cov + b_unc ))
  local total_mt=$(( m_cov + m_unc ))
  local total_ln=$(( l_cov + l_unc ))
  local br_pct=0; [ "${total_br}" -gt 0 ] && br_pct=$(( b_cov * 100 / total_br ))
  local mt_pct=0; [ "${total_mt}" -gt 0 ] && mt_pct=$(( m_cov * 100 / total_mt ))
  local ln_pct=0; [ "${total_ln}" -gt 0 ] && ln_pct=$(( l_cov * 100 / total_ln ))

  log "  pass=${pass} fail=${fail} total=${total} pass_rate=${pass_rate}%"
  log "  branch: ${b_cov}/${total_br}(${br_pct}%)  method: ${m_cov}/${total_mt}(${mt_pct}%)  line: ${l_cov}/${total_ln}(${ln_pct}%)"

  cat > "${out_dir}/summary.json" << EOJSON
{
  "variant": "${variant_name}",
  "pass": ${pass},
  "fail": ${fail},
  "total_tests": ${total},
  "pass_rate_pct": ${pass_rate},
  "covered_branches": ${b_cov},
  "uncovered_branches": ${b_unc},
  "total_branches": ${total_br},
  "branch_coverage_pct": ${br_pct},
  "covered_methods": ${m_cov},
  "uncovered_methods": ${m_unc},
  "total_methods": ${total_mt},
  "method_coverage_pct": ${mt_pct},
  "covered_lines": ${l_cov},
  "uncovered_lines": ${l_unc},
  "total_lines": ${total_ln},
  "line_coverage_pct": ${ln_pct},
  "jcov_xml": "${xml}",
  "jtreg_exit": ${jtreg_exit}
}
EOJSON
}

# ── Rodar as 3 variantes ─────────────────────────────────────────────────────

# 1. Baseline: testes originais Cipher/AES
BASELINE_TEST="${VARIANTS_ROOT}/baseline/test/jdk/com/sun/crypto/provider/Cipher/AES"
if [[ -d "${BASELINE_TEST}" ]]; then
  run_jcov "baseline" "${BASELINE_TEST}"
else
  warn "baseline/test não encontrado — pulando"
fi

# 2. wit-context: testes gerados
WIT_TEST="${VARIANTS_ROOT}/wit-context/test/jdk/witup/generated"
if [[ -d "${WIT_TEST}" ]]; then
  run_jcov "wit-context" "${WIT_TEST}"
else
  warn "wit-context/test/jdk/witup/generated não encontrado — pulando"
fi

# 3. direct-tests: testes gerados
DIRECT_TEST="${VARIANTS_ROOT}/direct-tests/test/jdk/witup/generated"
if [[ -d "${DIRECT_TEST}" ]]; then
  run_jcov "direct-tests" "${DIRECT_TEST}"
else
  warn "direct-tests/test/jdk/witup/generated não encontrado — pulando"
fi

# ── Comparação final ─────────────────────────────────────────────────────────
log ""
log "=== Resumo JCov Piloto ==="
python3 - "${RESULTS_DIR}" << 'PYEOF'
import json, os, sys

d = sys.argv[1]
variants = ['baseline', 'wit-context', 'direct-tests']
results = {}
for v in variants:
    p = os.path.join(d, v, 'summary.json')
    if os.path.exists(p):
        with open(p) as f:
            results[v] = json.load(f)

print(f"\n{'Variante':<16} {'Testes':>7} {'Pass%':>6} {'Branch%':>8} {'Method%':>8} {'Line%':>7}")
print('-'*60)
for v in variants:
    if v not in results:
        print(f"{v:<16} {'—':>7} {'—':>6} {'—':>8} {'—':>8} {'—':>7}")
        continue
    r = results[v]
    print(f"{v:<16} {r.get('total_tests',0):>7} {r.get('pass_rate_pct',0):>5}% "
          f"{r.get('branch_coverage_pct',0):>7}% {r.get('method_coverage_pct',0):>7}% "
          f"{r.get('line_coverage_pct',0):>6}%")

# Salvar comparação
with open(os.path.join(d, 'comparison.json'), 'w') as f:
    json.dump(results, f, indent=2)
print(f"\ncomparison.json salvo em {d}")
PYEOF

log ""
log "Resultados: ${RESULTS_DIR}"

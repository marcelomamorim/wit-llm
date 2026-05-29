#!/usr/bin/env bash
# run-jtreg-docker.sh
# Executa jtreg sobre os testes gerados nas variantes direct-tests e wit-context.
# Não re-materializa as variantes — usa as que já existem.
# Opcionalmente roda o baseline tier1+tier2 (lento; RUN_BASELINE=sim para ativar).
#
# Variáveis de ambiente:
#   EXPERIMENT_DIR      : subdiretório em generated/experiments/
#   RUN_STAMP           : timestamp do run
#   RUN_BASELINE        : "sim" para rodar jtreg tier1+tier2 no baseline (default: nao)
#   JTREG_CONCURRENCY   : paralelismo do jtreg (default: 4)
#   JTREG_TEST_TIMEOUT  : timeout por teste em segundos (default: 120)

set -euo pipefail

EXPERIMENT_DIR="${EXPERIMENT_DIR:-jdk-global-impact-study}"
RUN_STAMP="${RUN_STAMP:-run}"
RUN_BASELINE="${RUN_BASELINE:-nao}"

RUN_DIR="/data/generated/experiments/${EXPERIMENT_DIR}/${RUN_STAMP}"
VARIANTS_ROOT="${RUN_DIR}/variants"
RESULTS_FILE="${RUN_DIR}/jtreg-results.json"

JTREG="${JTREG_HOME:-/opt/jtreg}/bin/jtreg"
TEST_JDK="${TEST_JDK:-/opt/test-jdk}"
CONCURRENCY="${JTREG_CONCURRENCY:-4}"
TIMEOUT_FACTOR="${JTREG_TIMEOUT_FACTOR:-1}"

log() {
  printf '[jtreg] %s\n' "$*"
}

parse_jtreg_counts() {
  local logfile="$1"
  # jtreg summary line: "Test results: passed: N; failed: N; error: N"
  local pass fail error
  pass=$(grep -oP "passed:\s+\K[0-9]+" "${logfile}" 2>/dev/null | tail -1 || echo "0")
  fail=$(grep -oP "failed:\s+\K[0-9]+" "${logfile}" 2>/dev/null | tail -1 || echo "0")
  error=$(grep -oP "error:\s+\K[0-9]+" "${logfile}" 2>/dev/null | tail -1 || echo "0")
  echo "${pass:-0} ${fail:-0} ${error:-0}"
}

run_jtreg_generated() {
  local variant_name="$1"
  local variant_root="${VARIANTS_ROOT}/${variant_name}"
  local generated_dir="${variant_root}/test/jdk/witup/generated"

  log "=== variante: ${variant_name} ==="

  if [ ! -d "${generated_dir}" ]; then
    log "SKIP — sem testes gerados em ${generated_dir}"
    echo "\"${variant_name}\": {\"pass\": 0, \"fail\": 0, \"error\": 0, \"status\": \"skipped\"}"
    return
  fi

  local test_count
  test_count=$(find "${generated_dir}" -name "*.java" | wc -l | tr -d ' ')
  log "testes encontrados: ${test_count}"

  local work_dir="${variant_root}/jtreg-work-witup"
  local report_dir="${variant_root}/jtreg-report-witup"
  local log_file="${report_dir}/jtreg-run.log"
  mkdir -p "${work_dir}" "${report_dir}"

  log "iniciando jtreg (conc=${CONCURRENCY}, timeout_factor=${TIMEOUT_FACTOR}x)..."

  set +e
  "${JTREG}" \
    -jdk:"${TEST_JDK}" \
    -w:"${work_dir}" \
    -r:"${report_dir}" \
    -conc:"${CONCURRENCY}" \
    -timeout:"${TIMEOUT_FACTOR}" \
    -verbose:fail \
    -javacoption:-encoding -javacoption:UTF-8 \
    "${generated_dir}" 2>&1 | tee "${log_file}"
  local jtreg_exit=$?
  set -e

  read -r pass fail error <<< "$(parse_jtreg_counts "${log_file}")"
  local total=$(( pass + fail + error ))
  local rate=0
  [ "${total}" -gt 0 ] && rate=$(( pass * 100 / total ))

  log "${variant_name}: pass=${pass} fail=${fail} error=${error} total=${total} pass_rate=${rate}%"

  echo "\"${variant_name}\": {\"pass\": ${pass}, \"fail\": ${fail}, \"error\": ${error}, \"total\": ${total}, \"pass_rate_pct\": ${rate}, \"jtreg_exit\": ${jtreg_exit}}"
}

run_jtreg_baseline_tier1_tier2() {
  local variant_root="${VARIANTS_ROOT}/baseline"
  log "=== variante: baseline (tier1+tier2) ==="

  local test_dir="${variant_root}/test"
  if [ ! -d "${test_dir}" ]; then
    log "SKIP — diretório test/ não encontrado em ${variant_root}"
    echo "\"baseline\": {\"pass\": 0, \"fail\": 0, \"error\": 0, \"status\": \"skipped\"}"
    return
  fi

  local work_dir="${variant_root}/jtreg-work-baseline"
  local report_dir="${variant_root}/jtreg-report-baseline"
  local log_file="${report_dir}/jtreg-run.log"
  mkdir -p "${work_dir}" "${report_dir}"

  log "iniciando jtreg baseline tier1+tier2 (conc=1, timeout_factor=${TIMEOUT_FACTOR}x)..."

  set +e
  "${JTREG}" \
    -jdk:"${TEST_JDK}" \
    -w:"${work_dir}" \
    -r:"${report_dir}" \
    -conc:1 \
    -timeout:"${TIMEOUT_FACTOR}" \
    -verbose:fail \
    -k:"tier1|tier2" \
    "${test_dir}/jdk" 2>&1 | tee "${log_file}"
  local jtreg_exit=$?
  set -e

  read -r pass fail error <<< "$(parse_jtreg_counts "${log_file}")"
  local total=$(( pass + fail + error ))
  local rate=0
  [ "${total}" -gt 0 ] && rate=$(( pass * 100 / total ))

  log "baseline: pass=${pass} fail=${fail} error=${error} total=${total} pass_rate=${rate}%"

  echo "\"baseline\": {\"pass\": ${pass}, \"fail\": ${fail}, \"error\": ${error}, \"total\": ${total}, \"pass_rate_pct\": ${rate}, \"jtreg_exit\": ${jtreg_exit}}"
}

# ── Main ──────────────────────────────────────────────────────────────────────

log "RUN_DIR=${RUN_DIR}"
log "TEST_JDK=${TEST_JDK}"
log "CONCURRENCY=${CONCURRENCY}"
log "TIMEOUT_FACTOR=${TIMEOUT_FACTOR}x"
log "RUN_BASELINE=${RUN_BASELINE}"

mkdir -p "${RUN_DIR}"

declare -a results=()

results+=( "$(run_jtreg_generated "direct-tests")" )
results+=( "$(run_jtreg_generated "wit-context")" )

if [[ "${RUN_BASELINE}" =~ ^(sim|1|yes|true)$ ]]; then
  results+=( "$(run_jtreg_baseline_tier1_tier2)" )
fi

# Escrever JSON de resultados
{
  printf '{\n'
  for i in "${!results[@]}"; do
    printf '  %s' "${results[$i]}"
    [ $i -lt $(( ${#results[@]} - 1 )) ] && printf ','
    printf '\n'
  done
  printf '}\n'
} > "${RESULTS_FILE}"

log "Resultados gravados em ${RESULTS_FILE}"
cat "${RESULTS_FILE}"

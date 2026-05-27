#!/usr/bin/env bash
# run-overnight-pipeline.sh
# Pipeline noturno completo: jtreg → JCov → test smells
#
# JDK OBRIGATÓRIA: da75f3c4 (JDK 11+28) — mesma versão do artigo WIT.
# A execução é ABORTADA se TEST_JDK não for JDK 11.
#
# Variáveis de ambiente:
#   EXPERIMENT_DIR      : default jdk-global-impact-study
#   RUN_STAMP           : default 20260526T142519Z_jdk_global_impact_batch_gpt41nano
#   JTREG_CONCURRENCY   : default 4 (jtreg); JCov usa 1
#   JTREG_TIMEOUT_FACTOR: default 2
#   SKIP_JTREG          : "sim" para pular jtreg (usar resultado existente)
#   SKIP_JCOV           : "sim" para pular JCov
#   SKIP_SMELLS         : "sim" para pular smells

set -euo pipefail

EXPERIMENT_DIR="${EXPERIMENT_DIR:-jdk-global-impact-study}"
RUN_STAMP="${RUN_STAMP:-}"
SKIP_JTREG="${SKIP_JTREG:-nao}"
SKIP_JCOV="${SKIP_JCOV:-nao}"
SKIP_SMELLS="${SKIP_SMELLS:-nao}"
TEST_JDK="${TEST_JDK:-/opt/test-jdk}"

if [[ -z "${RUN_STAMP}" ]]; then
  echo "[pipeline] ERRO: RUN_STAMP não definido. Passe via variável de ambiente." >&2
  exit 1
fi

# ── Log central do pipeline ───────────────────────────────────────────────────
# Toda a saída (stdout + stderr) é espelhada em pipeline.log dentro do run dir.
# No host: generated/experiments/${EXPERIMENT_DIR}/${RUN_STAMP}/pipeline.log
RUN_DIR_LOG="/data/generated/experiments/${EXPERIMENT_DIR}/${RUN_STAMP}"
mkdir -p "${RUN_DIR_LOG}"
PIPELINE_LOG="${RUN_DIR_LOG}/pipeline.log"
exec > >(tee -a "${PIPELINE_LOG}") 2>&1

log() { printf '\n[pipeline] %s\n' "$*"; }
hr()  { printf '=%.0s' {1..70}; printf '\n'; }

hr
log "Pipeline noturno iniciado: $(date -u '+%Y-%m-%dT%H:%M:%SZ')"
log "EXPERIMENT_DIR=${EXPERIMENT_DIR}"
log "RUN_STAMP=${RUN_STAMP}"
log "TEST_JDK=${TEST_JDK}"
hr

# ── Verificação crítica: JDK deve ser da75f3c4 (JDK 11+28) ────────────────────
JDK_VERSION=$("${TEST_JDK}/bin/java" -version 2>&1 | head -1)
log "TEST_JDK version: ${JDK_VERSION}"
if ! echo "${JDK_VERSION}" | grep -q '"11'; then
  log "ERRO CRÍTICO: TEST_JDK não é JDK 11!"
  log "  Encontrado: ${JDK_VERSION}"
  log "  Esperado:   JDK 11+28 (da75f3c4)"
  log "A JDK usada DEVE ser da75f3c4 sem exceções. Abortando."
  exit 2
fi
log "OK: JDK 11 confirmada."

# ── Stage 1: jtreg ─────────────────────────────────────────────────────────────
if [[ "${SKIP_JTREG}" =~ ^(sim|1|yes|true)$ ]]; then
  log "SKIP_JTREG=sim — pulando jtreg"
else
  hr
  log "Stage 1/3: jtreg (direct-tests + wit-context)"
  EXPERIMENT_DIR="${EXPERIMENT_DIR}" \
  RUN_STAMP="${RUN_STAMP}" \
  JTREG_CONCURRENCY="${JTREG_CONCURRENCY:-4}" \
  JTREG_TIMEOUT_FACTOR="${JTREG_TIMEOUT_FACTOR:-2}" \
  TEST_JDK="${TEST_JDK}" \
  bash /data/scripts/run-jtreg-docker.sh
  log "Stage 1 concluído: $(date -u '+%H:%M:%SZ')"
fi

# ── Stage 2: JCov ──────────────────────────────────────────────────────────────
if [[ "${SKIP_JCOV}" =~ ^(sim|1|yes|true)$ ]]; then
  log "SKIP_JCOV=sim — pulando JCov"
else
  hr
  log "Stage 2/3: JCov (branch coverage — serial, conc=1)"
  EXPERIMENT_DIR="${EXPERIMENT_DIR}" \
  RUN_STAMP="${RUN_STAMP}" \
  JTREG_CONCURRENCY=1 \
  JTREG_TIMEOUT_FACTOR="${JTREG_TIMEOUT_FACTOR:-2}" \
  TEST_JDK="${TEST_JDK}" \
  bash /data/scripts/run-jcov-docker.sh
  log "Stage 2 concluído: $(date -u '+%H:%M:%SZ')"
fi

# ── Stage 3: Test Smells ───────────────────────────────────────────────────────
if [[ "${SKIP_SMELLS}" =~ ^(sim|1|yes|true)$ ]]; then
  log "SKIP_SMELLS=sim — pulando test smells"
else
  hr
  log "Stage 3/3: Test Smells (análise estática)"
  EXPERIMENT_DIR="${EXPERIMENT_DIR}" \
  RUN_STAMP="${RUN_STAMP}" \
  SMELLS_OUT="/data/generated/experiments/${EXPERIMENT_DIR}/${RUN_STAMP}/smells-results" \
  bash /data/scripts/run-smells-docker.sh
  log "Stage 3 concluído: $(date -u '+%H:%M:%SZ')"
fi

# ── Resumo final ───────────────────────────────────────────────────────────────
hr
log "Pipeline noturno CONCLUÍDO: $(date -u '+%Y-%m-%dT%H:%M:%SZ')"
log "Resultados em: /data/generated/experiments/${EXPERIMENT_DIR}/${RUN_STAMP}/"
log ""
log "  jtreg-results.json"
log "  jcov-results/{direct-tests,wit-context}/summary.json"
log "  smells-results/{direct-tests,wit-context}-smells.json"
log "  smells-results/smells-summary.json"
hr

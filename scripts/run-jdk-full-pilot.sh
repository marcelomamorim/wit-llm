#!/usr/bin/env bash
# run-jdk-full-pilot.sh  (host-side)
#
# Orquestrador end-to-end do piloto JDK:
#   1. Clonar OpenJDK @ da75f3c4
#   2. Preparar amostra de métodos + gerar requests JSONL
#   3. Submeter batch OpenAI (gpt-4.1-nano, mais barato)
#   4. Polling automático até batch completar + download respostas
#   5. Avaliar / materializar variantes (baseline, wit-context, direct-tests)
#   6. Build imagem Docker evaluator
#   7. jtreg nas 3 variantes (baseline: tier1+tier2; gerados: todos)
#   8. JCov nas 3 variantes (branch + method + line coverage)
#   9. Test smells (wit-context vs direct-tests)
#  10. Relatório final consolidado
#
# Variáveis obrigatórias:
#   (nenhuma — o script gera testes via batch OpenAI automaticamente)
#
# Variáveis opcionais de controle:
#   PILOT_METHODS        : número de métodos amostrados (default: 10)
#   JTREG_CONCURRENCY    : paralelismo jtreg (default: 2)
#   FORCE_RECLONE        : "sim" para re-clonar o JDK mesmo se já existir
#   SKIP_BUILD_IMAGE     : "sim" para pular docker build (imagem já existe)
#   SKIP_BATCH_SUBMIT    : "sim" para pular submissão; requer RESPONSES_JSONL definido
#   RESPONSES_JSONL      : JSONL de respostas já baixado (quando SKIP_BATCH_SUBMIT=sim)
#   POLL_INTERVAL        : segundos entre polls (default: 60)
#   POLL_TIMEOUT_HOURS   : timeout máximo de polling (default: 24)
#   RUN_STAMP            : timestamp do run (gerado automaticamente se omitido)
#   MODEL_KEY            : chave do modelo no runtime.json (default: openai_main)
#   OPENAI_MODEL         : modelo OpenAI a usar (default: gpt-4.1-nano-2025-04-14)
#
# Uso básico:
#   ./scripts/run-jdk-full-pilot.sh
#
# Uso com reruns (batch já baixado):
#   SKIP_BATCH_SUBMIT=sim \
#   RESPONSES_JSONL=generated/experiments/jdk-pilot/<stamp>/responses_openai_batch_generation.jsonl \
#   RUN_STAMP=pilot-20260606T000000Z \
#     ./scripts/run-jdk-full-pilot.sh

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

PILOT_METHODS="${PILOT_METHODS:-10}"
JTREG_CONCURRENCY="${JTREG_CONCURRENCY:-2}"
FORCE_RECLONE="${FORCE_RECLONE:-nao}"
SKIP_BUILD_IMAGE="${SKIP_BUILD_IMAGE:-nao}"
SKIP_BATCH_SUBMIT="${SKIP_BATCH_SUBMIT:-nao}"
RESPONSES_JSONL="${RESPONSES_JSONL:-}"
POLL_INTERVAL="${POLL_INTERVAL:-60}"
POLL_TIMEOUT_HOURS="${POLL_TIMEOUT_HOURS:-24}"
MODEL_KEY="${MODEL_KEY:-openai_main}"
OPENAI_MODEL="${OPENAI_MODEL:-gpt-4.1-nano-2025-04-14}"

EXPERIMENT_SUBDIR="jdk-pilot"
RUN_STAMP="${RUN_STAMP:-pilot-$(date -u +%Y%m%dT%H%M%SZ)}"
PILOT_DIR="${ROOT_DIR}/generated/experiments/${EXPERIMENT_SUBDIR}/${RUN_STAMP}"
RUNTIME_CONFIG="${ROOT_DIR}/generated/configs/jdk-pilot.runtime.json"
JDK_COMMIT="da75f3c4ad5bdf25167a3ed80e51f567ab3dbd01"
JDK_ROOT="${ROOT_DIR}/generated/repos/jdk"
WIT_ANALYSIS="${ROOT_DIR}/generated/wit-data/jdk/wit_filtered.json"
WITUP="${ROOT_DIR}/bin/witup"

log()  { printf '\n[jdk-pilot] %s\n' "$*"; }
err()  { printf '[jdk-pilot] ERRO: %s\n' "$*" >&2; exit 1; }
step() { printf '\n[jdk-pilot] ══════════════════════════════\n[jdk-pilot] %s\n[jdk-pilot] ══════════════════════════════\n' "$*"; }

mkdir -p "${PILOT_DIR}"

# ── Compilar witup ────────────────────────────────────────────────────────────
if [[ ! -x "${WITUP}" ]]; then
  log "Compilando witup CLI..."
  mkdir -p "${ROOT_DIR}/bin"
  (cd "${ROOT_DIR}" && go build -o "${WITUP}" ./cmd/witup/)
fi

# ── Gerar runtime.json (temperature=0, max_output_tokens=2048 para compilação) ─
step "Gerando runtime.json"
mkdir -p "$(dirname "${RUNTIME_CONFIG}")"
python3 - "${RUNTIME_CONFIG}" "${OPENAI_MODEL}" << 'PYEOF'
import json, sys
path, model = sys.argv[1], sys.argv[2]
config = {
    "version": "1",
    "models": {
        "openai_main": {
            "provider": "openai_compatible",
            "model": model,
            "base_url": "https://api.openai.com/v1",
            "api_key_env": "OPENAI_API_KEY",
            "execution_backend": "batch",
            "endpoint": "/v1/responses",
            "temperature": 0,
            "max_output_tokens": 2048,
            "timeout_seconds": 120,
            "max_retries": 3
        }
    }
}
with open(path, 'w') as f:
    json.dump(config, f, indent=2)
print(f"  modelo  : {model}")
print(f"  temp    : 0  |  max_tokens: 2048")
print(f"  escrito : {path}")
PYEOF

# ── Passo 1: Clonar JDK ───────────────────────────────────────────────────────
step "Passo 1/9 — Clonar OpenJDK @ ${JDK_COMMIT:0:8}"
if [[ "${FORCE_RECLONE}" == "sim" ]]; then
  rm -rf "${JDK_ROOT}"
fi
if [[ ! -d "${JDK_ROOT}/.git" ]]; then
  log "Clonando openjdk/jdk (pode demorar ~10min)..."
  mkdir -p "$(dirname "${JDK_ROOT}")"
  git clone --filter=blob:none https://github.com/openjdk/jdk.git "${JDK_ROOT}"
fi
log "Checkout do commit WIT..."
git -C "${JDK_ROOT}" fetch --quiet origin
git -C "${JDK_ROOT}" checkout --quiet "${JDK_COMMIT}"
log "JDK pronto em ${JDK_ROOT}"

# ── Extrair wit_filtered.json se necessário ───────────────────────────────────
if [[ ! -f "${WIT_ANALYSIS}" ]]; then
  log "Extraindo wit_filtered.json da imagem Docker..."
  mkdir -p "$(dirname "${WIT_ANALYSIS}")"
  docker run --rm witup-llm/article-evaluator:latest \
    cat /opt/wit-data/jdk/wit_filtered.json > "${WIT_ANALYSIS}" || \
  docker run --rm witup-llm/evaluator:latest \
    cat /opt/wit-data/jdk/wit_filtered.json > "${WIT_ANALYSIS}"
  log "wit_filtered.json extraído"
fi

# ── Passo 2: Preparar amostra ─────────────────────────────────────────────────
step "Passo 2/9 — Preparar amostra de ${PILOT_METHODS} métodos"

REQUESTS_JSONL="${PILOT_DIR}/requests_pilot.jsonl"
"${WITUP}" preparar-estudo-jdk-global \
  --config          "${RUNTIME_CONFIG}" \
  --generation-model "${MODEL_KEY}" \
  --jdk-root        "${JDK_ROOT}" \
  --wit-analysis    "${WIT_ANALYSIS}" \
  --output-dir      "${PILOT_DIR}" \
  --requests        "${REQUESTS_JSONL}" \
  --method-count    "${PILOT_METHODS}" \
  --workers 4

REQ_COUNT=$(wc -l < "${REQUESTS_JSONL}" | tr -d ' ')
log "Requests JSONL: ${REQUESTS_JSONL} (${REQ_COUNT} linhas)"

# ── Passo 3: Submeter batch ───────────────────────────────────────────────────
BATCH_METADATA="${PILOT_DIR}/batch_submit_metadata.json"
BATCH_ID_FILE="${PILOT_DIR}/batch_id.txt"

if [[ "${SKIP_BATCH_SUBMIT}" =~ ^(sim|1|yes|true)$ ]]; then
  step "Passo 3/9 — SKIP_BATCH_SUBMIT=sim — pulando submissão"

  if [[ -z "${RESPONSES_JSONL}" ]]; then
    # Tentar localizar respostas já baixadas
    CANDIDATE="${PILOT_DIR}/responses_openai_batch_generation.jsonl"
    [[ -f "${CANDIDATE}" ]] || err "SKIP_BATCH_SUBMIT=sim mas RESPONSES_JSONL não definido e ${CANDIDATE} não existe."
    RESPONSES_JSONL="${CANDIDATE}"
  fi
  log "Usando respostas existentes: ${RESPONSES_JSONL}"
else
  step "Passo 3/9 — Submeter batch OpenAI (${OPENAI_MODEL})"

  "${WITUP}" submeter-openai-batch \
    --config   "${RUNTIME_CONFIG}" \
    --model    "${MODEL_KEY}" \
    --requests "${REQUESTS_JSONL}" \
    --output   "${BATCH_METADATA}"

  BATCH_ID=$(python3 -c "import json; d=json.load(open('${BATCH_METADATA}')); print(d.get('batch_id',''))")
  echo "${BATCH_ID}" > "${BATCH_ID_FILE}"
  log "Batch submetido: ${BATCH_ID}"
  log "Metadados: ${BATCH_METADATA}"

  # ── Passo 4: Polling ──────────────────────────────────────────────────────
  step "Passo 4/9 — Polling batch (intervalo=${POLL_INTERVAL}s, timeout=${POLL_TIMEOUT_HOURS}h)"

  BATCH_ID="${BATCH_ID}" \
  RUNTIME_CONFIG="${RUNTIME_CONFIG}" \
  OUTPUT_DIR="${PILOT_DIR}" \
  MODEL_KEY="${MODEL_KEY}" \
  POLL_INTERVAL="${POLL_INTERVAL}" \
  POLL_TIMEOUT_HOURS="${POLL_TIMEOUT_HOURS}" \
  WITUP="${WITUP}" \
    "${ROOT_DIR}/scripts/poll-openai-batch.sh"

  RESPONSES_JSONL="${PILOT_DIR}/responses_openai_batch_generation.jsonl"
fi

[[ -f "${RESPONSES_JSONL}" ]] || err "arquivo de respostas não encontrado: ${RESPONSES_JSONL}"
RESP_COUNT=$(wc -l < "${RESPONSES_JSONL}" | tr -d ' ')
log "Respostas disponíveis: ${RESPONSES_JSONL} (${RESP_COUNT} linhas)"

# ── Passo 5: Avaliar / materializar variantes ─────────────────────────────────
step "Passo 5/9 — Materializar variantes (baseline / wit-context / direct-tests)"

"${WITUP}" avaliar-estudo-jdk-global \
  --config          "${RUNTIME_CONFIG}" \
  --generation-model "${MODEL_KEY}" \
  --jdk-root        "${JDK_ROOT}" \
  --run-dir         "${PILOT_DIR}" \
  --responses       "${RESPONSES_JSONL}" \
  --workers 3

log "Variantes materializadas:"
for variant in baseline wit-context direct-tests; do
  dir="${PILOT_DIR}/variants/${variant}/test/jdk/witup/generated"
  if [[ -d "${dir}" ]]; then
    count=$(find "${dir}" -name "*.java" | wc -l | tr -d ' ')
    log "  ${variant}: ${count} arquivo(s) .java"
  else
    log "  ${variant}: (sem diretório de testes gerados)"
  fi
done

# ── Passo 6: Build imagem Docker evaluator ────────────────────────────────────
step "Passo 6/9 — Build imagem witup-llm/evaluator"
if [[ "${SKIP_BUILD_IMAGE}" =~ ^(sim|1|yes|true)$ ]]; then
  log "SKIP_BUILD_IMAGE=sim — pulando build"
else
  log "Build da imagem evaluator (~60min na primeira vez, usa cache depois)..."
  docker compose -f "${ROOT_DIR}/docker-compose.yml" build evaluator
fi

# ── Passo 7: jtreg nas 3 variantes ───────────────────────────────────────────
step "Passo 7/9 — jtreg (baseline: tier1+tier2, gerados: todos)"
log "JTREG_CONCURRENCY=${JTREG_CONCURRENCY}"

docker compose -f "${ROOT_DIR}/docker-compose.yml" run --rm \
  -v "${ROOT_DIR}/scripts:/data/scripts:ro" \
  -e EXPERIMENT_DIR="${EXPERIMENT_SUBDIR}" \
  -e RUN_STAMP="${RUN_STAMP}" \
  -e RUN_BASELINE="sim" \
  -e JTREG_CONCURRENCY="${JTREG_CONCURRENCY}" \
  -e JTREG_TIER_FILTER="tier1|tier2" \
  run-jtreg

JTREG_RESULTS="${PILOT_DIR}/jtreg-results.json"
if [[ -f "${JTREG_RESULTS}" ]]; then
  log "Resultados jtreg:"
  python3 - "${JTREG_RESULTS}" << 'PYEOF'
import json, sys
try:
    with open(sys.argv[1]) as f:
        results = json.load(f)
    print(f"\n  {'Variante':<20} {'Pass':>6} {'Fail':>6} {'Error':>7} {'Pass%':>7}")
    print('  ' + '-'*50)
    for variant, data in results.items():
        if isinstance(data, dict):
            p = data.get('pass', 0)
            fa = data.get('fail', 0)
            e = data.get('error', 0)
            t = p + fa + e
            r = data.get('pass_rate_pct', round(p/t*100, 1) if t else 0)
            st = data.get('status', '')
            flag = f' [{st}]' if st in ('skipped', 'error') else ''
            print(f"  {variant:<20} {p:>6} {fa:>6} {e:>7} {r:>6.1f}%{flag}")
except Exception as ex:
    print(f"  (erro ao ler resultados: {ex})")
PYEOF
fi

# ── Passo 8: JCov nas 3 variantes ─────────────────────────────────────────────
step "Passo 8/9 — JCov (branch + method + line coverage)"

docker compose -f "${ROOT_DIR}/docker-compose.yml" run --rm \
  -v "${ROOT_DIR}/scripts:/data/scripts:ro" \
  -e EXPERIMENT_DIR="${EXPERIMENT_SUBDIR}" \
  -e RUN_STAMP="${RUN_STAMP}" \
  -e JTREG_CONCURRENCY="${JTREG_CONCURRENCY}" \
  jcov-baseline 2>/dev/null || \
docker run --rm \
  -v "${ROOT_DIR}/generated:/data/generated" \
  -v "${ROOT_DIR}/scripts:/data/scripts:ro" \
  -e EXPERIMENT_DIR="${EXPERIMENT_SUBDIR}" \
  -e RUN_STAMP="${RUN_STAMP}" \
  -e JTREG_CONCURRENCY="${JTREG_CONCURRENCY}" \
  witup-llm/evaluator:latest \
  bash /data/scripts/run-jcov-pilot-docker.sh

JCOV_DIR="${PILOT_DIR}/jcov-results"
if [[ -d "${JCOV_DIR}" ]]; then
  log "Métricas JCov:"
  python3 - "${JCOV_DIR}" << 'PYEOF'
import json, os, sys

d = sys.argv[1]
variants = ['baseline', 'wit-context', 'direct-tests']
print(f"\n  {'Variante':<16} {'Branch%':>8} {'Method%':>8} {'Line%':>7} {'Tests':>7} {'Pass%':>6}")
print('  ' + '-'*60)
for v in variants:
    p = os.path.join(d, v, 'summary.json')
    if not os.path.exists(p):
        print(f"  {v:<16} {'—':>8} {'—':>8} {'—':>7} {'—':>7} {'—':>6}")
        continue
    with open(p) as f:
        r = json.load(f)
    print(f"  {v:<16} {r.get('branch_coverage_pct',0):>7}% "
          f"{r.get('method_coverage_pct',0):>7}% "
          f"{r.get('line_coverage_pct',0):>6}% "
          f"{r.get('total_tests',0):>7} "
          f"{r.get('pass_rate_pct',0):>5}%")
PYEOF
fi

# ── Passo 9: Test smells ──────────────────────────────────────────────────────
step "Passo 9/9 — Test smells (wit-context vs direct-tests)"

docker compose -f "${ROOT_DIR}/docker-compose.yml" run --rm \
  -v "${ROOT_DIR}/scripts:/data/scripts:ro" \
  -e EXPERIMENT_DIR="${EXPERIMENT_SUBDIR}" \
  -e RUN_STAMP="${RUN_STAMP}" \
  run-smells 2>/dev/null || \
docker run --rm \
  -v "${ROOT_DIR}/generated:/data/generated" \
  -v "${ROOT_DIR}/scripts:/data/scripts:ro" \
  -e EXPERIMENT_DIR="${EXPERIMENT_SUBDIR}" \
  -e RUN_STAMP="${RUN_STAMP}" \
  witup-llm/evaluator:latest \
  bash /data/scripts/run-smells-docker.sh

SMELLS_DIR="${PILOT_DIR}/smells-results"

# ── Relatório final consolidado ───────────────────────────────────────────────
log ""
step "Relatório Final Consolidado"
python3 - "${PILOT_DIR}" "${JTREG_RESULTS:-}" "${JCOV_DIR:-}" "${SMELLS_DIR:-}" << 'PYEOF'
import json, os, sys

run_dir, jtreg_path, jcov_dir, smells_dir = sys.argv[1:5]
raw_variants = ['baseline', 'wit-context', 'direct-tests']

# ── Carregar dados brutos ────────────────────────────────────────────────────

jtreg_data = {}
if jtreg_path and os.path.exists(jtreg_path):
    with open(jtreg_path) as f:
        jtreg_data = json.load(f)

jcov_data = {}
if jcov_dir and os.path.isdir(jcov_dir):
    for v in raw_variants:
        p = os.path.join(jcov_dir, v, 'summary.json')
        if os.path.exists(p):
            with open(p) as f:
                jcov_data[v] = json.load(f)

smells_data = {}
if smells_dir and os.path.isdir(smells_dir):
    for v in ['wit-context', 'direct-tests']:
        for p in [os.path.join(smells_dir, v, 'summary.json'),
                  os.path.join(smells_dir, f'{v}.json')]:
            if os.path.exists(p):
                with open(p) as f:
                    smells_data[v] = json.load(f)
                break

# ── Construir entrada por variante raw ───────────────────────────────────────

def build_entry(v):
    entry = {}
    if v in jtreg_data and isinstance(jtreg_data[v], dict):
        jt = jtreg_data[v]
        entry['jtreg_pass']  = jt.get('pass', 0)
        entry['jtreg_fail']  = jt.get('fail', 0)
        entry['jtreg_error'] = jt.get('error', 0)
        entry['jtreg_total'] = jt.get('total', entry['jtreg_pass'] + entry['jtreg_fail'] + entry['jtreg_error'])
        entry['jtreg_pass_rate_pct'] = jt.get('pass_rate_pct', 0)
    if v in jcov_data:
        jc = jcov_data[v]
        for k in ('branch_coverage_pct', 'method_coverage_pct', 'line_coverage_pct',
                  'covered_branches', 'total_branches',
                  'covered_methods', 'total_methods',
                  'covered_lines',   'total_lines'):
            entry[k] = jc.get(k, 0)
    if v in smells_data:
        sm = smells_data[v]
        entry['total_files']          = sm.get('total_files', 0)
        entry['files_with_smells']    = sm.get('files_with_smells', 0)
        entry['total_smell_instances']= sm.get('total_smell_instances', 0)
        entry['smell_density'] = round(
            sm.get('total_smell_instances', 0) / max(sm.get('total_files', 1), 1), 2)
        entry['smell_breakdown'] = sm.get('smell_summary', {})
    return entry

raw = {v: build_entry(v) for v in raw_variants}

# ── Cenários cumulativos (merge por soma — baseline nunca re-executa) ─────────
#
#  (1) baseline           → testes originais JDK tier1+tier2
#  (2) baseline + wit     → (1) + testes gerados wit-context
#  (3) baseline + direct  → (1) + testes gerados direct-tests
#
# Para jtreg: somamos pass/fail/error e recalculamos pass_rate.
# Para jcov:  os testes gerados rodam sobre o mesmo código-base;
#             usamos a cobertura da variante gerada como proxy do delta
#             (a cobertura do cenário combinado seria a união, não disponível
#              sem re-execução conjunta — reportamos as duas separadas e o delta).

def merge_jtreg(base, generated):
    """Soma contagens de dois cenários jtreg."""
    m = {}
    for k in ('jtreg_pass', 'jtreg_fail', 'jtreg_error'):
        m[k] = base.get(k, 0) + generated.get(k, 0)
    m['jtreg_total'] = m['jtreg_pass'] + m['jtreg_fail'] + m['jtreg_error']
    m['jtreg_pass_rate_pct'] = round(
        m['jtreg_pass'] / m['jtreg_total'] * 100, 1) if m['jtreg_total'] else 0
    return m

scenarios = {
    '(1) baseline':          raw['baseline'],
    '(2) baseline+wit':      {**raw['wit-context'],   **merge_jtreg(raw['baseline'], raw['wit-context'])},
    '(3) baseline+direct':   {**raw['direct-tests'],  **merge_jtreg(raw['baseline'], raw['direct-tests'])},
}

# ── Salvar relatório completo ────────────────────────────────────────────────
report = {
    "run_dir": run_dir,
    "raw_variants": raw,
    "scenarios": {k: v for k, v in scenarios.items()},
}
final_path = os.path.join(run_dir, 'pilot-final-report.json')
with open(final_path, 'w') as f:
    json.dump(report, f, indent=2)

# ── Tabela comparativa dos 3 cenários ────────────────────────────────────────
W = 22
print(f"\n{'='*80}")
print(f"  COMPARATIVO — {os.path.basename(run_dir)}")
print(f"{'='*80}")

scenario_labels = list(scenarios.keys())
header = f"  {'Métrica':<28}" + "".join(f"{s:>{W}}" for s in scenario_labels)
print(f"\n{header}")
print(f"  {'-'*(28 + W*len(scenario_labels))}")

jtreg_metrics = [
    ('jtreg_total',          'testes executados'),
    ('jtreg_pass',           '  └ passou'),
    ('jtreg_fail',           '  └ falhou'),
    ('jtreg_error',          '  └ erro'),
    ('jtreg_pass_rate_pct',  'pass rate (%)'),
]
coverage_metrics = [
    ('branch_coverage_pct',  'branch coverage (%)'),
    ('method_coverage_pct',  'method coverage (%)'),
    ('line_coverage_pct',    'line coverage (%)'),
]
smell_metrics = [
    ('total_files',          'arquivos gerados'),
    ('files_with_smells',    'arquivos c/ smells'),
    ('smell_density',        'smell density (/arq)'),
]

def print_section(title, metrics_list):
    print(f"\n  ── {title} {'─'*(74-len(title))}")
    for key, label in metrics_list:
        vals = [scenarios[s].get(key, '—') for s in scenario_labels]
        row = f"  {label:<28}" + "".join(f"{str(v):>{W}}" for v in vals)
        print(row)

print_section("jtreg (merge baseline + gerados)", jtreg_metrics)
print_section("JCov — variantes geradas (sem re-exec baseline)", coverage_metrics)
print_section("Test Smells — apenas testes gerados", smell_metrics)

# Smells por tipo
print(f"\n  ── Smells por tipo {'─'*57}")
wc_smells = raw['wit-context'].get('smell_breakdown', {})
dt_smells = raw['direct-tests'].get('smell_breakdown', {})
all_smells = sorted(set(list(wc_smells.keys()) + list(dt_smells.keys())))
if all_smells:
    print(f"  {'Tipo':<30} {'wit-context':>12} {'direct-tests':>14}")
    print(f"  {'-'*58}")
    for smell in all_smells:
        print(f"  {smell:<30} {wc_smells.get(smell,0):>12} {dt_smells.get(smell,0):>14}")

print(f"\n  Relatório salvo em: {final_path}")
PYEOF

log ""
log "Pipeline concluído. Run: ${RUN_STAMP}"
log "  Diretório  : ${PILOT_DIR}"
log "  jtreg      : ${PILOT_DIR}/jtreg-results.json"
log "  JCov       : ${PILOT_DIR}/jcov-results/"
log "  Smells     : ${PILOT_DIR}/smells-results/"
log "  Relatório  : ${PILOT_DIR}/pilot-final-report.json"

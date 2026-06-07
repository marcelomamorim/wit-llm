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
JTREG_TIER_FILTER="${JTREG_TIER_FILTER:-tier1|tier2}"
# BASELINE_RESULTS_JSON: caminho para um jtreg-results.json anterior.
# Se definido, a entrada "baseline" é copiada de lá e o jtreg NÃO re-executa o baseline.
BASELINE_RESULTS_JSON="${BASELINE_RESULTS_JSON:-}"

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

# Normaliza e corrige o cabeçalho jtreg de cada arquivo gerado:
#  - Extrai anotações @test/@summary/@run/@modules do bloco /* */ (uma ou várias linhas)
#    ou de linhas @xxx soltas no topo do arquivo
#  - Sempre re-emite o cabeçalho em formato multi-linha correto
#  - Injeta @modules java.base/<pkg> para todo pacote interno importado
#    (com.sun.*, sun.*, jdk.internal.*)
#  - Corrige @run main ClassName → nome real da classe
#  - Remove package declaration (inválido em testes jtreg standalone)
#
# IMPORTANTE: processa TODOS os arquivos, inclusive os que já têm /* @test */,
# pois o LLM pode ter gerado o bloco em uma linha só sem @modules.
fix_jtreg_format() {
  local dir="$1"
  find "${dir}" -name "*.java" | while read -r f; do
    local classname
    classname=$(basename "${f}" .java)
    python3 - "${f}" "${classname}" << 'PYEOF'
import sys, re

path, classname = sys.argv[1], sys.argv[2]
with open(path, encoding='utf-8', errors='replace') as fh:
    content = fh.read()

# ── 1. Coletar imports para detectar pacotes internos ─────────────────────────
INTERNAL_PREFIXES = ('com.sun.', 'sun.', 'jdk.internal.')
import_pkgs = set()
for line in content.split('\n'):
    m = re.match(r'^\s*import\s+([\w.]+)\.\w+\s*;', line)
    if m:
        import_pkgs.add(m.group(1))

internal_mods = sorted(
    f'java.base/{pkg}'
    for pkg in import_pkgs
    if any(pkg.startswith(p) for p in INTERNAL_PREFIXES)
)

# ── 2. Extrair anotações jtreg existentes (de bloco /* */ ou linhas @xxx) ────
#   Suporte a bloco em uma linha: /* @test @summary ... @run main X */
#   e a bloco multi-linha ou anotações soltas no topo.
existing = {'test': True, 'summary': None, 'run': None, 'modules': [], 'extra': []}

# Tentar extrair de bloco /* ... */ no início do arquivo
block_match = re.match(r'^\s*/\*(.*?)\*/', content, re.DOTALL)
if block_match:
    block_text = block_match.group(1)
    # Separar por tokens @xxx (cada @ inicia uma diretiva)
    tokens = re.split(r'(?=@\w)', block_text)
    for tok in tokens:
        tok = tok.strip().lstrip('* ').rstrip('* ')
        if not tok:
            continue
        m = re.match(r'@(\w+)\s*(.*)', tok, re.DOTALL)
        if not m:
            continue
        key, val = m.group(1).lower(), m.group(2).strip().replace('\n', ' ')
        val = re.sub(r'\s+', ' ', val)
        if key == 'summary':
            existing['summary'] = val
        elif key == 'run':
            existing['run'] = val
        elif key == 'modules':
            existing['modules'].append(val)
        elif key not in ('test',):
            existing['extra'].append(f'@{m.group(1)} {val}'.strip())
    # Remover o bloco do conteúdo
    content_body = content[block_match.end():]
else:
    # Sem bloco: verificar anotações soltas no início
    content_body = content
    lines = content.split('\n')
    body_start = 0
    for i, line in enumerate(lines):
        stripped = line.strip()
        m = re.match(r'^@(test|summary|run|modules|bug|library|key|requires|compile|ignore)\b(.*)', stripped, re.IGNORECASE)
        if m:
            key, val = m.group(1).lower(), m.group(2).strip()
            if key == 'summary': existing['summary'] = val
            elif key == 'run':   existing['run'] = val
            elif key == 'modules': existing['modules'].append(val)
            body_start = i + 1
        elif stripped == '' and body_start == i:
            body_start = i + 1
        else:
            break
    content_body = '\n'.join(lines[body_start:])

# ── 3. Remover package declaration (inválido em standalone jtreg) ─────────────
content_body = re.sub(r'^\s*package\s+[\w.]+\s*;\s*\n?', '', content_body, flags=re.MULTILINE)

# ── 4. Corrigir @run main: substituir ClassName genérico pelo nome real ────────
run_val = existing['run'] or f'main {classname}'
run_val = re.sub(r'(?i)\bmain\s+className\b', f'main {classname}', run_val)
run_val = re.sub(r'(?i)^main\s*$', f'main {classname}', run_val)
if not run_val.strip().startswith('main'):
    run_val = f'main {classname}'
# Garantir que o classname no @run bate com o nome do arquivo
run_val = re.sub(r'main\s+\S+', f'main {classname}', run_val)

# ── 5. Normalizar @summary ─────────────────────────────────────────────────────
summary_val = existing['summary'] or f'Generated test for {classname}'
summary_val = re.sub(r'^\("|"\)$', '', summary_val)  # remover parênteses/aspas extras

# ── 6. Construir @modules finais ──────────────────────────────────────────────
#   Partir dos @modules já declarados + adicionar os faltantes dos imports
declared_mods = set()
for m_val in existing['modules']:
    for part in m_val.split():
        declared_mods.add(part.strip())
# Adicionar módulos internos detectados via import
all_mods = sorted(declared_mods | set(internal_mods))
# Remover entrada genérica "java.base" sem subpacote se já há java.base/pkg
if 'java.base' in all_mods and any(m.startswith('java.base/') for m in all_mods):
    all_mods = [m for m in all_mods if m != 'java.base']

# ── 7. Montar cabeçalho final ─────────────────────────────────────────────────
header_lines = ['/*', ' * @test', f' * @summary {summary_val}']
for mod in all_mods:
    header_lines.append(f' * @modules {mod}')
for extra in existing['extra']:
    header_lines.append(f' * {extra}')
header_lines.append(f' * @run {run_val}')
header_lines.append(' */')

header = '\n'.join(header_lines) + '\n'
new_content = header + content_body.lstrip('\n')

with open(path, 'w', encoding='utf-8') as fh:
    fh.write(new_content)
PYEOF
  done
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

  # Corrigir formato jtreg dos testes gerados
  log "  corrigindo formato jtreg dos testes gerados..."
  fix_jtreg_format "${generated_dir}"

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

  # Baseline: todos os testes originais do JDK no diretório dos módulos amostrados.
  # Nota: os testes de com/sun/crypto/provider/Cipher/AES não usam @key tier1/tier2,
  # portanto o filtro de tier NÃO é aplicado ao baseline (seria "no tests selected").
  local tier_filter="${JTREG_TIER_FILTER:-}"
  if [[ -n "${tier_filter}" ]]; then
    log "iniciando jtreg baseline (com/sun/crypto/provider/Cipher/AES, tier=${tier_filter}, conc=1)..."
  else
    log "iniciando jtreg baseline (com/sun/crypto/provider/Cipher/AES, sem filtro de tier, conc=1)..."
  fi

  set +e
  local tier_args=()
  [[ -n "${tier_filter}" ]] && tier_args=( -k:"${tier_filter}" )
  "${JTREG}" \
    -jdk:"${TEST_JDK}" \
    -w:"${work_dir}" \
    -r:"${report_dir}" \
    -conc:1 \
    -timeout:"${TIMEOUT_FACTOR}" \
    -verbose:fail \
    "${tier_args[@]}" \
    "${test_dir}/jdk/com/sun/crypto/provider/Cipher/AES" 2>&1 | tee "${log_file}"
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
log "JTREG_TIER_FILTER=${JTREG_TIER_FILTER}"
log "BASELINE_RESULTS_JSON=${BASELINE_RESULTS_JSON:-<não definido>}"

mkdir -p "${RUN_DIR}"

declare -a results=()

results+=( "$(run_jtreg_generated "direct-tests")" )
results+=( "$(run_jtreg_generated "wit-context")" )

if [[ "${RUN_BASELINE}" =~ ^(sim|1|yes|true)$ ]]; then
  if [[ -n "${BASELINE_RESULTS_JSON}" && -f "${BASELINE_RESULTS_JSON}" ]]; then
    # Reutilizar baseline de run anterior — sem re-execução
    log "=== variante: baseline (reutilizando ${BASELINE_RESULTS_JSON}) ==="
    baseline_entry=$(python3 -c "
import json, sys
with open('${BASELINE_RESULTS_JSON}') as f:
    d = json.load(f)
b = d.get('baseline')
if b:
    b['reused_from'] = '${BASELINE_RESULTS_JSON}'
    print('\"baseline\": ' + json.dumps(b))
" 2>/dev/null || echo "")
    if [[ -n "${baseline_entry}" ]]; then
      results+=( "${baseline_entry}" )
      log "  baseline reutilizado com sucesso."
    else
      log "  AVISO: entrada 'baseline' não encontrada em ${BASELINE_RESULTS_JSON} — re-executando"
      results+=( "$(run_jtreg_baseline_tier1_tier2)" )
    fi
  else
    results+=( "$(run_jtreg_baseline_tier1_tier2)" )
  fi
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

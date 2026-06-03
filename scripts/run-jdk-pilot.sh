#!/usr/bin/env bash
# run-jdk-pilot.sh  (host-side)
#
# Piloto ponta-a-ponta do experimento JDK com subconjunto reduzido.
#
# Fluxo:
#   1. Clona OpenJDK @ da75f3c4 (se ainda não clonado)
#   2. Roda witup preparar-estudo-jdk-global --method-count N
#      → gera preparation JSON + analysis com N métodos amostrados
#   3. Filtra os JSONL de respostas para conter apenas os custom_ids do passo 2
#   4. Roda witup avaliar-estudo-jdk-global
#      → materializa 3 variantes: baseline / wit-context / direct-tests
#   5. Constrói imagem Docker evaluator (JDK compilado + jtreg + JCov)
#   6. Roda jtreg nas 3 variantes via run-jtreg-docker.sh
#   7. Exibe tabela comparativa final
#
# Variáveis obrigatórias:
#   DIRECT_JSONL   : caminho para o JSONL de respostas direct-tests
#   WIT_JSONL      : caminho para o JSONL de respostas wit-context
#
# Variáveis opcionais:
#   PILOT_METHODS     : número de métodos para o piloto (default: 10)
#   RUN_BASELINE      : "sim" para rodar jtreg tier1+tier2 no baseline (default: sim)
#   JTREG_CONCURRENCY : paralelismo jtreg (default: 2)
#   FORCE_RECLONE     : "sim" para re-clonar mesmo se já existir
#   SKIP_BUILD_IMAGE  : "sim" para pular o docker build (imagem já existe)
#
# Uso:
#   DIRECT_JSONL=~/Downloads/batch_direct_output.jsonl \
#   WIT_JSONL=~/Downloads/batch_wit_output.jsonl \
#     ./scripts/run-jdk-pilot.sh

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
DIRECT_JSONL="${DIRECT_JSONL:-}"
WIT_JSONL="${WIT_JSONL:-}"
PILOT_METHODS="${PILOT_METHODS:-10}"
RUN_BASELINE="${RUN_BASELINE:-sim}"
JTREG_CONCURRENCY="${JTREG_CONCURRENCY:-2}"
FORCE_RECLONE="${FORCE_RECLONE:-nao}"
SKIP_BUILD_IMAGE="${SKIP_BUILD_IMAGE:-nao}"

JDK_COMMIT="da75f3c4ad5bdf25167a3ed80e51f567ab3dbd01"
JDK_ROOT="$(cd "${ROOT_DIR}" && pwd)/generated/repos/jdk"
WIT_ANALYSIS="${ROOT_DIR}/generated/wit-data/jdk/wit_filtered.json"

# RUN_STAMP e PILOT_DIR precisam estar dentro de generated/experiments/
# para o volume ./generated:/data/generated funcionar no run-jtreg
EXPERIMENT_SUBDIR="jdk-pilot"
RUN_STAMP="${RUN_STAMP:-pilot-$(date -u +%Y%m%dT%H%M%SZ)}"
PILOT_DIR="${ROOT_DIR}/generated/experiments/${EXPERIMENT_SUBDIR}/${RUN_STAMP}"
RUNTIME_CONFIG="${ROOT_DIR}/generated/configs/jdk-pilot.runtime.json"
WITUP="${ROOT_DIR}/bin/witup"

log()  { printf '\n[jdk-pilot] %s\n' "$*"; }
err()  { printf '[jdk-pilot] ERRO: %s\n' "$*" >&2; exit 1; }
step() { printf '\n[jdk-pilot] ══ %s ══\n' "$*"; }

# ── Validações ────────────────────────────────────────────────────────────────
[[ -n "${DIRECT_JSONL}" ]] || err "DIRECT_JSONL é obrigatório."
[[ -f "${DIRECT_JSONL}" ]] || err "arquivo não encontrado: ${DIRECT_JSONL}"
[[ -n "${WIT_JSONL}" ]]    || err "WIT_JSONL é obrigatório."
[[ -f "${WIT_JSONL}" ]]    || err "arquivo não encontrado: ${WIT_JSONL}"

mkdir -p "${PILOT_DIR}"

# ── Compilar witup ────────────────────────────────────────────────────────────
if [[ ! -x "${WITUP}" ]]; then
  step "Compilando witup CLI..."
  mkdir -p "${ROOT_DIR}/bin"
  (cd "${ROOT_DIR}" && go build -o "${WITUP}" ./cmd/witup/)
fi

# ── Gerar runtime.json mínimo ─────────────────────────────────────────────────
step "Gerando runtime.json para o piloto..."
mkdir -p "$(dirname "${RUNTIME_CONFIG}")"
python3 - "${RUNTIME_CONFIG}" << 'PYEOF'
import json, sys
path = sys.argv[1]
config = {
    "version": "1",
    "models": {
        "openai_main": {
            "provider": "openai_compatible",
            "model": "gpt-4.1-nano-2025-04-14",
            "base_url": "https://api.openai.com/v1",
            "api_key_env": "OPENAI_API_KEY",
            "execution_backend": "batch",
            "endpoint": "/v1/responses"
        }
    }
}
with open(path, 'w') as f:
    json.dump(config, f, indent=2)
print(f"runtime.json escrito em {path}")
PYEOF

# ── Passo 1: Clonar JDK ───────────────────────────────────────────────────────
step "Passo 1/6 — Clonar OpenJDK @ ${JDK_COMMIT:0:8}"
if [[ "${FORCE_RECLONE}" == "sim" ]]; then
  rm -rf "${JDK_ROOT}"
fi
if [[ ! -d "${JDK_ROOT}/.git" ]]; then
  log "Clonando openjdk/jdk (pode demorar ~10min)..."
  mkdir -p "$(dirname "${JDK_ROOT}")"
  git clone --filter=blob:none https://github.com/openjdk/jdk.git "${JDK_ROOT}"
fi
log "Fazendo checkout do commit WIT..."
git -C "${JDK_ROOT}" fetch --quiet origin
git -C "${JDK_ROOT}" checkout --quiet "${JDK_COMMIT}"
log "JDK pronto em ${JDK_ROOT}"

# ── Passo 2: Preparar amostra de N métodos ────────────────────────────────────
step "Passo 2/6 — Preparar amostra de ${PILOT_METHODS} métodos"

# wit_filtered.json: extrair do Docker se não existir localmente
if [[ ! -f "${WIT_ANALYSIS}" ]]; then
  log "Extraindo wit_filtered.json do jdk da imagem article-evaluator..."
  mkdir -p "$(dirname "${WIT_ANALYSIS}")"
  docker run --rm witup-llm/article-evaluator:latest \
    cat /opt/wit-data/jdk/wit_filtered.json > "${WIT_ANALYSIS}"
  log "wit_filtered.json extraído ($(wc -l < "${WIT_ANALYSIS}") linhas)"
fi

REQUESTS_JSONL="${PILOT_DIR}/requests_pilot.jsonl"
"${WITUP}" preparar-estudo-jdk-global \
  --config   "${RUNTIME_CONFIG}" \
  --jdk-root "${JDK_ROOT}" \
  --wit-analysis "${WIT_ANALYSIS}" \
  --output-dir   "${PILOT_DIR}" \
  --requests     "${REQUESTS_JSONL}" \
  --method-count "${PILOT_METHODS}" \
  --workers 4
log "Preparação concluída — preparation_jdk_global_impact.json gerado"

# ── Passo 3: Filtrar JSONLs para os custom_ids do piloto ──────────────────────
step "Passo 3/6 — Filtrar JSONLs (${PILOT_METHODS} métodos)"

DIRECT_FILTERED="${PILOT_DIR}/responses_direct_filtered.jsonl"
WIT_FILTERED="${PILOT_DIR}/responses_wit_filtered.jsonl"
COMBINED_FILTERED="${PILOT_DIR}/responses_combined_filtered.jsonl"

python3 - \
  "${PILOT_DIR}/preparation_jdk_global_impact.json" \
  "${DIRECT_JSONL}" \
  "${WIT_JSONL}" \
  "${DIRECT_FILTERED}" \
  "${WIT_FILTERED}" \
  "${COMBINED_FILTERED}" << 'PYEOF'
import json, sys

prep_path, direct_in, wit_in, direct_out, wit_out, combined_out = sys.argv[1:]

# Extrair custom_ids esperados a partir dos métodos do preparation
with open(prep_path) as f:
    prep = json.load(f)

# Os custom_ids no JSONL têm o formato:
#   jdk/{cenario}/{class-slug}/{class-slug}-{method-slug}-{line}
# O preparation tem a lista de métodos — extraímos os slugs esperados
# comparando com os custom_ids presentes nos JSONLs.

# Estratégia: ler todos os custom_ids do requests_jsonl gerado
# (preparar gera o JSONL de requests — os IDs estão lá)
import os
requests_path = os.path.join(os.path.dirname(prep_path), 'requests_pilot.jsonl')

expected_method_ids = set()
if os.path.exists(requests_path):
    with open(requests_path) as f:
        for line in f:
            line = line.strip()
            if not line: continue
            req = json.loads(line)
            cid = req.get('custom_id', '')
            # custom_id do request: jdk/{cenario}/{...} — extrair a parte
            # comum (sem cenário) para filtrar nos dois JSONLs
            parts = cid.split('/')
            if len(parts) >= 4:
                # chave = tudo a partir do índice 2 (sem "jdk/{cenario}/")
                expected_method_ids.add('/'.join(parts[2:]))

print(f"IDs esperados extraídos do requests: {len(expected_method_ids)}")

def filter_jsonl(src, dst, scenario):
    kept = 0
    with open(src) as fin, open(dst, 'w') as fout:
        for line in fin:
            line = line.strip()
            if not line: continue
            obj = json.loads(line)
            cid = obj.get('custom_id', '')
            parts = cid.split('/')
            if len(parts) >= 4:
                key = '/'.join(parts[2:])
                if key in expected_method_ids:
                    fout.write(json.dumps(obj) + '\n')
                    kept += 1
    print(f"  {scenario}: {kept} itens filtrados → {dst}")

filter_jsonl(direct_in, direct_out, 'direct-tests')
filter_jsonl(wit_in,    wit_out,    'wit-context')

# Combinar em um único JSONL (avaliar precisa dos dois cenários juntos)
with open(combined_out, 'w') as fout:
    for src in [direct_out, wit_out]:
        with open(src) as fin:
            for line in fin:
                if line.strip():
                    fout.write(line)
PYEOF

# Verificar se filtrou algo
direct_count=$(wc -l < "${DIRECT_FILTERED}" | tr -d ' ')
wit_count=$(wc -l < "${WIT_FILTERED}" | tr -d ' ')
combined_count=$(wc -l < "${COMBINED_FILTERED}" | tr -d ' ')
log "Filtrado: direct=${direct_count} wit=${wit_count} combined=${combined_count} linhas"
[[ "${combined_count}" -gt 0 ]] || \
  err "JSONL combinado está vazio — custom_ids não batem com os do piloto"

# ── Passo 4: Avaliar (materializar variantes) ─────────────────────────────────
step "Passo 4/6 — Materializar variantes (baseline / wit-context / direct-tests)"

# Avaliar com JSONL combinado (direct + wit) em uma única chamada
# IMPORTANTE: o avaliar precisa dos dois cenários juntos para materializar
# todas as variantes corretamente. Chamadas separadas sobrescrevem as anteriores.
"${WITUP}" avaliar-estudo-jdk-global \
  --config         "${RUNTIME_CONFIG}" \
  --jdk-root       "${JDK_ROOT}" \
  --run-dir        "${PILOT_DIR}" \
  --responses      "${COMBINED_FILTERED}" \
  --workers 3

# Contar testes materializados
log "Variantes materializadas:"
for variant in baseline wit-context direct-tests; do
  dir="${PILOT_DIR}/variants/${variant}/test/jdk/witup/generated"
  if [[ -d "${dir}" ]]; then
    count=$(find "${dir}" -name "*.java" | wc -l | tr -d ' ')
    log "  ${variant}: ${count} arquivos .java"
  else
    log "  ${variant}: (sem testes gerados — ok para baseline)"
  fi
done

# ── Passo 5: Build imagem Docker evaluator ────────────────────────────────────
step "Passo 5/6 — Build imagem witup-llm/evaluator (JDK + jtreg + JCov)"
if [[ "${SKIP_BUILD_IMAGE}" == "sim" ]]; then
  log "SKIP_BUILD_IMAGE=sim — pulando build"
else
  log "Build da imagem evaluator (~60min na primeira vez, usa cache depois)..."
  docker compose -f "${ROOT_DIR}/docker-compose.yml" build evaluator
fi

# ── Passo 6: Rodar jtreg nas 3 variantes ─────────────────────────────────────
step "Passo 6/6 — Rodar jtreg (baseline tier1+tier2 + testes gerados)"
log "RUN_BASELINE=${RUN_BASELINE} | JTREG_CONCURRENCY=${JTREG_CONCURRENCY}"

docker compose -f "${ROOT_DIR}/docker-compose.yml" run --rm \
  -e EXPERIMENT_DIR="${EXPERIMENT_SUBDIR}" \
  -e RUN_STAMP="${RUN_STAMP}" \
  -e RUN_BASELINE="${RUN_BASELINE}" \
  -e JTREG_CONCURRENCY="${JTREG_CONCURRENCY}" \
  run-jtreg

# ── Resultado ─────────────────────────────────────────────────────────────────
RESULTS_FILE="${PILOT_DIR}/jtreg-results.json"
if [[ -f "${RESULTS_FILE}" ]]; then
  step "Resultado final"
  python3 - "${RESULTS_FILE}" << 'PYEOF'
import json, sys
with open(sys.argv[1]) as f:
    results = json.load(f)

print(f"\n{'Variante':<20} {'Pass':>6} {'Fail':>6} {'Error':>7} {'Total':>7} {'Pass%':>7}")
print('-'*60)
for variant, data in results.items():
    if isinstance(data, dict):
        p  = data.get('pass', 0)
        fa = data.get('fail', 0)
        e  = data.get('error', 0)
        t  = data.get('total', p+fa+e)
        r  = data.get('pass_rate_pct', round(p/t*100, 2) if t else 0)
        st = data.get('status', 'ok')
        flag = '' if st not in ('skipped','error') else f' [{st}]'
        print(f"{variant:<20} {p:>6} {fa:>6} {e:>7} {t:>7} {r:>6.1f}%{flag}")
print()
PYEOF
fi

log "Piloto concluído. Resultados em: ${PILOT_DIR}"

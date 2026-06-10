#!/usr/bin/env bash
# run-jdk-generate-all.sh
#
# Gera testes para TODOS os métodos do wit_filtered.json (jdk-pilot),
# submetendo sub-lotes sequenciais de N métodos por batch para não
# ultrapassar o limite de 20M tokens enfileirados da OpenAI Batch API.
#
# Fluxo:
#   1. Compilar witup + gerar runtime.json
#   2. Preparar requests_pilot.jsonl completo
#   3. Separar por variante (wit-context / direct-tests)
#   4. Para cada variante, dividir em chunks de CHUNK_SIZE requests
#   5. Submeter cada chunk → polling → download, sequencialmente
#   6. Mesclar todas as respostas em responses_openai_batch_generation.jsonl
#   7. Materializar variantes
#   8. Zipar variants/ → variants-generated.zip
#
# Variáveis de ambiente:
#   PILOT_METHODS      : número de métodos (default: 9999 = todos)
#   CHUNK_SIZE         : requests por sub-lote (default: 2500)
#   RUN_STAMP          : timestamp do run (gerado automaticamente)
#   POLL_INTERVAL      : segundos entre consultas (default: 60)
#   POLL_TIMEOUT_HOURS : horas máximas de espera por batch (default: 24)
#   MODEL_KEY          : chave do modelo no config (default: openai_main)

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

PILOT_METHODS="${PILOT_METHODS:-9999}"
CHUNK_SIZE="${CHUNK_SIZE:-2500}"
POLL_INTERVAL="${POLL_INTERVAL:-60}"
POLL_TIMEOUT_HOURS="${POLL_TIMEOUT_HOURS:-24}"
MODEL_KEY="${MODEL_KEY:-openai_main}"
OPENAI_MODEL="${OPENAI_MODEL:-gpt-4.1-nano-2025-04-14}"

EXPERIMENT_SUBDIR="jdk-pilot"
RUN_STAMP="${RUN_STAMP:-pilot-$(date -u +%Y%m%dT%H%M%SZ)}"
PILOT_DIR="${ROOT_DIR}/generated/experiments/${EXPERIMENT_SUBDIR}/${RUN_STAMP}"
RUNTIME_CONFIG="${ROOT_DIR}/generated/configs/jdk-pilot.runtime.json"
JDK_ROOT="${ROOT_DIR}/generated/repos/jdk"
WIT_ANALYSIS="${ROOT_DIR}/generated/wit-data/jdk/wit_filtered.json"
WITUP="${ROOT_DIR}/bin/witup"

log()  { printf '\n[jdk-generate-all] %s\n' "$*"; }
err()  { printf '[jdk-generate-all] ERRO: %s\n' "$*" >&2; exit 1; }
step() { printf '\n[jdk-generate-all] ══════════════════════════════\n[jdk-generate-all] %s\n[jdk-generate-all] ══════════════════════════════\n' "$*"; }

mkdir -p "${PILOT_DIR}"

log "RUN_STAMP  : ${RUN_STAMP}"
log "PILOT_DIR  : ${PILOT_DIR}"
log "MÉTODOS    : ${PILOT_METHODS}"
log "CHUNK_SIZE : ${CHUNK_SIZE}"

# ── Passo 1: Compilar witup ───────────────────────────────────────────────────
if [[ ! -x "${WITUP}" ]]; then
  log "Compilando witup CLI..."
  mkdir -p "${ROOT_DIR}/bin"
  (cd "${ROOT_DIR}" && go build -o "${WITUP}" ./cmd/witup/)
fi

# ── Gerar runtime.json ────────────────────────────────────────────────────────
step "Passo 1/8 — Gerando runtime.json"
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
            "max_output_tokens": 1024,
            "timeout_seconds": 120,
            "max_retries": 3
        }
    }
}
with open(path, 'w') as f:
    json.dump(config, f, indent=2)
print(f"runtime.json gerado: {path}")
PYEOF

# ── Passo 2: Preparar requests completos ─────────────────────────────────────
step "Passo 2/8 — Preparar amostra de ${PILOT_METHODS} métodos"

REQUESTS_JSONL="${PILOT_DIR}/requests_pilot.jsonl"
"${WITUP}" preparar-estudo-jdk-global \
  --config           "${RUNTIME_CONFIG}" \
  --generation-model "${MODEL_KEY}" \
  --jdk-root         "${JDK_ROOT}" \
  --wit-analysis     "${WIT_ANALYSIS}" \
  --output-dir       "${PILOT_DIR}" \
  --requests         "${REQUESTS_JSONL}" \
  --method-count     "${PILOT_METHODS}" \
  --workers 4

REQ_COUNT=$(wc -l < "${REQUESTS_JSONL}" | tr -d ' ')
log "Requests JSONL: ${REQUESTS_JSONL} (${REQ_COUNT} linhas)"

# ── Passo 3: Separar por variante ────────────────────────────────────────────
step "Passo 3/8 — Separar requests por variante"

REQUESTS_WIT="${PILOT_DIR}/requests_wit-context.jsonl"
REQUESTS_DIRECT="${PILOT_DIR}/requests_direct-tests.jsonl"

python3 - "${REQUESTS_JSONL}" "${REQUESTS_WIT}" "${REQUESTS_DIRECT}" << 'PYEOF'
import json, sys
src, out_wit, out_direct = sys.argv[1], sys.argv[2], sys.argv[3]
wit_count = direct_count = 0
with open(src) as f, open(out_wit, 'w') as fw, open(out_direct, 'w') as fd:
    for line in f:
        line = line.strip()
        if not line:
            continue
        obj = json.loads(line)
        cid = obj.get('custom_id', '')
        if '/wit-context/' in cid:
            fw.write(line + '\n')
            wit_count += 1
        elif '/direct-tests/' in cid:
            fd.write(line + '\n')
            direct_count += 1
        else:
            print(f"AVISO: custom_id sem variante conhecida: {cid}", file=sys.stderr)
print(f"wit-context  : {wit_count} requests -> {out_wit}")
print(f"direct-tests : {direct_count} requests -> {out_direct}")
PYEOF

WIT_COUNT=$(wc -l < "${REQUESTS_WIT}" | tr -d ' ')
DIRECT_COUNT=$(wc -l < "${REQUESTS_DIRECT}" | tr -d ' ')
log "wit-context  : ${WIT_COUNT} requests"
log "direct-tests : ${DIRECT_COUNT} requests"

# ── Passo 4: Dividir em chunks ────────────────────────────────────────────────
step "Passo 4/8 — Dividir em chunks de ${CHUNK_SIZE} requests"

CHUNKS_DIR="${PILOT_DIR}/chunks"
mkdir -p "${CHUNKS_DIR}"

python3 - "${REQUESTS_WIT}" "${REQUESTS_DIRECT}" "${CHUNKS_DIR}" "${CHUNK_SIZE}" << 'PYEOF'
import json, sys, os, math

wit_file, direct_file, chunks_dir, chunk_size = sys.argv[1], sys.argv[2], sys.argv[3], int(sys.argv[4])

def split_file(src, label):
    with open(src) as f:
        lines = [l.strip() for l in f if l.strip()]
    n_chunks = math.ceil(len(lines) / chunk_size)
    for i in range(n_chunks):
        chunk = lines[i * chunk_size:(i + 1) * chunk_size]
        out = os.path.join(chunks_dir, f"requests_{label}_chunk{i+1:03d}.jsonl")
        with open(out, 'w') as fw:
            fw.write('\n'.join(chunk) + '\n')
        print(f"  {label} chunk {i+1}/{n_chunks}: {len(chunk)} requests -> {out}")
    return n_chunks

n_wit    = split_file(wit_file,    "wit-context")
n_direct = split_file(direct_file, "direct-tests")
print(f"Total chunks: wit-context={n_wit}, direct-tests={n_direct}")
PYEOF

log "Chunks gerados em: ${CHUNKS_DIR}"
ls "${CHUNKS_DIR}" | head -20

# ── Função: submeter um chunk + poll + retornar arquivo de respostas ──────────
submeter_chunk() {
  local label="$1"       # ex: wit-context_chunk001
  local requests_file="$2"
  local responses_out="$3"

  local batch_meta="${PILOT_DIR}/batch_meta_${label}.json"
  local batch_id_file="${PILOT_DIR}/batch_id_${label}.txt"
  local poll_dir="${PILOT_DIR}/poll_${label}"

  local n_req
  n_req=$(wc -l < "${requests_file}" | tr -d ' ')
  log "→ Submetendo chunk '${label}' (${n_req} requests)..."

  "${WITUP}" submeter-openai-batch \
    --config   "${RUNTIME_CONFIG}" \
    --model    "${MODEL_KEY}" \
    --requests "${requests_file}" \
    --output   "${batch_meta}"

  local batch_id
  batch_id=$(python3 -c "import json; d=json.load(open('${batch_meta}')); print(d.get('batch_id',''))")
  echo "${batch_id}" > "${batch_id_file}"
  log "  Batch ID: ${batch_id}"

  mkdir -p "${poll_dir}"
  BATCH_ID="${batch_id}" \
  RUNTIME_CONFIG="${RUNTIME_CONFIG}" \
  OUTPUT_DIR="${poll_dir}" \
  MODEL_KEY="${MODEL_KEY}" \
  POLL_INTERVAL="${POLL_INTERVAL}" \
  POLL_TIMEOUT_HOURS="${POLL_TIMEOUT_HOURS}" \
  WITUP="${WITUP}" \
    "${ROOT_DIR}/scripts/poll-openai-batch.sh"

  local downloaded="${poll_dir}/responses_openai_batch_generation.jsonl"
  [[ -f "${downloaded}" ]] || err "respostas não encontradas: ${downloaded}"
  cp "${downloaded}" "${responses_out}"
  log "  ✓ ${label}: $(wc -l < "${responses_out}" | tr -d ' ') respostas salvas"
}

# ── Passo 5: Submeter todos os chunks sequencialmente ────────────────────────
step "Passo 5/8 — Submeter chunks sequencialmente"

RESPONSES_DIR="${PILOT_DIR}/responses_chunks"
mkdir -p "${RESPONSES_DIR}"

for chunk_file in $(ls "${CHUNKS_DIR}"/requests_*.jsonl | sort); do
  basename_chunk=$(basename "${chunk_file}" .jsonl)          # requests_wit-context_chunk001
  label="${basename_chunk#requests_}"                        # wit-context_chunk001
  responses_out="${RESPONSES_DIR}/responses_${label}.jsonl"

  submeter_chunk "${label}" "${chunk_file}" "${responses_out}"
done

log "Todos os chunks processados."

# ── Passo 6: Mesclar todas as respostas ──────────────────────────────────────
step "Passo 6/8 — Mesclar respostas"

RESPONSES_JSONL="${PILOT_DIR}/responses_openai_batch_generation.jsonl"
cat "${RESPONSES_DIR}"/responses_*.jsonl > "${RESPONSES_JSONL}"
RESP_COUNT=$(wc -l < "${RESPONSES_JSONL}" | tr -d ' ')
log "Respostas mescladas: ${RESPONSES_JSONL} (${RESP_COUNT} linhas)"

# ── Passo 7: Materializar variantes ──────────────────────────────────────────
step "Passo 7/8 — Materializar variantes"

"${WITUP}" avaliar-estudo-jdk-global \
  --config           "${RUNTIME_CONFIG}" \
  --generation-model "${MODEL_KEY}" \
  --jdk-root         "${JDK_ROOT}" \
  --run-dir          "${PILOT_DIR}" \
  --responses        "${RESPONSES_JSONL}"

log "Variantes materializadas:"
for variant in wit-context direct-tests; do
  dir="${PILOT_DIR}/variants/${variant}/test/jdk/witup/generated"
  if [[ -d "${dir}" ]]; then
    count=$(find "${dir}" -name "*.java" | wc -l | tr -d ' ')
    log "  ${variant}: ${count} arquivo(s) .java"
  else
    log "  ${variant}: (sem diretório de testes gerados)"
  fi
done

# ── Passo 8: Zipar variants/ ──────────────────────────────────────────────────
step "Passo 8/8 — Zipar variants/"
ZIP_FILE="${PILOT_DIR}/variants-generated.zip"
(cd "${PILOT_DIR}" && zip -r "${ZIP_FILE}" variants/)
log "ZIP gerado: ${ZIP_FILE} ($(du -sh "${ZIP_FILE}" | cut -f1))"

log ""
log "════════════════════════════════════════"
log "Geração concluída!"
log "  Run      : ${RUN_STAMP}"
log "  Chunks   : $(ls "${CHUNKS_DIR}"/requests_*.jsonl | wc -l | tr -d ' ') batches submetidos"
log "  Variantes: ${PILOT_DIR}/variants/"
log "  ZIP      : ${ZIP_FILE}"
log "  Próximo  : fazer upload do ZIP para S3 e iniciar CodeBuild"
log "════════════════════════════════════════"

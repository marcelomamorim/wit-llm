#!/usr/bin/env bash
# submit-batch-em-partes.sh
#
# Divide o JSONL de requests em 2 partes, envia a primeira, aguarda conclusão,
# envia a segunda, aguarda conclusão e mescla as respostas.
#
# Motivo: gpt-4.1-nano tem limite de 20M enqueued tokens; um batch único com
# ~7.700 requests ultrapassa esse limite. Dividindo em 2 partes de ~3.850
# requests cada, ficamos confortavelmente abaixo.
#
# Uso:
#   CONFIRMAR_EXECUCAO_PAGA=sim \
#   RUN_DIR=generated/experiments/jdk-global-impact-study/<RUN_STAMP> \
#   RUNTIME_CONFIG=generated/configs/rodada-artigo-host.runtime.json \
#     bash scripts/submit-batch-em-partes.sh
#
# Variáveis opcionais:
#   GENERATION_MODEL   (default: openai_main)
#   REQUESTS_JSONL     (default: $RUN_DIR/requests_*.jsonl — detectado automaticamente)
#   POLL_INTERVAL      (default: 60  — segundos entre polls de status)

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
BIN="$ROOT_DIR/bin/witup"

RUNTIME_CONFIG="${RUNTIME_CONFIG:-$ROOT_DIR/generated/configs/rodada-artigo.runtime.json}"
GENERATION_MODEL="${GENERATION_MODEL:-openai_main}"
RUN_DIR="${RUN_DIR:-}"
POLL_INTERVAL="${POLL_INTERVAL:-60}"

log()  { printf '[submit-partes] %s\n' "$*"; }
die()  { printf '[submit-partes] ERRO: %s\n' "$*" >&2; exit 1; }

# ── Validações iniciais ───────────────────────────────────────────────────────

[[ "${CONFIRMAR_EXECUCAO_PAGA:-}" == "sim" ]] || \
  die "Defina CONFIRMAR_EXECUCAO_PAGA=sim para autorizar a submissão paga."

[[ -n "${OPENAI_API_KEY:-}" ]] || die "OPENAI_API_KEY não definida."

[[ -n "$RUN_DIR" ]] || die "RUN_DIR é obrigatório."
[[ -d "$RUN_DIR" ]] || die "RUN_DIR não encontrado: $RUN_DIR"

[[ -x "$BIN" ]] || die "Binário witup não encontrado em $BIN. Compile com: go build -o $BIN ./cmd/witup"

# Detectar o JSONL de requests automaticamente se não fornecido
if [[ -z "${REQUESTS_JSONL:-}" ]]; then
  REQUESTS_JSONL="$(ls "$RUN_DIR"/requests_*.jsonl 2>/dev/null | head -1 || true)"
fi
[[ -f "$REQUESTS_JSONL" ]] || die "JSONL de requests não encontrado em $RUN_DIR. Defina REQUESTS_JSONL=..."

TOTAL_LINES=$(wc -l < "$REQUESTS_JSONL" | tr -d ' ')
HALF=$(( TOTAL_LINES / 2 ))

log "RUN_DIR=$RUN_DIR"
log "REQUESTS_JSONL=$REQUESTS_JSONL ($TOTAL_LINES requests)"
log "RUNTIME_CONFIG=$RUNTIME_CONFIG"
log "GENERATION_MODEL=$GENERATION_MODEL"
log "POLL_INTERVAL=${POLL_INTERVAL}s"
log "Dividindo em parte1=$HALF requests + parte2=$(( TOTAL_LINES - HALF )) requests"

# ── Dividir o JSONL ───────────────────────────────────────────────────────────

PART1="$RUN_DIR/requests_part1.jsonl"
PART2="$RUN_DIR/requests_part2.jsonl"

if [[ -f "$PART1" && -f "$PART2" ]]; then
  log "Partes já existem — pulando divisão."
else
  log "Criando parte 1 ($HALF linhas)..."
  head -n "$HALF" "$REQUESTS_JSONL" > "$PART1"
  log "Criando parte 2 ($(( TOTAL_LINES - HALF )) linhas)..."
  tail -n "+$(( HALF + 1 ))" "$REQUESTS_JSONL" > "$PART2"
fi

log "part1: $(wc -l < "$PART1") requests ($(du -sh "$PART1" | cut -f1))"
log "part2: $(wc -l < "$PART2") requests ($(du -sh "$PART2" | cut -f1))"

# ── Funções de submit e poll ──────────────────────────────────────────────────

extrair_batch_id() {
  local metadata="$1"
  python3 - "$metadata" <<'PY'
import json, sys
try:
    d = json.load(open(sys.argv[1]))
    print(d.get("batch_id") or d.get("id") or "")
except Exception:
    print("")
PY
}

extrair_status() {
  local metadata="$1"
  python3 - "$metadata" <<'PY'
import json, sys
try:
    d = json.load(open(sys.argv[1]))
    print(d.get("status") or "unknown")
except Exception:
    print("unknown")
PY
}

extrair_progresso() {
  local metadata="$1"
  python3 - "$metadata" <<'PY'
import json, sys
try:
    d = json.load(open(sys.argv[1]))
    c = d.get("request_counts") or {}
    total = int(c.get("total") or 0)
    done  = int(c.get("completed") or 0) + int(c.get("failed") or 0)
    print(f"{done}/{total}")
except Exception:
    print("?/?")
PY
}

submeter_parte() {
  local part_num="$1"
  local requests_file="$2"
  local metadata_out="$3"

  # Redirecionar toda saída para stderr — só o batch_id vai para stdout (capturado pelo caller)
  log "── Submetendo parte $part_num ──────────────────────────────────────────" >&2
  "$BIN" submeter-openai-batch \
    --config "$RUNTIME_CONFIG" \
    --model  "$GENERATION_MODEL" \
    --requests "$requests_file" \
    --output   "$metadata_out" >&2

  local batch_id
  batch_id="$(extrair_batch_id "$metadata_out")"
  [[ -n "$batch_id" ]] || die "Não foi possível extrair batch_id de $metadata_out"
  log "Parte $part_num submetida — batch_id=$batch_id" >&2
  echo "$batch_id"
}

aguardar_parte() {
  local part_num="$1"
  local batch_id="$2"
  local collect_dir="$3"

  log "── Aguardando parte $part_num (batch_id=$batch_id) ────────────────────"
  mkdir -p "$collect_dir"

  local metadata="$collect_dir/openai_batch_metadata.json"
  local status=""

  while true; do
    set +e
    "$BIN" coletar-openai-batch \
      --config    "$RUNTIME_CONFIG" \
      --model     "$GENERATION_MODEL" \
      --batch-id  "$batch_id" \
      --output-dir "$collect_dir" 2>&1
    local exit_code=$?
    set -e

    if [[ -f "$metadata" ]]; then
      status="$(extrair_status "$metadata")"
      log "Parte $part_num — status=$status progresso=$(extrair_progresso "$metadata")"
    fi

    case "${status:-}" in
      completed|failed|expired|cancelled)
        break
        ;;
    esac

    log "Parte $part_num — aguardando ${POLL_INTERVAL}s..."
    sleep "$POLL_INTERVAL"
  done

  if [[ "$status" != "completed" ]]; then
    die "Parte $part_num terminou com status=$status (esperado: completed). Verifique $collect_dir"
  fi

  log "Parte $part_num concluída!"
}

# ── Parte 1 ───────────────────────────────────────────────────────────────────

META1="$RUN_DIR/batch_submission_part1.json"
COLLECT1="$RUN_DIR/collect-part1"

if [[ -f "$COLLECT1/responses_openai_batch_generation.jsonl" ]]; then
  log "Parte 1 já coletada — pulando submit+collect."
elif [[ -f "$META1" ]] && BATCH_ID1="$(extrair_batch_id "$META1")" && [[ -n "$BATCH_ID1" ]]; then
  log "Parte 1 já submetida — batch_id=$BATCH_ID1 — pulando submit, aguardando..."
  aguardar_parte 1 "$BATCH_ID1" "$COLLECT1"
else
  BATCH_ID1="$(submeter_parte 1 "$PART1" "$META1")"
  aguardar_parte 1 "$BATCH_ID1" "$COLLECT1"
fi

# ── Parte 2 ───────────────────────────────────────────────────────────────────

META2="$RUN_DIR/batch_submission_part2.json"
COLLECT2="$RUN_DIR/collect-part2"

if [[ -f "$COLLECT2/responses_openai_batch_generation.jsonl" ]]; then
  log "Parte 2 já coletada — pulando submit+collect."
elif [[ -f "$META2" ]] && BATCH_ID2="$(extrair_batch_id "$META2")" && [[ -n "$BATCH_ID2" ]]; then
  log "Parte 2 já submetida — batch_id=$BATCH_ID2 — pulando submit, aguardando..."
  aguardar_parte 2 "$BATCH_ID2" "$COLLECT2"
else
  BATCH_ID2="$(submeter_parte 2 "$PART2" "$META2")"
  aguardar_parte 2 "$BATCH_ID2" "$COLLECT2"
fi

# ── Mesclar respostas ─────────────────────────────────────────────────────────

log "── Mesclando respostas ──────────────────────────────────────────────────"

RESPONSES_OUT="$RUN_DIR/responses_openai_batch_generation.jsonl"
ERRORS_OUT="$RUN_DIR/errors_openai_batch_generation.jsonl"

cat "$COLLECT1/responses_openai_batch_generation.jsonl" \
    "$COLLECT2/responses_openai_batch_generation.jsonl" \
    > "$RESPONSES_OUT"

# Mesclar erros se existirem
touch "$ERRORS_OUT"
for f in "$COLLECT1/errors_openai_batch_generation.jsonl" \
         "$COLLECT2/errors_openai_batch_generation.jsonl"; do
  [[ -f "$f" ]] && cat "$f" >> "$ERRORS_OUT" || true
done

TOTAL_RESPONSES=$(wc -l < "$RESPONSES_OUT" | tr -d ' ')
TOTAL_ERRORS=$(wc -l < "$ERRORS_OUT" | tr -d ' ')

log ""
log "=== Submit em partes concluído ==="
log "Respostas : $RESPONSES_OUT ($TOTAL_RESPONSES linhas)"
log "Erros     : $ERRORS_OUT ($TOTAL_ERRORS linhas)"
log ""
log "Próximo passo — materializar testes:"
log "  RUN_DIR=$RUN_DIR \\"
log "  RUNTIME_CONFIG=$RUNTIME_CONFIG \\"
log "    bash scripts/evaluate-jdk-global-impact-experiment.sh"

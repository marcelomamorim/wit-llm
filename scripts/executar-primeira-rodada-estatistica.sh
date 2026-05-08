#!/usr/bin/env bash

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
ROUND_DIR="${ROUND_DIR:-$ROOT_DIR/generated/statistical-round-1}"
RUNTIME_CONFIG="${RUNTIME_CONFIG:-$ROOT_DIR/generated/configs/primeira-rodada-estatistica.runtime.json}"
MANIFEST="${MANIFEST:-$ROUND_DIR/statistical-manifest.csv}"
BIN="$ROOT_DIR/bin/witup"
PREFLIGHT_LOG="$ROUND_DIR/preflight.log"
PAID_RUN_LOG="$ROUND_DIR/paid-run.log"
export MAVEN_REPO_LOCAL="${MAVEN_REPO_LOCAL:-$ROOT_DIR/generated/m2-repo}"
export MAVEN_PROFILE_ARGS="${MAVEN_PROFILE_ARGS:--P!java14+}"

log() {
  printf '[primeira-rodada/executar] %s\n' "$*"
}

extrair_caminho_relatorio_preflight() {
  awk -F':' '/Relatório preflight/ {
    sub(/^[[:space:]]+/, "", $2);
    print $2;
  }' "$PREFLIGHT_LOG" | tail -1
}

extrair_caminho_relatorio_fase2() {
  awk -F':' '/Relatório da fase 2/ {
    sub(/^[[:space:]]+/, "", $2);
    print $2;
  }' "$PAID_RUN_LOG" | tail -1
}

validar_preflight_pronto() {
  local report="$1"
  python3 - "$report" <<'PY'
import json
import sys

path = sys.argv[1]
with open(path, "r", encoding="utf-8") as handle:
    report = json.load(handle)
ready = bool(report.get("ready"))
projects = report.get("projects", [])
aligned = sum(1 for project in projects if project.get("ready") and project.get("aligned_method_count") == 1)
total = len(projects)
if not ready:
    print(f"erro: preflight não está pronto ({aligned}/{total} slices prontos). Relatório: {path}", file=sys.stderr)
    for project in projects:
        if not project.get("ready") or project.get("aligned_method_count") != 1:
            problems = "; ".join(project.get("problems") or [])
            print(f"- {project.get('project')}: ready={project.get('ready')} aligned={project.get('aligned_method_count')} problems={problems}", file=sys.stderr)
    sys.exit(1)
if aligned != total:
    print(f"erro: esperado 1 método alinhado por slice, mas recebi {aligned}/{total}. Relatório: {path}", file=sys.stderr)
    sys.exit(1)
print(f"preflight_ok={aligned}/{total}")
PY
}

mkdir -p "$ROUND_DIR"

log "preparando repositórios, slices, manifesto e configuração"
"$ROOT_DIR/scripts/preparar-primeira-rodada-estatistica.sh"

log "executando preflight sem chamada paga"
"$BIN" preflight-segunda-fase --config "$RUNTIME_CONFIG" --check-build | tee "$PREFLIGHT_LOG"

PREFLIGHT_REPORT="$(extrair_caminho_relatorio_preflight)"
if [[ -z "$PREFLIGHT_REPORT" || ! -f "$PREFLIGHT_REPORT" ]]; then
  printf 'erro: não consegui localizar o relatório de preflight em %s\n' "$PREFLIGHT_LOG" >&2
  exit 1
fi
validar_preflight_pronto "$PREFLIGHT_REPORT"

if [[ "${CONFIRMAR_EXECUCAO_PAGA:-}" != "sim" ]]; then
  log "preflight válido; execução paga ainda não foi iniciada"
  printf 'Para iniciar a rodada paga, execute:\n'
  printf '  ROUND_DIR=%q RUNTIME_CONFIG=%q SLICES_PER_PROJECT=%q CANDIDATE_SLICES_PER_PROJECT=%q CONFIRMAR_EXECUCAO_PAGA=sim %q\n' \
    "$ROUND_DIR" "$RUNTIME_CONFIG" "${SLICES_PER_PROJECT:-15}" "${CANDIDATE_SLICES_PER_PROJECT:-80}" "$ROOT_DIR/scripts/executar-primeira-rodada-estatistica.sh"
  exit 0
fi

if [[ -z "${OPENAI_API_KEY:-}" ]]; then
  printf 'erro: OPENAI_API_KEY é obrigatória para a etapa paga.\n' >&2
  exit 1
fi

log "executando fase 2 paga em modo strict_1call"
"$BIN" executar-segunda-fase --config "$RUNTIME_CONFIG" --generation-model openai_main | tee "$PAID_RUN_LOG"

PHASE_TWO_REPORT="$(extrair_caminho_relatorio_fase2)"
if [[ -z "$PHASE_TWO_REPORT" || ! -f "$PHASE_TWO_REPORT" ]]; then
  printf 'erro: não consegui localizar o relatório da fase 2 em %s\n' "$PAID_RUN_LOG" >&2
  exit 1
fi

RUN_ROOT="$(dirname "$PHASE_TWO_REPORT")"
CSV_DIR="$RUN_ROOT/csv"

log "consolidando estatísticas pareadas"
"$BIN" consolidar-estatisticas-primeira-rodada \
  --manifest "$MANIFEST" \
  --summary "$CSV_DIR/phase-two-summary.csv" \
  --metrics "$CSV_DIR/phase-two-metrics.csv" \
  --comparison "$CSV_DIR/phase-two-comparison.csv" \
  --output-dir "$CSV_DIR"

log "relatório da fase 2: $PHASE_TWO_REPORT"
log "manifesto: $MANIFEST"
log "estatísticas CSV: $CSV_DIR/phase-two-statistics.csv"
log "estatísticas MD: $CSV_DIR/phase-two-statistics.md"

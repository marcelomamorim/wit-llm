#!/usr/bin/env bash
# run-smells-docker.sh
# Detecta test smells nos testes Java gerados pelas variantes direct-tests e wit-context.
# Usa análise estática (Python) — não requer compilação.
#
# Variáveis de ambiente:
#   EXPERIMENT_DIR : subdiretório em generated/experiments/ (default: jdk-global-impact-study)
#   RUN_STAMP      : timestamp do run

set -euo pipefail

EXPERIMENT_DIR="${EXPERIMENT_DIR:-jdk-global-impact-study}"
RUN_STAMP="${RUN_STAMP:-run}"

RUN_DIR="/data/generated/experiments/${EXPERIMENT_DIR}/${RUN_STAMP}"
VARIANTS_ROOT="${RUN_DIR}/variants"
SMELLS_OUT="${SMELLS_OUT:-${RUN_DIR}/smells-results}"
export SMELLS_OUT

log() { printf '[smells] %s\n' "$*"; }

mkdir -p "${SMELLS_OUT}"

for variant in direct-tests wit-context; do
  GENERATED_DIR="${VARIANTS_ROOT}/${variant}/test/jdk/witup/generated"
  if [ ! -d "${GENERATED_DIR}" ]; then
    log "SKIP ${variant} — sem ${GENERATED_DIR}"
    continue
  fi
  log "analisando ${variant}..."
  python3 /data/scripts/detect_test_smells.py \
    --input-dir "${GENERATED_DIR}" \
    --output "${SMELLS_OUT}/${variant}-smells.json" \
    --variant "${variant}"
  log "${variant}: concluído"
done

log "Consolidando resultados..."
python3 - <<PYEOF
import json, glob, os, sys

out_dir = "${SMELLS_OUT}"
files = glob.glob(f"{out_dir}/*-smells.json")
if not files:
    print("[smells] Nenhum resultado encontrado", file=sys.stderr)
    sys.exit(1)

all_data = {}
for f in sorted(files):
    try:
        data = json.load(open(f))
        variant = data.get("variant", os.path.basename(f))
        all_data[variant] = data
    except Exception as e:
        print(f"[smells] Erro lendo {f}: {e}", file=sys.stderr)

summary_path = os.path.join(out_dir, "smells-summary.json")
with open(summary_path, "w") as fp:
    json.dump(all_data, fp, indent=2)
print(f"[smells] Resumo salvo em {summary_path}")
for variant, d in all_data.items():
    sm = d.get("smell_summary", {})
    total = d.get("total_files", 0)
    print(f"  {variant}: {total} testes, smells={json.dumps(sm)}")
PYEOF

#!/usr/bin/env bash
# Pipeline completo de uma variante: materializa → empacota → S3 → 48 builds.
set -uo pipefail
cd "$(dirname "$0")"; R="$PWD"
K="$1"                                   # jdk-v1 | jdk-v2
D="$R/generated/experiments/jdk-variantes/$K"
STAMP="${STAMP:-${K}-$(date +%Y%m%d)}"
BUCKET=witup-jcov-results
log(){ echo "[$(date +%H:%M)] [$K] $*"; }

# ── 1. layout esperado pelo rematerializador ────────────────────────────────
log "montando layout"
A="$D/artifact"; rm -rf "$A"; mkdir -p "$A/prompts" "$A/responses"
python3 - "$D" "$A" <<'PY'
import json,sys,pathlib,collections
D,A=pathlib.Path(sys.argv[1]),pathlib.Path(sys.argv[2])
# requests por cenário
out={s:open(A/"prompts"/f"requests_{s}.jsonl","w") for s in ("direct-tests","wit-context")}
for line in open(D/"requests.jsonl"):
    cid=json.loads(line)["custom_id"]; p=cid.split("/")
    if p[1] in out: out[p[1]].write(line)
for f in out.values(): f.close()
# respostas por cenário, numeradas em chunks
buf=collections.defaultdict(list)
for f in sorted((D/"respostas").rglob("responses_openai_batch_generation.jsonl")):
    for line in open(f):
        try: cid=json.loads(line)["custom_id"]
        except Exception: continue
        p=cid.split("/")
        if len(p)>1: buf[p[1]].append(line)
for s,linhas in buf.items():
    with open(A/"responses"/f"responses_{s}_chunk001.jsonl","w") as fh: fh.writelines(linhas)
    print(f"  {s}: {len(linhas)} respostas")
PY

# ── 2. materializar pares ───────────────────────────────────────────────────
log "materializando pares"
M="$D/materialized"; rm -rf "$M"
python3 "$R/scripts-variantes/remat.py" --artifact "$A" --output "$M" --shards 24 >"$D/remat.log" 2>&1 \
  || { log "FALHOU materialização"; tail -3 "$D/remat.log"; exit 1; }
grep -oE '"materialized_pairs":[^,]*' "$M/summary.json" 2>/dev/null | sed 's/^/  /'

# ── 3. empacotar shards ─────────────────────────────────────────────────────
log "empacotando shards"
P="$D/packages"; rm -rf "$P"
python3 "$R/scripts-variantes/pkg.py" --input "$M" --output "$P" >"$D/pkg.log" 2>&1 \
  || { log "FALHOU empacotamento"; tail -3 "$D/pkg.log"; exit 1; }
n=$(ls "$P"/paired-shard-*.zip 2>/dev/null | wc -l | tr -d ' '); log "shards: $n"
[ "$n" -gt 0 ] || { log "sem shards"; exit 1; }

# ── 4. S3 ───────────────────────────────────────────────────────────────────
log "subindo inputs para s3://$BUCKET/$STAMP/inputs/"
aws s3 cp "$P/" "s3://$BUCKET/$STAMP/inputs/" --recursive --only-show-errors \
  --exclude "*" --include "paired-shard-*.zip"
aws s3 cp --copy-props none --only-show-errors \
  "s3://$BUCKET/paired-20260706-full02/inputs/jcov-baseline.xml" \
  "s3://$BUCKET/$STAMP/inputs/jcov-baseline.xml"
log "inputs no S3: $(aws s3 ls s3://$BUCKET/$STAMP/inputs/ | wc -l | tr -d ' ')"
echo "$STAMP" > "$D/.run_stamp"
log "pronto para executar — STAMP=$STAMP"

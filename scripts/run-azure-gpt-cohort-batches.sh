#!/usr/bin/env bash
# Azure VM supervisor. Batches and their source inputs remain on the persistent OS disk.
set -euo pipefail
base=${WIT_GPT_BASE:-/opt/wit-gpt/full}
image=cloudarchlab/witup-evaluator@sha256:9cb51259cf4a2249d60d4e60aeead28b8d776fc5cb39d1e742757fc41ffdcdd7
mkdir -p "$base/results"
trap 'printf "failed %s\n" "$(date -u +%FT%TZ)" > "$base/status.txt"' ERR
for input in "$base"/batches/*; do
  batch=${input##*/}; output="$base/results/$batch"
  if [[ -f "$output/verified.json" ]]; then continue; fi
  if [[ -e "$output" ]]; then echo "Incomplete output: $output; manual reconciliation required" >&2; exit 1; fi
  available=$(df -Pk "$base" | awk 'NR==2 {print $4}')
  (( available > 10485760 )) || { echo 'Less than 10 GiB free' >&2; exit 1; }
  mkdir -m 777 "$output"
  printf 'running %s %s\n' "$batch" "$(date -u +%FT%TZ)" > "$base/status.txt"
  sudo docker run --rm --user root --network none --read-only \
    --tmpfs /tmp:rw,exec,size=3g --tmpfs /opt/openjdk-src/test/jdk/witup/azure-haiku:rw,exec,size=256m \
    --pids-limit 512 --memory 6g --cpus 2 --cap-drop ALL \
    -v "$input:/in:ro" -v "$base/scripts:/scripts:ro" -v "$output:/out" \
    "$image" python3 /scripts/run-gpt-cohort-container.py > "$output/driver.log" 2>&1
  python3 - "$input" "$output" <<'PY'
import json,sys,pathlib
source,out=map(pathlib.Path,sys.argv[1:])
plan=json.loads((source/'test-plan.json').read_text())
report=json.loads((out/'execution-summary.json').read_text())
assert report['java_files']==len(plan)
assert all(t['exit_code'] != 124 for t in report['timings']), 'Whole-stage timeout'
assert all(not d['outcomes'].get('not_discovered',0) and not d['outcomes'].get('incomplete_result',0) for d in report['by_arm_stage'].values()), 'Missing result'
for arm in ('direct-tests','wit-context'):
 mapping=out/arm/'jcov'/'pid-test-map.tsv'
 raw=list((out/arm/'jcov'/'raw').glob('jcov-*.xml.gz'))
 lines=mapping.read_text().splitlines() if mapping.exists() else []
 ids={}
 for line in lines:
  pid,name=line.split('\t',1)
  assert pid.isdigit() and pid not in ids, 'ambiguous PID mapping'
  ids[pid]=name
 valid={r['suite_relative'] for r in json.loads((out/'test-plan.json').read_text()) if r['arm']==arm}
 assert all(name in valid for name in ids.values()), 'unknown test name'
 assert all(p.name.removeprefix('jcov-').removesuffix('.xml.gz') in ids for p in raw), 'XML without PID mapping'
(out/'verified.json').write_text(json.dumps({'files':len(plan),'status':'complete','pid_map_checked':True})+'\n')
PY
done
printf 'complete %s\n' "$(date -u +%FT%TZ)" > "$base/status.txt"

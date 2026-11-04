#!/usr/bin/env bash
# Executa as variantes no CodeBuild: 48 builds cada, 24 simultâneos.
set -uo pipefail
cd "$(dirname "$0")"; R="$PWD"
MAX="${MAX_CONC:-24}"
COMPUTE="${COMPUTE:-BUILD_GENERAL1_MEDIUM}"
COMMIT=1da1d9909a176a0f1306bf223dbb2600a4574e10
BASE="https://raw.githubusercontent.com/marcelomamorim/wit-llm/$COMMIT/scripts"
SPEC="$(cat "$R/rerun-codebuild/paired-shard-buildspec.yml")"
LOG="$R/exec-variantes-$(date +%Y%m%d-%H%M%S).log"
exec > >(tee -a "$LOG") 2>&1
REG="$R/generated/experiments/jdk-variantes/builds.tsv"; touch "$REG"

# Uma única chamada batch-get-builds (aceita até 100 ids) em vez de uma por
# linha: builds.tsv acumula runs antigos e o custo por poll era ~96 chamadas.
rodando(){
  local ids
  ids=$(awk '$2 ~ /^witup/ {print $2}' "$REG" | tail -100 | tr '\n' ' ')
  [ -z "$ids" ] && { echo 0; return; }
  aws codebuild batch-get-builds --ids $ids \
    --query 'length(builds[?buildStatus==`IN_PROGRESS`])' --output text 2>/dev/null || echo 0
}

echo "### início $(date) — max $MAX simultâneos, compute=$COMPUTE"
for K in "$@"; do
  STAMP=$(cat "$R/generated/experiments/jdk-variantes/$K/.run_stamp")
  echo "=== $K (stamp=$STAMP) ==="
  for v in direct-tests wit-context; do
    for n in $(seq -w 0 23); do
      tag="$K/$v/$n"
      grep -q "^$tag " "$REG" && continue
      until [ "$(rodando)" -lt "$MAX" ]; do sleep 30; done
      id=$(aws codebuild start-build --project-name witup-jcov-baseline \
        --buildspec-override "$SPEC" --compute-type-override "$COMPUTE" \
        --timeout-in-minutes-override 240 \
        --environment-variables-override \
          name=RUN_STAMP,value="$STAMP" name=JCOV_VARIANT,value="$v" \
          name=SHARD_ID,value="$n" name=INPUT_ARCHIVE,value="paired-shard-$n.zip" \
          name=ARTIFACT_SCRIPT_BASE,value="$BASE" \
        --query 'build.id' --output text 2>/dev/null)
      case "$id" in witup*) echo "$tag $id" >> "$REG"; echo "[$(date +%H:%M)] → $tag";;
                    *) echo "[$(date +%H:%M)] recusado $tag, aguardando"; sleep 60;; esac
    done
  done
done
echo "### todos lançados, aguardando conclusão..."
until [ "$(rodando)" -eq 0 ]; do sleep 60; done
echo "### fim $(date)"
awk '{print $2}' "$REG" | while read -r id; do
  aws codebuild batch-get-builds --ids "$id" --query 'builds[0].buildStatus' --output text 2>/dev/null
done | sort | uniq -c

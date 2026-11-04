#!/usr/bin/env bash
# Dispara os shards restantes mantendo MAX_CONC builds simultâneos.
# Reentrante: pula o que já está em builds.txt. Roda sem supervisão.
set -uo pipefail
cd "$(dirname "$0")"

MAX_CONC="${MAX_CONC:-24}"   # teto medido: 30 rodando / 15 na fila
COMPUTE="${COMPUTE:-BUILD_GENERAL1_LARGE}"
RUN_STAMP=$(cat .run_stamp)
COMMIT=1da1d9909a176a0f1306bf223dbb2600a4574e10
BASE="https://raw.githubusercontent.com/marcelomamorim/wit-llm/$COMMIT/scripts"
SPEC="$(cat paired-shard-buildspec.yml)"
LOG="driver-$(date +%Y%m%d-%H%M%S).log"
exec > >(tee -a "$LOG") 2>&1

echo "### run=$RUN_STAMP compute=$COMPUTE max_conc=$MAX_CONC"

running() {  # quantos dos nossos ainda estão rodando
  local n=0
  while read -r _ _ id; do
    [ -z "${id:-}" ] && continue
    s=$(aws codebuild batch-get-builds --ids "$id" --query 'builds[0].buildStatus' --output text 2>/dev/null)
    [ "$s" = "IN_PROGRESS" ] && n=$((n+1))
  done < builds.txt
  echo "$n"
}

launch() { # variant shard
  local v=$1 n=$2
  local id
  id=$(aws codebuild start-build --project-name witup-jcov-baseline \
    --buildspec-override "$SPEC" \
    --compute-type-override "$COMPUTE" \
    --timeout-in-minutes-override 240 \
    --environment-variables-override \
      name=RUN_STAMP,value="$RUN_STAMP" name=JCOV_VARIANT,value="$v" \
      name=SHARD_ID,value="$n" name=INPUT_ARCHIVE,value="paired-shard-$n.zip" \
      name=ARTIFACT_SCRIPT_BASE,value="$BASE" \
    --query 'build.id' --output text 2>/dev/null)
  case "$id" in
    witup*) echo "$v $n $id" >> builds.txt; echo "  [$(date +%H:%M:%S)] lancado $v/$n"; return 0;;
    *)      return 1;;
  esac
}

for v in direct-tests wit-context; do
  for n in $(seq -w 0 23); do
    grep -q "^$v $n " builds.txt 2>/dev/null && continue
    until [ "$(running)" -lt "$MAX_CONC" ]; do sleep 20; done
    sleep 2   # evita estourar o limite de 15 na fila
    until launch "$v" "$n"; do echo "  [$(date +%H:%M:%S)] recusado $v/$n, aguardando"; sleep 60; done
  done
done

echo "### todos os 48 lancados. aguardando conclusao..."
until [ "$(running)" -eq 0 ]; do sleep 60; done
echo "### fim: $(date)"
bash status.sh

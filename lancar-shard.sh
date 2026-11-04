#!/usr/bin/env bash
# Lança um shard avulso e registra em builds.tsv. Existe porque o caminho de
# recusa do driver-variantes.sh não tem retry: ele abandona o shard recusado e
# segue para o próximo, deixando buracos silenciosos.
set -uo pipefail
cd "$(dirname "$0")"; R="$PWD"
K="$1" V="$2" N="$3"
COMPUTE="${COMPUTE:-BUILD_GENERAL1_MEDIUM}"
REG="$R/generated/experiments/jdk-variantes/builds.tsv"
STAMP=$(cat "$R/generated/experiments/jdk-variantes/$K/.run_stamp")
SPEC="$(cat "$R/rerun-codebuild/paired-shard-buildspec.yml")"
COMMIT=1da1d9909a176a0f1306bf223dbb2600a4574e10
BASE="https://raw.githubusercontent.com/marcelomamorim/wit-llm/$COMMIT/scripts"
tag="$K/$V/$N"
grep -q "^$tag " "$REG" && { echo "já registrado: $tag"; exit 0; }
for t in 1 2 3 4 5; do
  id=$(aws codebuild start-build --project-name witup-jcov-baseline \
    --buildspec-override "$SPEC" --compute-type-override "$COMPUTE" \
    --timeout-in-minutes-override 240 \
    --environment-variables-override \
      name=RUN_STAMP,value="$STAMP" name=JCOV_VARIANT,value="$V" \
      name=SHARD_ID,value="$N" name=INPUT_ARCHIVE,value="paired-shard-$N.zip" \
      name=ARTIFACT_SCRIPT_BASE,value="$BASE" \
    --query 'build.id' --output text 2>&1)
  case "$id" in
    witup*) echo "$tag $id" >> "$REG"; echo "→ $tag  ($id)"; exit 0;;
    *) echo "tentativa $t recusada: $(echo "$id" | head -1)"; sleep 45;;
  esac
done
echo "FALHOU após 5 tentativas: $tag"; exit 1

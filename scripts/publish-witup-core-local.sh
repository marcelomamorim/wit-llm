#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
JAR_PATH="${1:-$ROOT_DIR/vendor/witup-core/target/witup-core-0.0.0-SNAPSHOT.jar}"

if [[ ! -f "$JAR_PATH" ]]; then
  echo "witup-core jar not found at: $JAR_PATH" >&2
  echo "Pass the jar path as the first argument." >&2
  exit 1
fi

DEST_DIR="$ROOT_DIR/vendor/m2/br/unb/cic/witup/witup-core/0.0.0-local"
mkdir -p "$DEST_DIR"

cp "$JAR_PATH" "$DEST_DIR/witup-core-0.0.0-local.jar"
unzip -p "$JAR_PATH" META-INF/maven/br.unb.cic.witup/witup-core/pom.xml > "$DEST_DIR/witup-core-0.0.0-local.pom"
perl -0pi -e 's/<version>0\.0\.0-SNAPSHOT<\/version>/<version>0.0.0-local<\/version>/s' "$DEST_DIR/witup-core-0.0.0-local.pom"

echo "Published witup-core:0.0.0-local to $DEST_DIR"

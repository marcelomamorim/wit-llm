#!/usr/bin/env bash

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

export PILOT_PROJECT_KEY="${PILOT_PROJECT_KEY:-commons-io}"
export PILOT_PROJECT_LABEL="${PILOT_PROJECT_LABEL:-Apache Commons IO}"
export PILOT_PROJECT_ROOT="${PILOT_PROJECT_ROOT:-$ROOT_DIR/generated/repos/commons-io}"
export PILOT_PROJECT_GIT_URL="${PILOT_PROJECT_GIT_URL:-https://github.com/apache/commons-io.git}"
export PILOT_PROJECT_GIT_REF="${PILOT_PROJECT_GIT_REF:-}"
export PILOT_WIT_ANALYSIS="${PILOT_WIT_ANALYSIS:-$ROOT_DIR/resources/wit-replication-package/data/output/commons-io/wit_filtered.json}"
export PILOT_TARGET_CONTAINER="${PILOT_TARGET_CONTAINER:-}"
if [[ -n "${PILOT_TARGET_CONTAINERS:-}" ]]; then
  export PILOT_TARGET_CONTAINERS
elif [[ -n "$PILOT_TARGET_CONTAINER" ]]; then
  export PILOT_TARGET_CONTAINERS="$PILOT_TARGET_CONTAINER"
else
  export PILOT_TARGET_CONTAINER="org.apache.commons.io.IOCase"
  export PILOT_TARGET_CONTAINERS="org.apache.commons.io.IOCase,org.apache.commons.io.input.BoundedReader"
fi
export PILOT_OVERVIEW_FILE="${PILOT_OVERVIEW_FILE:-$PILOT_PROJECT_ROOT/README.md}"

exec "$ROOT_DIR/scripts/executar-piloto-segunda-fase.sh"

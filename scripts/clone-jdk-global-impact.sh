#!/usr/bin/env bash

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
JDK_URL="${JDK_URL:-https://github.com/openjdk/jdk.git}"
JDK_COMMIT="${JDK_COMMIT:-da75f3c4ad5bdf25167a3ed80e51f567ab3dbd01}"
JDK_ROOT="${JDK_ROOT:-$ROOT_DIR/generated/repos/jdk}"

log() {
  printf '[jdk-global/clone] %s\n' "$*"
}

mkdir -p "$(dirname "$JDK_ROOT")"
log "jdk_url=$JDK_URL"
log "jdk_commit=$JDK_COMMIT"
log "jdk_root=$JDK_ROOT"

if [[ ! -d "$JDK_ROOT/.git" ]]; then
  log "clonando OpenJDK/JDK"
  git clone "$JDK_URL" "$JDK_ROOT"
fi

log "fazendo checkout do commit WIT"
git -C "$JDK_ROOT" fetch --tags --prune origin
git -C "$JDK_ROOT" checkout "$JDK_COMMIT"
git -C "$JDK_ROOT" status --short
log "checkout pronto"

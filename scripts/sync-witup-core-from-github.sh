#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
REPO_URL="https://github.com/WITUP-Project/witup-core.git"
REPO_DIR="$ROOT_DIR/vendor/witup-core"
REF="main"
SKIP_TESTS=true
SKIP_PUBLISH=false

usage() {
  cat <<EOF
Usage:
  $0 [options]

Options:
  --repo-url <url>      Git URL of witup-core (default: $REPO_URL)
  --repo-dir <path>     Local checkout dir (default: $REPO_DIR)
  --ref <branch|tag|sha>Git ref to checkout (default: $REF)
  --with-tests          Build witup-core running tests
  --skip-publish        Do not publish artifact to vendor/m2
  --help                Show this help
EOF
}

require_cmd() {
  local cmd="$1"
  if ! command -v "$cmd" >/dev/null 2>&1; then
    echo "Missing required command: $cmd" >&2
    exit 1
  fi
}

parse_args() {
  while [[ $# -gt 0 ]]; do
    case "$1" in
      --repo-url)
        REPO_URL="${2:-}"
        shift 2
        ;;
      --repo-dir)
        REPO_DIR="${2:-}"
        shift 2
        ;;
      --ref)
        REF="${2:-}"
        shift 2
        ;;
      --with-tests)
        SKIP_TESTS=false
        shift 1
        ;;
      --skip-publish)
        SKIP_PUBLISH=true
        shift 1
        ;;
      --help|-h)
        usage
        exit 0
        ;;
      *)
        echo "Unknown argument: $1" >&2
        usage
        exit 1
        ;;
    esac
  done
}

ensure_clean_worktree() {
  if [[ -n "$(git -C "$REPO_DIR" status --porcelain)" ]]; then
    echo "Repository has local changes: $REPO_DIR" >&2
    echo "Please commit/stash changes or use another --repo-dir." >&2
    exit 1
  fi
}

checkout_ref() {
  if git -C "$REPO_DIR" show-ref --verify --quiet "refs/remotes/origin/$REF"; then
    git -C "$REPO_DIR" checkout -B "$REF" "origin/$REF"
    return 0
  fi

  if git -C "$REPO_DIR" show-ref --verify --quiet "refs/tags/$REF"; then
    git -C "$REPO_DIR" checkout "tags/$REF"
    return 0
  fi

  git -C "$REPO_DIR" checkout "$REF"
}

sync_repo() {
  echo "[1/4] Syncing witup-core repository..."

  if [[ ! -d "$REPO_DIR/.git" ]]; then
    mkdir -p "$(dirname "$REPO_DIR")"
    git clone "$REPO_URL" "$REPO_DIR"
  else
    ensure_clean_worktree
    git -C "$REPO_DIR" remote set-url origin "$REPO_URL"
    git -C "$REPO_DIR" fetch origin --tags --prune
  fi

  checkout_ref
}

build_witup_core() {
  echo "[2/4] Building witup-core..."

  local mvn_args=(
    -q
    -f "$REPO_DIR/pom.xml"
    -DskipTests="$SKIP_TESTS"
    package
  )

  mvn "${mvn_args[@]}"
}

publish_local_artifact() {
  if [[ "$SKIP_PUBLISH" == true ]]; then
    echo "[3/4] Skipping local publish (--skip-publish)."
    return 0
  fi

  echo "[3/4] Publishing witup-core to vendor/m2..."
  "$ROOT_DIR/scripts/publish-witup-core-local.sh" \
    "$REPO_DIR/target/witup-core-0.0.0-SNAPSHOT.jar"
}

print_summary() {
  local commit
  commit="$(git -C "$REPO_DIR" rev-parse --short HEAD)"

  echo "[4/4] Done."
  echo "witup-core repo:   $REPO_DIR"
  echo "witup-core ref:    $REF"
  echo "witup-core commit: $commit"

  if [[ "$SKIP_PUBLISH" == false ]]; then
    echo "Published artifact: $ROOT_DIR/vendor/m2/br/unb/cic/witup/witup-core/0.0.0-local/witup-core-0.0.0-local.jar"
    echo "Use dependency: br.unb.cic.witup:witup-core:0.0.0-local"
  fi
}

main() {
  parse_args "$@"

  require_cmd git
  require_cmd mvn

  sync_repo
  build_witup_core
  publish_local_artifact
  print_summary
}

main "$@"

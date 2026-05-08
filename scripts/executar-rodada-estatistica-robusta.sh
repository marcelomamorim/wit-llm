#!/usr/bin/env bash

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

export ROUND_DIR="${ROUND_DIR:-$ROOT_DIR/generated/statistical-round-robust}"
export RUNTIME_CONFIG="${RUNTIME_CONFIG:-$ROOT_DIR/generated/configs/rodada-estatistica-robusta.runtime.json}"
export SLICES_PER_PROJECT="${SLICES_PER_PROJECT:-30}"
export CANDIDATE_SLICES_PER_PROJECT="${CANDIDATE_SLICES_PER_PROJECT:-80}"

"$ROOT_DIR/scripts/executar-primeira-rodada-estatistica.sh"

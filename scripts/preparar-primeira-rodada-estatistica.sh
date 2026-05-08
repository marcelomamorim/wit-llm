#!/usr/bin/env bash

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
ROUND_DIR="${ROUND_DIR:-$ROOT_DIR/generated/statistical-round-1}"
CONFIG_DIR="$ROOT_DIR/generated/configs"
BASELINE_DIR="$ROUND_DIR/baselines"
OVERVIEW_DIR="$ROUND_DIR/overviews"
BIN="$ROOT_DIR/bin/witup"
RUNTIME_CONFIG="${RUNTIME_CONFIG:-$CONFIG_DIR/primeira-rodada-estatistica.runtime.json}"
MANIFEST="$ROUND_DIR/statistical-manifest.csv"
CANDIDATE_CONFIG="$ROUND_DIR/candidate.runtime.json"
CANDIDATE_MANIFEST="$ROUND_DIR/candidate-manifest.csv"
ALIGNMENT_PREFLIGHT_LOG="$ROUND_DIR/alignment-preflight.log"
MAVEN_REPO_LOCAL="${MAVEN_REPO_LOCAL:-$ROOT_DIR/generated/m2-repo}"
MAVEN_PROFILE_ARGS="${MAVEN_PROFILE_ARGS:--P!java14+}"

STATISTICAL_SEED="${STATISTICAL_SEED:-20260429}"
FINAL_SLICES_PER_PROJECT="${SLICES_PER_PROJECT:-15}"
CANDIDATE_SLICES_PER_PROJECT="${CANDIDATE_SLICES_PER_PROJECT:-80}"
OPENAI_MODEL="${OPENAI_MODEL:-o4-mini-2025-04-16}"
OPENAI_BASE_URL="${OPENAI_BASE_URL:-https://api.openai.com/v1}"
OPENAI_REASONING_EFFORT="${OPENAI_REASONING_EFFORT:-medium}"
OPENAI_EXECUTION_BACKEND="${OPENAI_EXECUTION_BACKEND:-sync}"
OPENAI_ENDPOINT="${OPENAI_ENDPOINT:-/v1/responses}"
OPENAI_BATCH_COMPLETION_WINDOW="${OPENAI_BATCH_COMPLETION_WINDOW:-24h}"
PHASE_TWO_EXECUTION_MODE="${PHASE_TWO_EXECUTION_MODE:-strict_1call}"

JACKSON_ROOT="${JACKSON_ROOT:-$ROOT_DIR/generated/repos/jackson-databind}"
JACKSON_URL="${JACKSON_URL:-https://github.com/FasterXML/jackson-databind.git}"
JACKSON_COMMIT="${JACKSON_COMMIT:-972d5a28ae5a4c012b799ef6da2ffa6fe2291e50}"
JACKSON_WIT="${JACKSON_WIT:-$ROOT_DIR/resources/wit-replication-package/data/output/jackson-databind/wit_filtered.json}"

HTTPCLIENT_ROOT="${HTTPCLIENT_ROOT:-$ROOT_DIR/generated/repos/httpcomponents-client}"
HTTPCLIENT_URL="${HTTPCLIENT_URL:-https://github.com/apache/httpcomponents-client.git}"
HTTPCLIENT_COMMIT="${HTTPCLIENT_COMMIT:-29ba623ebeec67cd6e8d940b2fed9151c16e4daa}"
HTTPCLIENT_WIT="${HTTPCLIENT_WIT:-$ROOT_DIR/resources/wit-replication-package/data/output/httpcomponents-client/wit_filtered.json}"

PDFBOX_ROOT="${PDFBOX_ROOT:-$ROOT_DIR/generated/repos/pdfbox}"
PDFBOX_URL="${PDFBOX_URL:-https://github.com/apache/pdfbox.git}"
PDFBOX_COMMIT="${PDFBOX_COMMIT:-01bce4dde73db7b434a58c82dc79057a20460fd8}"
PDFBOX_WIT="${PDFBOX_WIT:-$ROOT_DIR/resources/wit-replication-package/data/output/pdfbox/wit_filtered.json}"

COMMONS_LANG_ROOT="${COMMONS_LANG_ROOT:-$ROOT_DIR/generated/repos/commons-lang}"
COMMONS_LANG_URL="${COMMONS_LANG_URL:-https://github.com/apache/commons-lang.git}"
COMMONS_LANG_COMMIT="${COMMONS_LANG_COMMIT:-90e0a9bb234658b5b845f0db7ec923422002a1d7}"
COMMONS_LANG_WIT="${COMMONS_LANG_WIT:-$ROOT_DIR/resources/wit-replication-package/data/output/commons-lang/wit_filtered.json}"

COMMONS_IO_ROOT="${COMMONS_IO_ROOT:-$ROOT_DIR/generated/repos/commons-io}"
COMMONS_IO_URL="${COMMONS_IO_URL:-https://github.com/apache/commons-io.git}"
COMMONS_IO_COMMIT="${COMMONS_IO_COMMIT:-2ae025fe5c4a7d2046c53072b0898e37a079fe62}"
COMMONS_IO_WIT="${COMMONS_IO_WIT:-$ROOT_DIR/resources/wit-replication-package/data/output/commons-io/wit_filtered.json}"

SPRING_DATA_COMMONS_ROOT="${SPRING_DATA_COMMONS_ROOT:-$ROOT_DIR/generated/repos/spring-data-commons}"
SPRING_DATA_COMMONS_URL="${SPRING_DATA_COMMONS_URL:-https://github.com/spring-projects/spring-data-commons.git}"
SPRING_DATA_COMMONS_COMMIT="${SPRING_DATA_COMMONS_COMMIT:-4acd3b70033633943aa2645e4875ac1e1dd1040b}"
SPRING_DATA_COMMONS_WIT="${SPRING_DATA_COMMONS_WIT:-$ROOT_DIR/resources/wit-replication-package/data/output/spring-data-commons/wit_filtered.json}"

COMMONS_TEXT_ROOT="${COMMONS_TEXT_ROOT:-$ROOT_DIR/generated/repos/commons-text}"
COMMONS_TEXT_URL="${COMMONS_TEXT_URL:-https://github.com/apache/commons-text.git}"
COMMONS_TEXT_COMMIT="${COMMONS_TEXT_COMMIT:-21fc34f17175aba66f55fb6f805e60c13055da49}"
COMMONS_TEXT_WIT="${COMMONS_TEXT_WIT:-$ROOT_DIR/resources/wit-replication-package/data/output/commons-text/wit_filtered.json}"

BYTE_BUDDY_ROOT="${BYTE_BUDDY_ROOT:-$ROOT_DIR/generated/repos/byte-buddy}"
BYTE_BUDDY_URL="${BYTE_BUDDY_URL:-https://github.com/raphw/byte-buddy.git}"
BYTE_BUDDY_COMMIT="${BYTE_BUDDY_COMMIT:-4c57c80aabe088174578f7d59d217cc08f4d7518}"
BYTE_BUDDY_WIT="${BYTE_BUDDY_WIT:-$ROOT_DIR/resources/wit-replication-package/data/output/byte-buddy/wit_filtered.json}"

POI_ROOT="${POI_ROOT:-$ROOT_DIR/generated/repos/poi}"
POI_URL="${POI_URL:-https://github.com/apache/poi.git}"
POI_COMMIT="${POI_COMMIT:-270107d9e80bc40c73e1478a9f74ec3a690013a6}"
POI_WIT="${POI_WIT:-$ROOT_DIR/resources/wit-replication-package/data/output/poi/wit_filtered.json}"

H2DATABASE_ROOT="${H2DATABASE_ROOT:-$ROOT_DIR/generated/repos/h2database}"
H2DATABASE_URL="${H2DATABASE_URL:-https://github.com/h2database/h2database.git}"
H2DATABASE_COMMIT="${H2DATABASE_COMMIT:-0ee51f54af8c9d3be10ae58b0ccdeec827942363}"
H2DATABASE_WIT="${H2DATABASE_WIT:-$ROOT_DIR/resources/wit-replication-package/data/output/h2database/wit_filtered.json}"

log() {
  printf '[primeira-rodada/preparar] %s\n' "$*"
}

ensure_git_checkout() {
  local target_dir="$1"
  local repo_url="$2"
  local repo_commit="$3"

  if ! command -v git >/dev/null 2>&1; then
    printf 'erro: git não está disponível no PATH.\n' >&2
    exit 1
  fi

  mkdir -p "$(dirname "$target_dir")"
  if [[ ! -d "$target_dir/.git" ]]; then
    if [[ -d "$target_dir" ]] && [[ -n "$(find "$target_dir" -mindepth 1 -maxdepth 1 2>/dev/null)" ]]; then
      printf 'erro: diretório já existe e não parece ser checkout git: %s\n' "$target_dir" >&2
      exit 1
    fi
    log "clonando $repo_url em $target_dir"
    git clone "$repo_url" "$target_dir"
  fi

  (
    cd "$target_dir"
    if [[ -n "$(git status --porcelain)" && "${ALLOW_DIRTY_REPOS:-0}" != "1" ]]; then
      printf 'erro: checkout com alterações locais em %s. Use ALLOW_DIRTY_REPOS=1 se quiser prosseguir mesmo assim.\n' "$target_dir" >&2
      exit 1
    fi
    log "atualizando $target_dir para commit $repo_commit"
    if ! git cat-file -e "$repo_commit^{commit}" >/dev/null 2>&1; then
      git fetch --depth 1 origin "$repo_commit" >/dev/null 2>&1 || git fetch origin "$repo_commit" >/dev/null 2>&1
    fi
    git checkout --detach "$repo_commit" >/dev/null
  )
}

resolve_overview() {
  local project_key="$1"
  local project_label="$2"
  local root="$3"

  for candidate in README.md README.adoc README.rst README.txt; do
    if [[ -f "$root/$candidate" ]]; then
      printf '%s' "$root/$candidate"
      return 0
    fi
  done

  mkdir -p "$OVERVIEW_DIR"
  local overview="$OVERVIEW_DIR/$project_key.md"
  cat > "$overview" <<EOF
# $project_label

Projeto usado na primeira rodada estatística do experimento WIT_CONTEXT vs DIRECT_TESTS.
O arquivo foi gerado automaticamente porque o checkout não possuía README na raiz esperada.
EOF
  printf '%s' "$overview"
}

install_jackson_parent_fallback() {
  local release_version="2.13.0-rc2"
  local snapshot_version="2.13.0-rc2-SNAPSHOT"
  local release_pom="$MAVEN_REPO_LOCAL/com/fasterxml/jackson/jackson-base/$release_version/jackson-base-$release_version.pom"
  local snapshot_pom="$MAVEN_REPO_LOCAL/com/fasterxml/jackson/jackson-base/$snapshot_version/jackson-base-$snapshot_version.pom"

  if [[ -f "$snapshot_pom" ]]; then
    return 0
  fi
  if ! command -v mvn >/dev/null 2>&1; then
    log "aviso: Maven não disponível; não foi possível instalar fallback do parent POM do Jackson"
    return 0
  fi

  log "preparando fallback local para parent POM do Jackson ($snapshot_version)"
  if [[ ! -f "$release_pom" ]]; then
    mvn -q -Dmaven.repo.local="$MAVEN_REPO_LOCAL" dependency:get -Dartifact="com.fasterxml.jackson:jackson-base:$release_version:pom"
  fi
  mkdir -p "$(dirname "$snapshot_pom")"
  sed "0,/<version>$release_version<\\/version>/s//<version>$snapshot_version<\\/version>/" "$release_pom" > "$snapshot_pom"
  cat > "$(dirname "$snapshot_pom")/maven-metadata-local.xml" <<EOF
<?xml version="1.0" encoding="UTF-8"?>
<metadata>
  <groupId>com.fasterxml.jackson</groupId>
  <artifactId>jackson-base</artifactId>
  <versioning>
    <versions>
      <version>$snapshot_version</version>
    </versions>
    <lastUpdated>20260430000000</lastUpdated>
  </versioning>
</metadata>
EOF
  cat > "$(dirname "$snapshot_pom")/_remote.repositories" <<EOF
# Generated by witup-llm first statistical round harness.
jackson-base-$snapshot_version.pom>=
EOF
}

for baseline in "$JACKSON_WIT" "$PDFBOX_WIT" "$HTTPCLIENT_WIT" "$COMMONS_LANG_WIT" "$COMMONS_IO_WIT" "$SPRING_DATA_COMMONS_WIT" "$COMMONS_TEXT_WIT" "$BYTE_BUDDY_WIT" "$POI_WIT" "$H2DATABASE_WIT"; do
  if [[ ! -f "$baseline" ]]; then
    printf 'erro: baseline WIT filtrado não encontrado: %s\n' "$baseline" >&2
    exit 1
  fi
done

mkdir -p "$CONFIG_DIR" "$ROUND_DIR" "$BASELINE_DIR" "$OVERVIEW_DIR" "$ROOT_DIR/bin" "$MAVEN_REPO_LOCAL"

ensure_git_checkout "$JACKSON_ROOT" "$JACKSON_URL" "$JACKSON_COMMIT"
ensure_git_checkout "$PDFBOX_ROOT" "$PDFBOX_URL" "$PDFBOX_COMMIT"
ensure_git_checkout "$HTTPCLIENT_ROOT" "$HTTPCLIENT_URL" "$HTTPCLIENT_COMMIT"
ensure_git_checkout "$COMMONS_LANG_ROOT" "$COMMONS_LANG_URL" "$COMMONS_LANG_COMMIT"
ensure_git_checkout "$COMMONS_IO_ROOT" "$COMMONS_IO_URL" "$COMMONS_IO_COMMIT"
ensure_git_checkout "$SPRING_DATA_COMMONS_ROOT" "$SPRING_DATA_COMMONS_URL" "$SPRING_DATA_COMMONS_COMMIT"
ensure_git_checkout "$COMMONS_TEXT_ROOT" "$COMMONS_TEXT_URL" "$COMMONS_TEXT_COMMIT"
ensure_git_checkout "$BYTE_BUDDY_ROOT" "$BYTE_BUDDY_URL" "$BYTE_BUDDY_COMMIT"
ensure_git_checkout "$POI_ROOT" "$POI_URL" "$POI_COMMIT"
ensure_git_checkout "$H2DATABASE_ROOT" "$H2DATABASE_URL" "$H2DATABASE_COMMIT"
install_jackson_parent_fallback

JACKSON_OVERVIEW="$(resolve_overview "jackson-databind" "Jackson Databind" "$JACKSON_ROOT")"
PDFBOX_OVERVIEW="$(resolve_overview "pdfbox" "Apache PDFBox" "$PDFBOX_ROOT")"
HTTPCLIENT_OVERVIEW="$(resolve_overview "httpcomponents-client" "HttpComponents Client" "$HTTPCLIENT_ROOT")"
COMMONS_LANG_OVERVIEW="$(resolve_overview "commons-lang" "Apache Commons Lang" "$COMMONS_LANG_ROOT")"
COMMONS_IO_OVERVIEW="$(resolve_overview "commons-io" "Apache Commons IO" "$COMMONS_IO_ROOT")"
SPRING_DATA_COMMONS_OVERVIEW="$(resolve_overview "spring-data-commons" "Spring Data Commons" "$SPRING_DATA_COMMONS_ROOT")"
COMMONS_TEXT_OVERVIEW="$(resolve_overview "commons-text" "Apache Commons Text" "$COMMONS_TEXT_ROOT")"
BYTE_BUDDY_OVERVIEW="$(resolve_overview "byte-buddy" "Byte Buddy" "$BYTE_BUDDY_ROOT")"
POI_OVERVIEW="$(resolve_overview "poi" "Apache POI" "$POI_ROOT")"
H2DATABASE_OVERVIEW="$(resolve_overview "h2database" "H2 Database" "$H2DATABASE_ROOT")"

log "compilando CLI em $BIN"
(
  cd "$ROOT_DIR"
  GOCACHE="$ROOT_DIR/.gocache" GOMODCACHE="$ROOT_DIR/.gomodcache" go build -o "$BIN" ./cmd/witup
)

export ROOT_DIR ROUND_DIR BASELINE_DIR MANIFEST RUNTIME_CONFIG BIN MAVEN_REPO_LOCAL MAVEN_PROFILE_ARGS
export CANDIDATE_CONFIG CANDIDATE_MANIFEST ALIGNMENT_PREFLIGHT_LOG
export STATISTICAL_SEED FINAL_SLICES_PER_PROJECT CANDIDATE_SLICES_PER_PROJECT OPENAI_MODEL OPENAI_BASE_URL OPENAI_REASONING_EFFORT PHASE_TWO_EXECUTION_MODE
export JACKSON_ROOT JACKSON_WIT JACKSON_COMMIT JACKSON_OVERVIEW
export PDFBOX_ROOT PDFBOX_WIT PDFBOX_COMMIT PDFBOX_OVERVIEW
export HTTPCLIENT_ROOT HTTPCLIENT_WIT HTTPCLIENT_COMMIT HTTPCLIENT_OVERVIEW
export COMMONS_LANG_ROOT COMMONS_LANG_WIT COMMONS_LANG_COMMIT COMMONS_LANG_OVERVIEW
export COMMONS_IO_ROOT COMMONS_IO_WIT COMMONS_IO_COMMIT COMMONS_IO_OVERVIEW
export SPRING_DATA_COMMONS_ROOT SPRING_DATA_COMMONS_WIT SPRING_DATA_COMMONS_COMMIT SPRING_DATA_COMMONS_OVERVIEW
export COMMONS_TEXT_ROOT COMMONS_TEXT_WIT COMMONS_TEXT_COMMIT COMMONS_TEXT_OVERVIEW
export BYTE_BUDDY_ROOT BYTE_BUDDY_WIT BYTE_BUDDY_COMMIT BYTE_BUDDY_OVERVIEW
export POI_ROOT POI_WIT POI_COMMIT POI_OVERVIEW
export H2DATABASE_ROOT H2DATABASE_WIT H2DATABASE_COMMIT H2DATABASE_OVERVIEW

python3 <<'PY'
import copy
import csv
import json
import os
import random
import sys
from pathlib import Path

root_dir = Path(os.environ["ROOT_DIR"])
round_dir = Path(os.environ["ROUND_DIR"])
baseline_dir = Path(os.environ["BASELINE_DIR"])
manifest_path = Path(os.environ["CANDIDATE_MANIFEST"])
runtime_config = Path(os.environ["CANDIDATE_CONFIG"])
bin_path = Path(os.environ["BIN"])
maven_repo = Path(os.environ["MAVEN_REPO_LOCAL"])
maven_profile_args = os.environ.get("MAVEN_PROFILE_ARGS", "").strip()
seed = int(os.environ["STATISTICAL_SEED"])
slices_per_project = int(os.environ["CANDIDATE_SLICES_PER_PROJECT"])

projects = [
    {
        "key": "jackson-databind",
        "label": "Jackson Databind",
        "root": Path(os.environ["JACKSON_ROOT"]),
        "baseline": Path(os.environ["JACKSON_WIT"]),
        "commit": os.environ["JACKSON_COMMIT"],
        "overview": Path(os.environ["JACKSON_OVERVIEW"]),
        "seed_offset": 0,
    },
    {
        "key": "pdfbox",
        "label": "Apache PDFBox",
        "root": Path(os.environ["PDFBOX_ROOT"]),
        "baseline": Path(os.environ["PDFBOX_WIT"]),
        "commit": os.environ["PDFBOX_COMMIT"],
        "overview": Path(os.environ["PDFBOX_OVERVIEW"]),
        "seed_offset": 1000,
    },
    {
        "key": "httpcomponents-client",
        "label": "HttpComponents Client",
        "root": Path(os.environ["HTTPCLIENT_ROOT"]),
        "baseline": Path(os.environ["HTTPCLIENT_WIT"]),
        "commit": os.environ["HTTPCLIENT_COMMIT"],
        "overview": Path(os.environ["HTTPCLIENT_OVERVIEW"]),
        "seed_offset": 2000,
    },
    {
        "key": "commons-lang",
        "label": "Apache Commons Lang",
        "root": Path(os.environ["COMMONS_LANG_ROOT"]),
        "baseline": Path(os.environ["COMMONS_LANG_WIT"]),
        "commit": os.environ["COMMONS_LANG_COMMIT"],
        "overview": Path(os.environ["COMMONS_LANG_OVERVIEW"]),
        "seed_offset": 3000,
    },
    {
        "key": "commons-io",
        "label": "Apache Commons IO",
        "root": Path(os.environ["COMMONS_IO_ROOT"]),
        "baseline": Path(os.environ["COMMONS_IO_WIT"]),
        "commit": os.environ["COMMONS_IO_COMMIT"],
        "overview": Path(os.environ["COMMONS_IO_OVERVIEW"]),
        "seed_offset": 4000,
    },
    {
        "key": "spring-data-commons",
        "label": "Spring Data Commons",
        "root": Path(os.environ["SPRING_DATA_COMMONS_ROOT"]),
        "baseline": Path(os.environ["SPRING_DATA_COMMONS_WIT"]),
        "commit": os.environ["SPRING_DATA_COMMONS_COMMIT"],
        "overview": Path(os.environ["SPRING_DATA_COMMONS_OVERVIEW"]),
        "seed_offset": 5000,
    },
    {
        "key": "commons-text",
        "label": "Apache Commons Text",
        "root": Path(os.environ["COMMONS_TEXT_ROOT"]),
        "baseline": Path(os.environ["COMMONS_TEXT_WIT"]),
        "commit": os.environ["COMMONS_TEXT_COMMIT"],
        "overview": Path(os.environ["COMMONS_TEXT_OVERVIEW"]),
        "seed_offset": 6000,
        "fallback": True,
    },
    {
        "key": "byte-buddy",
        "label": "Byte Buddy",
        "root": Path(os.environ["BYTE_BUDDY_ROOT"]),
        "baseline": Path(os.environ["BYTE_BUDDY_WIT"]),
        "commit": os.environ["BYTE_BUDDY_COMMIT"],
        "overview": Path(os.environ["BYTE_BUDDY_OVERVIEW"]),
        "seed_offset": 7000,
        "fallback": True,
    },
    {
        "key": "poi",
        "label": "Apache POI",
        "root": Path(os.environ["POI_ROOT"]),
        "baseline": Path(os.environ["POI_WIT"]),
        "commit": os.environ["POI_COMMIT"],
        "overview": Path(os.environ["POI_OVERVIEW"]),
        "seed_offset": 8000,
        "fallback": True,
    },
    {
        "key": "h2database",
        "label": "H2 Database",
        "root": Path(os.environ["H2DATABASE_ROOT"]),
        "baseline": Path(os.environ["H2DATABASE_WIT"]),
        "commit": os.environ["H2DATABASE_COMMIT"],
        "overview": Path(os.environ["H2DATABASE_OVERVIEW"]),
        "seed_offset": 9000,
        "fallback": True,
    },
]

def relative_source_path(project_key, raw_path):
    normalized = raw_path.replace("\\", "/")
    marker = f"/{project_key}/"
    if marker in normalized:
        return normalized.split(marker, 1)[1]
    marker = f"{project_key}/"
    if marker in normalized:
        return normalized.split(marker, 1)[1]
    for known in ("src/main/java/", "httpclient5/", "httpclient5-cache/", "httpclient5-fluent/", "httpclient5-testing/"):
        if known in normalized:
            return normalized[normalized.index(known):]
    return normalized.rsplit("/", 1)[-1]

def method_parts(signature):
    head = signature.split("(", 1)[0]
    if "." not in head:
        return "", head
    container, method = head.rsplit(".", 1)
    return container, method

def grouped_candidates(project, baseline):
    groups = {}
    classes = baseline.get("classes", [])
    for class_index, klass in enumerate(classes):
        class_path = klass.get("path", "")
        for method in klass.get("methods", []) or []:
            signature = (method.get("qualifiedSignature") or "").strip()
            if not signature:
                continue
            relative_path = relative_source_path(project["key"], class_path)
            local_source = project["root"] / relative_path
            if not local_source.exists():
                continue
            key = (class_path, signature)
            if key not in groups:
                groups[key] = {
                    "class_index": class_index,
                    "class_path": class_path,
                    "relative_path": relative_path,
                    "methods": [],
                    "signature": signature,
                }
            groups[key]["methods"].append(method)
    return list(groups.values())

def build_slice(project, baseline, candidate, slice_index):
    slice_key = f"{project['key']}-s{slice_index:03d}"
    slice_dir = baseline_dir / "_candidates" / project["key"]
    slice_dir.mkdir(parents=True, exist_ok=True)
    slice_path = slice_dir / f"wit_filtered.slice-{slice_index:03d}.json"

    sliced = copy.deepcopy(baseline)
    source_class = copy.deepcopy(baseline["classes"][candidate["class_index"]])
    source_class["methods"] = copy.deepcopy(candidate["methods"])
    sliced["classes"] = [source_class]
    with slice_path.open("w", encoding="utf-8") as handle:
        json.dump(sliced, handle, indent=2, ensure_ascii=False)
        handle.write("\n")

    container, method_name = method_parts(candidate["signature"])
    first_method = candidate["methods"][0]
    return {
        "source_project_key": project["key"],
        "source_project_label": project["label"],
        "slice_key": slice_key,
        "slice_index": f"{slice_index:03d}",
        "qualified_signature": candidate["signature"],
        "container_name": container,
        "method_name": method_name,
        "source_file": candidate["relative_path"],
        "line": str(first_method.get("line", "")),
        "throwing_line": str(first_method.get("throwingLine", "")),
        "expath_count": str(len(candidate["methods"])),
        "commit_hash": baseline.get("commitHash", ""),
        "baseline_original": str(project["baseline"]),
        "baseline_slice": str(slice_path),
        "seed": str(seed),
    }, {
        "key": slice_key,
        "label": f"{project['label']} slice {slice_index:03d}",
        "root": str(project["root"]),
        "wit_analysis_path": str(slice_path),
        "overview_file": str(project["overview"]),
        "include": ["src/main/java", "."],
        "exclude": [".git", "target", "build", "generated", "tests", "docs/javadoc"],
        "test_framework": "infer",
    }

manifest_rows = []
phase_projects = []
selected_project_count = 0
for project in projects:
    with project["baseline"].open("r", encoding="utf-8") as handle:
        baseline = json.load(handle)
    commit = baseline.get("commitHash", "")
    if commit != project["commit"]:
        print(
            f"erro: commitHash do baseline {project['baseline']} é {commit}, esperado {project['commit']}",
            file=sys.stderr,
        )
        sys.exit(1)
    candidates = grouped_candidates(project, baseline)
    rng = random.Random(seed + project["seed_offset"])
    rng.shuffle(candidates)
    if len(candidates) < slices_per_project:
        message = f"{project['key']} tem somente {len(candidates)} candidatos com arquivo fonte local; esperado {slices_per_project}"
        if project.get("fallback") or selected_project_count >= 6:
            print(f"aviso: fallback ignorado: {message}", file=sys.stderr)
            continue
        print(f"aviso: primário será substituído se houver fallback: {message}", file=sys.stderr)
        continue
    selected = candidates[:slices_per_project]
    for index, candidate in enumerate(selected, start=1):
        row, phase_project = build_slice(project, baseline, candidate, index)
        manifest_rows.append(row)
        phase_projects.append(phase_project)
    selected_project_count += 1
    if selected_project_count >= 6:
        break

if selected_project_count != 6:
    print(f"erro: esperado selecionar 6 projetos, mas selecionei {selected_project_count}", file=sys.stderr)
    sys.exit(1)

manifest_path.parent.mkdir(parents=True, exist_ok=True)
fieldnames = [
    "source_project_key",
    "source_project_label",
    "slice_key",
    "slice_index",
    "qualified_signature",
    "container_name",
    "method_name",
    "source_file",
    "line",
    "throwing_line",
    "expath_count",
    "commit_hash",
    "baseline_original",
    "baseline_slice",
    "seed",
]
with manifest_path.open("w", newline="", encoding="utf-8") as handle:
    writer = csv.DictWriter(handle, fieldnames=fieldnames)
    writer.writeheader()
    writer.writerows(manifest_rows)

def mvn_command(goals):
    profile_args = f" {maven_profile_args}" if maven_profile_args else ""
    return (
        "sh -lc 'if [ -x ./mvnw ]; then "
        f"./mvnw -q -Dmaven.repo.local={maven_repo}{profile_args} {goals}; "
        "else "
        f"mvn -q -Dmaven.repo.local={maven_repo}{profile_args} {goals}; "
        "fi'"
    )

def metric(name, kind, command, weight, scale=100.0, regex=None, timeout=600, outputs=None, description="", fallbacks=None):
    payload = {
        "name": name,
        "kind": kind,
        "command": command,
        "weight": weight,
        "scale": scale,
        "working_directory": ".",
        "timeout_seconds": timeout,
        "description": description,
    }
    if regex:
        payload["value_regex"] = regex
    if outputs:
        payload["expected_outputs"] = outputs
    if fallbacks:
        payload["fallbacks"] = fallbacks
    return payload

metric_regex = r"WITUP_METRIC=([0-9]+(?:\.[0-9]+)?)"
metrics = [
    metric(
        "test-compilation",
        "build",
        mvn_command("-DskipTests test-compile"),
        0.5,
        timeout=600,
        description="Compila a suíte gerada antes das demais métricas.",
    ),
    metric(
        "unit-tests",
        "tests",
        f"{mvn_command('test')} && \"{bin_path}\" extrair-surefire --report-dir target/surefire-reports",
        1.0,
        regex=metric_regex,
        timeout=900,
        outputs=["target/surefire-reports"],
        description="Executa os testes unitários gerados.",
    ),
    metric(
        "test-pass-rate",
        "tests",
        f"{mvn_command('test')} && \"{bin_path}\" extrair-surefire --report-dir target/surefire-reports --kind pass-rate",
        0.8,
        regex=metric_regex,
        timeout=900,
        outputs=["target/surefire-reports"],
        description="Percentual de testes gerados aprovados.",
    ),
    metric(
        "target-method-coverage",
        "generation_static",
        f"\"{bin_path}\" extrair-geracao --analysis {{analysis_path}} --generation {{generation_path}} --kind target-method-coverage",
        0.8,
        regex=metric_regex,
        timeout=120,
        description="Percentual de métodos-alvo com pelo menos um teste associado.",
    ),
    metric(
        "assertive-tests-rate",
        "generation_static",
        f"\"{bin_path}\" extrair-geracao --analysis {{analysis_path}} --generation {{generation_path}} --kind assertive-tests-rate",
        0.6,
        regex=metric_regex,
        timeout=120,
        description="Percentual de testes gerados com assertivas explícitas.",
    ),
    metric(
        "exception-assertion-rate",
        "generation_static",
        f"\"{bin_path}\" extrair-geracao --analysis {{analysis_path}} --generation {{generation_path}} --kind exception-assertion-rate",
        0.6,
        regex=metric_regex,
        timeout=120,
        description="Percentual de testes focados em exceções.",
    ),
    metric(
        "valid-java-rate",
        "generation_static_diagnostic",
        f"\"{bin_path}\" extrair-geracao --analysis {{analysis_path}} --generation {{generation_path}} --kind valid-java-rate",
        0.0,
        regex=metric_regex,
        timeout=120,
        description="Percentual de arquivos gerados que parecem Java puro, sem Markdown/HTML e com estrutura mínima de classe.",
    ),
    metric(
        "package-path-valid-rate",
        "generation_static_diagnostic",
        f"\"{bin_path}\" extrair-geracao --analysis {{analysis_path}} --generation {{generation_path}} --kind package-path-valid-rate",
        0.0,
        regex=metric_regex,
        timeout=120,
        description="Percentual de arquivos cujo package é compatível com o caminho relativo do teste.",
    ),
    metric(
        "test-method-presence-rate",
        "generation_static_diagnostic",
        f"\"{bin_path}\" extrair-geracao --analysis {{analysis_path}} --generation {{generation_path}} --kind test-method-presence-rate",
        0.0,
        regex=metric_regex,
        timeout=120,
        description="Percentual de arquivos gerados com pelo menos um método anotado com @Test.",
    ),
    metric(
        "target-invocation-rate",
        "generation_static_diagnostic",
        f"\"{bin_path}\" extrair-geracao --analysis {{analysis_path}} --generation {{generation_path}} --kind target-invocation-rate",
        0.0,
        regex=metric_regex,
        timeout=120,
        description="Percentual de métodos-alvo aparentemente invocados pelo teste gerado.",
    ),
    metric(
        "forbidden-dependency-rate",
        "generation_static_diagnostic",
        f"\"{bin_path}\" extrair-geracao --analysis {{analysis_path}} --generation {{generation_path}} --project-root {{project_root}} --kind forbidden-dependency-rate",
        0.0,
        regex=metric_regex,
        timeout=120,
        description="Percentual de arquivos que usam bibliotecas externas não declaradas no projeto; menor é melhor.",
    ),
    metric(
        "reflection-usage-rate",
        "generation_static_diagnostic",
        f"\"{bin_path}\" extrair-geracao --analysis {{analysis_path}} --generation {{generation_path}} --kind reflection-usage-rate",
        0.0,
        regex=metric_regex,
        timeout=120,
        description="Percentual de arquivos que usam reflexão frágil; menor é melhor.",
    ),
    metric(
        "brittle-exception-assertion-rate",
        "generation_static_diagnostic",
        f"\"{bin_path}\" extrair-geracao --analysis {{analysis_path}} --generation {{generation_path}} --kind brittle-exception-assertion-rate",
        0.0,
        regex=metric_regex,
        timeout=120,
        description="Percentual de blocos @Test com assertThrows frágil envolvendo reflexão; menor é melhor.",
    ),
    metric(
        "internal-state-assertion-rate",
        "generation_static_diagnostic",
        f"\"{bin_path}\" extrair-geracao --analysis {{analysis_path}} --generation {{generation_path}} --kind internal-state-assertion-rate",
        0.0,
        regex=metric_regex,
        timeout=120,
        description="Percentual de arquivos com assertivas sobre estado interno/campos privados; menor é melhor.",
    ),
    metric(
        "jacoco-line",
        "coverage",
        f"{mvn_command('test org.jacoco:jacoco-maven-plugin:0.8.12:report')} && \"{bin_path}\" extrair-jacoco --xml target/site/jacoco/jacoco.xml --counter LINE",
        1.0,
        regex=metric_regex,
        timeout=1200,
        outputs=["target/site/jacoco/jacoco.xml"],
        description="Cobertura de linhas via JaCoCo.",
        fallbacks=[
            {
                "name": "explicit-jacoco-agent",
                "command": f"{mvn_command('org.jacoco:jacoco-maven-plugin:0.8.12:prepare-agent test org.jacoco:jacoco-maven-plugin:0.8.12:report')} && \"{bin_path}\" extrair-jacoco --xml target/site/jacoco/jacoco.xml --counter LINE",
                "expected_outputs": ["target/site/jacoco/jacoco.xml"],
                "timeout_seconds": 1200,
                "description": "Injeta o agente JaCoCo quando o projeto não gera relatório no fluxo principal.",
            }
        ],
    ),
    metric(
        "jacoco-branch",
        "coverage",
        f"{mvn_command('test org.jacoco:jacoco-maven-plugin:0.8.12:report')} && \"{bin_path}\" extrair-jacoco --xml target/site/jacoco/jacoco.xml --counter BRANCH",
        1.0,
        regex=metric_regex,
        timeout=1200,
        outputs=["target/site/jacoco/jacoco.xml"],
        description="Cobertura de branches via JaCoCo.",
        fallbacks=[
            {
                "name": "explicit-jacoco-agent",
                "command": f"{mvn_command('org.jacoco:jacoco-maven-plugin:0.8.12:prepare-agent test org.jacoco:jacoco-maven-plugin:0.8.12:report')} && \"{bin_path}\" extrair-jacoco --xml target/site/jacoco/jacoco.xml --counter BRANCH",
                "expected_outputs": ["target/site/jacoco/jacoco.xml"],
                "timeout_seconds": 1200,
                "description": "Injeta o agente JaCoCo quando o projeto não gera relatório no fluxo principal.",
            }
        ],
    ),
    metric(
        "pit-mutation",
        "mutation",
        f"{mvn_command('-DtimestampedReports=false -DoutputFormats=XML org.pitest:pitest-maven:1.23.0:mutationCoverage')} && \"{bin_path}\" extrair-pit --report-dir target/pit-reports",
        1.0,
        regex=metric_regex,
        timeout=1800,
        outputs=["target/pit-reports"],
        description="Mutation score via PIT.",
        fallbacks=[
            {
                "name": "pit-with-target-classes",
                "command": f"{mvn_command('-DtimestampedReports=false -DoutputFormats=XML -DtargetClasses=* org.pitest:pitest-maven:1.23.0:mutationCoverage')} && \"{bin_path}\" extrair-pit --report-dir target/pit-reports",
                "expected_outputs": ["target/pit-reports"],
                "timeout_seconds": 1800,
                "description": "Executa PIT com targetClasses explícito quando o plugin não infere alvos.",
            }
        ],
    ),
]

config = {
    "version": "1",
    "project": {
        "root": str(projects[0]["root"]),
        "include": ["src/main/java", "."],
        "exclude": [".git", "target", "build", "generated", "tests", "docs/javadoc"],
        "test_framework": "infer",
    },
    "pipeline": {
        "output_dir": str(round_dir / "runs"),
        "save_prompts": True,
        "max_methods": 0,
        "llm_mode": "direct",
        "deep_validation_subset_size": 0,
        "judge_model": "",
    },
    "models": {
        "openai_main": {
            "provider": "openai_compatible",
            "model": os.environ["OPENAI_MODEL"],
            "base_url": os.environ["OPENAI_BASE_URL"],
            "api_key_env": "OPENAI_API_KEY",
            "execution_backend": os.environ["OPENAI_EXECUTION_BACKEND"],
            "endpoint": os.environ["OPENAI_ENDPOINT"],
            "batch_completion_window": os.environ["OPENAI_BATCH_COMPLETION_WINDOW"],
            "timeout_seconds": 240,
            "max_retries": 2,
            "reasoning_effort": os.environ["OPENAI_REASONING_EFFORT"],
            "prompt_cache_retention": "24h",
            "service_tier": "auto",
            "max_output_tokens": 0,
        }
    },
    "metrics": metrics,
    "phase_two": {
        "visualization_title": "Rodada artigo: WIT/expaths em seis projetos Java",
        "execution_mode": os.environ["PHASE_TWO_EXECUTION_MODE"],
        "projects": phase_projects,
    },
}

runtime_config.parent.mkdir(parents=True, exist_ok=True)
with runtime_config.open("w", encoding="utf-8") as handle:
    json.dump(config, handle, indent=2, ensure_ascii=False)
    handle.write("\n")

print(f"manifest={manifest_path}")
print(f"runtime_config={runtime_config}")
print(f"candidate_slices={len(manifest_rows)}")
PY

log "validando alinhamento dos candidatos sem executar build"
set +e
"$BIN" preflight-segunda-fase --config "$CANDIDATE_CONFIG" > "$ALIGNMENT_PREFLIGHT_LOG"
PREFLIGHT_STATUS=$?
set -e
ALIGNMENT_REPORT="$(awk -F':' '/Relatório preflight/ {sub(/^[[:space:]]+/, "", $2); print $2}' "$ALIGNMENT_PREFLIGHT_LOG" | tail -1)"
if [[ -z "$ALIGNMENT_REPORT" || ! -f "$ALIGNMENT_REPORT" ]]; then
  cat "$ALIGNMENT_PREFLIGHT_LOG" >&2
  printf 'erro: não foi possível localizar o relatório de alinhamento dos candidatos.\n' >&2
  exit 1
fi
if [[ "$PREFLIGHT_STATUS" -ne 0 ]]; then
  log "preflight de candidatos encontrou entradas não alinhadas; selecionando apenas as válidas"
fi

export ALIGNMENT_REPORT
python3 <<'PY'
import csv
import json
import os
import sys
from pathlib import Path

baseline_dir = Path(os.environ["BASELINE_DIR"])
candidate_manifest = Path(os.environ["CANDIDATE_MANIFEST"])
candidate_config = Path(os.environ["CANDIDATE_CONFIG"])
manifest_path = Path(os.environ["MANIFEST"])
runtime_config = Path(os.environ["RUNTIME_CONFIG"])
alignment_report = Path(os.environ["ALIGNMENT_REPORT"])
final_count = int(os.environ["FINAL_SLICES_PER_PROJECT"])

with candidate_manifest.open("r", encoding="utf-8", newline="") as handle:
    rows = list(csv.DictReader(handle))
fieldnames = rows[0].keys() if rows else []
with candidate_config.open("r", encoding="utf-8") as handle:
    config = json.load(handle)
with alignment_report.open("r", encoding="utf-8") as handle:
    report = json.load(handle)

status = {
    project["project_key"]: int(project.get("aligned_method_count") or 0)
    for project in report.get("projects", [])
}
projects_by_key = {project["key"]: project for project in config["phase_two"]["projects"]}
source_projects = []
for row in rows:
    key = row["source_project_key"]
    if key not in source_projects:
        source_projects.append(key)

final_rows = []
final_projects = []
pending_writes = []
for source_key in source_projects:
    candidates = [row for row in rows if row["source_project_key"] == source_key]
    candidates.sort(key=lambda row: int(row["slice_index"]))
    aligned = [row for row in candidates if status.get(row["slice_key"]) == 1]
    if len(aligned) < final_count:
        print(
            f"erro: {source_key} possui somente {len(aligned)} candidatos alinháveis; esperado {final_count}. "
            f"Aumente CANDIDATE_SLICES_PER_PROJECT.",
            file=sys.stderr,
        )
        sys.exit(1)
    for index, row in enumerate(aligned[:final_count], start=1):
        old_key = row["slice_key"]
        old_path = Path(row["baseline_slice"])
        old_content = old_path.read_text(encoding="utf-8")
        new_key = f"{source_key}-s{index:03d}"
        new_path = baseline_dir / source_key / f"wit_filtered.slice-{index:03d}.json"
        new_row = dict(row)
        new_row["slice_key"] = new_key
        new_row["slice_index"] = f"{index:03d}"
        new_row["baseline_slice"] = str(new_path)
        final_rows.append(new_row)

        project = dict(projects_by_key[old_key])
        project["key"] = new_key
        project["label"] = f"{row['source_project_label']} slice {index:03d}"
        project["wit_analysis_path"] = str(new_path)
        final_projects.append(project)
        pending_writes.append((new_path, old_content))

for path, content in pending_writes:
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(content, encoding="utf-8")

manifest_path.parent.mkdir(parents=True, exist_ok=True)
with manifest_path.open("w", encoding="utf-8", newline="") as handle:
    writer = csv.DictWriter(handle, fieldnames=fieldnames)
    writer.writeheader()
    writer.writerows(final_rows)

config["phase_two"]["projects"] = final_projects
runtime_config.parent.mkdir(parents=True, exist_ok=True)
with runtime_config.open("w", encoding="utf-8") as handle:
    json.dump(config, handle, indent=2, ensure_ascii=False)
    handle.write("\n")

print(f"manifest={manifest_path}")
print(f"runtime_config={runtime_config}")
print(f"final_slices={len(final_rows)}")
PY

log "manifesto estatístico: $MANIFEST"
log "config runtime: $RUNTIME_CONFIG"
log "slices por projeto: $FINAL_SLICES_PER_PROJECT"
log "candidatos por projeto avaliados: $CANDIDATE_SLICES_PER_PROJECT"
log "modo de execução: $PHASE_TWO_EXECUTION_MODE"
log "modelo: $OPENAI_MODEL"
log "preparo concluído sem chamada à OpenAI"

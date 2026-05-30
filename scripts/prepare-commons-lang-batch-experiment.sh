#!/usr/bin/env bash

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
RUN_STAMP="${RUN_STAMP:-$(date -u +%Y%m%dT%H%M%SZ)}"
EXPERIMENT_ROOT="${EXPERIMENT_ROOT:-$ROOT_DIR/generated/experiments/commons-lang-batch-study}"
RUN_DIR="${RUN_DIR:-$EXPERIMENT_ROOT/${RUN_STAMP}_commons_lang_batch_gpt54mini_strict1call}"
CONFIG_DIR="$ROOT_DIR/generated/configs"
RUNTIME_CONFIG="${RUNTIME_CONFIG:-$CONFIG_DIR/commons-lang-batch.runtime.json}"
CANDIDATE_CONFIG="$RUN_DIR/commons-lang-candidates.runtime.json"
CANDIDATE_MANIFEST="$RUN_DIR/commons-lang-candidates-manifest.csv"
MANIFEST="${MANIFEST:-$RUN_DIR/commons-lang-manifest.csv}"
REQUESTS_JSONL="${REQUESTS_JSONL:-$RUN_DIR/requests_${RUN_STAMP}_openai_batch_generation.jsonl}"
PREFLIGHT_LOG="$RUN_DIR/preflight_${RUN_STAMP}_commons_lang.log"
CANDIDATE_PREFLIGHT_LOG="$RUN_DIR/preflight_${RUN_STAMP}_commons_lang_candidates.log"
BASELINE_LOG="$RUN_DIR/baseline_maven_test.log"
BASELINE_METRICS_JSON="$RUN_DIR/baseline_metrics.json"
BASELINE_METRICS_CSV="$RUN_DIR/baseline_metrics.csv"
BIN="$ROOT_DIR/bin/witup"

COMMONS_LANG_ROOT="${COMMONS_LANG_ROOT:-$ROOT_DIR/generated/repos/commons-lang}"
COMMONS_LANG_URL="${COMMONS_LANG_URL:-https://github.com/apache/commons-lang.git}"
COMMONS_LANG_COMMIT="${COMMONS_LANG_COMMIT:-90e0a9bb234683abb502a6b61f36848bb4d65aa6}"
COMMONS_LANG_WIT="${COMMONS_LANG_WIT:-$ROOT_DIR/resources/wit-replication-package/data/output/commons-lang/wit_filtered.json}"

STATISTICAL_SEED="${STATISTICAL_SEED:-20260529}"
SLICES_PER_PROJECT="${SLICES_PER_PROJECT:-20}"
CANDIDATE_SLICES="${CANDIDATE_SLICES:-80}"
EXPECTED_REQUESTS="${EXPECTED_REQUESTS:-40}"
GENERATION_MODEL="${GENERATION_MODEL:-openai_main}"
OPENAI_MODEL="${OPENAI_MODEL:-gpt-5.4-mini}"
OPENAI_BASE_URL="${OPENAI_BASE_URL:-https://api.openai.com/v1}"
OPENAI_REASONING_EFFORT="${OPENAI_REASONING_EFFORT:-medium}"
OPENAI_BATCH_COMPLETION_WINDOW="${OPENAI_BATCH_COMPLETION_WINDOW:-24h}"
PHASE_TWO_EXECUTION_MODE="${PHASE_TWO_EXECUTION_MODE:-strict_1call}"
MAVEN_REPO_LOCAL="${MAVEN_REPO_LOCAL:-$ROOT_DIR/generated/m2-repo}"
MAVEN_PROFILE_ARGS="${MAVEN_PROFILE_ARGS:--Denforcer.skip=true -Drat.skip=true -Dcheckstyle.skip=true -Dspotbugs.skip=true}"

log() {
  printf '[commons-lang/prepare] %s\n' "$*"
}

configure_java17() {
  if [[ -x /usr/libexec/java_home ]]; then
    local java17_home
    if java17_home="$(/usr/libexec/java_home -v 17 2>/dev/null)" && [[ -n "$java17_home" ]]; then
      export JAVA_HOME="$java17_home"
      export PATH="$JAVA_HOME/bin:$PATH"
      log "JAVA_HOME=$JAVA_HOME"
      return 0
    fi
  fi
  if [[ -n "${JAVA_HOME:-}" && -x "$JAVA_HOME/bin/java" ]]; then
    export PATH="$JAVA_HOME/bin:$PATH"
    log "JAVA_HOME=$JAVA_HOME"
    return 0
  fi
  log "aviso: Java 17 não detectado automaticamente; usando java disponível no PATH"
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
    if ! git cat-file -e "$repo_commit^{tree}" >/dev/null 2>&1; then
      git fetch --depth 1 origin "$repo_commit" >/dev/null 2>&1 || git fetch origin "$repo_commit" >/dev/null 2>&1
    fi
    git checkout --detach "$repo_commit" >/dev/null
  )
}

resolve_overview() {
  local root="$1"
  for candidate in README.md README.adoc README.rst README.txt; do
    if [[ -f "$root/$candidate" ]]; then
      printf '%s' "$root/$candidate"
      return 0
    fi
  done
  printf '%s' "$COMMONS_LANG_WIT"
}

run_maven_in_project() {
  local project_root="$1"
  shift
  (
    cd "$project_root"
    if [[ -x ./mvnw ]]; then
      ./mvnw "$@"
    else
      mvn "$@"
    fi
  )
}

build_cli() {
  log "compilando CLI em $BIN"
  mkdir -p "$(dirname "$BIN")"
  (
    cd "$ROOT_DIR"
    GOCACHE="$ROOT_DIR/.gocache" GOMODCACHE="$ROOT_DIR/.gomodcache" go build -o "$BIN" ./cmd/witup
  )
}

run_baseline_tests() {
  log "rodando testes oficiais do Commons Lang para métricas-base"
  local -a args
  args=(-q "-Dmaven.repo.local=$MAVEN_REPO_LOCAL")
  if [[ -n "$MAVEN_PROFILE_ARGS" ]]; then
    read -r -a extra_args <<< "$MAVEN_PROFILE_ARGS"
    args+=("${extra_args[@]}")
  fi
  args+=(test)

  set +e
  run_maven_in_project "$COMMONS_LANG_ROOT" "${args[@]}" > "$BASELINE_LOG" 2>&1
  local status=$?
  set -e

  python3 - "$status" "$COMMONS_LANG_ROOT" "$BASELINE_LOG" "$BASELINE_METRICS_JSON" "$BASELINE_METRICS_CSV" <<'PY'
import csv
import json
import sys
import xml.etree.ElementTree as ET
from pathlib import Path

exit_code = int(sys.argv[1])
root = Path(sys.argv[2])
log_path = Path(sys.argv[3])
json_path = Path(sys.argv[4])
csv_path = Path(sys.argv[5])

totals = {"tests": 0, "failures": 0, "errors": 0, "skipped": 0, "time_seconds": 0.0}
files = sorted(root.glob("**/target/surefire-reports/TEST-*.xml"))
for path in files:
    try:
        suite = ET.parse(path).getroot()
    except ET.ParseError:
        continue
    for key in ("tests", "failures", "errors", "skipped"):
        totals[key] += int(float(suite.attrib.get(key, "0") or 0))
    totals["time_seconds"] += float(suite.attrib.get("time", "0") or 0)

executed = totals["tests"] - totals["skipped"]
passed = max(executed - totals["failures"] - totals["errors"], 0)
pass_rate = (passed / executed * 100.0) if executed else 0.0
payload = {
    "project_key": "commons-lang",
    "commit": "90e0a9bb234683abb502a6b61f36848bb4d65aa6",
    "maven_exit_code": exit_code,
    "success": exit_code == 0 and totals["failures"] == 0 and totals["errors"] == 0,
    "surefire_report_count": len(files),
    "log_path": str(log_path),
    **totals,
    "executed": executed,
    "passed": passed,
    "pass_rate": round(pass_rate, 4),
}
json_path.parent.mkdir(parents=True, exist_ok=True)
json_path.write_text(json.dumps(payload, indent=2, ensure_ascii=False) + "\n", encoding="utf-8")
with csv_path.open("w", encoding="utf-8", newline="") as handle:
    writer = csv.writer(handle)
    writer.writerow(["metric", "value"])
    for key, value in payload.items():
        writer.writerow([key, value])
PY

  if [[ "$status" -ne 0 ]]; then
    log "testes-base falharam; métricas registradas em $BASELINE_METRICS_JSON"
    return "$status"
  fi
}

extract_preflight_report() {
  local log_path="$1"
  python3 - "$log_path" <<'PY'
import re
import sys
from pathlib import Path

text = Path(sys.argv[1]).read_text(encoding="utf-8", errors="replace")
matches = re.findall(r"Relatório preflight\s*:\s*(.+)", text)
print(matches[-1].strip() if matches else "")
PY
}

mkdir -p "$RUN_DIR" "$CONFIG_DIR" "$MAVEN_REPO_LOCAL"
configure_java17
export MAVEN_REPO_LOCAL MAVEN_PROFILE_ARGS

if [[ ! -f "$COMMONS_LANG_WIT" ]]; then
  printf 'erro: baseline WIT não encontrado: %s\n' "$COMMONS_LANG_WIT" >&2
  exit 1
fi

ensure_git_checkout "$COMMONS_LANG_ROOT" "$COMMONS_LANG_URL" "$COMMONS_LANG_COMMIT"
COMMONS_LANG_OVERVIEW="$(resolve_overview "$COMMONS_LANG_ROOT")"
build_cli
run_baseline_tests

export ROOT_DIR RUN_DIR RUNTIME_CONFIG CANDIDATE_CONFIG CANDIDATE_MANIFEST MANIFEST BIN
export COMMONS_LANG_ROOT COMMONS_LANG_WIT COMMONS_LANG_COMMIT COMMONS_LANG_OVERVIEW
export STATISTICAL_SEED SLICES_PER_PROJECT CANDIDATE_SLICES OPENAI_MODEL OPENAI_BASE_URL OPENAI_REASONING_EFFORT OPENAI_BATCH_COMPLETION_WINDOW PHASE_TWO_EXECUTION_MODE MAVEN_REPO_LOCAL MAVEN_PROFILE_ARGS

log "gerando candidatos determinísticos a partir do wit_filtered.json"
python3 <<'PY'
import copy
import csv
import json
import os
import random
import shlex
from pathlib import Path

root_dir = Path(os.environ["ROOT_DIR"])
run_dir = Path(os.environ["RUN_DIR"])
candidate_config = Path(os.environ["CANDIDATE_CONFIG"])
candidate_manifest = Path(os.environ["CANDIDATE_MANIFEST"])
project_root = Path(os.environ["COMMONS_LANG_ROOT"])
baseline_path = Path(os.environ["COMMONS_LANG_WIT"])
commit = os.environ["COMMONS_LANG_COMMIT"]
overview = Path(os.environ["COMMONS_LANG_OVERVIEW"])
seed = int(os.environ["STATISTICAL_SEED"])
candidate_count = int(os.environ["CANDIDATE_SLICES"])
bin_path = Path(os.environ["BIN"])
maven_repo = Path(os.environ["MAVEN_REPO_LOCAL"])
maven_profile_args = os.environ.get("MAVEN_PROFILE_ARGS", "").strip()

with baseline_path.open("r", encoding="utf-8") as handle:
    baseline = json.load(handle)
if baseline.get("commitHash") != commit:
    raise SystemExit(f"commitHash do baseline é {baseline.get('commitHash')}, esperado {commit}")

def relative_source_path(raw_path):
    normalized = raw_path.replace("\\", "/")
    marker = "/commons-lang/"
    if marker in normalized:
        return normalized.split(marker, 1)[1]
    marker = "commons-lang/"
    if marker in normalized:
        return normalized.split(marker, 1)[1]
    if "src/main/java/" in normalized:
        return normalized[normalized.index("src/main/java/"):]
    return normalized.rsplit("/", 1)[-1]

def method_parts(signature):
    head = signature.split("(", 1)[0]
    if "." not in head:
        return "", head
    return head.rsplit(".", 1)

def grouped_candidates():
    groups = {}
    for class_index, klass in enumerate(baseline.get("classes", [])):
        class_path = klass.get("path", "")
        relative_path = relative_source_path(class_path)
        if not (project_root / relative_path).exists():
            continue
        for method in klass.get("methods", []) or []:
            signature = (method.get("qualifiedSignature") or "").strip()
            if not signature:
                continue
            key = (class_path, signature)
            if key not in groups:
                groups[key] = {
                    "class_index": class_index,
                    "class_path": class_path,
                    "relative_path": relative_path,
                    "signature": signature,
                    "methods": [],
                }
            groups[key]["methods"].append(method)
    return list(groups.values())

def write_slice(candidate, index, base_dir):
    slice_dir = base_dir / "baseline_slices" / "_candidates"
    slice_dir.mkdir(parents=True, exist_ok=True)
    slice_path = slice_dir / f"wit_filtered.slice-{index:03d}.json"
    sliced = copy.deepcopy(baseline)
    source_class = copy.deepcopy(baseline["classes"][candidate["class_index"]])
    source_class["methods"] = copy.deepcopy(candidate["methods"])
    sliced["classes"] = [source_class]
    with slice_path.open("w", encoding="utf-8") as handle:
        json.dump(sliced, handle, indent=2, ensure_ascii=False)
        handle.write("\n")
    return slice_path

def shell_join(parts):
    return " ".join(shlex.quote(str(part)) for part in parts if str(part))

common_maven_args = [f"-Dmaven.repo.local={maven_repo}"]
if maven_profile_args:
    common_maven_args.extend(shlex.split(maven_profile_args))

def mvn_command(goals):
    args = shell_join(["-q", *common_maven_args])
    return f"if [ -x ./mvnw ]; then ./mvnw {args} {goals}; else mvn {args} {goals}; fi"

def metric(name, kind, command, weight, scale=100.0, value_regex="WITUP_METRIC=([0-9]+(?:\\\\.[0-9]+)?)", outputs=None, timeout=600, description=""):
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
    if value_regex:
        payload["value_regex"] = value_regex
    if outputs:
        payload["expected_outputs"] = outputs
    return payload

metrics = [
    metric("test-compilation", "build", mvn_command("-DskipTests test-compile"), 0.5, value_regex="", description="Compila a suíte gerada."),
    metric("unit-tests", "tests", f"{mvn_command('test')} && {shlex.quote(str(bin_path))} extrair-surefire --report-dir target/surefire-reports", 1.0, outputs=["target/surefire-reports"], description="Executa a suíte unitária gerada."),
    metric("test-pass-rate", "tests", f"{mvn_command('test')} && {shlex.quote(str(bin_path))} extrair-surefire --report-dir target/surefire-reports --kind pass-rate", 0.8, outputs=["target/surefire-reports"], description="Percentual de testes aprovados via Surefire."),
    metric("target-method-coverage", "generation_static", f"{shlex.quote(str(bin_path))} extrair-geracao --analysis {{analysis_path}} --generation {{generation_path}} --kind target-method-coverage", 0.8, description="Percentual de métodos-alvo com pelo menos um teste associado."),
    metric("assertive-tests-rate", "generation_static", f"{shlex.quote(str(bin_path))} extrair-geracao --analysis {{analysis_path}} --generation {{generation_path}} --kind assertive-tests-rate", 0.6, description="Percentual de métodos de teste com pelo menos uma assertiva."),
    metric("exception-assertion-rate", "generation_static", f"{shlex.quote(str(bin_path))} extrair-geracao --analysis {{analysis_path}} --generation {{generation_path}} --kind exception-assertion-rate", 0.6, description="Percentual de testes focados em assertivas de exceção."),
    metric("jacoco-line", "coverage", f"{mvn_command('test org.jacoco:jacoco-maven-plugin:0.8.12:report')} && {shlex.quote(str(bin_path))} extrair-jacoco --xml target/site/jacoco/jacoco.xml --counter LINE", 1.0, outputs=["target/site/jacoco/jacoco.xml"], description="Cobertura de linhas via JaCoCo."),
    metric("jacoco-branch", "coverage", f"{mvn_command('test org.jacoco:jacoco-maven-plugin:0.8.12:report')} && {shlex.quote(str(bin_path))} extrair-jacoco --xml target/site/jacoco/jacoco.xml --counter BRANCH", 1.0, outputs=["target/site/jacoco/jacoco.xml"], description="Cobertura de branches via JaCoCo."),
    metric("pit-mutation", "mutation", f"{mvn_command('-DtimestampedReports=false -DoutputFormats=XML org.pitest:pitest-maven:1.23.0:mutationCoverage')} && {shlex.quote(str(bin_path))} extrair-pit --report-dir target/pit-reports", 0.7, outputs=["target/pit-reports"], timeout=1800, description="Mutation score via PIT."),
]

candidates = grouped_candidates()
random.Random(seed).shuffle(candidates)
if len(candidates) < candidate_count:
    raise SystemExit(f"Commons Lang tem só {len(candidates)} candidatos locais; esperado {candidate_count}")

manifest_rows = []
phase_projects = []
for index, candidate in enumerate(candidates[:candidate_count], start=1):
    slice_key = f"commons-lang-s{index:03d}"
    slice_path = write_slice(candidate, index, run_dir)
    container, method_name = method_parts(candidate["signature"])
    first_method = candidate["methods"][0]
    manifest_rows.append({
        "source_project_key": "commons-lang",
        "source_project_label": "Apache Commons Lang",
        "slice_key": slice_key,
        "slice_index": f"{index:03d}",
        "qualified_signature": candidate["signature"],
        "container_name": container,
        "method_name": method_name,
        "source_file": candidate["relative_path"],
        "line": str(first_method.get("line", "")),
        "throwing_line": str(first_method.get("throwingLine", "")),
        "expath_count": str(len(candidate["methods"])),
        "commit_hash": baseline.get("commitHash", ""),
        "baseline_original": str(baseline_path),
        "baseline_slice": str(slice_path),
        "seed": str(seed),
    })
    phase_projects.append({
        "key": slice_key,
        "label": f"Apache Commons Lang slice {index:03d}",
        "root": str(project_root),
        "wit_analysis_path": str(slice_path),
        "overview_file": str(overview),
        "include": ["src/main/java", "."],
        "exclude": [".git", "target", "build", "generated", "tests", "docs/javadoc"],
        "test_framework": "infer",
    })

candidate_manifest.parent.mkdir(parents=True, exist_ok=True)
fieldnames = ["source_project_key", "source_project_label", "slice_key", "slice_index", "qualified_signature", "container_name", "method_name", "source_file", "line", "throwing_line", "expath_count", "commit_hash", "baseline_original", "baseline_slice", "seed"]
with candidate_manifest.open("w", encoding="utf-8", newline="") as handle:
    writer = csv.DictWriter(handle, fieldnames=fieldnames)
    writer.writeheader()
    writer.writerows(manifest_rows)

config = {
    "version": "1",
    "project": {
        "root": str(project_root),
        "include": ["src/main/java", "."],
        "exclude": [".git", "target", "build", "generated", "tests", "docs/javadoc"],
        "test_framework": "infer",
    },
    "pipeline": {
        "output_dir": str(run_dir / "candidate-preflight"),
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
            "execution_backend": "batch",
            "endpoint": "/v1/responses",
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
        "visualization_title": "Commons Lang: WIT_CONTEXT vs DIRECT_TESTS",
        "execution_mode": os.environ["PHASE_TWO_EXECUTION_MODE"],
        "projects": phase_projects,
    },
}
candidate_config.parent.mkdir(parents=True, exist_ok=True)
candidate_config.write_text(json.dumps(config, indent=2, ensure_ascii=False) + "\n", encoding="utf-8")
PY

log "validando alinhamento dos candidatos sem build"
set +e
"$BIN" preflight-segunda-fase --config "$CANDIDATE_CONFIG" > "$CANDIDATE_PREFLIGHT_LOG"
CANDIDATE_PREFLIGHT_STATUS=$?
set -e
if [[ "$CANDIDATE_PREFLIGHT_STATUS" -ne 0 ]]; then
  log "preflight de candidatos encontrou entradas não prontas; selecionando apenas as alinháveis"
fi
CANDIDATE_PREFLIGHT_REPORT="$(extract_preflight_report "$CANDIDATE_PREFLIGHT_LOG")"
if [[ -z "$CANDIDATE_PREFLIGHT_REPORT" || ! -f "$CANDIDATE_PREFLIGHT_REPORT" ]]; then
  cat "$CANDIDATE_PREFLIGHT_LOG" >&2
  printf 'erro: não foi possível localizar o relatório de preflight dos candidatos.\n' >&2
  exit 1
fi

export CANDIDATE_PREFLIGHT_REPORT
log "selecionando $SLICES_PER_PROJECT slices alinháveis"
python3 <<'PY'
import csv
import json
import os
import shutil
from pathlib import Path

run_dir = Path(os.environ["RUN_DIR"])
runtime_config = Path(os.environ["RUNTIME_CONFIG"])
manifest = Path(os.environ["MANIFEST"])
candidate_config = Path(os.environ["CANDIDATE_CONFIG"])
candidate_manifest = Path(os.environ["CANDIDATE_MANIFEST"])
preflight_report = Path(os.environ["CANDIDATE_PREFLIGHT_REPORT"])
expected = int(os.environ["SLICES_PER_PROJECT"])

with candidate_config.open("r", encoding="utf-8") as handle:
    config = json.load(handle)
with candidate_manifest.open("r", encoding="utf-8", newline="") as handle:
    rows = list(csv.DictReader(handle))
with preflight_report.open("r", encoding="utf-8") as handle:
    report = json.load(handle)

ready = {
    project["project_key"]
    for project in report.get("projects", [])
    if project.get("ready") and int(project.get("aligned_method_count") or 0) > 0
}
rows_by_key = {row["slice_key"]: row for row in rows}
projects_by_key = {project["key"]: project for project in config["phase_two"]["projects"]}
selected_keys = [row["slice_key"] for row in rows if row["slice_key"] in ready]
if len(selected_keys) < expected:
    raise SystemExit(f"somente {len(selected_keys)} slices alinháveis; esperado {expected}")

final_dir = run_dir / "baseline_slices"
final_dir.mkdir(parents=True, exist_ok=True)
selected_rows = []
selected_projects = []
for new_index, old_key in enumerate(selected_keys[:expected], start=1):
    new_key = f"commons-lang-s{new_index:03d}"
    row = dict(rows_by_key[old_key])
    project = dict(projects_by_key[old_key])
    source_slice = Path(row["baseline_slice"])
    target_slice = final_dir / f"wit_filtered.slice-{new_index:03d}.json"
    shutil.copyfile(source_slice, target_slice)
    row["slice_key"] = new_key
    row["slice_index"] = f"{new_index:03d}"
    row["baseline_slice"] = str(target_slice)
    project["key"] = new_key
    project["label"] = f"Apache Commons Lang slice {new_index:03d}"
    project["wit_analysis_path"] = str(target_slice)
    selected_rows.append(row)
    selected_projects.append(project)

fieldnames = list(rows[0].keys())
manifest.parent.mkdir(parents=True, exist_ok=True)
with manifest.open("w", encoding="utf-8", newline="") as handle:
    writer = csv.DictWriter(handle, fieldnames=fieldnames)
    writer.writeheader()
    writer.writerows(selected_rows)

config["pipeline"]["output_dir"] = str(run_dir / "runs")
config["phase_two"]["projects"] = selected_projects
runtime_config.parent.mkdir(parents=True, exist_ok=True)
runtime_config.write_text(json.dumps(config, indent=2, ensure_ascii=False) + "\n", encoding="utf-8")
PY

log "validando configuração final JSON"
python3 -m json.tool "$RUNTIME_CONFIG" >/dev/null

log "executando preflight final com build check"
"$BIN" preflight-segunda-fase --config "$RUNTIME_CONFIG" --check-build | tee "$PREFLIGHT_LOG"

log "gerando JSONL de requests Batch sem submeter à OpenAI"
"$BIN" preparar-batch-segunda-fase \
  --config "$RUNTIME_CONFIG" \
  --generation-model "$GENERATION_MODEL" \
  --requests "$REQUESTS_JSONL"

REQUEST_COUNT="$(python3 - "$REQUESTS_JSONL" <<'PY'
import sys
from pathlib import Path
print(sum(1 for _ in Path(sys.argv[1]).open("r", encoding="utf-8")))
PY
)"
if [[ "$REQUEST_COUNT" != "$EXPECTED_REQUESTS" ]]; then
  printf 'erro: esperado %s requests, gerado %s em %s\n' "$EXPECTED_REQUESTS" "$REQUEST_COUNT" "$REQUESTS_JSONL" >&2
  exit 1
fi

log "baseline_metrics_json=$BASELINE_METRICS_JSON"
log "baseline_metrics_csv=$BASELINE_METRICS_CSV"
log "manifest=$MANIFEST"
log "runtime_config=$RUNTIME_CONFIG"
log "requests_jsonl=$REQUESTS_JSONL"
printf 'Para submeter a rodada paga via Batch, execute:\n'
printf '  RUN_DIR=%q RUNTIME_CONFIG=%q MANIFEST=%q GENERATION_MODEL=%q REQUESTS_JSONL=%q CONFIRMAR_EXECUCAO_PAGA=sim %q\n' \
  "$RUN_DIR" "$RUNTIME_CONFIG" "$MANIFEST" "$GENERATION_MODEL" "$REQUESTS_JSONL" "$ROOT_DIR/scripts/submit-article-main-batch.sh"

#!/usr/bin/env bash

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
CONFIG_DIR="$ROOT_DIR/generated/configs"
OUTPUT_DIR="$ROOT_DIR/generated/fase-dois"
BIN="$ROOT_DIR/bin/witup"
RUNTIME_CONFIG="$CONFIG_DIR/fase-dois-guava-commons.runtime.json"
MAVEN_REPO_LOCAL="${MAVEN_REPO_LOCAL:-$ROOT_DIR/generated/m2-repo}"

log() {
  printf '[fase-dois] %s\n' "$*"
}

require_env() {
  local name="$1"
  if [[ -z "${!name:-}" ]]; then
    printf 'erro: variável obrigatória ausente: %s\n' "$name" >&2
    exit 1
  fi
}

resolve_java_home() {
  local fallback=""
  if [[ -n "${JAVA_HOME:-}" ]] && [[ -x "${JAVA_HOME}/bin/java" ]] && ! "${JAVA_HOME}/bin/java" -version 2>&1 | grep -qi 'graalvm'; then
    printf '%s' "$JAVA_HOME"
    return 0
  fi
  if [[ -n "${JAVA_HOME:-}" ]] && [[ -x "${JAVA_HOME}/bin/java" ]]; then
    fallback="$JAVA_HOME"
  fi
  if command -v /usr/libexec/java_home >/dev/null 2>&1; then
    if JAVA_CANDIDATE="$(/usr/libexec/java_home -v 17 2>/dev/null)" && [[ -n "$JAVA_CANDIDATE" ]] && ! "$JAVA_CANDIDATE/bin/java" -version 2>&1 | grep -qi 'graalvm'; then
      printf '%s' "$JAVA_CANDIDATE"
      return 0
    fi
    if JAVA_CANDIDATE="$(/usr/libexec/java_home -v 17 2>/dev/null)" && [[ -n "$JAVA_CANDIDATE" ]] && [[ -z "$fallback" ]]; then
      fallback="$JAVA_CANDIDATE"
    fi
    if JAVA_CANDIDATE="$(/usr/libexec/java_home -v 11 2>/dev/null)" && [[ -n "$JAVA_CANDIDATE" ]] && ! "$JAVA_CANDIDATE/bin/java" -version 2>&1 | grep -qi 'graalvm'; then
      printf '%s' "$JAVA_CANDIDATE"
      return 0
    fi
    if JAVA_CANDIDATE="$(/usr/libexec/java_home -v 11 2>/dev/null)" && [[ -n "$JAVA_CANDIDATE" ]] && [[ -z "$fallback" ]]; then
      fallback="$JAVA_CANDIDATE"
    fi
  fi
  if [[ -n "$fallback" ]]; then
    printf '%s' "$fallback"
    return 0
  fi
  return 1
}

require_env "OPENAI_API_KEY"
require_env "GUAVA_ROOT"
require_env "GUAVA_WIT_ANALYSIS"
require_env "COMMONS_COLLECTIONS_ROOT"
require_env "COMMONS_COLLECTIONS_WIT_ANALYSIS"

OPENAI_MODEL="${OPENAI_MODEL:-o4-mini-2025-04-16}"
OPENAI_JUDGE_MODEL="${OPENAI_JUDGE_MODEL:-$OPENAI_MODEL}"
OPENAI_BASE_URL="${OPENAI_BASE_URL:-https://api.openai.com/v1}"
OPENAI_REASONING_EFFORT="${OPENAI_REASONING_EFFORT:-medium}"
PHASE_TWO_EXECUTION_MODE="${PHASE_TWO_EXECUTION_MODE:-repair_1retry}"

for path in "$GUAVA_ROOT" "$COMMONS_COLLECTIONS_ROOT"; do
  if [[ ! -d "$path" ]]; then
    printf 'erro: diretório não encontrado: %s\n' "$path" >&2
    exit 1
  fi
done
for path in "$GUAVA_WIT_ANALYSIS" "$COMMONS_COLLECTIONS_WIT_ANALYSIS"; do
  if [[ ! -f "$path" ]]; then
    printf 'erro: arquivo WIT não encontrado: %s\n' "$path" >&2
    exit 1
  fi
done

if ! JAVA_HOME_RESOLVIDO="$(resolve_java_home)"; then
  printf 'erro: não encontrei um JDK 17/11 não-Graal. Exporte JAVA_HOME antes de rodar.\n' >&2
  exit 1
fi

mkdir -p "$CONFIG_DIR" "$OUTPUT_DIR" "$ROOT_DIR/bin"
mkdir -p "$MAVEN_REPO_LOCAL"

log "usando JAVA_HOME: $JAVA_HOME_RESOLVIDO"
if "$JAVA_HOME_RESOLVIDO/bin/java" -version 2>&1 | grep -qi 'graalvm'; then
  log "aviso: usando GraalVM por falta de alternativa não-Graal; PIT pode precisar de ajuste posterior"
fi
log "usando repositório Maven local: $MAVEN_REPO_LOCAL"
log "compilando a CLI"
(
  cd "$ROOT_DIR"
  GOCACHE="$ROOT_DIR/.gocache" go build -o "$BIN" ./cmd/witup
)

cat > "$RUNTIME_CONFIG" <<EOF
{
  "version": "1",
  "project": {
    "root": "$GUAVA_ROOT",
    "include": ["src/main/java", "."],
    "exclude": [".git", "target", "build", "generated", "tests"],
    "test_framework": "infer"
  },
  "pipeline": {
    "output_dir": "$OUTPUT_DIR",
    "save_prompts": true,
    "max_methods": 0,
    "llm_mode": "direct",
    "deep_validation_subset_size": 0,
    "judge_model": "openai_judge"
  },
  "models": {
    "openai_main": {
      "provider": "openai_compatible",
      "model": "$OPENAI_MODEL",
      "base_url": "$OPENAI_BASE_URL",
      "api_key_env": "OPENAI_API_KEY",
      "timeout_seconds": 180,
      "max_retries": 2,
      "reasoning_effort": "$OPENAI_REASONING_EFFORT",
      "prompt_cache_retention": "24h",
      "service_tier": "auto",
      "max_output_tokens": 0
    },
    "openai_judge": {
      "provider": "openai_compatible",
      "model": "$OPENAI_JUDGE_MODEL",
      "base_url": "$OPENAI_BASE_URL",
      "api_key_env": "OPENAI_API_KEY",
      "timeout_seconds": 180,
      "max_retries": 2,
      "reasoning_effort": "$OPENAI_REASONING_EFFORT",
      "prompt_cache_retention": "24h",
      "service_tier": "auto",
      "max_output_tokens": 0
    }
  },
  "metrics": [
    {
      "name": "test-compilation",
      "kind": "build",
      "command": "sh -lc 'if [ -x ./mvnw ]; then ./mvnw -q -DskipTests test-compile; else mvn -q -DskipTests test-compile; fi'",
      "weight": 0.5,
      "scale": 100.0,
      "working_directory": ".",
      "description": "Compila a suíte gerada antes de executar o restante das métricas"
    },
    {
      "name": "unit-tests",
      "kind": "tests",
      "command": "sh -lc 'if [ -x ./mvnw ]; then ./mvnw -q test; else mvn -q test; fi' && \"$BIN\" extrair-surefire --report-dir target/surefire-reports",
      "weight": 1.0,
      "value_regex": "WITUP_METRIC=([0-9]+(?:\\\\.[0-9]+)?)",
      "scale": 100.0,
      "working_directory": ".",
      "expected_outputs": ["target/surefire-reports"],
      "description": "Executa os testes unitários após a geração"
    },
    {
      "name": "test-pass-rate",
      "kind": "tests",
      "command": "sh -lc 'if [ -x ./mvnw ]; then ./mvnw -q test; else mvn -q test; fi' && \"$BIN\" extrair-surefire --report-dir target/surefire-reports --kind pass-rate",
      "weight": 0.8,
      "value_regex": "WITUP_METRIC=([0-9]+(?:\\\\.[0-9]+)?)",
      "scale": 100.0,
      "working_directory": ".",
      "expected_outputs": ["target/surefire-reports"],
      "description": "Percentual de testes aprovados via Surefire"
    },
    {
      "name": "target-method-coverage",
      "kind": "generation_static",
      "command": "\"$BIN\" extrair-geracao --analysis {analysis_path} --generation {generation_path} --kind target-method-coverage",
      "weight": 0.8,
      "value_regex": "WITUP_METRIC=([0-9]+(?:\\\\.[0-9]+)?)",
      "scale": 100.0,
      "working_directory": ".",
      "description": "Percentual de métodos-alvo com pelo menos um teste associado"
    },
    {
      "name": "assertive-tests-rate",
      "kind": "generation_static",
      "command": "\"$BIN\" extrair-geracao --analysis {analysis_path} --generation {generation_path} --kind assertive-tests-rate",
      "weight": 0.6,
      "value_regex": "WITUP_METRIC=([0-9]+(?:\\\\.[0-9]+)?)",
      "scale": 100.0,
      "working_directory": ".",
      "description": "Percentual de métodos de teste com pelo menos uma assertiva"
    },
    {
      "name": "exception-assertion-rate",
      "kind": "generation_static",
      "command": "\"$BIN\" extrair-geracao --analysis {analysis_path} --generation {generation_path} --kind exception-assertion-rate",
      "weight": 0.6,
      "value_regex": "WITUP_METRIC=([0-9]+(?:\\\\.[0-9]+)?)",
      "scale": 100.0,
      "working_directory": ".",
      "description": "Percentual de testes focados em assertivas de exceção"
    },
    {
      "name": "jacoco-line",
      "kind": "coverage",
      "command": "sh -lc 'if [ -x ./mvnw ]; then ./mvnw -q test org.jacoco:jacoco-maven-plugin:0.8.12:report; else mvn -q test org.jacoco:jacoco-maven-plugin:0.8.12:report; fi' && \"$BIN\" extrair-jacoco --xml target/site/jacoco/jacoco.xml --counter LINE",
      "weight": 1.0,
      "value_regex": "WITUP_METRIC=([0-9]+(?:\\\\.[0-9]+)?)",
      "scale": 100.0,
      "working_directory": ".",
      "expected_outputs": ["target/site/jacoco/jacoco.xml"],
      "description": "Cobertura de linhas via JaCoCo",
      "fallbacks": [
        {
          "name": "reuse-jacoco-report",
          "command": "[ -f target/site/jacoco/jacoco.xml ] && \"$BIN\" extrair-jacoco --xml target/site/jacoco/jacoco.xml --counter LINE",
          "expected_outputs": ["target/site/jacoco/jacoco.xml"],
          "description": "Reaproveita um relatório JaCoCo já gerado pelo projeto"
        },
        {
          "name": "explicit-jacoco-agent",
          "command": "sh -lc 'if [ -x ./mvnw ]; then ./mvnw -q org.jacoco:jacoco-maven-plugin:0.8.12:prepare-agent test org.jacoco:jacoco-maven-plugin:0.8.12:report; else mvn -q org.jacoco:jacoco-maven-plugin:0.8.12:prepare-agent test org.jacoco:jacoco-maven-plugin:0.8.12:report; fi' && \"$BIN\" extrair-jacoco --xml target/site/jacoco/jacoco.xml --counter LINE",
          "expected_outputs": ["target/site/jacoco/jacoco.xml"],
          "description": "Injeta o agente JaCoCo apenas quando o projeto não o configura"
        }
      ]
    },
    {
      "name": "jacoco-branch",
      "kind": "coverage",
      "command": "sh -lc 'if [ -x ./mvnw ]; then ./mvnw -q test org.jacoco:jacoco-maven-plugin:0.8.12:report; else mvn -q test org.jacoco:jacoco-maven-plugin:0.8.12:report; fi' && \"$BIN\" extrair-jacoco --xml target/site/jacoco/jacoco.xml --counter BRANCH",
      "weight": 1.0,
      "value_regex": "WITUP_METRIC=([0-9]+(?:\\\\.[0-9]+)?)",
      "scale": 100.0,
      "working_directory": ".",
      "expected_outputs": ["target/site/jacoco/jacoco.xml"],
      "description": "Cobertura de branches via JaCoCo",
      "fallbacks": [
        {
          "name": "reuse-jacoco-report",
          "command": "[ -f target/site/jacoco/jacoco.xml ] && \"$BIN\" extrair-jacoco --xml target/site/jacoco/jacoco.xml --counter BRANCH",
          "expected_outputs": ["target/site/jacoco/jacoco.xml"],
          "description": "Reaproveita um relatório JaCoCo já gerado pelo projeto"
        },
        {
          "name": "explicit-jacoco-agent",
          "command": "sh -lc 'if [ -x ./mvnw ]; then ./mvnw -q org.jacoco:jacoco-maven-plugin:0.8.12:prepare-agent test org.jacoco:jacoco-maven-plugin:0.8.12:report; else mvn -q org.jacoco:jacoco-maven-plugin:0.8.12:prepare-agent test org.jacoco:jacoco-maven-plugin:0.8.12:report; fi' && \"$BIN\" extrair-jacoco --xml target/site/jacoco/jacoco.xml --counter BRANCH",
          "expected_outputs": ["target/site/jacoco/jacoco.xml"],
          "description": "Injeta o agente JaCoCo apenas quando o projeto não o configura"
        }
      ]
    },
    {
      "name": "pit-mutation",
      "kind": "mutation",
      "command": "sh -lc 'if [ -x ./mvnw ]; then ./mvnw -q -DtimestampedReports=false -DoutputFormats=XML org.pitest:pitest-maven:1.23.0:mutationCoverage; else mvn -q -DtimestampedReports=false -DoutputFormats=XML org.pitest:pitest-maven:1.23.0:mutationCoverage; fi' && \"$BIN\" extrair-pit --report-dir target/pit-reports",
      "weight": 1.2,
      "value_regex": "WITUP_METRIC=([0-9]+(?:\\\\.[0-9]+)?)",
      "scale": 100.0,
      "working_directory": ".",
      "expected_outputs": ["target/pit-reports/mutations.xml"],
      "description": "Mutation score via PIT",
      "fallbacks": [
        {
          "name": "reuse-pit-report",
          "command": "find target/pit-reports -name mutations.xml -print -quit | grep -q . && \"$BIN\" extrair-pit --report-dir target/pit-reports",
          "expected_outputs": ["target/pit-reports/mutations.xml"],
          "description": "Reaproveita um relatório PIT já materializado"
        }
      ]
    }
  ],
  "phase_two": {
    "execution_mode": "$PHASE_TWO_EXECUTION_MODE",
    "visualization_title": "Segunda fase: Google Guava vs Apache Commons Collections",
    "projects": [
      {
        "key": "guava",
        "label": "Google Guava",
        "root": "$GUAVA_ROOT",
        "wit_analysis_path": "$GUAVA_WIT_ANALYSIS",
        "overview_file": "$GUAVA_ROOT/README.md",
        "test_framework": "infer"
      },
      {
        "key": "commons-collections",
        "label": "Apache Commons Collections",
        "root": "$COMMONS_COLLECTIONS_ROOT",
        "wit_analysis_path": "$COMMONS_COLLECTIONS_WIT_ANALYSIS",
        "overview_file": "$COMMONS_COLLECTIONS_ROOT/README.md",
        "test_framework": "infer"
      }
    ]
  }
}
EOF

python3 -m json.tool "$RUNTIME_CONFIG" >/dev/null

log "configuração runtime gerada em $RUNTIME_CONFIG"
export JAVA_HOME="$JAVA_HOME_RESOLVIDO"
export PATH="$JAVA_HOME/bin:$PATH"
export MAVEN_OPTS="-Dmaven.repo.local=$MAVEN_REPO_LOCAL ${MAVEN_OPTS:-}"
log "executando preflight da segunda fase"
"$BIN" preflight-segunda-fase --config "$RUNTIME_CONFIG" --check-build
log "executando a segunda fase"
"$BIN" executar-segunda-fase --config "$RUNTIME_CONFIG" --generation-model openai_main

#!/usr/bin/env bash
# run-article-evaluate-docker.sh
#
# Executa DENTRO do container article-evaluator.
# Etapa 2 do pipeline: gera runtime.json e executa witup avaliar-batch-segunda-fase.
#
# Requer que a Etapa 1 (run-article-setup-docker.sh) já tenha sido executada,
# ou que REPOS_ROOT e WIT_BASELINES_ROOT já contenham os projetos e baselines.
#
# Variáveis obrigatórias:
#   RESPONSES_JSONL : caminho para o batch JSONL de respostas OpenAI
#
# Variáveis opcionais:
#   REPOS_ROOT        : projetos Java clonados  (default: /data/generated/repos)
#   WIT_BASELINES_ROOT: baselines WIT           (default: /data/generated/wit-baselines)
#   MAVEN_LOCAL_REPO  : repo Maven local        (default: /data/generated/m2-repo)
#   ERRORS_JSONL      : batch JSONL de erros    (default: "")
#   OUTPUT_DIR        : onde salvar resultados  (default: /data/generated/results/article-eval)
#   RUN_STAMP         : timestamp da rodada     (default: auto)
#   GENERATION_MODEL  : chave do modelo         (default: openai_main)
#   MAVEN_PROFILE_ARGS: args extra de perfil Maven (default: -P!java14+ -Denforcer.skip=true)

set -euo pipefail

RESPONSES_JSONL="${RESPONSES_JSONL:-}"
ERRORS_JSONL="${ERRORS_JSONL:-}"
REPOS_ROOT="${REPOS_ROOT:-/data/generated/repos}"
WIT_BASELINES_ROOT="${WIT_BASELINES_ROOT:-/data/generated/wit-baselines}"
MAVEN_LOCAL_REPO="${MAVEN_LOCAL_REPO:-/data/generated/m2-repo}"
OUTPUT_DIR="${OUTPUT_DIR:-/data/generated/results/article-eval}"
RUN_STAMP="${RUN_STAMP:-$(date -u +%Y%m%dT%H%M%SZ)}"
GENERATION_MODEL="${GENERATION_MODEL:-openai_main}"
MAVEN_PROFILE_ARGS="${MAVEN_PROFILE_ARGS:- -P!java14+ -Denforcer.skip=true -Drat.skip=true -Dcheckstyle.skip=true -Dpmd.skip=true -Dspotbugs.skip=true -Dmaven.compiler.source=8 -Dmaven.compiler.target=8}"

WITUP=/usr/local/bin/witup
CONFIG_PATH="${OUTPUT_DIR}/rodada-artigo.runtime.json"

log() { printf '[article-evaluate] %s\n' "$*"; }
err() { printf '[article-evaluate] ERRO: %s\n' "$*" >&2; exit 1; }

# ── Validações ────────────────────────────────────────────────────────────────
[[ -n "${RESPONSES_JSONL}" ]] || err "RESPONSES_JSONL é obrigatório. Ex: -e RESPONSES_JSONL=/data/batch.jsonl"
[[ -f "${RESPONSES_JSONL}" ]] || err "arquivo não encontrado: ${RESPONSES_JSONL}"

for key in commons-io commons-lang h2database httpcomponents-client jackson-databind joda-time logging-log4j2; do
  local_root="${REPOS_ROOT}/${key}"
  [[ -d "${local_root}" ]] || err "projeto não clonado: ${local_root} — execute primeiro: docker compose run --rm article-setup"
  baseline="${WIT_BASELINES_ROOT}/${key}/wit_filtered.json"
  [[ -f "${baseline}" ]] || err "baseline WIT ausente: ${baseline} — execute primeiro: docker compose run --rm article-setup"
done

mkdir -p "${OUTPUT_DIR}"

log "=== Avaliação do experimento do artigo ==="
log "RESPONSES_JSONL=${RESPONSES_JSONL}"
log "OUTPUT_DIR=${OUTPUT_DIR}"
log "RUN_STAMP=${RUN_STAMP}"
log "GENERATION_MODEL=${GENERATION_MODEL}"
log ""

# ── Passo 1: Gerar runtime.json ───────────────────────────────────────────────
log "Passo 1/2: Gerando rodada-artigo.runtime.json..."

# h2database tem o pom.xml em h2/ (subdiretório)
H2_ROOT="${REPOS_ROOT}/h2database/h2"
[[ -f "${H2_ROOT}/pom.xml" ]] || H2_ROOT="${REPOS_ROOT}/h2database"

python3 - << PYEOF
import json, os

witup        = "/usr/local/bin/witup"
repos        = "${REPOS_ROOT}"
wit_data     = "${WIT_BASELINES_ROOT}"
m2           = "${MAVEN_LOCAL_REPO}"
profile_args = "${MAVEN_PROFILE_ARGS}".strip()
h2_root      = "${H2_ROOT}"

profile_str = f" {profile_args}" if profile_args else ""

def mvn(goals):
    return (
        f"sh -lc 'if [ -x ./mvnw ]; then "
        f"./mvnw -q -Dmaven.repo.local={m2}{profile_str} {goals}; "
        f"else "
        f"mvn -q -Dmaven.repo.local={m2}{profile_str} {goals}; "
        f"fi'"
    )

regex = r"WITUP_METRIC=([0-9]+(?:\.[0-9]+)?)"

def metric(name, kind, command, weight, scale=100.0, timeout=600,
           outputs=None, description="", fallbacks=None, regex_=None):
    p = {
        "name": name, "kind": kind, "command": command,
        "weight": weight, "scale": scale,
        "working_directory": ".", "timeout_seconds": timeout,
        "description": description,
    }
    if regex_ or kind not in ("build",):
        p["value_regex"] = regex_ or regex
    if outputs:
        p["expected_outputs"] = outputs
    if fallbacks:
        p["fallbacks"] = fallbacks
    return p

metrics = [
    metric("test-compilation", "build",
           mvn("-DskipTests test-compile"), 0.5,
           description="Compila a suíte gerada."),
    metric("unit-tests", "tests",
           f"{mvn('test')} && \"{witup}\" extrair-surefire --report-dir target/surefire-reports",
           1.0, timeout=900, outputs=["target/surefire-reports"],
           description="Executa os testes unitários gerados."),
    metric("test-pass-rate", "tests",
           f"{mvn('test')} && \"{witup}\" extrair-surefire --report-dir target/surefire-reports --kind pass-rate",
           0.8, timeout=900, outputs=["target/surefire-reports"],
           description="Percentual de testes aprovados."),
    metric("target-method-coverage", "generation_static",
           f'"{witup}" extrair-geracao --analysis {{analysis_path}} --generation {{generation_path}} --kind target-method-coverage',
           0.8, timeout=120,
           description="Percentual de métodos-alvo com pelo menos um teste associado."),
    metric("assertive-tests-rate", "generation_static",
           f'"{witup}" extrair-geracao --analysis {{analysis_path}} --generation {{generation_path}} --kind assertive-tests-rate',
           0.6, timeout=120,
           description="Percentual de testes com assertivas explícitas."),
    metric("exception-assertion-rate", "generation_static",
           f'"{witup}" extrair-geracao --analysis {{analysis_path}} --generation {{generation_path}} --kind exception-assertion-rate',
           0.6, timeout=120,
           description="Percentual de testes focados em exceções."),
    metric("valid-java-rate", "generation_static_diagnostic",
           f'"{witup}" extrair-geracao --analysis {{analysis_path}} --generation {{generation_path}} --kind valid-java-rate',
           0.0, timeout=120,
           description="Arquivos gerados com Java puro válido."),
    metric("package-path-valid-rate", "generation_static_diagnostic",
           f'"{witup}" extrair-geracao --analysis {{analysis_path}} --generation {{generation_path}} --kind package-path-valid-rate',
           0.0, timeout=120,
           description="Arquivos cujo package é compatível com o caminho relativo."),
    metric("test-method-presence-rate", "generation_static_diagnostic",
           f'"{witup}" extrair-geracao --analysis {{analysis_path}} --generation {{generation_path}} --kind test-method-presence-rate',
           0.0, timeout=120,
           description="Arquivos com pelo menos um método @Test."),
    metric("target-invocation-rate", "generation_static_diagnostic",
           f'"{witup}" extrair-geracao --analysis {{analysis_path}} --generation {{generation_path}} --kind target-invocation-rate',
           0.0, timeout=120,
           description="Métodos-alvo aparentemente invocados pelo teste gerado."),
    metric("forbidden-dependency-rate", "generation_static_diagnostic",
           f'"{witup}" extrair-geracao --analysis {{analysis_path}} --generation {{generation_path}} --project-root {{project_root}} --kind forbidden-dependency-rate',
           0.0, timeout=120,
           description="Arquivos com dependências externas não declaradas no projeto."),
    metric("reflection-usage-rate", "generation_static_diagnostic",
           f'"{witup}" extrair-geracao --analysis {{analysis_path}} --generation {{generation_path}} --kind reflection-usage-rate',
           0.0, timeout=120,
           description="Arquivos com uso de reflexão frágil."),
    metric("brittle-exception-assertion-rate", "generation_static_diagnostic",
           f'"{witup}" extrair-geracao --analysis {{analysis_path}} --generation {{generation_path}} --kind brittle-exception-assertion-rate',
           0.0, timeout=120,
           description="Testes com assertThrows frágil via reflexão."),
    metric("internal-state-assertion-rate", "generation_static_diagnostic",
           f'"{witup}" extrair-geracao --analysis {{analysis_path}} --generation {{generation_path}} --kind internal-state-assertion-rate',
           0.0, timeout=120,
           description="Arquivos com assertivas sobre estado interno/campos privados."),
    metric("jacoco-line", "coverage",
           f"{mvn('test org.jacoco:jacoco-maven-plugin:0.8.12:report')} && \"{witup}\" extrair-jacoco --xml target/site/jacoco/jacoco.xml --counter LINE",
           1.0, timeout=1200, outputs=["target/site/jacoco/jacoco.xml"],
           description="Cobertura de linhas via JaCoCo.",
           fallbacks=[{
               "name": "explicit-jacoco-agent",
               "command": f"{mvn('org.jacoco:jacoco-maven-plugin:0.8.12:prepare-agent test org.jacoco:jacoco-maven-plugin:0.8.12:report')} && \"{witup}\" extrair-jacoco --xml target/site/jacoco/jacoco.xml --counter LINE",
               "expected_outputs": ["target/site/jacoco/jacoco.xml"],
               "timeout_seconds": 1200,
               "description": "Injeta agente JaCoCo explicitamente.",
           }]),
    metric("jacoco-branch", "coverage",
           f"{mvn('test org.jacoco:jacoco-maven-plugin:0.8.12:report')} && \"{witup}\" extrair-jacoco --xml target/site/jacoco/jacoco.xml --counter BRANCH",
           1.0, timeout=1200, outputs=["target/site/jacoco/jacoco.xml"],
           description="Cobertura de branches via JaCoCo.",
           fallbacks=[{
               "name": "explicit-jacoco-agent",
               "command": f"{mvn('org.jacoco:jacoco-maven-plugin:0.8.12:prepare-agent test org.jacoco:jacoco-maven-plugin:0.8.12:report')} && \"{witup}\" extrair-jacoco --xml target/site/jacoco/jacoco.xml --counter BRANCH",
               "expected_outputs": ["target/site/jacoco/jacoco.xml"],
               "timeout_seconds": 1200,
               "description": "Injeta agente JaCoCo explicitamente.",
           }]),
    metric("pit-mutation", "mutation",
           f"{mvn('-DtimestampedReports=false -DoutputFormats=XML -DexcludedClasses=org.pitest.*,org.junit.*,org.hamcrest.* org.pitest:pitest-maven:1.23.0:mutationCoverage')} && \"{witup}\" extrair-pit --report-dir target/pit-reports",
           1.0, timeout=1800, outputs=["target/pit-reports"],
           description="Mutation score via PIT.",
           fallbacks=[{
               "name": "pit-explicit-targets",
               "command": f"{mvn('-DtimestampedReports=false -DoutputFormats=XML -DtargetClasses=org.apache.*,org.h2.*,com.fasterxml.*,org.joda.*,org.springframework.* -DexcludedClasses=org.pitest.* org.pitest:pitest-maven:1.23.0:mutationCoverage')} && \"{witup}\" extrair-pit --report-dir target/pit-reports",
               "expected_outputs": ["target/pit-reports"],
               "timeout_seconds": 1800,
               "description": "PIT com targetClasses explícito para evitar auto-mutação.",
           }]),
]

projects = [
    {"key": "commons-io",            "label": "Apache Commons IO",
     "root": f"{repos}/commons-io",
     "overview": f"{repos}/commons-io/README.md",
     "wit": f"{wit_data}/commons-io/wit_filtered.json"},
    {"key": "commons-lang",          "label": "Apache Commons Lang",
     "root": f"{repos}/commons-lang",
     "overview": f"{repos}/commons-lang/README.md",
     "wit": f"{wit_data}/commons-lang/wit_filtered.json"},
    # h2database: layout não-padrão src/main/org/h2/ (sem java/)
    # baseline normalizado para raiz h2/ — paths ficam src/main/org/h2/...
    {"key": "h2database",            "label": "H2 Database",
     "root": h2_root,
     "overview": f"{repos}/h2database/README.md",
     "wit": f"{wit_data}/h2database/wit_filtered.json",
     "include": ["src/main", "src/test"]},
    {"key": "httpcomponents-client", "label": "HttpComponents Client",
     "root": f"{repos}/httpcomponents-client",
     "overview": f"{repos}/httpcomponents-client/README.md",
     "wit": f"{wit_data}/httpcomponents-client/wit_filtered.json"},
    {"key": "jackson-databind",      "label": "Jackson Databind",
     "root": f"{repos}/jackson-databind",
     "overview": f"{repos}/jackson-databind/README.md",
     "wit": f"{wit_data}/jackson-databind/wit_filtered.json"},
    {"key": "joda-time",             "label": "Joda-Time",
     "root": f"{repos}/joda-time",
     "overview": f"{repos}/joda-time/README.md",
     "wit": f"{wit_data}/joda-time/wit_filtered.json"},
    {"key": "logging-log4j2",        "label": "Apache Log4j 2",
     "root": f"{repos}/logging-log4j2",
     # log4j2 usa README.adoc, não README.md
     "overview": f"{repos}/logging-log4j2/README.adoc",
     "wit": f"{wit_data}/logging-log4j2/wit_filtered.json"},
]

import json as _json

def _has_analyses(wit_path):
    """Retorna True se o arquivo WIT tem conteúdo (chave classes, analises ou analyses)."""
    try:
        with open(wit_path) as _f:
            data = _json.load(_f)
        # figshare usa "classes", witup usa "analises"/"analyses"
        for key in ("classes", "analises", "analyses"):
            val = data.get(key)
            if val:
                return True
        # Se for lista direta
        if isinstance(data, list):
            return len(data) > 0
        return False
    except Exception:
        return False

# Excluir projetos cujo baseline WIT está vazio ou ausente
skip_keys = set(os.environ.get("SKIP_PROJECTS", "").split(",")) - {""}
phase_projects = []
for p in projects:
    if p["key"] in skip_keys:
        print(f"[article-evaluate] projeto {p['key']} excluído via SKIP_PROJECTS")
        continue
    if not _has_analyses(p["wit"]):
        print(f"[article-evaluate] AVISO: baseline WIT de {p['key']} está vazio — pulando")
        continue
    phase_projects.append({
        "key":               p["key"],
        "label":             p["label"],
        "root":              p["root"],
        "wit_analysis_path": p["wit"],
        "overview_file":     p["overview"],
        "include":           p.get("include", ["src/main/java", "."]),
        "exclude":           [".git", "target", "build", "generated", "docs/javadoc"],
        "test_framework":    "infer",
    })

print(f"[article-evaluate] projetos selecionados: {[p['key'] for p in phase_projects]}")

config = {
    "version": "1",
    "project": {
        "root":          projects[0]["root"],
        "include":       ["src/main/java", "."],
        "exclude":       [".git", "target", "build", "generated", "docs/javadoc"],
        "test_framework": "infer",
    },
    "pipeline": {
        "output_dir":               "${OUTPUT_DIR}/runs",
        "save_prompts":             True,
        "max_methods":              0,
        "llm_mode":                 "direct",
        "deep_validation_subset_size": 0,
        "judge_model":              "",
    },
    "models": {
        "openai_main": {
            "provider":                "openai_compatible",
            "model":                   "gpt-4.1-mini-2025-04-14",
            "base_url":                "https://api.openai.com/v1",
            "api_key_env":             "OPENAI_API_KEY",
            "execution_backend":       "batch",
            "endpoint":                "/v1/responses",
            "batch_completion_window": "24h",
            "timeout_seconds":         240,
            "max_retries":             2,
            "reasoning_effort":        "low",
            "prompt_cache_retention":  "24h",
            "service_tier":            "auto",
            "max_output_tokens":       0,
            "temperature":             0.0,
        }
    },
    "metrics": metrics,
    "phase_two": {
        "visualization_title": "Rodada artigo: WIT-context vs Direct-tests (7 projetos)",
        "execution_mode":      "strict_1call",
        "projects":            phase_projects,
    },
}

out = "${CONFIG_PATH}"
os.makedirs(os.path.dirname(out), exist_ok=True)
with open(out, "w", encoding="utf-8") as f:
    json.dump(config, f, indent=2, ensure_ascii=False)
    f.write("\n")

print(f"[article-evaluate] runtime.json escrito em {out}")
PYEOF

log "runtime.json gerado em ${CONFIG_PATH}"
log ""

# ── Passo 2: Executar avaliação ───────────────────────────────────────────────
log "Passo 2/2: Executando witup avaliar-batch-segunda-fase..."
log "  responses=${RESPONSES_JSONL}"
log "  output_dir=${OUTPUT_DIR}"
log "  run_stamp=${RUN_STAMP}"
log ""

ERRORS_ARG=""
if [[ -n "${ERRORS_JSONL}" && -f "${ERRORS_JSONL}" ]]; then
  ERRORS_ARG="--errors ${ERRORS_JSONL}"
fi

"${WITUP}" avaliar-batch-segunda-fase \
  --config          "${CONFIG_PATH}" \
  --generation-model "${GENERATION_MODEL}" \
  --responses       "${RESPONSES_JSONL}" \
  ${ERRORS_ARG} \
  --output-dir      "${OUTPUT_DIR}" \
  --run-stamp       "${RUN_STAMP}"

log ""
log "=== Avaliação concluída ==="
log "Resultados em: ${OUTPUT_DIR}"
log ""
log "Arquivos gerados:"
find "${OUTPUT_DIR}" -maxdepth 1 \( -name "*.json" -o -name "*.csv" -o -name "*.html" -o -name "*.md" \) \
  | sort | while read -r f; do
    printf '  %s (%s)\n' "$(basename "$f")" "$(du -sh "$f" | cut -f1)"
  done

#!/usr/bin/env bash
# run-article-setup-docker.sh
#
# Executa DENTRO do container article-evaluator.
# Etapa 1 do pipeline de avaliação do artigo:
#   - Clona os 7 projetos Java nos commits exatos do experimento
#   - Pré-aquece o repositório Maven local (mvn dependency:resolve)
#   - Gera baselines WIT para projetos não presentes no pacote figshare
#   - Grava tudo em /data/generated/ (volume montado do host)
#
# Variáveis opcionais (passadas via docker-compose ou -e):
#   REPOS_ROOT        : onde clonar os projetos (default: /data/generated/repos)
#   WIT_BASELINES_ROOT: onde salvar os baselines WIT (default: /data/generated/wit-baselines)
#   MAVEN_LOCAL_REPO  : repositório Maven local     (default: /data/generated/m2-repo)
#   SKIP_MAVEN_WARMUP : "sim" para pular pré-aquecimento Maven
#   FORCE_RECLONE     : "sim" para forçar re-clone mesmo se já existir

set -euo pipefail

REPOS_ROOT="${REPOS_ROOT:-/data/generated/repos}"
WIT_BASELINES_ROOT="${WIT_BASELINES_ROOT:-/data/generated/wit-baselines}"
MAVEN_LOCAL_REPO="${MAVEN_LOCAL_REPO:-/data/generated/m2-repo}"
SKIP_MAVEN_WARMUP="${SKIP_MAVEN_WARMUP:-nao}"
FORCE_RECLONE="${FORCE_RECLONE:-nao}"

WIT_RUNNER=/opt/wit/run-wit.py
WIT_JAR=/opt/wit/wit.jar
FIGSHARE_DATA=/opt/wit-data

# ── Commits exatos do experimento ─────────────────────────────────────────────
# Cinco projetos com commits definidos em preparar-primeira-rodada-estatistica.sh
COMMONS_IO_URL="https://github.com/apache/commons-io.git"
COMMONS_IO_COMMIT="2ae025fe5c4a7d2046c53072b0898e37a079fe62"

COMMONS_LANG_URL="https://github.com/apache/commons-lang.git"
COMMONS_LANG_COMMIT="90e0a9bb234683abb502a6b61f36848bb4d65aa6"

H2DATABASE_URL="https://github.com/h2database/h2database.git"
H2DATABASE_COMMIT="0ee51f54af8c9d3be10ae58b0ccdeec827942363"

HTTPCLIENT_URL="https://github.com/apache/httpcomponents-client.git"
HTTPCLIENT_COMMIT="29ba623ebeec67cd6e8d940b2fed9151c16e4daa"

JACKSON_URL="https://github.com/FasterXML/jackson-databind.git"
JACKSON_COMMIT="972d5a28ae5a4c012b799ef6da2ffa6fe2291e50"

# Dois projetos sem commit definido nos scripts existentes — usar tags estáveis
JODA_TIME_URL="https://github.com/JodaOrg/joda-time.git"
JODA_TIME_COMMIT="${JODA_TIME_COMMIT:-v2.12.7}"

LOG4J2_URL="https://github.com/apache/logging-log4j2.git"
LOG4J2_COMMIT="${LOG4J2_COMMIT:-rel/2.23.1}"

# ── Helpers ───────────────────────────────────────────────────────────────────
log() { printf '[article-setup] %s\n' "$*"; }
err() { printf '[article-setup] ERRO: %s\n' "$*" >&2; exit 1; }

clone_or_reuse() {
  local key="$1" url="$2" ref="$3"
  local target="${REPOS_ROOT}/${key}"

  if [[ -d "${target}/.git" && "${FORCE_RECLONE}" != "sim" ]]; then
    log "  ${key}: checkout já existe — reutilizando"
    return 0
  fi

  if [[ -d "${target}" && "${FORCE_RECLONE}" == "sim" ]]; then
    log "  ${key}: removendo checkout existente (FORCE_RECLONE=sim)"
    rm -rf "${target}"
  fi

  mkdir -p "$(dirname "${target}")"
  log "  ${key}: clonando ${url} @ ${ref}"
  git clone "${url}" "${target}"
  (
    cd "${target}"
    # Tentar checkout direto (funciona para SHAs e tags já presentes)
    if ! git checkout "${ref}" 2>/dev/null; then
      # Fallback: fetch explícito para SHAs não acessíveis ou tags remotas
      git fetch --depth 1 origin "${ref}" 2>/dev/null \
        || git fetch origin "refs/tags/${ref}:refs/tags/${ref}" 2>/dev/null \
        || git fetch origin "${ref}"
      git checkout "${ref}" 2>/dev/null || git checkout FETCH_HEAD
    fi
  )
  log "  ${key}: clone concluído"
}

maven_warmup() {
  local key="$1" root="$2"
  local maven_args="-q -Dmaven.repo.local=${MAVEN_LOCAL_REPO} -Denforcer.skip=true"
  log "  ${key}: pré-aquecendo dependências Maven..."

  local mvn_cmd="mvn"
  [[ -x "${root}/mvnw" ]] && mvn_cmd="${root}/mvnw"

  (
    cd "${root}"
    # dependency:resolve é rápido e popula o cache sem compilar
    "${mvn_cmd}" ${maven_args} dependency:resolve --fail-at-end 2>/dev/null \
      || log "  ${key}: dependency:resolve retornou erro (ignorado — cache parcial é suficiente)"
  )
}

generate_wit_baseline() {
  local key="$1" root="$2"
  local output_dir="${WIT_BASELINES_ROOT}/${key}"
  local output_json="${output_dir}/wit_filtered.json"

  if [[ -f "${output_json}" && "${FORCE_RECLONE}" != "sim" ]]; then
    log "  ${key}: baseline WIT já existe — pulando geração"
    return 0
  fi

  log "  ${key}: gerando baseline WIT (pode demorar vários minutos)..."
  mkdir -p "${output_dir}"

  WIT_OUTPUT_PATH="${output_dir}" \
    python3 "${WIT_RUNNER}" "${root}" --single-project --include-maybes

  if [[ ! -f "${output_json}" ]]; then
    # WIT grava como wit.json; renomear para wit_filtered.json
    local wit_json
    wit_json="$(find "${output_dir}" -name "wit.json" | head -1)"
    if [[ -n "${wit_json}" ]]; then
      cp "${wit_json}" "${output_json}"
      log "  ${key}: renomeado wit.json → wit_filtered.json"
    else
      err "${key}: WIT não produziu nenhum arquivo de saída em ${output_dir}"
    fi
  fi

  log "  ${key}: baseline WIT gerado em ${output_json}"
}

copy_figshare_baseline() {
  local key="$1"
  local src="${FIGSHARE_DATA}/${key}/wit_filtered.json"
  local dst="${WIT_BASELINES_ROOT}/${key}/wit_filtered.json"

  if [[ -f "${dst}" && "${FORCE_RECLONE}" != "sim" ]]; then
    log "  ${key}: baseline figshare já copiado"
    return 0
  fi

  if [[ -f "${src}" ]]; then
    mkdir -p "$(dirname "${dst}")"
    cp "${src}" "${dst}"
    log "  ${key}: baseline copiado do pacote figshare"
    return 0
  fi

  # Tentar variações de nome no figshare (wit.json)
  local src_alt="${FIGSHARE_DATA}/${key}/wit.json"
  if [[ -f "${src_alt}" ]]; then
    mkdir -p "$(dirname "${dst}")"
    cp "${src_alt}" "${dst}"
    log "  ${key}: baseline (wit.json) copiado do pacote figshare e renomeado"
    return 0
  fi

  return 1  # não encontrado
}

# ── Início ────────────────────────────────────────────────────────────────────
log "=== Configuração do ambiente de avaliação do artigo ==="
log "REPOS_ROOT=${REPOS_ROOT}"
log "WIT_BASELINES_ROOT=${WIT_BASELINES_ROOT}"
log "MAVEN_LOCAL_REPO=${MAVEN_LOCAL_REPO}"
log ""

mkdir -p "${REPOS_ROOT}" "${WIT_BASELINES_ROOT}" "${MAVEN_LOCAL_REPO}"

# ── Passo 1: Clonar projetos ──────────────────────────────────────────────────
log "Passo 1/3: Clonando projetos Java..."
clone_or_reuse "commons-io"           "${COMMONS_IO_URL}"  "${COMMONS_IO_COMMIT}"
clone_or_reuse "commons-lang"         "${COMMONS_LANG_URL}" "${COMMONS_LANG_COMMIT}"
clone_or_reuse "h2database"           "${H2DATABASE_URL}"  "${H2DATABASE_COMMIT}"
clone_or_reuse "httpcomponents-client" "${HTTPCLIENT_URL}"  "${HTTPCLIENT_COMMIT}"
clone_or_reuse "jackson-databind"     "${JACKSON_URL}"     "${JACKSON_COMMIT}"
clone_or_reuse "joda-time"            "${JODA_TIME_URL}"   "${JODA_TIME_COMMIT}"
clone_or_reuse "logging-log4j2"       "${LOG4J2_URL}"      "${LOG4J2_COMMIT}"
log ""

# ── Passo 2: Pré-aquecer Maven ────────────────────────────────────────────────
if [[ "${SKIP_MAVEN_WARMUP}" != "sim" ]]; then
  log "Passo 2/3: Pré-aquecendo dependências Maven (reduz tempo de avaliação)..."
  maven_warmup "commons-io"           "${REPOS_ROOT}/commons-io"
  maven_warmup "commons-lang"         "${REPOS_ROOT}/commons-lang"
  maven_warmup "h2database"           "${REPOS_ROOT}/h2database/h2"  # h2 tem subdiretório h2/
  maven_warmup "httpcomponents-client" "${REPOS_ROOT}/httpcomponents-client"
  maven_warmup "jackson-databind"     "${REPOS_ROOT}/jackson-databind"
  maven_warmup "joda-time"            "${REPOS_ROOT}/joda-time"
  maven_warmup "logging-log4j2"       "${REPOS_ROOT}/logging-log4j2"
  log ""
else
  log "Passo 2/3: pré-aquecimento Maven pulado (SKIP_MAVEN_WARMUP=sim)"
fi

# ── Passo 3: Baselines WIT ────────────────────────────────────────────────────
log "Passo 3/3: Obtendo baselines WIT..."

for key in commons-io commons-lang h2database httpcomponents-client jackson-databind; do
  if ! copy_figshare_baseline "${key}"; then
    log "  ${key}: não encontrado no figshare — gerando com WIT..."
    generate_wit_baseline "${key}" "${REPOS_ROOT}/${key}"
  fi
done

# joda-time e log4j2 geralmente não estão no figshare — gerar
for key in joda-time logging-log4j2; do
  if ! copy_figshare_baseline "${key}"; then
    generate_wit_baseline "${key}" "${REPOS_ROOT}/${key}"
  fi
done

# ── Relatório final ───────────────────────────────────────────────────────────
log ""
log "=== Setup concluído ==="
log ""
log "Projetos disponíveis em: ${REPOS_ROOT}"
log "Baselines WIT em:        ${WIT_BASELINES_ROOT}"
log "Maven local repo em:     ${MAVEN_LOCAL_REPO}"
log ""
log "Execute agora a etapa de avaliação:"
log "  docker compose run --rm article-evaluate"

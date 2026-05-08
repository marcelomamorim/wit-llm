#!/usr/bin/env bash

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
WIT_IMPL_DIR="${ROOT_DIR}/resources/wit-replication-package/implementation"
WIT_RUNNER="${WIT_IMPL_DIR}/run-wit.py"
WIT_JAR="${WIT_IMPL_DIR}/wit.jar"
BASE_PYTHON_BIN="${PYTHON_BIN:-python3}"
PYTHON_BIN="${BASE_PYTHON_BIN}"
OUTPUT_ROOT="${OUTPUT_ROOT:-${ROOT_DIR}/generated/wit-output}"
REPOS_ROOT="${REPOS_ROOT:-${ROOT_DIR}/generated/repos}"
WIT_VENV_DIR="${WIT_VENV_DIR:-${ROOT_DIR}/generated/tools/wit-venv}"

GUAVA_ROOT_DEFAULT="${REPOS_ROOT}/guava"
COMMONS_ROOT_DEFAULT="${REPOS_ROOT}/commons-collections"

GUAVA_ROOT="${GUAVA_ROOT:-${GUAVA_ROOT_DEFAULT}}"
COMMONS_COLLECTIONS_ROOT="${COMMONS_COLLECTIONS_ROOT:-${COMMONS_ROOT_DEFAULT}}"
GUAVA_WIT_PROJECT_ROOT="${GUAVA_WIT_PROJECT_ROOT:-}"
COMMONS_COLLECTIONS_WIT_PROJECT_ROOT="${COMMONS_COLLECTIONS_WIT_PROJECT_ROOT:-}"

GUAVA_GIT_URL="${GUAVA_GIT_URL:-https://github.com/google/guava.git}"
COMMONS_COLLECTIONS_GIT_URL="${COMMONS_COLLECTIONS_GIT_URL:-https://github.com/apache/commons-collections.git}"
GUAVA_SPARSE_SUBDIR="${GUAVA_SPARSE_SUBDIR:-guava}"

GUAVA_GIT_REF="${GUAVA_GIT_REF:-}"
COMMONS_COLLECTIONS_GIT_REF="${COMMONS_COLLECTIONS_GIT_REF:-}"

INCLUDE_MAYBES=1
FORCAR=0
SO_GUAVA=0
SO_COMMONS=0
INSTALAR_DEPS=1

log() {
  printf '[gerar-baselines-wit] %s\n' "$*"
}

erro() {
  printf 'erro: %s\n' "$*" >&2
  exit 1
}

ensure_command() {
  local cmd="$1"
  command -v "$cmd" >/dev/null 2>&1 || erro "comando obrigatório ausente: ${cmd}"
}

python_has_module() {
  local python_bin="$1"
  local module="$2"
  "${python_bin}" - <<PY >/dev/null 2>&1
import importlib
importlib.import_module("${module}")
PY
}

ensure_python_module() {
  local module="$1"
  python_has_module "${PYTHON_BIN}" "${module}" || erro "módulo Python ausente: ${module}"
}

ensure_wit_python() {
  local package_specs=("z3-solver" "sympy" "psutil")
  local required_modules=("z3" "sympy" "psutil")
  local missing=()
  local module=""
  local package=""
  local index=0

  ensure_command "${BASE_PYTHON_BIN}"

  for module in "${required_modules[@]}"; do
    if ! python_has_module "${BASE_PYTHON_BIN}" "${module}"; then
      missing+=("${module}")
    fi
  done

  if [[ "${#missing[@]}" -eq 0 ]]; then
    PYTHON_BIN="${BASE_PYTHON_BIN}"
    return 0
  fi

  if [[ "${INSTALAR_DEPS}" != "1" ]]; then
    erro "faltam módulos Python (${missing[*]}). Reexecute sem --sem-instalar-deps ou instale-os em um ambiente próprio"
  fi

  log "criando/atualizando ambiente virtual local em ${WIT_VENV_DIR}"
  "${BASE_PYTHON_BIN}" -m venv "${WIT_VENV_DIR}" || erro "não foi possível criar o virtualenv em ${WIT_VENV_DIR}"
  PYTHON_BIN="${WIT_VENV_DIR}/bin/python"
  [[ -x "${PYTHON_BIN}" ]] || erro "python do virtualenv não encontrado em ${PYTHON_BIN}"

  log "instalando dependências Python do WIT no virtualenv local"
  "${PYTHON_BIN}" -m pip install --upgrade pip >/dev/null || erro "não foi possível atualizar o pip do virtualenv"
  "${PYTHON_BIN}" -m pip install "${package_specs[@]}" || erro "não foi possível instalar dependências Python do WIT no virtualenv. Verifique conexão de rede ou reexecute informando um virtualenv já preparado em WIT_VENV_DIR"

  for ((index = 0; index < ${#required_modules[@]}; index++)); do
    module="${required_modules[index]}"
    package="${package_specs[index]}"
    python_has_module "${PYTHON_BIN}" "${module}" || erro "dependência ${package} foi instalada, mas o módulo ${module} ainda não está disponível"
  done
}

ensure_git_checkout() {
  local target_dir="$1"
  local repo_url="$2"
  local repo_ref="${3:-}"
  local sparse_subdir="${4:-}"

  if [[ -d "${target_dir}/.git" ]]; then
    return 0
  fi
  if [[ -d "${target_dir}" ]] && [[ -n "$(find "${target_dir}" -mindepth 1 -maxdepth 1 2>/dev/null)" ]]; then
    erro "diretório já existe e não parece ser um checkout git: ${target_dir}"
  fi

  mkdir -p "$(dirname "${target_dir}")"
  log "clonando ${repo_url} em ${target_dir}"

  if [[ -n "${sparse_subdir}" ]]; then
    git clone --depth 1 --filter=blob:none --sparse "${repo_url}" "${target_dir}"
    (
      cd "${target_dir}"
      git sparse-checkout set "${sparse_subdir}"
    )
  else
    git clone --depth 1 "${repo_url}" "${target_dir}"
  fi

  if [[ -n "${repo_ref}" ]]; then
    (
      cd "${target_dir}"
      git fetch --depth 1 origin "${repo_ref}"
      git checkout "${repo_ref}"
    )
  fi
}

resolve_wit_project_root() {
  local project_name="$1"
  local checkout_root="$2"
  local explicit_root="${3:-}"
  local candidate=""

  if [[ -n "${explicit_root}" ]]; then
    [[ -d "${explicit_root}" ]] || erro "raiz explícita do projeto para ${project_name} não encontrada: ${explicit_root}"
    printf '%s\n' "${explicit_root}"
    return 0
  fi

  if [[ "${project_name}" == "guava" ]]; then
    candidate="${checkout_root}/guava"
    if [[ -d "${candidate}" ]] && [[ -f "${candidate}/pom.xml" || -f "${candidate}/build.gradle" || -f "${candidate}/build.gradle.kts" ]]; then
      log "usando subprojeto do Guava em ${candidate}"
      printf '%s\n' "${candidate}"
      return 0
    fi
  fi

  printf '%s\n' "${checkout_root}"
}

run_wit_for_project() {
  local project_name="$1"
  local checkout_root="$2"
  local explicit_project_root="${3:-}"
  local project_root=""
  local output_json="${OUTPUT_ROOT}/${project_name}/wit.json"

  if [[ "${FORCAR}" != "1" && -f "${output_json}" ]]; then
    log "baseline já existe para ${project_name}: ${output_json}"
    return 0
  fi

  project_root="$(resolve_wit_project_root "${project_name}" "${checkout_root}" "${explicit_project_root}")"

  mkdir -p "${OUTPUT_ROOT}"
  export WIT_OUTPUT_PATH="${OUTPUT_ROOT}"

  log "executando WIT para ${project_name}"
  (
    cd "${WIT_IMPL_DIR}"
    local args=("${WIT_RUNNER}" "${project_root}" "--single-project")
    if [[ "${INCLUDE_MAYBES}" == "1" ]]; then
      args+=("--include-maybes")
    fi
    "${PYTHON_BIN}" "${args[@]}"
  )

  if [[ ! -f "${output_json}" ]]; then
    erro "WIT terminou sem produzir ${output_json}"
  fi
  log "baseline gerado para ${project_name}: ${output_json}"
}

uso() {
  cat <<EOF
Uso: $(basename "$0") [opções]

Gera os baselines WIT para Google Guava e Apache Commons Collections.

Opções:
  --forcar              Reexecuta mesmo se o wit.json já existir
  --sem-maybes          Roda sem --include-maybes
  --sem-instalar-deps   Não cria virtualenv nem instala dependências Python
  --so-guava            Gera apenas o baseline do Guava
  --so-commons          Gera apenas o baseline do Commons Collections
  --ajuda               Exibe esta mensagem

Variáveis úteis:
  PYTHON_BIN                    Python base a usar para bootstrap (default: python3)
  OUTPUT_ROOT                   Pasta de saída do WIT (default: generated/wit-output)
  REPOS_ROOT                    Pasta base dos checkouts (default: generated/repos)
  WIT_VENV_DIR                  Virtualenv local do WIT (default: generated/tools/wit-venv)
  GUAVA_ROOT                    Checkout do Guava
  GUAVA_WIT_PROJECT_ROOT        Subprojeto do Guava a analisar (default: <checkout>/guava, se existir)
  GUAVA_SPARSE_SUBDIR           Caminho esparso do checkout do Guava (default: guava)
  COMMONS_COLLECTIONS_ROOT      Checkout do Commons Collections
  COMMONS_COLLECTIONS_WIT_PROJECT_ROOT  Raiz a analisar do Commons Collections (default: checkout inteiro)
  GUAVA_GIT_URL                 Repositório Git do Guava
  COMMONS_COLLECTIONS_GIT_URL   Repositório Git do Commons Collections
  GUAVA_GIT_REF                 Ref opcional do Guava
  COMMONS_COLLECTIONS_GIT_REF   Ref opcional do Commons Collections
EOF
}

while [[ $# -gt 0 ]]; do
  case "$1" in
    --forcar)
      FORCAR=1
      shift
      ;;
    --sem-maybes)
      INCLUDE_MAYBES=0
      shift
      ;;
    --sem-instalar-deps)
      INSTALAR_DEPS=0
      shift
      ;;
    --so-guava)
      SO_GUAVA=1
      shift
      ;;
    --so-commons)
      SO_COMMONS=1
      shift
      ;;
    --ajuda|-h|--help)
      uso
      exit 0
      ;;
    *)
      erro "argumento desconhecido: $1"
      ;;
  esac
done

if [[ "${SO_GUAVA}" == "1" && "${SO_COMMONS}" == "1" ]]; then
  erro "--so-guava e --so-commons não podem ser usados juntos"
fi

ensure_command git
ensure_wit_python

[[ -f "${WIT_RUNNER}" ]] || erro "run-wit.py não encontrado em ${WIT_RUNNER}"
[[ -f "${WIT_JAR}" ]] || erro "wit.jar não encontrado em ${WIT_JAR}"

ensure_python_module z3
ensure_python_module sympy
ensure_python_module psutil

ensure_git_checkout "${GUAVA_ROOT}" "${GUAVA_GIT_URL}" "${GUAVA_GIT_REF}" "${GUAVA_SPARSE_SUBDIR}"
ensure_git_checkout "${COMMONS_COLLECTIONS_ROOT}" "${COMMONS_COLLECTIONS_GIT_URL}" "${COMMONS_COLLECTIONS_GIT_REF}"

if [[ "${SO_COMMONS}" != "1" ]]; then
  run_wit_for_project "guava" "${GUAVA_ROOT}" "${GUAVA_WIT_PROJECT_ROOT}"
fi
if [[ "${SO_GUAVA}" != "1" ]]; then
  run_wit_for_project "commons-collections" "${COMMONS_COLLECTIONS_ROOT}" "${COMMONS_COLLECTIONS_WIT_PROJECT_ROOT}"
fi

log "baselines disponíveis em:"
if [[ "${SO_COMMONS}" != "1" ]]; then
  printf '  - %s\n' "${OUTPUT_ROOT}/guava/wit.json"
fi
if [[ "${SO_GUAVA}" != "1" ]]; then
  printf '  - %s\n' "${OUTPUT_ROOT}/commons-collections/wit.json"
fi

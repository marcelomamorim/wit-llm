#!/usr/bin/env bash
# run-article-pit-docker.sh
#
# Executa DENTRO do container article-evaluator.
# Para cada projeto/cenário:
#   1. Pega os testes já materializados da última rodada
#   2. Copia para o projeto Java
#   3. Compila individualmente — remove arquivos que falham
#   4. Executa JaCoCo (line + branch) + Surefire + PIT nos testes que compilam
#   5. Salva resultados em /data/generated/results/pit-eval/
#
# Requer: article-setup já executado e article-evaluate já executado (testes materializados)

set -uo pipefail

REPOS_ROOT="${REPOS_ROOT:-/data/generated/repos}"
RESULTS_ROOT="${RESULTS_ROOT:-/data/generated/results/article-eval}"
OUTPUT_DIR="${OUTPUT_DIR:-/data/generated/results/pit-eval}"
MAVEN_LOCAL_REPO="${MAVEN_LOCAL_REPO:-/data/generated/m2-repo}"
MVN_ARGS="-q -Dmaven.repo.local=${MAVEN_LOCAL_REPO} -nsu -P!java14+ -Denforcer.skip=true -Drat.skip=true -Dcheckstyle.skip=true -Dpmd.skip=true -Dspotbugs.skip=true -Danimal.sniffer.skip=true -Dmaven.compiler.source=8 -Dmaven.compiler.target=8 -DfailIfNoTests=false -Dmaven.test.failure.ignore=true -Djacoco.skip=false -Dsurefire.useSystemClassLoader=false"
# Nota: JAVA_TOOL_OPTIONS é usado para injetar JaCoCo; para surefire plain use argLine
SUREFIRE_HEAP="-Xmx256m"  # limita heap para que OOM seja exception (não crash JVM)
WITUP=/usr/local/bin/witup

log()  { printf '[pit-eval] %s\n' "$*"; }
warn() { printf '[pit-eval] AVISO: %s\n' "$*"; }
err()  { printf '[pit-eval] ERRO: %s\n' "$*" >&2; }

mkdir -p "${OUTPUT_DIR}"

# Mapeamento projeto → raiz Maven
# h2database: subdir h2/ contém o módulo Maven real
# logging-log4j2: todos os testes gerados são do módulo log4j-api
declare -A PROJECT_ROOTS=(
  ["commons-io"]="${REPOS_ROOT}/commons-io"
  ["commons-lang"]="${REPOS_ROOT}/commons-lang"
  ["h2database"]="${REPOS_ROOT}/h2database/h2"
  ["httpcomponents-client"]="${REPOS_ROOT}/httpcomponents-client"
  ["jackson-databind"]="${REPOS_ROOT}/jackson-databind"
  ["joda-time"]="${REPOS_ROOT}/joda-time"
  ["logging-log4j2"]="${REPOS_ROOT}/logging-log4j2"
)

# Para h2database: verificar se h2/ existe com pom.xml
if [[ ! -f "${REPOS_ROOT}/h2database/h2/pom.xml" ]]; then
  PROJECT_ROOTS["h2database"]="${REPOS_ROOT}/h2database"
fi

# ── Injetar pitest-junit5-plugin no pom.xml do workspace ─────────────────────
# Necessário para projetos cujos testes gerados usam JUnit 5 (Jupiter).
# PIT 1.23 requer plugin separado para detectar JUnit 5; sem ele retorna
# "Please check you have correctly installed the pitest plugin".
inject_pitest_junit5() {
  local pom="$1"
  grep -q "pitest-junit5-plugin" "${pom}" 2>/dev/null && return 0
  python3 - "${pom}" << 'PYEOF'
import sys, re

pom_path = sys.argv[1]
with open(pom_path, encoding='utf-8', errors='replace') as f:
    content = f.read()

if 'pitest-junit5-plugin' in content:
    sys.exit(0)

pitest_plugin = (
    '<plugin>'
    '<groupId>org.pitest</groupId>'
    '<artifactId>pitest-maven</artifactId>'
    '<version>1.23.0</version>'
    '<dependencies>'
    '<dependency>'
    '<groupId>org.pitest</groupId>'
    '<artifactId>pitest-junit5-plugin</artifactId>'
    '<version>1.2.2</version>'
    '</dependency>'
    '</dependencies>'
    '</plugin>'
)

# Injetar DENTRO de <build><plugins> (não <reporting><plugins> nem <pluginManagement>).
# Estratégia: encontrar o </build> da seção principal (antes de <profiles> e <reporting>)
# e injetar antes dele como <plugins>pitest</plugins> ou dentro de <plugins> existente.

# Determinar o limite: início de <reporting> ou <profiles> (o que vier primeiro)
reporting_pos = content.find('<reporting>')
profiles_pos = content.find('<profiles>')
limits = [p for p in [reporting_pos, profiles_pos] if p > 0]
boundary = min(limits) if limits else len(content)

main_section = content[:boundary]

# Buscar o bloco <build>...</build> em todo o documento (alguns poms, ex h2database,
# colocam <profiles> ANTES de <build>)
build_start = content.find('<build')
build_end_rel = content.find('</build>', build_start) if build_start >= 0 else -1

if build_start >= 0 and build_end_rel >= 0:
    build_section = content[build_start:build_end_rel]
    # Encontrar último </plugins> dentro do <build>, fora de <pluginManagement>
    in_pm = 0
    i = 0
    last_plugins_close = -1
    while i < len(build_section):
        if build_section[i:].startswith('<pluginManagement'):
            in_pm += 1
        elif build_section[i:].startswith('</pluginManagement>'):
            in_pm -= 1
        elif build_section[i:].startswith('</plugins>') and in_pm == 0:
            last_plugins_close = i
        i += 1
    if last_plugins_close >= 0:
        abs_idx = build_start + last_plugins_close
        content = content[:abs_idx] + pitest_plugin + content[abs_idx:]
    else:
        # Sem </plugins> fora de pluginManagement: criar antes de </build>
        content = content[:build_end_rel] + '<plugins>' + pitest_plugin + '</plugins>' + content[build_end_rel:]
else:
    # Sem <build>: criar antes do boundary (antes de profiles ou no final)
    content = content[:boundary] + '<build><plugins>' + pitest_plugin + '</plugins></build>' + content[boundary:]

with open(pom_path, 'w', encoding='utf-8') as f:
    f.write(content)
print('ok')
PYEOF
  [[ $? -eq 0 ]] || return 1
  log "  pom.xml: pitest-junit5-plugin injetado"
}

# ── Detectar módulo correto para httpcomponents (multi-módulo) ────────────────
# Os testes wit-context pertencem a httpclient5-fluent (package fluent)
# e direct-tests a httpclient5-cache. Compilar no root não funciona porque
# as classes do submodulo não estão no target/classes da raiz.
detect_httpclient_module() {
  local gen_tests_src="$1"
  local proj_root="$2"
  local module_name=""

  # Tentativa 1: extrair módulo do path (ex: generated-tests/httpclient5-cache/src/test/java)
  if echo "${gen_tests_src}" | grep -qE "/[a-z][a-z0-9-]+/src/test/java$"; then
    module_name=$(echo "${gen_tests_src}" | sed 's|.*/\([a-z][a-z0-9-]*\)/src/test/java$|\1|')
    # Validar que é um subdir real do proj_root
    [[ ! -d "${proj_root}/${module_name}" ]] && module_name=""
  fi

  # Tentativa 2: inferir pelo package do primeiro arquivo Java
  if [[ -z "${module_name}" ]]; then
    local first_java pkg
    first_java=$(find "${gen_tests_src}" -name "*.java" 2>/dev/null | head -1)
    if [[ -n "${first_java}" ]]; then
      pkg=$(grep "^package " "${first_java}" 2>/dev/null | head -1 | awk '{print $2}' | tr -d ';')
      if echo "${pkg}" | grep -q "fluent";   then module_name="httpclient5-fluent"
      elif echo "${pkg}" | grep -q "cache";  then module_name="httpclient5-cache"
      elif echo "${pkg}" | grep -q "win32";  then module_name="httpclient5-win"
      elif echo "${pkg}" | grep -q "testing";then module_name="httpclient5-testing"
      else module_name="httpclient5"
      fi
    fi
  fi

  [[ -d "${proj_root}/${module_name}" ]] && echo "${module_name}" || echo ""
}

run_mvn() {
  local proj_root="$1"; shift
  local mvn_cmd="mvn"
  [[ -x "${proj_root}/mvnw" ]] && mvn_cmd="${proj_root}/mvnw"
  (cd "${proj_root}" && "${mvn_cmd}" ${MVN_ARGS} "$@" 2>&1)
}

# ── Compilar individualmente e filtrar testes que passam ─────────────────────
prune_failing_tests() {
  local proj="$1" proj_root="$2" test_src_dir="$3"
  local java_files kept=0 removed=0

  mapfile -t java_files < <(find "${test_src_dir}" -name "*.java" | sort)
  [[ ${#java_files[@]} -eq 0 ]] && { warn "${proj}: nenhum arquivo Java encontrado em ${test_src_dir}"; return 1; }

  log "${proj}: testando compilação individual de ${#java_files[@]} arquivos..."

  local tmp_cp
  # Obter classpath do projeto para compilação isolada.
  # Se proj_root é um submodulo (ex: workspace/log4j-api), MVN_ARGS pode conter
  # "-pl log4j-api -am" que falha quando executado de dentro do submodulo.
  # Solução: tentar primeiro no proj_root, depois no pai (que tem o contexto correto).
  tmp_cp=$(run_mvn "${proj_root}" dependency:build-classpath -Dmdep.outputFile=/tmp/cp_${proj}.txt -q 2>/dev/null \
    && cat /tmp/cp_${proj}.txt 2>/dev/null || echo "")
  if [[ -z "${tmp_cp}" ]]; then
    local parent_root; parent_root=$(dirname "${proj_root}")
    if [[ -f "${parent_root}/pom.xml" ]]; then
      tmp_cp=$(run_mvn "${parent_root}" dependency:build-classpath -Dmdep.outputFile=/tmp/cp_${proj}_parent.txt -q 2>/dev/null \
        && cat /tmp/cp_${proj}_parent.txt 2>/dev/null || echo "")
    fi
  fi
  # Adicionar classes do projeto ao classpath
  local proj_classes="${proj_root}/target/classes"
  local junit5_jar
  junit5_jar=$(find "${MAVEN_LOCAL_REPO}" -name 'junit-jupiter-api-*.jar' 2>/dev/null | head -1 | sed "s|${MAVEN_LOCAL_REPO}/||")
  # Sempre incluir proj_classes se existir (independente de tmp_cp)
  if [[ -d "${proj_classes}" ]]; then
    tmp_cp="${proj_classes}:${MAVEN_LOCAL_REPO}/${junit5_jar}:${tmp_cp}"
  elif [[ -n "${tmp_cp}" ]]; then
    tmp_cp="${MAVEN_LOCAL_REPO}/${junit5_jar}:${tmp_cp}"
  fi

  for java_file in "${java_files[@]}"; do
    local compile_out
    if ! compile_out=$(javac -cp "${proj_root}/target/classes:${tmp_cp}" \
      -sourcepath "${test_src_dir}" \
      "${java_file}" 2>&1); then
      warn "  removendo (erro compilação): $(basename "${java_file}")"
      rm -f "${java_file}"
      ((removed++)) || true
    else
      ((kept++)) || true
    fi
  done

  log "${proj}: mantidos=${kept} removidos=${removed}"
  [[ ${kept} -gt 0 ]]
}

# ── Loop principal por projeto e cenário ─────────────────────────────────────
SUMMARY_FILE="${OUTPUT_DIR}/pit_results_summary.tsv"
printf "projeto\tcenario\tarquivos_originais\tarquivos_ok\tcompilacao\tsurefire_tests\tpass_rate\tjacoco_line\tjacoco_branch\tpit_mutation\n" > "${SUMMARY_FILE}"

# ── Fix especial: jackson-databind usa parent SNAPSHOT indisponível no Sonatype ──
# Instalar o POM da versão release equivalente no repo local para que Maven encontre o parent.
log "Pre-fix logging-log4j2: extraindo versão \${revision} para patches de workspace..."
# log4j-api usa CI-friendly versioning: ${revision} só resolve com o parent POM presente.
# Solução: substituir ${revision} pelo valor real diretamente no pom.xml do workspace.
LOG4J2_REVISION=$(python3 -c "
import re
content = open('${REPOS_ROOT}/logging-log4j2/pom.xml').read()
m = re.search(r'<revision>([^<]+)</revision>', content)
print(m.group(1) if m else '2.23.1')
" 2>/dev/null || echo "2.23.1")
log "  log4j2: revisão=${LOG4J2_REVISION}"

log "Pre-fix jackson-databind: instalando parent POM release no repo local..."
for jackson_release in "2.13.0" "2.13.1" "2.12.7"; do
  if mvn -q -Dmaven.repo.local="${MAVEN_LOCAL_REPO}" dependency:get \
      -Dartifact="com.fasterxml.jackson:jackson-base:${jackson_release}:pom" 2>/dev/null; then
    log "  jackson-base:${jackson_release}:pom instalado"
    break
  fi
done || true

for proj in commons-io commons-lang h2database httpcomponents-client jackson-databind joda-time logging-log4j2; do
  proj_root="${PROJECT_ROOTS[${proj}]:-}"
  if [[ -z "${proj_root}" || ! -d "${proj_root}" ]]; then
    warn "projeto ${proj}: diretório não encontrado — pulando"
    continue
  fi

  for scen in wit-context direct-tests; do
    scen_dir="${RESULTS_ROOT}/${proj}/${scen}"
    [[ -d "${scen_dir}" ]] || { warn "${proj}/${scen}: sem resultados — pulando"; continue; }

    # Pegar última rodada
    latest=$(ls -1d "${scen_dir}"/20*/ 2>/dev/null | sort | tail -1)
    [[ -z "${latest}" ]] && { warn "${proj}/${scen}: sem rodadas — pulando"; continue; }

    gen_tests_src="${latest}generated-tests/src/test/java"
    if [[ ! -d "${gen_tests_src}" ]]; then
      # Estrutura multi-módulo (ex: log4j2): buscar qualquer src/test/java sob generated-tests/
      gen_tests_src=$(find "${latest}generated-tests" -type d -name "java" 2>/dev/null | \
        grep "src/test/java" | head -1 || echo "")
      if [[ -z "${gen_tests_src}" ]]; then
        warn "${proj}/${scen}: sem generated-tests — pulando"; continue
      fi
      log "  multi-módulo: usando ${gen_tests_src}"
    fi

    # ── Para log4j2: usar raiz do projeto (não log4j-api isolado) ────────────────
    # log4j-api usa ${revision} e herda plugins do POM raiz. Compilar isolado falha.
    # Solução: copiar a raiz COMPLETA e usar -pl log4j-api -am em todos os mvn calls.
    LOG4J2_MODULE=""
    if [[ "${proj}" == "logging-log4j2" ]]; then
      LOG4J2_MODULE="log4j-api"
    fi

    # ── Para httpcomponents: detectar submodulo correto ─────────────────────────
    effective_proj_root="${proj_root}"
    if [[ "${proj}" == "httpcomponents-client" ]]; then
      module_name=$(detect_httpclient_module "${gen_tests_src}" "${proj_root}")
      if [[ -n "${module_name}" ]]; then
        effective_proj_root="${proj_root}/${module_name}"
        log "  httpcomponents: módulo detectado → ${module_name}"
        # Instalar módulo e suas dependências no repo local: httpclient5-cache
        # depende de httpclient5 que precisa estar no m2-repo para compilação isolada.
        log "  httpcomponents: instalando dependências do módulo..."
        (cd "${proj_root}" && mvn -q -Dmaven.repo.local="${MAVEN_LOCAL_REPO}" \
          -nsu -DskipTests -Denforcer.skip=true -Drat.skip=true \
          -Dcheckstyle.skip=true -Danimal.sniffer.skip=true \
          install -am -pl "${module_name}" 2>/dev/null) || \
          warn "  httpcomponents: install -am falhou (continuando mesmo assim)"
      else
        warn "  httpcomponents: módulo não detectado — usando root (pode falhar)"
      fi
    fi

    java_count=$(find "${gen_tests_src}" -name "*.java" | wc -l | tr -d ' ')
    log ""
    log "━━━━ ${proj} / ${scen} (${java_count} arquivos) ━━━━"

    # Criar workspace temporário preservando fonte original
    workspace=$(mktemp -d "/tmp/pit-eval-${proj}-${scen}-XXXXXX")
    trap "rm -rf '${workspace}'" EXIT

    # Copiar projeto base para workspace
    log "  copiando projeto base..."
    cp -r "${effective_proj_root}/." "${workspace}/"

    # Para log4j2: os testes vão em log4j-api/src/test/java (submodulo dentro da raiz)
    test_base="${workspace}"
    [[ -n "${LOG4J2_MODULE}" ]] && test_base="${workspace}/${LOG4J2_MODULE}"

    # effective_target_dir: onde ficam surefire-reports, jacoco.exec e pit-reports.
    # Para projetos com submodulo (log4j2), mvn -pl log4j-api -am coloca os reports
    # no submodulo (workspace/log4j-api/target/), não na raiz (workspace/target/).
    effective_target_dir="${workspace}/target"
    [[ -n "${LOG4J2_MODULE}" ]] && effective_target_dir="${workspace}/${LOG4J2_MODULE}/target"

    # IMPORTANTE: limpar testes originais do projeto — queremos só os gerados pelo LLM.
    rm -rf "${test_base}/src/test/java"

    # Copiar testes gerados para workspace
    test_dest="${test_base}/src/test/java"
    mkdir -p "${test_dest}"
    cp -r "${gen_tests_src}/." "${test_dest}/"

    # Compilar projeto base (sem testes) para ter target/classes
    # Patch especial: jackson-databind usa parent SNAPSHOT indisponível
    # Substituir 2.13.0-rc2-SNAPSHOT por 2.13.0 no pom.xml
    if [[ "${proj}" == "jackson-databind" ]] && [[ -f "${workspace}/pom.xml" ]]; then
      if grep -q "SNAPSHOT" "${workspace}/pom.xml" 2>/dev/null; then
        sed -i 's/2\.13\.0-rc2-SNAPSHOT/2.13.0/g; s/2\.13\.0-SNAPSHOT/2.13.0/g' "${workspace}/pom.xml" 2>/dev/null || true
        log "  jackson pom.xml: SNAPSHOT→release patched"
      fi
    fi

    # Patch especial: logging-log4j2 usa Java 11+ e error-prone como compiler plugin.
    # error-prone acessa internals do JDK 17 (com.sun.tools.javac.api.BasicJavacTask)
    # que não estão exportados por padrão → "An unknown compilation problem occurred".
    # Fixes: (1) remover -source/-target 8 (usa release nativo do projeto);
    #         (2) adicionar --add-exports via MAVEN_OPTS para o compilador;
    #         (3) desabilitar error-prone via propriedade se disponível.
    if [[ "${proj}" == "logging-log4j2" ]]; then
      MVN_ARGS="${MVN_ARGS//-Dmaven.compiler.source=8 -Dmaven.compiler.target=8/}"
      MVN_ARGS="${MVN_ARGS} -pl log4j-api -am"
      log "  log4j2: -source/-target 8 removidos; -pl log4j-api -am adicionado ao MVN_ARGS"
    fi

    # Patch especial: logging-log4j2 — a raiz usa ${revision} como versão CI-friendly.
    # Substituir em todos os pom.xml do workspace para que o Maven resolva corretamente.
    if [[ "${proj}" == "logging-log4j2" ]]; then
      find "${workspace}" -name "pom.xml" -not -path "*/target/*" | \
        xargs sed -i "s|\${revision}|${LOG4J2_REVISION}|g" 2>/dev/null || true
      log "  log4j2: \${revision}→${LOG4J2_REVISION} resolvido em todos os pom.xml"
    fi

    # Patch especial: logging-log4j2/log4j-api não declara JUnit 5 como dependência de teste.
    # Os testes gerados usam junit-jupiter — sem a dep, mvn test-compile falha com
    # "package org.junit.jupiter.api does not exist".
    # Fix: injetar junit-jupiter-api como dependência de teste no log4j-api/pom.xml do workspace.
    if [[ "${proj}" == "logging-log4j2" ]] && [[ -f "${workspace}/log4j-api/pom.xml" ]]; then
      if ! grep -q "junit-jupiter-api" "${workspace}/log4j-api/pom.xml" 2>/dev/null; then
        python3 - "${workspace}/log4j-api/pom.xml" << 'PYEOF'
import sys, re
pom_path = sys.argv[1]
with open(pom_path, encoding='utf-8', errors='replace') as f:
    content = f.read()
dep = ('<dependency>'
       '<groupId>org.junit.jupiter</groupId>'
       '<artifactId>junit-jupiter-api</artifactId>'
       '<version>5.10.0</version>'
       '<scope>test</scope>'
       '</dependency>')
# Injetar antes de </dependencies> (fora de <dependencyManagement>)
dm_start = content.find('<dependencyManagement>')
dm_end = content.find('</dependencyManagement>') + len('</dependencyManagement>') if dm_start >= 0 else 0
# Encontrar </dependencies> após dependencyManagement (ou no main body)
idx = content.find('</dependencies>', dm_end if dm_end > 0 else 0)
if idx >= 0:
    content = content[:idx] + dep + content[idx:]
else:
    # Sem <dependencies>: criar antes de </project>
    idx = content.rfind('</project>')
    content = content[:idx] + '<dependencies>' + dep + '</dependencies>' + content[idx:]
with open(pom_path, 'w', encoding='utf-8') as f:
    f.write(content)
print('ok')
PYEOF
        log "  log4j2: junit-jupiter-api adicionado como dep de teste no log4j-api/pom.xml"
      fi
    fi

    # Patch especial: h2database — src/tools contém arquivos que importam com.sun.javadoc
    # (removida do Java 17):
    # - BuildBase.java: usa "import com.sun.javadoc.*" no topo, mas o código real usa
    #   Class.forName (reflection). Fix: remover apenas as linhas de import problemáticas.
    # - doclet/Doclet.java e doclet/ResourceDoclet.java: deletar o diretório doclet/.
    # - src/tools/org/h2/jcr/ usa doclet → deletar.
    # Build.java (extend BuildBase) e doc/ são mantidos pois os testes gerados precisam deles.
    if [[ "${proj}" == "h2database" ]]; then
      # Remover imports de com.sun.javadoc do BuildBase.java (sem afetar runtime reflection)
      if [[ -f "${workspace}/src/tools/org/h2/build/BuildBase.java" ]]; then
        sed -i '/^import com\.sun\.javadoc\./d' \
            "${workspace}/src/tools/org/h2/build/BuildBase.java" 2>/dev/null || true
        log "  h2database: BuildBase.java imports com.sun.javadoc removidos"
      fi
      # Deletar doclet/ (usa javadoc API extensamente) e jcr/ (depende de doclet)
      rm -rf "${workspace}/src/tools/org/h2/build/doclet" \
             "${workspace}/src/tools/org/h2/jcr" \
             "${workspace}/src/java9" "${workspace}/src/java10" 2>/dev/null || true
      log "  h2database: doclet|jcr|java9|java10 removidos, build+doc+dev preservados"
      # h2database usa src/test/org/ (não src/test/java/) — layout não-padrão.
      # O rm -rf src/test/java do loop principal não remove nada, então TestAllJunit
      # (que chama System.exit) e outros testes originais ficam no workspace.
      # Fix: deletar src/test/org/ para ficar apenas com os testes gerados em src/test/java/.
      rm -rf "${workspace}/src/test/org" 2>/dev/null || true
      log "  h2database: src/test/org/ removido (testes originais — evita TestAllJunit crash)"
      # src/tools/ é adicionado pelo pom como source dir mas contém arquivos que importam
      # org.h2.test.utils (deletado com src/test/org/ acima). Os testes gerados não dependem
      # de nada em src/tools/ — deletar o diretório inteiro para evitar erros de compilação.
      rm -rf "${workspace}/src/tools" 2>/dev/null || true
      log "  h2database: src/tools/ removido (evita dependências em org.h2.test.utils deletado)"
      # O pom.xml do h2 restringe surefire a <include>TestAllJunit.java</include>.
      # Deletamos TestAllJunit junto com src/test/org/, então surefire não encontra nenhum teste.
      # Fix: substituir o include para rodar todos os *WitupGeneratedTest.java gerados.
      if [[ -f "${workspace}/pom.xml" ]]; then
        python3 -c "
import re, sys
pom = open('${workspace}/pom.xml').read()
pom = re.sub(
    r'<includes>\s*<include>(?:\*\*/)?TestAllJunit\.java</include>\s*</includes>',
    '<includes><include>**/*WitupGeneratedTest.java</include></includes>',
    pom
)
open('${workspace}/pom.xml', 'w').write(pom)
print('ok')
" 2>/dev/null && log "  h2database pom.xml: surefire includes patched para *WitupGeneratedTest"
      fi
    fi

    # Patch especial: joda-time usa JUnit 3.8.2 mas os testes gerados usam @Test (JUnit 4).
    # PIT não consegue detectar nenhum runner — atualizar para JUnit 4.13.2.
    # Adicionalmente: surefire inclui apenas TestAllPackages.java — nossos *WitupGeneratedTest
    # são ignorados. Patch o pom para incluir o padrão correto.
    if [[ "${proj}" == "joda-time" ]] && [[ -f "${workspace}/pom.xml" ]]; then
      if grep -q "3\.8\.2" "${workspace}/pom.xml" 2>/dev/null; then
        sed -i 's|<junit\.version>3\.8\.2</junit\.version>|<junit.version>4.13.2</junit.version>|g' \
            "${workspace}/pom.xml" 2>/dev/null || true
        log "  joda-time pom.xml: JUnit 3.8.2→4.13.2 patched"
      fi
      # Substituir o padrão de includes do surefire para incluir nossos testes gerados
      python3 -c "
import re, sys
pom = open('${workspace}/pom.xml').read()
# Remover inclusions restritivas e deixar o surefire rodar todos os *Test.java e *Tests.java
pom = re.sub(
    r'<includes>\s*<include>\*\*/TestAllPackages\.java</include>\s*</includes>',
    '<includes><include>**/*Test.java</include><include>**/*Tests.java</include></includes>',
    pom
)
open('${workspace}/pom.xml', 'w').write(pom)
print('ok')
" 2>/dev/null && log "  joda-time pom.xml: surefire includes patched"
    fi

    # Patch especial: commons-io tem <argLine>-Xmx25M</argLine> hardcoded no pom.xml.
    # -DargLine=... NÃO sobrescreve configuração hardcoded do Surefire — apenas variáveis ${argLine}.
    # Com o agente JaCoCo injetado via JAVA_TOOL_OPTIONS + heap de só 25 MB, o JVM forkado
    # sofre OOM e o jacoco.exec fica vazio/corrompido.
    # Fix: subir argLine para 256 MB no workspace antes de compilar/testar.
    if [[ "${proj}" == "commons-io" ]] && [[ -f "${workspace}/pom.xml" ]]; then
      if grep -q "\-Xmx25M" "${workspace}/pom.xml" 2>/dev/null; then
        sed -i 's/-Xmx25M/-Xmx256m/g' "${workspace}/pom.xml" 2>/dev/null || true
        log "  commons-io pom.xml: -Xmx25M→-Xmx256m patched"
      fi
    fi

    log "  compilando projeto base..."
    (cd "${workspace}" && \
      mvn_cmd="mvn"; [[ -x "${workspace}/mvnw" ]] && mvn_cmd="${workspace}/mvnw"; \
      "${mvn_cmd}" ${MVN_ARGS} -DskipTests compile 2>/dev/null) || \
      { warn "${proj}/${scen}: falha na compilação do projeto base — pulando"; rm -rf "${workspace}"; continue; }

    # Para log4j2: o proj_root para classpath/classes é o submodulo dentro do workspace
    prune_proj_root="${workspace}"
    [[ -n "${LOG4J2_MODULE}" ]] && prune_proj_root="${workspace}/${LOG4J2_MODULE}"

    # ── Injetar pitest-junit5-plugin se os testes usam JUnit 5 ─────────────────
    if grep -rql "junit.jupiter\|junit-jupiter" "${test_dest}" 2>/dev/null; then
      log "  JUnit 5 detectado nos testes — injetando pitest-junit5-plugin"
      inject_pitest_junit5 "${prune_proj_root}/pom.xml" || \
        inject_pitest_junit5 "${workspace}/pom.xml" || \
        warn "  falha ao injetar pitest-junit5-plugin"
      # Garantir que o plugin está no cache local
      mvn -q -Dmaven.repo.local="${MAVEN_LOCAL_REPO}" \
          dependency:get -Dartifact=org.pitest:pitest-junit5-plugin:1.2.2 2>/dev/null || true
    fi

    # Compilar testes individualmente e remover falhos
    files_before=$(find "${test_dest}" -name "*.java" | wc -l | tr -d ' ')
    if ! prune_failing_tests "${proj}" "${prune_proj_root}" "${test_dest}"; then
      warn "${proj}/${scen}: nenhum teste compilável — pulando"
      printf "%s\t%s\t%s\t0\t0\t\t\t\t\t\n" "${proj}" "${scen}" "${files_before}" >> "${SUMMARY_FILE}"
      rm -rf "${workspace}"; continue
    fi
    files_after=$(find "${test_dest}" -name "*.java" | wc -l | tr -d ' ')

    # Loop iterativo: se mvn test-compile falha, identificar o arquivo problemático e removê-lo
    # Repetir até compilar (max 20 iterações) — captura erros que javac individual não detecta
    mvn_bin_tc="mvn"; [[ -x "${workspace}/mvnw" ]] && mvn_bin_tc="${workspace}/mvnw"
    tc_attempts=0
    while [[ ${tc_attempts} -lt 20 ]]; do
      tc_out=$(cd "${workspace}" && "${mvn_bin_tc}" ${MVN_ARGS} -DskipTests test-compile 2>&1)
      tc_exit=$?
      [[ ${tc_exit} -eq 0 ]] && break
      # Extrair nome do arquivo Java que causou o erro
      bad_file=$(echo "${tc_out}" | grep -oP '/[^\s]+\.java' | head -1 | xargs basename 2>/dev/null || echo "")
      if [[ -z "${bad_file}" ]]; then
        # Tentar extrair via "error: ..." padrão do javac
        bad_file=$(echo "${tc_out}" | grep -oP '\w+\.java' | head -1 || echo "")
      fi
      if [[ -z "${bad_file}" ]]; then
        warn "  mvn test-compile falhou mas não foi possível identificar o arquivo problemático"
        break
      fi
      bad_path=$(find "${test_dest}" -name "${bad_file}" 2>/dev/null | head -1)
      if [[ -n "${bad_path}" ]]; then
        warn "  removendo (mvn compile): ${bad_file}"
        rm -f "${bad_path}"
        ((tc_attempts++)) || true
      else
        warn "  mvn test-compile falhou: ${bad_file} não encontrado para remover"
        break
      fi
    done
    files_after=$(find "${test_dest}" -name "*.java" | wc -l | tr -d ' ')
    log "  ${files_before} → ${files_after} arquivos após pruning"

    # Métricas (sem local — estamos no escopo global do loop)
    comp_score="" surefire_tests="" pass_rate="" jl="" jb="" pit_score="" failing_classes=""

    # Compilação final
    log "  test-compile..."
    mvn_bin="mvn"; [[ -x "${workspace}/mvnw" ]] && mvn_bin="${workspace}/mvnw"
    if (cd "${workspace}" && "${mvn_bin}" ${MVN_ARGS} -DskipTests test-compile 2>/dev/null); then
      comp_score="1.00"; log "  compilação: OK"
    else
      comp_score="0.00"; warn "  compilação ainda falha após pruning"
    fi

    if [[ "${comp_score}" == "1.00" ]]; then
      # Surefire — com heap limitado (evita crash JVM por OOM; captura como Exception)
      log "  surefire..."
      mvn_bin="mvn"; [[ -x "${workspace}/mvnw" ]] && mvn_bin="${workspace}/mvnw"
      (cd "${workspace}" && "${mvn_bin}" ${MVN_ARGS} "-DargLine=${SUREFIRE_HEAP}" test 2>/dev/null) || true
      if [[ -d "${effective_target_dir}/surefire-reports" ]]; then
        surefire_out=$("${WITUP}" extrair-surefire --report-dir "${effective_target_dir}/surefire-reports" 2>/dev/null || echo "")
        surefire_tests=$(echo "${surefire_out}" | grep -oP 'WITUP_METRIC=\K[\d.]+' | head -1 || echo "")
        pass_rate_out=$("${WITUP}" extrair-surefire --report-dir "${effective_target_dir}/surefire-reports" --kind pass-rate 2>/dev/null || echo "")
        pass_rate=$(echo "${pass_rate_out}" | grep -oP 'WITUP_METRIC=\K[\d.]+' | head -1 || echo "")
        log "  surefire: testes=${surefire_tests:-?} pass_rate=${pass_rate:-?}"
      fi

      # JaCoCo — usar JAVA_TOOL_OPTIONS para injetar agente em TODOS os JVMs forked
      # Esta é a abordagem mais confiável: ignora completamente o argLine do pom.xml
      log "  jacoco..."
      mvn_bin="mvn"; [[ -x "${workspace}/mvnw" ]] && mvn_bin="${workspace}/mvnw"
      # Garantir que o agente está no cache local
      (cd "${workspace}" && "${mvn_bin}" ${MVN_ARGS} \
          org.jacoco:jacoco-maven-plugin:0.8.12:prepare-agent 2>/dev/null) || true
      JACOCO_AGENT=$(find "${MAVEN_LOCAL_REPO}/org/jacoco/org.jacoco.agent" -name "*-runtime.jar" 2>/dev/null | sort -V | tail -1)
      JACOCO_EXEC="${effective_target_dir}/jacoco.exec"
      JACOCO_XML="${effective_target_dir}/site/jacoco/jacoco.xml"
      mkdir -p "${workspace}/target"
      if [[ -n "${JACOCO_AGENT}" && -f "${JACOCO_AGENT}" ]]; then
        # JAVA_TOOL_OPTIONS é injetado em todos os processos JVM (forked ou não)
        # Usar export dentro do subshell para garantir propagação
        # Agente JaCoCo via JAVA_TOOL_OPTIONS (afeta todos os JVMs)
        # Heap via -DargLine afeta SOMENTE o fork do Surefire (não o Maven principal)
        (export JAVA_TOOL_OPTIONS="-javaagent:${JACOCO_AGENT}=destfile=${JACOCO_EXEC}"
         cd "${workspace}" && "${mvn_bin}" ${MVN_ARGS} "-DargLine=${SUREFIRE_HEAP}" test 2>/dev/null) || true
        # Gerar relatório a partir do exec file
        if [[ -s "${JACOCO_EXEC}" ]]; then
          log "  jacoco.exec: $(du -sh "${JACOCO_EXEC}" | cut -f1)"
          mkdir -p "${effective_target_dir}/site/jacoco"
          # Tentar 1: mvn jacoco:report — parâmetros mínimos corretos
          _REPORT_LOG="/tmp/jacoco_report_$$.log"
          (cd "${workspace}" && "${mvn_bin}" \
              -q -Dmaven.repo.local="${MAVEN_LOCAL_REPO}" \
              -P!java14+ -Denforcer.skip=true -Drat.skip=true \
              org.jacoco:jacoco-maven-plugin:0.8.12:report \
              "-Djacoco.dataFile=${JACOCO_EXEC}" 2>&1) > "${_REPORT_LOG}" || true
          if [[ -f "${JACOCO_XML}" ]]; then
            log "  jacoco.xml gerado via mvn report"
          else
            warn "  mvn report falhou: $(grep -i 'error\|ERROR\|FATAL' "${_REPORT_LOG}" | tail -2 | tr '\n' ' ')"
          fi
          rm -f "${_REPORT_LOG}"

          # Tentar 2: baixar CLI e usar diretamente
          if [[ ! -f "${JACOCO_XML}" ]]; then
            # Forçar download do CLI sem -nsu
            mvn -q -Dmaven.repo.local="${MAVEN_LOCAL_REPO}" \
                dependency:get -Dartifact=org.jacoco:org.jacoco.cli:0.8.12:jar:nodeps 2>/dev/null || true
            JACOCO_CLI=$(find "${MAVEN_LOCAL_REPO}/org/jacoco/org.jacoco.cli" -name "*nodeps*.jar" 2>/dev/null | sort -V | tail -1)
            if [[ -n "${JACOCO_CLI}" && -f "${JACOCO_CLI}" ]]; then
              java -jar "${JACOCO_CLI}" report "${JACOCO_EXEC}" \
                --classfiles "${effective_target_dir}/classes" \
                --xml "${JACOCO_XML}" 2>/dev/null || true
              [[ -f "${JACOCO_XML}" ]] && log "  jacoco.xml gerado via CLI"
            else
              warn "  jacoco CLI não encontrado em ${MAVEN_LOCAL_REPO}/org/jacoco/org.jacoco.cli"
            fi
          fi

          # Tentar 3: usar JaCoCo core/report JARs diretamente via Python+subprocess
          if [[ ! -f "${JACOCO_XML}" ]]; then
            JACOCO_CORE=$(find "${MAVEN_LOCAL_REPO}/org/jacoco/org.jacoco.core" -name "*.jar" | sort -V | tail -1)
            JACOCO_RPT=$(find "${MAVEN_LOCAL_REPO}/org/jacoco/org.jacoco.report" -name "*.jar" | sort -V | tail -1)
            ASM_JAR=$(find "${MAVEN_LOCAL_REPO}/org/ow2/asm" -name "asm-[0-9]*.jar" 2>/dev/null | sort -V | tail -1)
            if [[ -n "${JACOCO_CORE}" && -n "${JACOCO_RPT}" ]]; then
              log "  tentando geração via ant+jacoco..."
              (cd "${workspace}" && ant -q \
                  -Djacoco.exec="${JACOCO_EXEC}" \
                  -Djacoco.xml="${JACOCO_XML}" \
                  -Djacoco.core="${JACOCO_CORE}" \
                  -Djacoco.report="${JACOCO_RPT}" \
                  -Djacoco.classes="${effective_target_dir}/classes" \
                  -f /dev/stdin << 'ANTEOF' 2>/dev/null
<project name="jacoco-report" default="report">
  <taskdef name="jacoco-report" classname="org.jacoco.ant.ReportTask"
    classpath="${jacoco.core}:${jacoco.report}"/>
  <target name="report">
    <jacoco-report>
      <executiondata><file file="${jacoco.exec}"/></executiondata>
      <structure name="project">
        <classfiles><fileset dir="${jacoco.classes}" erroronmissingdir="false"/></classfiles>
      </structure>
      <xml destfile="${jacoco.xml}"/>
    </jacoco-report>
  </target>
</project>
ANTEOF
              ) || true
              [[ -f "${JACOCO_XML}" ]] && log "  jacoco.xml gerado via ant"
            fi
          fi
        else
          warn "  jacoco.exec ausente ou vazio após testes"
        fi
      fi
      # Extrair resultados
      if [[ -f "${JACOCO_XML}" ]]; then
        log "  jacoco.xml: $(du -sh "${JACOCO_XML}" | cut -f1) | head: $(head -1 "${JACOCO_XML}" | cut -c1-80)"
        jl_raw=$("${WITUP}" extrair-jacoco --xml "${JACOCO_XML}" --counter LINE 2>&1 || echo "")
        jl=$(echo "${jl_raw}" | grep -oP 'WITUP_METRIC=\K[\d.]+' | head -1 || echo "")
        [[ -z "${jl}" ]] && warn "  extrair-jacoco LINE output: ${jl_raw}" | head -c 200
        jb=$("${WITUP}" extrair-jacoco --xml "${JACOCO_XML}" --counter BRANCH 2>/dev/null | grep -oP 'WITUP_METRIC=\K[\d.]+' | head -1 || echo "")
        log "  jacoco: line=${jl:-?} branch=${jb:-?}"
      else
        warn "  jacoco: jacoco.xml não gerado"
      fi

      # Identificar e DELETAR fisicamente classes que falharam no Surefire
      # PIT roda TODOS os testes no baseline — exclusão lógica não é suficiente
      if [[ -d "${effective_target_dir}/surefire-reports" ]]; then
        cat > /tmp/find_failing_tests.py << 'PYEOF'
import sys, os, xml.etree.ElementTree as ET
reports_dir = sys.argv[1]
failing = set()
for f in os.listdir(reports_dir):
    path = os.path.join(reports_dir, f)
    # Ler XMLs (mais confiável que TXTs — inclui falhas por exceção em @BeforeAll etc.)
    if f.endswith('.xml') and f.startswith('TEST-'):
        try:
            tree = ET.parse(path)
            root = tree.getroot()
            tag = root.tag  # testsuite
            failures = int(root.get('failures', 0))
            errors = int(root.get('errors', 0))
            if failures > 0 or errors > 0:
                cls = root.get('name', '')
                if cls:
                    failing.add(cls)
        except Exception:
            pass
    # Fallback: TXTs
    elif f.endswith('.txt') and f.startswith('TEST-'):
        try:
            content = open(path).read()
            if 'FAILURE' in content or 'Tests in error' in content or '<<< ERROR!' in content:
                cls = f[5:-4]
                if cls:
                    failing.add(cls)
        except Exception:
            pass
print('\n'.join(sorted(failing)))
PYEOF
        failing_classes_list=$(python3 /tmp/find_failing_tests.py "${effective_target_dir}/surefire-reports" 2>/dev/null || echo "")
        if [[ -n "${failing_classes_list}" ]]; then
          cnt=$(echo "${failing_classes_list}" | wc -l | tr -d ' ')
          log "  deletando ${cnt} classes com falha para suite verde no PIT..."
          while IFS= read -r cls; do
            [[ -z "${cls}" ]] && continue
            # Converter org.Foo.Bar -> org/Foo/Bar.java
            java_path="${test_dest}/$(echo "${cls}" | tr '.' '/').java"
            if [[ -f "${java_path}" ]]; then
              rm -f "${java_path}"
              log "    removido: ${cls}"
            else
              # Fallback: buscar pelo nome simples (captura inner classes e layouts não-padrão)
              simple="${cls##*.}.java"
              found=$(find "${test_dest}" -name "${simple}" 2>/dev/null | head -1)
              if [[ -n "${found}" ]]; then
                rm -f "${found}"
                log "    removido (fallback): ${found}"
              else
                warn "    não encontrado para remover: ${cls} (path=${java_path})"
              fi
            fi
          done <<< "${failing_classes_list}"
          # Recompilar testes após deleção
          (cd "${workspace}" && mvn_bin_inner="mvn"; [[ -x "${workspace}/mvnw" ]] && mvn_bin_inner="${workspace}/mvnw"; \
           "${mvn_bin_inner}" ${MVN_ARGS} -DskipTests test-compile 2>/dev/null) || true
        fi
        failing_classes=$(echo "${failing_classes_list}" | tr '\n' ',' | sed 's/,$//')
      fi

      # Segunda rodada surefire para verificar suite verde antes do PIT
      # Rodadas ímpares: ordem padrão. Rodadas pares: ordem aleatória (captura falhas por dependência).
      # Loop até 8 rodadas ou suite 100% verde.
      pit_pass_rate="0"
      for _pit_round in 1 2 3 4 5 6 7 8; do
        mvn_bin_pit="mvn"; [[ -x "${workspace}/mvnw" ]] && mvn_bin_pit="${workspace}/mvnw"
        rm -rf "${effective_target_dir}/surefire-reports"
        # Alterna ordem sequencial ↔ aleatória para capturar dependências entre testes
        _run_order_flag=""
        (( _pit_round % 2 == 0 )) && _run_order_flag="-Dsurefire.runOrder=random"
        (cd "${workspace}" && "${mvn_bin_pit}" ${MVN_ARGS} ${_run_order_flag} "-DargLine=${SUREFIRE_HEAP}" test 2>/dev/null) || true
        if [[ ! -d "${effective_target_dir}/surefire-reports" ]]; then break; fi
        _pr_out=$("${WITUP}" extrair-surefire --report-dir "${effective_target_dir}/surefire-reports" --kind pass-rate 2>/dev/null || echo "")
        pit_pass_rate=$(echo "${_pr_out}" | grep -oP 'WITUP_METRIC=\K[\d.]+' | head -1 || echo "0")
        [[ "${pit_pass_rate}" == "100.00" || "${pit_pass_rate}" == "100" ]] && break
        # Detectar e remover classes com falha desta rodada
        _still_failing=$(python3 /tmp/find_failing_tests.py "${effective_target_dir}/surefire-reports" 2>/dev/null || echo "")
        [[ -z "${_still_failing}" ]] && break
        while IFS= read -r _cls; do
          [[ -z "${_cls}" ]] && continue
          _bad="${test_dest}/$(echo "${_cls}" | tr '.' '/').java"
          if [[ -f "${_bad}" ]]; then
            rm -f "${_bad}" && log "  removendo (runtime fail round ${_pit_round}): $(basename "${_bad}")"
          else
            _found=$(find "${test_dest}" -name "${_cls##*.}.java" 2>/dev/null | head -1)
            [[ -n "${_found}" ]] && rm -f "${_found}" && log "  removendo (runtime fail round ${_pit_round} fallback): $(basename "${_found}")"
          fi
        done <<< "${_still_failing}"
        # Recompilar após nova deleção
        (cd "${workspace}" && "${mvn_bin_pit}" ${MVN_ARGS} -DskipTests test-compile 2>/dev/null) || true
      done
      log "  suite pre-PIT: pass_rate=${pit_pass_rate}"

      # Verificar se ainda há testes para o PIT rodar
      remaining_tests=$(find "${test_dest}" -name "*.java" 2>/dev/null | wc -l | tr -d ' ')
      if [[ "${remaining_tests}" -eq 0 ]]; then
        warn "  nenhum teste restante após pruning — pulando PIT"
        pit_score=""
        # Salvar resultado e continuar
        printf "%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\n" \
          "${proj}" "${scen}" "${files_before}" "${files_after}" \
          "${comp_score}" "${surefire_tests}" "${pass_rate}" \
          "${jl}" "${jb}" "${pit_score}" >> "${SUMMARY_FILE}"
        rm -rf "${workspace}"; trap - EXIT
        log "  ${proj}/${scen}: concluído"
        continue
      fi

      # PIT — suite deve estar verde após deleção das classes com falha
      pit_opts="-DtimestampedReports=false -DoutputFormats=XML -DexcludedClasses=org.pitest.*"
      log "  pit mutation..."
      mvn_bin="mvn"; [[ -x "${workspace}/mvnw" ]] && mvn_bin="${workspace}/mvnw"
      pit_out=$((cd "${workspace}" && "${mvn_bin}" ${MVN_ARGS} ${pit_opts} \
          org.pitest:pitest-maven:1.23.0:mutationCoverage 2>&1) || echo "PIT_FAILED")
      if ! echo "${pit_out}" | grep -q "PIT_FAILED\|BUILD FAILURE\|mutationCoverage failed"; then
        pit_score=$("${WITUP}" extrair-pit --report-dir "${effective_target_dir}/pit-reports" 2>/dev/null | grep -oP 'WITUP_METRIC=\K[\d.]+' | head -1 || echo "")
        log "  PIT mutation score: ${pit_score:-?}"
      else
        # Diagnóstico: mostrar causa real
        _pit_cause=$(echo "${pit_out}" | grep -iE "tests did not pass|please check|No mutations|Cannot find|SEVERE|junit 5.*not installed" | head -3)
        warn "  PIT falhou: ${_pit_cause:-$(echo "${pit_out}" | grep -i 'ERROR' | tail -2)}"

        # Fallback A: "N tests did not pass" → excluir via -DexcludedTestClasses
        if echo "${pit_out}" | grep -q "tests did not pass"; then
          rm -rf "${effective_target_dir}/surefire-reports"
          (cd "${workspace}" && "${mvn_bin}" ${MVN_ARGS} "-DargLine=${SUREFIRE_HEAP}" \
            "-Dsurefire.runOrder=random" test 2>/dev/null) || true
          _pit_fallback_failing=$(python3 /tmp/find_failing_tests.py \
            "${effective_target_dir}/surefire-reports" 2>/dev/null || echo "")
          if [[ -n "${_pit_fallback_failing}" ]]; then
            failing_for_pit=$(echo "${_pit_fallback_failing}" | tr '\n' ',' | sed 's/,$//')
            log "  PIT fallback: excluindo ${failing_for_pit}"
            pit_out_retry=$((cd "${workspace}" && "${mvn_bin}" ${MVN_ARGS} ${pit_opts} \
              "-DexcludedTestClasses=${failing_for_pit}" \
              org.pitest:pitest-maven:1.23.0:mutationCoverage 2>&1) || echo "PIT_FAILED")
            if ! echo "${pit_out_retry}" | grep -q "PIT_FAILED\|BUILD FAILURE\|mutationCoverage failed"; then
              pit_score=$("${WITUP}" extrair-pit --report-dir "${effective_target_dir}/pit-reports" 2>/dev/null | grep -oP 'WITUP_METRIC=\K[\d.]+' | head -1 || echo "")
              log "  PIT mutation score (fallback-A): ${pit_score:-?}"
            fi
          fi
        fi

        # Fallback B: JUnit 5 não reconhecido → usar PIT CLI Java diretamente
        # O Maven não propaga <dependencies> do plugin via perfil para invocações diretas.
        # Solução: invocar PIT via java -cp incluindo pitest-junit5-plugin no classpath.
        if [[ -z "${pit_score}" ]] && echo "${pit_out}" | grep -qi "junit 5.*not installed\|please check.*pitest plugin"; then
          log "  PIT fallback-B: invocando via Java CLI com pitest-junit5-plugin"
          PITEST_JAR=$(find "${MAVEN_LOCAL_REPO}/org/pitest/pitest" -name "pitest-[0-9]*.jar" 2>/dev/null | sort -V | tail -1)
          PITEST_ENTRY=$(find "${MAVEN_LOCAL_REPO}/org/pitest/pitest-entry" -name "*.jar" 2>/dev/null | sort -V | tail -1)
          PITEST_JUNIT5=$(find "${MAVEN_LOCAL_REPO}/org/pitest/pitest-junit5-plugin" -name "pitest-junit5-plugin-*.jar" 2>/dev/null | grep -v "sources\|javadoc" | sort -V | tail -1)
          # Classpath: pitest core + junit5 plugin + projeto classes + test classes + dependências
          TEST_CP=$(mvn -q -Dmaven.repo.local="${MAVEN_LOCAL_REPO}" -f "${workspace}/pom.xml" \
            -nsu dependency:build-classpath -Dmdep.outputFile=/tmp/pit_cp_$$.txt 2>/dev/null \
            && cat /tmp/pit_cp_$$.txt 2>/dev/null || echo "")
          rm -f /tmp/pit_cp_$$.txt
          PIT_REPORT_DIR="${effective_target_dir}/pit-reports"
          mkdir -p "${PIT_REPORT_DIR}"
          # Pacotes-alvo: detectar do gen_tests_src
          _root_pkg=$(find "${test_dest}" -name "*.java" 2>/dev/null | head -1 | \
            xargs grep "^package " 2>/dev/null | awk '{print $2}' | tr -d ';' | \
            sed 's/\.[^.]*$//' | head -1)
          [[ -z "${_root_pkg}" ]] && _root_pkg="*"
          if [[ -n "${PITEST_JAR}" && -n "${PITEST_ENTRY}" && -n "${PITEST_JUNIT5}" ]]; then
            PIT_CLI_CP="${PITEST_JAR}:${PITEST_ENTRY}:${PITEST_JUNIT5}:${workspace}/target/classes:${workspace}/target/test-classes:${TEST_CP}"
            pit_cli_out=$(java -cp "${PIT_CLI_CP}" \
              org.pitest.mutationtest.commandline.MutationCoverageReport \
              --reportDir "${PIT_REPORT_DIR}" \
              --targetClasses "${_root_pkg}.*" \
              --targetTests "${_root_pkg}.*" \
              --sourceDirs "${workspace}/src/main/java" \
              --outputFormats XML \
              --timestampedReports false \
              2>&1 || echo "PIT_CLI_FAILED")
            if ! echo "${pit_cli_out}" | grep -q "PIT_CLI_FAILED\|BUILD FAILURE"; then
              pit_score=$("${WITUP}" extrair-pit --report-dir "${PIT_REPORT_DIR}" 2>/dev/null | grep -oP 'WITUP_METRIC=\K[\d.]+' | head -1 || echo "")
              log "  PIT mutation score (fallback-B/CLI): ${pit_score:-?}"
            else
              warn "  PIT CLI falhou: $(echo "${pit_cli_out}" | grep -iE 'SEVERE|ERROR|failed' | head -3)"
            fi
          else
            warn "  PIT fallback-B: JARs não encontrados (pitest=${PITEST_JAR:-?} entry=${PITEST_ENTRY:-?} junit5=${PITEST_JUNIT5:-?})"
          fi
        fi
      fi
    fi

    # Salvar resultado
    printf "%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\n" \
      "${proj}" "${scen}" "${files_before}" "${files_after}" \
      "${comp_score}" "${surefire_tests}" "${pass_rate}" \
      "${jl}" "${jb}" "${pit_score}" >> "${SUMMARY_FILE}"

    rm -rf "${workspace}"
    trap - EXIT
    log "  ${proj}/${scen}: concluído"
  done
done

log ""
log "=== PIT Evaluation concluída ==="
log "Resultados: ${SUMMARY_FILE}"
echo ""
python3 - "${SUMMARY_FILE}" << 'PYEOF'
import sys, csv
path = sys.argv[1]
with open(path) as f:
    rows = list(csv.reader(f, delimiter='\t'))
if not rows:
    sys.exit(0)
# Calcular largura máxima de cada coluna
cols = len(rows[0])
widths = [max(len(r[i]) if i < len(r) else 0 for r in rows) for i in range(cols)]
for r in rows:
    print('  '.join((r[i] if i < len(r) else '').ljust(widths[i]) for i in range(cols)))
PYEOF

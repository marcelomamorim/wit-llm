#!/usr/bin/env bash
# run-evaluate-docker.sh
#
# Executa avaliar-estudo-jdk-global dentro do container Docker e, em seguida,
# aplica automaticamente todos os fixes necessários para execução com jtreg+JCov.
#
# ─────────────────────────────────────────────────────────────────────────────
# RESTRIÇÃO CRÍTICA — JDK
# A JDK DEVE ser o commit da75f3c4 (JDK 11+28, pré-GA, setembro 2018).
# Mesma versão usada na análise WIT do Diego. ZERO EXCEÇÕES.
# ─────────────────────────────────────────────────────────────────────────────
#
# Variáveis de ambiente esperadas (definidas no docker-compose.yml):
#   EXPERIMENT_DIR      : subdiretório em generated/experiments/
#   RUN_STAMP           : timestamp do run
#   RUNTIME_CONFIG_FILE : arquivo de config runtime (ex: rodada-artigo.runtime.json)
#   GENERATION_MODEL    : chave do modelo (ex: openai_main)

set -euo pipefail

EXPERIMENT_DIR="${EXPERIMENT_DIR:-jdk21-pilot}"
RUN_STAMP="${RUN_STAMP:-run}"
RUNTIME_CONFIG_FILE="${RUNTIME_CONFIG_FILE:-jdk21-pilot.runtime.json}"
GENERATION_MODEL="${GENERATION_MODEL:-openai_main}"

RUN_DIR="/data/generated/experiments/${EXPERIMENT_DIR}/${RUN_STAMP}"

log() { printf '[evaluate] %s\n' "$*"; }

# ── Passo 1: Corrigir paths do host → Docker no preparation JSON ──────────────
# O prepare foi executado no host (paths /Users/...); dentro do Docker o projeto
# está em /data. Criamos uma cópia patched apenas para o evaluate.
PREP_JSON="${RUN_DIR}/preparation_jdk_global_impact.json"
PREP_JSON_DOCKER="${RUN_DIR}/preparation_jdk_global_impact_docker.json"

if [[ -f "${PREP_JSON}" ]]; then
  log "Adaptando paths do preparation JSON para o ambiente Docker..."
  python3 - "${PREP_JSON}" "${PREP_JSON_DOCKER}" << 'PY'
import json, sys, re

src, dst = sys.argv[1], sys.argv[2]
data = open(src).read()

# Detectar o prefixo do host (tudo antes de /generated/ ou /resources/)
m = re.search(r'("(?:[^"]+))((?:/generated/|/resources/|/bin/))', data)
if m:
    host_prefix = m.group(1).lstrip('"')
    # Extrair só o prefixo antes do primeiro /generated ou /resources
    for marker in ['/generated/', '/resources/', '/bin/']:
        idx = host_prefix.find(marker)
        if idx != -1:
            host_prefix = host_prefix[:idx]
            break
    data = data.replace(host_prefix, '/data')
    print(f'[evaluate] Substituído "{host_prefix}" → "/data"', file=sys.stderr)
else:
    print('[evaluate] AVISO: prefixo host não detectado — usando prep JSON original', file=sys.stderr)

open(dst, 'w').write(data)
PY
  # Substituir o arquivo original pelo patched temporariamente (witup lê pelo nome fixo)
  cp "${PREP_JSON}" "${PREP_JSON}.host_backup"
  cp "${PREP_JSON_DOCKER}" "${PREP_JSON}"
fi

# ── Passo 2: Materializar os testes via witup ─────────────────────────────────
log "Materializando testes gerados pelo LLM..."
witup avaliar-estudo-jdk-global \
    --config    "/data/generated/configs/${RUNTIME_CONFIG_FILE}" \
    --generation-model "${GENERATION_MODEL}" \
    --jdk-root  /opt/openjdk-src \
    --run-dir   "${RUN_DIR}" \
    --responses "${RUN_DIR}/responses_openai_batch_generation.jsonl" \
    --errors    "${RUN_DIR}/errors_openai_batch_generation.jsonl"

# Restaurar o JSON original do host
if [[ -f "${PREP_JSON}.host_backup" ]]; then
  mv "${PREP_JSON}.host_backup" "${PREP_JSON}"
fi

log "Materialização concluída."

# ── Passo 3: Criar TEST.ROOT mínimo em cada variante ─────────────────────────
# O jtreg sobe o filesystem buscando TEST.ROOT. O test/jdk/TEST.ROOT do OpenJDK
# referencia VMProps.java com -XX:+WhiteBoxAPI em caminhos inexistentes no
# container; sem este arquivo, o jtreg aborta silenciosamente ~1042 de 1066 testes.
# Nota: não alteramos o conteúdo dos testes gerados — apenas adicionamos o
# arquivo de infraestrutura que o jtreg precisa para processar o diretório.
log ""
log "Criando TEST.ROOT mínimo em cada variante..."

MINIMAL_TEST_ROOT="# Minimal TEST.ROOT for witup LLM-generated tests.
# Stops jtreg root search before reaching test/jdk/TEST.ROOT of the OpenJDK
# source tree, which references VMProps.java with -XX:+WhiteBoxAPI at paths
# that do not exist inside the evaluation container."

for VARIANT in direct-tests wit-context; do
  GENERATED_DIR="${RUN_DIR}/variants/${VARIANT}/test/jdk/witup/generated"
  if [[ ! -d "${GENERATED_DIR}" ]]; then
    log "  AVISO: ${VARIANT} não encontrado, pulando."
    continue
  fi
  TEST_ROOT="${GENERATED_DIR}/TEST.ROOT"
  if [[ -f "${TEST_ROOT}" ]]; then
    log "  ${VARIANT}: TEST.ROOT já existe — mantendo."
  else
    printf '%s\n' "${MINIMAL_TEST_ROOT}" > "${TEST_ROOT}"
    log "  ${VARIANT}: TEST.ROOT criado."
  fi
done

log ""
log "=== Evaluate + preparação concluídos ==="
log "Variantes prontas em: ${RUN_DIR}/variants/"
log ""
log "Para rodar jtreg + JCov (sequencial, CONCURRENCY=1):"
log "  JCOV_VARIANT=direct-tests JTREG_CONCURRENCY=1 JTREG_TIMEOUT_FACTOR=2 \\"
log "    docker compose run --rm overnight bash /data/scripts/run-jcov-docker.sh"
log ""
log "  (aguardar conclusão, depois:)"
log ""
log "  JCOV_VARIANT=wit-context  JTREG_CONCURRENCY=1 JTREG_TIMEOUT_FACTOR=2 \\"
log "    docker compose run --rm overnight bash /data/scripts/run-jcov-docker.sh"

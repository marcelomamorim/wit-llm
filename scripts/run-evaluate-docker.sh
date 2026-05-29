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

# ── Passo 1: Materializar os testes via witup ─────────────────────────────────
log "Materializando testes gerados pelo LLM..."
witup avaliar-estudo-jdk-global \
    --config    "/data/generated/configs/${RUNTIME_CONFIG_FILE}" \
    --generation-model "${GENERATION_MODEL}" \
    --jdk-root  /opt/openjdk-src \
    --run-dir   "${RUN_DIR}" \
    --responses "${RUN_DIR}/responses_openai_batch_generation.jsonl" \
    --errors    "${RUN_DIR}/errors_openai_batch_generation.jsonl"

log "Materialização concluída."

# ── Passo 2: Criar TEST.ROOT mínimo em cada variante ─────────────────────────
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

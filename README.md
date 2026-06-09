# witup-llm

[![CI](https://github.com/marcelomamorim/wit-llm/actions/workflows/ci.yml/badge.svg)](https://github.com/marcelomamorim/wit-llm/actions/workflows/ci.yml)
[![Release CLI](https://github.com/marcelomamorim/wit-llm/actions/workflows/release.yml/badge.svg)](https://github.com/marcelomamorim/wit-llm/actions/workflows/release.yml)
[![Go Report Card](https://goreportcard.com/badge/github.com/marcelomamorim/witup-llm)](https://goreportcard.com/report/github.com/marcelomamorim/witup-llm)
![Go Version](https://img.shields.io/badge/Go-1.24+-00ADD8?logo=go&logoColor=white)
![Target](https://img.shields.io/badge/Target-Java%20projects-orange?logo=openjdk&logoColor=white)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

`witup-llm` é uma CLI em Go para pesquisa sobre geração de testes unitários em projetos Java a partir de dois cenários:

- **WIT-context**: usa a análise WIT (exception paths) como contexto para o LLM;
- **Direct-tests**: gera testes diretamente do código, sem contexto WIT.

O objetivo é medir se o contexto WIT melhora a qualidade das suítes geradas em relação à geração direta.

---

## Experimento JDK (openjdk/jdk)

O experimento principal avalia a geração de testes para o OpenJDK usando o commit `da75f3c4` (JDK 11+28).

### Especificações

| Parâmetro | Valor | Arquivo |
|---|---|---|
| Projeto-alvo | `openjdk/jdk` @ `da75f3c4` (JDK 11+28) | `scripts/run-jdk-full-pilot.sh` |
| Baseline WIT | `generated/wit-data/jdk/wit_filtered.json` (~5.698 métodos) | `internal/aplicacao/jdk_global.go` |
| Modelo LLM | `gpt-4.1-nano-2025-04-14` | `generated/configs/jdk-pilot.runtime.json` |
| Temperature | 0 | `scripts/run-jdk-full-pilot.sh` |
| Max output tokens | 2048 | `scripts/run-jdk-full-pilot.sh` |
| Execução batch | OpenAI Batch API (`/v1/responses`) | `internal/llm/batch.go` |
| Preparação | `witup preparar-estudo-jdk-global` | `internal/aplicacao/comandos_jdk_global.go` |
| Materialização | `witup avaliar-estudo-jdk-global` | `internal/aplicacao/jdk_global.go` |
| Variantes | `baseline`, `wit-context`, `direct-tests` | `internal/aplicacao/jdk_global.go` |
| Framework de testes | jtreg (OpenJDK test runner) | `scripts/run-jtreg-docker.sh` |
| Cobertura | JCov (branch + method + line) | `scripts/run-jcov-pilot-docker.sh`, `scripts/run-jcov-baseline-docker.sh` |
| Baseline jtreg | tier1 + tier2 do JDK completo | `scripts/run-jcov-baseline-docker.sh` |
| Análise de smells | 10 padrões via análise estática | `scripts/detect_test_smells.py`, `scripts/run-smells-docker.sh` |

### Métricas coletadas

- **jtreg** (`scripts/run-jtreg-docker.sh`): pass, fail, error, pass rate — por variante
- **JCov** (`scripts/run-jcov-pilot-docker.sh`, `scripts/run-jcov-docker.sh`): branch, method e line coverage %
- **Cenários comparativos** (calculados via merge no relatório final):
  - `(1) baseline` — testes originais JDK tier1+tier2
  - `(2) baseline + wit-context` — merge JCov sem re-execução do baseline
  - `(3) baseline + direct-tests` — merge JCov sem re-execução do baseline
- **Test smells** (`scripts/detect_test_smells.py`): density, breakdown por tipo (wit-context vs direct-tests)
- **Comparação estatística** (`scripts/analyze_smells_comparison.py`): chi-square, odds ratio por tipo de smell

### Prompts de geração

Arquivo: [`internal/aplicacao/prompts.go`](internal/aplicacao/prompts.go)

#### Variante `wit-context`

Usa duas funções:

**System prompt** — `construirPromptGeracaoSistema("jtreg")`:
- Papel: especialista em testes jtreg para OpenJDK
- Restrições de versão: JDK 11+28 (`da75f3c4`) — proíbe records, text blocks, switch expressions, pattern matching, sealed classes
- Formato obrigatório: cabeçalho `/* @test @summary @run main NomeDaClasse */` em bloco `/* */`
- Módulos internos: obriga `@modules java.base/com.sun.*` para pacotes não exportados
- Checklist pré-resposta: compila? nome bate com arquivo? cabeçalho correto? `@run main`? sem pacotes inacessíveis?
- Regra de ouro: em caso de dúvida, simplificar ao mínimo que compila

**User prompt** — `construirPromptGeracaoUsuario(...)`:
- Recebe: visão geral do projeto, nome do contêiner, lista de métodos com expaths WIT
- Contexto WIT fornecido por método:
  - `method.source_code` — código-fonte completo do método no checkout atual
  - `expaths[]` — lista de caminhos de exceção (tipo, gatilho, condições de guarda, confiança, evidências)
  - `checkout_compatibility_notes` — expaths descartados por incompatibilidade com o checkout
- Instruções de uso dos expaths: tratá-los como hipóteses prioritárias para exceções, mas sempre validar contra o código atual; descartar se contraditório
- Saída esperada: `{"files":[{"relative_path":"...","content":"...","covered_method_ids":[...],"notes":"..."}]}`

#### Variante `direct-tests`

Usa as mesmas funções base com uma diferença:

**System prompt** — `construirPromptGeracaoDiretaSistema("jtreg")`:
- Idêntico ao system prompt wit-context, com sufixo adicional:
  > *"Nesta execução, você não receberá expaths nem contexto WITUP; derive testes diretamente do código-fonte dos métodos fornecidos."*

**User prompt** — `construirPromptGeracaoDiretaUsuario(...)`:
- Recebe: visão geral do projeto, nome do contêiner, lista de métodos **sem expaths**
- Contexto por método: apenas `method.source_code` e `method.signature`
- Sem referência a WIT, expaths ou caminhos de exceção pré-computados
- Instruções: derivar casos excepcionais somente quando evidente no código atual

---

## Como executar o experimento JDK

### Pré-requisitos

- Docker
- Go `1.24+`
- `OPENAI_API_KEY`

### Passo 1 — Build da imagem Docker (GitHub Actions → Docker Hub)

**Arquivos:** [`.github/workflows/build-evaluator-amd64.yml`](.github/workflows/build-evaluator-amd64.yml), [`docker/jdk-builder/Dockerfile`](docker/jdk-builder/Dockerfile), [`docker/evaluator/Dockerfile`](docker/evaluator/Dockerfile)

A imagem `witup-llm/evaluator` contém o JDK 11+28 compilado, jtreg e JCov.
O build para `linux/amd64` é feito automaticamente via GitHub Actions:

1. Configurar secrets no repositório GitHub:
   - `DOCKERHUB_USERNAME` — seu usuário Docker Hub
   - `DOCKERHUB_TOKEN` — token de acesso Docker Hub

2. Disparar o workflow:
   - GitHub → **Actions** → **"Build evaluator (amd64) → Docker Hub"** → **Run workflow**

3. A imagem é publicada em: `cloudarchlab/witup-evaluator:amd64`

> Tempo estimado: ~60–90 min (compila OpenJDK do zero)

### Passo 2 — Geração dos testes (OpenAI Batch)

**Arquivos:** [`scripts/run-jdk-full-pilot.sh`](scripts/run-jdk-full-pilot.sh), [`scripts/poll-openai-batch.sh`](scripts/poll-openai-batch.sh), [`internal/aplicacao/comandos_jdk_global.go`](internal/aplicacao/comandos_jdk_global.go), [`internal/llm/batch.go`](internal/llm/batch.go)

```bash
# Piloto com N métodos (recomendado: 10–100 para validação)
PILOT_METHODS=100 \
SKIP_BUILD_IMAGE=sim \
  ./scripts/run-jdk-full-pilot.sh
```

O script executa automaticamente:
1. Clona o JDK @ `da75f3c4`
2. Amostra N métodos do `wit_filtered.json` via `witup preparar-estudo-jdk-global`
3. Gera requests JSONL (wit-context + direct-tests) com prompts de `internal/aplicacao/prompts.go`
4. Submete à OpenAI Batch API via `witup submeter-openai-batch`
5. Aguarda conclusão via polling (`scripts/poll-openai-batch.sh`)
6. Materializa as 3 variantes via `witup avaliar-estudo-jdk-global`

Para re-rodar com respostas já baixadas:

```bash
SKIP_BATCH_SUBMIT=sim \
RUN_STAMP=<stamp> \
RESPONSES_JSONL=generated/experiments/jdk-pilot/<stamp>/responses_openai_batch_generation.jsonl \
  ./scripts/run-jdk-full-pilot.sh
```

### Passo 3 — Execução do JCov no AWS CodeBuild

**Arquivos:** [`scripts/run-jcov-baseline-docker.sh`](scripts/run-jcov-baseline-docker.sh), [`scripts/run-jcov-docker.sh`](scripts/run-jcov-docker.sh), [`scripts/run-jcov-pilot-docker.sh`](scripts/run-jcov-pilot-docker.sh)

A medição de cobertura JCov (tier1+tier2 do JDK completo) é executada no AWS CodeBuild para aproveitar alta concorrência (72 vCPUs).

#### 3.1 — Preparar dados

```bash
# Zipar os testes gerados
cd generated/experiments/jdk-pilot/<RUN_STAMP>
zip -r variants-generated.zip \
  variants/wit-context/test/jdk/witup/generated \
  variants/direct-tests/test/jdk/witup/generated
```

Upload do `variants-generated.zip` para o bucket S3 `witup-jcov-results` via console AWS.

#### 3.2 — Projeto CodeBuild

| Configuração | Valor |
|---|---|
| Nome | `witup-jcov-baseline` |
| Imagem | `cloudarchlab/witup-evaluator:amd64` |
| Compute | `BUILD_GENERAL1_2XLARGE` (72 vCPUs) |
| Timeout | 480 minutos |
| `JTREG_CONCURRENCY` | 48 |
| Bucket de saída | `witup-jcov-results` |

O buildspec executa em sequência:
1. Instala AWS CLI
2. Baixa testes gerados do S3
3. Roda JCov no **baseline** via `run-jcov-baseline-docker.sh` (tier1+tier2 do JDK)
4. Roda JCov no **wit-context** via `run-jcov-docker.sh`
5. Roda JCov no **direct-tests** via `run-jcov-docker.sh`
6. Merge: `baseline + wit` e `baseline + direct` via `java -jar jcov.jar Merger`
7. Extrai métricas (branch/method/line) → `jcov-summary.json`
8. Salva tudo no S3

> Tempo estimado: ~2–4 horas com 72 vCPUs

#### 3.3 — Baixar resultados

Após o build, baixar do S3:
- `jcov-summary.json` — métricas consolidadas (branch/method/line por cenário)
- `jcov-baseline/jcov-result.xml` — XML de cobertura do baseline
- `jcov-merged-baseline-wit.xml` — cobertura combinada baseline+wit
- `jcov-merged-baseline-direct.xml` — cobertura combinada baseline+direct

Mover para: `generated/experiments/jdk-pilot/<RUN_STAMP>/`

### Passo 4 — Test Smells e Relatório Final

**Arquivos:** [`scripts/run-smells-docker.sh`](scripts/run-smells-docker.sh), [`scripts/detect_test_smells.py`](scripts/detect_test_smells.py), [`scripts/analyze_smells_comparison.py`](scripts/analyze_smells_comparison.py), [`scripts/run-jdk-full-pilot.sh`](scripts/run-jdk-full-pilot.sh)

```bash
# Rodar smells (local, dentro do Docker)
docker compose run --rm \
  -e EXPERIMENT_DIR=jdk-pilot \
  -e RUN_STAMP=<RUN_STAMP> \
  run-smells

# Gerar relatório comparativo final
SKIP_BATCH_SUBMIT=sim \
RUN_STAMP=<RUN_STAMP> \
RESPONSES_JSONL=generated/experiments/jdk-pilot/<RUN_STAMP>/responses_openai_batch_generation.jsonl \
  ./scripts/run-jdk-full-pilot.sh
```

O relatório final (`pilot-final-report.json`) consolida:
- jtreg: 3 cenários com merge (pass/fail/error/pass rate)
- JCov: branch/method/line coverage por cenário e combinado
- Test smells: density e breakdown por tipo (wit-context vs direct-tests)

**Smells detectados** (`scripts/detect_test_smells.py`):

| Smell | Descrição |
|---|---|
| `empty_test` | Teste sem assertions |
| `assertion_roulette` | ≥3 assertions sem mensagens descritivas |
| `exception_catching` | `catch(Exception e)` genérico sem comentário "expected" |
| `conditional_logic` | `if`/`for`/`while`/`switch` no corpo do teste |
| `redundant_println` | `System.out.println` no teste |
| `verbose_test` | Método de teste com mais de 30 linhas |
| `ignored_test` | `@Ignore`/`@Disabled` ou `@run main` ausente |
| `sleepy_test` | `Thread.sleep` no teste |
| `empty_catch` | Bloco `catch` completamente vazio |
| `expected_exception_catch` | Padrão jtreg correto para exceções esperadas *(métrica positiva, não é smell)* |

---

## Experimento — 7 Projetos Open Source

Experimento secundário com projetos Maven (JUnit 4/5 + JaCoCo + PIT):

- Apache Commons IO
- Apache Commons Lang
- H2 Database
- HttpComponents Client
- Jackson Databind
- Joda-Time
- Apache Log4j 2

```bash
export OPENAI_API_KEY="sua-chave"
CONFIRMAR_EXECUCAO_PAGA=sim ./scripts/run-article-main-batch-pipeline.sh
```

---

## Pré-requisitos gerais

- Go `1.24+`
- Docker
- `OPENAI_API_KEY`
- AWS CLI (opcional — apenas para upload S3 automatizado)

## Instalação

```bash
git clone https://github.com/marcelomamorim/wit-llm.git
cd witup-llm
make build
```

## Documentação

- [`docs/`](docs/) — documentação navegável
- [`pipeline.example.json`](pipeline.example.json) — configuração de referência
- [`scripts/`](scripts/) — todos os scripts de execução

## Base acadêmica

1. Diego Marcilio, Carlo A. Furia. *Lightweight precise automatic extraction of exception preconditions in java methods*. Empirical Software Engineering, 29, artigo 30, 2024. DOI: [10.1007/s10664-023-10392-x](https://doi.org/10.1007/s10664-023-10392-x)
2. Diego Marcilio, Carlo A. Furia. *What Is Thrown? Lightweight Precise Automatic Extraction of Exception Preconditions in Java Methods*. ICSME 2022. DOI: [10.1109/ICSME55016.2022.00038](https://doi.org/10.1109/ICSME55016.2022.00038)
3. Diego Marcilio, Carlo A. Furia. *How Java Programmers Test Exceptional Behavior*. MSR 2021. DOI: [10.1109/MSR52588.2021.00033](https://doi.org/10.1109/MSR52588.2021.00033)

## Licença

MIT. Veja [`LICENSE`](LICENSE).

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

| Parâmetro | Valor |
|---|---|
| Projeto-alvo | `openjdk/jdk` @ `da75f3c4` (JDK 11+28) |
| Baseline WIT | `generated/wit-data/jdk/wit_filtered.json` (~5.698 métodos) |
| Modelo LLM | `gpt-4.1-nano-2025-04-14` |
| Temperature | 0 |
| Max output tokens | 2048 |
| Execução batch | OpenAI Batch API (`/v1/responses`) |
| Variantes | `baseline`, `wit-context`, `direct-tests` |
| Framework de testes | jtreg (OpenJDK test runner) |
| Cobertura | JCov (branch + method + line) |
| Baseline jtreg | tier1 + tier2 do JDK completo |
| Análise de smells | 10 padrões via análise estática |

### Métricas coletadas

- **jtreg**: pass, fail, error, pass rate — por variante e por cenário combinado
- **JCov**: branch coverage %, method coverage %, line coverage %
- **Cenários comparativos**:
  - `(1) baseline` — testes originais JDK tier1+tier2
  - `(2) baseline + wit-context` — merge sem re-execução
  - `(3) baseline + direct-tests` — merge sem re-execução
- **Test smells**: density, breakdown por tipo (wit-context vs direct-tests)

---

## Como executar o experimento JDK

### Pré-requisitos

- Docker
- Go `1.24+`
- `OPENAI_API_KEY`

### Passo 1 — Build da imagem Docker (GitHub Actions → Docker Hub)

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

```bash
# Piloto com N métodos (recomendado: 10–100 para validação)
PILOT_METHODS=100 \
SKIP_BUILD_IMAGE=sim \
  ./scripts/run-jdk-full-pilot.sh
```

O script executa automaticamente:
1. Clona o JDK @ `da75f3c4`
2. Amostra N métodos do `wit_filtered.json`
3. Gera requests JSONL (wit-context + direct-tests)
4. Submete à OpenAI Batch API
5. Aguarda conclusão (polling automático)
6. Materializa as 3 variantes

Para re-rodar com respostas já baixadas:

```bash
SKIP_BATCH_SUBMIT=sim \
RUN_STAMP=<stamp> \
RESPONSES_JSONL=generated/experiments/jdk-pilot/<stamp>/responses_openai_batch_generation.jsonl \
  ./scripts/run-jdk-full-pilot.sh
```

### Passo 3 — Execução do JCov no AWS CodeBuild

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
3. Roda JCov no **baseline** (tier1+tier2 do JDK)
4. Roda JCov no **wit-context** (testes gerados)
5. Roda JCov no **direct-tests** (testes gerados)
6. Merge: `baseline + wit` e `baseline + direct`
7. Extrai métricas → `jcov-summary.json`
8. Salva tudo no S3

> Tempo estimado: ~2–4 horas com 72 vCPUs

#### 3.3 — Baixar resultados

Após o build, baixar do S3:
- `jcov-summary.json` — métricas consolidadas
- `jcov-baseline/jcov-result.xml` — XML de cobertura do baseline
- `jcov-merged-baseline-wit.xml` — cobertura combinada
- `jcov-merged-baseline-direct.xml` — cobertura combinada

Mover para: `generated/experiments/jdk-pilot/<RUN_STAMP>/`

### Passo 4 — Test Smells e Relatório Final

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
- jtreg: 3 cenários com merge
- JCov: branch/method/line coverage
- Test smells: wit-context vs direct-tests

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

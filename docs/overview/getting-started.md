# Primeiros Passos

## Pré-requisitos

| Ferramenta | Versão mínima | Uso |
| :--- | :--- | :--- |
| Go | 1.24+ | Compilar a CLI |
| Java | 11+ | Boot JDK para o OpenJDK/JDK historico |
| Git | atual | Clonar e manter os projetos-alvo |
| autoconf | atual | Gerar `configure` do OpenJDK quando necessario |
| Xcode/SDK macOS | compativel | Compilar o OpenJDK no macOS |
| jtreg | 7.x | Executar a suite de testes do JDK |
| JCov | 3.x | Medir cobertura estrutural no JDK |

Além disso, você vai precisar de:

- `OPENAI_API_KEY`
- checkout local do OpenJDK/JDK;
- baseline WIT filtrado em `resources/wit-replication-package/data/output/jdk/wit_filtered.json`;
- JDK compilado em `build/.../images/jdk` para rodar `jtreg`.

## Compilação

```bash
git clone https://github.com/marcelomamorim/wit-llm.git
cd wit-llm
go build -o bin/witup ./cmd/witup
```

## Preparar uma rodada JDK sem chamada paga

O preparo gera manifesto e JSONL Batch, mas nao chama a API da OpenAI:

```bash
METHOD_COUNT=200 \
JDK_ROOT=/Users/marceloamorim/Documents/unb/jdk \
./scripts/prepare-jdk-global-impact-experiment.sh
```

O log imprime o `RUN_DIR` e o caminho de `requests_*_openai_batch_generation.jsonl`.

## Submeter e coletar Batch

```bash
export OPENAI_API_KEY="sua-chave"

RUN_DIR=<diretorio-da-rodada> \
REQUESTS_JSONL=<arquivo-requests-jsonl> \
CONFIRMAR_EXECUCAO_PAGA=sim \
./scripts/submit-article-main-batch.sh

RUN_DIR=<diretorio-da-rodada> \
BATCH_ID=<batch-id> \
./scripts/collect-article-main-batch.sh
```

## Materializar variantes

Depois da coleta, materialize as respostas em variantes do JDK:

```bash
RUN_DIR=<diretorio-da-rodada> \
JDK_ROOT=/Users/marceloamorim/Documents/unb/jdk \
./scripts/evaluate-jdk-global-impact-experiment.sh
```

## O que sai no final da rodada JDK

- `manifest_jdk_global_methods.csv`
- `requests_*_openai_batch_generation.jsonl`
- `responses_openai_batch_generation.jsonl`
- `results_jdk_global_impact.json`
- `results_jdk_global_jtreg_summary.csv`
- `results_jdk_jcov_200_fast_summary.csv`
- `results_jdk_exception_coverage_metrics.csv`
- `resultado_jcov_200_fast_fixed_include.md`
- `resultado_exception_coverage_metrics.md`

## Quando algo der errado

Os pontos mais comuns para revisar são:

1. caminho do checkout OpenJDK;
2. caminho do baseline WIT filtrado;
3. `OPENAI_API_KEY`;
4. disponibilidade de `jtreg`;
5. compatibilidade do JCov com os testes escolhidos;
6. testes sensiveis ao agente, como `java/lang/System/LoggerFinder/**`.

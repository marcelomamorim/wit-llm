# witup-llm

[![CI](https://github.com/marcelomamorim/witup-llm/actions/workflows/ci.yml/badge.svg)](https://github.com/marcelomamorim/witup-llm/actions/workflows/ci.yml)
[![Release CLI](https://github.com/marcelomamorim/witup-llm/actions/workflows/release.yml/badge.svg)](https://github.com/marcelomamorim/witup-llm/actions/workflows/release.yml)
[![Go Report Card](https://goreportcard.com/badge/github.com/marcelomamorim/witup-llm)](https://goreportcard.com/report/github.com/marcelomamorim/witup-llm)
![Go Version](https://img.shields.io/badge/Go-1.24+-00ADD8?logo=go&logoColor=white)
![Target](https://img.shields.io/badge/Target-Java%20projects-orange?logo=openjdk&logoColor=white)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

`witup-llm` é uma CLI em Go para pesquisa sobre geração de testes unitários em projetos Java a partir de dois cenários:

- usar a análise WIT como contexto;
- gerar testes diretamente do código, sem contexto WIT.

Na fase atual, o foco experimental está em uma **rodada pareada para artigo** com seis projetos já presentes no pacote WIT local:

- **Jackson Databind**
- **HttpComponents Client**
- **Apache Commons Lang**
- **Apache Commons IO**
- **Apache Commons Text**
- **Byte Buddy**

## Estado atual

O projeto já está preparado para executar a **rodada principal do artigo** com:

- seleção explícita dos projetos em `phase_two.projects`;
- uso de baselines WIT locais por projeto;
- geração de 20 slices alinháveis por projeto, com 1 método por slice;
- comparação pareada entre `WIT_CONTEXT` e `DIRECT_TESTS`;
- execução principal em `strict_1call`;
- geração via OpenAI Batch API com `gpt-5.4-mini`;
- juiz IA desativado na rodada principal;
- geração e avaliação das duas estratégias sobre os **mesmos métodos-alvo**;
- saída consolidada em:
  - `results_*_paired_study.json`
  - `results_*_paired_summary.csv`
  - `results_*_paired_metrics.csv`
  - `results_*_paired_comparison.csv`
  - `analysis_*_statistical_inference.md`
  - `dashboard_*_wit_expath_regression.html`

## Pergunta experimental desta fase

Para cada projeto, queremos comparar:

1. **WIT_CONTEXT**
   Usa a análise WIT como contexto para a geração de testes.

2. **DIRECT_TESTS**
   Gera os testes diretamente a partir do código local, sem expaths/contexto WIT.

O objetivo é medir se o contexto WIT realmente melhora a qualidade das suítes em relação à geração direta.

## Como o fluxo funciona

1. carrega o baseline WIT local do projeto;
2. cataloga o checkout Java;
3. alinha o baseline WIT ao checkout atual;
4. usa os métodos alinhados como conjunto-alvo comum;
5. executa os dois cenários:
   - com contexto WIT
   - geração direta
6. avalia as suítes geradas com métricas como:
   - compilação
   - testes executados
   - JaCoCo line
   - JaCoCo branch
   - PIT mutation
7. gera:
   - JSON consolidado
   - CSVs analíticos
   - dashboard HTML

O dashboard da fase 2 agora também mostra, por cenário:

- métricas com explicação contextual;
- parecer do juiz em português;
- auditoria de uso da IA (`request_count`, `repair_used`, `input_tokens`, `output_tokens`, `estimated_cost`);
- seção expansível com a classe de teste gerada e os métodos-alvo cobertos.

## Saídas principais

Ao final de `executar-segunda-fase`, você recebe:

- `phase-two-study.json`
- `csv/phase-two-summary.csv`
- `csv/phase-two-metrics.csv`
- `csv/phase-two-comparison.csv`
- `csv/phase-two-statistics.csv`
- `csv/phase-two-statistics.md`
- `dashboard.html`

Esses artefatos ficam dentro do diretório de saída configurado.

O modo padrão da fase 2 é `repair_1retry`, com no máximo uma tentativa de
reparo por cenário. Para uma comparação estrita e simétrica de orçamento de
inferência, use `strict_1call`.

## Harness para codificar com Codex

O projeto agora usa uma estrutura leve de harness engineering para orientar o
trabalho com agentes de código:

- [`AGENTS.md`](/Users/marceloamorim/Documents/unb/witup-llm/AGENTS.md) funciona como índice curto para o Codex;
- [`ARCHITECTURE.md`](/Users/marceloamorim/Documents/unb/witup-llm/ARCHITECTURE.md) resume o desenho técnico;
- [`docs/harness/`](/Users/marceloamorim/Documents/unb/witup-llm/docs/harness) guarda validação, rodadas pagas e padrões de falha;
- [`scripts/validar-codex.sh`](/Users/marceloamorim/Documents/unb/witup-llm/scripts/validar-codex.sh) executa os sensores baratos antes de uma rodada paga.

Para validar mudanças locais sem gastar créditos:

```bash
./scripts/validar-codex.sh
```

## Pré-requisitos

- Go `1.24+`
- Git
- Java `11+` ou `17+`
- Maven (`mvn`) ou `mvnw` no projeto-alvo
- `OPENAI_API_KEY`
- baselines WIT locais já processados em `resources/wit-replication-package/data/output`

Os scripts da primeira rodada clonam automaticamente os checkouts em `generated/repos/`.

## Instalação

```bash
git clone https://github.com/marcelomamorim/witup-llm.git
cd witup-llm
make build
```

Ou:

```bash
go build -o bin/witup ./cmd/witup
```

## Configuração

O projeto usa JSON versionado para configuração.

Arquivo-base:

- [`/Users/marceloamorim/Documents/unb/witup-llm/pipeline.example.json`](/Users/marceloamorim/Documents/unb/witup-llm/pipeline.example.json)

Perfil pronto para a fase nova:

- [`/Users/marceloamorim/Documents/unb/witup-llm/pipelines/fase-dois-guava-commons.json`](/Users/marceloamorim/Documents/unb/witup-llm/pipelines/fase-dois-guava-commons.json)

Campos mais importantes:

- `project.root`
- `pipeline.output_dir`
- `models.*`
- `metrics`
- `phase_two.projects[*].root`
- `phase_two.projects[*].wit_analysis_path`
- `phase_two.projects[*].target_containers`

## Execução rápida

### Primeira rodada estatística

Preparação barata, sem chamada OpenAI:

```bash
./scripts/preparar-primeira-rodada-estatistica.sh
```

Preflight com build mínimo, ainda sem chamada OpenAI:

```bash
./scripts/executar-primeira-rodada-estatistica.sh
```

Execução paga, somente após o preflight retornar todos os 120 slices como prontos:

```bash
export OPENAI_API_KEY="sua-chave"
CONFIRMAR_EXECUCAO_PAGA=sim ./scripts/run-article-main-batch-pipeline.sh
```

Detalhes importantes:

- o modelo padrão da rodada de artigo é `gpt-5.4-mini`;
- o backend de geração é OpenAI Batch API;
- o modo da rodada é `strict_1call`;
- o script seleciona 20 slices alinháveis por projeto;
- para o commit histórico do Jackson, o harness instala um fallback local do parent POM e desativa o perfil Maven `java14+` com `-P!java14+`.

### Opção 1: comando direto

```bash
./bin/witup preflight-segunda-fase \
  --config pipelines/fase-dois-guava-commons.json \
  --check-build

./bin/witup executar-segunda-fase \
  --config pipelines/fase-dois-guava-commons.json \
  --generation-model openai_main
```

### Opção 2: script dedicado

```bash
export OPENAI_API_KEY="sua-chave"
export GUAVA_ROOT="/caminho/para/guava"
export GUAVA_WIT_ANALYSIS="/caminho/para/guava-wit.json"
export COMMONS_COLLECTIONS_ROOT="/caminho/para/commons-collections"
export COMMONS_COLLECTIONS_WIT_ANALYSIS="/caminho/para/commons-collections-wit.json"

./scripts/executar-segunda-fase.sh
```

### Gerando os baselines WIT dos dois projetos

Se você ainda não tiver `guava/wit.json` e `commons-collections/wit.json`, use:

```bash
./scripts/gerar-baselines-wit.sh
```

O script cria automaticamente um virtualenv local em `generated/tools/wit-venv`
quando as dependências Python do WIT não estiverem disponíveis no Python base.
Para o Guava, ele usa por padrão o subprojeto `guava/` dentro do checkout, que é
o módulo principal que queremos analisar nesta fase, e faz checkout esparso
desse subdiretório para evitar baixar/popular o workspace com módulos extras.

Saídas esperadas:

- `generated/wit-output/guava/wit.json`
- `generated/wit-output/commons-collections/wit.json`

### Piloto reduzido: um projeto, uma classe

Antes de rodar a segunda fase completa, você pode validar o fluxo com um
piloto menor, focado em uma única classe de um dos projetos:

```bash
export OPENAI_API_KEY="sua-chave"
export PILOT_PROJECT_KEY="guava"
export PILOT_PROJECT_LABEL="Google Guava"
export PILOT_WIT_ANALYSIS="/caminho/para/guava-wit.json"
export PILOT_TARGET_CONTAINER="com.google.common.collect.ImmutableList"

./scripts/executar-piloto-segunda-fase.sh
```

O piloto usa o mesmo fluxo da fase dois, mas restringe a execução aos métodos
da classe informada em `PILOT_TARGET_CONTAINER`.

Se `PILOT_PROJECT_KEY=guava` e `PILOT_PROJECT_ROOT` não for informado, o script
clona automaticamente o checkout em `generated/repos/guava`.

### Commons IO já processado (`wit_filtered.json`)

Para um estudo mais barato de iniciar, o projeto já vem com um baseline filtrado
do `commons-io` em:

- `resources/wit-replication-package/data/output/commons-io/wit_filtered.json`

Você pode rodar um piloto por classe com:

```bash
export OPENAI_API_KEY="sua-chave"
./scripts/executar-piloto-commons-io-filtrado.sh
```

O piloto usa por padrão dois contêineres para aumentar a amostra inicial do
estudo:

- `org.apache.commons.io.IOCase`
- `org.apache.commons.io.input.BoundedReader`

Se quiser forçar uma única classe no piloto, use:

```bash
export PILOT_TARGET_CONTAINER="org.apache.commons.io.input.BoundedReader"
./scripts/executar-piloto-commons-io-filtrado.sh
```

Se quiser escolher explicitamente mais de um contêiner, use
`PILOT_TARGET_CONTAINERS` com uma lista separada por vírgulas:

```bash
export PILOT_TARGET_CONTAINERS="org.apache.commons.io.IOCase,org.apache.commons.io.input.BoundedReader"
./scripts/executar-piloto-commons-io-filtrado.sh
```

E, quando estiver confortável, pode rodar o estudo completo do `commons-io`
com o baseline filtrado:

```bash
export OPENAI_API_KEY="sua-chave"
./scripts/executar-estudo-commons-io-filtrado.sh
```

## Limpeza do workspace

```bash
./scripts/limpar-projeto.sh --confirmar
```

Isso remove artefatos gerados e recompõe o diretório de trabalho limpo para uma nova rodada.

## Cobertura e qualidade

Os testes automatizados do código Go foram validados com:

```bash
GOCACHE=$(pwd)/.gocache go test ./...
```

A etapa atual priorizou:

- estabilizar a segunda fase;
- tornar o fluxo reproduzível para Guava e Commons Collections;
- simplificar o fluxo para artefatos locais em JSON, CSV e HTML.

## Base acadêmica

Esta lista resume alguns dos trabalhos mais diretamente relacionados ao protocolo experimental e à baseline usada neste repositório.

1. Diego Marcilio, Carlo A. Furia. *Lightweight precise automatic extraction of exception preconditions in java methods*. Empirical Software Engineering, 29, artigo 30, 2024. DOI: [10.1007/s10664-023-10392-x](https://doi.org/10.1007/s10664-023-10392-x)
2. Diego Marcilio, Carlo A. Furia. *What Is Thrown? Lightweight Precise Automatic Extraction of Exception Preconditions in Java Methods*. ICSME 2022. DOI: [10.1109/ICSME55016.2022.00038](https://doi.org/10.1109/ICSME55016.2022.00038)
3. Diego Marcilio, Carlo A. Furia. *How Java Programmers Test Exceptional Behavior*. MSR 2021. DOI: [10.1109/MSR52588.2021.00033](https://doi.org/10.1109/MSR52588.2021.00033)

## Documentação

A documentação navegável do projeto fica em `docs/` e no site MkDocs.

Pontos de entrada mais úteis:

- [`/Users/marceloamorim/Documents/unb/witup-llm/docs/index.md`](/Users/marceloamorim/Documents/unb/witup-llm/docs/index.md)
- [`/Users/marceloamorim/Documents/unb/witup-llm/docs/overview/getting-started.md`](/Users/marceloamorim/Documents/unb/witup-llm/docs/overview/getting-started.md)
- [`/Users/marceloamorim/Documents/unb/witup-llm/docs/overview/configuration.md`](/Users/marceloamorim/Documents/unb/witup-llm/docs/overview/configuration.md)

## Contribuição

Pull requests são bem-vindos, principalmente em:

- robustez da geração de testes;
- avaliação com JaCoCo/PIT;
- suporte a novos projetos Java;
- qualidade da visualização HTML/CSV da fase dois.

## Licença

MIT. Veja [`LICENSE`](/Users/marceloamorim/Documents/unb/witup-llm/LICENSE).

# witup-llm

[![CI](https://github.com/marcelomamorim/witup-llm/actions/workflows/ci.yml/badge.svg)](https://github.com/marcelomamorim/witup-llm/actions/workflows/ci.yml)
[![Release CLI](https://github.com/marcelomamorim/witup-llm/actions/workflows/release.yml/badge.svg)](https://github.com/marcelomamorim/witup-llm/actions/workflows/release.yml)
[![Go Report Card](https://goreportcard.com/badge/github.com/marcelomamorim/witup-llm)](https://goreportcard.com/report/github.com/marcelomamorim/witup-llm)
![Go Version](https://img.shields.io/badge/Go-1.24+-00ADD8?logo=go&logoColor=white)
![Target](https://img.shields.io/badge/Target-Java%20projects-orange?logo=openjdk&logoColor=white)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

`witup-llm` é uma CLI em Go para pesquisa sobre geração de exception paths em projetos Java.

## Status do projeto

Este repositório está em `pesquisa ativa`.

Hoje ele já permite:
- carregar o baseline do artigo no DuckDB;
- comparar `WITUP_ONLY`, `LLM_ONLY` e `WITUP_PLUS_LLM`;
- gerar testes unitários a partir dos expaths;
- consolidar resultados e métricas no DuckDB.

Hoje ele ainda tem limitações importantes:
- a cobertura global de testes ainda não atingiu a meta de `>90%`;
- a meta de `>90%` em mutação ainda não foi atingida;
- a Parte 2 do experimento ainda depende da maturidade das métricas configuradas no pipeline para produzir resultados fortes em todos os projetos.

Esse posicionamento explícito ajuda o repositório a ficar mais honesto como projeto open source: ele já é utilizável, mas ainda é um protótipo de pesquisa em evolução.

O projeto compara três variantes experimentais:
- `WITUP_ONLY`
- `LLM_ONLY`
- `WITUP_PLUS_LLM`

O baseline do artigo é carregado primeiro em `DuckDB`. Depois disso, o `witup-llm` passa a interagir com os dados do artigo pelo banco, e não diretamente pela pasta `resources/`.

O protocolo de pesquisa tem duas comparações:

1. comparar a qualidade dos `expaths` gerados por `WITUP` e `LLM`;
2. gerar testes unitários a partir dessas fontes e comparar a qualidade das
   suítes derivadas.

Na branch `LLM_ONLY`, o experimento agora trabalha com `dois modos`:

- `direct`: uma chamada por método para varredura ampla;
- `multiagent`: começa com a varredura direta e só faz refino multiagente em
  casos `maybe`, divergências, métodos interprocedurais e um subconjunto
  adicional de validação profunda.

Em ambos os modos, a LLM analisa apenas o subconjunto de métodos do checkout
atual que foi resolvido a partir da baseline do WITUP.

## Visão geral

O fluxo principal é:

1. carregar as baselines do artigo para o DuckDB;
2. consultar e materializar a baseline WITUP a partir do banco;
3. alinhar a baseline WITUP ao checkout atual e resolver os métodos-alvo;
4. executar a branch `LLM_ONLY` sobre esse mesmo conjunto-alvo;
5. comparar WITUP e LLM;
6. materializar `WITUP_PLUS_LLM`;
7. registrar os artefatos gerados no DuckDB para consulta posterior.

## Referências bibliográficas

Esta lista resume alguns dos trabalhos mais diretamente relacionados ao
protocolo experimental e à baseline usada neste repositório. Ela não pretende
ser exaustiva e pode crescer conforme a pesquisa evoluir.

1. Diego Marcilio, Carlo A. Furia. *Lightweight precise automatic extraction of exception preconditions in java methods*. Empirical Software Engineering, 29, artigo 30, 2024. DOI: [10.1007/s10664-023-10392-x](https://doi.org/10.1007/s10664-023-10392-x)
2. Diego Marcilio, Carlo A. Furia. *What Is Thrown? Lightweight Precise Automatic Extraction of Exception Preconditions in Java Methods*. In: Proceedings of the 38th IEEE International Conference on Software Maintenance and Evolution (ICSME 2022), pp. 340-351. DOI: [10.1109/ICSME55016.2022.00038](https://doi.org/10.1109/ICSME55016.2022.00038)
3. Diego Marcilio, Carlo A. Furia. *How Java Programmers Test Exceptional Behavior*. In: Proceedings of the 18th IEEE/ACM International Conference on Mining Software Repositories (MSR 2021), pp. 207-218. DOI: [10.1109/MSR52588.2021.00033](https://doi.org/10.1109/MSR52588.2021.00033)

## Stack

- Go 1.24+
- DuckDB como armazenamento analítico e índice de artefatos
- projetos Java como alvo
- OpenAI via `Responses API` com `prompt_cache_key`
- provedores compatíveis com OpenAI e Ollama
- GitHub Actions para CI e releases
- configuração de pipeline em JSON versionado

## Pré-requisitos

- Go `1.24+`
- Git
- acesso ao pacote de replicação WITUP em `resources/wit-replication-package/data/output`
- uma chave válida em `OPENAI_API_KEY` para as execuções com OpenAI
- Java para avaliar as suítes geradas
- Maven, `mvnw` ou acesso de rede para o fallback automático de download do Maven no script do piloto

## Instalação

```bash
git clone https://github.com/marcelomamorim/witup-llm.git
cd witup-llm
make build
```

O binário será gerado em `bin/witup`.

## Configuração

O projeto usa arquivos JSON em `pipelines/`.

Comece copiando o exemplo base:

```bash
cp pipeline.example.json pipeline.local.json
```

Depois ajuste os campos principais:
- `project.root`
- `pipeline.output_dir`
- `pipeline.duckdb_path`
- `pipeline.replication_root`
- `pipeline.baseline_file`
- `pipeline.llm_mode`
- `pipeline.deep_validation_subset_size`

Exemplo mínimo:

```json
{
  "version": "1",
  "project": {
    "root": "/caminho/do/projeto-java"
  },
  "pipeline": {
    "output_dir": "./generated",
    "duckdb_path": "./generated/witup-llm.duckdb",
    "replication_root": "./resources/wit-replication-package/data/output",
    "baseline_file": "wit.json",
    "save_prompts": true,
    "max_methods": 0,
    "llm_mode": "multiagent",
    "deep_validation_subset_size": 8
  }
}
```

O schema está em [`schemas/pipeline.schema.json`](schemas/pipeline.schema.json).

## Início rápido

1. Configure sua API key:

```bash
export OPENAI_API_KEY="sua-chave"
```

2. Teste conectividade do modelo:

```bash
./bin/witup sondar --config pipeline.local.json --model openai_main
```

3. Carregue as baselines do artigo no DuckDB:

```bash
./bin/witup ingerir-witup --config pipeline.local.json
```

4. Abra a interface gráfica do banco:

```bash
./bin/witup visualizar-dados --config pipeline.local.json
```

Depois abra:

```text
http://127.0.0.1:8421
```

5. Execute apenas a Parte 1 do experimento para um projeto:

```bash
./bin/witup executar-experimento \
  --config pipeline.local.json \
  --model openai_main \
  --project-key commons-io
```

6. Execute o estudo completo para um projeto:

```bash
./bin/witup executar-estudo-completo \
  --config pipeline.local.json \
  --analysis-model openai_main \
  --generation-model openai_main \
  --judge-model openai_judge \
  --project-key commons-io
```

7. Gere testes manualmente a partir de uma variante, se quiser isolar a Parte 2:

```bash
./bin/witup gerar \
  --config pipeline.local.json \
  --analysis generated/<run-id>/variants/witup-plus-llm.analysis.json \
  --model openai_main
```

8. Consolide manualmente a Parte 1 e a Parte 2 em um único artefato, se necessário:

```bash
./bin/witup consolidar-estudo \
  --config pipeline.local.json \
  --summary generated/<run-id>/estudo-completo.json \
  --project-key commons-io
```

## Como executar a pesquisa completa

Este é o fluxo mais direto para rodar o piloto do `visualee` ponta a ponta.

### 1. Limpar artefatos anteriores

```bash
./scripts/limpar-projeto.sh --confirmar
```

### 2. Validar a integração real com a OpenAI

```bash
export OPENAI_API_KEY="sua-chave-aqui"
./bin/witup sondar --config pipeline.local.json --model openai_main
```

### 3. Executar o piloto do `visualee`

```bash
export OPENAI_API_KEY="sua-chave-aqui"
./scripts/executar-visualee-piloto.sh
```

O script já executa:

1. clone e checkout no commit do artigo;
2. carga do baseline no DuckDB;
3. alinhamento do conjunto-alvo do WITUP com o checkout local;
4. `WITUP_ONLY`, `LLM_ONLY` e `WITUP_PLUS_LLM`;
5. `LLM_ONLY` em `direct` ou `multiagent`, conforme o JSON;
6. geração de testes por variante;
7. avaliação das suítes geradas por variante;
8. geração automática de gráficos textuais a partir do DuckDB;
9. persistência do estudo completo e índices no DuckDB;
10. exportação histórica da execução em arquivos `.parquet` dentro de `historico/`.

Variáveis opcionais úteis:

```bash
MAX_METHODS=25
LLM_MODE=multiagent
OPENAI_MODEL=gpt-5.4
OPENAI_API_KEY_LOCAL=""
```

Para rodar explicitamente as três variantes do estudo em sequência e já receber os
diretórios principais ao final:

```bash
./scripts/executar-visualee-rodadas.sh
```

### 4. Abrir a interface do DuckDB

```bash
./bin/witup visualizar-dados --config generated/configs/piloto-visualee.runtime.json
```

Depois abra:

```text
http://127.0.0.1:8421
```

### 5. Consultas principais para a pesquisa

**Parte 1: qualidade dos expaths**

Resumo por execução:

```sql
SELECT *
FROM vw_comparacao_fontes_resumo
ORDER BY gerado_em DESC;
```

Colunas principais da Parte 1:
- `taxa_cobertura_metodos_llm_sobre_witup`
- `taxa_cobertura_expaths_llm_sobre_witup`
- `taxa_precisao_estrutural_llm`
- `indice_jaccard_expaths`
- `taxa_novidade_llm`

Relação estrutural por método:

```sql
SELECT *
FROM vw_h2_relacoes_estruturais
WHERE chave_projeto = 'visualee'
ORDER BY assinatura_metodo;
```

Casos `maybe` do WITUP com recuperação pela LLM:

```sql
SELECT *
FROM vw_h1_maybe_recuperacao
WHERE chave_projeto = 'visualee'
ORDER BY assinatura_metodo;
```

**Parte 2: qualidade dos testes derivados**

Comparação por variante:

```sql
SELECT *
FROM vw_estudo_variantes
WHERE chave_projeto = 'visualee'
ORDER BY id_execucao DESC, variante;
```

Colunas principais da Parte 2:
- `taxa_arquivos_teste_por_metodo`
- `taxa_arquivos_teste_por_expath`
- `taxa_sucesso_metricas`
- `nota_metricas`
- `nota_juiz`
- `nota_combinada`

Métricas individuais por variante:

```sql
SELECT *
FROM vw_h3_metricas_variantes
WHERE chave_projeto = 'visualee'
ORDER BY id_execucao DESC, variante, nome_metrica;
```

Comparação direta entre as três suítes:

```sql
SELECT *
FROM vw_h3_comparacao_suites
WHERE chave_projeto = 'visualee'
ORDER BY id_execucao DESC;
```

Essa view já expõe os deltas mais úteis:
- `delta_metricas_llm_vs_witup`
- `delta_metricas_combinado_vs_witup`
- `delta_metricas_combinado_vs_llm`
- `delta_combinada_llm_vs_witup`
- `delta_combinada_combinado_vs_witup`
- `delta_combinada_combinado_vs_llm`

Métricas fortes usadas no piloto `visualee`:
- `unit-tests`
- `jacoco-line`
- `jacoco-branch`
- `pit-mutation`
- `exception-reproduction`

Agregado por variante:

```sql
SELECT *
FROM vw_h3_qualidade_variantes
WHERE chave_projeto = 'visualee'
ORDER BY variante;
```

**Preparação para H4**

Base atual de divergências:

```sql
SELECT *
FROM vw_h4_divergencias_base
WHERE chave_projeto = 'visualee'
ORDER BY assinatura_metodo;
```

Essa view ainda não estratifica interproceduralidade. Ela prepara o terreno
para a futura classificação estrutural dos métodos.

## Scripts disponíveis

A pasta [`scripts`](scripts) foi reduzida ao mínimo operacional:

- [`scripts/executar-visualee-piloto.sh`](scripts/executar-visualee-piloto.sh)
- [`scripts/executar-visualee-rodadas.sh`](scripts/executar-visualee-rodadas.sh)
- [`scripts/limpar-projeto.sh`](scripts/limpar-projeto.sh)

## Interface gráfica do DuckDB

O comando `visualizar-dados` inicia uma interface web simples com:
- lista de tabelas e views;
- visualização rápida de linhas;
- execução de SQL somente de leitura;
- consulta direta dos artefatos indexados pelo projeto.

Além da interface embutida, o arquivo `.duckdb` também pode ser aberto em clientes externos compatíveis, como DBeaver.

Ao final de `executar-estudo-completo`, o projeto também gera gráficos textuais
em `plots/` dentro da raiz da execução. Quando a extensão `textplot` do DuckDB
está disponível, esses arquivos usam as funções de plotagem do DuckDB; caso
contrário, o projeto grava uma versão tabular de fallback sem interromper a
execução.

## Comandos

```text
modelos               Lista os modelos configurados
sondar                Testa conectividade e autenticação
ingerir-witup         Carrega as baselines do artigo para o DuckDB
visualizar-dados      Abre a interface web de consulta do DuckDB
analisar              Analisa métodos com prompt direto
analisar-multiagentes Executa a análise multiagente
comparar-fontes       Compara artefatos canônicos do WITUP e da LLM
consolidar-estudo     Registra o resumo consolidado da Parte 1 e Parte 2
gerar                 Gera testes a partir de uma análise
avaliar               Executa métricas e avaliação opcional por juiz
executar              Executa analisar -> gerar -> avaliar
executar-experimento  Executa WITUP_ONLY, LLM_ONLY e WITUP_PLUS_LLM
executar-estudo-completo Executa Parte 1 + Parte 2 e consolida o estudo
executar-benchmark    Executa cenários de benchmark
```

## Estrutura de saída

Cada execução gera artefatos em disco:
- `sources/`
- `comparisons/`
- `variants/`
- `traces/`
- `generated-tests/`
- `prompts/`
- `responses/`

O DuckDB funciona como camada persistente de consulta e índice, sem substituir os artefatos brutos.

Além disso, cada rodada de experimento também exporta snapshots analíticos em
`.parquet` para [`historico/`](historico), separados por projeto e `run_id`.

## Como reproduzir os testes

Executar toda a suíte:

```bash
make test
```

Executar formatação, vet e testes:

```bash
make quality
```

Executar cobertura:

```bash
make coverage
```

Se o ambiente restringir o cache global do Go:

```bash
mkdir -p .gocache
GOCACHE=$(pwd)/.gocache go test ./...
```

## Cobertura e qualidade

Última medição local validada neste repositório:

| Escopo | Cobertura de linha |
| --- | ---: |
| global (`go test ./... -coverprofile=coverage.out`) | `70.97%` |
| `internal/metricas` | `90.6%` |
| `internal/llm` | `75.0%` |
| `internal/aplicacao` | `63.7%` |
| `internal/armazenamento` | `63.5%` |

Notas importantes:
- os números acima são um retrato da última execução local validada;
- a meta de engenharia/pesquisa continua sendo `>90%` global de cobertura de linha;
- a meta de mutação `>90%` ainda não foi atingida.

Para regenerar os números:

```bash
mkdir -p .gocache
GOCACHE=$(pwd)/.gocache go test ./... -coverprofile=coverage.out
GOCACHE=$(pwd)/.gocache go tool cover -func=coverage.out
```

Para mutação, a rodada mais estável hoje é:

```bash
GOCACHE=$(pwd)/.gocache $(go env GOPATH)/bin/go-mutesting ./internal/metricas --exec-timeout=20
```

## Estrutura do repositório

```text
cmd/witup/principal.go
internal/
  agentes/
  aplicacao/
  armazenamento/
  artefatos/
  catalogo/
  configuracao/
  dominio/
  experimento/
  llm/
  metricas/
  witup/
pipelines/
resources/
schemas/
pipeline.example.json
```

## Contribuição

Contribuições são bem-vindas, principalmente em:
- melhoria da comparação estrutural e semântica de expaths;
- aumento de cobertura e força da suíte de testes;
- robustez da Parte 2 do experimento;
- suporte a mais projetos Java do baseline;
- melhoria da visualização dos resultados no DuckDB.

Enquanto o projeto não tiver um `CONTRIBUTING.md` dedicado, o fluxo recomendado é:
1. abrir uma issue descrevendo o problema ou proposta;
2. alinhar o escopo da mudança;
3. enviar um PR pequeno e focado.

## Suporte

Para relatar bugs, regressões ou discutir ideias de evolução:
- abra uma issue com passos de reprodução e contexto do experimento;
- inclua o `run_id`, o trecho relevante do log e a configuração usada quando o problema envolver uma execução;
- ao compartilhar resultados, prefira apontar também o caminho do artefato consolidado no DuckDB ou na pasta `generated/`.

## Licença

Este projeto é distribuído sob a licença MIT. Veja [`LICENSE`](LICENSE).

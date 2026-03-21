# witup-llm

CLI Python para analise de caminhos de excecao, geracao automatica de testes e avaliacao orientada a metricas + juiz LLM.

## Dominio (DDD)

### Problema de dominio
Escolher e operar pipelines de IA para gerar suites de teste confiaveis para um projeto alvo, com feedback objetivo.

### Linguagem ubiqua
- **Method Descriptor**: metodo catalogado do projeto alvo.
- **Exception Path (expath)**: caminho que pode levar a erro/excecao.
- **Method Analysis**: resultado de analise de um metodo.
- **Generation Report**: conjunto de arquivos de teste gerados.
- **Evaluation Report**: consolidado de metricas + avaliacao do juiz.
- **Benchmark Scenario**: combinacao `modelo_analise -> modelo_geracao`.
- **Benchmark Report**: ranking dos cenarios executados.

### Bounded contexts
- **Catalogacao**: descoberta e extracao de metodos de Java/Python.
- **Orquestracao de Pipeline**: fluxo `analyze -> generate -> evaluate`.
- **Avaliacao e Ranking**: metricas, juiz e ordenacao de cenarios.

## Arquitetura (Clean + Hexagonal)

### Camadas
- **Domain** (`witup_llm/domain`): regras de negocio puras (ex.: cenarios de benchmark).
- **Application** (`witup_llm/pipelines.py`): casos de uso e orquestracao do fluxo.
- **Adapters/Infrastructure**:
  - CLI (`witup_llm/cli.py`)
  - LLM HTTP client (`witup_llm/llm.py`)
  - runner de metricas shell (`witup_llm/metrics.py`)
  - leitura/escrita de artefatos (`witup_llm/artifacts.py`)
  - parser/catalogador de codigo (`witup_llm/project_catalog.py`)

### Principios aplicados
- Baixo acoplamento por interfaces implicitas (injecao de `llm_client` e `metric_runner` no `PipelineService`).
- Alta coesao por modulos especializados.
- Regras de dominio desacopladas de IO (ex.: `build_benchmark_scenarios`).

## Fluxo de uso

1. Catalogar metodos do projeto alvo.
2. Pedir ao modelo de analise os expaths.
3. Gerar testes por container.
4. Medir qualidade com metricas objetivas.
5. (Opcional) passar para juiz LLM.
6. Ranquear cenarios de benchmark.

## Requisitos

- Python 3.11+
- Endpoint(s) de modelo configurados (`ollama` ou `openai_compatible`)
- Arquivo TOML (`witup.toml`)

Use `witup.toml.example` como base.

## Comandos

Listar modelos:

```bash
python3 -m witup_llm models --config witup.toml
```

Analisar:

```bash
python3 -m witup_llm analyze --config witup.toml --model local_qwen
```

Gerar testes:

```bash
python3 -m witup_llm generate \
  --config witup.toml \
  --analysis generated/SEU-RUN/analysis.json \
  --model local_qwen
```

Avaliar:

```bash
python3 -m witup_llm evaluate \
  --config witup.toml \
  --analysis generated/SEU-RUN/analysis.json \
  --generation generated/SEU-RUN/generation.json \
  --judge-model judge
```

Pipeline completo:

```bash
python3 -m witup_llm run \
  --config witup.toml \
  --analysis-model local_qwen \
  --generation-model local_qwen \
  --judge-model judge
```

Benchmark acoplado (mesmo modelo para analise e geracao):

```bash
python3 -m witup_llm benchmark \
  --config witup.toml \
  --model local_qwen \
  --model local_llama \
  --judge-model judge
```

Benchmark em matriz (feature nova):

```bash
python3 -m witup_llm benchmark \
  --config witup.toml \
  --analysis-model local_qwen \
  --analysis-model local_llama \
  --generation-model local_qwen \
  --generation-model local_llama \
  --judge-model judge
```

## Artefatos gerados

Cada run cria pasta em `generated/` com:

- `catalog.json`
- `analysis.json`
- `generation.json`
- `evaluation.json`
- `benchmark.json`
- `benchmark.md`
- `generated-tests/`
- `prompts/`
- `responses/`

## Metricas

Metricas sao declaradas no TOML e executadas via shell com placeholders:

- `{project_root}`
- `{run_dir}`
- `{tests_dir}`
- `{analysis_path}`
- `{generation_path}`
- `{model_key}`

Se `value_regex` existir, o numero extraido e normalizado para score (0-100).

## Qualidade e testes

Executar testes:

```bash
python3 -m unittest discover -s tests -v
```

Cobertura e mutacao podem ser integradas pelas metricas configuradas no TOML do projeto alvo.

## Backlog tecnico

- Melhorar parser Java para casos avancados (generics complexos, lambdas e metodos encadeados).
- Adicionar adapter de persistencia para historico de runs e comparativos longitudinalmente.
- Incluir testes de integracao com endpoint HTTP mockado para validar contratos de LLM.

# Glossario

## Conceitos centrais

| Termo | Definição |
| :--- | :--- |
| **WIT** | What Is Thrown, tecnica de analise estatica que infere pre-condicoes relacionadas a excecoes em Java |
| **ExPath** | Fluxo de execução que leva a uma exceção |
| **WIT_CONTEXT** | Cenário em que a geração recebe o baseline WIT alinhado |
| **DIRECT_TESTS** | Cenário em que a geração recebe apenas o código local |
| **Baseline alinhado** | Baseline WIT já reconciliado com o checkout atual |
| **Métodos-alvo** | Métodos usados igualmente nos dois cenários |
| **Sandbox** | Cópia efêmera do projeto usada para compilar e medir a suíte gerada |
| **jtreg** | Harness de testes usado pelo OpenJDK |
| **JCov** | Ferramenta de cobertura usada no ecossistema OpenJDK |
| **Escopo fixo de classes** | Conjunto de classes usado como denominador comum para comparar cobertura entre variantes |

## Métricas

| Termo | Definição |
| :--- | :--- |
| `test-compilation` | Verifica se a suíte gerada compila |
| `unit-tests` | Quantidade de testes efetivamente executados |
| `test-pass-rate` | Percentual de testes aprovados no Surefire |
| `target-method-coverage` | Percentual de métodos-alvo com pelo menos um teste associado |
| `assertive-tests-rate` | Percentual de métodos de teste com pelo menos uma assertiva |
| `exception-assertion-rate` | Percentual de métodos de teste focados em exceções |
| `jacoco-line` | Cobertura de linhas |
| `jacoco-branch` | Cobertura de branches |
| `pit-mutation` | Mutation score |
| `metric_score` | Média ponderada das métricas ativas |
| `Exception Assertion Rate` | Percentual de metodos-alvo cujos testes gerados verificam excecoes explicitamente |
| `Passing Exception Test Rate` | Percentual dos testes com verificacao de excecao que passaram no jtreg |
| `Approximate Exception Path Coverage` | Aproximacao da proporcao de expaths usados ou adaptados na geracao |

## Artefatos

| Artefato | Uso |
| :--- | :--- |
| `phase-two-study.json` | Relatório completo da execução |
| `phase-two-summary.csv` | Resumo por projeto e cenário |
| `phase-two-metrics.csv` | Detalhe de métricas por cenário |
| `phase-two-comparison.csv` | Delatas entre geração direta e contexto WIT |
| `dashboard.html` | Visualização estática para apresentação |
| `manifest_jdk_global_methods.csv` | Metodos JDK selecionados para a rodada global |
| `results_jdk_jcov_200_fast_summary.csv` | Resumo de cobertura JCov da rodada JDK 200 |
| `results_jdk_exception_coverage_metrics.csv` | Metricas de excecao da rodada JDK 200 |

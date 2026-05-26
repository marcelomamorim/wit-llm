# CLI

Os comandos principais da fase atual sao os fluxos JDK e Batch.

```bash
witup preparar-estudo-jdk-global --config <arquivo.json> --jdk-root <jdk> --wit-analysis <wit_filtered.json> --output-dir <run>
```

## Comandos principais

| Comando | Uso |
| :--- | :--- |
| `preparar-estudo-jdk-global` | Prepara amostra JDK, manifesto e JSONL Batch |
| `avaliar-estudo-jdk-global` | Materializa respostas Batch em variantes JDK |
| `medir-impacto-jdk-global` | Executa `jtreg` nas variantes materializadas |
| `submit-openai-batch` | Submete JSONL para OpenAI Batch API |
| `collect-openai-batch` | Coleta respostas e erros de um Batch |

## Comandos auxiliares ainda úteis

| Comando | Uso |
| :--- | :--- |
| `sondar` | Testa conectividade/autenticação do modelo |
| `extrair-jacoco` | Extrai métrica de um `jacoco.xml` |
| `extrair-pit` | Extrai mutation score de `mutations.xml` |
| `extrair-geracao` | Extrai métricas estáticas de `generation.json` cruzado com `analysis.json` |
| `extrair-surefire` | Soma testes executados a partir dos XMLs do Surefire |
| `medir-reproducao-excecoes` | Mede a reprodução de expaths em uma geração |
| `executar-segunda-fase` | Fluxo legado pareado para projetos Maven |

## Exemplo JDK

```bash
./bin/witup preparar-estudo-jdk-global \
  --config generated/configs/rodada-artigo.runtime.json \
  --generation-model openai_main \
  --jdk-root /Users/marceloamorim/Documents/unb/jdk \
  --wit-analysis resources/wit-replication-package/data/output/jdk/wit_filtered.json \
  --output-dir generated/experiments/jdk-global-impact-study/<run> \
  --requests generated/experiments/jdk-global-impact-study/<run>/requests_openai_batch_generation.jsonl \
  --method-count 200
```

## Saída

Ao final, a CLI imprime:

- caminho do relatório JSON;
- caminho do manifesto;
- caminho do JSONL Batch;
- quantidade de métodos selecionados;
- quantidade de expaths;
- quantidade de requests.

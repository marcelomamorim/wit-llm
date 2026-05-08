# CLI

O comando principal da fase atual é:

```bash
witup executar-segunda-fase --config <arquivo.json> --generation-model <modelo>
```

## Comando principal

| Comando | Uso |
| :--- | :--- |
| `executar-segunda-fase` | Roda a comparação `WIT_CONTEXT` vs `DIRECT_TESTS` |

## Comandos auxiliares ainda úteis

| Comando | Uso |
| :--- | :--- |
| `sondar` | Testa conectividade/autenticação do modelo |
| `extrair-jacoco` | Extrai métrica de um `jacoco.xml` |
| `extrair-pit` | Extrai mutation score de `mutations.xml` |
| `extrair-geracao` | Extrai métricas estáticas de `generation.json` cruzado com `analysis.json` |
| `extrair-surefire` | Soma testes executados a partir dos XMLs do Surefire |
| `medir-reproducao-excecoes` | Mede a reprodução de expaths em uma geração |

## Exemplo

```bash
./bin/witup executar-segunda-fase \
  --config pipelines/fase-dois-guava-commons.json \
  --generation-model openai_main
```

## Saída

Ao final, a CLI imprime:

- caminho do relatório JSON;
- caminho dos CSVs;
- caminho do dashboard HTML;
- quantidade de projetos comparados.

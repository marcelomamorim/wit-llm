# Perfis de pipeline

Esta pasta organiza a configuração por cenário executável usando JSON versionado.

## Perfis incluídos

- `smoke-visualee.json`
- `piloto-commons-io.json`
- `benchmark-matriz.json`

## Como usar

Exemplo de carga do baseline e experimento piloto:

```bash
./bin/witup ingerir-witup --config pipelines/piloto-commons-io.json
./bin/witup executar-experimento --config pipelines/piloto-commons-io.json --model openai_main --project-key commons-io
```

## Ideia central

Cada arquivo representa uma intenção de execução:
- `smoke`: validar o fluxo rapidamente;
- `piloto`: rodar um projeto com mais profundidade;
- `benchmark`: comparar mais de um cenário.

Essa organização mantém a CLI simples e deixa o protocolo experimental mais legível.

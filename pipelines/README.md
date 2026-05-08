# Perfis de pipeline

Esta pasta agora está focada na segunda fase do estudo.

## Perfil principal

- `fase-dois-guava-commons.json`

Esse perfil serve como base para comparar:

- geração com contexto WIT;
- geração direta sem contexto WIT;

nos projetos:

- Google Guava
- Apache Commons Collections

## Como usar

```bash
./bin/witup executar-segunda-fase \
  --config pipelines/fase-dois-guava-commons.json \
  --generation-model openai_main
```

## Observação

Os caminhos de `root` e `wit_analysis_path` precisam ser ajustados para o seu ambiente antes da execução.

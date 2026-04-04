# Alinhamento Baseline WITUP

O subsistema de alinhamento garante que os dados do pacote de replicacao WITUP sejam corretamente mapeados ao codigo-fonte do checkout atual.

## Problema

Os baselines WITUP referenciam metodos por assinatura e numero de linha do artigo original. Como o codigo pode ter sido modificado desde a publicacao, o sistema precisa resolver a correspondencia entre o baseline e o codigo local.

## Processo de Alinhamento

1. **Carga do Baseline**: `CarregarRelatorioBaseline` recupera dados do DuckDB
2. **Resolucao de Metodos**: Para cada metodo no baseline, busca correspondencia no catalogo local
3. **Limite de Distancia**: Metodos com diferenca de linhas maior que 50 sao rejeitados (evita falsos positivos)
4. **Filtragem**: Apenas metodos resolvidos com sucesso sao incluidos na analise

## Invariantes Metodologicas

!!! warning "Invariantes Criticas"
    1. O baseline WITUP **deve** ser carregado no DuckDB antes da execucao principal
    2. A execucao principal **deve** consultar o baseline pelo DuckDB, nao diretamente pela pasta `resources/`
    3. A branch `LLM_ONLY` **deve** analisar apenas os metodos presentes no baseline WITUP resolvido no checkout atual
    4. A comparacao da Parte 1 precisa manter a mesma unidade de analise entre WITUP e LLM

## Funcoes Principais

| Funcao | Arquivo | Descricao |
| :--- | :--- | :--- |
| `alinharWITUPAoCatalogo` | `alvos_witup.go` | Mapeia baseline a metodos locais |
| `ImportarBaselineProjeto` | `internal/witup` | Converte JSON legado em `RelatorioAnalise` |
| `CarregarRelatorioBaseline` | `duckdb.go` | Recupera baseline do banco |

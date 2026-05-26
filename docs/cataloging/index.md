# Catalogacao e Metricas

O sistema usa dois componentes internos principais: o **Catalogador**, que descobre entidades de codigo-fonte, e o **Executor de Metricas**, que executa ferramentas externas para produzir scores quantitativos.

## Interacao dos Componentes

```mermaid
graph TD
    subgraph "Logica e Natural Language"
        A["Servico (internal/aplicacao)"]
        B["RelatorioAvaliacao (internal/dominio)"]
    end

    subgraph "Entidades de Codigo (Java)"
        C["NovoCatalogador (internal/catalogo)"]
        D["DescritorMetodo (internal/dominio)"]
        E["Arquivos Fonte (.java)"]
    end

    subgraph "Metricas e Qualidade"
        F["Executor (internal/metricas)"]
        G["JaCoCo / PIT / Maven"]
        H["ResultadoMetrica (internal/dominio)"]
    end

    A -- "NovoCatalogo()" --> C
    C -- "Escaneia" --> E
    E -- "Extrai" --> D
    D -- "Retorna" --> A

    A -- "ExecutarTodas()" --> F
    F -- "Invoca" --> G
    G -- "Parseia XML/Output" --> H
    H -- "Agrega" --> B
```

## Catalogador Java

O `Catalogador` escaneia o diretorio do projeto-alvo para identificar metodos Java elegiveis para analise e geracao de testes.

### Responsabilidades

- **Descoberta de Metodos**: Escaneia `.java` para identificar classes, metodos, assinaturas e corpo
- **Visao Geral do Projeto**: Fornece resumo da estrutura para contexto LLM via `CarregarVisaoGeral`
- **Filtragem**: Respeita caminhos de inclusao/exclusao definidos em `ConfigProjeto`

## Fluxo de Dados

| Fase | Componente | Entrada | Saida |
| :--- | :--- | :--- | :--- |
| **Descoberta** | `Catalogador` | Caminho no sistema de arquivos | `[]DescritorMetodo` |
| **Geracao** | `Servico` | `DescritorMetodo` + LLM | Arquivos de teste gerados |
| **Execucao** | `Executor` | Arquivos de teste | Relatorios (XML/Stdout) |
| **Extracao** | `Executor` | Relatorios | `[]ResultadoMetrica` |
| **Consolidacao** | `Servico` | `[]ResultadoMetrica` | `RelatorioAvaliacao` |

Detalhes do executor em: [Executor de Metricas](metrics.md)

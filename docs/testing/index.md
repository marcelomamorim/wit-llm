# Testes

O `witup-llm` emprega uma abordagem de testes em multiplas camadas, combinando testes unitarios e de integracao em Go para o motor central com uma suite Python para orquestracao de alto nivel.

## Arquitetura de Testes

```mermaid
graph TD
    subgraph "Espaco de Testes Go"
        G1["servico_test.go"] --> S1["Servico"]
        G2["cliente_test.go"] --> S2["Cliente"]
        G3["executor_test.go"] --> S3["Executor"]
    end

    subgraph "Espaco de Testes Python"
        P1["test_cli.py"] --> C1["witup_llm.cli"]
        P2["test_pipelines.py"] --> C2["witup_llm.pipelines"]
    end

    S1 -- "Usa" --> S2
    C1 -- "Executa" --> "binario witup"
    "binario witup" -- "Implementa" --> S1
```

## Responsabilidades por Camada

| Camada | Ferramenta | Alvo | Foco |
| :--- | :--- | :--- | :--- |
| **Go** | `go test` | `internal/*` | Logica de dominio, protocolos LLM, serializacao, extracao de metricas |
| **Python** | `pytest` | `witup_llm/*` | Orquestracao de pipelines, parsing de argumentos CLI, agregacao de benchmarks |
| **Integracao** | Scripts customizados | Binario `witup` | Execucao ponta-a-ponta de `executar-experimento` |

## Estado Atual

!!! info "Cobertura Conhecida"
    - Global: ~71%
    - `internal/metricas`: ~90.6%
    - `internal/llm`: ~75.0%
    - `internal/aplicacao`: ~63.7%
    - `internal/armazenamento`: ~63.5%

Detalhes da suite Go em: [Suite de Testes Go](go-tests.md)

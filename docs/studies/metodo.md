# Método experimental

## Objetivo e hipótese

O objetivo é investigar se e como o contexto de análise estática WIT afeta testes gerados por modelos de linguagem para comportamentos excepcionais no OpenJDK 11. A hipótese é que hipóteses de caminhos excepcionais possam orientar a seleção de entradas e verificações. Sua validade depende da análise empírica e não é pressuposta.

## Questões de pesquisa

- **QP1 — Contexto:** quais diferenças são observadas entre as condições com e sem WIT em aprovação, erros de compilação e cobertura?
- **QP2 — Modelo:** como essas diferenças variam entre os modelos avaliados, considerando os protocolos efetivamente utilizados?
- **QP3 — Recursos:** quantos tokens e quanto custo de geração são associados às condições e aos modelos, quando esses dados estão disponíveis?

## Objeto e unidade de comparação

O objeto de estudo é o OpenJDK 11. O catálogo do desenho contém 4.417 métodos-alvo, relacionados ao código-fonte e às informações WIT disponíveis. Para comparar condições, a unidade de pareamento é o **mesmo método-alvo**, identificado de forma consistente nos manifestos. O conjunto efetivamente analisado depende de disponibilidade e validade dos artefatos em cada rodada; não deve ser descrito como amostra aleatória.

## Condições experimentais

| Condição | Entrada principal |
|---|---|
| Sem WIT (`direct-tests`) | Código e informações do método, com instruções para derivar testes diretamente do código. |
| Com WIT (`wit-context`) | Código e informações do método, acrescidos de hipóteses WIT sobre exceções, gatilhos e guardas. |

As instruções históricas não são idênticas com um único campo adicional: diferem em redação e conteúdo. Por isso, o contraste refere-se às condições de prompt completas. Os registros originais devem ser preservados para identificar exatamente o que foi enviado.

### Exemplo ilustrativo de entrada

Para `java.util.Objects.requireNonNull`, o código abaixo estava presente no contexto de ambas as condições:

```java
public static <T> T requireNonNull(T obj) {
    if (obj == null)
        throw new NullPointerException();
    return obj;
}
```

No cenário sem WIT, um trecho da instrução solicitava testes diretamente dos métodos, sem expaths pré-computados. No cenário com WIT, a entrada incluía, entre outros campos:

```json
{
  "exception_type": "NullPointerException",
  "guard_conditions": ["null == obj"],
  "trigger": "null == obj"
}
```

São **excertos**, não prompts completos. A implementação de prompts pode evoluir; para reproduzir uma rodada, use as requisições e configurações arquivadas dessa rodada.

## Modelos e configurações

As rodadas históricas GPT-4.1 nano e GPT-4.1 mini usaram Batch, temperatura zero e limite de 1.024 tokens de saída. A rodada Claude Haiku 5.5 usou o mesmo texto histórico de entrada, limite de 4.096 tokens e thinking adaptativo de baixo esforço. Configurações e processamento distintos são ameaças à validade de comparações entre modelos; não se deve atribuir toda diferença observada exclusivamente à identidade do modelo.

## Procedimento

1. Selecionar e identificar os métodos-alvo e alinhar os insumos WIT e OpenJDK.
2. Preparar solicitações das duas condições para cada modelo, preservando prompts, parâmetros e IDs.
3. Gerar e arquivar respostas, estados, motivos de parada e uso de tokens quando disponíveis.
4. Materializar os testes Java e registrar transformações ou normalizações aplicadas.
5. Executar os testes no ambiente especificado, registrando descoberta, compilação, execução, falhas e timeouts.
6. Coletar dados JCov e preservar o vínculo entre testes, execuções e arquivos de cobertura.
7. Definir a coorte e analisar os resultados de forma pareada, explicitando ausências, exclusões e limitações.

A [arquitetura](../architecture/index.md) detalha o fluxo de dados; [métricas e análise](metricas.md) explicita o que cada medida permite concluir.

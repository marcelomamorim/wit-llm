# Métricas e plano de análise

## Métricas principais

| Medida | Definição operacional | Limite de interpretação |
|---|---|---|
| Aprovação no jtreg | Proporção de testes classificados como aprovados no conjunto e denominador declarados. | Passar não garante que o método-alvo foi exercitado nem que o oráculo é correto. |
| Erros de compilação | Casos em que o registro do executor identifica falha de compilação. | Não são, por si só, prova de alucinação. Um teste não descoberto não deve ser contado como compilado. |
| Cobertura de branches | Branches cobertos sobre branches elegíveis no escopo JCov explicitado. | Requer instrumentação, universo e merges compatíveis; cobertura não mede a correção do teste. |
| Locais associados a exceções | Aproximação baseada na entrada em blocos que contêm `ATHROW`, quando esse mapeamento é válido. | Entrar no bloco não comprova execução do lançamento, tipo da exceção ou caminho excepcional completo. |
| Tokens e custo | Uso de entrada e saída informado pelo provedor e custo calculado com a tarifa e modalidade registradas. | Estimativa não substitui fatura; custos de infraestrutura, cache e cobrança mínima exigem tratamento explícito. |

Quando pertinentes e disponíveis, cobertura de blocos e características dos testes, como asserções, podem complementar a análise. Métodos de extração devem ser aplicados de forma consistente aos modelos e validados antes de conclusões.

## Pareamento e denominadores

A comparação principal deve emparelhar as duas condições pelo identificador do método para cada modelo. Para comparar modelos, deve-se definir a interseção de alvos e registrar perdas por condição, modelo e estágio. Informar separadamente testes não descobertos, falhas de infraestrutura, timeouts, respostas ausentes e erros identificados; dados ausentes não são automaticamente zeros ou falhas de compilação.

Cobertura pode ser apresentada por universo estrutural comum ou por unidade explicitamente definida. Não é válido subtrair a contribuição de um teste de um XML agregado sem dados que permitam isolá-la. Antes da união, verificar versão e instrumentação do JDK/JCov, escopo, identidade das classes e proveniência dos testes.

## Inferência e discussão

Relatar diferenças pareadas e incerteza, além de valores absolutos; considerar a dependência entre métodos da mesma classe e registrar o tratamento de exclusões e dados ausentes. Comparações entre modelos são observacionais quando parâmetros de geração ou procedimentos diferem. Não extrapolar resultados para outros projetos, versões ou modelos sem evidência.

!!! note "Resultados em validação"
    As coberturas comparáveis das rodadas atuais dependem da validação dos dados individuais e da consolidação. A página não apresenta números finais antes dessa conferência.

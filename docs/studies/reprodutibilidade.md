# Reprodutibilidade e transparência

## Informações a registrar

Para cada rodada, preserve um manifesto que relacione:

1. Revisão do projeto, versão e revisão do OpenJDK, insumos WIT e respectivos hashes.
2. Critérios de seleção, identificadores dos métodos e motivos de exclusão.
3. Prompts completos, modelos e snapshots, parâmetros, modalidade da API, respostas brutas e motivos de parada.
4. Uso de tokens e critérios de estimativa de custos, incluindo data e tarifa.
5. Transformações aplicadas aos testes, ferramentas, versões, ambiente, limites de tempo e paralelismo.
6. Logs e estados de descoberta, compilação e execução, preservando resultados ausentes e falhas de infraestrutura.
7. Arquivos JCov, escopo instrumentado, vínculo com testes, regras de merge e denominadores.
8. Scripts de análise, coorte efetiva, dependências e instruções para reconstruir tabelas e gráficos.

## Separação entre código e dados

Este repositório contém código, documentação e artefatos selecionados. Os resultados brutos e ambientes volumosos podem estar em armazenamento separado, com acesso e licenças próprios. A existência de um arquivo agregado não garante a disponibilidade da cobertura individual necessária para uma nova coorte. Ao compartilhar dados, forneça um manifesto, hashes e instruções de obtenção, após verificar privacidade, termos dos provedores e licenças de terceiros.

Credenciais devem ser fornecidas por variáveis de ambiente ou arquivo local ignorado; não inclua chaves, tokens ou dados pessoais nos artefatos públicos. Consulte o [guia de configuração](../overview/configuration.md) antes de executar chamadas aos provedores.

## Validação antes de interpretar

Verifique que cada teste foi descoberto pelo executor e que os resultados de execução e de cobertura correspondem à mesma rodada e condição. Compare os testes processados com os manifestos, investigue discrepâncias e registre eventuais exclusões. Reexecute apenas quando necessário e preserve a distinção entre resultados originais e reexecuções.

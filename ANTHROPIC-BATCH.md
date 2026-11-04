# Rodada Anthropic pareada com o histórico

Modelo escolhido pelo usuário: `claude-sonnet-4-6`. Protocolo: 4.417 alvos,
com/sem WIT, uma geração por condição; 8.834 solicitações. Temperatura 0,
`max_tokens=1024`, `thinking.type=disabled`, versão de API `2023-06-01`.

Os textos de sistema e usuário são reutilizados de
`sast2026-artifact/prompts/requests_{direct-tests,wit-context}.jsonl`, sem instrução
nova de oráculo, exemplo adicional, alteração de código ou reparo. O formato de
transporte muda de Responses para Messages. Os IDs nativos curtos são mapeados de
volta aos IDs históricos. A chave é carregada localmente do `.env` e nunca salva
nos artefatos da rodada.

O limite de saída numérico é igual ao histórico; tokenizadores diferentes não
representam o mesmo número de caracteres. A mesma temperatura numérica também
não garante a mesma distribuição entre famílias de modelo. Ausência de réplicas
independentes limita inferência; 4.417 alvos não garantem validade por si só.

## Operação

A partir da raiz do repositório:

```bash
python3 scripts/anthropic-study-batch.py prepare \
  --run generated/experiments/anthropic/sonnet-4-6-historical-20261101

# Somente contagem de tokens, sem geração; entrada estimada por 16 amostras.
python3 scripts/anthropic-study-batch.py estimate \
  --run generated/experiments/anthropic/sonnet-4-6-historical-20261101

# Este comando envia gerações PAGAS em Batch. O primeiro lote tem 20 solicitações.
python3 scripts/anthropic-study-batch.py submit \
  --run generated/experiments/anthropic/sonnet-4-6-historical-20261101 --chunks 1

# Depois de conferir o lote inicial, os demais lotes podem ser enviados uma vez.
python3 scripts/anthropic-study-batch.py submit \
  --run generated/experiments/anthropic/sonnet-4-6-historical-20261101 --chunks {2..19}

python3 scripts/anthropic-study-batch.py status \
  --run generated/experiments/anthropic/sonnet-4-6-historical-20261101

python3 scripts/anthropic-study-batch.py collect \
  --run generated/experiments/anthropic/sonnet-4-6-historical-20261101
```

`prepare` valida fontes, igualdade dos alvos e parâmetros históricos e é idempotente.
O primeiro lote contém 10 pares. Todos os outros mantêm os pares completos e
contêm até 500 solicitações. Foram preparados 19 lotes, cada um abaixo de 10 MB.

`submit` exige números explícitos dos lotes. Persiste um checkpoint antes do POST
para impedir duplicação se o transporte falhar. Um ID remoto existente é reutilizado,
sem reenvio. Falha HTTP ou de transporte sem ID bloqueia novas tentativas: confira
os lotes no Console e reconcilie o checkpoint antes de qualquer alteração manual.
Não há retry automático de POST, geração truncada, recusa ou esquema inválido.

`status` e `collect` consultam apenas os IDs desta rodada. Resultados podem chegar
fora de ordem: IDs desconhecidos, duplicados ou ausentes interrompem a coleta.
O resultado bruto é preservado em `raw/`; o JSONL canônico em `responses.jsonl`
é compatível com o exportador existente. Respostas vazias `files=[]` ficam
contabilizadas separadamente; falha de geração não é convertida em teste.

O comando de exportação de Java, sem compilar nem modificar o conteúdo, é:

```bash
python3 scripts/export-model-tests.py \
  --run-root generated/experiments/anthropic --models sonnet-4-6-historical-20261101
```

A compilação, a execução e as três métricas do estudo são etapas posteriores.
Nenhum resultado do piloto antigo foi misturado a esta rodada.

## Evidências

`plan.json` registra fontes e hashes, parâmetros e identificação dos lotes;
`batches/` contém os corpos exatos enviados e os mapas de IDs;
`jobs/` guarda os IDs, horários e estados remotos; `raw/` preserva resultados
nativos; `normalized/` contém respostas por lote; `batch-summary.json` resume o
acompanhamento. O diretório `.batch.lock` impede operação simultânea.

O custo estimado usa os preços oficiais de Batch consultados em 01/11/2026:
US$ 1,50 por milhão de tokens de entrada e US$ 7,50 por milhão de saída para
Sonnet 4.6. A estimativa por amostra não constitui teto financeiro; o limite de
saída limita os tokens, não o custo de entrada, impostos ou câmbio.

Referências: https://platform.claude.com/docs/en/models/sonnet-4-6/overview e
https://platform.claude.com/docs/en/build-with-claude/batch-processing.

## Resultado do lote inicial e piloto separado

O lote inicial de 20 solicitações com 1.024 tokens terminou: a API aceitou todas,
mas todas atingiram `max_tokens` antes de completar o JSON. Foram registrados
100.383 tokens de entrada e 20.480 de saída; custo de Batch estimado de US$ 0,3042.
Os outros 8.814 pedidos dessa condição não foram enviados.

O usuário escolheu um piloto separado com os mesmos dez pares e `max_tokens=4096`.
Somente o limite de saída foi alterado. O comando de preparação desse piloto é:

```bash
python3 scripts/anthropic-study-batch.py prepare \
  --run generated/experiments/anthropic/sonnet-4-6-pilot-4096-20261101 \
  --max-output-tokens 4096 --method-limit 10
```

O piloto de 4.096 não deve ser misturado à condição histórica de 1.024 nem contado
como réplica da mesma configuração. O lote maior dependerá de uma decisão após
avaliar esse piloto. Não houve alteração dos prompts, structured output adicional,
reparo automático ou repetição do mesmo job remoto.

## Rodada ativa Haiku 5.5 com teto de US$ 120

O usuário escolheu `claude-haiku-5-5` e manteve a amostra inteira: 4.417 alvos,
8.834 solicitações. Os prompts históricos não foram alterados. A configuração
registrada é `max_tokens=4096`, thinking adaptativo e esforço `low`; temperatura
é omitida porque o modelo não aceita esse parâmetro. Essa diferença em relação
ao histórico deve ser declarada no artigo.

Diretório ativo:
`generated/experiments/anthropic/haiku-5-5-historical-4096-20261101`.

A política em `generated/experiments/anthropic/budget-policy.json` vincula cada
POST ao plano e payload aprovados, reserva o custo antes da submissão e bloqueia
os planos antigos de Sonnet. Não há reenvio automático de falhas. O limite
operacional é US$ 100 de API, com margem de US$ 20 para o teto solicitado.
A reserva preventiva total é US$ 91,455751, incluindo US$ 0,844547 dos dois pilotos
Sonnet. O cálculo de segurança considera a faixa mais cara do Haiku em todas as
requisições, um token de entrada por byte UTF-8 mais 4.096 tokens de overhead e
saída de 4.096 tokens por pedido. É uma reserva conservadora; não é a cobrança.
A contagem gratuita por amostra projetou US$ 12,03 para o Haiku com todas as
saídas no limite, antes dos pilotos anteriores e impostos.

O lote inicial do Haiku teve 20 respostas: 18 válidas (uma `files=[]`) e duas
truncadas. Todos os resultados foram preservados. O usuário autorizou prosseguir
com os lotes restantes, sem reparar ou regenerar as falhas.

Após a submissão, a coleta e exportação automática podem ser acompanhadas com:

```bash
python3 scripts/anthropic-study-batch.py watch \
  --run generated/experiments/anthropic/haiku-5-5-historical-4096-20261101
```

`watch` somente consulta e coleta; não envia novas gerações. Ao terminar, exporta
os Java originais e grava `completion.json`. A compilação e medição de cobertura
continuam sendo etapas separadas. O processamento remoto Batch continua mesmo
se o computador ficar indisponível; a coleta local requer o computador ligado.

## Piloto adicional de custo Sonnet 4.6 — 2 de novembro de 2026

O usuário autorizou somente um novo piloto pequeno para conferir a projeção.
Diretório: `generated/experiments/anthropic/sonnet-4-6-cost-pilot-20261102`.
Foram selecionados 20 alvos (40 solicitações pareadas) em 20 estratos por tamanho
dos prompts, com semente 20261102, excluindo os alvos do piloto anterior.
Os textos históricos foram preservados, temperatura 0, thinking desativado e
limite de saída 4096. O envio foi Batch, sem reparos ou reenvios automáticos.

A política ganhou uma autorização adicional restrita ao fingerprint, caminho,
hash do payload e 40 solicitações desse piloto. A reserva conservadora foi
US$ 2,469462 e o envio da rodada Sonnet completa continua bloqueado.

O consumo observado foi 204.856 tokens de entrada e 100.959 de saída, totalizando
US$ 1,0644765 estimados (não é fatura). Houve 34 respostas válidas com 34 arquivos
Java (17 por braço), quatro truncadas e duas sem o esquema `files[]` esperado.
Todas as falhas entram no custo. Processamento remoto: 143,68 segundos.

A projeção ponderada pelas populações dos estratos foi US$ 235,10 para 8.834
solicitações, contra US$ 238,68 do primeiro piloto. A saída no limite em todas
as solicitações levaria a US$ 339,25, ainda com entrada estimada. São projeções
exploratórias, sem garantia de teto financeiro. Acumulado estimado após o piloto:
US$ 10,67. Os Java exportados ainda não foram compilados ou executados.

Reproduzir a análise offline:

```bash
python3 scripts/analyze-sonnet-cost-pilot.py \
  generated/experiments/anthropic/sonnet-4-6-cost-pilot-20261102
```

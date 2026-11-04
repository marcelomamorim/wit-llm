# Geração com quatro modelos e APIs diretas

Configuração: `pipelines/quatro-modelos-diretos.json`. Não contém segredos.

| Chave | Provedor | Modelo | Perfil de geração |
|---|---|---|---|
| `gpt_reasoning` | OpenAI Responses | `gpt-6.1-sol` | raciocínio com esforço `low`; sem `temperature` |
| `gpt_standard` | OpenAI Responses | `gpt-4.1-2025-04-14` | sem parâmetro de raciocínio; temperatura 0 |
| `gemini` | Google generateContent | `gemini-3.8-flash` | thinking level `LOW`; temperatura 1 |
| `sonnet` | Anthropic Messages | `claude-sonnet-5-5` | thinking adaptativo, esforço `low`; sem `temperature` |

A distinção entre os GPTs é de perfil/capacidade de geração; não é uma afirmação
sobre suas arquiteturas internas. Acesso aos modelos e quotas dependem das contas.
Os nomes de modelo podem ser alterados no JSON. Não há DeepSeek neste perfil.

## 1. Preparar e validar sem chaves ou consumo

Execute os comandos a partir da raiz do repositório:

```bash
bash scripts/run-four-models.sh prepare
bash scripts/run-four-models.sh dry-run
```

O script compila a CLI e prepara 20 métodos por modelo, com dois braços
(`wit-context` e `direct-tests`). Usa o checkout local `jdk-src` e o WIT em
`sast2026-artifact/wit-expaths/wit_filtered.json`; não baixa nem troca o checkout.
Os quatro manifestos devem ser idênticos, ou a preparação falha.
A seleção atual é determinística, limitada pelo catálogo; não é amostragem aleatória.

O prompt é comum: `WITUP_PROMPT_ORACLE=1` e `WITUP_PROMPT_GOLDEN=0` (V1).
Use os mesmos valores nos quatro modelos e registre alterações como nova rodada.

Variáveis opcionais: `RUNTIME_CONFIG`, `JDK_ROOT`, `JDK_WIT_ANALYSIS`, `METHOD_COUNT`,
`MODEL_KEYS` (lista separada por espaços), `RUN_ROOT`. O diretório padrão é
`generated/experiments/four-models/pilot-01/<chave>/`.

Para uma rodada independente, use um novo `RUN_ROOT` em **todos** os estágios:

```bash
RUN_ROOT="$PWD/generated/experiments/four-models/replica-02" \
  bash scripts/run-four-models.sh prepare
```

Preparações existentes são preservadas. Para alterar prompts, amostra ou configuração,
prepare uma rodada em outro diretório. Não reutilize `pilot-01` para experimentos diferentes.

## 2. Configurar três chaves localmente

Os dois GPTs compartilham `OPENAI_API_KEY`. Gemini usa `GEMINI_API_KEY` e Claude
usa `ANTHROPIC_API_KEY`. Configure essas variáveis no terminal local. A CLI não
carrega `.env` automaticamente. Não inclua os valores no JSON nem no Git.

Opcionalmente, confira autenticação e acesso à listagem de modelos:

```bash
./bin/witup sondar --config pipelines/quatro-modelos-diretos.json --model gemini
```

A sonda não gera testes e não comprova que a conta pode executar o modelo escolhido;
a chamada piloto abaixo é a verificação efetiva.

## 3. Gerar: chamadas pagas somente neste estágio

Comece com no máximo duas requisições por modelo (oito no total, salvo retries):

```bash
REQUEST_LIMIT=2 bash scripts/run-four-models.sh generate
```

Depois, para continuar os mesmos lotes:

```bash
bash scripts/run-four-models.sh generate
```

O executor é sequencial por modelo; usa o maior intervalo entre `--interval` e
`request_interval_ms` do JSON, timeout, retries de transporte/HTTP transitório e
`Retry-After`. Erros 401/402/403/404 e 429 após retries interrompem o lote,
preservando o erro para corrigir credenciais, modelo ou quota antes de retomar. Aumente o intervalo conforme as quotas reais. O limite conta
requisições lógicas, e cada uma pode realizar até `max_retries + 1` tentativas HTTP.
Não há controle de TPM nem teto monetário; configure também limites no fornecedor.

Chamadas bem-sucedidas são gravadas e sincronizadas em disco imediatamente, e são
reutilizadas na retomada. Respostas vazias, truncadas, bloqueadas ou sem objeto JSON
válido são erros. Para esses casos, a repetição do comando tenta uma nova geração;
essa política pode afetar o estudo, então preserve e contabilize todas as tentativas.
Não há reparo automático do Java gerado.

Cada linha de `responses.jsonl` contém o `custom_id`, provedor, configuração,
horário, duração, resposta original e envelope normalizado aceito pelo avaliador.
Tokens de raciocínio do Gemini entram no total de saída; Claude contabiliza entrada
normal, leitura e criação de cache no total de entrada. Os detalhes originais ficam
preservados. Preços não cadastrados permanecem sem estimativa, não como custo zero.

A retomada exige os mesmos bytes de entrada e configuração (hash SHA-256).
Uma mudança no conteúdo ou configuração requer outro arquivo/diretório de saída.
`Ctrl+C` cancela requisições e esperas. Um término forçado pode deixar o diretório
`responses.jsonl.lock`; remova-o somente após conferir que nenhum executor está ativo.
Se a última linha ficar incompleta após falha de disco/processo, o executor recusa
retomar: preserve uma cópia e recupere o registro antes de prosseguir.

## Exportar somente os testes Java

Para obter os arquivos sem copiar o checkout inteiro do JDK:

```bash
python3 scripts/export-model-tests.py \
  --run-root generated/experiments/four-models/pilot-01
```

Cada resposta fica em `generated-tests/<braço>/<pair_id>/`, preservando o conteúdo
original e isolando classes com nomes iguais. `exported-tests.csv` mapeia os arquivos,
seus hashes e as requisições; `generation-summary.json` separa respostas válidas,
gerações vazias e erros. Essa exportação não compila nem executa os Java.

## 4. Materializar os arquivos Java

Somente após concluir a geração (o script verifica que todas as respostas são válidas):

```bash
bash scripts/run-four-models.sh materialize
```

O avaliador existente lê as respostas normalizadas e cria as variantes. A avaliação
com jtreg/JCov continua pelos scripts existentes, apontados para o diretório da rodada.
Este script não inicia AWS CodeBuild nem modifica as rodadas anteriores.

## Comando individual

```bash
./bin/witup executar-lote-modelo \
  --config pipelines/quatro-modelos-diretos.json \
  --model sonnet \
  --requests generated/experiments/four-models/pilot-01/sonnet/requests.jsonl \
  --output generated/experiments/four-models/pilot-01/sonnet/responses.jsonl \
  --interval 2s --limit 2
```

Acrescente `--dry-run` para validar sem chamadas. O nome `requests.jsonl` descreve
um formato intermediário canônico: o executor traduz os prompts para a API nativa.
Não envie esse arquivo diretamente ao Batch de Gemini ou Claude.

## Lotes locais e Batch nativo

`executar-lote-modelo` é uma fila local de chamadas individuais, com checkpoint.
**Não é o Batch assíncrono com desconto dos fornecedores.** O Batch nativo OpenAI
existente continua disponível pelos comandos `submeter-openai-batch` e
`coletar-openai-batch`. A submissão por esses comandos rejeita Gemini e Claude antes
de enviar arquivos. Batch assíncrono nativo Google/Anthropic não está implementado.

A preparação é offline; o piloto real só deve ser feito depois de configurar chaves.
Os testes automatizados usam HTTP simulado e não atestam acesso ou quotas das contas.

## Comparabilidade experimental

Os mesmos métodos e prompts são usados em todos os modelos, alterando apenas a
inclusão do contexto WIT dentro de cada par. Os parâmetros nativos são diferentes:
`low` não representa orçamento equivalente entre fornecedores. O limite de 8192
não assegura o mesmo número de tokens de código, pois pode incluir raciocínio.
Registre versões, configuração, truncamentos, recusas e tokens por tentativa.
As repetições devem usar novos diretórios; não contam como réplicas a simples
retomada dos sucessos anteriores.

## Referências de protocolo

- OpenAI: https://developers.openai.com/api/docs/guides/latest-model
- GPT-4.1: https://developers.openai.com/api/docs/models/gpt-4.1
- Gemini: https://ai.google.dev/api/generate-content
- Claude: https://platform.claude.com/docs/en/api/messages/create

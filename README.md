# WIT + LLM: geração de testes para comportamentos excepcionais

Ferramenta de pesquisa em Go para estudar como informações de análise estática afetam a geração de testes Java por modelos de linguagem. A CLI prepara prompts e experimentos, integra provedores de modelos e organiza a avaliação dos testes gerados. O experimento principal usa métodos do **OpenJDK 11**, o executor **jtreg** e a ferramenta de cobertura **JCov**.

> Projeto de pesquisa em andamento. Saídas de modelos podem conter erros de compilação, testes que passam sem verificar o comportamento pretendido e acesso inválido a APIs. Os resultados dependem da versão do código, dos prompts, dos modelos e do ambiente de execução.

## Pergunta do estudo

Métodos Java podem lançar exceções quando uma entrada ou condição de estado viola uma pré-condição. Esses comportamentos são relevantes para testes de regressão, mas suas condições nem sempre são evidentes para quem escreve o teste.

O estudo investiga se fornecer ao modelo informações do **WIT** — uma análise estática que identifica hipóteses de caminhos excepcionais, aqui chamadas *expaths* — contribui para gerar testes mais úteis. A hipótese é que esse contexto ajude a exercitar comportamentos excepcionais. A hipótese é investigada empiricamente; o projeto não pressupõe que o WIT sempre melhore os resultados.

Para cada método-alvo, comparamos:

| Condição | Informações fornecidas |
|---|---|
| **Sem WIT (`direct-tests`)** | Código e informações do alvo, com instruções para derivar testes diretamente do código. |
| **Com WIT (`wit-context`)** | Código e informações do alvo, mais hipóteses WIT sobre exceções, gatilhos, condições de guarda e evidências. |

As instruções completas dos dois braços diferem em redação e conteúdo; não se deve descrevê-las como prompts idênticos com apenas um campo acrescentado. Dentro de cada estudo, são preservados os prompts efetivamente enviados para permitir a auditoria.

## Desenho experimental

1. Selecionar métodos-alvo e relacioná-los às informações WIT e ao código-fonte da versão estudada do OpenJDK.
2. Preparar uma solicitação por método e cenário, preservando identificadores, prompts e parâmetros.
3. Gerar respostas para cada modelo e guardar respostas brutas, estado da geração e uso de tokens.
4. Materializar os arquivos Java em diretórios isolados e preservar sua proveniência.
5. Avaliar os testes com jtreg e coletar cobertura usando JCov, mantendo logs, falhas e resultados por execução.
6. Comparar cenários **pareados pelo mesmo método**, documentando ausências, diferenças de configuração e incerteza.

Os testes históricos incluem **GPT-4.1 nano** e **GPT-4.1 mini** (`2025-04-14`), em Batch, com temperatura zero e limite de 1.024 tokens de saída. A rodada **Claude Haiku 5.5** usou o mesmo texto histórico de entrada, mas limite de 4.096 tokens e thinking adaptativo de baixo esforço. Os protocolos históricos de materialização e execução também diferem. Portanto, uma comparação entre esses resultados não isola exclusivamente o efeito do modelo.

O catálogo utilizado nesse desenho contém 4.417 métodos-alvo. As análises de coortes comuns excluem gerações sem arquivos ou registros utilizáveis; não são amostras aleatórias. A reavaliação e a consolidação da cobertura estão em andamento. Não interpretamos métricas preliminares como resultados finais.

### Métricas e limites

- **Aprovação no jtreg:** o executor reporta que o teste passou; isso não prova que o método-alvo foi exercitado ou que o oráculo é correto.
- **Erros de compilação registrados:** testes identificados com falha de compilação. Ausência desse registro não prova que o teste compilou; erros do executor, testes não descobertos e timeouts precisam ser discriminados.
- **Cobertura de branches:** ramos cobertos em um universo estrutural e baseline explicitados; é necessário validar a compatibilidade dos XMLs JCov antes de uni-los.
- **Locais associados a exceções:** uma análise existente usa a entrada em blocos que contêm `ATHROW` como aproximação. Isso não comprova execução da instrução, tipo da exceção ou cobertura de um caminho excepcional completo.
- **Tokens e custo de geração:** entrada e saída reportadas pelos provedores e estimativa por tarifa, com cenário, data, cache e método de cobrança informados. O custo estimado não substitui a fatura e não inclui automaticamente infraestrutura.

Compilação malsucedida não é, por si só, prova de alucinação: o diagnóstico precisa identificar sua causa. Resultados ausentes, falhas de infraestrutura e exclusões devem permanecer visíveis.

## Exemplo de prompts usados

Os trechos a seguir são **excertos**, não o prompt completo, da solicitação histórica para `java.util.Objects:requireNonNull:219`. Ambos os cenários receberam o código do método; o cenário WIT também recebeu o caminho excepcional abaixo. Os textos completos incluem instruções de sistema, restrições de versão, contexto e formato JSON de resposta.

**Sem WIT — trecho da instrução:**

```text
Gere arquivos de teste Java determinísticos diretamente a partir dos métodos abaixo, sem usar expaths pré-computados.
```

O contexto continha o método:

```java
public static <T> T requireNonNull(T obj) {
    if (obj == null)
        throw new NullPointerException();
    return obj;
}
```

**Com WIT — trecho de instrução e dados de caminho:**

```text
Gere arquivos de teste Java determinísticos para os métodos abaixo.
```

```json
{
  "exception_type": "NullPointerException",
  "guard_conditions": ["null == obj"],
  "path_id": "java.util.Objects.requireNonNull(T)#208#1",
  "trigger": "null == obj"
}
```

O JSON acima é um excerto do objeto `expaths`: outros campos, inclusive `confidence` e `evidence`, foram omitidos aqui para facilitar a leitura. O alvo também era acompanhado do código-fonte; os dados WIT eram tratados como hipóteses a confrontar com esse código.

A implementação das instruções fica em [`internal/aplicacao/prompts.go`](internal/aplicacao/prompts.go). Para uma reprodução fiel, utilize os arquivos de requisições da rodada correspondente e seus hashes, pois o código pode evoluir depois da geração.

## Exemplos de código gerado

Os trechos seguintes foram extraídos das respostas **com WIT** dos três modelos para o mesmo alvo `java.util.Objects:requireNonNull:219`. São fragmentos ilustrativos, não os testes completos nem resultados de aprovação. A seleção não representa a qualidade média dos modelos.

<details>
<summary>GPT-4.1 nano — saída original</summary>

```java
try {
    Objects.requireNonNull(null);
    System.err.println("Failed: Expected NullPointerException not thrown");
} catch (NullPointerException e) {
    System.out.println("Passed: NullPointerException thrown as expected");
}
```

A mensagem de erro, por si só, não faz o processo falhar. Esse exemplo mostra por que uma execução aprovada não garante um oráculo efetivo.

</details>

<details>
<summary>GPT-4.1 mini — saída original</summary>

```java
try {
    java.util.Objects.requireNonNull(null);
    throw new AssertionError("requireNonNull(null) should throw NullPointerException");
} catch (NullPointerException expected) {
    // Expected exception
}
```

O arquivo completo dessa resposta também contém uma diretiva `@run` que precisa ser avaliada em conjunto com o nome e o pacote da classe; o fragmento isolado não demonstra que o arquivo inteiro executa.

</details>

<details>
<summary>Claude Haiku 5.5 — saída original</summary>

```java
boolean thrown = false;
try {
    Objects.requireNonNull(null);
} catch (NullPointerException e) {
    thrown = true;
}
if (!thrown) {
    throw new RuntimeException("requireNonNull(null) did not throw NullPointerException");
}
```

</details>

Os testes completos e respostas brutas podem conter conteúdo gerado incorreto ou dados locais; devem ser inspecionados antes de publicação ou execução.

## Começando

### Requisitos

- Go **1.24 ou superior**.
- Para avaliação Java: checkout do projeto-alvo e ambiente de testes correspondente; para o estudo do OpenJDK, JDK 11, jtreg e JCov compatíveis.
- Para geração: credencial e acesso ao modelo do provedor escolhido. **A geração por API pode gerar custos.**
- Python 3 e Docker são necessários apenas para scripts e avaliações que os utilizam.

### Compilar e executar os testes da CLI

```bash
git clone https://github.com/marcelomamorim/wit-llm.git
cd wit-llm
make build
./bin/witup ajuda
make test
```

A configuração de exemplo [`pipeline.example.json`](pipeline.example.json) contém caminhos fictícios. Copie-a para um local ignorado pelo Git e ajuste as seções `project`, `pipeline`, `models` e, se usada, `phase_two`. Variáveis e caminhos relativos podem depender da localização do arquivo de configuração. Consulte [`docs/overview/configuration.md`](docs/overview/configuration.md) e [`pipelines/README.md`](pipelines/README.md).

As chaves devem ser lidas de variáveis de ambiente, não colocadas no JSON ou em código. Conforme o provedor, use `OPENAI_API_KEY`, `ANTHROPIC_API_KEY` ou `GEMINI_API_KEY`. A CLI **não carrega `.env` automaticamente**. Consulte [`.env.example`](.env.example) para nomes de variáveis; esse arquivo não deve receber valores reais.

### Preparar uma rodada OpenJDK

Com a CLI compilada, um checkout OpenJDK compatível e um arquivo de análise WIT disponível localmente:

```bash
./bin/witup preparar-estudo-jdk-global \
  --config /caminho/para/config.json \
  --generation-model openai_main \
  --jdk-root /caminho/para/openjdk \
  --wit-analysis /caminho/para/wit_filtered.json \
  --output-dir ./generated/minha-rodada \
  --requests ./generated/minha-rodada/requests.jsonl \
  --method-count 20
```

A preparação produz solicitações; **não é uma submissão à API**. Confira os prompts, alvos, modelo e parâmetros antes de enviar qualquer lote. Para inspecionar as opções da CLI, execute `./bin/witup ajuda`; o comando de preparação também mostra seus flags com `--help`.

Para o fluxo com diferentes provedores e validação offline, consulte [`MULTI-MODEL.md`](MULTI-MODEL.md). Para um perfil de configuração e fluxo de segunda fase, veja [`pipelines/README.md`](pipelines/README.md). Os scripts de experimentos são especializados: revise seus caminhos, versão da imagem, infraestrutura e limites antes de usá-los.

## Organização do projeto

```text
cmd/witup/             Entrada da CLI
internal/              Aplicação, domínio, provedores e métricas
pipelines/             Perfis de configuração
scripts/               Preparação, avaliação e análise de experimentos
docker/                Ambientes de avaliação
schemas/               Esquemas de artefatos
resources/README.md    Orientações sobre recursos WIT externos
```

Dados de pesquisa, cópias do OpenJDK, credenciais, resultados gerados e configurações locais não são necessários para compilar a CLI. Parte dos artefatos históricos não está distribuída no repositório; seu acesso e suas licenças devem ser verificados antes da reprodução ou publicação.

## Reprodutibilidade e segurança

Registre versão do projeto e do OpenJDK, hash dos dados WIT, prompts exatos, modelo e snapshot, parâmetros, respostas e motivos de parada, transformação dos arquivos, versão do jtreg/JCov, flags, limites de tempo e denominadores. Para cobertura, mantenha os XMLs brutos e registre os testes que contribuíram para cada união. Não publique credenciais, dados pessoais ou caminhos/identificadores privados em logs e artefatos.

O repositório ignora `.env` e diretórios locais de geração. **`.gitignore` não remove dados já rastreados nem apaga o histórico Git.** Antes de publicar, revise o índice e o histórico com uma ferramenta de detecção de segredos e confira os arquivos binários e arquivos compactados separadamente. Se uma credencial real for exposta, revogue-a ou rotacione-a no provedor e trate também o histórico remoto.

## Desenvolvimento

```bash
make check-fmt
make vet
make test
```

Contribuições são bem-vindas por issues e pull requests; consulte [CONTRIBUTING.md](CONTRIBUTING.md). Para comunicar problemas de segurança, siga [SECURITY.md](SECURITY.md).

## Licença e fontes de terceiros

O código deste repositório está sob a [licença MIT](LICENSE). A licença não substitui as licenças dos materiais de terceiros utilizados no estudo, incluindo OpenJDK e pacotes de replicação WIT; consulte os termos originais antes de redistribuí-los. Saídas de provedores devem ser compartilhadas de acordo com seus respectivos termos e com a política dos dados utilizados.

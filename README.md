# witup-llm

Projeto Java que usa `witup-core` como dependencia do `witup-llm` e chama um modelo para gerar:
- testes unitarios JUnit 5

## Como o witup-core entra como dependencia

O `pom.xml` importa:

- `br.unb.cic.witup:witup-core:0.0.0-local`

E o repositorio Maven local do projeto esta em:

- `vendor/m2`

Para publicar o JAR do `witup-core` nesse repositorio local:

```bash
./scripts/publish-witup-core-local.sh /Users/marceloamorim/Documents/unb/witup-llm/vendor/witup-core/target/witup-core-0.0.0-SNAPSHOT.jar
```

Se nao passar argumento, o script usa esse caminho padrao automaticamente.

Para sincronizar do GitHub, compilar e publicar no `vendor/m2` automaticamente:

```bash
./scripts/sync-witup-core-from-github.sh
```

Exemplo fixando branch/commit:

```bash
./scripts/sync-witup-core-from-github.sh --ref main
./scripts/sync-witup-core-from-github.sh --ref 01a8ec3b6984afd6cd5258daed5b1e4cde793a55
```

## Requisitos

- Java 21+
- Maven 3.9+
- Ollama rodando localmente em `http://localhost:11434`
- Um modelo instalado no Ollama (exemplo: `qwen2.5-coder:7b`)
- Classes Java alvo compiladas com bytecode compativel com SootUp (recomendado `--release 17` ou `--release 21`)
- Opcional no macOS para limite rigido de CPU: `brew install cpulimit`

Comandos uteis do Ollama:

```bash
ollama serve
ollama pull qwen2.5-coder:7b
```

## Fluxo

1. O CLI roda o WITUp para extrair caminhos ate `throw` e condicoes simbolicas.
2. Salva esse contexto em `generated/witup-analysis.json`.
3. Monta um prompt com o contexto de analise.
4. Chama `/api/generate` no Ollama local.
5. Salva o Markdown final com os testes unitarios gerados.

## Limitar CPU no macOS

Voce tem 2 niveis de limite:

1. Limite por requisicao no Ollama (threads):

```bash
mvn -q -DskipTests exec:java -Dexec.args="--class-path /caminho/target/classes --class-name com.exemplo.MinhaClasse --num-thread 2"
```

2. Limite rigido do processo Ollama no macOS:

```bash
./scripts/macos-limit-ollama.sh 60
```

Isso limita o processo `ollama` a `60%` de CPU.  
Para remover o limite:

```bash
pkill -f "cpulimit -p"
```

## Como rodar

Exemplo:

```bash
mvn -q -DskipTests exec:java -Dexec.args="--class-path /caminho/target/classes --class-name com.exemplo.MinhaClasse --model qwen2.5-coder:7b --overview-file /caminho/contexto-witup.txt"
```

Saidas padrao:

- `generated/witup-analysis.json`
- `generated/witup-unit-tests.md`

## Classes de exemplo para testes

O projeto inclui classes Java de exemplo em:

- `examples/java-src/com/example/witupllmdemo/`

Classes disponiveis:

- `com.example.witupllmdemo.ExampleTransferService`
- `com.example.witupllmdemo.ExampleRegistrationService`
- `com.example.witupllmdemo.ExampleTextRules`

Contexto adicional para prompt:

- `examples/project-overview.txt`

## Script pronto para executar o fluxo completo

Para compilar as classes de exemplo e executar o `witup-llm`:

```bash
./scripts/run-example-witup-llm.sh
```

Esse script:

1. Compila as classes de exemplo para `generated/examples-classes`.
2. Compila o projeto `witup-llm`.
3. Sobe o Ollama (se necessario).
4. Aplica limite de CPU no macOS (`cpulimit`) quando disponivel.
5. Executa o `witup-llm` com limite por requisicao (`--num-thread`).

Exemplo em dry-run:

```bash
./scripts/run-example-witup-llm.sh --dry-run
```

Exemplo escolhendo classe/modelo/limite:

```bash
./scripts/run-example-witup-llm.sh \
  --class-name com.example.witupllmdemo.ExampleRegistrationService \
  --model qwen2.5-coder:7b \
  --num-thread 2 \
  --cpu-limit 50
```

## Modo sem Ollama (dry-run)

Para validar apenas a parte WITUp + prompt:

```bash
mvn -q -DskipTests exec:java -Dexec.args="--class-path /caminho/target/classes --class-name com.exemplo.MinhaClasse --dry-run"
```

Saidas no dry-run:

- `generated/witup-analysis.json`
- `generated/unit-test-prompt.txt`

## Testes do projeto

```bash
mvn test
```

## Testes de integracao com Testcontainers (Ollama)

O projeto inclui `OllamaContainerIT`, que sobe um container `ollama/ollama:latest` apenas durante o teste e remove ao final.

Para executar os testes de integracao:

```bash
mvn -Pllm-it verify
```

Notas:

- Requer Docker em execucao.
- Se Docker nao estiver disponivel, os testes `*IT` sao marcados como skipped automaticamente (`@Testcontainers(disabledWithoutDocker = true)`).

## Mutation tests (PIT)

Para rodar mutation testing:

```bash
mvn pitest:mutationCoverage
```

Relatorios gerados em:

- `target/pit-reports/`

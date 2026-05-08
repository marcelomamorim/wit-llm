# Primeiros Passos

## Pré-requisitos

| Ferramenta | Versão mínima | Uso |
| :--- | :--- | :--- |
| Go | 1.24+ | Compilar a CLI |
| Java | 11+ | Compilar e executar os projetos Java |
| Maven | 3.6+ | Rodar testes, JaCoCo e PIT |
| Git | atual | Clonar e manter os projetos-alvo |

Além disso, você vai precisar de:

- `OPENAI_API_KEY`
- checkout local de `guava`
- checkout local de `commons-collections`
- um baseline WIT em JSON para cada um dos dois projetos

## Compilação

```bash
git clone https://github.com/marcelomamorim/witup-llm.git
cd witup-llm
go build -o bin/witup ./cmd/witup
```

## Limpeza do workspace

Antes de uma nova rodada:

```bash
./scripts/limpar-projeto.sh --confirmar
```

## Execução recomendada

O caminho mais simples é usar o script dedicado:

```bash
export OPENAI_API_KEY="sua-chave"
export GUAVA_ROOT="/caminho/para/guava"
export GUAVA_WIT_ANALYSIS="/caminho/para/guava-wit.json"
export COMMONS_COLLECTIONS_ROOT="/caminho/para/commons-collections"
export COMMONS_COLLECTIONS_WIT_ANALYSIS="/caminho/para/commons-collections-wit.json"

./scripts/executar-segunda-fase.sh
```

## Execução manual

Se você quiser rodar sem o script:

```bash
./bin/witup executar-segunda-fase \
  --config pipelines/fase-dois-guava-commons.json \
  --generation-model openai_main
```

## O que sai no final

- `phase-two-study.json`
- `csv/phase-two-summary.csv`
- `csv/phase-two-metrics.csv`
- `csv/phase-two-comparison.csv`
- `dashboard.html`

## Quando algo der errado

Os pontos mais comuns para revisar são:

1. caminho do checkout Java;
2. caminho do baseline WIT;
3. `OPENAI_API_KEY`;
4. disponibilidade de `mvn` ou `mvnw`;
5. compatibilidade do projeto com JaCoCo e PIT.

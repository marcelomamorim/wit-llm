# Primeiros Passos

## Pre-requisitos

| Ferramenta | Versao Minima | Proposito |
| :--- | :--- | :--- |
| **Go** | 1.23+ | Compilacao do binario `witup` |
| **Java JDK** | 11+ | Compilacao e execucao dos projetos-alvo |
| **Maven** | 3.6+ | Build e execucao de testes Java |
| **DuckDB CLI** | 0.9+ | (Opcional) Consultas ad-hoc no banco analitico |

## Instalacao

### Compilacao do Binario

```bash
# Clone o repositorio
git clone https://github.com/marcelomamorim/witup-llm.git
cd witup-llm

# Compile o binario
go build -o bin/witup cmd/witup/main.go
```

### Verificacao

```bash
# Verificar conectividade com LLM
export OPENAI_API_KEY="sua-chave-aqui"
./bin/witup sondar --config pipeline.example.json
```

## Configuracao Rapida

Copie o arquivo de exemplo e ajuste para seu ambiente:

```bash
cp pipeline.example.json meu-pipeline.json
```

Edite `meu-pipeline.json` para configurar:

- **Projeto-alvo**: Caminho para o projeto Java a ser analisado
- **Modelo LLM**: Chave API, modelo, temperatura
- **Metricas**: Comandos Maven, JaCoCo e PIT

Veja a [Referencia de Configuracao](configuration.md) para detalhes completos.

## Executando o Piloto

O projeto inclui um script para executar o piloto completo com o projeto `visualee`:

```bash
# Limpar artefatos anteriores
./scripts/limpar-projeto.sh --confirmar

# Exportar chave da API
export OPENAI_API_KEY="sua-chave"

# Executar piloto completo
./scripts/executar-visualee-piloto.sh
```

O piloto executa automaticamente:

1. Clone do projeto `visualee`
2. Checkout no commit do artigo
3. Carga do baseline WITUP no DuckDB
4. Parte 1 (comparacao de ExPaths)
5. Parte 2 (geracao e avaliacao de testes)
6. Consolidacao e geracao de graficos

### Acompanhamento

```bash
# Monitorar logs em tempo real
tail -f generated/piloto-visualee/logs/cli.log

# Visualizar resultados no DuckDB
./bin/witup visualizar-dados --config generated/configs/piloto-visualee.runtime.json
```

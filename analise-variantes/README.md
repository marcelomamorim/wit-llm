# Scripts das variantes e da análise pós-paper

Reproduzem os resultados de V1/V2 e as análises que o paper original não tinha.

## Geração das variantes
| script | papel |
|---|---|
| `gerar-variantes.sh` | monta os JSONL das 6 variantes (um fator por vez vs baseline) |
| `submeter-variantes.sh` | submete em chunks de 2.500 ao Batch API |
| `daemon-submissao.sh` | reenfileira conforme a fila esvazia (limite: ~20M tokens POR MODELO) |

Variáveis: `WITUP_PROJECT_KEY`, `WITUP_PROMPT_GOLDEN` (0=baseline), `WITUP_PROMPT_ORACLE` (1=V1).

## Execução
| script | papel |
|---|---|
| `executar-variante.sh` | materializa pares → empacota shards → S3 |
| `driver-variantes.sh` | dispara 48 builds por variante no CodeBuild |
| `paired-shard-buildspec.yml` | buildspec com **preservação dos .jtr** (o original descartava) |
| `driver.sh` / `status.sh` | fan-out genérico; teto medido = 30 simultâneos (limite é 15 *na fila*) |

## Análise
| script | produz |
|---|---|
| `static_oracle.py` | classificação de oráculo por código-fonte (mira × voz) |
| `analisar-variante.py` | o mesmo, direto das respostas do Batch API |
| `analyze_full.py` | desfechos de execução a partir dos `.jtr` |
| `cruzar-variante.py` | estático × execução (métrica definitiva) |
| `recompute.py` | métricas do paper sobre base estrita |
| `merge-jcov.sh` | merge dos 24 shards + RepGen (local, via Docker) |
| `dims-extra.py` | ramos `catch`, `athrow`, por tipo de branch |
| `analisar-exec.py` | compila/passa/evidência por braço |
| `Mutador.java` | neutraliza o N-ésimo `athrow` via ASM (Eixo B) |
| `minerar-bugs.py` | candidatos a replay histórico (Eixo A, adiado) |

## ⚠️ Armadilhas verificadas
- **`java` do PATH na imagem é JDK 21**, não 11 — usar `/opt/test-jdk/bin/java`
  explicitamente, senão você enxerta classe do 11 num runtime 21.
- **`--method-count` limita o catálogo**, não só a amostra — catalogar com 9999 e filtrar
  o JSONL depois.
- **`--jdk-root` precisa ser absoluto** (ver commit 0d0e324).
- Offset de bytecode: usar índice do N-ésimo `athrow`, validado com `javap -c`.
- macOS traz bash 3.2 — sem `declare -A`.

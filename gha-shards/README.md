# Sharded jtreg + JCov runner

Executa uma suíte jtreg particionada em 24 shards × 2 braços, com cobertura
JCov, em runners amd64 nativos — 20 jobs em paralelo no plano free para
repositório público.

Entradas e saídas ficam em S3. Este repositório contém apenas infraestrutura:
nenhum teste, manifest ou resultado. Os nomes de bucket, run e braços vêm de
**secrets**, não de inputs de `workflow_dispatch`, porque inputs de dispatch
aparecem no histórico de execuções, que é público num repo público; valores de
secret o GitHub mascara nos logs.

## Secrets necessários

| secret | o que é |
|---|---|
| `AWS_ACCESS_KEY_ID` | credencial com escopo mínimo (ver `iam-policy.json`) |
| `AWS_SECRET_ACCESS_KEY` | idem |
| `S3_BUCKET` | bucket onde ficam `<run>/inputs/` e `<run>/paired-results/` |
| `RUN_STAMP_1` | prefixo do primeiro run |
| `RUN_STAMP_2` | prefixo do segundo run |
| `ARM_A` | nome do primeiro braço (componente de caminho no S3) |
| `ARM_B` | nome do segundo braço |
| `SRC_IMAGE` | imagem base no Docker Hub, `repo:tag` |

## Uso

1. `mirror-image` — uma vez. Espelha `SRC_IMAGE` para
   `ghcr.io/<owner>/<repo>/evaluator:amd64`. Leva ~15–20 min e o pull acontece
   na rede do GitHub, sem upload da sua máquina. **Depois, em Packages, marcar o
   pacote como público**, senão os jobs falham no pull.

2. `shards` — uma vez por run, com o input `run` = `1` ou `2`. Cada despacho
   são 48 jobs. Os dois podem ser disparados juntos; o limite de 20 jobs
   concorrentes é da conta e o GitHub enfileira o resto.

## Reentrância

O passo *pular se já concluído* verifica se o `jcov-result.xml` daquele shard já
está no S3 e sai sem fazer nada. Reexecutar o workflow depois de falhas parciais
roda só o que falta, e shards concluídos por outro meio também são respeitados.

## Comparabilidade

O `include=` do agente JCov em `shard.sh` é idêntico ao dos runs anteriores
(`java/, javax/, sun/, com/sun/, jdk/`). Estreitá-lo aceleraria pouco — os
pacotes dominantes já estão na lista — e tornaria a cobertura incomparável com o
baseline. Não alterar.

## Por que não rodar local

A imagem é amd64. Em Apple Silicon ela roda sob emulação QEMU, o que custou
~30 min por shard numa medição real; em runner amd64 nativo a expectativa é
5–10 min.

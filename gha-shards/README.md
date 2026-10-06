# Execução dos shards pareados no GitHub Actions

Executa os 96 shards (2 variantes × 2 cenários × 24) de jtreg + JCov em runners
amd64 nativos, 20 em paralelo. Substitui o run local, que roda sob emulação QEMU
em Apple Silicon e é 3–8× mais lento.

O repositório **não contém nenhum teste gerado, manifest ou resultado**: os inputs
vêm do S3 e os resultados voltam para o S3, no mesmo layout dos runs do CodeBuild
(`<stamp>/paired-results/<cenário>/<shard>/`). O conteúdo científico nunca fica
público; aqui só existe infraestrutura.

## Por que GHCR e não Docker Hub

A imagem do avaliador tem 6,98 GB. São 96 jobs, e o Docker Hub limita pulls
anônimos a 100 por 6 h — o run estouraria o limite no meio. O `mirror-image.yml`
copia a imagem para o GHCR uma única vez (um pull do Hub, feito na rede do
GitHub, sem upload da sua máquina), e os jobs puxam de lá, sem limite para
pacote público.

## Setup

1. **Criar o repositório público** e enviar só este diretório, sem histórico:

   ```bash
   cd gha-shards && git init && git add -A
   git -c user.name=anon -c user.email=anon@example.com commit -m "shard runner"
   git remote add origin git@github.com:<conta>/<repo>.git
   git push -u origin main
   ```

   Sem `--co-authored-by`, sem o histórico do `wit-llm` — os 44+ commits de lá
   carregam trailers e nome de autor que quebrariam o double-blind.

2. **Credenciais AWS com escopo mínimo.** Criar um usuário IAM novo (ex.
   `gha-shards`) com a policy de `iam-policy.json` — só lê `inputs/` e escreve
   `paired-results/` das duas variantes, nada além disso. O usuário
   `experiment-dev` atual não serve: tem permissão ampla demais e não pode criar
   IAM. Depois, em *Settings → Secrets and variables → Actions*, adicionar
   `AWS_ACCESS_KEY_ID` e `AWS_SECRET_ACCESS_KEY`.

3. **Espelhar a imagem** — rodar `mirror-image` (Actions → mirror-image → Run).
   Leva ~15–20 min. Depois, em *Packages*, marcar o pacote como **público**, ou
   os jobs falham no pull.

4. **Executar** — rodar `shards` uma vez por variante:

   | | |
   |---|---|
   | `stamp` | `jdk-v4-20261004` |
   | `stamp` | `jdk-v3a-20261004` |

   Os dois despachos podem ser disparados juntos; o limite de 20 jobs
   concorrentes é da conta e o GitHub enfileira o resto.

## Reentrância

O passo *pular se já concluído* verifica se o `jcov-result.xml` daquele shard já
está no S3 e sai sem fazer nada se estiver. Então reexecutar o workflow depois de
falhas parciais só roda o que falta — inclusive os 2 shards que já foram
concluídos localmente (`jdk-v4/direct-tests/00` e `01`).

## Comparabilidade com o baseline

O `include=` do agente JCov é **idêntico** ao dos runs de baseline no CodeBuild
(`java/, javax/, sun/, com/sun/, jdk/`). Estreitá-lo para os 82 pacotes-alvo
aceleraria pouco — os pacotes dominantes (`java/util/`, `java/lang/`,
`java/util/concurrent/`) já estão na lista — e tornaria a cobertura não
comparável, destruindo a comparação pareada. Não mexer.

## V3b e V3c

Não entram: têm 0 testes gerados. Pararam no `billing_hard_limit_reached` da
OpenAI e os diretórios têm apenas `requests.jsonl`. Precisam de nova geração
antes de qualquer execução.

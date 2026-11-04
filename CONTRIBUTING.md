# Como contribuir

Contribuições para a CLI, a documentação e a reprodutibilidade dos experimentos são bem-vindas. Para discutir uma mudança significativa, abra uma issue descrevendo o problema e a abordagem antes de enviar o pull request.

## Ambiente e validação

Use a versão de Go indicada em `go.mod`. Na raiz do repositório:

```bash
make check-fmt
make vet
make test
```

Ao alterar a geração ou a avaliação, adicione ou atualize testes que verifiquem o comportamento relevante. Descreva no pull request os comandos executados, as limitações e qualquer dependência de dados, infraestrutura ou serviços externos. Não faça chamadas pagas a APIs durante os testes automatizados sem uma configuração explicitamente opt-in.

## Experimentos e dados

Não sobrescreva artefatos originais. Registre modelo e versão, parâmetros, prompts, fontes de dados, versão do projeto-alvo, passos de execução e critérios de exclusão. Diferencie resultados observados, estimativas e hipóteses. Ao propor métricas de cobertura, documente seu denominador e o tratamento de testes ausentes, falhas e baseline.

Antes de publicar exemplos, logs ou resultados, confira sua licença e remova credenciais, dados pessoais e identificadores privados. Não envie `.env`, chaves, dumps de ambiente ou saídas locais de experimentos. Arquivos já rastreados continuam no Git mesmo que uma regra de `.gitignore` seja adicionada.

Para reportar vulnerabilidades ou vazamento de credenciais, siga [SECURITY.md](SECURITY.md) e não divulgue segredos em uma issue pública.

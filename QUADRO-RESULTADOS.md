# WIT vs Direct — Quadro de Resultados

OpenJDK 11 · 2026-10-08 · Marcelo Amorim

## Em resumo

Com o mesmo orçamento de testes, o contexto WIT produz **1,3× a 1,8× mais
testes que checam o contrato de exceção e passam** — 1.195 contra 681 no melhor
caso. O ganho depende de o teste ter como reprovar: quando o oráculo não
reprova, a vantagem desaparece (1,08×). Em cobertura de código a diferença é
pequena e às vezes negativa, porque o WIT alcança mais métodos e vai menos fundo
em cada um. **Detecção de defeito real ainda não foi medida.**

| Achado | Número | Robustez |
| --- | --- | --- |
| Mira na exceção documentada | 1,30–1,62× | Alta — nas quatro condições, inclusive no controle |
| Verificação forte que passa | 1,33–1,76× | Alta — nas três intervenções; 1,08× no controle |
| Métodos-alvo alcançados | 1,004–1,028× | Alta — nas quatro, mas magnitude pequena |
| Profundidade (branches por método) | 0,92–1,00× | Desvantagem do WIT em 3 de 4 |
| Detecção de defeito real | — | **Não medida** |

## O exemplo que explica tudo

O mesmo método, `Arrays.setAll(double[], IntToDoubleFunction)`, com os dois
prompts. Os dois testes recebem o mesmo orçamento e o mesmo método-alvo; a única
diferença é que o segundo prompt inclui a precondição de exceção extraída pelo
WIT.

**Direct** — testa o caminho normal:

```java
public static void main(String[] args) {
    double[] array = new double[5];
    IntToDoubleFunction generator = i -> i * 2.0;
    Arrays.setAll(array, generator);
    for (int i = 0; i < array.length; i++) {
        if (array[i] != i * 2.0) {
            throw new AssertionError("Unexpected value at index " + i);
        }
    }
    System.out.println("PASS");
}
```

**WIT** — vai direto à condição excepcional documentada:

```java
public static void main(String[] args) {
    double[] array = {1.0, 2.0, 3.0};
    try {
        Arrays.setAll(array, null);
        throw new AssertionError("Expected NullPointerException was not thrown");
    } catch (NullPointerException e) {
        // Expected exception, test passes
    } catch (Throwable t) {
        throw new AssertionError("Unexpected exception type: " + t);
    }
}
```

Esse par mostra os três achados de uma vez:

- **Mira** — o teste WIT captura `NullPointerException`, que é a exceção que a
  documentação do método promete. O Direct nem tenta: ele não sabe que existe
  essa condição. É a vantagem de 1,30–1,62×.
- **Profundidade** — o Direct percorre um laço de cinco elementos e verifica
  cinco valores; o WIT faz **uma** chamada e para. Mais cobertura para o Direct,
  no mesmo método. É a desvantagem de 2–6% em branches.
- **Voz** — ambos lançam `AssertionError` quando a expectativa falha, então
  ambos reprovam de verdade. Nesta condição (V1, com instrução de oráculo) 99,8%
  dos testes mirados têm voz. No controle de temperatura, só 59,5% — e aí o
  teste WIT viraria um que imprime a falha e aprova de todo modo.

Note também o `catch (Throwable t)` no teste WIT: ele rejeita o tipo errado de
exceção em vez de aceitar qualquer falha. É um oráculo mais preciso do que o
mínimo exigido.

## Como o experimento funciona

A cadeia completa, de uma ponta à outra:

1. **Análise estática (WIT)** — percorre o fonte do OpenJDK 11 e extrai, para
   cada método, as precondições que levam cada exceção a ser lançada (os
   *expaths*). Cada caminho é verificado por SMT, para descartar condição
   insatisfazível. Saída: 4.417 métodos-alvo com suas condições.
2. **Dois prompts por método** — o *Direct* pede testes para o método; o *WIT* é
   idêntico mais as precondições. Nada mais muda: mesmo modelo, mesma
   temperatura, mesmo orçamento. É o que torna o desenho pareado.
3. **Geração** — os dois conjuntos de prompts vão à Batch API da OpenAI. Cada
   variante (V1, V2, V4, V3a) muda **uma** coisa: instrução de oráculo, modelo,
   exemplo no cabeçalho ou temperatura.
4. **Materialização** — as respostas viram arquivos `.java` compiláveis,
   pareados por método entre os dois braços, e divididos em 24 fatias (*shards*).
5. **Execução** — cada shard roda no jtreg, o harness de regressão do próprio
   OpenJDK, dentro de um container com o JDK 11 de teste. Um agente do JCov
   instrumenta as classes e registra blocos, ramos e linhas atingidos. 96 shards
   no total, em paralelo no AWS CodeBuild.
6. **Medição** — três níveis independentes:
   - *estático*: classifica cada teste pelo código-fonte (mira × voz), cobrindo
     100% dos testes gerados;
   - *execução*: lê os arquivos `.jtr` para compile e pass de cada teste;
   - *cobertura*: mescla os relatórios do JCov e restringe aos métodos-alvo.

A especificação contra a qual os testes são julgados vem do próprio código. Isso
mede **conformidade de contrato**, não detecção de defeito — ver as ressalvas no
final.

## As quatro variações

Cada variante muda **uma** coisa em relação à linha de base. É isso que torna o
desenho interpretável.

| | Modelo | Temp. | Prompt | O que testa | Resultado |
| --- | --- | --- | --- | --- | --- |
| **V1** | gpt-4.1-nano | 0 | + instrução de oráculo | dar voz por **instrução** | **1,755×** |
| **V2** | gpt-4.1-**mini** | 0 | base | dar voz por **capacidade** | **1,329×** |
| **V4** | gpt-4.1-nano | 0 | + exemplo no cabeçalho | dar voz por **demonstração** | **1,705×** |
| **V3a** | gpt-4.1-nano | **0,7** | base | **controle negativo** | 1,075× |
| V3b, V3c | gpt-4.1-nano | 0,7 | base | réplicas de V3a | **não geradas** |

V1 acrescenta ao prompt a instrução de que o teste deve sinalizar falha lançando
exceção ou chamando `System.exit(1)`, e que imprimir `FAIL:` não reprova. V2
mantém o prompt da linha de base e troca apenas o modelo — é a única variante em
que o modelo muda. V4 acrescenta um cabeçalho obrigatório que **contém** um
`throw new AssertionError(...)`: a diferença em relação ao V1 é que o V1
*descreve* a regra e o V4 *demonstra* a estrutura.

V3a não muda prompt nem modelo — muda só a temperatura. É o controle negativo:
elevar a temperatura não tem razão para ensinar o modelo a reprovar um teste. Se
produzisse o mesmo ganho das três intervenções, o efeito todo seria artefato.

Dois pontos que valem na conversa. **As três intervenções são formas diferentes
do mesmo conserto** — instrução, capacidade e demonstração — e todas rendem
entre 1,33× e 1,76×; convergência por caminhos independentes é mais forte que um
efeito único repetido. E **V1, V4 e V3a usam o mesmo modelo e a mesma
temperatura base**, então a comparação entre elas isola exclusivamente o prompt.

V3b e V3c eram as réplicas — três amostras na mesma temperatura, para medir
quanto o resultado oscila por acaso de geração. Nunca foram geradas, e é por isso
que **os números de 1,33–1,76× não têm barra de erro**. Sem elas não se sabe se a
diferença entre 1,329× e 1,755× é efeito da intervenção ou ruído de amostragem.

## Resultado principal: verificação forte que compila e passa

Dado o mesmo número de tentativas e os mesmos métodos, o contexto WIT produz
entre 1,3× e 1,8× mais testes que verificam o contrato de exceção **e** passam.
Em números absolutos, no melhor caso são 1.195 testes úteis contra 681 — 514 a
mais, sem custo adicional de geração. Mas o ganho só aparece quando o teste tem
como reprovar: com oráculo sem voz, a vantagem cai a 1,08×, praticamente
empate. Contexto WIT e oráculo com voz são portanto **complementares** — nenhum
dos dois entrega o resultado sozinho, e é por isso que o controle negativo
importa tanto quanto as intervenções.

| Condição | O que varia | Tipo | Direct | WIT | Razão | % mirados com voz (WIT) |
| --- | --- | --- | --- | --- | --- | --- |
| V1 | instrução explícita de oráculo | intervenção | 681 | 1.195 | **1,755×** | 99,8% |
| V2 | modelo mini | intervenção | 940 | 1.249 | **1,329×** | 99,9% |
| V4 | few-shot no cabeçalho | intervenção | 655 | 1.117 | **1,705×** | 97,4% |
| V3a | temperatura 0,7 | controle | 623 | 670 | 1,075× | 59,5% |

*Verificação forte* = o teste captura o tipo de exceção documentado **e** possui
mecanismo que reprova o harness (`throw`, `System.exit(≥1)`, `assert`,
`Asserts.fail`). Imprimir `"FAIL:"` não reprova e não conta.

O ganho é composto, não um efeito único: no V1 ele decompõe em mira 1,543× ×
voz ≈1,0 × aprovação condicional dos fortes 1,082×. Direcionamento e qualidade
somam em vez de se compensarem.

O controle negativo cumpre seu papel: a temperatura destrói a voz (99% → 59,5%)
sem alterar a mira (1,586×). Isso separa os dois mecanismos e sustenta a
atribuição causal ao contexto WIT.

## Mira e profundidade: o que separa alcance de cobertura

O Direct cobre 3,32 branches por método alcançado nas quatro condições, com
estabilidade notável. O WIT cobre 3,07–3,32. É nesse fator, e só nele, que o
Direct ganha.

| Condição | Braço | Métodos alcançados | Branches por método | Branches totais |
| --- | --- | --- | --- | --- |
| V1 | Direct | 1.787 | 3,32 | 5.934 |
| V1 | WIT | 1.826 (+2,2%) | 3,07 (−7,6%) | 5.601 (−5,6%) |
| V2 | Direct | 1.900 | 3,32 | 6.301 |
| V2 | WIT | 1.907 (+0,4%) | 3,32 (+0,2%) | 6.335 (+0,5%) |
| V4 | Direct | 1.792 | 3,32 | 5.950 |
| V4 | WIT | 1.812 (+1,1%) | 3,21 (−3,2%) | 5.824 (−2,1%) |
| V3a | Direct | 1.795 | 3,30 | 5.925 |
| V3a | WIT | 1.845 (+2,8%) | 3,15 (−4,6%) | 5.806 (−2,0%) |

Três conceitos distintos, fáceis de confundir:

- **Alcance** — em quantos métodos-alvo diferentes o teste chega. O WIT ganha
  nas quatro condições.
- **Profundidade** — quanto do código dentro de cada método é exercitado. O
  Direct ganha.
- **Verificação** — se o teste de fato checa o contrato e reprova quando ele é
  violado. Um teste pode alcançar o método, cobrir todas as branches e não
  verificar nada.

O mecanismo é coerente com o desenho: o WIT direciona o teste à condição
excepcional documentada — passa `null`, passa índice inválido — confirma que a
exceção certa é lançada, e para. Um caminho de erro é, por natureza, curto. O
Direct, sem a indicação de onde está a exceção, gera testes mais genéricos sobre
o comportamento normal, que percorrem mais ramos e erram mais o alvo
excepcional.

Implicação prática: o WIT responde melhor "este método cumpre o contrato de
exceção que documenta?" e pior "este método está bem exercitado?". São perguntas
diferentes. Apresentar só cobertura subestima o WIT — a vantagem em verificação
é de 1,33–1,76×, uma ordem de grandeza maior que a diferença de 1–3% em
cobertura.

## Cobertura restrita aos métodos-alvo

O WIT vence **métodos alcançados nas quatro condições** (1,004–1,028×). Em
blocks e branches o Direct vence em 3 de 4, e as inversões do WIT são de
magnitude ≤1,011×.

Razão WIT/Direct — valores acima de 1,000 favorecem o WIT:

| Condição | Métodos | Blocks | Branches | Lines | Vitórias WIT |
| --- | --- | --- | --- | --- | --- |
| V1 instrução | **1,0218** | 0,9648 | 0,9439 | 0,9857 | 1/4 |
| V2 modelo mini | **1,0037** | **1,0056** | **1,0054** | **1,0105** | 4/4 |
| V4 few-shot | **1,0112** | 0,9816 | 0,9788 | 0,9907 | 1/4 |
| V3a temperatura | **1,0279** | 0,9906 | 0,9799 | **1,0053** | 2/4 |

O denominador é a união dos métodos do manifest presentes em qualquer relatório
— cerca de 2.520 métodos, **idêntico nos três braços de cada condição**.
Ausência de um método num relatório significa classe nunca carregada, o que é
cobertura zero e não dado faltante.

A linha `ALL:` do RepGen **não serve** para esta comparação: cada merge tem um
universo diferente de classes, porque o XML inclui as classes de teste geradas —
baseline 4.016, Direct 4.165, WIT 4.163. Dividir numeradores desses universos
por um denominador alheio produz números sem significado.

Um padrão a investigar, ainda não estabelecido: as condições de maior vantagem
de alcance (V3a 1,0279 e V1 1,0218) são as que mais perdem branches; o V2, de
menor alcance (1,0037), é o único que ganha branches. Sugere troca entre
espalhar a mira e aprofundar, mas são quatro pontos.

## Reprodutibilidade

Há três camadas com garantias diferentes. A distinção importa porque "os números
se repetem" é verdade para uma delas, desconhecido para outra e falso por
construção para a terceira.

| Camada | Repete? | Status |
| --- | --- | --- |
| Análise — classificação, cobertura, razões | Sim, exatamente | Verificado: md5 idêntico em três execuções |
| Execução — compile e pass dos testes | **Sim, nesta amostra** | Medido: 726 testes, 0 mudaram |
| Geração — testes produzidos pelo LLM | Não, por construção | É o que V3a/b/c mediriam; V3b e V3c nunca foram gerados |

A camada de análise foi conferida empiricamente, não assumida: rodar a mesma
análise três vezes sobre os mesmos artefatos produz saída idêntica bit a bit.
Qualquer pessoa com os XML e os `.jtr` reproduz os números deste quadro.

A camada de execução **foi medida em 09/10** e não variou. Oito shards do V4
(quatro de cada braço) foram reexecutados no CodeBuild num prefixo separado do
S3, e os `.jtr` das duas execuções foram comparados teste a teste: **726 testes
em comum, 726 com o mesmo desfecho, zero mudanças** (0,00%).

Isso contraria a expectativa: o jtreg usa `-timeout:4` com concorrência, e um
shard chegou a levar 151 min contra mediana de 33, o que sugeria testes
sensíveis a tempo. Nesta amostra eles não se manifestaram.

Escopo da afirmação: 726 testes dos 8.365 do V4 (8,7%), nos shards 00 a 03, que
não estão entre os mais lentos. A conclusão defensável é *"não foi observada
instabilidade em 726 testes"*, não *"não existe instabilidade"*. Ampliar a
amostra custa ~US$ 0,33 por shard.

A camada de geração não é um defeito a corrigir: reexecutar o lote na OpenAI
produz testes diferentes, e por isso o desenho correto reporta variância entre
amostras. As variantes V3a, V3b e V3c existiam exatamente para isso, com três
amostras na mesma temperatura; V3b e V3c pararam no limite de cobrança da OpenAI
e têm zero testes gerados. **Enquanto isso não for refeito, a variância de
geração segue sem estimativa.**

## Procedência e ressalvas

Sujeito: OpenJDK 11, 4.417 métodos-alvo com precondições de exceção extraídas
por análise estática e verificadas por SMT. Execução sob jtreg com cobertura
JCov 3.0. Desenho pareado com **orçamento de teste idêntico nos dois braços** de
cada condição.

| Condição | Shards por braço | Testes executados | Descartes no merge |
| --- | --- | --- | --- |
| V1 | 24/24 | 3.989 + 3.989 | 0 |
| V2 | 24/24 | 3.700 + 3.700 | 0 |
| V4 | 24/24 | 4.182 + 4.183 | 0 |
| V3a | 24/24 | 4.105 + 4.105 | 0 |

Um defeito de infraestrutura precisou ser corrigido antes destes números
existirem. O JCov Merger com `-boe skip` descarta arquivo problemático e
**termina com sucesso**: o XML de saída parece normal e nada indica que um shard
ficou de fora. Dez shards foram perdidos assim, dos quais sete em merges
anteriores, deixando um braço do V1 com 22 shards contra 23 do outro. A causa foi
estabelecida com correlação completa — 10 de 10 descartados continham o pacote
`com.sun.proxy`, cujas classes `$ProxyN` são geradas em runtime e nomeadas
sequencialmente por JVM, de modo que o mesmo nome carrega estruturas diferentes
entre shards. O filtro remove essas duas classes de cerca de 1.030, inexistentes
no fonte do JDK e fora dos métodos-alvo, e o `-boe skip` foi eliminado do
pipeline para que conflito futuro falhe em vez de sumir com dados.

Isso é relevante para o artigo: as diferenças de cobertura entre braços são de
1–3%, pequenas o bastante para que um shard a mais ou a menos desloque o
resultado. Vale declarar como ameaça à validade — qualquer estudo que mescle
cobertura de execuções paralelas de JVM com `include` abrangente está exposto,
porque proxies, lambdas e acessores de reflexão recebem nome sequencial por JVM.

### Ressalvas em aberto

- **O regex de sinalização foi validado** (08/10). A classificação
  forte/frágil depende de um padrão reconhecer mecanismos que reprovam o
  harness. Inspeção dos 1.570 testes classificados como frágeis no V3a/WIT
  encontrou mecanismos não reconhecidos em apenas **1,1%** deles (o principal:
  `throw <variável>;` rethrow de causa). Reexecutando a classificação com um
  regex ampliado — incluindo `throw <var>;`, `Assert*.algo()` e variantes — as
  razões não se movem: V1 1,755×, V2 1,329× e V4 1,705× ficam **idênticas**, e o
  V3a passa de 1,075× para 1,077×, um único teste reclassificado. **As
  conclusões não dependem da definição do regex.**
- **Granularidade da cobertura.** Medida em (classe, nome do método), com
  sobrecargas agregadas, porque o manifest usa assinatura Java e o RepGen usa
  descritor da JVM. A regra é a mesma nos três braços, então a comparação é
  justa, mas é mais grossa que por assinatura.
- **865 dos 3.383 métodos-alvo agregados nunca foram carregados** por nenhum
  braço nem pelo baseline, e ficam fora de qualquer medida.
- **V3b e V3c não existem.** Zero testes gerados, o que deixa a variância de
  geração sem estimativa.
- **Detecção de defeito ainda não medida.** Tudo aqui é conformidade de
  contrato: o teste checa o código contra especificação derivada do próprio
  código. Não prova que os testes detectam defeito real. Fechar essa lacuna
  exige mutação dirigida ou replay de bugs históricos.

## Próximos passos

Em ordem de valor por custo.

| Passo | Custo | O que destrava |
| --- | --- | --- |
| Validar o regex de sinalização em 20 casos | ~1h, zero | confirma o instrumento que sustenta todo o resultado |
| Reexecutar 8 shards e comparar teste a teste | ~US$ 3,30 | a variância de execução, hoje desconhecida |
| Regerar V3b e V3c | billing OpenAI | a barra de erro dos 1,33–1,76× |
| Mutação dirigida nos 387 sites | infra pronta | sai de conformidade de contrato para **detecção de defeito** |
| Replay de bugs reais via Defects4J | artigo à parte | detecção com falha real, não sintética |

Os dois primeiros são baratos e protegem o que já existe: o primeiro confere se a
classificação forte/frágil está certa, o segundo mede se as taxas de pass se
repetem.

A mutação dirigida é a que mais agrega ao argumento. O procedimento: neutralizar
por ASM o `athrow` de um site de exceção coberto, rodar os testes que cobrem
aquele site, e contar como **detecção** o teste que passa no código original e
reprova no mutante. Isso fecha a crítica de *quality proxy* — a mais provável ao
trabalho — porque troca a especificação derivada do código por uma fonte de
verdade independente dele. A infraestrutura (`Mutador.java` e os 387 sites
pareados) está pronta e validada em piloto.

## Glossário

### Oráculo de teste

O critério que decide se um teste aprova ou reprova. É o conceito central deste
trabalho, porque um teste sem oráculo executa código sem julgar nada.

Sob o jtreg, um teste é um `public static void main`, e o harness aprova se
`main` retorna sem lançar exceção. Isso tem uma consequência que não é óbvia:
**imprimir uma mensagem de falha não reprova o teste**.

Oráculo frágil — o teste *parece* checar, mas aprova mesmo quando o bug existe:

```java
public static void main(String[] args) {
    try {
        List.of(1, 2, 3).get(5);
        System.out.println("FAIL: esperava IndexOutOfBoundsException");
    } catch (IndexOutOfBoundsException e) {
        System.out.println("PASS");
    }
}
```

Se `get(5)` deixar de lançar a exceção, o teste imprime `FAIL:` e **retorna
normalmente**. O jtreg aprova. O defeito passa.

Oráculo forte — o mesmo teste, com voz:

```java
public static void main(String[] args) {
    try {
        List.of(1, 2, 3).get(5);
        throw new AssertionError("esperava IndexOutOfBoundsException");
    } catch (IndexOutOfBoundsException e) {
        // comportamento esperado
    }
}
```

Agora, se a exceção não vier, o `AssertionError` propaga e o jtreg reprova.
Mecanismos válidos: `throw`, `System.exit(≥1)`, `assert` (habilitado por `-ea`
em todas as execuções) e `Asserts.fail`.

### Os dois eixos do oráculo

São independentes, e separá-los foi o que permitiu atribuir causa.

| Termo | O que é | Pergunta |
| --- | --- | --- |
| **Mira** | o teste captura o tipo de exceção que a documentação promete? | o `catch` é do tipo certo? |
| **Voz** | o teste possui mecanismo que reprova o harness? | existe `throw`, `exit`, `assert`? |

Daí a classificação usada em todo o quadro: **forte** = mira e voz; **frágil** =
mira sem voz (o exemplo de cima); **outro** = sem mira.

### Alcance, profundidade, verificação

Três coisas que "cobertura" costuma misturar.

- **Alcance** — em quantos métodos-alvo diferentes algum teste chega. Mede
  largura.
- **Profundidade** — quantos ramos condicionais são exercitados dentro de cada
  método. Mede quanto de cada método foi visitado.
- **Verificação** — se o teste checa o contrato e reprova quando ele é violado.
  Independente das outras duas.

### Os dois braços comparados

- **Direct** — o prompt pede testes para o método, sem informação sobre onde
  estão suas exceções.
- **WIT** — o mesmo prompt, mais o **contexto WIT**: as precondições que levam
  cada exceção a ser lançada (os *expaths*), extraídas por análise estática do
  código e verificadas por SMT. Em vez de o modelo adivinhar que `get` lança
  quando o índice excede o tamanho, ele recebe essa condição escrita.

Ambos os braços recebem o **mesmo orçamento de testes** para os **mesmos
métodos**, o que torna a comparação pareada.

### Condições experimentais

- **Intervenção** — mudança feita para dar voz ao oráculo: instrução explícita
  no prompt (V1), modelo mais capaz (V2) ou exemplo de código no cabeçalho, o
  *few-shot* (V4).
- **Controle negativo** — mudança que **não deveria** consertar o oráculo, usada
  para testar se o efeito observado é real: elevar a temperatura a 0,7 (V3a). Se
  o controle produzisse o mesmo ganho das intervenções, o efeito seria artefato.
  Ele não produziu — e é isso que dá força ao resultado.

### Infraestrutura e medição

- **jtreg** — o harness de testes de regressão do próprio OpenJDK. Cada execução
  gera um arquivo `.jtr` com saída, diagnósticos do compilador e resultado.
- **JCov** — a ferramenta de cobertura do OpenJDK. Um agente instrumenta as
  classes em execução e grava quais blocos, ramos e linhas foram atingidos.
- **Shard** — fatia da suíte. Os testes foram divididos em 24 shards por braço
  para executar em paralelo; depois os relatórios de cobertura são mesclados.
- **Baseline** — a cobertura da suíte de regressão oficial do JDK, sem nenhum
  teste gerado. É o ponto de partida sobre o qual se mede o que cada braço
  acrescenta.
- **Denominador por união** — o conjunto de métodos usado como base de
  comparação: todos os métodos-alvo que aparecem em qualquer um dos relatórios.
  Ausente em um relatório significa classe nunca carregada, o que é cobertura
  zero, não dado faltante.
- **Branch** — ramo de uma decisão condicional (cada saída de um `if`, `switch`,
  `&&`). Cobertura de branches é mais exigente que de linhas, porque uma linha
  pode executar sem que todos os seus ramos sejam percorridos.

### Conformidade de contrato vs detecção de defeito

Distinção que delimita o alcance de tudo aqui.

- **Conformidade de contrato** — o que foi medido: o teste confirma que o método
  lança a exceção que sua documentação promete. A especificação é derivada do
  próprio código, então é **tautológico por construção**.
- **Detecção de defeito** — o que **não** foi medido: o teste pega um bug real.
  Exigiria uma fonte de verdade independente do código — mutação dirigida
  (introduzir defeito sintético e ver se o teste reprova) ou replay de bugs
  históricos.

Essa é a crítica mais provável ao trabalho, e a mais justa.

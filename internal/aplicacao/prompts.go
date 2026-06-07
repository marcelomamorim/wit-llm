package aplicacao

import (
	"encoding/json"
	"fmt"
	"strings"

	"github.com/marceloamorim/witup-llm/internal/dominio"
)

// construirPromptAnaliseSistema monta a instrução sistêmica da análise direta.
func construirPromptAnaliseSistema() string {
	return "Você é um analisador estático especialista em código Java. Responda apenas com JSON válido."
}

// construirPromptAnaliseUsuario monta o prompt de análise de expaths para um método.
func construirPromptAnaliseUsuario(overview string, method dominio.DescritorMetodo) string {
	return fmt.Sprintf(`Analise o método Java e liste os caminhos de exceção.
Return JSON: {"method_summary":"...","expaths":[{"path_id":"...","exception_type":"...","trigger":"...","guard_conditions":[...],"confidence":0.0,"evidence":[...]}]}

Visão geral do projeto:
%s

Assinatura do método: %s
Código-fonte do método:
%s
`, overview, method.Assinatura, method.Origem)
}

// construirPromptGeracaoSistema monta o prompt sistêmico para geração de testes.
func construirPromptGeracaoSistema(framework string) string {
	switch normalizarFrameworkTestes(framework) {
	case frameworkJUnit4:
		return "Você é um especialista em escrita de testes Java usando JUnit 4. Responda apenas com JSON. Gere testes compatíveis com org.junit.Test e org.junit.Assert. Use @Before/@After quando necessário. Não use imports, anotações ou utilitários de org.junit.jupiter.* e não dependa de recursos exclusivos de JUnit 5 como @TempDir, @DisplayName ou assertDoesNotThrow. Nunca invente pacotes, classes, métodos, construtores, campos ou enums que não estejam evidentes no contexto fornecido. Não assuma construtor sem argumentos; prefira fábricas públicas, builders, métodos getInstance(), of() ou valueOf() que estejam evidentes. Evite reflexão por padrão; só use getDeclaredField/getDeclaredConstructor/getDeclaredMethod/setAccessible quando o membro refletido aparecer explicitamente no source_code fornecido e não houver caminho público confiável. Não use Mockito a menos que o contexto mostre dependência explícita do projeto. Não tente sobrescrever ou mockar métodos estáticos de classes do projeto; use entradas reais e asserts sobre comportamento observável. Antes de responder, faça uma checagem estática mental: cada teste deve compilar com as APIs visíveis no checkout atual e deve ter alta chance de passar no primeiro run. Se houver dúvida entre o baseline e o código atual, priorize o comportamento observável do código atual."
	case frameworkJUnit5:
		return "Você é um especialista em escrita de testes Java usando JUnit 5 (Jupiter). Responda apenas com JSON. Gere testes compatíveis com org.junit.jupiter.api.*. Nunca invente pacotes, classes, métodos, construtores, campos ou enums que não estejam evidentes no contexto fornecido. Não assuma construtor sem argumentos; prefira fábricas públicas, builders, métodos getInstance(), of() ou valueOf() que estejam evidentes. Evite reflexão por padrão; só use getDeclaredField/getDeclaredConstructor/getDeclaredMethod/setAccessible quando o membro refletido aparecer explicitamente no source_code fornecido e não houver caminho público confiável. Não use Mockito a menos que o contexto mostre dependência explícita do projeto. Não tente sobrescrever ou mockar métodos estáticos de classes do projeto; use entradas reais e asserts sobre comportamento observável. Antes de responder, faça uma checagem estática mental: cada teste deve compilar com as APIs visíveis no checkout atual e deve ter alta chance de passar no primeiro run. Se houver dúvida entre o baseline e o código atual, priorize o comportamento observável do código atual."
	case frameworkJTReg:
		return "Você é um especialista em escrita de testes Java para o OpenJDK usando jtreg. Responda apenas com JSON." +
			" RESTRIÇÃO CRÍTICA DE VERSÃO: o código gerado DEVE ser 100% compatível com JDK 11+28 (commit da75f3c4, setembro de 2018, pré-lançamento do JDK 11 GA). NÃO use nenhuma API, sintaxe ou recurso introduzido após o JDK 11." +
			" Sintaxes e APIs PROIBIDAS: records (JDK 16+, ex: record Foo(...) {}); blocos de texto/text blocks (JDK 15+, ex: \"\"\"...\"\"\"); expressões switch com yield ou seta (JDK 14+, ex: switch(x) { case 1 -> ...}); pattern matching em instanceof (JDK 16+, ex: if (obj instanceof String s)); sealed classes/permits (JDK 17+); ObjectInputFilter.setSerialFilter() (JDK 17+); qualquer outro recurso pós-JDK-11." +
			" Pacotes NÃO exportados por padrão ao unnamed module — se o teste precisar acessar pacotes como com.sun.crypto.provider, com.sun.java.util.jar.pack, com.sun.net.ssl, com.sun.security.ntlm, sun.security.*, sun.net.*, com.sun.* ou qualquer subpacote de java.base não listado no módulo público, você DEVE declarar '@modules java.base/nome.do.pacote' no cabeçalho jtreg; sem essa diretiva a compilação falha. Para jdk.internal.* evite ao máximo; só use se o método-alvo estiver literalmente nesse pacote e declare '@modules java.base/jdk.internal.subpacote'." +
			" FORMATO OBRIGATÓRIO DO CABEÇALHO JTREG: o arquivo Java DEVE começar com um comentário de bloco /* */ contendo as diretivas jtreg — NUNCA use comentários de linha // para o cabeçalho. Formato exato:" +
			" /* @test" +
			"    @summary <descrição breve em inglês>" +
			"    @run main NomeDaClasse" +
			" */" +
			" onde NomeDaClasse é EXATAMENTE o mesmo nome da classe pública definida no arquivo." +
			" NOME DA CLASSE: a classe pública do arquivo DEVE ter o mesmo nome que o arquivo sugerido em relative_path (sem a extensão .java). Se relative_path sugerir XyzWitupTest.java, a classe deve ser 'public class XyzWitupTest'. Não use nomes baseados no método testado (ex: HashMapTryAdvanceTest) quando o arquivo se chamar HashMapWitupTest.java." +
			" NÃO declare package no arquivo gerado; use sempre o pacote padrão (unnamed package)." +
			" Diretiva @modules: use quando o teste acessar módulo ou pacote não exportado por padrão. Sintaxe CORRETA: '@modules java.base' ou '@modules java.base/sun.security.util'. Sintaxe PROIBIDA: '@modules(java.base)' — parênteses são inválidos e causam erro de parse no jtreg." +
			" Gere testes standalone. Não use JUnit, Mockito, AssertJ ou bibliotecas externas. Prefira APIs públicas e observáveis. Use apenas APIs visíveis no JDK 11+28 e que apareçam no contexto fornecido. Não invente pacotes, classes, métodos, construtores, campos ou enums não evidentes no contexto. Evite reflexão por padrão; só use quando o membro refletido aparecer literalmente no source_code e não houver caminho público." +
			" Antes de responder, faça uma checagem estática mental obrigatória: (1) o arquivo compila com JDK 11+28? (2) o nome da classe pública bate com o nome do arquivo? (3) o cabeçalho jtreg usa /* */? (4) há @run main? (5) nenhum pacote inacessível é importado? (6) nenhuma sintaxe pós-JDK-11 foi usada? Só devolva o JSON se todas as respostas forem sim." +
			" REGRA DE OURO: em caso de dúvida sobre compilação, simplifique o teste ao mínimo que compila — um teste simples que compila e passa tem mais valor que um teste elaborado que falha na compilação." +
			" MODELO OBRIGATÓRIO DE CABEÇALHO (copie este padrão exato — nunca invente variações):\n" +
			"/* @test\n   @summary Verifica comportamento de X.\n   @run main NomeDaClasseExato\n */\nimport java.util.Objects;\npublic class NomeDaClasseExato {\n    public static void main(String[] args) throws Exception {\n        try { Objects.requireNonNull(null); throw new AssertionError(\"expected NPE\"); } catch (NullPointerException e) { /* expected */ }\n    }\n}\n" +
			" Se houver dúvida entre o baseline WIT e o código atual, priorize o comportamento observável do código atual. Gere apenas código Java executável — sem comentários explicativos extras, sem Javadoc adicional. O cabeçalho jtreg é obrigatório; o restante deve ser código puro."
	default:
		return fmt.Sprintf("Você é um especialista em escrita de testes Java usando %s. Responda apenas com JSON.", framework)
	}
}

// construirPromptGeracaoUsuario monta o prompt de geração de testes para um contêiner.
func construirPromptGeracaoUsuario(overview, containerName string, methodsPayload []dominio.AnaliseMetodo, contextoOpcional ...map[string]interface{}) string {
	conteudoCompactado, _ := json.MarshalIndent(compactarAnalisesParaGeracao(methodsPayload), "", "  ")
	contextoComum, _ := json.MarshalIndent(selecionarContextoGeracaoPrompt(contextoOpcional...), "", "  ")
	return fmt.Sprintf(`Gere arquivos de teste Java determinísticos para os métodos abaixo.
Return JSON: {"files":[{"relative_path":"...","content":"...","covered_method_ids":[...],"notes":"..."}]}

Regras obrigatórias:
- gere testes que tentem revelar bugs reais e proteger contra regressões futuras, sem prometer segurança absoluta;
- use apenas tipos, pacotes, enums, construtores e métodos que apareçam de forma explícita no contexto fornecido;
- se a API de construção de um objeto não estiver clara, prefira descartar o caso frágil e gerar um teste observável menor em vez de inventar construtor público;
- não assuma construtor sem argumentos para classes do projeto;
- para singletons/fábricas, prefira getInstance() quando houver evidência no contexto;
- não introduza Mockito, AssertJ ou outras bibliotecas externas sem evidência explícita de que o projeto já as usa;
- evite imports curingas e referências para pacotes que não apareçam no código real fornecido.
- trate o relatório WIT como contexto auxiliar: se houver conflito entre o baseline WIT e o código/assinatura do checkout atual, priorize o código atual;
- não transforme automaticamente um expath em teste aprovável sem checar se o comportamento ainda é compatível com o código fornecido.
- antes de devolver o JSON final, revise mentalmente cada teste e remova qualquer caso que provavelmente não compile ou não passe no checkout atual;
- só use assertThrows quando o código atual realmente sustentar a exceção; se o método atual retornar false, null, Optional.empty ou outro valor observável em vez de lançar, teste esse comportamento;
- evite reflexão e acesso a estado interno; se precisar usar reflexão, o membro refletido deve aparecer literalmente no source_code e o notes deve justificar por que não há caminho público;
- se uma chamada por reflexão puder lançar InvocationTargetException, trate InvocationTargetException explicitamente ou valide getCause(), não espere diretamente a exceção interna no assertThrows;
- não faça assertivas sobre campos privados, nomes internos ou estrutura interna instável; prefira retorno público, exceção pública clara ou efeito observável;
- use o campo notes para registrar quando um expath do WIT foi descartado, adaptado ou mantido após essa checagem de compatibilidade.
- para JUnit, o arquivo gerado deve respeitar o package e o caminho sugeridos no contexto técnico comum;
- para OpenJDK/jtreg: NÃO declare package; use pacote padrão; o cabeçalho jtreg DEVE estar em comentário de bloco /* */ (NUNCA em comentários //), com as diretivas @test, @summary e @run main NomeDaClasse — onde NomeDaClasse é EXATAMENTE o nome da classe pública e o nome base do arquivo sugerido em relative_path (sem .java); a diretiva @modules, quando usada, tem sintaxe '@modules modulo' ou '@modules modulo/pacote' — NUNCA '@modules(modulo)' com parênteses;
- para jtreg no JDK 11+28 (commit da75f3c4): NÃO use records, text blocks ("""), expressões switch com yield ou seta, pattern matching em instanceof, sealed classes, ObjectInputFilter.setSerialFilter() nem qualquer API introduzida após JDK 11; pacotes não exportados por padrão ao unnamed module (sun.security.*, com.sun.crypto.provider, sun.net.*, com.sun.*, jdk.internal.* etc.) SÓ podem ser usados se o cabeçalho jtreg declarar '@modules java.base/nome.do.pacote' — sem essa diretiva a compilação falha com erro de acesso a módulo;
- cada teste deve invocar explicitamente o método-alvo ou um comportamento público que o alcance de forma evidente;
- se o contexto técnico comum não listar Mockito, AssertJ ou outra biblioteca externa, não use essa biblioteca.

Linguagem: Java
Contêiner: %s
Visão geral do projeto:
%s

Contexto técnico comum aos dois cenários:
%s

Como interpretar o relatório WIT abaixo:
O que é WIT:
- WIT é uma técnica de análise estática para extrair automaticamente pré-condições de exceções em métodos Java.
- Neste experimento, o relatório WIT é usado como baseline/contexto: ele sugere caminhos de exceção que podem ocorrer sob certas entradas, estados ou condições de guarda.

O que são expaths:
- Um expath, ou exception path, é uma hipótese estruturada de caminho excepcional em um método.
- Cada expath descreve o tipo de exceção esperado, o gatilho que tende a provocar a exceção, as condições de guarda relevantes, evidências e confiança heurística.

- method.method_id: identificador estável do método-alvo; use para preencher covered_method_ids.
- method.file_path e method.container_name: localização do método no checkout atual.
- method.signature: assinatura textual do método; ela ajuda a validar parâmetros e retorno.
- method.source_excerpt: trecho curto do código atual do método; ele tem prioridade sobre o baseline WIT se houver divergência.
- method.source_code: corpo completo do método no checkout atual; use este campo para decidir se um teste realmente compila e passa.
- method_summary: resumo textual do comportamento inferido do método.
- expaths: lista de caminhos de exceção inferidos para o método.
- expath.path_id: identificador do caminho de exceção.
- expath.exception_type: tipo de exceção associado ao caminho.
- expath.trigger: ação/entrada que tende a disparar o caminho.
- expath.guard_conditions: condições sob as quais o caminho ocorre.
- expath.confidence: confiança heurística do baseline; não trate como prova absoluta.
- expath.evidence: indícios textuais do porquê o caminho foi inferido.
- checkout_compatibility_notes: avisos produzidos no alinhamento; se indicarem expaths descartados, não reintroduza esses cenários via inferência própria.

Ao usar expaths:
- eles sugerem hipóteses de teste e são úteis para priorizar cenários de exceção;
- use expaths como hipóteses prioritárias para criar testes de comportamento excepcional;
- um bom teste derivado de expath deve tentar revelar bugs quando o método não valida entradas, não preserva invariantes ou deixa de lançar a exceção documentada/esperada pelo comportamento atual;
- neste estudo, segurança significa redução do risco de bugs e regressões; os testes devem funcionar como uma barreira regressiva contra alterações futuras que removam validações, alterem contratos excepcionais ou permitam estados inválidos;
- eles podem estar desatualizados em relação ao checkout atual;
- quando o código atual contradisser o expath, adapte o teste ao comportamento observável do código atual e registre essa decisão em notes;
- pense no relatório WIT como um extrato heurístico do baseline, não como uma verdade a ser reproduzida cegamente.
- se um método vier sem expaths ou com checkout_compatibility_notes indicando descarte, derive os testes apenas do código atual em method.source_code.
- não force um expath se ele exigir criar estado interno artificial, acessar campo privado frágil ou depender de reflexão especulativa.

Análises dos métodos:
%s
`, containerName, reduzirVisaoGeralParaGeracao(overview), string(contextoComum), string(conteudoCompactado))
}

// construirPromptGeracaoDiretaSistema monta o prompt sistêmico para geração
// direta de testes, sem o contexto estrutural do WIT.
func construirPromptGeracaoDiretaSistema(framework string) string {
	base := construirPromptGeracaoSistema(framework)
	return base + " Nesta execução, você não receberá expaths nem contexto WITUP; derive testes diretamente do código-fonte dos métodos fornecidos. Gere testes que revelem bugs e protejam contra regressões futuras usando apenas o código-fonte fornecido."
}

// construirPromptGeracaoDiretaUsuario monta o prompt de geração direta de testes
// usando apenas o código local e a visão geral do projeto.
func construirPromptGeracaoDiretaUsuario(overview, containerName string, methods []dominio.DescritorMetodo, contextoOpcional ...map[string]interface{}) string {
	conteudoCompactado, _ := json.MarshalIndent(compactarMetodosParaGeracaoDireta(methods), "", "  ")
	contextoComum, _ := json.MarshalIndent(selecionarContextoGeracaoPrompt(contextoOpcional...), "", "  ")
	return fmt.Sprintf(`Gere arquivos de teste Java determinísticos diretamente a partir dos métodos abaixo, sem usar expaths pré-computados.
Return JSON: {"files":[{"relative_path":"...","content":"...","covered_method_ids":[...],"notes":"..."}]}

Regras obrigatórias:
- gere testes que revelem bugs e protejam contra regressões futuras usando apenas o código-fonte fornecido;
- como este cenário não recebe WIT nem expaths, derive casos excepcionais somente quando o comportamento excepcional estiver evidente no código atual;
- use apenas tipos, pacotes, enums, construtores e métodos que apareçam de forma explícita no contexto fornecido;
- não invente exceções, helpers, factories ou contratos não observáveis no código real;
- escreva testes pequenos e concretos, priorizando casos observáveis e compiláveis;
- não introduza Mockito, AssertJ ou outras bibliotecas externas sem evidência explícita de que o projeto já as usa;
- evite imports curingas e referências para pacotes que não apareçam no código real fornecido.
- antes de devolver o JSON final, revise mentalmente cada teste e remova qualquer caso que provavelmente não compile ou não passe no checkout atual;
- só use assertThrows quando o código atual realmente sustentar a exceção; caso contrário, teste o retorno ou efeito observável.
- evite reflexão e acesso a estado interno; se precisar usar reflexão, o membro refletido deve aparecer literalmente no source_code e o notes deve justificar por que não há caminho público;
- se uma chamada por reflexão puder lançar InvocationTargetException, trate InvocationTargetException explicitamente ou valide getCause(), não espere diretamente a exceção interna no assertThrows;
- não faça assertivas sobre campos privados, nomes internos ou estrutura interna instável; prefira retorno público, exceção pública clara ou efeito observável;
- para JUnit, o arquivo gerado deve respeitar o package e o caminho sugeridos no contexto técnico comum;
- para OpenJDK/jtreg: NÃO declare package; use pacote padrão; o cabeçalho jtreg DEVE estar em comentário de bloco /* */ (NUNCA em comentários //), com as diretivas @test, @summary e @run main NomeDaClasse — onde NomeDaClasse é EXATAMENTE o nome da classe pública e o nome base do arquivo sugerido em relative_path (sem .java); a diretiva @modules, quando usada, tem sintaxe '@modules modulo' ou '@modules modulo/pacote' — NUNCA '@modules(modulo)' com parênteses;
- para jtreg no JDK 11+28 (commit da75f3c4): NÃO use records, text blocks ("""), expressões switch com yield ou seta, pattern matching em instanceof, sealed classes, ObjectInputFilter.setSerialFilter() nem qualquer API introduzida após JDK 11; pacotes não exportados por padrão ao unnamed module (sun.security.*, com.sun.crypto.provider, sun.net.*, com.sun.*, jdk.internal.* etc.) SÓ podem ser usados se o cabeçalho jtreg declarar '@modules java.base/nome.do.pacote' — sem essa diretiva a compilação falha com erro de acesso a módulo;
- cada teste deve invocar explicitamente o método-alvo ou um comportamento público que o alcance de forma evidente;
- se o contexto técnico comum não listar Mockito, AssertJ ou outra biblioteca externa, não use essa biblioteca.

Linguagem: Java
Contêiner: %s
Visão geral do projeto:
%s

Contexto técnico comum aos dois cenários:
%s

Métodos-alvo:
%s
`, containerName, reduzirVisaoGeralParaGeracao(overview), string(contextoComum), string(conteudoCompactado))
}

func construirPromptReparoSistema(framework string) string {
	base := construirPromptGeracaoSistema(framework)
	return base + " Você está corrigindo uma suíte de testes já gerada. Faça apenas uma revisão de reparo: preserve os testes já corretos, ajuste os que falharam e devolva arquivos completos prontos para substituir os anteriores. O objetivo principal é que a suíte compile e passe no checkout atual."
}

func construirPromptReparoUsuario(
	overview string,
	analysisReport dominio.RelatorioAnalise,
	generationReport dominio.RelatorioGeracao,
	evaluationReport dominio.RelatorioAvaliacao,
) string {
	analisesCompactadas, _ := json.MarshalIndent(compactarAnalisesParaGeracao(analysisReport.Analises), "", "  ")
	arquivosAtuais, _ := json.MarshalIndent(generationReport.ArquivosTeste, "", "  ")
	falhasMetricas, _ := json.MarshalIndent(compactarFalhasMetricasParaReparo(evaluationReport.ResultadosMetricas), "", "  ")

	return fmt.Sprintf(`Ajuste a suíte de testes Java abaixo com base nos erros da primeira execução.
Return JSON: {"strategy_summary":"...","files":[{"relative_path":"...","content":"...","covered_method_ids":[...],"notes":"..."}]}

Regras obrigatórias:
- esta é a única tentativa de reparo; produza a melhor versão final possível agora;
- preserve arquivos e testes que já parecem corretos, alterando apenas o necessário para compilar e passar no checkout atual;
- use o código atual dos métodos como fonte de verdade;
- se um expath do WIT foi descartado ou contradito pelo checkout atual, não o reintroduza;
- só use assertThrows quando o código atual sustentar claramente a exceção;
- reduza reflexão frágil: remova getDeclaredField/getDeclaredConstructor/getDeclaredMethod/setAccessible se houver caminho público equivalente;
- se mantiver reflexão, valide InvocationTargetException/getCause() corretamente e não espere diretamente a exceção interna;
- substitua assertivas sobre campos privados/estado interno por comportamento público observável sempre que possível;
- quando um teste atual falhar por expectativa incorreta, substitua-o por uma asserção do comportamento observável real;
- mantenha covered_method_ids corretos e devolva o conteúdo completo dos arquivos reparados.

Visão geral do projeto:
%s

Contexto canônico dos métodos-alvo:
%s

Arquivos de teste atuais:
%s

Falhas e sinais da primeira avaliação:
%s
`, reduzirVisaoGeralParaGeracao(overview), string(analisesCompactadas), string(arquivosAtuais), string(falhasMetricas))
}

// construirPromptJuizSistema monta o prompt sistêmico do juiz avaliador.
func construirPromptJuizSistema() string {
	return "Você é um avaliador rigoroso de suítes de teste Java. Responda apenas com JSON válido contendo as chaves score, verdict, strengths, weaknesses, risks e recommended_next_actions. Todos os textos de conteúdo devem estar em português do Brasil, com linguagem técnica clara, objetiva e auditável."
}

// construirPromptJuizUsuario monta o prompt de avaliação final da aplicacao.
func construirPromptJuizUsuario(analysis dominio.RelatorioAnalise, generation dominio.RelatorioGeracao, metricResults []dominio.ResultadoMetrica) string {
	analiseJSON, _ := json.MarshalIndent(analysis, "", "  ")
	geracaoJSON, _ := json.MarshalIndent(generation, "", "  ")
	metricasJSON, _ := json.MarshalIndent(metricResults, "", "  ")
	return fmt.Sprintf(`Avalie a qualidade da aplicacao. Responda em JSON:
{"score":0-100,"verdict":"...","strengths":[...],"weaknesses":[...],"risks":[...],"recommended_next_actions":[...]}

Instruções obrigatórias:
- escreva verdict, strengths, weaknesses, risks e recommended_next_actions em português do Brasil;
- avalie com foco em compilação, estabilidade, aderência aos métodos-alvo, qualidade das assertivas e utilidade científica da suíte;
- seja específico e auditável; evite elogios genéricos;
- se houver falhas de compilação ou execução, destaque isso explicitamente no verdict e nas recommended_next_actions.

Análise:
%s

Geração:
%s

Métricas:
%s
`, string(analiseJSON), string(geracaoJSON), string(metricasJSON))
}

// compactarAnalisesParaGeracao remove campos volumosos que não são necessários
// para a geração de testes e reduz o risco de estouro de tokens.
func compactarAnalisesParaGeracao(analises []dominio.AnaliseMetodo) []map[string]interface{} {
	compartilhado := make([]map[string]interface{}, 0, len(analises))
	for _, analise := range analises {
		caminhos := make([]map[string]interface{}, 0, len(analise.CaminhosExcecao))
		for _, caminho := range analise.CaminhosExcecao {
			caminhos = append(caminhos, map[string]interface{}{
				"path_id":          caminho.IDCaminho,
				"exception_type":   caminho.TipoExcecao,
				"trigger":          caminho.Gatilho,
				"guard_conditions": caminho.CondicoesGuarda,
				"confidence":       caminho.Confianca,
				"evidence":         caminho.Evidencias,
			})
		}

		compartilhado = append(compartilhado, map[string]interface{}{
			"method": map[string]interface{}{
				"method_id":      analise.Metodo.IDMetodo,
				"file_path":      analise.Metodo.CaminhoArquivo,
				"container_name": analise.Metodo.NomeContainer,
				"method_name":    analise.Metodo.NomeMetodo,
				"signature":      analise.Metodo.Assinatura,
				"source_excerpt": extrairCabecalhoMetodo(analise.Metodo.Origem),
				"source_code":    strings.TrimSpace(analise.Metodo.Origem),
			},
			"method_summary":               analise.ResumoMetodo,
			"expaths":                      caminhos,
			"checkout_compatibility_notes": extrairNotasCompatibilidadeCheckout(analise),
		})
	}
	return compartilhado
}

// compactarMetodosParaGeracaoDireta reduz o contexto dos métodos para a
// geração direta de testes, preservando apenas o essencial do código local.
func compactarMetodosParaGeracaoDireta(metodos []dominio.DescritorMetodo) []map[string]interface{} {
	compartilhado := make([]map[string]interface{}, 0, len(metodos))
	for _, metodo := range metodos {
		compartilhado = append(compartilhado, map[string]interface{}{
			"method": map[string]interface{}{
				"method_id":      metodo.IDMetodo,
				"file_path":      metodo.CaminhoArquivo,
				"container_name": metodo.NomeContainer,
				"method_name":    metodo.NomeMetodo,
				"signature":      metodo.Assinatura,
				"source_excerpt": extrairCabecalhoMetodo(metodo.Origem),
				"source_code":    strings.TrimSpace(metodo.Origem),
			},
		})
	}
	return compartilhado
}

func selecionarContextoGeracaoPrompt(contextos ...map[string]interface{}) map[string]interface{} {
	if len(contextos) == 0 || contextos[0] == nil {
		return map[string]interface{}{}
	}
	return contextos[0]
}

// extrairCabecalhoMetodo devolve apenas a primeira linha útil do método para
// reduzir o tamanho do prompt de geração.
func extrairCabecalhoMetodo(origem string) string {
	origem = strings.TrimSpace(origem)
	if origem == "" {
		return ""
	}
	linhas := strings.Split(origem, "\n")
	cabecalho := strings.TrimSpace(linhas[0])
	if len(cabecalho) > 240 {
		return cabecalho[:240] + "..."
	}
	return cabecalho
}

func extrairNotasCompatibilidadeCheckout(analise dominio.AnaliseMetodo) []string {
	if analise.RespostaBruta == nil {
		return nil
	}
	brutas, ok := analise.RespostaBruta["discarded_expaths_due_to_checkout"].([]string)
	if ok && len(brutas) > 0 {
		notas := make([]string, 0, len(brutas))
		for _, item := range brutas {
			item = strings.TrimSpace(item)
			if item == "" {
				continue
			}
			notas = append(notas, "Expath descartado por incompatibilidade com o checkout atual: "+item)
		}
		return notas
	}
	rawInterfaces, ok := analise.RespostaBruta["discarded_expaths_due_to_checkout"].([]interface{})
	if !ok || len(rawInterfaces) == 0 {
		return nil
	}
	notas := make([]string, 0, len(rawInterfaces))
	for _, item := range rawInterfaces {
		valor := strings.TrimSpace(fmt.Sprint(item))
		if valor == "" || valor == "<nil>" {
			continue
		}
		notas = append(notas, "Expath descartado por incompatibilidade com o checkout atual: "+valor)
	}
	return notas
}

func compactarFalhasMetricasParaReparo(resultados []dominio.ResultadoMetrica) []map[string]interface{} {
	falhas := make([]map[string]interface{}, 0)
	for _, resultado := range resultados {
		if resultado.Sucesso {
			continue
		}
		if !ehMetricaCriticaParaReparo(resultado.Nome) {
			continue
		}
		falhas = append(falhas, map[string]interface{}{
			"name":    resultado.Nome,
			"stdout":  truncarTextoPrompt(resultado.SaidaPadrao, 1800),
			"stderr":  truncarTextoPrompt(resultado.SaidaErro, 1800),
			"command": resultado.Comando,
		})
	}
	return falhas
}

func ehMetricaCriticaParaReparo(nome string) bool {
	switch strings.TrimSpace(nome) {
	case "test-compilation", "unit-tests", "test-pass-rate":
		return true
	default:
		return false
	}
}

func truncarTextoPrompt(texto string, limite int) string {
	texto = strings.TrimSpace(texto)
	if texto == "" || limite <= 0 {
		return texto
	}
	if len(texto) <= limite {
		return texto
	}
	return texto[:limite] + "\n...[truncado]..."
}

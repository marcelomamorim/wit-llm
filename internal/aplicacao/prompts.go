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
		return "Você é um especialista em testes Java para o OpenJDK usando jtreg. Responda SOMENTE com JSON válido.\n\n" +
			"════════════════════════════════════════════════\n" +
			"ESTRUTURA OBRIGATÓRIA DE CADA ARQUIVO GERADO\n" +
			"════════════════════════════════════════════════\n" +
			"O arquivo Java DEVE ter EXATAMENTE este formato — sem exceções:\n\n" +
			"/* @test\n" +
			" * @summary Brief description in English\n" +
			" * @run main ExactClassName\n" +
			" */\n" +
			"import java.io.IOException;\n\n" +
			"public class ExactClassName {\n" +
			"    public static void main(String[] args) throws Exception {\n" +
			"        // test logic here\n" +
			"    }\n" +
			"}\n\n" +
			"EXEMPLO REAL COMPLETO E CORRETO (use este como modelo):\n" +
			"/* @test\n" +
			" * @summary Tests ZonedDateTime.withZoneSameLocal adjusts zone correctly\n" +
			" * @run main ZonedDateTimeWitupTest\n" +
			" */\n" +
			"import java.time.ZoneId;\n" +
			"import java.time.ZonedDateTime;\n\n" +
			"public class ZonedDateTimeWitupTest {\n" +
			"    public static void main(String[] args) {\n" +
			"        testSameZone();\n" +
			"        testNullZone();\n" +
			"    }\n\n" +
			"    static void testSameZone() {\n" +
			"        ZonedDateTime zdt = ZonedDateTime.of(2023, 3, 15, 10, 30, 0, 0, ZoneId.of(\"America/New_York\"));\n" +
			"        ZonedDateTime result = zdt.withZoneSameLocal(ZoneId.of(\"Europe/Paris\"));\n" +
			"        if (!result.toLocalDateTime().equals(zdt.toLocalDateTime())) {\n" +
			"            throw new AssertionError(\"Local date-time should remain the same\");\n" +
			"        }\n" +
			"        System.out.println(\"PASS testSameZone\");\n" +
			"    }\n\n" +
			"    static void testNullZone() {\n" +
			"        ZonedDateTime zdt = ZonedDateTime.of(2023, 3, 15, 10, 30, 0, 0, ZoneId.of(\"America/New_York\"));\n" +
			"        try {\n" +
			"            zdt.withZoneSameLocal(null);\n" +
			"            throw new AssertionError(\"Expected NullPointerException\");\n" +
			"        } catch (NullPointerException expected) {\n" +
			"            System.out.println(\"PASS testNullZone\");\n" +
			"        }\n" +
			"    }\n" +
			"}\n\n" +
			"EXEMPLO COM @modules PARA PACOTE NÃO EXPORTADO (sun.security.*):\n" +
			"/* @test\n" +
			" * @summary Tests ObjectIdentifier rejects null OID\n" +
			" * @modules java.base/sun.security.util\n" +
			" * @run main ObjectIdentifierWitupTest\n" +
			" */\n" +
			"import sun.security.util.ObjectIdentifier;\n\n" +
			"public class ObjectIdentifierWitupTest {\n" +
			"    public static void main(String[] args) throws Exception {\n" +
			"        try {\n" +
			"            new ObjectIdentifier((int[]) null);\n" +
			"            throw new AssertionError(\"Expected exception for null OID\");\n" +
			"        } catch (NullPointerException | IllegalArgumentException expected) {\n" +
			"            System.out.println(\"PASS\");\n" +
			"        }\n" +
			"    }\n" +
			"}\n\n" +
			"════════════════════════════════════════════════\n" +
			"EXEMPLOS PROIBIDOS — NUNCA GERE ASSIM\n" +
			"════════════════════════════════════════════════\n" +
			"ERRADO 1 — @test fora de bloco /* */:\n" +
			"@test @summary foo @run main Foo   ← jtreg NÃO reconhece como teste\n\n" +
			"ERRADO 2 — usando // para o cabeçalho:\n" +
			"// @test\n" +
			"// @run main Foo                   ← jtreg NÃO reconhece como teste\n\n" +
			"ERRADO 3 — nome qualificado em @run main:\n" +
			"/* @run main com.sun.FooTest */    ← ERRADO: deve ser só 'FooTest'\n\n" +
			"ERRADO 4 — parênteses em @modules:\n" +
			"/* @modules(java.base) */          ← ERRADO: causa erro de parse\n\n" +
			"ERRADO 5 — @run main com nome diferente da classe:\n" +
			"/* @run main FooHelper */\n" +
			"public class FooWitupTest { ... }  ← ERRADO: nomes devem ser iguais\n\n" +
			"════════════════════════════════════════════════\n" +
			"REGRAS ABSOLUTAS\n" +
			"════════════════════════════════════════════════\n" +
			"R1. CABEÇALHO /* @test */: o arquivo DEVE conter um bloco /* @test ... */ com as diretivas jtreg. Sem esse bloco, o jtreg ignora o arquivo completamente e o teste nunca executa.\n" +
			"R2. @run main NomeDaClasse: NomeDaClasse é o nome SIMPLES (não qualificado) da classe pública. DEVE ser IDÊNTICO ao nome da classe pública E ao basename de relative_path (sem .java). Ex: relative_path='java/io/FooWitupTest.java' → @run main FooWitupTest → public class FooWitupTest.\n" +
			"R3. NOME DA CLASSE = NOME DO ARQUIVO: a classe pública DEVE ter exatamente o mesmo nome que o arquivo sugerido em relative_path. NUNCA use outro nome (ex: se o arquivo é FooWitupTest.java, a classe pública é FooWitupTest, não FooHelper, não FooTest, não Foo).\n" +
			"R4. SEM @Test annotations: NUNCA importe ou use @Test, @Before, @After, @BeforeEach de JUnit, TestNG ou qualquer framework. Todo o fluxo de teste passa pelo método main(). Chame métodos auxiliares estáticos diretamente de main().\n" +
			"R5. SEM classes externas: NUNCA referencie no @run ou no código uma classe que não seja definida no próprio arquivo. O arquivo deve ser autossuficiente — uma única public class com inner classes estáticas se necessário.\n" +
			"R6. @modules sintaxe: '@modules java.base/sun.security.util' (módulo/pacote). NUNCA '@modules(java.base)'. Para pacotes exportados (java.util, java.io, java.time etc.) não é necessário @modules.\n" +
			"R7. Pacotes não exportados: sun.security.*, com.sun.crypto.provider, sun.net.*, com.sun.*, jdk.internal.* EXIGEM '@modules java.base/nome.do.pacote'. Sem isso a compilação falha.\n" +
			"R8. NÃO declare package. Use pacote padrão (unnamed package).\n" +
			"R9. JDK 11+28 APENAS: PROIBIDO records, text blocks (\"\"\"), switch yield/seta (->), instanceof binding, sealed classes, ObjectInputFilter.setSerialFilter().\n" +
			"R10. Checagem obrigatória antes de gerar o JSON: (1) arquivo tem /* @test */? (2) @run main tem nome idêntico à classe pública? (3) nome da classe = basename do arquivo? (4) nenhum @Test annotation? (5) nenhuma classe externa referenciada? (6) compila com JDK 11? Se qualquer resposta for não, corrija primeiro."
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
- FORMATO JTREG OBRIGATÓRIO: cada arquivo DEVE começar com /* @test\n * @summary ...\n * @run main NomeDaClasse\n */  — o jtreg ignora completamente qualquer arquivo sem esse bloco /* */. NUNCA coloque @test fora de bloco /* */. NUNCA use // para o cabeçalho.
- @run main NomeDaClasse: NomeDaClasse é o nome SIMPLES (não qualificado) da classe pública, IDÊNTICO ao basename de relative_path sem .java. Ex: se relative_path='java/time/FooWitupTest.java' então @run main FooWitupTest e public class FooWitupTest.
- NÃO declare package. Sem JUnit, TestNG, Mockito — testes standalone com main() apenas.
- @modules sintaxe CORRETA: '@modules java.base/sun.security.util'. ERRADO: '@modules(java.base)'.
- JDK 11+28 (commit da75f3c4) SOMENTE: proibido records, text blocks, switch yield/seta, instanceof binding, sealed classes.
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
- FORMATO JTREG OBRIGATÓRIO: cada arquivo DEVE começar com /* @test\n * @summary ...\n * @run main NomeDaClasse\n */  — o jtreg ignora completamente qualquer arquivo sem esse bloco /* */. NUNCA coloque @test fora de bloco /* */. NUNCA use // para o cabeçalho.
- @run main NomeDaClasse: NomeDaClasse é o nome SIMPLES (não qualificado) da classe pública, IDÊNTICO ao basename de relative_path sem .java. Ex: se relative_path='java/time/FooWitupTest.java' então @run main FooWitupTest e public class FooWitupTest.
- NÃO declare package. Sem JUnit, TestNG, Mockito — testes standalone com main() apenas.
- @modules sintaxe CORRETA: '@modules java.base/sun.security.util'. ERRADO: '@modules(java.base)'.
- JDK 11+28 (commit da75f3c4) SOMENTE: proibido records, text blocks, switch yield/seta, instanceof binding, sealed classes.
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

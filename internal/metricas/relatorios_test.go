package metricas

import (
	"math"
	"path/filepath"
	"testing"

	"github.com/marceloamorim/witup-llm/internal/artefatos"
	"github.com/marceloamorim/witup-llm/internal/dominio"
)

func TestExtrairCoberturaJaCoCo(t *testing.T) {
	tempDir := t.TempDir()
	caminho := filepath.Join(tempDir, "jacoco.xml")
	xml := `<report><counter type="LINE" missed="20" covered="80"/><counter type="BRANCH" missed="10" covered="30"/></report>`
	if err := artefatos.EscreverTexto(caminho, xml); err != nil {
		t.Fatalf("escrever fixture jacoco: %v", err)
	}

	valor, err := ExtrairCoberturaJaCoCo(caminho, "LINE")
	if err != nil {
		t.Fatalf("extrair cobertura jacoco: %v", err)
	}
	if valor != 80 {
		t.Fatalf("esperava cobertura 80, recebi %.2f", valor)
	}
}

// TestExtrairCoberturaJaCoCoFallbackPackage cobre o cenário em que o XML não
// possui <counter> diretos na raiz (ex: exec vazio/truncado por OOM — commons-io
// tem <argLine>-Xmx25M</argLine> hardcoded). O fallback deve agregar os counters
// dos elementos <package>.
func TestExtrairCoberturaJaCoCoFallbackPackage(t *testing.T) {
	tempDir := t.TempDir()
	caminho := filepath.Join(tempDir, "jacoco.xml")
	// XML sem counter na raiz — apenas dentro de <package>
	xmlSemRaiz := `<report name="commons-io">` +
		`<package name="org/apache/commons/io">` +
		`<counter type="LINE" missed="20" covered="80"/>` +
		`<counter type="BRANCH" missed="10" covered="30"/>` +
		`</package>` +
		`<package name="org/apache/commons/io/input">` +
		`<counter type="LINE" missed="5" covered="15"/>` +
		`</package>` +
		`</report>`
	if err := artefatos.EscreverTexto(caminho, xmlSemRaiz); err != nil {
		t.Fatalf("escrever fixture: %v", err)
	}

	// LINE: coberto=80+15=95, perdido=20+5=25 → 95/120 ≈ 79.17%
	valor, err := ExtrairCoberturaJaCoCo(caminho, "LINE")
	if err != nil {
		t.Fatalf("fallback package LINE: %v", err)
	}
	esperado := 95.0 / 120.0 * 100.0
	if math.Abs(valor-esperado) > 0.01 {
		t.Fatalf("esperava %.2f, recebi %.2f", esperado, valor)
	}

	// BRANCH: coberto=30, perdido=10 → 75%
	valorB, err := ExtrairCoberturaJaCoCo(caminho, "BRANCH")
	if err != nil {
		t.Fatalf("fallback package BRANCH: %v", err)
	}
	if math.Abs(valorB-75.0) > 0.01 {
		t.Fatalf("esperava 75.00, recebi %.2f", valorB)
	}
}

// TestExtrairCoberturaJaCoCoEncodingISO8859 cobre o caso real do commons-io:
// o JaCoCo gera o XML com encoding="iso-8859-1", que o encoding/xml do Go
// rejeita. O normalizarEncodingXML deve reescrever para UTF-8 antes do parse.
func TestExtrairCoberturaJaCoCoEncodingISO8859(t *testing.T) {
	tempDir := t.TempDir()
	caminho := filepath.Join(tempDir, "jacoco.xml")
	// Simula exatamente o prólogo que o JaCoCo maven plugin gera
	xmlISO := `<?xml version="1.0" encoding="iso-8859-1" standalone="yes"?>` +
		`<!DOCTYPE report PUBLIC "-//JACOCO//DTD Report 1.1//EN" "report.dtd">` +
		`<report name="commons-io">` +
		`<counter type="LINE" missed="40" covered="60"/>` +
		`<counter type="BRANCH" missed="20" covered="30"/>` +
		`</report>`
	if err := artefatos.EscreverTexto(caminho, xmlISO); err != nil {
		t.Fatalf("escrever fixture: %v", err)
	}

	// LINE: 60/(60+40) = 60%
	valor, err := ExtrairCoberturaJaCoCo(caminho, "LINE")
	if err != nil {
		t.Fatalf("encoding iso-8859-1 LINE: %v", err)
	}
	if math.Abs(valor-60.0) > 0.01 {
		t.Fatalf("esperava 60.00, recebi %.2f", valor)
	}
}

func TestExtrairMutacaoPIT(t *testing.T) {
	tempDir := t.TempDir()
	caminho := filepath.Join(tempDir, "target", "pit-reports", "mutations.xml")
	xml := `<mutations><mutation detected="true" status="KILLED"></mutation><mutation detected="false" status="SURVIVED"></mutation></mutations>`
	if err := artefatos.EscreverTexto(caminho, xml); err != nil {
		t.Fatalf("escrever fixture pit: %v", err)
	}

	valor, encontrado, err := ExtrairMutacaoPIT(filepath.Join(tempDir, "target", "pit-reports"))
	if err != nil {
		t.Fatalf("extrair mutação PIT: %v", err)
	}
	if encontrado != caminho {
		t.Fatalf("esperava caminho %q, recebi %q", caminho, encontrado)
	}
	if valor != 50 {
		t.Fatalf("esperava mutation score 50, recebi %.2f", valor)
	}
}

func TestCalcularReproducaoExcecoes(t *testing.T) {
	tempDir := t.TempDir()
	caminhoAnalise := filepath.Join(tempDir, "analysis.json")
	caminhoGeracao := filepath.Join(tempDir, "generation.json")

	analise := dominio.RelatorioAnalise{
		Analises: []dominio.AnaliseMetodo{{
			Metodo: dominio.DescritorMetodo{IDMetodo: "sample.Example.run(String name):10"},
			CaminhosExcecao: []dominio.CaminhoExcecao{{
				IDCaminho:   "e1",
				TipoExcecao: "NullPointerException",
			}},
		}},
	}
	geracao := dominio.RelatorioGeracao{
		ArquivosTeste: []dominio.ArquivoTesteGerado{{
			CaminhoRelativo:    "src/test/java/sample/ExampleTest.java",
			Conteudo:           "assertThrows(NullPointerException.class, () -> subject.run(null));",
			IDsMetodosCobertos: []string{"sample.Example.run(String name):10"},
		}},
	}

	if err := artefatos.EscreverJSON(caminhoAnalise, analise); err != nil {
		t.Fatalf("escrever análise: %v", err)
	}
	if err := artefatos.EscreverJSON(caminhoGeracao, geracao); err != nil {
		t.Fatalf("escrever geração: %v", err)
	}

	valor, err := CalcularReproducaoExcecoes(caminhoAnalise, caminhoGeracao)
	if err != nil {
		t.Fatalf("calcular reprodução de exceções: %v", err)
	}
	if valor != 100 {
		t.Fatalf("esperava reprodução 100, recebi %.2f", valor)
	}
}

func TestExtrairCoberturaJaCoCoLidaComCounterAusenteEZero(t *testing.T) {
	tempDir := t.TempDir()
	caminho := filepath.Join(tempDir, "jacoco.xml")
	xml := `<report><counter type="LINE" missed="0" covered="0"/></report>`
	if err := artefatos.EscreverTexto(caminho, xml); err != nil {
		t.Fatalf("fixture jacoco: %v", err)
	}
	valor, err := ExtrairCoberturaJaCoCo(caminho, "line")
	if err != nil || valor != 0 {
		t.Fatalf("esperava cobertura zero sem erro, recebi valor=%.2f err=%v", valor, err)
	}
	if _, err := ExtrairCoberturaJaCoCo(caminho, "BRANCH"); err == nil {
		t.Fatalf("esperava erro para contador ausente")
	}
}

func TestExtrairMutacaoPITConsideraOutrosStatusDetectados(t *testing.T) {
	tempDir := t.TempDir()
	caminho := filepath.Join(tempDir, "pit", "mutations.xml")
	xml := `<mutations>
	  <mutation detected="false" status="TIMED_OUT"></mutation>
	  <mutation detected="false" status="MEMORY_ERROR"></mutation>
	  <mutation detected="false" status="SURVIVED"></mutation>
	</mutations>`
	if err := artefatos.EscreverTexto(caminho, xml); err != nil {
		t.Fatalf("fixture pit: %v", err)
	}
	valor, _, err := ExtrairMutacaoPIT(filepath.Join(tempDir, "pit"))
	if err != nil {
		t.Fatalf("extrair mutação pit: %v", err)
	}
	esperado := (2.0 / 3.0) * 100.0
	if math.Abs(valor-esperado) > 0.0001 {
		t.Fatalf("mutation score inesperado: %.2f", valor)
	}
}

func TestExtrairTestesExecutadosSurefire(t *testing.T) {
	tempDir := t.TempDir()
	relatoriosDir := filepath.Join(tempDir, "target", "surefire-reports")
	xmlA := `<testsuite tests="3"></testsuite>`
	xmlB := `<testsuite tests="5"></testsuite>`
	if err := artefatos.EscreverTexto(filepath.Join(relatoriosDir, "TEST-a.xml"), xmlA); err != nil {
		t.Fatalf("fixture surefire A: %v", err)
	}
	if err := artefatos.EscreverTexto(filepath.Join(relatoriosDir, "TEST-b.xml"), xmlB); err != nil {
		t.Fatalf("fixture surefire B: %v", err)
	}

	valor, err := ExtrairTestesExecutadosSurefire(relatoriosDir)
	if err != nil {
		t.Fatalf("extrair testes executados surefire: %v", err)
	}
	if valor != 8 {
		t.Fatalf("esperava 8 testes executados, recebi %.2f", valor)
	}
}

func TestCalcularReproducaoExcecoesUsaFallbackDeArquivosEClasseSimples(t *testing.T) {
	tempDir := t.TempDir()
	caminhoAnalise := filepath.Join(tempDir, "analysis.json")
	caminhoGeracao := filepath.Join(tempDir, "generation.json")
	analise := dominio.RelatorioAnalise{Analises: []dominio.AnaliseMetodo{{
		Metodo:          dominio.DescritorMetodo{IDMetodo: "m1"},
		CaminhosExcecao: []dominio.CaminhoExcecao{{TipoExcecao: "java.lang.IllegalStateException"}},
	}}}
	geracao := dominio.RelatorioGeracao{ArquivosTeste: []dominio.ArquivoTesteGerado{{Conteudo: "assertThrows(IllegalStateException.class, () -> x());"}}}
	if err := artefatos.EscreverJSON(caminhoAnalise, analise); err != nil {
		t.Fatalf("fixture analysis: %v", err)
	}
	if err := artefatos.EscreverJSON(caminhoGeracao, geracao); err != nil {
		t.Fatalf("fixture generation: %v", err)
	}
	valor, err := CalcularReproducaoExcecoes(caminhoAnalise, caminhoGeracao)
	if err != nil {
		t.Fatalf("reprodução de exceções: %v", err)
	}
	if valor != 100 {
		t.Fatalf("esperava 100 de reprodução, recebi %.2f", valor)
	}
}

func TestExtrairEstatisticasSurefireEPassRate(t *testing.T) {
	tempDir := t.TempDir()
	relatoriosDir := filepath.Join(tempDir, "target", "surefire-reports")
	xmlA := `<testsuite tests="4" failures="1" errors="1" skipped="1"></testsuite>`
	xmlB := `<testsuite tests="2" failures="0" errors="0" skipped="0"></testsuite>`
	if err := artefatos.EscreverTexto(filepath.Join(relatoriosDir, "TEST-a.xml"), xmlA); err != nil {
		t.Fatalf("fixture surefire A: %v", err)
	}
	if err := artefatos.EscreverTexto(filepath.Join(relatoriosDir, "TEST-b.xml"), xmlB); err != nil {
		t.Fatalf("fixture surefire B: %v", err)
	}

	estatisticas, err := ExtrairEstatisticasSurefire(relatoriosDir)
	if err != nil {
		t.Fatalf("extrair estatísticas surefire: %v", err)
	}
	if estatisticas.Tests != 6 || estatisticas.Failures != 1 || estatisticas.Errors != 1 || estatisticas.Skipped != 1 {
		t.Fatalf("estatísticas inesperadas: %#v", estatisticas)
	}
	if estatisticas.Aprovados() != 4 {
		t.Fatalf("aprovados inesperados: %d", estatisticas.Aprovados())
	}
	if math.Abs(estatisticas.TaxaSucesso()-66.6666667) > 0.001 {
		t.Fatalf("taxa de sucesso inesperada: %.4f", estatisticas.TaxaSucesso())
	}
}

func TestExtrairEstatisticasGeracao(t *testing.T) {
	tempDir := t.TempDir()
	analysisPath := filepath.Join(tempDir, "analysis.json")
	generationPath := filepath.Join(tempDir, "generation.json")
	analise := dominio.RelatorioAnalise{Analises: []dominio.AnaliseMetodo{
		{Metodo: dominio.DescritorMetodo{IDMetodo: "m1"}},
		{Metodo: dominio.DescritorMetodo{IDMetodo: "m2"}},
	}}
	geracao := dominio.RelatorioGeracao{ArquivosTeste: []dominio.ArquivoTesteGerado{{
		Conteudo: `
import org.junit.jupiter.api.Test;
class ExampleTest {
    @Test
    void cobreMetodo() {
        assertEquals(1, 1);
    }

    @Test
    void cobreExcecao() {
        assertThrows(IllegalArgumentException.class, () -> foo());
    }
}`,
		IDsMetodosCobertos: []string{"m1"},
	}}}
	if err := artefatos.EscreverJSON(analysisPath, analise); err != nil {
		t.Fatalf("fixture analysis: %v", err)
	}
	if err := artefatos.EscreverJSON(generationPath, geracao); err != nil {
		t.Fatalf("fixture generation: %v", err)
	}

	estatisticas, err := ExtrairEstatisticasGeracao(analysisPath, generationPath)
	if err != nil {
		t.Fatalf("extrair estatísticas de geração: %v", err)
	}
	if estatisticas.MetodosTeste != 2 || estatisticas.MetodosComAssertiva != 2 || estatisticas.MetodosComAssertivaExn != 1 {
		t.Fatalf("estatísticas de geração inesperadas: %#v", estatisticas)
	}
	if estatisticas.MetodosAlvo != 2 || estatisticas.MetodosAlvoCobertos != 1 {
		t.Fatalf("cobertura de métodos-alvo inesperada: %#v", estatisticas)
	}
	if estatisticas.TaxaMetodosAlvoCobertos() != 50 {
		t.Fatalf("taxa de cobertura de métodos-alvo inesperada: %.2f", estatisticas.TaxaMetodosAlvoCobertos())
	}
	if estatisticas.TaxaTestesAssertivos() != 100 {
		t.Fatalf("taxa de testes assertivos inesperada: %.2f", estatisticas.TaxaTestesAssertivos())
	}
	if estatisticas.TaxaTestesExcecao() != 50 {
		t.Fatalf("taxa de testes de exceção inesperada: %.2f", estatisticas.TaxaTestesExcecao())
	}
}

func TestExtrairEstatisticasGeracaoComDiagnosticosEDependencias(t *testing.T) {
	tempDir := t.TempDir()
	projectRoot := filepath.Join(tempDir, "project")
	if err := artefatos.EscreverTexto(filepath.Join(projectRoot, "pom.xml"), `<project><dependencies></dependencies></project>`); err != nil {
		t.Fatalf("fixture pom: %v", err)
	}
	analysisPath := filepath.Join(tempDir, "analysis.json")
	generationPath := filepath.Join(tempDir, "generation.json")
	analise := dominio.RelatorioAnalise{Analises: []dominio.AnaliseMetodo{{
		Metodo: dominio.DescritorMetodo{
			IDMetodo:      "m1",
			NomeContainer: "sample.Example",
			NomeMetodo:    "run",
		},
	}}}
	geracao := dominio.RelatorioGeracao{ArquivosTeste: []dominio.ArquivoTesteGerado{
		{
			CaminhoRelativo: "src/test/java/sample/ExampleTest.java",
			Conteudo: `package sample;
import org.junit.jupiter.api.Test;
class ExampleTest {
    @Test
    void callsTarget() {
        new Example().run();
        org.junit.jupiter.api.Assertions.assertEquals(1, 1);
    }
}`,
			IDsMetodosCobertos: []string{"m1"},
		},
		{
			CaminhoRelativo:    "src/test/java/wrong/BadTest.java",
			Conteudo:           "```html\n<html>org.mockito.Mockito.mock(Object.class)</html>\n```",
			IDsMetodosCobertos: []string{"m1"},
		},
	}}
	if err := artefatos.EscreverJSON(analysisPath, analise); err != nil {
		t.Fatalf("fixture analysis: %v", err)
	}
	if err := artefatos.EscreverJSON(generationPath, geracao); err != nil {
		t.Fatalf("fixture generation: %v", err)
	}

	estatisticas, err := ExtrairEstatisticasGeracaoComProjeto(analysisPath, generationPath, projectRoot)
	if err != nil {
		t.Fatalf("extrair estatísticas de geração: %v", err)
	}
	if estatisticas.TaxaArquivosJavaValidos() != 50 {
		t.Fatalf("taxa Java válido inesperada: %.2f", estatisticas.TaxaArquivosJavaValidos())
	}
	if estatisticas.TaxaPacotesValidos() != 50 {
		t.Fatalf("taxa package/caminho inesperada: %.2f", estatisticas.TaxaPacotesValidos())
	}
	if estatisticas.TaxaArquivosComMetodoTeste() != 50 {
		t.Fatalf("taxa @Test inesperada: %.2f", estatisticas.TaxaArquivosComMetodoTeste())
	}
	if estatisticas.TaxaMetodosAlvoInvocados() != 100 {
		t.Fatalf("taxa invocação alvo inesperada: %.2f", estatisticas.TaxaMetodosAlvoInvocados())
	}
	if estatisticas.TaxaDependenciasProibidas() != 50 {
		t.Fatalf("taxa dependências proibidas inesperada: %.2f", estatisticas.TaxaDependenciasProibidas())
	}
}

func TestExtrairEstatisticasGeracaoDetectaFragilidadePorReflexao(t *testing.T) {
	tempDir := t.TempDir()
	analysisPath := filepath.Join(tempDir, "analysis.json")
	generationPath := filepath.Join(tempDir, "generation.json")
	analise := dominio.RelatorioAnalise{Analises: []dominio.AnaliseMetodo{{
		Metodo: dominio.DescritorMetodo{
			IDMetodo:      "m1",
			NomeContainer: "sample.Example",
			NomeMetodo:    "run",
		},
	}}}
	geracao := dominio.RelatorioGeracao{ArquivosTeste: []dominio.ArquivoTesteGerado{
		{
			CaminhoRelativo: "src/test/java/sample/ExampleTest.java",
			Conteudo: `package sample;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
class ExampleTest {
    @Test
    void publicBehavior() {
        new Example().run();
        assertEquals(1, 1);
    }
}`,
			IDsMetodosCobertos: []string{"m1"},
		},
		{
			CaminhoRelativo: "src/test/java/sample/ExampleInternalStateTest.java",
			Conteudo: `package sample;
import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
class ExampleInternalStateTest {
    @Test
    void inspectsPrivateField() throws Exception {
        Field field = Example.class.getDeclaredField("explicitName");
        field.setAccessible(true);
        assertEquals("x", field.get(new Example()));
    }
}`,
			IDsMetodosCobertos: []string{"m1"},
		},
		{
			CaminhoRelativo: "src/test/java/sample/ExampleBrittleExceptionTest.java",
			Conteudo: `package sample;
import java.lang.reflect.Constructor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
class ExampleBrittleExceptionTest {
    @Test
    void expectsInnerExceptionDirectly() throws Exception {
        Constructor<Example> ctor = Example.class.getDeclaredConstructor(String.class);
        ctor.setAccessible(true);
        assertThrows(IllegalArgumentException.class, () -> ctor.newInstance("x"));
    }
}`,
			IDsMetodosCobertos: []string{"m1"},
		},
	}}
	if err := artefatos.EscreverJSON(analysisPath, analise); err != nil {
		t.Fatalf("fixture analysis: %v", err)
	}
	if err := artefatos.EscreverJSON(generationPath, geracao); err != nil {
		t.Fatalf("fixture generation: %v", err)
	}

	estatisticas, err := ExtrairEstatisticasGeracao(analysisPath, generationPath)
	if err != nil {
		t.Fatalf("extrair estatísticas de geração: %v", err)
	}
	if math.Abs(estatisticas.TaxaUsoReflexao()-66.6666667) > 0.001 {
		t.Fatalf("taxa de reflexão frágil inesperada: %.4f", estatisticas.TaxaUsoReflexao())
	}
	if math.Abs(estatisticas.TaxaAssertThrowsFragil()-33.3333333) > 0.001 {
		t.Fatalf("taxa de assertThrows frágil inesperada: %.4f", estatisticas.TaxaAssertThrowsFragil())
	}
	if math.Abs(estatisticas.TaxaAssertEstadoInterno()-33.3333333) > 0.001 {
		t.Fatalf("taxa de assert estado interno inesperada: %.4f", estatisticas.TaxaAssertEstadoInterno())
	}
}

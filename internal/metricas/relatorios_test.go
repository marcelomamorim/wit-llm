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

package aplicacao

import (
	"os"
	"path/filepath"
	"strings"
	"testing"

	"github.com/marceloamorim/witup-llm/internal/artefatos"
	"github.com/marceloamorim/witup-llm/internal/dominio"
)

func TestExportarCSVsSegundaFase(t *testing.T) {
	workspace, err := artefatos.NovoEspacoTrabalho(t.TempDir(), "phase-two-test")
	if err != nil {
		t.Fatalf("workspace: %v", err)
	}

	relatorio := dominio.RelatorioSegundaFase{
		IDExecucao:         "phase-two-test",
		ChaveModeloGeracao: "openai_main",
		Projetos: []dominio.ComparacaoProjetoSegundaFase{{
			Projeto:       "guava",
			RotuloProjeto: "Google Guava",
			ContextoWIT: dominio.ResultadoCenarioSegundaFase{
				Projeto:           "guava",
				Cenario:           dominio.CenarioSegundaFaseContextoWIT,
				ModoExecucao:      dominio.ModoExecucaoSegundaFaseReparo,
				RequestCount:      2,
				RepairUsed:        true,
				InputTokens:       120,
				OutputTokens:      45,
				EstimatedCost:     ponteiroFloatAplicacao(0.01),
				QuantidadeMetodos: 10,
				QuantidadeExpaths: 20,
				QuantidadeTestes:  5,
				NotaMetricas:      ponteiroFloatAplicacao(70),
				ResultadosMetricas: []dominio.ResultadoMetrica{
					{Nome: "test-compilation", Sucesso: true, CodigoSaida: 0, ValorNumerico: ponteiroFloatAplicacao(100), NotaNormalizada: ponteiroFloatAplicacao(100)},
					{Nome: "valid-java-rate", Sucesso: true, CodigoSaida: 0, NotaNormalizada: ponteiroFloatAplicacao(100)},
					{Nome: "jacoco-line", Sucesso: true, CodigoSaida: 0, NotaNormalizada: ponteiroFloatAplicacao(70)},
				},
				IntervencoesHarness: []string{"sandbox_sanitized_pom"},
			},
			GeracaoDireta: dominio.ResultadoCenarioSegundaFase{
				Projeto:           "guava",
				Cenario:           dominio.CenarioSegundaFaseDireto,
				ModoExecucao:      dominio.ModoExecucaoSegundaFaseEstrito,
				RequestCount:      1,
				RepairUsed:        false,
				InputTokens:       80,
				OutputTokens:      30,
				EstimatedCost:     ponteiroFloatAplicacao(0.00),
				QuantidadeMetodos: 10,
				QuantidadeTestes:  4,
				NotaMetricas:      ponteiroFloatAplicacao(55),
				ResultadosMetricas: []dominio.ResultadoMetrica{
					{Nome: "test-compilation", Sucesso: false, CodigoSaida: 1, ValorNumerico: ponteiroFloatAplicacao(0), NotaNormalizada: ponteiroFloatAplicacao(0), SaidaErro: "cannot find symbol"},
					{Nome: "valid-java-rate", Sucesso: true, CodigoSaida: 0, NotaNormalizada: ponteiroFloatAplicacao(50)},
					{Nome: "jacoco-line", Sucesso: true, CodigoSaida: 0, NotaNormalizada: ponteiroFloatAplicacao(55)},
				},
			},
			DirecaoDelta:      "WIT_CONTEXT_MINUS_DIRECT_TESTS",
			DeltaNotaMetricas: ponteiroFloatAplicacao(15),
		}},
	}

	resumo, metricasCSV, comparacao, err := exportarCSVsSegundaFase(workspace, relatorio)
	if err != nil {
		t.Fatalf("exportação: %v", err)
	}
	for _, caminho := range []string{resumo, metricasCSV, comparacao} {
		conteudo, err := os.ReadFile(caminho)
		if err != nil {
			t.Fatalf("ler CSV %s: %v", caminho, err)
		}
		if !strings.Contains(string(conteudo), "guava") {
			t.Fatalf("CSV %s deveria conter o projeto guava", caminho)
		}
	}
	resumoConteudo, err := os.ReadFile(resumo)
	if err != nil {
		t.Fatalf("ler summary csv: %v", err)
	}
	if !strings.Contains(string(resumoConteudo), "100.00") {
		t.Fatalf("summary csv deveria materializar test-compilation como 100/0: %s", string(resumoConteudo))
	}
	for _, trecho := range []string{"request_count", "repair_used", "input_tokens", "output_tokens", "estimated_cost", "valid_java_rate", "reflection_usage_rate", "brittle_exception_assertion_rate", "internal_state_assertion_rate", "compile_failure_category", "harness_interventions", "sandbox_sanitized_pom", "unknown_symbol", "repair_1retry", "strict_1call"} {
		if !strings.Contains(string(resumoConteudo), trecho) {
			t.Fatalf("summary csv deveria conter %q: %s", trecho, string(resumoConteudo))
		}
	}
	comparacaoConteudo, err := os.ReadFile(comparacao)
	if err != nil {
		t.Fatalf("ler comparison csv: %v", err)
	}
	if !strings.Contains(string(comparacaoConteudo), "delta_direction") || !strings.Contains(string(comparacaoConteudo), "WIT_CONTEXT_MINUS_DIRECT_TESTS") {
		t.Fatalf("comparison csv deveria declarar direção dos deltas: %s", string(comparacaoConteudo))
	}
	if !strings.Contains(string(comparacaoConteudo), ",1,") || !strings.Contains(string(comparacaoConteudo), ",40,") {
		t.Fatalf("comparison csv deveria exportar deltas WIT-DIRECT para requests/tokens: %s", string(comparacaoConteudo))
	}
}

func ponteiroFloatAplicacao(v float64) *float64 {
	return &v
}

func TestEscreverCSVResumoSegundaFaseIncluiCabecalho(t *testing.T) {
	caminho := filepath.Join(t.TempDir(), "summary.csv")
	relatorio := dominio.RelatorioSegundaFase{}
	if err := escreverCSVResumoSegundaFase(caminho, relatorio); err != nil {
		t.Fatalf("summary csv: %v", err)
	}
	conteudo, err := os.ReadFile(caminho)
	if err != nil {
		t.Fatalf("read summary csv: %v", err)
	}
	if !strings.Contains(string(conteudo), "project_key") {
		t.Fatalf("cabecalho ausente no summary csv: %s", string(conteudo))
	}
}

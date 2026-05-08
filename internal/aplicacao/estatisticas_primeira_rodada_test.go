package aplicacao

import (
	"os"
	"path/filepath"
	"strings"
	"testing"
)

func TestConsolidarEstatisticasPrimeiraRodadaCalculaDeltasPareados(t *testing.T) {
	dir := t.TempDir()
	manifesto := filepath.Join(dir, "statistical-manifest.csv")
	resumo := filepath.Join(dir, "phase-two-summary.csv")
	metricas := filepath.Join(dir, "phase-two-metrics.csv")
	comparacao := filepath.Join(dir, "phase-two-comparison.csv")
	saida := filepath.Join(dir, "out")

	escreverArquivoTeste(t, manifesto, strings.Join([]string{
		"source_project_key,slice_key,qualified_signature,baseline_slice",
		"jackson-databind,jackson-databind-s001,a.A#m(),slice-001.json",
		"jackson-databind,jackson-databind-s002,a.B#m(),slice-002.json",
		"httpcomponents-client,httpcomponents-client-s001,c.C#m(),slice-001.json",
	}, "\n"))
	escreverArquivoTeste(t, resumo, "project_key,scenario,metric_score\nx,WIT_CONTEXT,1\n")
	escreverArquivoTeste(t, metricas, "project_key,scenario,metric_name,numeric_value\nx,WIT_CONTEXT,metric_score,1\n")
	escreverArquivoTeste(t, comparacao, strings.Join([]string{
		"project_key,delta_metric_score,delta_combined_score,delta_test_pass_rate,delta_target_method_coverage,delta_assertive_tests_rate,delta_exception_assertion_rate,delta_jacoco_line,delta_jacoco_branch,delta_pit_mutation,delta_request_count,delta_input_tokens,delta_output_tokens,delta_estimated_cost",
		"jackson-databind-s001,-10,-10,-5,0,-20,,1,2,3,0,-100,-10,-0.01",
		"jackson-databind-s002,20,20,5,0,10,,2,4,,0,200,20,0.02",
		"httpcomponents-client-s001,-30,-30,,0,0,,3,6,9,0,-300,-30,-0.03",
	}, "\n"))

	csvPath, mdPath, err := consolidarEstatisticasPrimeiraRodada(caminhoEstatisticasPrimeiraRodada{
		Manifesto:  manifesto,
		Resumo:     resumo,
		Metricas:   metricas,
		Comparacao: comparacao,
		Saida:      saida,
	})
	if err != nil {
		t.Fatalf("consolidação falhou: %v", err)
	}

	conteudoCSV := lerArquivoTeste(t, csvPath)
	if !strings.Contains(conteudoCSV, "metric,n,mean_delta_wit_minus_direct") {
		t.Fatalf("CSV estatístico sem cabeçalho esperado:\n%s", conteudoCSV)
	}
	if !strings.Contains(conteudoCSV, "metric_score,3,6.6667,10.0000") {
		t.Fatalf("delta WIT-DIRECT de metric_score inesperado:\n%s", conteudoCSV)
	}
	if !strings.Contains(conteudoCSV, "assertive_tests_rate,3,3.3333,0.0000") {
		t.Fatalf("métrica com zero/negativo/positivo inesperada:\n%s", conteudoCSV)
	}

	conteudoMD := lerArquivoTeste(t, mdPath)
	if !strings.Contains(conteudoMD, "# Primeira rodada estatística") {
		t.Fatalf("Markdown sem título esperado:\n%s", conteudoMD)
	}
	if !strings.Contains(conteudoMD, "`jackson-databind`: 2 slices") {
		t.Fatalf("Markdown não resumiu manifesto por projeto:\n%s", conteudoMD)
	}
	if !strings.Contains(conteudoMD, "`httpcomponents-client`: 1 slices") {
		t.Fatalf("Markdown não resumiu manifesto por projeto:\n%s", conteudoMD)
	}
}

func TestColetarDeltasWITMenosDiretoUsaDirecaoNovaELegado(t *testing.T) {
	valoresNovos := coletarDeltasWITMenosDireto([]map[string]string{{
		"delta_direction":    "WIT_CONTEXT_MINUS_DIRECT_TESTS",
		"delta_metric_score": "12.5",
	}}, "delta_metric_score")
	if len(valoresNovos) != 1 || valoresNovos[0] != 12.5 {
		t.Fatalf("CSV novo deveria preservar WIT-DIRECT: %#v", valoresNovos)
	}

	valoresLegado := coletarDeltasWITMenosDireto([]map[string]string{{
		"delta_metric_score": "-12.5",
	}}, "delta_metric_score")
	if len(valoresLegado) != 1 || valoresLegado[0] != 12.5 {
		t.Fatalf("CSV legado deveria inverter DIRECT-WIT: %#v", valoresLegado)
	}
}

func escreverArquivoTeste(t *testing.T, caminho, conteudo string) {
	t.Helper()
	if err := os.WriteFile(caminho, []byte(conteudo), 0o644); err != nil {
		t.Fatalf("write %s: %v", caminho, err)
	}
}

func lerArquivoTeste(t *testing.T, caminho string) string {
	t.Helper()
	conteudo, err := os.ReadFile(caminho)
	if err != nil {
		t.Fatalf("read %s: %v", caminho, err)
	}
	return string(conteudo)
}

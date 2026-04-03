package armazenamento

import (
	"fmt"
	"os"
	"path/filepath"
	"testing"

	"github.com/marceloamorim/witup-llm/internal/artefatos"
)

func TestImportarEConsultarBaselineNoDuckDB(t *testing.T) {
	tempDir := t.TempDir()
	caminhoBanco := filepath.Join(tempDir, "witup-llm.duckdb")
	caminhoBaseline := filepath.Join(tempDir, "replication", "sample", "wit.json")

	payload := map[string]interface{}{
		"path":       "C:\\wit-projects\\sample\\",
		"commitHash": "abc123",
		"classes": []interface{}{
			map[string]interface{}{
				"path": "C:\\wit-projects\\sample\\src\\main\\java\\sample\\Example.java",
				"methods": []interface{}{
					map[string]interface{}{
						"qualifiedSignature":        "sample.Example.run(java.lang.String)",
						"exception":                 "throw new NullPointerException(\"name must not be null\");",
						"pathCojunction":            "(name == null)",
						"simplifiedPathConjunction": "name == null",
						"soundSymbolic":             true,
						"soundBackwards":            true,
						"line":                      10,
						"throwingLine":              11,
					},
				},
			},
		},
	}
	if err := artefatos.EscreverJSON(caminhoBaseline, payload); err != nil {
		t.Fatalf("escrever fixture da baseline: %v", err)
	}

	banco, err := AbrirBancoDuckDB(caminhoBanco)
	if err != nil {
		t.Fatalf("abrir DuckDB: %v", err)
	}
	defer banco.Fechar()

	importado, atualizado, err := banco.ImportarBaselineProjeto("sample", caminhoBaseline, "wit.json")
	if err != nil {
		t.Fatalf("importar baseline: %v", err)
	}
	if !importado || atualizado {
		t.Fatalf("esperava importação nova, recebi importado=%v atualizado=%v", importado, atualizado)
	}

	relatorio, origem, err := banco.CarregarRelatorioBaseline("sample", "wit.json")
	if err != nil {
		t.Fatalf("carregar baseline do banco: %v", err)
	}
	if origem != caminhoBaseline {
		t.Fatalf("esperava origem %q, recebi %q", caminhoBaseline, origem)
	}
	if relatorio.TotalMetodos != 1 {
		t.Fatalf("esperava 1 método no relatório carregado, recebi %d", relatorio.TotalMetodos)
	}

	resultado, err := banco.ExecutarConsultaSomenteLeitura("SELECT chave_projeto, total_metodos FROM vw_baselines_witup")
	if err != nil {
		t.Fatalf("consultar view do banco: %v", err)
	}
	if len(resultado.Linhas) != 1 || resultado.Linhas[0][0] != "sample" {
		t.Fatalf("resultado inesperado da consulta: %#v", resultado.Linhas)
	}
}

func TestExecutarConsultaSomenteLeituraTruncaResultadoGrande(t *testing.T) {
	tempDir := t.TempDir()
	caminhoBanco := filepath.Join(tempDir, "witup-llm.duckdb")

	banco, err := AbrirBancoDuckDB(caminhoBanco)
	if err != nil {
		t.Fatalf("abrir DuckDB: %v", err)
	}
	defer banco.Fechar()

	resultado, err := banco.ExecutarConsultaSomenteLeitura("SELECT i FROM range(0, 250) tbl(i)")
	if err != nil {
		t.Fatalf("executar consulta grande: %v", err)
	}
	if len(resultado.Linhas) != limiteLinhasConsulta {
		t.Fatalf("esperava %d linhas retornadas, recebi %d", limiteLinhasConsulta, len(resultado.Linhas))
	}
	if !resultado.LinhasTruncadas {
		t.Fatalf("esperava truncamento de resultado para consultas grandes")
	}
	if resultado.LimiteLinhas != limiteLinhasConsulta {
		t.Fatalf("limite retornado inesperado: %d", resultado.LimiteLinhas)
	}
}

func TestConsultaSomenteLeituraAceitaConsultaComComentarioInicialEFenceMarkdown(t *testing.T) {
	casos := []string{
		"SELECT\n  *\nFROM vw_baselines_witup",
		"-- comentário inicial\nSELECT * FROM vw_baselines_witup",
		"/* comentário */\nSELECT * FROM vw_baselines_witup",
		"```sql\nSELECT * FROM vw_baselines_witup\n```",
		";\nSELECT * FROM vw_baselines_witup",
	}

	for _, consulta := range casos {
		if !consultaSomenteLeitura(consulta) {
			t.Fatalf("esperava consulta de leitura permitida, mas foi rejeitada: %q", consulta)
		}
	}
}

func TestConsultaSomenteLeituraRejeitaEscrita(t *testing.T) {
	if consultaSomenteLeitura("DELETE FROM artefatos_execucao") {
		t.Fatalf("consulta de escrita não deveria ser aceita")
	}
}

func TestViewsAnaliticasDoEstudoCompleto(t *testing.T) {
	tempDir := t.TempDir()
	caminhoBanco := filepath.Join(tempDir, "witup-llm.duckdb")

	banco, err := AbrirBancoDuckDB(caminhoBanco)
	if err != nil {
		t.Fatalf("abrir DuckDB: %v", err)
	}
	defer banco.Fechar()

	analiseWITUP := map[string]interface{}{
		"run_id":       "run-1",
		"generated_at": "2026-04-01T12:00:00Z",
		"analyses": []interface{}{
			map[string]interface{}{
				"method": map[string]interface{}{
					"method_id":      "sample.Example.run(java.lang.String)",
					"file_path":      "src/main/java/sample/Example.java",
					"container_name": "sample.Example",
					"signature":      "sample.Example.run(java.lang.String)",
				},
				"expaths": []interface{}{
					map[string]interface{}{
						"path_id":          "wit-1",
						"exception_type":   "NullPointerException",
						"trigger":          "name == null",
						"confidence":       0.9,
						"guard_conditions": []interface{}{"name == null"},
						"metadata": map[string]interface{}{
							"maybe": true,
						},
					},
				},
			},
		},
	}
	if err := banco.RegistrarArtefatoExecucao(
		"run-1",
		"analise_witup",
		"sample",
		"WITUP_ONLY",
		"/tmp/witup.json",
		"2026-04-01T12:00:00Z",
		analiseWITUP,
	); err != nil {
		t.Fatalf("registrar analise_witup: %v", err)
	}

	comparacao := map[string]interface{}{
		"run_id":              "run-1",
		"generated_at":        "2026-04-01T12:01:00Z",
		"witup_analysis_path": "/tmp/witup.json",
		"llm_analysis_path":   "/tmp/llm.json",
		"summary": map[string]interface{}{
			"witup_method_count":      1,
			"llm_method_count":        1,
			"methods_in_both":         1,
			"methods_only_witup":      0,
			"methods_only_llm":        0,
			"witup_expath_count":      1,
			"llm_expath_count":        2,
			"shared_expath_count":     1,
			"witup_only_expath_count": 0,
			"llm_only_expath_count":   1,
		},
		"metrics": map[string]interface{}{
			"llm_method_coverage_over_witup": 100.0,
			"llm_expath_coverage_over_witup": 100.0,
			"llm_structural_precision":       50.0,
			"expath_jaccard_index":           50.0,
			"llm_novelty_rate":               50.0,
		},
		"methods": []interface{}{
			map[string]interface{}{
				"unit": map[string]interface{}{
					"class_name":       "sample.Example",
					"method_signature": "sample.Example.run(java.lang.String)",
					"exception_type":   "NullPointerException",
				},
				"witup_expath_count":    1,
				"llm_expath_count":      2,
				"shared_expath_count":   1,
				"witup_only_expath_ids": []interface{}{},
				"llm_only_expath_ids":   []interface{}{"llm-2"},
			},
		},
	}
	if err := banco.RegistrarArtefatoExecucao(
		"run-1",
		"comparacao_fontes",
		"sample",
		"",
		"/tmp/comparacao.json",
		"2026-04-01T12:01:00Z",
		comparacao,
	); err != nil {
		t.Fatalf("registrar comparacao_fontes: %v", err)
	}

	estudo := map[string]interface{}{
		"run_id":                 "run-1",
		"generated_at":           "2026-04-01T12:02:00Z",
		"project_key":            "sample",
		"analysis_model_key":     "openai_main",
		"generation_model_key":   "openai_main",
		"judge_model_key":        "openai_judge",
		"experiment_report_path": "/tmp/experimento.json",
		"comparison_path":        "/tmp/comparacao.json",
		"suite_comparison": map[string]interface{}{
			"best_variant_by_metric_score":         "WITUP_PLUS_LLM",
			"best_variant_by_combined_score":       "WITUP_PLUS_LLM",
			"delta_test_files_llm_vs_witup":        0.0,
			"delta_test_files_combined_vs_witup":   1.0,
			"delta_metric_score_llm_vs_witup":      0.1,
			"delta_metric_score_combined_vs_witup": 0.2,
			"delta_metric_score_combined_vs_llm":   0.1,
		},
		"variants": []interface{}{
			map[string]interface{}{
				"variant":         "WITUP_ONLY",
				"analysis_path":   "/tmp/witup-only.analysis.json",
				"method_count":    1,
				"expath_count":    1,
				"generation_path": "/tmp/witup-only.generation.json",
				"test_file_count": 1,
				"evaluation_path": "/tmp/witup-only.evaluation.json",
				"metric_results": []interface{}{
					map[string]interface{}{
						"name":             "coverage",
						"kind":             "coverage",
						"success":          true,
						"numeric_value":    70.0,
						"normalized_score": 70.0,
						"weight":           1.0,
						"description":      "Cobertura de código",
					},
				},
				"metric_score":   0.7,
				"judge_score":    0.9,
				"judge_verdict":  "bom",
				"combined_score": 0.8,
				"derived_metrics": map[string]interface{}{
					"test_files_per_method": 1.0,
					"test_files_per_expath": 1.0,
					"metric_success_rate":   100.0,
				},
			},
			map[string]interface{}{
				"variant":         "LLM_ONLY",
				"analysis_path":   "/tmp/llm-only.analysis.json",
				"method_count":    1,
				"expath_count":    1,
				"generation_path": "/tmp/llm-only.generation.json",
				"test_file_count": 1,
				"evaluation_path": "/tmp/llm-only.evaluation.json",
				"metric_results": []interface{}{
					map[string]interface{}{
						"name":             "coverage",
						"kind":             "coverage",
						"success":          true,
						"numeric_value":    80.0,
						"normalized_score": 80.0,
						"weight":           1.0,
						"description":      "Cobertura de código",
					},
				},
				"metric_score":   0.8,
				"judge_score":    0.85,
				"judge_verdict":  "forte",
				"combined_score": 0.82,
				"derived_metrics": map[string]interface{}{
					"test_files_per_method": 1.0,
					"test_files_per_expath": 1.0,
					"metric_success_rate":   100.0,
				},
			},
			map[string]interface{}{
				"variant":         "WITUP_PLUS_LLM",
				"analysis_path":   "/tmp/witup-plus-llm.analysis.json",
				"method_count":    1,
				"expath_count":    2,
				"generation_path": "/tmp/witup-plus-llm.generation.json",
				"test_file_count": 2,
				"evaluation_path": "/tmp/witup-plus-llm.evaluation.json",
				"metric_results": []interface{}{
					map[string]interface{}{
						"name":             "coverage",
						"kind":             "coverage",
						"success":          true,
						"numeric_value":    90.0,
						"normalized_score": 90.0,
						"weight":           1.0,
						"description":      "Cobertura de código",
					},
				},
				"metric_score":   0.9,
				"judge_score":    0.98,
				"judge_verdict":  "excelente",
				"combined_score": 0.95,
				"derived_metrics": map[string]interface{}{
					"test_files_per_method": 2.0,
					"test_files_per_expath": 1.0,
					"metric_success_rate":   100.0,
				},
			},
		},
	}
	if err := banco.RegistrarArtefatoExecucao(
		"run-1",
		"estudo_completo",
		"sample",
		"",
		"/tmp/estudo-completo.json",
		"2026-04-01T12:02:00Z",
		estudo,
	); err != nil {
		t.Fatalf("registrar estudo_completo: %v", err)
	}

	resultadoResumo, err := banco.ExecutarConsultaSomenteLeitura(`
		SELECT quantidade_expaths_llm, taxa_precisao_estrutural_llm
		FROM vw_comparacao_fontes_resumo
		WHERE id_execucao = 'run-1'`)
	if err != nil {
		t.Fatalf("consultar vw_comparacao_fontes_resumo: %v", err)
	}
	if len(resultadoResumo.Linhas) != 1 || resultadoResumo.Linhas[0][0] != "2" {
		t.Fatalf("resultado inesperado em vw_comparacao_fontes_resumo: %#v", resultadoResumo.Linhas)
	}
	if resultadoResumo.Linhas[0][1] == "" {
		t.Fatalf("esperava métrica derivada da Parte 1 em vw_comparacao_fontes_resumo")
	}

	resultadoMaybe, err := banco.ExecutarConsultaSomenteLeitura(`
		SELECT assinatura_metodo, llm_recuperou_metodo, llm_adicionou_expaths
		FROM vw_h1_maybe_recuperacao
		WHERE id_execucao = 'run-1'`)
	if err != nil {
		t.Fatalf("consultar vw_h1_maybe_recuperacao: %v", err)
	}
	if len(resultadoMaybe.Linhas) != 1 {
		t.Fatalf("esperava uma linha em vw_h1_maybe_recuperacao, recebi %#v", resultadoMaybe.Linhas)
	}
	if resultadoMaybe.Linhas[0][0] != "sample.Example.run(java.lang.String)" {
		t.Fatalf("assinatura inesperada em vw_h1_maybe_recuperacao: %#v", resultadoMaybe.Linhas[0])
	}

	resultadoVariantes, err := banco.ExecutarConsultaSomenteLeitura(`
		SELECT variante, quantidade_arquivos_teste, nota_combinada
		FROM vw_estudo_variantes
		WHERE id_execucao = 'run-1'
		ORDER BY variante`)
	if err != nil {
		t.Fatalf("consultar vw_estudo_variantes: %v", err)
	}
	if len(resultadoVariantes.Linhas) != 3 {
		t.Fatalf("esperava três linhas em vw_estudo_variantes, recebi %#v", resultadoVariantes.Linhas)
	}

	resultadoH3, err := banco.ExecutarConsultaSomenteLeitura(`
		SELECT variante, media_sucesso_metricas, media_nota_combinada
		FROM vw_h3_qualidade_variantes
		WHERE chave_projeto = 'sample'
		ORDER BY variante`)
	if err != nil {
		t.Fatalf("consultar vw_h3_qualidade_variantes: %v", err)
	}
	if len(resultadoH3.Linhas) != 3 {
		t.Fatalf("esperava três linhas em vw_h3_qualidade_variantes, recebi %#v", resultadoH3.Linhas)
	}

	resultadoMetricas, err := banco.ExecutarConsultaSomenteLeitura(`
		SELECT variante, nome_metrica, nota_normalizada
		FROM vw_h3_metricas_variantes
		WHERE id_execucao = 'run-1'
		ORDER BY variante`)
	if err != nil {
		t.Fatalf("consultar vw_h3_metricas_variantes: %v", err)
	}
	if len(resultadoMetricas.Linhas) != 3 {
		t.Fatalf("esperava três linhas em vw_h3_metricas_variantes, recebi %#v", resultadoMetricas.Linhas)
	}

	resultadoSuites, err := banco.ExecutarConsultaSomenteLeitura(`
		SELECT
		  arquivos_teste_witup,
		  arquivos_teste_llm,
		  arquivos_teste_combinado,
		  delta_metricas_llm_vs_witup,
		  delta_metricas_combinado_vs_witup
		FROM vw_h3_comparacao_suites
		WHERE id_execucao = 'run-1'`)
	if err != nil {
		t.Fatalf("consultar vw_h3_comparacao_suites: %v", err)
	}
	if len(resultadoSuites.Linhas) != 1 {
		t.Fatalf("esperava uma linha em vw_h3_comparacao_suites, recebi %#v", resultadoSuites.Linhas)
	}
	if resultadoSuites.Linhas[0][0] != "1" || resultadoSuites.Linhas[0][1] != "1" || resultadoSuites.Linhas[0][2] != "2" {
		t.Fatalf("quantidades inesperadas em vw_h3_comparacao_suites: %#v", resultadoSuites.Linhas[0])
	}
}

func TestGerarGraficosExecucaoMaterializaArquivosFallback(t *testing.T) {
	tempDir := t.TempDir()
	caminhoBanco := filepath.Join(tempDir, "witup-llm.duckdb")
	banco, err := AbrirBancoDuckDB(caminhoBanco)
	if err != nil {
		t.Fatalf("abrir DuckDB: %v", err)
	}
	defer banco.Fechar()

	comparacao := map[string]interface{}{
		"run_id":       "run-plot",
		"generated_at": "2026-04-01T12:01:00Z",
		"summary": map[string]interface{}{
			"witup_method_count":      1,
			"llm_method_count":        1,
			"methods_in_both":         1,
			"methods_only_witup":      0,
			"methods_only_llm":        0,
			"witup_expath_count":      1,
			"llm_expath_count":        1,
			"shared_expath_count":     1,
			"witup_only_expath_count": 0,
			"llm_only_expath_count":   0,
		},
		"metrics": map[string]interface{}{
			"llm_method_coverage_over_witup": 100.0,
			"llm_expath_coverage_over_witup": 100.0,
			"llm_structural_precision":       100.0,
			"expath_jaccard_index":           100.0,
			"llm_novelty_rate":               0.0,
		},
	}
	if err := banco.RegistrarArtefatoExecucao("run-plot", "comparacao_fontes", "sample", "", "/tmp/comparacao.json", "2026-04-01T12:01:00Z", comparacao); err != nil {
		t.Fatalf("registrar comparação: %v", err)
	}
	for _, variante := range []map[string]interface{}{
		{"variant": "WITUP_ONLY", "expath_count": 1, "test_file_count": 1, "metric_score": 70.0, "judge_score": 75.0, "combined_score": 71.5, "derived_metrics": map[string]interface{}{"test_files_per_method": 1.0, "test_files_per_expath": 1.0, "metric_success_rate": 100.0}, "metric_results": []interface{}{map[string]interface{}{"name": "coverage", "success": true, "numeric_value": 70.0, "normalized_score": 70.0}}},
		{"variant": "LLM_ONLY", "expath_count": 1, "test_file_count": 2, "metric_score": 80.0, "judge_score": 82.0, "combined_score": 80.6, "derived_metrics": map[string]interface{}{"test_files_per_method": 2.0, "test_files_per_expath": 2.0, "metric_success_rate": 100.0}, "metric_results": []interface{}{map[string]interface{}{"name": "coverage", "success": true, "numeric_value": 80.0, "normalized_score": 80.0}}},
	} {
		estudo := map[string]interface{}{
			"run_id":       "run-plot",
			"generated_at": "2026-04-01T12:02:00Z",
			"project_key":  "sample",
			"variants":     []interface{}{variante},
		}
		if err := banco.RegistrarArtefatoExecucao("run-plot", "estudo_completo", "sample", fmt.Sprint(variante["variant"]), "/tmp/estudo.json", "2026-04-01T12:02:00Z", estudo); err != nil {
			t.Fatalf("registrar estudo completo: %v", err)
		}
	}

	resumo, err := banco.GerarGraficosExecucao("run-plot", filepath.Join(tempDir, "plots"))
	if err != nil {
		t.Fatalf("gerar gráficos: %v", err)
	}
	if len(resumo.ArquivosGerados) != 3 {
		t.Fatalf("esperava três arquivos de gráfico, recebi %#v", resumo.ArquivosGerados)
	}
	for _, caminho := range resumo.ArquivosGerados {
		dados, err := os.ReadFile(caminho)
		if err != nil {
			t.Fatalf("ler gráfico gerado %q: %v", caminho, err)
		}
		if len(dados) == 0 {
			t.Fatalf("gráfico %q não deveria estar vazio", caminho)
		}
	}
}

func TestListarObjetosEVisualizarObjeto(t *testing.T) {
	tempDir := t.TempDir()
	banco, err := AbrirBancoDuckDB(filepath.Join(tempDir, "witup.duckdb"))
	if err != nil {
		t.Fatalf("abrir banco: %v", err)
	}
	defer banco.Fechar()

	objetos, err := banco.ListarObjetos()
	if err != nil {
		t.Fatalf("listar objetos: %v", err)
	}
	if len(objetos) == 0 {
		t.Fatalf("esperava objetos do esquema no banco")
	}
	resultado, err := banco.VisualizarObjeto("main", "vw_baselines_witup", 5)
	if err != nil {
		t.Fatalf("visualizar objeto: %v", err)
	}
	if len(resultado.Colunas) == 0 {
		t.Fatalf("esperava colunas retornadas na visualização")
	}
}

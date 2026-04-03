package aplicacao

import (
	"os"
	"path/filepath"
	"testing"

	"github.com/marceloamorim/witup-llm/internal/artefatos"
	"github.com/marceloamorim/witup-llm/internal/dominio"
)

func TestExecutarExperimentoProduzTresArtefatosDeVariantes(t *testing.T) {
	tempDir := t.TempDir()
	cfg := &dominio.ConfigAplicacao{
		Projeto: dominio.ConfigProjeto{
			Raiz: tempDir,
		},
		Fluxo: dominio.ConfigFluxo{
			DiretorioSaida:     filepath.Join(tempDir, "generated"),
			CaminhoDuckDB:      filepath.Join(tempDir, "generated", "witup-llm.duckdb"),
			RaizReplicacaoWIT:  filepath.Join(tempDir, "replication"),
			ArquivoBaselineWIT: "wit.json",
			SalvarPrompts:      true,
		},
		Modelos: map[string]dominio.ConfigModelo{
			"analysis": {Modelo: "gpt-5.4"},
		},
	}
	baselinePath := filepath.Join(cfg.Fluxo.RaizReplicacaoWIT, "sample", "wit.json")
	baselinePayload := map[string]interface{}{
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
	if err := artefatos.EscreverJSON(baselinePath, baselinePayload); err != nil {
		t.Fatalf("write baseline fixture: %v", err)
	}

	service := NovoServicoComDependencias(
		&fakeCompletionClient{
			responses: []*RespostaComplecao{
				{
					Payload: map[string]interface{}{
						"summary":          "Metodo validates the input",
						"method_summary":   "Metodo validates the input",
						"responsibilities": []interface{}{"validate input"},
						"input_risks":      []interface{}{"null input"},
						"exception_cues":   []interface{}{"throws when input is null"},
					},
					RawText: "{}",
				},
				{
					Payload: map[string]interface{}{
						"summary":                    "No external callees",
						"direct_dependencies":        []interface{}{},
						"callee_risks":               []interface{}{},
						"field_dependencies":         []interface{}{},
						"propagated_exception_clues": []interface{}{},
					},
					RawText: "{}",
				},
				{
					Payload: map[string]interface{}{
						"summary":        "One null-check path",
						"method_summary": "Throws for null input",
						"expaths": []interface{}{
							map[string]interface{}{
								"path_id":          "l1",
								"exception_type":   "NullPointerException",
								"trigger":          "name == null",
								"guard_conditions": []interface{}{"name == null"},
								"confidence":       0.9,
								"evidence":         []interface{}{"line 11"},
							},
						},
					},
					RawText: "{}",
				},
				{
					Payload: map[string]interface{}{
						"summary":        "Candidate is acceptable",
						"method_summary": "Throws for null input",
						"accepted_expaths": []interface{}{
							map[string]interface{}{
								"path_id":          "l1",
								"exception_type":   "NullPointerException",
								"trigger":          "name == null",
								"guard_conditions": []interface{}{"name == null"},
								"confidence":       0.9,
								"evidence":         []interface{}{"line 11"},
							},
						},
						"review_notes": []interface{}{"evidence is explicit"},
					},
					RawText: "{}",
				},
			},
		},
		fakeMetricRunner{},
		fakeCatalogFactory{
			catalog: fakeCatalog{
				methods: []dominio.DescritorMetodo{{
					IDMetodo:       "sample.Example.run(String name):10",
					NomeContainer:  "sample.Example",
					NomeMetodo:     "run",
					Assinatura:     "sample.Example.run(String name)",
					CaminhoArquivo: "src/main/java/sample/Example.java",
					LinhaInicial:   10,
					Origem:         "void run(String name) { if (name == null) { throw new NullPointerException(); } }",
				}},
				overview: "sample overview",
			},
		},
	)

	result, err := service.ExecutarExperimento(cfg, "sample", "analysis")
	if err != nil {
		t.Fatalf("ExecutarExperimento retornou erro inesperado: %v", err)
	}

	if len(result.ArtefatosVariantes) != 3 {
		t.Fatalf("esperava 3 artefatos de variantes, recebi %d", len(result.ArtefatosVariantes))
	}
	if result.RelatorioComparacao.Resumo.MetodosEmAmbos != 1 {
		t.Fatalf("esperava um método em comum, recebi %d", result.RelatorioComparacao.Resumo.MetodosEmAmbos)
	}
	if result.RelatorioComparacao.Metricas.TaxaCoberturaMetodosLLMSobreWITUP == nil || *result.RelatorioComparacao.Metricas.TaxaCoberturaMetodosLLMSobreWITUP != 100 {
		t.Fatalf("esperava cobertura de métodos da LLM sobre WITUP em 100%%, recebi %#v", result.RelatorioComparacao.Metricas.TaxaCoberturaMetodosLLMSobreWITUP)
	}
	if result.RelatorioComparacao.Metricas.IndiceJaccardExpaths == nil {
		t.Fatalf("esperava índice de Jaccard calculado na comparação")
	}
	if _, err := CarregarRelatorioAnalise(result.CaminhoAnaliseLLM); err != nil {
		t.Fatalf("esperava que o artefato de análise da LLM fosse legível: %v", err)
	}
	if _, err := os.Stat(result.CaminhoRastreio); err != nil {
		t.Fatalf("esperava que o artefato de rastreio fosse escrito: %v", err)
	}
}

func TestExecutarEstudoCompletoGeraEAvaliaAsTresVariantes(t *testing.T) {
	tempDir := t.TempDir()
	cfg := &dominio.ConfigAplicacao{
		Projeto: dominio.ConfigProjeto{
			Raiz: tempDir,
		},
		Fluxo: dominio.ConfigFluxo{
			DiretorioSaida:     filepath.Join(tempDir, "generated"),
			CaminhoDuckDB:      filepath.Join(tempDir, "generated", "witup-llm.duckdb"),
			RaizReplicacaoWIT:  filepath.Join(tempDir, "replication"),
			ArquivoBaselineWIT: "wit.json",
			SalvarPrompts:      true,
			ModoLLM:            string(dominio.ModoLLMDireto),
		},
		Modelos: map[string]dominio.ConfigModelo{
			"analysis":   {Modelo: "gpt-5.4"},
			"generation": {Modelo: "gpt-5.4"},
		},
	}
	baselinePath := filepath.Join(cfg.Fluxo.RaizReplicacaoWIT, "sample", "wit.json")
	baselinePayload := map[string]interface{}{
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
	if err := artefatos.EscreverJSON(baselinePath, baselinePayload); err != nil {
		t.Fatalf("write baseline fixture: %v", err)
	}

	notaMetrica := 82.0
	service := NovoServicoComDependencias(
		&fakeCompletionClient{
			responses: []*RespostaComplecao{
				{
					Payload: map[string]interface{}{
						"method_summary": "Throws for null input",
						"expaths": []interface{}{
							map[string]interface{}{
								"path_id":          "llm-1",
								"exception_type":   "NullPointerException",
								"trigger":          "name == null",
								"guard_conditions": []interface{}{"name == null"},
								"confidence":       0.9,
								"evidence":         []interface{}{"line 11"},
							},
						},
					},
					RawText: "{}",
				},
				{
					Payload: map[string]interface{}{
						"strategy_summary": "Testes WITUP",
						"files": []interface{}{
							map[string]interface{}{
								"relative_path":      "src/test/java/sample/WitupOnlyTest.java",
								"content":            "class WitupOnlyTest {}",
								"covered_method_ids": []interface{}{"sample.Example.run(String name):10"},
							},
						},
					},
					RawText: "{}",
				},
				{
					Payload: map[string]interface{}{
						"strategy_summary": "Testes LLM",
						"files": []interface{}{
							map[string]interface{}{
								"relative_path":      "src/test/java/sample/LLMOnlyTest.java",
								"content":            "class LLMOnlyTest {}",
								"covered_method_ids": []interface{}{"sample.Example.run(String name):10"},
							},
						},
					},
					RawText: "{}",
				},
				{
					Payload: map[string]interface{}{
						"strategy_summary": "Testes combinados",
						"files": []interface{}{
							map[string]interface{}{
								"relative_path":      "src/test/java/sample/WitupPlusLLMTest.java",
								"content":            "class WitupPlusLLMTest {}",
								"covered_method_ids": []interface{}{"sample.Example.run(String name):10"},
							},
						},
					},
					RawText: "{}",
				},
			},
		},
		fakeMetricRunner{
			results: []dominio.ResultadoMetrica{{
				Nome:            "coverage",
				Tipo:            "coverage",
				Sucesso:         true,
				NotaNormalizada: &notaMetrica,
				Peso:            1.0,
				Descricao:       "Cobertura de código",
			}},
		},
		fakeCatalogFactory{
			catalog: fakeCatalog{
				methods: []dominio.DescritorMetodo{{
					IDMetodo:       "sample.Example.run(String name):10",
					NomeContainer:  "sample.Example",
					NomeMetodo:     "run",
					Assinatura:     "sample.Example.run(String name)",
					CaminhoArquivo: "src/main/java/sample/Example.java",
					LinhaInicial:   10,
					Origem:         "void run(String name) { if (name == null) { throw new NullPointerException(); } }",
				}},
				overview: "sample overview",
			},
		},
	)

	result, err := service.ExecutarEstudoCompleto(cfg, "sample", "analysis", "generation", "")
	if err != nil {
		t.Fatalf("ExecutarEstudoCompleto retornou erro inesperado: %v", err)
	}

	if len(result.ResultadosVariantes) != 3 {
		t.Fatalf("esperava 3 variantes avaliadas, recebi %d", len(result.ResultadosVariantes))
	}
	if _, err := os.Stat(result.CaminhoEstudoCompleto); err != nil {
		t.Fatalf("esperava o artefato consolidado do estudo: %v", err)
	}
	if result.DiretorioGraficos == "" {
		t.Fatalf("esperava diretório de gráficos preenchido")
	}
	if _, err := os.Stat(filepath.Join(result.DiretorioGraficos, "parte-1-expaths.txt")); err != nil {
		t.Fatalf("esperava gráfico da Parte 1: %v", err)
	}
	for _, variante := range result.ResultadosVariantes {
		if variante.CaminhoGeracao == "" || variante.CaminhoAvaliacao == "" {
			t.Fatalf("esperava caminhos de geração e avaliação preenchidos: %#v", variante)
		}
		if variante.NotaMetricas == nil || *variante.NotaMetricas != notaMetrica {
			t.Fatalf("esperava nota de métricas %.2f, recebi %#v", notaMetrica, variante.NotaMetricas)
		}
		if variante.MetricasDerivadas.TaxaArquivosTestePorMetodo == nil || *variante.MetricasDerivadas.TaxaArquivosTestePorMetodo <= 0 {
			t.Fatalf("esperava taxa de arquivos de teste por método calculada: %#v", variante.MetricasDerivadas)
		}
		if variante.MetricasDerivadas.TaxaSucessoMetricas == nil || *variante.MetricasDerivadas.TaxaSucessoMetricas != 100 {
			t.Fatalf("esperava taxa de sucesso de métricas em 100%%, recebi %#v", variante.MetricasDerivadas.TaxaSucessoMetricas)
		}
	}
	if result.RelatorioEstudoCompleto.ComparacaoSuites.MelhorVariantePorNotaMetricas == "" {
		t.Fatalf("esperava melhor variante por nota de métricas preenchida")
	}
	if result.RelatorioEstudoCompleto.ComparacaoSuites.DeltaMetricasLLMVsWITUP == nil {
		t.Fatalf("esperava deltas de comparação entre suítes preenchidos")
	}
}

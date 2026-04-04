package aplicacao

import (
	"os"
	"path/filepath"
	"strings"
	"testing"

	"github.com/marceloamorim/witup-llm/internal/artefatos"
	"github.com/marceloamorim/witup-llm/internal/dominio"
	"github.com/marceloamorim/witup-llm/internal/metricas"
)

type fakeCompletionClient struct {
	responses []*RespostaComplecao
	index     int
	calls     int
}

func (f *fakeCompletionClient) CompletarJSON(dominio.ConfigModelo, string, string, dominio.OpcoesRequisicaoLLM) (*RespostaComplecao, error) {
	if f == nil {
		return &RespostaComplecao{Payload: map[string]interface{}{}, RawText: "{}"}, nil
	}
	f.calls++
	if len(f.responses) == 0 {
		return &RespostaComplecao{Payload: map[string]interface{}{}, RawText: "{}"}, nil
	}
	if f.index >= len(f.responses) {
		return &RespostaComplecao{Payload: map[string]interface{}{}, RawText: "{}"}, nil
	}
	response := f.responses[f.index]
	f.index++
	return response, nil
}

type fakeMetricRunner struct {
	results []dominio.ResultadoMetrica
}

func (f fakeMetricRunner) ExecutarTodas([]dominio.ConfigMetrica, metricas.ContextoExecucao) []dominio.ResultadoMetrica {
	return f.results
}

type fakeCatalog struct {
	methods  []dominio.DescritorMetodo
	overview string
}

func (f fakeCatalog) Catalogar() ([]dominio.DescritorMetodo, error) {
	return f.methods, nil
}

func (f fakeCatalog) CarregarVisaoGeral() (string, error) {
	return f.overview, nil
}

type fakeCatalogFactory struct {
	catalog CatalogoMetodos
}

func (f fakeCatalogFactory) NovoCatalogo(dominio.ConfigProjeto) CatalogoMetodos {
	return f.catalog
}

func TestAnalisarUsaAdaptadoresInjetados(t *testing.T) {
	tempDir := t.TempDir()
	cfg := &dominio.ConfigAplicacao{
		Projeto: dominio.ConfigProjeto{
			Raiz: tempDir,
		},
		Fluxo: dominio.ConfigFluxo{
			DiretorioSaida: filepath.Join(tempDir, "generated"),
			SalvarPrompts:  true,
		},
		Modelos: map[string]dominio.ConfigModelo{
			"analysis": {Modelo: "gpt-5.4"},
		},
	}

	method := dominio.DescritorMetodo{
		IDMetodo:      "sample:method:1",
		NomeContainer: "sample.Container",
		NomeMetodo:    "method",
		Assinatura:    "sample.Container.method()",
		Origem:        "void method() { throw new IllegalArgumentException(); }",
	}
	service := NovoServicoComDependencias(
		&fakeCompletionClient{
			responses: []*RespostaComplecao{{
				Payload: map[string]interface{}{
					"method_summary": "Raises when invalid",
					"expaths": []interface{}{
						map[string]interface{}{
							"path_id":          "p1",
							"exception_type":   "IllegalArgumentException",
							"trigger":          "invalid input",
							"guard_conditions": []interface{}{"arg < 0"},
							"confidence":       1.0,
							"evidence":         []interface{}{"line 12"},
						},
					},
				},
				RawText: `{"method_summary":"Raises when invalid"}`,
			}},
		},
		fakeMetricRunner{},
		fakeCatalogFactory{
			catalog: fakeCatalog{
				methods:  []dominio.DescritorMetodo{method},
				overview: "project overview",
			},
		},
	)

	report, analysisPath, workspace, err := service.Analisar(cfg, "analysis", nil)
	if err != nil {
		t.Fatalf("Analisar retornou erro inesperado: %v", err)
	}
	if report.TotalMetodos != 1 {
		t.Fatalf("expected 1 analyzed method, got %d", report.TotalMetodos)
	}
	if len(report.Analises) != 1 || len(report.Analises[0].CaminhosExcecao) != 1 {
		t.Fatalf("expected one normalized expath, got %#v", report.Analises)
	}
	if _, err := os.Stat(analysisPath); err != nil {
		t.Fatalf("expected analysis artifact to be written: %v", err)
	}
	promptFile := filepath.Join(workspace.Prompts, "analysis-0001-sample-method-1.txt")
	if _, err := os.Stat(promptFile); err != nil {
		t.Fatalf("expected saved prompt artifact: %v", err)
	}
}

func TestGerarEscreveApenasArquivosSeguros(t *testing.T) {
	tempDir := t.TempDir()
	cfg := &dominio.ConfigAplicacao{
		Projeto: dominio.ConfigProjeto{
			Raiz: tempDir,
		},
		Fluxo: dominio.ConfigFluxo{
			DiretorioSaida: filepath.Join(tempDir, "generated"),
		},
		Modelos: map[string]dominio.ConfigModelo{
			"generator": {Modelo: "gpt-5.4"},
		},
	}

	analysis := dominio.RelatorioAnalise{
		Analises: []dominio.AnaliseMetodo{{
			Metodo: dominio.DescritorMetodo{
				IDMetodo:      "sample:method:1",
				NomeContainer: "sample.Container",
			},
			CaminhosExcecao: []dominio.CaminhoExcecao{{
				IDCaminho:   "p1",
				TipoExcecao: "IllegalArgumentException",
			}},
		}},
	}

	service := NovoServicoComDependencias(
		&fakeCompletionClient{
			responses: []*RespostaComplecao{{
				Payload: map[string]interface{}{
					"strategy_summary": "One focused unit test",
					"files": []interface{}{
						map[string]interface{}{
							"relative_path":      "src/test/java/sample/ContainerTest.java",
							"content":            "class ContainerTest {}",
							"covered_method_ids": []interface{}{"sample:method:1"},
						},
					},
				},
				RawText: "{}",
			}},
		},
		fakeMetricRunner{},
		fakeCatalogFactory{catalog: fakeCatalog{overview: "project overview"}},
	)

	report, generationPath, workspace, err := service.Gerar(cfg, analysis, "/tmp/analysis.json", "generator", nil)
	if err != nil {
		t.Fatalf("Gerar retornou erro inesperado: %v", err)
	}
	if len(report.ArquivosTeste) != 1 {
		t.Fatalf("expected one generated test file, got %d", len(report.ArquivosTeste))
	}
	if _, err := os.Stat(generationPath); err != nil {
		t.Fatalf("expected generation report: %v", err)
	}
	generatedFile := filepath.Join(workspace.Testes, "src/test/java/sample/ContainerTest.java")
	if _, err := os.Stat(generatedFile); err != nil {
		t.Fatalf("expected generated test file to be written: %v", err)
	}
}

func TestGerarDivideConteinerGrandeEmLotesCompactos(t *testing.T) {
	tempDir := t.TempDir()
	cfg := &dominio.ConfigAplicacao{
		Projeto: dominio.ConfigProjeto{
			Raiz: tempDir,
		},
		Fluxo: dominio.ConfigFluxo{
			DiretorioSaida: filepath.Join(tempDir, "generated"),
		},
		Modelos: map[string]dominio.ConfigModelo{
			"generator": {Modelo: "gpt-5.4"},
		},
	}

	analises := make([]dominio.AnaliseMetodo, 0, 7)
	for i := 0; i < 7; i++ {
		analises = append(analises, dominio.AnaliseMetodo{
			Metodo: dominio.DescritorMetodo{
				IDMetodo:      "sample:method:" + string(rune('a'+i)),
				NomeContainer: "sample.Container",
				NomeMetodo:    "method",
				Assinatura:    "sample.Container.method()",
				Origem:        "public void method(final String value) { }",
			},
			ResumoMetodo: "summary",
			CaminhosExcecao: []dominio.CaminhoExcecao{{
				IDCaminho:   "p1",
				TipoExcecao: "IllegalArgumentException",
			}},
		})
	}

	cliente := &fakeCompletionClient{
		responses: []*RespostaComplecao{
			{
				Payload: map[string]interface{}{
					"strategy_summary": "lote 1",
					"files": []interface{}{
						map[string]interface{}{
							"relative_path":      "src/test/java/sample/ContainerTest.java",
							"content":            "class ContainerTest {}",
							"covered_method_ids": []interface{}{"sample:method:a"},
						},
					},
				},
				RawText: "{}",
			},
			{
				Payload: map[string]interface{}{
					"strategy_summary": "lote 2",
					"files": []interface{}{
						map[string]interface{}{
							"relative_path":      "src/test/java/sample/ContainerExtraTest.java",
							"content":            "class ContainerExtraTest {}",
							"covered_method_ids": []interface{}{"sample:method:g"},
						},
					},
				},
				RawText: "{}",
			},
		},
	}

	service := NovoServicoComDependencias(
		cliente,
		fakeMetricRunner{},
		fakeCatalogFactory{catalog: fakeCatalog{overview: strings.Repeat("overview ", 600)}},
	)

	report, _, workspace, err := service.Gerar(cfg, dominio.RelatorioAnalise{Analises: analises}, "/tmp/analysis.json", "generator", nil)
	if err != nil {
		t.Fatalf("Gerar retornou erro inesperado: %v", err)
	}
	if cliente.calls != 2 {
		t.Fatalf("expected 2 LLM calls for chunked generation, got %d", cliente.calls)
	}
	if len(report.ArquivosTeste) != 2 {
		t.Fatalf("expected 2 generated files after chunking, got %d", len(report.ArquivosTeste))
	}
	if _, err := os.Stat(filepath.Join(workspace.Testes, "src/test/java/sample/ContainerTest.java")); err != nil {
		t.Fatalf("expected first generated file to exist: %v", err)
	}
	if _, err := os.Stat(filepath.Join(workspace.Testes, "src/test/java/sample/ContainerExtraTest.java")); err != nil {
		t.Fatalf("expected second generated file to exist: %v", err)
	}
}

func TestAvaliarCombinaMetricasEJuiz(t *testing.T) {
	tempDir := t.TempDir()
	cfg := &dominio.ConfigAplicacao{
		Projeto: dominio.ConfigProjeto{
			Raiz: tempDir,
		},
		Fluxo: dominio.ConfigFluxo{
			DiretorioSaida: filepath.Join(tempDir, "generated"),
			ModeloJuiz:     "judge",
		},
		Modelos: map[string]dominio.ConfigModelo{
			"judge": {Modelo: "gpt-5.4"},
		},
		Metricas: []dominio.ConfigMetrica{{Nome: "coverage", Peso: 1.0}},
	}

	metricValue := 80.0
	service := NovoServicoComDependencias(
		&fakeCompletionClient{
			responses: []*RespostaComplecao{{
				Payload: map[string]interface{}{
					"score":                    60.0,
					"verdict":                  "acceptable",
					"strengths":                []interface{}{"deterministic"},
					"weaknesses":               []interface{}{"missing diff tests"},
					"risks":                    []interface{}{"recall gap"},
					"recommended_next_actions": []interface{}{"compare against baseline"},
				},
				RawText: "{}",
			}},
		},
		fakeMetricRunner{
			results: []dominio.ResultadoMetrica{{Nome: "coverage", NotaNormalizada: &metricValue, Peso: 1.0}},
		},
		fakeCatalogFactory{catalog: fakeCatalog{}},
	)

	workspace, err := artefatos.NovoEspacoTrabalho(cfg.Fluxo.DiretorioSaida, "evaluate-test")
	if err != nil {
		t.Fatalf("create workspace: %v", err)
	}

	report, evaluationPath, _, err := service.Avaliar(
		cfg,
		dominio.RelatorioAnalise{},
		"/tmp/analysis.json",
		dominio.RelatorioGeracao{ChaveModelo: "generator"},
		"/tmp/generation.json",
		"judge",
		workspace,
	)
	if err != nil {
		t.Fatalf("Avaliar retornou erro inesperado: %v", err)
	}
	if report.NotaMetricas == nil || *report.NotaMetricas != 80.0 {
		t.Fatalf("expected metric score 80, got %v", report.NotaMetricas)
	}
	if report.NotaCombinada == nil || *report.NotaCombinada != 74.0 {
		t.Fatalf("expected combined score 74, got %v", report.NotaCombinada)
	}
	if _, err := os.Stat(evaluationPath); err != nil {
		t.Fatalf("expected evaluation report: %v", err)
	}
}

// TestPrepararSandboxAvaliacaoIsolaTestes verifica que a sandbox de avaliação:
// 1. Remove testes originais (src/test) para não contaminar métricas
// 2. Injeta os testes gerados no local correto
// 3. Preserva o código-fonte do projeto
// Isso protege o invariante #5: a Parte 2 avalia em sandbox isolada.
func TestPrepararSandboxAvaliacaoIsolaTestes(t *testing.T) {
	tempDir := t.TempDir()

	// Simular estrutura de projeto Java com testes originais
	projetoRaiz := filepath.Join(tempDir, "projeto")
	for _, dir := range []string{
		"src/main/java/com/example",
		"src/test/java/com/example",
		"pom.xml",
	} {
		if strings.HasSuffix(dir, ".xml") {
			if err := os.MkdirAll(filepath.Join(projetoRaiz, filepath.Dir(dir)), 0o755); err != nil {
				t.Fatal(err)
			}
			if err := os.WriteFile(filepath.Join(projetoRaiz, dir), []byte("<project/>"), 0o644); err != nil {
				t.Fatal(err)
			}
		} else {
			if err := os.MkdirAll(filepath.Join(projetoRaiz, dir), 0o755); err != nil {
				t.Fatal(err)
			}
		}
	}
	// Arquivo fonte original
	if err := os.WriteFile(filepath.Join(projetoRaiz, "src/main/java/com/example/App.java"), []byte("class App {}"), 0o644); err != nil {
		t.Fatal(err)
	}
	// Teste original que NÃO deve aparecer na sandbox
	if err := os.WriteFile(filepath.Join(projetoRaiz, "src/test/java/com/example/AppTest.java"), []byte("class AppTest {}"), 0o644); err != nil {
		t.Fatal(err)
	}

	cfg := &dominio.ConfigAplicacao{
		Projeto: dominio.ConfigProjeto{
			Raiz: projetoRaiz,
		},
		Fluxo: dominio.ConfigFluxo{
			DiretorioSaida: filepath.Join(tempDir, "generated"),
		},
	}

	workspace, err := artefatos.NovoEspacoTrabalho(cfg.Fluxo.DiretorioSaida, "sandbox-test")
	if err != nil {
		t.Fatalf("criar workspace: %v", err)
	}

	// Escrever testes gerados no workspace
	testGerado := filepath.Join(workspace.Testes, "src/test/java/com/example/GeneratedTest.java")
	if err := os.MkdirAll(filepath.Dir(testGerado), 0o755); err != nil {
		t.Fatal(err)
	}
	if err := os.WriteFile(testGerado, []byte("class GeneratedTest {}"), 0o644); err != nil {
		t.Fatal(err)
	}

	raizSandbox, err := prepararSandboxAvaliacao(cfg, workspace)
	if err != nil {
		t.Fatalf("prepararSandboxAvaliacao falhou: %v", err)
	}

	// Invariante #5a: testes originais devem ter sido removidos
	testOriginalSandbox := filepath.Join(raizSandbox, "src/test/java/com/example/AppTest.java")
	if _, err := os.Stat(testOriginalSandbox); err == nil {
		t.Fatal("teste original NÃO deveria existir na sandbox (violação do invariante #5)")
	}

	// Invariante #5b: testes gerados devem estar presentes
	testGeradoSandbox := filepath.Join(raizSandbox, "src/test/java/com/example/GeneratedTest.java")
	if _, err := os.Stat(testGeradoSandbox); err != nil {
		t.Fatalf("teste gerado deveria existir na sandbox: %v", err)
	}

	// Invariante #5c: código-fonte do projeto deve estar preservado
	fonteSandbox := filepath.Join(raizSandbox, "src/main/java/com/example/App.java")
	if _, err := os.Stat(fonteSandbox); err != nil {
		t.Fatalf("código-fonte deveria estar preservado na sandbox: %v", err)
	}

	// Invariante #5d: pom.xml deve existir para que Maven funcione
	pomSandbox := filepath.Join(raizSandbox, "pom.xml")
	if _, err := os.Stat(pomSandbox); err != nil {
		t.Fatalf("pom.xml deveria estar preservado na sandbox: %v", err)
	}
}

// TestPrepararSandboxAvaliacaoMultiModulo verifica o cenário de projeto Maven
// com layout não-padrão de testes (ex: módulos aninhados).
func TestPrepararSandboxAvaliacaoMultiModulo(t *testing.T) {
	tempDir := t.TempDir()
	projetoRaiz := filepath.Join(tempDir, "multi-modulo")
	for _, dir := range []string{
		"modulo-a/src/main/java",
		"modulo-a/src/test/java",
		"modulo-b/src/main/java",
	} {
		if err := os.MkdirAll(filepath.Join(projetoRaiz, dir), 0o755); err != nil {
			t.Fatal(err)
		}
	}
	if err := os.WriteFile(filepath.Join(projetoRaiz, "modulo-a/src/test/java/OldTest.java"), []byte("class OldTest {}"), 0o644); err != nil {
		t.Fatal(err)
	}

	cfg := &dominio.ConfigAplicacao{
		Projeto: dominio.ConfigProjeto{Raiz: projetoRaiz},
		Fluxo:   dominio.ConfigFluxo{DiretorioSaida: filepath.Join(tempDir, "generated")},
	}
	workspace, err := artefatos.NovoEspacoTrabalho(cfg.Fluxo.DiretorioSaida, "sandbox-multi")
	if err != nil {
		t.Fatal(err)
	}

	raizSandbox, err := prepararSandboxAvaliacao(cfg, workspace)
	if err != nil {
		t.Fatalf("prepararSandboxAvaliacao falhou: %v", err)
	}

	// src/test no nível raiz deve ter sido removido, mas testes em submódulos
	// persistem porque prepararSandboxAvaliacao remove apenas src/test de primeiro nível.
	// Isso é um trade-off documentado: projetos multi-módulo podem precisar de
	// configuração de exclude adicional.
	if _, err := os.Stat(filepath.Join(raizSandbox, "modulo-a/src/main/java")); err != nil {
		t.Fatalf("submódulo fonte deveria estar preservado: %v", err)
	}
	if _, err := os.Stat(raizSandbox); err != nil {
		t.Fatalf("sandbox deveria existir: %v", err)
	}
}

func TestNormalizarAnaliseMetodoIgnoraEntradasInvalidas(t *testing.T) {
	method := dominio.DescritorMetodo{IDMetodo: "sample:method:1"}
	report := normalizarAnaliseMetodo(method, map[string]interface{}{
		"method_summary": "summary",
		"expaths": []interface{}{
			map[string]interface{}{"trigger": "missing exception type"},
			map[string]interface{}{
				"exception_type": "IllegalArgumentException",
				"confidence":     5.0,
			},
		},
	})

	if len(report.CaminhosExcecao) != 1 {
		t.Fatalf("expected only one normalized expath, got %d", len(report.CaminhosExcecao))
	}
	if report.CaminhosExcecao[0].Confianca != 1.0 {
		t.Fatalf("expected confidence clamp to 1.0, got %f", report.CaminhosExcecao[0].Confianca)
	}
}

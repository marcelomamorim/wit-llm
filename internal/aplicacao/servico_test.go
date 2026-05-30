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

type metricRunnerFunc func([]dominio.ConfigMetrica, metricas.ContextoExecucao) []dominio.ResultadoMetrica

func (f metricRunnerFunc) ExecutarTodas(metricasCfg []dominio.ConfigMetrica, ctx metricas.ContextoExecucao) []dominio.ResultadoMetrica {
	return f(metricasCfg, ctx)
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

func TestAvaliarMaterializaTestesDaGeracaoEmExecucaoIsolada(t *testing.T) {
	tempDir := t.TempDir()
	projetoRaiz := filepath.Join(tempDir, "projeto")
	if err := os.MkdirAll(filepath.Join(projetoRaiz, "src/main/java/com/example"), 0o755); err != nil {
		t.Fatal(err)
	}
	if err := os.WriteFile(filepath.Join(projetoRaiz, "pom.xml"), []byte("<project/>"), 0o644); err != nil {
		t.Fatal(err)
	}
	cfg := &dominio.ConfigAplicacao{
		Projeto: dominio.ConfigProjeto{Raiz: projetoRaiz},
		Fluxo:   dominio.ConfigFluxo{DiretorioSaida: filepath.Join(tempDir, "generated")},
	}

	var capturado metricas.ContextoExecucao
	service := NovoServicoComDependencias(
		nil,
		metricRunnerFunc(func(_ []dominio.ConfigMetrica, ctx metricas.ContextoExecucao) []dominio.ResultadoMetrica {
			capturado = ctx
			return nil
		}),
		fakeCatalogFactory{catalog: fakeCatalog{}},
	)

	reportGeracao := dominio.RelatorioGeracao{
		ChaveModelo: "generator",
		ArquivosTeste: []dominio.ArquivoTesteGerado{{
			CaminhoRelativo: "src/test/java/com/example/GeneratedTest.java",
			Conteudo:        "class GeneratedTest {}",
		}},
	}

	workspace, err := artefatos.NovoEspacoTrabalho(cfg.Fluxo.DiretorioSaida, "evaluate-rehydrate")
	if err != nil {
		t.Fatal(err)
	}
	if _, _, _, err := service.Avaliar(cfg, dominio.RelatorioAnalise{}, "/tmp/analysis.json", reportGeracao, "/tmp/generation.json", "", workspace); err != nil {
		t.Fatalf("Avaliar retornou erro inesperado: %v", err)
	}

	testeNoWorkspace := filepath.Join(workspace.Testes, "src/test/java/com/example/GeneratedTest.java")
	if _, err := os.Stat(testeNoWorkspace); err != nil {
		t.Fatalf("suite gerada deveria ser reidratada no workspace: %v", err)
	}
	testeNaSandbox := filepath.Join(capturado.RaizProjeto, "src/test/java/com/example/GeneratedTest.java")
	if _, err := os.Stat(testeNaSandbox); err != nil {
		t.Fatalf("suite gerada deveria estar presente na sandbox: %v", err)
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

func TestPrepararSandboxAvaliacaoSanitizaPomParaMetricas(t *testing.T) {
	tempDir := t.TempDir()
	projetoRaiz := filepath.Join(tempDir, "projeto")
	if err := os.MkdirAll(filepath.Join(projetoRaiz, "src/main/java/com/example"), 0o755); err != nil {
		t.Fatal(err)
	}
	pom := `<project>
  <packaging>maven-plugin</packaging>
  <build>
    <plugins>
      <plugin>
        <groupId>org.sonatype.plugins</groupId>
        <artifactId>nexus-staging-maven-plugin</artifactId>
        <extensions>true</extensions>
      </plugin>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-release-plugin</artifactId>
      </plugin>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-gpg-plugin</artifactId>
      </plugin>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <configuration>
          <source>1.7</source>
          <target>1.7</target>
        </configuration>
      </plugin>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-plugin-plugin</artifactId>
      </plugin>
      <plugin>
        <groupId>org.codehaus.mojo</groupId>
        <artifactId>license-maven-plugin</artifactId>
      </plugin>
    </plugins>
  </build>
  <distributionManagement>
    <repository><id>ossrh</id></repository>
  </distributionManagement>
</project>`
	if err := os.WriteFile(filepath.Join(projetoRaiz, "pom.xml"), []byte(pom), 0o644); err != nil {
		t.Fatal(err)
	}

	cfg := &dominio.ConfigAplicacao{
		Projeto: dominio.ConfigProjeto{Raiz: projetoRaiz},
		Fluxo:   dominio.ConfigFluxo{DiretorioSaida: filepath.Join(tempDir, "generated")},
	}
	workspace, err := artefatos.NovoEspacoTrabalho(cfg.Fluxo.DiretorioSaida, "sandbox-pom")
	if err != nil {
		t.Fatal(err)
	}

	raizSandbox, err := prepararSandboxAvaliacao(cfg, workspace)
	if err != nil {
		t.Fatalf("prepararSandboxAvaliacao falhou: %v", err)
	}
	dados, err := os.ReadFile(filepath.Join(raizSandbox, "pom.xml"))
	if err != nil {
		t.Fatalf("ler pom da sandbox: %v", err)
	}
	texto := string(dados)
	for _, removido := range []string{
		"nexus-staging-maven-plugin",
		"maven-release-plugin",
		"maven-gpg-plugin",
		"maven-plugin-plugin",
		"license-maven-plugin",
		"<distributionManagement>",
	} {
		if strings.Contains(texto, removido) {
			t.Fatalf("pom sanitizado ainda contém %q", removido)
		}
	}
	if strings.Contains(texto, "<packaging>maven-plugin</packaging>") {
		t.Fatal("pom sanitizado deveria remover o packaging maven-plugin para evitar goals implícitos de plugin")
	}
	if !strings.Contains(texto, "<packaging>jar</packaging>") {
		t.Fatal("pom sanitizado deveria rebaixar o packaging para jar")
	}
	if strings.Contains(texto, "<source>1.7</source>") || strings.Contains(texto, "<target>1.7</target>") {
		t.Fatal("pom sanitizado deveria elevar source/target antigos para um nível compatível")
	}
	if !strings.Contains(texto, "<source>1.8</source>") || !strings.Contains(texto, "<target>1.8</target>") {
		t.Fatal("pom sanitizado deveria materializar source/target em 1.8 quando o projeto usa níveis antigos")
	}
	if !strings.Contains(texto, "maven-compiler-plugin") {
		t.Fatal("pom sanitizado deveria preservar plugins necessários à compilação")
	}
}

func TestPrepararSandboxAvaliacaoPreservaPacoteBuildDoCodigoFonte(t *testing.T) {
	tempDir := t.TempDir()
	projetoRaiz := filepath.Join(tempDir, "projeto")
	if err := os.MkdirAll(filepath.Join(projetoRaiz, "src/main/java/org/apache/commons/io/build"), 0o755); err != nil {
		t.Fatal(err)
	}
	if err := os.MkdirAll(filepath.Join(projetoRaiz, "build/tmp"), 0o755); err != nil {
		t.Fatal(err)
	}
	if err := os.WriteFile(filepath.Join(projetoRaiz, "pom.xml"), []byte("<project/>"), 0o644); err != nil {
		t.Fatal(err)
	}
	if err := os.WriteFile(filepath.Join(projetoRaiz, "src/main/java/org/apache/commons/io/build/AbstractStreamBuilder.java"), []byte("package org.apache.commons.io.build; class AbstractStreamBuilder {}"), 0o644); err != nil {
		t.Fatal(err)
	}
	if err := os.WriteFile(filepath.Join(projetoRaiz, "build/tmp/generated.txt"), []byte("temp"), 0o644); err != nil {
		t.Fatal(err)
	}

	cfg := &dominio.ConfigAplicacao{
		Projeto: dominio.ConfigProjeto{
			Raiz:    projetoRaiz,
			Exclude: []string{".git", "target", "build"},
		},
		Fluxo: dominio.ConfigFluxo{DiretorioSaida: filepath.Join(tempDir, "generated")},
	}
	workspace, err := artefatos.NovoEspacoTrabalho(cfg.Fluxo.DiretorioSaida, "sandbox-build-package")
	if err != nil {
		t.Fatal(err)
	}

	raizSandbox, err := prepararSandboxAvaliacao(cfg, workspace)
	if err != nil {
		t.Fatalf("prepararSandboxAvaliacao falhou: %v", err)
	}
	if _, err := os.Stat(filepath.Join(raizSandbox, "build/tmp/generated.txt")); !os.IsNotExist(err) {
		t.Fatalf("esperava build/ raiz removido da sandbox, recebi err=%v", err)
	}
	if _, err := os.Stat(filepath.Join(raizSandbox, "src/main/java/org/apache/commons/io/build/AbstractStreamBuilder.java")); err != nil {
		t.Fatalf("esperava pacote Java build preservado, recebi err=%v", err)
	}
}

func TestPrepararSandboxAvaliacaoInjetaSuporteJunitJupiterQuandoTestesGeradosPrecisam(t *testing.T) {
	tempDir := t.TempDir()
	projetoRaiz := filepath.Join(tempDir, "projeto")
	if err := os.MkdirAll(filepath.Join(projetoRaiz, "src/main/java/com/example"), 0o755); err != nil {
		t.Fatal(err)
	}
	pom := `<project>
  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.12</version>
      <scope>test</scope>
    </dependency>
  </dependencies>
  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
      </plugin>
    </plugins>
  </build>
</project>`
	if err := os.WriteFile(filepath.Join(projetoRaiz, "pom.xml"), []byte(pom), 0o644); err != nil {
		t.Fatal(err)
	}

	cfg := &dominio.ConfigAplicacao{
		Projeto: dominio.ConfigProjeto{Raiz: projetoRaiz, TestFramework: "infer"},
		Fluxo:   dominio.ConfigFluxo{DiretorioSaida: filepath.Join(tempDir, "generated")},
	}
	workspace, err := artefatos.NovoEspacoTrabalho(cfg.Fluxo.DiretorioSaida, "sandbox-jupiter")
	if err != nil {
		t.Fatal(err)
	}
	testeGerado := filepath.Join(workspace.Testes, "src/test/java/com/example/GeneratedTest.java")
	if err := os.MkdirAll(filepath.Dir(testeGerado), 0o755); err != nil {
		t.Fatal(err)
	}
	conteudoTeste := `package com.example;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class GeneratedTest {
	@Test
	void sample() {
		assertThrows(RuntimeException.class, () -> { throw new RuntimeException(); });
	}
}`
	if err := os.WriteFile(testeGerado, []byte(conteudoTeste), 0o644); err != nil {
		t.Fatal(err)
	}

	raizSandbox, err := prepararSandboxAvaliacao(cfg, workspace)
	if err != nil {
		t.Fatalf("prepararSandboxAvaliacao falhou: %v", err)
	}
	dados, err := os.ReadFile(filepath.Join(raizSandbox, "pom.xml"))
	if err != nil {
		t.Fatalf("ler pom da sandbox: %v", err)
	}
	texto := string(dados)
	for _, esperado := range []string{
		"junit-jupiter-api",
		"junit-jupiter-engine",
		"maven-surefire-plugin",
	} {
		if !strings.Contains(texto, esperado) {
			t.Fatalf("pom sanitizado deveria conter %q para suportar testes JUnit 5", esperado)
		}
	}
}

func TestResolverFrameworkTestesInfereJUnit4DoPom(t *testing.T) {
	tempDir := t.TempDir()
	pom := `<project>
  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.12</version>
      <scope>test</scope>
    </dependency>
  </dependencies>
</project>`
	if err := os.WriteFile(filepath.Join(tempDir, "pom.xml"), []byte(pom), 0o644); err != nil {
		t.Fatal(err)
	}

	framework := resolverFrameworkTestes(dominio.ConfigProjeto{Raiz: tempDir, TestFramework: "infer"})
	if framework != frameworkJUnit4 {
		t.Fatalf("framework inferido inesperado: %s", framework)
	}
}

func TestConstruirPromptGeracaoSistemaParaJUnit4VedaJupiter(t *testing.T) {
	prompt := construirPromptGeracaoSistema("junit4")
	if !strings.Contains(prompt, "JUnit 4") {
		t.Fatalf("prompt deveria mencionar JUnit 4: %s", prompt)
	}
	if !strings.Contains(prompt, "org.junit.jupiter") {
		t.Fatalf("prompt deveria orientar a evitar Jupiter: %s", prompt)
	}
	if !strings.Contains(prompt, "checagem estática mental") {
		t.Fatalf("prompt deveria exigir revisão de compilação/execução: %s", prompt)
	}
}

func TestConstruirPromptGeracaoUsuarioExplicaCamposDoRelatorioWIT(t *testing.T) {
	prompt := construirPromptGeracaoUsuario("overview", "sample.Example", []dominio.AnaliseMetodo{{
		Metodo: dominio.DescritorMetodo{
			IDMetodo:       "sample.Example:run:10",
			CaminhoArquivo: "src/main/java/sample/Example.java",
			NomeContainer:  "sample.Example",
			NomeMetodo:     "run",
			Assinatura:     "sample.Example.run(java.lang.String)",
			Origem:         "public void run(String value) { if (value == null) throw new NullPointerException(); }",
		},
		ResumoMetodo: "resume",
		CaminhosExcecao: []dominio.CaminhoExcecao{{
			IDCaminho:       "path-1",
			TipoExcecao:     "java.lang.NullPointerException",
			Gatilho:         "value == null",
			CondicoesGuarda: []string{"value == null"},
			Confianca:       0.72,
			Evidencias:      []string{"Objects.requireNonNull(value)"},
		}},
	}})

	for _, esperado := range []string{
		"Como interpretar o relatório WIT abaixo",
		"O que é WIT",
		"técnica de análise estática",
		"O que são expaths",
		"segurança significa redução do risco de bugs",
		"method_summary",
		"expath.confidence",
		"expath.evidence",
		"method.source_code",
		"checkout_compatibility_notes",
		"trate o relatório WIT como contexto auxiliar",
		"priorize o código atual",
		"revise mentalmente cada teste",
		"revelar bugs reais e proteger contra regressões futuras",
		"só use assertThrows quando o código atual realmente sustentar a exceção",
		"extrato heurístico do baseline",
		"derive os testes apenas do código atual em method.source_code",
		"Contexto técnico comum aos dois cenários",
		"cada teste deve invocar explicitamente o método-alvo",
	} {
		if !strings.Contains(prompt, esperado) {
			t.Fatalf("prompt deveria conter %q:\n%s", esperado, prompt)
		}
	}
}

func TestConstruirPromptGeracaoUsuarioJUnitNaoIncluiRegrasJTReg(t *testing.T) {
	contexto := map[string]interface{}{
		"test_framework":                 "junit5",
		"recommended_test_package":       "sample",
		"recommended_relative_test_path": "src/test/java/sample/ExampleWitupGeneratedTest.java",
	}
	analises := []dominio.AnaliseMetodo{{
		Metodo: dominio.DescritorMetodo{
			IDMetodo:       "sample.Example:run:10",
			CaminhoArquivo: "src/main/java/sample/Example.java",
			NomeContainer:  "sample.Example",
			NomeMetodo:     "run",
			Assinatura:     "sample.Example.run(java.lang.String)",
			Origem:         "public void run(String value) { if (value == null) throw new NullPointerException(); }",
		},
	}}
	metodos := []dominio.DescritorMetodo{analises[0].Metodo}

	witPrompt := construirPromptGeracaoUsuario("overview", "sample.Example", analises, contexto)
	directPrompt := construirPromptGeracaoDiretaUsuario("overview", "sample.Example", metodos, contexto)
	for nome, prompt := range map[string]string{"wit": witPrompt, "direct": directPrompt} {
		for _, proibido := range []string{"FORMATO JTREG", "@test", "@run main", "@modules", "NÃO declare package"} {
			if strings.Contains(prompt, proibido) {
				t.Fatalf("prompt %s não deveria conter regra JTReg %q:\n%s", nome, proibido, prompt)
			}
		}
		for _, esperado := range []string{
			"JUnit 5/Jupiter",
			"recommended_test_package",
			"recommended_relative_test_path",
			"declare o package exatamente",
		} {
			if !strings.Contains(prompt, esperado) {
				t.Fatalf("prompt %s deveria conter regra JUnit %q:\n%s", nome, esperado, prompt)
			}
		}
	}
}

func TestConstruirPromptGeracaoUsuarioJTRegMantemRegrasObrigatorias(t *testing.T) {
	contexto := map[string]interface{}{
		"test_framework":                 "jtreg",
		"recommended_relative_test_path": "test/jdk/witup/generated/sample/ExampleWitupTest.java",
	}
	prompt := construirPromptGeracaoUsuario("overview", "sample.Example", []dominio.AnaliseMetodo{{
		Metodo: dominio.DescritorMetodo{
			IDMetodo:       "sample.Example:run:10",
			CaminhoArquivo: "src/main/java/sample/Example.java",
			NomeContainer:  "sample.Example",
			NomeMetodo:     "run",
			Assinatura:     "sample.Example.run(java.lang.String)",
			Origem:         "public void run(String value) { if (value == null) throw new NullPointerException(); }",
		},
	}}, contexto)

	for _, esperado := range []string{
		"FORMATO JTREG OBRIGATÓRIO",
		"@test",
		"@run main",
		"@modules",
		"NÃO declare package",
	} {
		if !strings.Contains(prompt, esperado) {
			t.Fatalf("prompt jtreg deveria conter %q:\n%s", esperado, prompt)
		}
	}
}

func TestCompactarAnalisesParaGeracaoIncluiCodigoAtualENotasDeCompatibilidade(t *testing.T) {
	compartilhado := compactarAnalisesParaGeracao([]dominio.AnaliseMetodo{{
		Metodo: dominio.DescritorMetodo{
			IDMetodo:       "sample.Example:run:10",
			CaminhoArquivo: "src/main/java/sample/Example.java",
			NomeContainer:  "sample.Example",
			NomeMetodo:     "run",
			Assinatura:     "sample.Example.run(java.lang.String)",
			Origem:         "public boolean run(String value) { return value == null; }",
		},
		ResumoMetodo: "resume",
		RespostaBruta: map[string]interface{}{
			"discarded_expaths_due_to_checkout": []string{"path-1"},
		},
	}})

	if len(compartilhado) != 1 {
		t.Fatalf("esperava um item compactado, recebi %d", len(compartilhado))
	}
	method, ok := compartilhado[0]["method"].(map[string]interface{})
	if !ok {
		t.Fatalf("payload do método inesperado: %#v", compartilhado[0]["method"])
	}
	if method["source_code"] != "public boolean run(String value) { return value == null; }" {
		t.Fatalf("source_code inesperado: %#v", method["source_code"])
	}
	notas, ok := compartilhado[0]["checkout_compatibility_notes"].([]string)
	if !ok {
		t.Fatalf("notas de compatibilidade inesperadas: %#v", compartilhado[0]["checkout_compatibility_notes"])
	}
	if len(notas) != 1 || !strings.Contains(notas[0], "path-1") {
		t.Fatalf("notas de compatibilidade inesperadas: %#v", notas)
	}
}

func TestConstruirPromptGeracaoDiretaUsuarioExigeCompatibilidadeComCheckoutAtual(t *testing.T) {
	prompt := construirPromptGeracaoDiretaUsuario("overview", "sample.Example", []dominio.DescritorMetodo{{
		IDMetodo:       "sample.Example:run:10",
		CaminhoArquivo: "src/main/java/sample/Example.java",
		NomeContainer:  "sample.Example",
		NomeMetodo:     "run",
		Assinatura:     "sample.Example.run(java.lang.String)",
		Origem:         "public boolean run(String value) { return value == null; }",
	}})

	for _, esperado := range []string{
		"revise mentalmente cada teste",
		"só use assertThrows quando o código atual realmente sustentar a exceção",
		"não compile ou não passe no checkout atual",
		"revelem bugs e protejam contra regressões futuras",
		"não recebe WIT nem expaths",
		"Contexto técnico comum aos dois cenários",
		"cada teste deve invocar explicitamente o método-alvo",
	} {
		if !strings.Contains(prompt, esperado) {
			t.Fatalf("prompt direto deveria conter %q:\n%s", esperado, prompt)
		}
	}
}

func TestConstruirPromptReparoUsuarioExplicaReparoUnicoEUsaLogsDeFalha(t *testing.T) {
	analysis := dominio.RelatorioAnalise{Analises: []dominio.AnaliseMetodo{{
		Metodo: dominio.DescritorMetodo{
			IDMetodo:       "sample.Example:run:10",
			CaminhoArquivo: "src/main/java/sample/Example.java",
			NomeContainer:  "sample.Example",
			NomeMetodo:     "run",
			Assinatura:     "sample.Example.run(java.lang.String)",
			Origem:         "public boolean run(String value) { return value == null; }",
		},
		RespostaBruta: map[string]interface{}{
			"discarded_expaths_due_to_checkout": []string{"path-1"},
		},
	}}}
	generation := dominio.RelatorioGeracao{
		ArquivosTeste: []dominio.ArquivoTesteGerado{{
			CaminhoRelativo:    "src/test/java/sample/ExampleTest.java",
			Conteudo:           "class ExampleTest {}",
			IDsMetodosCobertos: []string{"sample.Example:run:10"},
		}},
	}
	evaluation := dominio.RelatorioAvaliacao{
		ResultadosMetricas: []dominio.ResultadoMetrica{{
			Nome:        "unit-tests",
			Sucesso:     false,
			SaidaPadrao: "Expected java.lang.NullPointerException to be thrown",
		}},
	}

	prompt := construirPromptReparoUsuario("overview", analysis, generation, evaluation)
	for _, esperado := range []string{
		"única tentativa de reparo",
		"Arquivos de teste atuais",
		"Falhas e sinais da primeira avaliação",
		"Expected java.lang.NullPointerException to be thrown",
		"checkout_compatibility_notes",
	} {
		if !strings.Contains(prompt, esperado) {
			t.Fatalf("prompt de reparo deveria conter %q:\n%s", esperado, prompt)
		}
	}
}

func TestConstruirPromptJuizExigeRespostaEmPortugues(t *testing.T) {
	systemPrompt := construirPromptJuizSistema()
	if !strings.Contains(systemPrompt, "português do Brasil") {
		t.Fatalf("prompt sistêmico do juiz deveria exigir português: %s", systemPrompt)
	}

	userPrompt := construirPromptJuizUsuario(
		dominio.RelatorioAnalise{Analises: []dominio.AnaliseMetodo{{ResumoMetodo: "ok"}}},
		dominio.RelatorioGeracao{ResumoEstrategia: "estratégia"},
		[]dominio.ResultadoMetrica{{Nome: "test-compilation", Sucesso: true}},
	)
	for _, esperado := range []string{
		"escreva verdict, strengths, weaknesses, risks e recommended_next_actions em português do Brasil",
		"falhas de compilação ou execução",
		"utilidade científica da suíte",
	} {
		if !strings.Contains(userPrompt, esperado) {
			t.Fatalf("prompt do juiz deveria conter %q:\n%s", esperado, userPrompt)
		}
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

	if _, err := os.Stat(filepath.Join(raizSandbox, "modulo-a/src/main/java")); err != nil {
		t.Fatalf("submódulo fonte deveria estar preservado: %v", err)
	}
	if _, err := os.Stat(filepath.Join(raizSandbox, "modulo-a/src/test")); !os.IsNotExist(err) {
		t.Fatalf("testes originais de submódulos devem ser removidos para não contaminar métricas, err=%v", err)
	}
	if _, err := os.Stat(raizSandbox); err != nil {
		t.Fatalf("sandbox deveria existir: %v", err)
	}
}

func TestHarnessMavenPreservaPackagingPOMDeAgregador(t *testing.T) {
	tempDir := t.TempDir()
	rootPOM := filepath.Join(tempDir, "pom.xml")
	modulePOM := filepath.Join(tempDir, "module-a", "pom.xml")
	if err := artefatos.EscreverTexto(rootPOM, `<project>
  <modelVersion>4.0.0</modelVersion>
  <groupId>sample</groupId>
  <artifactId>root</artifactId>
  <version>1.0.0</version>
  <packaging>pom</packaging>
  <modules>
    <module>module-a</module>
  </modules>
</project>`); err != nil {
		t.Fatalf("fixture root pom: %v", err)
	}
	if err := artefatos.EscreverTexto(modulePOM, `<project>
  <modelVersion>4.0.0</modelVersion>
  <parent>
    <groupId>sample</groupId>
    <artifactId>root</artifactId>
    <version>1.0.0</version>
  </parent>
  <artifactId>module-a</artifactId>
  <packaging>jar</packaging>
</project>`); err != nil {
		t.Fatalf("fixture module pom: %v", err)
	}

	intervencoes, err := prepararProjetoMavenParaAvaliacao(tempDir, "junit5")
	if err != nil {
		t.Fatalf("preparar projeto Maven: %v", err)
	}
	rootAtualizado, err := os.ReadFile(rootPOM)
	if err != nil {
		t.Fatalf("ler root pom atualizado: %v", err)
	}
	if !strings.Contains(string(rootAtualizado), "<packaging>pom</packaging>") {
		t.Fatalf("root POM agregador não deveria ser convertido para jar:\n%s", string(rootAtualizado))
	}
	if !strings.Contains(strings.Join(intervencoes, ";"), "sandbox_kept_aggregator_packaging_pom") {
		t.Fatalf("intervenções deveriam registrar preservação do packaging pom, recebi %#v", intervencoes)
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

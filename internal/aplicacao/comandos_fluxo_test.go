package aplicacao

import (
	"bytes"
	"io"
	"os"
	"path/filepath"
	"strings"
	"testing"

	"github.com/marceloamorim/witup-llm/internal/artefatos"
	"github.com/marceloamorim/witup-llm/internal/dominio"
)

func capturarSaidas(t *testing.T, executar func() int) (string, string, int) {
	t.Helper()
	stdoutOriginal := os.Stdout
	stderrOriginal := os.Stderr
	stdoutReader, stdoutWriter, err := os.Pipe()
	if err != nil {
		t.Fatalf("pipe stdout: %v", err)
	}
	stderrReader, stderrWriter, err := os.Pipe()
	if err != nil {
		t.Fatalf("pipe stderr: %v", err)
	}
	os.Stdout = stdoutWriter
	os.Stderr = stderrWriter
	defer func() {
		os.Stdout = stdoutOriginal
		os.Stderr = stderrOriginal
	}()

	codigo := executar()
	_ = stdoutWriter.Close()
	_ = stderrWriter.Close()

	var stdoutBuffer, stderrBuffer bytes.Buffer
	_, _ = io.Copy(&stdoutBuffer, stdoutReader)
	_, _ = io.Copy(&stderrBuffer, stderrReader)
	return stdoutBuffer.String(), stderrBuffer.String(), codigo
}

func escreverConfigTeste(t *testing.T, cfg dominio.ConfigAplicacao) string {
	t.Helper()
	caminho := filepath.Join(t.TempDir(), "pipeline.json")
	payload := map[string]interface{}{
		"version":  cfg.Versao,
		"project":  cfg.Projeto,
		"pipeline": cfg.Fluxo,
		"models":   cfg.Modelos,
		"metrics":  cfg.Metricas,
	}
	if err := artefatos.EscreverJSON(caminho, payload); err != nil {
		t.Fatalf("escrever config: %v", err)
	}
	return caminho
}

func criarBaselineWITUPTeste(t *testing.T, raizReplicacao string) {
	t.Helper()
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
	if err := artefatos.EscreverJSON(filepath.Join(raizReplicacao, "sample", "wit.json"), payload); err != nil {
		t.Fatalf("escrever baseline: %v", err)
	}
}

func configBaseTeste(t *testing.T) dominio.ConfigAplicacao {
	raizProjeto := t.TempDir()
	caminhoREADME := filepath.Join(raizProjeto, "README.md")
	if err := artefatos.EscreverTexto(caminhoREADME, "visão geral de teste"); err != nil {
		t.Fatalf("escrever README de teste: %v", err)
	}
	return dominio.ConfigAplicacao{
		Versao: "1",
		Projeto: dominio.ConfigProjeto{
			Raiz:         raizProjeto,
			Include:      []string{"."},
			Exclude:      []string{".git"},
			OverviewFile: caminhoREADME,
		},
		Fluxo: dominio.ConfigFluxo{
			DiretorioSaida:     filepath.Join(raizProjeto, "generated"),
			CaminhoDuckDB:      filepath.Join(raizProjeto, "generated", "witup.duckdb"),
			RaizReplicacaoWIT:  filepath.Join(raizProjeto, "replication"),
			ArquivoBaselineWIT: "wit.json",
			SalvarPrompts:      true,
			ModoLLM:            string(dominio.ModoLLMDireto),
		},
		Modelos: map[string]dominio.ConfigModelo{
			"analysis": {Modelo: "gpt-5.4", Provedor: "openai_compatible", URLBase: "https://api.openai.com/v1", VariavelAmbienteChaveAPI: "OPENAI_API_KEY", SegundosTimeout: 30, MaximoTentativas: 1, EsforcoRaciocinio: "low"},
			"judge":    {Modelo: "gpt-5.4", Provedor: "openai_compatible", URLBase: "https://api.openai.com/v1", VariavelAmbienteChaveAPI: "OPENAI_API_KEY", SegundosTimeout: 30, MaximoTentativas: 1, EsforcoRaciocinio: "low"},
		},
	}
}

func TestExecutarModelosListaConfiguracaoOrdenada(t *testing.T) {
	cfg := configBaseTeste(t)
	cfg.Modelos["zeta"] = cfg.Modelos["analysis"]
	configPath := escreverConfigTeste(t, cfg)
	stdout, stderr, codigo := capturarSaidas(t, func() int {
		return executarModelos([]string{"--config", configPath})
	})
	if codigo != 0 || stderr != "" {
		t.Fatalf("execução inesperada codigo=%d stderr=%q", codigo, stderr)
	}
	if !strings.Contains(stdout, "analysis: provedor=openai_compatible") || !strings.Contains(stdout, "zeta: provedor=openai_compatible") {
		t.Fatalf("listagem de modelos inesperada: %q", stdout)
	}
}

func TestExecutarExtracoesDeMetricas(t *testing.T) {
	tempDir := t.TempDir()
	jacoco := filepath.Join(tempDir, "jacoco.xml")
	if err := artefatos.EscreverTexto(jacoco, `<report><counter type="LINE" missed="10" covered="90"/></report>`); err != nil {
		t.Fatalf("fixture jacoco: %v", err)
	}
	stdout, _, codigo := capturarSaidas(t, func() int {
		return executarExtracaoJacoco([]string{"--xml", jacoco})
	})
	if codigo != 0 || strings.TrimSpace(stdout) != "90.00" {
		t.Fatalf("resultado jacoco inesperado codigo=%d stdout=%q", codigo, stdout)
	}

	pitDir := filepath.Join(tempDir, "pit")
	if err := artefatos.EscreverTexto(filepath.Join(pitDir, "mutations.xml"), `<mutations><mutation detected="true" status="KILLED"/></mutations>`); err != nil {
		t.Fatalf("fixture pit: %v", err)
	}
	stdout, _, codigo = capturarSaidas(t, func() int {
		return executarExtracaoPIT([]string{"--report-dir", pitDir})
	})
	if codigo != 0 || strings.TrimSpace(stdout) != "100.00" {
		t.Fatalf("resultado pit inesperado codigo=%d stdout=%q", codigo, stdout)
	}

	analise := dominio.RelatorioAnalise{Analises: []dominio.AnaliseMetodo{{Metodo: dominio.DescritorMetodo{IDMetodo: "m1"}, CaminhosExcecao: []dominio.CaminhoExcecao{{TipoExcecao: "IllegalArgumentException"}}}}}
	geracao := dominio.RelatorioGeracao{ArquivosTeste: []dominio.ArquivoTesteGerado{{Conteudo: "assertThrows(IllegalArgumentException.class, () -> subject.run());", IDsMetodosCobertos: []string{"m1"}}}}
	analysisPath := filepath.Join(tempDir, "analysis.json")
	generationPath := filepath.Join(tempDir, "generation.json")
	if err := artefatos.EscreverJSON(analysisPath, analise); err != nil {
		t.Fatalf("analysis fixture: %v", err)
	}
	if err := artefatos.EscreverJSON(generationPath, geracao); err != nil {
		t.Fatalf("generation fixture: %v", err)
	}
	stdout, _, codigo = capturarSaidas(t, func() int {
		return executarReproducaoExcecoes([]string{"--analysis", analysisPath, "--generation", generationPath})
	})
	if codigo != 0 || strings.TrimSpace(stdout) != "100.00" {
		t.Fatalf("resultado reprodução inesperado codigo=%d stdout=%q", codigo, stdout)
	}
}

func TestExecutarComparacaoFontesGeraArtefatos(t *testing.T) {
	tempDir := t.TempDir()
	witupPath := filepath.Join(tempDir, "witup.json")
	llmPath := filepath.Join(tempDir, "llm.json")
	relatorio := dominio.RelatorioAnalise{
		Analises: []dominio.AnaliseMetodo{{
			Metodo:          dominio.DescritorMetodo{IDMetodo: "m1", CaminhoArquivo: "src/A.java", NomeContainer: "A", NomeMetodo: "run", Assinatura: "A.run()", LinhaInicial: 10},
			CaminhosExcecao: []dominio.CaminhoExcecao{{IDCaminho: "p1", TipoExcecao: "IllegalArgumentException", Gatilho: "x == null"}},
		}},
	}
	if err := artefatos.EscreverJSON(witupPath, relatorio); err != nil {
		t.Fatalf("write witup: %v", err)
	}
	if err := artefatos.EscreverJSON(llmPath, relatorio); err != nil {
		t.Fatalf("write llm: %v", err)
	}
	stdout, stderr, codigo := capturarSaidas(t, func() int {
		return executarComparacaoFontes([]string{"--witup", witupPath, "--llm", llmPath, "--output-dir", filepath.Join(tempDir, "generated")})
	})
	if codigo != 0 || stderr != "" {
		t.Fatalf("comparação inesperada codigo=%d stderr=%q", codigo, stderr)
	}
	if !strings.Contains(stdout, "Artefatos de variantes") {
		t.Fatalf("resumo de comparação inesperado: %q", stdout)
	}
}

func TestExecutarIngestaoWITUPSobrescreveBaselineEMaterializaProjeto(t *testing.T) {
	cfg := configBaseTeste(t)
	criarBaselineWITUPTeste(t, cfg.Fluxo.RaizReplicacaoWIT)
	configPath := escreverConfigTeste(t, cfg)
	service := NovoServico(nil, nil)
	stdout, stderr, codigo := capturarSaidas(t, func() int {
		return executarIngestaoWITUP([]string{"--config", configPath, "--project-key", "sample"}, service)
	})
	if codigo != 0 || stderr != "" {
		t.Fatalf("ingestão inesperada codigo=%d stderr=%q", codigo, stderr)
	}
	if !strings.Contains(stdout, "Projetos importados") || !strings.Contains(stdout, "Projeto materializado") {
		t.Fatalf("saída de ingestão inesperada: %q", stdout)
	}
}

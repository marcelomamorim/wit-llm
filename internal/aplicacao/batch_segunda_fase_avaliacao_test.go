package aplicacao

import (
	"os"
	"path/filepath"
	"strings"
	"testing"

	"github.com/marceloamorim/witup-llm/internal/artefatos"
	"github.com/marceloamorim/witup-llm/internal/dominio"
	"github.com/marceloamorim/witup-llm/internal/llm"
)

func TestMaterializarGeracaoBatchDiretaPreservaCustomIDTokensERawResponse(t *testing.T) {
	dir := t.TempDir()
	responsesPath := filepath.Join(dir, "responses.jsonl")
	content := `{"custom_id":"sample-project/direct-tests/sample-foo/batch-01","response":{"status_code":200,"request_id":"req_123","body":{"id":"resp_123","usage":{"input_tokens":11,"input_tokens_details":{"cached_tokens":3},"output_tokens":7},"output":[{"type":"message","content":[{"type":"output_text","text":"{\"strategy_summary\":\"cobre contrato excepcional\",\"files\":[{\"relative_path\":\"src/test/java/sample/FooTest.java\",\"content\":\"import org.junit.Test; class FooTest { @Test public void testX() {} }\",\"covered_method_ids\":[\"m1\"],\"notes\":\"batch\"}]}"}]}]}}}`
	if err := os.WriteFile(responsesPath, []byte(content+"\n"), 0o644); err != nil {
		t.Fatalf("write responses: %v", err)
	}
	resultados, err := llm.LerResultadosBatch(responsesPath)
	if err != nil {
		t.Fatalf("LerResultadosBatch: %v", err)
	}
	workspace, err := artefatos.NovoEspacoTrabalho(dir, "run")
	if err != nil {
		t.Fatalf("NovoEspacoTrabalho: %v", err)
	}
	cfg := &dominio.ConfigAplicacao{Projeto: dominio.ConfigProjeto{Raiz: dir, TestFramework: "junit4"}}
	metodos := []dominio.DescritorMetodo{{
		IDMetodo:      "m1",
		NomeContainer: "sample.Foo",
		NomeMetodo:    "x",
		Assinatura:    "void x()",
		Origem:        "class Foo { void x() {} }",
	}}
	report, generationPath, err := materializarGeracaoBatchDireta(
		cfg,
		dominio.ConfigModelo{Modelo: "gpt-5.4-mini"},
		"openai_main",
		"overview",
		"sample-project",
		metodos,
		filepath.Join(dir, "analysis.json"),
		resultados,
		workspace,
	)
	if err != nil {
		t.Fatalf("materializarGeracaoBatchDireta: %v", err)
	}
	if report.RequestCount != 1 || report.InputTokens != 11 || report.OutputTokens != 7 {
		t.Fatalf("telemetria inesperada: %+v", report)
	}
	if len(report.ArquivosTeste) != 1 || !strings.Contains(report.ArquivosTeste[0].Conteudo, "FooTest") {
		t.Fatalf("arquivos materializados inesperados: %+v", report.ArquivosTeste)
	}
	if _, err := os.Stat(generationPath); err != nil {
		t.Fatalf("generation.json não foi gravado: %v", err)
	}
	if _, err := os.Stat(filepath.Join(workspace.Testes, "src/test/java/sample/FooTest.java")); err != nil {
		t.Fatalf("teste gerado não foi gravado: %v", err)
	}
	batchInfo, ok := report.RespostasBrutas[0]["_batch"].(map[string]interface{})
	if !ok || batchInfo["custom_id"] != "sample-project/direct-tests/sample-foo/batch-01" {
		t.Fatalf("raw response não preservou custom_id: %#v", report.RespostasBrutas)
	}
}

func TestMaterializarGeracaoBatchRegistraRespostaAusenteSemFalhar(t *testing.T) {
	dir := t.TempDir()
	workspace, err := artefatos.NovoEspacoTrabalho(dir, "run")
	if err != nil {
		t.Fatalf("NovoEspacoTrabalho: %v", err)
	}
	report, _, err := materializarGeracaoBatch(
		workspace,
		dir,
		"openai_main",
		filepath.Join(dir, "analysis.json"),
		[]string{"sample.Foo"},
		func(string) int { return 1 },
		func(string, int) string { return "missing/custom-id" },
		func(string, int) (string, string) { return "user", "system" },
		map[string]llm.LinhaResultadoBatch{},
		dominio.ConfigModelo{Modelo: "gpt-5.4-mini"},
	)
	if err != nil {
		t.Fatalf("materializarGeracaoBatch: %v", err)
	}
	if report.RequestCount != 1 || len(report.ArquivosTeste) != 0 {
		t.Fatalf("relatório inesperado para resposta ausente: %+v", report)
	}
	if len(report.IntervencoesHarness) != 1 || !strings.Contains(report.IntervencoesHarness[0], "batch_response_missing") {
		t.Fatalf("intervenção de resposta ausente não registrada: %+v", report.IntervencoesHarness)
	}
}

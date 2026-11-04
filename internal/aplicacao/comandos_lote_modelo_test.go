package aplicacao

import (
	"encoding/json"
	"os"
	"path/filepath"
	"testing"

	"github.com/marceloamorim/witup-llm/internal/configuracao"
	"github.com/marceloamorim/witup-llm/internal/dominio"
	"github.com/marceloamorim/witup-llm/internal/llm"
)

func TestPerfilQuatroModelosDiretos(t *testing.T) {
	path := "../../pipelines/quatro-modelos-diretos.json"
	cfg, err := configuracao.CarregarParaBatch(path)
	if err != nil {
		t.Fatal(err)
	}
	if len(cfg.Modelos) != 4 {
		t.Fatalf("esperados 4 modelos: %v", cfg.Modelos)
	}
	counts := map[string]int{}
	for key, m := range cfg.Modelos {
		counts[m.Provedor]++
		if err = llm.ValidarModeloDireto(m); err != nil {
			t.Fatalf("%s: %v", key, err)
		}
	}
	if counts["openai"] != 2 || counts["gemini"] != 1 || counts["anthropic"] != 1 {
		t.Fatal(counts)
	}
	if cfg.Modelos["gpt_standard"].EsforcoRaciocinio != "" || cfg.Modelos["gpt_reasoning"].EsforcoRaciocinio != "low" {
		t.Fatal("perfis GPT devem diferir")
	}
}
func TestComandoLoteDryRunNaoExigeChavesENaoMaterializaIncompleto(t *testing.T) {
	dir := t.TempDir()
	m := dominio.ConfigModelo{Provedor: "gemini", Modelo: "gemini-3.8-flash", URLBase: "https://example.invalid/v1beta", VariavelAmbienteChaveAPI: "WIT_CLI_TEST_KEY", MaximoTokensSaida: 8192, SegundosTimeout: 60, EsforcoRaciocinio: "low"}
	t.Setenv("WIT_CLI_TEST_KEY", "")
	cfg := map[string]interface{}{"version": "1", "models": map[string]dominio.ConfigModelo{"gemini": m}}
	data, _ := json.Marshal(cfg)
	config := filepath.Join(dir, "config.json")
	_ = os.WriteFile(config, data, 0600)
	row, err := llm.ConstruirLinhaBatchResponses("pair-1", m, "sistema JSON", "usuario", dominio.OpcoesRequisicaoLLM{})
	if err != nil {
		t.Fatal(err)
	}
	input := filepath.Join(dir, "requests.jsonl")
	output := filepath.Join(dir, "responses.jsonl")
	if err = llm.EscreverJSONLBatch(input, []llm.LinhaRequisicaoBatch{row}); err != nil {
		t.Fatal(err)
	}
	args := []string{"--config", config, "--model", "gemini", "--requests", input, "--output", output, "--dry-run"}
	if code := executarLoteModelo(args); code != 0 {
		t.Fatalf("dry-run: %d", code)
	}
	if code := executarLoteModelo(append(args, "--require-complete")); code != 1 {
		t.Fatalf("lote incompleto aceito: %d", code)
	}
	if _, err = os.Stat(output); !os.IsNotExist(err) {
		t.Fatal("dry-run escreveu arquivo")
	}
	if code := executarLoteModelo([]string{"--config", config, "--require-complete"}); code != 2 {
		t.Fatalf("flags inválidas aceitas: %d", code)
	}
}

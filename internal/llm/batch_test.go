package llm

import (
	"os"
	"path/filepath"
	"strings"
	"testing"

	"github.com/marceloamorim/witup-llm/internal/dominio"
)

func TestConstruirLinhaBatchResponsesUsaEndpointEModelo(t *testing.T) {
	linha, err := ConstruirLinhaBatchResponses(
		"project/wit-context/sample/batch-01",
		dominio.ConfigModelo{Modelo: "gpt-5.4-mini", Endpoint: "/v1/responses"},
		"sistema",
		"usuario",
		dominio.OpcoesRequisicaoLLM{PromptCacheKey: "cache-key"},
	)
	if err != nil {
		t.Fatalf("ConstruirLinhaBatchResponses: %v", err)
	}
	if linha.CustomID != "project/wit-context/sample/batch-01" {
		t.Fatalf("custom_id inesperado: %#v", linha)
	}
	if linha.Method != "POST" || linha.URL != "/v1/responses" {
		t.Fatalf("endpoint Batch inesperado: %#v", linha)
	}
	if linha.Body["model"] != "gpt-5.4-mini" {
		t.Fatalf("modelo inesperado: %#v", linha.Body)
	}
	if linha.Body["prompt_cache_key"] != "cache-key" {
		t.Fatalf("prompt_cache_key inesperada: %#v", linha.Body)
	}
}

func TestEscreverJSONLBatchRejeitaCustomIDDuplicado(t *testing.T) {
	path := filepath.Join(t.TempDir(), "requests.jsonl")
	linha := LinhaRequisicaoBatch{CustomID: "x", Method: "POST", URL: "/v1/responses", Body: map[string]interface{}{"model": "gpt-5.4-mini"}}
	err := EscreverJSONLBatch(path, []LinhaRequisicaoBatch{linha, linha})
	if err == nil || !strings.Contains(err.Error(), "duplicado") {
		t.Fatalf("esperava erro de custom_id duplicado, recebi %v", err)
	}
}

func TestLerResultadosBatchMapeiaForaDeOrdemEExtraiResposta(t *testing.T) {
	path := filepath.Join(t.TempDir(), "responses.jsonl")
	content := strings.Join([]string{
		`{"custom_id":"b","response":{"status_code":200,"body":{"id":"resp_b","usage":{"input_tokens":10,"input_tokens_details":{"cached_tokens":2},"output_tokens":4},"output":[{"type":"message","content":[{"type":"output_text","text":"{\"ok\":\"b\"}"}]}]}}}`,
		`{"custom_id":"a","response":{"status_code":200,"body":{"id":"resp_a","usage":{"input_tokens":20,"input_tokens_details":{"cached_tokens":0},"output_tokens":8},"output":[{"type":"message","content":[{"type":"output_text","text":"{\"ok\":\"a\"}"}]}]}}}`,
	}, "\n")
	if err := os.WriteFile(path, []byte(content), 0o644); err != nil {
		t.Fatalf("write: %v", err)
	}
	resultados, err := LerResultadosBatch(path)
	if err != nil {
		t.Fatalf("LerResultadosBatch: %v", err)
	}
	resposta, err := ExtrairRespostaBatch(dominio.ConfigModelo{Modelo: "gpt-5.4-mini"}, resultados["a"])
	if err != nil {
		t.Fatalf("ExtrairRespostaBatch: %v", err)
	}
	if resposta.IDResposta != "resp_a" || resposta.Payload["ok"] != "a" {
		t.Fatalf("resposta inesperada: %+v", resposta)
	}
}

package llm

import (
	"context"
	"encoding/json"
	"io"
	"net/http"
	"os"
	"path/filepath"
	"strings"
	"testing"
	"time"

	"github.com/marceloamorim/witup-llm/internal/dominio"
)

func modeloDiretoTeste(provider string) dominio.ConfigModelo {
	m := dominio.ConfigModelo{Provedor: provider, Modelo: "gpt-4.1-2025-04-14", URLBase: "https://example.test/v1", VariavelAmbienteChaveAPI: "WIT_TEST_KEY", MaximoTokensSaida: 8192, SegundosTimeout: 5}
	if provider == "gemini" {
		m.Modelo = "gemini-3.8-flash"
		m.EsforcoRaciocinio = "low"
	}
	if provider == "anthropic" {
		m.Modelo = "claude-sonnet-5-5"
		m.EsforcoRaciocinio = "low"
	}
	return m
}
func respostaDiretaTeste(provider string) string {
	switch provider {
	case "gemini":
		return `{"responseId":"g1","candidates":[{"finishReason":"STOP","content":{"parts":[{"thought":true,"text":"ignorar"},{"text":"{\"files\":[]}"}]}}],"usageMetadata":{"promptTokenCount":100,"cachedContentTokenCount":20,"candidatesTokenCount":30,"thoughtsTokenCount":5}}`
	case "anthropic":
		return `{"id":"a1","stop_reason":"end_turn","content":[{"type":"thinking","thinking":"ignorar"},{"type":"text","text":"{\"files\":[]}"}],"usage":{"input_tokens":70,"cache_read_input_tokens":20,"cache_creation_input_tokens":10,"output_tokens":35}}`
	default:
		return `{"id":"o1","status":"completed","output":[{"type":"message","content":[{"type":"output_text","text":"{\"files\":[]}"}]}],"usage":{"input_tokens":100,"output_tokens":35,"input_tokens_details":{"cached_tokens":20}}}`
	}
}

func TestProvedoresDiretosProtocolosENormalizacao(t *testing.T) {
	t.Setenv("WIT_TEST_KEY", "segredo-de-teste")
	for _, provider := range []string{"openai", "gemini", "anthropic"} {
		t.Run(provider, func(t *testing.T) {
			m := modeloDiretoTeste(provider)
			c := NovoCliente()
			c.httpClient = &http.Client{Transport: roundTripFunc(func(r *http.Request) (*http.Response, error) {
				var body map[string]interface{}
				if err := json.NewDecoder(r.Body).Decode(&body); err != nil {
					t.Fatal(err)
				}
				switch provider {
				case "openai":
					if r.URL.Path != "/v1/responses" || r.Header.Get("Authorization") != "Bearer segredo-de-teste" {
						t.Fatalf("requisição: %v", r)
					}
				case "gemini":
					if r.URL.Path != "/v1/models/gemini-3.8-flash:generateContent" || r.Header.Get("x-goog-api-key") != "segredo-de-teste" {
						t.Fatalf("requisição: %v", r)
					}
					if body["input"] != nil || body["generationConfig"].(map[string]interface{})["responseMimeType"] != "application/json" {
						t.Fatal(body)
					}
				case "anthropic":
					if r.URL.Path != "/v1/messages" || r.Header.Get("x-api-key") != "segredo-de-teste" || r.Header.Get("anthropic-version") == "" {
						t.Fatalf("requisição: %v", r)
					}
					if body["system"] != "sistema JSON" || body["temperature"] != nil || body["thinking"] == nil {
						t.Fatal(body)
					}
				}
				return &http.Response{StatusCode: 200, Header: http.Header{}, Body: io.NopCloser(strings.NewReader(respostaDiretaTeste(provider))), Request: r}, nil
			})}
			r, err := c.CompletarJSON(m, "sistema JSON", "usuario", dominio.OpcoesRequisicaoLLM{})
			if err != nil {
				t.Fatal(err)
			}
			if r.InputTokens != 100 || r.OutputTokens != 35 || r.CachedInputTokens != 20 || r.RawText != `{"files":[]}` {
				t.Fatalf("resposta: %+v", r)
			}
		})
	}
}

func TestProvedoresRejeitamRespostasTruncadas(t *testing.T) {
	for _, tc := range []struct{ provider, old, new string }{{"openai", "completed", "incomplete"}, {"gemini", "STOP", "MAX_TOKENS"}, {"anthropic", "end_turn", "max_tokens"}} {
		var raw map[string]interface{}
		_ = json.Unmarshal([]byte(strings.ReplaceAll(respostaDiretaTeste(tc.provider), tc.old, tc.new)), &raw)
		if _, err := interpretarRespostaProvedor(modeloDiretoTeste(tc.provider), raw); err == nil {
			t.Fatalf("aceitou truncamento %s", tc.provider)
		}
	}
}

func TestGPT6OmiteTemperaturaEMigraCache(t *testing.T) {
	m := modeloDiretoTeste("openai")
	m.Modelo = "gpt-6.1-sol"
	m.EsforcoRaciocinio = "low"
	m.RetencaoCachePrompt = "24h"
	body := construirCorpoResponses(m, "s", "u", dominio.OpcoesRequisicaoLLM{})
	if body["temperature"] != nil || body["prompt_cache_retention"] != nil || body["prompt_cache_options"] == nil {
		t.Fatal(body)
	}
	m = modeloDiretoTeste("openai")
	body = construirCorpoResponses(m, "s", "u", dominio.OpcoesRequisicaoLLM{})
	if body["reasoning"] != nil || body["temperature"] != float64(0) {
		t.Fatal(body)
	}
}

func prepararLoteTeste(t *testing.T, m dominio.ConfigModelo) (string, string) {
	t.Helper()
	dir := t.TempDir()
	input := filepath.Join(dir, "requests.jsonl")
	output := filepath.Join(dir, "responses.jsonl")
	var rows []LinhaRequisicaoBatch
	for _, id := range []string{"wit-1", "direct-1"} {
		r, err := ConstruirLinhaBatchResponses(id, m, "JSON sistema", "usuario", dominio.OpcoesRequisicaoLLM{})
		if err != nil {
			t.Fatal(err)
		}
		rows = append(rows, r)
	}
	if err := EscreverJSONLBatch(input, rows); err != nil {
		t.Fatal(err)
	}
	return input, output
}
func TestLoteLocalRetomaSemRepetirChamadasEProtegeConfiguracao(t *testing.T) {
	t.Setenv("WIT_TEST_KEY", "chave")
	m := modeloDiretoTeste("gemini")
	input, output := prepararLoteTeste(t, m)
	calls := 0
	c := NovoCliente()
	c.httpClient = &http.Client{Transport: roundTripFunc(func(r *http.Request) (*http.Response, error) {
		calls++
		return &http.Response{StatusCode: 200, Header: http.Header{}, Body: io.NopCloser(strings.NewReader(respostaDiretaTeste("gemini"))), Request: r}, nil
	})}
	s, err := c.ExecutarLoteLocal(context.Background(), m, input, output, OpcoesLote{Limite: 1})
	if err != nil || s.Sucessos != 1 {
		t.Fatalf("%+v %v", s, err)
	}
	s, err = c.ExecutarLoteLocal(context.Background(), m, input, output, OpcoesLote{})
	if err != nil || s.Reutilizados != 1 || s.Sucessos != 1 || calls != 2 {
		t.Fatalf("%+v %v calls=%d", s, err, calls)
	}
	s, err = c.ExecutarLoteLocal(context.Background(), m, input, output, OpcoesLote{})
	if err != nil || s.Reutilizados != 2 || calls != 2 {
		t.Fatalf("%+v %v", s, err)
	}
	results, err := LerResultadosBatch(output)
	if err != nil || len(results) != 2 {
		t.Fatalf("%v %v", results, err)
	}
	for _, r := range results {
		if _, err = ExtrairRespostaBatch(m, r); err != nil {
			t.Fatal(err)
		}
	}
	data, _ := os.ReadFile(output)
	if !strings.Contains(string(data), "raw_provider_response") || strings.Contains(string(data), "chave") {
		t.Fatal("auditoria ausente ou chave vazada")
	}
	m.MaximoTokensSaida++
	if _, err = c.ExecutarLoteLocal(context.Background(), m, input, output, OpcoesLote{}); err == nil || calls != 2 {
		t.Fatal("misturou configurações")
	}
}
func TestLoteDryRunSemChaveNemArquivosDeSaida(t *testing.T) {
	t.Setenv("WIT_TEST_KEY", "")
	m := modeloDiretoTeste("anthropic")
	input, output := prepararLoteTeste(t, m)
	c := NovoCliente()
	s, err := c.ExecutarLoteLocal(context.Background(), m, input, output, OpcoesLote{DryRun: true})
	if err != nil || s.Total != 2 {
		t.Fatalf("%+v %v", s, err)
	}
	if _, err = os.Stat(output); !os.IsNotExist(err) {
		t.Fatal("dry-run escreveu saída")
	}
}
func TestLoteFalhaPreservaOriginalETentaNovamente(t *testing.T) {
	t.Setenv("WIT_TEST_KEY", "chave")
	m := modeloDiretoTeste("anthropic")
	input, output := prepararLoteTeste(t, m)
	calls := 0
	c := NovoCliente()
	c.httpClient = &http.Client{Transport: roundTripFunc(func(r *http.Request) (*http.Response, error) {
		calls++
		body := respostaDiretaTeste("anthropic")
		if calls == 1 {
			body = strings.ReplaceAll(body, "end_turn", "max_tokens")
		}
		return &http.Response{StatusCode: 200, Header: http.Header{}, Body: io.NopCloser(strings.NewReader(body)), Request: r}, nil
	})}
	s, err := c.ExecutarLoteLocal(context.Background(), m, input, output, OpcoesLote{})
	if err == nil || s.Falhas != 1 || s.Sucessos != 1 {
		t.Fatalf("%+v %v", s, err)
	}
	data, _ := os.ReadFile(output)
	if !strings.Contains(string(data), "max_tokens") {
		t.Fatal("perdeu original")
	}
	s, err = c.ExecutarLoteLocal(context.Background(), m, input, output, OpcoesLote{})
	if err != nil || s.Reutilizados != 1 || s.Sucessos != 1 || calls != 3 {
		t.Fatalf("%+v %v", s, err)
	}
}
func TestLotePreflightDuplicatasLockECancelamento(t *testing.T) {
	t.Setenv("WIT_TEST_KEY", "chave")
	m := modeloDiretoTeste("openai")
	input, output := prepararLoteTeste(t, m)
	c := NovoCliente()
	data, _ := os.ReadFile(input)
	_ = os.WriteFile(input, append(data, data...), 0600)
	if _, err := c.ExecutarLoteLocal(context.Background(), m, input, output, OpcoesLote{DryRun: true}); err == nil {
		t.Fatal("aceitou duplicados")
	}
	_ = os.WriteFile(input, data, 0600)
	_ = os.Mkdir(output+".lock", 0700)
	if _, err := c.ExecutarLoteLocal(context.Background(), m, input, output, OpcoesLote{}); err == nil {
		t.Fatal("ignorou lock")
	}
	_ = os.Remove(output + ".lock")
	ctx, cancel := context.WithCancel(context.Background())
	cancel()
	if _, err := c.ExecutarLoteLocal(ctx, m, input, output, OpcoesLote{}); err == nil {
		t.Fatal("ignorou cancelamento")
	}
}
func TestRequestJSONCancelaRetryAfter(t *testing.T) {
	c := NovoCliente()
	ctx, cancel := context.WithCancel(context.Background())
	c.httpClient = &http.Client{Transport: roundTripFunc(func(r *http.Request) (*http.Response, error) {
		cancel()
		return &http.Response{StatusCode: 429, Header: http.Header{"Retry-After": []string{"300"}}, Body: io.NopCloser(strings.NewReader(`{"error":"limit"}`)), Request: r}, nil
	})}
	m := modeloDiretoTeste("openai")
	m.MaximoTentativas = 2
	start := time.Now()
	if _, err := c.requestJSONContext(ctx, "POST", m.URLBase, map[string]interface{}{}, m, nil); err == nil {
		t.Fatal("cancelamento ignorado")
	}
	if time.Since(start) > time.Second {
		t.Fatal("Retry-After não cancelável")
	}
}

func TestLoteInterrompeCredencialInvalidaEPreservaErro(t *testing.T) {
	t.Setenv("WIT_TEST_KEY", "chave")
	m := modeloDiretoTeste("gemini")
	input, output := prepararLoteTeste(t, m)
	calls := 0
	c := NovoCliente()
	c.httpClient = &http.Client{Transport: roundTripFunc(func(r *http.Request) (*http.Response, error) {
		calls++
		return &http.Response{StatusCode: 401, Header: http.Header{}, Body: io.NopCloser(strings.NewReader(`{"error":"invalid key"}`)), Request: r}, nil
	})}
	s, err := c.ExecutarLoteLocal(context.Background(), m, input, output, OpcoesLote{})
	if err == nil || calls != 1 || s.Falhas != 1 {
		t.Fatalf("%+v %v calls=%d", s, err, calls)
	}
	records, err := LerResultadosBatch(output)
	if err != nil || records["wit-1"].Error == nil {
		t.Fatalf("erro não preservado: %v %v", records, err)
	}
}

func TestLoteRejeitaArquivoParcialAntesDeQualquerChamada(t *testing.T) {
	t.Setenv("WIT_TEST_KEY", "chave")
	m := modeloDiretoTeste("openai")
	input, output := prepararLoteTeste(t, m)
	if err := os.WriteFile(output, []byte(`{"custom_id":`), 0600); err != nil {
		t.Fatal(err)
	}
	if _, err := NovoCliente().ExecutarLoteLocal(context.Background(), m, input, output, OpcoesLote{}); err == nil {
		t.Fatal("aceitou journal parcial")
	}
}

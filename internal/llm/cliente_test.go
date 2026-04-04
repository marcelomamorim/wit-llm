package llm

import (
	"encoding/json"
	"io"
	"net/http"
	"strings"
	"testing"
	"time"

	"github.com/marceloamorim/witup-llm/internal/dominio"
)

func TestNewClientUsesPerRequestTimeoutsOnly(t *testing.T) {
	client := NovoCliente()
	if client.httpClient.Timeout != 0 {
		t.Fatalf("expected zero client timeout, got %v", client.httpClient.Timeout)
	}
}

func TestRequestJSONDoesNotSendBodyOrContentTypeForGET(t *testing.T) {
	var contentType string
	var body string

	client := NovoCliente()
	client.httpClient = &http.Client{
		Transport: roundTripFunc(func(r *http.Request) (*http.Response, error) {
			contentType = r.Header.Get("Content-Type")
			if r.Body != nil {
				data, err := io.ReadAll(r.Body)
				if err != nil {
					t.Fatalf("read request body: %v", err)
				}
				body = string(data)
			}
			return &http.Response{
				StatusCode: http.StatusOK,
				Header:     http.Header{"Content-Type": []string{"application/json"}},
				Body:       io.NopCloser(strings.NewReader(`{"status":"ok"}`)),
				Request:    r,
			}, nil
		}),
	}
	model := dominio.ConfigModelo{
		URLBase:          "https://example.test",
		SegundosTimeout:  600,
		MaximoTentativas: 0,
	}
	payload, err := client.requestJSON(http.MethodGet, model.URLBase, nil, model, nil)
	if err != nil {
		t.Fatalf("requestJSON returned unexpected error: %v", err)
	}
	if payload["status"] != "ok" {
		t.Fatalf("unexpected payload: %#v", payload)
	}
	if contentType != "" {
		t.Fatalf("expected no Content-Type for GET without body, got %q", contentType)
	}
	if body != "" {
		t.Fatalf("expected empty GET body, got %q", body)
	}
}

func TestCompletarJSONUsaResponsesAPIComPromptCacheKey(t *testing.T) {
	t.Setenv("OPENAI_API_KEY", "test-key")

	var caminho string
	var promptCacheKey string
	var reasoning map[string]interface{}
	var payload map[string]interface{}

	client := NovoCliente()
	client.httpClient = &http.Client{
		Transport: roundTripFunc(func(r *http.Request) (*http.Response, error) {
			caminho = r.URL.Path
			corpo, err := io.ReadAll(r.Body)
			if err != nil {
				t.Fatalf("read request body: %v", err)
			}
			if err := json.Unmarshal(corpo, &payload); err != nil {
				t.Fatalf("unmarshal request body: %v", err)
			}
			promptCacheKey = payload["prompt_cache_key"].(string)
			reasoning, _ = payload["reasoning"].(map[string]interface{})
			return &http.Response{
				StatusCode: http.StatusOK,
				Header:     http.Header{"Content-Type": []string{"application/json"}},
				Body: io.NopCloser(strings.NewReader(`{
					"id":"resp_123",
					"output":[
						{
							"type":"message",
							"content":[{"type":"output_text","text":"{\"ok\":true}"}]
						}
					]
				}`)),
				Request: r,
			}, nil
		}),
	}

	resposta, err := client.CompletarJSON(dominio.ConfigModelo{
		Provedor:                 "openai_compatible",
		Modelo:                   "gpt-5.4",
		URLBase:                  "https://api.openai.com/v1",
		VariavelAmbienteChaveAPI: "OPENAI_API_KEY",
		SegundosTimeout:          120,
		EsforcoRaciocinio:        "low",
		RetencaoCachePrompt:      "24h",
		NivelServico:             "auto",
	}, "sistema", "usuario", dominio.OpcoesRequisicaoLLM{PromptCacheKey: "cache-key-1"})
	if err != nil {
		t.Fatalf("CompletarJSON retornou erro inesperado: %v", err)
	}
	if caminho != "/v1/responses" {
		t.Fatalf("esperava /v1/responses, recebi %q", caminho)
	}
	if promptCacheKey != "cache-key-1" {
		t.Fatalf("prompt_cache_key inesperada: %q", promptCacheKey)
	}
	if reasoning["effort"] != "low" {
		t.Fatalf("reasoning inesperado: %#v", reasoning)
	}
	if _, existe := payload["temperature"]; existe {
		t.Fatalf("temperature não deveria ser enviado para gpt-5.4: %#v", payload)
	}
	if resposta.Payload["ok"] != true {
		t.Fatalf("payload inesperado: %#v", resposta.Payload)
	}
	if resposta.IDResposta != "resp_123" {
		t.Fatalf("id de resposta inesperado: %q", resposta.IDResposta)
	}
	if strings.TrimSpace(resposta.RawText) != "{\"ok\":true}" {
		t.Fatalf("texto bruto inesperado: %q", resposta.RawText)
	}
}

func TestCompletarJSONUsaPreviousResponseIDNaResponsesAPI(t *testing.T) {
	t.Setenv("OPENAI_API_KEY", "test-key")

	var payload map[string]interface{}

	client := NovoCliente()
	client.httpClient = &http.Client{
		Transport: roundTripFunc(func(r *http.Request) (*http.Response, error) {
			corpo, err := io.ReadAll(r.Body)
			if err != nil {
				t.Fatalf("read request body: %v", err)
			}
			if err := json.Unmarshal(corpo, &payload); err != nil {
				t.Fatalf("unmarshal request body: %v", err)
			}
			return &http.Response{
				StatusCode: http.StatusOK,
				Header:     http.Header{"Content-Type": []string{"application/json"}},
				Body: io.NopCloser(strings.NewReader(`{
					"id":"resp_456",
					"output":[
						{
							"type":"message",
							"content":[{"type":"output_text","text":"{\"ok\":true}"}]
						}
					]
				}`)),
				Request: r,
			}, nil
		}),
	}

	_, err := client.CompletarJSON(dominio.ConfigModelo{
		Provedor:                 "openai_compatible",
		Modelo:                   "gpt-5.4",
		URLBase:                  "https://api.openai.com/v1",
		VariavelAmbienteChaveAPI: "OPENAI_API_KEY",
		SegundosTimeout:          120,
		EsforcoRaciocinio:        "low",
	}, "sistema", "usuario", dominio.OpcoesRequisicaoLLM{
		PreviousResponseID: "resp_anterior",
		PreservarEstado:    true,
	})
	if err != nil {
		t.Fatalf("CompletarJSON retornou erro inesperado: %v", err)
	}
	if payload["previous_response_id"] != "resp_anterior" {
		t.Fatalf("previous_response_id inesperado: %#v", payload)
	}
	if _, existe := payload["store"]; existe {
		t.Fatalf("store não deveria ser enviado em chamadas stateful: %#v", payload)
	}
}

func TestCompletarJSONEncurtaPromptCacheKeyLonga(t *testing.T) {
	t.Setenv("OPENAI_API_KEY", "test-key")

	var payload map[string]interface{}

	client := NovoCliente()
	client.httpClient = &http.Client{
		Transport: roundTripFunc(func(r *http.Request) (*http.Response, error) {
			corpo, err := io.ReadAll(r.Body)
			if err != nil {
				t.Fatalf("read request body: %v", err)
			}
			if err := json.Unmarshal(corpo, &payload); err != nil {
				t.Fatalf("unmarshal request body: %v", err)
			}
			return &http.Response{
				StatusCode: http.StatusOK,
				Header:     http.Header{"Content-Type": []string{"application/json"}},
				Body: io.NopCloser(strings.NewReader(`{
					"id":"resp_789",
					"output":[
						{
							"type":"message",
							"content":[{"type":"output_text","text":"{\"ok\":true}"}]
						}
					]
				}`)),
				Request: r,
			}, nil
		}),
	}

	chaveOriginal := "agentic:helper-method-with-a-very-long-signature:expath-extractor:sample-container:linha-123"
	_, err := client.CompletarJSON(dominio.ConfigModelo{
		Provedor:                 "openai_compatible",
		Modelo:                   "gpt-5.4",
		URLBase:                  "https://api.openai.com/v1",
		VariavelAmbienteChaveAPI: "OPENAI_API_KEY",
		SegundosTimeout:          120,
		EsforcoRaciocinio:        "low",
	}, "sistema", "usuario", dominio.OpcoesRequisicaoLLM{
		PromptCacheKey: chaveOriginal,
	})
	if err != nil {
		t.Fatalf("CompletarJSON retornou erro inesperado: %v", err)
	}

	chaveEnviada, ok := payload["prompt_cache_key"].(string)
	if !ok {
		t.Fatalf("prompt_cache_key não foi enviada: %#v", payload)
	}
	if len(chaveEnviada) > 64 {
		t.Fatalf("prompt_cache_key excedeu o limite: %q", chaveEnviada)
	}
	if chaveEnviada == chaveOriginal {
		t.Fatalf("prompt_cache_key longa deveria ter sido normalizada: %q", chaveEnviada)
	}
}

func TestCompletarJSONMantemTemperatureParaModelosNaoGPT5(t *testing.T) {
	t.Setenv("OPENAI_API_KEY", "test-key")

	var payload map[string]interface{}

	client := NovoCliente()
	client.httpClient = &http.Client{
		Transport: roundTripFunc(func(r *http.Request) (*http.Response, error) {
			corpo, err := io.ReadAll(r.Body)
			if err != nil {
				t.Fatalf("read request body: %v", err)
			}
			if err := json.Unmarshal(corpo, &payload); err != nil {
				t.Fatalf("unmarshal request body: %v", err)
			}
			return &http.Response{
				StatusCode: http.StatusOK,
				Header:     http.Header{"Content-Type": []string{"application/json"}},
				Body: io.NopCloser(strings.NewReader(`{
					"output":[
						{
							"type":"message",
							"content":[{"type":"output_text","text":"{\"ok\":true}"}]
						}
					]
				}`)),
				Request: r,
			}, nil
		}),
	}

	_, err := client.CompletarJSON(dominio.ConfigModelo{
		Provedor:                 "openai_compatible",
		Modelo:                   "gpt-4.1-mini",
		URLBase:                  "https://api.openai.com/v1",
		VariavelAmbienteChaveAPI: "OPENAI_API_KEY",
		SegundosTimeout:          120,
		Temperature:              0.2,
	}, "sistema", "usuario", dominio.OpcoesRequisicaoLLM{})
	if err != nil {
		t.Fatalf("CompletarJSON retornou erro inesperado: %v", err)
	}

	if payload["temperature"] != 0.2 {
		t.Fatalf("temperature deveria ser enviada para modelos fora da família gpt-5: %#v", payload)
	}
}

type roundTripFunc func(*http.Request) (*http.Response, error)

func (f roundTripFunc) RoundTrip(req *http.Request) (*http.Response, error) {
	return f(req)
}

func TestExtrairPayloadJSONEAuxiliares(t *testing.T) {
	texto := "prefixo```json\n{\"ok\":true}\n```sufixo"
	payload, err := ExtrairPayloadJSON(texto)
	if err != nil {
		t.Fatalf("extrair payload JSON: %v", err)
	}
	if payload != "{\"ok\":true}" {
		t.Fatalf("payload inesperado: %q", payload)
	}
	if idx := findMatchingJSONEnd("{\"a\": [1, 2, {\"b\": true}]}"); idx <= 0 {
		t.Fatalf("esperava índice final válido, recebi %d", idx)
	}
	if stripFence("```json\n{\"ok\":true}\n```") != "{\"ok\":true}" {
		t.Fatalf("stripFence deveria remover cercas markdown")
	}
}

func TestAuxiliaresResponses(t *testing.T) {
	if !aceitaTemperaturaResponses("gpt-4.1-mini") {
		t.Fatalf("modelos fora da família gpt-5 devem aceitar temperature")
	}
	if aceitaTemperaturaResponses("gpt-5.4") {
		t.Fatalf("gpt-5.4 não deveria aceitar temperature")
	}
	if got := normalizarPromptCacheKey(strings.Repeat("x", 80)); len(got) > 64 {
		t.Fatalf("prompt_cache_key normalizada excedeu limite: %q", got)
	}
	header := http.Header{"X-Request-Id": []string{"req-1"}}
	if primeiroHeader(header, "request-id", "x-request-id") != "req-1" {
		t.Fatalf("esperava encontrar header com fallback case-insensitive")
	}
}

func TestSondarOpenAICompatibleEOllama(t *testing.T) {
	t.Setenv("OPENAI_API_KEY", "test-key")

	client := NovoCliente()
	client.httpClient = &http.Client{
		Transport: roundTripFunc(func(r *http.Request) (*http.Response, error) {
			switch r.URL.Path {
			case "/v1/models":
				return &http.Response{
					StatusCode: http.StatusOK,
					Header:     http.Header{"Content-Type": []string{"application/json"}},
					Body:       io.NopCloser(strings.NewReader(`{"data":[{"id":"gpt-5.4"}]}`)),
					Request:    r,
				}, nil
			case "/api/tags":
				return &http.Response{
					StatusCode: http.StatusOK,
					Header:     http.Header{"Content-Type": []string{"application/json"}},
					Body:       io.NopCloser(strings.NewReader(`{"models":[{"name":"llama3"}]}`)),
					Request:    r,
				}, nil
			default:
				t.Fatalf("caminho inesperado: %s", r.URL.Path)
				return nil, nil
			}
		}),
	}

	payload, err := client.Sondar(dominio.ConfigModelo{
		Provedor:                 "openai_compatible",
		Modelo:                   "gpt-5.4",
		URLBase:                  "https://api.openai.com/v1",
		VariavelAmbienteChaveAPI: "OPENAI_API_KEY",
		SegundosTimeout:          10,
	})
	if err != nil {
		t.Fatalf("sondar openai: %v", err)
	}
	if payload["models_available"] != 1 {
		t.Fatalf("payload openai inesperado: %#v", payload)
	}

	payload, err = client.Sondar(dominio.ConfigModelo{
		Provedor:         "ollama",
		Modelo:           "llama3",
		URLBase:          "http://ollama.local",
		SegundosTimeout:  10,
		MaximoTentativas: 1,
	})
	if err != nil {
		t.Fatalf("sondar ollama: %v", err)
	}
	if payload["models_available"] != 1 {
		t.Fatalf("payload ollama inesperado: %#v", payload)
	}
}

func TestCompletarTextoComOllamaEProviderInvalido(t *testing.T) {
	client := NovoCliente()
	client.httpClient = &http.Client{
		Transport: roundTripFunc(func(r *http.Request) (*http.Response, error) {
			if r.URL.Path != "/api/generate" {
				t.Fatalf("caminho inesperado: %s", r.URL.Path)
			}
			return &http.Response{
				StatusCode: http.StatusOK,
				Header:     http.Header{"Content-Type": []string{"application/json"}},
				Body:       io.NopCloser(strings.NewReader(`{"response":"{\"ok\":true}"}`)),
				Request:    r,
			}, nil
		}),
	}
	texto, err := client.CompletarTexto(dominio.ConfigModelo{
		Provedor:         "ollama",
		Modelo:           "llama3",
		URLBase:          "http://ollama.local",
		SegundosTimeout:  10,
		MaximoTentativas: 1,
		Temperature:      0.1,
	}, "sistema", "usuario", dominio.OpcoesRequisicaoLLM{})
	if err != nil {
		t.Fatalf("completar texto ollama: %v", err)
	}
	if strings.TrimSpace(texto) != "{\"ok\":true}" {
		t.Fatalf("texto ollama inesperado: %q", texto)
	}

	if _, err := client.CompletarTexto(dominio.ConfigModelo{Provedor: "desconhecido"}, "", "", dominio.OpcoesRequisicaoLLM{}); err == nil {
		t.Fatalf("esperava erro para provedor não suportado")
	}
}

func TestCompletarJSONFazFallbackParaOllama(t *testing.T) {
	client := NovoCliente()
	client.httpClient = &http.Client{
		Transport: roundTripFunc(func(r *http.Request) (*http.Response, error) {
			return &http.Response{
				StatusCode: http.StatusOK,
				Header:     http.Header{"Content-Type": []string{"application/json"}},
				Body:       io.NopCloser(strings.NewReader("{\"response\":\"```json\\n{\\\"ok\\\":true}\\n```\"}")),
				Request:    r,
			}, nil
		}),
	}
	resposta, err := client.CompletarJSON(dominio.ConfigModelo{
		Provedor:         "ollama",
		Modelo:           "llama3",
		URLBase:          "http://ollama.local",
		SegundosTimeout:  10,
		MaximoTentativas: 1,
	}, "sistema", "usuario", dominio.OpcoesRequisicaoLLM{})
	if err != nil {
		t.Fatalf("completar JSON via ollama: %v", err)
	}
	if resposta.Payload["ok"] != true {
		t.Fatalf("payload inesperado: %#v", resposta.Payload)
	}
}

func TestSleepBackoffRespeitaRetryAfter(t *testing.T) {
	// Sem Retry-After: backoff exponencial normal
	inicio := time.Now()
	sleepBackoff(1, 0)
	elapsed := time.Since(inicio)
	if elapsed < 150*time.Millisecond || elapsed > 500*time.Millisecond {
		t.Fatalf("sleepBackoff(1, 0) deveria aguardar ~200ms, levou %v", elapsed)
	}

	// Com Retry-After maior que o backoff: deve usar o Retry-After
	inicio = time.Now()
	sleepBackoff(1, 1*time.Second)
	elapsed = time.Since(inicio)
	if elapsed < 900*time.Millisecond {
		t.Fatalf("sleepBackoff(1, 1s) deveria aguardar ~1s (Retry-After), levou %v", elapsed)
	}

	// Com Retry-After menor que o backoff calculado: deve usar o backoff
	inicio = time.Now()
	sleepBackoff(3, 50*time.Millisecond) // backoff = 200ms * 2^2 = 800ms
	elapsed = time.Since(inicio)
	if elapsed < 700*time.Millisecond {
		t.Fatalf("sleepBackoff(3, 50ms) deveria usar backoff ~800ms, levou %v", elapsed)
	}
}

func TestParseRetryAfter(t *testing.T) {
	// Segundos inteiros
	if d := parseRetryAfter("5"); d != 5*time.Second {
		t.Fatalf("esperava 5s, obteve %v", d)
	}

	// Vazio
	if d := parseRetryAfter(""); d != 0 {
		t.Fatalf("esperava 0, obteve %v", d)
	}

	// Valor negativo ou inválido
	if d := parseRetryAfter("abc"); d != 0 {
		t.Fatalf("esperava 0 para valor inválido, obteve %v", d)
	}

	// Zero
	if d := parseRetryAfter("0"); d != 0 {
		t.Fatalf("esperava 0 para zero, obteve %v", d)
	}
}

func TestOpenAIHeadersErrosESleepBackoffTruncate(t *testing.T) {
	if _, err := openAIHeaders(dominio.ConfigModelo{
		Modelo:                   "gpt-5.4",
		VariavelAmbienteChaveAPI: "OPENAI_API_KEY",
	}); err == nil {
		t.Fatalf("esperava erro quando a variável da API key está ausente")
	}

	inicio := time.Now()
	sleepBackoff(1, 0)
	if time.Since(inicio) < 150*time.Millisecond {
		t.Fatalf("sleepBackoff deveria aguardar pelo menos o backoff mínimo")
	}

	if got := truncate("abcdef", 3); got != "abc..." {
		t.Fatalf("truncate inesperado: %q", got)
	}
}

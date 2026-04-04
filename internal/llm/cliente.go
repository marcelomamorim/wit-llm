package llm

import (
	"bytes"
	"context"
	"crypto/sha256"
	"encoding/hex"
	"encoding/json"
	"errors"
	"fmt"
	"io"
	"net/http"
	"os"
	"strings"
	"time"

	"github.com/marceloamorim/witup-llm/internal/dominio"
	"github.com/marceloamorim/witup-llm/internal/registro"
)

var retryableStatus = map[int]bool{
	408: true,
	409: true,
	425: true,
	429: true,
	500: true,
	502: true,
	503: true,
	504: true,
}

// Resposta encapsula o payload JSON parseado e o texto bruto devolvido pela LLM.
type Resposta struct {
	IDResposta string
	Payload    map[string]interface{}
	RawText    string
}

// Cliente executa requisições contra os provedores de modelo configurados.
type Cliente struct {
	httpClient *http.Client
}

// NovoCliente cria um cliente HTTP que depende dos timeouts por requisição
// definidos no modelo ativo.
func NovoCliente() *Cliente {
	return &Cliente{httpClient: &http.Client{}}
}

// CompletarJSON solicita uma conclusão textual e extrai um objeto JSON da resposta.
func (c *Cliente) CompletarJSON(model dominio.ConfigModelo, systemPrompt, userPrompt string, opcoes dominio.OpcoesRequisicaoLLM) (*Resposta, error) {
	if model.Provedor == "openai_compatible" {
		return c.completeOpenAICompatibleJSON(model, systemPrompt, userPrompt, opcoes)
	}
	text, err := c.CompletarTexto(model, systemPrompt, userPrompt, opcoes)
	if err != nil {
		return nil, err
	}
	jsonText, err := ExtrairPayloadJSON(text)
	if err != nil {
		return nil, err
	}
	payload := map[string]interface{}{}
	if err := json.Unmarshal([]byte(jsonText), &payload); err != nil {
		return nil, fmt.Errorf("ao interpretar o payload JSON do modelo: %w", err)
	}
	return &Resposta{
		IDResposta: "",
		Payload:    payload,
		RawText:    text,
	}, nil
}

// CompletarTexto solicita uma conclusão textual ao provedor configurado.
func (c *Cliente) CompletarTexto(model dominio.ConfigModelo, systemPrompt, userPrompt string, opcoes dominio.OpcoesRequisicaoLLM) (string, error) {
	switch model.Provedor {
	case "ollama":
		return c.completeOllama(model, systemPrompt, userPrompt)
	case "openai_compatible":
		response, err := c.completeOpenAICompatibleJSON(model, systemPrompt, userPrompt, opcoes)
		if err != nil {
			return "", err
		}
		return response.RawText, nil
	default:
		return "", fmt.Errorf("provedor não suportado %q", model.Provedor)
	}
}

// Sondar valida endpoint e autenticação usando chamadas nativas do provedor.
func (c *Cliente) Sondar(model dominio.ConfigModelo) (map[string]interface{}, error) {
	registro.Info("llm", "iniciando sonda do provedor=%s modelo=%s endpoint=%s", model.Provedor, model.Modelo, strings.TrimRight(model.URLBase, "/"))
	switch model.Provedor {
	case "openai_compatible":
		headers, err := openAIHeaders(model)
		if err != nil {
			return nil, err
		}
		payload, err := c.requestJSON(http.MethodGet, strings.TrimRight(model.URLBase, "/")+"/models", nil, model, headers)
		if err != nil {
			return nil, err
		}
		count := 0
		if arr, ok := payload["data"].([]interface{}); ok {
			count = len(arr)
		}
		return map[string]interface{}{
			"status":           "ok",
			"probe":            "models.list",
			"model_echo":       model.Modelo,
			"models_available": count,
		}, nil
	case "ollama":
		payload, err := c.requestJSON(http.MethodGet, strings.TrimRight(model.URLBase, "/")+"/api/tags", nil, model, nil)
		if err != nil {
			return nil, err
		}
		count := 0
		if arr, ok := payload["models"].([]interface{}); ok {
			count = len(arr)
		}
		return map[string]interface{}{
			"status":           "ok",
			"probe":            "api.tags",
			"model_echo":       model.Modelo,
			"models_available": count,
		}, nil
	default:
		return nil, fmt.Errorf("provedor não suportado %q", model.Provedor)
	}
}

// completeOllama executa uma chamada no formato esperado pela API do Ollama.
func (c *Cliente) completeOllama(model dominio.ConfigModelo, systemPrompt, userPrompt string) (string, error) {
	prompt := "Instruções de sistema:\n" + systemPrompt + "\n\n" +
		"Solicitação do usuário:\n" + userPrompt + "\n\n" +
		"Responda apenas com JSON válido."
	body := map[string]interface{}{
		"model":  model.Modelo,
		"prompt": prompt,
		"stream": false,
		"options": map[string]interface{}{
			"temperature": model.Temperature,
		},
	}
	payload, err := c.requestJSON(http.MethodPost, strings.TrimRight(model.URLBase, "/")+"/api/generate", body, model, nil)
	if err != nil {
		return "", err
	}
	text := strings.TrimSpace(fmt.Sprint(payload["response"]))
	if text == "" {
		return "", errors.New("o Ollama retornou uma resposta vazia")
	}
	return text, nil
}

// completeOpenAICompatibleJSON executa uma chamada usando a Responses API da
// OpenAI e devolve o identificador da resposta junto do payload estruturado.
func (c *Cliente) completeOpenAICompatibleJSON(
	model dominio.ConfigModelo,
	systemPrompt string,
	userPrompt string,
	opcoes dominio.OpcoesRequisicaoLLM,
) (*Resposta, error) {
	headers, err := openAIHeaders(model)
	if err != nil {
		return nil, err
	}
	body := construirCorpoResponses(model, systemPrompt, userPrompt, opcoes)
	payload, err := c.requestJSON(http.MethodPost, strings.TrimRight(model.URLBase, "/")+"/responses", body, model, headers)
	if err != nil {
		return nil, err
	}
	texto, err := extrairTextoResponses(payload)
	if err != nil {
		return nil, err
	}
	jsonText, err := ExtrairPayloadJSON(texto)
	if err != nil {
		return nil, err
	}
	jsonPayload := map[string]interface{}{}
	if err := json.Unmarshal([]byte(jsonText), &jsonPayload); err != nil {
		return nil, fmt.Errorf("ao interpretar o payload JSON do modelo: %w", err)
	}
	return &Resposta{
		IDResposta: strings.TrimSpace(fmt.Sprint(payload["id"])),
		Payload:    jsonPayload,
		RawText:    texto,
	}, nil
}

// construirCorpoResponses monta o corpo padrão enviado à Responses API.
func construirCorpoResponses(
	model dominio.ConfigModelo,
	systemPrompt string,
	userPrompt string,
	opcoes dominio.OpcoesRequisicaoLLM,
) map[string]interface{} {
	body := map[string]interface{}{
		"model": model.Modelo,
		"input": []map[string]interface{}{
			{
				"role": "system",
				"content": []map[string]string{
					{"type": "input_text", "text": systemPrompt},
				},
			},
			{
				"role": "user",
				"content": []map[string]string{
					{"type": "input_text", "text": userPrompt},
				},
			},
		},
		"text": map[string]interface{}{
			"format": map[string]string{"type": "json_object"},
		},
	}
	if anterior := strings.TrimSpace(opcoes.PreviousResponseID); anterior != "" {
		body["previous_response_id"] = anterior
	}
	if aceitaTemperaturaResponses(model.Modelo) {
		body["temperature"] = model.Temperature
	} else {
		registro.Debug("llm", "omitindo temperature para o modelo %s na Responses API", model.Modelo)
	}
	if model.MaximoTokensSaida > 0 {
		body["max_output_tokens"] = model.MaximoTokensSaida
	}
	if esforco := strings.TrimSpace(model.EsforcoRaciocinio); esforco != "" {
		body["reasoning"] = map[string]string{"effort": esforco}
	}
	if nivelServico := strings.TrimSpace(model.NivelServico); nivelServico != "" {
		body["service_tier"] = nivelServico
	}
	if retencao := strings.TrimSpace(model.RetencaoCachePrompt); retencao != "" {
		body["prompt_cache_retention"] = retencao
	}
	if cacheKey := normalizarPromptCacheKey(opcoes.PromptCacheKey); cacheKey != "" {
		body["prompt_cache_key"] = cacheKey
	}
	if !opcoes.PreservarEstado && strings.TrimSpace(opcoes.PreviousResponseID) == "" {
		body["store"] = false
	}
	return body
}

// normalizarPromptCacheKey garante que a chave de cache respeite o limite da
// Responses API sem perder estabilidade entre chamadas equivalentes.
func normalizarPromptCacheKey(chave string) string {
	chave = strings.TrimSpace(chave)
	if chave == "" {
		return ""
	}
	if len(chave) <= 64 {
		return chave
	}
	soma := sha256.Sum256([]byte(chave))
	return "pck-" + hex.EncodeToString(soma[:16])
}

// extrairTextoResponses lê o texto consolidado de uma resposta da Responses API.
func extrairTextoResponses(payload map[string]interface{}) (string, error) {
	var blocos []string
	outputs, ok := payload["output"].([]interface{})
	if !ok || len(outputs) == 0 {
		return "", errors.New("a resposta openai_compatible não contém output")
	}
	for _, bruto := range outputs {
		item, ok := bruto.(map[string]interface{})
		if !ok || strings.TrimSpace(fmt.Sprint(item["type"])) != "message" {
			continue
		}
		conteudos, ok := item["content"].([]interface{})
		if !ok {
			continue
		}
		for _, conteudoBruto := range conteudos {
			conteudo, ok := conteudoBruto.(map[string]interface{})
			if !ok {
				continue
			}
			if strings.TrimSpace(fmt.Sprint(conteudo["type"])) != "output_text" {
				continue
			}
			texto := strings.TrimSpace(fmt.Sprint(conteudo["text"]))
			if texto == "" || texto == "<nil>" {
				continue
			}
			blocos = append(blocos, texto)
		}
	}
	if len(blocos) == 0 {
		return "", errors.New("o provedor openai_compatible retornou output_text vazio")
	}
	return strings.TrimSpace(strings.Join(blocos, "\n")), nil
}

// aceitaTemperaturaResponses informa se o modelo aceita o parâmetro
// temperature na Responses API.
//
// A API tem rejeitado explicitamente esse parâmetro para a família GPT-5.4.
// Mantemos a regra por família GPT-5 para evitar falhas iguais em variantes
// próximas, sem afetar modelos anteriores que continuam usando temperature.
func aceitaTemperaturaResponses(modelo string) bool {
	normalizado := strings.ToLower(strings.TrimSpace(modelo))
	return !strings.HasPrefix(normalizado, "gpt-5")
}

// openAIHeaders monta os headers de autenticação para provedores compatíveis com OpenAI.
func openAIHeaders(model dominio.ConfigModelo) (map[string]string, error) {
	headers := map[string]string{}
	if strings.TrimSpace(model.VariavelAmbienteChaveAPI) == "" {
		return headers, nil
	}
	apiKey := strings.TrimSpace(os.Getenv(model.VariavelAmbienteChaveAPI))
	if apiKey == "" {
		return nil, fmt.Errorf("a variável de ambiente %q é obrigatória para o modelo %q", model.VariavelAmbienteChaveAPI, model.Modelo)
	}
	headers["Authorization"] = "Bearer " + apiKey
	return headers, nil
}

// requestJSON executa uma requisição HTTP, aplica retries e devolve o corpo parseado.
func (c *Cliente) requestJSON(method, url string, payload map[string]interface{}, model dominio.ConfigModelo, extraHeaders map[string]string) (map[string]interface{}, error) {
	attempts := model.MaximoTentativas + 1
	if attempts < 1 {
		attempts = 1
	}

	var encoded []byte
	var err error
	if payload != nil {
		encoded, err = json.Marshal(payload)
		if err != nil {
			return nil, fmt.Errorf("ao serializar o corpo da requisição: %w", err)
		}
	}

	for attempt := 1; attempt <= attempts; attempt++ {
		registro.Info("llm", "requisição %s %s tentativa=%d/%d modelo=%s", method, url, attempt, attempts, model.Modelo)
		ctx, cancel := context.WithTimeout(context.Background(), time.Duration(model.SegundosTimeout)*time.Second)
		requestBody := io.Reader(nil)
		if payload != nil {
			requestBody = bytes.NewReader(encoded)
		}
		req, err := http.NewRequestWithContext(ctx, method, url, requestBody)
		if err != nil {
			cancel()
			return nil, fmt.Errorf("ao criar a requisição: %w", err)
		}
		req.Header.Set("Accept", "application/json")
		if payload != nil {
			req.Header.Set("Content-Type", "application/json")
		}
		for k, v := range extraHeaders {
			req.Header.Set(k, v)
		}

		resp, err := c.httpClient.Do(req)
		if err != nil {
			cancel()
			registro.Info("llm", "falha de transporte em %s %s tentativa=%d/%d: %v", method, url, attempt, attempts, err)
			if attempt < attempts {
				sleepBackoff(attempt, 0)
				continue
			}
			return nil, fmt.Errorf("a requisição para %s falhou: %w", url, err)
		}

		responseBody, readErr := io.ReadAll(resp.Body)
		closeErr := resp.Body.Close()
		cancel()
		registro.Info(
			"llm",
			"resposta %s %s status=%d request_id=%s client_request_id=%s bytes=%d",
			method,
			url,
			resp.StatusCode,
			primeiroHeader(resp.Header, "x-request-id", "X-Request-Id"),
			primeiroHeader(resp.Header, "x-client-request-id", "X-Client-Request-Id"),
			len(responseBody),
		)
		if readErr != nil {
			return nil, fmt.Errorf("ao ler o corpo da resposta: %w", readErr)
		}
		if closeErr != nil {
			return nil, fmt.Errorf("ao fechar o corpo da resposta: %w", closeErr)
		}

		if resp.StatusCode < 200 || resp.StatusCode >= 300 {
			registro.Info("llm", "resposta não bem-sucedida em %s %s: status=%d corpo=%s", method, url, resp.StatusCode, truncate(string(responseBody), 400))
			if retryableStatus[resp.StatusCode] && attempt < attempts {
				sleepBackoff(attempt, parseRetryAfter(resp.Header.Get("Retry-After")))
				continue
			}
			return nil, fmt.Errorf("http %d de %s: %s", resp.StatusCode, url, truncate(string(responseBody), 800))
		}

		parsed := map[string]interface{}{}
		if err := json.Unmarshal(responseBody, &parsed); err != nil {
			return nil, fmt.Errorf("ao interpretar o JSON de %s: %w", url, err)
		}
		registro.Debug("llm", "payload JSON interpretado com sucesso para %s %s", method, url)
		return parsed, nil
	}
	return nil, fmt.Errorf("a requisição para %s falhou após as tentativas configuradas", url)
}

// primeiroHeader devolve o primeiro cabeçalho não vazio encontrado entre as
// chaves candidatas, preservando a ordem de preferência informada.
func primeiroHeader(headers http.Header, chaves ...string) string {
	for _, chave := range chaves {
		if valor := strings.TrimSpace(headers.Get(chave)); valor != "" {
			return valor
		}
	}
	return "-"
}

// sleepBackoff aplica um backoff exponencial entre tentativas, respeitando
// o cabeçalho Retry-After quando informado pelo servidor.
func sleepBackoff(attempt int, retryAfter time.Duration) {
	d := 200 * time.Millisecond * time.Duration(1<<(attempt-1))
	if d > 60*time.Second {
		d = 60 * time.Second
	}
	const limiteRetryAfter = 5 * time.Minute
	if retryAfter > 0 && retryAfter > d {
		if retryAfter > limiteRetryAfter {
			retryAfter = limiteRetryAfter
		}
		d = retryAfter
	}
	time.Sleep(d)
}

// parseRetryAfter extrai a duração do cabeçalho Retry-After.
// Aceita segundos inteiros ou datas HTTP (RFC1123).
func parseRetryAfter(header string) time.Duration {
	header = strings.TrimSpace(header)
	if header == "" {
		return 0
	}
	var seconds int
	if _, err := fmt.Sscanf(header, "%d", &seconds); err == nil && seconds > 0 {
		return time.Duration(seconds) * time.Second
	}
	if t, err := time.Parse(time.RFC1123, header); err == nil {
		delta := time.Until(t)
		if delta > 0 {
			return delta
		}
	}
	return 0
}

// truncate limita uma string a um tamanho máximo para mensagens de erro.
func truncate(s string, max int) string {
	if len(s) <= max {
		return s
	}
	return s[:max] + "..."
}

// ExtrairPayloadJSON localiza o primeiro objeto ou array JSON completo na saída do modelo.
func ExtrairPayloadJSON(text string) (string, error) {
	trimmed := strings.TrimSpace(text)
	if strings.HasPrefix(trimmed, "```") {
		trimmed = stripFence(trimmed)
	}
	start := strings.IndexAny(trimmed, "[{")
	if start < 0 {
		return "", errors.New("não foi possível encontrar um payload JSON na saída do modelo")
	}
	fragment := trimmed[start:]
	end := findMatchingJSONEnd(fragment)
	if end < 0 {
		return "", errors.New("não foi possível interpretar um payload JSON completo na saída do modelo")
	}
	return fragment[:end+1], nil
}

// stripFence remove cercas Markdown comuns de respostas formatadas em bloco de código.
func stripFence(text string) string {
	lines := strings.Split(text, "\n")
	if len(lines) >= 2 && strings.HasPrefix(lines[0], "```") && strings.HasPrefix(strings.TrimSpace(lines[len(lines)-1]), "```") {
		return strings.TrimSpace(strings.Join(lines[1:len(lines)-1], "\n"))
	}
	return text
}

// findMatchingJSONEnd encontra o índice final do primeiro JSON completo na string.
func findMatchingJSONEnd(text string) int {
	stack := make([]rune, 0, 32)
	inString := false
	escaped := false
	for i, r := range text {
		if inString {
			if escaped {
				escaped = false
				continue
			}
			if r == '\\' {
				escaped = true
				continue
			}
			if r == '"' {
				inString = false
			}
			continue
		}
		switch r {
		case '"':
			inString = true
		case '{':
			stack = append(stack, '}')
		case '[':
			stack = append(stack, ']')
		case '}', ']':
			if len(stack) == 0 || stack[len(stack)-1] != r {
				return -1
			}
			stack = stack[:len(stack)-1]
			if len(stack) == 0 {
				return i
			}
		}
	}
	return -1
}

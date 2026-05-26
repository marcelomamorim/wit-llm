package llm

import (
	"bytes"
	"context"
	"encoding/json"
	"fmt"
	"io"
	"mime/multipart"
	"net/http"
	"os"
	"path/filepath"
	"strings"
	"time"

	"github.com/marceloamorim/witup-llm/internal/dominio"
)

// LinhaRequisicaoBatch representa uma linha JSONL aceita pela Batch API.
type LinhaRequisicaoBatch struct {
	CustomID string                 `json:"custom_id"`
	Method   string                 `json:"method"`
	URL      string                 `json:"url"`
	Body     map[string]interface{} `json:"body"`
}

// MetadadosBatch resume a submissão de um lote OpenAI.
type MetadadosBatch struct {
	BatchID       string                 `json:"batch_id"`
	InputFileID   string                 `json:"input_file_id"`
	OutputFileID  string                 `json:"output_file_id,omitempty"`
	ErrorFileID   string                 `json:"error_file_id,omitempty"`
	Status        string                 `json:"status,omitempty"`
	RequestCounts *ContagensBatch        `json:"request_counts,omitempty"`
	Raw           map[string]interface{} `json:"raw,omitempty"`
}

// ContagensBatch resume request_counts retornado pelo objeto Batch.
type ContagensBatch struct {
	Total     int `json:"total"`
	Completed int `json:"completed"`
	Failed    int `json:"failed"`
}

// LinhaResultadoBatch representa uma linha de saída da Batch API.
type LinhaResultadoBatch struct {
	CustomID string `json:"custom_id"`
	Response *struct {
		StatusCode int                    `json:"status_code"`
		RequestID  string                 `json:"request_id"`
		Body       map[string]interface{} `json:"body"`
	} `json:"response,omitempty"`
	Error interface{} `json:"error,omitempty"`
}

// ConstruirLinhaBatchResponses materializa uma solicitação /v1/responses para JSONL.
func ConstruirLinhaBatchResponses(customID string, model dominio.ConfigModelo, systemPrompt, userPrompt string, opcoes dominio.OpcoesRequisicaoLLM) (LinhaRequisicaoBatch, error) {
	customID = strings.TrimSpace(customID)
	if customID == "" {
		return LinhaRequisicaoBatch{}, fmt.Errorf("custom_id é obrigatório para Batch")
	}
	return LinhaRequisicaoBatch{
		CustomID: customID,
		Method:   http.MethodPost,
		URL:      endpointBatchResponses(model),
		Body:     construirCorpoResponses(model, systemPrompt, userPrompt, opcoes),
	}, nil
}

// EscreverJSONLBatch grava requisições Batch e falha se houver custom_id duplicado.
func EscreverJSONLBatch(path string, linhas []LinhaRequisicaoBatch) error {
	if err := os.MkdirAll(filepath.Dir(path), 0o755); err != nil {
		return fmt.Errorf("ao criar diretório do JSONL Batch: %w", err)
	}
	seen := map[string]bool{}
	var buffer bytes.Buffer
	encoder := json.NewEncoder(&buffer)
	for _, linha := range linhas {
		if strings.TrimSpace(linha.CustomID) == "" {
			return fmt.Errorf("custom_id vazio no JSONL Batch")
		}
		if seen[linha.CustomID] {
			return fmt.Errorf("custom_id duplicado no JSONL Batch: %s", linha.CustomID)
		}
		seen[linha.CustomID] = true
		if err := encoder.Encode(linha); err != nil {
			return fmt.Errorf("ao codificar linha Batch %s: %w", linha.CustomID, err)
		}
	}
	if err := os.WriteFile(path, buffer.Bytes(), 0o644); err != nil {
		return fmt.Errorf("ao gravar JSONL Batch %q: %w", path, err)
	}
	return nil
}

// LerResultadosBatch indexa linhas de saída por custom_id.
func LerResultadosBatch(path string) (map[string]LinhaResultadoBatch, error) {
	content, err := os.ReadFile(path)
	if err != nil {
		return nil, fmt.Errorf("ao ler resultado Batch %q: %w", path, err)
	}
	resultados := map[string]LinhaResultadoBatch{}
	for i, raw := range strings.Split(strings.TrimSpace(string(content)), "\n") {
		if strings.TrimSpace(raw) == "" {
			continue
		}
		var linha LinhaResultadoBatch
		if err := json.Unmarshal([]byte(raw), &linha); err != nil {
			return nil, fmt.Errorf("ao interpretar linha %d do resultado Batch: %w", i+1, err)
		}
		if strings.TrimSpace(linha.CustomID) == "" {
			return nil, fmt.Errorf("linha %d do resultado Batch sem custom_id", i+1)
		}
		resultados[linha.CustomID] = linha
	}
	return resultados, nil
}

// ExtrairRespostaBatch converte a resposta de uma linha Batch no mesmo formato usado pela chamada síncrona.
func ExtrairRespostaBatch(model dominio.ConfigModelo, linha LinhaResultadoBatch) (*Resposta, error) {
	if linha.Response == nil {
		return nil, fmt.Errorf("linha Batch %s sem response: %v", linha.CustomID, linha.Error)
	}
	if linha.Response.StatusCode < 200 || linha.Response.StatusCode >= 300 {
		return nil, fmt.Errorf("linha Batch %s retornou HTTP %d", linha.CustomID, linha.Response.StatusCode)
	}
	payload := linha.Response.Body
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
		return nil, fmt.Errorf("ao interpretar payload JSON do Batch: %w", err)
	}
	inputTokens := extrairInteiroMapa(payload, "usage", "input_tokens")
	cachedTokens := extrairInteiroMapa(payload, "usage", "input_tokens_details", "cached_tokens")
	outputTokens := extrairInteiroMapa(payload, "usage", "output_tokens")
	return &Resposta{
		IDResposta:        strings.TrimSpace(fmt.Sprint(payload["id"])),
		Payload:           jsonPayload,
		RawText:           texto,
		InputTokens:       inputTokens,
		OutputTokens:      outputTokens,
		CachedInputTokens: cachedTokens,
		EstimatedCost:     estimarCustoTokens(model.Modelo, inputTokens, cachedTokens, outputTokens),
	}, nil
}

// SubmeterBatchOpenAI envia um arquivo JSONL já preparado para a Batch API.
func (c *Cliente) SubmeterBatchOpenAI(model dominio.ConfigModelo, jsonlPath string) (MetadadosBatch, error) {
	headers, err := openAIHeaders(model)
	if err != nil {
		return MetadadosBatch{}, err
	}
	fileID, err := c.uploadBatchFile(model, jsonlPath, headers)
	if err != nil {
		return MetadadosBatch{}, err
	}
	window := strings.TrimSpace(model.JanelaConclusaoBatch)
	if window == "" {
		window = "24h"
	}
	body := map[string]interface{}{
		"input_file_id":     fileID,
		"endpoint":          endpointBatchResponses(model),
		"completion_window": window,
	}
	payload, err := c.requestJSON(http.MethodPost, strings.TrimRight(model.URLBase, "/")+"/batches", body, model, headers)
	if err != nil {
		return MetadadosBatch{}, err
	}
	return metadadosBatch(payload, fileID), nil
}

// ConsultarBatchOpenAI consulta metadados de um batch já submetido.
func (c *Cliente) ConsultarBatchOpenAI(model dominio.ConfigModelo, batchID string) (MetadadosBatch, error) {
	headers, err := openAIHeaders(model)
	if err != nil {
		return MetadadosBatch{}, err
	}
	payload, err := c.requestJSON(http.MethodGet, strings.TrimRight(model.URLBase, "/")+"/batches/"+strings.TrimSpace(batchID), nil, model, headers)
	if err != nil {
		return MetadadosBatch{}, err
	}
	return metadadosBatch(payload, ""), nil
}

// BaixarArquivoOpenAI baixa output_file_id/error_file_id da Files API.
func (c *Cliente) BaixarArquivoOpenAI(model dominio.ConfigModelo, fileID, destino string) error {
	headers, err := openAIHeaders(model)
	if err != nil {
		return err
	}
	content, err := c.requestRaw(http.MethodGet, strings.TrimRight(model.URLBase, "/")+"/files/"+strings.TrimSpace(fileID)+"/content", nil, model, headers)
	if err != nil {
		return err
	}
	if err := os.MkdirAll(filepath.Dir(destino), 0o755); err != nil {
		return fmt.Errorf("ao criar diretório do arquivo baixado: %w", err)
	}
	if err := os.WriteFile(destino, content, 0o644); err != nil {
		return fmt.Errorf("ao gravar arquivo baixado %q: %w", destino, err)
	}
	return nil
}

func endpointBatchResponses(model dominio.ConfigModelo) string {
	endpoint := strings.TrimSpace(model.Endpoint)
	if endpoint == "" {
		return "/v1/responses"
	}
	if strings.HasPrefix(endpoint, "/") {
		return endpoint
	}
	return "/" + endpoint
}

func metadadosBatch(payload map[string]interface{}, inputFileID string) MetadadosBatch {
	if inputFileID == "" {
		inputFileID = strings.TrimSpace(fmt.Sprint(payload["input_file_id"]))
	}
	return MetadadosBatch{
		BatchID:       strings.TrimSpace(fmt.Sprint(payload["id"])),
		InputFileID:   inputFileID,
		OutputFileID:  strings.TrimSpace(fmt.Sprint(payload["output_file_id"])),
		ErrorFileID:   strings.TrimSpace(fmt.Sprint(payload["error_file_id"])),
		Status:        strings.TrimSpace(fmt.Sprint(payload["status"])),
		RequestCounts: contagensBatch(payload),
		Raw:           payload,
	}
}

func contagensBatch(payload map[string]interface{}) *ContagensBatch {
	raw, ok := payload["request_counts"].(map[string]interface{})
	if !ok {
		return nil
	}
	return &ContagensBatch{
		Total:     extrairInteiroMapa(raw, "total"),
		Completed: extrairInteiroMapa(raw, "completed"),
		Failed:    extrairInteiroMapa(raw, "failed"),
	}
}

func (c *Cliente) uploadBatchFile(model dominio.ConfigModelo, path string, headers map[string]string) (string, error) {
	file, err := os.Open(path)
	if err != nil {
		return "", fmt.Errorf("ao abrir arquivo Batch %q: %w", path, err)
	}
	defer file.Close()

	var body bytes.Buffer
	writer := multipart.NewWriter(&body)
	if err := writer.WriteField("purpose", "batch"); err != nil {
		return "", err
	}
	part, err := writer.CreateFormFile("file", filepath.Base(path))
	if err != nil {
		return "", err
	}
	if _, err := io.Copy(part, file); err != nil {
		return "", err
	}
	if err := writer.Close(); err != nil {
		return "", err
	}

	localHeaders := map[string]string{"Content-Type": writer.FormDataContentType()}
	for k, v := range headers {
		localHeaders[k] = v
	}
	payload, err := c.requestJSONWithBody(http.MethodPost, strings.TrimRight(model.URLBase, "/")+"/files", body.Bytes(), model, localHeaders)
	if err != nil {
		return "", err
	}
	fileID := strings.TrimSpace(fmt.Sprint(payload["id"]))
	if fileID == "" {
		return "", fmt.Errorf("upload do arquivo Batch não retornou id")
	}
	return fileID, nil
}

func (c *Cliente) requestJSONWithBody(method, url string, encoded []byte, model dominio.ConfigModelo, extraHeaders map[string]string) (map[string]interface{}, error) {
	content, err := c.requestRaw(method, url, encoded, model, extraHeaders)
	if err != nil {
		return nil, err
	}
	parsed := map[string]interface{}{}
	if err := json.Unmarshal(content, &parsed); err != nil {
		return nil, fmt.Errorf("ao interpretar o JSON de %s: %w", url, err)
	}
	return parsed, nil
}

func (c *Cliente) requestRaw(method, url string, encoded []byte, model dominio.ConfigModelo, extraHeaders map[string]string) ([]byte, error) {
	attempts := model.MaximoTentativas + 1
	if attempts < 1 {
		attempts = 1
	}
	for attempt := 1; attempt <= attempts; attempt++ {
		ctx, cancel := context.WithTimeout(context.Background(), time.Duration(model.SegundosTimeout)*time.Second)
		var requestBody io.Reader
		if encoded != nil {
			requestBody = bytes.NewReader(encoded)
		}
		req, err := http.NewRequestWithContext(ctx, method, url, requestBody)
		if err != nil {
			cancel()
			return nil, err
		}
		req.Header.Set("Accept", "application/json")
		for k, v := range extraHeaders {
			req.Header.Set(k, v)
		}
		resp, err := c.httpClient.Do(req)
		if err != nil {
			cancel()
			if attempt < attempts {
				sleepBackoff(attempt, 0)
				continue
			}
			return nil, fmt.Errorf("a requisição para %s falhou: %w", url, err)
		}
		body, readErr := io.ReadAll(resp.Body)
		closeErr := resp.Body.Close()
		cancel()
		if readErr != nil {
			return nil, readErr
		}
		if closeErr != nil {
			return nil, closeErr
		}
		if resp.StatusCode < 200 || resp.StatusCode >= 300 {
			if retryableStatus[resp.StatusCode] && attempt < attempts {
				sleepBackoff(attempt, parseRetryAfter(resp.Header.Get("Retry-After")))
				continue
			}
			return nil, fmt.Errorf("http %d de %s: %s", resp.StatusCode, url, truncate(string(body), 800))
		}
		return body, nil
	}
	return nil, fmt.Errorf("a requisição para %s falhou após as tentativas configuradas", url)
}

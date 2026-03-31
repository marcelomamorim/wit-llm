package llm

import (
	"bytes"
	"context"
	"encoding/json"
	"errors"
	"fmt"
	"io"
	"net/http"
	"os"
	"strings"
	"time"

	"github.com/marceloamorim/witup-llm/internal/domain"
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

// Response wraps parsed JSON payload and original text response.
type Response struct {
	Payload map[string]interface{}
	RawText string
}

// Client executes requests against model providers.
type Client struct {
	httpClient *http.Client
}

// NewClient builds an HTTP client with a conservative timeout fallback.
func NewClient() *Client {
	return &Client{httpClient: &http.Client{Timeout: 5 * time.Minute}}
}

// CompleteJSON requests a completion and extracts JSON from model output.
func (c *Client) CompleteJSON(model domain.ModelConfig, systemPrompt, userPrompt string) (*Response, error) {
	text, err := c.CompleteText(model, systemPrompt, userPrompt)
	if err != nil {
		return nil, err
	}
	jsonText, err := ExtractJSONPayload(text)
	if err != nil {
		return nil, err
	}
	payload := map[string]interface{}{}
	if err := json.Unmarshal([]byte(jsonText), &payload); err != nil {
		return nil, fmt.Errorf("parse model JSON payload: %w", err)
	}
	return &Response{Payload: payload, RawText: text}, nil
}

// CompleteText requests a textual completion from configured provider.
func (c *Client) CompleteText(model domain.ModelConfig, systemPrompt, userPrompt string) (string, error) {
	switch model.Provider {
	case "ollama":
		return c.completeOllama(model, systemPrompt, userPrompt)
	case "openai_compatible":
		return c.completeOpenAICompatible(model, systemPrompt, userPrompt)
	default:
		return "", fmt.Errorf("unsupported provider %q", model.Provider)
	}
}

// Probe validates endpoint and authentication using provider-native health calls.
func (c *Client) Probe(model domain.ModelConfig) (map[string]interface{}, error) {
	switch model.Provider {
	case "openai_compatible":
		headers, err := openAIHeaders(model)
		if err != nil {
			return nil, err
		}
		payload, err := c.requestJSON(http.MethodGet, strings.TrimRight(model.BaseURL, "/")+"/models", nil, model, headers)
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
			"model_echo":       model.Model,
			"models_available": count,
		}, nil
	case "ollama":
		payload, err := c.requestJSON(http.MethodGet, strings.TrimRight(model.BaseURL, "/")+"/api/tags", nil, model, nil)
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
			"model_echo":       model.Model,
			"models_available": count,
		}, nil
	default:
		return nil, fmt.Errorf("unsupported provider %q", model.Provider)
	}
}

func (c *Client) completeOllama(model domain.ModelConfig, systemPrompt, userPrompt string) (string, error) {
	prompt := "System instructions:\n" + systemPrompt + "\n\n" +
		"User request:\n" + userPrompt + "\n\n" +
		"Return valid JSON only."
	body := map[string]interface{}{
		"model":  model.Model,
		"prompt": prompt,
		"stream": false,
		"options": map[string]interface{}{
			"temperature": model.Temperature,
		},
	}
	payload, err := c.requestJSON(http.MethodPost, strings.TrimRight(model.BaseURL, "/")+"/api/generate", body, model, nil)
	if err != nil {
		return "", err
	}
	text := strings.TrimSpace(fmt.Sprint(payload["response"]))
	if text == "" {
		return "", errors.New("ollama returned empty response")
	}
	return text, nil
}

func (c *Client) completeOpenAICompatible(model domain.ModelConfig, systemPrompt, userPrompt string) (string, error) {
	headers, err := openAIHeaders(model)
	if err != nil {
		return "", err
	}
	body := map[string]interface{}{
		"model":       model.Model,
		"temperature": model.Temperature,
		"messages": []map[string]string{
			{"role": "system", "content": systemPrompt},
			{"role": "user", "content": userPrompt},
		},
		"response_format": map[string]string{"type": "json_object"},
	}
	payload, err := c.requestJSON(http.MethodPost, strings.TrimRight(model.BaseURL, "/")+"/chat/completions", body, model, headers)
	if err != nil {
		return "", err
	}
	choices, ok := payload["choices"].([]interface{})
	if !ok || len(choices) == 0 {
		return "", errors.New("openai-compatible response contains no choices")
	}
	first, ok := choices[0].(map[string]interface{})
	if !ok {
		return "", errors.New("openai-compatible choice has invalid format")
	}
	message, ok := first["message"].(map[string]interface{})
	if !ok {
		return "", errors.New("openai-compatible choice has no message")
	}
	text := strings.TrimSpace(fmt.Sprint(message["content"]))
	if text == "" {
		return "", errors.New("openai-compatible returned empty content")
	}
	return text, nil
}

func openAIHeaders(model domain.ModelConfig) (map[string]string, error) {
	headers := map[string]string{}
	if strings.TrimSpace(model.APIKeyEnv) == "" {
		return headers, nil
	}
	apiKey := strings.TrimSpace(os.Getenv(model.APIKeyEnv))
	if apiKey == "" {
		return nil, fmt.Errorf("environment variable %q is required for model %q", model.APIKeyEnv, model.Model)
	}
	headers["Authorization"] = "Bearer " + apiKey
	return headers, nil
}

func (c *Client) requestJSON(method, url string, payload map[string]interface{}, model domain.ModelConfig, extraHeaders map[string]string) (map[string]interface{}, error) {
	attempts := model.MaxRetries + 1
	if attempts < 1 {
		attempts = 1
	}

	var encoded []byte
	var err error
	if payload != nil {
		encoded, err = json.Marshal(payload)
		if err != nil {
			return nil, fmt.Errorf("marshal request body: %w", err)
		}
	}

	for attempt := 1; attempt <= attempts; attempt++ {
		ctx, cancel := context.WithTimeout(context.Background(), time.Duration(model.TimeoutSeconds)*time.Second)
		req, err := http.NewRequestWithContext(ctx, method, url, bytes.NewReader(encoded))
		if err != nil {
			cancel()
			return nil, fmt.Errorf("create request: %w", err)
		}
		req.Header.Set("Content-Type", "application/json")
		req.Header.Set("Accept", "application/json")
		for k, v := range extraHeaders {
			req.Header.Set(k, v)
		}

		resp, err := c.httpClient.Do(req)
		if err != nil {
			cancel()
			if attempt < attempts {
				sleepBackoff(attempt)
				continue
			}
			return nil, fmt.Errorf("request to %s failed: %w", url, err)
		}

		body, readErr := io.ReadAll(resp.Body)
		closeErr := resp.Body.Close()
		cancel()
		if readErr != nil {
			return nil, fmt.Errorf("read response body: %w", readErr)
		}
		if closeErr != nil {
			return nil, fmt.Errorf("close response body: %w", closeErr)
		}

		if resp.StatusCode < 200 || resp.StatusCode >= 300 {
			if retryableStatus[resp.StatusCode] && attempt < attempts {
				sleepBackoff(attempt)
				continue
			}
			return nil, fmt.Errorf("http %d from %s: %s", resp.StatusCode, url, truncate(string(body), 800))
		}

		parsed := map[string]interface{}{}
		if err := json.Unmarshal(body, &parsed); err != nil {
			return nil, fmt.Errorf("parse JSON from %s: %w", url, err)
		}
		return parsed, nil
	}
	return nil, fmt.Errorf("request to %s failed after retries", url)
}

func sleepBackoff(attempt int) {
	d := 200 * time.Millisecond * time.Duration(1<<(attempt-1))
	if d > 2*time.Second {
		d = 2 * time.Second
	}
	time.Sleep(d)
}

func truncate(s string, max int) string {
	if len(s) <= max {
		return s
	}
	return s[:max] + "..."
}

// ExtractJSONPayload locates the first complete JSON object/array in model output.
func ExtractJSONPayload(text string) (string, error) {
	trimmed := strings.TrimSpace(text)
	if strings.HasPrefix(trimmed, "```") {
		trimmed = stripFence(trimmed)
	}
	start := strings.IndexAny(trimmed, "[{")
	if start < 0 {
		return "", errors.New("could not find JSON payload in model output")
	}
	fragment := trimmed[start:]
	end := findMatchingJSONEnd(fragment)
	if end < 0 {
		return "", errors.New("could not parse complete JSON payload in model output")
	}
	return fragment[:end+1], nil
}

func stripFence(text string) string {
	lines := strings.Split(text, "\n")
	if len(lines) >= 2 && strings.HasPrefix(lines[0], "```") && strings.HasPrefix(strings.TrimSpace(lines[len(lines)-1]), "```") {
		return strings.TrimSpace(strings.Join(lines[1:len(lines)-1], "\n"))
	}
	return text
}

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

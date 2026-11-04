package llm

import (
	"context"
	"encoding/json"
	"fmt"
	"net/http"
	"net/url"
	"os"
	"strings"

	"github.com/marceloamorim/witup-llm/internal/dominio"
)

// requisitarProvedor conserva os prompts e traduz somente o protocolo de transporte.
func (c *Cliente) requisitarProvedor(m dominio.ConfigModelo, sistema, usuario string, opts dominio.OpcoesRequisicaoLLM) (map[string]interface{}, error) {
	return c.requisitarProvedorContext(context.Background(), m, sistema, usuario, opts)
}

func (c *Cliente) requisitarProvedorContext(ctx context.Context, m dominio.ConfigModelo, sistema, usuario string, opts dominio.OpcoesRequisicaoLLM) (map[string]interface{}, error) {
	if err := ValidarModeloDireto(m); err != nil {
		return nil, err
	}
	if opts.PreviousResponseID != "" || opts.PreservarEstado {
		return nil, fmt.Errorf("%s requer chamadas independentes, sem previous_response_id", m.Provedor)
	}
	endpoint, body, headers, err := construirRequisicaoProvedor(m, sistema, usuario)
	if err != nil {
		return nil, err
	}
	return c.requestJSONContext(ctx, http.MethodPost, endpoint, body, m, headers)
}

func construirRequisicaoProvedor(m dominio.ConfigModelo, sistema, usuario string) (string, map[string]interface{}, map[string]string, error) {
	base := strings.TrimRight(m.URLBase, "/")
	key := strings.TrimSpace(os.Getenv(m.VariavelAmbienteChaveAPI))
	if key == "" {
		return "", nil, nil, fmt.Errorf("variável %s obrigatória para %s", m.VariavelAmbienteChaveAPI, m.Modelo)
	}
	headers := map[string]string{}
	limit := m.MaximoTokensSaida
	if limit <= 0 {
		return "", nil, nil, fmt.Errorf("max_output_tokens deve ser > 0 para %s", m.Provedor)
	}
	effort := strings.TrimSpace(m.EsforcoRaciocinio)
	switch m.Provedor {
	case "gemini":
		headers["x-goog-api-key"] = key
		gen := map[string]interface{}{"maxOutputTokens": limit, "temperature": m.Temperature, "responseMimeType": "application/json"}
		if effort != "" {
			gen["thinkingConfig"] = map[string]string{"thinkingLevel": strings.ToUpper(effort)}
		}
		body := map[string]interface{}{
			"systemInstruction": map[string]interface{}{"parts": []map[string]string{{"text": sistema}}},
			"contents":          []map[string]interface{}{{"role": "user", "parts": []map[string]string{{"text": usuario}}}},
			"generationConfig":  gen,
		}
		return base + "/models/" + url.PathEscape(strings.TrimPrefix(m.Modelo, "models/")) + ":generateContent", body, headers, nil
	case "anthropic":
		headers["x-api-key"] = key
		headers["anthropic-version"] = "2023-06-01"
		body := map[string]interface{}{"model": m.Modelo, "system": sistema, "max_tokens": limit, "messages": []map[string]string{{"role": "user", "content": usuario}}}
		if effort != "" && effort != "none" {
			body["thinking"] = map[string]string{"type": "adaptive"}
			body["output_config"] = map[string]string{"effort": effort}
		} else {
			body["temperature"] = m.Temperature
		}
		return base + "/messages", body, headers, nil
	default:
		return "", nil, nil, fmt.Errorf("provedor nativo desconhecido: %s", m.Provedor)
	}
}

func (c *Cliente) sondarProvedor(m dominio.ConfigModelo) (map[string]interface{}, error) {
	key := strings.TrimSpace(os.Getenv(m.VariavelAmbienteChaveAPI))
	if key == "" {
		return nil, fmt.Errorf("variável %s obrigatória", m.VariavelAmbienteChaveAPI)
	}
	h := map[string]string{"Authorization": "Bearer " + key}
	if m.Provedor == "gemini" {
		h = map[string]string{"x-goog-api-key": key}
	}
	if m.Provedor == "anthropic" {
		h = map[string]string{"x-api-key": key, "anthropic-version": "2023-06-01"}
	}
	return c.requestJSON(http.MethodGet, strings.TrimRight(m.URLBase, "/")+"/models", nil, m, h)
}

// normalizarRespostaProvedor produz o envelope Responses consumido pelos avaliadores existentes.
// O payload original é preservado separadamente pelo executor de lotes.
func normalizarRespostaProvedor(m dominio.ConfigModelo, raw map[string]interface{}) (map[string]interface{}, error) {
	if m.Provedor == "openai" || m.Provedor == "openai_compatible" {
		return raw, nil
	}
	var texto, id, motivo string
	var entrada, saida, cache, raciocinio int
	switch m.Provedor {
	case "gemini":
		candidates, _ := raw["candidates"].([]interface{})
		if len(candidates) != 1 {
			return nil, fmt.Errorf("Gemini retornou %d candidatos; esperado 1", len(candidates))
		}
		cand, _ := candidates[0].(map[string]interface{})
		motivo, _ = cand["finishReason"].(string)
		if motivo != "STOP" {
			return nil, fmt.Errorf("Gemini não concluiu: %s", motivo)
		}
		content, _ := cand["content"].(map[string]interface{})
		parts, _ := content["parts"].([]interface{})
		for _, p := range parts {
			part, _ := p.(map[string]interface{})
			thought, _ := part["thought"].(bool)
			if !thought {
				t, _ := part["text"].(string)
				texto += t
			}
		}
		id, _ = raw["responseId"].(string)
		entrada = extrairInteiroMapa(raw, "usageMetadata", "promptTokenCount")
		raciocinio = extrairInteiroMapa(raw, "usageMetadata", "thoughtsTokenCount")
		saida = extrairInteiroMapa(raw, "usageMetadata", "candidatesTokenCount") + raciocinio
		cache = extrairInteiroMapa(raw, "usageMetadata", "cachedContentTokenCount")
	case "anthropic":
		motivo, _ = raw["stop_reason"].(string)
		if motivo != "end_turn" {
			return nil, fmt.Errorf("Claude não concluiu: %s", motivo)
		}
		parts, _ := raw["content"].([]interface{})
		for _, p := range parts {
			part, _ := p.(map[string]interface{})
			if part["type"] == "text" {
				t, _ := part["text"].(string)
				texto += t
			}
		}
		id, _ = raw["id"].(string)
		cache = extrairInteiroMapa(raw, "usage", "cache_read_input_tokens")
		entrada = extrairInteiroMapa(raw, "usage", "input_tokens") + cache + extrairInteiroMapa(raw, "usage", "cache_creation_input_tokens")
		saida = extrairInteiroMapa(raw, "usage", "output_tokens")
	default:
		return nil, fmt.Errorf("normalização não suportada: %s", m.Provedor)
	}
	if strings.TrimSpace(texto) == "" {
		return nil, fmt.Errorf("%s retornou texto vazio", m.Provedor)
	}
	return map[string]interface{}{"id": id, "model": m.Modelo, "status": "completed", "output": []interface{}{map[string]interface{}{"type": "message", "content": []interface{}{map[string]interface{}{"type": "output_text", "text": texto}}}}, "usage": map[string]interface{}{"input_tokens": entrada, "output_tokens": saida, "input_tokens_details": map[string]interface{}{"cached_tokens": cache}, "output_tokens_details": map[string]interface{}{"reasoning_tokens": raciocinio}}, "provider": m.Provedor, "finish_reason": motivo}, nil
}

func interpretarRespostaProvedor(m dominio.ConfigModelo, raw map[string]interface{}) (*Resposta, error) {
	payload, err := normalizarRespostaProvedor(m, raw)
	if err != nil {
		return nil, err
	}
	texto, err := extrairTextoResponses(payload)
	if err != nil {
		return nil, err
	}
	fragment, err := ExtrairPayloadJSON(texto)
	if err != nil {
		return nil, err
	}
	obj := map[string]interface{}{}
	if err = json.Unmarshal([]byte(fragment), &obj); err != nil {
		return nil, err
	}
	id, _ := payload["id"].(string)
	in := extrairInteiroMapa(payload, "usage", "input_tokens")
	out := extrairInteiroMapa(payload, "usage", "output_tokens")
	cached := extrairInteiroMapa(payload, "usage", "input_tokens_details", "cached_tokens")
	return &Resposta{IDResposta: id, Payload: obj, RawText: texto, InputTokens: in, OutputTokens: out, CachedInputTokens: cached, EstimatedCost: estimarCustoTokens(m.Modelo, in, cached, out)}, nil
}

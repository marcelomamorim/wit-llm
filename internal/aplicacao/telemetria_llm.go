package aplicacao

import "fmt"

type acumuladorCustoLLM struct {
	total        *float64
	indisponivel bool
}

func (a *acumuladorCustoLLM) adicionar(requestCount int, estimatedCost *float64) {
	if requestCount <= 0 {
		return
	}
	if estimatedCost == nil {
		a.indisponivel = true
		a.total = nil
		return
	}
	if a.indisponivel {
		return
	}
	if a.total == nil {
		valor := *estimatedCost
		a.total = &valor
		return
	}
	valor := *a.total + *estimatedCost
	a.total = &valor
}

func (a acumuladorCustoLLM) valor() *float64 {
	if a.indisponivel {
		return nil
	}
	return a.total
}

func formatarCustoLLM(valor *float64) string {
	if valor == nil {
		return "n/d"
	}
	return fmt.Sprintf("US$ %.4f", *valor)
}

func enriquecerPayloadRespostaLLM(payload map[string]interface{}, response *RespostaComplecao) map[string]interface{} {
	copia := map[string]interface{}{}
	for chave, valor := range payload {
		copia[chave] = valor
	}
	if response == nil {
		return copia
	}
	metadados := map[string]interface{}{
		"request_count":       1,
		"input_tokens":        response.InputTokens,
		"output_tokens":       response.OutputTokens,
		"cached_input_tokens": response.CachedInputTokens,
	}
	if response.IDResposta != "" {
		metadados["response_id"] = response.IDResposta
	}
	if response.EstimatedCost != nil {
		metadados["estimated_cost"] = *response.EstimatedCost
	}
	copia["_llm_request"] = metadados
	return copia
}

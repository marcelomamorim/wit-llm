package llm

import "strings"

type precificacaoModelo struct {
	entrada      float64
	entradaCache float64
	saida        float64
}

var precosPadraoModelos = map[string]precificacaoModelo{
	"o4-mini":      {entrada: 1.10, entradaCache: 0.275, saida: 4.40},
	"gpt-5.4-mini": {entrada: 0.75, entradaCache: 0.075, saida: 4.50},
	"gpt-5.4":      {entrada: 2.50, entradaCache: 0.25, saida: 15.00},
	"gpt-5-mini":   {entrada: 0.25, entradaCache: 0.025, saida: 2.00},
	"gpt-5":        {entrada: 1.25, entradaCache: 0.125, saida: 10.00},
	"gpt-5.4-nano": {entrada: 0.20, entradaCache: 0.02, saida: 1.25},
	"gpt-5-nano":   {entrada: 0.05, entradaCache: 0.005, saida: 0.40},
	"gpt-4o-mini":  {entrada: 0.15, entradaCache: 0.075, saida: 0.60},
	"gpt-4.1-mini": {entrada: 0.40, entradaCache: 0.10, saida: 1.60},
}

func estimarCustoTokens(modelo string, inputTokens, cachedInputTokens, outputTokens int) *float64 {
	preco, ok := resolverPrecificacaoModelo(modelo)
	if !ok {
		return nil
	}
	if inputTokens < 0 {
		inputTokens = 0
	}
	if cachedInputTokens < 0 {
		cachedInputTokens = 0
	}
	if outputTokens < 0 {
		outputTokens = 0
	}
	if cachedInputTokens > inputTokens {
		cachedInputTokens = inputTokens
	}
	entradaNaoCacheada := inputTokens - cachedInputTokens
	custo := (float64(entradaNaoCacheada) * preco.entrada / 1_000_000) +
		(float64(cachedInputTokens) * preco.entradaCache / 1_000_000) +
		(float64(outputTokens) * preco.saida / 1_000_000)
	return &custo
}

func resolverPrecificacaoModelo(modelo string) (precificacaoModelo, bool) {
	normalizado := strings.TrimSpace(strings.ToLower(modelo))
	for prefixo, preco := range precosPadraoModelos {
		if normalizado == prefixo || strings.HasPrefix(normalizado, prefixo+"-") {
			return preco, true
		}
	}
	return precificacaoModelo{}, false
}

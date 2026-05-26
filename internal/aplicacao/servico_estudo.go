package aplicacao

import "github.com/marceloamorim/witup-llm/internal/dominio"

// calcularMetricasVarianteEstudo resume produtividade e estabilidade da suíte
// produzida por uma variante específica.
func calcularMetricasVarianteEstudo(
	quantidadeMetodos int,
	quantidadeExpaths int,
	quantidadeArquivosTeste int,
	resultadosMetricas []dominio.ResultadoMetrica,
) dominio.MetricasVarianteEstudo {
	return dominio.MetricasVarianteEstudo{
		TaxaArquivosTestePorMetodo: taxaFloatSegura(float64(quantidadeArquivosTeste), quantidadeMetodos),
		TaxaArquivosTestePorExpath: taxaFloatSegura(float64(quantidadeArquivosTeste), quantidadeExpaths),
		TaxaSucessoMetricas:        taxaSucessoMetricas(resultadosMetricas),
	}
}

// taxaFloatSegura calcula razões simples protegendo a divisão por zero.
func taxaFloatSegura(numerador float64, denominador int) *float64 {
	if denominador <= 0 {
		return nil
	}
	valor := numerador / float64(denominador)
	return &valor
}

// taxaSucessoMetricas resume a fração percentual de métricas bem-sucedidas.
func taxaSucessoMetricas(resultados []dominio.ResultadoMetrica) *float64 {
	if len(resultados) == 0 {
		return nil
	}
	sucessos := 0
	for _, resultado := range resultados {
		if resultado.Sucesso {
			sucessos++
		}
	}
	valor := (float64(sucessos) / float64(len(resultados))) * 100.0
	return &valor
}

// calcularComparacaoSuites consolida os deltas principais entre WITUP, LLM e
// a variante combinada.
func calcularComparacaoSuites(variantes []dominio.ResultadoVarianteEstudoCompleto) dominio.ComparacaoSuitesEstudo {
	indice := make(map[dominio.VarianteComparacao]dominio.ResultadoVarianteEstudoCompleto, len(variantes))
	for _, variante := range variantes {
		indice[variante.Variante] = variante
	}

	witup := indice[dominio.VarianteWITUPApenas]
	llm := indice[dominio.VarianteLLMApenas]
	combinado := indice[dominio.VarianteWITUPMaisLLM]

	return dominio.ComparacaoSuitesEstudo{
		MelhorVariantePorNotaMetricas:      melhorVariantePorNota(variantes, false),
		MelhorVariantePorNotaCombinada:     melhorVariantePorNota(variantes, true),
		DeltaArquivosTesteLLMVsWITUP:       deltaInteiros(llm.QuantidadeArquivosTeste, witup.QuantidadeArquivosTeste),
		DeltaArquivosTesteCombinadoVsWITUP: deltaInteiros(combinado.QuantidadeArquivosTeste, witup.QuantidadeArquivosTeste),
		DeltaMetricasLLMVsWITUP:            deltaPontuacoes(llm.NotaMetricas, witup.NotaMetricas),
		DeltaMetricasCombinadoVsWITUP:      deltaPontuacoes(combinado.NotaMetricas, witup.NotaMetricas),
		DeltaMetricasCombinadoVsLLM:        deltaPontuacoes(combinado.NotaMetricas, llm.NotaMetricas),
		DeltaCombinadaLLMVsWITUP:           deltaPontuacoes(llm.NotaCombinada, witup.NotaCombinada),
		DeltaCombinadaCombinadoVsWITUP:     deltaPontuacoes(combinado.NotaCombinada, witup.NotaCombinada),
		DeltaCombinadaCombinadoVsLLM:       deltaPontuacoes(combinado.NotaCombinada, llm.NotaCombinada),
	}
}

// deltaInteiros expõe a diferença entre duas contagens no formato do relatório.
func deltaInteiros(esquerda, direita int) *float64 {
	valor := float64(esquerda - direita)
	return &valor
}

// deltaPontuacoes subtrai duas notas opcionais quando ambas estão disponíveis.
func deltaPontuacoes(esquerda, direita *float64) *float64 {
	if esquerda == nil || direita == nil {
		return nil
	}
	valor := *esquerda - *direita
	return &valor
}

// melhorVariantePorNota escolhe a variante com a melhor nota métrica ou combinada.
func melhorVariantePorNota(variantes []dominio.ResultadoVarianteEstudoCompleto, usarNotaCombinada bool) string {
	melhor := ""
	var melhorNota *float64
	for _, variante := range variantes {
		nota := variante.NotaMetricas
		if usarNotaCombinada {
			nota = variante.NotaCombinada
		}
		if nota == nil {
			continue
		}
		if melhorNota == nil || *nota > *melhorNota {
			valor := *nota
			melhorNota = &valor
			melhor = string(variante.Variante)
		}
	}
	return melhor
}

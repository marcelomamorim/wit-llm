package aplicacao

import (
	"fmt"
	"strings"

	"github.com/marceloamorim/witup-llm/internal/dominio"
)

// ConstruirCenariosBenchmark monta os cenários de benchmark em modo acoplado
// (--model) ou matricial (--analysis-model x --generation-model).
func ConstruirCenariosBenchmark(modelKeys, analysisModelKeys, generationModelKeys []string) ([]dominio.CenarioBenchmark, error) {
	acoplados := valoresUnicosNaoVazios(modelKeys)
	modelosAnalise := valoresUnicosNaoVazios(analysisModelKeys)
	modelosGeracao := valoresUnicosNaoVazios(generationModelKeys)

	if len(acoplados) > 0 && (len(modelosAnalise) > 0 || len(modelosGeracao) > 0) {
		return nil, fmt.Errorf("use --model ou (--analysis-model com --generation-model), mas não ambos")
	}

	if len(acoplados) > 0 {
		cenarios := make([]dominio.CenarioBenchmark, 0, len(acoplados))
		for _, chave := range acoplados {
			cenarios = append(cenarios, dominio.CenarioBenchmark{
				ChaveModeloAnalise: chave,
				ChaveModeloGeracao: chave,
			})
		}
		return cenarios, nil
	}

	if len(modelosAnalise) == 0 && len(modelosGeracao) == 0 {
		return nil, fmt.Errorf("o benchmark exige ao menos um --model ou ambos --analysis-model e --generation-model")
	}
	if len(modelosAnalise) == 0 {
		return nil, fmt.Errorf("faltou --analysis-model para o benchmark matricial")
	}
	if len(modelosGeracao) == 0 {
		return nil, fmt.Errorf("faltou --generation-model para o benchmark matricial")
	}

	cenarios := make([]dominio.CenarioBenchmark, 0, len(modelosAnalise)*len(modelosGeracao))
	for _, analise := range modelosAnalise {
		for _, geracao := range modelosGeracao {
			cenarios = append(cenarios, dominio.CenarioBenchmark{
				ChaveModeloAnalise: analise,
				ChaveModeloGeracao: geracao,
			})
		}
	}
	return cenarios, nil
}

// valoresUnicosNaoVazios remove vazios e duplicados preservando a primeira ocorrência.
func valoresUnicosNaoVazios(values []string) []string {
	jaVistos := map[string]bool{}
	saida := make([]string, 0, len(values))
	for _, bruto := range values {
		valor := strings.TrimSpace(bruto)
		if valor == "" || jaVistos[valor] {
			continue
		}
		jaVistos[valor] = true
		saida = append(saida, valor)
	}
	return saida
}

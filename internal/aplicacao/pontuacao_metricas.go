package aplicacao

import (
	"math"
	"strings"

	"github.com/marceloamorim/witup-llm/internal/dominio"
	"github.com/marceloamorim/witup-llm/internal/metricas"
)

const (
	razaoCapPontuacaoNenhuma        = "none"
	razaoCapPontuacaoSemTestes      = "no_test_files"
	razaoCapPontuacaoCompilacaoErro = "compile_failed"
)

// calcularPontuacaoAuditadaSegundaFase separa a qualidade textual da suíte da
// evidência de execução e impede que uma geração não executável pareça vitória.
func calcularPontuacaoAuditadaSegundaFase(resultados []dominio.ResultadoMetrica, quantidadeTestes int) (*float64, dominio.AuditoriaPontuacaoMetricas) {
	bruta := metricas.AgregarPontuacao(resultados)
	estatica := metricas.AgregarPontuacao(filtrarResultadosMetricasPorTipo(resultados, true))
	executavel := metricas.AgregarPontuacao(filtrarResultadosMetricasPorTipo(resultados, false))

	compilacaoMensurada, compilacaoSucesso := estadoCompilacaoMetricas(resultados)
	auditoria := dominio.AuditoriaPontuacaoMetricas{
		NotaBruta:           bruta,
		NotaEstatica:        estatica,
		NotaExecutavel:      executavel,
		RazaoCap:            razaoCapPontuacaoNenhuma,
		SuiteExecutavel:     quantidadeTestes > 0 && compilacaoSucesso,
		QuantidadeTestes:    quantidadeTestes,
		CompilacaoMensurada: compilacaoMensurada,
		CompilacaoSucesso:   compilacaoSucesso,
	}

	switch {
	case bruta == nil:
		return nil, auditoria
	case !compilacaoMensurada:
		return bruta, auditoria
	case quantidadeTestes == 0:
		zero := 0.0
		auditoria.RazaoCap = razaoCapPontuacaoSemTestes
		return &zero, auditoria
	case !compilacaoSucesso:
		nota := math.Min(*bruta, 25)
		auditoria.RazaoCap = razaoCapPontuacaoCompilacaoErro
		return &nota, auditoria
	default:
		return bruta, auditoria
	}
}

func filtrarResultadosMetricasPorTipo(resultados []dominio.ResultadoMetrica, somenteEstaticas bool) []dominio.ResultadoMetrica {
	filtradas := make([]dominio.ResultadoMetrica, 0, len(resultados))
	for _, resultado := range resultados {
		estatica := strings.EqualFold(strings.TrimSpace(resultado.Tipo), "generation_static")
		if estatica == somenteEstaticas {
			filtradas = append(filtradas, resultado)
		}
	}
	return filtradas
}

func estadoCompilacaoMetricas(resultados []dominio.ResultadoMetrica) (bool, bool) {
	for _, resultado := range resultados {
		if resultado.Nome == "test-compilation" {
			return true, resultado.Sucesso
		}
	}
	return false, false
}

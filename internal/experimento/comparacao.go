package experimento

import (
	"fmt"
	"sort"
	"strings"

	"github.com/marceloamorim/witup-llm/internal/artefatos"
	"github.com/marceloamorim/witup-llm/internal/dominio"
)

type bucketMetodo struct {
	chave   string
	unidade dominio.UnidadeComparacao
	analise dominio.AnaliseMetodo
}

// ConstruirRelatorioComparacao compara dois relatórios canônicos e resume a
// sobreposição entre WITUP e LLM antes da geração de artefatos derivados.
func ConstruirRelatorioComparacao(
	caminhoWITUP string,
	relatorioWITUP dominio.RelatorioAnalise,
	caminhoLLM string,
	relatorioLLM dominio.RelatorioAnalise,
) dominio.RelatorioComparacaoFontes {
	bucketsWITUP := criarBucketsMetodos(relatorioWITUP)
	bucketsLLM := criarBucketsMetodos(relatorioLLM)
	chaves := chavesUnificadasOrdenadas(bucketsWITUP, bucketsLLM)

	metodos := make([]dominio.ComparacaoMetodo, 0, len(chaves))
	resumo := dominio.ResumoComparacaoFontes{
		QuantidadeMetodosWITUP: contarBuckets(bucketsWITUP),
		QuantidadeMetodosLLM:   contarBuckets(bucketsLLM),
		QuantidadeExpathsWITUP: contarCaminhosRelatorio(relatorioWITUP),
		QuantidadeExpathsLLM:   contarCaminhosRelatorio(relatorioLLM),
	}

	for _, chave := range chaves {
		bucketWITUP, existeWITUP := bucketsWITUP[chave]
		bucketLLM, existeLLM := bucketsLLM[chave]
		atualizarResumoMetodos(&resumo, existeWITUP, existeLLM)

		comparacaoMetodo, compartilhados, apenasWITUP, apenasLLM := construirComparacaoMetodo(bucketWITUP, bucketLLM)
		resumo.QuantidadeExpathsCompartilhados += compartilhados
		resumo.QuantidadeExpathsApenasWITUP += apenasWITUP
		resumo.QuantidadeExpathsApenasLLM += apenasLLM
		metodos = append(metodos, comparacaoMetodo)
	}

	return dominio.RelatorioComparacaoFontes{
		IDExecucao:          artefatos.NovoIDExecucao("source-comparison"),
		GeradoEm:            dominio.HorarioUTC(),
		CaminhoAnaliseWITUP: caminhoWITUP,
		CaminhoAnaliseLLM:   caminhoLLM,
		Metodos:             metodos,
		Resumo:              resumo,
		Metricas:            calcularMetricasComparacao(resumo),
	}
}

// calcularMetricasComparacao deriva os indicadores agregados usados na Parte 1.
func calcularMetricasComparacao(resumo dominio.ResumoComparacaoFontes) dominio.MetricasComparacaoFontes {
	unionExpaths := resumo.QuantidadeExpathsCompartilhados + resumo.QuantidadeExpathsApenasWITUP + resumo.QuantidadeExpathsApenasLLM

	return dominio.MetricasComparacaoFontes{
		TaxaCoberturaMetodosLLMSobreWITUP: percentualSeguro(resumo.MetodosEmAmbos, resumo.QuantidadeMetodosWITUP),
		TaxaCoberturaExpathsLLMSobreWITUP: percentualSeguro(resumo.QuantidadeExpathsCompartilhados, resumo.QuantidadeExpathsWITUP),
		TaxaPrecisaoEstruturalLLM:         percentualSeguro(resumo.QuantidadeExpathsCompartilhados, resumo.QuantidadeExpathsLLM),
		IndiceJaccardExpaths:              percentualSeguro(resumo.QuantidadeExpathsCompartilhados, unionExpaths),
		TaxaNovidadeLLM:                   percentualSeguro(resumo.QuantidadeExpathsApenasLLM, resumo.QuantidadeExpathsLLM),
	}
}

// percentualSeguro evita divisão por zero ao calcular proporções percentuais.
func percentualSeguro(parte, total int) *float64 {
	if total <= 0 {
		return nil
	}
	valor := (float64(parte) / float64(total)) * 100.0
	return &valor
}

// atualizarResumoMetodos contabiliza a presença de cada lado no resumo global.
func atualizarResumoMetodos(resumo *dominio.ResumoComparacaoFontes, existeWITUP, existeLLM bool) {
	switch {
	case existeWITUP && existeLLM:
		resumo.MetodosEmAmbos++
	case existeWITUP:
		resumo.MetodosApenasWITUP++
	case existeLLM:
		resumo.MetodosApenasLLM++
	}
}

// construirComparacaoMetodo compara dois buckets alinhados do mesmo método.
func construirComparacaoMetodo(
	bucketWITUP bucketMetodo,
	bucketLLM bucketMetodo,
) (dominio.ComparacaoMetodo, int, int, int) {
	indiceWITUP := criarIndiceCaminhosExcecao(bucketWITUP.analise.CaminhosExcecao)
	indiceLLM := criarIndiceCaminhosExcecao(bucketLLM.analise.CaminhosExcecao)
	idsApenasWITUP, idsApenasLLM, compartilhados := diferenciarIndices(indiceWITUP, indiceLLM)

	comparacao := dominio.ComparacaoMetodo{
		Unidade:                         unidadeComparacaoDisponivel(bucketWITUP, bucketLLM),
		QuantidadeExpathsWITUP:          len(bucketWITUP.analise.CaminhosExcecao),
		QuantidadeExpathsLLM:            len(bucketLLM.analise.CaminhosExcecao),
		QuantidadeExpathsCompartilhados: compartilhados,
		IDsExpathsApenasWITUP:           idsApenasWITUP,
		IDsExpathsApenasLLM:             idsApenasLLM,
	}
	return comparacao, compartilhados, len(idsApenasWITUP), len(idsApenasLLM)
}

// diferenciarIndices separa caminhos exclusivos e compartilhados entre os índices.
func diferenciarIndices(
	indiceWITUP map[string]dominio.CaminhoExcecao,
	indiceLLM map[string]dominio.CaminhoExcecao,
) ([]string, []string, int) {
	compartilhados := 0
	apenasWITUP := make([]string, 0)
	apenasLLM := make([]string, 0)

	for chave, caminho := range indiceWITUP {
		if _, ok := indiceLLM[chave]; ok {
			compartilhados++
			continue
		}
		apenasWITUP = append(apenasWITUP, caminho.IDCaminho)
	}
	for chave, caminho := range indiceLLM {
		if _, ok := indiceWITUP[chave]; ok {
			continue
		}
		apenasLLM = append(apenasLLM, caminho.IDCaminho)
	}

	sort.Strings(apenasWITUP)
	sort.Strings(apenasLLM)
	return apenasWITUP, apenasLLM, compartilhados
}

// criarBucketsMetodos indexa o relatório por método para simplificar o alinhamento.
func criarBucketsMetodos(relatorio dominio.RelatorioAnalise) map[string]bucketMetodo {
	saida := make(map[string]bucketMetodo, len(relatorio.Analises))
	for _, analise := range relatorio.Analises {
		chave := chaveMetodo(analise.Metodo)
		saida[chave] = bucketMetodo{
			chave:   chave,
			unidade: construirUnidadeComparacao(analise.Metodo, analise.CaminhosExcecao),
			analise: analise,
		}
	}
	return saida
}

// construirUnidadeComparacao extrai a identidade do método e o tipo principal de exceção.
func construirUnidadeComparacao(metodo dominio.DescritorMetodo, caminhos []dominio.CaminhoExcecao) dominio.UnidadeComparacao {
	unidade := dominio.UnidadeComparacao{
		NomeClasse:       metodo.NomeContainer,
		CaminhoArquivo:   metodo.CaminhoArquivo,
		NomeMetodo:       metodo.NomeMetodo,
		AssinaturaMetodo: metodo.Assinatura,
		LinhaInicial:     metodo.LinhaInicial,
	}
	if len(caminhos) > 0 {
		unidade.TipoExcecao = caminhos[0].TipoExcecao
	}
	return unidade
}

// unidadeComparacaoDisponivel escolhe a unidade mais completa entre WITUP e LLM.
func unidadeComparacaoDisponivel(bucketWITUP, bucketLLM bucketMetodo) dominio.UnidadeComparacao {
	switch {
	case bucketWITUP.unidade.AssinaturaMetodo != "":
		return bucketWITUP.unidade
	case bucketLLM.unidade.AssinaturaMetodo != "":
		return bucketLLM.unidade
	default:
		return dominio.UnidadeComparacao{}
	}
}

// chaveMetodo produz a chave estável usada para alinhar buckets de método.
func chaveMetodo(metodo dominio.DescritorMetodo) string {
	if strings.TrimSpace(metodo.IDMetodo) != "" {
		return strings.TrimSpace(metodo.IDMetodo)
	}
	return fmt.Sprintf("%s|%s|%d", metodo.CaminhoArquivo, strings.ToLower(strings.TrimSpace(metodo.NomeMetodo)), metodo.LinhaInicial)
}

// criarIndiceCaminhosExcecao indexa expaths pela chave estrutural do projeto.
func criarIndiceCaminhosExcecao(caminhos []dominio.CaminhoExcecao) map[string]dominio.CaminhoExcecao {
	saida := make(map[string]dominio.CaminhoExcecao, len(caminhos))
	for _, caminho := range caminhos {
		saida[chaveCaminhoExcecao(caminho)] = caminho
	}
	return saida
}

// chaveCaminhoExcecao sintetiza os campos usados na comparação estrutural.
//
// LIMITAÇÃO CONHECIDA: a comparação atual é puramente sintática — dois expaths
// são considerados iguais apenas quando exception_type, trigger e guard_conditions
// coincidem textualmente. Isso significa que:
//   - reformulações semânticas ("x == null" vs "x is null") geram falsos negativos;
//   - variações de ordenação das guard_conditions também causam divergência;
//   - a LLM tende a produzir descrições mais detalhadas que o baseline, inflando
//     a contagem de expaths "apenas LLM".
//
// Para o estágio atual da pesquisa, isso é aceitável porque o Jaccard Index e as
// taxas de cobertura já expõem o grau de sobreposição. Uma futura melhoria seria
// aplicar similaridade semântica (embedding distance) ou normalização canônica
// antes de comparar os campos.
func chaveCaminhoExcecao(caminho dominio.CaminhoExcecao) string {
	return strings.Join([]string{
		strings.TrimSpace(caminho.TipoExcecao),
		strings.TrimSpace(caminho.Gatilho),
		strings.Join(caminho.CondicoesGuarda, " && "),
	}, "|")
}

// chavesUnificadasOrdenadas reúne e ordena todas as chaves presentes nos dois lados.
func chavesUnificadasOrdenadas(
	esquerda map[string]bucketMetodo,
	direita map[string]bucketMetodo,
) []string {
	jaVistas := map[string]bool{}
	chaves := make([]string, 0, len(esquerda)+len(direita))
	for chave := range esquerda {
		if jaVistas[chave] {
			continue
		}
		jaVistas[chave] = true
		chaves = append(chaves, chave)
	}
	for chave := range direita {
		if jaVistas[chave] {
			continue
		}
		jaVistas[chave] = true
		chaves = append(chaves, chave)
	}
	sort.Strings(chaves)
	return chaves
}

// contarBuckets devolve a quantidade total de métodos indexados.
func contarBuckets(valores map[string]bucketMetodo) int {
	return len(valores)
}

// contarCaminhosRelatorio soma a quantidade de expaths presentes no relatório.
func contarCaminhosRelatorio(relatorio dominio.RelatorioAnalise) int {
	total := 0
	for _, analise := range relatorio.Analises {
		total += len(analise.CaminhosExcecao)
	}
	return total
}

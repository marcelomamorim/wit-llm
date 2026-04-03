package experimento

import (
	"fmt"
	"path/filepath"
	"sort"

	"github.com/marceloamorim/witup-llm/internal/artefatos"
	"github.com/marceloamorim/witup-llm/internal/dominio"
)

var ordemVariantes = []dominio.VarianteComparacao{
	dominio.VarianteWITUPApenas,
	dominio.VarianteLLMApenas,
	dominio.VarianteWITUPMaisLLM,
}

// ConstruirVariantes materializa as três variantes do experimento atual:
// baseline WITUP, baseline LLM e combinação controlada das duas fontes.
func ConstruirVariantes(
	relatorioWITUP dominio.RelatorioAnalise,
	relatorioLLM dominio.RelatorioAnalise,
) map[dominio.VarianteComparacao]dominio.RelatorioAnalise {
	return map[dominio.VarianteComparacao]dominio.RelatorioAnalise{
		dominio.VarianteWITUPApenas:  clonarRelatorio(relatorioWITUP),
		dominio.VarianteLLMApenas:    clonarRelatorio(relatorioLLM),
		dominio.VarianteWITUPMaisLLM: combinarRelatorios(relatorioWITUP, relatorioLLM),
	}
}

// EscreverArtefatosVariantes persiste os relatórios de variantes em ordem
// determinística e devolve um manifesto compacto para o relatório final.
func EscreverArtefatosVariantes(
	espaco *artefatos.EspacoTrabalho,
	variantes map[dominio.VarianteComparacao]dominio.RelatorioAnalise,
) ([]dominio.ArtefatoVariante, error) {
	artefatosGerados := make([]dominio.ArtefatoVariante, 0, len(ordemVariantes))
	for _, variante := range ordemVariantes {
		relatorio, ok := variantes[variante]
		if !ok {
			continue
		}

		nomeArquivo := fmt.Sprintf("%s.analysis.json", artefatos.Slugificar(string(variante)))
		caminhoAnalise := filepath.Join(espaco.Variantes, nomeArquivo)
		if err := artefatos.EscreverJSON(caminhoAnalise, relatorio); err != nil {
			return nil, err
		}

		artefatosGerados = append(artefatosGerados, dominio.ArtefatoVariante{
			Variante:          variante,
			CaminhoAnalise:    caminhoAnalise,
			QuantidadeMetodos: len(relatorio.Analises),
			QuantidadeExpaths: contarCaminhosRelatorio(relatorio),
		})
	}
	return artefatosGerados, nil
}

// clonarRelatorio cria uma cópia profunda suficiente para que cada variante
// tenha slices e mapas independentes.
func clonarRelatorio(relatorio dominio.RelatorioAnalise) dominio.RelatorioAnalise {
	clonado := relatorio
	clonado.Analises = make([]dominio.AnaliseMetodo, 0, len(relatorio.Analises))
	for _, analise := range relatorio.Analises {
		clonado.Analises = append(clonado.Analises, clonarAnaliseMetodo(analise))
	}
	clonado.TotalMetodos = len(clonado.Analises)
	return clonado
}

// combinarRelatorios une WITUP e LLM por método, removendo caminhos duplicados
// e marcando caminhos compartilhados como provenientes da variante combinada.
func combinarRelatorios(
	relatorioWITUP dominio.RelatorioAnalise,
	relatorioLLM dominio.RelatorioAnalise,
) dominio.RelatorioAnalise {
	bucketsWITUP := criarBucketsMetodos(relatorioWITUP)
	bucketsLLM := criarBucketsMetodos(relatorioLLM)
	chaves := chavesUnificadasOrdenadas(bucketsWITUP, bucketsLLM)

	analises := make([]dominio.AnaliseMetodo, 0, len(chaves))
	for _, chave := range chaves {
		bucketWITUP, existeWITUP := bucketsWITUP[chave]
		bucketLLM, existeLLM := bucketsLLM[chave]

		switch {
		case existeWITUP && existeLLM:
			analises = append(analises, combinarAnaliseMetodo(bucketWITUP.analise, bucketLLM.analise))
		case existeWITUP:
			analises = append(analises, clonarAnaliseMetodo(bucketWITUP.analise))
		case existeLLM:
			analises = append(analises, clonarAnaliseMetodo(bucketLLM.analise))
		}
	}

	sort.Slice(analises, func(i, j int) bool {
		return chaveMetodo(analises[i].Metodo) < chaveMetodo(analises[j].Metodo)
	})

	return dominio.RelatorioAnalise{
		IDExecucao:   artefatos.NovoIDExecucao("witup-plus-llm"),
		RaizProjeto:  primeiroTextoPreenchido(relatorioWITUP.RaizProjeto, relatorioLLM.RaizProjeto),
		ChaveModelo:  relatorioLLM.ChaveModelo,
		Origem:       dominio.OrigemExpathCombinada,
		Estrategia:   "witup_plus_llm",
		GeradoEm:     dominio.HorarioUTC(),
		TotalMetodos: len(analises),
		Analises:     analises,
	}
}

// combinarAnaliseMetodo consolida duas análises do mesmo método preservando a
// origem dos caminhos exclusivos e deduplicando caminhos equivalentes.
func combinarAnaliseMetodo(
	analiseWITUP dominio.AnaliseMetodo,
	analiseLLM dominio.AnaliseMetodo,
) dominio.AnaliseMetodo {
	indiceWITUP := criarIndiceCaminhosExcecao(analiseWITUP.CaminhosExcecao)
	indiceLLM := criarIndiceCaminhosExcecao(analiseLLM.CaminhosExcecao)
	chaves := chavesCaminhosOrdenadas(indiceWITUP, indiceLLM)

	caminhos := make([]dominio.CaminhoExcecao, 0, len(chaves))
	for _, chave := range chaves {
		caminhoWITUP, existeWITUP := indiceWITUP[chave]
		caminhoLLM, existeLLM := indiceLLM[chave]

		switch {
		case existeWITUP && existeLLM:
			caminho := clonarCaminhoExcecao(caminhoWITUP)
			caminho.Origem = dominio.OrigemExpathCombinada
			caminho.Metadados = combinarMetadados(caminho.Metadados, caminhoLLM.Metadados)
			caminho.Metadados["fontes_combinadas"] = []string{string(dominio.OrigemExpathWITUP), string(dominio.OrigemExpathLLM)}
			caminhos = append(caminhos, caminho)
		case existeWITUP:
			caminhos = append(caminhos, clonarCaminhoExcecao(caminhoWITUP))
		case existeLLM:
			caminhos = append(caminhos, clonarCaminhoExcecao(caminhoLLM))
		}
	}

	return dominio.AnaliseMetodo{
		Metodo:          preferirDescritorMetodo(analiseWITUP.Metodo, analiseLLM.Metodo),
		ResumoMetodo:    primeiroTextoPreenchido(analiseWITUP.ResumoMetodo, analiseLLM.ResumoMetodo),
		CaminhosExcecao: caminhos,
		RespostaBruta: map[string]interface{}{
			"witup": analiseWITUP.RespostaBruta,
			"llm":   analiseLLM.RespostaBruta,
		},
	}
}

// preferirDescritorMetodo escolhe o descritor mais completo sem misturar regras
// de merge por toda a camada de aplicação.
func preferirDescritorMetodo(witup, llm dominio.DescritorMetodo) dominio.DescritorMetodo {
	if witup.Assinatura != "" {
		return witup
	}
	return llm
}

// chavesCaminhosOrdenadas devolve a lista ordenada de chaves de caminhos de exceção.
func chavesCaminhosOrdenadas(
	indiceWITUP map[string]dominio.CaminhoExcecao,
	indiceLLM map[string]dominio.CaminhoExcecao,
) []string {
	jaVistas := make(map[string]struct{}, len(indiceWITUP)+len(indiceLLM))
	chaves := make([]string, 0, len(jaVistas))

	for chave := range indiceWITUP {
		if _, existe := jaVistas[chave]; existe {
			continue
		}
		jaVistas[chave] = struct{}{}
		chaves = append(chaves, chave)
	}
	for chave := range indiceLLM {
		if _, existe := jaVistas[chave]; existe {
			continue
		}
		jaVistas[chave] = struct{}{}
		chaves = append(chaves, chave)
	}

	sort.Strings(chaves)
	return chaves
}

// clonarAnaliseMetodo isola uma análise antes de materializá-la em outra variante.
func clonarAnaliseMetodo(analise dominio.AnaliseMetodo) dominio.AnaliseMetodo {
	clonada := analise
	clonada.CaminhosExcecao = make([]dominio.CaminhoExcecao, 0, len(analise.CaminhosExcecao))
	for _, caminho := range analise.CaminhosExcecao {
		clonada.CaminhosExcecao = append(clonada.CaminhosExcecao, clonarCaminhoExcecao(caminho))
	}
	if analise.RespostaBruta != nil {
		clonada.RespostaBruta = make(map[string]interface{}, len(analise.RespostaBruta))
		for chave, valor := range analise.RespostaBruta {
			clonada.RespostaBruta[chave] = valor
		}
	}
	return clonada
}

// clonarCaminhoExcecao copia slices e metadados do caminho.
func clonarCaminhoExcecao(caminho dominio.CaminhoExcecao) dominio.CaminhoExcecao {
	clonado := caminho
	clonado.CondicoesGuarda = append([]string(nil), caminho.CondicoesGuarda...)
	clonado.Evidencias = append([]string(nil), caminho.Evidencias...)
	if caminho.Metadados != nil {
		clonado.Metadados = make(map[string]interface{}, len(caminho.Metadados))
		for chave, valor := range caminho.Metadados {
			clonado.Metadados[chave] = valor
		}
	}
	return clonado
}

// combinarMetadados une dois mapas rasos preservando a segunda origem em caso de conflito.
func combinarMetadados(esquerda, direita map[string]interface{}) map[string]interface{} {
	if esquerda == nil && direita == nil {
		return map[string]interface{}{}
	}

	combinado := make(map[string]interface{}, len(esquerda)+len(direita))
	for chave, valor := range esquerda {
		combinado[chave] = valor
	}
	for chave, valor := range direita {
		combinado[chave] = valor
	}
	return combinado
}

// primeiroTextoPreenchido escolhe o primeiro valor textual útil para manter a
// combinação previsível e sem ruído de campos vazios.
func primeiroTextoPreenchido(valores ...string) string {
	for _, valor := range valores {
		if valor != "" {
			return valor
		}
	}
	return ""
}

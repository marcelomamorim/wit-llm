package aplicacao

import (
	"fmt"
	"path/filepath"
	"sort"
	"strings"

	"github.com/marceloamorim/witup-llm/internal/dominio"
)

type resumoAlvosWITUP struct {
	QuantidadeBaseline       int
	QuantidadeCorrespondidos int
	QuantidadeNaoEncontrados int
}

type metodoRefinoLLM struct {
	Metodo       dominio.DescritorMetodo
	Motivos      []string
	AnaliseWITUP dominio.AnaliseMetodo
}

type candidatoCatalogo struct {
	metodo dominio.DescritorMetodo
	usado  bool
}

// alinharWITUPAoCatalogo reduz a baseline ao subconjunto realmente resolvido no
// checkout atual do projeto e enriquece a análise WITUP com descritores vindos
// do catálogo local.
func alinharWITUPAoCatalogo(
	relatorio dominio.RelatorioAnalise,
	metodosCatalogados []dominio.DescritorMetodo,
	maximoMetodos int,
) (dominio.RelatorioAnalise, []dominio.DescritorMetodo, resumoAlvosWITUP) {
	indice := indexarCatalogoPorArquivoENome(metodosCatalogados)
	analisesAlinhadas := make([]dominio.AnaliseMetodo, 0, len(relatorio.Analises))
	metodosAlvo := make([]dominio.DescritorMetodo, 0, len(relatorio.Analises))
	resumo := resumoAlvosWITUP{QuantidadeBaseline: len(relatorio.Analises)}

	for _, analise := range relatorio.Analises {
		if maximoMetodos > 0 && len(analisesAlinhadas) >= maximoMetodos {
			break
		}

		metodoCatalogado, ok := resolverMetodoCatalogado(indice, analise.Metodo)
		if !ok {
			resumo.QuantidadeNaoEncontrados++
			continue
		}

		analisesAlinhadas = append(analisesAlinhadas, enriquecerAnaliseWITUP(analise, metodoCatalogado))
		metodosAlvo = append(metodosAlvo, metodoCatalogado)
		resumo.QuantidadeCorrespondidos++
	}

	relatorio.Analises = analisesAlinhadas
	relatorio.TotalMetodos = len(analisesAlinhadas)
	return relatorio, metodosAlvo, resumo
}

// indexarCatalogoPorArquivoENome prepara uma busca estável por arquivo e nome
// do método, preservando os candidatos ordenados por linha.
func indexarCatalogoPorArquivoENome(metodos []dominio.DescritorMetodo) map[string][]*candidatoCatalogo {
	indice := make(map[string][]*candidatoCatalogo, len(metodos))
	for _, metodo := range metodos {
		metodo := metodo
		chave := chaveArquivoENome(metodo.CaminhoArquivo, metodo.NomeMetodo)
		indice[chave] = append(indice[chave], &candidatoCatalogo{metodo: metodo})
	}
	for _, candidatos := range indice {
		sort.Slice(candidatos, func(i, j int) bool {
			return candidatos[i].metodo.LinhaInicial < candidatos[j].metodo.LinhaInicial
		})
	}
	return indice
}

// limiteDistanciaLinhasAlinhamento define a distância máxima de linhas permitida
// entre a posição reportada pelo WITUP e o candidato local. Alinhamentos que
// excedam este limite são descartados para evitar correspondências incorretas
// quando o código-fonte divergiu significativamente do commit original.
const limiteDistanciaLinhasAlinhamento = 50

// resolverMetodoCatalogado escolhe o melhor candidato local para um método do
// WITUP usando arquivo, nome e proximidade de linha, respeitando um limite
// máximo de distância para evitar falsos positivos.
func resolverMetodoCatalogado(indice map[string][]*candidatoCatalogo, metodoWITUP dominio.DescritorMetodo) (dominio.DescritorMetodo, bool) {
	candidatos := indice[chaveArquivoENome(metodoWITUP.CaminhoArquivo, metodoWITUP.NomeMetodo)]
	if len(candidatos) == 0 {
		return dominio.DescritorMetodo{}, false
	}

	var melhor *candidatoCatalogo
	melhorDistancia := -1
	for _, candidato := range candidatos {
		if candidato.usado {
			continue
		}
		distancia := distanciaLinhas(metodoWITUP.LinhaInicial, candidato.metodo.LinhaInicial)
		if distancia > limiteDistanciaLinhasAlinhamento {
			continue
		}
		if melhor == nil || distancia < melhorDistancia {
			melhor = candidato
			melhorDistancia = distancia
		}
	}
	if melhor == nil {
		return dominio.DescritorMetodo{}, false
	}
	melhor.usado = true
	return melhor.metodo, true
}

// enriquecerAnaliseWITUP preserva o descritor original do artigo e injeta o
// descritor resolvido no checkout atual.
func enriquecerAnaliseWITUP(analise dominio.AnaliseMetodo, metodoCatalogado dominio.DescritorMetodo) dominio.AnaliseMetodo {
	if analise.RespostaBruta == nil {
		analise.RespostaBruta = map[string]interface{}{}
	}
	analise.RespostaBruta["witup_method_original"] = analise.Metodo
	analise.Metodo = metodoCatalogado
	return analise
}

// selecionarMetodosRefino escolhe o subconjunto aprofundado pelo fluxo
// multiagente com base em maybe, interproceduralidade e divergência.
func selecionarMetodosRefino(
	relatorioWITUP dominio.RelatorioAnalise,
	relatorioDireto dominio.RelatorioAnalise,
	comparacao dominio.RelatorioComparacaoFontes,
	tamanhoSubconjunto int,
) []metodoRefinoLLM {
	porChaveWITUP := indexarAnalisesPorChave(relatorioWITUP)
	porChaveDireto := indexarAnalisesPorChave(relatorioDireto)
	selecionados := map[string]*metodoRefinoLLM{}

	for _, analise := range relatorioWITUP.Analises {
		chave := chaveMetodoAnalise(analise.Metodo)
		if analiseTemMaybe(analise) {
			adicionarMotivoRefino(selecionados, chave, analise, "witup_maybe")
		}
		if analiseInterprocedural(analise) {
			adicionarMotivoRefino(selecionados, chave, analise, "interprocedural")
		}
	}

	for _, metodo := range comparacao.Metodos {
		if metodo.QuantidadeExpathsCompartilhados == metodo.QuantidadeExpathsWITUP &&
			metodo.QuantidadeExpathsCompartilhados == metodo.QuantidadeExpathsLLM &&
			len(metodo.IDsExpathsApenasWITUP) == 0 &&
			len(metodo.IDsExpathsApenasLLM) == 0 {
			continue
		}
		chave := chaveArquivoENomeELinha(
			metodo.Unidade.CaminhoArquivo,
			metodo.Unidade.NomeMetodo,
			metodo.Unidade.LinhaInicial,
		)
		if analise, ok := porChaveWITUP[chave]; ok {
			adicionarMotivoRefino(selecionados, chave, analise, "divergence")
		}
	}

	if tamanhoSubconjunto > 0 {
		chavesDiretas := make([]string, 0, len(porChaveDireto))
		for chave := range porChaveDireto {
			chavesDiretas = append(chavesDiretas, chave)
		}
		sort.Strings(chavesDiretas)
		adicionados := 0
		for _, chave := range chavesDiretas {
			if adicionados >= tamanhoSubconjunto {
				break
			}
			if _, jaExiste := selecionados[chave]; jaExiste {
				continue
			}
			analise, ok := porChaveWITUP[chave]
			if !ok {
				continue
			}
			adicionarMotivoRefino(selecionados, chave, analise, "deep_validation_subset")
			adicionados++
		}
	}

	chaves := make([]string, 0, len(selecionados))
	for chave := range selecionados {
		chaves = append(chaves, chave)
	}
	sort.Strings(chaves)

	resultado := make([]metodoRefinoLLM, 0, len(chaves))
	for _, chave := range chaves {
		item := selecionados[chave]
		resultado = append(resultado, *item)
	}
	return resultado
}

// adicionarMotivoRefino acumula motivos de seleção sem duplicar entradas.
func adicionarMotivoRefino(
	selecionados map[string]*metodoRefinoLLM,
	chave string,
	analise dominio.AnaliseMetodo,
	motivo string,
) {
	item, ok := selecionados[chave]
	if !ok {
		item = &metodoRefinoLLM{
			Metodo:       analise.Metodo,
			AnaliseWITUP: analise,
		}
		selecionados[chave] = item
	}
	for _, existente := range item.Motivos {
		if existente == motivo {
			return
		}
	}
	item.Motivos = append(item.Motivos, motivo)
	sort.Strings(item.Motivos)
}

// indexarAnalisesPorChave facilita o encontro rápido da análise canônica por
// método alinhado ao checkout local.
func indexarAnalisesPorChave(relatorio dominio.RelatorioAnalise) map[string]dominio.AnaliseMetodo {
	indice := make(map[string]dominio.AnaliseMetodo, len(relatorio.Analises))
	for _, analise := range relatorio.Analises {
		indice[chaveMetodoAnalise(analise.Metodo)] = analise
	}
	return indice
}

// analiseTemMaybe detecta se o WITUP marcou algum expath daquele método como maybe.
func analiseTemMaybe(analise dominio.AnaliseMetodo) bool {
	for _, caminho := range analise.CaminhosExcecao {
		if caminho.Metadados == nil {
			continue
		}
		if maybe, ok := caminho.Metadados["maybe"].(bool); ok && maybe {
			return true
		}
	}
	return false
}

// analiseInterprocedural identifica heurísticas leves de propagação entre chamadas.
func analiseInterprocedural(analise dominio.AnaliseMetodo) bool {
	for _, caminho := range analise.CaminhosExcecao {
		if len(extrairListaMetadados(caminho.Metadados["call_sequence"])) > 0 {
			return true
		}
		if len(extrairListaMetadados(caminho.Metadados["inline_sequence"])) > 0 {
			return true
		}
	}
	return false
}

// extrairListaMetadados converte listas genéricas vindas dos metadados para um
// slice de strings limpas.
func extrairListaMetadados(raw interface{}) []string {
	itens, ok := raw.([]interface{})
	if !ok {
		return nil
	}
	saida := make([]string, 0, len(itens))
	for _, item := range itens {
		valor := strings.TrimSpace(fmt.Sprint(item))
		if valor == "" || valor == "<nil>" {
			continue
		}
		saida = append(saida, valor)
	}
	return saida
}

// chaveMetodoAnalise gera a chave única usada no alinhamento de métodos.
func chaveMetodoAnalise(metodo dominio.DescritorMetodo) string {
	return chaveArquivoENomeELinha(metodo.CaminhoArquivo, metodo.NomeMetodo, metodo.LinhaInicial)
}

// chaveArquivoENome agrega arquivo e nome do método em uma chave normalizada.
func chaveArquivoENome(caminhoArquivo, nomeMetodo string) string {
	return normalizarCaminhoPesquisa(caminhoArquivo) + "|" + strings.ToLower(strings.TrimSpace(nomeMetodo))
}

// chaveArquivoENomeELinha adiciona a linha inicial à chave de arquivo e nome.
func chaveArquivoENomeELinha(caminhoArquivo, nomeMetodo string, linhaInicial int) string {
	return fmt.Sprintf("%s|%d", chaveArquivoENome(caminhoArquivo, nomeMetodo), linhaInicial)
}

// normalizarCaminhoPesquisa padroniza barras e caixa para buscas tolerantes.
func normalizarCaminhoPesquisa(caminho string) string {
	caminho = strings.TrimSpace(caminho)
	caminho = strings.ReplaceAll(caminho, "\\", "/")
	caminho = filepath.ToSlash(caminho)
	caminho = strings.TrimPrefix(caminho, "./")
	return strings.ToLower(caminho)
}

// distanciaLinhas devolve a distância absoluta entre duas linhas do código.
func distanciaLinhas(esquerda, direita int) int {
	if esquerda > direita {
		return esquerda - direita
	}
	return direita - esquerda
}

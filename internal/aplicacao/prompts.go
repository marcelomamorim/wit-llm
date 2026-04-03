package aplicacao

import (
	"encoding/json"
	"fmt"
	"strings"

	"github.com/marceloamorim/witup-llm/internal/dominio"
)

// construirPromptAnaliseSistema monta a instrução sistêmica da análise direta.
func construirPromptAnaliseSistema() string {
	return "Você é um analisador estático especialista em código Java. Responda apenas com JSON válido."
}

// construirPromptAnaliseUsuario monta o prompt de análise de expaths para um método.
func construirPromptAnaliseUsuario(overview string, method dominio.DescritorMetodo) string {
	return fmt.Sprintf(`Analise o método Java e liste os caminhos de exceção.
Return JSON: {"method_summary":"...","expaths":[{"path_id":"...","exception_type":"...","trigger":"...","guard_conditions":[...],"confidence":0.0,"evidence":[...]}]}

Visão geral do projeto:
%s

Assinatura do método: %s
Código-fonte do método:
%s
`, overview, method.Assinatura, method.Origem)
}

// construirPromptGeracaoSistema monta o prompt sistêmico para geração de testes.
func construirPromptGeracaoSistema(framework string) string {
	return fmt.Sprintf("Você é um especialista em escrita de testes Java usando %s. Responda apenas com JSON.", framework)
}

// construirPromptGeracaoUsuario monta o prompt de geração de testes para um contêiner.
func construirPromptGeracaoUsuario(overview, containerName string, methodsPayload []dominio.AnaliseMetodo) string {
	conteudoCompactado, _ := json.MarshalIndent(compactarAnalisesParaGeracao(methodsPayload), "", "  ")
	return fmt.Sprintf(`Gere arquivos de teste Java determinísticos para os métodos abaixo.
Return JSON: {"strategy_summary":"...","files":[{"relative_path":"...","content":"...","covered_method_ids":[...],"notes":"..."}]}

Linguagem: Java
Contêiner: %s
Visão geral do projeto:
%s

Análises dos métodos:
%s
`, containerName, reduzirVisaoGeralParaGeracao(overview), string(conteudoCompactado))
}

// construirPromptJuizSistema monta o prompt sistêmico do juiz avaliador.
func construirPromptJuizSistema() string {
	return "Você é um avaliador rigoroso. Responda apenas com JSON contendo score, verdict, strengths, weaknesses, risks e recommended_next_actions."
}

// construirPromptJuizUsuario monta o prompt de avaliação final da aplicacao.
func construirPromptJuizUsuario(analysis dominio.RelatorioAnalise, generation dominio.RelatorioGeracao, metricResults []dominio.ResultadoMetrica) string {
	analiseJSON, _ := json.MarshalIndent(analysis, "", "  ")
	geracaoJSON, _ := json.MarshalIndent(generation, "", "  ")
	metricasJSON, _ := json.MarshalIndent(metricResults, "", "  ")
	return fmt.Sprintf(`Avalie a qualidade da aplicacao. Responda em JSON:
{"score":0-100,"verdict":"...","strengths":[...],"weaknesses":[...],"risks":[...],"recommended_next_actions":[...]}

Análise:
%s

Geração:
%s

Métricas:
%s
`, string(analiseJSON), string(geracaoJSON), string(metricasJSON))
}

// compactarAnalisesParaGeracao remove campos volumosos que não são necessários
// para a geração de testes e reduz o risco de estouro de tokens.
func compactarAnalisesParaGeracao(analises []dominio.AnaliseMetodo) []map[string]interface{} {
	compartilhado := make([]map[string]interface{}, 0, len(analises))
	for _, analise := range analises {
		caminhos := make([]map[string]interface{}, 0, len(analise.CaminhosExcecao))
		for _, caminho := range analise.CaminhosExcecao {
			caminhos = append(caminhos, map[string]interface{}{
				"path_id":          caminho.IDCaminho,
				"exception_type":   caminho.TipoExcecao,
				"trigger":          caminho.Gatilho,
				"guard_conditions": caminho.CondicoesGuarda,
				"confidence":       caminho.Confianca,
				"evidence":         caminho.Evidencias,
			})
		}

		compartilhado = append(compartilhado, map[string]interface{}{
			"method": map[string]interface{}{
				"method_id":      analise.Metodo.IDMetodo,
				"file_path":      analise.Metodo.CaminhoArquivo,
				"container_name": analise.Metodo.NomeContainer,
				"method_name":    analise.Metodo.NomeMetodo,
				"signature":      analise.Metodo.Assinatura,
				"source_excerpt": extrairCabecalhoMetodo(analise.Metodo.Origem),
			},
			"method_summary": analise.ResumoMetodo,
			"expaths":        caminhos,
		})
	}
	return compartilhado
}

// extrairCabecalhoMetodo devolve apenas a primeira linha útil do método para
// reduzir o tamanho do prompt de geração.
func extrairCabecalhoMetodo(origem string) string {
	origem = strings.TrimSpace(origem)
	if origem == "" {
		return ""
	}
	linhas := strings.Split(origem, "\n")
	cabecalho := strings.TrimSpace(linhas[0])
	if len(cabecalho) > 240 {
		return cabecalho[:240] + "..."
	}
	return cabecalho
}

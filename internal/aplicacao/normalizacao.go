package aplicacao

import (
	"fmt"
	"strings"

	"github.com/marceloamorim/witup-llm/internal/dominio"
)

// normalizarAnaliseMetodo converte o payload bruto da LLM para a análise canônica.
func normalizarAnaliseMetodo(method dominio.DescritorMetodo, payload map[string]interface{}) dominio.AnaliseMetodo {
	resumo := strings.TrimSpace(fmt.Sprint(payload["method_summary"]))
	if resumo == "<nil>" {
		resumo = ""
	}

	caminhosExcecao := make([]dominio.CaminhoExcecao, 0)
	if raw, ok := payload["expaths"].([]interface{}); ok {
		for i, item := range raw {
			entrada, ok := item.(map[string]interface{})
			if !ok {
				continue
			}

			tipoExcecao := strings.TrimSpace(fmt.Sprint(entrada["exception_type"]))
			gatilho := strings.TrimSpace(fmt.Sprint(entrada["trigger"]))
			if gatilho == "<nil>" {
				gatilho = ""
			}
			if tipoExcecao == "" || tipoExcecao == "<nil>" {
				continue
			}

			confianca := converterParaFloat(entrada["confidence"], 0)
			if confianca < 0 {
				confianca = 0
			}
			if confianca > 1 {
				confianca = 1
			}

			caminhosExcecao = append(caminhosExcecao, dominio.CaminhoExcecao{
				IDCaminho:       fallbackIDCaminho(fmt.Sprint(entrada["path_id"]), method.IDMetodo, i+1),
				TipoExcecao:     tipoExcecao,
				Gatilho:         gatilho,
				CondicoesGuarda: paraListaStrings(entrada["guard_conditions"]),
				Confianca:       confianca,
				Evidencias:      paraListaStrings(entrada["evidence"]),
			})
		}
	}

	return dominio.AnaliseMetodo{
		Metodo:          method,
		ResumoMetodo:    resumo,
		CaminhosExcecao: caminhosExcecao,
		RespostaBruta:   payload,
	}
}

// normalizarRespostaGeracao normaliza a resposta de geração para o modelo
// interno de arquivos de teste.
func normalizarRespostaGeracao(payload map[string]interface{}) (string, []dominio.ArquivoTesteGerado) {
	resumo := strings.TrimSpace(fmt.Sprint(payload["strategy_summary"]))
	if resumo == "<nil>" {
		resumo = ""
	}

	files := []dominio.ArquivoTesteGerado{}
	raw, ok := payload["files"].([]interface{})
	if !ok {
		return resumo, files
	}

	for _, item := range raw {
		entrada, ok := item.(map[string]interface{})
		if !ok {
			continue
		}

		caminhoRelativo := strings.TrimSpace(fmt.Sprint(entrada["relative_path"]))
		conteudo := strings.TrimSpace(fmt.Sprint(entrada["content"]))
		if caminhoRelativo == "" || caminhoRelativo == "<nil>" || conteudo == "" || conteudo == "<nil>" {
			continue
		}

		files = append(files, dominio.ArquivoTesteGerado{
			CaminhoRelativo:    caminhoRelativo,
			Conteudo:           conteudo,
			IDsMetodosCobertos: paraListaStrings(entrada["covered_method_ids"]),
			Observacoes:        strings.TrimSpace(fmt.Sprint(entrada["notes"])),
		})
	}
	return resumo, files
}

// normalizarRespostaJuiz converte a resposta bruta do juiz em um objeto tipado.
func normalizarRespostaJuiz(payload map[string]interface{}) dominio.AvaliacaoJuiz {
	nota := converterParaFloat(payload["score"], 0)
	if nota < 0 {
		nota = 0
	}
	if nota > 100 {
		nota = 100
	}

	return dominio.AvaliacaoJuiz{
		Nota:                      nota,
		Veredito:                  strings.TrimSpace(fmt.Sprint(payload["verdict"])),
		Forcas:                    paraListaStrings(payload["strengths"]),
		Fraquezas:                 paraListaStrings(payload["weaknesses"]),
		Riscos:                    paraListaStrings(payload["risks"]),
		ProximasAcoesRecomendadas: paraListaStrings(payload["recommended_next_actions"]),
		RespostaBruta:             payload,
	}
}

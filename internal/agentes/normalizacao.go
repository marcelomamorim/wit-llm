package agentes

import (
	"fmt"
	"strings"

	"github.com/marceloamorim/witup-llm/internal/dominio"
)

// normalizarAnaliseMetodo consolida as saídas dos agentes no mesmo modelo
// canônico usado pelas baselines já normalizadas do WITUP.
func normalizarAnaliseMetodo(
	metodo dominio.DescritorMetodo,
	saidaArqueologo map[string]interface{},
	saidaDependencias map[string]interface{},
	saidaExtrator map[string]interface{},
	saidaCetico map[string]interface{},
) dominio.AnaliseMetodo {
	resumo := primeiroTextoPreenchido(
		saidaCetico["method_summary"],
		saidaExtrator["method_summary"],
		saidaArqueologo["method_summary"],
	)
	return dominio.AnaliseMetodo{
		Metodo:          metodo,
		ResumoMetodo:    resumo,
		CaminhosExcecao: normalizarCaminhosExcecao(metodo, saidaCetico, saidaExtrator),
		RespostaBruta: map[string]interface{}{
			"archaeologist":    saidaArqueologo,
			"dependency_mesh":  saidaDependencias,
			"extractor":        saidaExtrator,
			"skeptic_reviewer": saidaCetico,
		},
	}
}

// normalizarCaminhosExcecao escolhe os caminhos aceitos e os converte para a
// representação canônica de expaths usada em todo o projeto.
func normalizarCaminhosExcecao(
	metodo dominio.DescritorMetodo,
	saidaCetico map[string]interface{},
	saidaExtrator map[string]interface{},
) []dominio.CaminhoExcecao {
	brutos := saidaCetico["accepted_expaths"]
	if brutos == nil {
		brutos = saidaExtrator["expaths"]
	}
	itens, ok := brutos.([]interface{})
	if !ok {
		return nil
	}

	caminhos := make([]dominio.CaminhoExcecao, 0, len(itens))
	for indice, item := range itens {
		entrada, ok := item.(map[string]interface{})
		if !ok {
			continue
		}
		tipoExcecao := strings.TrimSpace(fmt.Sprint(entrada["exception_type"]))
		if tipoExcecao == "" || tipoExcecao == "<nil>" {
			continue
		}

		caminhos = append(caminhos, dominio.CaminhoExcecao{
			IDCaminho:       idCaminhoFallback(fmt.Sprint(entrada["path_id"]), metodo.IDMetodo, indice+1),
			TipoExcecao:     tipoExcecao,
			Gatilho:         strings.TrimSpace(fmt.Sprint(entrada["trigger"])),
			CondicoesGuarda: listaStrings(entrada["guard_conditions"]),
			Confianca:       limitarConfianca(entrada["confidence"]),
			Evidencias:      listaStrings(entrada["evidence"]),
			Origem:          dominio.OrigemExpathLLM,
			Metadados:       metadadosCaminhoAgente(saidaCetico),
		})
	}
	return caminhos
}

// metadadosCaminhoAgente mantém, em um único lugar, os detalhes auxiliares
// usados para rastrear se um caminho foi aceito pelo cético e quais notas
// acompanharam essa revisão.
func metadadosCaminhoAgente(saidaCetico map[string]interface{}) map[string]interface{} {
	return map[string]interface{}{
		"accepted_by_skeptic": saidaCetico["accepted_expaths"] != nil,
		"review_notes":        saidaCetico["review_notes"],
	}
}

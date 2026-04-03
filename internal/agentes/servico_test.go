package agentes

import (
	"testing"

	"github.com/marceloamorim/witup-llm/internal/artefatos"
	"github.com/marceloamorim/witup-llm/internal/dominio"
)

type chamadaExecutorTeste struct {
	promptSistema string
	promptUsuario string
	opcoes        dominio.OpcoesRequisicaoLLM
}

func TestOrquestradorEncadeiaEstadoDoProjetoEMemoriaEntreMetodos(t *testing.T) {
	chamadas := make([]chamadaExecutorTeste, 0, 6)
	respostas := []ResultadoExecucaoAgente{
		{
			IDResposta: "resp_projeto_arqueologo",
			Payload: map[string]interface{}{
				"summary":        "visão do projeto",
				"method_summary": "contexto arqueológico",
			},
			RawText: `{"summary":"visão do projeto"}`,
		},
		{
			IDResposta: "resp_projeto_dependencias",
			Payload: map[string]interface{}{
				"summary": "malha de dependências",
			},
			RawText: `{"summary":"malha de dependências"}`,
		},
		{
			IDResposta: "resp_extrator_helper",
			Payload: map[string]interface{}{
				"summary":        "extrator helper",
				"method_summary": "helper",
				"expaths": []interface{}{
					map[string]interface{}{
						"path_id":          "p1",
						"exception_type":   "IllegalArgumentException",
						"trigger":          "entrada inválida",
						"guard_conditions": []interface{}{"valor == null"},
						"confidence":       0.8,
						"evidence":         []interface{}{"linha 10"},
					},
				},
			},
			RawText: `{"summary":"extrator helper"}`,
		},
		{
			IDResposta: "resp_cetico_helper",
			Payload: map[string]interface{}{
				"summary":        "cético helper",
				"method_summary": "helper revisado",
				"accepted_expaths": []interface{}{
					map[string]interface{}{
						"path_id":          "p1",
						"exception_type":   "IllegalArgumentException",
						"trigger":          "entrada inválida",
						"guard_conditions": []interface{}{"valor == null"},
						"confidence":       0.9,
						"evidence":         []interface{}{"linha 10"},
					},
				},
			},
			RawText: `{"summary":"cético helper"}`,
		},
		{
			IDResposta: "resp_extrator_caller",
			Payload: map[string]interface{}{
				"summary":        "extrator caller",
				"method_summary": "caller",
				"expaths": []interface{}{
					map[string]interface{}{
						"path_id":          "p2",
						"exception_type":   "IllegalStateException",
						"trigger":          "helper falha",
						"guard_conditions": []interface{}{"helper() lança exceção"},
						"confidence":       0.7,
						"evidence":         []interface{}{"linha 22"},
					},
				},
			},
			RawText: `{"summary":"extrator caller"}`,
		},
		{
			IDResposta: "resp_cetico_caller",
			Payload: map[string]interface{}{
				"summary":        "cético caller",
				"method_summary": "caller revisado",
				"accepted_expaths": []interface{}{
					map[string]interface{}{
						"path_id":          "p2",
						"exception_type":   "IllegalStateException",
						"trigger":          "helper falha",
						"guard_conditions": []interface{}{"helper() lança exceção"},
						"confidence":       0.85,
						"evidence":         []interface{}{"linha 22"},
					},
				},
			},
			RawText: `{"summary":"cético caller"}`,
		},
	}
	indice := 0

	orquestrador, err := NovoOrquestrador(func(
		model dominio.ConfigModelo,
		systemPrompt string,
		userPrompt string,
		opcoes dominio.OpcoesRequisicaoLLM,
	) (ResultadoExecucaoAgente, error) {
		chamadas = append(chamadas, chamadaExecutorTeste{
			promptSistema: systemPrompt,
			promptUsuario: userPrompt,
			opcoes:        opcoes,
		})
		resposta := respostas[indice]
		indice++
		return resposta, nil
	})
	if err != nil {
		t.Fatalf("NovoOrquestrador retornou erro inesperado: %v", err)
	}
	espaco, err := artefatos.NovoEspacoTrabalho(t.TempDir(), "teste-agentes")
	if err != nil {
		t.Fatalf("NovoEspacoTrabalho retornou erro inesperado: %v", err)
	}

	metodos := []dominio.DescritorMetodo{
		{
			IDMetodo:       "helper-id",
			NomeContainer:  "sample.Service",
			NomeMetodo:     "helper",
			Assinatura:     "sample.Service.helper(String valor)",
			CaminhoArquivo: "src/main/java/sample/Service.java",
			Origem:         "private void helper(String valor) { if (valor == null) throw new IllegalArgumentException(); }",
		},
		{
			IDMetodo:       "caller-id",
			NomeContainer:  "sample.Service",
			NomeMetodo:     "caller",
			Assinatura:     "sample.Service.caller(String valor)",
			CaminhoArquivo: "src/main/java/sample/Service.java",
			Origem:         "void caller(String valor) { helper(valor); }",
		},
	}

	relatorio, rastreio, err := orquestrador.ExecutarAnaliseSeletiva(
		dominio.ConfigModelo{Modelo: "gpt-5.4"},
		"openai_main",
		"visão geral",
		metodos,
		nil,
		false,
		espaco,
	)
	if err != nil {
		t.Fatalf("ExecutarAnaliseSeletiva retornou erro inesperado: %v", err)
	}

	if relatorio.TotalMetodos != 2 {
		t.Fatalf("esperava 2 métodos analisados, recebi %d", relatorio.TotalMetodos)
	}
	if len(rastreio.EtapasProjeto) != 2 {
		t.Fatalf("esperava 2 etapas de projeto, recebi %d", len(rastreio.EtapasProjeto))
	}
	if len(chamadas) != 6 {
		t.Fatalf("esperava 6 chamadas ao executor, recebi %d", len(chamadas))
	}
	if chamadas[1].opcoes.PreviousResponseID != "resp_projeto_arqueologo" {
		t.Fatalf("dependências deveria encadear o arqueólogo, recebeu %q", chamadas[1].opcoes.PreviousResponseID)
	}
	if chamadas[2].opcoes.PreviousResponseID != "resp_projeto_dependencias" {
		t.Fatalf("extrator do primeiro método deveria usar o contexto do projeto, recebeu %q", chamadas[2].opcoes.PreviousResponseID)
	}
	if chamadas[3].opcoes.PreviousResponseID != "resp_extrator_helper" {
		t.Fatalf("cético do primeiro método deveria encadear o extrator, recebeu %q", chamadas[3].opcoes.PreviousResponseID)
	}
	if chamadas[4].opcoes.PreviousResponseID != "resp_cetico_helper" {
		t.Fatalf("extrator do segundo método deveria reaproveitar o método já analisado, recebeu %q", chamadas[4].opcoes.PreviousResponseID)
	}
	if rastreio.Metodos[1].Etapas[0].IDRespostaAnterior != "resp_cetico_helper" {
		t.Fatalf("rastreio do segundo método não registrou o previous_response_id esperado: %#v", rastreio.Metodos[1].Etapas[0])
	}
}

package aplicacao

import (
	"testing"

	"github.com/marceloamorim/witup-llm/internal/dominio"
	"github.com/marceloamorim/witup-llm/internal/llm"
	"github.com/marceloamorim/witup-llm/internal/metricas"
)

func TestAdaptadorClienteComplecaoDelegaAoClienteLLM(t *testing.T) {
	adaptador := NovoClienteComplecao(llm.NovoCliente())
	resposta, err := adaptador.CompletarJSON(dominio.ConfigModelo{
		Provedor: "nao-suportado",
	}, "sistema", "usuario", dominio.OpcoesRequisicaoLLM{})
	if err == nil {
		t.Fatalf("esperava erro propagado pelo adaptador de completions")
	}
	if resposta != nil {
		t.Fatalf("resposta inesperada: %#v", resposta)
	}
}

func TestAdaptadorExecutorMetricasDelegaAoExecutor(t *testing.T) {
	adaptador := NovoExecutorMetricas(metricas.NovoExecutor())
	resultados := adaptador.ExecutarTodas([]dominio.ConfigMetrica{{
		Nome:       "ok",
		Comando:    "printf '10'",
		RegexValor: `(10)`,
		Escala:     10,
		Peso:       1,
	}}, metricas.ContextoExecucao{RaizProjeto: t.TempDir()})
	if len(resultados) != 1 || !resultados[0].Sucesso {
		t.Fatalf("resultado inesperado: %#v", resultados)
	}
}

func TestFabricaCatalogoPadraoCriaCatalogo(t *testing.T) {
	catalogo := fabricaCatalogoPadrao{}.NovoCatalogo(dominio.ConfigProjeto{Raiz: t.TempDir()})
	if catalogo == nil {
		t.Fatalf("esperava catálogo não nulo")
	}
}

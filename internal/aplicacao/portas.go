package aplicacao

import (
	"github.com/marceloamorim/witup-llm/internal/catalogo"
	"github.com/marceloamorim/witup-llm/internal/dominio"
	"github.com/marceloamorim/witup-llm/internal/llm"
	"github.com/marceloamorim/witup-llm/internal/metricas"
)

// RespostaComplecao representa a resposta de uma LLM no formato esperado pela
// camada de aplicação.
type RespostaComplecao struct {
	IDResposta string
	Payload    map[string]interface{}
	RawText    string
}

// ClienteComplecao abstrai o provedor de completions usado pela aplicacao.
type ClienteComplecao interface {
	CompletarJSON(model dominio.ConfigModelo, systemPrompt, userPrompt string, opcoes dominio.OpcoesRequisicaoLLM) (*RespostaComplecao, error)
}

// ExecutorMetricas abstrai a execução de métricas para permitir doubles determinísticos
// nos testes.
type ExecutorMetricas interface {
	ExecutarTodas(metricas []dominio.ConfigMetrica, ctx metricas.ContextoExecucao) []dominio.ResultadoMetrica
}

// CatalogoMetodos expõe descoberta de métodos e leitura opcional da visão geral do projeto.
type CatalogoMetodos interface {
	Catalogar() ([]dominio.DescritorMetodo, error)
	CarregarVisaoGeral() (string, error)
}

// FabricaCatalogo cria catálogos de métodos a partir de uma configuração de projeto.
type FabricaCatalogo interface {
	NovoCatalogo(cfg dominio.ConfigProjeto) CatalogoMetodos
}

type adaptadorClienteComplecao struct {
	cliente *llm.Cliente
}

// NovoClienteComplecao constrói o adaptador padrão para chamadas de completion.
func NovoClienteComplecao(client *llm.Cliente) ClienteComplecao {
	if client == nil {
		client = llm.NovoCliente()
	}
	return adaptadorClienteComplecao{cliente: client}
}

// CompletarJSON delega a chamada JSON ao cliente concreto de LLM.
func (a adaptadorClienteComplecao) CompletarJSON(model dominio.ConfigModelo, systemPrompt, userPrompt string, opcoes dominio.OpcoesRequisicaoLLM) (*RespostaComplecao, error) {
	response, err := a.cliente.CompletarJSON(model, systemPrompt, userPrompt, opcoes)
	if err != nil {
		return nil, err
	}
	return &RespostaComplecao{
		IDResposta: response.IDResposta,
		Payload:    response.Payload,
		RawText:    response.RawText,
	}, nil
}

type adaptadorExecutorMetricas struct {
	executor *metricas.Executor
}

// NovoExecutorMetricas constrói o adaptador padrão para execução de métricas.
func NovoExecutorMetricas(runner *metricas.Executor) ExecutorMetricas {
	if runner == nil {
		runner = metricas.NovoExecutor()
	}
	return adaptadorExecutorMetricas{executor: runner}
}

// ExecutarTodas delega a execução de métricas ao executor concreto.
func (a adaptadorExecutorMetricas) ExecutarTodas(metricConfigs []dominio.ConfigMetrica, ctx metricas.ContextoExecucao) []dominio.ResultadoMetrica {
	return a.executor.ExecutarTodas(metricConfigs, ctx)
}

type fabricaCatalogoPadrao struct{}

// NovoCatalogo cria o catalogador padrão de métodos Java do projeto.
func (fabricaCatalogoPadrao) NovoCatalogo(cfg dominio.ConfigProjeto) CatalogoMetodos {
	return catalogo.NovoCatalogador(cfg)
}

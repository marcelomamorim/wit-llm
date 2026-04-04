package agentes

import (
	"fmt"
	"path/filepath"
	"strings"

	"github.com/marceloamorim/witup-llm/internal/artefatos"
	"github.com/marceloamorim/witup-llm/internal/dominio"
	"github.com/marceloamorim/witup-llm/internal/registro"
)

// ResultadoExecucaoAgente encapsula a resposta estruturada e o identificador
// retornado pela Responses API para uma etapa de agente.
type ResultadoExecucaoAgente struct {
	IDResposta string
	Payload    map[string]interface{}
	RawText    string
}

// ExecutorAgenteJSON representa a fronteira mínima usada pelo orquestrador
// multiagente para solicitar respostas estruturadas a um provedor de LLM.
type ExecutorAgenteJSON func(
	model dominio.ConfigModelo,
	systemPrompt string,
	userPrompt string,
	opcoes dominio.OpcoesRequisicaoLLM,
) (ResultadoExecucaoAgente, error)

// Orquestrador executa a ramificação multiagente da variante LLM_ONLY.
type Orquestrador struct {
	executor       ExecutorAgenteJSON
	memoriaMetodos map[string]string
}

type contextoProjetoCompartilhado struct {
	Arqueologo           map[string]interface{}
	Dependencias         map[string]interface{}
	IDRespostaArqueologo string
	IDRespostaDepend     string
}

// NovoOrquestrador cria um orquestrador multiagente com a dependência de execução informada.
func NovoOrquestrador(executar ExecutorAgenteJSON) (*Orquestrador, error) {
	if executar == nil {
		return nil, fmt.Errorf("o orquestrador multiagente exige uma função de execução")
	}
	return &Orquestrador{
		executor:       executar,
		memoriaMetodos: map[string]string{},
	}, nil
}

// ExecutarAnalise processa todos os métodos-alvo e retorna os artefatos canônicos
// da análise LLM_ONLY e do rastreio completo por agente.
func (o *Orquestrador) ExecutarAnalise(
	model dominio.ConfigModelo,
	chaveModelo string,
	visaoGeral string,
	metodos []dominio.DescritorMetodo,
	salvarPrompts bool,
	espaco *artefatos.EspacoTrabalho,
) (dominio.RelatorioAnalise, dominio.RelatorioRastreioAgente, error) {
	return o.ExecutarAnaliseSeletiva(model, chaveModelo, visaoGeral, metodos, nil, salvarPrompts, espaco)
}

// ExecutarAnaliseSeletiva executa o fluxo multiagente com contexto compartilhado
// de projeto e refino apenas para os métodos informados.
func (o *Orquestrador) ExecutarAnaliseSeletiva(
	model dominio.ConfigModelo,
	chaveModelo string,
	visaoGeral string,
	metodos []dominio.DescritorMetodo,
	motivosPorMetodo map[string][]string,
	salvarPrompts bool,
	espaco *artefatos.EspacoTrabalho,
) (dominio.RelatorioAnalise, dominio.RelatorioRastreioAgente, error) {
	analises := make([]dominio.AnaliseMetodo, 0, len(metodos))
	rastreios := make([]dominio.RastreioAgenteMetodo, 0, len(metodos))
	contextoProjeto, etapasProjeto, err := o.analisarProjeto(model, visaoGeral, metodos, salvarPrompts, espaco)
	if err != nil {
		return dominio.RelatorioAnalise{}, dominio.RelatorioRastreioAgente{}, err
	}

	for indice, metodo := range metodos {
		registro.Info("agentes", "processando método %d/%d: %s", indice+1, len(metodos), metodo.Assinatura)
		rastreio, analise, err := o.analisarMetodo(model, metodo, contextoProjeto, indice, motivosPorMetodo[chaveMetodo(metodo)], salvarPrompts, espaco)
		if err != nil {
			return dominio.RelatorioAnalise{}, dominio.RelatorioRastreioAgente{}, fmt.Errorf("a análise multiagente falhou para %s: %w", metodo.Assinatura, err)
		}
		rastreios = append(rastreios, rastreio)
		analises = append(analises, analise)
	}

	relatorioAnalise := dominio.RelatorioAnalise{
		IDExecucao:   filepath.Base(espaco.Raiz),
		ChaveModelo:  chaveModelo,
		Origem:       dominio.OrigemExpathLLM,
		Estrategia:   "llm_multi_agent",
		GeradoEm:     dominio.HorarioUTC(),
		TotalMetodos: len(metodos),
		Analises:     analises,
	}
	relatorioRastreio := dominio.RelatorioRastreioAgente{
		IDExecucao:    filepath.Base(espaco.Raiz),
		ChaveModelo:   chaveModelo,
		GeradoEm:      dominio.HorarioUTC(),
		EtapasProjeto: etapasProjeto,
		Metodos:       rastreios,
	}
	return relatorioAnalise, relatorioRastreio, nil
}

// analisarProjeto monta o contexto compartilhado do projeto apenas uma vez no
// início da execução multiagente.
func (o *Orquestrador) analisarProjeto(
	model dominio.ConfigModelo,
	visaoGeral string,
	metodos []dominio.DescritorMetodo,
	salvarPrompts bool,
	espaco *artefatos.EspacoTrabalho,
) (contextoProjetoCompartilhado, []dominio.EtapaRastreioAgente, error) {
	resultadoArqueologo, err := o.executarPapel(
		model,
		dominio.PapelAgenteArqueologo,
		construirPromptSistemaArqueologoProjeto(),
		construirPromptUsuarioArqueologoProjeto(visaoGeral, metodos),
		dominio.OpcoesRequisicaoLLM{
			PromptCacheKey:  construirChaveCacheAgente("project", string(dominio.PapelAgenteArqueologo)),
			PreservarEstado: true,
		},
		salvarPrompts,
		espaco,
	)
	if err != nil {
		return contextoProjetoCompartilhado{}, nil, err
	}

	resultadoDependencias, err := o.executarPapel(
		model,
		dominio.PapelAgenteDependencias,
		construirPromptSistemaDependenciasProjeto(),
		construirPromptUsuarioDependenciasProjeto(visaoGeral, metodos, resultadoArqueologo.saida),
		dominio.OpcoesRequisicaoLLM{
			PromptCacheKey:     construirChaveCacheAgente("project", string(dominio.PapelAgenteDependencias)),
			PreviousResponseID: resultadoArqueologo.idResposta,
			PreservarEstado:    true,
		},
		salvarPrompts,
		espaco,
	)
	if err != nil {
		return contextoProjetoCompartilhado{}, nil, err
	}
	return contextoProjetoCompartilhado{
			Arqueologo:           resultadoArqueologo.saida,
			Dependencias:         resultadoDependencias.saida,
			IDRespostaArqueologo: resultadoArqueologo.idResposta,
			IDRespostaDepend:     resultadoDependencias.idResposta,
		},
		[]dominio.EtapaRastreioAgente{resultadoArqueologo.etapa, resultadoDependencias.etapa},
		nil
}

// analisarMetodo executa as etapas de extrator e cético para um método já
// apoiado pelo contexto compartilhado do projeto.
func (o *Orquestrador) analisarMetodo(
	model dominio.ConfigModelo,
	metodo dominio.DescritorMetodo,
	contextoProjeto contextoProjetoCompartilhado,
	indice int,
	motivosSelecao []string,
	salvarPrompts bool,
	espaco *artefatos.EspacoTrabalho,
) (dominio.RastreioAgenteMetodo, dominio.AnaliseMetodo, error) {
	respostaContexto := o.resolverRespostaContextoMetodo(metodo, contextoProjeto.IDRespostaDepend)

	resultadoExtrator, err := o.executarPapelPorMetodo(
		model,
		dominio.PapelAgenteExtrator,
		construirPromptSistemaExtrator(),
		construirPromptUsuarioExtrator(metodo),
		dominio.OpcoesRequisicaoLLM{
			PromptCacheKey:     construirChaveCacheAgente(metodo.IDMetodo, string(dominio.PapelAgenteExtrator)),
			PreviousResponseID: respostaContexto,
			PreservarEstado:    true,
		},
		indice,
		metodo,
		salvarPrompts,
		espaco,
	)
	if err != nil {
		return dominio.RastreioAgenteMetodo{}, dominio.AnaliseMetodo{}, err
	}

	resultadoCetico, err := o.executarPapelPorMetodo(
		model,
		dominio.PapelAgenteCetico,
		construirPromptSistemaCetico(),
		construirPromptUsuarioCetico(metodo, resultadoExtrator.saida),
		dominio.OpcoesRequisicaoLLM{
			PromptCacheKey:     construirChaveCacheAgente(metodo.IDMetodo, string(dominio.PapelAgenteCetico)),
			PreviousResponseID: resultadoExtrator.idResposta,
			PreservarEstado:    true,
		},
		indice,
		metodo,
		salvarPrompts,
		espaco,
	)
	if err != nil {
		return dominio.RastreioAgenteMetodo{}, dominio.AnaliseMetodo{}, err
	}

	if strings.TrimSpace(resultadoCetico.idResposta) != "" {
		o.memorizarRespostaMetodo(metodo, resultadoCetico.idResposta)
	}

	etapas := []dominio.EtapaRastreioAgente{
		resultadoExtrator.etapa,
		resultadoCetico.etapa,
	}
	analise := normalizarAnaliseMetodo(metodo, contextoProjeto.Arqueologo, contextoProjeto.Dependencias, resultadoExtrator.saida, resultadoCetico.saida)
	return dominio.RastreioAgenteMetodo{Metodo: metodo, MotivosSelecao: motivosSelecao, Etapas: etapas}, analise, nil
}

type resultadoPapel struct {
	idResposta string
	saida      map[string]interface{}
	etapa      dominio.EtapaRastreioAgente
}

// executarPapel processa uma etapa de contexto compartilhado em nível de projeto.
func (o *Orquestrador) executarPapel(
	model dominio.ConfigModelo,
	papel dominio.PapelAgente,
	promptSistema string,
	promptUsuario string,
	opcoes dominio.OpcoesRequisicaoLLM,
	salvarPrompts bool,
	espaco *artefatos.EspacoTrabalho,
) (resultadoPapel, error) {
	resultadoExecucao, err := o.executar(model, papel, promptSistema, promptUsuario, opcoes)
	if err != nil {
		return resultadoPapel{}, err
	}
	etapa, err := persistirEtapaProjeto(espaco, salvarPrompts, papel, promptUsuario, opcoes.PreviousResponseID, resultadoExecucao.IDResposta, resultadoExecucao.RawText, resultadoExecucao.Payload)
	if err != nil {
		return resultadoPapel{}, err
	}
	return resultadoPapel{idResposta: resultadoExecucao.IDResposta, saida: resultadoExecucao.Payload, etapa: etapa}, nil
}

// executarPapelPorMetodo processa uma etapa vinculada a um método específico.
func (o *Orquestrador) executarPapelPorMetodo(
	model dominio.ConfigModelo,
	papel dominio.PapelAgente,
	promptSistema string,
	promptUsuario string,
	opcoes dominio.OpcoesRequisicaoLLM,
	indice int,
	metodo dominio.DescritorMetodo,
	salvarPrompts bool,
	espaco *artefatos.EspacoTrabalho,
) (resultadoPapel, error) {
	resultadoExecucao, err := o.executar(model, papel, promptSistema, promptUsuario, opcoes)
	if err != nil {
		return resultadoPapel{}, err
	}
	etapa, err := persistirEtapaAgente(espaco, salvarPrompts, indice, metodo, papel, promptUsuario, opcoes.PreviousResponseID, resultadoExecucao.IDResposta, resultadoExecucao.RawText, resultadoExecucao.Payload)
	if err != nil {
		return resultadoPapel{}, err
	}
	return resultadoPapel{idResposta: resultadoExecucao.IDResposta, saida: resultadoExecucao.Payload, etapa: etapa}, nil
}

// executar centraliza a chamada ao executor injetado e enriquece o erro com o papel do agente.
// Quando previous_response_id está presente e a chamada falha, tenta novamente sem o contexto
// stateful para evitar que a expiração do ID aborte toda a execução.
func (o *Orquestrador) executar(
	model dominio.ConfigModelo,
	papel dominio.PapelAgente,
	promptSistema string,
	promptUsuario string,
	opcoes dominio.OpcoesRequisicaoLLM,
) (ResultadoExecucaoAgente, error) {
	resultado, err := o.executor(model, promptSistema, promptUsuario, opcoes)
	if err != nil && strings.TrimSpace(opcoes.PreviousResponseID) != "" {
		registro.Info("agentes", "agente %s falhou com previous_response_id=%s; tentando sem contexto stateful: %v",
			papel, opcoes.PreviousResponseID, err)
		opcoesSemContexto := opcoes
		opcoesSemContexto.PreviousResponseID = ""
		resultado, err = o.executor(model, promptSistema, promptUsuario, opcoesSemContexto)
	}
	if err != nil {
		return ResultadoExecucaoAgente{}, fmt.Errorf("a chamada do agente %s falhou: %w", papel, err)
	}
	registro.Debug("agentes", "agente %s concluiu a etapa com payload estruturado", papel)
	return resultado, nil
}

// resolverRespostaContextoMetodo decide qual response_id stateful deve servir
// de ponto de partida para o método atual.
func (o *Orquestrador) resolverRespostaContextoMetodo(metodo dominio.DescritorMetodo, respostaPadrao string) string {
	if resposta := o.buscarRespostaMetodoRelacionado(metodo); resposta != "" {
		return resposta
	}
	return respostaPadrao
}

// buscarRespostaMetodoRelacionado tenta reaproveitar o contexto de um método já
// analisado que seja chamado pelo método atual.
func (o *Orquestrador) buscarRespostaMetodoRelacionado(metodo dominio.DescritorMetodo) string {
	chavesAtuais := map[string]struct{}{}
	for _, chave := range chavesMemoriaMetodo(metodo) {
		chavesAtuais[chave] = struct{}{}
	}
	origemNormalizada := strings.ToLower(metodo.Origem)
	for chave, idResposta := range o.memoriaMetodos {
		if strings.TrimSpace(idResposta) == "" {
			continue
		}
		if _, eAtual := chavesAtuais[chave]; eAtual {
			continue
		}
		nomeMetodo := extrairNomeMetodoMemoria(chave)
		if nomeMetodo == "" || nomeMetodo == metodo.NomeMetodo {
			continue
		}
		if strings.Contains(origemNormalizada, nomeMetodo+"(") {
			return idResposta
		}
	}
	return ""
}

// memorizarRespostaMetodo associa o response_id final às chaves de busca do método.
func (o *Orquestrador) memorizarRespostaMetodo(metodo dominio.DescritorMetodo, idResposta string) {
	for _, chave := range chavesMemoriaMetodo(metodo) {
		o.memoriaMetodos[chave] = idResposta
	}
}

// chavesMemoriaMetodo gera as chaves alternativas usadas para localizar o método na memória.
func chavesMemoriaMetodo(metodo dominio.DescritorMetodo) []string {
	return []string{
		"id|" + metodo.IDMetodo,
		"signature|" + strings.ToLower(strings.TrimSpace(metodo.Assinatura)),
		"container_method|" + strings.ToLower(strings.TrimSpace(metodo.NomeContainer)) + "#" + strings.ToLower(strings.TrimSpace(metodo.NomeMetodo)),
		"method|" + strings.ToLower(strings.TrimSpace(metodo.NomeMetodo)),
	}
}

// extrairNomeMetodoMemoria recupera o nome do método a partir da chave persistida.
func extrairNomeMetodoMemoria(chave string) string {
	if strings.HasPrefix(chave, "method|") {
		return strings.TrimPrefix(chave, "method|")
	}
	if strings.HasPrefix(chave, "container_method|") {
		partes := strings.Split(strings.TrimPrefix(chave, "container_method|"), "#")
		if len(partes) == 2 {
			return partes[1]
		}
	}
	return ""
}

// chaveMetodo devolve a chave principal usada para identificar motivos de seleção.
func chaveMetodo(metodo dominio.DescritorMetodo) string {
	if metodo.IDMetodo != "" {
		return metodo.IDMetodo
	}
	return fmt.Sprintf("%s|%d", metodo.Assinatura, metodo.LinhaInicial)
}

// construirChaveCacheAgente cria uma chave curta e estável para prompt caching.
func construirChaveCacheAgente(partes ...string) string {
	limpas := make([]string, 0, len(partes))
	for _, parte := range partes {
		parte = strings.TrimSpace(parte)
		if parte == "" {
			continue
		}
		limpas = append(limpas, artefatos.Slugificar(parte))
	}
	if len(limpas) == 0 {
		return "agentic:default"
	}
	return "agentic:" + strings.Join(limpas, ":")
}

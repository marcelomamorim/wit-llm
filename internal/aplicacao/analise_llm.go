package aplicacao

import (
	"crypto/sha256"
	"encoding/hex"
	"fmt"
	"path/filepath"

	"github.com/marceloamorim/witup-llm/internal/agentes"
	"github.com/marceloamorim/witup-llm/internal/artefatos"
	"github.com/marceloamorim/witup-llm/internal/dominio"
	"github.com/marceloamorim/witup-llm/internal/experimento"
	"github.com/marceloamorim/witup-llm/internal/registro"
)

// analisarMetodosDireto executa a varredura ampla com uma chamada por método.
func (s *Servico) analisarMetodosDireto(
	cfg *dominio.ConfigAplicacao,
	modelKey string,
	visaoGeral string,
	metodos []dominio.DescritorMetodo,
	workspace *artefatos.EspacoTrabalho,
	rotuloCache string,
) (dominio.RelatorioAnalise, error) {
	model, err := getModelOrError(cfg, modelKey)
	if err != nil {
		return dominio.RelatorioAnalise{}, err
	}

	analises := make([]dominio.AnaliseMetodo, 0, len(metodos))
	chaveProjeto := identificarProjeto(cfg)
	for i, metodo := range metodos {
		registro.Info("pipeline", "analisando método %d/%d: %s", i+1, len(metodos), metodo.Assinatura)
		systemPrompt := construirPromptAnaliseSistema()
		userPrompt := construirPromptAnaliseUsuario(visaoGeral, metodo)
		opcoes := dominio.OpcoesRequisicaoLLM{
			PromptCacheKey: construirPromptCacheKey(chaveProjeto, rotuloCache, metodo.NomeContainer),
		}
		response, err := s.completionClient.CompletarJSON(model, systemPrompt, userPrompt, opcoes)
		if err != nil {
			return dominio.RelatorioAnalise{}, fmt.Errorf("a análise falhou para %s: %w", metodo.Assinatura, err)
		}
		analise := normalizarAnaliseMetodo(metodo, response.Payload)
		analises = append(analises, analise)

		if cfg.Fluxo.SalvarPrompts {
			stem := fmt.Sprintf("%s-%04d-%s", rotuloCache, i+1, artefatos.Slugificar(metodo.IDMetodo))
			if err := persistirPromptEResposta(workspace, stem, userPrompt, response.RawText); err != nil {
				return dominio.RelatorioAnalise{}, err
			}
		}
	}

	return dominio.RelatorioAnalise{
		IDExecucao:   filepath.Base(workspace.Raiz),
		RaizProjeto:  cfg.Projeto.Raiz,
		ChaveModelo:  modelKey,
		Origem:       dominio.OrigemExpathLLM,
		Estrategia:   "llm_direct",
		GeradoEm:     dominio.HorarioUTC(),
		TotalMetodos: len(metodos),
		Analises:     analises,
	}, nil
}

// analisarMetodosMultiagente executa o refino seletivo usando o orquestrador de agentes.
func (s *Servico) analisarMetodosMultiagente(
	cfg *dominio.ConfigAplicacao,
	modelKey string,
	visaoGeral string,
	metodos []dominio.DescritorMetodo,
	motivosPorMetodo map[string][]string,
	workspace *artefatos.EspacoTrabalho,
) (dominio.RelatorioAnalise, dominio.RelatorioRastreioAgente, error) {
	model, err := getModelOrError(cfg, modelKey)
	if err != nil {
		return dominio.RelatorioAnalise{}, dominio.RelatorioRastreioAgente{}, err
	}

	orquestrador, err := agentes.NovoOrquestrador(func(
		model dominio.ConfigModelo,
		systemPrompt string,
		userPrompt string,
		opcoes dominio.OpcoesRequisicaoLLM,
	) (agentes.ResultadoExecucaoAgente, error) {
		if opcoes.PromptCacheKey == "" {
			opcoes.PromptCacheKey = construirPromptCacheKey(identificarProjeto(cfg), "multiagent", systemPrompt)
		}
		response, err := s.completionClient.CompletarJSON(model, systemPrompt, userPrompt, opcoes)
		if err != nil {
			return agentes.ResultadoExecucaoAgente{}, err
		}
		return agentes.ResultadoExecucaoAgente{
			IDResposta: response.IDResposta,
			Payload:    response.Payload,
			RawText:    response.RawText,
		}, nil
	})
	if err != nil {
		return dominio.RelatorioAnalise{}, dominio.RelatorioRastreioAgente{}, err
	}

	report, traceReport, err := orquestrador.ExecutarAnaliseSeletiva(model, modelKey, visaoGeral, metodos, motivosPorMetodo, cfg.Fluxo.SalvarPrompts, workspace)
	if err != nil {
		return dominio.RelatorioAnalise{}, dominio.RelatorioRastreioAgente{}, err
	}
	report.RaizProjeto = cfg.Projeto.Raiz
	report.Estrategia = "llm_multi_agent"
	return report, traceReport, nil
}

// executarBranchLLMExperimento escolhe entre o modo direto e o modo seletivo
// multiagente conforme a configuração do fluxo.
func (s *Servico) executarBranchLLMExperimento(
	cfg *dominio.ConfigAplicacao,
	modelKey string,
	witupReport dominio.RelatorioAnalise,
	metodosAlvo []dominio.DescritorMetodo,
	visaoGeral string,
	workspace *artefatos.EspacoTrabalho,
) (dominio.RelatorioAnalise, string, dominio.RelatorioRastreioAgente, string, error) {
	switch modoFluxoLLM(cfg) {
	case dominio.ModoLLMDireto:
		report, err := s.analisarMetodosDireto(cfg, modelKey, visaoGeral, metodosAlvo, workspace, "analysis")
		if err != nil {
			return dominio.RelatorioAnalise{}, "", dominio.RelatorioRastreioAgente{}, "", err
		}
		caminhoAnalise := filepath.Join(workspace.Fontes, "llm-analysis.json")
		if err := artefatos.EscreverJSON(caminhoAnalise, report); err != nil {
			return dominio.RelatorioAnalise{}, "", dominio.RelatorioRastreioAgente{}, "", err
		}
		if err := registrarArtefatoNoBanco(cfg, report.IDExecucao, "analise_llm_experimento", "", string(dominio.VarianteLLMApenas), caminhoAnalise, report.GeradoEm, report); err != nil {
			return dominio.RelatorioAnalise{}, "", dominio.RelatorioRastreioAgente{}, "", err
		}
		if err := registrarArtefatoNoBanco(cfg, report.IDExecucao, "analise_llm_direta", "", string(dominio.VarianteLLMApenas), caminhoAnalise, report.GeradoEm, report); err != nil {
			return dominio.RelatorioAnalise{}, "", dominio.RelatorioRastreioAgente{}, "", err
		}
		return report, caminhoAnalise, dominio.RelatorioRastreioAgente{}, "", nil
	case dominio.ModoLLMMultiagente:
		return s.executarBranchLLMMultiagente(cfg, modelKey, witupReport, metodosAlvo, visaoGeral, workspace)
	default:
		return dominio.RelatorioAnalise{}, "", dominio.RelatorioRastreioAgente{}, "", fmt.Errorf("modo LLM não suportado %q", cfg.Fluxo.ModoLLM)
	}
}

// executarBranchLLMMultiagente primeiro varre todos os métodos em modo direto e
// depois refina apenas os candidatos selecionados.
func (s *Servico) executarBranchLLMMultiagente(
	cfg *dominio.ConfigAplicacao,
	modelKey string,
	witupReport dominio.RelatorioAnalise,
	metodosAlvo []dominio.DescritorMetodo,
	visaoGeral string,
	workspace *artefatos.EspacoTrabalho,
) (dominio.RelatorioAnalise, string, dominio.RelatorioRastreioAgente, string, error) {
	relatorioDireto, err := s.analisarMetodosDireto(cfg, modelKey, visaoGeral, metodosAlvo, workspace, "analysis-direct")
	if err != nil {
		return dominio.RelatorioAnalise{}, "", dominio.RelatorioRastreioAgente{}, "", err
	}

	caminhoDireto := filepath.Join(workspace.Fontes, "llm-analysis-direct.json")
	if err := artefatos.EscreverJSON(caminhoDireto, relatorioDireto); err != nil {
		return dominio.RelatorioAnalise{}, "", dominio.RelatorioRastreioAgente{}, "", err
	}
	if err := registrarArtefatoNoBanco(cfg, relatorioDireto.IDExecucao, "analise_llm_direta", "", string(dominio.VarianteLLMApenas), caminhoDireto, relatorioDireto.GeradoEm, relatorioDireto); err != nil {
		return dominio.RelatorioAnalise{}, "", dominio.RelatorioRastreioAgente{}, "", err
	}

	comparacaoPreliminar := experimento.ConstruirRelatorioComparacao("witup-aligned", witupReport, caminhoDireto, relatorioDireto)
	metodosRefino := selecionarMetodosRefino(witupReport, relatorioDireto, comparacaoPreliminar, cfg.Fluxo.TamanhoSubconjunto)
	if len(metodosRefino) == 0 {
		relatorioDireto.Estrategia = "llm_direct_without_refinement"
		caminhoFinal := filepath.Join(workspace.Fontes, "llm-analysis.json")
		if err := artefatos.EscreverJSON(caminhoFinal, relatorioDireto); err != nil {
			return dominio.RelatorioAnalise{}, "", dominio.RelatorioRastreioAgente{}, "", err
		}
		if err := registrarArtefatoNoBanco(cfg, relatorioDireto.IDExecucao, "analise_llm_experimento", "", string(dominio.VarianteLLMApenas), caminhoFinal, relatorioDireto.GeradoEm, relatorioDireto); err != nil {
			return dominio.RelatorioAnalise{}, "", dominio.RelatorioRastreioAgente{}, "", err
		}
		return relatorioDireto, caminhoFinal, dominio.RelatorioRastreioAgente{}, "", nil
	}

	motivosPorMetodo := make(map[string][]string, len(metodosRefino))
	alvosRefino := make([]dominio.DescritorMetodo, 0, len(metodosRefino))
	for _, item := range metodosRefino {
		alvosRefino = append(alvosRefino, item.Metodo)
		motivosPorMetodo[item.Metodo.IDMetodo] = item.Motivos
	}
	registro.Info("pipeline", "modo multiagente: refinando %d/%d métodos após varredura direta", len(alvosRefino), len(metodosAlvo))

	relatorioRefinado, rastreio, err := s.analisarMetodosMultiagente(cfg, modelKey, visaoGeral, alvosRefino, motivosPorMetodo, workspace)
	if err != nil {
		return dominio.RelatorioAnalise{}, "", dominio.RelatorioRastreioAgente{}, "", err
	}

	relatorioFinal := mesclarAnalisesDiretoEMultiagente(relatorioDireto, relatorioRefinado)
	relatorioFinal.Estrategia = "llm_direct_plus_targeted_multi_agent"
	caminhoFinal := filepath.Join(workspace.Fontes, "llm-analysis.json")
	if err := artefatos.EscreverJSON(caminhoFinal, relatorioFinal); err != nil {
		return dominio.RelatorioAnalise{}, "", dominio.RelatorioRastreioAgente{}, "", err
	}
	caminhoRastreio := filepath.Join(workspace.Rastreios, "agent-trace-report.json")
	if err := artefatos.EscreverJSON(caminhoRastreio, rastreio); err != nil {
		return dominio.RelatorioAnalise{}, "", dominio.RelatorioRastreioAgente{}, "", err
	}
	if err := registrarArtefatoNoBanco(cfg, relatorioFinal.IDExecucao, "analise_llm_experimento", "", string(dominio.VarianteLLMApenas), caminhoFinal, relatorioFinal.GeradoEm, relatorioFinal); err != nil {
		return dominio.RelatorioAnalise{}, "", dominio.RelatorioRastreioAgente{}, "", err
	}
	if err := registrarArtefatoNoBanco(cfg, relatorioFinal.IDExecucao, "analise_llm_multiagente", "", string(dominio.VarianteLLMApenas), caminhoFinal, relatorioFinal.GeradoEm, relatorioFinal); err != nil {
		return dominio.RelatorioAnalise{}, "", dominio.RelatorioRastreioAgente{}, "", err
	}
	if err := registrarArtefatoNoBanco(cfg, relatorioFinal.IDExecucao, "rastreio_agentes", "", string(dominio.VarianteLLMApenas), caminhoRastreio, rastreio.GeradoEm, rastreio); err != nil {
		return dominio.RelatorioAnalise{}, "", dominio.RelatorioRastreioAgente{}, "", err
	}
	return relatorioFinal, caminhoFinal, rastreio, caminhoRastreio, nil
}

// mesclarAnalisesDiretoEMultiagente substitui no relatório base apenas os
// métodos que passaram pelo refino seletivo.
func mesclarAnalisesDiretoEMultiagente(base dominio.RelatorioAnalise, refinado dominio.RelatorioAnalise) dominio.RelatorioAnalise {
	porChave := make(map[string]dominio.AnaliseMetodo, len(base.Analises))
	for _, analise := range base.Analises {
		porChave[chaveMetodoAnalise(analise.Metodo)] = analise
	}
	for _, analise := range refinado.Analises {
		porChave[chaveMetodoAnalise(analise.Metodo)] = analise
	}

	analises := make([]dominio.AnaliseMetodo, 0, len(base.Analises))
	for _, analise := range base.Analises {
		analises = append(analises, porChave[chaveMetodoAnalise(analise.Metodo)])
	}
	base.Analises = analises
	base.TotalMetodos = len(analises)
	base.GeradoEm = dominio.HorarioUTC()
	return base
}

// construirPromptCacheKey gera uma chave curta e determinística para o reuse de prompt.
func construirPromptCacheKey(partes ...string) string {
	hash := sha256.Sum256([]byte(fmt.Sprint(partes)))
	return "witup-llm:" + hex.EncodeToString(hash[:12])
}

// identificarProjeto extrai um identificador simples do diretório raiz atual.
func identificarProjeto(cfg *dominio.ConfigAplicacao) string {
	return filepath.Base(cfg.Projeto.Raiz)
}

// modoFluxoLLM resolve o modo da branch LLM com fallback para multiagente.
func modoFluxoLLM(cfg *dominio.ConfigAplicacao) dominio.ModoLLM {
	if cfg == nil || cfg.Fluxo.ModoLLM == "" {
		return dominio.ModoLLMMultiagente
	}
	return dominio.ModoLLM(cfg.Fluxo.ModoLLM)
}

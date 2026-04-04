package aplicacao

import (
	"fmt"
	"os"
	"path/filepath"
	"sort"
	"strings"

	"github.com/marceloamorim/witup-llm/internal/artefatos"
	"github.com/marceloamorim/witup-llm/internal/dominio"
	"github.com/marceloamorim/witup-llm/internal/metricas"
	"github.com/marceloamorim/witup-llm/internal/registro"
)

// Gerar pede ao modelo de geração que crie arquivos de teste a partir da análise.
func (s *Servico) Gerar(cfg *dominio.ConfigAplicacao, analysisReport dominio.RelatorioAnalise, analysisPath, modelKey string, workspace *artefatos.EspacoTrabalho) (dominio.RelatorioGeracao, string, *artefatos.EspacoTrabalho, error) {
	registro.Info("pipeline", "iniciando geração de testes com modelo=%s origem=%s", modelKey, analysisPath)
	model, err := getModelOrError(cfg, modelKey)
	if err != nil {
		return dominio.RelatorioGeracao{}, "", workspace, err
	}
	overview, err := s.catalogFactory.NovoCatalogo(cfg.Projeto).CarregarVisaoGeral()
	if err != nil {
		return dominio.RelatorioGeracao{}, "", workspace, err
	}
	if workspace == nil {
		workspace, err = artefatos.NovoEspacoTrabalho(cfg.Fluxo.DiretorioSaida, artefatos.NovoIDExecucao("generate-"+modelKey))
		if err != nil {
			return dominio.RelatorioGeracao{}, "", workspace, err
		}
	}

	grouped := agruparAnalisesPorContainer(analysisReport)
	strategyParts := make([]string, 0, len(grouped))
	allFiles := make([]dominio.ArquivoTesteGerado, 0, len(grouped))
	rawResponses := make([]map[string]interface{}, 0, len(grouped))

	keys := make([]string, 0, len(grouped))
	for key := range grouped {
		keys = append(keys, key)
	}
	sort.Strings(keys)

	for i, containerName := range keys {
		lotes := dividirAnalisesParaGeracao(grouped[containerName])
		for indiceLote, methodsPayload := range lotes {
			registro.Info(
				"pipeline",
				"gerando testes para contêiner %d/%d lote %d/%d: %s (%d métodos, %d expaths)",
				i+1,
				len(keys),
				indiceLote+1,
				len(lotes),
				containerName,
				len(methodsPayload),
				contarCaminhosAnalises(methodsPayload),
			)
			systemPrompt := construirPromptGeracaoSistema(cfg.Projeto.TestFramework)
			userPrompt := construirPromptGeracaoUsuario(overview, containerName, methodsPayload)
			response, err := s.completionClient.CompletarJSON(model, systemPrompt, userPrompt, dominio.OpcoesRequisicaoLLM{
				PromptCacheKey: construirPromptCacheKey(identificarProjeto(cfg), "generation", containerName),
			})
			if err != nil {
				return dominio.RelatorioGeracao{}, "", workspace, fmt.Errorf("a geração falhou para %s (lote %d/%d): %w", containerName, indiceLote+1, len(lotes), err)
			}
			summary, files := normalizarRespostaGeracao(response.Payload)
			if strings.TrimSpace(summary) != "" {
				strategyParts = append(strategyParts, summary)
			}
			allFiles = append(allFiles, files...)
			rawResponses = append(rawResponses, response.Payload)

			if cfg.Fluxo.SalvarPrompts {
				stem := fmt.Sprintf("generation-%04d-%02d-%s", i+1, indiceLote+1, artefatos.Slugificar(containerName))
				if err := artefatos.EscreverTexto(filepath.Join(workspace.Prompts, stem+".txt"), userPrompt); err != nil {
					return dominio.RelatorioGeracao{}, "", workspace, err
				}
				if err := artefatos.EscreverTexto(filepath.Join(workspace.Respostas, stem+".txt"), response.RawText); err != nil {
					return dominio.RelatorioGeracao{}, "", workspace, err
				}
			}
		}
	}

	allFiles = consolidarArquivosGerados(allFiles)

	for _, file := range allFiles {
		rel, err := artefatos.CaminhoRelativoSeguro(file.CaminhoRelativo)
		if err != nil {
			return dominio.RelatorioGeracao{}, "", workspace, err
		}
		if err := artefatos.EscreverTexto(filepath.Join(workspace.Testes, rel), file.Conteudo); err != nil {
			return dominio.RelatorioGeracao{}, "", workspace, err
		}
	}

	report := dominio.RelatorioGeracao{
		IDExecucao:           filepath.Base(workspace.Raiz),
		CaminhoAnaliseOrigem: analysisPath,
		ChaveModelo:          modelKey,
		GeradoEm:             dominio.HorarioUTC(),
		ResumoEstrategia:     strings.TrimSpace(strings.Join(strategyParts, "\n")),
		ArquivosTeste:        allFiles,
		RespostasBrutas:      rawResponses,
	}
	generationPath := filepath.Join(workspace.Raiz, "generation.json")
	if err := artefatos.EscreverJSON(generationPath, report); err != nil {
		return dominio.RelatorioGeracao{}, "", workspace, err
	}
	if err := registrarArtefatoNoBanco(cfg, report.IDExecucao, "geracao_testes", "", "", generationPath, report.GeradoEm, report); err != nil {
		return dominio.RelatorioGeracao{}, "", workspace, err
	}
	registro.Info("pipeline", "geração concluída: arquivos=%d artefato=%s", len(report.ArquivosTeste), generationPath)
	return report, generationPath, workspace, nil
}

// Avaliar executa as métricas e, opcionalmente, a avaliação por juiz.
func (s *Servico) Avaliar(cfg *dominio.ConfigAplicacao, analysisReport dominio.RelatorioAnalise, analysisPath string, generationReport dominio.RelatorioGeracao, generationPath string, judgeModelKey string, workspace *artefatos.EspacoTrabalho) (dominio.RelatorioAvaliacao, string, *artefatos.EspacoTrabalho, error) {
	registro.Info("pipeline", "iniciando avaliação: análise=%s geração=%s juiz=%s", analysisPath, generationPath, judgeModelKey)
	var err error
	if workspace == nil {
		workspace, err = artefatos.NovoEspacoTrabalho(cfg.Fluxo.DiretorioSaida, artefatos.NovoIDExecucao("evaluate-"+generationReport.ChaveModelo))
		if err != nil {
			return dominio.RelatorioAvaliacao{}, "", workspace, err
		}
	}

	raizProjetoAvaliado, err := prepararSandboxAvaliacao(cfg, workspace)
	if err != nil {
		return dominio.RelatorioAvaliacao{}, "", workspace, err
	}

	metricResults := s.metricRunner.ExecutarTodas(cfg.Metricas, metricas.ContextoExecucao{
		RaizProjeto:       raizProjetoAvaliado,
		DiretorioExecucao: workspace.Raiz,
		DiretorioTestes:   workspace.Testes,
		CaminhoAnalise:    analysisPath,
		CaminhoGeracao:    generationPath,
		ChaveModelo:       generationReport.ChaveModelo,
	})
	metricScore := metricas.AgregarPontuacao(metricResults)
	registro.Info("pipeline", "métricas executadas: total=%d nota=%s", len(metricResults), metricas.FormatarPontuacao(metricScore))

	var judgeEvaluation *dominio.AvaliacaoJuiz
	var judgeScore *float64
	if strings.TrimSpace(judgeModelKey) != "" {
		judgeModel, err := getModelOrError(cfg, judgeModelKey)
		if err != nil {
			return dominio.RelatorioAvaliacao{}, "", workspace, err
		}
		judgePrompt := construirPromptJuizUsuario(analysisReport, generationReport, metricResults)
		response, err := s.completionClient.CompletarJSON(judgeModel, construirPromptJuizSistema(), judgePrompt, dominio.OpcoesRequisicaoLLM{
			PromptCacheKey: construirPromptCacheKey(identificarProjeto(cfg), "judge", generationReport.ChaveModelo),
		})
		if err != nil {
			return dominio.RelatorioAvaliacao{}, "", workspace, err
		}
		normalized := normalizarRespostaJuiz(response.Payload)
		judgeEvaluation = &normalized
		judgeScore = &normalized.Nota
		if cfg.Fluxo.SalvarPrompts {
			if err := artefatos.EscreverTexto(filepath.Join(workspace.Prompts, "judge.txt"), judgePrompt); err != nil {
				return dominio.RelatorioAvaliacao{}, "", workspace, err
			}
			if err := artefatos.EscreverTexto(filepath.Join(workspace.Respostas, "judge.txt"), response.RawText); err != nil {
				return dominio.RelatorioAvaliacao{}, "", workspace, err
			}
		}
	}

	combined := metricas.CombinarPontuacoes(metricScore, judgeScore)
	report := dominio.RelatorioAvaliacao{
		IDExecucao:         filepath.Base(workspace.Raiz),
		ChaveModelo:        generationReport.ChaveModelo,
		GeradoEm:           dominio.HorarioUTC(),
		CaminhoAnalise:     analysisPath,
		CaminhoGeracao:     generationPath,
		ResultadosMetricas: metricResults,
		NotaMetricas:       metricScore,
		ChaveModeloJuiz:    judgeModelKey,
		AvaliacaoJuiz:      judgeEvaluation,
		NotaCombinada:      combined,
	}
	evaluationPath := filepath.Join(workspace.Raiz, "evaluation.json")
	if err := artefatos.EscreverJSON(evaluationPath, report); err != nil {
		return dominio.RelatorioAvaliacao{}, "", workspace, err
	}
	if err := registrarArtefatoNoBanco(cfg, report.IDExecucao, "avaliacao", "", "", evaluationPath, report.GeradoEm, report); err != nil {
		return dominio.RelatorioAvaliacao{}, "", workspace, err
	}
	registro.Info("pipeline", "avaliação concluída: nota_final=%s artefato=%s", metricas.FormatarPontuacao(report.NotaCombinada), evaluationPath)
	return report, evaluationPath, workspace, nil
}

// prepararSandboxAvaliacao cria um checkout efêmero contendo apenas a suíte
// gerada para que as métricas não misturem testes originais e testes sintetizados.
// Isso preserva o invariante #5: a Parte 2 avalia em sandbox isolada.
func prepararSandboxAvaliacao(cfg *dominio.ConfigAplicacao, workspace *artefatos.EspacoTrabalho) (string, error) {
	raizSandbox := filepath.Join(os.TempDir(), "witup-llm-evaluation", filepath.Base(workspace.Raiz))
	if err := os.RemoveAll(raizSandbox); err != nil {
		return "", fmt.Errorf("ao limpar sandbox de avaliação %q: %w", raizSandbox, err)
	}
	if err := artefatos.CopiarDiretorioFiltrado(cfg.Projeto.Raiz, raizSandbox, cfg.Projeto.Exclude); err != nil {
		return "", fmt.Errorf("ao copiar o projeto para a sandbox de avaliação: %w", err)
	}
	if err := os.RemoveAll(filepath.Join(raizSandbox, "src", "test")); err != nil {
		return "", fmt.Errorf("ao limpar testes originais da sandbox de avaliação: %w", err)
	}
	if err := artefatos.CopiarDiretorioNoDestino(workspace.Testes, raizSandbox); err != nil {
		return "", fmt.Errorf("ao injetar os testes gerados na sandbox de avaliação: %w", err)
	}
	return raizSandbox, nil
}

package aplicacao

import (
	"fmt"
	"os"
	"path/filepath"
	"regexp"
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
	analysisReport = filtrarAnalisesParte2(analysisReport)
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
	harnessInterventions := make([]string, 0)
	totalRequests := 0
	totalInputTokens := 0
	totalOutputTokens := 0
	estimatedCost := acumuladorCustoLLM{}

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
			systemPrompt := construirPromptGeracaoSistema(resolverFrameworkTestes(cfg.Projeto))
			contextoComum := construirContextoGeracaoTestes(cfg.Projeto, containerName, extrairMetodosDasAnalises(methodsPayload))
			userPrompt := construirPromptGeracaoUsuario(overview, containerName, methodsPayload, contextoComum)
			response, err := s.completionClient.CompletarJSON(model, systemPrompt, userPrompt, dominio.OpcoesRequisicaoLLM{
				PromptCacheKey: construirPromptCacheKey(identificarProjeto(cfg), "generation", containerName),
			})
			if err != nil {
				return dominio.RelatorioGeracao{}, "", workspace, fmt.Errorf("a geração falhou para %s (lote %d/%d): %w", containerName, indiceLote+1, len(lotes), err)
			}
			summary, files := normalizarRespostaGeracao(response.Payload)
			files, intervencoes := adaptarArquivosTesteAoProjetoAuditado(cfg.Projeto.Raiz, files)
			if strings.TrimSpace(summary) != "" {
				strategyParts = append(strategyParts, summary)
			}
			allFiles = append(allFiles, files...)
			harnessInterventions = append(harnessInterventions, intervencoes...)
			rawResponses = append(rawResponses, enriquecerPayloadRespostaLLM(response.Payload, response))
			totalRequests++
			totalInputTokens += response.InputTokens
			totalOutputTokens += response.OutputTokens
			estimatedCost.adicionar(1, response.EstimatedCost)

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
		IntervencoesHarness:  deduplicarStrings(harnessInterventions),
		RequestCount:         totalRequests,
		InputTokens:          totalInputTokens,
		OutputTokens:         totalOutputTokens,
		EstimatedCost:        estimatedCost.valor(),
	}
	generationPath := filepath.Join(workspace.Raiz, "generation.json")
	if err := artefatos.EscreverJSON(generationPath, report); err != nil {
		return dominio.RelatorioGeracao{}, "", workspace, err
	}
	registro.Info(
		"pipeline",
		"geração concluída: arquivos=%d requests=%d input_tokens=%d output_tokens=%d custo=%s artefato=%s",
		len(report.ArquivosTeste),
		report.RequestCount,
		report.InputTokens,
		report.OutputTokens,
		formatarCustoLLM(report.EstimatedCost),
		generationPath,
	)
	return report, generationPath, workspace, nil
}

// GerarHintExcecao gera testes a partir da análise WIT mas envia ao modelo
// apenas os tipos de exceção (sem estrutura completa de expath), funcionando
// como condição de ablação entre WIT_CONTEXT e DIRECT_TESTS.
func (s *Servico) GerarHintExcecao(cfg *dominio.ConfigAplicacao, analysisReport dominio.RelatorioAnalise, analysisPath, modelKey string, workspace *artefatos.EspacoTrabalho) (dominio.RelatorioGeracao, string, *artefatos.EspacoTrabalho, error) {
	registro.Info("pipeline", "iniciando geração hint-exceção com modelo=%s origem=%s", modelKey, analysisPath)
	analysisReport = filtrarAnalisesParte2(analysisReport)
	model, err := getModelOrError(cfg, modelKey)
	if err != nil {
		return dominio.RelatorioGeracao{}, "", workspace, err
	}
	overview, err := s.catalogFactory.NovoCatalogo(cfg.Projeto).CarregarVisaoGeral()
	if err != nil {
		return dominio.RelatorioGeracao{}, "", workspace, err
	}
	if workspace == nil {
		workspace, err = artefatos.NovoEspacoTrabalho(cfg.Fluxo.DiretorioSaida, artefatos.NovoIDExecucao("generate-hint-"+modelKey))
		if err != nil {
			return dominio.RelatorioGeracao{}, "", workspace, err
		}
	}

	grouped := agruparAnalisesPorContainer(analysisReport)
	strategyParts := make([]string, 0, len(grouped))
	allFiles := make([]dominio.ArquivoTesteGerado, 0, len(grouped))
	rawResponses := make([]map[string]interface{}, 0, len(grouped))
	harnessInterventions := make([]string, 0)
	totalRequests, totalInputTokens, totalOutputTokens := 0, 0, 0
	estimatedCost := acumuladorCustoLLM{}

	keys := make([]string, 0, len(grouped))
	for k := range grouped {
		keys = append(keys, k)
	}
	sort.Strings(keys)

	for i, containerName := range keys {
		lotes := dividirAnalisesParaGeracao(grouped[containerName])
		for indiceLote, methodsPayload := range lotes {
			registro.Info("pipeline", "hint-exceção contêiner %d/%d lote %d/%d: %s (%d métodos)", i+1, len(keys), indiceLote+1, len(lotes), containerName, len(methodsPayload))
			systemPrompt := construirPromptGeracaoHintExcecaoSistema(resolverFrameworkTestes(cfg.Projeto))
			contextoComum := construirContextoGeracaoTestes(cfg.Projeto, containerName, extrairMetodosDasAnalises(methodsPayload))
			userPrompt := construirPromptGeracaoHintExcecaoUsuario(overview, containerName, methodsPayload, contextoComum)
			response, err := s.completionClient.CompletarJSON(model, systemPrompt, userPrompt, dominio.OpcoesRequisicaoLLM{
				PromptCacheKey: construirPromptCacheKey(identificarProjeto(cfg), "hint-generation", containerName),
			})
			if err != nil {
				return dominio.RelatorioGeracao{}, "", workspace, fmt.Errorf("geração hint-exceção falhou para %s (lote %d/%d): %w", containerName, indiceLote+1, len(lotes), err)
			}
			summary, files := normalizarRespostaGeracao(response.Payload)
			files, intervencoes := adaptarArquivosTesteAoProjetoAuditado(cfg.Projeto.Raiz, files)
			if strings.TrimSpace(summary) != "" {
				strategyParts = append(strategyParts, summary)
			}
			allFiles = append(allFiles, files...)
			harnessInterventions = append(harnessInterventions, intervencoes...)
			rawResponses = append(rawResponses, enriquecerPayloadRespostaLLM(response.Payload, response))
			totalRequests++
			totalInputTokens += response.InputTokens
			totalOutputTokens += response.OutputTokens
			estimatedCost.adicionar(1, response.EstimatedCost)

			if cfg.Fluxo.SalvarPrompts {
				stem := fmt.Sprintf("hint-generation-%04d-%02d-%s", i+1, indiceLote+1, artefatos.Slugificar(containerName))
				_ = artefatos.EscreverTexto(filepath.Join(workspace.Prompts, stem+".txt"), userPrompt)
				_ = artefatos.EscreverTexto(filepath.Join(workspace.Respostas, stem+".txt"), response.RawText)
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
		IntervencoesHarness:  deduplicarStrings(harnessInterventions),
		RequestCount:         totalRequests,
		InputTokens:          totalInputTokens,
		OutputTokens:         totalOutputTokens,
		EstimatedCost:        estimatedCost.valor(),
	}
	generationPath := filepath.Join(workspace.Raiz, "generation.json")
	if err := artefatos.EscreverJSON(generationPath, report); err != nil {
		return dominio.RelatorioGeracao{}, "", workspace, err
	}
	registro.Info("pipeline", "geração hint-exceção concluída: arquivos=%d requests=%d artefato=%s", len(report.ArquivosTeste), report.RequestCount, generationPath)
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
	analysisReport = filtrarAnalisesParte2(analysisReport)
	arquivosAdaptados, intervencoesAdaptacao := adaptarArquivosTesteAoProjetoAuditado(cfg.Projeto.Raiz, generationReport.ArquivosTeste)
	generationReport.ArquivosTeste = arquivosAdaptados
	generationReport.IntervencoesHarness = deduplicarStrings(append(generationReport.IntervencoesHarness, intervencoesAdaptacao...))
	analysisPathMetricas := analysisPath
	generationPathMetricas := generationPath
	if analysisPathMetricas, generationPathMetricas, err = materializarArtefatosFiltradosParte2(workspace, analysisReport, generationReport); err != nil {
		return dominio.RelatorioAvaliacao{}, "", workspace, err
	}
	if err := materializarSuiteGeradaNoWorkspace(workspace, generationReport); err != nil {
		return dominio.RelatorioAvaliacao{}, "", workspace, err
	}

	raizProjetoAvaliado, intervencoesSandbox, err := prepararSandboxAvaliacaoAuditado(cfg, workspace)
	if err != nil {
		return dominio.RelatorioAvaliacao{}, "", workspace, err
	}
	intervencoesHarness := deduplicarStrings(append(append([]string{}, generationReport.IntervencoesHarness...), intervencoesSandbox...))
	if len(intervencoesHarness) > 0 {
		if err := artefatos.EscreverTexto(filepath.Join(workspace.Logs, "harness-interventions.txt"), strings.Join(intervencoesHarness, "\n")+"\n"); err != nil {
			registro.Info("pipeline", "não foi possível persistir intervenções do harness: %v", err)
		}
	}

	metricResults := s.metricRunner.ExecutarTodas(cfg.Metricas, metricas.ContextoExecucao{
		RaizProjeto:       raizProjetoAvaliado,
		DiretorioExecucao: workspace.Raiz,
		DiretorioTestes:   workspace.Testes,
		CaminhoAnalise:    analysisPathMetricas,
		CaminhoGeracao:    generationPathMetricas,
		ChaveModelo:       generationReport.ChaveModelo,
	})
	if err := persistirLogsMetricas(workspace, metricResults); err != nil {
		registro.Info("pipeline", "não foi possível persistir logs das métricas: %v", err)
	}
	registrarResumoMetricas(metricResults, workspace)
	metricScore, auditoriaPontuacao := calcularPontuacaoAuditadaSegundaFase(metricResults, len(generationReport.ArquivosTeste))
	registro.Info("pipeline", "métricas executadas: total=%d nota=%s", len(metricResults), metricas.FormatarPontuacao(metricScore))

	var judgeEvaluation *dominio.AvaliacaoJuiz
	var judgeScore *float64
	judgeRequestCount := 0
	judgeInputTokens := 0
	judgeOutputTokens := 0
	judgeEstimatedCost := acumuladorCustoLLM{}
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
		judgeRequestCount = 1
		judgeInputTokens = response.InputTokens
		judgeOutputTokens = response.OutputTokens
		judgeEstimatedCost.adicionar(1, response.EstimatedCost)
		judgeEvaluation.RespostaBruta = enriquecerPayloadRespostaLLM(judgeEvaluation.RespostaBruta, response)
		if cfg.Fluxo.SalvarPrompts {
			if err := artefatos.EscreverTexto(filepath.Join(workspace.Prompts, "judge.txt"), judgePrompt); err != nil {
				return dominio.RelatorioAvaliacao{}, "", workspace, err
			}
			if err := artefatos.EscreverTexto(filepath.Join(workspace.Respostas, "judge.txt"), response.RawText); err != nil {
				return dominio.RelatorioAvaliacao{}, "", workspace, err
			}
		}
		registro.Info(
			"pipeline",
			"juiz concluído: requests=%d input_tokens=%d output_tokens=%d custo=%s",
			judgeRequestCount,
			judgeInputTokens,
			judgeOutputTokens,
			formatarCustoLLM(judgeEstimatedCost.valor()),
		)
	}

	combined := metricas.CombinarPontuacoes(metricScore, judgeScore)
	report := dominio.RelatorioAvaliacao{
		IDExecucao:          filepath.Base(workspace.Raiz),
		ChaveModelo:         generationReport.ChaveModelo,
		GeradoEm:            dominio.HorarioUTC(),
		CaminhoAnalise:      analysisPath,
		CaminhoGeracao:      generationPath,
		ResultadosMetricas:  metricResults,
		NotaMetricas:        metricScore,
		AuditoriaPontuacao:  auditoriaPontuacao,
		ChaveModeloJuiz:     judgeModelKey,
		AvaliacaoJuiz:       judgeEvaluation,
		NotaCombinada:       combined,
		IntervencoesHarness: intervencoesHarness,
		RequestCount:        judgeRequestCount,
		InputTokens:         judgeInputTokens,
		OutputTokens:        judgeOutputTokens,
		EstimatedCost:       judgeEstimatedCost.valor(),
	}
	evaluationPath := filepath.Join(workspace.Raiz, "evaluation.json")
	if err := artefatos.EscreverJSON(evaluationPath, report); err != nil {
		return dominio.RelatorioAvaliacao{}, "", workspace, err
	}
	registro.Info(
		"pipeline",
		"avaliação concluída: nota_final=%s judge_requests=%d judge_input_tokens=%d judge_output_tokens=%d judge_custo=%s artefato=%s",
		metricas.FormatarPontuacao(report.NotaCombinada),
		report.RequestCount,
		report.InputTokens,
		report.OutputTokens,
		formatarCustoLLM(report.EstimatedCost),
		evaluationPath,
	)
	return report, evaluationPath, workspace, nil
}

// prepararSandboxAvaliacao cria um checkout efêmero contendo apenas a suíte
// gerada para que as métricas não misturem testes originais e testes sintetizados.
// Isso preserva o invariante #5: a Parte 2 avalia em sandbox isolada.
func prepararSandboxAvaliacao(cfg *dominio.ConfigAplicacao, workspace *artefatos.EspacoTrabalho) (string, error) {
	raiz, _, err := prepararSandboxAvaliacaoAuditado(cfg, workspace)
	return raiz, err
}

func prepararSandboxAvaliacaoAuditado(cfg *dominio.ConfigAplicacao, workspace *artefatos.EspacoTrabalho) (string, []string, error) {
	raizSandbox := filepath.Join(os.TempDir(), "witup-llm-evaluation", filepath.Base(workspace.Raiz))
	if err := os.RemoveAll(raizSandbox); err != nil {
		return "", nil, fmt.Errorf("ao limpar sandbox de avaliação %q: %w", raizSandbox, err)
	}
	if err := artefatos.CopiarDiretorioFiltrado(cfg.Projeto.Raiz, raizSandbox, cfg.Projeto.Exclude); err != nil {
		return "", nil, fmt.Errorf("ao copiar o projeto para a sandbox de avaliação: %w", err)
	}
	if err := removerDiretoriosTestesOriginais(raizSandbox); err != nil {
		return "", nil, fmt.Errorf("ao limpar testes originais da sandbox de avaliação: %w", err)
	}
	if err := artefatos.CopiarDiretorioNoDestino(workspace.Testes, raizSandbox); err != nil {
		return "", nil, fmt.Errorf("ao injetar os testes gerados na sandbox de avaliação: %w", err)
	}
	intervencoes, err := prepararProjetoMavenParaAvaliacao(raizSandbox, resolverFrameworkTestes(cfg.Projeto))
	if err != nil {
		return "", nil, fmt.Errorf("ao preparar o projeto Maven na sandbox de avaliação: %w", err)
	}
	return raizSandbox, intervencoes, nil
}

// materializarSuiteGeradaNoWorkspace reidrata os arquivos da geração quando a
// avaliação é executada isoladamente a partir de um generation.json já salvo.
func materializarSuiteGeradaNoWorkspace(workspace *artefatos.EspacoTrabalho, generationReport dominio.RelatorioGeracao) error {
	if workspace == nil {
		return nil
	}
	for _, arquivo := range generationReport.ArquivosTeste {
		rel, err := artefatos.CaminhoRelativoSeguro(arquivo.CaminhoRelativo)
		if err != nil {
			return err
		}
		if err := artefatos.EscreverTexto(filepath.Join(workspace.Testes, rel), arquivo.Conteudo); err != nil {
			return err
		}
	}
	return nil
}

func materializarArtefatosFiltradosParte2(workspace *artefatos.EspacoTrabalho, analysisReport dominio.RelatorioAnalise, generationReport dominio.RelatorioGeracao) (string, string, error) {
	if workspace == nil {
		return "", "", fmt.Errorf("workspace de avaliação ausente")
	}
	analysisPath := filepath.Join(workspace.Raiz, "analysis-parte-2.json")
	if err := artefatos.EscreverJSON(analysisPath, analysisReport); err != nil {
		return "", "", err
	}
	generationPath := filepath.Join(workspace.Raiz, "generation-parte-2.json")
	if err := artefatos.EscreverJSON(generationPath, generationReport); err != nil {
		return "", "", err
	}
	return analysisPath, generationPath, nil
}

func persistirLogsMetricas(workspace *artefatos.EspacoTrabalho, metricResults []dominio.ResultadoMetrica) error {
	if workspace == nil {
		return nil
	}
	for _, resultado := range metricResults {
		base := filepath.Join(workspace.Logs, "metrics", artefatos.Slugificar(resultado.Nome))
		if strings.TrimSpace(resultado.SaidaPadrao) != "" {
			if err := artefatos.EscreverTexto(base+".stdout.log", resultado.SaidaPadrao); err != nil {
				return err
			}
		}
		if strings.TrimSpace(resultado.SaidaErro) != "" {
			if err := artefatos.EscreverTexto(base+".stderr.log", resultado.SaidaErro); err != nil {
				return err
			}
		}
	}
	return nil
}

func registrarResumoMetricas(metricResults []dominio.ResultadoMetrica, workspace *artefatos.EspacoTrabalho) {
	for _, resultado := range metricResults {
		registro.Info(
			"pipeline",
			"métrica=%s sucesso=%t exit=%d nota=%s",
			resultado.Nome,
			resultado.Sucesso,
			resultado.CodigoSaida,
			metricas.FormatarPontuacao(resultado.NotaNormalizada),
		)
		if resultado.Sucesso {
			continue
		}
		if resumo := resumirSaidaLog(resultado.SaidaPadrao); resumo != "" {
			registro.Info("pipeline", "métrica=%s stdout-resumo=%s", resultado.Nome, resumo)
		}
		if resumo := resumirSaidaLog(resultado.SaidaErro); resumo != "" {
			registro.Info("pipeline", "métrica=%s stderr-resumo=%s", resultado.Nome, resumo)
		}
		if workspace != nil {
			registro.Info(
				"pipeline",
				"métrica=%s logs=%s",
				resultado.Nome,
				filepath.Join(workspace.Logs, "metrics", artefatos.Slugificar(resultado.Nome)),
			)
		}
	}
}

func resumirSaidaLog(texto string) string {
	texto = strings.TrimSpace(texto)
	if texto == "" {
		return ""
	}
	linhas := strings.Split(texto, "\n")
	resumidas := make([]string, 0, 4)
	for _, linha := range linhas {
		linha = strings.TrimSpace(linha)
		if linha == "" {
			continue
		}
		resumidas = append(resumidas, linha)
		if len(resumidas) == 4 {
			break
		}
	}
	return strings.Join(resumidas, " | ")
}

// prepararProjetoMavenParaAvaliacao remove extensões e plugins de release que
// não participam da execução das métricas, mas podem impedir builds locais da
// sandbox por exigirem credenciais ou repositórios extras.
func prepararProjetoMavenParaAvaliacao(raizSandbox string, frameworkProjeto string) ([]string, error) {
	caminhosPOM, err := localizarPOMsMaven(raizSandbox)
	if err != nil {
		return nil, err
	}
	if len(caminhosPOM) == 0 {
		return nil, nil
	}

	intervencoes := make([]string, 0)
	for _, caminhoPOM := range caminhosPOM {
		relativo, err := filepath.Rel(raizSandbox, caminhoPOM)
		if err != nil {
			relativo = caminhoPOM
		}
		atuais, err := prepararPOMMavenParaAvaliacao(caminhoPOM, raizSandbox, frameworkProjeto)
		if err != nil {
			return nil, fmt.Errorf("ao preparar %s: %w", relativo, err)
		}
		intervencoes = append(intervencoes, atuais...)
	}
	return deduplicarStrings(intervencoes), nil
}

func prepararPOMMavenParaAvaliacao(caminhoPOM string, raizSandbox string, frameworkProjeto string) ([]string, error) {
	dados, err := os.ReadFile(caminhoPOM)
	if err != nil {
		if os.IsNotExist(err) {
			return nil, nil
		}
		return nil, fmt.Errorf("ao ler pom.xml da sandbox: %w", err)
	}

	conteudoOriginal := string(dados)
	conteudoAjustado := conteudoOriginal
	intervencoes := make([]string, 0)
	if pomEhAgregadorMaven(conteudoOriginal) {
		intervencoes = append(intervencoes, "sandbox_kept_aggregator_packaging_pom")
	} else {
		conteudoAjustado = substituirTagXML(conteudoAjustado, "packaging", "jar")
	}
	conteudoAjustado = removerTagXML(conteudoAjustado, "defaultGoal")
	conteudoAjustado = ajustarNivelCompilacaoMaven(conteudoAjustado)
	for _, artifactID := range []string{
		"nexus-staging-maven-plugin",
		"maven-gpg-plugin",
		"maven-release-plugin",
		"maven-plugin-plugin",
		"license-maven-plugin",
		"apache-rat-plugin",
		"maven-checkstyle-plugin",
		"spotbugs-maven-plugin",
		"maven-pmd-plugin",
		"japicmp-maven-plugin",
	} {
		conteudoAjustado = removerPluginMaven(conteudoAjustado, artifactID)
	}
	conteudoAjustado = removerBlocoXML(conteudoAjustado, "distributionManagement")
	conteudoAjustado = garantirPropriedadesSkipMaven(conteudoAjustado)
	if testesGeradosUsamJUnitJupiter(raizSandbox) && !pomSuportaJUnitJupiter(conteudoAjustado) {
		conteudoAjustado = garantirDependenciasJUnitJupiter(conteudoAjustado)
		conteudoAjustado = garantirPluginSurefireCompativelJUnit5(conteudoAjustado)
		intervencoes = append(intervencoes, "sandbox_added_junit_jupiter")
		registro.Info("pipeline", "sandbox de avaliação adaptada para compilar/executar testes JUnit 5 sobre projeto %s", frameworkProjeto)
	}
	if testesGeradosUsamJUnitJupiter(raizSandbox) {
		antesPIT := conteudoAjustado
		conteudoAjustado = garantirPluginPITCompativelJUnit5(conteudoAjustado)
		if conteudoAjustado != antesPIT {
			intervencoes = append(intervencoes, "sandbox_added_pitest_junit5")
		}
	}
	if testesGeradosUsamMockito(raizSandbox) && !pomSuportaMockito(conteudoAjustado) {
		conteudoAjustado = garantirDependenciasMockito(conteudoAjustado)
		intervencoes = append(intervencoes, "sandbox_added_mockito")
		registro.Info("pipeline", "sandbox de avaliação adaptada para compilar testes que usam Mockito")
	}

	if conteudoAjustado == conteudoOriginal {
		return deduplicarStrings(intervencoes), nil
	}
	if len(intervencoes) == 0 {
		intervencoes = append(intervencoes, "sandbox_sanitized_pom")
	}
	if err := os.WriteFile(caminhoPOM, []byte(conteudoAjustado), 0o644); err != nil {
		return nil, fmt.Errorf("ao reescrever pom.xml sanitizado na sandbox: %w", err)
	}
	return deduplicarStrings(intervencoes), nil
}

func localizarPOMsMaven(raizSandbox string) ([]string, error) {
	caminhos := make([]string, 0)
	err := filepath.Walk(raizSandbox, func(caminho string, info os.FileInfo, err error) error {
		if err != nil {
			return err
		}
		if info == nil {
			return nil
		}
		if info.IsDir() {
			nome := info.Name()
			if nome == ".git" || nome == "target" || nome == "build" {
				return filepath.SkipDir
			}
			return nil
		}
		if info.Name() == "pom.xml" {
			caminhos = append(caminhos, caminho)
		}
		return nil
	})
	if err != nil {
		return nil, fmt.Errorf("ao localizar POMs Maven na sandbox: %w", err)
	}
	sort.Slice(caminhos, func(i, j int) bool {
		profundidadeI := strings.Count(filepath.ToSlash(caminhos[i]), "/")
		profundidadeJ := strings.Count(filepath.ToSlash(caminhos[j]), "/")
		if profundidadeI == profundidadeJ {
			return caminhos[i] < caminhos[j]
		}
		return profundidadeI < profundidadeJ
	})
	return caminhos, nil
}

func removerDiretoriosTestesOriginais(raizSandbox string) error {
	diretorios := make([]string, 0)
	err := filepath.Walk(raizSandbox, func(caminho string, info os.FileInfo, err error) error {
		if err != nil {
			return err
		}
		if info == nil || !info.IsDir() {
			return nil
		}
		nome := info.Name()
		if nome == ".git" || nome == "target" || nome == "build" {
			return filepath.SkipDir
		}
		normalizado := filepath.ToSlash(caminho)
		if strings.HasSuffix(normalizado, "/src/test") || normalizado == filepath.ToSlash(filepath.Join(raizSandbox, "src", "test")) {
			diretorios = append(diretorios, caminho)
			return filepath.SkipDir
		}
		return nil
	})
	if err != nil {
		return err
	}
	sort.Slice(diretorios, func(i, j int) bool {
		return len(diretorios[i]) > len(diretorios[j])
	})
	for _, dir := range diretorios {
		if err := os.RemoveAll(dir); err != nil {
			return err
		}
	}
	return nil
}

func pomEhAgregadorMaven(conteudo string) bool {
	return strings.Contains(conteudo, "<packaging>pom</packaging>") &&
		strings.Contains(conteudo, "<modules>") &&
		strings.Contains(conteudo, "</modules>")
}

// garantirDependenciasJUnitJupiter injeta as dependências mínimas de API e
// engine do JUnit 5 para que a sandbox consiga compilar e executar testes
// previamente gerados com Jupiter.
func garantirDependenciasJUnitJupiter(conteudo string) string {
	if pomSuportaJUnitJupiter(conteudo) {
		return conteudo
	}
	bloco := `
    <dependency>
      <groupId>org.junit.jupiter</groupId>
      <artifactId>junit-jupiter-api</artifactId>
      <version>5.10.2</version>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>org.junit.jupiter</groupId>
      <artifactId>junit-jupiter-engine</artifactId>
      <version>5.10.2</version>
      <scope>test</scope>
    </dependency>`
	if strings.Contains(conteudo, "</dependencies>") {
		return strings.Replace(conteudo, "</dependencies>", bloco+"\n  </dependencies>", 1)
	}
	if strings.Contains(conteudo, "</project>") {
		novoBloco := fmt.Sprintf("  <dependencies>%s\n  </dependencies>\n</project>", bloco)
		return strings.Replace(conteudo, "</project>", novoBloco, 1)
	}
	return conteudo
}

// garantirDependenciasMockito injeta Mockito quando os testes gerados
// dependem dele e o projeto original não o declara.
func garantirDependenciasMockito(conteudo string) string {
	if pomSuportaMockito(conteudo) {
		return conteudo
	}
	bloco := `
    <dependency>
      <groupId>org.mockito</groupId>
      <artifactId>mockito-core</artifactId>
      <version>5.12.0</version>
      <scope>test</scope>
    </dependency>`
	if strings.Contains(conteudo, "</dependencies>") {
		return strings.Replace(conteudo, "</dependencies>", bloco+"\n  </dependencies>", 1)
	}
	if strings.Contains(conteudo, "</project>") {
		novoBloco := fmt.Sprintf("  <dependencies>%s\n  </dependencies>\n</project>", bloco)
		return strings.Replace(conteudo, "</project>", novoBloco, 1)
	}
	return conteudo
}

// garantirPluginSurefireCompativelJUnit5 adiciona uma versão moderna do
// Surefire quando o projeto original não a declara explicitamente.
func garantirPluginSurefireCompativelJUnit5(conteudo string) string {
	if strings.Contains(conteudo, "<artifactId>maven-surefire-plugin</artifactId>") {
		return conteudo
	}
	plugin := `
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <version>3.2.5</version>
        <configuration>
          <useModulePath>false</useModulePath>
        </configuration>
      </plugin>`
	if strings.Contains(conteudo, "</plugins>") {
		return strings.Replace(conteudo, "</plugins>", plugin+"\n    </plugins>", 1)
	}
	if strings.Contains(conteudo, "</build>") {
		novoBloco := fmt.Sprintf("    <plugins>%s\n    </plugins>\n  </build>", plugin)
		return strings.Replace(conteudo, "</build>", novoBloco, 1)
	}
	if strings.Contains(conteudo, "</project>") {
		novoBloco := fmt.Sprintf("  <build>\n    <plugins>%s\n    </plugins>\n  </build>\n</project>", plugin)
		return strings.Replace(conteudo, "</project>", novoBloco, 1)
	}
	return conteudo
}

// garantirPluginPITCompativelJUnit5 injeta o plugin do PIT para JUnit 5 quando
// os testes gerados usam Jupiter.
func garantirPluginPITCompativelJUnit5(conteudo string) string {
	blocoPlugin := `
      <plugin>
        <groupId>org.pitest</groupId>
        <artifactId>pitest-maven</artifactId>
        <version>1.23.0</version>
        <dependencies>
          <dependency>
            <groupId>org.pitest</groupId>
            <artifactId>pitest-junit5-plugin</artifactId>
            <version>1.2.2</version>
          </dependency>
        </dependencies>
      </plugin>`

	if strings.Contains(conteudo, "<artifactId>pitest-maven</artifactId>") {
		if strings.Contains(conteudo, "<artifactId>pitest-junit5-plugin</artifactId>") {
			return conteudo
		}
		regexPlugins := regexp.MustCompile(`(?s)<plugin>.*?<artifactId>\s*pitest-maven\s*</artifactId>.*?</plugin>`)
		return regexPlugins.ReplaceAllStringFunc(conteudo, func(bloco string) string {
			if strings.Contains(bloco, "<artifactId>pitest-junit5-plugin</artifactId>") {
				return bloco
			}
			if strings.Contains(bloco, "</dependencies>") {
				dep := `
          <dependency>
            <groupId>org.pitest</groupId>
            <artifactId>pitest-junit5-plugin</artifactId>
            <version>1.2.2</version>
          </dependency>`
				return strings.Replace(bloco, "</dependencies>", dep+"\n        </dependencies>", 1)
			}
			if strings.Contains(bloco, "</plugin>") {
				deps := `
        <dependencies>
          <dependency>
            <groupId>org.pitest</groupId>
            <artifactId>pitest-junit5-plugin</artifactId>
            <version>1.2.2</version>
          </dependency>
        </dependencies>`
				return strings.Replace(bloco, "</plugin>", deps+"\n      </plugin>", 1)
			}
			return bloco
		})
	}

	if strings.Contains(conteudo, "</plugins>") {
		return strings.Replace(conteudo, "</plugins>", blocoPlugin+"\n    </plugins>", 1)
	}
	if strings.Contains(conteudo, "</build>") {
		novoBloco := fmt.Sprintf("    <plugins>%s\n    </plugins>\n  </build>", blocoPlugin)
		return strings.Replace(conteudo, "</build>", novoBloco, 1)
	}
	if strings.Contains(conteudo, "</project>") {
		novoBloco := fmt.Sprintf("  <build>\n    <plugins>%s\n    </plugins>\n  </build>\n</project>", blocoPlugin)
		return strings.Replace(conteudo, "</project>", novoBloco, 1)
	}
	return conteudo
}

// removerPluginMaven elimina plugins específicos do POM quando a execução de
// avaliação precisa ignorar etapas de release/deploy.
func removerPluginMaven(conteudo, artifactID string) string {
	regexPlugins := regexp.MustCompile(`(?s)<plugin>.*?</plugin>`)
	regexArtifact := regexp.MustCompile(fmt.Sprintf(`<artifactId>\s*%s\s*</artifactId>`, regexp.QuoteMeta(artifactID)))
	return regexPlugins.ReplaceAllStringFunc(conteudo, func(bloco string) string {
		if regexArtifact.MatchString(bloco) {
			return ""
		}
		return bloco
	})
}

// removerBlocoXML apaga blocos simples do POM que não influenciam compilação
// ou execução dos testes durante a avaliação.
func removerBlocoXML(conteudo, nome string) string {
	padrao := fmt.Sprintf(`(?s)<%s>\s*.*?</%s>`, regexp.QuoteMeta(nome), regexp.QuoteMeta(nome))
	return regexp.MustCompile(padrao).ReplaceAllString(conteudo, "")
}

// removerTagXML elimina uma tag simples do documento quando ela está presente.
func removerTagXML(conteudo, nomeTag string) string {
	padrao := fmt.Sprintf(`(?s)<%s>\s*.*?\s*</%s>`, regexp.QuoteMeta(nomeTag), regexp.QuoteMeta(nomeTag))
	return regexp.MustCompile(padrao).ReplaceAllString(conteudo, "")
}

// substituirTagXML troca o valor textual de uma tag simples quando ela está
// presente no documento.
func substituirTagXML(conteudo, nomeTag, novoValor string) string {
	padrao := fmt.Sprintf(`(?s)<%s>\s*.*?\s*</%s>`, regexp.QuoteMeta(nomeTag), regexp.QuoteMeta(nomeTag))
	regex := regexp.MustCompile(padrao)
	if !regex.MatchString(conteudo) {
		return conteudo
	}
	replacement := fmt.Sprintf("<%s>%s</%s>", nomeTag, novoValor, nomeTag)
	return regex.ReplaceAllString(conteudo, replacement)
}

// ajustarNivelCompilacaoMaven eleva source/target antigos para Java 8 dentro
// da sandbox, evitando falhas de compilação em toolchains atuais.
func ajustarNivelCompilacaoMaven(conteudo string) string {
	for _, tag := range []string{"source", "target"} {
		padrao := fmt.Sprintf(`(?s)<%s>\s*(?:1\.)?[0-7]\s*</%s>`, regexp.QuoteMeta(tag), regexp.QuoteMeta(tag))
		regex := regexp.MustCompile(padrao)
		replacement := fmt.Sprintf("<%s>1.8</%s>", tag, tag)
		conteudo = regex.ReplaceAllString(conteudo, replacement)
	}
	return conteudo
}

// garantirPropriedadesSkipMaven injeta propriedades que desligam etapas de
// release/compliance irrelevantes para a medição da suíte gerada.
func garantirPropriedadesSkipMaven(conteudo string) string {
	propriedades := map[string]string{
		"rat.skip":           "true",
		"checkstyle.skip":    "true",
		"pmd.skip":           "true",
		"spotbugs.skip":      "true",
		"japicmp.skip":       "true",
		"maven.javadoc.skip": "true",
		"skipITs":            "true",
		"enforcer.skip":      "true",
	}

	for nome, valor := range propriedades {
		if strings.Contains(conteudo, "<"+nome+">") {
			conteudo = substituirTagXML(conteudo, nome, valor)
			continue
		}
		bloco := fmt.Sprintf("    <%s>%s</%s>\n", nome, valor, nome)
		if strings.Contains(conteudo, "</properties>") {
			conteudo = strings.Replace(conteudo, "</properties>", bloco+"  </properties>", 1)
			continue
		}
		if strings.Contains(conteudo, "</project>") {
			novoBloco := fmt.Sprintf("  <properties>\n%s  </properties>\n</project>", bloco)
			conteudo = strings.Replace(conteudo, "</project>", novoBloco, 1)
		}
	}
	return conteudo
}

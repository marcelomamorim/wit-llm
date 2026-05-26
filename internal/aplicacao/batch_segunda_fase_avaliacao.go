package aplicacao

import (
	"context"
	"fmt"
	"os"
	"path/filepath"
	"sort"
	"strings"

	"github.com/marceloamorim/witup-llm/internal/artefatos"
	"github.com/marceloamorim/witup-llm/internal/dominio"
	"github.com/marceloamorim/witup-llm/internal/llm"
	"github.com/marceloamorim/witup-llm/internal/registro"
	"github.com/marceloamorim/witup-llm/internal/visualizacao"
	"github.com/marceloamorim/witup-llm/internal/witup"
)

// AvaliarBatchSegundaFase materializa respostas OpenAI Batch em generation.json
// por cenário e executa apenas a avaliação local, sem novas chamadas pagas.
func (s *Servico) AvaliarBatchSegundaFase(cfg *dominio.ConfigAplicacao, generationModelKey, responsesPath, errorsPath, outputDir, runStamp string) (dominio.RelatorioSegundaFase, string, error) {
	if len(cfg.SegundaFase.Projetos) == 0 {
		return dominio.RelatorioSegundaFase{}, "", fmt.Errorf("phase_two.projects precisa listar ao menos um projeto")
	}
	model, err := getModelOrError(cfg, generationModelKey)
	if err != nil {
		return dominio.RelatorioSegundaFase{}, "", err
	}
	resultadosBatch, err := llm.LerResultadosBatch(responsesPath)
	if err != nil {
		return dominio.RelatorioSegundaFase{}, "", err
	}
	if strings.TrimSpace(outputDir) == "" {
		return dominio.RelatorioSegundaFase{}, "", fmt.Errorf("outputDir da rodada Batch é obrigatório")
	}
	if strings.TrimSpace(runStamp) == "" {
		runStamp = dominio.HorarioUTC()
		runStamp = strings.NewReplacer("-", "", ":", "", ".", "", "Z", "Z").Replace(runStamp)
	}
	if strings.TrimSpace(errorsPath) != "" {
		if _, err := os.Stat(errorsPath); err == nil {
			if err := copiarArquivo(errorsPath, filepath.Join(outputDir, "errors_openai_batch_generation.jsonl")); err != nil {
				return dominio.RelatorioSegundaFase{}, "", err
			}
		}
	}

	ctxHeartbeat, cancelCtxHeartbeat := context.WithCancel(context.Background())
	progressoHeartbeat := registro.NovoProgresso(len(cfg.SegundaFase.Projetos))
	cancelHeartbeat := registro.IniciarHeartbeat(ctxHeartbeat, "phase-two", "batch_materialize_evaluate", "all", "running", progressoHeartbeat)
	defer cancelHeartbeat()
	defer cancelCtxHeartbeat()

	comparacoes := make([]dominio.ComparacaoProjetoSegundaFase, 0, len(cfg.SegundaFase.Projetos))
	cacheCatalogo := map[string][]dominio.DescritorMetodo{}
	for _, projeto := range cfg.SegundaFase.Projetos {
		comparacao, err := s.avaliarProjetoBatchSegundaFase(cfg, projeto, model, generationModelKey, resultadosBatch, outputDir, cacheCatalogo)
		if err != nil {
			return dominio.RelatorioSegundaFase{}, "", err
		}
		comparacoes = append(comparacoes, comparacao)
		progressoHeartbeat.Incrementar()
	}

	relatorio := dominio.RelatorioSegundaFase{
		IDExecucao:         filepath.Base(outputDir),
		GeradoEm:           dominio.HorarioUTC(),
		ChaveModeloGeracao: generationModelKey,
		Projetos:           comparacoes,
	}
	if err := os.MkdirAll(outputDir, 0o755); err != nil {
		return dominio.RelatorioSegundaFase{}, "", err
	}

	workspace := &artefatos.EspacoTrabalho{Raiz: outputDir}
	resumoCSV, metricasCSV, comparacaoCSV, err := exportarCSVsSegundaFase(workspace, relatorio)
	if err != nil {
		return dominio.RelatorioSegundaFase{}, "", err
	}
	academicSummary := filepath.Join(outputDir, fmt.Sprintf("results_%s_paired_summary.csv", runStamp))
	academicMetrics := filepath.Join(outputDir, fmt.Sprintf("results_%s_paired_metrics.csv", runStamp))
	academicComparison := filepath.Join(outputDir, fmt.Sprintf("results_%s_paired_comparison.csv", runStamp))
	if err := copiarArquivo(resumoCSV, academicSummary); err != nil {
		return dominio.RelatorioSegundaFase{}, "", err
	}
	if err := copiarArquivo(metricasCSV, academicMetrics); err != nil {
		return dominio.RelatorioSegundaFase{}, "", err
	}
	if err := copiarArquivo(comparacaoCSV, academicComparison); err != nil {
		return dominio.RelatorioSegundaFase{}, "", err
	}
	relatorio.CaminhoCSVResumo = academicSummary
	relatorio.CaminhoCSVMetricas = academicMetrics
	relatorio.CaminhoCSVComparacao = academicComparison

	dashboardPath := filepath.Join(outputDir, fmt.Sprintf("dashboard_%s_wit_expath_regression.html", runStamp))
	dashboard, err := visualizacao.NovoGeradorSegundaFase(cfg.SegundaFase.TituloVisualizacao).Gerar(relatorio, dashboardPath)
	if err != nil {
		return dominio.RelatorioSegundaFase{}, "", err
	}
	relatorio.CaminhoDashboard = dashboard

	reportPath := filepath.Join(outputDir, fmt.Sprintf("results_%s_paired_study.json", runStamp))
	if err := artefatos.EscreverJSON(reportPath, relatorio); err != nil {
		return dominio.RelatorioSegundaFase{}, "", err
	}
	registro.Info("phase-two", "batch materializado e avaliado: relatório=%s dashboard=%s", reportPath, dashboard)
	return relatorio, reportPath, nil
}

func (s *Servico) avaliarProjetoBatchSegundaFase(
	cfg *dominio.ConfigAplicacao,
	projeto dominio.ConfigProjetoSegundaFase,
	model dominio.ConfigModelo,
	generationModelKey string,
	resultadosBatch map[string]llm.LinhaResultadoBatch,
	outputDir string,
	cacheCatalogo map[string][]dominio.DescritorMetodo,
) (dominio.ComparacaoProjetoSegundaFase, error) {
	cfgProjeto := clonarConfiguracaoParaProjetoSegundaFase(cfg, projeto)
	cfgProjeto.Fluxo.ModeloJuiz = ""
	cfgProjeto.Metricas = filtrarMetricasSegundaFase(cfgProjeto.Metricas)
	metodosCatalogados, err := s.carregarCatalogoSegundaFaseComCache(cfgProjeto, cacheCatalogo)
	if err != nil {
		return dominio.ComparacaoProjetoSegundaFase{}, err
	}
	metodosCatalogados = filtrarMetodosPorContainers(metodosCatalogados, projeto.ContainersAlvo)
	baselineReport, err := witup.CarregarAnalise(projeto.CaminhoBaseline)
	if err != nil {
		return dominio.ComparacaoProjetoSegundaFase{}, fmt.Errorf("ao carregar baseline WIT do projeto %s: %w", projeto.Chave, err)
	}
	baselineAlinhada, metodosAlvo, resumo := alinharWITUPAoCatalogo(baselineReport, metodosCatalogados, cfgProjeto.Fluxo.MaximoMetodos)
	if len(metodosAlvo) == 0 {
		return dominio.ComparacaoProjetoSegundaFase{}, fmt.Errorf("nenhum método WIT foi alinhado no projeto %s", projeto.Chave)
	}
	registro.Info("phase-two", "batch avaliação projeto=%s baseline=%d alinhados=%d não_encontrados=%d", projeto.Chave, resumo.QuantidadeBaseline, resumo.QuantidadeCorrespondidos, resumo.QuantidadeNaoEncontrados)

	baseProjeto := filepath.Join(outputDir, artefatos.Slugificar(projeto.Chave))
	overview, err := s.catalogFactory.NovoCatalogo(cfgProjeto.Projeto).CarregarVisaoGeral()
	if err != nil {
		return dominio.ComparacaoProjetoSegundaFase{}, err
	}
	resultadoWIT, err := s.avaliarCenarioBatchWIT(cfgProjeto, projeto, model, generationModelKey, overview, baselineAlinhada, resultadosBatch, filepath.Join(baseProjeto, "wit-context"))
	if err != nil {
		return dominio.ComparacaoProjetoSegundaFase{}, err
	}
	analiseDireta := construirRelatorioAnaliseDireta(cfgProjeto.Projeto.Raiz, generationModelKey, metodosAlvo)
	resultadoDireto, err := s.avaliarCenarioBatchDireto(cfgProjeto, projeto, model, generationModelKey, overview, analiseDireta, metodosAlvo, resultadosBatch, filepath.Join(baseProjeto, "direct-tests"))
	if err != nil {
		return dominio.ComparacaoProjetoSegundaFase{}, err
	}
	return dominio.ComparacaoProjetoSegundaFase{
		Projeto:              projeto.Chave,
		RotuloProjeto:        projeto.Rotulo,
		ContextoWIT:          resultadoWIT,
		GeracaoDireta:        resultadoDireto,
		DirecaoDelta:         "WIT_CONTEXT_MINUS_DIRECT_TESTS",
		DeltaNotaMetricas:    deltaPontuacoes(resultadoWIT.NotaMetricas, resultadoDireto.NotaMetricas),
		DeltaCoberturaLinha:  deltaPontuacoes(valorMetricaPorNome(resultadoWIT.ResultadosMetricas, "jacoco-line"), valorMetricaPorNome(resultadoDireto.ResultadosMetricas, "jacoco-line")),
		DeltaCoberturaBranch: deltaPontuacoes(valorMetricaPorNome(resultadoWIT.ResultadosMetricas, "jacoco-branch"), valorMetricaPorNome(resultadoDireto.ResultadosMetricas, "jacoco-branch")),
		DeltaMutacao:         deltaPontuacoes(valorMetricaPorNome(resultadoWIT.ResultadosMetricas, "pit-mutation"), valorMetricaPorNome(resultadoDireto.ResultadosMetricas, "pit-mutation")),
	}, nil
}

func (s *Servico) avaliarCenarioBatchWIT(
	cfg *dominio.ConfigAplicacao,
	projeto dominio.ConfigProjetoSegundaFase,
	model dominio.ConfigModelo,
	generationModelKey, overview string,
	analysisReport dominio.RelatorioAnalise,
	resultadosBatch map[string]llm.LinhaResultadoBatch,
	diretorioSaida string,
) (dominio.ResultadoCenarioSegundaFase, error) {
	workspace, err := artefatos.NovoEspacoTrabalho(diretorioSaida, artefatos.NovoIDExecucao("wit-context-batch"))
	if err != nil {
		return dominio.ResultadoCenarioSegundaFase{}, err
	}
	analysisPath := filepath.Join(workspace.Fontes, "wit-context.analysis.json")
	if err := artefatos.EscreverJSON(analysisPath, analysisReport); err != nil {
		return dominio.ResultadoCenarioSegundaFase{}, err
	}
	generationReport, generationPath, err := materializarGeracaoBatchWIT(cfg, model, generationModelKey, overview, projeto.Chave, analysisReport, analysisPath, resultadosBatch, workspace)
	if err != nil {
		return dominio.ResultadoCenarioSegundaFase{}, err
	}
	evaluationReport, evaluationPath, _, err := s.Avaliar(cfg, analysisReport, analysisPath, generationReport, generationPath, "", nil)
	if err != nil {
		return dominio.ResultadoCenarioSegundaFase{}, err
	}
	auditoria := auditoriaCenarioSegundaFase{}
	auditoria.acumularGeracao(generationReport)
	auditoria.acumularAvaliacao(evaluationReport)
	metodosDetalhados := detalharMetodosAlvoSegundaFase(analysisReport.Analises)
	arquivosDetalhados := detalharArquivosTesteSegundaFase(generationReport.ArquivosTeste)
	return dominio.ResultadoCenarioSegundaFase{
		Projeto:             projeto.Chave,
		RotuloProjeto:       projeto.Rotulo,
		Cenario:             dominio.CenarioSegundaFaseContextoWIT,
		CaminhoBaseline:     projeto.CaminhoBaseline,
		CaminhoAnalise:      analysisPath,
		CaminhoGeracao:      generationPath,
		CaminhoAvaliacao:    evaluationPath,
		QuantidadeMetodos:   analysisReport.TotalMetodos,
		QuantidadeExpaths:   contarCaminhosAnalises(analysisReport.Analises),
		QuantidadeTestes:    len(generationReport.ArquivosTeste),
		MetodosAlvo:         metodosDetalhados,
		ArquivosTeste:       arquivosDetalhados,
		ParesMetodoTeste:    construirParesMetodoTesteSegundaFase(metodosDetalhados, arquivosDetalhados),
		ResultadosMetricas:  evaluationReport.ResultadosMetricas,
		NotaMetricas:        evaluationReport.NotaMetricas,
		AuditoriaPontuacao:  evaluationReport.AuditoriaPontuacao,
		NotaCombinada:       evaluationReport.NotaCombinada,
		IntervencoesHarness: deduplicarStrings(append(append([]string{}, generationReport.IntervencoesHarness...), evaluationReport.IntervencoesHarness...)),
		ModoExecucao:        modoExecucaoSegundaFase(cfg),
		RequestCount:        auditoria.RequestCount,
		InputTokens:         auditoria.InputTokens,
		OutputTokens:        auditoria.OutputTokens,
		EstimatedCost:       auditoria.custoEstimado(),
	}, nil
}

func (s *Servico) avaliarCenarioBatchDireto(
	cfg *dominio.ConfigAplicacao,
	projeto dominio.ConfigProjetoSegundaFase,
	model dominio.ConfigModelo,
	generationModelKey, overview string,
	analysisReport dominio.RelatorioAnalise,
	metodos []dominio.DescritorMetodo,
	resultadosBatch map[string]llm.LinhaResultadoBatch,
	diretorioSaida string,
) (dominio.ResultadoCenarioSegundaFase, error) {
	workspace, err := artefatos.NovoEspacoTrabalho(diretorioSaida, artefatos.NovoIDExecucao("direct-tests-batch"))
	if err != nil {
		return dominio.ResultadoCenarioSegundaFase{}, err
	}
	analysisPath := filepath.Join(workspace.Fontes, "direct-tests.analysis.json")
	if err := artefatos.EscreverJSON(analysisPath, analysisReport); err != nil {
		return dominio.ResultadoCenarioSegundaFase{}, err
	}
	generationReport, generationPath, err := materializarGeracaoBatchDireta(cfg, model, generationModelKey, overview, projeto.Chave, metodos, analysisPath, resultadosBatch, workspace)
	if err != nil {
		return dominio.ResultadoCenarioSegundaFase{}, err
	}
	evaluationReport, evaluationPath, _, err := s.Avaliar(cfg, analysisReport, analysisPath, generationReport, generationPath, "", nil)
	if err != nil {
		return dominio.ResultadoCenarioSegundaFase{}, err
	}
	auditoria := auditoriaCenarioSegundaFase{}
	auditoria.acumularGeracao(generationReport)
	auditoria.acumularAvaliacao(evaluationReport)
	metodosDetalhados := detalharMetodosAlvoSegundaFase(analysisReport.Analises)
	arquivosDetalhados := detalharArquivosTesteSegundaFase(generationReport.ArquivosTeste)
	return dominio.ResultadoCenarioSegundaFase{
		Projeto:             projeto.Chave,
		RotuloProjeto:       projeto.Rotulo,
		Cenario:             dominio.CenarioSegundaFaseDireto,
		CaminhoBaseline:     projeto.CaminhoBaseline,
		CaminhoAnalise:      analysisPath,
		CaminhoGeracao:      generationPath,
		CaminhoAvaliacao:    evaluationPath,
		QuantidadeMetodos:   len(metodos),
		QuantidadeExpaths:   0,
		QuantidadeTestes:    len(generationReport.ArquivosTeste),
		MetodosAlvo:         metodosDetalhados,
		ArquivosTeste:       arquivosDetalhados,
		ParesMetodoTeste:    construirParesMetodoTesteSegundaFase(metodosDetalhados, arquivosDetalhados),
		ResultadosMetricas:  evaluationReport.ResultadosMetricas,
		NotaMetricas:        evaluationReport.NotaMetricas,
		AuditoriaPontuacao:  evaluationReport.AuditoriaPontuacao,
		NotaCombinada:       evaluationReport.NotaCombinada,
		IntervencoesHarness: deduplicarStrings(append(append([]string{}, generationReport.IntervencoesHarness...), evaluationReport.IntervencoesHarness...)),
		ModoExecucao:        modoExecucaoSegundaFase(cfg),
		RequestCount:        auditoria.RequestCount,
		InputTokens:         auditoria.InputTokens,
		OutputTokens:        auditoria.OutputTokens,
		EstimatedCost:       auditoria.custoEstimado(),
	}, nil
}

func materializarGeracaoBatchWIT(cfg *dominio.ConfigAplicacao, model dominio.ConfigModelo, generationModelKey, overview, projeto string, analysisReport dominio.RelatorioAnalise, analysisPath string, resultadosBatch map[string]llm.LinhaResultadoBatch, workspace *artefatos.EspacoTrabalho) (dominio.RelatorioGeracao, string, error) {
	grouped := agruparAnalisesPorContainer(filtrarAnalisesParte2(analysisReport))
	keys := ordenarChavesAnalises(grouped)
	return materializarGeracaoBatch(workspace, cfg.Projeto.Raiz, generationModelKey, analysisPath, keys, func(container string) int {
		return len(dividirAnalisesParaGeracao(grouped[container]))
	}, func(container string, indiceLote int) string {
		return fmt.Sprintf("%s/wit-context/%s/batch-%02d", artefatos.Slugificar(projeto), artefatos.Slugificar(container), indiceLote+1)
	}, func(container string, indiceLote int) (string, string) {
		lote := dividirAnalisesParaGeracao(grouped[container])[indiceLote]
		contextoComum := construirContextoGeracaoTestes(cfg.Projeto, container, extrairMetodosDasAnalises(lote))
		return construirPromptGeracaoUsuario(overview, container, lote, contextoComum), construirPromptGeracaoSistema(resolverFrameworkTestes(cfg.Projeto))
	}, resultadosBatch, model)
}

func materializarGeracaoBatchDireta(cfg *dominio.ConfigAplicacao, model dominio.ConfigModelo, generationModelKey, overview, projeto string, metodos []dominio.DescritorMetodo, analysisPath string, resultadosBatch map[string]llm.LinhaResultadoBatch, workspace *artefatos.EspacoTrabalho) (dominio.RelatorioGeracao, string, error) {
	grupos := agruparMetodosPorContainer(metodos)
	keys := ordenarChavesMetodos(grupos)
	return materializarGeracaoBatch(workspace, cfg.Projeto.Raiz, generationModelKey, analysisPath, keys, func(container string) int {
		return len(dividirMetodosDiretosParaGeracao(grupos[container]))
	}, func(container string, indiceLote int) string {
		return fmt.Sprintf("%s/direct-tests/%s/batch-%02d", artefatos.Slugificar(projeto), artefatos.Slugificar(container), indiceLote+1)
	}, func(container string, indiceLote int) (string, string) {
		lote := dividirMetodosDiretosParaGeracao(grupos[container])[indiceLote]
		contextoComum := construirContextoGeracaoTestes(cfg.Projeto, container, lote)
		return construirPromptGeracaoDiretaUsuario(overview, container, lote, contextoComum), construirPromptGeracaoDiretaSistema(resolverFrameworkTestes(cfg.Projeto))
	}, resultadosBatch, model)
}

func materializarGeracaoBatch(
	workspace *artefatos.EspacoTrabalho,
	raizProjeto string,
	generationModelKey string,
	analysisPath string,
	containers []string,
	totalLotes func(string) int,
	customID func(string, int) string,
	prompts func(string, int) (string, string),
	resultadosBatch map[string]llm.LinhaResultadoBatch,
	model dominio.ConfigModelo,
) (dominio.RelatorioGeracao, string, error) {
	strategyParts := []string{}
	allFiles := []dominio.ArquivoTesteGerado{}
	rawResponses := []map[string]interface{}{}
	harnessInterventions := []string{}
	totalRequests := 0
	totalInputTokens := 0
	totalOutputTokens := 0
	estimatedCost := acumuladorCustoLLM{}
	for indiceContainer, containerName := range containers {
		for indiceLote := 0; indiceLote < totalLotes(containerName); indiceLote++ {
			id := customID(containerName, indiceLote)
			linha, ok := resultadosBatch[id]
			totalRequests++
			if !ok {
				rawResponses = append(rawResponses, map[string]interface{}{
					"_batch": map[string]interface{}{"custom_id": id, "error": "missing_batch_response"},
				})
				harnessInterventions = append(harnessInterventions, "batch_response_missing:"+id)
				estimatedCost.adicionar(1, nil)
				continue
			}
			responseLLM, err := llm.ExtrairRespostaBatch(model, linha)
			if err != nil {
				rawResponses = append(rawResponses, map[string]interface{}{
					"_batch": map[string]interface{}{"custom_id": id, "error": err.Error(), "raw_error": linha.Error},
				})
				harnessInterventions = append(harnessInterventions, "batch_response_error:"+id)
				estimatedCost.adicionar(1, nil)
				continue
			}
			response := &RespostaComplecao{
				IDResposta:        responseLLM.IDResposta,
				Payload:           responseLLM.Payload,
				RawText:           responseLLM.RawText,
				InputTokens:       responseLLM.InputTokens,
				OutputTokens:      responseLLM.OutputTokens,
				CachedInputTokens: responseLLM.CachedInputTokens,
				EstimatedCost:     responseLLM.EstimatedCost,
			}
			summary, files := normalizarRespostaGeracao(response.Payload)
			files, intervencoes := adaptarArquivosTesteAoProjetoAuditado(raizProjeto, files)
			if strings.TrimSpace(summary) != "" {
				strategyParts = append(strategyParts, summary)
			}
			allFiles = append(allFiles, files...)
			harnessInterventions = append(harnessInterventions, intervencoes...)
			rawPayload := enriquecerPayloadRespostaLLM(response.Payload, response)
			rawPayload["_batch"] = map[string]interface{}{"custom_id": id, "status_code": linha.Response.StatusCode, "request_id": linha.Response.RequestID}
			rawResponses = append(rawResponses, rawPayload)
			totalInputTokens += response.InputTokens
			totalOutputTokens += response.OutputTokens
			estimatedCost.adicionar(1, response.EstimatedCost)
			userPrompt, _ := prompts(containerName, indiceLote)
			stem := fmt.Sprintf("batch-generation-%04d-%02d-%s", indiceContainer+1, indiceLote+1, artefatos.Slugificar(containerName))
			if err := artefatos.EscreverTexto(filepath.Join(workspace.Prompts, stem+".txt"), userPrompt); err != nil {
				return dominio.RelatorioGeracao{}, "", err
			}
			if err := artefatos.EscreverTexto(filepath.Join(workspace.Respostas, stem+".txt"), response.RawText); err != nil {
				return dominio.RelatorioGeracao{}, "", err
			}
		}
	}
	allFiles = consolidarArquivosGerados(allFiles)
	for _, file := range allFiles {
		rel, err := artefatos.CaminhoRelativoSeguro(file.CaminhoRelativo)
		if err != nil {
			return dominio.RelatorioGeracao{}, "", err
		}
		if err := artefatos.EscreverTexto(filepath.Join(workspace.Testes, rel), file.Conteudo); err != nil {
			return dominio.RelatorioGeracao{}, "", err
		}
	}
	report := dominio.RelatorioGeracao{
		IDExecucao:           filepath.Base(workspace.Raiz),
		CaminhoAnaliseOrigem: analysisPath,
		ChaveModelo:          generationModelKey,
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
		return dominio.RelatorioGeracao{}, "", err
	}
	registro.Info("phase-two", "batch generation materializada: artefato=%s arquivos=%d requests=%d", generationPath, len(report.ArquivosTeste), report.RequestCount)
	return report, generationPath, nil
}

func ordenarChavesAnalises(grupos map[string][]dominio.AnaliseMetodo) []string {
	keys := make([]string, 0, len(grupos))
	for key := range grupos {
		keys = append(keys, key)
	}
	sort.Strings(keys)
	return keys
}

func ordenarChavesMetodos(grupos map[string][]dominio.DescritorMetodo) []string {
	keys := make([]string, 0, len(grupos))
	for key := range grupos {
		keys = append(keys, key)
	}
	sort.Strings(keys)
	return keys
}

func copiarArquivo(origem, destino string) error {
	content, err := os.ReadFile(origem)
	if err != nil {
		return fmt.Errorf("ao ler arquivo %q: %w", origem, err)
	}
	if err := os.MkdirAll(filepath.Dir(destino), 0o755); err != nil {
		return fmt.Errorf("ao criar diretório de %q: %w", destino, err)
	}
	if err := os.WriteFile(destino, content, 0o644); err != nil {
		return fmt.Errorf("ao gravar arquivo %q: %w", destino, err)
	}
	return nil
}

package aplicacao

import (
	"encoding/json"
	"fmt"
	"os"
	"path/filepath"
	"sort"
	"strings"

	"github.com/marceloamorim/witup-llm/internal/artefatos"
	"github.com/marceloamorim/witup-llm/internal/dominio"
	"github.com/marceloamorim/witup-llm/internal/metricas"
	"github.com/marceloamorim/witup-llm/internal/registro"
	"github.com/marceloamorim/witup-llm/internal/visualizacao"
	"github.com/marceloamorim/witup-llm/internal/witup"
)

const maxTentativasReparoGeracaoSegundaFase = 1

type auditoriaCenarioSegundaFase struct {
	RequestCount      int
	RepairUsed        bool
	InputTokens       int
	OutputTokens      int
	estimatedCost     *float64
	custoIndisponivel bool
}

func (a *auditoriaCenarioSegundaFase) acumular(requestCount, inputTokens, outputTokens int, estimatedCost *float64) {
	if requestCount <= 0 && inputTokens <= 0 && outputTokens <= 0 && estimatedCost == nil {
		return
	}
	a.RequestCount += requestCount
	a.InputTokens += inputTokens
	a.OutputTokens += outputTokens
	if requestCount > 0 && estimatedCost == nil {
		a.custoIndisponivel = true
		a.estimatedCost = nil
		return
	}
	if a.custoIndisponivel || estimatedCost == nil {
		return
	}
	if a.estimatedCost == nil {
		total := *estimatedCost
		a.estimatedCost = &total
		return
	}
	total := *a.estimatedCost + *estimatedCost
	a.estimatedCost = &total
}

func (a *auditoriaCenarioSegundaFase) acumularGeracao(report dominio.RelatorioGeracao) {
	a.acumular(report.RequestCount, report.InputTokens, report.OutputTokens, report.EstimatedCost)
}

func (a *auditoriaCenarioSegundaFase) acumularAvaliacao(report dominio.RelatorioAvaliacao) {
	a.acumular(report.RequestCount, report.InputTokens, report.OutputTokens, report.EstimatedCost)
}

func (a auditoriaCenarioSegundaFase) custoEstimado() *float64 {
	if a.custoIndisponivel {
		return nil
	}
	return a.estimatedCost
}

// ExecutarSegundaFase executa a nova fase do estudo focada em dois cenários:
// geração com contexto WIT e geração direta sem o contexto WIT.
func (s *Servico) ExecutarSegundaFase(cfg *dominio.ConfigAplicacao, generationModelKey string) (dominio.RelatorioSegundaFase, string, error) {
	if len(cfg.SegundaFase.Projetos) == 0 {
		return dominio.RelatorioSegundaFase{}, "", fmt.Errorf("phase_two.projects precisa listar ao menos um projeto")
	}
	workspace, err := artefatos.NovoEspacoTrabalho(cfg.Fluxo.DiretorioSaida, artefatos.NovoIDExecucao("phase-two-"+generationModelKey))
	if err != nil {
		return dominio.RelatorioSegundaFase{}, "", err
	}

	comparacoes := make([]dominio.ComparacaoProjetoSegundaFase, 0, len(cfg.SegundaFase.Projetos))
	cacheCatalogo := map[string][]dominio.DescritorMetodo{}
	for _, projeto := range cfg.SegundaFase.Projetos {
		registro.Info("phase-two", "executando projeto=%s cenário=wit_context+direct_tests", projeto.Chave)
		comparacao, err := s.executarProjetoSegundaFase(cfg, projeto, generationModelKey, workspace, cacheCatalogo)
		if err != nil {
			return dominio.RelatorioSegundaFase{}, "", err
		}
		comparacoes = append(comparacoes, comparacao)
	}

	relatorio := dominio.RelatorioSegundaFase{
		IDExecucao:         filepath.Base(workspace.Raiz),
		GeradoEm:           dominio.HorarioUTC(),
		ChaveModeloGeracao: generationModelKey,
		ChaveModeloJuiz:    strings.TrimSpace(cfg.Fluxo.ModeloJuiz),
		Projetos:           comparacoes,
	}

	resumoCSV, metricasCSV, comparacaoCSV, err := exportarCSVsSegundaFase(workspace, relatorio)
	if err != nil {
		return dominio.RelatorioSegundaFase{}, "", err
	}
	relatorio.CaminhoCSVResumo = resumoCSV
	relatorio.CaminhoCSVMetricas = metricasCSV
	relatorio.CaminhoCSVComparacao = comparacaoCSV

	dashboard, err := visualizacao.NovoGeradorSegundaFase(cfg.SegundaFase.TituloVisualizacao).Gerar(relatorio, filepath.Join(workspace.Raiz, "dashboard.html"))
	if err != nil {
		return dominio.RelatorioSegundaFase{}, "", err
	}
	relatorio.CaminhoDashboard = dashboard

	caminhoRelatorio := filepath.Join(workspace.Raiz, "phase-two-study.json")
	if err := artefatos.EscreverJSON(caminhoRelatorio, relatorio); err != nil {
		return dominio.RelatorioSegundaFase{}, "", err
	}

	registro.Info("phase-two", "segunda fase concluída: relatório=%s dashboard=%s", caminhoRelatorio, dashboard)
	return relatorio, caminhoRelatorio, nil
}

func (s *Servico) executarProjetoSegundaFase(
	cfg *dominio.ConfigAplicacao,
	projeto dominio.ConfigProjetoSegundaFase,
	generationModelKey string,
	workspace *artefatos.EspacoTrabalho,
	cacheCatalogo map[string][]dominio.DescritorMetodo,
) (dominio.ComparacaoProjetoSegundaFase, error) {
	cfgProjeto := clonarConfiguracaoParaProjetoSegundaFase(cfg, projeto)
	metodosCatalogados, err := s.carregarCatalogoSegundaFaseComCache(cfgProjeto, cacheCatalogo)
	if err != nil {
		return dominio.ComparacaoProjetoSegundaFase{}, err
	}
	metodosCatalogados = filtrarMetodosPorContainers(metodosCatalogados, projeto.ContainersAlvo)
	if len(metodosCatalogados) == 0 {
		return dominio.ComparacaoProjetoSegundaFase{}, fmt.Errorf("nenhum método catalogado permaneceu após aplicar target_containers no projeto %s", projeto.Chave)
	}

	baselineReport, err := witup.CarregarAnalise(projeto.CaminhoBaseline)
	if err != nil {
		return dominio.ComparacaoProjetoSegundaFase{}, fmt.Errorf("ao carregar baseline WIT do projeto %s: %w", projeto.Chave, err)
	}
	baselineAlinhada, metodosAlvo, resumo := alinharWITUPAoCatalogo(baselineReport, metodosCatalogados, cfgProjeto.Fluxo.MaximoMetodos)
	if len(metodosAlvo) == 0 {
		return dominio.ComparacaoProjetoSegundaFase{}, fmt.Errorf("nenhum método WIT foi alinhado no projeto %s", projeto.Chave)
	}
	registro.Info("phase-two", "projeto=%s baseline=%d alinhados=%d não_encontrados=%d", projeto.Chave, resumo.QuantidadeBaseline, resumo.QuantidadeCorrespondidos, resumo.QuantidadeNaoEncontrados)

	baseProjeto := filepath.Join(workspace.Raiz, artefatos.Slugificar(projeto.Chave))
	if err := osMkdirAll(baseProjeto); err != nil {
		return dominio.ComparacaoProjetoSegundaFase{}, err
	}

	resultadoWIT, err := s.executarCenarioContextoWIT(cfgProjeto, projeto, generationModelKey, baselineAlinhada, filepath.Join(baseProjeto, "wit-context"))
	if err != nil {
		return dominio.ComparacaoProjetoSegundaFase{}, err
	}
	resultadoDireto, err := s.executarCenarioDireto(cfgProjeto, projeto, generationModelKey, metodosAlvo, filepath.Join(baseProjeto, "direct-tests"))
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

func (s *Servico) executarCenarioContextoWIT(
	cfg *dominio.ConfigAplicacao,
	projeto dominio.ConfigProjetoSegundaFase,
	generationModelKey string,
	analysisReport dominio.RelatorioAnalise,
	diretorioSaida string,
) (dominio.ResultadoCenarioSegundaFase, error) {
	cfgLocal := *cfg
	cfgLocal.Fluxo.DiretorioSaida = diretorioSaida
	cfgLocal.Metricas = filtrarMetricasSegundaFase(cfg.Metricas)
	judgeModelKey := strings.TrimSpace(cfgLocal.Fluxo.ModeloJuiz)

	workspace, err := artefatos.NovoEspacoTrabalho(cfgLocal.Fluxo.DiretorioSaida, artefatos.NovoIDExecucao("wit-context"))
	if err != nil {
		return dominio.ResultadoCenarioSegundaFase{}, err
	}
	analysisPath := filepath.Join(workspace.Fontes, "wit-context.analysis.json")
	if err := artefatos.EscreverJSON(analysisPath, analysisReport); err != nil {
		return dominio.ResultadoCenarioSegundaFase{}, err
	}
	generationReport, generationPath, _, err := s.Gerar(&cfgLocal, analysisReport, analysisPath, generationModelKey, workspace)
	if err != nil {
		return dominio.ResultadoCenarioSegundaFase{}, err
	}
	evaluationReport, evaluationPath, _, err := s.Avaliar(&cfgLocal, analysisReport, analysisPath, generationReport, generationPath, judgeModelKey, nil)
	if err != nil {
		return dominio.ResultadoCenarioSegundaFase{}, err
	}
	auditoria := auditoriaCenarioSegundaFase{}
	auditoria.acumularGeracao(generationReport)
	auditoria.acumularAvaliacao(evaluationReport)
	generationReport, generationPath, evaluationReport, evaluationPath, auditoriaReparo, err := s.tentarRepararSuiteSegundaFase(
		&cfgLocal,
		analysisReport,
		analysisPath,
		generationModelKey,
		judgeModelKey,
		"WIT_CONTEXT",
		generationReport,
		generationPath,
		evaluationReport,
		evaluationPath,
	)
	if err != nil {
		return dominio.ResultadoCenarioSegundaFase{}, err
	}
	auditoria.acumular(auditoriaReparo.RequestCount, auditoriaReparo.InputTokens, auditoriaReparo.OutputTokens, auditoriaReparo.custoEstimado())
	auditoria.RepairUsed = auditoriaReparo.RepairUsed
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
		ChaveModeloJuiz:     evaluationReport.ChaveModeloJuiz,
		AvaliacaoJuiz:       evaluationReport.AvaliacaoJuiz,
		NotaCombinada:       evaluationReport.NotaCombinada,
		IntervencoesHarness: deduplicarStrings(append(append([]string{}, generationReport.IntervencoesHarness...), evaluationReport.IntervencoesHarness...)),
		ModoExecucao:        modoExecucaoSegundaFase(&cfgLocal),
		RequestCount:        auditoria.RequestCount,
		RepairUsed:          auditoria.RepairUsed,
		InputTokens:         auditoria.InputTokens,
		OutputTokens:        auditoria.OutputTokens,
		EstimatedCost:       auditoria.custoEstimado(),
	}, nil
}

func (s *Servico) executarCenarioDireto(
	cfg *dominio.ConfigAplicacao,
	projeto dominio.ConfigProjetoSegundaFase,
	generationModelKey string,
	metodos []dominio.DescritorMetodo,
	diretorioSaida string,
) (dominio.ResultadoCenarioSegundaFase, error) {
	cfgLocal := *cfg
	cfgLocal.Fluxo.DiretorioSaida = diretorioSaida
	cfgLocal.Metricas = filtrarMetricasSegundaFase(cfg.Metricas)
	judgeModelKey := strings.TrimSpace(cfgLocal.Fluxo.ModeloJuiz)

	generationReport, generationPath, generationWorkspace, err := s.GerarDireto(&cfgLocal, metodos, generationModelKey, nil)
	if err != nil {
		return dominio.ResultadoCenarioSegundaFase{}, err
	}
	analiseDireta := construirRelatorioAnaliseDireta(cfgLocal.Projeto.Raiz, generationModelKey, metodos)
	analysisPath := filepath.Join(generationWorkspace.Raiz, "direct-tests.analysis.json")
	if err := artefatos.EscreverJSON(analysisPath, analiseDireta); err != nil {
		return dominio.ResultadoCenarioSegundaFase{}, err
	}
	evaluationReport, evaluationPath, _, err := s.Avaliar(&cfgLocal, analiseDireta, analysisPath, generationReport, generationPath, judgeModelKey, nil)
	if err != nil {
		return dominio.ResultadoCenarioSegundaFase{}, err
	}
	auditoria := auditoriaCenarioSegundaFase{}
	auditoria.acumularGeracao(generationReport)
	auditoria.acumularAvaliacao(evaluationReport)
	generationReport, generationPath, evaluationReport, evaluationPath, auditoriaReparo, err := s.tentarRepararSuiteSegundaFase(
		&cfgLocal,
		analiseDireta,
		analysisPath,
		generationModelKey,
		judgeModelKey,
		"DIRECT_TESTS",
		generationReport,
		generationPath,
		evaluationReport,
		evaluationPath,
	)
	if err != nil {
		return dominio.ResultadoCenarioSegundaFase{}, err
	}
	auditoria.acumular(auditoriaReparo.RequestCount, auditoriaReparo.InputTokens, auditoriaReparo.OutputTokens, auditoriaReparo.custoEstimado())
	auditoria.RepairUsed = auditoriaReparo.RepairUsed
	metodosDetalhados := detalharMetodosAlvoSegundaFase(analiseDireta.Analises)
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
		ChaveModeloJuiz:     evaluationReport.ChaveModeloJuiz,
		AvaliacaoJuiz:       evaluationReport.AvaliacaoJuiz,
		NotaCombinada:       evaluationReport.NotaCombinada,
		IntervencoesHarness: deduplicarStrings(append(append([]string{}, generationReport.IntervencoesHarness...), evaluationReport.IntervencoesHarness...)),
		ModoExecucao:        modoExecucaoSegundaFase(&cfgLocal),
		RequestCount:        auditoria.RequestCount,
		RepairUsed:          auditoria.RepairUsed,
		InputTokens:         auditoria.InputTokens,
		OutputTokens:        auditoria.OutputTokens,
		EstimatedCost:       auditoria.custoEstimado(),
	}, nil
}

func construirRelatorioAnaliseDireta(raizProjeto, generationModelKey string, metodos []dominio.DescritorMetodo) dominio.RelatorioAnalise {
	analises := make([]dominio.AnaliseMetodo, 0, len(metodos))
	for _, metodo := range metodos {
		analises = append(analises, dominio.AnaliseMetodo{
			Metodo:       metodo,
			ResumoMetodo: "Método-alvo para geração direta, sem contexto WIT.",
		})
	}
	return dominio.RelatorioAnalise{
		IDExecucao:   artefatos.NovoIDExecucao("direct-generation"),
		RaizProjeto:  raizProjeto,
		ChaveModelo:  generationModelKey,
		Origem:       dominio.OrigemExpathLLM,
		Estrategia:   "direct_test_generation_without_wit",
		GeradoEm:     dominio.HorarioUTC(),
		TotalMetodos: len(metodos),
		Analises:     analises,
	}
}

func detalharMetodosAlvoSegundaFase(analises []dominio.AnaliseMetodo) []dominio.MetodoAlvoDetalhadoSegundaFase {
	if len(analises) == 0 {
		return nil
	}
	detalhes := make([]dominio.MetodoAlvoDetalhadoSegundaFase, 0, len(analises))
	for _, analise := range analises {
		detalhes = append(detalhes, dominio.MetodoAlvoDetalhadoSegundaFase{
			IDMetodo:       analise.Metodo.IDMetodo,
			CaminhoArquivo: analise.Metodo.CaminhoArquivo,
			NomeContainer:  analise.Metodo.NomeContainer,
			NomeMetodo:     analise.Metodo.NomeMetodo,
			Assinatura:     analise.Metodo.Assinatura,
			Origem:         strings.TrimSpace(analise.Metodo.Origem),
		})
	}
	return detalhes
}

func detalharArquivosTesteSegundaFase(arquivos []dominio.ArquivoTesteGerado) []dominio.ArquivoTesteDetalhadoSegundaFase {
	if len(arquivos) == 0 {
		return nil
	}
	detalhes := make([]dominio.ArquivoTesteDetalhadoSegundaFase, 0, len(arquivos))
	for _, arquivo := range arquivos {
		detalhes = append(detalhes, dominio.ArquivoTesteDetalhadoSegundaFase{
			CaminhoRelativo:    arquivo.CaminhoRelativo,
			Conteudo:           strings.TrimSpace(arquivo.Conteudo),
			IDsMetodosCobertos: append([]string{}, arquivo.IDsMetodosCobertos...),
			Observacoes:        strings.TrimSpace(arquivo.Observacoes),
		})
	}
	return detalhes
}

func construirParesMetodoTesteSegundaFase(
	metodos []dominio.MetodoAlvoDetalhadoSegundaFase,
	arquivos []dominio.ArquivoTesteDetalhadoSegundaFase,
) []dominio.ParMetodoTesteSegundaFase {
	if len(metodos) == 0 || len(arquivos) == 0 {
		return nil
	}

	arquivosPorMetodo := make(map[string][]dominio.ArquivoTesteDetalhadoSegundaFase, len(metodos))
	for _, arquivo := range arquivos {
		for _, idMetodo := range arquivo.IDsMetodosCobertos {
			arquivosPorMetodo[idMetodo] = append(arquivosPorMetodo[idMetodo], arquivo)
		}
	}

	pares := make([]dominio.ParMetodoTesteSegundaFase, 0, len(metodos))
	for _, metodo := range metodos {
		pares = append(pares, dominio.ParMetodoTesteSegundaFase{
			Metodo: metodo,
			Testes: append([]dominio.ArquivoTesteDetalhadoSegundaFase{}, arquivosPorMetodo[metodo.IDMetodo]...),
		})
	}
	return pares
}

// GerarDireto cria testes diretamente a partir do código local dos métodos, sem expaths.
func (s *Servico) GerarDireto(cfg *dominio.ConfigAplicacao, methods []dominio.DescritorMetodo, modelKey string, workspace *artefatos.EspacoTrabalho) (dominio.RelatorioGeracao, string, *artefatos.EspacoTrabalho, error) {
	registro.Info("pipeline", "iniciando geração direta de testes com modelo=%s", modelKey)
	model, err := getModelOrError(cfg, modelKey)
	if err != nil {
		return dominio.RelatorioGeracao{}, "", workspace, err
	}
	overview, err := s.catalogFactory.NovoCatalogo(cfg.Projeto).CarregarVisaoGeral()
	if err != nil {
		return dominio.RelatorioGeracao{}, "", workspace, err
	}
	if workspace == nil {
		workspace, err = artefatos.NovoEspacoTrabalho(cfg.Fluxo.DiretorioSaida, artefatos.NovoIDExecucao("generate-direct-"+modelKey))
		if err != nil {
			return dominio.RelatorioGeracao{}, "", workspace, err
		}
	}
	if err := persistirCatalogo(workspace, methods); err != nil {
		return dominio.RelatorioGeracao{}, "", workspace, err
	}

	grupos := agruparMetodosPorContainer(methods)
	strategyParts := make([]string, 0, len(grupos))
	allFiles := make([]dominio.ArquivoTesteGerado, 0, len(grupos))
	rawResponses := make([]map[string]interface{}, 0, len(grupos))
	harnessInterventions := make([]string, 0)
	totalRequests := 0
	totalInputTokens := 0
	totalOutputTokens := 0
	estimatedCost := acumuladorCustoLLM{}
	keys := make([]string, 0, len(grupos))
	for key := range grupos {
		keys = append(keys, key)
	}
	sort.Strings(keys)

	for i, containerName := range keys {
		lotes := dividirMetodosDiretosParaGeracao(grupos[containerName])
		for indiceLote, methodsPayload := range lotes {
			systemPrompt := construirPromptGeracaoDiretaSistema(resolverFrameworkTestes(cfg.Projeto))
			contextoComum := construirContextoGeracaoTestes(cfg.Projeto, containerName, methodsPayload)
			userPrompt := construirPromptGeracaoDiretaUsuario(overview, containerName, methodsPayload, contextoComum)
			response, err := s.completionClient.CompletarJSON(model, systemPrompt, userPrompt, dominio.OpcoesRequisicaoLLM{
				PromptCacheKey: construirPromptCacheKey(identificarProjeto(cfg), "direct-generation", containerName),
			})
			if err != nil {
				return dominio.RelatorioGeracao{}, "", workspace, fmt.Errorf("a geração direta falhou para %s (lote %d/%d): %w", containerName, indiceLote+1, len(lotes), err)
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
				stem := fmt.Sprintf("direct-generation-%04d-%02d-%s", i+1, indiceLote+1, artefatos.Slugificar(containerName))
				if err := persistirPromptEResposta(workspace, stem, userPrompt, response.RawText); err != nil {
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
		CaminhoAnaliseOrigem: "",
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
		"geração direta concluída: arquivos=%d requests=%d input_tokens=%d output_tokens=%d custo=%s artefato=%s",
		len(report.ArquivosTeste),
		report.RequestCount,
		report.InputTokens,
		report.OutputTokens,
		formatarCustoLLM(report.EstimatedCost),
		generationPath,
	)
	return report, generationPath, workspace, nil
}

func clonarConfiguracaoParaProjetoSegundaFase(cfg *dominio.ConfigAplicacao, projeto dominio.ConfigProjetoSegundaFase) *dominio.ConfigAplicacao {
	copia := *cfg
	copia.Projeto = dominio.ConfigProjeto{
		Raiz:          projeto.Raiz,
		Include:       append([]string{}, projeto.Include...),
		Exclude:       append([]string{}, projeto.Exclude...),
		OverviewFile:  projeto.OverviewFile,
		TestFramework: projeto.TestFramework,
	}
	return &copia
}

func filtrarMetricasSegundaFase(metricasCfg []dominio.ConfigMetrica) []dominio.ConfigMetrica {
	filtradas := make([]dominio.ConfigMetrica, 0, len(metricasCfg))
	for _, item := range metricasCfg {
		if strings.EqualFold(item.Tipo, "exception_reproduction") || strings.EqualFold(item.Nome, "exception-reproduction") {
			continue
		}
		filtradas = append(filtradas, item)
	}
	return filtradas
}

func agruparMetodosPorContainer(metodos []dominio.DescritorMetodo) map[string][]dominio.DescritorMetodo {
	grupos := map[string][]dominio.DescritorMetodo{}
	for _, metodo := range metodos {
		grupos[metodo.NomeContainer] = append(grupos[metodo.NomeContainer], metodo)
	}
	return grupos
}

func dividirMetodosDiretosParaGeracao(metodos []dominio.DescritorMetodo) [][]dominio.DescritorMetodo {
	if len(metodos) == 0 {
		return nil
	}
	lotes := make([][]dominio.DescritorMetodo, 0, len(metodos))
	for inicio := 0; inicio < len(metodos); inicio += limiteMetodosPorLoteGeracao {
		fim := inicio + limiteMetodosPorLoteGeracao
		if fim > len(metodos) {
			fim = len(metodos)
		}
		lotes = append(lotes, metodos[inicio:fim])
	}
	return lotes
}

func valorMetricaPorNome(resultados []dominio.ResultadoMetrica, nome string) *float64 {
	for _, resultado := range resultados {
		if resultado.Nome == nome {
			if resultado.NotaNormalizada != nil {
				return resultado.NotaNormalizada
			}
			return resultado.ValorNumerico
		}
	}
	return nil
}

func (s *Servico) tentarRepararSuiteSegundaFase(
	cfg *dominio.ConfigAplicacao,
	analysisReport dominio.RelatorioAnalise,
	analysisPath string,
	generationModelKey string,
	judgeModelKey string,
	cenario string,
	generationReport dominio.RelatorioGeracao,
	generationPath string,
	evaluationReport dominio.RelatorioAvaliacao,
	evaluationPath string,
) (dominio.RelatorioGeracao, string, dominio.RelatorioAvaliacao, string, auditoriaCenarioSegundaFase, error) {
	if !permiteReparoSegundaFase(cfg) {
		registro.Info("phase-two", "cenário=%s sem reparo: modo de execução=%s", cenario, modoExecucaoSegundaFase(cfg))
		return generationReport, generationPath, evaluationReport, evaluationPath, auditoriaCenarioSegundaFase{}, nil
	}
	if !deveTentarReparoSuiteGerada(evaluationReport.ResultadosMetricas) {
		registro.Info("phase-two", "cenário=%s sem reparo: suíte inicial já é válida o suficiente", cenario)
		return generationReport, generationPath, evaluationReport, evaluationPath, auditoriaCenarioSegundaFase{}, nil
	}

	melhorGeracao := generationReport
	melhorGeracaoPath := generationPath
	melhorAvaliacao := evaluationReport
	melhorAvaliacaoPath := evaluationPath
	auditoria := auditoriaCenarioSegundaFase{}

	for tentativa := 1; tentativa <= maxTentativasReparoGeracaoSegundaFase; tentativa++ {
		registro.Info("phase-two", "cenário=%s iniciando reparo tentativa=%d/%d", cenario, tentativa, maxTentativasReparoGeracaoSegundaFase)
		auditoria.RepairUsed = true
		repairWorkspace, err := artefatos.NovoEspacoTrabalho(cfg.Fluxo.DiretorioSaida, artefatos.NovoIDExecucao("repair-"+strings.ToLower(cenario)+"-"+generationModelKey))
		if err != nil {
			return melhorGeracao, melhorGeracaoPath, melhorAvaliacao, melhorAvaliacaoPath, auditoria, err
		}

		repairedGeneration, repairedGenerationPath, _, err := s.RepararGeracao(
			cfg,
			analysisReport,
			analysisPath,
			generationModelKey,
			generationReport,
			evaluationReport,
			repairWorkspace,
		)
		if err != nil {
			registro.Info("phase-two", "cenário=%s reparo falhou na chamada LLM: %v", cenario, err)
			break
		}
		auditoria.acumularGeracao(repairedGeneration)
		repairedEvaluation, repairedEvaluationPath, _, err := s.Avaliar(
			cfg,
			analysisReport,
			analysisPath,
			repairedGeneration,
			repairedGenerationPath,
			judgeModelKey,
			repairWorkspace,
		)
		if err != nil {
			registro.Info("phase-two", "cenário=%s reparo falhou na avaliação: %v", cenario, err)
			break
		}
		auditoria.acumularAvaliacao(repairedEvaluation)
		if reparoSuperaResultadoAtual(melhorAvaliacao, repairedEvaluation) {
			registro.Info("phase-two", "cenário=%s reparo aceito: nota anterior=%s nota nova=%s", cenario, metricas.FormatarPontuacao(melhorAvaliacao.NotaCombinada), metricas.FormatarPontuacao(repairedEvaluation.NotaCombinada))
			melhorGeracao = repairedGeneration
			melhorGeracaoPath = repairedGenerationPath
			melhorAvaliacao = repairedEvaluation
			melhorAvaliacaoPath = repairedEvaluationPath
		} else {
			registro.Info("phase-two", "cenário=%s reparo descartado: suíte original permaneceu melhor", cenario)
		}
		break
	}

	return melhorGeracao, melhorGeracaoPath, melhorAvaliacao, melhorAvaliacaoPath, auditoria, nil
}

func (s *Servico) RepararGeracao(
	cfg *dominio.ConfigAplicacao,
	analysisReport dominio.RelatorioAnalise,
	analysisPath string,
	modelKey string,
	generationReport dominio.RelatorioGeracao,
	evaluationReport dominio.RelatorioAvaliacao,
	workspace *artefatos.EspacoTrabalho,
) (dominio.RelatorioGeracao, string, *artefatos.EspacoTrabalho, error) {
	model, err := getModelOrError(cfg, modelKey)
	if err != nil {
		return dominio.RelatorioGeracao{}, "", workspace, err
	}
	overview, err := s.catalogFactory.NovoCatalogo(cfg.Projeto).CarregarVisaoGeral()
	if err != nil {
		return dominio.RelatorioGeracao{}, "", workspace, err
	}
	if workspace == nil {
		workspace, err = artefatos.NovoEspacoTrabalho(cfg.Fluxo.DiretorioSaida, artefatos.NovoIDExecucao("repair-"+modelKey))
		if err != nil {
			return dominio.RelatorioGeracao{}, "", workspace, err
		}
	}

	systemPrompt := construirPromptReparoSistema(resolverFrameworkTestes(cfg.Projeto))
	userPrompt := construirPromptReparoUsuario(overview, analysisReport, generationReport, evaluationReport)
	response, err := s.completionClient.CompletarJSON(model, systemPrompt, userPrompt, dominio.OpcoesRequisicaoLLM{
		PromptCacheKey: construirPromptCacheKey(identificarProjeto(cfg), "repair-generation", generationReport.ChaveModelo),
	})
	if err != nil {
		return dominio.RelatorioGeracao{}, "", workspace, err
	}
	summary, files := normalizarRespostaGeracao(response.Payload)
	files, intervencoes := adaptarArquivosTesteAoProjetoAuditado(cfg.Projeto.Raiz, files)
	files = consolidarArquivosGerados(files)
	for _, file := range files {
		rel, err := artefatos.CaminhoRelativoSeguro(file.CaminhoRelativo)
		if err != nil {
			return dominio.RelatorioGeracao{}, "", workspace, err
		}
		if err := artefatos.EscreverTexto(filepath.Join(workspace.Testes, rel), file.Conteudo); err != nil {
			return dominio.RelatorioGeracao{}, "", workspace, err
		}
	}

	rawPayload := enriquecerPayloadRespostaLLM(response.Payload, response)
	rawPayload["repair_context"] = map[string]interface{}{
		"previous_generation_summary": generationReport.ResumoEstrategia,
		"failure_summary":             compactarFalhasMetricasParaReparo(evaluationReport.ResultadosMetricas),
	}

	report := dominio.RelatorioGeracao{
		IDExecucao:           filepath.Base(workspace.Raiz),
		CaminhoAnaliseOrigem: analysisPath,
		ChaveModelo:          modelKey,
		GeradoEm:             dominio.HorarioUTC(),
		ResumoEstrategia:     strings.TrimSpace(summary),
		ArquivosTeste:        files,
		RespostasBrutas:      []map[string]interface{}{rawPayload},
		IntervencoesHarness:  deduplicarStrings(intervencoes),
		RequestCount:         1,
		InputTokens:          response.InputTokens,
		OutputTokens:         response.OutputTokens,
		EstimatedCost:        response.EstimatedCost,
	}
	generationPath := filepath.Join(workspace.Raiz, "generation.json")
	if err := artefatos.EscreverJSON(generationPath, report); err != nil {
		return dominio.RelatorioGeracao{}, "", workspace, err
	}
	if cfg.Fluxo.SalvarPrompts {
		if err := persistirPromptEResposta(workspace, "repair-generation", userPrompt, response.RawText); err != nil {
			return dominio.RelatorioGeracao{}, "", workspace, err
		}
	}
	if err := artefatos.EscreverTexto(filepath.Join(workspace.Logs, "repair-summary.json"), stringifyJSON(rawPayload)); err != nil {
		return dominio.RelatorioGeracao{}, "", workspace, err
	}
	registro.Info(
		"pipeline",
		"reparo de geração concluído: arquivos=%d requests=%d input_tokens=%d output_tokens=%d custo=%s artefato=%s",
		len(report.ArquivosTeste),
		report.RequestCount,
		report.InputTokens,
		report.OutputTokens,
		formatarCustoLLM(report.EstimatedCost),
		generationPath,
	)
	return report, generationPath, workspace, nil
}

func deveTentarReparoSuiteGerada(resultados []dominio.ResultadoMetrica) bool {
	for _, resultado := range resultados {
		switch resultado.Nome {
		case "test-compilation", "unit-tests", "test-pass-rate":
			if !resultado.Sucesso {
				return true
			}
		}
	}
	return false
}

func reparoSuperaResultadoAtual(atual, reparado dominio.RelatorioAvaliacao) bool {
	atualValido := suitePassa(atual.ResultadosMetricas)
	reparadoValido := suitePassa(reparado.ResultadosMetricas)
	if reparadoValido && !atualValido {
		return true
	}
	if atualValido && !reparadoValido {
		return false
	}
	atualNota := valorPontuacaoAvaliacao(atual)
	reparadaNota := valorPontuacaoAvaliacao(reparado)
	return reparadaNota > atualNota
}

func suitePassa(resultados []dominio.ResultadoMetrica) bool {
	compila := false
	testes := false
	for _, resultado := range resultados {
		switch resultado.Nome {
		case "test-compilation":
			compila = resultado.Sucesso
		case "unit-tests", "test-pass-rate":
			testes = testes || resultado.Sucesso
		}
	}
	return compila && testes
}

func valorPontuacaoAvaliacao(report dominio.RelatorioAvaliacao) float64 {
	if report.NotaCombinada != nil {
		return *report.NotaCombinada
	}
	if report.NotaMetricas != nil {
		return *report.NotaMetricas
	}
	return -1
}

func stringifyJSON(payload map[string]interface{}) string {
	dados, err := json.MarshalIndent(payload, "", "  ")
	if err != nil {
		return "{}"
	}
	return string(dados)
}

func modoExecucaoSegundaFase(cfg *dominio.ConfigAplicacao) string {
	if cfg == nil {
		return dominio.ModoExecucaoSegundaFaseReparo
	}
	modo := strings.TrimSpace(cfg.SegundaFase.ModoExecucao)
	if modo == "" {
		return dominio.ModoExecucaoSegundaFaseReparo
	}
	return modo
}

func permiteReparoSegundaFase(cfg *dominio.ConfigAplicacao) bool {
	return modoExecucaoSegundaFase(cfg) == dominio.ModoExecucaoSegundaFaseReparo
}

func osMkdirAll(path string) error {
	return os.MkdirAll(path, 0o755)
}

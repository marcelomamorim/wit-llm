package aplicacao

import (
	"fmt"
	"path/filepath"
	"sort"
	"strings"

	"github.com/marceloamorim/witup-llm/internal/armazenamento"
	"github.com/marceloamorim/witup-llm/internal/artefatos"
	"github.com/marceloamorim/witup-llm/internal/dominio"
	"github.com/marceloamorim/witup-llm/internal/experimento"
	"github.com/marceloamorim/witup-llm/internal/llm"
	"github.com/marceloamorim/witup-llm/internal/metricas"
	"github.com/marceloamorim/witup-llm/internal/registro"
)

// Servico orquestra os fluxos de análise, geração, avaliação e benchmark.
type Servico struct {
	completionClient ClienteComplecao
	metricRunner     ExecutorMetricas
	catalogFactory   FabricaCatalogo
}

// ResultadoExecucao reúne os caminhos dos artefatos e os relatórios desserializados
// produzidos pelo comando run.
type ResultadoExecucao struct {
	EspacoTrabalho     string
	CaminhoAnalise     string
	CaminhoGeracao     string
	CaminhoAvaliacao   string
	RelatorioAnalise   dominio.RelatorioAnalise
	RelatorioGeracao   dominio.RelatorioGeracao
	RelatorioAvaliacao dominio.RelatorioAvaliacao
}

// ResultadoExecucaoExperimento reúne os artefatos principais do experimento de
// comparação entre fontes.
type ResultadoExecucaoExperimento struct {
	EspacoTrabalho       string
	DiretorioHistorico   string
	CaminhoAnaliseWITUP  string
	CaminhoAnaliseLLM    string
	CaminhoComparacao    string
	CaminhoRastreio      string
	ArtefatosVariantes   []dominio.ArtefatoVariante
	RelatorioComparacao  dominio.RelatorioComparacaoFontes
	RelatorioExperimento dominio.RelatorioExperimento
}

// ResultadoExecucaoEstudoCompleto reúne a Parte 1 e a Parte 2 do estudo em uma
// única execução consolidada.
type ResultadoExecucaoEstudoCompleto struct {
	EspacoTrabalho           string
	CaminhoExperimento       string
	CaminhoEstudoCompleto    string
	DiretorioGraficos        string
	DiretorioHistorico       string
	RelatorioExperimento     dominio.RelatorioExperimento
	RelatorioComparacao      dominio.RelatorioComparacaoFontes
	RelatorioEstudoCompleto  dominio.RelatorioEstudoCompleto
	ResultadosVariantes      []dominio.ResultadoVarianteEstudoCompleto
	CaminhoComparacao        string
	CaminhoRelatorioRastreio string
}

// NovoServico conecta os adaptadores padrão de infraestrutura.
func NovoServico(llmClient *llm.Cliente, metricRunner *metricas.Executor) *Servico {
	return NovoServicoComDependencias(
		NovoClienteComplecao(llmClient),
		NovoExecutorMetricas(metricRunner),
		fabricaCatalogoPadrao{},
	)
}

// NovoServicoComDependencias preserva a orquestração testável e agnóstica a adaptadores.
func NovoServicoComDependencias(
	completionClient ClienteComplecao,
	metricRunner ExecutorMetricas,
	catalogFactory FabricaCatalogo,
) *Servico {
	if completionClient == nil {
		completionClient = NovoClienteComplecao(nil)
	}
	if metricRunner == nil {
		metricRunner = NovoExecutorMetricas(nil)
	}
	if catalogFactory == nil {
		catalogFactory = fabricaCatalogoPadrao{}
	}
	return &Servico{
		completionClient: completionClient,
		metricRunner:     metricRunner,
		catalogFactory:   catalogFactory,
	}
}

// Analisar descobre métodos do projeto e pede ao modelo configurado os caminhos de exceção.
func (s *Servico) Analisar(cfg *dominio.ConfigAplicacao, modelKey string, workspace *artefatos.EspacoTrabalho) (dominio.RelatorioAnalise, string, *artefatos.EspacoTrabalho, error) {
	registro.Info("pipeline", "iniciando análise direta do projeto com modelo=%s", modelKey)
	metodos, visaoGeral, err := carregarCatalogoProjeto(
		s.catalogFactory.NovoCatalogo(cfg.Projeto),
		cfg.Fluxo.MaximoMetodos,
	)
	if err != nil {
		return dominio.RelatorioAnalise{}, "", workspace, err
	}
	workspace, err = prepararEspacoTrabalho(workspace, cfg.Fluxo.DiretorioSaida, "analyze-"+modelKey)
	if err != nil {
		return dominio.RelatorioAnalise{}, "", workspace, err
	}
	if err := persistirCatalogo(workspace, metodos); err != nil {
		return dominio.RelatorioAnalise{}, "", workspace, err
	}
	report, err := s.analisarMetodosDireto(cfg, modelKey, visaoGeral, metodos, workspace, "analysis")
	if err != nil {
		return dominio.RelatorioAnalise{}, "", workspace, err
	}
	analysisPath := filepath.Join(workspace.Raiz, "analysis.json")
	if err := artefatos.EscreverJSON(analysisPath, report); err != nil {
		return dominio.RelatorioAnalise{}, "", workspace, err
	}
	if err := registrarArtefatoNoBanco(cfg, report.IDExecucao, "analise_llm", "", "", analysisPath, report.GeradoEm, report); err != nil {
		return dominio.RelatorioAnalise{}, "", workspace, err
	}
	registro.Info("pipeline", "análise direta concluída: métodos=%d artefato=%s", report.TotalMetodos, analysisPath)
	return report, analysisPath, workspace, nil
}

// AnalisarMultiagentes executa a branch LLM_ONLY usando um fluxo multiagente fixo e
// persiste tanto a análise final quanto o relatório de traces por agente.
func (s *Servico) AnalisarMultiagentes(cfg *dominio.ConfigAplicacao, modelKey string, workspace *artefatos.EspacoTrabalho) (dominio.RelatorioAnalise, string, dominio.RelatorioRastreioAgente, string, *artefatos.EspacoTrabalho, error) {
	registro.Info("pipeline", "iniciando análise multiagente com modelo=%s", modelKey)
	metodos, visaoGeral, err := carregarCatalogoProjeto(
		s.catalogFactory.NovoCatalogo(cfg.Projeto),
		cfg.Fluxo.MaximoMetodos,
	)
	if err != nil {
		return dominio.RelatorioAnalise{}, "", dominio.RelatorioRastreioAgente{}, "", workspace, err
	}
	workspace, err = prepararEspacoTrabalho(workspace, cfg.Fluxo.DiretorioSaida, "analyze-agentic-"+modelKey)
	if err != nil {
		return dominio.RelatorioAnalise{}, "", dominio.RelatorioRastreioAgente{}, "", workspace, err
	}
	if err := persistirCatalogo(workspace, metodos); err != nil {
		return dominio.RelatorioAnalise{}, "", dominio.RelatorioRastreioAgente{}, "", workspace, err
	}
	report, traceReport, err := s.analisarMetodosMultiagente(cfg, modelKey, visaoGeral, metodos, nil, workspace)
	if err != nil {
		return dominio.RelatorioAnalise{}, "", dominio.RelatorioRastreioAgente{}, "", workspace, err
	}
	report.RaizProjeto = cfg.Projeto.Raiz

	analysisPath := filepath.Join(workspace.Fontes, "llm-analysis.json")
	if err := artefatos.EscreverJSON(analysisPath, report); err != nil {
		return dominio.RelatorioAnalise{}, "", dominio.RelatorioRastreioAgente{}, "", workspace, err
	}
	tracePath := filepath.Join(workspace.Rastreios, "agent-trace-report.json")
	if err := artefatos.EscreverJSON(tracePath, traceReport); err != nil {
		return dominio.RelatorioAnalise{}, "", dominio.RelatorioRastreioAgente{}, "", workspace, err
	}
	if err := registrarArtefatoNoBanco(cfg, report.IDExecucao, "analise_llm_multiagente", "", string(dominio.VarianteLLMApenas), analysisPath, report.GeradoEm, report); err != nil {
		return dominio.RelatorioAnalise{}, "", dominio.RelatorioRastreioAgente{}, "", workspace, err
	}
	if err := registrarArtefatoNoBanco(cfg, report.IDExecucao, "rastreio_agentes", "", string(dominio.VarianteLLMApenas), tracePath, traceReport.GeradoEm, traceReport); err != nil {
		return dominio.RelatorioAnalise{}, "", dominio.RelatorioRastreioAgente{}, "", workspace, err
	}
	registro.Info("pipeline", "análise multiagente concluída: métodos=%d análise=%s rastreio=%s", report.TotalMetodos, analysisPath, tracePath)
	return report, analysisPath, traceReport, tracePath, workspace, nil
}

// SincronizarBaselinesWITUP carrega para o DuckDB os arquivos originais do artigo.
func (s *Servico) SincronizarBaselinesWITUP(cfg *dominio.ConfigAplicacao) (armazenamento.ResumoSincronizacao, error) {
	registro.Info("pipeline", "sincronizando baselines WITUP para o DuckDB em %s", cfg.Fluxo.CaminhoDuckDB)
	banco, err := abrirBancoAnalitico(cfg)
	if err != nil {
		return armazenamento.ResumoSincronizacao{}, err
	}
	defer banco.Fechar()
	resumo, err := banco.SincronizarBaselines(cfg.Fluxo.RaizReplicacaoWIT, cfg.Fluxo.ArquivoBaselineWIT)
	if err == nil {
		registro.Info("pipeline", "sincronização concluída: encontrados=%d importados=%d atualizados=%d", resumo.ProjetosEncontrados, resumo.ProjetosImportados, resumo.ProjetosAtualizados)
	}
	return resumo, err
}

// IngerirWITUP lê do DuckDB uma baseline já carregada e a materializa como
// análise canônica no workspace da execução.
func (s *Servico) IngerirWITUP(cfg *dominio.ConfigAplicacao, chaveProjeto string, workspace *artefatos.EspacoTrabalho) (dominio.RelatorioAnalise, string, *artefatos.EspacoTrabalho, error) {
	registro.Info("pipeline", "carregando baseline WITUP do projeto=%s arquivo=%s", chaveProjeto, cfg.Fluxo.ArquivoBaselineWIT)
	banco, err := abrirBancoAnalitico(cfg)
	if err != nil {
		return dominio.RelatorioAnalise{}, "", workspace, err
	}
	defer banco.Fechar()

	report, _, err := banco.CarregarRelatorioBaseline(chaveProjeto, cfg.Fluxo.ArquivoBaselineWIT)
	if err != nil {
		if _, syncErr := banco.SincronizarBaselines(cfg.Fluxo.RaizReplicacaoWIT, cfg.Fluxo.ArquivoBaselineWIT); syncErr != nil {
			return dominio.RelatorioAnalise{}, "", workspace, syncErr
		}
		report, _, err = banco.CarregarRelatorioBaseline(chaveProjeto, cfg.Fluxo.ArquivoBaselineWIT)
		if err != nil {
			return dominio.RelatorioAnalise{}, "", workspace, err
		}
	}
	workspace, err = prepararEspacoTrabalho(workspace, cfg.Fluxo.DiretorioSaida, "ingest-witup-"+chaveProjeto)
	if err != nil {
		return dominio.RelatorioAnalise{}, "", workspace, err
	}
	analysisPath := filepath.Join(workspace.Fontes, "witup-analysis.json")
	if err := artefatos.EscreverJSON(analysisPath, report); err != nil {
		return dominio.RelatorioAnalise{}, "", workspace, err
	}
	if err := registrarArtefatoNoBanco(cfg, filepath.Base(workspace.Raiz), "analise_witup", chaveProjeto, string(dominio.VarianteWITUPApenas), analysisPath, report.GeradoEm, report); err != nil {
		return dominio.RelatorioAnalise{}, "", workspace, err
	}
	registro.Info("pipeline", "baseline WITUP materializada: métodos=%d artefato=%s", report.TotalMetodos, analysisPath)
	return report, analysisPath, workspace, nil
}

// ExecutarExperimento executa o experimento de três ramos:
// WITUP_ONLY, LLM_ONLY e WITUP_PLUS_LLM.
func (s *Servico) ExecutarExperimento(cfg *dominio.ConfigAplicacao, chaveProjeto, analysisModelKey string) (ResultadoExecucaoExperimento, error) {
	registro.Info("pipeline", "iniciando experimento: projeto=%s modelo_llm=%s", chaveProjeto, analysisModelKey)
	workspace, err := artefatos.NovoEspacoTrabalho(cfg.Fluxo.DiretorioSaida, artefatos.NovoIDExecucao("experiment-"+analysisModelKey))
	if err != nil {
		return ResultadoExecucaoExperimento{}, err
	}
	witupReport, witupPath, _, err := s.IngerirWITUP(cfg, chaveProjeto, workspace)
	if err != nil {
		return ResultadoExecucaoExperimento{}, err
	}
	catalogo := s.catalogFactory.NovoCatalogo(cfg.Projeto)
	todosMetodos, visaoGeral, err := carregarCatalogoProjeto(catalogo, 0)
	if err != nil {
		return ResultadoExecucaoExperimento{}, err
	}
	witupReport, metodosAlvo, resumoAlvos := alinharWITUPAoCatalogo(witupReport, todosMetodos, cfg.Fluxo.MaximoMetodos)
	if len(metodosAlvo) == 0 {
		return ResultadoExecucaoExperimento{}, fmt.Errorf("nenhum método do WITUP foi resolvido no checkout atual do projeto")
	}
	if err := persistirCatalogo(workspace, metodosAlvo); err != nil {
		return ResultadoExecucaoExperimento{}, err
	}
	if err := artefatos.EscreverJSON(witupPath, witupReport); err != nil {
		return ResultadoExecucaoExperimento{}, err
	}
	if err := registrarArtefatoNoBanco(cfg, filepath.Base(workspace.Raiz), "analise_witup", chaveProjeto, string(dominio.VarianteWITUPApenas), witupPath, witupReport.GeradoEm, witupReport); err != nil {
		return ResultadoExecucaoExperimento{}, err
	}
	registro.Info(
		"pipeline",
		"alvos WITUP resolvidos no checkout: baseline=%d correspondidos=%d não_encontrados=%d",
		resumoAlvos.QuantidadeBaseline,
		resumoAlvos.QuantidadeCorrespondidos,
		resumoAlvos.QuantidadeNaoEncontrados,
	)

	llmReport, llmPath, _, tracePath, err := s.executarBranchLLMExperimento(cfg, analysisModelKey, witupReport, metodosAlvo, visaoGeral, workspace)
	if err != nil {
		return ResultadoExecucaoExperimento{}, err
	}

	comparison := experimento.ConstruirRelatorioComparacao(witupPath, witupReport, llmPath, llmReport)
	comparisonPath := filepath.Join(workspace.Comparacoes, "source-comparison.json")
	if err := artefatos.EscreverJSON(comparisonPath, comparison); err != nil {
		return ResultadoExecucaoExperimento{}, err
	}
	if err := registrarArtefatoNoBanco(cfg, filepath.Base(workspace.Raiz), "comparacao_fontes", chaveProjeto, "", comparisonPath, comparison.GeradoEm, comparison); err != nil {
		return ResultadoExecucaoExperimento{}, err
	}

	variants := experimento.ConstruirVariantes(witupReport, llmReport)
	variantArtifacts, err := experimento.EscreverArtefatosVariantes(workspace, variants)
	if err != nil {
		return ResultadoExecucaoExperimento{}, err
	}
	for _, artefatoVariante := range variantArtifacts {
		relatorioVariante := variants[artefatoVariante.Variante]
		if err := registrarArtefatoNoBanco(
			cfg,
			filepath.Base(workspace.Raiz),
			"variante",
			chaveProjeto,
			string(artefatoVariante.Variante),
			artefatoVariante.CaminhoAnalise,
			relatorioVariante.GeradoEm,
			relatorioVariante,
		); err != nil {
			return ResultadoExecucaoExperimento{}, err
		}
	}

	report := dominio.RelatorioExperimento{
		IDExecucao:                     filepath.Base(workspace.Raiz),
		GeradoEm:                       dominio.HorarioUTC(),
		CaminhoAnaliseWITUP:            witupPath,
		CaminhoAnaliseLLM:              llmPath,
		CaminhoComparacao:              comparisonPath,
		ArtefatosVariantes:             variantArtifacts,
		ResumoComparacao:               comparison.Resumo,
		CaminhoRelatorioRastreioAgente: tracePath,
	}
	reportPath := filepath.Join(workspace.Raiz, "experimento.json")
	if err := artefatos.EscreverJSON(reportPath, report); err != nil {
		return ResultadoExecucaoExperimento{}, err
	}
	if err := registrarArtefatoNoBanco(cfg, report.IDExecucao, "experimento", chaveProjeto, "", reportPath, report.GeradoEm, report); err != nil {
		return ResultadoExecucaoExperimento{}, err
	}
	resumoHistorico, err := exportarHistoricoParquet(cfg, report.IDExecucao, chaveProjeto)
	if err != nil {
		return ResultadoExecucaoExperimento{}, err
	}
	registro.Info("pipeline", "experimento concluído: comparação=%s variantes=%d raiz=%s", comparisonPath, len(variantArtifacts), workspace.Raiz)

	return ResultadoExecucaoExperimento{
		EspacoTrabalho:       workspace.Raiz,
		DiretorioHistorico:   resumoHistorico.Diretorio,
		CaminhoAnaliseWITUP:  witupPath,
		CaminhoAnaliseLLM:    llmPath,
		CaminhoComparacao:    comparisonPath,
		CaminhoRastreio:      tracePath,
		ArtefatosVariantes:   variantArtifacts,
		RelatorioComparacao:  comparison,
		RelatorioExperimento: report,
	}, nil
}

// ExecutarEstudoCompleto roda a Parte 1 e a Parte 2 do estudo no mesmo fluxo:
// compara expaths, gera testes por variante, avalia as suítes e consolida o
// resultado no DuckDB.
func (s *Servico) ExecutarEstudoCompleto(
	cfg *dominio.ConfigAplicacao,
	chaveProjeto string,
	chaveModeloAnalise string,
	chaveModeloGeracao string,
	chaveModeloJuiz string,
) (ResultadoExecucaoEstudoCompleto, error) {
	registro.Info(
		"pipeline",
		"iniciando estudo completo: projeto=%s analise=%s geracao=%s juiz=%s",
		chaveProjeto,
		chaveModeloAnalise,
		chaveModeloGeracao,
		chaveModeloJuiz,
	)

	resultadoExperimento, err := s.ExecutarExperimento(cfg, chaveProjeto, chaveModeloAnalise)
	if err != nil {
		return ResultadoExecucaoEstudoCompleto{}, err
	}

	resultadosVariantes := make([]dominio.ResultadoVarianteEstudoCompleto, 0, len(resultadoExperimento.ArtefatosVariantes))
	for _, artefatoVariante := range resultadoExperimento.ArtefatosVariantes {
		registro.Info(
			"pipeline",
			"processando variante %s: análise=%s",
			artefatoVariante.Variante,
			artefatoVariante.CaminhoAnalise,
		)

		relatorioAnalise, err := CarregarRelatorioAnalise(artefatoVariante.CaminhoAnalise)
		if err != nil {
			return ResultadoExecucaoEstudoCompleto{}, err
		}

		espacoVariante, err := artefatos.NovoEspacoTrabalho(
			filepath.Join(resultadoExperimento.EspacoTrabalho, "parte-2"),
			artefatos.Slugificar(string(artefatoVariante.Variante))+"-"+artefatos.Slugificar(chaveModeloGeracao),
		)
		if err != nil {
			return ResultadoExecucaoEstudoCompleto{}, err
		}

		relatorioGeracao, caminhoGeracao, _, err := s.Gerar(
			cfg,
			relatorioAnalise,
			artefatoVariante.CaminhoAnalise,
			chaveModeloGeracao,
			espacoVariante,
		)
		if err != nil {
			return ResultadoExecucaoEstudoCompleto{}, err
		}
		if err := registrarArtefatoNoBanco(
			cfg,
			relatorioGeracao.IDExecucao,
			"geracao_testes_variante",
			chaveProjeto,
			string(artefatoVariante.Variante),
			caminhoGeracao,
			relatorioGeracao.GeradoEm,
			relatorioGeracao,
		); err != nil {
			return ResultadoExecucaoEstudoCompleto{}, err
		}

		relatorioAvaliacao, caminhoAvaliacao, _, err := s.Avaliar(
			cfg,
			relatorioAnalise,
			artefatoVariante.CaminhoAnalise,
			relatorioGeracao,
			caminhoGeracao,
			chaveModeloJuiz,
			espacoVariante,
		)
		if err != nil {
			return ResultadoExecucaoEstudoCompleto{}, err
		}
		if err := registrarArtefatoNoBanco(
			cfg,
			relatorioAvaliacao.IDExecucao,
			"avaliacao_variante",
			chaveProjeto,
			string(artefatoVariante.Variante),
			caminhoAvaliacao,
			relatorioAvaliacao.GeradoEm,
			relatorioAvaliacao,
		); err != nil {
			return ResultadoExecucaoEstudoCompleto{}, err
		}

		var notaJuiz *float64
		var vereditoJuiz string
		if relatorioAvaliacao.AvaliacaoJuiz != nil {
			notaJuiz = &relatorioAvaliacao.AvaliacaoJuiz.Nota
			vereditoJuiz = relatorioAvaliacao.AvaliacaoJuiz.Veredito
		}

		resultadosVariantes = append(resultadosVariantes, dominio.ResultadoVarianteEstudoCompleto{
			Variante:                artefatoVariante.Variante,
			CaminhoAnalise:          artefatoVariante.CaminhoAnalise,
			QuantidadeMetodos:       artefatoVariante.QuantidadeMetodos,
			QuantidadeExpaths:       artefatoVariante.QuantidadeExpaths,
			CaminhoGeracao:          caminhoGeracao,
			QuantidadeArquivosTeste: len(relatorioGeracao.ArquivosTeste),
			CaminhoAvaliacao:        caminhoAvaliacao,
			ResultadosMetricas:      relatorioAvaliacao.ResultadosMetricas,
			NotaMetricas:            relatorioAvaliacao.NotaMetricas,
			NotaJuiz:                notaJuiz,
			VereditoJuiz:            vereditoJuiz,
			NotaCombinada:           relatorioAvaliacao.NotaCombinada,
			MetricasDerivadas:       calcularMetricasVarianteEstudo(artefatoVariante.QuantidadeMetodos, artefatoVariante.QuantidadeExpaths, len(relatorioGeracao.ArquivosTeste), relatorioAvaliacao.ResultadosMetricas),
		})
	}

	relatorioEstudo := dominio.RelatorioEstudoCompleto{
		IDExecucao:          resultadoExperimento.RelatorioExperimento.IDExecucao,
		GeradoEm:            dominio.HorarioUTC(),
		ChaveProjeto:        chaveProjeto,
		ChaveModeloAnalise:  chaveModeloAnalise,
		ChaveModeloGeracao:  chaveModeloGeracao,
		ChaveModeloJuiz:     chaveModeloJuiz,
		CaminhoExperimento:  filepath.Join(resultadoExperimento.EspacoTrabalho, "experimento.json"),
		CaminhoComparacao:   resultadoExperimento.CaminhoComparacao,
		ResultadosVariantes: resultadosVariantes,
		ComparacaoSuites:    calcularComparacaoSuites(resultadosVariantes),
	}
	caminhoEstudo := filepath.Join(resultadoExperimento.EspacoTrabalho, "estudo-completo.json")
	if err := artefatos.EscreverJSON(caminhoEstudo, relatorioEstudo); err != nil {
		return ResultadoExecucaoEstudoCompleto{}, err
	}
	if err := registrarArtefatoNoBanco(
		cfg,
		relatorioEstudo.IDExecucao,
		"estudo_completo",
		chaveProjeto,
		"",
		caminhoEstudo,
		relatorioEstudo.GeradoEm,
		relatorioEstudo,
	); err != nil {
		return ResultadoExecucaoEstudoCompleto{}, err
	}

	diretorioGraficos := filepath.Join(resultadoExperimento.EspacoTrabalho, "plots")
	bancoAnalitico, err := abrirBancoAnalitico(cfg)
	if err != nil {
		return ResultadoExecucaoEstudoCompleto{}, err
	}
	resumoGraficos, err := bancoAnalitico.GerarGraficosExecucao(relatorioEstudo.IDExecucao, diretorioGraficos)
	_ = bancoAnalitico.Fechar()
	if err != nil {
		return ResultadoExecucaoEstudoCompleto{}, err
	}
	resumoHistorico, err := exportarHistoricoParquet(cfg, relatorioEstudo.IDExecucao, chaveProjeto)
	if err != nil {
		return ResultadoExecucaoEstudoCompleto{}, err
	}

	registro.Info(
		"pipeline",
		"estudo completo concluído: estudo=%s variantes=%d gráficos=%s histórico=%s textplot=%t",
		caminhoEstudo,
		len(resultadosVariantes),
		resumoGraficos.Diretorio,
		resumoHistorico.Diretorio,
		resumoGraficos.UsouTextplot,
	)

	return ResultadoExecucaoEstudoCompleto{
		EspacoTrabalho:           resultadoExperimento.EspacoTrabalho,
		CaminhoExperimento:       filepath.Join(resultadoExperimento.EspacoTrabalho, "experimento.json"),
		CaminhoEstudoCompleto:    caminhoEstudo,
		DiretorioGraficos:        resumoGraficos.Diretorio,
		DiretorioHistorico:       resumoHistorico.Diretorio,
		RelatorioExperimento:     resultadoExperimento.RelatorioExperimento,
		RelatorioComparacao:      resultadoExperimento.RelatorioComparacao,
		RelatorioEstudoCompleto:  relatorioEstudo,
		ResultadosVariantes:      resultadosVariantes,
		CaminhoComparacao:        resultadoExperimento.CaminhoComparacao,
		CaminhoRelatorioRastreio: resultadoExperimento.CaminhoRastreio,
	}, nil
}

// Executar executa analisar -> gerar -> avaliar dentro de um único workspace.
func (s *Servico) Executar(cfg *dominio.ConfigAplicacao, analysisModelKey, generationModelKey, judgeModelKey string) (ResultadoExecucao, error) {
	workspace, err := artefatos.NovoEspacoTrabalho(cfg.Fluxo.DiretorioSaida, artefatos.NovoIDExecucao("run-"+analysisModelKey+"-"+generationModelKey))
	if err != nil {
		return ResultadoExecucao{}, err
	}
	analysisReport, analysisPath, _, err := s.Analisar(cfg, analysisModelKey, workspace)
	if err != nil {
		return ResultadoExecucao{}, err
	}
	generationReport, generationPath, _, err := s.Gerar(cfg, analysisReport, analysisPath, generationModelKey, workspace)
	if err != nil {
		return ResultadoExecucao{}, err
	}
	evaluationReport, evaluationPath, _, err := s.Avaliar(cfg, analysisReport, analysisPath, generationReport, generationPath, judgeModelKey, workspace)
	if err != nil {
		return ResultadoExecucao{}, err
	}
	return ResultadoExecucao{
		EspacoTrabalho:     workspace.Raiz,
		CaminhoAnalise:     analysisPath,
		CaminhoGeracao:     generationPath,
		CaminhoAvaliacao:   evaluationPath,
		RelatorioAnalise:   analysisReport,
		RelatorioGeracao:   generationReport,
		RelatorioAvaliacao: evaluationReport,
	}, nil
}

// CarregarRelatorioAnalise carrega um relatório de análise a partir de um artefato JSON.
func CarregarRelatorioAnalise(path string) (dominio.RelatorioAnalise, error) {
	out := dominio.RelatorioAnalise{}
	if err := artefatos.LerJSON(path, &out); err != nil {
		return dominio.RelatorioAnalise{}, err
	}
	return out, nil
}

// CarregarRelatorioGeracao carrega um relatório de geração a partir de um artefato JSON.
func CarregarRelatorioGeracao(path string) (dominio.RelatorioGeracao, error) {
	out := dominio.RelatorioGeracao{}
	if err := artefatos.LerJSON(path, &out); err != nil {
		return dominio.RelatorioGeracao{}, err
	}
	return out, nil
}

// getModelOrError recupera a configuração de um modelo ou retorna um erro descritivo.
func getModelOrError(cfg *dominio.ConfigAplicacao, modelKey string) (dominio.ConfigModelo, error) {
	model, ok := cfg.Modelos[modelKey]
	if !ok {
		keys := make([]string, 0, len(cfg.Modelos))
		for k := range cfg.Modelos {
			keys = append(keys, k)
		}
		sort.Strings(keys)
		return dominio.ConfigModelo{}, fmt.Errorf("o modelo %q não está configurado. disponíveis: %s", modelKey, strings.Join(keys, ", "))
	}
	return model, nil
}

package aplicacao

import (
	"fmt"
	"os"
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
	registro.Info("pipeline", "experimento concluído: comparação=%s variantes=%d raiz=%s", comparisonPath, len(variantArtifacts), workspace.Raiz)

	return ResultadoExecucaoExperimento{
		EspacoTrabalho:       workspace.Raiz,
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

	registro.Info(
		"pipeline",
		"estudo completo concluído: estudo=%s variantes=%d gráficos=%s textplot=%t",
		caminhoEstudo,
		len(resultadosVariantes),
		resumoGraficos.Diretorio,
		resumoGraficos.UsouTextplot,
	)

	return ResultadoExecucaoEstudoCompleto{
		EspacoTrabalho:           resultadoExperimento.EspacoTrabalho,
		CaminhoExperimento:       filepath.Join(resultadoExperimento.EspacoTrabalho, "experimento.json"),
		CaminhoEstudoCompleto:    caminhoEstudo,
		DiretorioGraficos:        resumoGraficos.Diretorio,
		RelatorioExperimento:     resultadoExperimento.RelatorioExperimento,
		RelatorioComparacao:      resultadoExperimento.RelatorioComparacao,
		RelatorioEstudoCompleto:  relatorioEstudo,
		ResultadosVariantes:      resultadosVariantes,
		CaminhoComparacao:        resultadoExperimento.CaminhoComparacao,
		CaminhoRelatorioRastreio: resultadoExperimento.CaminhoRastreio,
	}, nil
}

// calcularMetricasVarianteEstudo resume produtividade e estabilidade da suíte
// produzida por uma variante específica.
func calcularMetricasVarianteEstudo(
	quantidadeMetodos int,
	quantidadeExpaths int,
	quantidadeArquivosTeste int,
	resultadosMetricas []dominio.ResultadoMetrica,
) dominio.MetricasVarianteEstudo {
	return dominio.MetricasVarianteEstudo{
		TaxaArquivosTestePorMetodo: taxaFloatSegura(float64(quantidadeArquivosTeste), quantidadeMetodos),
		TaxaArquivosTestePorExpath: taxaFloatSegura(float64(quantidadeArquivosTeste), quantidadeExpaths),
		TaxaSucessoMetricas:        taxaSucessoMetricas(resultadosMetricas),
	}
}

// taxaFloatSegura calcula razões simples protegendo a divisão por zero.
func taxaFloatSegura(numerador float64, denominador int) *float64 {
	if denominador <= 0 {
		return nil
	}
	valor := numerador / float64(denominador)
	return &valor
}

// taxaSucessoMetricas resume a fração percentual de métricas bem-sucedidas.
func taxaSucessoMetricas(resultados []dominio.ResultadoMetrica) *float64 {
	if len(resultados) == 0 {
		return nil
	}
	sucessos := 0
	for _, resultado := range resultados {
		if resultado.Sucesso {
			sucessos++
		}
	}
	valor := (float64(sucessos) / float64(len(resultados))) * 100.0
	return &valor
}

// calcularComparacaoSuites consolida os deltas principais entre WITUP, LLM e
// a variante combinada.
func calcularComparacaoSuites(variantes []dominio.ResultadoVarianteEstudoCompleto) dominio.ComparacaoSuitesEstudo {
	indice := make(map[dominio.VarianteComparacao]dominio.ResultadoVarianteEstudoCompleto, len(variantes))
	for _, variante := range variantes {
		indice[variante.Variante] = variante
	}

	witup := indice[dominio.VarianteWITUPApenas]
	llm := indice[dominio.VarianteLLMApenas]
	combinado := indice[dominio.VarianteWITUPMaisLLM]

	return dominio.ComparacaoSuitesEstudo{
		MelhorVariantePorNotaMetricas:      melhorVariantePorNota(variantes, false),
		MelhorVariantePorNotaCombinada:     melhorVariantePorNota(variantes, true),
		DeltaArquivosTesteLLMVsWITUP:       deltaInteiros(llm.QuantidadeArquivosTeste, witup.QuantidadeArquivosTeste),
		DeltaArquivosTesteCombinadoVsWITUP: deltaInteiros(combinado.QuantidadeArquivosTeste, witup.QuantidadeArquivosTeste),
		DeltaMetricasLLMVsWITUP:            deltaPontuacoes(llm.NotaMetricas, witup.NotaMetricas),
		DeltaMetricasCombinadoVsWITUP:      deltaPontuacoes(combinado.NotaMetricas, witup.NotaMetricas),
		DeltaMetricasCombinadoVsLLM:        deltaPontuacoes(combinado.NotaMetricas, llm.NotaMetricas),
		DeltaCombinadaLLMVsWITUP:           deltaPontuacoes(llm.NotaCombinada, witup.NotaCombinada),
		DeltaCombinadaCombinadoVsWITUP:     deltaPontuacoes(combinado.NotaCombinada, witup.NotaCombinada),
		DeltaCombinadaCombinadoVsLLM:       deltaPontuacoes(combinado.NotaCombinada, llm.NotaCombinada),
	}
}

// deltaInteiros expõe a diferença entre duas contagens no formato do relatório.
func deltaInteiros(esquerda, direita int) *float64 {
	valor := float64(esquerda - direita)
	return &valor
}

// deltaPontuacoes subtrai duas notas opcionais quando ambas estão disponíveis.
func deltaPontuacoes(esquerda, direita *float64) *float64 {
	if esquerda == nil || direita == nil {
		return nil
	}
	valor := *esquerda - *direita
	return &valor
}

// melhorVariantePorNota escolhe a variante com a melhor nota métrica ou combinada.
func melhorVariantePorNota(variantes []dominio.ResultadoVarianteEstudoCompleto, usarNotaCombinada bool) string {
	melhor := ""
	var melhorNota *float64
	for _, variante := range variantes {
		nota := variante.NotaMetricas
		if usarNotaCombinada {
			nota = variante.NotaCombinada
		}
		if nota == nil {
			continue
		}
		if melhorNota == nil || *nota > *melhorNota {
			valor := *nota
			melhorNota = &valor
			melhor = string(variante.Variante)
		}
	}
	return melhor
}

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

// ExecutarBenchmark executa cenários e persiste os artefatos de ranqueamento.
func (s *Servico) ExecutarBenchmark(cfg *dominio.ConfigAplicacao, scenarios []dominio.CenarioBenchmark, judgeModelKey string) (dominio.RelatorioBenchmark, string, error) {
	workspace, err := artefatos.NovoEspacoTrabalho(cfg.Fluxo.DiretorioSaida, artefatos.NovoIDExecucao("benchmark"))
	if err != nil {
		return dominio.RelatorioBenchmark{}, "", err
	}

	entries := make([]dominio.EntradaBenchmark, 0, len(scenarios))
	for _, sc := range scenarios {
		subWorkspace, err := artefatos.NovoEspacoTrabalho(workspace.Raiz, artefatos.Slugificar(sc.ChaveModeloAnalise+"-to-"+sc.ChaveModeloGeracao))
		if err != nil {
			return dominio.RelatorioBenchmark{}, "", err
		}
		analysisReport, analysisPath, _, err := s.Analisar(cfg, sc.ChaveModeloAnalise, subWorkspace)
		if err != nil {
			return dominio.RelatorioBenchmark{}, "", err
		}
		generationReport, generationPath, _, err := s.Gerar(cfg, analysisReport, analysisPath, sc.ChaveModeloGeracao, subWorkspace)
		if err != nil {
			return dominio.RelatorioBenchmark{}, "", err
		}
		evaluationReport, evaluationPath, _, err := s.Avaliar(cfg, analysisReport, analysisPath, generationReport, generationPath, judgeModelKey, subWorkspace)
		if err != nil {
			return dominio.RelatorioBenchmark{}, "", err
		}
		var judgeScore *float64
		if evaluationReport.AvaliacaoJuiz != nil {
			judgeScore = &evaluationReport.AvaliacaoJuiz.Nota
		}
		entries = append(entries, dominio.EntradaBenchmark{
			ChaveModeloAnalise: sc.ChaveModeloAnalise,
			ChaveModeloGeracao: sc.ChaveModeloGeracao,
			CaminhoAvaliacao:   evaluationPath,
			NotaMetricas:       evaluationReport.NotaMetricas,
			JudgeScore:         judgeScore,
			NotaCombinada:      evaluationReport.NotaCombinada,
		})
	}

	sort.Slice(entries, func(i, j int) bool {
		return chaveOrdenacaoNota(entries[i].NotaCombinada, entries[i].NotaMetricas, entries[i].JudgeScore) >
			chaveOrdenacaoNota(entries[j].NotaCombinada, entries[j].NotaMetricas, entries[j].JudgeScore)
	})
	for i := range entries {
		entries[i].Posicao = i + 1
	}

	report := dominio.RelatorioBenchmark{
		IDExecucao:      filepath.Base(workspace.Raiz),
		GeradoEm:        dominio.HorarioUTC(),
		ChaveModeloJuiz: judgeModelKey,
		Entradas:        entries,
	}
	benchmarkPath := filepath.Join(workspace.Raiz, "benchmark.json")
	if err := artefatos.EscreverJSON(benchmarkPath, report); err != nil {
		return dominio.RelatorioBenchmark{}, "", err
	}
	if err := artefatos.EscreverTexto(filepath.Join(workspace.Raiz, "benchmark.md"), construirMarkdownBenchmark(entries)); err != nil {
		return dominio.RelatorioBenchmark{}, "", err
	}
	if err := registrarArtefatoNoBanco(cfg, report.IDExecucao, "benchmark", "", "", benchmarkPath, report.GeradoEm, report); err != nil {
		return dominio.RelatorioBenchmark{}, "", err
	}
	return report, benchmarkPath, nil
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

package aplicacao

import (
	"flag"
	"fmt"
	"os"
	"path/filepath"

	"github.com/marceloamorim/witup-llm/internal/configuracao"
	"github.com/marceloamorim/witup-llm/internal/metricas"
)

// executarGeracao gera arquivos de teste a partir de uma análise já persistida.
func executarGeracao(args []string, service *Servico) int {
	fs := flag.NewFlagSet("generate", flag.ContinueOnError)
	configPath := fs.String("config", "", "Caminho para o arquivo de configuração JSON")
	analysisPath := fs.String("analysis", "", "Caminho para analysis.json")
	modelKey := fs.String("model", "", "Chave do modelo configurado")
	if err := fs.Parse(args); err != nil {
		return 2
	}
	if *configPath == "" || *analysisPath == "" || *modelKey == "" {
		fmt.Fprintln(os.Stderr, "erro: --config, --analysis e --model são obrigatórios")
		return 2
	}

	cfg, err := configuracao.Carregar(*configPath)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}
	analysisPathAbs, err := filepath.Abs(*analysisPath)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: ao resolver o caminho da análise: %v\n", err)
		return 1
	}
	if err := GarantirCaminhosExistem(analysisPathAbs); err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}

	analysisReport, err := CarregarRelatorioAnalise(analysisPathAbs)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}

	report, generationPath, espaco, err := service.Gerar(cfg, analysisReport, analysisPathAbs, *modelKey, nil)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}

	fmt.Printf("Caminho da geração    : %s\n", generationPath)
	fmt.Printf("Arquivos gerados      : %d\n", len(report.ArquivosTeste))
	fmt.Printf("Diretório de testes   : %s\n", espaco.Testes)
	imprimirResumoObservabilidade(*configPath, cfg, espaco.Raiz)
	return 0
}

// executarAvaliacao executa métricas e, opcionalmente, um juiz avaliador.
func executarAvaliacao(args []string, service *Servico) int {
	fs := flag.NewFlagSet("evaluate", flag.ContinueOnError)
	configPath := fs.String("config", "", "Caminho para o arquivo de configuração JSON")
	analysisPath := fs.String("analysis", "", "Caminho para analysis.json")
	generationPath := fs.String("generation", "", "Caminho para generation.json")
	judgeModel := fs.String("judge-model", "", "Chave opcional do modelo juiz")
	if err := fs.Parse(args); err != nil {
		return 2
	}
	if *configPath == "" || *analysisPath == "" || *generationPath == "" {
		fmt.Fprintln(os.Stderr, "erro: --config, --analysis e --generation são obrigatórios")
		return 2
	}

	cfg, err := configuracao.Carregar(*configPath)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}
	analysisAbs, _ := filepath.Abs(*analysisPath)
	generationAbs, _ := filepath.Abs(*generationPath)
	if err := GarantirCaminhosExistem(analysisAbs, generationAbs); err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}

	analysisReport, err := CarregarRelatorioAnalise(analysisAbs)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}
	generationReport, err := CarregarRelatorioGeracao(generationAbs)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}

	selectedJudge := *judgeModel
	if selectedJudge == "" {
		selectedJudge = cfg.Fluxo.ModeloJuiz
	}
	report, evaluationPath, espaco, err := service.Avaliar(cfg, analysisReport, analysisAbs, generationReport, generationAbs, selectedJudge, nil)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}

	fmt.Printf("Caminho da avaliação  : %s\n", evaluationPath)
	fmt.Printf("Nota de métricas      : %s\n", metricas.FormatarPontuacao(report.NotaMetricas))
	fmt.Printf("Nota combinada        : %s\n", metricas.FormatarPontuacao(report.NotaCombinada))
	if report.AvaliacaoJuiz != nil {
		fmt.Printf("Veredito do juiz      : %s\n", report.AvaliacaoJuiz.Veredito)
	}
	imprimirResumoObservabilidade(*configPath, cfg, espaco.Raiz)
	return 0
}

// executarTudo executa a pipeline completa analisar -> gerar -> avaliar.
func executarTudo(args []string, service *Servico) int {
	fs := flag.NewFlagSet("run", flag.ContinueOnError)
	configPath := fs.String("config", "", "Caminho para o arquivo de configuração JSON")
	analysisModel := fs.String("analysis-model", "", "Chave do modelo de análise")
	generationModel := fs.String("generation-model", "", "Chave do modelo de geração")
	judgeModel := fs.String("judge-model", "", "Chave opcional do modelo juiz")
	if err := fs.Parse(args); err != nil {
		return 2
	}
	if *configPath == "" || *analysisModel == "" || *generationModel == "" {
		fmt.Fprintln(os.Stderr, "erro: --config, --analysis-model e --generation-model são obrigatórios")
		return 2
	}

	cfg, err := configuracao.Carregar(*configPath)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}

	selectedJudge := *judgeModel
	if selectedJudge == "" {
		selectedJudge = cfg.Fluxo.ModeloJuiz
	}
	result, err := service.Executar(cfg, *analysisModel, *generationModel, selectedJudge)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}

	fmt.Printf("Caminho da análise    : %s\n", result.CaminhoAnalise)
	fmt.Printf("Caminho da geração    : %s\n", result.CaminhoGeracao)
	fmt.Printf("Caminho da avaliação  : %s\n", result.CaminhoAvaliacao)
	fmt.Printf("Nota combinada        : %s\n", metricas.FormatarPontuacao(result.RelatorioAvaliacao.NotaCombinada))
	imprimirResumoObservabilidade(*configPath, cfg, result.EspacoTrabalho)
	return 0
}

// executarExperimento executa o experimento de três ramos: WITUP_ONLY, LLM_ONLY e combinado.
func executarExperimento(args []string, service *Servico) int {
	fs := flag.NewFlagSet("run-experiment", flag.ContinueOnError)
	configPath := fs.String("config", "", "Caminho para o arquivo de configuração JSON")
	modelKey := fs.String("model", "", "Chave do modelo configurado para a branch LLM_ONLY")
	projectKey := fs.String("project-key", "", "Chave do projeto dentro do pacote de replicação local")
	baselineFile := fs.String("baseline-file", "", "Sobrescreve o arquivo de baseline configurado")
	if err := fs.Parse(args); err != nil {
		return 2
	}
	if *configPath == "" || *modelKey == "" || *projectKey == "" {
		fmt.Fprintln(os.Stderr, "erro: --config, --model e --project-key são obrigatórios")
		return 2
	}

	cfg, err := configuracao.Carregar(*configPath)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}
	if *baselineFile != "" {
		cfg.Fluxo.ArquivoBaselineWIT = *baselineFile
	}
	if _, err := service.SincronizarBaselinesWITUP(cfg); err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}

	result, err := service.ExecutarExperimento(cfg, *projectKey, *modelKey)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}

	fmt.Printf("Análise WITUP         : %s\n", result.CaminhoAnaliseWITUP)
	fmt.Printf("Análise LLM           : %s\n", result.CaminhoAnaliseLLM)
	fmt.Printf("Caminho da comparação : %s\n", result.CaminhoComparacao)
	fmt.Printf("Rastreios de agentes  : %s\n", result.CaminhoRastreio)
	fmt.Printf("Métodos em comum      : %d\n", result.RelatorioComparacao.Resumo.MetodosEmAmbos)
	fmt.Printf("Variantes             : %d\n", len(result.ArtefatosVariantes))
	fmt.Printf("Cobertura métodos LLM : %s\n", metricas.FormatarPontuacao(result.RelatorioComparacao.Metricas.TaxaCoberturaMetodosLLMSobreWITUP))
	fmt.Printf("Cobertura expaths LLM : %s\n", metricas.FormatarPontuacao(result.RelatorioComparacao.Metricas.TaxaCoberturaExpathsLLMSobreWITUP))
	fmt.Printf("Jaccard expaths       : %s\n", metricas.FormatarPontuacao(result.RelatorioComparacao.Metricas.IndiceJaccardExpaths))
	fmt.Printf("Histórico Parquet     : %s\n", result.DiretorioHistorico)
	imprimirResumoObservabilidade(*configPath, cfg, result.EspacoTrabalho)
	return 0
}

// executarEstudoCompleto executa o experimento de expaths, gera as suítes de
// teste por variante, avalia essas suítes e registra tudo no DuckDB.
func executarEstudoCompleto(args []string, service *Servico) int {
	fs := flag.NewFlagSet("run-full-study", flag.ContinueOnError)
	configPath := fs.String("config", "", "Caminho para o arquivo de configuração JSON")
	analysisModelKey := fs.String("analysis-model", "", "Chave do modelo configurado para a branch LLM_ONLY")
	generationModelKey := fs.String("generation-model", "", "Chave do modelo configurado para geração de testes")
	judgeModelKey := fs.String("judge-model", "", "Chave opcional do modelo juiz")
	projectKey := fs.String("project-key", "", "Chave do projeto dentro do pacote de replicação local")
	baselineFile := fs.String("baseline-file", "", "Sobrescreve o arquivo de baseline configurado")
	if err := fs.Parse(args); err != nil {
		return 2
	}
	if *configPath == "" || *analysisModelKey == "" || *generationModelKey == "" || *projectKey == "" {
		fmt.Fprintln(os.Stderr, "erro: --config, --analysis-model, --generation-model e --project-key são obrigatórios")
		return 2
	}

	cfg, err := configuracao.Carregar(*configPath)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}
	if *baselineFile != "" {
		cfg.Fluxo.ArquivoBaselineWIT = *baselineFile
	}
	if _, err := service.SincronizarBaselinesWITUP(cfg); err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}

	selectedJudge := *judgeModelKey
	if selectedJudge == "" {
		selectedJudge = cfg.Fluxo.ModeloJuiz
	}

	result, err := service.ExecutarEstudoCompleto(cfg, *projectKey, *analysisModelKey, *generationModelKey, selectedJudge)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}

	fmt.Printf("Caminho do experimento: %s\n", result.CaminhoExperimento)
	fmt.Printf("Caminho da comparação : %s\n", result.CaminhoComparacao)
	fmt.Printf("Caminho do estudo     : %s\n", result.CaminhoEstudoCompleto)
	fmt.Printf("Gráficos do estudo    : %s\n", result.DiretorioGraficos)
	fmt.Printf("Histórico Parquet     : %s\n", result.DiretorioHistorico)
	fmt.Printf("Rastreios de agentes  : %s\n", result.CaminhoRelatorioRastreio)
	fmt.Printf("Métodos em comum      : %d\n", result.RelatorioComparacao.Resumo.MetodosEmAmbos)
	fmt.Printf("Variantes avaliadas   : %d\n", len(result.ResultadosVariantes))
	fmt.Printf("Jaccard expaths       : %s\n", metricas.FormatarPontuacao(result.RelatorioComparacao.Metricas.IndiceJaccardExpaths))
	fmt.Printf("Melhor suíte métricas : %s\n", result.RelatorioEstudoCompleto.ComparacaoSuites.MelhorVariantePorNotaMetricas)
	fmt.Printf("Melhor suíte combinada: %s\n", result.RelatorioEstudoCompleto.ComparacaoSuites.MelhorVariantePorNotaCombinada)
	for _, variante := range result.ResultadosVariantes {
		fmt.Printf(
			"- %s expaths=%d testes=%d métricas=%s combinada=%s sucesso_metricas=%s\n",
			variante.Variante,
			variante.QuantidadeExpaths,
			variante.QuantidadeArquivosTeste,
			metricas.FormatarPontuacao(variante.NotaMetricas),
			metricas.FormatarPontuacao(variante.NotaCombinada),
			metricas.FormatarPontuacao(variante.MetricasDerivadas.TaxaSucessoMetricas),
		)
	}
	imprimirResumoObservabilidade(*configPath, cfg, result.EspacoTrabalho)
	return 0
}

// executarBenchmark executa cenários acoplados ou matriciais de benchmark.
func executarBenchmark(args []string, service *Servico) int {
	fs := flag.NewFlagSet("benchmark", flag.ContinueOnError)
	configPath := fs.String("config", "", "Caminho para o arquivo de configuração JSON")
	judgeModel := fs.String("judge-model", "", "Chave opcional do modelo juiz")
	modelos := &listaStringsFlag{}
	modelosAnalise := &listaStringsFlag{}
	modelosGeracao := &listaStringsFlag{}
	fs.Var(modelos, "model", "Chave do modelo para o benchmark acoplado (repetível)")
	fs.Var(modelosAnalise, "analysis-model", "Chave do modelo de análise para o benchmark matricial (repetível)")
	fs.Var(modelosGeracao, "generation-model", "Chave do modelo de geração para o benchmark matricial (repetível)")
	if err := fs.Parse(args); err != nil {
		return 2
	}
	if *configPath == "" {
		fmt.Fprintln(os.Stderr, "erro: --config é obrigatório")
		return 2
	}

	cfg, err := configuracao.Carregar(*configPath)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}
	cenarios, err := ConstruirCenariosBenchmark(modelos.valores, modelosAnalise.valores, modelosGeracao.valores)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 2
	}

	selectedJudge := *judgeModel
	if selectedJudge == "" {
		selectedJudge = cfg.Fluxo.ModeloJuiz
	}
	report, benchmarkPath, err := service.ExecutarBenchmark(cfg, cenarios, selectedJudge)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}

	fmt.Printf("Caminho do benchmark: %s\n", benchmarkPath)
	for _, entry := range report.Entradas {
		fmt.Printf("#%d %s->%s combinado=%s metrica=%s juiz=%s\n",
			entry.Posicao,
			entry.ChaveModeloAnalise,
			entry.ChaveModeloGeracao,
			metricas.FormatarPontuacao(entry.NotaCombinada),
			metricas.FormatarPontuacao(entry.NotaMetricas),
			metricas.FormatarPontuacao(entry.JudgeScore),
		)
	}
	return 0
}

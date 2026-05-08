package aplicacao

import (
	"flag"
	"fmt"
	"os"
	"path/filepath"

	"github.com/marceloamorim/witup-llm/internal/artefatos"
	"github.com/marceloamorim/witup-llm/internal/configuracao"
	"github.com/marceloamorim/witup-llm/internal/experimento"
)

// executarIngestaoWITUP materializa uma baseline WIT local como análise canônica.
func executarIngestaoWITUP(args []string, service *Servico) int {
	fs := flag.NewFlagSet("ingest-witup", flag.ContinueOnError)
	configPath := fs.String("config", "", "Caminho para o arquivo de configuração JSON")
	projectKey := fs.String("project-key", "", "Projeto no pacote de replicação a ser materializado")
	baselineFile := fs.String("baseline-file", "", "Sobrescreve o arquivo de baseline configurado")
	if err := fs.Parse(args); err != nil {
		return 2
	}
	if *configPath == "" || *projectKey == "" {
		fmt.Fprintln(os.Stderr, "erro: --config e --project-key são obrigatórios")
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

	report, analysisPath, _, err := service.IngerirWITUP(cfg, *projectKey, nil)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}

	fmt.Printf("Raiz de replicação    : %s\n", cfg.Fluxo.RaizReplicacaoWIT)
	fmt.Printf("Arquivo de baseline   : %s\n", cfg.Fluxo.ArquivoBaselineWIT)
	fmt.Printf("Projeto materializado : %s\n", *projectKey)
	fmt.Printf("Caminho da análise    : %s\n", analysisPath)
	fmt.Printf("Métodos               : %d\n", report.TotalMetodos)
	imprimirResumoObservabilidade(*configPath, cfg, filepath.Dir(analysisPath))
	return 0
}

// executarAnalise executa a análise direta com um único prompt por método.
func executarAnalise(args []string, service *Servico) int {
	fs := flag.NewFlagSet("analyze", flag.ContinueOnError)
	configPath := fs.String("config", "", "Caminho para o arquivo de configuração JSON")
	modelKey := fs.String("model", "", "Chave do modelo configurado")
	if err := fs.Parse(args); err != nil {
		return 2
	}
	if *configPath == "" || *modelKey == "" {
		fmt.Fprintln(os.Stderr, "erro: --config e --model são obrigatórios")
		return 2
	}

	cfg, err := configuracao.Carregar(*configPath)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}

	report, analysisPath, espaco, err := service.Analisar(cfg, *modelKey, nil)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}

	fmt.Printf("Caminho da análise    : %s\n", analysisPath)
	fmt.Printf("Métodos               : %d\n", report.TotalMetodos)
	fmt.Printf("Modelo                : %s\n", report.ChaveModelo)
	imprimirResumoObservabilidade(*configPath, cfg, espaco.Raiz)
	return 0
}

// executarAnaliseMultiagentes executa o fluxo multiagente da branch LLM_ONLY.
func executarAnaliseMultiagentes(args []string, service *Servico) int {
	fs := flag.NewFlagSet("analyze-agentic", flag.ContinueOnError)
	configPath := fs.String("config", "", "Caminho para o arquivo de configuração JSON")
	modelKey := fs.String("model", "", "Chave do modelo configurado")
	if err := fs.Parse(args); err != nil {
		return 2
	}
	if *configPath == "" || *modelKey == "" {
		fmt.Fprintln(os.Stderr, "erro: --config e --model são obrigatórios")
		return 2
	}

	cfg, err := configuracao.Carregar(*configPath)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}

	report, analysisPath, traceReport, tracePath, espaco, err := service.AnalisarMultiagentes(cfg, *modelKey, nil)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}

	fmt.Printf("Caminho da análise    : %s\n", analysisPath)
	fmt.Printf("Caminho dos traces    : %s\n", tracePath)
	fmt.Printf("Métodos               : %d\n", report.TotalMetodos)
	fmt.Printf("Rastreios de agentes  : %d\n", len(traceReport.Metodos))
	imprimirResumoObservabilidade(*configPath, cfg, espaco.Raiz)
	return 0
}

// executarComparacaoFontes compara análises canônicas vindas do WITUP e da branch LLM.
func executarComparacaoFontes(args []string) int {
	fs := flag.NewFlagSet("compare-sources", flag.ContinueOnError)
	witupPath := fs.String("witup", "", "Caminho para a análise canônica do WITUP em JSON")
	llmPath := fs.String("llm", "", "Caminho para a análise canônica da LLM em JSON")
	outputDir := fs.String("output-dir", "generated", "Diretório dos artefatos de comparação")
	if err := fs.Parse(args); err != nil {
		return 2
	}
	if *witupPath == "" || *llmPath == "" {
		fmt.Fprintln(os.Stderr, "erro: --witup e --llm são obrigatórios")
		return 2
	}

	witupAbs, _ := filepath.Abs(*witupPath)
	llmAbs, _ := filepath.Abs(*llmPath)
	if err := GarantirCaminhosExistem(witupAbs, llmAbs); err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}

	witupReport, err := CarregarRelatorioAnalise(witupAbs)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}
	llmReport, err := CarregarRelatorioAnalise(llmAbs)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}

	espaco, err := artefatos.NovoEspacoTrabalho(*outputDir, artefatos.NovoIDExecucao("compare-sources"))
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}

	comparison := experimento.ConstruirRelatorioComparacao(witupAbs, witupReport, llmAbs, llmReport)
	caminhoComparacao := filepath.Join(espaco.Comparacoes, "source-comparison.json")
	if err := artefatos.EscreverJSON(caminhoComparacao, comparison); err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}

	variants := experimento.ConstruirVariantes(witupReport, llmReport)
	artefatosVariantes, err := experimento.EscreverArtefatosVariantes(espaco, variants)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}

	fmt.Printf("Diretório da execução   : %s\n", espaco.Raiz)
	fmt.Printf("Caminho da comparação   : %s\n", caminhoComparacao)
	fmt.Printf("Métodos em comum        : %d\n", comparison.Resumo.MetodosEmAmbos)
	fmt.Printf("Artefatos de variantes  : %d\n", len(artefatosVariantes))
	return 0
}

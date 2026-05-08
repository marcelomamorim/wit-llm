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

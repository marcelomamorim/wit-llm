package aplicacao

import (
	"context"
	"flag"
	"fmt"
	"os"
	"path/filepath"

	"github.com/marceloamorim/witup-llm/internal/artefatos"
	"github.com/marceloamorim/witup-llm/internal/configuracao"
	"github.com/marceloamorim/witup-llm/internal/llm"
	"github.com/marceloamorim/witup-llm/internal/registro"
)

func executarPreparacaoBatchSegundaFase(args []string, service *Servico) int {
	fs := flag.NewFlagSet("prepare-phase-two-batch", flag.ContinueOnError)
	configPath := fs.String("config", "", "Caminho para o arquivo de configuração JSON")
	modelKey := fs.String("generation-model", "", "Chave do modelo configurado para geração de testes")
	requestsPath := fs.String("requests", "", "Arquivo JSONL de saída")
	if err := fs.Parse(args); err != nil {
		return 2
	}
	if *configPath == "" || *modelKey == "" || *requestsPath == "" {
		fmt.Fprintln(os.Stderr, "erro: --config, --generation-model e --requests são obrigatórios")
		return 2
	}
	cfg, err := configuracao.Carregar(*configPath)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}
	count, err := service.PrepararBatchGeracaoSegundaFase(cfg, *modelKey, *requestsPath)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}
	fmt.Printf("Requests JSONL        : %s\n", *requestsPath)
	fmt.Printf("Requests geradas      : %d\n", count)
	return 0
}

func executarSubmissaoOpenAIBatch(args []string) int {
	fs := flag.NewFlagSet("submit-openai-batch", flag.ContinueOnError)
	configPath := fs.String("config", "", "Caminho para o arquivo de configuração JSON")
	modelKey := fs.String("model", "", "Chave do modelo configurado")
	requestsPath := fs.String("requests", "", "Arquivo JSONL de requisições Batch")
	outputPath := fs.String("output", "", "Arquivo JSON para metadados da submissão")
	if err := fs.Parse(args); err != nil {
		return 2
	}
	if *configPath == "" || *modelKey == "" || *requestsPath == "" || *outputPath == "" {
		fmt.Fprintln(os.Stderr, "erro: --config, --model, --requests e --output são obrigatórios")
		return 2
	}
	cfg, err := configuracao.Carregar(*configPath)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}
	model, ok := cfg.Modelos[*modelKey]
	if !ok {
		fmt.Fprintf(os.Stderr, "erro: modelo %q não encontrado\n", *modelKey)
		return 1
	}
	ctxHeartbeat, cancelCtxHeartbeat := context.WithCancel(context.Background())
	progressoHeartbeat := registro.NovoProgresso(1)
	cancelHeartbeat := registro.IniciarHeartbeat(ctxHeartbeat, "batch", "batch_submit", "all", "running", progressoHeartbeat)
	defer cancelHeartbeat()
	defer cancelCtxHeartbeat()
	metadata, err := llm.NovoCliente().SubmeterBatchOpenAI(model, *requestsPath)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}
	progressoHeartbeat.Incrementar()
	if err := artefatos.EscreverJSON(*outputPath, metadata); err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}
	fmt.Printf("Batch ID              : %s\n", metadata.BatchID)
	fmt.Printf("Input file ID         : %s\n", metadata.InputFileID)
	fmt.Printf("Metadados             : %s\n", *outputPath)
	return 0
}

func executarColetaOpenAIBatch(args []string) int {
	fs := flag.NewFlagSet("collect-openai-batch", flag.ContinueOnError)
	configPath := fs.String("config", "", "Caminho para o arquivo de configuração JSON")
	modelKey := fs.String("model", "", "Chave do modelo configurado")
	batchID := fs.String("batch-id", "", "ID do batch OpenAI")
	outputDir := fs.String("output-dir", "", "Diretório para salvar metadados e arquivos baixados")
	if err := fs.Parse(args); err != nil {
		return 2
	}
	if *configPath == "" || *modelKey == "" || *batchID == "" || *outputDir == "" {
		fmt.Fprintln(os.Stderr, "erro: --config, --model, --batch-id e --output-dir são obrigatórios")
		return 2
	}
	cfg, err := configuracao.Carregar(*configPath)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}
	model, ok := cfg.Modelos[*modelKey]
	if !ok {
		fmt.Fprintf(os.Stderr, "erro: modelo %q não encontrado\n", *modelKey)
		return 1
	}
	client := llm.NovoCliente()
	ctxHeartbeat, cancelCtxHeartbeat := context.WithCancel(context.Background())
	progressoHeartbeat := registro.NovoProgresso(1)
	cancelHeartbeat := registro.IniciarHeartbeat(ctxHeartbeat, "batch", "batch_collect", "all", "running", progressoHeartbeat)
	defer cancelHeartbeat()
	defer cancelCtxHeartbeat()
	metadata, err := client.ConsultarBatchOpenAI(model, *batchID)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}
	if err := artefatos.EscreverJSON(filepath.Join(*outputDir, "openai_batch_metadata.json"), metadata); err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}
	if metadata.OutputFileID != "" && metadata.OutputFileID != "<nil>" {
		if err := client.BaixarArquivoOpenAI(model, metadata.OutputFileID, filepath.Join(*outputDir, "responses_openai_batch_generation.jsonl")); err != nil {
			fmt.Fprintf(os.Stderr, "erro: %v\n", err)
			return 1
		}
	}
	if metadata.ErrorFileID != "" && metadata.ErrorFileID != "<nil>" {
		if err := client.BaixarArquivoOpenAI(model, metadata.ErrorFileID, filepath.Join(*outputDir, "errors_openai_batch_generation.jsonl")); err != nil {
			fmt.Fprintf(os.Stderr, "erro: %v\n", err)
			return 1
		}
	}
	progressoHeartbeat.Incrementar()
	fmt.Printf("Batch ID              : %s\n", metadata.BatchID)
	fmt.Printf("Status                : %s\n", metadata.Status)
	fmt.Printf("Diretório de saída    : %s\n", *outputDir)
	return 0
}

func executarAvaliacaoBatchSegundaFase(args []string, service *Servico) int {
	fs := flag.NewFlagSet("evaluate-phase-two-batch", flag.ContinueOnError)
	configPath := fs.String("config", "", "Caminho para o arquivo de configuração JSON")
	modelKey := fs.String("generation-model", "", "Chave do modelo configurado para geração")
	responsesPath := fs.String("responses", "", "Arquivo JSONL de respostas Batch")
	errorsPath := fs.String("errors", "", "Arquivo JSONL de erros Batch")
	outputDir := fs.String("output-dir", "", "Diretório da rodada acadêmica")
	runStamp := fs.String("run-stamp", "", "Carimbo UTC usado nos artefatos acadêmicos")
	if err := fs.Parse(args); err != nil {
		return 2
	}
	if *configPath == "" || *modelKey == "" || *responsesPath == "" || *outputDir == "" {
		fmt.Fprintln(os.Stderr, "erro: --config, --generation-model, --responses e --output-dir são obrigatórios")
		return 2
	}
	cfg, err := configuracao.Carregar(*configPath)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}
	report, reportPath, err := service.AvaliarBatchSegundaFase(cfg, *modelKey, *responsesPath, *errorsPath, *outputDir, *runStamp)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}
	fmt.Printf("Relatório Batch       : %s\n", reportPath)
	fmt.Printf("Resumo CSV            : %s\n", report.CaminhoCSVResumo)
	fmt.Printf("Métricas CSV          : %s\n", report.CaminhoCSVMetricas)
	fmt.Printf("Comparação CSV        : %s\n", report.CaminhoCSVComparacao)
	fmt.Printf("Dashboard             : %s\n", report.CaminhoDashboard)
	return 0
}

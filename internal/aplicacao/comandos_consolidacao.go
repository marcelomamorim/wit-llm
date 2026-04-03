package aplicacao

import (
	"flag"
	"fmt"
	"os"

	"github.com/marceloamorim/witup-llm/internal/artefatos"
	"github.com/marceloamorim/witup-llm/internal/configuracao"
	"github.com/marceloamorim/witup-llm/internal/dominio"
)

// executarConsolidacaoEstudo registra no DuckDB um artefato consolidado que
// conecta comparação de expaths, geração de testes e avaliação por variante.
func executarConsolidacaoEstudo(args []string) int {
	fs := flag.NewFlagSet("consolidate-study", flag.ContinueOnError)
	configPath := fs.String("config", "", "Caminho para o arquivo de configuração JSON")
	summaryPath := fs.String("summary", "", "Caminho para o relatório consolidado em JSON")
	projectKey := fs.String("project-key", "", "Chave opcional do projeto para sobrescrever o relatório")
	if err := fs.Parse(args); err != nil {
		return 2
	}
	if *configPath == "" || *summaryPath == "" {
		fmt.Fprintln(os.Stderr, "erro: --config e --summary são obrigatórios")
		return 2
	}

	cfg, err := configuracao.Carregar(*configPath)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}

	relatorio := dominio.RelatorioEstudoCompleto{}
	if err := artefatos.LerJSON(*summaryPath, &relatorio); err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}
	if *projectKey != "" {
		relatorio.ChaveProjeto = *projectKey
	}
	if relatorio.IDExecucao == "" {
		fmt.Fprintln(os.Stderr, "erro: o relatório consolidado precisa conter run_id")
		return 1
	}
	if relatorio.GeradoEm == "" {
		fmt.Fprintln(os.Stderr, "erro: o relatório consolidado precisa conter generated_at")
		return 1
	}

	if err := registrarArtefatoNoBanco(
		cfg,
		relatorio.IDExecucao,
		"estudo_completo",
		relatorio.ChaveProjeto,
		"",
		*summaryPath,
		relatorio.GeradoEm,
		relatorio,
	); err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}

	fmt.Printf("Relatório consolidado : %s\n", *summaryPath)
	fmt.Printf("Projeto               : %s\n", relatorio.ChaveProjeto)
	fmt.Printf("Variantes consolidadas: %d\n", len(relatorio.ResultadosVariantes))
	imprimirResumoObservabilidade(*configPath, cfg, "")
	return 0
}

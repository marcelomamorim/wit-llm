package aplicacao

import (
	"flag"
	"fmt"
	"os"

	"github.com/marceloamorim/witup-llm/internal/metricas"
)

// executarExtracaoJacoco lê um relatório JaCoCo e imprime a cobertura percentual.
func executarExtracaoJacoco(args []string) int {
	fs := flag.NewFlagSet("extract-jacoco", flag.ContinueOnError)
	caminhoXML := fs.String("xml", "", "Caminho para o arquivo jacoco.xml")
	tipoContador := fs.String("counter", "LINE", "Tipo do contador JaCoCo (LINE ou BRANCH)")
	if err := fs.Parse(args); err != nil {
		return 2
	}
	if *caminhoXML == "" {
		fmt.Fprintln(os.Stderr, "erro: --xml é obrigatório")
		return 2
	}

	valor, err := metricas.ExtrairCoberturaJaCoCo(*caminhoXML, *tipoContador)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}
	fmt.Printf("%.2f\n", valor)
	return 0
}

// executarExtracaoPIT lê o relatório mais recente do PIT e imprime o mutation score.
func executarExtracaoPIT(args []string) int {
	fs := flag.NewFlagSet("extract-pit", flag.ContinueOnError)
	raizRelatorios := fs.String("report-dir", "", "Diretório raiz dos relatórios do PIT")
	if err := fs.Parse(args); err != nil {
		return 2
	}
	if *raizRelatorios == "" {
		fmt.Fprintln(os.Stderr, "erro: --report-dir é obrigatório")
		return 2
	}

	valor, _, err := metricas.ExtrairMutacaoPIT(*raizRelatorios)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}
	fmt.Printf("%.2f\n", valor)
	return 0
}

// executarReproducaoExcecoes mede quantos expaths foram materializados em testes.
func executarReproducaoExcecoes(args []string) int {
	fs := flag.NewFlagSet("measure-exception-reproduction", flag.ContinueOnError)
	caminhoAnalise := fs.String("analysis", "", "Caminho para analysis.json")
	caminhoGeracao := fs.String("generation", "", "Caminho para generation.json")
	if err := fs.Parse(args); err != nil {
		return 2
	}
	if *caminhoAnalise == "" || *caminhoGeracao == "" {
		fmt.Fprintln(os.Stderr, "erro: --analysis e --generation são obrigatórios")
		return 2
	}

	valor, err := metricas.CalcularReproducaoExcecoes(*caminhoAnalise, *caminhoGeracao)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}
	fmt.Printf("%.2f\n", valor)
	return 0
}

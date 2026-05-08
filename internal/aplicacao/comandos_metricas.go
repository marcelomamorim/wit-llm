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
	fmt.Printf("WITUP_METRIC=%.2f\n", valor)
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
	fmt.Printf("WITUP_METRIC=%.2f\n", valor)
	return 0
}

// executarExtracaoSurefire soma a quantidade de testes executados a partir dos
// relatórios XML do Maven Surefire.
func executarExtracaoSurefire(args []string) int {
	fs := flag.NewFlagSet("extract-surefire", flag.ContinueOnError)
	raizRelatorios := fs.String("report-dir", "", "Diretório dos relatórios do Surefire")
	tipo := fs.String("kind", "tests", "Métrica desejada: tests, pass-rate, passed, failures, errors ou skipped")
	if err := fs.Parse(args); err != nil {
		return 2
	}
	if *raizRelatorios == "" {
		fmt.Fprintln(os.Stderr, "erro: --report-dir é obrigatório")
		return 2
	}

	estatisticas, err := metricas.ExtrairEstatisticasSurefire(*raizRelatorios)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}

	switch *tipo {
	case "tests":
		fmt.Printf("WITUP_METRIC=%.0f\n", float64(estatisticas.Executados()))
	case "pass-rate":
		fmt.Printf("WITUP_METRIC=%.2f\n", estatisticas.TaxaSucesso())
	case "passed":
		fmt.Printf("WITUP_METRIC=%.0f\n", float64(estatisticas.Aprovados()))
	case "failures":
		fmt.Printf("WITUP_METRIC=%.0f\n", float64(estatisticas.Failures))
	case "errors":
		fmt.Printf("WITUP_METRIC=%.0f\n", float64(estatisticas.Errors))
	case "skipped":
		fmt.Printf("WITUP_METRIC=%.0f\n", float64(estatisticas.Skipped))
	default:
		fmt.Fprintf(os.Stderr, "erro: --kind inválido: %s\n", *tipo)
		return 2
	}
	return 0
}

// executarExtracaoGeracao resume sinais estáticos do generation.json cruzados com a análise.
func executarExtracaoGeracao(args []string) int {
	fs := flag.NewFlagSet("extract-generation", flag.ContinueOnError)
	caminhoAnalise := fs.String("analysis", "", "Caminho para analysis.json")
	caminhoGeracao := fs.String("generation", "", "Caminho para generation.json")
	raizProjeto := fs.String("project-root", "", "Raiz opcional do projeto para validar dependências declaradas")
	tipo := fs.String("kind", "target-method-coverage", "Métrica desejada: target-method-coverage, assertive-tests-rate, exception-assertion-rate, valid-java-rate, package-path-valid-rate, test-method-presence-rate, target-invocation-rate, forbidden-dependency-rate, reflection-usage-rate, brittle-exception-assertion-rate ou internal-state-assertion-rate")
	if err := fs.Parse(args); err != nil {
		return 2
	}
	if *caminhoAnalise == "" || *caminhoGeracao == "" {
		fmt.Fprintln(os.Stderr, "erro: --analysis e --generation são obrigatórios")
		return 2
	}

	estatisticas, err := metricas.ExtrairEstatisticasGeracaoComProjeto(*caminhoAnalise, *caminhoGeracao, *raizProjeto)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}

	switch *tipo {
	case "target-method-coverage":
		fmt.Printf("WITUP_METRIC=%.2f\n", estatisticas.TaxaMetodosAlvoCobertos())
	case "assertive-tests-rate":
		fmt.Printf("WITUP_METRIC=%.2f\n", estatisticas.TaxaTestesAssertivos())
	case "exception-assertion-rate":
		fmt.Printf("WITUP_METRIC=%.2f\n", estatisticas.TaxaTestesExcecao())
	case "valid-java-rate":
		fmt.Printf("WITUP_METRIC=%.2f\n", estatisticas.TaxaArquivosJavaValidos())
	case "package-path-valid-rate":
		fmt.Printf("WITUP_METRIC=%.2f\n", estatisticas.TaxaPacotesValidos())
	case "test-method-presence-rate":
		fmt.Printf("WITUP_METRIC=%.2f\n", estatisticas.TaxaArquivosComMetodoTeste())
	case "target-invocation-rate":
		fmt.Printf("WITUP_METRIC=%.2f\n", estatisticas.TaxaMetodosAlvoInvocados())
	case "forbidden-dependency-rate":
		fmt.Printf("WITUP_METRIC=%.2f\n", estatisticas.TaxaDependenciasProibidas())
	case "reflection-usage-rate":
		fmt.Printf("WITUP_METRIC=%.2f\n", estatisticas.TaxaUsoReflexao())
	case "brittle-exception-assertion-rate":
		fmt.Printf("WITUP_METRIC=%.2f\n", estatisticas.TaxaAssertThrowsFragil())
	case "internal-state-assertion-rate":
		fmt.Printf("WITUP_METRIC=%.2f\n", estatisticas.TaxaAssertEstadoInterno())
	default:
		fmt.Fprintf(os.Stderr, "erro: --kind inválido: %s\n", *tipo)
		return 2
	}
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

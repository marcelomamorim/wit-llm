package aplicacao

import (
	"errors"
	"fmt"
	"os"
)

// Principal é o ponto de entrada único da CLI usado por cmd/witup.
func Principal(argv []string) int {
	if len(argv) == 0 {
		printBannerIfEnabled(argv)
		imprimirUso()
		return 2
	}

	servico := NovoServico(nil, nil)
	comando := argv[0]
	args := argv[1:]

	switch comando {
	case "modelos", "models":
		return executarModelos(args)
	case "sondar", "probe":
		return executarSonda(args)
	case "ingerir-witup", "ingest-witup":
		return executarIngestaoWITUP(args, servico)
	case "visualizar-dados", "browse-data":
		return executarVisualizacaoDados(args)
	case "analisar", "analyze":
		return executarAnalise(args, servico)
	case "analisar-multiagentes", "analyze-agentic":
		return executarAnaliseMultiagentes(args, servico)
	case "comparar-fontes", "compare-sources":
		return executarComparacaoFontes(args)
	case "consolidar-estudo", "consolidate-study":
		return executarConsolidacaoEstudo(args)
	case "extrair-jacoco":
		return executarExtracaoJacoco(args)
	case "extrair-pit":
		return executarExtracaoPIT(args)
	case "extrair-surefire":
		return executarExtracaoSurefire(args)
	case "medir-reproducao-excecoes":
		return executarReproducaoExcecoes(args)
	case "gerar", "generate":
		return executarGeracao(args, servico)
	case "avaliar", "evaluate":
		return executarAvaliacao(args, servico)
	case "executar", "run":
		return executarTudo(args, servico)
	case "executar-experimento", "run-experiment":
		return executarExperimento(args, servico)
	case "executar-estudo-completo", "run-full-study":
		return executarEstudoCompleto(args, servico)
	case "executar-benchmark", "benchmark":
		return executarBenchmark(args, servico)
	case "ajuda", "help", "-h", "--help":
		printBannerIfEnabled(argv)
		imprimirUso()
		return 0
	default:
		fmt.Fprintf(os.Stderr, "erro: comando desconhecido %q\n\n", comando)
		imprimirUso()
		return 2
	}
}

// imprimirUso imprime a ajuda principal da CLI.
func imprimirUso() {
	fmt.Println("witup - CLI para análise de caminhos de exceção e orquestração de experimentos")
	fmt.Println("")
	fmt.Println("Uso:")
	fmt.Println("  witup <command> [flags]")
	fmt.Println("")
	fmt.Println("Comandos:")
	fmt.Println("  modelos               Lista os modelos configurados")
	fmt.Println("  sondar                Testa conectividade e autenticação do modelo")
	fmt.Println("  ingerir-witup         Carrega as baselines do artigo para o DuckDB")
	fmt.Println("  visualizar-dados      Abre uma interface web para consultar o DuckDB")
	fmt.Println("  analisar              Analisa métodos e extrai caminhos de exceção")
	fmt.Println("  analisar-multiagentes Executa a análise LLM baseada em multiagentes")
	fmt.Println("  comparar-fontes       Compara artefatos canônicos do WITUP e da LLM")
	fmt.Println("  consolidar-estudo     Registra no DuckDB o resumo completo do estudo")
	fmt.Println("  extrair-jacoco        Extrai uma métrica numérica de um relatório JaCoCo")
	fmt.Println("  extrair-pit           Extrai o mutation score do relatório PIT")
	fmt.Println("  extrair-surefire      Soma os testes executados a partir dos relatórios do Surefire")
	fmt.Println("  medir-reproducao-excecoes Mede a reprodução de expaths nos testes gerados")
	fmt.Println("  gerar                 Gera testes a partir de um relatório de análise")
	fmt.Println("  avaliar               Executa métricas e, opcionalmente, avaliação por juiz")
	fmt.Println("  executar              Executa analisar -> gerar -> avaliar")
	fmt.Println("  executar-experimento  Prepara WITUP_ONLY, LLM_ONLY e WITUP_PLUS_LLM")
	fmt.Println("  executar-estudo-completo Executa Parte 1 + Parte 2 e consolida o estudo")
	fmt.Println("  executar-benchmark    Executa cenários de benchmark acoplados ou matriciais")
	fmt.Println("  ajuda                 Exibe esta mensagem")
	fmt.Println("")
	fmt.Println("Aliases compatíveis:")
	fmt.Println("  models, probe, ingest-witup, browse-data, analyze, analyze-agentic, compare-sources, consolidate-study")
	fmt.Println("  generate, evaluate, run, run-experiment, run-full-study, benchmark, help")
}

type listaStringsFlag struct {
	valores []string
}

// String renderiza os valores recebidos pelo flag como lista separada por vírgulas.
func (f *listaStringsFlag) String() string {
	return juntarComVirgula(f.valores)
}

// Set acumula valores repetidos em flags multiuso.
func (f *listaStringsFlag) Set(valor string) error {
	if valor == "" {
		return errors.New("valor vazio")
	}
	f.valores = append(f.valores, valor)
	return nil
}

// juntarComVirgula concatena uma lista de strings em um texto legível para CLI.
func juntarComVirgula(valores []string) string {
	if len(valores) == 0 {
		return ""
	}
	saida := valores[0]
	for i := 1; i < len(valores); i++ {
		saida += ", " + valores[i]
	}
	return saida
}

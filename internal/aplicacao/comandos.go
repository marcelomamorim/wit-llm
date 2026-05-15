package aplicacao

import (
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
	case "analisar", "analyze":
		return executarAnalise(args, servico)
	case "analisar-multiagentes", "analyze-agentic":
		return executarAnaliseMultiagentes(args, servico)
	case "comparar-fontes", "compare-sources":
		return executarComparacaoFontes(args)
	case "extrair-jacoco":
		return executarExtracaoJacoco(args)
	case "extrair-pit":
		return executarExtracaoPIT(args)
	case "extrair-surefire":
		return executarExtracaoSurefire(args)
	case "extrair-geracao":
		return executarExtracaoGeracao(args)
	case "medir-reproducao-excecoes":
		return executarReproducaoExcecoes(args)
	case "gerar", "generate":
		return executarGeracao(args, servico)
	case "avaliar", "evaluate":
		return executarAvaliacao(args, servico)
	case "executar", "run":
		return executarTudo(args, servico)
	case "executar-segunda-fase", "run-phase-two":
		return executarSegundaFase(args, servico)
	case "preflight-segunda-fase", "preflight-phase-two":
		return executarPreflightSegundaFase(args, servico)
	case "preparar-batch-segunda-fase", "prepare-phase-two-batch":
		return executarPreparacaoBatchSegundaFase(args, servico)
	case "submeter-openai-batch", "submit-openai-batch":
		return executarSubmissaoOpenAIBatch(args)
	case "coletar-openai-batch", "collect-openai-batch":
		return executarColetaOpenAIBatch(args)
	case "avaliar-batch-segunda-fase", "evaluate-phase-two-batch":
		return executarAvaliacaoBatchSegundaFase(args, servico)
	case "preparar-estudo-jdk-global", "prepare-jdk-global-impact":
		return executarPreparacaoJDKGlobal(args, servico)
	case "avaliar-estudo-jdk-global", "evaluate-jdk-global-impact":
		return executarAvaliacaoJDKGlobal(args, servico)
	case "medir-impacto-jdk-global", "measure-jdk-global-impact":
		return executarMedicaoJTRegJDKGlobal(args, servico)
	case "consolidar-estatisticas-primeira-rodada", "consolidate-first-round":
		return executarConsolidacaoEstatisticasPrimeiraRodada(args)
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
	fmt.Println("  ingerir-witup         Materializa uma baseline WIT local como análise canônica")
	fmt.Println("  analisar              Analisa métodos e extrai caminhos de exceção")
	fmt.Println("  analisar-multiagentes Executa a análise LLM baseada em multiagentes")
	fmt.Println("  comparar-fontes       Compara artefatos canônicos do WITUP e da LLM")
	fmt.Println("  extrair-jacoco        Extrai uma métrica numérica de um relatório JaCoCo")
	fmt.Println("  extrair-pit           Extrai o mutation score do relatório PIT")
	fmt.Println("  extrair-surefire      Extrai métricas dos relatórios do Surefire")
	fmt.Println("  extrair-geracao       Extrai métricas estáticas do generation.json")
	fmt.Println("  medir-reproducao-excecoes Mede a reprodução de expaths nos testes gerados")
	fmt.Println("  gerar                 Gera testes a partir de um relatório de análise")
	fmt.Println("  avaliar               Executa métricas e, opcionalmente, avaliação por juiz")
	fmt.Println("  executar              Executa analisar -> gerar -> avaliar")
	fmt.Println("  executar-segunda-fase Executa a fase 2 focada em contexto WIT vs geração direta")
	fmt.Println("  preflight-segunda-fase Valida ambiente, baselines e alinhamento antes da fase 2")
	fmt.Println("  preparar-batch-segunda-fase Gera JSONL Batch para a geração WIT vs direta")
	fmt.Println("  submeter-openai-batch Submete um JSONL à Batch API da OpenAI")
	fmt.Println("  coletar-openai-batch Consulta um batch e baixa outputs/erros quando disponíveis")
	fmt.Println("  avaliar-batch-segunda-fase Materializa respostas Batch e executa métricas locais")
	fmt.Println("  preparar-estudo-jdk-global Prepara amostra JDK, manifest e JSONL Batch")
	fmt.Println("  avaliar-estudo-jdk-global Materializa variantes JDK e executa métricas globais")
	fmt.Println("  medir-impacto-jdk-global Executa jtreg nas variantes JDK materializadas")
	fmt.Println("  consolidar-estatisticas-primeira-rodada Consolida deltas pareados da rodada estatística")
	fmt.Println("  ajuda                 Exibe esta mensagem")
	fmt.Println("")
	fmt.Println("Aliases compatíveis:")
	fmt.Println("  models, probe, ingest-witup, analyze, analyze-agentic, compare-sources")
	fmt.Println("  generate, evaluate, run, run-phase-two, preflight-phase-two, prepare-phase-two-batch, submit-openai-batch, collect-openai-batch, evaluate-phase-two-batch, prepare-jdk-global-impact, evaluate-jdk-global-impact, measure-jdk-global-impact, consolidate-first-round, help")
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

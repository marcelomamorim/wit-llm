package aplicacao

import (
	"flag"
	"fmt"
	"os"

	"github.com/marceloamorim/witup-llm/internal/configuracao"
)

// executarPreflightSegundaFase valida o ambiente e os baselines antes de uma
// rodada paga da fase 2.
func executarPreflightSegundaFase(args []string, service *Servico) int {
	fs := flag.NewFlagSet("preflight-phase-two", flag.ContinueOnError)
	configPath := fs.String("config", "", "Caminho para o arquivo de configuração JSON")
	checkBuild := fs.Bool("check-build", false, "Executa mvn -DskipTests test-compile em cada projeto")
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

	relatorio, caminhoRelatorio, err := service.PreflightSegundaFase(cfg, *configPath, *checkBuild)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}

	fmt.Printf("Relatório preflight    : %s\n", caminhoRelatorio)
	fmt.Printf("Build check habilitado : %t\n", relatorio.VerificacaoBuild)
	fmt.Printf("Projetos avaliados     : %d\n", len(relatorio.Projetos))
	fmt.Printf("Pronto para rodada     : %t\n", relatorio.Pronto)
	fmt.Printf("Java disponível        : %t\n", relatorio.Ambiente.JavaDisponivel)
	fmt.Printf("Maven disponível       : %t\n", relatorio.Ambiente.MavenDisponivel)
	for _, projeto := range relatorio.Projetos {
		fmt.Printf("- %s: pronto=%t alinhados=%d/%d\n", projeto.Projeto, projeto.Pronto, projeto.MetodosAlinhados, projeto.MetodosBaseline)
	}
	if !relatorio.Pronto {
		return 1
	}
	return 0
}

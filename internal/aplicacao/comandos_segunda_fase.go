package aplicacao

import (
	"flag"
	"fmt"
	"os"
	"path/filepath"

	"github.com/marceloamorim/witup-llm/internal/configuracao"
)

// executarSegundaFase executa a fase 2 focada em dois cenários:
// geração com contexto WIT versus geração direta a partir do código.
func executarSegundaFase(args []string, service *Servico) int {
	fs := flag.NewFlagSet("run-phase-two", flag.ContinueOnError)
	configPath := fs.String("config", "", "Caminho para o arquivo de configuração JSON")
	generationModelKey := fs.String("generation-model", "", "Chave do modelo configurado para geração de testes")
	if err := fs.Parse(args); err != nil {
		return 2
	}
	if *configPath == "" || *generationModelKey == "" {
		fmt.Fprintln(os.Stderr, "erro: --config e --generation-model são obrigatórios")
		return 2
	}

	cfg, err := configuracao.Carregar(*configPath)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}

	relatorio, caminhoRelatorio, err := service.ExecutarSegundaFase(cfg, *generationModelKey)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}

	fmt.Printf("Relatório da fase 2   : %s\n", caminhoRelatorio)
	fmt.Printf("Resumo CSV            : %s\n", relatorio.CaminhoCSVResumo)
	fmt.Printf("Métricas CSV          : %s\n", relatorio.CaminhoCSVMetricas)
	fmt.Printf("Comparação CSV        : %s\n", relatorio.CaminhoCSVComparacao)
	fmt.Printf("Dashboard HTML        : %s\n", relatorio.CaminhoDashboard)
	fmt.Printf("Projetos comparados   : %d\n", len(relatorio.Projetos))
	imprimirResumoObservabilidade(*configPath, cfg, filepath.Dir(caminhoRelatorio))
	return 0
}

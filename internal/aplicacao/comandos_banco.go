package aplicacao

import (
	"flag"
	"fmt"
	"os"

	"github.com/marceloamorim/witup-llm/internal/armazenamento"
	"github.com/marceloamorim/witup-llm/internal/configuracao"
)

// executarVisualizacaoDados inicia uma interface web simples para navegar no DuckDB.
func executarVisualizacaoDados(args []string) int {
	fs := flag.NewFlagSet("browse-data", flag.ContinueOnError)
	configPath := fs.String("config", "", "Caminho para o arquivo de configuração JSON")
	endereco := fs.String("addr", "127.0.0.1:8421", "Endereço HTTP da interface de visualização")
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

	if err := armazenamento.IniciarInterfaceHTTP(cfg.Fluxo.CaminhoDuckDB, *endereco); err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}
	return 0
}

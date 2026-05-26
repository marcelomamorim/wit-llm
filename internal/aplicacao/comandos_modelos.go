package aplicacao

import (
	"encoding/json"
	"flag"
	"fmt"
	"os"
	"sort"

	"github.com/marceloamorim/witup-llm/internal/configuracao"
	"github.com/marceloamorim/witup-llm/internal/llm"
)

// executarModelos lista os modelos configurados no arquivo JSON.
func executarModelos(args []string) int {
	fs := flag.NewFlagSet("models", flag.ContinueOnError)
	configPath := fs.String("config", "", "Caminho para o arquivo de configuração JSON")
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

	chaves := make([]string, 0, len(cfg.Modelos))
	for chave := range cfg.Modelos {
		chaves = append(chaves, chave)
	}
	sort.Strings(chaves)

	for _, chave := range chaves {
		modelo := cfg.Modelos[chave]
		fmt.Printf("%s: provedor=%s modelo=%s base_url=%s\n", chave, modelo.Provedor, modelo.Modelo, modelo.URLBase)
	}
	return 0
}

// executarSonda testa conectividade e autenticação do modelo configurado.
func executarSonda(args []string) int {
	fs := flag.NewFlagSet("probe", flag.ContinueOnError)
	configPath := fs.String("config", "", "Caminho para o arquivo de configuração JSON")
	modelKey := fs.String("model", "", "Chave do modelo configurado")
	jsonOutput := fs.Bool("json", false, "Imprime o payload em JSON")
	if err := fs.Parse(args); err != nil {
		return 2
	}
	if *configPath == "" || *modelKey == "" {
		fmt.Fprintln(os.Stderr, "erro: --config e --model são obrigatórios")
		return 2
	}

	cfg, err := configuracao.Carregar(*configPath)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}

	modelo, ok := cfg.Modelos[*modelKey]
	if !ok {
		fmt.Fprintf(os.Stderr, "erro: o modelo %q não está configurado\n", *modelKey)
		return 1
	}

	cliente := llm.NovoCliente()
	payload, err := cliente.Sondar(modelo)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}
	if *jsonOutput {
		dados, _ := json.MarshalIndent(payload, "", "  ")
		fmt.Println(string(dados))
		return 0
	}

	chaves := make([]string, 0, len(payload))
	for chave := range payload {
		chaves = append(chaves, chave)
	}
	sort.Strings(chaves)

	fmt.Printf("Modelo          : %s\n", *modelKey)
	fmt.Printf("Provedor        : %s\n", modelo.Provedor)
	fmt.Printf("Endpoint        : %s\n", modelo.URLBase)
	fmt.Printf("Status do probe : %v\n", payload["status"])
	fmt.Printf("Chaves payload  : %s\n", juntarComVirgula(chaves))
	imprimirResumoObservabilidade(*configPath, cfg, "")
	return 0
}

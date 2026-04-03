package main

import (
	"os"

	"github.com/marceloamorim/witup-llm/internal/aplicacao"
)

// main delega toda a execução para a camada de pipeline e devolve o código de saída.
func main() {
	os.Exit(executar(os.Args[1:]))
}

// executar isola o código de saída da CLI para permitir testes do pacote main
// sem acionar diretamente os efeitos colaterais de os.Exit.
func executar(argv []string) int {
	return aplicacao.Principal(argv)
}

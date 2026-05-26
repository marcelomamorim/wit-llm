package aplicacao

import (
	"fmt"
	"os"
)

const cliBanner = `
========================================================
 witup-llm :: experimentos com exception paths na JVM
========================================================
`

// printBannerIfEnabled exibe o banner da CLI em execuções humanas e permite
// que ambientes automatizados o suprimam.
func printBannerIfEnabled(argv []string) {
	if os.Getenv("WITUP_NO_BANNER") == "1" {
		return
	}
	for _, arg := range argv {
		if arg == "--json" {
			return
		}
	}
	fmt.Print(cliBanner)
}

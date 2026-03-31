package pipeline

import (
	"fmt"
	"os"
)

const cliBanner = `
__        ___ _______ _   _ _____        _     _     __  __
\ \      / / |_   _| | | | |  __ \      | |   | |   |  \/  |
 \ \ /\ / /| | | | | | | | | |__) |_____| |   | |   | \  / |
  \ V  V / | | | | | | | | |  ___/______| |   | |   | |\/| |
   \_/\_/  |_| |_| |_|_| |_|_|          | |___| |___| |  | |
                                         |_____|_____|_|  |_|
`

// printBannerIfEnabled shows the CLI banner for human-facing runs while still
// allowing scripted executions to suppress it.
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

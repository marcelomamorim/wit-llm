//go:build windows

package metricas

import "os/exec"

func configurarCancelamentoComando(cmd *exec.Cmd) {
	// No Windows, exec.CommandContext ja cancela o processo principal.
}

//go:build windows

package metricas

import "os/exec"

// configurarCancelamentoComando é no-op no Windows porque exec.CommandContext já
// cancela o processo filho diretamente. No Unix, o equivalente usa Setpgid para
// garantir que subprocessos do mesmo grupo (ex.: mvn spawning javac) também
// sejam encerrados via SIGKILL no processo group inteiro.
func configurarCancelamentoComando(cmd *exec.Cmd) {}

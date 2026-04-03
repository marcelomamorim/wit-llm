package registro

import (
	"fmt"
	"io"
	"os"
	"path/filepath"
	"strings"
	"sync"
	"time"
)

// nivel representa o nível mínimo de mensagens exibidas na CLI.
type nivel int

const (
	nivelSilencioso nivel = iota
	nivelInfo
	nivelDebug
)

var (
	muSaida          sync.Mutex
	inicializarSaida sync.Once
	saida            io.Writer = os.Stderr
	caminhoArquivo   string
)

// Info registra uma mensagem informativa com timestamp e componente.
func Info(componente, formato string, args ...interface{}) {
	if nivelAtual() < nivelInfo {
		return
	}
	escrever("INFO", componente, formato, args...)
}

// Debug registra mensagens detalhadas quando WITUP_LOG_LEVEL=debug.
func Debug(componente, formato string, args ...interface{}) {
	if nivelAtual() < nivelDebug {
		return
	}
	escrever("DEBUG", componente, formato, args...)
}

// CaminhoArquivo devolve o destino configurado para espelhamento dos logs.
func CaminhoArquivo() string {
	inicializarDestino()
	return caminhoArquivo
}

// escrever monta a linha final de log e a envia para o destino configurado.
func escrever(tipo, componente, formato string, args ...interface{}) {
	inicializarDestino()

	muSaida.Lock()
	defer muSaida.Unlock()

	instante := time.Now().Format("2006-01-02 15:04:05")
	mensagem := fmt.Sprintf(formato, args...)
	fmt.Fprintf(saida, "%s [%s] %s\n", instante, strings.ToLower(componente)+"/"+strings.ToLower(tipo), mensagem)
}

// inicializarDestino prepara a escrita opcional em arquivo sem repetir a
// abertura do handle ao longo da execução.
func inicializarDestino() {
	inicializarSaida.Do(func() {
		caminhoArquivo = strings.TrimSpace(os.Getenv("WITUP_LOG_FILE"))
		if caminhoArquivo == "" {
			return
		}
		if err := os.MkdirAll(filepath.Dir(caminhoArquivo), 0o755); err != nil {
			caminhoArquivo = ""
			return
		}
		arquivo, err := os.OpenFile(caminhoArquivo, os.O_APPEND|os.O_CREATE|os.O_WRONLY, 0o644)
		if err != nil {
			caminhoArquivo = ""
			return
		}
		saida = io.MultiWriter(os.Stderr, arquivo)
	})
}

// nivelAtual traduz a variável de ambiente WITUP_LOG_LEVEL para o enum interno.
func nivelAtual() nivel {
	switch strings.ToLower(strings.TrimSpace(os.Getenv("WITUP_LOG_LEVEL"))) {
	case "silent", "none", "off", "0":
		return nivelSilencioso
	case "debug":
		return nivelDebug
	default:
		return nivelInfo
	}
}

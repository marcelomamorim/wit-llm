package registro

import (
	"os"
	"path/filepath"
	"strings"
	"sync"
	"testing"
)

func resetRegistroParaTeste() {
	muSaida = sync.Mutex{}
	inicializarSaida = sync.Once{}
	saida = os.Stderr
	caminhoArquivo = ""
}

func TestCaminhoArquivoInicializaDestinoConfigurado(t *testing.T) {
	resetRegistroParaTeste()
	caminho := filepath.Join(t.TempDir(), "logs", "cli.log")
	t.Setenv("WITUP_LOG_FILE", caminho)

	if got := CaminhoArquivo(); got != caminho {
		t.Fatalf("esperava caminho %q, recebi %q", caminho, got)
	}
	if _, err := os.Stat(filepath.Dir(caminho)); err != nil {
		t.Fatalf("esperava diretório de log criado: %v", err)
	}
}

func TestCaminhoArquivoIgnoraDestinoInvalido(t *testing.T) {
	resetRegistroParaTeste()
	arquivo := filepath.Join(t.TempDir(), "nao-e-diretorio")
	if err := os.WriteFile(arquivo, []byte("x"), 0o644); err != nil {
		t.Fatalf("escrever fixture: %v", err)
	}
	t.Setenv("WITUP_LOG_FILE", filepath.Join(arquivo, "cli.log"))

	if got := CaminhoArquivo(); got != "" {
		t.Fatalf("esperava caminho vazio quando o destino é inválido, recebi %q", got)
	}
}

func TestNivelAtualRespeitaAmbiente(t *testing.T) {
	casos := map[string]nivel{
		"debug": nivelDebug,
		"off":   nivelSilencioso,
		"":      nivelInfo,
	}
	for valor, esperado := range casos {
		t.Run(valor, func(t *testing.T) {
			resetRegistroParaTeste()
			t.Setenv("WITUP_LOG_LEVEL", valor)
			if got := nivelAtual(); got != esperado {
				t.Fatalf("esperava nível %v, recebi %v", esperado, got)
			}
		})
	}
}

func TestEscreverRegistraMensagemNoArquivo(t *testing.T) {
	resetRegistroParaTeste()
	caminho := filepath.Join(t.TempDir(), "logs", "cli.log")
	t.Setenv("WITUP_LOG_FILE", caminho)

	Info("pipeline", "mensagem %d", 42)

	dados, err := os.ReadFile(caminho)
	if err != nil {
		t.Fatalf("ler log escrito: %v", err)
	}
	texto := string(dados)
	if !strings.Contains(texto, "[pipeline/info] mensagem 42") {
		t.Fatalf("esperava mensagem registrada no log, recebi %q", texto)
	}
}

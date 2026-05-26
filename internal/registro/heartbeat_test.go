package registro

import (
	"bytes"
	"context"
	"strings"
	"sync"
	"testing"
	"time"
)

func TestHeartbeatEmiteCamposEParaComCancelamento(t *testing.T) {
	t.Setenv("WITUP_LOG_LEVEL", "info")
	var buffer bytes.Buffer
	antigaSaida := saida
	antigoCaminho := caminhoArquivo
	antigaOnce := inicializarSaida
	saida = &buffer
	caminhoArquivo = ""
	inicializarSaida = &sync.Once{}
	defer func() {
		saida = antigaSaida
		caminhoArquivo = antigoCaminho
		inicializarSaida = antigaOnce
	}()

	progresso := NovoProgresso(2)
	ctx, cancelCtx := context.WithCancel(context.Background())
	cancelHeartbeat := IniciarHeartbeatComIntervalo(ctx, "teste", "batch_collect", "project x", "running", progresso, 10*time.Millisecond)
	time.Sleep(25 * time.Millisecond)
	progresso.Incrementar()
	cancelHeartbeat()
	cancelCtx()
	antes := buffer.Len()
	time.Sleep(25 * time.Millisecond)
	depois := buffer.Len()

	conteudo := buffer.String()
	for _, esperado := range []string{"heartbeat etapa=batch_collect", "projeto=project_x", "progresso=", "status=running", "elapsed="} {
		if !strings.Contains(conteudo, esperado) {
			t.Fatalf("heartbeat deveria conter %q:\n%s", esperado, conteudo)
		}
	}
	if antes != depois {
		t.Fatalf("heartbeat continuou escrevendo após cancelamento")
	}
}


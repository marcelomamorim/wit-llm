package registro

import (
	"context"
	"fmt"
	"strings"
	"sync/atomic"
	"time"
)

const IntervaloHeartbeatPadrao = 5 * time.Second

type ProgressoHeartbeat struct {
	done  atomic.Int64
	total atomic.Int64
}

func NovoProgresso(total int) *ProgressoHeartbeat {
	p := &ProgressoHeartbeat{}
	p.total.Store(int64(total))
	return p
}

func (p *ProgressoHeartbeat) Incrementar() {
	if p == nil {
		return
	}
	p.done.Add(1)
}

func (p *ProgressoHeartbeat) String() string {
	if p == nil {
		return "0/0"
	}
	return fmt.Sprintf("%d/%d", p.done.Load(), p.total.Load())
}

// IniciarHeartbeat registra periodicamente o estado de uma etapa longa.
func IniciarHeartbeat(ctx context.Context, componente, etapa, projeto, status string, progresso *ProgressoHeartbeat) context.CancelFunc {
	return IniciarHeartbeatComIntervalo(ctx, componente, etapa, projeto, status, progresso, IntervaloHeartbeatPadrao)
}

// IniciarHeartbeatComIntervalo existe para testes e para scripts/runtime com cadências específicas.
func IniciarHeartbeatComIntervalo(ctx context.Context, componente, etapa, projeto, status string, progresso *ProgressoHeartbeat, intervalo time.Duration) context.CancelFunc {
	if intervalo <= 0 {
		intervalo = IntervaloHeartbeatPadrao
	}
	ctxHeartbeat, cancel := context.WithCancel(ctx)
	inicio := time.Now()
	log := func() {
		Info(
			componente,
			"heartbeat etapa=%s elapsed=%s projeto=%s progresso=%s status=%s",
			valorHeartbeat(etapa, "unknown"),
			formatarDuracaoHeartbeat(time.Since(inicio)),
			valorHeartbeat(projeto, "all"),
			progresso.String(),
			valorHeartbeat(status, "running"),
		)
	}
	log()
	go func() {
		ticker := time.NewTicker(intervalo)
		defer ticker.Stop()
		for {
			select {
			case <-ctxHeartbeat.Done():
				return
			case <-ticker.C:
				log()
			}
		}
	}()
	return cancel
}

func valorHeartbeat(valor, fallback string) string {
	valor = strings.TrimSpace(valor)
	if valor == "" {
		return fallback
	}
	return strings.ReplaceAll(valor, " ", "_")
}

func formatarDuracaoHeartbeat(d time.Duration) string {
	if d < time.Second {
		return d.Truncate(time.Millisecond).String()
	}
	return d.Truncate(time.Second).String()
}

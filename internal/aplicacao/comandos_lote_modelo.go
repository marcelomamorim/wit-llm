package aplicacao

import (
	"context"
	"encoding/json"
	"flag"
	"fmt"
	"os"
	"os/signal"
	"time"

	"github.com/marceloamorim/witup-llm/internal/configuracao"
	"github.com/marceloamorim/witup-llm/internal/llm"
)

func executarLoteModelo(args []string) int {
	fs := flag.NewFlagSet("executar-lote-modelo", flag.ContinueOnError)
	config := fs.String("config", "", "Configuração JSON")
	model := fs.String("model", "", "Chave do modelo")
	input := fs.String("requests", "", "JSONL produzido pela preparação")
	output := fs.String("output", "", "JSONL de respostas/checkpoints")
	limit := fs.Int("limit", 0, "Máximo de novas chamadas nesta execução (0 = todas)")
	interval := fs.Duration("interval", time.Second, "Intervalo mínimo entre chamadas")
	complete := fs.Bool("require-complete", false, "Exige que todas as respostas já tenham sucesso; usar com --dry-run")
	dry := fs.Bool("dry-run", false, "Valida o lote sem acessar APIs nem exigir chave")
	if fs.Parse(args) != nil {
		return 2
	}
	if *complete && !*dry {
		fmt.Fprintln(os.Stderr, "--require-complete exige --dry-run")
		return 2
	}
	if *config == "" || *model == "" || *input == "" || *output == "" {
		fmt.Fprintln(os.Stderr, "erro: --config, --model, --requests e --output são obrigatórios")
		return 2
	}
	cfg, err := configuracao.CarregarParaBatch(*config)
	if err != nil {
		fmt.Fprintln(os.Stderr, err)
		return 1
	}
	m, ok := cfg.Modelos[*model]
	if !ok {
		fmt.Fprintln(os.Stderr, "modelo não encontrado:", *model)
		return 2
	}
	ctx, stop := signal.NotifyContext(context.Background(), os.Interrupt)
	defer stop()
	summary, err := llm.NovoCliente().ExecutarLoteLocal(ctx, m, *input, *output, llm.OpcoesLote{Intervalo: *interval, Limite: *limit, DryRun: *dry, Progresso: func(s string) { fmt.Fprintln(os.Stderr, s) }})
	if err == nil && *complete && summary.Reutilizados != summary.Total {
		err = fmt.Errorf("lote incompleto: %d de %d respostas válidas", summary.Reutilizados, summary.Total)
	}
	_ = json.NewEncoder(os.Stdout).Encode(summary)
	if err != nil {
		fmt.Fprintln(os.Stderr, err)
		return 1
	}
	return 0
}

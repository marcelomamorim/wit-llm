package aplicacao

import (
	"context"
	"flag"
	"fmt"
	"os"

	"github.com/marceloamorim/witup-llm/internal/registro"
)

func executarConsolidacaoEstatisticasPrimeiraRodada(args []string) int {
	fs := flag.NewFlagSet("consolidar-estatisticas-primeira-rodada", flag.ContinueOnError)
	manifesto := fs.String("manifest", "", "Caminho para statistical-manifest.csv")
	resumo := fs.String("summary", "", "Caminho para phase-two-summary.csv")
	metricas := fs.String("metrics", "", "Caminho para phase-two-metrics.csv")
	comparacao := fs.String("comparison", "", "Caminho para phase-two-comparison.csv")
	saida := fs.String("output-dir", "", "Diretório onde phase-two-statistics.csv/md serão escritos")
	if err := fs.Parse(args); err != nil {
		return 2
	}
	if *manifesto == "" || *resumo == "" || *metricas == "" || *comparacao == "" || *saida == "" {
		fmt.Fprintln(os.Stderr, "erro: --manifest, --summary, --metrics, --comparison e --output-dir são obrigatórios")
		return 2
	}
	ctxHeartbeat, cancelCtxHeartbeat := context.WithCancel(context.Background())
	progressoHeartbeat := registro.NovoProgresso(1)
	cancelHeartbeat := registro.IniciarHeartbeat(ctxHeartbeat, "estatisticas", "statistical_consolidation", "all", "running", progressoHeartbeat)
	defer cancelHeartbeat()
	defer cancelCtxHeartbeat()

	csvPath, mdPath, err := consolidarEstatisticasPrimeiraRodada(caminhoEstatisticasPrimeiraRodada{
		Manifesto:  *manifesto,
		Resumo:     *resumo,
		Metricas:   *metricas,
		Comparacao: *comparacao,
		Saida:      *saida,
	})
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}
	progressoHeartbeat.Incrementar()
	fmt.Printf("Estatísticas CSV      : %s\n", csvPath)
	fmt.Printf("Resumo estatístico MD : %s\n", mdPath)
	return 0
}

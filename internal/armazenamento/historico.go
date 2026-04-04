package armazenamento

import (
	"fmt"
	"os"
	"path/filepath"
	"strings"
)

// ResumoHistoricoParquet descreve os arquivos Parquet gerados para uma execução.
type ResumoHistoricoParquet struct {
	Diretorio       string
	ArquivosGerados []string
}

// ExportarHistoricoExecucaoParquet materializa snapshots analíticos em Parquet
// para facilitar comparação longitudinal e arquivamento fora do DuckDB.
func (b *BancoDuckDB) ExportarHistoricoExecucaoParquet(idExecucao, chaveProjeto, diretorioSaida string) (ResumoHistoricoParquet, error) {
	if strings.TrimSpace(idExecucao) == "" {
		return ResumoHistoricoParquet{}, fmt.Errorf("o id da execução é obrigatório para exportar o histórico")
	}
	if strings.TrimSpace(diretorioSaida) == "" {
		return ResumoHistoricoParquet{}, fmt.Errorf("o diretório de saída do histórico é obrigatório")
	}
	if err := os.MkdirAll(diretorioSaida, 0o755); err != nil {
		return ResumoHistoricoParquet{}, fmt.Errorf("ao criar diretório de histórico %q: %w", diretorioSaida, err)
	}

	idEscapado := escaparLiteralSQL(idExecucao)
	projetoEscapado := escaparLiteralSQL(chaveProjeto)

	especificacoes := []struct {
		nomeArquivo string
		consulta    string
	}{
		{
			nomeArquivo: "artefatos_execucao.parquet",
			consulta:    fmt.Sprintf("SELECT * FROM artefatos_execucao WHERE id_execucao = '%s'", idEscapado),
		},
		{
			nomeArquivo: "comparacao_fontes_resumo.parquet",
			consulta:    fmt.Sprintf("SELECT * FROM vw_comparacao_fontes_resumo WHERE id_execucao = '%s'", idEscapado),
		},
		{
			nomeArquivo: "comparacao_fontes_metodo.parquet",
			consulta:    fmt.Sprintf("SELECT * FROM vw_comparacao_fontes_metodo WHERE id_execucao = '%s'", idEscapado),
		},
		{
			nomeArquivo: "h1_maybe_recuperacao.parquet",
			consulta:    fmt.Sprintf("SELECT * FROM vw_h1_maybe_recuperacao WHERE id_execucao = '%s'", idEscapado),
		},
		{
			nomeArquivo: "h2_relacoes_estruturais.parquet",
			consulta:    fmt.Sprintf("SELECT * FROM vw_h2_relacoes_estruturais WHERE id_execucao = '%s'", idEscapado),
		},
		{
			nomeArquivo: "estudos_completos.parquet",
			consulta:    fmt.Sprintf("SELECT * FROM vw_estudos_completos WHERE id_execucao = '%s'", idEscapado),
		},
		{
			nomeArquivo: "estudo_variantes.parquet",
			consulta:    fmt.Sprintf("SELECT * FROM vw_estudo_variantes WHERE id_execucao = '%s'", idEscapado),
		},
		{
			nomeArquivo: "h3_metricas_variantes.parquet",
			consulta:    fmt.Sprintf("SELECT * FROM vw_h3_metricas_variantes WHERE id_execucao = '%s'", idEscapado),
		},
		{
			nomeArquivo: "h3_comparacao_suites.parquet",
			consulta:    fmt.Sprintf("SELECT * FROM vw_h3_comparacao_suites WHERE id_execucao = '%s'", idEscapado),
		},
		{
			nomeArquivo: "h4_divergencias_base.parquet",
			consulta:    fmt.Sprintf("SELECT * FROM vw_h4_divergencias_base WHERE id_execucao = '%s'", idEscapado),
		},
	}
	if strings.TrimSpace(chaveProjeto) != "" {
		especificacoes = append(especificacoes, struct {
			nomeArquivo string
			consulta    string
		}{
			nomeArquivo: "baseline_witup.parquet",
			consulta:    fmt.Sprintf("SELECT * FROM vw_baselines_witup WHERE chave_projeto = '%s'", projetoEscapado),
		})
	}

	arquivos := make([]string, 0, len(especificacoes))
	for _, especificacao := range especificacoes {
		caminhoArquivo := filepath.Join(diretorioSaida, especificacao.nomeArquivo)
		if err := os.Remove(caminhoArquivo); err != nil && !os.IsNotExist(err) {
			return ResumoHistoricoParquet{}, fmt.Errorf("ao limpar histórico anterior %q: %w", caminhoArquivo, err)
		}
		if err := b.copiarConsultaParaParquet(especificacao.consulta, caminhoArquivo); err != nil {
			return ResumoHistoricoParquet{}, err
		}
		arquivos = append(arquivos, caminhoArquivo)
	}

	return ResumoHistoricoParquet{
		Diretorio:       diretorioSaida,
		ArquivosGerados: arquivos,
	}, nil
}

func (b *BancoDuckDB) copiarConsultaParaParquet(consulta, caminhoArquivo string) error {
	comando := fmt.Sprintf(
		"COPY (%s) TO '%s' (FORMAT PARQUET)",
		strings.TrimSpace(consulta),
		escaparLiteralSQL(caminhoArquivo),
	)
	if _, err := b.db.Exec(comando); err != nil {
		return fmt.Errorf("ao exportar histórico Parquet %q: %w", caminhoArquivo, err)
	}
	return nil
}

package aplicacao

import (
	"path/filepath"
	"sort"

	"github.com/marceloamorim/witup-llm/internal/artefatos"
	"github.com/marceloamorim/witup-llm/internal/dominio"
)

// ExecutarBenchmark executa cenários e persiste os artefatos de ranqueamento.
func (s *Servico) ExecutarBenchmark(cfg *dominio.ConfigAplicacao, scenarios []dominio.CenarioBenchmark, judgeModelKey string) (dominio.RelatorioBenchmark, string, error) {
	workspace, err := artefatos.NovoEspacoTrabalho(cfg.Fluxo.DiretorioSaida, artefatos.NovoIDExecucao("benchmark"))
	if err != nil {
		return dominio.RelatorioBenchmark{}, "", err
	}

	entries := make([]dominio.EntradaBenchmark, 0, len(scenarios))
	for _, sc := range scenarios {
		subWorkspace, err := artefatos.NovoEspacoTrabalho(workspace.Raiz, artefatos.Slugificar(sc.ChaveModeloAnalise+"-to-"+sc.ChaveModeloGeracao))
		if err != nil {
			return dominio.RelatorioBenchmark{}, "", err
		}
		analysisReport, analysisPath, _, err := s.Analisar(cfg, sc.ChaveModeloAnalise, subWorkspace)
		if err != nil {
			return dominio.RelatorioBenchmark{}, "", err
		}
		generationReport, generationPath, _, err := s.Gerar(cfg, analysisReport, analysisPath, sc.ChaveModeloGeracao, subWorkspace)
		if err != nil {
			return dominio.RelatorioBenchmark{}, "", err
		}
		evaluationReport, evaluationPath, _, err := s.Avaliar(cfg, analysisReport, analysisPath, generationReport, generationPath, judgeModelKey, subWorkspace)
		if err != nil {
			return dominio.RelatorioBenchmark{}, "", err
		}
		var judgeScore *float64
		if evaluationReport.AvaliacaoJuiz != nil {
			judgeScore = &evaluationReport.AvaliacaoJuiz.Nota
		}
		entries = append(entries, dominio.EntradaBenchmark{
			ChaveModeloAnalise: sc.ChaveModeloAnalise,
			ChaveModeloGeracao: sc.ChaveModeloGeracao,
			CaminhoAvaliacao:   evaluationPath,
			NotaMetricas:       evaluationReport.NotaMetricas,
			JudgeScore:         judgeScore,
			NotaCombinada:      evaluationReport.NotaCombinada,
		})
	}

	sort.Slice(entries, func(i, j int) bool {
		return chaveOrdenacaoNota(entries[i].NotaCombinada, entries[i].NotaMetricas, entries[i].JudgeScore) >
			chaveOrdenacaoNota(entries[j].NotaCombinada, entries[j].NotaMetricas, entries[j].JudgeScore)
	})
	for i := range entries {
		entries[i].Posicao = i + 1
	}

	report := dominio.RelatorioBenchmark{
		IDExecucao:      filepath.Base(workspace.Raiz),
		GeradoEm:        dominio.HorarioUTC(),
		ChaveModeloJuiz: judgeModelKey,
		Entradas:        entries,
	}
	benchmarkPath := filepath.Join(workspace.Raiz, "benchmark.json")
	if err := artefatos.EscreverJSON(benchmarkPath, report); err != nil {
		return dominio.RelatorioBenchmark{}, "", err
	}
	if err := artefatos.EscreverTexto(filepath.Join(workspace.Raiz, "benchmark.md"), construirMarkdownBenchmark(entries)); err != nil {
		return dominio.RelatorioBenchmark{}, "", err
	}
	if err := registrarArtefatoNoBanco(cfg, report.IDExecucao, "benchmark", "", "", benchmarkPath, report.GeradoEm, report); err != nil {
		return dominio.RelatorioBenchmark{}, "", err
	}
	return report, benchmarkPath, nil
}

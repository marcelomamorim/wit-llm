package aplicacao

// helpers_batch_segunda_fase.go — funções auxiliares compartilhadas pelo
// pipeline Batch da segunda fase (PrepararBatchGeracaoSegundaFase e
// AvaliarBatchSegundaFase). Extraídas do servico_segunda_fase.go original
// que foi removido junto com a execução local (não-Batch).

import (
	"strings"

	"github.com/marceloamorim/witup-llm/internal/dominio"
)

// clonarConfiguracaoParaProjetoSegundaFase cria uma ConfigAplicacao derivada da
// config global, sobrescrevendo as configurações de projeto com os dados
// específicos do projeto da segunda fase.
func clonarConfiguracaoParaProjetoSegundaFase(cfg *dominio.ConfigAplicacao, projeto dominio.ConfigProjetoSegundaFase) *dominio.ConfigAplicacao {
	clone := *cfg
	clone.Projeto = dominio.ConfigProjeto{
		Raiz:          projeto.Raiz,
		Include:       projeto.Include,
		Exclude:       projeto.Exclude,
		OverviewFile:  projeto.OverviewFile,
		TestFramework: projeto.TestFramework,
	}
	return &clone
}

// agruparMetodosPorContainer agrupa descritores de método pelo nome do container
// (classe Java) ao qual pertencem.
func agruparMetodosPorContainer(metodos []dominio.DescritorMetodo) map[string][]dominio.DescritorMetodo {
	grupos := make(map[string][]dominio.DescritorMetodo, len(metodos))
	for _, m := range metodos {
		grupos[m.NomeContainer] = append(grupos[m.NomeContainer], m)
	}
	return grupos
}

// dividirMetodosDiretosParaGeracao quebra uma lista de métodos em lotes menores
// para reduzir o contexto enviado ao modelo na geração direta.
func dividirMetodosDiretosParaGeracao(metodos []dominio.DescritorMetodo) [][]dominio.DescritorMetodo {
	if len(metodos) == 0 {
		return nil
	}
	lotes := make([][]dominio.DescritorMetodo, 0)
	loteAtual := make([]dominio.DescritorMetodo, 0, limiteMetodosPorLoteGeracao)
	for _, m := range metodos {
		if len(loteAtual) >= limiteMetodosPorLoteGeracao {
			lotes = append(lotes, loteAtual)
			loteAtual = make([]dominio.DescritorMetodo, 0, limiteMetodosPorLoteGeracao)
		}
		loteAtual = append(loteAtual, m)
	}
	if len(loteAtual) > 0 {
		lotes = append(lotes, loteAtual)
	}
	return lotes
}

// filtrarMetricasSegundaFase retorna as métricas aplicáveis à segunda fase.
// Mantém todas as métricas configuradas; pode ser restrita conforme necessidade.
func filtrarMetricasSegundaFase(metricas []dominio.ConfigMetrica) []dominio.ConfigMetrica {
	return metricas
}

// construirRelatorioAnaliseDireta monta um RelatorioAnalise vazio para o cenário
// de geração direta, onde não há análise WIT — apenas os descritores dos métodos.
func construirRelatorioAnaliseDireta(raiz, generationModelKey string, metodos []dominio.DescritorMetodo) dominio.RelatorioAnalise {
	analises := make([]dominio.AnaliseMetodo, 0, len(metodos))
	for _, m := range metodos {
		analises = append(analises, dominio.AnaliseMetodo{
			Metodo:          m,
			CaminhosExcecao: nil,
		})
	}
	return dominio.RelatorioAnalise{
		RaizProjeto:   raiz,
		ChaveModelo:   generationModelKey,
		TotalMetodos:  len(metodos),
		Analises:      analises,
	}
}

// valorMetricaPorNome retorna o valor normalizado de uma métrica pelo seu nome,
// ou nil se a métrica não foi encontrada ou não produziu valor.
func valorMetricaPorNome(resultados []dominio.ResultadoMetrica, nome string) *float64 {
	for _, r := range resultados {
		if strings.EqualFold(r.Nome, nome) {
			return r.NotaNormalizada
		}
	}
	return nil
}

// ── auditoriaCenarioSegundaFase ────────────────────────────────────────────────

// auditoriaCenarioSegundaFase acumula telemetria de LLM (requests, tokens, custo)
// ao longo das etapas de geração e avaliação de um cenário da segunda fase.
type auditoriaCenarioSegundaFase struct {
	RequestCount      int
	RepairUsed        bool
	InputTokens       int
	OutputTokens      int
	estimatedCost     *float64
	custoIndisponivel bool
}

func (a *auditoriaCenarioSegundaFase) acumular(requestCount, inputTokens, outputTokens int, estimatedCost *float64) {
	a.RequestCount += requestCount
	a.InputTokens += inputTokens
	a.OutputTokens += outputTokens
	if estimatedCost == nil {
		a.custoIndisponivel = true
	} else if !a.custoIndisponivel {
		if a.estimatedCost == nil {
			v := *estimatedCost
			a.estimatedCost = &v
		} else {
			*a.estimatedCost += *estimatedCost
		}
	}
}

func (a *auditoriaCenarioSegundaFase) acumularGeracao(r dominio.RelatorioGeracao) {
	a.acumular(r.RequestCount, r.InputTokens, r.OutputTokens, r.EstimatedCost)
}

func (a *auditoriaCenarioSegundaFase) acumularAvaliacao(r dominio.RelatorioAvaliacao) {
	a.acumular(r.RequestCount, r.InputTokens, r.OutputTokens, r.EstimatedCost)
}

func (a *auditoriaCenarioSegundaFase) custoEstimado() *float64 {
	if a.custoIndisponivel || a.estimatedCost == nil {
		return nil
	}
	v := *a.estimatedCost
	return &v
}

// ── Detalhamento de artefatos para auditoria ──────────────────────────────────

// detalharMetodosAlvoSegundaFase converte análises WIT nos descritores detalhados
// usados no artefato final da segunda fase.
func detalharMetodosAlvoSegundaFase(analises []dominio.AnaliseMetodo) []dominio.MetodoAlvoDetalhadoSegundaFase {
	out := make([]dominio.MetodoAlvoDetalhadoSegundaFase, 0, len(analises))
	for _, a := range analises {
		out = append(out, dominio.MetodoAlvoDetalhadoSegundaFase{
			IDMetodo:       a.Metodo.IDMetodo,
			CaminhoArquivo: a.Metodo.CaminhoArquivo,
			NomeContainer:  a.Metodo.NomeContainer,
			NomeMetodo:     a.Metodo.NomeMetodo,
			Assinatura:     a.Metodo.Assinatura,
			Origem:         a.Metodo.Origem,
		})
	}
	return out
}

// detalharArquivosTesteSegundaFase converte arquivos de teste gerados nos
// descritores detalhados do artefato da segunda fase.
func detalharArquivosTesteSegundaFase(arquivos []dominio.ArquivoTesteGerado) []dominio.ArquivoTesteDetalhadoSegundaFase {
	out := make([]dominio.ArquivoTesteDetalhadoSegundaFase, 0, len(arquivos))
	for _, f := range arquivos {
		out = append(out, dominio.ArquivoTesteDetalhadoSegundaFase{
			CaminhoRelativo:    f.CaminhoRelativo,
			Conteudo:           f.Conteudo,
			IDsMetodosCobertos: f.IDsMetodosCobertos,
			AcoesExpath:        f.AcoesExpath,
		})
	}
	return out
}

// construirParesMetodoTesteSegundaFase associa cada método-alvo aos arquivos de
// teste gerados que declaram cobri-lo (por IDsMetodosCobertos).
func construirParesMetodoTesteSegundaFase(
	metodos []dominio.MetodoAlvoDetalhadoSegundaFase,
	arquivos []dominio.ArquivoTesteDetalhadoSegundaFase,
) []dominio.ParMetodoTesteSegundaFase {
	// Índice inverso: método → arquivos
	idxArquivos := make(map[string][]dominio.ArquivoTesteDetalhadoSegundaFase, len(arquivos))
	for _, arq := range arquivos {
		for _, id := range arq.IDsMetodosCobertos {
			idxArquivos[id] = append(idxArquivos[id], arq)
		}
	}
	pares := make([]dominio.ParMetodoTesteSegundaFase, 0, len(metodos))
	for _, m := range metodos {
		testes := idxArquivos[m.IDMetodo]
		if testes == nil {
			testes = []dominio.ArquivoTesteDetalhadoSegundaFase{}
		}
		pares = append(pares, dominio.ParMetodoTesteSegundaFase{
			Metodo: m,
			Testes: testes,
		})
	}
	return pares
}

// modoExecucaoSegundaFase retorna o modo de execução registrado na configuração
// da segunda fase, ou o padrão estrito quando não configurado.
func modoExecucaoSegundaFase(cfg *dominio.ConfigAplicacao) string {
	modo := strings.TrimSpace(cfg.SegundaFase.ModoExecucao)
	if modo == "" {
		return dominio.ModoExecucaoSegundaFaseEstrito
	}
	return modo
}

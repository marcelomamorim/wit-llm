package visualizacao

import (
	"os"
	"path/filepath"
	"strings"
	"testing"

	"github.com/marceloamorim/witup-llm/internal/dominio"
)

func TestGeradorSegundaFaseGeraDashboardComProjetosEMetricas(t *testing.T) {
	gerador := NovoGeradorSegundaFase("Comparação Guava vs Commons")
	saida := filepath.Join(t.TempDir(), "dashboard.html")
	relatorio := dominio.RelatorioSegundaFase{
		IDExecucao:         "phase-two-run",
		GeradoEm:           "2026-04-11T20:00:00Z",
		ChaveModeloGeracao: "openai_main",
		Projetos: []dominio.ComparacaoProjetoSegundaFase{{
			Projeto:       "guava",
			RotuloProjeto: "Google Guava",
			ContextoWIT: dominio.ResultadoCenarioSegundaFase{
				Cenario:             dominio.CenarioSegundaFaseContextoWIT,
				NotaMetricas:        ponteiroFloatVisualizacao(71.5),
				ModoExecucao:        dominio.ModoExecucaoSegundaFaseReparo,
				RequestCount:        2,
				RepairUsed:          true,
				InputTokens:         140,
				OutputTokens:        60,
				EstimatedCost:       ponteiroFloatVisualizacao(0.02),
				IntervencoesHarness: []string{"sandbox_sanitized_pom"},
				MetodosAlvo: []dominio.MetodoAlvoDetalhadoSegundaFase{{
					IDMetodo:       "sample.Example:run:10",
					CaminhoArquivo: "src/main/java/sample/Example.java",
					NomeContainer:  "sample.Example",
					NomeMetodo:     "run",
					Assinatura:     "sample.Example.run()",
					Origem:         "public void run() {}",
				}},
				ArquivosTeste: []dominio.ArquivoTesteDetalhadoSegundaFase{{
					CaminhoRelativo:    "src/test/java/sample/ExampleTest.java",
					Conteudo:           "class ExampleTest {}",
					IDsMetodosCobertos: []string{"sample.Example:run:10"},
				}},
				ParesMetodoTeste: []dominio.ParMetodoTesteSegundaFase{{
					Metodo: dominio.MetodoAlvoDetalhadoSegundaFase{
						IDMetodo:       "sample.Example:run:10",
						CaminhoArquivo: "src/main/java/sample/Example.java",
						NomeContainer:  "sample.Example",
						NomeMetodo:     "run",
						Assinatura:     "sample.Example.run()",
						Origem:         "public void run() {}",
					},
					Testes: []dominio.ArquivoTesteDetalhadoSegundaFase{{
						CaminhoRelativo:    "src/test/java/sample/ExampleTest.java",
						Conteudo:           "class ExampleTest {}",
						IDsMetodosCobertos: []string{"sample.Example:run:10"},
					}},
				}},
				ResultadosMetricas: []dominio.ResultadoMetrica{
					{Nome: "unit-tests", NotaNormalizada: ponteiroFloatVisualizacao(18)},
					{Nome: "valid-java-rate", NotaNormalizada: ponteiroFloatVisualizacao(100)},
					{Nome: "target-invocation-rate", NotaNormalizada: ponteiroFloatVisualizacao(100)},
					{Nome: "jacoco-line", NotaNormalizada: ponteiroFloatVisualizacao(42)},
				},
			},
			GeracaoDireta: dominio.ResultadoCenarioSegundaFase{
				Cenario:       dominio.CenarioSegundaFaseDireto,
				NotaMetricas:  ponteiroFloatVisualizacao(55.2),
				ModoExecucao:  dominio.ModoExecucaoSegundaFaseEstrito,
				RequestCount:  1,
				RepairUsed:    false,
				InputTokens:   90,
				OutputTokens:  40,
				EstimatedCost: ponteiroFloatVisualizacao(0.01),
				MetodosAlvo: []dominio.MetodoAlvoDetalhadoSegundaFase{{
					IDMetodo:       "sample.Example:check:20",
					CaminhoArquivo: "src/main/java/sample/Example.java",
					NomeContainer:  "sample.Example",
					NomeMetodo:     "check",
					Assinatura:     "sample.Example.check()",
					Origem:         "public boolean check() { return true; }",
				}},
				ArquivosTeste: []dominio.ArquivoTesteDetalhadoSegundaFase{{
					CaminhoRelativo:    "src/test/java/sample/ExampleDirectTest.java",
					Conteudo:           "class ExampleDirectTest {}",
					IDsMetodosCobertos: []string{"sample.Example:check:20"},
				}},
				ParesMetodoTeste: []dominio.ParMetodoTesteSegundaFase{{
					Metodo: dominio.MetodoAlvoDetalhadoSegundaFase{
						IDMetodo:       "sample.Example:check:20",
						CaminhoArquivo: "src/main/java/sample/Example.java",
						NomeContainer:  "sample.Example",
						NomeMetodo:     "check",
						Assinatura:     "sample.Example.check()",
						Origem:         "public boolean check() { return true; }",
					},
					Testes: []dominio.ArquivoTesteDetalhadoSegundaFase{{
						CaminhoRelativo:    "src/test/java/sample/ExampleDirectTest.java",
						Conteudo:           "class ExampleDirectTest {}",
						IDsMetodosCobertos: []string{"sample.Example:check:20"},
					}},
				}},
				ResultadosMetricas: []dominio.ResultadoMetrica{
					{Nome: "test-compilation", Sucesso: false, SaidaErro: "cannot find symbol"},
					{Nome: "unit-tests", NotaNormalizada: ponteiroFloatVisualizacao(15)},
					{Nome: "valid-java-rate", NotaNormalizada: ponteiroFloatVisualizacao(50)},
					{Nome: "jacoco-line", NotaNormalizada: ponteiroFloatVisualizacao(30)},
				},
			},
		}},
	}

	caminho, err := gerador.Gerar(relatorio, saida)
	if err != nil {
		t.Fatalf("gerar dashboard: %v", err)
	}
	conteudo, err := os.ReadFile(caminho)
	if err != nil {
		t.Fatalf("ler dashboard: %v", err)
	}
	texto := string(conteudo)
	for _, trecho := range []string{"Google Guava", "WIT como contexto", "Método cru (sem WIT)", "Taxa de sucesso", "Cobertura dos métodos-alvo", "Java válido", "Invocação do alvo", "JaCoCo line", "Chamadas IA", "Repair usado", "Tokens de entrada", "Custo estimado", "Categoria da falha", "unknown_symbol", "Intervenções harness", "sandbox_sanitized_pom", "repair_1retry", "Ver classe de teste gerada e métodos-alvo", "Ver pares método testado + testes gerados", "src/test/java/sample/ExampleTest.java", "sample.Example.run()", "tok-keyword"} {
		if !strings.Contains(texto, trecho) {
			t.Fatalf("dashboard deveria conter %q", trecho)
		}
	}
}

func ponteiroFloatVisualizacao(v float64) *float64 {
	return &v
}

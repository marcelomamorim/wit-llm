package aplicacao

import (
	"testing"

	"github.com/marceloamorim/witup-llm/internal/dominio"
)

func TestConstruirRelatorioAnaliseDiretaPreservaMetodosAlvo(t *testing.T) {
	metodos := []dominio.DescritorMetodo{
		{
			IDMetodo:      "sample.Example:run:10",
			NomeContainer: "sample.Example",
			NomeMetodo:    "run",
			Assinatura:    "sample.Example.run(java.lang.String)",
		},
		{
			IDMetodo:      "sample.Example:check:20",
			NomeContainer: "sample.Example",
			NomeMetodo:    "check",
			Assinatura:    "sample.Example.check(java.lang.String)",
		},
	}

	relatorio := construirRelatorioAnaliseDireta("/tmp/project", "openai_main", metodos)
	if relatorio.TotalMetodos != len(metodos) {
		t.Fatalf("esperava total_methods=%d, recebi %d", len(metodos), relatorio.TotalMetodos)
	}
	if len(relatorio.Analises) != len(metodos) {
		t.Fatalf("esperava %d análises, recebi %d", len(metodos), len(relatorio.Analises))
	}
	for indice, analise := range relatorio.Analises {
		if analise.Metodo.IDMetodo != metodos[indice].IDMetodo {
			t.Fatalf("método-alvo inesperado na análise %d: %s", indice, analise.Metodo.IDMetodo)
		}
		if analise.ResumoMetodo == "" {
			t.Fatalf("esperava resumo do método na análise %d", indice)
		}
		if len(analise.CaminhosExcecao) != 0 {
			t.Fatalf("análise direta não deveria inventar expaths; recebi %d", len(analise.CaminhosExcecao))
		}
	}
}

func TestDetalharArtefatosSegundaFasePreservaMetodosEArquivos(t *testing.T) {
	analises := []dominio.AnaliseMetodo{{
		Metodo: dominio.DescritorMetodo{
			IDMetodo:       "sample.Example:run:10",
			CaminhoArquivo: "src/main/java/sample/Example.java",
			NomeContainer:  "sample.Example",
			NomeMetodo:     "run",
			Assinatura:     "sample.Example.run(java.lang.String)",
			Origem:         "public void run(String value) {}",
		},
	}}
	arquivos := []dominio.ArquivoTesteGerado{{
		CaminhoRelativo:    "src/test/java/sample/ExampleTest.java",
		Conteudo:           "class ExampleTest {}",
		IDsMetodosCobertos: []string{"sample.Example:run:10"},
		Observacoes:        "gerado com sucesso",
	}}

	metodosDetalhados := detalharMetodosAlvoSegundaFase(analises)
	if len(metodosDetalhados) != 1 || metodosDetalhados[0].Assinatura != "sample.Example.run(java.lang.String)" {
		t.Fatalf("detalhamento de métodos inesperado: %#v", metodosDetalhados)
	}

	arquivosDetalhados := detalharArquivosTesteSegundaFase(arquivos)
	if len(arquivosDetalhados) != 1 {
		t.Fatalf("detalhamento de arquivos inesperado: %#v", arquivosDetalhados)
	}
	if arquivosDetalhados[0].CaminhoRelativo != "src/test/java/sample/ExampleTest.java" || arquivosDetalhados[0].Observacoes != "gerado com sucesso" {
		t.Fatalf("arquivo detalhado inesperado: %#v", arquivosDetalhados[0])
	}

	pares := construirParesMetodoTesteSegundaFase(metodosDetalhados, arquivosDetalhados)
	if len(pares) != 1 {
		t.Fatalf("esperava 1 par método-teste, recebi %d", len(pares))
	}
	if pares[0].Metodo.IDMetodo != "sample.Example:run:10" {
		t.Fatalf("método do par inesperado: %#v", pares[0])
	}
	if len(pares[0].Testes) != 1 || pares[0].Testes[0].CaminhoRelativo != "src/test/java/sample/ExampleTest.java" {
		t.Fatalf("teste associado inesperado: %#v", pares[0].Testes)
	}
}

func TestDeveTentarReparoSuiteGeradaApenasParaFalhasCriticas(t *testing.T) {
	if !deveTentarReparoSuiteGerada([]dominio.ResultadoMetrica{{Nome: "unit-tests", Sucesso: false}}) {
		t.Fatalf("esperava reparo quando unit-tests falha")
	}
	if !deveTentarReparoSuiteGerada([]dominio.ResultadoMetrica{{Nome: "test-compilation", Sucesso: false}}) {
		t.Fatalf("esperava reparo quando test-compilation falha")
	}
	if deveTentarReparoSuiteGerada([]dominio.ResultadoMetrica{{Nome: "jacoco-line", Sucesso: false}}) {
		t.Fatalf("não deveria tentar reparo apenas por JaCoCo falhar")
	}
}

func TestReparoSuperaResultadoAtualQuandoPassaSuite(t *testing.T) {
	atual := dominio.RelatorioAvaliacao{
		NotaCombinada: ponteiroFloatAux(20),
		ResultadosMetricas: []dominio.ResultadoMetrica{
			{Nome: "test-compilation", Sucesso: true},
			{Nome: "unit-tests", Sucesso: false},
		},
	}
	reparado := dominio.RelatorioAvaliacao{
		NotaCombinada: ponteiroFloatAux(10),
		ResultadosMetricas: []dominio.ResultadoMetrica{
			{Nome: "test-compilation", Sucesso: true},
			{Nome: "unit-tests", Sucesso: true},
		},
	}
	if !reparoSuperaResultadoAtual(atual, reparado) {
		t.Fatalf("reparo deveria ser preferido quando faz a suíte passar")
	}
}

func TestReparoSuperaResultadoAtualPorNotaQuandoAmbosTemMesmoStatus(t *testing.T) {
	atual := dominio.RelatorioAvaliacao{
		NotaCombinada: ponteiroFloatAux(20),
		ResultadosMetricas: []dominio.ResultadoMetrica{
			{Nome: "test-compilation", Sucesso: true},
			{Nome: "unit-tests", Sucesso: false},
		},
	}
	reparado := dominio.RelatorioAvaliacao{
		NotaCombinada: ponteiroFloatAux(25),
		ResultadosMetricas: []dominio.ResultadoMetrica{
			{Nome: "test-compilation", Sucesso: true},
			{Nome: "unit-tests", Sucesso: false},
		},
	}
	if !reparoSuperaResultadoAtual(atual, reparado) {
		t.Fatalf("reparo deveria ser preferido quando ambos são inválidos, mas o reparo tem nota melhor")
	}
}

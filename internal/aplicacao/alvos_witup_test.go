package aplicacao

import (
	"testing"

	"github.com/marceloamorim/witup-llm/internal/dominio"
)

func TestAlinharWITUPAoCatalogoUsaArquivoNomeELinha(t *testing.T) {
	relatorioWITUP := dominio.RelatorioAnalise{
		Analises: []dominio.AnaliseMetodo{{
			Metodo: dominio.DescritorMetodo{
				IDMetodo:       "sample.Example.run(java.lang.String)",
				CaminhoArquivo: "src/main/java/sample/Example.java",
				NomeContainer:  "sample.Example",
				NomeMetodo:     "run",
				Assinatura:     "sample.Example.run(java.lang.String)",
				LinhaInicial:   17,
			},
			CaminhosExcecao: []dominio.CaminhoExcecao{{
				IDCaminho:   "p1",
				TipoExcecao: "IllegalArgumentException",
			}},
			RespostaBruta: map[string]interface{}{"baseline": "witup"},
		}},
	}
	metodosCatalogados := []dominio.DescritorMetodo{
		{
			IDMetodo:       "sample.Example.run(String value):17",
			CaminhoArquivo: "src/main/java/sample/Example.java",
			NomeContainer:  "sample.Example",
			NomeMetodo:     "run",
			Assinatura:     "sample.Example.run(String value)",
			LinhaInicial:   17,
			Origem:         "void run(String value) { throw new IllegalArgumentException(); }",
		},
		{
			IDMetodo:       "sample.Example.run(String other):99",
			CaminhoArquivo: "src/main/java/sample/Example.java",
			NomeContainer:  "sample.Example",
			NomeMetodo:     "run",
			Assinatura:     "sample.Example.run(String other)",
			LinhaInicial:   99,
			Origem:         "void run(String other) { }",
		},
	}

	relatorioAlinhado, metodosAlvo, resumo := alinharWITUPAoCatalogo(relatorioWITUP, metodosCatalogados, 0)
	if resumo.QuantidadeCorrespondidos != 1 || resumo.QuantidadeNaoEncontrados != 0 {
		t.Fatalf("resumo inesperado: %#v", resumo)
	}
	if len(metodosAlvo) != 1 {
		t.Fatalf("esperava um método-alvo, recebi %d", len(metodosAlvo))
	}
	if relatorioAlinhado.Analises[0].Metodo.IDMetodo != "sample.Example.run(String value):17" {
		t.Fatalf("método alinhado incorreto: %#v", relatorioAlinhado.Analises[0].Metodo)
	}
	if relatorioAlinhado.Analises[0].Metodo.Origem == "" {
		t.Fatalf("esperava o método WITUP enriquecido com o código-fonte do catálogo")
	}
}

package aplicacao

import (
	"strings"
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

func TestAlinharWITUPAoCatalogoDescartaExpathContraditoPeloCheckoutAtual(t *testing.T) {
	relatorioWITUP := dominio.RelatorioAnalise{
		Analises: []dominio.AnaliseMetodo{{
			Metodo: dominio.DescritorMetodo{
				IDMetodo:       "org.apache.commons.io.IOCase.checkEquals(java.lang.String, java.lang.String)",
				CaminhoArquivo: "src/main/java/org/apache/commons/io/IOCase.java",
				NomeContainer:  "org.apache.commons.io.IOCase",
				NomeMetodo:     "checkEquals",
				Assinatura:     "org.apache.commons.io.IOCase.checkEquals(java.lang.String, java.lang.String)",
				LinhaInicial:   161,
			},
			ResumoMetodo: "Importado do baseline WIT.",
			CaminhosExcecao: []dominio.CaminhoExcecao{{
				IDCaminho:       "path-1",
				TipoExcecao:     "NullPointerException",
				Gatilho:         "null == str1 || null == str2",
				CondicoesGuarda: []string{"null == str1 || null == str2"},
			}},
			RespostaBruta: map[string]interface{}{"baseline": "witup"},
		}},
	}
	metodosCatalogados := []dominio.DescritorMetodo{{
		IDMetodo:       "org.apache.commons.io.IOCase:checkEquals:172",
		CaminhoArquivo: "src/main/java/org/apache/commons/io/IOCase.java",
		NomeContainer:  "org.apache.commons.io.IOCase",
		NomeMetodo:     "checkEquals",
		Assinatura:     "org.apache.commons.io.IOCase.checkEquals(final String str1, final String str2)",
		LinhaInicial:   172,
		Origem:         "public boolean checkEquals(final String str1, final String str2) { return str1 == str2 || str1 != null && str1.equals(str2); }",
	}}

	relatorioAlinhado, _, _ := alinharWITUPAoCatalogo(relatorioWITUP, metodosCatalogados, 0)
	if len(relatorioAlinhado.Analises) != 1 {
		t.Fatalf("esperava uma análise alinhada, recebi %d", len(relatorioAlinhado.Analises))
	}
	if len(relatorioAlinhado.Analises[0].CaminhosExcecao) != 0 {
		t.Fatalf("o expath incompatível deveria ser descartado: %#v", relatorioAlinhado.Analises[0].CaminhosExcecao)
	}
	if !strings.Contains(relatorioAlinhado.Analises[0].ResumoMetodo, "descartados") {
		t.Fatalf("resumo deveria registrar descarte de expath: %q", relatorioAlinhado.Analises[0].ResumoMetodo)
	}
}

func TestAlinharWITUPAoCatalogoMantemExpathNuloQuandoCodigoAtualLanca(t *testing.T) {
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
				IDCaminho:       "p1",
				TipoExcecao:     "NullPointerException",
				Gatilho:         "value == null",
				CondicoesGuarda: []string{"value == null"},
			}},
		}},
	}
	metodosCatalogados := []dominio.DescritorMetodo{{
		IDMetodo:       "sample.Example.run(String value):17",
		CaminhoArquivo: "src/main/java/sample/Example.java",
		NomeContainer:  "sample.Example",
		NomeMetodo:     "run",
		Assinatura:     "sample.Example.run(String value)",
		LinhaInicial:   17,
		Origem:         "void run(String value) { java.util.Objects.requireNonNull(value, \"value\"); }",
	}}

	relatorioAlinhado, _, _ := alinharWITUPAoCatalogo(relatorioWITUP, metodosCatalogados, 0)
	if len(relatorioAlinhado.Analises[0].CaminhosExcecao) != 1 {
		t.Fatalf("o expath suportado pelo código atual deveria ser mantido: %#v", relatorioAlinhado.Analises[0].CaminhosExcecao)
	}
}

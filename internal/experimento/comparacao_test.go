package experimento

import (
	"testing"

	"github.com/marceloamorim/witup-llm/internal/dominio"
)

func TestConstruirRelatorioComparacaoEVariantes(t *testing.T) {
	witupReport := dominio.RelatorioAnalise{
		Origem: dominio.OrigemExpathWITUP,
		Analises: []dominio.AnaliseMetodo{
			{
				Metodo: descritorMetodo("sample.Example.run(java.lang.String)", "src/main/java/sample/Example.java"),
				CaminhosExcecao: []dominio.CaminhoExcecao{
					caminhoExcecao("w1", "NullPointerException", "name == null", dominio.OrigemExpathWITUP),
					caminhoExcecao("w2", "IllegalArgumentException", "name.isBlank()", dominio.OrigemExpathWITUP),
				},
			},
		},
	}
	llmReport := dominio.RelatorioAnalise{
		Origem: dominio.OrigemExpathLLM,
		Analises: []dominio.AnaliseMetodo{
			{
				Metodo: descritorMetodo("sample.Example.run(java.lang.String)", "src/main/java/sample/Example.java"),
				CaminhosExcecao: []dominio.CaminhoExcecao{
					caminhoExcecao("l1", "NullPointerException", "name == null", dominio.OrigemExpathLLM),
					caminhoExcecao("l2", "IllegalStateException", "cache == null", dominio.OrigemExpathLLM),
				},
			},
			{
				Metodo: descritorMetodo("sample.Helper.validate()", "src/main/java/sample/Helper.java"),
				CaminhosExcecao: []dominio.CaminhoExcecao{
					caminhoExcecao("l3", "IllegalArgumentException", "count < 0", dominio.OrigemExpathLLM),
				},
			},
		},
	}

	comparison := ConstruirRelatorioComparacao("/tmp/witup.json", witupReport, "/tmp/llm.json", llmReport)
	if comparison.Resumo.MetodosEmAmbos != 1 {
		t.Fatalf("expected 1 shared method, got %d", comparison.Resumo.MetodosEmAmbos)
	}
	if comparison.Resumo.MetodosApenasLLM != 1 {
		t.Fatalf("expected 1 LLM-only method, got %d", comparison.Resumo.MetodosApenasLLM)
	}
	if comparison.Resumo.QuantidadeExpathsCompartilhados != 1 {
		t.Fatalf("expected 1 shared expath, got %d", comparison.Resumo.QuantidadeExpathsCompartilhados)
	}

	variants := ConstruirVariantes(witupReport, llmReport)
	merged := variants[dominio.VarianteWITUPMaisLLM]
	if merged.Origem != dominio.OrigemExpathCombinada {
		t.Fatalf("expected combined source, got %q", merged.Origem)
	}
	if len(merged.Analises) != 2 {
		t.Fatalf("expected 2 merged methods, got %d", len(merged.Analises))
	}
	if len(merged.Analises[0].CaminhosExcecao) != 3 {
		t.Fatalf("expected merged expaths to deduplicate shared entry, got %d", len(merged.Analises[0].CaminhosExcecao))
	}
}

func descritorMetodo(signature, filePath string) dominio.DescritorMetodo {
	return dominio.DescritorMetodo{
		IDMetodo:       signature,
		CaminhoArquivo: filePath,
		NomeContainer:  "sample.Example",
		NomeMetodo:     "run",
		Assinatura:     signature,
	}
}

func caminhoExcecao(id, exceptionType, trigger string, source dominio.OrigemExpath) dominio.CaminhoExcecao {
	return dominio.CaminhoExcecao{
		IDCaminho:       id,
		TipoExcecao:     exceptionType,
		Gatilho:         trigger,
		CondicoesGuarda: []string{trigger},
		Origem:          source,
	}
}

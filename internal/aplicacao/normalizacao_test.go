package aplicacao

import (
	"testing"

	"github.com/marceloamorim/witup-llm/internal/dominio"
)

func TestNormalizarAnaliseMetodoLimpaSentinelaNilNoGatilho(t *testing.T) {
	method := dominio.DescritorMetodo{IDMetodo: "sample:method:1"}
	analysis := normalizarAnaliseMetodo(method, map[string]interface{}{
		"method_summary": "summary",
		"expaths": []interface{}{
			map[string]interface{}{
				"path_id":        "p1",
				"exception_type": "IllegalArgumentException",
				"trigger":        nil,
				"confidence":     0.8,
			},
		},
	})
	if len(analysis.CaminhosExcecao) != 1 {
		t.Fatalf("expected one expath, got %d", len(analysis.CaminhosExcecao))
	}
	if analysis.CaminhosExcecao[0].Gatilho != "" {
		t.Fatalf("expected empty trigger when source value is nil, got %q", analysis.CaminhosExcecao[0].Gatilho)
	}
}

func TestNormalizarRespostaGeracaoIgnoraConteudoNil(t *testing.T) {
	summary, files := normalizarRespostaGeracao(map[string]interface{}{
		"strategy_summary": "summary",
		"files": []interface{}{
			map[string]interface{}{
				"relative_path": "src/test/java/sample/Test.java",
				"content":       nil,
			},
			map[string]interface{}{
				"relative_path": "src/test/java/sample/RealTest.java",
				"content":       "class RealTest {}",
			},
		},
	})
	if summary != "summary" {
		t.Fatalf("unexpected summary: %q", summary)
	}
	if len(files) != 1 {
		t.Fatalf("expected only one valid generated file, got %d", len(files))
	}
	if files[0].CaminhoRelativo != "src/test/java/sample/RealTest.java" {
		t.Fatalf("unexpected file kept: %#v", files[0])
	}
}

package witup

import (
	"path/filepath"
	"testing"

	"github.com/marceloamorim/witup-llm/internal/artefatos"
	"github.com/marceloamorim/witup-llm/internal/dominio"
)

func TestCarregarAnaliseAgrupaMetodosENormalizaExpaths(t *testing.T) {
	tempDir := t.TempDir()
	baselinePath := filepath.Join(tempDir, "wit.json")
	payload := map[string]interface{}{
		"path":       "C:\\wit-projects\\sample\\",
		"commitHash": "abc123",
		"classes": []interface{}{
			map[string]interface{}{
				"path": "C:\\wit-projects\\sample\\src\\main\\java\\sample\\Example.java",
				"methods": []interface{}{
					map[string]interface{}{
						"qualifiedSignature":        "sample.Example.run(java.lang.String)",
						"exception":                 "throw new NullPointerException(\"name must not be null\");",
						"pathCojunction":            "(name == null)",
						"simplifiedPathConjunction": "name == null",
						"soundSymbolic":             true,
						"soundBackwards":            true,
						"maybe":                     false,
						"line":                      10,
						"throwingLine":              11,
						"callSequence":              []interface{}{"sample.Example.run(java.lang.String)"},
					},
					map[string]interface{}{
						"qualifiedSignature":        "sample.Example.run(java.lang.String)",
						"exception":                 "throw new IllegalArgumentException(\"bad value\");",
						"pathCojunction":            "(name.isBlank())",
						"simplifiedPathConjunction": "name.isBlank()",
						"soundSymbolic":             true,
						"soundBackwards":            false,
						"maybe":                     true,
						"line":                      20,
						"throwingLine":              21,
						"callSequence":              []interface{}{"sample.Example.run(java.lang.String)"},
					},
				},
			},
		},
	}
	if err := artefatos.EscreverJSON(baselinePath, payload); err != nil {
		t.Fatalf("write baseline fixture: %v", err)
	}

	report, err := CarregarAnalise(baselinePath)
	if err != nil {
		t.Fatalf("CarregarAnalise returned unexpected error: %v", err)
	}

	if report.Origem != dominio.OrigemExpathWITUP {
		t.Fatalf("expected WITUP source, got %q", report.Origem)
	}
	if report.TotalMetodos != 1 {
		t.Fatalf("expected 1 grouped method, got %d", report.TotalMetodos)
	}
	if len(report.Analises) != 1 {
		t.Fatalf("expected one method analysis, got %d", len(report.Analises))
	}
	analysis := report.Analises[0]
	if analysis.Metodo.CaminhoArquivo != "src/main/java/sample/Example.java" {
		t.Fatalf("unexpected normalized path: %s", analysis.Metodo.CaminhoArquivo)
	}
	if len(analysis.CaminhosExcecao) != 2 {
		t.Fatalf("expected 2 expaths, got %d", len(analysis.CaminhosExcecao))
	}
	if analysis.CaminhosExcecao[0].TipoExcecao != "NullPointerException" {
		t.Fatalf("unexpected exception type: %s", analysis.CaminhosExcecao[0].TipoExcecao)
	}
	if analysis.CaminhosExcecao[0].Origem != dominio.OrigemExpathWITUP {
		t.Fatalf("expected expath source to be WITUP, got %q", analysis.CaminhosExcecao[0].Origem)
	}
}

func TestNormalizarCaminhoArquivoTrataSegmentosWindowsSemSensibilidadeAMaiusculas(t *testing.T) {
	path := normalizarCaminhoArquivo(`C:\wit-projects\sample\SRC\MAIN\JAVA\sample\Example.java`)
	if path != "SRC/MAIN/JAVA/sample/Example.java" {
		t.Fatalf("unexpected normalized path: %q", path)
	}
}

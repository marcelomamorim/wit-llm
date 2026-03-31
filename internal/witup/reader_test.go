package witup

import (
	"path/filepath"
	"testing"

	"github.com/marceloamorim/witup-llm/internal/artifacts"
	"github.com/marceloamorim/witup-llm/internal/domain"
)

func TestLoadAnalysisGroupsMethodsAndNormalizesExpaths(t *testing.T) {
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
	if err := artifacts.WriteJSON(baselinePath, payload); err != nil {
		t.Fatalf("write baseline fixture: %v", err)
	}

	report, err := LoadAnalysis(baselinePath)
	if err != nil {
		t.Fatalf("LoadAnalysis returned unexpected error: %v", err)
	}

	if report.Source != domain.ExpathSourceWITUP {
		t.Fatalf("expected WITUP source, got %q", report.Source)
	}
	if report.TotalMethods != 1 {
		t.Fatalf("expected 1 grouped method, got %d", report.TotalMethods)
	}
	if len(report.Analyses) != 1 {
		t.Fatalf("expected one method analysis, got %d", len(report.Analyses))
	}
	analysis := report.Analyses[0]
	if analysis.Method.FilePath != "src/main/java/sample/Example.java" {
		t.Fatalf("unexpected normalized path: %s", analysis.Method.FilePath)
	}
	if len(analysis.Expaths) != 2 {
		t.Fatalf("expected 2 expaths, got %d", len(analysis.Expaths))
	}
	if analysis.Expaths[0].ExceptionType != "NullPointerException" {
		t.Fatalf("unexpected exception type: %s", analysis.Expaths[0].ExceptionType)
	}
	if analysis.Expaths[0].Source != domain.ExpathSourceWITUP {
		t.Fatalf("expected expath source to be WITUP, got %q", analysis.Expaths[0].Source)
	}
}

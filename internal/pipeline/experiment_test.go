package pipeline

import (
	"os"
	"path/filepath"
	"testing"

	"github.com/marceloamorim/witup-llm/internal/artifacts"
	"github.com/marceloamorim/witup-llm/internal/domain"
)

func TestRunExperimentProducesThreeVariantArtifacts(t *testing.T) {
	tempDir := t.TempDir()
	cfg := &domain.AppConfig{
		Project: domain.ProjectConfig{
			Root: tempDir,
		},
		Pipeline: domain.PipelineConfig{
			OutputDir:   filepath.Join(tempDir, "generated"),
			SavePrompts: true,
		},
		Models: map[string]domain.ModelConfig{
			"analysis": {Model: "gpt-5.4"},
		},
	}
	baselinePath := filepath.Join(tempDir, "wit.json")
	baselinePayload := map[string]interface{}{
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
						"line":                      10,
						"throwingLine":              11,
					},
				},
			},
		},
	}
	if err := artifacts.WriteJSON(baselinePath, baselinePayload); err != nil {
		t.Fatalf("write baseline fixture: %v", err)
	}

	service := NewServiceWithDependencies(
		&fakeCompletionClient{
			responses: []*CompletionResponse{
				{
					Payload: map[string]interface{}{
						"summary":          "Method validates the input",
						"method_summary":   "Method validates the input",
						"responsibilities": []interface{}{"validate input"},
						"input_risks":      []interface{}{"null input"},
						"exception_cues":   []interface{}{"throws when input is null"},
					},
					RawText: "{}",
				},
				{
					Payload: map[string]interface{}{
						"summary":                    "No external callees",
						"direct_dependencies":        []interface{}{},
						"callee_risks":               []interface{}{},
						"field_dependencies":         []interface{}{},
						"propagated_exception_clues": []interface{}{},
					},
					RawText: "{}",
				},
				{
					Payload: map[string]interface{}{
						"summary":        "One null-check path",
						"method_summary": "Throws for null input",
						"expaths": []interface{}{
							map[string]interface{}{
								"path_id":          "l1",
								"exception_type":   "NullPointerException",
								"trigger":          "name == null",
								"guard_conditions": []interface{}{"name == null"},
								"confidence":       0.9,
								"evidence":         []interface{}{"line 11"},
							},
						},
					},
					RawText: "{}",
				},
				{
					Payload: map[string]interface{}{
						"summary":        "Candidate is acceptable",
						"method_summary": "Throws for null input",
						"accepted_expaths": []interface{}{
							map[string]interface{}{
								"path_id":          "l1",
								"exception_type":   "NullPointerException",
								"trigger":          "name == null",
								"guard_conditions": []interface{}{"name == null"},
								"confidence":       0.9,
								"evidence":         []interface{}{"line 11"},
							},
						},
						"review_notes": []interface{}{"evidence is explicit"},
					},
					RawText: "{}",
				},
			},
		},
		fakeMetricRunner{},
		fakeCatalogFactory{
			catalog: fakeCatalog{
				methods: []domain.MethodDescriptor{{
					MethodID:      "sample.Example.run(java.lang.String)",
					ContainerName: "sample.Example",
					MethodName:    "run",
					Signature:     "sample.Example.run(java.lang.String)",
					FilePath:      "src/main/java/sample/Example.java",
					Source:        "void run(String name) { if (name == null) { throw new NullPointerException(); } }",
				}},
				overview: "sample overview",
			},
		},
	)

	result, err := service.RunExperiment(cfg, baselinePath, "analysis")
	if err != nil {
		t.Fatalf("RunExperiment returned unexpected error: %v", err)
	}

	if len(result.VariantArtifacts) != 3 {
		t.Fatalf("expected 3 variant artifacts, got %d", len(result.VariantArtifacts))
	}
	if result.ComparisonReport.Summary.MethodsInBoth != 1 {
		t.Fatalf("expected one shared method, got %d", result.ComparisonReport.Summary.MethodsInBoth)
	}
	if _, err := LoadAnalysisReport(result.LLMAnalysisPath); err != nil {
		t.Fatalf("expected LLM analysis artifact to be readable: %v", err)
	}
	if _, err := os.Stat(result.TracePath); err != nil {
		t.Fatalf("expected trace artifact to be written: %v", err)
	}
}

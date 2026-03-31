package pipeline

import "testing"

func TestBuildBenchmarkScenariosCoupled(t *testing.T) {
	scenarios, err := BuildBenchmarkScenarios([]string{"a", "b", "a"}, nil, nil)
	if err != nil {
		t.Fatalf("unexpected error: %v", err)
	}
	if len(scenarios) != 2 {
		t.Fatalf("expected 2 scenarios, got %d", len(scenarios))
	}
	if scenarios[0].AnalysisModelKey != "a" || scenarios[0].GenerationModelKey != "a" {
		t.Fatalf("unexpected first scenario: %#v", scenarios[0])
	}
}

func TestBuildBenchmarkScenariosMatrix(t *testing.T) {
	scenarios, err := BuildBenchmarkScenarios(nil, []string{"x", "y"}, []string{"g1"})
	if err != nil {
		t.Fatalf("unexpected error: %v", err)
	}
	if len(scenarios) != 2 {
		t.Fatalf("expected 2 scenarios, got %d", len(scenarios))
	}
}

func TestBuildBenchmarkScenariosError(t *testing.T) {
	_, err := BuildBenchmarkScenarios(nil, []string{"x"}, nil)
	if err == nil {
		t.Fatalf("expected error for missing generation models")
	}
}

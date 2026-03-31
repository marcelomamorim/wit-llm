package pipeline

import (
	"fmt"
	"strings"

	"github.com/marceloamorim/witup-llm/internal/domain"
)

// BuildBenchmarkScenarios supports coupled (--model) and matrix modes.
func BuildBenchmarkScenarios(modelKeys, analysisModelKeys, generationModelKeys []string) ([]domain.BenchmarkScenario, error) {
	coupled := uniqueNonEmpty(modelKeys)
	analysis := uniqueNonEmpty(analysisModelKeys)
	generation := uniqueNonEmpty(generationModelKeys)

	if len(coupled) > 0 && (len(analysis) > 0 || len(generation) > 0) {
		return nil, fmt.Errorf("use either --model or (--analysis-model with --generation-model), not both")
	}

	if len(coupled) > 0 {
		scenarios := make([]domain.BenchmarkScenario, 0, len(coupled))
		for _, key := range coupled {
			scenarios = append(scenarios, domain.BenchmarkScenario{
				AnalysisModelKey:   key,
				GenerationModelKey: key,
			})
		}
		return scenarios, nil
	}

	if len(analysis) == 0 && len(generation) == 0 {
		return nil, fmt.Errorf("benchmark requires at least one --model or both --analysis-model and --generation-model")
	}
	if len(analysis) == 0 {
		return nil, fmt.Errorf("missing --analysis-model for benchmark matrix")
	}
	if len(generation) == 0 {
		return nil, fmt.Errorf("missing --generation-model for benchmark matrix")
	}

	scenarios := make([]domain.BenchmarkScenario, 0, len(analysis)*len(generation))
	for _, a := range analysis {
		for _, g := range generation {
			scenarios = append(scenarios, domain.BenchmarkScenario{
				AnalysisModelKey:   a,
				GenerationModelKey: g,
			})
		}
	}
	return scenarios, nil
}

func uniqueNonEmpty(values []string) []string {
	seen := map[string]bool{}
	out := make([]string, 0, len(values))
	for _, raw := range values {
		v := strings.TrimSpace(raw)
		if v == "" || seen[v] {
			continue
		}
		seen[v] = true
		out = append(out, v)
	}
	return out
}

package pipeline

import (
	"fmt"
	"os"
	"strings"

	"github.com/marceloamorim/witup-llm/internal/domain"
	"github.com/marceloamorim/witup-llm/internal/metrics"
)

func groupAnalysisByContainer(report domain.AnalysisReport) map[string][]domain.MethodAnalysis {
	groups := map[string][]domain.MethodAnalysis{}
	for _, analysis := range report.Analyses {
		container := analysis.Method.ContainerName
		groups[container] = append(groups[container], analysis)
	}
	return groups
}

func toStringList(raw interface{}) []string {
	if raw == nil {
		return nil
	}
	list, ok := raw.([]interface{})
	if !ok {
		return nil
	}
	out := make([]string, 0, len(list))
	for _, item := range list {
		value := strings.TrimSpace(fmt.Sprint(item))
		if value == "" || value == "<nil>" {
			continue
		}
		out = append(out, value)
	}
	return out
}

func parseFloat(value interface{}, fallback float64) float64 {
	raw := strings.TrimSpace(fmt.Sprint(value))
	if raw == "" || raw == "<nil>" {
		return fallback
	}

	var parsed float64
	if _, err := fmt.Sscanf(raw, "%f", &parsed); err != nil {
		return fallback
	}
	return parsed
}

func fallbackPathID(raw, methodID string, index int) string {
	value := strings.TrimSpace(raw)
	if value == "" || value == "<nil>" {
		return fmt.Sprintf("%s:%d", methodID, index)
	}
	return value
}

func scoreSortKey(combined, metric, judge *float64) float64 {
	if combined != nil {
		return *combined
	}
	if metric != nil {
		return *metric
	}
	if judge != nil {
		return *judge
	}
	return -1
}

func buildBenchmarkMarkdown(entries []domain.BenchmarkEntry) string {
	lines := []string{
		"# Benchmark Report",
		"",
		"| Rank | Scenario | Metric | Judge | Combined |",
		"| --- | --- | ---: | ---: | ---: |",
	}
	for _, entry := range entries {
		lines = append(lines, fmt.Sprintf("| %d | %s->%s | %s | %s | %s |",
			entry.Rank,
			entry.AnalysisModelKey,
			entry.GenerationModelKey,
			metrics.FormatScore(entry.MetricScore),
			metrics.FormatScore(entry.JudgeScore),
			metrics.FormatScore(entry.CombinedScore),
		))
	}
	lines = append(lines, "")
	return strings.Join(lines, "\n")
}

// EnsurePathsExist validates expected artifact files before loading.
func EnsurePathsExist(paths ...string) error {
	for _, path := range paths {
		info, err := os.Stat(path)
		if err != nil {
			return fmt.Errorf("required file %q: %w", path, err)
		}
		if info.IsDir() {
			return fmt.Errorf("required file %q is a directory", path)
		}
	}
	return nil
}

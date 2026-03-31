package metrics

import (
	"fmt"
	"os/exec"
	"path/filepath"
	"regexp"
	"strconv"
	"strings"

	"github.com/marceloamorim/witup-llm/internal/domain"
)

// RuntimeContext provides placeholders for metric command rendering.
type RuntimeContext struct {
	ProjectRoot    string
	RunDir         string
	TestsDir       string
	AnalysisPath   string
	GenerationPath string
	ModelKey       string
}

// Runner executes metrics command-by-command and parses numeric outputs.
type Runner struct{}

// NewRunner creates a metric runner instance.
func NewRunner() *Runner {
	return &Runner{}
}

// RunAll executes all configured metrics.
func (r *Runner) RunAll(metrics []domain.MetricConfig, ctx RuntimeContext) []domain.MetricResult {
	results := make([]domain.MetricResult, 0, len(metrics))
	for _, metric := range metrics {
		results = append(results, r.RunMetric(metric, ctx))
	}
	return results
}

// RunMetric executes one metric command in its configured working directory.
func (r *Runner) RunMetric(metric domain.MetricConfig, ctx RuntimeContext) domain.MetricResult {
	command := renderCommand(metric.Command, ctx)
	cwd := ctx.ProjectRoot
	if strings.TrimSpace(metric.WorkingDirectory) != "" {
		cwd = filepath.Clean(filepath.Join(ctx.ProjectRoot, metric.WorkingDirectory))
	}

	cmd := exec.Command("sh", "-c", command)
	cmd.Dir = cwd
	stdoutBytes, err := cmd.Output()
	stderr := ""
	exitCode := 0
	if err != nil {
		exitCode = 1
		if e, ok := err.(*exec.ExitError); ok {
			exitCode = e.ExitCode()
			stderr = string(e.Stderr)
		} else {
			stderr = err.Error()
		}
	}
	stdout := string(stdoutBytes)

	numeric := parseNumericValue(metric.ValueRegex, stdout, stderr)
	normalized := normalizeScore(numeric, metric.Scale)

	return domain.MetricResult{
		Name:            metric.Name,
		Kind:            metric.Kind,
		Command:         command,
		Success:         exitCode == 0,
		ExitCode:        exitCode,
		Stdout:          stdout,
		Stderr:          stderr,
		NumericValue:    numeric,
		NormalizedScore: normalized,
		Weight:          metric.Weight,
		Description:     metric.Description,
	}
}

// AggregateScore computes weighted average over normalized metric scores.
func AggregateScore(results []domain.MetricResult) *float64 {
	weightedTotal := 0.0
	weightSum := 0.0
	for _, result := range results {
		if result.NormalizedScore == nil {
			continue
		}
		weightedTotal += (*result.NormalizedScore) * result.Weight
		weightSum += result.Weight
	}
	if weightSum == 0 {
		return nil
	}
	out := weightedTotal / weightSum
	return &out
}

func renderCommand(template string, ctx RuntimeContext) string {
	replacer := strings.NewReplacer(
		"{project_root}", ctx.ProjectRoot,
		"{run_dir}", ctx.RunDir,
		"{tests_dir}", ctx.TestsDir,
		"{analysis_path}", ctx.AnalysisPath,
		"{generation_path}", ctx.GenerationPath,
		"{model_key}", ctx.ModelKey,
	)
	return replacer.Replace(template)
}

func parseNumericValue(valueRegex, stdout, stderr string) *float64 {
	if strings.TrimSpace(valueRegex) == "" {
		return nil
	}
	re, err := regexp.Compile(valueRegex)
	if err != nil {
		return nil
	}
	combined := stdout + "\n" + stderr
	m := re.FindStringSubmatch(combined)
	if len(m) < 2 {
		return nil
	}
	raw := strings.TrimSpace(strings.TrimSuffix(m[1], "%"))
	v, err := strconv.ParseFloat(raw, 64)
	if err != nil {
		return nil
	}
	return &v
}

func normalizeScore(value *float64, scale float64) *float64 {
	if value == nil || scale <= 0 {
		return nil
	}
	n := (*value / scale) * 100.0
	if n < 0 {
		n = 0
	}
	if n > 100 {
		n = 100
	}
	return &n
}

// CombineScores merges metric and judge score with 70/30 weighting when both exist.
func CombineScores(metricScore, judgeScore *float64) *float64 {
	switch {
	case metricScore != nil && judgeScore != nil:
		out := (*metricScore * 0.7) + (*judgeScore * 0.3)
		return &out
	case metricScore != nil:
		out := *metricScore
		return &out
	case judgeScore != nil:
		out := *judgeScore
		return &out
	default:
		return nil
	}
}

// FormatScore keeps output stable in CLI reports.
func FormatScore(value *float64) string {
	if value == nil {
		return "-"
	}
	return fmt.Sprintf("%.2f", *value)
}

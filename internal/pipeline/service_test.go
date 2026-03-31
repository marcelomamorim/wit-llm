package pipeline

import (
	"os"
	"path/filepath"
	"testing"

	"github.com/marceloamorim/witup-llm/internal/artifacts"
	"github.com/marceloamorim/witup-llm/internal/domain"
	"github.com/marceloamorim/witup-llm/internal/metrics"
)

type fakeCompletionClient struct {
	responses []*CompletionResponse
	index     int
}

func (f *fakeCompletionClient) CompleteJSON(domain.ModelConfig, string, string) (*CompletionResponse, error) {
	if f == nil || len(f.responses) == 0 {
		return &CompletionResponse{Payload: map[string]interface{}{}, RawText: "{}"}, nil
	}
	if f.index >= len(f.responses) {
		return &CompletionResponse{Payload: map[string]interface{}{}, RawText: "{}"}, nil
	}
	response := f.responses[f.index]
	f.index++
	return response, nil
}

type fakeMetricRunner struct {
	results []domain.MetricResult
}

func (f fakeMetricRunner) RunAll([]domain.MetricConfig, metrics.RuntimeContext) []domain.MetricResult {
	return f.results
}

type fakeCatalog struct {
	methods  []domain.MethodDescriptor
	overview string
}

func (f fakeCatalog) Catalog() ([]domain.MethodDescriptor, error) {
	return f.methods, nil
}

func (f fakeCatalog) LoadOverview() (string, error) {
	return f.overview, nil
}

type fakeCatalogFactory struct {
	catalog MethodCatalog
}

func (f fakeCatalogFactory) NewCatalog(domain.ProjectConfig) MethodCatalog {
	return f.catalog
}

func TestAnalyzeUsesInjectedAdapters(t *testing.T) {
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

	method := domain.MethodDescriptor{
		MethodID:      "sample:method:1",
		ContainerName: "sample.Container",
		MethodName:    "method",
		Signature:     "sample.Container.method()",
		Source:        "void method() { throw new IllegalArgumentException(); }",
	}
	service := NewServiceWithDependencies(
		&fakeCompletionClient{
			responses: []*CompletionResponse{{
				Payload: map[string]interface{}{
					"method_summary": "Raises when invalid",
					"expaths": []interface{}{
						map[string]interface{}{
							"path_id":          "p1",
							"exception_type":   "IllegalArgumentException",
							"trigger":          "invalid input",
							"guard_conditions": []interface{}{"arg < 0"},
							"confidence":       1.0,
							"evidence":         []interface{}{"line 12"},
						},
					},
				},
				RawText: `{"method_summary":"Raises when invalid"}`,
			}},
		},
		fakeMetricRunner{},
		fakeCatalogFactory{
			catalog: fakeCatalog{
				methods:  []domain.MethodDescriptor{method},
				overview: "project overview",
			},
		},
	)

	report, analysisPath, workspace, err := service.Analyze(cfg, "analysis", nil)
	if err != nil {
		t.Fatalf("Analyze returned unexpected error: %v", err)
	}
	if report.TotalMethods != 1 {
		t.Fatalf("expected 1 analyzed method, got %d", report.TotalMethods)
	}
	if len(report.Analyses) != 1 || len(report.Analyses[0].Expaths) != 1 {
		t.Fatalf("expected one normalized expath, got %#v", report.Analyses)
	}
	if _, err := os.Stat(analysisPath); err != nil {
		t.Fatalf("expected analysis artifact to be written: %v", err)
	}
	promptFile := filepath.Join(workspace.Prompts, "analysis-0001-sample-method-1.txt")
	if _, err := os.Stat(promptFile); err != nil {
		t.Fatalf("expected saved prompt artifact: %v", err)
	}
}

func TestGenerateWritesOnlySafeFiles(t *testing.T) {
	tempDir := t.TempDir()
	cfg := &domain.AppConfig{
		Project: domain.ProjectConfig{
			Root: tempDir,
		},
		Pipeline: domain.PipelineConfig{
			OutputDir: filepath.Join(tempDir, "generated"),
		},
		Models: map[string]domain.ModelConfig{
			"generator": {Model: "gpt-5.4"},
		},
	}

	analysis := domain.AnalysisReport{
		Analyses: []domain.MethodAnalysis{{
			Method: domain.MethodDescriptor{
				MethodID:      "sample:method:1",
				ContainerName: "sample.Container",
			},
			Expaths: []domain.ExceptionPath{{
				PathID:        "p1",
				ExceptionType: "IllegalArgumentException",
			}},
		}},
	}

	service := NewServiceWithDependencies(
		&fakeCompletionClient{
			responses: []*CompletionResponse{{
				Payload: map[string]interface{}{
					"strategy_summary": "One focused unit test",
					"files": []interface{}{
						map[string]interface{}{
							"relative_path":      "src/test/java/sample/ContainerTest.java",
							"content":            "class ContainerTest {}",
							"covered_method_ids": []interface{}{"sample:method:1"},
						},
					},
				},
				RawText: "{}",
			}},
		},
		fakeMetricRunner{},
		fakeCatalogFactory{catalog: fakeCatalog{overview: "project overview"}},
	)

	report, generationPath, workspace, err := service.Generate(cfg, analysis, "/tmp/analysis.json", "generator", nil)
	if err != nil {
		t.Fatalf("Generate returned unexpected error: %v", err)
	}
	if len(report.TestFiles) != 1 {
		t.Fatalf("expected one generated test file, got %d", len(report.TestFiles))
	}
	if _, err := os.Stat(generationPath); err != nil {
		t.Fatalf("expected generation report: %v", err)
	}
	generatedFile := filepath.Join(workspace.Tests, "src/test/java/sample/ContainerTest.java")
	if _, err := os.Stat(generatedFile); err != nil {
		t.Fatalf("expected generated test file to be written: %v", err)
	}
}

func TestEvaluateCombinesMetricsAndJudge(t *testing.T) {
	tempDir := t.TempDir()
	cfg := &domain.AppConfig{
		Project: domain.ProjectConfig{
			Root: tempDir,
		},
		Pipeline: domain.PipelineConfig{
			OutputDir:  filepath.Join(tempDir, "generated"),
			JudgeModel: "judge",
		},
		Models: map[string]domain.ModelConfig{
			"judge": {Model: "gpt-5.4"},
		},
		Metrics: []domain.MetricConfig{{Name: "coverage", Weight: 1.0}},
	}

	metricValue := 80.0
	service := NewServiceWithDependencies(
		&fakeCompletionClient{
			responses: []*CompletionResponse{{
				Payload: map[string]interface{}{
					"score":                    60.0,
					"verdict":                  "acceptable",
					"strengths":                []interface{}{"deterministic"},
					"weaknesses":               []interface{}{"missing diff tests"},
					"risks":                    []interface{}{"recall gap"},
					"recommended_next_actions": []interface{}{"compare against baseline"},
				},
				RawText: "{}",
			}},
		},
		fakeMetricRunner{
			results: []domain.MetricResult{{Name: "coverage", NormalizedScore: &metricValue, Weight: 1.0}},
		},
		fakeCatalogFactory{catalog: fakeCatalog{}},
	)

	workspace, err := artifacts.NewWorkspace(cfg.Pipeline.OutputDir, "evaluate-test")
	if err != nil {
		t.Fatalf("create workspace: %v", err)
	}

	report, evaluationPath, _, err := service.Evaluate(
		cfg,
		domain.AnalysisReport{},
		"/tmp/analysis.json",
		domain.GenerationReport{ModelKey: "generator"},
		"/tmp/generation.json",
		"judge",
		workspace,
	)
	if err != nil {
		t.Fatalf("Evaluate returned unexpected error: %v", err)
	}
	if report.MetricScore == nil || *report.MetricScore != 80.0 {
		t.Fatalf("expected metric score 80, got %v", report.MetricScore)
	}
	if report.CombinedScore == nil || *report.CombinedScore != 74.0 {
		t.Fatalf("expected combined score 74, got %v", report.CombinedScore)
	}
	if _, err := os.Stat(evaluationPath); err != nil {
		t.Fatalf("expected evaluation report: %v", err)
	}
}

func TestNormalizeMethodAnalysisSkipsInvalidEntries(t *testing.T) {
	method := domain.MethodDescriptor{MethodID: "sample:method:1"}
	report := normalizeMethodAnalysis(method, map[string]interface{}{
		"method_summary": "summary",
		"expaths": []interface{}{
			map[string]interface{}{"trigger": "missing exception type"},
			map[string]interface{}{
				"exception_type": "IllegalArgumentException",
				"confidence":     5.0,
			},
		},
	})

	if len(report.Expaths) != 1 {
		t.Fatalf("expected only one normalized expath, got %d", len(report.Expaths))
	}
	if report.Expaths[0].Confidence != 1.0 {
		t.Fatalf("expected confidence clamp to 1.0, got %f", report.Expaths[0].Confidence)
	}
}

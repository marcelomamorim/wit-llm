package pipeline

import (
	"fmt"
	"path/filepath"
	"sort"
	"strings"

	"github.com/marceloamorim/witup-llm/internal/agentic"
	"github.com/marceloamorim/witup-llm/internal/artifacts"
	"github.com/marceloamorim/witup-llm/internal/domain"
	"github.com/marceloamorim/witup-llm/internal/experiment"
	"github.com/marceloamorim/witup-llm/internal/llm"
	"github.com/marceloamorim/witup-llm/internal/metrics"
	"github.com/marceloamorim/witup-llm/internal/witup"
)

// Service orchestrates analysis, generation, evaluation and benchmark workflows.
type Service struct {
	completionClient CompletionClient
	metricRunner     MetricRunner
	catalogFactory   CatalogFactory
}

// RunResult stores all artifact paths and deserialized reports from run command.
type RunResult struct {
	Workspace        string
	AnalysisPath     string
	GenerationPath   string
	EvaluationPath   string
	AnalysisReport   domain.AnalysisReport
	GenerationReport domain.GenerationReport
	EvaluationReport domain.EvaluationReport
}

// ExperimentRunResult stores the primary artifacts for the source-comparison
// experiment.
type ExperimentRunResult struct {
	Workspace         string
	WITUPAnalysisPath string
	LLMAnalysisPath   string
	ComparisonPath    string
	TracePath         string
	VariantArtifacts  []domain.VariantArtifact
	ComparisonReport  domain.SourceComparisonReport
	ExperimentReport  domain.ExperimentReport
}

// NewService wires default infrastructure adapters.
func NewService(llmClient *llm.Client, metricRunner *metrics.Runner) *Service {
	return NewServiceWithDependencies(
		NewCompletionClient(llmClient),
		NewMetricRunner(metricRunner),
		defaultCatalogFactory{},
	)
}

// NewServiceWithDependencies keeps orchestration testable and adapter-agnostic.
func NewServiceWithDependencies(
	completionClient CompletionClient,
	metricRunner MetricRunner,
	catalogFactory CatalogFactory,
) *Service {
	if completionClient == nil {
		completionClient = NewCompletionClient(nil)
	}
	if metricRunner == nil {
		metricRunner = NewMetricRunner(nil)
	}
	if catalogFactory == nil {
		catalogFactory = defaultCatalogFactory{}
	}
	return &Service{
		completionClient: completionClient,
		metricRunner:     metricRunner,
		catalogFactory:   catalogFactory,
	}
}

// Analyze discovers methods and asks the configured model for exception paths.
func (s *Service) Analyze(cfg *domain.AppConfig, modelKey string, workspace *artifacts.Workspace) (domain.AnalysisReport, string, *artifacts.Workspace, error) {
	model, err := getModelOrError(cfg, modelKey)
	if err != nil {
		return domain.AnalysisReport{}, "", workspace, err
	}
	cataloger := s.catalogFactory.NewCatalog(cfg.Project)
	methods, err := cataloger.Catalog()
	if err != nil {
		return domain.AnalysisReport{}, "", workspace, err
	}
	if cfg.Pipeline.MaxMethods > 0 && len(methods) > cfg.Pipeline.MaxMethods {
		methods = methods[:cfg.Pipeline.MaxMethods]
	}
	overview, err := cataloger.LoadOverview()
	if err != nil {
		return domain.AnalysisReport{}, "", workspace, err
	}

	if workspace == nil {
		workspace, err = artifacts.NewWorkspace(cfg.Pipeline.OutputDir, artifacts.NewRunID("analyze-"+modelKey))
		if err != nil {
			return domain.AnalysisReport{}, "", workspace, err
		}
	}
	if err := artifacts.WriteJSON(filepath.Join(workspace.Root, "catalog.json"), methods); err != nil {
		return domain.AnalysisReport{}, "", workspace, err
	}

	analyses := make([]domain.MethodAnalysis, 0, len(methods))
	for i, method := range methods {
		systemPrompt := buildAnalysisSystemPrompt()
		userPrompt := buildAnalysisUserPrompt(overview, method)
		response, err := s.completionClient.CompleteJSON(model, systemPrompt, userPrompt)
		if err != nil {
			return domain.AnalysisReport{}, "", workspace, fmt.Errorf("analysis failed for %s: %w", method.Signature, err)
		}
		analysis := normalizeMethodAnalysis(method, response.Payload)
		analyses = append(analyses, analysis)

		if cfg.Pipeline.SavePrompts {
			stem := fmt.Sprintf("analysis-%04d-%s", i+1, artifacts.Slugify(method.MethodID))
			if err := artifacts.WriteText(filepath.Join(workspace.Prompts, stem+".txt"), userPrompt); err != nil {
				return domain.AnalysisReport{}, "", workspace, err
			}
			if err := artifacts.WriteText(filepath.Join(workspace.Responses, stem+".txt"), response.RawText); err != nil {
				return domain.AnalysisReport{}, "", workspace, err
			}
		}
	}

	report := domain.AnalysisReport{
		RunID:        filepath.Base(workspace.Root),
		ProjectRoot:  cfg.Project.Root,
		ModelKey:     modelKey,
		Source:       domain.ExpathSourceLLM,
		GeneratedAt:  domain.TimestampUTC(),
		TotalMethods: len(methods),
		Analyses:     analyses,
	}
	analysisPath := filepath.Join(workspace.Root, "analysis.json")
	if err := artifacts.WriteJSON(analysisPath, report); err != nil {
		return domain.AnalysisReport{}, "", workspace, err
	}
	return report, analysisPath, workspace, nil
}

// AnalyzeAgentic executes the LLM-only branch using a fixed multi-agent
// workflow and persists both the final analysis and the per-agent trace report.
func (s *Service) AnalyzeAgentic(cfg *domain.AppConfig, modelKey string, workspace *artifacts.Workspace) (domain.AnalysisReport, string, domain.AgentTraceReport, string, *artifacts.Workspace, error) {
	model, err := getModelOrError(cfg, modelKey)
	if err != nil {
		return domain.AnalysisReport{}, "", domain.AgentTraceReport{}, "", workspace, err
	}
	cataloger := s.catalogFactory.NewCatalog(cfg.Project)
	methods, err := cataloger.Catalog()
	if err != nil {
		return domain.AnalysisReport{}, "", domain.AgentTraceReport{}, "", workspace, err
	}
	if cfg.Pipeline.MaxMethods > 0 && len(methods) > cfg.Pipeline.MaxMethods {
		methods = methods[:cfg.Pipeline.MaxMethods]
	}
	overview, err := cataloger.LoadOverview()
	if err != nil {
		return domain.AnalysisReport{}, "", domain.AgentTraceReport{}, "", workspace, err
	}
	if workspace == nil {
		workspace, err = artifacts.NewWorkspace(cfg.Pipeline.OutputDir, artifacts.NewRunID("analyze-agentic-"+modelKey))
		if err != nil {
			return domain.AnalysisReport{}, "", domain.AgentTraceReport{}, "", workspace, err
		}
	}
	if err := artifacts.WriteJSON(filepath.Join(workspace.Root, "catalog.json"), methods); err != nil {
		return domain.AnalysisReport{}, "", domain.AgentTraceReport{}, "", workspace, err
	}

	orchestrator, err := agentic.NewService(func(model domain.ModelConfig, systemPrompt, userPrompt string) (map[string]interface{}, string, error) {
		response, err := s.completionClient.CompleteJSON(model, systemPrompt, userPrompt)
		if err != nil {
			return nil, "", err
		}
		return response.Payload, response.RawText, nil
	})
	if err != nil {
		return domain.AnalysisReport{}, "", domain.AgentTraceReport{}, "", workspace, err
	}

	report, traceReport, err := orchestrator.Analyze(model, modelKey, overview, methods, cfg.Pipeline.SavePrompts, workspace)
	if err != nil {
		return domain.AnalysisReport{}, "", domain.AgentTraceReport{}, "", workspace, err
	}
	report.ProjectRoot = cfg.Project.Root

	analysisPath := filepath.Join(workspace.Sources, "llm-analysis.json")
	if err := artifacts.WriteJSON(analysisPath, report); err != nil {
		return domain.AnalysisReport{}, "", domain.AgentTraceReport{}, "", workspace, err
	}
	tracePath := filepath.Join(workspace.Traces, "agent-trace-report.json")
	if err := artifacts.WriteJSON(tracePath, traceReport); err != nil {
		return domain.AnalysisReport{}, "", domain.AgentTraceReport{}, "", workspace, err
	}
	return report, analysisPath, traceReport, tracePath, workspace, nil
}

// IngestWITUP reads a replication-package JSON artifact and converts it to the
// canonical analysis representation used by downstream steps.
func (s *Service) IngestWITUP(witupJSONPath string, workspace *artifacts.Workspace) (domain.AnalysisReport, string, *artifacts.Workspace, error) {
	report, err := witup.LoadAnalysis(witupJSONPath)
	if err != nil {
		return domain.AnalysisReport{}, "", workspace, err
	}
	if workspace == nil {
		workspace, err = artifacts.NewWorkspace(filepath.Dir(filepath.Dir(witupJSONPath)), artifacts.NewRunID("ingest-witup"))
		if err != nil {
			return domain.AnalysisReport{}, "", workspace, err
		}
	}
	analysisPath := filepath.Join(workspace.Sources, "witup-analysis.json")
	if err := artifacts.WriteJSON(analysisPath, report); err != nil {
		return domain.AnalysisReport{}, "", workspace, err
	}
	return report, analysisPath, workspace, nil
}

// RunExperiment executes the three-branch source experiment:
// WITUP_ONLY, LLM_ONLY, and WITUP_PLUS_LLM.
func (s *Service) RunExperiment(cfg *domain.AppConfig, witupJSONPath, analysisModelKey string) (ExperimentRunResult, error) {
	workspace, err := artifacts.NewWorkspace(cfg.Pipeline.OutputDir, artifacts.NewRunID("experiment-"+analysisModelKey))
	if err != nil {
		return ExperimentRunResult{}, err
	}
	witupReport, witupPath, _, err := s.IngestWITUP(witupJSONPath, workspace)
	if err != nil {
		return ExperimentRunResult{}, err
	}
	llmReport, llmPath, _, tracePath, _, err := s.AnalyzeAgentic(cfg, analysisModelKey, workspace)
	if err != nil {
		return ExperimentRunResult{}, err
	}

	comparison := experiment.BuildComparisonReport(witupPath, witupReport, llmPath, llmReport)
	comparisonPath := filepath.Join(workspace.Comparisons, "source-comparison.json")
	if err := artifacts.WriteJSON(comparisonPath, comparison); err != nil {
		return ExperimentRunResult{}, err
	}

	variants := experiment.BuildVariants(witupReport, llmReport)
	variantArtifacts, err := experiment.WriteVariantArtifacts(workspace, variants)
	if err != nil {
		return ExperimentRunResult{}, err
	}

	report := domain.ExperimentReport{
		RunID:                filepath.Base(workspace.Root),
		GeneratedAt:          domain.TimestampUTC(),
		WITUPAnalysisPath:    witupPath,
		LLMAnalysisPath:      llmPath,
		ComparisonPath:       comparisonPath,
		VariantArtifacts:     variantArtifacts,
		ComparisonSummary:    comparison.Summary,
		AgentTraceReportPath: tracePath,
	}
	reportPath := filepath.Join(workspace.Root, "experiment.json")
	if err := artifacts.WriteJSON(reportPath, report); err != nil {
		return ExperimentRunResult{}, err
	}

	return ExperimentRunResult{
		Workspace:         workspace.Root,
		WITUPAnalysisPath: witupPath,
		LLMAnalysisPath:   llmPath,
		ComparisonPath:    comparisonPath,
		TracePath:         tracePath,
		VariantArtifacts:  variantArtifacts,
		ComparisonReport:  comparison,
		ExperimentReport:  report,
	}, nil
}

// Generate asks the generation model to create test files from analysis.
func (s *Service) Generate(cfg *domain.AppConfig, analysisReport domain.AnalysisReport, analysisPath, modelKey string, workspace *artifacts.Workspace) (domain.GenerationReport, string, *artifacts.Workspace, error) {
	model, err := getModelOrError(cfg, modelKey)
	if err != nil {
		return domain.GenerationReport{}, "", workspace, err
	}
	overview, err := s.catalogFactory.NewCatalog(cfg.Project).LoadOverview()
	if err != nil {
		return domain.GenerationReport{}, "", workspace, err
	}
	if workspace == nil {
		workspace, err = artifacts.NewWorkspace(cfg.Pipeline.OutputDir, artifacts.NewRunID("generate-"+modelKey))
		if err != nil {
			return domain.GenerationReport{}, "", workspace, err
		}
	}

	grouped := groupAnalysisByContainer(analysisReport)
	strategyParts := make([]string, 0, len(grouped))
	allFiles := make([]domain.GeneratedTestFile, 0, len(grouped))
	rawResponses := make([]map[string]interface{}, 0, len(grouped))

	keys := make([]string, 0, len(grouped))
	for key := range grouped {
		keys = append(keys, key)
	}
	sort.Strings(keys)

	for i, containerName := range keys {
		methodsPayload := grouped[containerName]
		systemPrompt := buildGenerationSystemPrompt(cfg.Project.TestFramework)
		userPrompt := buildGenerationUserPrompt(overview, containerName, methodsPayload)
		response, err := s.completionClient.CompleteJSON(model, systemPrompt, userPrompt)
		if err != nil {
			return domain.GenerationReport{}, "", workspace, fmt.Errorf("generation failed for %s: %w", containerName, err)
		}
		summary, files := normalizeGenerationResponse(response.Payload)
		if strings.TrimSpace(summary) != "" {
			strategyParts = append(strategyParts, summary)
		}
		allFiles = append(allFiles, files...)
		rawResponses = append(rawResponses, response.Payload)

		if cfg.Pipeline.SavePrompts {
			stem := fmt.Sprintf("generation-%04d-%s", i+1, artifacts.Slugify(containerName))
			if err := artifacts.WriteText(filepath.Join(workspace.Prompts, stem+".txt"), userPrompt); err != nil {
				return domain.GenerationReport{}, "", workspace, err
			}
			if err := artifacts.WriteText(filepath.Join(workspace.Responses, stem+".txt"), response.RawText); err != nil {
				return domain.GenerationReport{}, "", workspace, err
			}
		}
	}

	for _, file := range allFiles {
		rel, err := artifacts.SafeRelativePath(file.RelativePath)
		if err != nil {
			return domain.GenerationReport{}, "", workspace, err
		}
		if err := artifacts.WriteText(filepath.Join(workspace.Tests, rel), file.Content); err != nil {
			return domain.GenerationReport{}, "", workspace, err
		}
	}

	report := domain.GenerationReport{
		RunID:              filepath.Base(workspace.Root),
		SourceAnalysisPath: analysisPath,
		ModelKey:           modelKey,
		GeneratedAt:        domain.TimestampUTC(),
		StrategySummary:    strings.TrimSpace(strings.Join(strategyParts, "\n")),
		TestFiles:          allFiles,
		RawResponses:       rawResponses,
	}
	generationPath := filepath.Join(workspace.Root, "generation.json")
	if err := artifacts.WriteJSON(generationPath, report); err != nil {
		return domain.GenerationReport{}, "", workspace, err
	}
	return report, generationPath, workspace, nil
}

// Evaluate executes metrics and optional judge evaluation.
func (s *Service) Evaluate(cfg *domain.AppConfig, analysisReport domain.AnalysisReport, analysisPath string, generationReport domain.GenerationReport, generationPath string, judgeModelKey string, workspace *artifacts.Workspace) (domain.EvaluationReport, string, *artifacts.Workspace, error) {
	var err error
	if workspace == nil {
		workspace, err = artifacts.NewWorkspace(cfg.Pipeline.OutputDir, artifacts.NewRunID("evaluate-"+generationReport.ModelKey))
		if err != nil {
			return domain.EvaluationReport{}, "", workspace, err
		}
	}

	metricResults := s.metricRunner.RunAll(cfg.Metrics, metrics.RuntimeContext{
		ProjectRoot:    cfg.Project.Root,
		RunDir:         workspace.Root,
		TestsDir:       workspace.Tests,
		AnalysisPath:   analysisPath,
		GenerationPath: generationPath,
		ModelKey:       generationReport.ModelKey,
	})
	metricScore := metrics.AggregateScore(metricResults)

	var judgeEvaluation *domain.JudgeEvaluation
	var judgeScore *float64
	if strings.TrimSpace(judgeModelKey) != "" {
		judgeModel, err := getModelOrError(cfg, judgeModelKey)
		if err != nil {
			return domain.EvaluationReport{}, "", workspace, err
		}
		judgePrompt := buildJudgeUserPrompt(analysisReport, generationReport, metricResults)
		response, err := s.completionClient.CompleteJSON(judgeModel, buildJudgeSystemPrompt(), judgePrompt)
		if err != nil {
			return domain.EvaluationReport{}, "", workspace, err
		}
		normalized := normalizeJudgeResponse(response.Payload)
		judgeEvaluation = &normalized
		judgeScore = &normalized.Score
		if cfg.Pipeline.SavePrompts {
			if err := artifacts.WriteText(filepath.Join(workspace.Prompts, "judge.txt"), judgePrompt); err != nil {
				return domain.EvaluationReport{}, "", workspace, err
			}
			if err := artifacts.WriteText(filepath.Join(workspace.Responses, "judge.txt"), response.RawText); err != nil {
				return domain.EvaluationReport{}, "", workspace, err
			}
		}
	}

	combined := metrics.CombineScores(metricScore, judgeScore)
	report := domain.EvaluationReport{
		RunID:           filepath.Base(workspace.Root),
		ModelKey:        generationReport.ModelKey,
		GeneratedAt:     domain.TimestampUTC(),
		AnalysisPath:    analysisPath,
		GenerationPath:  generationPath,
		MetricResults:   metricResults,
		MetricScore:     metricScore,
		JudgeModelKey:   judgeModelKey,
		JudgeEvaluation: judgeEvaluation,
		CombinedScore:   combined,
	}
	evaluationPath := filepath.Join(workspace.Root, "evaluation.json")
	if err := artifacts.WriteJSON(evaluationPath, report); err != nil {
		return domain.EvaluationReport{}, "", workspace, err
	}
	return report, evaluationPath, workspace, nil
}

// Run executes analyze -> generate -> evaluate within one workspace.
func (s *Service) Run(cfg *domain.AppConfig, analysisModelKey, generationModelKey, judgeModelKey string) (RunResult, error) {
	workspace, err := artifacts.NewWorkspace(cfg.Pipeline.OutputDir, artifacts.NewRunID("run-"+analysisModelKey+"-"+generationModelKey))
	if err != nil {
		return RunResult{}, err
	}
	analysisReport, analysisPath, _, err := s.Analyze(cfg, analysisModelKey, workspace)
	if err != nil {
		return RunResult{}, err
	}
	generationReport, generationPath, _, err := s.Generate(cfg, analysisReport, analysisPath, generationModelKey, workspace)
	if err != nil {
		return RunResult{}, err
	}
	evaluationReport, evaluationPath, _, err := s.Evaluate(cfg, analysisReport, analysisPath, generationReport, generationPath, judgeModelKey, workspace)
	if err != nil {
		return RunResult{}, err
	}
	return RunResult{
		Workspace:        workspace.Root,
		AnalysisPath:     analysisPath,
		GenerationPath:   generationPath,
		EvaluationPath:   evaluationPath,
		AnalysisReport:   analysisReport,
		GenerationReport: generationReport,
		EvaluationReport: evaluationReport,
	}, nil
}

// Benchmark runs scenarios and stores ranking artifacts.
func (s *Service) Benchmark(cfg *domain.AppConfig, scenarios []domain.BenchmarkScenario, judgeModelKey string) (domain.BenchmarkReport, string, error) {
	workspace, err := artifacts.NewWorkspace(cfg.Pipeline.OutputDir, artifacts.NewRunID("benchmark"))
	if err != nil {
		return domain.BenchmarkReport{}, "", err
	}

	entries := make([]domain.BenchmarkEntry, 0, len(scenarios))
	for _, sc := range scenarios {
		subWorkspace, err := artifacts.NewWorkspace(workspace.Root, artifacts.Slugify(sc.AnalysisModelKey+"-to-"+sc.GenerationModelKey))
		if err != nil {
			return domain.BenchmarkReport{}, "", err
		}
		analysisReport, analysisPath, _, err := s.Analyze(cfg, sc.AnalysisModelKey, subWorkspace)
		if err != nil {
			return domain.BenchmarkReport{}, "", err
		}
		generationReport, generationPath, _, err := s.Generate(cfg, analysisReport, analysisPath, sc.GenerationModelKey, subWorkspace)
		if err != nil {
			return domain.BenchmarkReport{}, "", err
		}
		evaluationReport, evaluationPath, _, err := s.Evaluate(cfg, analysisReport, analysisPath, generationReport, generationPath, judgeModelKey, subWorkspace)
		if err != nil {
			return domain.BenchmarkReport{}, "", err
		}
		var judgeScore *float64
		if evaluationReport.JudgeEvaluation != nil {
			judgeScore = &evaluationReport.JudgeEvaluation.Score
		}
		entries = append(entries, domain.BenchmarkEntry{
			AnalysisModelKey:   sc.AnalysisModelKey,
			GenerationModelKey: sc.GenerationModelKey,
			EvaluationPath:     evaluationPath,
			MetricScore:        evaluationReport.MetricScore,
			JudgeScore:         judgeScore,
			CombinedScore:      evaluationReport.CombinedScore,
		})
	}

	sort.Slice(entries, func(i, j int) bool {
		return scoreSortKey(entries[i].CombinedScore, entries[i].MetricScore, entries[i].JudgeScore) >
			scoreSortKey(entries[j].CombinedScore, entries[j].MetricScore, entries[j].JudgeScore)
	})
	for i := range entries {
		entries[i].Rank = i + 1
	}

	report := domain.BenchmarkReport{
		RunID:         filepath.Base(workspace.Root),
		GeneratedAt:   domain.TimestampUTC(),
		JudgeModelKey: judgeModelKey,
		Entries:       entries,
	}
	benchmarkPath := filepath.Join(workspace.Root, "benchmark.json")
	if err := artifacts.WriteJSON(benchmarkPath, report); err != nil {
		return domain.BenchmarkReport{}, "", err
	}
	if err := artifacts.WriteText(filepath.Join(workspace.Root, "benchmark.md"), buildBenchmarkMarkdown(entries)); err != nil {
		return domain.BenchmarkReport{}, "", err
	}
	return report, benchmarkPath, nil
}

// LoadAnalysisReport loads analysis report from JSON artifact.
func LoadAnalysisReport(path string) (domain.AnalysisReport, error) {
	out := domain.AnalysisReport{}
	if err := artifacts.ReadJSON(path, &out); err != nil {
		return domain.AnalysisReport{}, err
	}
	return out, nil
}

// LoadGenerationReport loads generation report from JSON artifact.
func LoadGenerationReport(path string) (domain.GenerationReport, error) {
	out := domain.GenerationReport{}
	if err := artifacts.ReadJSON(path, &out); err != nil {
		return domain.GenerationReport{}, err
	}
	return out, nil
}

func getModelOrError(cfg *domain.AppConfig, modelKey string) (domain.ModelConfig, error) {
	model, ok := cfg.Models[modelKey]
	if !ok {
		keys := make([]string, 0, len(cfg.Models))
		for k := range cfg.Models {
			keys = append(keys, k)
		}
		sort.Strings(keys)
		return domain.ModelConfig{}, fmt.Errorf("model %q is not configured. available: %s", modelKey, strings.Join(keys, ", "))
	}
	return model, nil
}

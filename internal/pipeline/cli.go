package pipeline

import (
	"encoding/json"
	"errors"
	"flag"
	"fmt"
	"os"
	"path/filepath"
	"sort"

	"github.com/marceloamorim/witup-llm/internal/artifacts"
	"github.com/marceloamorim/witup-llm/internal/config"
	"github.com/marceloamorim/witup-llm/internal/experiment"
	"github.com/marceloamorim/witup-llm/internal/llm"
	"github.com/marceloamorim/witup-llm/internal/metrics"
	"github.com/marceloamorim/witup-llm/internal/witup"
)

// Main is the single CLI entrypoint used by cmd/witup.
func Main(argv []string) int {
	if len(argv) == 0 {
		printBannerIfEnabled(argv)
		printUsage()
		return 2
	}
	printBannerIfEnabled(argv)

	service := NewService(nil, nil)
	command := argv[0]
	args := argv[1:]

	switch command {
	case "models":
		return runModels(args)
	case "probe":
		return runProbe(args)
	case "ingest-witup":
		return runIngestWITUP(args, service)
	case "analyze":
		return runAnalyze(args, service)
	case "analyze-agentic":
		return runAnalyzeAgentic(args, service)
	case "compare-sources":
		return runCompareSources(args)
	case "generate":
		return runGenerate(args, service)
	case "evaluate":
		return runEvaluate(args, service)
	case "run":
		return runAll(args, service)
	case "run-experiment":
		return runExperiment(args, service)
	case "benchmark":
		return runBenchmark(args, service)
	case "help", "-h", "--help":
		printUsage()
		return 0
	default:
		fmt.Fprintf(os.Stderr, "error: unknown command %q\n\n", command)
		printUsage()
		return 2
	}
}

func runModels(args []string) int {
	fs := flag.NewFlagSet("models", flag.ContinueOnError)
	configPath := fs.String("config", "", "Path to TOML config file")
	if err := fs.Parse(args); err != nil {
		return 2
	}
	if *configPath == "" {
		fmt.Fprintln(os.Stderr, "error: --config is required")
		return 2
	}
	cfg, err := config.Load(*configPath)
	if err != nil {
		fmt.Fprintf(os.Stderr, "error: %v\n", err)
		return 1
	}
	keys := make([]string, 0, len(cfg.Models))
	for key := range cfg.Models {
		keys = append(keys, key)
	}
	sort.Strings(keys)
	for _, key := range keys {
		model := cfg.Models[key]
		fmt.Printf("%s: provider=%s model=%s base_url=%s\n", key, model.Provider, model.Model, model.BaseURL)
	}
	return 0
}

func runProbe(args []string) int {
	fs := flag.NewFlagSet("probe", flag.ContinueOnError)
	configPath := fs.String("config", "", "Path to TOML config file")
	modelKey := fs.String("model", "", "Configured model key")
	jsonOutput := fs.Bool("json", false, "Print payload as JSON")
	if err := fs.Parse(args); err != nil {
		return 2
	}
	if *configPath == "" || *modelKey == "" {
		fmt.Fprintln(os.Stderr, "error: --config and --model are required")
		return 2
	}
	cfg, err := config.Load(*configPath)
	if err != nil {
		fmt.Fprintf(os.Stderr, "error: %v\n", err)
		return 1
	}
	model, ok := cfg.Models[*modelKey]
	if !ok {
		fmt.Fprintf(os.Stderr, "error: model %q is not configured\n", *modelKey)
		return 1
	}
	client := llm.NewClient()
	payload, err := client.Probe(model)
	if err != nil {
		fmt.Fprintf(os.Stderr, "error: %v\n", err)
		return 1
	}
	if *jsonOutput {
		data, _ := json.MarshalIndent(payload, "", "  ")
		fmt.Println(string(data))
	} else {
		keys := make([]string, 0, len(payload))
		for k := range payload {
			keys = append(keys, k)
		}
		sort.Strings(keys)
		fmt.Printf("Model        : %s\n", *modelKey)
		fmt.Printf("Provider     : %s\n", model.Provider)
		fmt.Printf("Endpoint     : %s\n", model.BaseURL)
		fmt.Printf("Probe status : %v\n", payload["status"])
		fmt.Printf("Payload keys : %s\n", joinComma(keys))
	}
	return 0
}

func runIngestWITUP(args []string, service *Service) int {
	fs := flag.NewFlagSet("ingest-witup", flag.ContinueOnError)
	witupJSON := fs.String("witup-json", "", "Path to wit.json or wit_filtered.json")
	projectKey := fs.String("project-key", "", "Project key under the local replication package")
	replicationRoot := fs.String("replication-root", "resources/wit-replication-package/data/output", "Replication package output root")
	baselineFile := fs.String("baseline-file", "wit.json", "Baseline file name inside the project folder")
	outputDir := fs.String("output-dir", "generated", "Directory for generated artifacts")
	if err := fs.Parse(args); err != nil {
		return 2
	}
	sourcePath := resolveWITUPPath(*witupJSON, *projectKey, *replicationRoot, *baselineFile)
	if sourcePath == "" {
		fmt.Fprintln(os.Stderr, "error: provide --witup-json or --project-key")
		return 2
	}
	workspace, err := artifacts.NewWorkspace(*outputDir, artifacts.NewRunID("ingest-witup"))
	if err != nil {
		fmt.Fprintf(os.Stderr, "error: %v\n", err)
		return 1
	}
	report, analysisPath, _, err := service.IngestWITUP(sourcePath, workspace)
	if err != nil {
		fmt.Fprintf(os.Stderr, "error: %v\n", err)
		return 1
	}
	fmt.Printf("Run dir      : %s\n", workspace.Root)
	fmt.Printf("Baseline path: %s\n", sourcePath)
	fmt.Printf("Analysis path: %s\n", analysisPath)
	fmt.Printf("Methods      : %d\n", report.TotalMethods)
	return 0
}

func runAnalyze(args []string, service *Service) int {
	fs := flag.NewFlagSet("analyze", flag.ContinueOnError)
	configPath := fs.String("config", "", "Path to TOML config file")
	modelKey := fs.String("model", "", "Configured model key")
	if err := fs.Parse(args); err != nil {
		return 2
	}
	if *configPath == "" || *modelKey == "" {
		fmt.Fprintln(os.Stderr, "error: --config and --model are required")
		return 2
	}
	cfg, err := config.Load(*configPath)
	if err != nil {
		fmt.Fprintf(os.Stderr, "error: %v\n", err)
		return 1
	}
	report, analysisPath, workspace, err := service.Analyze(cfg, *modelKey, nil)
	if err != nil {
		fmt.Fprintf(os.Stderr, "error: %v\n", err)
		return 1
	}
	fmt.Printf("Run dir      : %s\n", workspace.Root)
	fmt.Printf("Analysis path: %s\n", analysisPath)
	fmt.Printf("Methods      : %d\n", report.TotalMethods)
	fmt.Printf("Model        : %s\n", report.ModelKey)
	return 0
}

func runAnalyzeAgentic(args []string, service *Service) int {
	fs := flag.NewFlagSet("analyze-agentic", flag.ContinueOnError)
	configPath := fs.String("config", "", "Path to TOML config file")
	modelKey := fs.String("model", "", "Configured model key")
	if err := fs.Parse(args); err != nil {
		return 2
	}
	if *configPath == "" || *modelKey == "" {
		fmt.Fprintln(os.Stderr, "error: --config and --model are required")
		return 2
	}
	cfg, err := config.Load(*configPath)
	if err != nil {
		fmt.Fprintf(os.Stderr, "error: %v\n", err)
		return 1
	}
	report, analysisPath, traceReport, tracePath, workspace, err := service.AnalyzeAgentic(cfg, *modelKey, nil)
	if err != nil {
		fmt.Fprintf(os.Stderr, "error: %v\n", err)
		return 1
	}
	fmt.Printf("Run dir      : %s\n", workspace.Root)
	fmt.Printf("Analysis path: %s\n", analysisPath)
	fmt.Printf("Trace path   : %s\n", tracePath)
	fmt.Printf("Methods      : %d\n", report.TotalMethods)
	fmt.Printf("Agent traces : %d\n", len(traceReport.Methods))
	return 0
}

func runCompareSources(args []string) int {
	fs := flag.NewFlagSet("compare-sources", flag.ContinueOnError)
	witupPath := fs.String("witup", "", "Path to canonical WITUP analysis JSON")
	llmPath := fs.String("llm", "", "Path to canonical LLM analysis JSON")
	outputDir := fs.String("output-dir", "generated", "Directory for comparison artifacts")
	if err := fs.Parse(args); err != nil {
		return 2
	}
	if *witupPath == "" || *llmPath == "" {
		fmt.Fprintln(os.Stderr, "error: --witup and --llm are required")
		return 2
	}
	witupAbs, _ := filepath.Abs(*witupPath)
	llmAbs, _ := filepath.Abs(*llmPath)
	if err := EnsurePathsExist(witupAbs, llmAbs); err != nil {
		fmt.Fprintf(os.Stderr, "error: %v\n", err)
		return 1
	}
	witupReport, err := LoadAnalysisReport(witupAbs)
	if err != nil {
		fmt.Fprintf(os.Stderr, "error: %v\n", err)
		return 1
	}
	llmReport, err := LoadAnalysisReport(llmAbs)
	if err != nil {
		fmt.Fprintf(os.Stderr, "error: %v\n", err)
		return 1
	}
	workspace, err := artifacts.NewWorkspace(*outputDir, artifacts.NewRunID("compare-sources"))
	if err != nil {
		fmt.Fprintf(os.Stderr, "error: %v\n", err)
		return 1
	}
	comparison := experiment.BuildComparisonReport(witupAbs, witupReport, llmAbs, llmReport)
	comparisonPath := filepath.Join(workspace.Comparisons, "source-comparison.json")
	if err := artifacts.WriteJSON(comparisonPath, comparison); err != nil {
		fmt.Fprintf(os.Stderr, "error: %v\n", err)
		return 1
	}
	variants := experiment.BuildVariants(witupReport, llmReport)
	variantArtifacts, err := experiment.WriteVariantArtifacts(workspace, variants)
	if err != nil {
		fmt.Fprintf(os.Stderr, "error: %v\n", err)
		return 1
	}
	fmt.Printf("Run dir         : %s\n", workspace.Root)
	fmt.Printf("Comparison path : %s\n", comparisonPath)
	fmt.Printf("Methods in both : %d\n", comparison.Summary.MethodsInBoth)
	fmt.Printf("Variant artifacts: %d\n", len(variantArtifacts))
	return 0
}

func runGenerate(args []string, service *Service) int {
	fs := flag.NewFlagSet("generate", flag.ContinueOnError)
	configPath := fs.String("config", "", "Path to TOML config file")
	analysisPath := fs.String("analysis", "", "Path to analysis.json")
	modelKey := fs.String("model", "", "Configured model key")
	if err := fs.Parse(args); err != nil {
		return 2
	}
	if *configPath == "" || *analysisPath == "" || *modelKey == "" {
		fmt.Fprintln(os.Stderr, "error: --config, --analysis and --model are required")
		return 2
	}
	cfg, err := config.Load(*configPath)
	if err != nil {
		fmt.Fprintf(os.Stderr, "error: %v\n", err)
		return 1
	}
	analysisPathAbs, err := filepath.Abs(*analysisPath)
	if err != nil {
		fmt.Fprintf(os.Stderr, "error: resolve analysis path: %v\n", err)
		return 1
	}
	if err := EnsurePathsExist(analysisPathAbs); err != nil {
		fmt.Fprintf(os.Stderr, "error: %v\n", err)
		return 1
	}
	analysisReport, err := LoadAnalysisReport(analysisPathAbs)
	if err != nil {
		fmt.Fprintf(os.Stderr, "error: %v\n", err)
		return 1
	}
	report, generationPath, workspace, err := service.Generate(cfg, analysisReport, analysisPathAbs, *modelKey, nil)
	if err != nil {
		fmt.Fprintf(os.Stderr, "error: %v\n", err)
		return 1
	}
	fmt.Printf("Run dir        : %s\n", workspace.Root)
	fmt.Printf("Generation path: %s\n", generationPath)
	fmt.Printf("Generated files: %d\n", len(report.TestFiles))
	fmt.Printf("Tests dir      : %s\n", workspace.Tests)
	return 0
}

func runEvaluate(args []string, service *Service) int {
	fs := flag.NewFlagSet("evaluate", flag.ContinueOnError)
	configPath := fs.String("config", "", "Path to TOML config file")
	analysisPath := fs.String("analysis", "", "Path to analysis.json")
	generationPath := fs.String("generation", "", "Path to generation.json")
	judgeModel := fs.String("judge-model", "", "Optional judge model key")
	if err := fs.Parse(args); err != nil {
		return 2
	}
	if *configPath == "" || *analysisPath == "" || *generationPath == "" {
		fmt.Fprintln(os.Stderr, "error: --config, --analysis and --generation are required")
		return 2
	}
	cfg, err := config.Load(*configPath)
	if err != nil {
		fmt.Fprintf(os.Stderr, "error: %v\n", err)
		return 1
	}
	analysisAbs, _ := filepath.Abs(*analysisPath)
	generationAbs, _ := filepath.Abs(*generationPath)
	if err := EnsurePathsExist(analysisAbs, generationAbs); err != nil {
		fmt.Fprintf(os.Stderr, "error: %v\n", err)
		return 1
	}
	analysisReport, err := LoadAnalysisReport(analysisAbs)
	if err != nil {
		fmt.Fprintf(os.Stderr, "error: %v\n", err)
		return 1
	}
	generationReport, err := LoadGenerationReport(generationAbs)
	if err != nil {
		fmt.Fprintf(os.Stderr, "error: %v\n", err)
		return 1
	}
	selectedJudge := *judgeModel
	if selectedJudge == "" {
		selectedJudge = cfg.Pipeline.JudgeModel
	}
	report, evaluationPath, workspace, err := service.Evaluate(cfg, analysisReport, analysisAbs, generationReport, generationAbs, selectedJudge, nil)
	if err != nil {
		fmt.Fprintf(os.Stderr, "error: %v\n", err)
		return 1
	}
	fmt.Printf("Run dir         : %s\n", workspace.Root)
	fmt.Printf("Evaluation path : %s\n", evaluationPath)
	fmt.Printf("Metric score    : %s\n", metrics.FormatScore(report.MetricScore))
	fmt.Printf("Combined score  : %s\n", metrics.FormatScore(report.CombinedScore))
	if report.JudgeEvaluation != nil {
		fmt.Printf("Judge verdict   : %s\n", report.JudgeEvaluation.Verdict)
	}
	return 0
}

func runAll(args []string, service *Service) int {
	fs := flag.NewFlagSet("run", flag.ContinueOnError)
	configPath := fs.String("config", "", "Path to TOML config file")
	analysisModel := fs.String("analysis-model", "", "Model key for analysis")
	generationModel := fs.String("generation-model", "", "Model key for generation")
	judgeModel := fs.String("judge-model", "", "Optional judge model key")
	if err := fs.Parse(args); err != nil {
		return 2
	}
	if *configPath == "" || *analysisModel == "" || *generationModel == "" {
		fmt.Fprintln(os.Stderr, "error: --config, --analysis-model and --generation-model are required")
		return 2
	}
	cfg, err := config.Load(*configPath)
	if err != nil {
		fmt.Fprintf(os.Stderr, "error: %v\n", err)
		return 1
	}
	selectedJudge := *judgeModel
	if selectedJudge == "" {
		selectedJudge = cfg.Pipeline.JudgeModel
	}
	result, err := service.Run(cfg, *analysisModel, *generationModel, selectedJudge)
	if err != nil {
		fmt.Fprintf(os.Stderr, "error: %v\n", err)
		return 1
	}
	fmt.Printf("Run dir         : %s\n", result.Workspace)
	fmt.Printf("Analysis path   : %s\n", result.AnalysisPath)
	fmt.Printf("Generation path : %s\n", result.GenerationPath)
	fmt.Printf("Evaluation path : %s\n", result.EvaluationPath)
	fmt.Printf("Combined score  : %s\n", metrics.FormatScore(result.EvaluationReport.CombinedScore))
	return 0
}

func runExperiment(args []string, service *Service) int {
	fs := flag.NewFlagSet("run-experiment", flag.ContinueOnError)
	configPath := fs.String("config", "", "Path to TOML config file")
	modelKey := fs.String("model", "", "Configured model key for the LLM-only branch")
	witupJSON := fs.String("witup-json", "", "Explicit path to wit.json or wit_filtered.json")
	projectKey := fs.String("project-key", "", "Project key under the local replication package")
	replicationRoot := fs.String("replication-root", "resources/wit-replication-package/data/output", "Replication package output root")
	baselineFile := fs.String("baseline-file", "wit.json", "Baseline file name inside the project folder")
	if err := fs.Parse(args); err != nil {
		return 2
	}
	if *configPath == "" || *modelKey == "" {
		fmt.Fprintln(os.Stderr, "error: --config and --model are required")
		return 2
	}
	sourcePath := resolveWITUPPath(*witupJSON, *projectKey, *replicationRoot, *baselineFile)
	if sourcePath == "" {
		fmt.Fprintln(os.Stderr, "error: provide --witup-json or --project-key")
		return 2
	}
	cfg, err := config.Load(*configPath)
	if err != nil {
		fmt.Fprintf(os.Stderr, "error: %v\n", err)
		return 1
	}
	result, err := service.RunExperiment(cfg, sourcePath, *modelKey)
	if err != nil {
		fmt.Fprintf(os.Stderr, "error: %v\n", err)
		return 1
	}
	fmt.Printf("Run dir         : %s\n", result.Workspace)
	fmt.Printf("WITUP analysis  : %s\n", result.WITUPAnalysisPath)
	fmt.Printf("LLM analysis    : %s\n", result.LLMAnalysisPath)
	fmt.Printf("Comparison path : %s\n", result.ComparisonPath)
	fmt.Printf("Agent traces    : %s\n", result.TracePath)
	fmt.Printf("Methods in both : %d\n", result.ComparisonReport.Summary.MethodsInBoth)
	fmt.Printf("Variants        : %d\n", len(result.VariantArtifacts))
	return 0
}

func runBenchmark(args []string, service *Service) int {
	fs := flag.NewFlagSet("benchmark", flag.ContinueOnError)
	configPath := fs.String("config", "", "Path to TOML config file")
	judgeModel := fs.String("judge-model", "", "Optional judge model key")
	models := &stringSliceFlag{}
	analysisModels := &stringSliceFlag{}
	generationModels := &stringSliceFlag{}
	fs.Var(models, "model", "Model key for coupled benchmark mode (repeatable)")
	fs.Var(analysisModels, "analysis-model", "Analysis model key for matrix benchmark mode (repeatable)")
	fs.Var(generationModels, "generation-model", "Generation model key for matrix benchmark mode (repeatable)")
	if err := fs.Parse(args); err != nil {
		return 2
	}
	if *configPath == "" {
		fmt.Fprintln(os.Stderr, "error: --config is required")
		return 2
	}
	cfg, err := config.Load(*configPath)
	if err != nil {
		fmt.Fprintf(os.Stderr, "error: %v\n", err)
		return 1
	}
	scenarios, err := BuildBenchmarkScenarios(models.values, analysisModels.values, generationModels.values)
	if err != nil {
		fmt.Fprintf(os.Stderr, "error: %v\n", err)
		return 2
	}
	selectedJudge := *judgeModel
	if selectedJudge == "" {
		selectedJudge = cfg.Pipeline.JudgeModel
	}
	report, benchmarkPath, err := service.Benchmark(cfg, scenarios, selectedJudge)
	if err != nil {
		fmt.Fprintf(os.Stderr, "error: %v\n", err)
		return 1
	}
	fmt.Printf("Benchmark path: %s\n", benchmarkPath)
	for _, entry := range report.Entries {
		fmt.Printf("#%d %s->%s combined=%s metric=%s judge=%s\n",
			entry.Rank,
			entry.AnalysisModelKey,
			entry.GenerationModelKey,
			metrics.FormatScore(entry.CombinedScore),
			metrics.FormatScore(entry.MetricScore),
			metrics.FormatScore(entry.JudgeScore),
		)
	}
	return 0
}

func printUsage() {
	fmt.Println("witup - AI pipeline CLI for exception-path analysis and experiment orchestration")
	fmt.Println("")
	fmt.Println("Usage:")
	fmt.Println("  witup <command> [flags]")
	fmt.Println("")
	fmt.Println("Commands:")
	fmt.Println("  models       List configured models")
	fmt.Println("  probe        Probe model connectivity and auth")
	fmt.Println("  analyze      Analyze methods and extract exception paths")
	fmt.Println("  ingest-witup Import a WITUP baseline into canonical analysis JSON")
	fmt.Println("  analyze-agentic Run the role-based multi-agent LLM analysis")
	fmt.Println("  compare-sources Compare canonical WITUP and LLM analysis artifacts")
	fmt.Println("  generate     Generate tests from analysis report")
	fmt.Println("  evaluate     Run metrics and optional judge evaluation")
	fmt.Println("  run          Execute analyze -> generate -> evaluate")
	fmt.Println("  run-experiment Execute WITUP_ONLY, LLM_ONLY, and WITUP_PLUS_LLM preparation")
	fmt.Println("  benchmark    Run coupled or matrix benchmark scenarios")
	fmt.Println("  help         Show this message")
}

func resolveWITUPPath(explicitPath, projectKey, replicationRoot, baselineFile string) string {
	if explicitPath != "" {
		abs, err := filepath.Abs(explicitPath)
		if err != nil {
			return explicitPath
		}
		return abs
	}
	if projectKey == "" {
		return ""
	}
	path := witup.ResolveBaselinePath(replicationRoot, projectKey, baselineFile)
	abs, err := filepath.Abs(path)
	if err != nil {
		return path
	}
	return abs
}

type stringSliceFlag struct {
	values []string
}

func (f *stringSliceFlag) String() string {
	return joinComma(f.values)
}

func (f *stringSliceFlag) Set(value string) error {
	if value == "" {
		return errors.New("empty value")
	}
	f.values = append(f.values, value)
	return nil
}

func joinComma(values []string) string {
	if len(values) == 0 {
		return ""
	}
	out := values[0]
	for i := 1; i < len(values); i++ {
		out += ", " + values[i]
	}
	return out
}

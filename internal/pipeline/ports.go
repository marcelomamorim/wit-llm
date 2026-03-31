package pipeline

import (
	"github.com/marceloamorim/witup-llm/internal/catalog"
	"github.com/marceloamorim/witup-llm/internal/domain"
	"github.com/marceloamorim/witup-llm/internal/llm"
	"github.com/marceloamorim/witup-llm/internal/metrics"
)

// CompletionResponse is the application-facing representation of an LLM answer.
type CompletionResponse struct {
	Payload map[string]interface{}
	RawText string
}

// CompletionClient abstracts the completion provider used by the pipeline.
type CompletionClient interface {
	CompleteJSON(model domain.ModelConfig, systemPrompt, userPrompt string) (*CompletionResponse, error)
}

// MetricRunner abstracts metric execution so tests can provide deterministic doubles.
type MetricRunner interface {
	RunAll(metrics []domain.MetricConfig, ctx metrics.RuntimeContext) []domain.MetricResult
}

// MethodCatalog exposes method discovery and optional project overview loading.
type MethodCatalog interface {
	Catalog() ([]domain.MethodDescriptor, error)
	LoadOverview() (string, error)
}

// CatalogFactory creates method catalogs for a project configuration.
type CatalogFactory interface {
	NewCatalog(cfg domain.ProjectConfig) MethodCatalog
}

type completionClientAdapter struct {
	client *llm.Client
}

// NewCompletionClient builds the default completion adapter.
func NewCompletionClient(client *llm.Client) CompletionClient {
	if client == nil {
		client = llm.NewClient()
	}
	return completionClientAdapter{client: client}
}

func (a completionClientAdapter) CompleteJSON(model domain.ModelConfig, systemPrompt, userPrompt string) (*CompletionResponse, error) {
	response, err := a.client.CompleteJSON(model, systemPrompt, userPrompt)
	if err != nil {
		return nil, err
	}
	return &CompletionResponse{
		Payload: response.Payload,
		RawText: response.RawText,
	}, nil
}

type metricRunnerAdapter struct {
	runner *metrics.Runner
}

// NewMetricRunner builds the default metric adapter.
func NewMetricRunner(runner *metrics.Runner) MetricRunner {
	if runner == nil {
		runner = metrics.NewRunner()
	}
	return metricRunnerAdapter{runner: runner}
}

func (a metricRunnerAdapter) RunAll(metricConfigs []domain.MetricConfig, ctx metrics.RuntimeContext) []domain.MetricResult {
	return a.runner.RunAll(metricConfigs, ctx)
}

type defaultCatalogFactory struct{}

func (defaultCatalogFactory) NewCatalog(cfg domain.ProjectConfig) MethodCatalog {
	return catalog.NewCataloger(cfg)
}

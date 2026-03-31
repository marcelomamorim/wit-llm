package agentic

import (
	"encoding/json"
	"fmt"
	"path/filepath"
	"strings"

	"github.com/marceloamorim/witup-llm/internal/artifacts"
	"github.com/marceloamorim/witup-llm/internal/domain"
)

// JSONCompleter is the narrow provider-agnostic seam used by the multi-agent
// orchestration. The project owns the workflow; providers only supply JSON.
type JSONCompleter func(model domain.ModelConfig, systemPrompt, userPrompt string) (map[string]interface{}, string, error)

// Service runs the LLM-only branch as a deterministic sequence of specialized
// agents.
type Service struct {
	complete JSONCompleter
}

// NewService validates the required completion dependency.
func NewService(complete JSONCompleter) (*Service, error) {
	if complete == nil {
		return nil, fmt.Errorf("agentic service requires a completion function")
	}
	return &Service{complete: complete}, nil
}

// Analyze executes the role-based LLM workflow for all target methods.
func (s *Service) Analyze(
	model domain.ModelConfig,
	modelKey string,
	overview string,
	methods []domain.MethodDescriptor,
	savePrompts bool,
	workspace *artifacts.Workspace,
) (domain.AnalysisReport, domain.AgentTraceReport, error) {
	analyses := make([]domain.MethodAnalysis, 0, len(methods))
	traces := make([]domain.MethodAgentTrace, 0, len(methods))

	for index, method := range methods {
		trace, analysis, err := s.analyzeMethod(model, overview, method, index, savePrompts, workspace)
		if err != nil {
			return domain.AnalysisReport{}, domain.AgentTraceReport{}, fmt.Errorf("agentic analysis failed for %s: %w", method.Signature, err)
		}
		traces = append(traces, trace)
		analyses = append(analyses, analysis)
	}

	analysisReport := domain.AnalysisReport{
		RunID:        filepath.Base(workspace.Root),
		ProjectRoot:  "",
		ModelKey:     modelKey,
		Source:       domain.ExpathSourceLLM,
		Strategy:     "llm_multi_agent",
		GeneratedAt:  domain.TimestampUTC(),
		TotalMethods: len(methods),
		Analyses:     analyses,
	}
	traceReport := domain.AgentTraceReport{
		RunID:       filepath.Base(workspace.Root),
		ModelKey:    modelKey,
		GeneratedAt: domain.TimestampUTC(),
		Methods:     traces,
	}
	return analysisReport, traceReport, nil
}

func (s *Service) analyzeMethod(
	model domain.ModelConfig,
	overview string,
	method domain.MethodDescriptor,
	index int,
	savePrompts bool,
	workspace *artifacts.Workspace,
) (domain.MethodAgentTrace, domain.MethodAnalysis, error) {
	steps := make([]domain.AgentTraceStep, 0, 4)

	archOutput, archRaw, archPrompt, err := s.runAgent(model, domain.AgentRoleArchaeologist, buildArchaeologistSystemPrompt(), buildArchaeologistUserPrompt(overview, method))
	if err != nil {
		return domain.MethodAgentTrace{}, domain.MethodAnalysis{}, err
	}
	archStep, err := persistAgentStep(workspace, savePrompts, index, method, domain.AgentRoleArchaeologist, archPrompt, archRaw, archOutput)
	if err != nil {
		return domain.MethodAgentTrace{}, domain.MethodAnalysis{}, err
	}
	steps = append(steps, archStep)

	dependencyOutput, dependencyRaw, dependencyPrompt, err := s.runAgent(
		model,
		domain.AgentRoleDependency,
		buildDependencySystemPrompt(),
		buildDependencyUserPrompt(overview, method, archOutput),
	)
	if err != nil {
		return domain.MethodAgentTrace{}, domain.MethodAnalysis{}, err
	}
	dependencyStep, err := persistAgentStep(workspace, savePrompts, index, method, domain.AgentRoleDependency, dependencyPrompt, dependencyRaw, dependencyOutput)
	if err != nil {
		return domain.MethodAgentTrace{}, domain.MethodAnalysis{}, err
	}
	steps = append(steps, dependencyStep)

	extractorOutput, extractorRaw, extractorPrompt, err := s.runAgent(
		model,
		domain.AgentRoleExtractor,
		buildExtractorSystemPrompt(),
		buildExtractorUserPrompt(overview, method, archOutput, dependencyOutput),
	)
	if err != nil {
		return domain.MethodAgentTrace{}, domain.MethodAnalysis{}, err
	}
	extractorStep, err := persistAgentStep(workspace, savePrompts, index, method, domain.AgentRoleExtractor, extractorPrompt, extractorRaw, extractorOutput)
	if err != nil {
		return domain.MethodAgentTrace{}, domain.MethodAnalysis{}, err
	}
	steps = append(steps, extractorStep)

	skepticOutput, skepticRaw, skepticPrompt, err := s.runAgent(
		model,
		domain.AgentRoleSkeptic,
		buildSkepticSystemPrompt(),
		buildSkepticUserPrompt(method, extractorOutput, archOutput, dependencyOutput),
	)
	if err != nil {
		return domain.MethodAgentTrace{}, domain.MethodAnalysis{}, err
	}
	skepticStep, err := persistAgentStep(workspace, savePrompts, index, method, domain.AgentRoleSkeptic, skepticPrompt, skepticRaw, skepticOutput)
	if err != nil {
		return domain.MethodAgentTrace{}, domain.MethodAnalysis{}, err
	}
	steps = append(steps, skepticStep)

	analysis := normalizeMethodAnalysis(method, archOutput, dependencyOutput, extractorOutput, skepticOutput)
	return domain.MethodAgentTrace{Method: method, Steps: steps}, analysis, nil
}

func (s *Service) runAgent(
	model domain.ModelConfig,
	role domain.AgentRole,
	systemPrompt string,
	userPrompt string,
) (map[string]interface{}, string, string, error) {
	output, rawText, err := s.complete(model, systemPrompt, userPrompt)
	if err != nil {
		return nil, "", "", fmt.Errorf("%s request failed: %w", role, err)
	}
	return output, rawText, userPrompt, nil
}

func persistAgentStep(
	workspace *artifacts.Workspace,
	savePrompts bool,
	index int,
	method domain.MethodDescriptor,
	role domain.AgentRole,
	prompt string,
	rawResponse string,
	output map[string]interface{},
) (domain.AgentTraceStep, error) {
	step := domain.AgentTraceStep{
		Role:    role,
		Summary: strings.TrimSpace(fmt.Sprint(output["summary"])),
		Output:  output,
	}
	if !savePrompts || workspace == nil {
		return step, nil
	}

	stem := fmt.Sprintf("agentic-%04d-%s-%s", index+1, role, artifacts.Slugify(method.MethodID))
	promptPath := filepath.Join(workspace.Prompts, stem+".txt")
	responsePath := filepath.Join(workspace.Responses, stem+".txt")
	outputPath := filepath.Join(workspace.Traces, stem+".json")

	if err := artifacts.WriteText(promptPath, prompt); err != nil {
		return domain.AgentTraceStep{}, err
	}
	if err := artifacts.WriteText(responsePath, rawResponse); err != nil {
		return domain.AgentTraceStep{}, err
	}
	if err := artifacts.WriteJSON(outputPath, output); err != nil {
		return domain.AgentTraceStep{}, err
	}

	step.PromptFile = promptPath
	step.OutputFile = outputPath
	return step, nil
}

func normalizeMethodAnalysis(
	method domain.MethodDescriptor,
	archaeologist map[string]interface{},
	dependency map[string]interface{},
	extractor map[string]interface{},
	skeptic map[string]interface{},
) domain.MethodAnalysis {
	summary := firstNonEmptyString(
		skeptic["method_summary"],
		extractor["method_summary"],
		archaeologist["method_summary"],
	)
	expaths := normalizeExpaths(method, skeptic, extractor)
	rawResponse := map[string]interface{}{
		"archaeologist":    archaeologist,
		"dependency_mesh":  dependency,
		"extractor":        extractor,
		"skeptic_reviewer": skeptic,
	}
	return domain.MethodAnalysis{
		Method:        method,
		MethodSummary: summary,
		Expaths:       expaths,
		RawResponse:   rawResponse,
	}
}

func normalizeExpaths(method domain.MethodDescriptor, skeptic map[string]interface{}, extractor map[string]interface{}) []domain.ExceptionPath {
	raw := skeptic["accepted_expaths"]
	if raw == nil {
		raw = extractor["expaths"]
	}
	items, ok := raw.([]interface{})
	if !ok {
		return nil
	}

	expaths := make([]domain.ExceptionPath, 0, len(items))
	for index, item := range items {
		entry, ok := item.(map[string]interface{})
		if !ok {
			continue
		}
		exceptionType := strings.TrimSpace(fmt.Sprint(entry["exception_type"]))
		if exceptionType == "" || exceptionType == "<nil>" {
			continue
		}
		confidence := parseConfidence(entry["confidence"])
		expaths = append(expaths, domain.ExceptionPath{
			PathID:          fallbackPathID(fmt.Sprint(entry["path_id"]), method.MethodID, index+1),
			ExceptionType:   exceptionType,
			Trigger:         strings.TrimSpace(fmt.Sprint(entry["trigger"])),
			GuardConditions: toStringList(entry["guard_conditions"]),
			Confidence:      confidence,
			Evidence:        toStringList(entry["evidence"]),
			Source:          domain.ExpathSourceLLM,
			Metadata: map[string]interface{}{
				"accepted_by_skeptic": skeptic["accepted_expaths"] != nil,
				"review_notes":        skeptic["review_notes"],
			},
		})
	}
	return expaths
}

func buildArchaeologistSystemPrompt() string {
	return "You are the Archaeologist agent for Java exception-path research. Read one Java method deeply and return valid JSON only."
}

func buildArchaeologistUserPrompt(overview string, method domain.MethodDescriptor) string {
	return fmt.Sprintf(`Study the method deeply and summarize what it does.
Return JSON:
{"summary":"...","method_summary":"...","responsibilities":[...],"input_risks":[...],"exception_cues":[...],"state_dependencies":[...]}

Project overview:
%s

Method signature: %s
Method source:
%s
`, overview, method.Signature, method.Source)
}

func buildDependencySystemPrompt() string {
	return "You are the Dependency Mesh agent for Java exception-path research. Expand callees, field usage, and dependency risks. Return valid JSON only."
}

func buildDependencyUserPrompt(overview string, method domain.MethodDescriptor, archaeologist map[string]interface{}) string {
	return fmt.Sprintf(`Expand the dependency mesh for this Java method.
Return JSON:
{"summary":"...","direct_dependencies":[...],"callee_risks":[...],"field_dependencies":[...],"propagated_exception_clues":[...],"context_gaps":[...]}

Project overview:
%s

Method signature: %s
Method source:
%s

Archaeologist notes:
%s
`, overview, method.Signature, method.Source, mustPrettyJSON(archaeologist))
}

func buildExtractorSystemPrompt() string {
	return "You are the Exception Path Extractor agent for Java exception-path research. Infer candidate exception paths and return valid JSON only."
}

func buildExtractorUserPrompt(overview string, method domain.MethodDescriptor, archaeologist map[string]interface{}, dependency map[string]interface{}) string {
	return fmt.Sprintf(`Infer candidate exception paths for the method.
Return JSON:
{"summary":"...","method_summary":"...","dependency_mesh":{},"expaths":[{"path_id":"...","exception_type":"...","trigger":"...","guard_conditions":[...],"confidence":0.0,"evidence":[...]}]}

Project overview:
%s

Method signature: %s
Method source:
%s

Archaeologist notes:
%s

Dependency mesh:
%s
`, overview, method.Signature, method.Source, mustPrettyJSON(archaeologist), mustPrettyJSON(dependency))
}

func buildSkepticSystemPrompt() string {
	return "You are the Skeptic Reviewer agent for Java exception-path research. Keep only defensible exception paths with explicit evidence. Return valid JSON only."
}

func buildSkepticUserPrompt(method domain.MethodDescriptor, extractor map[string]interface{}, archaeologist map[string]interface{}, dependency map[string]interface{}) string {
	return fmt.Sprintf(`Review the candidate exception paths and keep only defensible ones.
Return JSON:
{"summary":"...","method_summary":"...","accepted_expaths":[{"path_id":"...","exception_type":"...","trigger":"...","guard_conditions":[...],"confidence":0.0,"evidence":[...]}],"rejected_paths":[{"path_id":"...","reason":"..."}],"review_notes":[...]}

Method signature: %s
Method source:
%s

Archaeologist notes:
%s

Dependency mesh:
%s

Extractor candidates:
%s
`, method.Signature, method.Source, mustPrettyJSON(archaeologist), mustPrettyJSON(dependency), mustPrettyJSON(extractor))
}

func mustPrettyJSON(payload map[string]interface{}) string {
	data, err := json.MarshalIndent(payload, "", "  ")
	if err != nil {
		return "{}"
	}
	return string(data)
}

func firstNonEmptyString(values ...interface{}) string {
	for _, value := range values {
		trimmed := strings.TrimSpace(fmt.Sprint(value))
		if trimmed == "" || trimmed == "<nil>" {
			continue
		}
		return trimmed
	}
	return ""
}

func toStringList(raw interface{}) []string {
	if raw == nil {
		return nil
	}
	items, ok := raw.([]interface{})
	if !ok {
		return nil
	}
	values := make([]string, 0, len(items))
	for _, item := range items {
		trimmed := strings.TrimSpace(fmt.Sprint(item))
		if trimmed == "" || trimmed == "<nil>" {
			continue
		}
		values = append(values, trimmed)
	}
	return values
}

func parseConfidence(raw interface{}) float64 {
	value := strings.TrimSpace(fmt.Sprint(raw))
	if value == "" || value == "<nil>" {
		return 0
	}
	var confidence float64
	if _, err := fmt.Sscanf(value, "%f", &confidence); err != nil {
		return 0
	}
	if confidence < 0 {
		return 0
	}
	if confidence > 1 {
		return 1
	}
	return confidence
}

func fallbackPathID(raw, methodID string, index int) string {
	value := strings.TrimSpace(raw)
	if value == "" || value == "<nil>" {
		return fmt.Sprintf("%s:%d", methodID, index)
	}
	return value
}

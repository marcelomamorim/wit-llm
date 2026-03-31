package domain

// AgentRole identifies one deterministic step in the LLM-only multi-agent
// branch. Each role has a narrow responsibility so the overall orchestration is
// explainable and easy to test.
type AgentRole string

const (
	AgentRoleArchaeologist AgentRole = "archaeologist"
	AgentRoleDependency    AgentRole = "dependency_mesh"
	AgentRoleExtractor     AgentRole = "expath_extractor"
	AgentRoleSkeptic       AgentRole = "skeptic_reviewer"
)

// AgentTraceStep captures one agent execution for one method.
type AgentTraceStep struct {
	Role       AgentRole              `json:"role"`
	Summary    string                 `json:"summary"`
	PromptFile string                 `json:"prompt_file,omitempty"`
	OutputFile string                 `json:"output_file,omitempty"`
	Output     map[string]interface{} `json:"output"`
}

// MethodAgentTrace groups all agent steps executed for one method.
type MethodAgentTrace struct {
	Method MethodDescriptor `json:"method"`
	Steps  []AgentTraceStep `json:"steps"`
}

// AgentTraceReport is the persisted trace artifact for the LLM-only branch.
type AgentTraceReport struct {
	RunID       string             `json:"run_id"`
	ModelKey    string             `json:"model_key"`
	GeneratedAt string             `json:"generated_at"`
	Methods     []MethodAgentTrace `json:"methods"`
}

// MethodComparison summarizes how two sources align for one comparison unit.
type MethodComparison struct {
	Unit               ComparisonUnit `json:"unit"`
	WITUPExpathCount   int            `json:"witup_expath_count"`
	LLMExpathCount     int            `json:"llm_expath_count"`
	SharedExpathCount  int            `json:"shared_expath_count"`
	WITUPOnlyExpathIDs []string       `json:"witup_only_expath_ids"`
	LLMOnlyExpathIDs   []string       `json:"llm_only_expath_ids"`
}

// SourceComparisonSummary stores aggregate overlap counts for one experiment.
type SourceComparisonSummary struct {
	WITUPMethodCount   int `json:"witup_method_count"`
	LLMMethodCount     int `json:"llm_method_count"`
	MethodsInBoth      int `json:"methods_in_both"`
	MethodsOnlyWITUP   int `json:"methods_only_witup"`
	MethodsOnlyLLM     int `json:"methods_only_llm"`
	WITUPExpathCount   int `json:"witup_expath_count"`
	LLMExpathCount     int `json:"llm_expath_count"`
	SharedExpathCount  int `json:"shared_expath_count"`
	WITUPOnlyExpathIDs int `json:"witup_only_expath_count"`
	LLMOnlyExpathIDs   int `json:"llm_only_expath_count"`
}

// SourceComparisonReport captures the source-level comparison before any
// derived artifacts, such as generated tests, are produced.
type SourceComparisonReport struct {
	RunID             string                  `json:"run_id"`
	GeneratedAt       string                  `json:"generated_at"`
	WITUPAnalysisPath string                  `json:"witup_analysis_path"`
	LLMAnalysisPath   string                  `json:"llm_analysis_path"`
	Methods           []MethodComparison      `json:"methods"`
	Summary           SourceComparisonSummary `json:"summary"`
}

// VariantArtifact points to one persisted analysis artifact for a specific
// experimental variant.
type VariantArtifact struct {
	Variant      ComparisonVariant `json:"variant"`
	AnalysisPath string            `json:"analysis_path"`
	MethodCount  int               `json:"method_count"`
	ExpathCount  int               `json:"expath_count"`
}

// ExperimentReport ties the three supported branches together:
// WITUP_ONLY, LLM_ONLY, and WITUP_PLUS_LLM.
type ExperimentReport struct {
	RunID                string                  `json:"run_id"`
	GeneratedAt          string                  `json:"generated_at"`
	WITUPAnalysisPath    string                  `json:"witup_analysis_path"`
	LLMAnalysisPath      string                  `json:"llm_analysis_path"`
	ComparisonPath       string                  `json:"comparison_path"`
	VariantArtifacts     []VariantArtifact       `json:"variant_artifacts"`
	ComparisonSummary    SourceComparisonSummary `json:"comparison_summary"`
	AgentTraceReportPath string                  `json:"agent_trace_report_path,omitempty"`
}

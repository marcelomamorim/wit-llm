package domain

// MethodDescriptor identifies one discovered Java method in the target project.
type MethodDescriptor struct {
	MethodID      string `json:"method_id"`
	FilePath      string `json:"file_path"`
	ContainerName string `json:"container_name"`
	MethodName    string `json:"method_name"`
	Signature     string `json:"signature"`
	StartLine     int    `json:"start_line"`
	EndLine       int    `json:"end_line"`
	Source        string `json:"source"`
}

// ExceptionPath represents one hypothesized exception path.
type ExceptionPath struct {
	PathID          string                 `json:"path_id"`
	ExceptionType   string                 `json:"exception_type"`
	Trigger         string                 `json:"trigger"`
	GuardConditions []string               `json:"guard_conditions"`
	Confidence      float64                `json:"confidence"`
	Evidence        []string               `json:"evidence"`
	Source          ExpathSource           `json:"source,omitempty"`
	Metadata        map[string]interface{} `json:"metadata,omitempty"`
}

// MethodAnalysis is the canonical per-method analysis artifact.
type MethodAnalysis struct {
	Method        MethodDescriptor       `json:"method"`
	MethodSummary string                 `json:"method_summary"`
	Expaths       []ExceptionPath        `json:"expaths"`
	RawResponse   map[string]interface{} `json:"raw_response"`
}

// AnalysisReport stores all method analyses for one run.
type AnalysisReport struct {
	RunID        string           `json:"run_id"`
	ProjectRoot  string           `json:"project_root"`
	ModelKey     string           `json:"model_key"`
	Source       ExpathSource     `json:"source"`
	Strategy     string           `json:"strategy,omitempty"`
	GeneratedAt  string           `json:"generated_at"`
	TotalMethods int              `json:"total_methods"`
	Analyses     []MethodAnalysis `json:"analyses"`
}

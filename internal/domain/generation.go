package domain

// GeneratedTestFile is one emitted test file.
type GeneratedTestFile struct {
	RelativePath     string   `json:"relative_path"`
	Content          string   `json:"content"`
	CoveredMethodIDs []string `json:"covered_method_ids"`
	Notes            string   `json:"notes"`
}

// GenerationReport summarizes generated tests.
type GenerationReport struct {
	RunID              string                   `json:"run_id"`
	SourceAnalysisPath string                   `json:"source_analysis_path"`
	ModelKey           string                   `json:"model_key"`
	GeneratedAt        string                   `json:"generated_at"`
	StrategySummary    string                   `json:"strategy_summary"`
	TestFiles          []GeneratedTestFile      `json:"test_files"`
	RawResponses       []map[string]interface{} `json:"raw_responses"`
}

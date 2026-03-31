package domain

// MetricResult captures one metric command execution.
type MetricResult struct {
	Name            string   `json:"name"`
	Kind            string   `json:"kind"`
	Command         string   `json:"command"`
	Success         bool     `json:"success"`
	ExitCode        int      `json:"exit_code"`
	Stdout          string   `json:"stdout"`
	Stderr          string   `json:"stderr"`
	NumericValue    *float64 `json:"numeric_value"`
	NormalizedScore *float64 `json:"normalized_score"`
	Weight          float64  `json:"weight"`
	Description     string   `json:"description"`
}

// JudgeEvaluation stores optional LLM judge output.
type JudgeEvaluation struct {
	Score                  float64                `json:"score"`
	Verdict                string                 `json:"verdict"`
	Strengths              []string               `json:"strengths"`
	Weaknesses             []string               `json:"weaknesses"`
	Risks                  []string               `json:"risks"`
	RecommendedNextActions []string               `json:"recommended_next_actions"`
	RawResponse            map[string]interface{} `json:"raw_response"`
}

// EvaluationReport is the final report for one end-to-end run.
type EvaluationReport struct {
	RunID           string           `json:"run_id"`
	ModelKey        string           `json:"model_key"`
	GeneratedAt     string           `json:"generated_at"`
	AnalysisPath    string           `json:"analysis_path"`
	GenerationPath  string           `json:"generation_path"`
	MetricResults   []MetricResult   `json:"metric_results"`
	MetricScore     *float64         `json:"metric_score"`
	JudgeModelKey   string           `json:"judge_model_key,omitempty"`
	JudgeEvaluation *JudgeEvaluation `json:"judge_evaluation,omitempty"`
	CombinedScore   *float64         `json:"combined_score"`
}

// BenchmarkScenario links one analysis model to one generation model.
type BenchmarkScenario struct {
	AnalysisModelKey   string `json:"analysis_model_key"`
	GenerationModelKey string `json:"generation_model_key"`
}

// BenchmarkEntry is one ranked benchmark result.
type BenchmarkEntry struct {
	AnalysisModelKey   string   `json:"analysis_model_key"`
	GenerationModelKey string   `json:"generation_model_key"`
	EvaluationPath     string   `json:"evaluation_path"`
	MetricScore        *float64 `json:"metric_score"`
	JudgeScore         *float64 `json:"judge_score"`
	CombinedScore      *float64 `json:"combined_score"`
	Rank               int      `json:"rank"`
}

// BenchmarkReport stores ranking across scenarios.
type BenchmarkReport struct {
	RunID         string           `json:"run_id"`
	GeneratedAt   string           `json:"generated_at"`
	JudgeModelKey string           `json:"judge_model_key,omitempty"`
	Entries       []BenchmarkEntry `json:"entries"`
}

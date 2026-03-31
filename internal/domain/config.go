package domain

// ProjectConfig describes the Java project analyzed by the pipeline.
//
// The current implementation catalogs Java source code only. Support for other
// JVM languages can be added later, but they are intentionally out of scope for
// the present research baseline.
type ProjectConfig struct {
	Root          string   `toml:"root" json:"root"`
	Include       []string `toml:"include" json:"include"`
	Exclude       []string `toml:"exclude" json:"exclude"`
	OverviewFile  string   `toml:"overview_file" json:"overview_file"`
	TestFramework string   `toml:"test_framework" json:"test_framework"`
}

// PipelineConfig controls pipeline behavior.
type PipelineConfig struct {
	OutputDir   string `toml:"output_dir" json:"output_dir"`
	SavePrompts bool   `toml:"save_prompts" json:"save_prompts"`
	MaxMethods  int    `toml:"max_methods" json:"max_methods"`
	JudgeModel  string `toml:"judge_model" json:"judge_model"`
}

// ModelConfig defines one configured LLM endpoint.
type ModelConfig struct {
	Provider       string  `toml:"provider" json:"provider"`
	Model          string  `toml:"model" json:"model"`
	BaseURL        string  `toml:"base_url" json:"base_url"`
	APIKeyEnv      string  `toml:"api_key_env" json:"api_key_env"`
	Temperature    float64 `toml:"temperature" json:"temperature"`
	TimeoutSeconds int     `toml:"timeout_seconds" json:"timeout_seconds"`
	MaxRetries     int     `toml:"max_retries" json:"max_retries"`
}

// MetricConfig defines one executable metric command.
type MetricConfig struct {
	Name             string  `toml:"name" json:"name"`
	Kind             string  `toml:"kind" json:"kind"`
	Command          string  `toml:"command" json:"command"`
	Weight           float64 `toml:"weight" json:"weight"`
	ValueRegex       string  `toml:"value_regex" json:"value_regex"`
	Scale            float64 `toml:"scale" json:"scale"`
	WorkingDirectory string  `toml:"working_directory" json:"working_directory"`
	Description      string  `toml:"description" json:"description"`
}

// AppConfig is the root application configuration.
type AppConfig struct {
	ConfigPath string                 `json:"config_path"`
	Project    ProjectConfig          `toml:"project" json:"project"`
	Pipeline   PipelineConfig         `toml:"pipeline" json:"pipeline"`
	Models     map[string]ModelConfig `toml:"models" json:"models"`
	Metrics    []MetricConfig         `toml:"metrics" json:"metrics"`
}

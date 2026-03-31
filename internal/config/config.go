package config

import (
	"errors"
	"fmt"
	"os"
	"path/filepath"
	"strings"

	toml "github.com/pelletier/go-toml/v2"

	"github.com/marceloamorim/witup-llm/internal/domain"
)

var (
	supportedProviders = map[string]bool{"ollama": true, "openai_compatible": true}
	defaultExclude     = []string{
		".git",
		"target",
		"build",
		"generated",
		"tests",
	}
)

// Load parses, normalizes, and validates the application configuration.
//
// The current runtime is Java-only, so the defaults intentionally point to the
// standard Maven/Gradle source layout under src/main/java.
func Load(path string) (*domain.AppConfig, error) {
	absolutePath, err := filepath.Abs(path)
	if err != nil {
		return nil, fmt.Errorf("resolve config path: %w", err)
	}

	content, err := os.ReadFile(absolutePath)
	if err != nil {
		return nil, fmt.Errorf("read config %q: %w", absolutePath, err)
	}

	cfg := &domain.AppConfig{}
	if err := toml.Unmarshal(content, cfg); err != nil {
		return nil, fmt.Errorf("parse TOML config %q: %w", absolutePath, err)
	}
	cfg.ConfigPath = absolutePath

	if err := applyDefaults(cfg); err != nil {
		return nil, err
	}
	if err := resolvePaths(cfg); err != nil {
		return nil, err
	}
	if err := validate(cfg); err != nil {
		return nil, err
	}
	return cfg, nil
}

func applyDefaults(cfg *domain.AppConfig) error {
	if len(cfg.Project.Include) == 0 {
		cfg.Project.Include = []string{"src/main/java", "."}
	}
	if len(cfg.Project.Exclude) == 0 {
		cfg.Project.Exclude = append([]string{}, defaultExclude...)
	}
	if cfg.Project.TestFramework == "" {
		cfg.Project.TestFramework = "infer"
	}
	if cfg.Pipeline.OutputDir == "" {
		cfg.Pipeline.OutputDir = "generated"
	}
	if !cfg.Pipeline.SavePrompts {
		// Explicit false must be preserved. The default is true only when the
		// field is absent from the TOML file.
		_, has := lookupRawPipelineFlag(cfg.ConfigPath, "save_prompts")
		if !has {
			cfg.Pipeline.SavePrompts = true
		}
	}

	for key, model := range cfg.Models {
		if model.TimeoutSeconds == 0 {
			model.TimeoutSeconds = 180
		}
		cfg.Models[key] = model
	}
	for index := range cfg.Metrics {
		if cfg.Metrics[index].Kind == "" {
			cfg.Metrics[index].Kind = cfg.Metrics[index].Name
		}
		if cfg.Metrics[index].Weight == 0 {
			cfg.Metrics[index].Weight = 1.0
		}
		if cfg.Metrics[index].Scale == 0 {
			cfg.Metrics[index].Scale = 100.0
		}
	}
	return nil
}

func resolvePaths(cfg *domain.AppConfig) error {
	baseDir := filepath.Dir(cfg.ConfigPath)
	if cfg.Project.Root == "" {
		cfg.Project.Root = "."
	}
	cfg.Project.Root = resolvePath(baseDir, cfg.Project.Root)
	cfg.Pipeline.OutputDir = resolvePath(baseDir, cfg.Pipeline.OutputDir)
	if strings.TrimSpace(cfg.Project.OverviewFile) != "" {
		cfg.Project.OverviewFile = resolvePath(baseDir, cfg.Project.OverviewFile)
	}
	return nil
}

func validate(cfg *domain.AppConfig) error {
	if len(cfg.Models) == 0 {
		return errors.New("config must declare at least one model in [models]")
	}

	projectInfo, err := os.Stat(cfg.Project.Root)
	if err != nil {
		return fmt.Errorf("project root %q: %w", cfg.Project.Root, err)
	}
	if !projectInfo.IsDir() {
		return fmt.Errorf("project root %q must be a directory", cfg.Project.Root)
	}

	if cfg.Project.OverviewFile != "" {
		overviewInfo, err := os.Stat(cfg.Project.OverviewFile)
		if err != nil {
			return fmt.Errorf("overview file %q: %w", cfg.Project.OverviewFile, err)
		}
		if overviewInfo.IsDir() {
			return fmt.Errorf("overview file %q must be a file", cfg.Project.OverviewFile)
		}
	}

	if cfg.Pipeline.MaxMethods < 0 {
		return errors.New("pipeline.max_methods must be >= 0")
	}
	if cfg.Pipeline.JudgeModel != "" {
		if _, ok := cfg.Models[cfg.Pipeline.JudgeModel]; !ok {
			return fmt.Errorf("pipeline.judge_model references unknown model %q", cfg.Pipeline.JudgeModel)
		}
	}

	for key, model := range cfg.Models {
		if !supportedProviders[model.Provider] {
			return fmt.Errorf("unsupported provider %q for model %q", model.Provider, key)
		}
		if strings.TrimSpace(model.Model) == "" {
			return fmt.Errorf("models.%s.model is required", key)
		}
		if strings.TrimSpace(model.BaseURL) == "" {
			return fmt.Errorf("models.%s.base_url is required", key)
		}
		if model.TimeoutSeconds <= 0 {
			return fmt.Errorf("models.%s.timeout_seconds must be > 0", key)
		}
		if model.MaxRetries < 0 {
			return fmt.Errorf("models.%s.max_retries must be >= 0", key)
		}
		if model.Temperature < 0 {
			return fmt.Errorf("models.%s.temperature must be >= 0", key)
		}
	}

	for index, metric := range cfg.Metrics {
		label := fmt.Sprintf("metrics[%d]", index)
		if strings.TrimSpace(metric.Name) == "" {
			return fmt.Errorf("%s.name is required", label)
		}
		if strings.TrimSpace(metric.Command) == "" {
			return fmt.Errorf("%s.command is required", label)
		}
		if metric.Weight < 0 {
			return fmt.Errorf("%s.weight must be >= 0", label)
		}
		if metric.Scale < 0 {
			return fmt.Errorf("%s.scale must be >= 0", label)
		}
	}

	return nil
}

func resolvePath(baseDir, candidate string) string {
	if filepath.IsAbs(candidate) {
		return filepath.Clean(candidate)
	}
	return filepath.Clean(filepath.Join(baseDir, candidate))
}

// lookupRawPipelineFlag keeps the save_prompts default stable even though TOML
// unmarshalling does not let us distinguish "false" from "unset" directly.
func lookupRawPipelineFlag(configPath, key string) (string, bool) {
	content, err := os.ReadFile(configPath)
	if err != nil {
		return "", false
	}

	var raw map[string]interface{}
	if err := toml.Unmarshal(content, &raw); err != nil {
		return "", false
	}

	pipelineSection, ok := raw["pipeline"].(map[string]interface{})
	if !ok {
		return "", false
	}
	value, ok := pipelineSection[key]
	if !ok {
		return "", false
	}
	return fmt.Sprint(value), true
}

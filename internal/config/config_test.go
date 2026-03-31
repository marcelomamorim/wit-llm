package config

import (
	"os"
	"path/filepath"
	"testing"
)

func TestLoadConfigHappyPath(t *testing.T) {
	tempDir := t.TempDir()
	projectRoot := filepath.Join(tempDir, "project")
	if err := os.MkdirAll(projectRoot, 0o755); err != nil {
		t.Fatal(err)
	}

	configPath := filepath.Join(tempDir, "witup.toml")
	content := `
[project]
root = "./project"

[pipeline]
output_dir = "./generated"
save_prompts = true
max_methods = 10

[models.openai_main]
provider = "openai_compatible"
model = "gpt-5.4"
base_url = "https://api.openai.com/v1"
api_key_env = "OPENAI_API_KEY"
timeout_seconds = 60
max_retries = 1

[[metrics]]
name = "unit-tests"
kind = "tests"
command = "echo ok"
weight = 1.0
`
	if err := os.WriteFile(configPath, []byte(content), 0o644); err != nil {
		t.Fatal(err)
	}

	cfg, err := Load(configPath)
	if err != nil {
		t.Fatalf("unexpected error: %v", err)
	}
	if len(cfg.Models) != 1 {
		t.Fatalf("expected one model")
	}
	if len(cfg.Project.Include) == 0 || cfg.Project.Include[0] != "src/main/java" {
		t.Fatalf("expected Java include defaults, got %v", cfg.Project.Include)
	}
}

func TestLoadConfigWithoutModelsFails(t *testing.T) {
	tempDir := t.TempDir()
	projectRoot := filepath.Join(tempDir, "project")
	if err := os.MkdirAll(projectRoot, 0o755); err != nil {
		t.Fatal(err)
	}

	configPath := filepath.Join(tempDir, "witup.toml")
	content := `
[project]
root = "./project"
`
	if err := os.WriteFile(configPath, []byte(content), 0o644); err != nil {
		t.Fatal(err)
	}

	if _, err := Load(configPath); err == nil {
		t.Fatalf("expected validation error")
	}
}

package configuracao

import (
	"os"
	"path/filepath"
	"testing"
)

func TestCarregarConfiguracaoCaminhoFeliz(t *testing.T) {
	tempDir := t.TempDir()
	projectRoot := filepath.Join(tempDir, "project")
	if err := os.MkdirAll(projectRoot, 0o755); err != nil {
		t.Fatal(err)
	}

	configPath := filepath.Join(tempDir, "pipeline.json")
	content := `{
  "version": "1",
  "project": {
    "root": "./project"
  },
  "pipeline": {
    "output_dir": "./generated",
    "save_prompts": true,
    "max_methods": 10
  },
  "models": {
    "openai_main": {
      "provider": "openai_compatible",
      "model": "gpt-5.4",
      "base_url": "https://api.openai.com/v1",
      "api_key_env": "OPENAI_API_KEY",
      "timeout_seconds": 60,
      "max_retries": 1
    }
  },
  "metrics": [
    {
      "name": "unit-tests",
      "kind": "tests",
      "command": "echo ok",
      "weight": 1.0
    }
  ]
}`
	if err := os.WriteFile(configPath, []byte(content), 0o644); err != nil {
		t.Fatal(err)
	}

	cfg, err := Carregar(configPath)
	if err != nil {
		t.Fatalf("unexpected error: %v", err)
	}
	if len(cfg.Modelos) != 1 {
		t.Fatalf("expected one model")
	}
	if len(cfg.Projeto.Include) == 0 || cfg.Projeto.Include[0] != "src/main/java" {
		t.Fatalf("expected Java include defaults, got %v", cfg.Projeto.Include)
	}
}

func TestCarregarConfiguracaoSemModelosFalha(t *testing.T) {
	tempDir := t.TempDir()
	projectRoot := filepath.Join(tempDir, "project")
	if err := os.MkdirAll(projectRoot, 0o755); err != nil {
		t.Fatal(err)
	}

	configPath := filepath.Join(tempDir, "pipeline.json")
	content := `{
  "version": "1",
  "project": {
    "root": "./project"
  }
}`
	if err := os.WriteFile(configPath, []byte(content), 0o644); err != nil {
		t.Fatal(err)
	}

	if _, err := Carregar(configPath); err == nil {
		t.Fatalf("expected validation error")
	}
}

func TestCarregarConfiguracaoPreservaSalvarPromptsFalse(t *testing.T) {
	tempDir := t.TempDir()
	projectRoot := filepath.Join(tempDir, "project")
	if err := os.MkdirAll(projectRoot, 0o755); err != nil {
		t.Fatal(err)
	}

	configPath := filepath.Join(tempDir, "pipeline.json")
	content := `{
  "version": "1",
  "project": {
    "root": "./project"
  },
  "pipeline": {
    "save_prompts": false
  },
  "models": {
    "openai_main": {
      "provider": "openai_compatible",
      "model": "gpt-5.4",
      "base_url": "https://api.openai.com/v1",
      "api_key_env": "OPENAI_API_KEY"
    }
  },
  "metrics": [
    {
      "name": "unit-tests",
      "command": "echo ok"
    }
  ]
}`
	if err := os.WriteFile(configPath, []byte(content), 0o644); err != nil {
		t.Fatal(err)
	}

	cfg, err := Carregar(configPath)
	if err != nil {
		t.Fatalf("unexpected error: %v", err)
	}
	if cfg.Fluxo.SalvarPrompts {
		t.Fatalf("expected save_prompts to remain false")
	}
}

func TestCarregarConfiguracaoNormalizaReasoningMinimalParaLow(t *testing.T) {
	tempDir := t.TempDir()
	projectRoot := filepath.Join(tempDir, "project")
	if err := os.MkdirAll(projectRoot, 0o755); err != nil {
		t.Fatal(err)
	}

	configPath := filepath.Join(tempDir, "pipeline.json")
	content := `{
  "version": "1",
  "project": {
    "root": "./project"
  },
  "models": {
    "openai_main": {
      "provider": "openai_compatible",
      "model": "gpt-5.4",
      "base_url": "https://api.openai.com/v1",
      "api_key_env": "OPENAI_API_KEY",
      "reasoning_effort": "minimal"
    }
  },
  "metrics": [
    {
      "name": "unit-tests",
      "command": "echo ok"
    }
  ]
}`
	if err := os.WriteFile(configPath, []byte(content), 0o644); err != nil {
		t.Fatal(err)
	}

	cfg, err := Carregar(configPath)
	if err != nil {
		t.Fatalf("unexpected error: %v", err)
	}
	if cfg.Modelos["openai_main"].EsforcoRaciocinio != "low" {
		t.Fatalf("expected reasoning_effort to normalize to low, got %q", cfg.Modelos["openai_main"].EsforcoRaciocinio)
	}
}

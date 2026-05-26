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
	if cfg.Metricas[0].SegundosTimeout != 600 {
		t.Fatalf("expected default metric timeout 600, got %d", cfg.Metricas[0].SegundosTimeout)
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

func TestCarregarConfiguracaoRejeitaTimeoutInvalidoDeMetrica(t *testing.T) {
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
      "api_key_env": "OPENAI_API_KEY"
    }
  },
  "metrics": [
    {
      "name": "unit-tests",
      "command": "echo ok",
      "timeout_seconds": -1
    }
  ]
}`
	if err := os.WriteFile(configPath, []byte(content), 0o644); err != nil {
		t.Fatal(err)
	}

	if _, err := Carregar(configPath); err == nil {
		t.Fatalf("expected validation error for invalid metric timeout")
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

func TestCarregarConfiguracaoAceitaFallbacksDeMetricas(t *testing.T) {
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
      "model": "o4-mini",
      "base_url": "https://api.openai.com/v1",
      "api_key_env": "OPENAI_API_KEY"
    }
  },
  "metrics": [
    {
      "name": "pit",
      "command": "echo primary",
      "fallbacks": [
        {
          "name": "reuse-report",
          "command": "echo fallback",
          "expected_outputs": ["target/pit-reports/mutations.xml"]
        }
      ]
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
	if len(cfg.Metricas) != 1 || len(cfg.Metricas[0].Fallbacks) != 1 {
		t.Fatalf("fallbacks não carregados corretamente: %#v", cfg.Metricas)
	}
	if cfg.Metricas[0].Fallbacks[0].Nome != "reuse-report" {
		t.Fatalf("nome do fallback inesperado: %#v", cfg.Metricas[0].Fallbacks[0])
	}
}

func TestCarregarConfiguracaoSegundaFaseDefineModoExecucaoPadrao(t *testing.T) {
	tempDir := t.TempDir()
	projectRoot := filepath.Join(tempDir, "project")
	if err := os.MkdirAll(projectRoot, 0o755); err != nil {
		t.Fatal(err)
	}
	baselinePath := filepath.Join(tempDir, "wit_filtered.json")
	if err := os.WriteFile(baselinePath, []byte(`{"path":".","commitHash":"abc","classes":[]}`), 0o644); err != nil {
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
      "model": "o4-mini",
      "base_url": "https://api.openai.com/v1",
      "api_key_env": "OPENAI_API_KEY"
    }
  },
  "metrics": [
    {
      "name": "unit-tests",
      "command": "echo ok"
    }
  ],
  "phase_two": {
    "projects": [
      {
        "key": "commons-io",
        "root": "./project",
        "wit_analysis_path": "./wit_filtered.json"
      }
    ]
  }
}`
	if err := os.WriteFile(configPath, []byte(content), 0o644); err != nil {
		t.Fatal(err)
	}

	cfg, err := Carregar(configPath)
	if err != nil {
		t.Fatalf("unexpected error: %v", err)
	}
	if cfg.SegundaFase.ModoExecucao != "repair_1retry" {
		t.Fatalf("modo padrão inesperado: %q", cfg.SegundaFase.ModoExecucao)
	}
}

func TestCarregarConfiguracaoFalhaSemComandoNoFallback(t *testing.T) {
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
      "model": "o4-mini",
      "base_url": "https://api.openai.com/v1",
      "api_key_env": "OPENAI_API_KEY"
    }
  },
  "metrics": [
    {
      "name": "pit",
      "command": "echo primary",
      "fallbacks": [
        {
          "name": "reuse-report"
        }
      ]
    }
  ]
}`
	if err := os.WriteFile(configPath, []byte(content), 0o644); err != nil {
		t.Fatal(err)
	}

	if _, err := Carregar(configPath); err == nil {
		t.Fatalf("expected fallback validation error")
	}
}

func TestCarregarConfiguracaoAceitaSegundaFaseComDoisProjetos(t *testing.T) {
	tempDir := t.TempDir()
	projectRoot := filepath.Join(tempDir, "project")
	guavaRoot := filepath.Join(tempDir, "guava")
	commonsRoot := filepath.Join(tempDir, "commons-collections")
	for _, dir := range []string{projectRoot, guavaRoot, commonsRoot} {
		if err := os.MkdirAll(dir, 0o755); err != nil {
			t.Fatal(err)
		}
	}
	guavaBaseline := filepath.Join(tempDir, "guava-wit.json")
	commonsBaseline := filepath.Join(tempDir, "commons-wit.json")
	for _, path := range []string{guavaBaseline, commonsBaseline} {
		if err := os.WriteFile(path, []byte(`{"run_id":"x","analyses":[]}`), 0o644); err != nil {
			t.Fatal(err)
		}
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
      "model": "o4-mini",
      "base_url": "https://api.openai.com/v1",
      "api_key_env": "OPENAI_API_KEY"
    }
  },
  "metrics": [
    {
      "name": "unit-tests",
      "command": "echo ok"
    }
  ],
  "phase_two": {
    "visualization_title": "fase dois",
    "projects": [
      {
        "key": "guava",
        "root": "./guava",
        "wit_analysis_path": "./guava-wit.json",
        "target_containers": ["com.google.common.collect.ImmutableList"]
      },
      {
        "key": "commons-collections",
        "label": "Apache Commons Collections",
        "root": "./commons-collections",
        "wit_analysis_path": "./commons-wit.json",
        "test_framework": "junit4"
      }
    ]
  }
}`
	if err := os.WriteFile(configPath, []byte(content), 0o644); err != nil {
		t.Fatal(err)
	}

	cfg, err := Carregar(configPath)
	if err != nil {
		t.Fatalf("unexpected error: %v", err)
	}
	if len(cfg.SegundaFase.Projetos) != 2 {
		t.Fatalf("esperava dois projetos na segunda fase, recebi %d", len(cfg.SegundaFase.Projetos))
	}
	if cfg.SegundaFase.Projetos[0].Rotulo != "guava" {
		t.Fatalf("rotulo default inesperado: %#v", cfg.SegundaFase.Projetos[0])
	}
	if cfg.SegundaFase.Projetos[0].TestFramework != "infer" {
		t.Fatalf("test framework deveria herdar o padrão do projeto, recebi %q", cfg.SegundaFase.Projetos[0].TestFramework)
	}
	if len(cfg.SegundaFase.Projetos[0].ContainersAlvo) != 1 || cfg.SegundaFase.Projetos[0].ContainersAlvo[0] != "com.google.common.collect.ImmutableList" {
		t.Fatalf("target_containers não foi carregado corretamente: %#v", cfg.SegundaFase.Projetos[0].ContainersAlvo)
	}
}

func TestCarregarConfiguracaoFalhaComProjetoDuplicadoNaSegundaFase(t *testing.T) {
	tempDir := t.TempDir()
	projectRoot := filepath.Join(tempDir, "project")
	targetRoot := filepath.Join(tempDir, "guava")
	if err := os.MkdirAll(projectRoot, 0o755); err != nil {
		t.Fatal(err)
	}
	if err := os.MkdirAll(targetRoot, 0o755); err != nil {
		t.Fatal(err)
	}
	baseline := filepath.Join(tempDir, "guava-wit.json")
	if err := os.WriteFile(baseline, []byte(`{"run_id":"x","analyses":[]}`), 0o644); err != nil {
		t.Fatal(err)
	}

	configPath := filepath.Join(tempDir, "pipeline.json")
	content := `{
  "version": "1",
  "project": { "root": "./project" },
  "models": {
    "openai_main": {
      "provider": "openai_compatible",
      "model": "o4-mini",
      "base_url": "https://api.openai.com/v1",
      "api_key_env": "OPENAI_API_KEY"
    }
  },
  "metrics": [{ "name": "unit-tests", "command": "echo ok" }],
  "phase_two": {
    "projects": [
      { "key": "guava", "root": "./guava", "wit_analysis_path": "./guava-wit.json" },
      { "key": "guava", "root": "./guava", "wit_analysis_path": "./guava-wit.json" }
    ]
  }
}`
	if err := os.WriteFile(configPath, []byte(content), 0o644); err != nil {
		t.Fatal(err)
	}

	if _, err := Carregar(configPath); err == nil {
		t.Fatalf("esperava erro para chave duplicada na segunda fase")
	}
}

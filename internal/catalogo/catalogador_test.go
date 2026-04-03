package catalogo

import (
	"os"
	"path/filepath"
	"testing"

	"github.com/marceloamorim/witup-llm/internal/dominio"
)

func TestCatalogarMetodosJavaApenas(t *testing.T) {
	tempDir := t.TempDir()
	sourceDir := filepath.Join(tempDir, "src", "main", "java", "sample")
	if err := os.MkdirAll(sourceDir, 0o755); err != nil {
		t.Fatal(err)
	}

	javaFile := filepath.Join(sourceDir, "Example.java")
	javaSource := `package sample;

public class Example {
    public void ok(int value) {
        if (value < 0) {
            throw new IllegalArgumentException();
        }
    }

    public String name() {
        return "ok";
    }
}`
	if err := os.WriteFile(javaFile, []byte(javaSource), 0o644); err != nil {
		t.Fatal(err)
	}

	ignoredFile := filepath.Join(sourceDir, "ignored.txt")
	if err := os.WriteFile(ignoredFile, []byte("this file must not be cataloged"), 0o644); err != nil {
		t.Fatal(err)
	}

	cataloger := NovoCatalogador(dominio.ConfigProjeto{
		Raiz:    tempDir,
		Include: []string{"src/main/java"},
	})
	methods, err := cataloger.Catalogar()
	if err != nil {
		t.Fatalf("Catalog returned unexpected error: %v", err)
	}
	if len(methods) != 2 {
		t.Fatalf("expected 2 Java methods, got %d", len(methods))
	}
	if methods[0].NomeContainer != "sample.Example" {
		t.Fatalf("unexpected container name: %s", methods[0].NomeContainer)
	}
}

func TestCarregarVisaoGeralRetornaConteudoDoArquivo(t *testing.T) {
	tempDir := t.TempDir()
	overviewPath := filepath.Join(tempDir, "README.md")
	if err := os.WriteFile(overviewPath, []byte("project overview"), 0o644); err != nil {
		t.Fatal(err)
	}

	cataloger := NovoCatalogador(dominio.ConfigProjeto{
		Raiz:         tempDir,
		OverviewFile: overviewPath,
	})
	overview, err := cataloger.CarregarVisaoGeral()
	if err != nil {
		t.Fatalf("LoadOverview returned unexpected error: %v", err)
	}
	if overview != "project overview" {
		t.Fatalf("unexpected overview content: %q", overview)
	}
}

func TestCatalogarIgnoraBlocosCatchESynchronized(t *testing.T) {
	tempDir := t.TempDir()
	sourceDir := filepath.Join(tempDir, "src", "main", "java", "sample")
	if err := os.MkdirAll(sourceDir, 0o755); err != nil {
		t.Fatal(err)
	}

	javaFile := filepath.Join(sourceDir, "Example.java")
	javaSource := `package sample;

public class Example {
    public void ok() {
        synchronized (this) {
            try {
                work();
            } catch (RuntimeException ex) {
                throw ex;
            }
        }
    }

    void work() {
    }
}`
	if err := os.WriteFile(javaFile, []byte(javaSource), 0o644); err != nil {
		t.Fatal(err)
	}

	cataloger := NovoCatalogador(dominio.ConfigProjeto{
		Raiz:    tempDir,
		Include: []string{"src/main/java"},
	})
	methods, err := cataloger.Catalogar()
	if err != nil {
		t.Fatalf("Catalog returned unexpected error: %v", err)
	}
	if len(methods) != 2 {
		t.Fatalf("expected 2 real methods, got %d", len(methods))
	}
}

func TestCatalogarNaoExcluiProjetoSoPorqueARaizContemGenerated(t *testing.T) {
	tempDir := t.TempDir()
	projectRoot := filepath.Join(tempDir, "generated", "repos", "sample")
	sourceDir := filepath.Join(projectRoot, "src", "main", "java", "sample")
	if err := os.MkdirAll(sourceDir, 0o755); err != nil {
		t.Fatal(err)
	}

	javaFile := filepath.Join(sourceDir, "Example.java")
	javaSource := `package sample;

public class Example {
    public void ok() {
    }
}`
	if err := os.WriteFile(javaFile, []byte(javaSource), 0o644); err != nil {
		t.Fatal(err)
	}

	cataloger := NovoCatalogador(dominio.ConfigProjeto{
		Raiz:    projectRoot,
		Include: []string{"src/main/java"},
		Exclude: []string{"generated", "target", "build"},
	})
	methods, err := cataloger.Catalogar()
	if err != nil {
		t.Fatalf("Catalog returned unexpected error: %v", err)
	}
	if len(methods) != 1 {
		t.Fatalf("expected 1 Java method even with root under generated/, got %d", len(methods))
	}
}

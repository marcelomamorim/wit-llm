package aplicacao

import (
	"bytes"
	"io"
	"os"
	"strings"
	"testing"
)

func TestPrincipalAjudaListaComandosDeExperimento(t *testing.T) {
	output := capturarSaidaPadrao(t, func() {
		if code := Principal([]string{"help"}); code != 0 {
			t.Fatalf("expected help exit code 0, got %d", code)
		}
	})

	if !strings.Contains(output, "witup-llm :: experimentos com exception paths na JVM") {
		t.Fatalf("expected help output to contain CLI banner, got:\n%s", output)
	}
	for _, command := range []string{"ingest-witup", "browse-data", "analyze-agentic", "compare-sources", "run-experiment"} {
		if !strings.Contains(output, command) {
			t.Fatalf("expected help output to mention %q, got:\n%s", command, output)
		}
	}
}

func TestBannerEhSuprimidoQuandoAmbienteSolicita(t *testing.T) {
	t.Setenv("WITUP_NO_BANNER", "1")
	output := capturarSaidaPadrao(t, func() {
		_ = Principal([]string{"help"})
	})
	if strings.Contains(output, "__        ___") {
		t.Fatalf("expected banner to be suppressed by env, got:\n%s", output)
	}
	if strings.Contains(output, "witup-llm :: experimentos com exception paths na JVM") {
		t.Fatalf("expected banner to be suppressed by env, got:\n%s", output)
	}
}

func capturarSaidaPadrao(t *testing.T, executar func()) string {
	t.Helper()

	original := os.Stdout
	reader, writer, err := os.Pipe()
	if err != nil {
		t.Fatalf("create stdout pipe: %v", err)
	}
	os.Stdout = writer
	defer func() {
		os.Stdout = original
	}()

	executar()

	if err := writer.Close(); err != nil {
		t.Fatalf("close writer: %v", err)
	}
	var buffer bytes.Buffer
	if _, err := io.Copy(&buffer, reader); err != nil {
		t.Fatalf("copy stdout: %v", err)
	}
	return buffer.String()
}

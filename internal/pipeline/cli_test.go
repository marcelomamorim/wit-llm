package pipeline

import (
	"bytes"
	"io"
	"os"
	"path/filepath"
	"strings"
	"testing"
)

func TestMainHelpListsExperimentCommands(t *testing.T) {
	output := captureStdout(t, func() {
		if code := Main([]string{"help"}); code != 0 {
			t.Fatalf("expected help exit code 0, got %d", code)
		}
	})

	if !strings.Contains(output, "WITUP") {
		t.Fatalf("expected help output to contain CLI banner, got:\n%s", output)
	}
	for _, command := range []string{"ingest-witup", "analyze-agentic", "compare-sources", "run-experiment"} {
		if !strings.Contains(output, command) {
			t.Fatalf("expected help output to mention %q, got:\n%s", command, output)
		}
	}
}

func TestResolveWITUPPathPrefersExplicitPath(t *testing.T) {
	explicit := filepath.Join(t.TempDir(), "wit.json")
	resolved := resolveWITUPPath(explicit, "commons-io", "resources/wit-replication-package/data/output", "wit.json")
	if resolved != explicit {
		t.Fatalf("expected explicit path to win, got %q", resolved)
	}
}

func TestBannerIsSuppressedWhenEnvironmentRequestsIt(t *testing.T) {
	t.Setenv("WITUP_NO_BANNER", "1")
	output := captureStdout(t, func() {
		_ = Main([]string{"help"})
	})
	if strings.Contains(output, "__        ___") {
		t.Fatalf("expected banner to be suppressed by env, got:\n%s", output)
	}
}

func captureStdout(t *testing.T, run func()) string {
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

	run()

	if err := writer.Close(); err != nil {
		t.Fatalf("close writer: %v", err)
	}
	var buffer bytes.Buffer
	if _, err := io.Copy(&buffer, reader); err != nil {
		t.Fatalf("copy stdout: %v", err)
	}
	return buffer.String()
}

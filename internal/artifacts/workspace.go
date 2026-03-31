package artifacts

import (
	"encoding/json"
	"fmt"
	"os"
	"path/filepath"
	"strings"
	"time"
)

// Workspace groups output folders for one pipeline run.
type Workspace struct {
	Root        string
	Prompts     string
	Responses   string
	Tests       string
	Sources     string
	Comparisons string
	Variants    string
	Traces      string
}

// NewWorkspace creates all run folders and returns their paths.
func NewWorkspace(outputRoot, runID string) (*Workspace, error) {
	root := filepath.Join(outputRoot, runID)
	w := &Workspace{
		Root:        root,
		Prompts:     filepath.Join(root, "prompts"),
		Responses:   filepath.Join(root, "responses"),
		Tests:       filepath.Join(root, "generated-tests"),
		Sources:     filepath.Join(root, "sources"),
		Comparisons: filepath.Join(root, "comparisons"),
		Variants:    filepath.Join(root, "variants"),
		Traces:      filepath.Join(root, "traces"),
	}
	for _, p := range []string{
		w.Root,
		w.Prompts,
		w.Responses,
		w.Tests,
		w.Sources,
		w.Comparisons,
		w.Variants,
		w.Traces,
	} {
		if err := os.MkdirAll(p, 0o755); err != nil {
			return nil, fmt.Errorf("create workspace directory %q: %w", p, err)
		}
	}
	return w, nil
}

// NewRunID generates a sortable run identifier with microsecond precision.
func NewRunID(label string) string {
	now := time.Now().UTC().Format("20060102T150405.000000Z")
	return now + "-" + Slugify(label)
}

// Slugify creates deterministic filesystem-safe labels.
func Slugify(value string) string {
	v := strings.ToLower(value)
	b := strings.Builder{}
	lastDash := false
	for _, r := range v {
		if (r >= 'a' && r <= 'z') || (r >= '0' && r <= '9') {
			b.WriteRune(r)
			lastDash = false
			continue
		}
		if !lastDash {
			b.WriteByte('-')
			lastDash = true
		}
	}
	slug := strings.Trim(b.String(), "-")
	if slug == "" {
		return "run"
	}
	return slug
}

// WriteJSON marshals payload with stable formatting.
func WriteJSON(path string, payload interface{}) error {
	if err := os.MkdirAll(filepath.Dir(path), 0o755); err != nil {
		return fmt.Errorf("create json directory: %w", err)
	}
	data, err := json.MarshalIndent(payload, "", "  ")
	if err != nil {
		return fmt.Errorf("marshal json: %w", err)
	}
	data = append(data, '\n')
	if err := os.WriteFile(path, data, 0o644); err != nil {
		return fmt.Errorf("write json %q: %w", path, err)
	}
	return nil
}

// ReadJSON loads JSON artifact into the provided destination pointer.
func ReadJSON(path string, dst interface{}) error {
	data, err := os.ReadFile(path)
	if err != nil {
		return fmt.Errorf("read json %q: %w", path, err)
	}
	if err := json.Unmarshal(data, dst); err != nil {
		return fmt.Errorf("parse json %q: %w", path, err)
	}
	return nil
}

// WriteText writes UTF-8 text files creating directories when needed.
func WriteText(path, content string) error {
	if err := os.MkdirAll(filepath.Dir(path), 0o755); err != nil {
		return fmt.Errorf("create text directory: %w", err)
	}
	if err := os.WriteFile(path, []byte(content), 0o644); err != nil {
		return fmt.Errorf("write text %q: %w", path, err)
	}
	return nil
}

// SafeRelativePath blocks generated paths from escaping target directories.
func SafeRelativePath(raw string) (string, error) {
	clean := filepath.Clean(raw)
	if filepath.IsAbs(clean) {
		return "", fmt.Errorf("generated file path must be relative, got %q", raw)
	}
	if clean == ".." || strings.HasPrefix(clean, "../") {
		return "", fmt.Errorf("generated file path cannot escape output directory: %q", raw)
	}
	return clean, nil
}

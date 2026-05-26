package artefatos

import "testing"

func TestSafeRelativePath(t *testing.T) {
	if _, err := CaminhoRelativoSeguro("../escape"); err == nil {
		t.Fatalf("expected path traversal error")
	}
	if _, err := CaminhoRelativoSeguro(`..\escape`); err == nil {
		t.Fatalf("expected Windows-style path traversal error")
	}
	if _, err := CaminhoRelativoSeguro("ok/path.txt"); err != nil {
		t.Fatalf("unexpected error: %v", err)
	}
}

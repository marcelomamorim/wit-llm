package artifacts

import "testing"

func TestSafeRelativePath(t *testing.T) {
	if _, err := SafeRelativePath("../escape"); err == nil {
		t.Fatalf("expected path traversal error")
	}
	if _, err := SafeRelativePath("ok/path.txt"); err != nil {
		t.Fatalf("unexpected error: %v", err)
	}
}

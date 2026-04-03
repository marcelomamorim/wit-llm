package artefatos

import (
	"os"
	"path/filepath"
	"testing"
)

func TestCopiarDiretorioFiltradoIgnoraSegmentosExcluidos(t *testing.T) {
	origem := t.TempDir()
	destino := filepath.Join(t.TempDir(), "destino")

	if err := os.MkdirAll(filepath.Join(origem, ".git"), 0o755); err != nil {
		t.Fatalf("mkdir .git: %v", err)
	}
	if err := os.MkdirAll(filepath.Join(origem, "src", "main"), 0o755); err != nil {
		t.Fatalf("mkdir src: %v", err)
	}
	if err := os.WriteFile(filepath.Join(origem, ".git", "config"), []byte("git"), 0o644); err != nil {
		t.Fatalf("write git config: %v", err)
	}
	if err := os.WriteFile(filepath.Join(origem, "src", "main", "App.java"), []byte("class App {}"), 0o755); err != nil {
		t.Fatalf("write app.java: %v", err)
	}

	if err := CopiarDiretorioFiltrado(origem, destino, []string{".git"}); err != nil {
		t.Fatalf("copiar diretório filtrado: %v", err)
	}

	if _, err := os.Stat(filepath.Join(destino, ".git", "config")); !os.IsNotExist(err) {
		t.Fatalf("esperava .git ignorado, recebi err=%v", err)
	}
	info, err := os.Stat(filepath.Join(destino, "src", "main", "App.java"))
	if err != nil {
		t.Fatalf("esperava arquivo copiado: %v", err)
	}
	if info.Mode().Perm() != 0o755 {
		t.Fatalf("esperava preservar permissão 0755, recebi %v", info.Mode().Perm())
	}
}

func TestCopiarDiretorioNoDestinoReplicaArvoreCompleta(t *testing.T) {
	origem := t.TempDir()
	destino := filepath.Join(t.TempDir(), "destino")

	if err := os.MkdirAll(filepath.Join(origem, "src", "test"), 0o755); err != nil {
		t.Fatalf("mkdir nested: %v", err)
	}
	conteudo := []byte("class ExampleTest {}")
	caminhoOrigem := filepath.Join(origem, "src", "test", "ExampleTest.java")
	if err := os.WriteFile(caminhoOrigem, conteudo, 0o644); err != nil {
		t.Fatalf("write nested file: %v", err)
	}

	if err := CopiarDiretorioNoDestino(origem, destino); err != nil {
		t.Fatalf("copiar diretório inteiro: %v", err)
	}

	dados, err := os.ReadFile(filepath.Join(destino, "src", "test", "ExampleTest.java"))
	if err != nil {
		t.Fatalf("ler cópia: %v", err)
	}
	if string(dados) != string(conteudo) {
		t.Fatalf("conteúdo copiado inesperado: %q", string(dados))
	}
}

func TestCopiarDiretorioFiltradoValidaOrigem(t *testing.T) {
	arquivo := filepath.Join(t.TempDir(), "arquivo.txt")
	if err := os.WriteFile(arquivo, []byte("x"), 0o644); err != nil {
		t.Fatalf("write file: %v", err)
	}

	if err := CopiarDiretorioFiltrado(arquivo, filepath.Join(t.TempDir(), "destino"), nil); err == nil {
		t.Fatalf("esperava erro quando a origem não é diretório")
	}
}

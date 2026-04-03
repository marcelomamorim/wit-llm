package artefatos

import (
	"fmt"
	"os"
	"path/filepath"
	"strings"
	"time"
)

// EspacoTrabalho descreve o layout de diretórios usado por uma única execução.
//
// Cada campo aponta para uma pasta específica de artefatos. Isso mantém o fluxo
// reprodutível e evita que a aplicação espalhe convenções de caminho por vários
// pacotes.
type EspacoTrabalho struct {
	Raiz        string
	Prompts     string
	Respostas   string
	Testes      string
	Fontes      string
	Comparacoes string
	Variantes   string
	Rastreios   string
}

// NovoEspacoTrabalho materializa o layout padrão de saída de uma execução.
//
// O diretório raiz é composto por `outputRoot/runID`, e a função garante a
// criação de todas as subpastas esperadas antes que o restante da pipeline
// comece a persistir artefatos.
func NovoEspacoTrabalho(outputRoot, runID string) (*EspacoTrabalho, error) {
	root := filepath.Join(outputRoot, runID)
	w := &EspacoTrabalho{
		Raiz:        root,
		Prompts:     filepath.Join(root, "prompts"),
		Respostas:   filepath.Join(root, "responses"),
		Testes:      filepath.Join(root, "generated-tests"),
		Fontes:      filepath.Join(root, "sources"),
		Comparacoes: filepath.Join(root, "comparisons"),
		Variantes:   filepath.Join(root, "variants"),
		Rastreios:   filepath.Join(root, "traces"),
	}
	for _, p := range []string{
		w.Raiz,
		w.Prompts,
		w.Respostas,
		w.Testes,
		w.Fontes,
		w.Comparacoes,
		w.Variantes,
		w.Rastreios,
	} {
		if err := os.MkdirAll(p, 0o755); err != nil {
			return nil, fmt.Errorf("ao criar o diretório de workspace %q: %w", p, err)
		}
	}
	return w, nil
}

// NovoIDExecucao gera um identificador ordenável de execução com precisão de microssegundos.
func NovoIDExecucao(label string) string {
	now := time.Now().UTC().Format("20060102T150405.000000Z")
	return now + "-" + Slugificar(label)
}

// Slugificar cria rótulos determinísticos e seguros para uso no sistema de arquivos.
func Slugificar(value string) string {
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

// EscreverTexto grava arquivos de texto UTF-8 criando diretórios quando necessário.
func EscreverTexto(path, content string) error {
	if err := os.MkdirAll(filepath.Dir(path), 0o755); err != nil {
		return fmt.Errorf("ao criar o diretório do texto: %w", err)
	}
	if err := os.WriteFile(path, []byte(content), 0o644); err != nil {
		return fmt.Errorf("ao gravar o texto %q: %w", path, err)
	}
	return nil
}

// CaminhoRelativoSeguro impede que caminhos relativos escapem do diretório de destino.
func CaminhoRelativoSeguro(raw string) (string, error) {
	clean := filepath.Clean(raw)
	normalized := strings.ReplaceAll(filepath.ToSlash(clean), "\\", "/")
	if filepath.IsAbs(clean) {
		return "", fmt.Errorf("o caminho de arquivo gerado deve ser relativo, recebido %q", raw)
	}
	if normalized == ".." || strings.HasPrefix(normalized, "../") {
		return "", fmt.Errorf("o caminho do arquivo gerado não pode escapar do diretório de saída: %q", raw)
	}
	return clean, nil
}

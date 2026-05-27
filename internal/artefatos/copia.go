package artefatos

import (
	"errors"
	"fmt"
	"io"
	"log/slog"
	"os"
	"path/filepath"
	"strings"
	"syscall"
)

// CopiarDiretorioFiltrado replica uma árvore de diretórios ignorando caminhos
// relativos explicitamente excluídos a partir da raiz copiada.
func CopiarDiretorioFiltrado(origem, destino string, segmentosExcluidos []string) error {
	info, err := os.Stat(origem)
	if err != nil {
		return fmt.Errorf("ao inspecionar diretório de origem %q: %w", origem, err)
	}
	if !info.IsDir() {
		return fmt.Errorf("a origem %q deve ser um diretório", origem)
	}

	excluidos := make(map[string]struct{}, len(segmentosExcluidos))
	for _, segmento := range segmentosExcluidos {
		segmento = strings.TrimSpace(segmento)
		if segmento == "" {
			continue
		}
		excluidos[filepath.Clean(segmento)] = struct{}{}
	}

	return filepath.WalkDir(origem, func(caminho string, entrada os.DirEntry, err error) error {
		if err != nil {
			return err
		}

		relativo, err := filepath.Rel(origem, caminho)
		if err != nil {
			return fmt.Errorf("ao calcular caminho relativo de %q: %w", caminho, err)
		}
		if relativo == "." {
			return os.MkdirAll(destino, 0o755)
		}
		if contemCaminhoExcluido(relativo, excluidos) {
			if entrada.IsDir() {
				return filepath.SkipDir
			}
			return nil
		}

		destinoAtual := filepath.Join(destino, relativo)
		info, err := entrada.Info()
		if err != nil {
			return err
		}
		if entrada.IsDir() {
			return os.MkdirAll(destinoAtual, info.Mode().Perm())
		}

		if err := os.MkdirAll(filepath.Dir(destinoAtual), 0o755); err != nil {
			return fmt.Errorf("ao criar diretório de destino %q: %w", filepath.Dir(destinoAtual), err)
		}
		if err := copiarArquivo(caminho, destinoAtual, info.Mode().Perm()); err != nil {
			// Arquivos inacessíveis via bind-mount Docker (ex: nomes com espaço no macOS)
			// retornam EDEADLK. São duplicatas acidentais — pular sem abortar.
			if errors.Is(err, syscall.EDEADLK) {
				slog.Warn("arquivo ignorado (inacessível via bind-mount)", "caminho", caminho)
				return nil
			}
			return err
		}
		return nil
	})
}

// CopiarDiretorioNoDestino replica uma árvore inteira preservando caminhos
// relativos a partir do diretório informado.
func CopiarDiretorioNoDestino(origem, destino string) error {
	info, err := os.Stat(origem)
	if err != nil {
		return fmt.Errorf("ao inspecionar origem %q: %w", origem, err)
	}
	if !info.IsDir() {
		return fmt.Errorf("a origem %q deve ser um diretório", origem)
	}

	return filepath.WalkDir(origem, func(caminho string, entrada os.DirEntry, err error) error {
		if err != nil {
			return err
		}
		relativo, err := filepath.Rel(origem, caminho)
		if err != nil {
			return fmt.Errorf("ao calcular caminho relativo de %q: %w", caminho, err)
		}
		if relativo == "." {
			return os.MkdirAll(destino, 0o755)
		}

		destinoAtual := filepath.Join(destino, relativo)
		info, err := entrada.Info()
		if err != nil {
			return err
		}
		if entrada.IsDir() {
			return os.MkdirAll(destinoAtual, info.Mode().Perm())
		}

		if err := os.MkdirAll(filepath.Dir(destinoAtual), 0o755); err != nil {
			return fmt.Errorf("ao criar diretório de destino %q: %w", filepath.Dir(destinoAtual), err)
		}
		return copiarArquivo(caminho, destinoAtual, info.Mode().Perm())
	})
}

// contemCaminhoExcluido informa se o caminho relativo corresponde a um item
// explicitamente ignorado na raiz do checkout ou a um subcaminho dele.
func contemCaminhoExcluido(relativo string, excluidos map[string]struct{}) bool {
	relativo = filepath.ToSlash(filepath.Clean(relativo))
	for item := range excluidos {
		item = filepath.ToSlash(filepath.Clean(item))
		if item == "." || item == "" {
			continue
		}
		if relativo == item || strings.HasPrefix(relativo, item+"/") {
			return true
		}
	}
	return false
}

// copiarArquivo replica um arquivo individual preservando o modo recebido do
// diretório de origem.
//
// Tenta hardlink primeiro: é instantâneo, não lê conteúdo e não aciona o
// Gatekeeper do macOS em arquivos com atributo de quarantine. Faz fallback para
// cópia de conteúdo caso o hardlink falhe (filesystems diferentes, etc.).
func copiarArquivo(origem, destino string, modo os.FileMode) error {
	if err := os.Link(origem, destino); err == nil {
		return nil
	}

	arquivoOrigem, err := os.Open(origem)
	if err != nil {
		return fmt.Errorf("ao abrir arquivo de origem %q: %w", origem, err)
	}
	defer arquivoOrigem.Close()

	arquivoDestino, err := os.OpenFile(destino, os.O_CREATE|os.O_TRUNC|os.O_WRONLY, modo)
	if err != nil {
		return fmt.Errorf("ao abrir arquivo de destino %q: %w", destino, err)
	}
	defer arquivoDestino.Close()

	if _, err := io.Copy(arquivoDestino, arquivoOrigem); err != nil {
		return fmt.Errorf("ao copiar %q para %q: %w", origem, destino, err)
	}
	return nil
}

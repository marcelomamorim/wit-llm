package artefatos

import (
	"fmt"
	"io"
	"os"
	"path/filepath"
	"strings"
)

// CopiarDiretorioFiltrado replica uma árvore de diretórios ignorando segmentos
// específicos do caminho relativo.
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
		if contemSegmentoExcluido(relativo, excluidos) {
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
		return copiarArquivo(caminho, destinoAtual, info.Mode().Perm())
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

// contemSegmentoExcluido informa se algum segmento do caminho relativo faz
// parte da lista de diretórios que devem ser ignorados.
func contemSegmentoExcluido(relativo string, excluidos map[string]struct{}) bool {
	partes := strings.Split(filepath.Clean(relativo), string(filepath.Separator))
	for _, parte := range partes {
		if _, ok := excluidos[parte]; ok {
			return true
		}
	}
	return false
}

// copiarArquivo replica um arquivo individual preservando o modo recebido do
// diretório de origem.
func copiarArquivo(origem, destino string, modo os.FileMode) error {
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

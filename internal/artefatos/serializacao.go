package artefatos

import (
	"encoding/json"
	"fmt"
	"os"
	"path/filepath"
)

// EscreverJSON serializa um payload em JSON com formatação estável.
func EscreverJSON(path string, payload interface{}) error {
	if err := os.MkdirAll(filepath.Dir(path), 0o755); err != nil {
		return fmt.Errorf("ao criar o diretório do JSON: %w", err)
	}
	data, err := json.MarshalIndent(payload, "", "  ")
	if err != nil {
		return fmt.Errorf("ao serializar JSON: %w", err)
	}
	data = append(data, '\n')
	if err := os.WriteFile(path, data, 0o644); err != nil {
		return fmt.Errorf("ao gravar o JSON %q: %w", path, err)
	}
	return nil
}

// LerJSON carrega um artefato JSON no destino informado.
func LerJSON(path string, dst interface{}) error {
	data, err := os.ReadFile(path)
	if err != nil {
		return fmt.Errorf("ao ler o JSON %q: %w", path, err)
	}
	if err := json.Unmarshal(data, dst); err != nil {
		return fmt.Errorf("ao interpretar o JSON %q: %w", path, err)
	}
	return nil
}

package agentes

import (
	"bytes"
	"embed"
	"fmt"
	"strings"
	"text/template"
)

//go:embed prompt_templates/*.tmpl
var promptTemplatesFS embed.FS

var promptTemplates = template.Must(template.ParseFS(promptTemplatesFS, "prompt_templates/*.tmpl"))

func renderizarPromptTemplate(nome string, dados interface{}) (string, error) {
	var buffer bytes.Buffer
	if err := promptTemplates.ExecuteTemplate(&buffer, nome, dados); err != nil {
		return "", fmt.Errorf("ao renderizar prompt %s: %w", nome, err)
	}
	return strings.TrimSpace(buffer.String()), nil
}

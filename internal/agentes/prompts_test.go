package agentes

import (
	"strings"
	"testing"

	"github.com/marceloamorim/witup-llm/internal/dominio"
)

func TestPromptsAgentesSaoRenderizadosAPartirDeTemplates(t *testing.T) {
	metodos := []dominio.DescritorMetodo{{
		IDMetodo:       "sample.Example:run:10",
		CaminhoArquivo: "src/main/java/sample/Example.java",
		NomeContainer:  "sample.Example",
		NomeMetodo:     "run",
		Assinatura:     "sample.Example.run(java.lang.String)",
		LinhaInicial:   10,
		Origem:         "public void run(String value) { }",
	}}

	promptArqueologo, err := construirPromptUsuarioArqueologoProjeto("visão geral", metodos)
	if err != nil {
		t.Fatalf("renderizar prompt do arqueólogo: %v", err)
	}
	if !strings.Contains(promptArqueologo, "Manifesto dos métodos-alvo") || !strings.Contains(promptArqueologo, "sample.Example.run(java.lang.String)") {
		t.Fatalf("prompt do arqueólogo não foi renderizado como esperado: %s", promptArqueologo)
	}

	promptCetico, err := construirPromptUsuarioCetico(metodos[0], map[string]interface{}{"accepted_expaths": []interface{}{"path-1"}})
	if err != nil {
		t.Fatalf("renderizar prompt do cético: %v", err)
	}
	if !strings.Contains(promptCetico, "Candidatos do extrator") || !strings.Contains(promptCetico, "\"accepted_expaths\"") {
		t.Fatalf("prompt do cético não foi renderizado como esperado: %s", promptCetico)
	}
}

func TestRenderizarPromptTemplateRetornaErroParaTemplateInexistente(t *testing.T) {
	if _, err := renderizarPromptTemplate("nao_existe.tmpl", nil); err == nil {
		t.Fatalf("esperava erro para template inexistente")
	}
}

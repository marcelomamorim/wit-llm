package agentes

import (
	"os"
	"path/filepath"
	"testing"

	"github.com/marceloamorim/witup-llm/internal/artefatos"
	"github.com/marceloamorim/witup-llm/internal/dominio"
)

func TestPersistirEtapaAgenteEProjetoCompartilhamPersistencia(t *testing.T) {
	espaco, err := artefatos.NovoEspacoTrabalho(t.TempDir(), "run-1")
	if err != nil {
		t.Fatalf("novo espaço: %v", err)
	}
	metodo := dominio.DescritorMetodo{IDMetodo: "sample.Example.run(String):10"}
	saida := map[string]interface{}{"summary": "ok"}

	etapaMetodo, err := persistirEtapaAgente(espaco, true, 0, metodo, dominio.PapelAgenteExtrator, "prompt", "prev", "resp", "raw", saida)
	if err != nil {
		t.Fatalf("persistir etapa de método: %v", err)
	}
	if etapaMetodo.ArquivoPrompt == "" || etapaMetodo.ArquivoSaida == "" {
		t.Fatalf("esperava arquivos persistidos no rastreio do método: %#v", etapaMetodo)
	}
	if _, err := os.Stat(etapaMetodo.ArquivoPrompt); err != nil {
		t.Fatalf("prompt do método deveria existir: %v", err)
	}

	etapaProjeto, err := persistirEtapaProjeto(espaco, true, dominio.PapelAgenteArqueologo, "prompt projeto", "", "resp-projeto", "raw", saida)
	if err != nil {
		t.Fatalf("persistir etapa de projeto: %v", err)
	}
	if filepath.Base(etapaProjeto.ArquivoSaida) != "agentic-project-archaeologist.json" {
		t.Fatalf("nome do artefato de projeto inesperado: %q", etapaProjeto.ArquivoSaida)
	}
}

func TestMetadadosCaminhoAgenteRegistramRevisaoCetica(t *testing.T) {
	metadados := metadadosCaminhoAgente(map[string]interface{}{
		"accepted_expaths": []interface{}{"x"},
		"review_notes":     []interface{}{"nota"},
	})
	if metadados["accepted_by_skeptic"] != true {
		t.Fatalf("esperava accepted_by_skeptic=true: %#v", metadados)
	}
}

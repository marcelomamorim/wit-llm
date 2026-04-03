package artefatos

import (
	"path/filepath"
	"strings"
	"testing"
)

func TestNovoEspacoTrabalhoESerializacaoHelpers(t *testing.T) {
	espaco, err := NovoEspacoTrabalho(t.TempDir(), "run-1")
	if err != nil {
		t.Fatalf("novo espaço de trabalho: %v", err)
	}
	for _, caminho := range []string{espaco.Raiz, espaco.Prompts, espaco.Respostas, espaco.Testes, espaco.Fontes, espaco.Comparacoes, espaco.Variantes, espaco.Rastreios} {
		if !strings.HasPrefix(caminho, espaco.Raiz) {
			t.Fatalf("esperava subdiretório do workspace, recebi %q", caminho)
		}
	}

	payload := map[string]string{"ok": "true"}
	caminhoJSON := filepath.Join(espaco.Raiz, "payload.json")
	if err := EscreverJSON(caminhoJSON, payload); err != nil {
		t.Fatalf("escrever json: %v", err)
	}
	var lido map[string]string
	if err := LerJSON(caminhoJSON, &lido); err != nil {
		t.Fatalf("ler json: %v", err)
	}
	if lido["ok"] != "true" {
		t.Fatalf("payload lido inesperado: %#v", lido)
	}
}

func TestSlugificarENovoIDExecucao(t *testing.T) {
	if got := Slugificar(" Examinar::Método / 42 "); got != "examinar-m-todo-42" {
		t.Fatalf("slug inesperado: %q", got)
	}
	if got := Slugificar("***"); got != "run" {
		t.Fatalf("slug vazio deveria virar run: %q", got)
	}
	if id := NovoIDExecucao("Meu Teste"); !strings.HasSuffix(id, "-meu-teste") {
		t.Fatalf("id de execução inesperado: %q", id)
	}
}

func TestEscreverTextoECaminhoRelativoSeguro(t *testing.T) {
	caminho := filepath.Join(t.TempDir(), "nested", "file.txt")
	if err := EscreverTexto(caminho, "conteúdo"); err != nil {
		t.Fatalf("escrever texto: %v", err)
	}
	if seguro, err := CaminhoRelativoSeguro("src/test/java/AppTest.java"); err != nil || seguro == "" {
		t.Fatalf("caminho relativo válido deveria passar: %q %v", seguro, err)
	}
	if _, err := CaminhoRelativoSeguro("../fora.txt"); err == nil {
		t.Fatalf("esperava erro para caminho escapando o diretório")
	}
}

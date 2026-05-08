package aplicacao

import (
	"os"
	"path/filepath"
	"testing"

	"github.com/marceloamorim/witup-llm/internal/artefatos"
	"github.com/marceloamorim/witup-llm/internal/dominio"
)

func TestPreflightSegundaFaseValidaProjetoComBuildCheck(t *testing.T) {
	tempDir := t.TempDir()
	binDir := filepath.Join(tempDir, "bin")
	if err := os.MkdirAll(binDir, 0o755); err != nil {
		t.Fatalf("mkdir bin: %v", err)
	}
	escreverScriptExecutavel(t, filepath.Join(binDir, "java"), "#!/bin/sh\nif [ \"$1\" = \"-version\" ]; then\n  echo 'openjdk version \"17.0.10\"' >&2\n  exit 0\nfi\nexit 0\n")
	escreverScriptExecutavel(t, filepath.Join(binDir, "mvn"), "#!/bin/sh\nif [ \"$1\" = \"-v\" ]; then\n  echo 'Apache Maven 3.9.9'\n  exit 0\nfi\nexit 0\n")
	t.Setenv("PATH", binDir+string(os.PathListSeparator)+os.Getenv("PATH"))

	projeto := filepath.Join(tempDir, "guava")
	if err := os.MkdirAll(filepath.Join(projeto, "src", "main", "java", "sample"), 0o755); err != nil {
		t.Fatalf("mkdir project tree: %v", err)
	}
	if err := artefatos.EscreverTexto(filepath.Join(projeto, "README.md"), "overview"); err != nil {
		t.Fatalf("overview: %v", err)
	}
	if err := artefatos.EscreverTexto(filepath.Join(projeto, "pom.xml"), "<project/>"); err != nil {
		t.Fatalf("pom: %v", err)
	}
	javaSource := `package sample;

public class Example {
    public void run(String value) {
        if (value == null) {
            throw new IllegalArgumentException();
        }
    }
}`
	if err := artefatos.EscreverTexto(filepath.Join(projeto, "src", "main", "java", "sample", "Example.java"), javaSource); err != nil {
		t.Fatalf("java source: %v", err)
	}

	baseline := filepath.Join(tempDir, "guava-wit.json")
	baselinePayload := map[string]interface{}{
		"path":       projeto,
		"commitHash": "abc123",
		"classes": []interface{}{
			map[string]interface{}{
				"path": filepath.Join(projeto, "src", "main", "java", "sample", "Example.java"),
				"methods": []interface{}{
					map[string]interface{}{
						"qualifiedSignature":        "sample.Example.run(java.lang.String)",
						"exception":                 "throw new IllegalArgumentException();",
						"pathCojunction":            "(value == null)",
						"simplifiedPathConjunction": "value == null",
						"soundSymbolic":             true,
						"soundBackwards":            true,
						"line":                      4,
						"throwingLine":              5,
					},
				},
			},
		},
	}
	if err := artefatos.EscreverJSON(baseline, baselinePayload); err != nil {
		t.Fatalf("baseline: %v", err)
	}

	cfg := dominio.ConfigAplicacao{
		Versao: "1",
		Projeto: dominio.ConfigProjeto{
			Raiz:          projeto,
			Include:       []string{"src/main/java", "."},
			Exclude:       []string{".git", "target", "build"},
			OverviewFile:  filepath.Join(projeto, "README.md"),
			TestFramework: "infer",
		},
		Fluxo: dominio.ConfigFluxo{
			DiretorioSaida: filepath.Join(tempDir, "generated"),
		},
		Modelos: map[string]dominio.ConfigModelo{
			"openai_main": {Modelo: "o4-mini", Provedor: "openai_compatible", URLBase: "https://api.openai.com/v1", VariavelAmbienteChaveAPI: "OPENAI_API_KEY"},
		},
		SegundaFase: dominio.ConfigSegundaFase{
			Projetos: []dominio.ConfigProjetoSegundaFase{{
				Chave:           "guava",
				Rotulo:          "Google Guava",
				Raiz:            projeto,
				CaminhoBaseline: baseline,
				OverviewFile:    filepath.Join(projeto, "README.md"),
				Include:         []string{"src/main/java", "."},
				Exclude:         []string{".git", "target", "build"},
				ContainersAlvo:  []string{"sample.Example"},
				TestFramework:   "infer",
			}},
		},
	}

	servico := NovoServico(nil, nil)
	relatorio, caminho, err := servico.PreflightSegundaFase(&cfg, filepath.Join(tempDir, "pipeline.json"), true)
	if err != nil {
		t.Fatalf("preflight: %v", err)
	}
	if caminho == "" {
		t.Fatalf("esperava caminho de relatório")
	}
	if !relatorio.Pronto {
		t.Fatalf("preflight deveria marcar o projeto como pronto: %#v", relatorio)
	}
	if len(relatorio.Projetos) != 1 || !relatorio.Projetos[0].Pronto {
		t.Fatalf("diagnóstico do projeto inesperado: %#v", relatorio.Projetos)
	}
	if relatorio.Projetos[0].MetodosAlinhados != 1 {
		t.Fatalf("esperava um método alinhado, recebi %d", relatorio.Projetos[0].MetodosAlinhados)
	}
	if !relatorio.Projetos[0].BuildCheckExecutado || !relatorio.Projetos[0].BuildCheckSucesso {
		t.Fatalf("build check deveria passar: %#v", relatorio.Projetos[0])
	}
}

func escreverScriptExecutavel(t *testing.T, caminho, conteudo string) {
	t.Helper()
	if err := os.WriteFile(caminho, []byte(conteudo), 0o755); err != nil {
		t.Fatalf("write script %s: %v", caminho, err)
	}
}

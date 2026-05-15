package aplicacao

import (
	"os"
	"path/filepath"
	"strings"
	"testing"

	"github.com/marceloamorim/witup-llm/internal/artefatos"
	"github.com/marceloamorim/witup-llm/internal/dominio"
)

func TestPrepararEstudoJDKGlobalGeraManifestERequestsPareados(t *testing.T) {
	root := t.TempDir()
	jdkRoot := filepath.Join(root, "jdk")
	sourcePath := filepath.Join(jdkRoot, "src", "java.base", "share", "classes", "java", "io", "Foo.java")
	if err := os.MkdirAll(filepath.Dir(sourcePath), 0o755); err != nil {
		t.Fatal(err)
	}
	source := `package java.io;

public class Foo {
    public void bar(String value) {
        if (value == null) {
            throw new NullPointerException();
        }
    }

    public void baz(String value) {
        if (value.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}

func TestResolverAlvoBaseContextualJDKGlobal(t *testing.T) {
	root := t.TempDir()
	runDir := filepath.Join(root, "run")
	baselineRoot := filepath.Join(runDir, "variants", "baseline")
	for _, dir := range []string{
		filepath.Join(baselineRoot, "test", "jdk", "java", "lang", "String"),
		filepath.Join(baselineRoot, "test", "jdk", "tools", "pack200"),
	} {
		if err := os.MkdirAll(dir, 0o755); err != nil {
			t.Fatal(err)
		}
		if err := os.WriteFile(filepath.Join(dir, "ExistingTest.java"), []byte("class ExistingTest {}"), 0o644); err != nil {
			t.Fatal(err)
		}
	}
	preparation := dominio.RelatorioPreparacaoJDKGlobal{
		Metodos: []dominio.MetodoJDKGlobal{
			{
				CaminhoArquivo: "src/java.base/share/classes/java/lang/String.java",
				NomeContainer:  "java.lang.String",
			},
			{
				CaminhoArquivo: "src/java.base/share/classes/com/sun/java/util/jar/pack/Attribute.java",
				NomeContainer:  "com.sun.java.util.jar.pack.Attribute",
			},
		},
	}
	if err := artefatos.EscreverJSON(filepath.Join(runDir, jdkGlobalPreparationFile), preparation); err != nil {
		t.Fatal(err)
	}

	targets := resolverAlvoBaseContextualJDKGlobal(runDir)
	if !strings.Contains(targets, "test/jdk/java/lang/String") {
		t.Fatalf("alvo String não inferido: %s", targets)
	}
	if !strings.Contains(targets, "test/jdk/tools/pack200") {
		t.Fatalf("alvo pack200 não inferido: %s", targets)
	}
}
`
	if err := os.WriteFile(sourcePath, []byte(source), 0o644); err != nil {
		t.Fatal(err)
	}
	witPath := filepath.Join(root, "wit_filtered.json")
	wit := map[string]interface{}{
		"path":       `C:\dev\jdk\`,
		"commitHash": "da75f3c4ad5bdf25167a3ed80e51f567ab3dbd01",
		"classes": []interface{}{
			map[string]interface{}{
				"path": `C:\dev\jdk\src\java.base\share\classes\java\io\Foo.java`,
				"methods": []interface{}{
					map[string]interface{}{
						"qualifiedSignature":        "java.io.Foo.bar(java.lang.String)",
						"exception":                 "throw new NullPointerException();",
						"pathCojunction":            "value == null",
						"simplifiedPathConjunction": "value == null",
						"line":                      4,
						"throwingLine":              6,
						"soundSymbolic":             true,
						"soundBackwards":            true,
					},
					map[string]interface{}{
						"qualifiedSignature":        "java.io.Foo.baz(java.lang.String)",
						"exception":                 "throw new IllegalArgumentException();",
						"pathCojunction":            "value.isEmpty()",
						"simplifiedPathConjunction": "value.isEmpty()",
						"line":                      10,
						"throwingLine":              12,
						"soundSymbolic":             true,
						"soundBackwards":            true,
					},
				},
			},
		},
	}
	if err := artefatos.EscreverJSON(witPath, wit); err != nil {
		t.Fatal(err)
	}
	cfg := &dominio.ConfigAplicacao{
		Modelos: map[string]dominio.ConfigModelo{
			"openai_main": {
				Provedor:                 "openai",
				Modelo:                   "gpt-5.4-mini",
				URLBase:                  "https://api.openai.com/v1",
				VariavelAmbienteChaveAPI: "OPENAI_API_KEY",
				Endpoint:                 "/v1/responses",
				BackendExecucao:          "batch",
			},
		},
	}
	outputDir := filepath.Join(root, "run")
	requestsPath := filepath.Join(outputDir, "requests.jsonl")

	report, reportPath, err := NovoServico(nil, nil).PrepararEstudoJDKGlobal(cfg, "openai_main", jdkRoot, witPath, outputDir, requestsPath, 2, 2)
	if err != nil {
		t.Fatalf("PrepararEstudoJDKGlobal: %v", err)
	}
	if report.QuantidadeMetodos != 2 || report.QuantidadeRequests != 4 {
		t.Fatalf("contagens inesperadas: métodos=%d requests=%d", report.QuantidadeMetodos, report.QuantidadeRequests)
	}
	if report.UnidadeExperimental != jdkGlobalExperimentalUnit {
		t.Fatalf("unidade experimental inesperada: %s", report.UnidadeExperimental)
	}
	if _, err := os.Stat(reportPath); err != nil {
		t.Fatalf("relatório não materializado: %v", err)
	}
	content, err := os.ReadFile(requestsPath)
	if err != nil {
		t.Fatal(err)
	}
	text := string(content)
	if !strings.Contains(text, "/wit-context/") || !strings.Contains(text, "/direct-tests/") {
		t.Fatalf("requests não contêm os dois cenários: %s", text)
	}
	if strings.Count(text, "/wit-context/") != 2 || strings.Count(text, "/direct-tests/") != 2 {
		t.Fatalf("esperava uma request por método por cenário: %s", text)
	}
}

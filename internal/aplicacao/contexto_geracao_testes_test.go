package aplicacao

import (
	"path/filepath"
	"strings"
	"testing"

	"github.com/marceloamorim/witup-llm/internal/artefatos"
	"github.com/marceloamorim/witup-llm/internal/dominio"
)

func TestConstruirContextoGeracaoTestesDetectaModuloFrameworkEImports(t *testing.T) {
	raiz := t.TempDir()
	if err := artefatos.EscreverTexto(filepath.Join(raiz, "pom.xml"), `<project><modules><module>module-a</module></modules></project>`); err != nil {
		t.Fatal(err)
	}
	pomModulo := `<project>
  <dependencies>
    <dependency><groupId>org.junit.jupiter</groupId><artifactId>junit-jupiter-api</artifactId></dependency>
    <dependency><groupId>org.mockito</groupId><artifactId>mockito-core</artifactId></dependency>
  </dependencies>
</project>`
	if err := artefatos.EscreverTexto(filepath.Join(raiz, "module-a", "pom.xml"), pomModulo); err != nil {
		t.Fatal(err)
	}
	fonte := `package com.example;

import java.util.Objects;
import static java.util.Collections.emptyList;

public class Example {
    public Example(String name) {}
    public static Example of(String name) { return new Example(name); }
    public boolean run(String value) { return Objects.nonNull(value); }
}`
	if err := artefatos.EscreverTexto(filepath.Join(raiz, "module-a", "src", "main", "java", "com", "example", "Example.java"), fonte); err != nil {
		t.Fatal(err)
	}

	contexto := construirContextoGeracaoTestes(
		dominio.ConfigProjeto{Raiz: raiz, TestFramework: "infer"},
		"com.example.Example",
		[]dominio.DescritorMetodo{{
			IDMetodo:       "m1",
			CaminhoArquivo: "module-a/src/main/java/com/example/Example.java",
			NomeContainer:  "com.example.Example",
			NomeMetodo:     "run",
			Assinatura:     "com.example.Example.run(String)",
			Origem:         "public boolean run(String value) { return Objects.nonNull(value); }",
		}},
	)

	if contexto["maven_module"] != "module-a" {
		t.Fatalf("módulo inesperado: %#v", contexto["maven_module"])
	}
	if contexto["test_framework"] != frameworkJUnit5 {
		t.Fatalf("framework inesperado: %#v", contexto["test_framework"])
	}
	if contexto["recommended_relative_test_path"] != "module-a/src/test/java/com/example/ExampleWitupGeneratedTest.java" {
		t.Fatalf("caminho de teste inesperado: %#v", contexto["recommended_relative_test_path"])
	}
	imports := contexto["source_imports"].([]string)
	if strings.Join(imports, "\n") != "import java.util.Objects;\nimport static java.util.Collections.emptyList;" {
		t.Fatalf("imports inesperados: %#v", imports)
	}
	sinais := contexto["dependency_signals"].(map[string]bool)
	if !sinais["mockito"] || !sinais["junit_jupiter"] {
		t.Fatalf("sinais de dependência inesperados: %#v", sinais)
	}
	hints := contexto["construction_hints"].(map[string]interface{})
	if len(hints["public_or_protected_constructors"].([]string)) != 1 {
		t.Fatalf("construtores não detectados: %#v", hints)
	}
	if len(hints["public_static_factories"].([]string)) != 1 {
		t.Fatalf("factories não detectadas: %#v", hints)
	}
}

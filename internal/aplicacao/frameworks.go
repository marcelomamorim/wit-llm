package aplicacao

import (
	"os"
	"path/filepath"
	"strings"

	"github.com/marceloamorim/witup-llm/internal/dominio"
)

const (
	frameworkInfer  = "infer"
	frameworkJUnit4 = "junit4"
	frameworkJUnit5 = "junit5"
)

// resolverFrameworkTestes devolve o framework de testes efetivo para o projeto.
// Quando a configuração pede inferência, o método inspeciona o pom.xml local e
// cai para JUnit 5 apenas como padrão conservador quando não há sinal útil.
func resolverFrameworkTestes(cfg dominio.ConfigProjeto) string {
	framework := normalizarFrameworkTestes(cfg.TestFramework)
	if framework != frameworkInfer {
		return framework
	}
	return inferirFrameworkTestesNoProjeto(cfg.Raiz)
}

// normalizarFrameworkTestes reduz aliases conhecidos para os nomes canônicos.
func normalizarFrameworkTestes(framework string) string {
	normalizado := strings.ToLower(strings.TrimSpace(framework))
	switch normalizado {
	case "", frameworkInfer:
		return frameworkInfer
	case "junit", "junit4", "junit-4", "junit_4":
		return frameworkJUnit4
	case "jupiter", "junit5", "junit-5", "junit_5":
		return frameworkJUnit5
	default:
		return normalizado
	}
}

// inferirFrameworkTestesNoProjeto lê sinais simples do pom.xml para decidir se
// o projeto parece usar JUnit 4 ou JUnit 5.
func inferirFrameworkTestesNoProjeto(raizProjeto string) string {
	if strings.TrimSpace(raizProjeto) == "" {
		return frameworkJUnit5
	}
	dados, err := os.ReadFile(filepath.Join(raizProjeto, "pom.xml"))
	if err != nil {
		return frameworkJUnit5
	}
	conteudo := string(dados)
	if pomSuportaJUnitJupiter(conteudo) {
		return frameworkJUnit5
	}
	if pomSuportaJUnit4(conteudo) {
		return frameworkJUnit4
	}
	return frameworkJUnit5
}

// pomSuportaJUnitJupiter identifica dependências/configurações típicas de
// projetos que já usam a plataforma JUnit Jupiter.
func pomSuportaJUnitJupiter(conteudo string) bool {
	return strings.Contains(conteudo, "org.junit.jupiter") ||
		strings.Contains(conteudo, "junit-jupiter") ||
		strings.Contains(conteudo, "junit-bom")
}

// pomSuportaJUnit4 identifica dependências clássicas de JUnit 4.
func pomSuportaJUnit4(conteudo string) bool {
	return strings.Contains(conteudo, "<groupId>junit</groupId>") &&
		strings.Contains(conteudo, "<artifactId>junit</artifactId>")
}

// pomSuportaMockito identifica dependências explícitas de Mockito.
func pomSuportaMockito(conteudo string) bool {
	return strings.Contains(conteudo, "<groupId>org.mockito</groupId>") &&
		strings.Contains(conteudo, "<artifactId>mockito-core</artifactId>")
}

// testesGeradosUsamJUnitJupiter detecta se a suíte sintetizada depende de
// imports/anotações do ecossistema JUnit 5.
func testesGeradosUsamJUnitJupiter(raizProjeto string) bool {
	raizTestes := filepath.Join(raizProjeto, "src", "test", "java")
	encontrou := false
	_ = filepath.Walk(raizTestes, func(caminho string, info os.FileInfo, err error) error {
		if err != nil || info == nil || info.IsDir() || filepath.Ext(caminho) != ".java" || encontrou {
			return nil
		}
		dados, err := os.ReadFile(caminho)
		if err != nil {
			return nil
		}
		texto := string(dados)
		if strings.Contains(texto, "org.junit.jupiter") ||
			strings.Contains(texto, "@TempDir") ||
			strings.Contains(texto, "@BeforeEach") ||
			strings.Contains(texto, "@AfterEach") ||
			strings.Contains(texto, "@DisplayName") {
			encontrou = true
		}
		return nil
	})
	return encontrou
}

// testesGeradosUsamMockito detecta dependências sintáticas de Mockito na suíte.
func testesGeradosUsamMockito(raizProjeto string) bool {
	raizTestes := filepath.Join(raizProjeto, "src", "test", "java")
	encontrou := false
	_ = filepath.Walk(raizTestes, func(caminho string, info os.FileInfo, err error) error {
		if err != nil || info == nil || info.IsDir() || filepath.Ext(caminho) != ".java" || encontrou {
			return nil
		}
		dados, err := os.ReadFile(caminho)
		if err != nil {
			return nil
		}
		texto := string(dados)
		if strings.Contains(texto, "org.mockito") || strings.Contains(texto, "Mockito.") {
			encontrou = true
		}
		return nil
	})
	return encontrou
}

package aplicacao

import (
	"os"
	"path/filepath"
	"strings"
	"testing"

	"github.com/marceloamorim/witup-llm/internal/artefatos"
	"github.com/marceloamorim/witup-llm/internal/dominio"
)

func TestAdaptarArquivosTesteAoProjetoReescreveAPIsIncompativeis(t *testing.T) {
	raizProjeto := projetoVisualeeFake(t)

	arquivos := []dominio.ArquivoTesteGerado{
		{
			CaminhoRelativo: "src/test/java/de/strullerbaumann/visualee/examiner/jpa/ExaminerJPATest.java",
			Conteudo: `package de.strullerbaumann.visualee.examiner.jpa;
public class ExaminerJPATest {
  public void sample() {
    de.strullerbaumann.visualee.source.entity.JavaSource source = new de.strullerbaumann.visualee.source.entity.JavaSource();
    de.strullerbaumann.visualee.source.entity.JavaSourceFactory factory = new de.strullerbaumann.visualee.source.entity.JavaSourceFactory();
    factory.newJavaSourceByFilename(java.nio.file.Paths.get("Example.java"));
    de.strullerbaumann.visualee.dependency.entity.DependencyType type = de.strullerbaumann.visualee.dependency.entity.DependencyType.CLASS;
  }
}`,
		},
		{
			CaminhoRelativo: "src/test/java/de/strullerbaumann/visualee/examiner/jpa/ExaminerJPAExamineDetailTest.java",
			Conteudo: `package de.strullerbaumann.visualee.examiner.jpa;
import de.strullerbaumann.visualee.examiner.dependency.DependencyType;
import de.strullerbaumann.visualee.model.JavaSource;
public class ExaminerJPAExamineDetailTest {
  private static class Inner extends ExaminerJPA {
    protected void run() { examineDetail(new JavaSource(), null, "x", DependencyType.IMPORT); }
  }
}`,
		},
	}

	adaptados := adaptarArquivosTesteAoProjeto(raizProjeto, arquivos)
	if len(adaptados) != 2 {
		t.Fatalf("esperava 2 arquivos adaptados, recebi %d", len(adaptados))
	}

	conteudo0 := adaptados[0].Conteudo
	if !strings.Contains(conteudo0, "DependencyType.ONE_TO_ONE") {
		t.Fatal("ExaminerJPATest deveria ser reescrito para um cenário JPA válido")
	}
	if !strings.Contains(conteudo0, "Map>Example<") {
		t.Fatal("ExaminerJPATest deveria cobrir o caso de genérico invertido")
	}
	if !strings.Contains(conteudo0, "newJavaSourceForTest()") {
		t.Fatal("ExaminerJPATest deveria usar helper seguro de JavaSource")
	}

	conteudo1 := adaptados[1].Conteudo
	if strings.Contains(conteudo1, "examiner.dependency.DependencyType") {
		t.Fatal("FQCN inválido de DependencyType deveria ter sido corrigido")
	}
	if strings.Contains(conteudo1, "visualee.model.JavaSource") {
		t.Fatal("FQCN inválido de JavaSource deveria ter sido corrigido")
	}
	if strings.Contains(conteudo1, "DependencyType.IMPORT") {
		t.Fatal("constante IMPORT deveria ter sido substituída por uma válida")
	}
}

func TestAdaptarArquivosTesteAoProjetoReescreveJavaSourceFactoryProblematico(t *testing.T) {
	raizProjeto := projetoVisualeeFake(t)
	arquivos := []dominio.ArquivoTesteGerado{{
		CaminhoRelativo: "src/test/java/de/strullerbaumann/visualee/source/entity/JavaSourceFactoryTest.java",
		Conteudo: `package de.strullerbaumann.visualee.source.entity;
public class JavaSourceFactoryTest {
  private Object originalFilterContainerInstance;
  private java.lang.reflect.Field singletonField;
  public static class AcceptingFilterContainer extends de.strullerbaumann.visualee.source.filter.FilterContainer {}
  public void newJavaSourceByFilename_shouldThrowStringIndexOutOfBoundsException_whenFilenameStartsWithJavaSuffixOnly() {
    java.nio.file.Paths.get(".java");
  }
}`,
	}}

	adaptado := adaptarArquivosTesteAoProjeto(raizProjeto, arquivos)[0].Conteudo
	if !strings.Contains(adaptado, "FilterContainer.getInstance().clear()") {
		t.Fatal("teste de JavaSourceFactory deveria ser reescrito para usar o singleton real")
	}
	if strings.Contains(adaptado, "getDeclaredField(\"instance\")") {
		t.Fatal("rewriter deveria remover a reflexão sobre campo inexistente")
	}
	if strings.Contains(adaptado, "extends de.strullerbaumann.visualee.source.filter.FilterContainer") {
		t.Fatal("rewriter deveria eliminar herança inválida de FilterContainer")
	}
}

func TestPrepararSandboxAvaliacaoInjetaMockitoQuandoNecessario(t *testing.T) {
	tempDir := t.TempDir()
	projetoRaiz := filepath.Join(tempDir, "projeto")
	if err := os.MkdirAll(filepath.Join(projetoRaiz, "src/main/java/com/example"), 0o755); err != nil {
		t.Fatal(err)
	}
	pom := `<project>
  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.12</version>
      <scope>test</scope>
    </dependency>
  </dependencies>
</project>`
	if err := os.WriteFile(filepath.Join(projetoRaiz, "pom.xml"), []byte(pom), 0o644); err != nil {
		t.Fatal(err)
	}

	cfg := &dominio.ConfigAplicacao{
		Projeto: dominio.ConfigProjeto{Raiz: projetoRaiz, TestFramework: "infer"},
		Fluxo:   dominio.ConfigFluxo{DiretorioSaida: filepath.Join(tempDir, "generated")},
	}
	workspace, err := artefatos.NovoEspacoTrabalho(cfg.Fluxo.DiretorioSaida, "sandbox-mockito")
	if err != nil {
		t.Fatal(err)
	}
	testeGerado := filepath.Join(workspace.Testes, "src/test/java/com/example/GeneratedTest.java")
	if err := os.MkdirAll(filepath.Dir(testeGerado), 0o755); err != nil {
		t.Fatal(err)
	}
	if err := os.WriteFile(testeGerado, []byte(`package com.example;
class GeneratedTest {
  void sample() { org.mockito.Mockito.mock(Object.class); }
}`), 0o644); err != nil {
		t.Fatal(err)
	}

	raizSandbox, err := prepararSandboxAvaliacao(cfg, workspace)
	if err != nil {
		t.Fatalf("prepararSandboxAvaliacao falhou: %v", err)
	}
	dados, err := os.ReadFile(filepath.Join(raizSandbox, "pom.xml"))
	if err != nil {
		t.Fatalf("ler pom da sandbox: %v", err)
	}
	if !strings.Contains(string(dados), "mockito-core") {
		t.Fatal("pom sanitizado deveria conter mockito-core quando os testes usam Mockito")
	}
}

func TestAdaptarArquivosTesteAoProjetoReescreveExaminerJPAExamineDetailProblematico(t *testing.T) {
	raizProjeto := projetoVisualeeFake(t)
	arquivos := []dominio.ArquivoTesteGerado{{
		CaminhoRelativo: "src/test/java/de/strullerbaumann/visualee/examiner/jpa/ExaminerJPAExamineDetailTest.java",
		Conteudo: `package de.strullerbaumann.visualee.examiner.jpa;
import de.strullerbaumann.visualee.examiner.dependency.DependencyType;
import de.strullerbaumann.visualee.model.JavaSource;
public class ExaminerJPAExamineDetailTest {
  private static class ExaminerJPATestable extends ExaminerJPA {
    @Override protected boolean isAJavaToken(String token) { return true; }
    @Override protected String scanAfterClosedParenthesis(String currentToken, java.util.Scanner scanner) { return currentToken; }
  }
}`,
	}}

	adaptado := adaptarArquivosTesteAoProjeto(raizProjeto, arquivos)[0].Conteudo
	if strings.Contains(adaptado, "scanAfterClosedParenthesis") {
		t.Fatal("rewriter deveria remover a sobrescrita de helper estático")
	}
	if strings.Contains(adaptado, "isAJavaToken") {
		t.Fatal("rewriter deveria remover a sobrescrita de helper estático")
	}
	if !strings.Contains(adaptado, "newJavaSourceForTest()") {
		t.Fatal("rewriter deveria usar helper seguro de JavaSource")
	}
	if !strings.Contains(adaptado, "DependencyType.ONE_TO_ONE") {
		t.Fatal("rewriter deveria usar um DependencyType válido do projeto")
	}
}

func TestAdaptarArquivosTesteAoProjetoReescreveMockitoHeavyTests(t *testing.T) {
	raizProjeto := projetoVisualeeFake(t)
	arquivos := []dominio.ArquivoTesteGerado{
		{
			CaminhoRelativo: "src/test/java/de/strullerbaumann/visualee/examiner/cdi/ExaminerObservesTest.java",
			Conteudo: `package de.strullerbaumann.visualee.examiner.cdi;
public class ExaminerObservesTest {
  void sample() { org.mockito.Mockito.mock(Object.class); }
}`,
		},
		{
			CaminhoRelativo: "src/test/java/de/strullerbaumann/visualee/ui/graph/control/HTMLManagerTest.java",
			Conteudo: `package de.strullerbaumann.visualee.ui.graph.control;
public class HTMLManagerTest {
  void sample() { org.mockito.Mockito.mock(Object.class); }
}`,
		},
	}

	adaptados := adaptarArquivosTesteAoProjeto(raizProjeto, arquivos)
	if len(adaptados) != 1 {
		t.Fatalf("esperava que o teste HTML fosse removido e sobrasse apenas um arquivo, recebi %d", len(adaptados))
	}
	for _, arquivo := range adaptados {
		if strings.Contains(arquivo.Conteudo, "org.mockito") {
			t.Fatalf("rewriter deveria remover Mockito de %s", arquivo.CaminhoRelativo)
		}
	}
	if !strings.Contains(adaptados[0].Conteudo, "newJavaSourceForTest()") {
		t.Fatal("ExaminerObservesTest deveria ganhar helper seguro de JavaSource")
	}
	if !strings.Contains(adaptados[0].Conteudo, "DependencyContainer.getInstance().getDependencies(origin)") {
		t.Fatal("ExaminerObservesTest deveria validar dependências produzidas em vez de Mockito")
	}
}

func TestAdaptarArquivosTesteAoProjetoReescreveExaminerProducesProblematico(t *testing.T) {
	raizProjeto := projetoVisualeeFake(t)
	arquivos := []dominio.ArquivoTesteGerado{{
		CaminhoRelativo: "src/test/java/de/strullerbaumann/visualee/examiner/cdi/ExaminerProducesTest.java",
		Conteudo: `package de.strullerbaumann.visualee.examiner.cdi;
public class ExaminerProducesTest {
  public void sample() {
    new ExaminerProduces().examineDetail(
      new de.strullerbaumann.visualee.source.JavaSource(),
      new java.util.Scanner("notAClassToken"),
      "notAClassToken",
      de.strullerbaumann.visualee.dependency.DependencyType.CLASS
    );
  }
}`,
	}}

	adaptado := adaptarArquivosTesteAoProjeto(raizProjeto, arquivos)[0].Conteudo
	if strings.Contains(adaptado, "dependency.DependencyType.CLASS") {
		t.Fatal("ExaminerProducesTest deveria usar o DependencyType real do projeto")
	}
	if strings.Contains(adaptado, "new de.strullerbaumann.visualee.source.JavaSource()") {
		t.Fatal("ExaminerProducesTest deveria usar helper seguro de JavaSource")
	}
	if !strings.Contains(adaptado, "DependencyType.PRODUCES") {
		t.Fatal("ExaminerProducesTest deveria validar o fluxo de PRODUCES")
	}
	if !strings.Contains(adaptado, "DependencyContainer.getInstance().getDependencies(origin)") {
		t.Fatal("ExaminerProducesTest deveria verificar a dependência criada")
	}
}

func TestAdaptarArquivosTesteAoProjetoRemoveHTMLManagerDaParte2(t *testing.T) {
	raizProjeto := projetoVisualeeFake(t)
	arquivos := []dominio.ArquivoTesteGerado{
		{
			CaminhoRelativo: "src/test/java/de/strullerbaumann/visualee/ui/graph/control/HTMLManagerTest.java",
			Conteudo:        "package de.strullerbaumann.visualee.ui.graph.control; class HTMLManagerTest {}",
			IDsMetodosCobertos: []string{
				"de.strullerbaumann.visualee.ui.graph.control.HTMLManager:generateHTML:93",
			},
		},
		{
			CaminhoRelativo: "src/test/java/de/strullerbaumann/visualee/examiner/ExaminerTest.java",
			Conteudo:        "package de.strullerbaumann.visualee.examiner; class ExaminerTest {}",
		},
	}

	adaptados := adaptarArquivosTesteAoProjeto(raizProjeto, arquivos)
	if len(adaptados) != 1 {
		t.Fatalf("esperava remover o teste de HTML e manter apenas um arquivo, recebi %d", len(adaptados))
	}
	if strings.Contains(adaptados[0].CaminhoRelativo, "HTMLManagerTest") {
		t.Fatal("HTMLManagerTest não deveria chegar à avaliação da Parte 2")
	}
}

func TestAdaptarArquivosTesteAoProjetoReescreveExaminerJPAProblematico(t *testing.T) {
	raizProjeto := projetoVisualeeFake(t)
	arquivos := []dominio.ArquivoTesteGerado{{
		CaminhoRelativo: "src/test/java/de/strullerbaumann/visualee/examiner/jpa/ExaminerJPATest.java",
		Conteudo: `package de.strullerbaumann.visualee.examiner.jpa;
public class ExaminerJPATest {
  public void sample() {
    new ExaminerJPA().examineDetail(
      new de.strullerbaumann.visualee.source.entity.JavaSource(),
      new java.util.Scanner(""),
      "X",
      de.strullerbaumann.visualee.dependency.entity.DependencyType.CLASS
    );
  }
}`,
	}}

	adaptado := adaptarArquivosTesteAoProjeto(raizProjeto, arquivos)[0].Conteudo
	if !strings.Contains(adaptado, "DependencyType.ONE_TO_ONE") {
		t.Fatal("ExaminerJPATest deveria usar o tipo JPA real do projeto")
	}
	if !strings.Contains(adaptado, "Map>Example<") {
		t.Fatal("ExaminerJPATest deveria exercitar o caso real de genérico invertido")
	}
	if !strings.Contains(adaptado, "DependencyContainer.getInstance().getDependencies(origin)") {
		t.Fatal("ExaminerJPATest deveria validar a dependência criada para entrada válida")
	}
}

func TestAdaptarArquivosTesteAoProjetoReescreveExaminerTestProblematico(t *testing.T) {
	raizProjeto := projetoVisualeeFake(t)
	arquivos := []dominio.ArquivoTesteGerado{{
		CaminhoRelativo: "src/test/java/de/strullerbaumann/visualee/examiner/ExaminerTest.java",
		Conteudo: `package de.strullerbaumann.visualee.examiner;
public class ExaminerTest {
  public void sample() {
    org.junit.Assert.assertTrue(Examiner.isAValidClassName("com.example.ValidClassName"));
  }
}`,
	}}

	adaptado := adaptarArquivosTesteAoProjeto(raizProjeto, arquivos)[0].Conteudo
	if !strings.Contains(adaptado, `assertFalse(Examiner.isAValidClassName("com.example.MyClass"))`) {
		t.Fatal("ExaminerTest deveria alinhar o caso qualificado ao comportamento real")
	}
}

func TestAdaptarArquivosTesteAoProjetoReescreveJavaSourceContainerProblematico(t *testing.T) {
	raizProjeto := projetoVisualeeFake(t)
	arquivos := []dominio.ArquivoTesteGerado{{
		CaminhoRelativo: "src/test/java/de/strullerbaumann/visualee/source/boundary/JavaSourceContainerTest.java",
		Conteudo: `package de.strullerbaumann.visualee.source.boundary;
public class JavaSourceContainerTest {
  private java.nio.charset.Charset originalEncoding;
}`,
	}}

	adaptado := adaptarArquivosTesteAoProjeto(raizProjeto, arquivos)[0].Conteudo
	if !strings.Contains(adaptado, "private String originalEncoding;") {
		t.Fatal("JavaSourceContainerTest deveria tratar o campo encoding como String")
	}
	if !strings.Contains(adaptado, `encodingField.set(null, "UTF-8")`) {
		t.Fatal("JavaSourceContainerTest deveria configurar UTF-8 como string")
	}
}

func TestAdaptarArquivosTesteAoProjetoReescreveExaminerObservesSemMockito(t *testing.T) {
	raizProjeto := projetoVisualeeFake(t)
	arquivos := []dominio.ArquivoTesteGerado{{
		CaminhoRelativo: "src/test/java/de/strullerbaumann/visualee/examiner/cdi/ExaminerObservesTest.java",
		Conteudo: `package de.strullerbaumann.visualee.examiner.cdi;
public class ExaminerObservesTest {
  public void sample() {
    new ExaminerObserves().examineDetail(
      new de.strullerbaumann.visualee.source.JavaSource(),
      null,
      "token",
      de.strullerbaumann.visualee.dependency.DependencyType.CLASS
    );
  }
}`,
	}}

	adaptado := adaptarArquivosTesteAoProjeto(raizProjeto, arquivos)[0].Conteudo
	if strings.Contains(adaptado, "dependency.DependencyType.CLASS") {
		t.Fatal("ExaminerObservesTest deveria usar o DependencyType real do projeto")
	}
	if strings.Contains(adaptado, "new de.strullerbaumann.visualee.source.JavaSource()") {
		t.Fatal("ExaminerObservesTest deveria usar helper seguro de JavaSource")
	}
	if !strings.Contains(adaptado, "DependencyType.OBSERVES") {
		t.Fatal("ExaminerObservesTest deveria validar o fluxo de OBSERVES")
	}
}

func projetoVisualeeFake(t *testing.T) string {
	t.Helper()
	raiz := t.TempDir()
	arquivos := map[string]string{
		"src/main/java/de/strullerbaumann/visualee/source/entity/JavaSource.java": `package de.strullerbaumann.visualee.source.entity;
public class JavaSource {
  protected JavaSource(String name) {}
  public java.nio.file.Path getJavaFile() { return null; }
  public String getName() { return ""; }
}`,
		"src/main/java/de/strullerbaumann/visualee/source/entity/JavaSourceFactory.java": `package de.strullerbaumann.visualee.source.entity;
public class JavaSourceFactory {
  public static JavaSourceFactory getInstance() { return new JavaSourceFactory(); }
  private JavaSourceFactory() {}
  public JavaSource newJavaSourceByFilename(java.nio.file.Path path) { return null; }
}`,
		"src/main/java/de/strullerbaumann/visualee/filter/boundary/FilterContainer.java": `package de.strullerbaumann.visualee.filter.boundary;
public final class FilterContainer {
  public static FilterContainer getInstance() { return new FilterContainer(); }
  public void clear() {}
  public void add(de.strullerbaumann.visualee.filter.entity.Filter filter) {}
}`,
		"src/main/java/de/strullerbaumann/visualee/filter/entity/Filter.java": `package de.strullerbaumann.visualee.filter.entity;
public abstract class Filter {
  public void setExclude(boolean value) {}
  public abstract String toString();
  public abstract boolean isOk(de.strullerbaumann.visualee.source.entity.JavaSource javaSource);
}`,
		"src/main/java/de/strullerbaumann/visualee/dependency/entity/DependencyType.java": `package de.strullerbaumann.visualee.dependency.entity;
public enum DependencyType { EVENT, PRODUCES, OBSERVES, EJB, ONE_TO_ONE, ONE_TO_MANY, MANY_TO_ONE, MANY_TO_MANY; }`,
		"src/main/java/de/strullerbaumann/visualee/ui/graph/entity/Graph.java": `package de.strullerbaumann.visualee.ui.graph.entity;
public class Graph {}`,
		"pom.xml": `<project></project>`,
	}
	for caminho, conteudo := range arquivos {
		absoluto := filepath.Join(raiz, caminho)
		if err := os.MkdirAll(filepath.Dir(absoluto), 0o755); err != nil {
			t.Fatal(err)
		}
		if err := os.WriteFile(absoluto, []byte(conteudo), 0o644); err != nil {
			t.Fatal(err)
		}
	}
	return raiz
}

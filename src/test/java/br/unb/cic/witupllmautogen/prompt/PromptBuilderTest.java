package br.unb.cic.witupllmautogen.prompt;

import static org.junit.jupiter.api.Assertions.assertTrue;

import br.unb.cic.witupllmautogen.analysis.model.AnalysisReport;
import br.unb.cic.witupllmautogen.analysis.model.PathConditionReport;
import br.unb.cic.witupllmautogen.analysis.model.ThrowPathReport;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class PromptBuilderTest {

  @Test
  void shouldIncludeOverviewAndAnalysisJson() throws Exception {
    AnalysisReport report =
        new AnalysisReport(
            "/tmp/classes",
            "example.MyClass",
            Instant.parse("2026-03-16T00:00:00Z"),
            1,
            1,
            Map.of("x", "INT"),
            List.of(
                new ThrowPathReport(
                    "example.MyClass:void m(int)",
                    0,
                    0,
                    "new IllegalArgumentException()",
                    List.of(new PathConditionReport(true, "x < 0")))));

    PromptBuilder builder = new PromptBuilder();
    String prompt = builder.buildPrompt("WITUp static analyzer context", report);

    assertTrue(prompt.contains("WITUp static analyzer context"));
    assertTrue(prompt.contains("example.MyClass:void m(int)"));
    assertTrue(prompt.contains("x < 0"));
    assertTrue(prompt.contains("# Unit Tests"));
    assertTrue(prompt.contains("Your task is to generate only unit tests from WITUp analysis."));
    assertTrue(prompt.contains("Every generated test must cite the related method signature"));
    assertTrue(prompt.contains("Throw paths by method:"));
    assertTrue(prompt.contains("throw#0/path#0"));
    assertTrue(prompt.contains("Symbol kinds: x=INT"));
    assertTrue(prompt.contains("Evidence boundaries:"));
    assertTrue(prompt.contains("Methods with direct throw-path evidence: example.MyClass:void m(int)"));
    assertTrue(prompt.contains("Use exactly one top-level section:"));
    assertTrue(prompt.contains("For every generated JUnit test method, add a short `// Evidence:` comment"));
  }

  @Test
  void shouldUseDefaultTextWhenOverviewIsBlank() throws Exception {
    AnalysisReport report =
        new AnalysisReport(
            "/tmp/classes",
            "example.MyClass",
            Instant.parse("2026-03-16T00:00:00Z"),
            1,
            1,
            Map.of(),
            List.of());

    PromptBuilder builder = new PromptBuilder();
    String prompt = builder.buildPrompt("   ", report);

    assertTrue(prompt.contains("(none provided)"));
    assertTrue(prompt.contains("Explain that no throw-path evidence was found"));
    assertTrue(prompt.contains("Methods with direct throw-path evidence: none"));
    assertTrue(prompt.contains("Analysed methods without direct throw-path evidence: 1"));
  }

  @Test
  void shouldStripOverviewAndListMultipleThrowPathConditions() throws Exception {
    AnalysisReport report =
        new AnalysisReport(
            "/tmp/classes",
            "example.AdvancedClass",
            Instant.parse("2026-03-16T00:00:00Z"),
            2,
            2,
            Map.of("arg0", "INT", "flag", "BOOLEAN"),
            List.of(
                new ThrowPathReport(
                    "example.AdvancedClass:int divide(int,int)",
                    0,
                    0,
                    "new IllegalArgumentException(\"zero\")",
                    List.of(
                        new PathConditionReport(false, "divisor != 0"),
                        new PathConditionReport(true, "flag"))),
                new ThrowPathReport(
                    "example.AdvancedClass:int divide(int,int)",
                    0,
                    1,
                    "new ArithmeticException()",
                    List.of())));

    PromptBuilder builder = new PromptBuilder();
    String prompt = builder.buildPrompt("  Domain overview here.  ", report);

    assertTrue(prompt.contains("Additional project context:\nDomain overview here."));
    assertTrue(prompt.contains("arg0=INT"));
    assertTrue(prompt.contains("flag=BOOLEAN"));
    assertTrue(prompt.contains("false:divisor != 0, true:flag"));
    assertTrue(prompt.contains("conditions: none"));
    assertTrue(prompt.contains("Add negative tests for each distinct throw path"));
    assertTrue(
        prompt.contains(
            "Methods with direct throw-path evidence: example.AdvancedClass:int divide(int,int)"));
    assertTrue(prompt.contains("Analysed methods without direct throw-path evidence: 1"));
    assertTrue(prompt.contains("Evidence: overview-only"));
  }
}

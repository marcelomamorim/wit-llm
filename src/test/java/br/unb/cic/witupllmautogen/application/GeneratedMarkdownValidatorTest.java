package br.unb.cic.witupllmautogen.application;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.unb.cic.witupllmautogen.analysis.model.AnalysisReport;
import br.unb.cic.witupllmautogen.analysis.model.PathConditionReport;
import br.unb.cic.witupllmautogen.analysis.model.ThrowPathReport;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class GeneratedMarkdownValidatorTest {

  private final GeneratedMarkdownValidator validator = new GeneratedMarkdownValidator();

  @Test
  void shouldAcceptMarkdownThatContainsRequiredSectionsAndTraceability() {
    String markdown =
        "# Unit Tests\n"
            + "Evidence: demo.Sample:void validate(int) | throw#0/path#0 | conditions: true:x < 0\n\n"
            + "```java\n"
            + "class SampleTest {\n"
            + "  // Evidence: demo.Sample:void validate(int) | throw#0/path#0\n"
            + "}\n"
            + "```";

    assertDoesNotThrow(() -> validator.validate(markdown, sampleReport()));
  }

  @Test
  void shouldRejectMarkdownWhenThrowPathMarkersAreMissing() {
    String markdown =
        "# Unit Tests\n"
            + "Evidence: demo.Sample:void validate(int)\n\n"
            + "```java\nclass SampleTest {}\n```";

    IllegalStateException error =
        assertThrows(IllegalStateException.class, () -> validator.validate(markdown, sampleReport()));

    assertTrue(error.getMessage().contains("throw#0/path#0"));
  }

  @Test
  void shouldRejectMarkdownWhenJavaCodeFenceIsMissing() {
    String markdown =
        "# Unit Tests\n"
            + "Evidence: demo.Sample:void validate(int) | throw#0/path#0\n\n"
            + "No runnable code block.";

    IllegalStateException error =
        assertThrows(IllegalStateException.class, () -> validator.validate(markdown, sampleReport()));

    assertTrue(error.getMessage().contains("```java"));
  }

  private static AnalysisReport sampleReport() {
    return new AnalysisReport(
        "/tmp/classes",
        "demo.Sample",
        Instant.parse("2026-03-18T00:00:00Z"),
        1,
        1,
        Map.of("x", "INT"),
        List.of(
            new ThrowPathReport(
                "demo.Sample:void validate(int)",
                0,
                0,
                "new IllegalArgumentException()",
                List.of(new PathConditionReport(true, "x < 0")))));
  }
}

package br.unb.cic.witupllmautogen.application;

import br.unb.cic.witupllmautogen.analysis.model.AnalysisReport;
import br.unb.cic.witupllmautogen.analysis.model.ThrowPathReport;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public final class GeneratedMarkdownValidator {

  public void validate(final String markdown, final AnalysisReport analysisReport) {
    List<String> violations = new ArrayList<>();
    if (markdown == null || markdown.isBlank()) {
      violations.add("generated Markdown is blank");
    } else {
      validateRequiredSections(markdown, violations);
      validateJavaCodeBlocks(markdown, violations);
      validateTraceability(markdown, analysisReport, violations);
    }

    if (!violations.isEmpty()) {
      throw new IllegalStateException(
          "Generated Markdown failed validation: " + String.join("; ", violations));
    }
  }

  private static void validateRequiredSections(
      final String markdown, final List<String> violations) {
    if (!markdown.contains("# Unit Tests")) {
      violations.add("missing `# Unit Tests` section");
    }
  }

  private static void validateJavaCodeBlocks(
      final String markdown, final List<String> violations) {
    if (!markdown.contains("```java")) {
      violations.add("missing JUnit code block fenced with ```java");
    }
  }

  private static void validateTraceability(
      final String markdown, final AnalysisReport analysisReport, final List<String> violations) {
    Set<String> methodSignatures = new LinkedHashSet<>();
    for (ThrowPathReport throwPath : analysisReport.throwPaths()) {
      methodSignatures.add(throwPath.methodSignature());
      String throwMarker = "throw#" + throwPath.throwIndex() + "/path#" + throwPath.pathIndex();
      if (!markdown.contains(throwMarker)) {
        violations.add("missing traceability marker `" + throwMarker + "`");
      }
    }

    for (String methodSignature : methodSignatures) {
      if (!markdown.contains(methodSignature)) {
        violations.add("missing method signature `" + methodSignature + "`");
      }
    }
  }
}

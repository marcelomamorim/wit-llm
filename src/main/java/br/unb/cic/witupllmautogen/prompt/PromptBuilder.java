package br.unb.cic.witupllmautogen.prompt;

import br.unb.cic.witupllmautogen.analysis.model.AnalysisReport;
import br.unb.cic.witupllmautogen.analysis.model.PathConditionReport;
import br.unb.cic.witupllmautogen.analysis.model.ThrowPathReport;
import br.unb.cic.witupllmautogen.common.JsonMapperFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedHashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class PromptBuilder implements PromptComposer {

  private final ObjectMapper mapper;

  public PromptBuilder() {
    this.mapper = JsonMapperFactory.createDefaultMapper();
  }

  @Override
  public String buildPrompt(final String projectOverview, final AnalysisReport analysisReport)
      throws JsonProcessingException {
    StringBuilder prompt = new StringBuilder();

    appendRole(prompt);
    appendOutputContract(prompt);
    appendAdditionalContext(prompt, projectOverview);
    appendAnalysisSummary(prompt, analysisReport);
    appendEvidenceBoundaries(prompt, analysisReport);
    appendCoverageChecklist(prompt, analysisReport);
    appendAnalysisJson(prompt, analysisReport);
    prompt.append("Generate the final Markdown now.\n");
    return prompt.toString();
  }

  private static void appendRole(final StringBuilder prompt) {
    prompt.append("You are a senior Java engineer focused on high-quality unit tests.\n");
    prompt.append("Your task is to generate only unit tests from WITUp analysis.\n");
    prompt.append("Ground every statement in the provided overview or WITUp data.\n\n");
  }

  private static void appendOutputContract(final StringBuilder prompt) {
    prompt.append("Output rules:\n");
    prompt.append("1. Return only Markdown.\n");
    prompt.append("2. Use exactly one top-level section:\n");
    prompt.append("   - # Unit Tests\n");
    prompt.append("3. In # Unit Tests, include runnable JUnit 5 test classes inside ```java blocks.\n");
    prompt.append("4. Every generated test must cite the related method signature and relevant path ");
    prompt.append("conditions from the WITUp analysis.\n");
    prompt.append("5. Cover happy-path behavior, boundary cases, and failure paths implied by the ");
    prompt.append("analysis. Prefer concrete assertions over placeholders.\n");
    prompt.append("6. Do not invent classes, methods, exceptions, or dependencies that are not present ");
    prompt.append("in the provided context.\n");
    prompt.append("7. If context is incomplete, state assumptions explicitly in short prose and avoid encoding ");
    prompt.append("those assumptions as hard facts in the tests.\n");
    prompt.append("8. Do not output TODOs, pseudocode, or ellipses inside test code.\n\n");
    prompt.append("9. Before each generated JUnit test method, add a short `// Evidence:` comment ");
    prompt.append("immediately above the test method using the same exact markers.\n");
    prompt.append("10. If a claim is supported only by the project overview, mark it as ");
    prompt.append("`Evidence: overview-only` and keep the wording explicitly tentative.\n");
    prompt.append("11. If evidence is unavailable, say so instead of inferring implementation details.\n\n");
  }

  private static void appendAdditionalContext(
      final StringBuilder prompt, final String projectOverview) {
    prompt.append("Additional project context:\n");
    if (projectOverview == null || projectOverview.isBlank()) {
      prompt.append("(none provided)\n\n");
      return;
    }
    prompt.append(projectOverview.strip()).append("\n\n");
  }

  private static void appendAnalysisSummary(
      final StringBuilder prompt, final AnalysisReport analysisReport) {
    prompt.append("Analysis summary:\n");
    prompt
        .append("- Analysed class: ")
        .append(analysisReport.className())
        .append('\n');
    prompt
        .append("- Analysed methods: ")
        .append(analysisReport.analysedMethods())
        .append('\n');
    prompt
        .append("- Throw paths: ")
        .append(analysisReport.analysedThrowPaths())
        .append('\n');
    prompt
        .append("- Symbol kinds: ")
        .append(formatSymbolKinds(analysisReport.symbolKinds()))
        .append('\n');
    prompt.append("- Throw paths by method:\n");
    if (analysisReport.throwPaths().isEmpty()) {
      prompt.append("  - none\n\n");
      return;
    }

    for (Map.Entry<String, List<ThrowPathReport>> entry :
        groupThrowPathsByMethod(analysisReport.throwPaths()).entrySet()) {
      prompt.append("  - ").append(entry.getKey()).append('\n');
      for (ThrowPathReport throwPath : entry.getValue()) {
        prompt
            .append("    - throw#")
            .append(throwPath.throwIndex())
            .append("/path#")
            .append(throwPath.pathIndex())
            .append(": ")
            .append(throwPath.throwExpression())
            .append(" | conditions: ")
            .append(formatConditions(throwPath.conditions()))
            .append('\n');
      }
    }
    prompt.append('\n');
  }

  private static void appendEvidenceBoundaries(
      final StringBuilder prompt, final AnalysisReport analysisReport) {
    prompt.append("Evidence boundaries:\n");
    Set<String> methodsWithThrowEvidence = collectMethodSignatures(analysisReport.throwPaths());
    prompt.append("- Methods with direct throw-path evidence: ");
    if (methodsWithThrowEvidence.isEmpty()) {
      prompt.append("none\n");
    } else {
      prompt.append(String.join(", ", methodsWithThrowEvidence)).append('\n');
    }

    int methodsWithoutDirectThrowEvidence =
        Math.max(0, analysisReport.analysedMethods() - methodsWithThrowEvidence.size());
    prompt
        .append("- Analysed methods without direct throw-path evidence: ")
        .append(methodsWithoutDirectThrowEvidence)
        .append('\n');
    prompt.append("- Do not describe control flow, return values, or side effects unless they are ");
    prompt.append("grounded in the overview or the throw-path evidence above.\n");
    prompt.append("- When a method lacks direct evidence, limit yourself to cautious high-level ");
    prompt.append("statements and explain the missing evidence.\n\n");
  }

  private static void appendCoverageChecklist(
      final StringBuilder prompt, final AnalysisReport analysisReport) {
    prompt.append("Coverage checklist for generated tests:\n");
    prompt.append("- Add at least one valid scenario for each method whose behavior can be inferred.\n");
    if (analysisReport.throwPaths().isEmpty()) {
      prompt.append("- Explain that no throw-path evidence was found in the WITUp analysis.\n");
    } else {
      prompt.append("- Add negative tests for each distinct throw path found in the WITUp analysis.\n");
      prompt.append("- Use assertions that distinguish different path conditions whenever possible.\n");
    }
    prompt.append("- Keep tests deterministic and self-contained.\n\n");
  }

  private void appendAnalysisJson(final StringBuilder prompt, final AnalysisReport analysisReport)
      throws JsonProcessingException {
    prompt.append("WITUp analysis JSON:\n");
    prompt.append("```json\n");
    prompt.append(mapper.writeValueAsString(analysisReport));
    prompt.append("\n```\n\n");
  }

  private static Map<String, List<ThrowPathReport>> groupThrowPathsByMethod(
      final List<ThrowPathReport> throwPaths) {
    Map<String, List<ThrowPathReport>> grouped = new LinkedHashMap<>();
    for (ThrowPathReport throwPath : throwPaths) {
      grouped.computeIfAbsent(throwPath.methodSignature(), ignored -> new java.util.ArrayList<>())
          .add(throwPath);
    }
    return grouped;
  }

  private static Set<String> collectMethodSignatures(final List<ThrowPathReport> throwPaths) {
    return throwPaths.stream()
        .map(ThrowPathReport::methodSignature)
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  private static String formatSymbolKinds(final Map<String, String> symbolKinds) {
    if (symbolKinds == null || symbolKinds.isEmpty()) {
      return "none";
    }
    return symbolKinds.entrySet().stream()
        .map(entry -> entry.getKey() + "=" + entry.getValue())
        .collect(Collectors.joining(", "));
  }

  private static String formatConditions(final List<PathConditionReport> conditions) {
    if (conditions == null || conditions.isEmpty()) {
      return "none";
    }
    return conditions.stream()
        .map(condition -> (condition.truthValue() ? "true" : "false") + ":" + condition.expression())
        .collect(Collectors.joining(", "));
  }
}

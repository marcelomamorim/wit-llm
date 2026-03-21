package br.unb.cic.witupllmautogen.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.unb.cic.witupllmautogen.analysis.AnalysisProvider;
import br.unb.cic.witupllmautogen.analysis.model.AnalysisReport;
import br.unb.cic.witupllmautogen.analysis.model.PathConditionReport;
import br.unb.cic.witupllmautogen.analysis.model.ThrowPathReport;
import br.unb.cic.witupllmautogen.application.mode.GenerationModeFactory;
import br.unb.cic.witupllmautogen.common.JsonMapperFactory;
import br.unb.cic.witupllmautogen.io.LocalFileGateway;
import br.unb.cic.witupllmautogen.ollama.TextGenerator;
import br.unb.cic.witupllmautogen.prompt.PromptComposer;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class DocumentationGenerationFacadeTest {

  @TempDir Path tempDir;

  @Test
  void shouldGeneratePromptInDryRunMode() throws Exception {
    GenerationRequest request = createRequest(true);
    Files.writeString(request.overviewFile(), "Overview text", StandardCharsets.UTF_8);

    DocumentationGenerationFacade facade =
        new DocumentationGenerationFacade(
            fixedAnalysisProvider(),
            fixedPromptComposer(),
            new GenerationModeFactory(ignoredUrl -> sampleMarkdownGenerator()),
            new LocalFileGateway(JsonMapperFactory.createDefaultMapper()));

    GenerationResult result = facade.generate(request);

    assertTrue(result.dryRun());
    assertEquals(request.promptOutputPath(), result.outputPath());
    assertTrue(Files.exists(request.analysisOutputPath()));
    assertTrue(Files.exists(request.promptOutputPath()));
    assertTrue(Files.readString(request.promptOutputPath()).contains("PROMPT::Overview text"));
  }

  @Test
  void shouldGenerateMarkdownInOllamaMode() throws Exception {
    GenerationRequest request = createRequest(false);
    Files.writeString(request.overviewFile(), "Overview text", StandardCharsets.UTF_8);

    DocumentationGenerationFacade facade =
        new DocumentationGenerationFacade(
            fixedAnalysisProvider(),
            fixedPromptComposer(),
            new GenerationModeFactory(ignoredUrl -> sampleMarkdownGenerator()),
            new LocalFileGateway(JsonMapperFactory.createDefaultMapper()));

    GenerationResult result = facade.generate(request);

    assertFalse(result.dryRun());
    assertEquals(request.outputPath(), result.outputPath());
    assertTrue(Files.exists(request.analysisOutputPath()));
    assertTrue(Files.exists(request.outputPath()));
    assertTrue(Files.readString(request.outputPath()).contains("# Unit Tests"));
  }

  @Test
  void shouldUseEmptyOverviewWhenOverviewPathIsNull() throws Exception {
    GenerationRequest request = createRequest(true, null);

    DocumentationGenerationFacade facade =
        new DocumentationGenerationFacade(
            fixedAnalysisProvider(),
            fixedPromptComposer(),
            new GenerationModeFactory(ignoredUrl -> sampleMarkdownGenerator()),
            new LocalFileGateway(JsonMapperFactory.createDefaultMapper()));

    GenerationResult result = facade.generate(request);

    assertEquals(request.promptOutputPath(), result.outputPath());
    assertTrue(Files.readString(request.promptOutputPath()).contains("PROMPT::::demo.Sample"));
  }

  @Test
  void shouldPropagatePromptComposerFailureAfterWritingAnalysisJson() throws Exception {
    GenerationRequest request = createRequest(true);
    Files.writeString(request.overviewFile(), "Overview text", StandardCharsets.UTF_8);
    PromptComposer failingComposer =
        (overview, report) -> {
          throw throwJsonProcessingException("failure while composing prompt from analysis context");
        };

    DocumentationGenerationFacade facade =
        new DocumentationGenerationFacade(
            fixedAnalysisProvider(),
            failingComposer,
            new GenerationModeFactory(ignoredUrl -> sampleMarkdownGenerator()),
            new LocalFileGateway(JsonMapperFactory.createDefaultMapper()));

    assertThrows(JsonProcessingException.class, () -> facade.generate(request));
    assertTrue(Files.exists(request.analysisOutputPath()));
    assertFalse(Files.exists(request.promptOutputPath()));
  }

  @Test
  void shouldRejectGeneratedMarkdownWithoutTraceabilityEvidence() throws Exception {
    GenerationRequest request = createRequest(false);
    Files.writeString(request.overviewFile(), "Overview text", StandardCharsets.UTF_8);

    DocumentationGenerationFacade facade =
        new DocumentationGenerationFacade(
            fixedAnalysisProvider(),
            fixedPromptComposer(),
            new GenerationModeFactory(ignoredUrl -> invalidMarkdownGenerator()),
            new LocalFileGateway(JsonMapperFactory.createDefaultMapper()));

    IllegalStateException error =
        assertThrows(IllegalStateException.class, () -> facade.generate(request));

    assertTrue(error.getMessage().contains("throw#0/path#0"));
    assertFalse(Files.exists(request.outputPath()));
    assertTrue(Files.exists(request.analysisOutputPath()));
  }

  private GenerationRequest createRequest(final boolean dryRun) {
    return createRequest(dryRun, tempDir.resolve("overview.txt"));
  }

  private GenerationRequest createRequest(final boolean dryRun, final Path overviewPath) {
    return new GenerationRequest(
        "/tmp/classes",
        "demo.Sample",
        "qwen2.5-coder:7b",
        2,
        "http://localhost:11434",
        tempDir.resolve("unit-tests.md"),
        tempDir.resolve("analysis.json"),
        tempDir.resolve("unit-test-prompt.txt"),
        overviewPath,
        dryRun);
  }

  private static AnalysisProvider fixedAnalysisProvider() {
    AnalysisReport report =
        new AnalysisReport(
            "/tmp/classes",
            "demo.Sample",
            Instant.parse("2026-03-17T00:00:00Z"),
            1,
            1,
            Map.of("x", "INT"),
            List.of(
                new ThrowPathReport(
                    "<demo.Sample: int divide(int,int)>",
                    0,
                    0,
                    "#l0",
                    List.of(new PathConditionReport(false, "(l2 != 0)")))));
    return (classPath, className) -> report;
  }

  private static PromptComposer fixedPromptComposer() {
    return (overview, report) -> "PROMPT::" + overview + "::" + report.className();
  }

  private static TextGenerator sampleMarkdownGenerator() {
    return (model, prompt, numThread) ->
        "# Unit Tests\n"
            + "Evidence: <demo.Sample: int divide(int,int)> | throw#0/path#0 | conditions: false:(l2 != 0)\n\n"
            + "```java\n"
            + "class SampleTest {\n"
            + "  // Evidence: <demo.Sample: int divide(int,int)> | throw#0/path#0\n"
            + "}\n"
            + "```\n"
            + "model="
            + model
            + "\nnumThread="
            + numThread
            + "\n"
            + prompt;
  }

  private static TextGenerator invalidMarkdownGenerator() {
    return (model, prompt, numThread) ->
        "# Unit Tests\nNo traceability markers here.\n\n```java\nclass BrokenTest {}\n```";
  }

  private static JsonProcessingException throwJsonProcessingException(final String message) {
    return new JsonProcessingException(message) {
      private static final long serialVersionUID = 1L;
    };
  }
}

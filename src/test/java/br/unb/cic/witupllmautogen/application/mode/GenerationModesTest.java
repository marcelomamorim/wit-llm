package br.unb.cic.witupllmautogen.application.mode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.unb.cic.witupllmautogen.application.GenerationRequest;
import br.unb.cic.witupllmautogen.common.JsonMapperFactory;
import br.unb.cic.witupllmautogen.io.LocalFileGateway;
import br.unb.cic.witupllmautogen.ollama.TextGenerator;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class GenerationModesTest {

  @TempDir Path tempDir;

  @Test
  void shouldReturnDryRunModeFromFactory() {
    GenerationModeFactory factory =
        new GenerationModeFactory(
            url -> {
              throw new IllegalStateException("Text generator factory should not be called");
            });

    GenerationMode mode = factory.create(true, "http://localhost:11434");

    assertTrue(mode instanceof DryRunGenerationMode);
  }

  @Test
  void shouldReturnOllamaModeFromFactoryAndPropagateUrl() throws Exception {
    AtomicReference<String> capturedUrl = new AtomicReference<>();
    TextGenerator generator = (model, prompt, numThread) -> "generated";

    GenerationModeFactory factory =
        new GenerationModeFactory(
            url -> {
              capturedUrl.set(url);
              return generator;
            });

    GenerationMode mode = factory.create(false, "http://localhost:11434");

    assertTrue(mode instanceof OllamaGenerationMode);
    assertEquals("http://localhost:11434", capturedUrl.get());
  }

  @Test
  void shouldWritePromptInDryRunMode() throws Exception {
    DryRunGenerationMode mode = new DryRunGenerationMode();
    LocalFileGateway fileGateway = new LocalFileGateway(JsonMapperFactory.createDefaultMapper());
    GenerationRequest request = sampleRequest(tempDir);

    Path outputPath = mode.execute(request, "PROMPT", fileGateway);

    assertEquals(request.promptOutputPath(), outputPath);
    assertEquals("PROMPT", Files.readString(request.promptOutputPath()));
  }

  @Test
  void shouldWriteGeneratedTextInOllamaMode() throws Exception {
    AtomicReference<String> capturedModel = new AtomicReference<>();
    AtomicReference<String> capturedPrompt = new AtomicReference<>();
    AtomicReference<Integer> capturedNumThread = new AtomicReference<>();

    TextGenerator generator =
        (model, prompt, numThread) -> {
          capturedModel.set(model);
          capturedPrompt.set(prompt);
          capturedNumThread.set(numThread);
          return "MARKDOWN";
        };

    OllamaGenerationMode mode = new OllamaGenerationMode(generator);
    LocalFileGateway fileGateway = new LocalFileGateway(JsonMapperFactory.createDefaultMapper());
    GenerationRequest request = sampleRequest(tempDir);

    Path outputPath = mode.execute(request, "PROMPT", fileGateway);

    assertEquals(request.outputPath(), outputPath);
    assertEquals("MARKDOWN", Files.readString(request.outputPath()));
    assertEquals("qwen2.5-coder:7b", capturedModel.get());
    assertEquals("PROMPT", capturedPrompt.get());
    assertEquals(3, capturedNumThread.get());
  }

  private static GenerationRequest sampleRequest(final Path tempDir) {
    return new GenerationRequest(
        "/tmp/classes",
        "demo.Sample",
        "qwen2.5-coder:7b",
        3,
        "http://localhost:11434",
        tempDir.resolve("unit-tests.md"),
        tempDir.resolve("analysis.json"),
        tempDir.resolve("unit-test-prompt.txt"),
        tempDir.resolve("overview.txt"),
        false);
  }
}

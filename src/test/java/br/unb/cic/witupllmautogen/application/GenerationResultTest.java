package br.unb.cic.witupllmautogen.application;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class GenerationResultTest {

  @Test
  void shouldDescribeDryRunOutput() {
    GenerationResult result =
        new GenerationResult(
            true, Path.of("generated/analysis.json"), Path.of("generated/unit-test-prompt.txt"));

    String description = result.describe();

    assertTrue(description.contains("Dry-run complete"));
    assertTrue(description.contains("unit-test-prompt.txt"));
    assertTrue(description.contains("analysis.json"));
  }

  @Test
  void shouldDescribeGenerationOutput() {
    GenerationResult result =
        new GenerationResult(
            false, Path.of("generated/analysis.json"), Path.of("generated/unit-tests.md"));

    String description = result.describe();

    assertTrue(description.contains("Unit test generation complete"));
    assertTrue(description.contains("unit-tests.md"));
    assertTrue(description.contains("analysis.json"));
  }
}

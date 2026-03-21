package br.unb.cic.witupllmautogen.application;

import java.nio.file.Path;

public record GenerationResult(boolean dryRun, Path analysisOutputPath, Path outputPath) {

  public String describe() {
    if (dryRun) {
      return String.format(
          "Dry-run complete. Unit test prompt: %s | Analysis JSON: %s",
          outputPath.toAbsolutePath(), analysisOutputPath.toAbsolutePath());
    }
    return String.format(
        "Unit test generation complete. Output: %s | Analysis JSON: %s",
        outputPath.toAbsolutePath(), analysisOutputPath.toAbsolutePath());
  }
}

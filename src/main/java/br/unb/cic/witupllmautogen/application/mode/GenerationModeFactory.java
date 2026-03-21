package br.unb.cic.witupllmautogen.application.mode;

import br.unb.cic.witupllmautogen.ollama.TextGenerator;
import java.util.function.Function;

public final class GenerationModeFactory {

  private final Function<String, TextGenerator> textGeneratorFactory;

  public GenerationModeFactory(final Function<String, TextGenerator> textGeneratorFactory) {
    this.textGeneratorFactory = textGeneratorFactory;
  }

  public GenerationMode create(final boolean dryRun, final String ollamaUrl) {
    if (dryRun) {
      return new DryRunGenerationMode();
    }
    return new OllamaGenerationMode(textGeneratorFactory.apply(ollamaUrl));
  }
}

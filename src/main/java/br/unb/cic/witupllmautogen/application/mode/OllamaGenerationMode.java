package br.unb.cic.witupllmautogen.application.mode;

import br.unb.cic.witupllmautogen.application.GenerationRequest;
import br.unb.cic.witupllmautogen.io.FileGateway;
import br.unb.cic.witupllmautogen.ollama.TextGenerator;
import java.io.IOException;
import java.nio.file.Path;

public final class OllamaGenerationMode implements GenerationMode {

  private final TextGenerator textGenerator;

  public OllamaGenerationMode(final TextGenerator textGenerator) {
    this.textGenerator = textGenerator;
  }

  @Override
  public Path execute(final GenerationRequest request, final String prompt, final FileGateway fileGateway)
      throws IOException, InterruptedException {
    String generatedMarkdown = textGenerator.generate(request.model(), prompt, request.numThread());
    fileGateway.writeText(request.outputPath(), generatedMarkdown);
    return request.outputPath();
  }
}

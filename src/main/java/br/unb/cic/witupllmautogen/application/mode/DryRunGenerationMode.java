package br.unb.cic.witupllmautogen.application.mode;

import br.unb.cic.witupllmautogen.application.GenerationRequest;
import br.unb.cic.witupllmautogen.io.FileGateway;
import java.io.IOException;
import java.nio.file.Path;

public final class DryRunGenerationMode implements GenerationMode {

  @Override
  public Path execute(final GenerationRequest request, final String prompt, final FileGateway fileGateway)
      throws IOException {
    fileGateway.writeText(request.promptOutputPath(), prompt);
    return request.promptOutputPath();
  }
}

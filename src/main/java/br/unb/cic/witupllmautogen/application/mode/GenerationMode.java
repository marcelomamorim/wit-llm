package br.unb.cic.witupllmautogen.application.mode;

import br.unb.cic.witupllmautogen.application.GenerationRequest;
import br.unb.cic.witupllmautogen.io.FileGateway;
import java.io.IOException;
import java.nio.file.Path;

public interface GenerationMode {
  Path execute(GenerationRequest request, String prompt, FileGateway fileGateway)
      throws IOException, InterruptedException;
}

package br.unb.cic.witupllmautogen.io;

import java.io.IOException;
import java.nio.file.Path;

public interface FileGateway {
  void writeJson(Path outputPath, Object value) throws IOException;

  void writeText(Path outputPath, String content) throws IOException;

  String readText(Path path) throws IOException;

  String readTextOrEmpty(Path path) throws IOException;
}

package br.unb.cic.witupllmautogen.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class LocalFileGateway implements FileGateway {

  private final ObjectMapper mapper;

  public LocalFileGateway(final ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public void writeJson(final Path outputPath, final Object value) throws IOException {
    ensureParent(outputPath);
    mapper.writeValue(outputPath.toFile(), value);
  }

  @Override
  public void writeText(final Path outputPath, final String content) throws IOException {
    ensureParent(outputPath);
    Files.writeString(outputPath, content, StandardCharsets.UTF_8);
  }

  @Override
  public String readText(final Path path) throws IOException {
    return Files.readString(path, StandardCharsets.UTF_8);
  }

  @Override
  public String readTextOrEmpty(final Path path) throws IOException {
    if (path == null) {
      return "";
    }
    return readText(path);
  }

  private static void ensureParent(final Path path) throws IOException {
    Path parent = path.toAbsolutePath().getParent();
    if (parent != null) {
      Files.createDirectories(parent);
    }
  }
}

package br.unb.cic.witupllmautogen.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.unb.cic.witupllmautogen.common.JsonMapperFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class LocalFileGatewayTest {

  @TempDir Path tempDir;

  @Test
  void shouldWriteAndReadText() throws Exception {
    LocalFileGateway gateway = new LocalFileGateway(JsonMapperFactory.createDefaultMapper());
    Path path = tempDir.resolve("a/b/c.txt");

    gateway.writeText(path, "hello");

    assertEquals("hello", gateway.readText(path));
  }

  @Test
  void shouldWriteJsonAndCreateParentDirectories() throws Exception {
    LocalFileGateway gateway = new LocalFileGateway(JsonMapperFactory.createDefaultMapper());
    Path jsonPath = tempDir.resolve("deep/path/payload.json");

    gateway.writeJson(jsonPath, Map.of("ok", true));

    assertTrue(Files.exists(jsonPath));
    assertTrue(Files.readString(jsonPath).contains("\"ok\""));
  }

  @Test
  void shouldReturnEmptyStringWhenPathIsNull() throws Exception {
    LocalFileGateway gateway = new LocalFileGateway(JsonMapperFactory.createDefaultMapper());

    assertEquals("", gateway.readTextOrEmpty(null));
  }

  @Test
  void shouldReadTextOrEmptyWhenPathExists() throws Exception {
    LocalFileGateway gateway = new LocalFileGateway(JsonMapperFactory.createDefaultMapper());
    Path path = tempDir.resolve("overview.txt");
    gateway.writeText(path, "context");

    assertEquals("context", gateway.readTextOrEmpty(path));
  }
}

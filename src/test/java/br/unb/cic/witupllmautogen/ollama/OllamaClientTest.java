package br.unb.cic.witupllmautogen.ollama;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class OllamaClientTest {

  private HttpServer server;

  @AfterEach
  void teardown() {
    if (server != null) {
      server.stop(0);
    }
  }

  @Test
  void shouldReadGeneratedTextFromOllamaResponse() throws Exception {
    AtomicReference<String> requestBody = new AtomicReference<>();
    server = HttpServer.create(new InetSocketAddress(0), 0);
    server.createContext(
        "/api/generate",
        exchange -> {
          requestBody.set(readRequestBody(exchange));
          respondJson(exchange, 200, "{\"response\":\"# Unit Tests\\nOK\"}");
        });
    server.start();

    String baseUrl = "http://localhost:" + server.getAddress().getPort();
    OllamaClient client = new OllamaClient(baseUrl);

    String result = client.generate("qwen2.5-coder:7b", "prompt", 2);
    assertEquals("# Unit Tests\nOK", result);
    assertTrue(requestBody.get().contains("\"num_thread\":2"));
  }

  @Test
  void shouldThrowWhenOllamaReturnsError() throws Exception {
    server = HttpServer.create(new InetSocketAddress(0), 0);
    server.createContext(
        "/api/generate", exchange -> respondJson(exchange, 200, "{\"error\":\"model not found\"}"));
    server.start();

    String baseUrl = "http://localhost:" + server.getAddress().getPort();
    OllamaClient client = new OllamaClient(baseUrl);

    assertThrows(IOException.class, () -> client.generate("missing", "prompt", null));
  }

  @Test
  void shouldThrowWhenStatusCodeIsNotSuccessful() throws Exception {
    server = HttpServer.create(new InetSocketAddress(0), 0);
    server.createContext("/api/generate", exchange -> respondJson(exchange, 500, "{\"error\":\"internal\"}"));
    server.start();

    String baseUrl = "http://localhost:" + server.getAddress().getPort();
    OllamaClient client = new OllamaClient(baseUrl);

    assertThrows(IOException.class, () -> client.generate("qwen2.5-coder:7b", "prompt", 2));
  }

  @Test
  void shouldThrowWhenResponseFieldIsMissing() throws Exception {
    server = HttpServer.create(new InetSocketAddress(0), 0);
    server.createContext("/api/generate", exchange -> respondJson(exchange, 200, "{\"done\":true}"));
    server.start();

    String baseUrl = "http://localhost:" + server.getAddress().getPort();
    OllamaClient client = new OllamaClient(baseUrl);

    assertThrows(IOException.class, () -> client.generate("qwen2.5-coder:7b", "prompt", 2));
  }

  @Test
  void shouldNotIncludeNumThreadWhenNull() throws Exception {
    AtomicReference<String> requestBody = new AtomicReference<>();
    server = HttpServer.create(new InetSocketAddress(0), 0);
    server.createContext(
        "/api/generate",
        exchange -> {
          requestBody.set(readRequestBody(exchange));
          respondJson(exchange, 200, "{\"response\":\"ok\"}");
        });
    server.start();

    String baseUrl = "http://localhost:" + server.getAddress().getPort();
    OllamaClient client = new OllamaClient(baseUrl);

    String result = client.generate("qwen2.5-coder:7b", "prompt", null);

    assertEquals("ok", result);
    assertFalse(requestBody.get().contains("\"num_thread\""));
  }

  private static void respondJson(final HttpExchange exchange, final int status, final String body)
      throws IOException {
    byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
    exchange.getResponseHeaders().add("Content-Type", "application/json");
    exchange.sendResponseHeaders(status, bytes.length);
    try (OutputStream outputStream = exchange.getResponseBody()) {
      outputStream.write(bytes);
    }
  }

  private static String readRequestBody(final HttpExchange exchange) throws IOException {
    return new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
  }
}

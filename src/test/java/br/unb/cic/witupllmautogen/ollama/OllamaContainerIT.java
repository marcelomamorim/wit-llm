package br.unb.cic.witupllmautogen.ollama;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers(disabledWithoutDocker = true)
class OllamaContainerIT {

  private static final int OLLAMA_PORT = 11434;

  @Container
  private static final GenericContainer<?> OLLAMA =
      new GenericContainer<>(DockerImageName.parse("ollama/ollama:latest"))
          .withExposedPorts(OLLAMA_PORT)
          .waitingFor(Wait.forHttp("/api/tags").forStatusCode(200))
          .withStartupTimeout(Duration.ofMinutes(5));

  @Test
  void shouldExposeTagsEndpoint() throws Exception {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request =
        HttpRequest.newBuilder(baseUri().resolve("/api/tags"))
            .GET()
            .timeout(Duration.ofSeconds(30))
            .build();

    HttpResponse<String> response =
        client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

    assertEquals(200, response.statusCode());
    assertTrue(response.body().contains("\"models\""));
  }

  @Test
  void shouldReturnMeaningfulErrorForUnknownModel() {
    OllamaClient client = new OllamaClient(baseUri().toString());

    IOException exception =
        assertThrows(
            IOException.class,
            () -> client.generate("model-that-does-not-exist", "hello", 1));

    assertTrue(
        exception.getMessage().contains("Ollama returned status")
            || exception.getMessage().contains("Ollama error"));
  }

  private static URI baseUri() {
    return URI.create("http://" + OLLAMA.getHost() + ":" + OLLAMA.getMappedPort(OLLAMA_PORT));
  }
}

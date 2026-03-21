package br.unb.cic.witupllmautogen.ollama;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public final class OllamaClient implements TextGenerator {
  private final HttpClient httpClient;
  private final URI generateUri;
  private final ObjectMapper mapper;

  public OllamaClient(final String baseUrl) {
    this(HttpClient.newHttpClient(), baseUrl, new ObjectMapper());
  }

  OllamaClient(final HttpClient httpClient, final String baseUrl, final ObjectMapper mapper) {
    this.httpClient = httpClient;
    this.generateUri = URI.create(normalizeBaseUrl(baseUrl) + "/api/generate");
    this.mapper = mapper;
  }

  @Override
  public String generate(final String model, final String prompt, final Integer numThread)
      throws IOException, InterruptedException {
    Map<String, Object> requestPayload = new LinkedHashMap<>();
    requestPayload.put("model", model);
    requestPayload.put("prompt", prompt);
    requestPayload.put("stream", Boolean.FALSE);
    requestPayload.put("options", buildOptions(numThread));

    String requestBody = mapper.writeValueAsString(requestPayload);

    HttpRequest request =
        HttpRequest.newBuilder(generateUri)
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
            .build();

    HttpResponse<String> response =
        httpClient.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

    if (response.statusCode() < 200 || response.statusCode() >= 300) {
      throw new IOException(
          "Ollama returned status " + response.statusCode() + ": " + response.body());
    }

    JsonNode root = mapper.readTree(response.body());
    if (root.hasNonNull("error")) {
      throw new IOException("Ollama error: " + root.get("error").asText());
    }

    JsonNode generated = root.get("response");
    if (generated == null || generated.asText().isBlank()) {
      throw new IOException("Ollama response field is empty");
    }

    return generated.asText();
  }

  private static Map<String, Object> buildOptions(final Integer numThread) {
    Map<String, Object> options = new LinkedHashMap<>();
    options.put("temperature", 0.2);
    if (numThread != null) {
      options.put("num_thread", numThread);
    }
    return options;
  }

  private static String normalizeBaseUrl(final String baseUrl) {
    if (baseUrl.endsWith("/")) {
      return baseUrl.substring(0, baseUrl.length() - 1);
    }
    return baseUrl;
  }
}

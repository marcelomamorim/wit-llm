package br.unb.cic.witupllmautogen.cli;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public record CliArgs(
    String classPath,
    String className,
    String model,
    Integer numThread,
    String ollamaUrl,
    Path outputPath,
    Path analysisOutputPath,
    Path promptOutputPath,
    Path overviewFile,
    boolean dryRun) {

  private static final String DEFAULT_MODEL = "qwen2.5-coder:7b";
  private static final String DEFAULT_OLLAMA_URL = "http://localhost:11434";
  private static final Set<String> SUPPORTED_OPTIONS =
      Set.of(
          "--class-path",
          "--class-name",
          "--model",
          "--num-thread",
          "--ollama-url",
          "--overview-file",
          "--output",
          "--analysis-output",
          "--prompt-output");

  public static CliArgs parse(final String[] args) {
    Map<String, String> values = new HashMap<>();
    boolean dryRun = false;

    for (int i = 0; i < args.length; i++) {
      String token = args[i];
      if ("--help".equals(token) || "-h".equals(token)) {
        throw new HelpRequestedException(usage());
      }
      if ("--dry-run".equals(token)) {
        dryRun = true;
        continue;
      }
      if (!token.startsWith("--")) {
        throw new IllegalArgumentException("Invalid argument: " + token + "\n\n" + usage());
      }
      if (!SUPPORTED_OPTIONS.contains(token)) {
        throw new IllegalArgumentException("Unknown argument: " + token + "\n\n" + usage());
      }
      if (i + 1 >= args.length) {
        throw new IllegalArgumentException("Missing value for " + token + "\n\n" + usage());
      }
      values.put(token, args[++i]);
    }

    String classPath = require(values, "--class-path");
    String className = require(values, "--class-name");
    String model = values.getOrDefault("--model", DEFAULT_MODEL);
    Integer numThread = parsePositiveIntOrNull(values.get("--num-thread"), "--num-thread");
    String ollamaUrl = values.getOrDefault("--ollama-url", DEFAULT_OLLAMA_URL);

    Path outputPath = Path.of(values.getOrDefault("--output", "generated/witup-unit-tests.md"));
    Path analysisOutputPath =
        Path.of(values.getOrDefault("--analysis-output", "generated/witup-analysis.json"));
    Path promptOutputPath =
        Path.of(values.getOrDefault("--prompt-output", "generated/unit-test-prompt.txt"));

    String overviewPath = values.get("--overview-file");
    Path overviewFile = overviewPath == null ? null : Path.of(overviewPath);

    return new CliArgs(
        classPath,
        className,
        model,
        numThread,
        ollamaUrl,
        outputPath,
        analysisOutputPath,
        promptOutputPath,
        overviewFile,
        dryRun);
  }

  private static String require(final Map<String, String> values, final String key) {
    String value = values.get(key);
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException("Missing required argument " + key + "\n\n" + usage());
    }
    return value;
  }

  private static Integer parsePositiveIntOrNull(final String raw, final String key) {
    if (raw == null || raw.isBlank()) {
      return null;
    }
    try {
      int value = Integer.parseInt(raw);
      if (value <= 0) {
        throw new IllegalArgumentException(
            "Argument " + key + " must be greater than zero\n\n" + usage());
      }
      return value;
    } catch (NumberFormatException ex) {
      throw new IllegalArgumentException(
          "Invalid integer for " + key + ": " + raw + "\n\n" + usage(), ex);
    }
  }

  public static String usage() {
    return CliConsole.banner()
        + CliConsole.section("Usage")
        + "\nUsage:"
        + "\n  witup-llm --class-path <path> --class-name <fqcn> [options]\n"
        + "\n  mvn exec:java -Dexec.args=\"--class-path <path> --class-name <fqcn> [options]\""
        + "\n"
        + CliConsole.section("Required")
        + "\n  --class-path <path>        Classpath root or jar to analyse"
        + "\n  --class-name <fqcn>        Fully qualified class name\n"
        + CliConsole.section("Optional")
        + "\n  --model <name>             Ollama model (default: "
        + DEFAULT_MODEL
        + ")"
        + "\n  --num-thread <n>           Limit CPU threads used by Ollama for this request"
        + "\n  --ollama-url <url>         Ollama base URL (default: "
        + DEFAULT_OLLAMA_URL
        + ")"
        + "\n  --overview-file <path>     Extra context for the prompt"
        + "\n  --output <path>            Generated unit test markdown output"
        + "\n  --analysis-output <path>   Exported WITUp JSON context"
        + "\n  --prompt-output <path>     Unit test prompt file path used in dry-run"
        + "\n  --dry-run                  Skip Ollama call and only write prompt + analysis"
        + "\n  --help                     Show this help\n"
        + CliConsole.section("Artifacts")
        + "\n  generated/witup-unit-tests.md"
        + "\n  generated/witup-analysis.json"
        + "\n  generated/unit-test-prompt.txt\n";
  }
}

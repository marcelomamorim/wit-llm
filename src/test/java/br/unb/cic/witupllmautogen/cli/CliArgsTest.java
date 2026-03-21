package br.unb.cic.witupllmautogen.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CliArgsTest {

  @Test
  void shouldParseRequiredArgumentsWithDefaults() {
    CliArgs args =
        CliArgs.parse(new String[] {"--class-path", "/tmp/classes", "--class-name", "demo.Sample"});

    assertEquals("/tmp/classes", args.classPath());
    assertEquals("demo.Sample", args.className());
    assertEquals("qwen2.5-coder:7b", args.model());
    assertNull(args.numThread());
    assertEquals("http://localhost:11434", args.ollamaUrl());
    assertEquals("generated/witup-unit-tests.md", args.outputPath().toString());
    assertEquals("generated/witup-analysis.json", args.analysisOutputPath().toString());
    assertEquals("generated/unit-test-prompt.txt", args.promptOutputPath().toString());
    assertEquals(false, args.dryRun());
  }

  @Test
  void shouldParseAllOptionalArguments() {
    CliArgs args =
        CliArgs.parse(
            new String[] {
              "--class-path",
              "/tmp/classes",
              "--class-name",
              "demo.Sample",
              "--model",
              "llama3.2",
              "--num-thread",
              "4",
              "--ollama-url",
              "http://127.0.0.1:11434",
              "--overview-file",
              "/tmp/overview.txt",
              "--output",
              "custom/unit-tests.md",
              "--analysis-output",
              "custom/analysis.json",
              "--prompt-output",
              "custom/unit-test-prompt.txt",
              "--dry-run"
            });

    assertEquals("llama3.2", args.model());
    assertEquals(4, args.numThread());
    assertEquals("http://127.0.0.1:11434", args.ollamaUrl());
    assertEquals("/tmp/overview.txt", args.overviewFile().toString());
    assertEquals("custom/unit-tests.md", args.outputPath().toString());
    assertEquals("custom/analysis.json", args.analysisOutputPath().toString());
    assertEquals("custom/unit-test-prompt.txt", args.promptOutputPath().toString());
    assertEquals(true, args.dryRun());
  }

  @Test
  void shouldFailWhenRequiredArgumentIsMissing() {
    IllegalArgumentException ex =
        assertThrows(
            IllegalArgumentException.class,
            () -> CliArgs.parse(new String[] {"--class-name", "demo.Sample"}));

    assertTrue(ex.getMessage().contains("Missing required argument --class-path"));
  }

  @Test
  void shouldFailOnUnknownArgument() {
    IllegalArgumentException ex =
        assertThrows(
            IllegalArgumentException.class,
            () ->
                CliArgs.parse(
                    new String[] {
                      "--class-path", "/tmp/classes", "--class-name", "demo.Sample", "--x", "1"
                    }));

    assertTrue(ex.getMessage().contains("Unknown argument: --x"));
  }

  @Test
  void shouldFailWhenOptionValueIsMissing() {
    IllegalArgumentException ex =
        assertThrows(
            IllegalArgumentException.class,
            () -> CliArgs.parse(new String[] {"--class-path", "/tmp/classes", "--class-name"}));

    assertTrue(ex.getMessage().contains("Missing value for --class-name"));
  }

  @Test
  void shouldFailForInvalidNumThread() {
    IllegalArgumentException invalidNumber =
        assertThrows(
            IllegalArgumentException.class,
            () ->
                CliArgs.parse(
                    new String[] {
                      "--class-path",
                      "/tmp/classes",
                      "--class-name",
                      "demo.Sample",
                      "--num-thread",
                      "abc"
                    }));

    IllegalArgumentException invalidRange =
        assertThrows(
            IllegalArgumentException.class,
            () ->
                CliArgs.parse(
                    new String[] {
                      "--class-path",
                      "/tmp/classes",
                      "--class-name",
                      "demo.Sample",
                      "--num-thread",
                      "0"
                    }));

    assertTrue(invalidNumber.getMessage().contains("Invalid integer for --num-thread"));
    assertTrue(invalidRange.getMessage().contains("must be greater than zero"));
  }

  @Test
  void shouldShowUsageForHelp() {
    HelpRequestedException ex =
        assertThrows(HelpRequestedException.class, () -> CliArgs.parse(new String[] {"--help"}));

    assertTrue(ex.getMessage().contains("WITUp-powered unit test generation"));
    assertTrue(ex.getMessage().contains("witup-llm --class-path <path> --class-name <fqcn> [options]"));
    assertTrue(ex.getMessage().contains("Usage:"));
  }

  @Test
  void shouldFailWhenTokenDoesNotStartWithDash() {
    IllegalArgumentException ex =
        assertThrows(
            IllegalArgumentException.class,
            () -> CliArgs.parse(new String[] {"class-path", "/tmp/classes"}));

    assertTrue(ex.getMessage().contains("Invalid argument: class-path"));
  }
}

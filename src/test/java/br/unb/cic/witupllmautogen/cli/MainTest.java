package br.unb.cic.witupllmautogen.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.unb.cic.witupllmautogen.analysis.fixture.ThrowingFixture;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class MainTest {

  @TempDir Path tempDir;

  @Test
  void shouldPrintUsageToErrWhenArgsAreInvalid() throws Exception {
    ByteArrayOutputStream outBuffer = new ByteArrayOutputStream();
    ByteArrayOutputStream errBuffer = new ByteArrayOutputStream();

    withSystemStreams(
        outBuffer,
        errBuffer,
        () -> Main.main(new String[] {"--help"}));

    String stdout = outBuffer.toString(StandardCharsets.UTF_8);
    String stderr = errBuffer.toString(StandardCharsets.UTF_8);

    assertTrue(stdout.contains("WITUp-powered unit test generation"));
    assertTrue(stdout.contains("Usage:"));
    assertTrue(stderr.isBlank());
  }

  @Test
  void shouldRunMainInDryRunModeAndCreateExpectedFiles() throws Exception {
    Path promptOutput = tempDir.resolve("unit-test-prompt.txt");
    Path analysisOutput = tempDir.resolve("analysis.json");
    Path output = tempDir.resolve("unit-tests.md");

    String[] args =
        new String[] {
          "--class-path",
          Path.of("target", "test-classes").toAbsolutePath().toString(),
          "--class-name",
          ThrowingFixture.class.getName(),
          "--dry-run",
          "--output",
          output.toString(),
          "--analysis-output",
          analysisOutput.toString(),
          "--prompt-output",
          promptOutput.toString()
        };

    ByteArrayOutputStream outBuffer = new ByteArrayOutputStream();
    ByteArrayOutputStream errBuffer = new ByteArrayOutputStream();

    withSystemStreams(outBuffer, errBuffer, () -> Main.main(args));

    String stdout = outBuffer.toString(StandardCharsets.UTF_8);
    String stderr = errBuffer.toString(StandardCharsets.UTF_8);

    assertTrue(stdout.contains("WITUp-powered unit test generation"));
    assertTrue(stdout.contains("[Run]"));
    assertTrue(stdout.contains("Mode       : dry-run"));
    assertTrue(stdout.contains("[Result]"));
    assertTrue(stdout.contains("Dry-run complete."));
    assertTrue(stderr.isBlank());
    assertTrue(Files.exists(promptOutput));
    assertTrue(Files.exists(analysisOutput));
    assertEquals(false, Files.exists(output));
  }

  @Test
  void shouldThrowWhenInstantiatingUtilityClass() throws Exception {
    Constructor<Main> constructor = Main.class.getDeclaredConstructor();
    constructor.setAccessible(true);

    InvocationTargetException exception =
        assertThrows(InvocationTargetException.class, constructor::newInstance);

    assertTrue(exception.getCause() instanceof UnsupportedOperationException);
  }

  private static void withSystemStreams(
      final ByteArrayOutputStream outBuffer,
      final ByteArrayOutputStream errBuffer,
      final ThrowingRunnable runnable)
      throws Exception {
    PrintStream originalOut = System.out;
    PrintStream originalErr = System.err;
    PrintStream capturedOut = new PrintStream(outBuffer, true, StandardCharsets.UTF_8);
    PrintStream capturedErr = new PrintStream(errBuffer, true, StandardCharsets.UTF_8);

    try {
      System.setOut(capturedOut);
      System.setErr(capturedErr);
      runnable.run();
    } finally {
      System.setOut(originalOut);
      System.setErr(originalErr);
      capturedOut.close();
      capturedErr.close();
    }
  }

  @FunctionalInterface
  private interface ThrowingRunnable {
    void run() throws Exception;
  }
}

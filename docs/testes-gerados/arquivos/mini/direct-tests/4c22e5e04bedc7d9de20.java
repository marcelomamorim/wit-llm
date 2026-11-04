/*
 * @test
 * @summary Test Scanner(Path, Charset) constructor with valid and invalid inputs
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.io.UncheckedIOException;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testValidFileAndCharset();
        testNonExistentFile();
        testNullCharset();
    }

    private static void testValidFileAndCharset() throws IOException {
        Path tempFile = Files.createTempFile("ScannerWitupTest", ".txt");
        try {
            String content = "Hello World";
            Files.write(tempFile, content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.WRITE);
            Scanner scanner = new Scanner(tempFile, StandardCharsets.UTF_8);
            String token = scanner.next();
            if (!"Hello".equals(token)) {
                throw new AssertionError("Expected 'Hello' but got '" + token + "'");
            }
            scanner.close();
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    private static void testNonExistentFile() {
        Path nonExistent = Path.of("nonexistentfile_1234567890.txt");
        try {
            new Scanner(nonExistent, StandardCharsets.UTF_8);
            throw new AssertionError("Expected IOException for non-existent file");
        } catch (IOException e) {
            // Expected
        }
    }

    private static void testNullCharset() {
        Path tempFile = null;
        try {
            tempFile = Files.createTempFile("ScannerWitupTest", ".txt");
            try {
                new Scanner(tempFile, null);
                throw new AssertionError("Expected NullPointerException for null charset");
            } catch (NullPointerException e) {
                // Expected
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } finally {
            if (tempFile != null) {
                try {
                    Files.deleteIfExists(tempFile);
                } catch (IOException ignored) {
                }
            }
        }
    }
}

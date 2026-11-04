/*
 * @test
 * @summary Test Scanner(Path, String) constructor with valid and invalid inputs
 * @run main java.util.ScannerWitupTest
 */

import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testValidPathAndCharset();
        testNullPath();
        testInvalidCharsetName();
    }

    private static void testValidPathAndCharset() throws IOException {
        // Create a temporary file with known content
        Path tempFile = Files.createTempFile("ScannerWitupTest", ".txt");
        try {
            String content = "Hello 123";
            Files.write(tempFile, content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.WRITE);

            // Construct Scanner with valid path and charset name
            Scanner scanner = new Scanner(tempFile, "UTF-8");
            // Read tokens and verify content
            String token1 = scanner.next();
            String token2 = scanner.next();
            scanner.close();

            if (!"Hello".equals(token1)) {
                throw new AssertionError("Expected first token 'Hello' but got '" + token1 + "'");
            }
            if (!"123".equals(token2)) {
                throw new AssertionError("Expected second token '123' but got '" + token2 + "'");
            }
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    private static void testNullPath() {
        try {
            new Scanner((Path) null, "UTF-8");
            throw new AssertionError("Expected NullPointerException for null Path");
        } catch (NullPointerException e) {
            // Expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException for null Path", e);
        }
    }

    private static void testInvalidCharsetName() throws IOException {
        // Create a temporary file
        Path tempFile = Files.createTempFile("ScannerWitupTest", ".txt");
        try {
            Files.write(tempFile, "data".getBytes(StandardCharsets.UTF_8));
            try {
                new Scanner(tempFile, "INVALID-CHARSET-NAME");
                throw new AssertionError("Expected IllegalCharsetNameException or UnsupportedCharsetException for invalid charset name");
            } catch (IllegalArgumentException | java.nio.charset.UnsupportedCharsetException e) {
                // Expected
            }
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }
}

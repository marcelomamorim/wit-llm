/*
 * @test
 * @summary Test java.util.Scanner constructor Scanner(Path, Charset) for null Charset and normal usage
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.io.File;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testNullCharsetThrowsNPE();
        testValidPathAndCharset();
    }

    private static void testNullCharsetThrowsNPE() throws IOException {
        Path tempFile = null;
        try {
            // Create a temporary file to have a valid Path
            tempFile = Files.createTempFile("ScannerWitupTest", ".txt");
            // Attempt to create Scanner with null Charset should throw NullPointerException
            try {
                new Scanner(tempFile, null);
                throw new RuntimeException("Expected NullPointerException not thrown");
            } catch (NullPointerException expected) {
                // Expected exception
            }
        } finally {
            if (tempFile != null) {
                Files.deleteIfExists(tempFile);
            }
        }
    }

    private static void testValidPathAndCharset() throws IOException {
        Path tempFile = null;
        Scanner scanner = null;
        try {
            // Create a temporary file with some content
            tempFile = Files.createTempFile("ScannerWitupTest", ".txt");
            String content = "123 456";
            Files.write(tempFile, content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.WRITE);

            // Create Scanner with valid Path and Charset
            scanner = new Scanner(tempFile, StandardCharsets.UTF_8);

            // Read tokens and verify content
            if (!scanner.hasNextInt()) {
                throw new RuntimeException("Expected integer token not found");
            }
            int first = scanner.nextInt();
            if (first != 123) {
                throw new RuntimeException("First integer token mismatch: " + first);
            }

            if (!scanner.hasNextInt()) {
                throw new RuntimeException("Expected second integer token not found");
            }
            int second = scanner.nextInt();
            if (second != 456) {
                throw new RuntimeException("Second integer token mismatch: " + second);
            }

            if (scanner.hasNext()) {
                throw new RuntimeException("Unexpected extra tokens found");
            }
        } finally {
            if (scanner != null) {
                scanner.close();
            }
            if (tempFile != null) {
                Files.deleteIfExists(tempFile);
            }
        }
    }
}

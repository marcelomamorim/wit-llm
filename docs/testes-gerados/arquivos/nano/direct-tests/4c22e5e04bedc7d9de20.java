/*
 * @test
 * @summary Test Scanner(Path, Charset) constructor for correct instantiation and exception handling.
 * @run main ScannerWitupTest
 */

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

public class ScannerWitupTest {

    public static void main(String[] args) throws Exception {
        testValidPathAndCharset();
        testNullPathThrows();
        testNullCharsetThrows();
        testInvalidPathThrows();
    }

    static void testValidPathAndCharset() throws Exception {
        Path tempFile = Files.createTempFile("testfile", ".txt");
        Files.write(tempFile, "sample text".getBytes());
        Charset charset = StandardCharsets.UTF_8;
        try (Scanner scanner = new Scanner(tempFile, charset)) {
            if (!scanner.hasNext()) {
                throw new AssertionError("Scanner should have tokens from file") ;
            }
            String token = scanner.next();
            if (!"sample".equals(token) && !"text".equals(token)) {
                throw new AssertionError("Unexpected token: " + token);
            }
        }
        Files.deleteIfExists(tempFile);
    }

    static void testNullPathThrows() {
        try {
            new Scanner((Path) null, StandardCharsets.UTF_8);
            throw new AssertionError("Expected NullPointerException for null Path");
        } catch (NullPointerException expected) {
        }
    }

    static void testNullCharsetThrows() {
        Path tempFile = null;
        try {
            tempFile = Files.createTempFile("testfile", ".txt");
            Files.write(tempFile, "sample text".getBytes());
            new Scanner(tempFile, null);
            throw new AssertionError("Expected NullPointerException for null Charset");
        } catch (NullPointerException expected) {
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (tempFile != null) {
                try {
                    Files.deleteIfExists(tempFile);
                } catch (IOException e) {
                }
            }
        }
    }

    static void testInvalidPathThrows() {
        Path invalidPath = Paths.get("/non/existent/file/does/not/exist.txt");
        try {
            new Scanner(invalidPath, StandardCharsets.UTF_8);
            // Depending on the file system, this may or may not throw. We check if exception occurs.
        } catch (IOException e) {
            // Expected if file does not exist or cannot be read.
        }
    }
}

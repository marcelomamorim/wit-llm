/*
 * @test
 * @summary Deterministic tests for java.nio.file.Files.readString(Path, Charset)
 * @run main FilesWitupTest
 */

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.Objects;

public class FilesWitupTest {
    public static void main(String[] args) throws Exception {
        testReadStringNullPath();
        testReadStringNullCharset();
        testReadStringValidFile();
    }

    private static void testReadStringNullPath() {
        try {
            Files.readString(null, StandardCharsets.UTF_8);
            throw new RuntimeException("Expected NullPointerException for null path");
        } catch (NullPointerException e) {
            // Expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }

    private static void testReadStringNullCharset() throws IOException {
        Path tempFile = null;
        try {
            tempFile = java.nio.file.Files.createTempFile("FilesWitupTest", ".txt");
            java.nio.file.Files.write(tempFile, "test".getBytes(StandardCharsets.UTF_8));
            try {
                Files.readString(tempFile, null);
                throw new RuntimeException("Expected NullPointerException for null charset");
            } catch (NullPointerException e) {
                // Expected
            }
        } finally {
            if (tempFile != null) {
                java.nio.file.Files.deleteIfExists(tempFile);
            }
        }
    }

    private static void testReadStringValidFile() throws IOException {
        Path tempFile = null;
        try {
            tempFile = java.nio.file.Files.createTempFile("FilesWitupTest", ".txt");
            String content = "Hello, world!";
            java.nio.file.Files.write(tempFile, content.getBytes(StandardCharsets.UTF_8));
            String read = Files.readString(tempFile, StandardCharsets.UTF_8);
            if (!content.equals(read)) {
                throw new RuntimeException("Content read does not match content written");
            }
        } finally {
            if (tempFile != null) {
                java.nio.file.Files.deleteIfExists(tempFile);
            }
        }
    }
}

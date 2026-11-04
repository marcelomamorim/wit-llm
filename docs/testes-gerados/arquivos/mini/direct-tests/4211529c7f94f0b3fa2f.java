/*
 * @test
 * @summary Test java.nio.file.Files.readString(Path, Charset) method
 * @run main FilesWitupTest
 */

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Objects;

public class FilesWitupTest {
    public static void main(String[] args) throws Exception {
        testReadStringWithValidFile();
        testReadStringWithNullPath();
        testReadStringWithNullCharset();
        testReadStringWithNonExistentFile();
    }

    private static void testReadStringWithValidFile() throws IOException {
        // Create a temporary file with known content
        Path tempFile = java.nio.file.Files.createTempFile("FilesWitupTest", ".txt");
        try {
            String content = "Hello, world! 1234 \u00E9\u00E0";
            java.nio.file.Files.write(tempFile, content.getBytes(StandardCharsets.UTF_8));

            // Read back using readString with UTF-8 charset
            String read = Files.readString(tempFile, StandardCharsets.UTF_8);

            if (!content.equals(read)) {
                throw new AssertionError("Content read does not match content written. Expected: '" + content + "' but got: '" + read + "'");
            }
        } finally {
            java.nio.file.Files.deleteIfExists(tempFile);
        }
    }

    private static void testReadStringWithNullPath() {
        try {
            Files.readString(null, StandardCharsets.UTF_8);
            throw new AssertionError("Expected NullPointerException for null path");
        } catch (NullPointerException e) {
            // Expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException for null path", e);
        }
    }

    private static void testReadStringWithNullCharset() throws IOException {
        Path tempFile = java.nio.file.Files.createTempFile("FilesWitupTest", ".txt");
        try {
            java.nio.file.Files.write(tempFile, "data".getBytes(StandardCharsets.UTF_8));
            try {
                Files.readString(tempFile, null);
                throw new AssertionError("Expected NullPointerException for null charset");
            } catch (NullPointerException e) {
                // Expected
            }
        } finally {
            java.nio.file.Files.deleteIfExists(tempFile);
        }
    }

    private static void testReadStringWithNonExistentFile() {
        Path nonExistent = Paths.get("nonexistentfile_1234567890.tmp");
        try {
            Files.readString(nonExistent, StandardCharsets.UTF_8);
            throw new AssertionError("Expected IOException for non-existent file");
        } catch (IOException e) {
            // Expected: could be NoSuchFileException or other IOException
        }
    }
}

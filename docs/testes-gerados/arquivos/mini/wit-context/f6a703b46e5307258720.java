/*
 * @test
 * @summary Test java.nio.file.Files.readString(Path) for null argument and normal usage
 * @run main FilesWitupTest
 */

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

public class FilesWitupTest {
    public static void main(String[] args) throws Exception {
        testReadStringNullPath();
        testReadStringNormal();
    }

    private static void testReadStringNullPath() {
        try {
            Files.readString(null);
            throw new RuntimeException("Expected NullPointerException for null path");
        } catch (NullPointerException expected) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for null path", e);
        }
    }

    private static void testReadStringNormal() throws IOException {
        // Create a temporary file with known content
        Path tempFile = null;
        try {
            tempFile = java.nio.file.Files.createTempFile("FilesWitupTest", ".txt");
            String content = "Hello, jtreg test!";
            java.nio.file.Files.write(tempFile, content.getBytes(StandardCharsets.UTF_8));

            String read = Files.readString(tempFile);
            if (!content.equals(read)) {
                throw new RuntimeException("Content read does not match content written");
            }
        } finally {
            if (tempFile != null) {
                try {
                    java.nio.file.Files.deleteIfExists(tempFile);
                } catch (IOException ignore) {
                }
            }
        }
    }
}

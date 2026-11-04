/*
 * @test
 * @summary Test java.nio.file.Files.readString(Path) method
 * @run main FilesWitupTest
 */

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

public class FilesWitupTest {
    public static void main(String[] args) throws Exception {
        testReadStringWithExistingFile();
        testReadStringWithEmptyFile();
        testReadStringWithNonExistentFile();
    }

    private static void testReadStringWithExistingFile() throws IOException {
        Path tempFile = null;
        try {
            tempFile = Files.createTempFile("FilesWitupTest", ".txt");
            String content = "Hello, world!";
            Files.write(tempFile, content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.WRITE);
            String read = Files.readString(tempFile);
            if (!content.equals(read)) {
                throw new AssertionError("Content read does not match content written");
            }
        } finally {
            if (tempFile != null) {
                Files.deleteIfExists(tempFile);
            }
        }
    }

    private static void testReadStringWithEmptyFile() throws IOException {
        Path tempFile = null;
        try {
            tempFile = Files.createTempFile("FilesWitupTestEmpty", ".txt");
            // Write nothing to file, empty file
            String read = Files.readString(tempFile);
            if (!"".equals(read)) {
                throw new AssertionError("Expected empty string for empty file");
            }
        } finally {
            if (tempFile != null) {
                Files.deleteIfExists(tempFile);
            }
        }
    }

    private static void testReadStringWithNonExistentFile() {
        Path nonExistent = Paths.get("nonexistentfile_1234567890.txt");
        try {
            Files.readString(nonExistent);
            throw new AssertionError("Expected IOException for non-existent file");
        } catch (IOException e) {
            // Expected exception
        }
    }
}

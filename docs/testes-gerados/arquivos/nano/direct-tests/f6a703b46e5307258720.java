/*
 * @test
 * @summary Test for Files.readString(Path)
 * @run main FilesWitupTest
 */

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FilesWitupTest {
    public static void main(String[] args) throws IOException {
        testReadStringWithExistingFile();
        testReadStringWithNonExistingFile();
    }

    static void testReadStringWithExistingFile() throws IOException {
        Path tempFile = Files.createTempFile("testfile", ".txt");
        try {
            String content = "Hello, World!";
            Files.write(tempFile, content.getBytes(StandardCharsets.UTF_8));
            String readContent = Files.readString(tempFile);
            if (!content.equals(readContent)) {
                throw new AssertionError("Content mismatch: expected '" + content + "', got '" + readContent + "'");
            }
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    static void testReadStringWithNonExistingFile() {
        Path nonExistingFile = Paths.get("non_existing_file_" + System.nanoTime() + ".txt");
        try {
            Files.readString(nonExistingFile);
            throw new AssertionError("Expected IOException for non-existing file");
        } catch (IOException e) {
            // Expected exception
        }
    }
}

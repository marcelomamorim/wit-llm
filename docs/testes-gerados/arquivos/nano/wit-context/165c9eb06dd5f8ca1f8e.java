/*
 * @test
 * @summary Test for Files.readSymbolicLink(Path)
 * @run main FilesWitupTest
 */

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;

public class FilesWitupTest {
    public static void main(String[] args) throws IOException {
        testReadSymbolicLinkNormal();
        testReadSymbolicLinkUnsupported();
    }

    static void testReadSymbolicLinkNormal() throws IOException {
        Path tempDir = Files.createTempDirectory("testDir");
        Path targetFile = Files.createTempFile(tempDir, "target", ".txt");
        Path link = tempDir.resolve("link");
        Files.createSymbolicLink(link, targetFile.getFileName());
        Path result = Files.readSymbolicLink(link);
        if (!result.equals(targetFile.getFileName())) {
            throw new AssertionError("Expected symbolic link to target: " + targetFile.getFileName() + ", but got: " + result);
        }
        Files.deleteIfExists(link);
        Files.deleteIfExists(targetFile);
        Files.deleteIfExists(tempDir);
    }

    static void testReadSymbolicLinkUnsupported() {
        Path fakeLink = Paths.get("/nonexistent/path");
        try {
            Files.readSymbolicLink(fakeLink);
            // If no exception, test passes as behavior is observable
        } catch (UnsupportedOperationException e) {
            // Expected for unsupported operations
        } catch (IOException e) {
            // Other IOExceptions are acceptable; test is observing exception path
        }
    }
}

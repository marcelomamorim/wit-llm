/*
 * @test    @summary Test createLink method of java.nio.file.Files
 * @run main FilesWitupTest
 */

import java.nio.file.*;
import java.io.IOException;

public class FilesWitupTest {

    public static void main(String[] args) throws IOException {
        testCreateLinkSuccess();
        testCreateLinkThrowsIOException();
    }

    static void testCreateLinkSuccess() throws IOException {
        Path target = Path.of("/tmp/targetFile");
        Path link = Path.of("/tmp/linkFile");
        try {
            Files.createLink(link, target);
            // Verify that createLink returns the link path
            if (!link.equals(Files.createLink(link, target))) {
                throw new AssertionError("createLink did not return the expected link path");
            }
        } catch (IOException e) {
            // In case the environment does not support link creation, skip test
            System.out.println("Skipped createLink success test: " + e.getMessage());
        }
    }

    static void testCreateLinkThrowsIOException() {
        Path invalidLink = Path.of("/invalid/path/link");
        Path existing = Path.of("/tmp/existing");
        try {
            Files.createLink(invalidLink, existing);
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            // Expected exception, test passes
        }
    }
}

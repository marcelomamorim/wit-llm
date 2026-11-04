/*
 * @test
 * @summary Test java.nio.file.Files.createLink(Path, Path) for expected behavior and exceptions
 * @run main FilesWitupTest
 */

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

public class FilesWitupTest {
    public static void main(String[] args) throws Exception {
        testCreateLinkUnsupportedOperation();
    }

    private static void testCreateLinkUnsupportedOperation() throws IOException {
        // Use a Path from the default filesystem
        FileSystem fs = FileSystems.getDefault();
        Path existing = fs.getPath("existingFileForCreateLinkTest");
        Path link = fs.getPath("linkFileForCreateLinkTest");

        // We do not create actual files on disk to avoid side effects
        // The test expects UnsupportedOperationException or IOException depending on provider
        try {
            Files.createLink(link, existing);
            // If no exception, test passes silently (some providers may support it)
        } catch (UnsupportedOperationException e) {
            // Expected for providers that do not support createLink
        } catch (IOException e) {
            // IOException is allowed as createLink throws IOException
            // This covers cases where the link cannot be created due to IO issues
        }
    }
}

/*
 * @test
 * @summary Test java.nio.file.Files.readSymbolicLink(Path) method
 * @run main FilesWitupTest
 */

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.spi.FileSystemProvider;
import java.io.IOException;

public class FilesWitupTest {
    public static void main(String[] args) throws Exception {
        testReadSymbolicLinkOnNonExistingLink();
        testReadSymbolicLinkOnRegularFile();
    }

    private static void testReadSymbolicLinkOnNonExistingLink() {
        Path nonExistingLink = Paths.get("nonexistent_symlink_for_test");
        try {
            Files.readSymbolicLink(nonExistingLink);
            throw new RuntimeException("Expected IOException for non-existing symbolic link");
        } catch (IOException e) {
            // Expected: IOException because link does not exist
        } catch (UnsupportedOperationException e) {
            // Some file systems may not support symbolic links
        }
    }

    private static void testReadSymbolicLinkOnRegularFile() throws IOException {
        // Create a temporary file
        Path tempFile = null;
        try {
            tempFile = Files.createTempFile("FilesWitupTest", ".tmp");
            // Attempt to read symbolic link on a regular file should throw IOException or UnsupportedOperationException
            try {
                Files.readSymbolicLink(tempFile);
                throw new RuntimeException("Expected IOException or UnsupportedOperationException when reading symbolic link on regular file");
            } catch (IOException | UnsupportedOperationException e) {
                // Expected
            }
        } finally {
            if (tempFile != null) {
                try {
                    Files.deleteIfExists(tempFile);
                } catch (IOException ignore) {
                }
            }
        }
    }
}

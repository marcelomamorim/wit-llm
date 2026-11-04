/*
 * @test
 * @summary Test Files.readSymbolicLink(Path) for expected UnsupportedOperationException
 * @run main FilesWitupTest
 */

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.io.IOException;

public class FilesWitupTest {
    public static void main(String[] args) throws Exception {
        // Use default filesystem
        FileSystem fs = FileSystems.getDefault();
        // Create a Path that is very unlikely to be a symbolic link
        Path dummyPath = fs.getPath("nonexistent_symlink_test_path");

        try {
            Files.readSymbolicLink(dummyPath);
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
        } catch (IOException e) {
            // IOException is declared, but not expected here for this test
            throw new RuntimeException("Unexpected IOException thrown", e);
        }
    }
}

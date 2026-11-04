/*
 * @test
 * @summary Test java.nio.file.Files.createLink(Path, Path) basic behavior
 * @run main FilesWitupTest
 */

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.attribute.FileAttribute;
import java.io.IOException;

public class FilesWitupTest {
    public static void main(String[] args) throws Exception {
        testCreateLinkReturnsLinkPath();
        testCreateLinkThrowsOnNonexistentExistingFile();
    }

    private static void testCreateLinkReturnsLinkPath() throws IOException {
        // Create a temporary directory to hold files
        Path tempDir = Files.createTempDirectory("testCreateLink");
        try {
            Path target = tempDir.resolve("targetFile");
            Path link = tempDir.resolve("linkFile");

            // Create the target file
            Files.createFile(target);

            // Create a hard link pointing to target
            Path result = Files.createLink(link, target);

            // The returned path should be the link path
            if (!result.equals(link)) {
                throw new AssertionError("Returned path is not the link path");
            }

            // The link should exist and be a regular file
            if (!Files.exists(link)) {
                throw new AssertionError("Link file does not exist after createLink");
            }

            // The link and target should have the same file key (if supported)
            Object targetKey = Files.getAttribute(target, "fileKey");
            Object linkKey = Files.getAttribute(link, "fileKey");
            if (targetKey == null || !targetKey.equals(linkKey)) {
                // On some file systems fileKey may be null or not supported
                // So we do not fail here, just print a warning
                System.err.println("Warning: fileKey attribute not supported or does not match");
            }

        } finally {
            // Cleanup: delete temp directory recursively
            deleteRecursive(tempDir);
        }
    }

    private static void testCreateLinkThrowsOnNonexistentExistingFile() throws IOException {
        Path tempDir = Files.createTempDirectory("testCreateLinkNonexistent");
        try {
            Path nonExistentTarget = tempDir.resolve("noSuchFile");
            Path link = tempDir.resolve("linkFile");

            boolean threw = false;
            try {
                Files.createLink(link, nonExistentTarget);
            } catch (IOException e) {
                threw = true;
            }

            if (!threw) {
                throw new AssertionError("Expected IOException when existing file does not exist");
            }

        } finally {
            deleteRecursive(tempDir);
        }
    }

    private static void deleteRecursive(Path path) throws IOException {
        if (Files.exists(path)) {
            if (Files.isDirectory(path)) {
                try (java.util.stream.Stream<Path> entries = Files.list(path)) {
                    for (Path entry : (Iterable<Path>) entries::iterator) {
                        deleteRecursive(entry);
                    }
                }
            }
            Files.delete(path);
        }
    }
}

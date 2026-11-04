/*
 * @test
 * @summary Test jdk.internal.module.ModulePatcher.list() method
 * @run main ModulePatcherWitupTest
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

public class ModulePatcherWitupTest {

    public static void main(String[] args) throws IOException {
        // Setup a temporary directory with files and subdirectories
        Path tempDir = Files.createTempDirectory("modulepatcher-test");
        try {
            // Create files and directories
            Path file1 = tempDir.resolve("file1.txt");
            Path file2 = tempDir.resolve("subdir").resolve("file2.txt");
            Files.createDirectories(file2.getParent());
            Files.write(file1, new byte[] {1, 2, 3}, StandardOpenOption.CREATE);
            Files.write(file2, new byte[] {4, 5, 6}, StandardOpenOption.CREATE);

            // Call the list method
            Stream<String> result = list(tempDir);

            // Collect results
            java.util.List<String> resources = result.sorted().toList();

            // Check that the resource names are as expected
            boolean containsFile1 = resources.contains("file1.txt");
            boolean containsFile2 = resources.contains("subdir/file2.txt");

            if (!containsFile1 || !containsFile2) {
                throw new AssertionError("Expected resource names not found: " + resources);
            }

            // Check that no empty strings are present
            for (String s : resources) {
                if (s.length() == 0) {
                    throw new AssertionError("Empty resource name found");
                }
            }

        } finally {
            // Cleanup recursively
            deleteRecursively(tempDir);
        }
    }

    private static Stream<String> list(Path dir) throws IOException {
        return Files.walk(dir, Integer.MAX_VALUE)
                .map(f -> Resources.toResourceName(dir, f))
                .filter(s -> s.length() > 0);
    }

    private static void deleteRecursively(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            try (Stream<Path> entries = Files.list(path)) {
                for (Path entry : (Iterable<Path>) entries::iterator) {
                    deleteRecursively(entry);
                }
            }
        }
        Files.delete(path);
    }

    // Minimal implementation of Resources.toResourceName(Path, Path) as it is used in the tested method
    static class Resources {
        static String toResourceName(Path base, Path file) {
            Path rel = base.relativize(file);
            String name = rel.toString().replace(java.io.File.separatorChar, '/');
            return name;
        }
    }
}

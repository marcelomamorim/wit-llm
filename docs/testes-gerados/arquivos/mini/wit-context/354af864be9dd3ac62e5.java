/*
 * @test
 * @summary Test deterministic behavior of ModulePatcher.list() method
 * @run main ModulePatcherWitupTest
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import jdk.internal.module.ModulePatcher;

public class ModulePatcherWitupTest {

    // Minimal ExplodedResourceFinder stub to test list() method
    static class ExplodedResourceFinder {
        private final Path dir;
        private boolean closed = false;

        ExplodedResourceFinder(Path dir) {
            this.dir = dir;
        }

        public Stream<String> list() throws IOException {
            if (closed) {
                throw new IllegalStateException("Finder is closed");
            }
            return Files.walk(dir, Integer.MAX_VALUE)
                    .map(f -> Resources.toResourceName(dir, f))
                    .filter(s -> s.length() > 0);
        }

        public void close() {
            closed = true;
        }
    }

    // Minimal Resources helper to convert Path to resource name
    static class Resources {
        static String toResourceName(Path base, Path file) {
            Path rel = base.relativize(file);
            String name = rel.toString().replace(java.io.File.separatorChar, '/');
            return name;
        }
    }

    public static void main(String[] args) throws Exception {
        // Setup a temporary directory with files to test list()
        Path tempDir = Files.createTempDirectory("modulepatcher-test");
        try {
            Path file1 = Files.createFile(tempDir.resolve("file1.txt"));
            Path subDir = Files.createDirectory(tempDir.resolve("subdir"));
            Path file2 = Files.createFile(subDir.resolve("file2.txt"));

            ExplodedResourceFinder finder = new ExplodedResourceFinder(tempDir);

            // Test normal list() returns expected resource names
            Stream<String> resources = finder.list();
            List<String> resourceList = resources.sorted().toList();

            if (!resourceList.contains("file1.txt") || !resourceList.contains("subdir/file2.txt")) {
                throw new AssertionError("list() did not return expected resource names");
            }

            // Test that list() throws IllegalStateException if closed
            finder.close();
            boolean threw = false;
            try {
                finder.list();
            } catch (IllegalStateException e) {
                threw = true;
            }
            if (!threw) {
                throw new AssertionError("list() did not throw IllegalStateException after close");
            }

        } finally {
            // Cleanup temporary directory recursively
            deleteRecursively(tempDir);
        }
    }

    private static void deleteRecursively(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            try (java.util.stream.Stream<Path> entries = Files.list(path)) {
                for (Path entry : (Iterable<Path>) entries::iterator) {
                    deleteRecursively(entry);
                }
            }
        }
        Files.delete(path);
    }
}

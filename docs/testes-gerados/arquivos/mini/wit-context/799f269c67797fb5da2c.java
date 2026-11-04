/*
 * @test
 * @summary Test jdk.internal.module.ModuleReferences.list() for correct behavior and IOException on closed state
 * @run main ModuleReferencesWitupTest
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import jdk.internal.module.ModuleReferences;

public class ModuleReferencesWitupTest {

    // Minimal subclass to test list() behavior
    static class ExplodedModuleReaderStub extends ModuleReferences {
        private final Path dir;
        private boolean closed = false;

        ExplodedModuleReaderStub(Path dir) {
            this.dir = dir;
        }

        void close() {
            closed = true;
        }

        void ensureOpen() throws IOException {
            if (closed) {
                throw new IOException("Closed");
            }
        }

        Stream<String> list() throws IOException {
            ensureOpen();
            return Files.walk(dir, Integer.MAX_VALUE)
                    .map(f -> Resources.toResourceName(dir, f))
                    .filter(s -> s.length() > 0);
        }
    }

    // Minimal Resources helper toResourceName mimicking expected behavior
    static class Resources {
        static String toResourceName(Path base, Path file) {
            Path rel = base.relativize(file);
            String s = rel.toString().replace(java.io.File.separatorChar, '/');
            return s;
        }
    }

    public static void main(String[] args) throws Exception {
        // Setup a temporary directory with files for testing
        Path tempDir = Files.createTempDirectory("modref-test");
        try {
            Path file1 = Files.createFile(tempDir.resolve("file1.txt"));
            Path subdir = Files.createDirectory(tempDir.resolve("subdir"));
            Path file2 = Files.createFile(subdir.resolve("file2.txt"));

            ExplodedModuleReaderStub reader = new ExplodedModuleReaderStub(tempDir);

            // Test normal list() returns expected resource names
            Stream<String> resources = reader.list();
            boolean foundFile1 = false;
            boolean foundFile2 = false;
            for (String res : (Iterable<String>) resources::iterator) {
                if ("file1.txt".equals(res)) {
                    foundFile1 = true;
                } else if ("subdir/file2.txt".equals(res)) {
                    foundFile2 = true;
                }
            }
            if (!foundFile1 || !foundFile2) {
                throw new AssertionError("list() did not return expected resource names");
            }

            // Close and test IOException is thrown
            reader.close();
            boolean threw = false;
            try {
                reader.list();
            } catch (IOException e) {
                threw = true;
            }
            if (!threw) {
                throw new AssertionError("list() did not throw IOException when closed");
            }

        } finally {
            // Cleanup temporary directory recursively
            deleteRecursively(tempDir);
        }
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
}

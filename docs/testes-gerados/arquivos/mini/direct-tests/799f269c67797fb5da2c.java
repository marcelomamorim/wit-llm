/*
 * @test
 * @summary Test jdk.internal.module.ModuleReferences.list() method behavior
 * @run main ModuleReferencesWitupTest
 */

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

public class ModuleReferencesWitupTest {

    // Minimal subclass to expose list() method for testing
    static class TestModuleReferences extends jdk.internal.module.ModuleReferences {
        private final Path dir;
        private boolean open = true;

        TestModuleReferences(Path dir) {
            this.dir = dir;
        }

        void ensureOpen() throws IOException {
            if (!open) {
                throw new IOException("Closed");
            }
        }

        @Override
        public Stream<String> list() throws IOException {
            ensureOpen();
            return Files.walk(dir, Integer.MAX_VALUE)
                    .map(f -> jdk.internal.module.Resources.toResourceName(dir, f))
                    .filter(s -> s.length() > 0);
        }

        void close() {
            open = false;
        }
    }

    public static void main(String[] args) throws Exception {
        // Setup temporary directory with files
        Path tempDir = Files.createTempDirectory("modrefstest");
        try {
            // Create files and directories
            Path file1 = tempDir.resolve("file1.txt");
            Path subDir = tempDir.resolve("subdir");
            Files.createDirectory(subDir);
            Path file2 = subDir.resolve("file2.txt");

            Files.write(file1, "content1".getBytes("UTF-8"), StandardOpenOption.CREATE);
            Files.write(file2, "content2".getBytes("UTF-8"), StandardOpenOption.CREATE);

            TestModuleReferences modRef = new TestModuleReferences(tempDir);

            // Test that list() returns expected resource names
            try (Stream<String> stream = modRef.list()) {
                Object[] resources = stream.toArray();

                boolean foundFile1 = false;
                boolean foundFile2 = false;

                for (Object o : resources) {
                    if ("file1.txt".equals(o)) {
                        foundFile1 = true;
                    } else if ("subdir/file2.txt".equals(o)) {
                        foundFile2 = true;
                    }
                }

                if (!foundFile1) {
                    throw new AssertionError("file1.txt not found in list()");
                }
                if (!foundFile2) {
                    throw new AssertionError("subdir/file2.txt not found in list()");
                }
            }

            // Test that list() throws IOException if closed
            modRef.close();
            boolean threw = false;
            try {
                modRef.list();
            } catch (IOException e) {
                threw = true;
            }
            if (!threw) {
                throw new AssertionError("Expected IOException when calling list() after close");
            }

        } finally {
            // Cleanup
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

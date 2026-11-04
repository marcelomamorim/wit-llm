/*
 * @test
 * @summary Test for jdk.internal.module.ModuleReferences.list()
 * @run main ModuleReferencesWitupTest
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ModuleReferencesWitupTest {

    public static void main(String[] args) throws Exception {
        testListMethodWithOpenDirectory();
        testListMethodWithClosedDirectory();
    }

    private static void testListMethodWithOpenDirectory() throws Exception {
        Path tempDir = Files.createTempDirectory("testDirOpen");
        try {
            // Create a dummy file inside the directory
            Files.createFile(tempDir.resolve("file.txt"));
            // Call list() method
            Stream<String> result = jdk.internal.module.ModuleReferences.list(tempDir);
            boolean hasElements = result.anyMatch(s -> s.contains("file.txt"));
            if (!hasElements) {
                throw new AssertionError("Expected to find 'file.txt' in list output.");
            }
        } finally {
            Files.deleteIfExists(tempDir.resolve("file.txt"));
            Files.deleteIfExists(tempDir);
        }
    }

    private static void testListMethodWithClosedDirectory() throws Exception {
        Path tempDir = Files.createTempDirectory("testDirClosed");
        try {
            // Create a dummy file inside the directory
            Files.createFile(tempDir.resolve("file.txt"));
            // Close the directory by deleting it
            Files.deleteIfExists(tempDir);
            // Attempt to call list() method, expecting IOException
            try {
                jdk.internal.module.ModuleReferences.list(tempDir);
                throw new AssertionError("Expected IOException when listing closed directory.");
            } catch (IOException e) {
                // Expected exception
            }
        } finally {
            Files.deleteIfExists(tempDir.resolve("file.txt"));
            Files.deleteIfExists(tempDir);
        }
    }
}

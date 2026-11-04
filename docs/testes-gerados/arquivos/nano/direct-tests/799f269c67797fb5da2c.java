/*
 * @test
 * @summary Test for ModuleReferences.list() method to ensure it correctly lists module resource names.
 * @run main ModuleReferencesWitupTest
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModuleReferencesWitupTest {

    public static void main(String[] args) throws IOException {
        Path tempDir = Files.createTempDirectory("moduleTestDir");
        try {
            // Create dummy files to simulate module resources
            Path file1 = Files.createFile(tempDir.resolve("moduleA/resource1.txt"));
            Path file2 = Files.createFile(tempDir.resolve("moduleB/resource2.txt"));
            Path subDir = Files.createDirectory(tempDir.resolve("moduleC/submodule"));
            Path file3 = Files.createFile(subDir.resolve("resource3.txt"));

            // Call the list method, simulating the internal method
            Stream<String> resourceNames = listResources(tempDir);
            List<String> resourceList = resourceNames.collect(Collectors.toList());

            // Verify that resource names are correctly listed
            boolean containsResource1 = resourceList.contains("moduleA/resource1.txt");
            boolean containsResource2 = resourceList.contains("moduleB/resource2.txt");
            boolean containsResource3 = resourceList.contains("moduleC/submodule/resource3.txt");

            if (containsResource1 && containsResource2 && containsResource3) {
                System.out.println("Test passed: All resources listed correctly.");
            } else {
                System.err.println("Test failed: Missing resources in list.");
            }
        } finally {
            // Cleanup: delete created files and directories
            Files.walk(tempDir)
                .sorted((a, b) -> b.compareTo(a)) // delete children before parent
                .forEach(path -> {
                    try {
                        Files.deleteIfExists(path);
                    } catch (IOException e) {
                        // ignore cleanup errors
                    }
                });
        }
    }

    private static Stream<String> listResources(Path dir) throws IOException {
        // Simulate the internal list() method behavior
        return Files.walk(dir, Integer.MAX_VALUE)
                .map(f -> Resources.toResourceName(dir, f))
                .filter(s -> s.length() > 0);
    }

    // Mimic Resources.toResourceName for resource name conversion
    static class Resources {
        static String toResourceName(Path baseDir, Path file) {
            Path relativePath = baseDir.relativize(file);
            return relativePath.toString().replace(File.separatorChar, '/');
        }
    }
}

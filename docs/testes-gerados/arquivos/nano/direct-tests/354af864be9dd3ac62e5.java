/*
 * @test
 * @summary Test the list() method of ModulePatcher for correct stream output
 * @run main ModulePatcherWitupTest
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ModulePatcherWitupTest {

    public static void main(String[] args) throws IOException {
        testListMethod();
    }

    public static void testListMethod() throws IOException {
        Path tempDir = Files.createTempDirectory("testDir");
        Path file1 = Files.createFile(tempDir.resolve("file1.txt"));
        Path file2 = Files.createFile(tempDir.resolve("file2.txt"));
        Path subDir = Files.createDirectory(tempDir.resolve("subdir"));
        Files.createFile(subDir.resolve("file3.txt"));
        
        // Instantiate ModulePatcher with dummy data to access list method
        java.util.Map<String, java.util.List<String>> dummyMap = java.util.Collections.emptyMap();
        jdk.internal.module.ModulePatcher patcher = new jdk.internal.module.ModulePatcher(dummyMap);
        // Use reflection to invoke list() method
        try {
            java.lang.reflect.Method listMethod = jdk.internal.module.ModulePatcher.class.getDeclaredMethod("list");
            listMethod.setAccessible(true);
            Stream<String> resultStream = (Stream<String>) listMethod.invoke(patcher);
            boolean foundFile1 = false;
            boolean foundFile2 = false;
            boolean foundSubDir = false;
            for (String s : (Iterable<String>) resultStream::iterator) {
                if (s.contains("file1.txt")) {
                    foundFile1 = true;
                }
                if (s.contains("file2.txt")) {
                    foundFile2 = true;
                }
                if (s.contains("subdir")) {
                    foundSubDir = true;
                }
            }
            assert foundFile1 : "file1.txt not found in list() output";
            assert foundFile2 : "file2.txt not found in list() output";
            assert foundSubDir : "subdir not found in list() output";
        } finally {
            Files.deleteIfExists(file1);
            Files.deleteIfExists(file2);
            Files.deleteIfExists(subDir.resolve("file3.txt"));
            Files.deleteIfExists(subDir);
            Files.deleteIfExists(tempDir);
        }
    }
}

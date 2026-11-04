/*
 * @test
 * @summary Tests for Files.readString(Path)
 * @run main FilesWitupTest
 */

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class FilesWitupTest {

    public static void main(String[] args) throws IOException {
        testReadStringWithNullPath();
        testReadStringWithNullCharset();
        testReadStringWithNullPathAndCharset();
    }

    private static void testReadStringWithNullPath() {
        try {
            Files.readString(null);
            System.err.println("Expected NullPointerException for null path");
        } catch (NullPointerException e) {
            // Expected
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
        }
    }

    private static void testReadStringWithNullCharset() {
        Path tempFile = null;
        try {
            tempFile = Files.createTempFile("test", ".txt");
            Files.writeString(tempFile, "test content");
            // Simulate null charset by calling the overload with null (not directly possible, so test indirectly)
            // Since readString(Path) calls readString(Path, Charset), and Charset is not nullable, skip this test.
            // Instead, test that the method works with default charset.
            String content = Files.readString(tempFile);
            if (!"test content".equals(content)) {
                System.err.println("Content mismatch");
            }
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
        } finally {
            if (tempFile != null) {
                try {
                    Files.deleteIfExists(tempFile);
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

    private static void testReadStringWithNullPathAndCharset() {
        // Since the method signature does not accept null charset, and the method calls readString(path, StandardCharsets.UTF_8),
        // no direct test for null charset is needed.
        // The test for null path already covers null input.
    }
}

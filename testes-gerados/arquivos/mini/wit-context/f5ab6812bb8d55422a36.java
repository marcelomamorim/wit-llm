/*
 * @test
 * @summary Test java.nio.file.Files.copy(Path, OutputStream) for null and normal behavior
 * @run main FilesWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

public class FilesWitupTest {
    public static void main(String[] args) throws Exception {
        testCopyNullOutputStream();
        testCopyNormal();
    }

    private static void testCopyNullOutputStream() {
        Path dummyPath = Paths.get("/dev/null");
        OutputStream nullOut = null;
        try {
            Files.copy(dummyPath, nullOut);
            throw new AssertionError("Expected NullPointerException for null OutputStream");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (IOException ioe) {
            // IOException may be thrown if /dev/null does not exist or not accessible, ignore here
        }
    }

    private static void testCopyNormal() throws IOException {
        // Create a temporary file with known content
        Path tempFile = java.nio.file.Files.createTempFile("FilesWitupTest", ".txt");
        try {
            byte[] content = "Test content for Files.copy method.".getBytes("UTF-8");
            java.nio.file.Files.write(tempFile, content, StandardOpenOption.WRITE);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            long copied = Files.copy(tempFile, baos);

            if (copied != content.length) {
                throw new AssertionError("Copied byte count " + copied + " does not match content length " + content.length);
            }

            byte[] copiedBytes = baos.toByteArray();
            if (copiedBytes.length != content.length) {
                throw new AssertionError("OutputStream byte count " + copiedBytes.length + " does not match content length " + content.length);
            }

            for (int i = 0; i < content.length; i++) {
                if (content[i] != copiedBytes[i]) {
                    throw new AssertionError("Byte mismatch at index " + i);
                }
            }
        } finally {
            java.nio.file.Files.deleteIfExists(tempFile);
        }
    }
}

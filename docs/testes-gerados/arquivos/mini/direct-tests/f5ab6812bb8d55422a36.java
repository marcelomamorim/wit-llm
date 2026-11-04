/*
 * @test
 * @summary Test java.nio.file.Files.copy(Path, OutputStream) method
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
        testCopyWithValidFile();
        testCopyWithNullOutputStream();
        testCopyWithNonExistentSource();
    }

    private static void testCopyWithValidFile() throws IOException {
        // Create a temporary file with known content
        Path tempFile = Files.createTempFile("FilesWitupTest", ".txt");
        try {
            byte[] content = "Hello, jtreg!".getBytes("UTF-8");
            Files.write(tempFile, content, StandardOpenOption.WRITE);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            long copied = Files.copy(tempFile, baos);

            if (copied != content.length) {
                throw new AssertionError("Copied byte count " + copied + " does not match content length " + content.length);
            }

            byte[] copiedBytes = baos.toByteArray();
            if (copiedBytes.length != content.length) {
                throw new AssertionError("OutputStream byte array length " + copiedBytes.length + " does not match content length " + content.length);
            }

            for (int i = 0; i < content.length; i++) {
                if (content[i] != copiedBytes[i]) {
                    throw new AssertionError("Byte mismatch at index " + i);
                }
            }
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    private static void testCopyWithNullOutputStream() {
        Path dummyPath = Paths.get("dummy");
        try {
            Files.copy(dummyPath, null);
            throw new AssertionError("Expected NullPointerException for null OutputStream");
        } catch (NullPointerException e) {
            // Expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException instead of NullPointerException", e);
        }
    }

    private static void testCopyWithNonExistentSource() {
        Path nonExistent = Paths.get("nonexistentfile_1234567890.tmp");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            Files.copy(nonExistent, baos);
            throw new AssertionError("Expected IOException for non-existent source file");
        } catch (IOException e) {
            // Expected
        }
    }
}

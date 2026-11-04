/*
 * @test    @summary Test for Files.copy(Path, OutputStream) method.
 * @run main FilesWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilesWitupTest {
    public static void main(String[] args) throws IOException {
        testCopyMethod();
    }

    public static void testCopyMethod() throws IOException {
        Path tempFile = Files.createTempFile("testfile", ".txt");
        try {
            String content = "Hello, World!";
            Files.write(tempFile, content.getBytes());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            long bytesCopied = Files.copy(tempFile, baos);
            String result = baos.toString();
            if (!"Hello, World!".equals(result)) {
                throw new AssertionError("Content mismatch: expected 'Hello, World!', got '" + result + "'");
            }
            if (bytesCopied != content.length()) {
                throw new AssertionError("Bytes copied mismatch: expected " + content.length() + ", got " + bytesCopied);
            }
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }
}
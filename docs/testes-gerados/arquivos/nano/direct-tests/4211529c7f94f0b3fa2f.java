/*
 * @test
 * @summary Test for Files.readString(Path, Charset) method.
 * @run main FilesWitupTest
 */

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.io.IOException;

public class FilesWitupTest {
    public static void main(String[] args) throws IOException {
        testReadStringWithValidFile();
        testReadStringWithNonExistentFile();
        testReadStringWithNullPath();
        testReadStringWithNullCharset();
    }

    private static void testReadStringWithValidFile() throws IOException {
        Path tempFile = Files.createTempFile("testfile", ".txt");
        try {
            String content = "Hello, World!";
            Files.write(tempFile, content.getBytes(Charset.forName("UTF-8")));
            String result = Files.readString(tempFile, Charset.forName("UTF-8"));
            if (!content.equals(result)) {
                throw new AssertionError("Content mismatch: expected '" + content + "', got '" + result + "'");
            }
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    private static void testReadStringWithNonExistentFile() {
        Path nonExistentPath = Paths.get("/non/existent/file.txt");
        try {
            Files.readString(nonExistentPath, Charset.forName("UTF-8"));
            throw new AssertionError("Expected IOException for non-existent file");
        } catch (IOException e) {
            // Expected exception
        }
    }

    private static void testReadStringWithNullPath() {
        try {
            Files.readString(null, Charset.forName("UTF-8"));
            throw new AssertionError("Expected NullPointerException for null path");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testReadStringWithNullCharset() {
        Path tempFile = null;
        try {
            tempFile = Files.createTempFile("testfile", ".txt");
            Files.write(tempFile, "Test".getBytes(Charset.forName("UTF-8")));
            Files.readString(tempFile, null);
            throw new AssertionError("Expected NullPointerException for null charset");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (IOException e) {
            throw new RuntimeException(e);
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
}

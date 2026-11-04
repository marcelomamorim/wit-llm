/*
 * @test
 * @summary Test for Files.readString(Path, Charset) method, focusing on null input handling.
 * @run main FilesWitupTest
 */

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

public class FilesWitupTest {
    public static void main(String[] args) throws IOException {
        testReadStringWithNullPath();
        testReadStringWithNullCharset();
    }

    static void testReadStringWithNullPath() {
        try {
            Files.readString(null, StandardCharsets.UTF_8);
            System.err.println("Failed: Expected NullPointerException for null Path");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown for null Path");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception for null Path: " + t);
        }
    }

    static void testReadStringWithNullCharset() {
        Path tempFile = null;
        try {
            tempFile = Files.createTempFile("test", ".txt");
            Files.writeString(tempFile, "test content", StandardCharsets.UTF_8);
            Files.readString(tempFile, null);
            System.err.println("Failed: Expected NullPointerException for null Charset");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown for null Charset");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception for null Charset: " + t);
        } finally {
            if (tempFile != null) {
                try {
                    Files.deleteIfExists(tempFile);
                } catch (IOException e) {
                    System.err.println("Warning: Failed to delete temp file: " + e);
                }
            }
        }
    }
}

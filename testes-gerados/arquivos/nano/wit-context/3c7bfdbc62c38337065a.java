/*
 * @test
 * @summary Tests for java.util.zip.ZipFile constructor with String parameter
 * @run main ZipFileWitupTest
 */

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ZipFileWitupTest {

    public static void main(String[] args) throws IOException {
        testNullFileName();
        testValidFileName();
    }

    static void testNullFileName() {
        try {
            new java.util.zip.ZipFile((String) null);
            System.err.println("Expected NullPointerException for null filename");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (Exception e) {
            System.err.println("Unexpected exception for null filename: " + e);
        }
    }

    static void testValidFileName() throws IOException {
        Path tempFile = Files.createTempFile("test", ".zip");
        try {
            // Create an empty zip file
            Files.write(tempFile, new byte[0]);
            java.util.zip.ZipFile zip = new java.util.zip.ZipFile(tempFile.toString());
            zip.close();
        } catch (IOException e) {
            System.err.println("IOException during valid file test: " + e);
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }
}

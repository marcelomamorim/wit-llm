/*
 * @test
 * @summary Test ZipFile(String) constructor for basic valid and invalid inputs
 * @run main ZipFileWitupTest
 */

import java.util.zip.ZipFile;
import java.io.File;
import java.io.IOException;

public class ZipFileWitupTest {
    public static void main(String[] args) throws IOException {
        testValidZipFileConstructor();
        testNullNameThrowsNPE();
    }

    private static void testValidZipFileConstructor() throws IOException {
        // Create a temporary empty file to act as a zip file
        File tempFile = File.createTempFile("ZipFileWitupTest", ".zip");
        tempFile.deleteOnExit();

        // Construct ZipFile with valid file path
        ZipFile zf = new ZipFile(tempFile.getAbsolutePath());
        try {
            // Check that name is as expected (via public API: getName())
            if (!tempFile.getAbsolutePath().equals(zf.getName())) {
                throw new AssertionError("ZipFile name does not match input path");
            }
        } finally {
            zf.close();
        }
    }

    private static void testNullNameThrowsNPE() {
        try {
            new ZipFile((String) null);
            throw new AssertionError("Expected NullPointerException for null name");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (IOException ioe) {
            // IOException is not expected here, fail if thrown
            throw new AssertionError("Unexpected IOException for null name", ioe);
        }
    }
}

/*
 * @test
 * @summary Test ZipFile(String name) constructor with valid and invalid inputs
 * @run main ZipFileWitupTest
 */

import java.util.zip.ZipFile;
import java.io.File;
import java.io.IOException;

public class ZipFileWitupTest {
    public static void main(String[] args) throws Exception {
        testValidZipFile();
        testNonExistentFile();
        testNullName();
    }

    private static void testValidZipFile() throws IOException {
        // Create a temporary empty zip file
        File tempZip = File.createTempFile("testZipFile", ".zip");
        tempZip.deleteOnExit();

        // Construct ZipFile with the temp file path
        ZipFile zf = new ZipFile(tempZip.getAbsolutePath());
        zf.close();
    }

    private static void testNonExistentFile() {
        String nonExistentPath = "nonexistentfile1234567890.zip";
        try {
            new ZipFile(nonExistentPath);
            throw new RuntimeException("Expected IOException for non-existent file");
        } catch (IOException e) {
            // Expected
        }
    }

    private static void testNullName() {
        try {
            new ZipFile((String) null);
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException e) {
            // Expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException for null name", e);
        }
    }
}

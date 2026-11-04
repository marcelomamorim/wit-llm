/*
 * @test
 * @summary Test ZipFile(File) constructor with valid and invalid inputs
 * @run main ZipFileWitupTest
 */

import java.util.zip.ZipFile;
import java.util.zip.ZipException;
import java.io.File;
import java.io.IOException;

public class ZipFileWitupTest {
    public static void main(String[] args) throws Exception {
        testValidZipFile();
        testNonExistentFile();
        testNullFile();
    }

    private static void testValidZipFile() throws IOException {
        // Create a temporary empty zip file
        File tempZip = File.createTempFile("testZipFile", ".zip");
        tempZip.deleteOnExit();

        // Open ZipFile with the temp file - should succeed
        ZipFile zf = null;
        try {
            zf = new ZipFile(tempZip);
            if (zf == null) {
                throw new AssertionError("ZipFile constructor returned null");
            }
        } finally {
            if (zf != null) {
                zf.close();
            }
        }
    }

    private static void testNonExistentFile() {
        File nonExistent = new File("nonexistentfile.zip");
        try {
            new ZipFile(nonExistent);
            throw new AssertionError("Expected IOException for non-existent file");
        } catch (IOException e) {
            // Expected IOException (FileNotFoundException subclass)
        } catch (ZipException e) {
            // ZipException is a subclass of IOException, so also acceptable
        }
    }

    private static void testNullFile() {
        try {
            new ZipFile((File) null);
            throw new AssertionError("Expected NullPointerException for null file");
        } catch (NullPointerException e) {
            // Expected
        } catch (Exception e) {
            throw new AssertionError("Expected NullPointerException but got " + e);
        }
    }
}

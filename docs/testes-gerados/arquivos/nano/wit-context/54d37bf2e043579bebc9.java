/*
 * @test
 * @summary Test for ZipFile.getEntry(String name) method, focusing on null input handling.
 * @run main ZipFileWitupTest
 */

import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;
import java.io.File;
import java.io.IOException;

public class ZipFileWitupTest {

    public static void main(String[] args) throws IOException {
        testGetEntryWithNullName();
    }

    public static void testGetEntryWithNullName() {
        File tempFile = null;
        ZipFile zipFile = null;
        try {
            // Create a temporary zip file for testing
            tempFile = File.createTempFile("test", ".zip");
            tempFile.deleteOnExit();
            zipFile = new ZipFile(tempFile);
            // Call getEntry with null, expecting NullPointerException
            try {
                zipFile.getEntry(null);
                // If no exception, test fails
                System.err.println("Expected NullPointerException not thrown.");
            } catch (NullPointerException e) {
                // Expected exception
                System.out.println("NullPointerException correctly thrown.");
            }
        } catch (IOException e) {
            System.err.println("IOException during test setup: " + e.getMessage());
        } finally {
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (IOException e) {
                    // Ignore
                }
            }
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }
    }
}

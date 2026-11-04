/*
 * @test
 * @summary Basic tests for ZipFile.getEntry(String) method
 * @run main ZipFileWitupTest
 */

import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;
import java.io.File;
import java.io.IOException;

public class ZipFileWitupTest {
    public static void main(String[] args) throws IOException {
        // Create a temporary zip file with one entry for testing
        File tempZip = File.createTempFile("testZipFile", ".zip");
        tempZip.deleteOnExit();

        // Use java.util.zip.ZipOutputStream to create a zip file with one entry
        try (java.util.zip.ZipOutputStream zos = new java.util.zip.ZipOutputStream(
                new java.io.FileOutputStream(tempZip))) {
            ZipEntry entry = new ZipEntry("testEntry.txt");
            zos.putNextEntry(entry);
            byte[] data = "Hello, world!".getBytes("UTF-8");
            zos.write(data, 0, data.length);
            zos.closeEntry();
        }

        // Open the zip file for reading
        try (ZipFile zf = new ZipFile(tempZip)) {
            // Test 1: getEntry with existing entry name
            ZipEntry ze = zf.getEntry("testEntry.txt");
            if (ze == null) {
                throw new AssertionError("Expected entry 'testEntry.txt' not found");
            }
            if (!"testEntry.txt".equals(ze.getName())) {
                throw new AssertionError("Entry name mismatch");
            }

            // Test 2: getEntry with non-existing entry name returns null
            ZipEntry ze2 = zf.getEntry("nonexistent.txt");
            if (ze2 != null) {
                throw new AssertionError("Expected null for non-existing entry");
            }

            // Test 3: getEntry with null argument throws NullPointerException
            boolean npeThrown = false;
            try {
                zf.getEntry(null);
            } catch (NullPointerException npe) {
                npeThrown = true;
            }
            if (!npeThrown) {
                throw new AssertionError("Expected NullPointerException for null argument");
            }
        }
    }
}

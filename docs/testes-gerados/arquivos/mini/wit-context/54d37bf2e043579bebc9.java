/*
 * @test
 * @summary Test ZipFile.getEntry(String) for null argument and normal usage
 * @run main ZipFileWitupTest
 */

import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;
import java.io.File;
import java.io.IOException;

public class ZipFileWitupTest {
    public static void main(String[] args) throws IOException {
        testGetEntryNullName();
        testGetEntryNonNullName();
    }

    private static void testGetEntryNullName() throws IOException {
        File tempZip = createEmptyZipFile();
        ZipFile zipFile = new ZipFile(tempZip);
        try {
            try {
                zipFile.getEntry(null);
                throw new AssertionError("Expected NullPointerException for null name");
            } catch (NullPointerException expected) {
                // expected
            }
        } finally {
            zipFile.close();
            tempZip.delete();
        }
    }

    private static void testGetEntryNonNullName() throws IOException {
        File tempZip = createEmptyZipFile();
        ZipFile zipFile = new ZipFile(tempZip);
        try {
            ZipEntry entry = zipFile.getEntry("nonexistent");
            if (entry != null) {
                throw new AssertionError("Expected null for non-existing entry name");
            }
        } finally {
            zipFile.close();
            tempZip.delete();
        }
    }

    private static File createEmptyZipFile() throws IOException {
        // Create a temporary empty zip file
        File tempFile = File.createTempFile("empty", ".zip");
        tempFile.deleteOnExit();
        // Write minimal ZIP file header for empty zip
        java.io.FileOutputStream fos = new java.io.FileOutputStream(tempFile);
        try {
            // End of central directory record for empty zip
            byte[] eocd = {
                0x50, 0x4b, 0x05, 0x06, // EOCD signature
                0, 0,                   // number of this disk
                0, 0,                   // number of the disk with the start of the central directory
                0, 0,                   // total number of entries in the central dir on this disk
                0, 0,                   // total number of entries in the central dir
                0, 0, 0, 0,            // size of the central directory
                0, 0, 0, 0             // offset of start of central directory
                // no zip file comment
            };
            fos.write(eocd);
        } finally {
            fos.close();
        }
        return tempFile;
    }
}

/*
 * @test
 * @summary Test ZipFile.getInputStream(ZipEntry) for null argument and basic behavior
 * @run main ZipFileWitupTest
 */

import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;
import java.io.InputStream;
import java.io.IOException;
import java.io.File;

public class ZipFileWitupTest {
    public static void main(String[] args) throws Exception {
        testNullEntryThrowsNPE();
        testNonExistingEntryReturnsNull();
    }

    private static void testNullEntryThrowsNPE() throws IOException {
        File f = createEmptyZipFile();
        ZipFile zf = new ZipFile(f);
        try {
            try {
                zf.getInputStream(null);
                throw new RuntimeException("Expected NullPointerException for null entry");
            } catch (NullPointerException npe) {
                // expected
            }
        } finally {
            zf.close();
            f.delete();
        }
    }

    private static void testNonExistingEntryReturnsNull() throws IOException {
        File f = createEmptyZipFile();
        ZipFile zf = new ZipFile(f);
        try {
            ZipEntry nonExisting = new ZipEntry("nonexistent-entry");
            InputStream is = zf.getInputStream(nonExisting);
            if (is != null) {
                throw new RuntimeException("Expected null InputStream for non-existing entry");
            }
        } finally {
            zf.close();
            f.delete();
        }
    }

    private static File createEmptyZipFile() throws IOException {
        // Create a minimal empty zip file on disk
        File tempFile = File.createTempFile("empty", ".zip");
        tempFile.deleteOnExit();
        // Write minimal ZIP file structure: End of central directory record only
        // ZIP file with no entries is valid
        byte[] eocd = {
            0x50, 0x4b, 0x05, 0x06, // End of central dir signature
            0, 0,                   // Number of this disk
            0, 0,                   // Number of the disk with the start of the central directory
            0, 0,                   // Total number of entries in the central dir on this disk
            0, 0,                   // Total number of entries in the central dir
            0, 0, 0, 0,             // Size of the central directory
            0, 0, 0, 0              // Offset of start of central directory
            // No zip file comment
        };
        java.nio.file.Files.write(tempFile.toPath(), eocd);
        return tempFile;
    }
}

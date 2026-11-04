/* @test    @summary Test for ZipInputStream.closeEntry() method    @run main ZipInputStreamWitupTest */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.ZipInputStream;

public class ZipInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testCloseEntryClosesStream();
    }

    private static void testCloseEntryClosesStream() throws IOException {
        byte[] zipData = createDummyZipData();
        try (ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(zipData))) {
            // Move to first entry
            zis.getNextEntry();
            // Call closeEntry
            zis.closeEntry();
            // After closeEntry, reading should return -1 (EOF)
            int readResult = zis.read();
            assert readResult == -1 : "Expected EOF after closeEntry, but got: " + readResult;
        }
    }

    private static byte[] createDummyZipData() {
        // Minimal ZIP data with one entry
        // ZIP file header: local file header with no data
        return new byte[] {
            0x50, 0x4b, 0x03, 0x04, // local file header signature
            0x14, 0x00, 0x00, 0x00, // version needed to extract
            0x00, 0x00, 0x00, 0x00, // general purpose bit flag
            0x00, 0x00, 0x00, 0x00, // compression method
            0x00, 0x00, 0x00, 0x00, // last mod file time & date
            0x00, 0x00, 0x00, 0x00, // crc-32
            0x00, 0x00, 0x00, 0x00, // compressed size
            0x00, 0x00, 0x00, 0x00, // uncompressed size
            0x00, 0x00, // filename length
            0x00, 0x00  // extra field length
        };
    }
}

/*
 * @test
 * @summary Test GZIPInputStream constructor with explicit buffer size
 * @run main GZIPInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipException;

public class GZIPInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidGzipHeader();
        testInvalidGzipHeader();
    }

    private static void testValidGzipHeader() throws IOException {
        // Minimal valid GZIP header + empty deflated data + CRC + ISIZE
        byte[] validGzip = new byte[] {
            (byte)0x1f, (byte)0x8b, // ID1 and ID2
            (byte)0x08, // CM = deflate
            (byte)0x00, // FLG
            (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, // MTIME
            (byte)0x00, // XFL
            (byte)0x00, // OS
            // no compressed data
            (byte)0x03, (byte)0x00, // empty deflate block
            (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, // CRC32
            (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00  // ISIZE
        };
        ByteArrayInputStream bais = new ByteArrayInputStream(validGzip);
        GZIPInputStream gis = new GZIPInputStream(bais, 512);
        int read = gis.read();
        if (read != -1) {
            throw new AssertionError("Expected end of stream (-1) but got " + read);
        }
        gis.close();
    }

    private static void testInvalidGzipHeader() {
        // Invalid header: wrong ID1 and ID2
        byte[] invalidGzip = new byte[] {
            (byte)0x00, (byte)0x00, // invalid ID1 and ID2
            (byte)0x08, // CM
            (byte)0x00, // FLG
            (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, // MTIME
            (byte)0x00, // XFL
            (byte)0x00  // OS
        };
        ByteArrayInputStream bais = new ByteArrayInputStream(invalidGzip);
        try {
            new GZIPInputStream(bais, 512);
            throw new AssertionError("Expected ZipException due to invalid GZIP header");
        } catch (ZipException e) {
            // Expected
        } catch (IOException e) {
            // Accept IOException as well since readHeader can throw IOException
        }
    }
}

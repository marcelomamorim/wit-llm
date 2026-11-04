/*
 * @test
 * @summary Test GZIPInputStream(InputStream in) constructor for valid and invalid gzip headers
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
        // Minimal valid GZIP header (10 bytes) + empty deflated data + CRC + ISIZE
        byte[] validGzip = new byte[] {
            (byte)0x1f, (byte)0x8b, // ID1 and ID2
            (byte)0x08,             // CM (deflate)
            0,                      // FLG
            0, 0, 0, 0,             // MTIME
            0,                      // XFL
            0                       // OS
            // No compressed data, no CRC, no ISIZE (will cause EOF on read)
        };
        ByteArrayInputStream bais = new ByteArrayInputStream(validGzip);
        GZIPInputStream gis = new GZIPInputStream(bais);
        // Reading should return -1 immediately because no compressed data
        int read = gis.read();
        if (read != -1) {
            throw new AssertionError("Expected -1 on read from empty gzip stream, got " + read);
        }
        gis.close();
    }

    private static void testInvalidGzipHeader() {
        // Invalid GZIP header: wrong ID1 and ID2
        byte[] invalidGzip1 = new byte[] {
            (byte)0x00, (byte)0x00, // invalid ID1 and ID2
            (byte)0x08,             // CM
            0, 0, 0, 0, 0, 0, 0
        };
        try {
            new GZIPInputStream(new ByteArrayInputStream(invalidGzip1));
            throw new AssertionError("Expected ZipException for invalid gzip header ID1/ID2");
        } catch (ZipException e) {
            // Expected
        } catch (IOException e) {
            throw new AssertionError("Expected ZipException but got IOException", e);
        }

        // Invalid GZIP header: correct ID1/ID2 but wrong CM
        byte[] invalidGzip2 = new byte[] {
            (byte)0x1f, (byte)0x8b, // valid ID1 and ID2
            (byte)0x00,             // invalid CM (should be 8)
            0, 0, 0, 0, 0, 0, 0
        };
        try {
            new GZIPInputStream(new ByteArrayInputStream(invalidGzip2));
            throw new AssertionError("Expected ZipException for invalid compression method");
        } catch (ZipException e) {
            // Expected
        } catch (IOException e) {
            throw new AssertionError("Expected ZipException but got IOException", e);
        }
    }
}

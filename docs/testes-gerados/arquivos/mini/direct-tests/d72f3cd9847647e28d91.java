/*
 * @test
 * @summary Test GZIPInputStream constructor with default buffer size
 * @run main GZIPInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

public class GZIPInputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        // Minimal valid GZIP header + empty deflate block + CRC + ISIZE
        byte[] minimalGzip = new byte[] {
            (byte)0x1f, (byte)0x8b, // ID1 and ID2
            (byte)0x08, // Compression method (deflate)
            (byte)0x00, // Flags
            (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, // MTIME
            (byte)0x00, // XFL
            (byte)0x00, // OS
            // Empty deflate block (1 byte 0x03 = end of block)
            (byte)0x03,
            // CRC32 (4 bytes) - zero for empty data
            (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
            // ISIZE (4 bytes) - zero for empty data
            (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00
        };

        ByteArrayInputStream bais = new ByteArrayInputStream(minimalGzip);
        GZIPInputStream gis = new GZIPInputStream(bais);

        // Read should return -1 immediately because no data
        int read = gis.read();
        if (read != -1) {
            throw new AssertionError("Expected -1 on read from empty GZIP stream, got " + read);
        }

        gis.close();
    }
}

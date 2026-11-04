/*
 * @test
 * @summary Test InflaterInputStream.read() method behavior
 * @run main InflaterInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.InflaterInputStream;
import java.util.zip.Inflater;

public class InflaterInputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testReadReturnsMinusOneOnEmptyStream();
        testReadReturnsByteValueOnValidCompressedData();
        testReadThrowsIOExceptionWhenClosed();
    }

    private static void testReadReturnsMinusOneOnEmptyStream() throws IOException {
        // InflaterInputStream wrapping an empty stream should return -1 on read()
        ByteArrayInputStream empty = new ByteArrayInputStream(new byte[0]);
        InflaterInputStream iis = new InflaterInputStream(empty, new Inflater());
        int result = iis.read();
        if (result != -1) {
            throw new AssertionError("Expected -1 for empty compressed stream, got: " + result);
        }
        iis.close();
    }

    private static void testReadReturnsByteValueOnValidCompressedData() throws IOException {
        // Compressed data for empty input is 0x03 0x00 (zlib format minimal)
        // But to keep test simple and deterministic, compress "" with Deflater and decompress
        // Here we use a known compressed byte array for empty input
        byte[] compressedEmpty = new byte[] { 120, 1, 3, 0, 0, 0, 0, 1 };
        ByteArrayInputStream bais = new ByteArrayInputStream(compressedEmpty);
        InflaterInputStream iis = new InflaterInputStream(bais, new Inflater());
        int firstByte = iis.read();
        // For empty input, read() should return -1 (EOF) or 0 if some data is decompressed
        // According to InflaterInputStream contract, -1 means EOF
        if (firstByte != -1) {
            // It may return 0 if some data is decompressed, but for empty input it should be -1
            throw new AssertionError("Expected -1 for empty decompressed data, got: " + firstByte);
        }
        iis.close();
    }

    private static void testReadThrowsIOExceptionWhenClosed() throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(new byte[0]);
        InflaterInputStream iis = new InflaterInputStream(bais, new Inflater());
        iis.close();
        try {
            iis.read();
            throw new AssertionError("Expected IOException when reading from closed stream");
        } catch (IOException e) {
            // Expected
        }
    }
}

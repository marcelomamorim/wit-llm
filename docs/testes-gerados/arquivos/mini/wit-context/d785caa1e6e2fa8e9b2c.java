/*
 * @test
 * @summary Deterministic tests for java.util.zip.InflaterInputStream.read()
 * @run main InflaterInputStreamWitupTest
 */

import java.util.zip.InflaterInputStream;
import java.util.zip.Inflater;
import java.io.InputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;

public class InflaterInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testReadThrowsIOExceptionWhenClosed();
        testReadReturnsMinusOneOnEmptyStream();
        testReadReturnsByteValue();
    }

    // Test that read() throws IOException if the stream is closed
    private static void testReadThrowsIOExceptionWhenClosed() {
        InflaterInputStream iis = new InflaterInputStream(new ByteArrayInputStream(new byte[0]));
        try {
            iis.close();
            iis.read();
            throw new RuntimeException("Expected IOException not thrown when reading from closed stream");
        } catch (IOException e) {
            // Expected
        }
    }

    // Test that read() returns -1 on empty compressed input stream
    private static void testReadReturnsMinusOneOnEmptyStream() throws IOException {
        // Empty compressed data (deflate) - minimal valid compressed stream
        byte[] emptyCompressed = new byte[] { 0x03, 0x00 }; // minimal deflate block with no data
        InflaterInputStream iis = new InflaterInputStream(new ByteArrayInputStream(emptyCompressed));
        int result = iis.read();
        if (result != -1) {
            throw new RuntimeException("Expected -1 on empty compressed input, got: " + result);
        }
        iis.close();
    }

    // Test that read() returns correct unsigned byte value when data is present
    private static void testReadReturnsByteValue() throws IOException {
        // Compressed data for single byte 0x41 ('A') using deflate
        // This is a valid deflate compressed byte array for 'A'
        byte[] compressedA = new byte[] {
            0x0b, 0x49, 0x2d, 0x2e, 0x01, 0x00
        };
        InflaterInputStream iis = new InflaterInputStream(new ByteArrayInputStream(compressedA));
        int b = iis.read();
        if (b != 0x41) {
            throw new RuntimeException("Expected byte 0x41, got: " + b);
        }
        // After reading one byte, next read should return -1 (EOF)
        int eof = iis.read();
        if (eof != -1) {
            throw new RuntimeException("Expected EOF (-1) after reading all data, got: " + eof);
        }
        iis.close();
    }
}

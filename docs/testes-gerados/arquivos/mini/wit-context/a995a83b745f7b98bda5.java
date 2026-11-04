/*
 * @test
 * @summary Test sun.security.util.DerInputStream.getDerValue() for expected IOException on empty and malformed input
 * @run main DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import java.io.IOException;

public class DerInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testEmptyBufferThrowsIOException();
        testMalformedLengthThrowsIOException();
        testMalformedLengthWithZeroThrowsIOException();
        testValidBufferReturnsDerValue();
        System.out.println("All tests passed.");
    }

    private static void testEmptyBufferThrowsIOException() {
        byte[] empty = new byte[0];
        try {
            DerInputStream dis = new DerInputStream(empty);
            dis.getDerValue();
            throw new RuntimeException("Expected IOException not thrown for empty buffer");
        } catch (IOException e) {
            // Expected
        }
    }

    private static void testMalformedLengthThrowsIOException() {
        // Construct a buffer that triggers length checks in DerValue constructor
        // Use a single byte with high bit set and invalid length bytes
        byte[] data = new byte[] {(byte)0x81, (byte)0xFF};
        try {
            DerInputStream dis = new DerInputStream(data);
            dis.getDerValue();
            throw new RuntimeException("Expected IOException not thrown for malformed length");
        } catch (IOException e) {
            // Expected
        }
    }

    private static void testMalformedLengthWithZeroThrowsIOException() {
        // Construct a buffer that triggers the zero length condition
        byte[] data = new byte[] {(byte)0x82, 0x00, 0x00};
        try {
            DerInputStream dis = new DerInputStream(data);
            dis.getDerValue();
            throw new RuntimeException("Expected IOException not thrown for zero length in length bytes");
        } catch (IOException e) {
            // Expected
        }
    }

    private static void testValidBufferReturnsDerValue() throws IOException {
        // Minimal valid DER encoding: INTEGER 0
        byte[] data = new byte[] {0x02, 0x01, 0x00};
        DerInputStream dis = new DerInputStream(data);
        DerValue val = dis.getDerValue();
        if (val == null) {
            throw new RuntimeException("getDerValue returned null for valid input");
        }
    }
}

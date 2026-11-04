/*
 * @test
 * @summary Test sun.security.util.DerInputStream.peekByte() method
 * @run main sun.security.util.DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import sun.security.util.DerInputBuffer;
import java.io.IOException;

public class DerInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testPeekByteReturnsCorrectValue();
        testPeekByteThrowsOnEmptyBuffer();
    }

    private static void testPeekByteReturnsCorrectValue() throws IOException {
        byte[] data = new byte[] { (byte)0xAB, (byte)0xCD, (byte)0xEF };
        DerInputStream dis = new DerInputStream(data);
        int peeked = dis.peekByte();
        if (peeked != (data[0] & 0xFF)) {
            throw new RuntimeException("peekByte() returned " + peeked + ", expected " + (data[0] & 0xFF));
        }
        // Calling peekByte multiple times should return the same value
        int peekedAgain = dis.peekByte();
        if (peekedAgain != peeked) {
            throw new RuntimeException("peekByte() returned different values on subsequent calls");
        }
    }

    private static void testPeekByteThrowsOnEmptyBuffer() {
        byte[] empty = new byte[0];
        try {
            DerInputStream dis = new DerInputStream(empty);
            dis.peekByte();
            throw new RuntimeException("peekByte() did not throw IOException on empty buffer");
        } catch (IOException e) {
            // Expected exception
        }
    }
}

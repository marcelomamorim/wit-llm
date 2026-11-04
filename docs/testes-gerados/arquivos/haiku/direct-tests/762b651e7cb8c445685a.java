/*
 * @test
 * @summary Verifies that DerInputStream.peekByte returns the next byte without consuming it
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testPeekReturnsFirstByte();
        testPeekIsIdempotent();
        testPeekRespectsOffset();
    }

    static void testPeekReturnsFirstByte() throws Exception {
        byte[] data = {0x30, 0x03, 0x02, 0x01, 0x05};
        DerInputStream in = new DerInputStream(data);
        int b = in.peekByte();
        if (b != 0x30) {
            throw new RuntimeException("Expected 0x30 but got " + b);
        }
    }

    static void testPeekIsIdempotent() throws Exception {
        byte[] data = {0x02, 0x01, 0x05};
        DerInputStream in = new DerInputStream(data);
        int first = in.peekByte();
        int second = in.peekByte();
        if (first != 0x02 || second != 0x02) {
            throw new RuntimeException("peekByte consumed data: " + first + ", " + second);
        }
    }

    static void testPeekRespectsOffset() throws Exception {
        byte[] data = {0x00, 0x04, 0x7F, 0x01};
        DerInputStream in = new DerInputStream(data, 1, 3, true);
        int b = in.peekByte();
        if (b != 0x04) {
            throw new RuntimeException("Expected 0x04 at offset 1 but got " + b);
        }
    }
}

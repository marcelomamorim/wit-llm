/*
 * @test
 * @summary Verify DerInputStream.peekByte() returns the next byte without consuming it and signals an IOException when no data remains
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */

import java.io.IOException;
import sun.security.util.DerInputStream;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testPeekReturnsFirstByte();
        testPeekIsIdempotent();
        testPeekOnEmptyBufferThrowsIOException();
        testPeekOnZeroLengthSliceThrowsIOException();
    }

    private static void testPeekReturnsFirstByte() throws IOException {
        byte[] data = {0x30, 0x02, 0x05, 0x00};
        DerInputStream in = new DerInputStream(data);
        int b = in.peekByte();
        if (b != 0x30) {
            throw new RuntimeException("Expected 0x30 but got " + b);
        }
    }

    private static void testPeekIsIdempotent() throws IOException {
        byte[] data = {0x02, 0x01, 0x07};
        DerInputStream in = new DerInputStream(data);
        int first = in.peekByte();
        int second = in.peekByte();
        if (first != 0x02 || second != 0x02) {
            throw new RuntimeException("peekByte consumed data: first=" + first + ", second=" + second);
        }
    }

    private static void testPeekOnEmptyBufferThrowsIOException() {
        boolean thrown = false;
        try {
            DerInputStream in = new DerInputStream(new byte[0]);
            in.peekByte();
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException for peekByte on empty buffer");
        }
    }

    private static void testPeekOnZeroLengthSliceThrowsIOException() {
        boolean thrown = false;
        try {
            byte[] data = {0x30, 0x02, 0x05};
            DerInputStream in = new DerInputStream(data, 0, 0, true);
            in.peekByte();
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException for peekByte on zero-length slice");
        }
    }
}

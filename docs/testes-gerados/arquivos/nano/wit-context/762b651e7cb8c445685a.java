/* @test    @summary Test for sun.security.util.DerInputStream.peekByte() method to verify exception handling and normal behavior    @run main DerInputStreamWitupTest */

import sun.security.util.DerInputStream;
import java.io.IOException;
import java.util.Arrays;

public class DerInputStreamWitupTest {

    public static void main(String[] args) {
        testPeekByteNormal();
        testPeekByteThrowsIOException();
    }

    private static void testPeekByteNormal() {
        byte[] data = {0x02, 0x01, 0x05}; // arbitrary valid data
        try {
            DerInputStream dis = new DerInputStream(data);
            int result = dis.peekByte();
            if (result != (data[0] & 0xFF)) {
                throw new AssertionError("Expected: " + (data[0] & 0xFF) + ", but got: " + result);
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException in normal peekByte test", e);
        }
    }

    private static void testPeekByteThrowsIOException() {
        byte[] emptyData = {};
        try {
            DerInputStream dis = new DerInputStream(emptyData);
            // Force buffer.peek() to throw IOException by manipulating internal state
            // Since buffer is private, simulate by creating a subclass or reflection is needed.
            // As reflection is discouraged, we simulate by passing empty data and expecting IOException
            dis.peekByte();
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            // Expected exception
            if (!(e instanceof IOException)) {
                throw new AssertionError("Unexpected exception type", e);
            }
        }
    }
}

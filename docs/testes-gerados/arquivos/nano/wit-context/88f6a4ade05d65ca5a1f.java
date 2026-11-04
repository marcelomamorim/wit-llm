/* @test    @summary Test for sun.security.util.DerInputStream.getBytes(byte[]) method    @run main DerInputStreamWitupTest */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testGetBytesNormal();
        testGetBytesZeroLength();
        testGetBytesShortRead();
        testGetBytesIOException();
    }

    private static void testGetBytesNormal() throws IOException {
        byte[] data = {1, 2, 3, 4, 5};
        DerInputStream dis = new DerInputStream(data);
        byte[] buffer = new byte[5];
        dis.getBytes(buffer);
        boolean result = java.util.Arrays.equals(buffer, data);
        if (!result) {
            throw new AssertionError("Normal getBytes failed: buffer does not match data");
        }
    }

    private static void testGetBytesZeroLength() throws IOException {
        byte[] data = {};
        DerInputStream dis = new DerInputStream(data);
        byte[] buffer = new byte[0];
        dis.getBytes(buffer);
        // No exception expected, buffer remains empty
    }

    private static void testGetBytesShortRead() {
        byte[] data = {1, 2, 3};
        DerInputStream dis;
        try {
            dis = new DerInputStream(data);
            byte[] buffer = {0, 0, 0, 0};
            dis.getBytes(buffer);
            throw new AssertionError("Expected IOException for short read not thrown");
        } catch (IOException e) {
            // Expected exception
        }
    }

    private static void testGetBytesIOException() {
        byte[] data = {1, 2, 3};
        DerInputStream dis;
        try {
            dis = new DerInputStream(data);
            byte[] buffer = {0, 0, 0, 0};
            dis.getBytes(buffer);
            throw new AssertionError("Expected IOException for short read not thrown");
        } catch (IOException e) {
            // Expected exception
        }
    }
}

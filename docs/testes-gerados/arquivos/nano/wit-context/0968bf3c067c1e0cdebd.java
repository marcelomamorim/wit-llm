/*
 * @test
 * @summary Test ByteArrayOutputStream.writeBytes(byte[])
 * @run main ByteArrayOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ByteArrayOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testWriteBytesNormal();
        testWriteBytesNegativeSize();
        testWriteBytesOutOfMemory();
    }

    private static void testWriteBytesNormal() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] data = {1, 2, 3, 4, 5};
        baos.writeBytes(data);
        byte[] result = baos.toByteArray();
        boolean success = result.length == data.length;
        for (int i = 0; i < data.length && success; i++) {
            success &= result[i] == data[i];
        }
        if (!success) {
            throw new AssertionError("testWriteBytesNormal failed");
        }
    }

    private static void testWriteBytesNegativeSize() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] data = {1, 2, 3};
        try {
            // Simulate a negative size by passing null or invalid array (not possible directly, so test null)
            baos.writeBytes(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testWriteBytesOutOfMemory() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] largeData = new byte[Integer.MAX_VALUE];
        try {
            baos.writeBytes(largeData);
            // Depending on JVM, this may throw OutOfMemoryError or succeed
            // We accept either, but if no exception, check length
            if (baos.toByteArray().length != largeData.length) {
                throw new AssertionError("Length mismatch in large data write");
            }
        } catch (OutOfMemoryError e) {
            // expected in some environments
        }
    }
}

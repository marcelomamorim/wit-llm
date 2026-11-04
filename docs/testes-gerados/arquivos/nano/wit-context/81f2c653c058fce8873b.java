/* @test    @summary Test for ByteArrayInputStream.readAllBytes() method to verify behavior and exception handling    @run main ByteArrayInputStreamWitupTest */

import java.io.ByteArrayInputStream;
import java.util.Arrays;

public class ByteArrayInputStreamWitupTest {

    public static void main(String[] args) {
        testReadAllBytesNormal();
        testReadAllBytesWithCountZero();
        testReadAllBytesWithInvalidState();
    }

    private static void testReadAllBytesNormal() {
        byte[] data = {1, 2, 3, 4, 5};
        ByteArrayInputStream stream = new ByteArrayInputStream(data);
        byte[] result = stream.readAllBytes();
        if (!Arrays.equals(result, data)) {
            throw new AssertionError("Expected " + Arrays.toString(data) + " but got " + Arrays.toString(result));
        }
        if (stream.pos != stream.count) {
            throw new AssertionError("Stream position should be at count after readAllBytes");
        }
    }

    private static void testReadAllBytesWithCountZero() {
        byte[] data = {1, 2, 3};
        ByteArrayInputStream stream = new ByteArrayInputStream(data);
        stream.pos = stream.count; // move position to end
        byte[] result = stream.readAllBytes();
        if (result.length != 0) {
            throw new AssertionError("Expected empty array but got " + Arrays.toString(result));
        }
        if (stream.pos != stream.count) {
            throw new AssertionError("Stream position should remain at count after readAllBytes with pos at count");
        }
    }

    private static void testReadAllBytesWithInvalidState() {
        byte[] data = {10, 20, 30};
        ByteArrayInputStream stream = new ByteArrayInputStream(data);
        stream.pos = 2;
        stream.count = 1; // simulate invalid state where pos > count
        try {
            stream.readAllBytes();
            throw new AssertionError("Expected IllegalArgumentException due to count < pos");
        } catch (IllegalArgumentException e) {
            // expected exception
        }
    }
}

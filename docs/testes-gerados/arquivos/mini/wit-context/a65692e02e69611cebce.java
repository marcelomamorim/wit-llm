/*
 * @test
 * @summary Test BufferedInputStream constructors for expected behavior and exception
 * @run main BufferedInputStreamWitupTest
 */

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class BufferedInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorWithValidInput();
        testConstructorWithInvalidBufferSize();
    }

    private static void testConstructorWithValidInput() throws Exception {
        byte[] data = {1, 2, 3, 4, 5};
        InputStream in = new ByteArrayInputStream(data);
        BufferedInputStream bis = new BufferedInputStream(in);
        int firstByte = bis.read();
        if (firstByte != 1) {
            throw new AssertionError("Expected first byte 1 but got " + firstByte);
        }
        bis.close();
    }

    private static void testConstructorWithInvalidBufferSize() {
        // The default constructor calls this(in, DEFAULT_BUFFER_SIZE).
        // According to the WIT expath, if DEFAULT_BUFFER_SIZE <= 0, constructor throws IllegalArgumentException.
        // We cannot change DEFAULT_BUFFER_SIZE, so we test the two-arg constructor directly with invalid size.
        InputStream in = new ByteArrayInputStream(new byte[0]);
        try {
            new BufferedInputStream(in, 0);
            throw new AssertionError("Expected IllegalArgumentException for buffer size 0");
        } catch (IllegalArgumentException expected) {
            // expected
        }
        try {
            new BufferedInputStream(in, -1);
            throw new AssertionError("Expected IllegalArgumentException for negative buffer size");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}

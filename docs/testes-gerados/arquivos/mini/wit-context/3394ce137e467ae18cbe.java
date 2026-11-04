/*
 * @test
 * @summary Test BufferedInputStream constructor with buffer size validation
 * @run main BufferedInputStreamWitupTest
 */

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class BufferedInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidBufferSize();
        testZeroBufferSize();
        testNegativeBufferSize();
    }

    private static void testValidBufferSize() throws Exception {
        byte[] data = {1, 2, 3};
        InputStream in = new ByteArrayInputStream(data);
        BufferedInputStream bis = new BufferedInputStream(in, 10);
        int readByte = bis.read();
        if (readByte != 1) {
            throw new RuntimeException("Expected first byte to be 1 but got " + readByte);
        }
        bis.close();
    }

    private static void testZeroBufferSize() {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        try {
            new BufferedInputStream(in, 0);
            throw new RuntimeException("Expected IllegalArgumentException for buffer size 0");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testNegativeBufferSize() {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        try {
            new BufferedInputStream(in, -1);
            throw new RuntimeException("Expected IllegalArgumentException for negative buffer size");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}

/*
 * @test
 * @summary Test BufferedInputStream constructor with buffer size parameter
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
        BufferedInputStream bis = new BufferedInputStream(in, 5);
        int firstByte = bis.read();
        if (firstByte != 1) {
            throw new RuntimeException("Expected first byte 1 but got " + firstByte);
        }
        bis.close();
    }

    private static void testZeroBufferSize() {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        try {
            new BufferedInputStream(in, 0);
            throw new RuntimeException("Expected IllegalArgumentException for buffer size 0");
        } catch (IllegalArgumentException e) {
            if (!"Buffer size <= 0".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testNegativeBufferSize() {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        try {
            new BufferedInputStream(in, -1);
            throw new RuntimeException("Expected IllegalArgumentException for negative buffer size");
        } catch (IllegalArgumentException e) {
            if (!"Buffer size <= 0".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}

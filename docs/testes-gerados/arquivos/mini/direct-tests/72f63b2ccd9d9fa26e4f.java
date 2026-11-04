/*
 * @test
 * @summary Test ByteArrayOutputStream(int size) constructor for valid and invalid sizes
 * @run main ByteArrayOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;

public class ByteArrayOutputStreamWitupTest {
    public static void main(String[] args) {
        testValidSize();
        testZeroSize();
        testNegativeSize();
    }

    private static void testValidSize() {
        int size = 10;
        ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
        if (baos == null) {
            throw new RuntimeException("Constructor returned null");
        }
        // The buffer length should be at least the requested size
        if (baos.buf.length < size) {
            throw new RuntimeException("Buffer length " + baos.buf.length + " is less than requested size " + size);
        }
    }

    private static void testZeroSize() {
        int size = 0;
        ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
        if (baos == null) {
            throw new RuntimeException("Constructor returned null for zero size");
        }
        if (baos.buf.length != 0) {
            throw new RuntimeException("Buffer length " + baos.buf.length + " is not zero for zero size constructor");
        }
    }

    private static void testNegativeSize() {
        int size = -1;
        try {
            new ByteArrayOutputStream(size);
            throw new RuntimeException("Expected IllegalArgumentException for negative size");
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Negative initial size")) {
                throw new RuntimeException("IllegalArgumentException message unexpected: " + msg);
            }
        }
    }
}

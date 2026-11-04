/*
 * @test
 * @summary Test ByteArrayOutputStream(int) constructor for valid and invalid sizes
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
        // Test with a positive size
        int size = 10;
        ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
        if (baos == null) {
            throw new RuntimeException("Constructor returned null for positive size");
        }
    }

    private static void testZeroSize() {
        // Test with zero size, should succeed
        int size = 0;
        ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
        if (baos == null) {
            throw new RuntimeException("Constructor returned null for zero size");
        }
    }

    private static void testNegativeSize() {
        // Test with negative size, should throw IllegalArgumentException
        int size = -1;
        try {
            new ByteArrayOutputStream(size);
            throw new RuntimeException("Expected IllegalArgumentException for negative size not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Negative initial size")) {
                throw new RuntimeException("IllegalArgumentException message unexpected: " + msg);
            }
        }
    }
}

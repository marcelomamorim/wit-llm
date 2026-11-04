/*
 * @test
 * @summary Test BufferedOutputStream constructor with buffer size validation
 * @run main BufferedOutputStreamWitupTest
 */

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class BufferedOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidBufferSize();
        testInvalidBufferSize();
    }

    private static void testValidBufferSize() throws Exception {
        OutputStream out = new ByteArrayOutputStream();
        // Test with a valid buffer size
        BufferedOutputStream bos = new BufferedOutputStream(out, 1);
        // The constructor should succeed and buffer size should be at least 1
        bos.write(0); // write a byte to check basic functionality
        bos.flush();
        bos.close();
    }

    private static void testInvalidBufferSize() {
        OutputStream out = new ByteArrayOutputStream();
        try {
            new BufferedOutputStream(out, 0);
            throw new RuntimeException("Expected IllegalArgumentException for size=0");
        } catch (IllegalArgumentException iae) {
            // Expected exception
        }
        try {
            new BufferedOutputStream(out, -1);
            throw new RuntimeException("Expected IllegalArgumentException for size=-1");
        } catch (IllegalArgumentException iae) {
            // Expected exception
        }
    }
}

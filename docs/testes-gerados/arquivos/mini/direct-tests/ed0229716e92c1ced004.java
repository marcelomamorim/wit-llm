/*
 * @test
 * @summary Test BufferedOutputStream constructor with buffer size validation
 * @run main BufferedOutputStreamWitupTest
 */

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.IOException;

public class BufferedOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidBufferSize();
        testZeroBufferSize();
        testNegativeBufferSize();
    }

    private static void testValidBufferSize() throws IOException {
        OutputStream dummyOut = new OutputStream() {
            public void write(int b) throws IOException {
                // do nothing
            }
        };
        int size = 1024;
        BufferedOutputStream bos = new BufferedOutputStream(dummyOut, size);
        if (bos.buf.length != size) {
            throw new RuntimeException("Buffer size not set correctly");
        }
    }

    private static void testZeroBufferSize() {
        OutputStream dummyOut = new OutputStream() {
            public void write(int b) throws IOException {
                // do nothing
            }
        };
        try {
            new BufferedOutputStream(dummyOut, 0);
            throw new RuntimeException("Expected IllegalArgumentException for zero buffer size");
        } catch (IllegalArgumentException e) {
            // expected
            if (!"Buffer size <= 0".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testNegativeBufferSize() {
        OutputStream dummyOut = new OutputStream() {
            public void write(int b) throws IOException {
                // do nothing
            }
        };
        try {
            new BufferedOutputStream(dummyOut, -1);
            throw new RuntimeException("Expected IllegalArgumentException for negative buffer size");
        } catch (IllegalArgumentException e) {
            // expected
            if (!"Buffer size <= 0".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}

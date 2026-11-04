/*
 * @test
 * @summary Test BufferedWriter constructors for valid and invalid buffer sizes
 * @run main BufferedWriterWitupTest
 */

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

public class BufferedWriterWitupTest {

    static class DummyWriter extends Writer {
        public void write(char[] cbuf, int off, int len) throws IOException {}
        public void flush() throws IOException {}
        public void close() throws IOException {}
    }

    public static void main(String[] args) {
        testValidBufferSize();
        testInvalidBufferSize();
    }

    private static void testValidBufferSize() {
        Writer dummy = new DummyWriter();
        // Test with default buffer size (8192)
        BufferedWriter bw1 = new BufferedWriter(dummy);
        if (bw1 == null) {
            throw new AssertionError("BufferedWriter constructor with default size returned null");
        }

        // Test with a positive buffer size
        int sz = 1024;
        BufferedWriter bw2 = new BufferedWriter(dummy, sz);
        if (bw2 == null) {
            throw new AssertionError("BufferedWriter constructor with positive size returned null");
        }
    }

    private static void testInvalidBufferSize() {
        Writer dummy = new DummyWriter();
        try {
            new BufferedWriter(dummy, 0);
            throw new AssertionError("Expected IllegalArgumentException for buffer size 0");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        try {
            new BufferedWriter(dummy, -1);
            throw new AssertionError("Expected IllegalArgumentException for negative buffer size");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }
}

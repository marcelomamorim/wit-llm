/*
 * @test
 * @summary Test BufferedWriter constructor with buffer size parameter
 * @run main BufferedWriterWitupTest
 */

import java.io.BufferedWriter;
import java.io.Writer;
import java.io.IOException;

public class BufferedWriterWitupTest {
    public static void main(String[] args) throws Exception {
        testValidBufferSize();
        testZeroBufferSize();
        testNegativeBufferSize();
    }

    private static void testValidBufferSize() throws IOException {
        Writer dummy = new Writer() {
            public void write(char[] cbuf, int off, int len) throws IOException {}
            public void flush() throws IOException {}
            public void close() throws IOException {}
        };
        int size = 10;
        BufferedWriter bw = new BufferedWriter(dummy, size);
        // Check that no exception is thrown and buffer size is set (indirectly)
        // We cannot access private fields, so just write and flush to check no error
        bw.write('a');
        bw.flush();
        bw.close();
    }

    private static void testZeroBufferSize() {
        Writer dummy = new Writer() {
            public void write(char[] cbuf, int off, int len) throws IOException {}
            public void flush() throws IOException {}
            public void close() throws IOException {}
        };
        try {
            new BufferedWriter(dummy, 0);
            throw new RuntimeException("Expected IllegalArgumentException for zero buffer size");
        } catch (IllegalArgumentException e) {
            if (!"Buffer size <= 0".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testNegativeBufferSize() {
        Writer dummy = new Writer() {
            public void write(char[] cbuf, int off, int len) throws IOException {}
            public void flush() throws IOException {}
            public void close() throws IOException {}
        };
        try {
            new BufferedWriter(dummy, -1);
            throw new RuntimeException("Expected IllegalArgumentException for negative buffer size");
        } catch (IllegalArgumentException e) {
            if (!"Buffer size <= 0".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}

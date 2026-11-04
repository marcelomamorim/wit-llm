/*
 * @test
 * @summary Test BufferedWriter constructors for expected behavior and exception
 * @run main BufferedWriterWitupTest
 */

import java.io.BufferedWriter;
import java.io.Writer;
import java.io.IOException;

public class BufferedWriterWitupTest {

    static class DummyWriter extends Writer {
        public void write(char[] cbuf, int off, int len) throws IOException {
            // no-op
        }
        public void flush() throws IOException {
            // no-op
        }
        public void close() throws IOException {
            // no-op
        }
    }

    public static void main(String[] args) throws Exception {
        testValidConstructor();
        testConstructorWithZeroBufferSize();
        testConstructorWithNegativeBufferSize();
    }

    private static void testValidConstructor() throws IOException {
        Writer w = new DummyWriter();
        BufferedWriter bw = new BufferedWriter(w);
        // Write some data to check no exception and basic functionality
        bw.write('a');
        bw.flush();
        bw.close();
    }

    private static void testConstructorWithZeroBufferSize() {
        Writer w = new DummyWriter();
        try {
            new BufferedWriter(w, 0);
            throw new RuntimeException("Expected IllegalArgumentException for zero buffer size");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }
    }

    private static void testConstructorWithNegativeBufferSize() {
        Writer w = new DummyWriter();
        try {
            new BufferedWriter(w, -1);
            throw new RuntimeException("Expected IllegalArgumentException for negative buffer size");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }
    }
}

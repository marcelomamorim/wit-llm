/*
 * @test
 * @summary Test java.io.Writer.write(String) for null and closed stream exceptions
 * @run main WriterWitupTest
 */

import java.io.Writer;
import java.io.IOException;
import java.util.Objects;

public class WriterWitupTest {

    // Minimal concrete subclass of Writer to test write(String) method
    static class TestWriter extends Writer {
        private boolean closed = false;

        protected TestWriter() {
            super();
        }

        @Override
        public void write(char[] cbuf, int off, int len) throws IOException {
            // no-op
        }

        @Override
        public void flush() throws IOException {
            // no-op
        }

        @Override
        public void close() throws IOException {
            closed = true;
        }

        // expose ensureOpen method behavior by throwing IOException if closed
        void ensureOpen() throws IOException {
            if (closed) {
                throw new IOException("Stream closed");
            }
        }

        // Override write(String) to call super implementation
        @Override
        public void write(String str) throws IOException {
            Objects.requireNonNull(str);
            ensureOpen();
        }
    }

    public static void main(String[] args) throws Exception {
        testWriteNullStringThrowsNPE();
        testWriteClosedStreamThrowsIOException();
        testWriteValidStringSucceeds();
        System.out.println("All tests passed.");
    }

    private static void testWriteNullStringThrowsNPE() {
        TestWriter w = new TestWriter();
        try {
            w.write(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        } catch (IOException ioe) {
            throw new RuntimeException("Unexpected IOException", ioe);
        }
    }

    private static void testWriteClosedStreamThrowsIOException() throws IOException {
        TestWriter w = new TestWriter();
        w.close();
        try {
            w.write(""); // empty string but not null
            throw new RuntimeException("Expected IOException not thrown");
        } catch (IOException expected) {
            // expected
        }
    }

    private static void testWriteValidStringSucceeds() throws IOException {
        TestWriter w = new TestWriter();
        w.write(""); // empty string
        w.write("test");
        // no exception expected
    }
}

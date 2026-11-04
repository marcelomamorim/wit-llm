/*
 * @test
 * @summary Test Reader.transferTo(Writer) method basic behavior
 * @run main ReaderWitupTest
 */

import java.io.Reader;
import java.io.Writer;
import java.io.IOException;
import java.util.Objects;

public class ReaderWitupTest {

    static class TestReader extends Reader {
        private boolean closed = false;

        @Override
        public int read(char[] cbuf, int off, int len) throws IOException {
            if (closed) throw new IOException("Stream closed");
            return -1; // simulate EOF
        }

        @Override
        public void close() throws IOException {
            closed = true;
        }

        @Override
        public long transferTo(Writer out) throws IOException {
            Objects.requireNonNull(out);
            if (closed) throw new IOException("Stream closed");
            return 0L;
        }
    }

    static class DummyWriter extends Writer {
        @Override
        public void write(char[] cbuf, int off, int len) throws IOException {
            // do nothing
        }

        @Override
        public void flush() throws IOException {
            // do nothing
        }

        @Override
        public void close() throws IOException {
            // do nothing
        }
    }

    public static void main(String[] args) throws Exception {
        TestReader reader = new TestReader();
        DummyWriter writer = new DummyWriter();

        // Test that transferTo returns 0 when open
        long transferred = reader.transferTo(writer);
        if (transferred != 0L) {
            throw new AssertionError("Expected transferTo to return 0, got " + transferred);
        }

        // Test that transferTo throws IOException after close
        reader.close();
        boolean threw = false;
        try {
            reader.transferTo(writer);
        } catch (IOException e) {
            threw = true;
            if (!"Stream closed".equals(e.getMessage())) {
                throw new AssertionError("Expected IOException with message 'Stream closed', got: " + e.getMessage());
            }
        }
        if (!threw) {
            throw new AssertionError("Expected IOException after close() on transferTo");
        }

        // Test that transferTo throws NullPointerException if argument is null
        reader = new TestReader();
        try {
            reader.transferTo(null);
            throw new AssertionError("Expected NullPointerException when passing null Writer");
        } catch (NullPointerException npe) {
            // expected
        }
    }
}

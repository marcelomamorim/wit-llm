/*
 * @test
 * @summary Test java.io.Reader.read(CharBuffer) method behavior including null argument and closed stream
 * @run main ReaderWitupTest
 */

import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.Objects;

public class ReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testReadNullCharBuffer();
        testReadClosedStream();
        testReadNoRemaining();
        testReadHasRemaining();
    }

    // A minimal Reader subclass to test nullReader() behavior
    static class NullReader extends Reader {
        private volatile boolean closed = false;

        NullReader() {
            super();
        }

        private void ensureOpen() throws IOException {
            if (closed) {
                throw new IOException("Stream closed");
            }
        }

        @Override
        public int read(CharBuffer target) throws IOException {
            Objects.requireNonNull(target);
            ensureOpen();
            if (target.hasRemaining()) {
                return -1;
            }
            return 0;
        }

        @Override
        public int read(char[] cbuf, int off, int len) throws IOException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void close() throws IOException {
            closed = true;
        }
    }

    // Test that read(CharBuffer) throws NullPointerException if target is null
    private static void testReadNullCharBuffer() {
        Reader r = Reader.nullReader();
        try {
            r.read((CharBuffer) null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        } catch (IOException ioe) {
            throw new RuntimeException("Unexpected IOException", ioe);
        }
    }

    // Test that read(CharBuffer) throws IOException if stream is closed
    private static void testReadClosedStream() throws IOException {
        Reader r = Reader.nullReader();
        r.close();
        try {
            r.read(CharBuffer.allocate(1));
            throw new RuntimeException("Expected IOException not thrown after close");
        } catch (IOException expected) {
            // expected
        }
    }

    // Test read(CharBuffer) returns 0 if target has no remaining space
    private static void testReadNoRemaining() throws IOException {
        Reader r = Reader.nullReader();
        CharBuffer buf = CharBuffer.allocate(1);
        buf.position(buf.limit()); // no remaining
        int result = r.read(buf);
        if (result != 0) {
            throw new RuntimeException("Expected 0 when no remaining, got " + result);
        }
    }

    // Test read(CharBuffer) returns -1 if target has remaining space
    private static void testReadHasRemaining() throws IOException {
        Reader r = Reader.nullReader();
        CharBuffer buf = CharBuffer.allocate(1);
        buf.position(0); // has remaining
        int result = r.read(buf);
        if (result != -1) {
            throw new RuntimeException("Expected -1 when has remaining, got " + result);
        }
    }
}

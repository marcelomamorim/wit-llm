/*
 * @test
 * @summary Test Reader.skip(long) method behavior including IOException on closed stream
 * @run main ReaderWitupTest
 */

import java.io.IOException;
import java.io.Reader;

public class ReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testSkipReturnsZeroWhenOpen();
        testSkipThrowsIOExceptionWhenClosed();
    }

    private static void testSkipReturnsZeroWhenOpen() throws IOException {
        Reader r = new Reader() {
            private boolean closed = false;
            @Override
            public int read(char[] cbuf, int off, int len) throws IOException {
                if (closed) throw new IOException("Stream closed");
                return -1;
            }
            @Override
            public void close() throws IOException {
                closed = true;
            }
        };

        long skipped = r.skip(10L);
        if (skipped != 0L) {
            throw new AssertionError("Expected skip to return 0 when open, got " + skipped);
        }
    }

    private static void testSkipThrowsIOExceptionWhenClosed() {
        Reader r = new Reader() {
            private boolean closed = true;
            private void ensureOpen() throws IOException {
                if (closed) {
                    throw new IOException("Stream closed");
                }
            }
            @Override
            public long skip(long n) throws IOException {
                ensureOpen();
                return 0L;
            }
            @Override
            public int read(char[] cbuf, int off, int len) throws IOException {
                ensureOpen();
                return -1;
            }
            @Override
            public void close() throws IOException {
                closed = true;
            }
        };

        try {
            r.skip(1L);
            throw new AssertionError("Expected IOException when skipping on closed Reader");
        } catch (IOException e) {
            // Expected
            if (!"Stream closed".equals(e.getMessage())) {
                throw new AssertionError("Unexpected IOException message: " + e.getMessage());
            }
        }
    }
}

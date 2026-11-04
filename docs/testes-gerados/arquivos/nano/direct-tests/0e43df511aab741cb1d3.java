/* @test    @summary Test the skip method of java.io.Reader for deterministic behavior    @run main ReaderWitupTest */

import java.io.Reader;
import java.io.IOException;

public class ReaderWitupTest {

    public static void main(String[] args) {
        testSkipReturnsZeroWhenCalled();
    }

    public static void testSkipReturnsZeroWhenCalled() {
        Reader reader = new Reader() {
            private boolean closed = false;

            private void ensureOpen() throws IOException {
                if (closed) {
                    throw new IOException("Stream closed");
                }
            }

            @Override
            public int read() throws IOException {
                ensureOpen();
                return -1;
            }

            @Override
            public int read(char[] cbuf, int off, int len) throws IOException {
                Objects.checkFromIndexSize(off, len, cbuf.length);
                ensureOpen();
                if (len == 0) {
                    return 0;
                }
                return -1;
            }

            @Override
            public long skip(long n) throws IOException {
                ensureOpen();
                return 0L;
            }

            @Override
            public void close() throws IOException {
                closed = true;
            }
        };
        try {
            long skipped = reader.skip(100);
            if (skipped != 0L) {
                throw new AssertionError("Expected skip to return 0, but got " + skipped);
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e);
        }
    }
}

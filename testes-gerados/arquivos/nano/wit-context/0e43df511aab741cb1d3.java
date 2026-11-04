/* @test    @summary Testa o método skip de java.io.Reader para verificar comportamento com stream fechado    @run main ReaderWitupTest */

import java.io.IOException;
import java.io.Reader;

public class ReaderWitupTest {

    public static void main(String[] args) {
        testSkipWhenClosed();
    }

    public static void testSkipWhenClosed() {
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
                ensureOpen();
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
            reader.close();
            long result = reader.skip(10);
            if (result != 0L) {
                throw new AssertionError("Expected skip to return 0 after close, but got: " + result);
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e.getMessage(), e);
        }
    }
}

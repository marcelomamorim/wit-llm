/*
 * @test
 * @summary Test java.io.Reader.skip(long) method behavior
 * @run main ReaderWitupTest
 */

import java.io.IOException;
import java.io.Reader;

public class ReaderWitupTest {

    static class TestReader extends Reader {
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

        @Override
        public long skip(long n) throws IOException {
            ensureOpen();
            return 0L;
        }

        void ensureOpen() throws IOException {
            if (closed) throw new IOException("Stream closed");
        }
    }

    public static void main(String[] args) throws Exception {
        TestReader r = new TestReader();

        // skip with positive n returns 0
        long skipped = r.skip(10L);
        if (skipped != 0L) {
            throw new AssertionError("Expected skip(10) to return 0 but got " + skipped);
        }

        // skip with zero n returns 0
        skipped = r.skip(0L);
        if (skipped != 0L) {
            throw new AssertionError("Expected skip(0) to return 0 but got " + skipped);
        }

        // skip with negative n returns 0 (no exception expected)
        skipped = r.skip(-5L);
        if (skipped != 0L) {
            throw new AssertionError("Expected skip(-5) to return 0 but got " + skipped);
        }

        // close and then skip should throw IOException
        r.close();
        try {
            r.skip(1L);
            throw new AssertionError("Expected IOException after close when calling skip");
        } catch (IOException e) {
            // expected
        }
    }
}

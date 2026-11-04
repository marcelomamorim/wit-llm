/*
 * @test
 * @summary Test java.io.Reader.skip(long) for negative argument and normal behavior
 * @run main ReaderWitupTest
 */

import java.io.IOException;
import java.io.Reader;

public class ReaderWitupTest {

    static class DummyReader extends Reader {
        private boolean closed = false;
        private final char[] data = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        private int pos = 0;

        @Override
        public int read(char[] cbuf, int off, int len) throws IOException {
            if (closed) throw new IOException("Stream closed");
            if (cbuf == null) throw new NullPointerException();
            if (off < 0 || len < 0 || off + len > cbuf.length) throw new IndexOutOfBoundsException();
            if (pos >= data.length) return -1;
            int charsToRead = Math.min(len, data.length - pos);
            System.arraycopy(data, pos, cbuf, off, charsToRead);
            pos += charsToRead;
            return charsToRead;
        }

        @Override
        public void close() throws IOException {
            closed = true;
        }
    
        @Override
        public int read() throws IOException {
            if (closed) throw new IOException("Stream closed");
            if (pos >= data.length) return -1;
            return data[pos++];
        }
    }

    public static void main(String[] args) throws Exception {
        testSkipNegative();
        testSkipNormal();
        System.out.println("All tests passed.");
    }

    private static void testSkipNegative() {
        Reader r = new DummyReader();
        try {
            r.skip(-1L);
            throw new AssertionError("Expected IllegalArgumentException for negative skip value");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
    }

    private static void testSkipNormal() throws IOException {
        DummyReader r = new DummyReader();
        long skipped = r.skip(5L);
        if (skipped != 5L) {
            throw new AssertionError("Expected to skip 5 chars, but skipped " + skipped);
        }
        int nextChar = r.read();
        if (nextChar != 'f') {
            throw new AssertionError("Expected next char 'f' after skipping 5 chars, but got '" + (char)nextChar + "'");
        }

        // Skip more than remaining chars
        skipped = r.skip(100L);
        if (skipped != 20L) { // 26 total - 6 read = 20 remaining
            throw new AssertionError("Expected to skip remaining 20 chars, but skipped " + skipped);
        }
        int endChar = r.read();
        if (endChar != -1) {
            throw new AssertionError("Expected end of stream after skipping all chars, but got " + endChar);
        }
    }
}

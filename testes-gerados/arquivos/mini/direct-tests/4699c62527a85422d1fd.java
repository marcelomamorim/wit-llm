/*
 * @test
 * @summary Test java.io.Reader.skip(long) method behavior
 * @run main ReaderWitupTest
 */

import java.io.IOException;
import java.io.Reader;

public class ReaderWitupTest {

    static class TestReader extends Reader {
        private final char[] data;
        private int pos = 0;

        TestReader(char[] data) {
            super();
            this.data = data;
        }

        @Override
        public int read(char[] cbuf, int off, int len) throws IOException {
            if (pos >= data.length) {
                return -1;
            }
            int toRead = Math.min(len, data.length - pos);
            System.arraycopy(data, pos, cbuf, off, toRead);
            pos += toRead;
            return toRead;
        }

        @Override
        public void close() throws IOException {
            // no-op
        }
    }

    public static void main(String[] args) throws Exception {
        testSkipNegative();
        testSkipZero();
        testSkipLessThanDataLength();
        testSkipMoreThanDataLength();
    }

    private static void testSkipNegative() {
        Reader r = new TestReader(new char[0]);
        try {
            r.skip(-1L);
            throw new RuntimeException("Expected IllegalArgumentException for negative skip value");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }

    private static void testSkipZero() throws IOException {
        char[] data = {'a', 'b', 'c'};
        TestReader r = new TestReader(data);
        long skipped = r.skip(0L);
        if (skipped != 0L) {
            throw new RuntimeException("Expected skip(0) to return 0 but got " + skipped);
        }
        // read one char to verify reader still works
        char[] buf = new char[1];
        int read = r.read(buf, 0, 1);
        if (read != 1 || buf[0] != 'a') {
            throw new RuntimeException("Expected to read 'a' after skip(0)");
        }
    }

    private static void testSkipLessThanDataLength() throws IOException {
        char[] data = {'a', 'b', 'c', 'd', 'e'};
        TestReader r = new TestReader(data);
        long toSkip = 3L;
        long skipped = r.skip(toSkip);
        if (skipped != toSkip) {
            throw new RuntimeException("Expected to skip " + toSkip + " but skipped " + skipped);
        }
        char[] buf = new char[2];
        int read = r.read(buf, 0, 2);
        if (read != 2 || buf[0] != 'd' || buf[1] != 'e') {
            throw new RuntimeException("Expected to read 'd' and 'e' after skipping 3 chars");
        }
    }

    private static void testSkipMoreThanDataLength() throws IOException {
        char[] data = {'x', 'y'};
        TestReader r = new TestReader(data);
        long toSkip = 10L;
        long skipped = r.skip(toSkip);
        if (skipped != data.length) {
            throw new RuntimeException("Expected to skip only available chars " + data.length + " but skipped " + skipped);
        }
        int read = r.read();
        if (read != -1) {
            throw new RuntimeException("Expected end of stream after skipping all chars");
        }
    }
}

/*
 * @test
 * @summary Behavioral tests for Reader.skip(long) using a minimal public subclass
 * @run main ReaderWitupTest
 */

import java.io.IOException;
import java.io.Reader;

public class ReaderWitupTest {

    static class ArrayReader extends Reader {
        private final char[] data;
        private int pos;

        ArrayReader(String s) {
            data = s.toCharArray();
        }

        @Override
        public int read(char[] cbuf, int off, int len) throws IOException {
            if (len == 0) {
                return 0;
            }
            if (pos >= data.length) {
                return -1;
            }
            int n = Math.min(len, data.length - pos);
            System.arraycopy(data, pos, cbuf, off, n);
            pos += n;
            return n;
        }

        @Override
        public void close() {
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) throws Exception {
        testNegativeSkipThrows();
        testZeroSkipReturnsZero();
        testPartialSkipThenRead();
        testSkipPastEndReturnsAvailable();
        testLargeSkipCrossesBufferChunks();
    }

    static void testNegativeSkipThrows() throws IOException {
        ArrayReader r = new ArrayReader("abc");
        boolean thrown = false;
        try {
            r.skip(-1L);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "skip(-1) must throw IllegalArgumentException");
    }

    static void testZeroSkipReturnsZero() throws IOException {
        ArrayReader r = new ArrayReader("abc");
        check(r.skip(0L) == 0L, "skip(0) must return 0");
        check(r.read() == 'a', "skip(0) must not consume characters");
    }

    static void testPartialSkipThenRead() throws IOException {
        ArrayReader r = new ArrayReader("abcdef");
        check(r.skip(3L) == 3L, "skip(3) must return 3");
        check(r.read() == 'd', "next char after skip(3) must be 'd'");
    }

    static void testSkipPastEndReturnsAvailable() throws IOException {
        ArrayReader r = new ArrayReader("abc");
        check(r.skip(100L) == 3L, "skip beyond end must return number of available chars");
        check(r.read() == -1, "stream must be at end after skip");
    }

    static void testLargeSkipCrossesBufferChunks() throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 20000; i++) {
            sb.append('x');
        }
        ArrayReader r = new ArrayReader(sb.toString());
        check(r.skip(20000L) == 20000L, "large skip must return full count");
        check(r.read() == -1, "stream must be at end after large skip");
    }
}

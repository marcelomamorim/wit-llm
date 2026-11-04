/*
 * @test
 * @summary Verifies Reader.skip(long) argument validation, return values, end-of-stream behavior and multi-read looping
 * @run main ReaderWitupTest
 */

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class ReaderWitupTest {

    static class OneCharReader extends Reader {
        private final String s;
        private int pos;

        OneCharReader(String s) {
            this.s = s;
        }

        @Override
        public int read(char[] cbuf, int off, int len) {
            if (pos >= s.length()) {
                return -1;
            }
            if (len == 0) {
                return 0;
            }
            cbuf[off] = s.charAt(pos++);
            return 1;
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
        testZeroSkip();
        testSkipThenRead();
        testSkipPastEnd();
        testSkipWithPartialReads();
        testLargeSkip();
    }

    static void testNegativeSkipThrows() {
        boolean thrown = false;
        try {
            new StringReader("abc").skip(-1L);
        } catch (IllegalArgumentException e) {
            thrown = true;
        } catch (IOException e) {
            throw new RuntimeException("unexpected IOException", e);
        }
        check(thrown, "skip(-1) should throw IllegalArgumentException");
    }

    static void testZeroSkip() throws IOException {
        check(new StringReader("abc").skip(0L) == 0L, "skip(0) should return 0");
    }

    static void testSkipThenRead() throws IOException {
        StringReader r = new StringReader("abcdef");
        check(r.skip(2L) == 2L, "skip(2) should return 2");
        check(r.read() == 'c', "read after skip(2) should return 'c'");
    }

    static void testSkipPastEnd() throws IOException {
        StringReader r = new StringReader("abc");
        check(r.skip(100L) == 3L, "skip past end should return number of available chars");
        check(r.skip(1L) == 0L, "skip at end of stream should return 0");
        check(r.read() == -1, "read at end of stream should return -1");
    }

    static void testSkipWithPartialReads() throws IOException {
        OneCharReader r = new OneCharReader("abcdefgh");
        check(r.skip(5L) == 5L, "skip must loop over partial reads and return 5");
        check(r.read() == 'f', "read after skip(5) should return 'f'");
    }

    static void testLargeSkip() throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            sb.append('x');
        }
        StringReader r = new StringReader(sb.toString());
        check(r.skip(Long.MAX_VALUE) == 10000L, "skip(Long.MAX_VALUE) should return 10000 for 10000 chars");
        check(r.read() == -1, "stream should be exhausted after large skip");
    }
}

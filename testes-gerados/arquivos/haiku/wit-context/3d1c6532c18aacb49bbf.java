/*
 * @test
 * @summary Behavioral tests for Writer.write(String, int, int) using a minimal concrete Writer subclass
 * @run main WriterWitupTest
 */

import java.io.IOException;
import java.io.Writer;

public class WriterWitupTest {

    static class RecordingWriter extends Writer {
        final StringBuilder sb = new StringBuilder();
        int writeCalls;

        @Override
        public void write(char[] cbuf, int off, int len) throws IOException {
            writeCalls++;
            sb.append(cbuf, off, len);
        }

        @Override
        public void flush() throws IOException {
        }

        @Override
        public void close() throws IOException {
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new AssertionError(msg);
        }
    }

    static void testValidRange() throws IOException {
        RecordingWriter w = new RecordingWriter();
        w.write("hello", 1, 3);
        check(w.sb.toString().equals("ell"), "expected 'ell' but was '" + w.sb + "'");
    }

    static void testFullAndEmptyRanges() throws IOException {
        RecordingWriter w = new RecordingWriter();
        w.write("abc", 0, 3);
        check(w.sb.toString().equals("abc"), "full range mismatch");

        RecordingWriter e = new RecordingWriter();
        e.write("abc", 0, 0);
        check(e.sb.length() == 0, "zero-length write must emit nothing");

        RecordingWriter t = new RecordingWriter();
        t.write("abc", 3, 0);
        check(t.sb.length() == 0, "zero-length write at end must emit nothing");
    }

    static void testLargeStringUsesOwnBufferPath() throws IOException {
        StringBuilder src = new StringBuilder();
        for (int i = 0; i < 2000; i++) {
            src.append((char) ('a' + (i % 26)));
        }
        String s = src.toString();
        RecordingWriter w = new RecordingWriter();
        w.write(s, 0, s.length());
        check(w.sb.toString().equals(s), "large write content mismatch");
    }

    static void testInvalidRangesDoNotWrite() throws IOException {
        int[][] cases = { {-1, 1}, {0, 4}, {2, 2}, {4, 0}, {0, -1} };
        for (int[] c : cases) {
            RecordingWriter w = new RecordingWriter();
            boolean thrown = false;
            try {
                w.write("abc", c[0], c[1]);
            } catch (IndexOutOfBoundsException ex) {
                thrown = true;
            }
            check(thrown, "expected IndexOutOfBoundsException for off=" + c[0] + " len=" + c[1]);
            check(w.sb.length() == 0, "no characters may be written on invalid range");
            check(w.writeCalls == 0, "write(char[],int,int) must not be called on invalid range");
        }
    }

    static void testNullStringThrowsNpe() throws IOException {
        RecordingWriter w = new RecordingWriter();
        boolean thrown = false;
        try {
            w.write((String) null, 0, 0);
        } catch (NullPointerException ex) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null string");
        check(w.sb.length() == 0, "no characters may be written for null string");
    }

    public static void main(String[] args) throws IOException {
        testValidRange();
        testFullAndEmptyRanges();
        testLargeStringUsesOwnBufferPath();
        testInvalidRangesDoNotWrite();
        testNullStringThrowsNpe();
    }
}

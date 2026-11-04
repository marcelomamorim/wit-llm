/*
 * @test
 * @summary Verifies Reader.transferTo(Writer) null checks, transfer result, buffered looping and closed-stream behavior of nullReader()
 * @run main ReaderWitupTest
 */

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class ReaderWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static class CountingReader extends Reader {
        private final char[] data;
        private int pos;
        boolean closed;

        CountingReader(String s) {
            data = s.toCharArray();
        }

        @Override
        public int read(char[] cbuf, int off, int len) throws IOException {
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
            closed = true;
        }
    }

    static class CollectingWriter extends Writer {
        final StringBuilder sb = new StringBuilder();

        @Override
        public void write(char[] cbuf, int off, int len) {
            sb.append(cbuf, off, len);
        }

        @Override
        public void flush() {
        }

        @Override
        public void close() {
        }
    }

    static void testBasicTransfer() throws IOException {
        CountingReader r = new CountingReader("hello");
        CollectingWriter w = new CollectingWriter();
        long n = r.transferTo(w);
        check(n == 5L, "expected 5 but was " + n);
        check("hello".equals(w.sb.toString()), "unexpected content: " + w.sb);
    }

    static void testEmptyInput() throws IOException {
        CountingReader r = new CountingReader("");
        CollectingWriter w = new CollectingWriter();
        long n = r.transferTo(w);
        check(n == 0L, "expected 0 but was " + n);
        check(w.sb.length() == 0, "writer should be empty");
    }

    static void testLargeInputSpansBuffer() throws IOException {
        StringBuilder in = new StringBuilder();
        for (int i = 0; i < 20000; i++) {
            in.append('x');
        }
        CountingReader r = new CountingReader(in.toString());
        CollectingWriter w = new CollectingWriter();
        long n = r.transferTo(w);
        check(n == 20000L, "expected 20000 but was " + n);
        check(w.sb.length() == 20000, "unexpected written length " + w.sb.length());
    }

    static void testNullWriterRejected() {
        CountingReader r = new CountingReader("abc");
        try {
            r.transferTo(null);
        } catch (NullPointerException e) {
            return;
        } catch (IOException e) {
            throw new RuntimeException("expected NullPointerException, got IOException", e);
        }
        throw new RuntimeException("expected NullPointerException");
    }

    static void testNullReaderTransfersZero() throws IOException {
        Reader r = Reader.nullReader();
        CollectingWriter w = new CollectingWriter();
        long n = r.transferTo(w);
        check(n == 0L, "expected 0 but was " + n);
        check(w.sb.length() == 0, "writer should be empty");
    }

    static void testNullReaderNullWriterRejected() {
        Reader r = Reader.nullReader();
        try {
            r.transferTo(null);
        } catch (NullPointerException e) {
            return;
        } catch (IOException e) {
            throw new RuntimeException("expected NullPointerException, got IOException", e);
        }
        throw new RuntimeException("expected NullPointerException");
    }

    static void testClosedNullReaderThrows() throws IOException {
        Reader r = Reader.nullReader();
        r.close();
        CollectingWriter w = new CollectingWriter();
        try {
            r.transferTo(w);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("expected IOException after close");
    }

    public static void main(String[] args) throws IOException {
        testBasicTransfer();
        testEmptyInput();
        testLargeInputSpansBuffer();
        testNullWriterRejected();
        testNullReaderTransfersZero();
        testNullReaderNullWriterRejected();
        testClosedNullReaderThrows();
    }
}

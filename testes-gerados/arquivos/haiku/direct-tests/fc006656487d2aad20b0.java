/*
 * @test
 * @summary Verifies observable behavior of Writer.append(CharSequence) and the nullWriter() returned instance
 * @run main WriterWitupTest
 */

import java.io.IOException;
import java.io.Writer;

public class WriterWitupTest {

    static final class RecordingWriter extends Writer {
        final StringBuilder sb = new StringBuilder();

        @Override
        public void write(char[] cbuf, int off, int len) throws IOException {
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
            throw new RuntimeException(msg);
        }
    }

    static void testAppendWritesStringAndReturnsThis() throws IOException {
        RecordingWriter w = new RecordingWriter();
        Writer r = w.append((CharSequence) "abc");
        check(r == w, "append must return this");
        check(w.sb.toString().equals("abc"), "expected 'abc' but was '" + w.sb + "'");
    }

    static void testAppendNullWritesNullString() throws IOException {
        RecordingWriter w = new RecordingWriter();
        Writer r = w.append((CharSequence) null);
        check(r == w, "append(null) must return this");
        check(w.sb.toString().equals("null"), "expected 'null' but was '" + w.sb + "'");
    }

    static void testAppendAccumulates() throws IOException {
        RecordingWriter w = new RecordingWriter();
        w.append((CharSequence) "ab").append((CharSequence) "cd");
        check(w.sb.toString().equals("abcd"), "expected 'abcd' but was '" + w.sb + "'");
    }

    static void testNullWriterAppendOpenReturnsThis() throws IOException {
        Writer nw = Writer.nullWriter();
        Writer r = nw.append((CharSequence) "discarded");
        check(r == nw, "nullWriter append must return this while open");
    }

    static void testNullWriterAppendAfterCloseThrows() throws IOException {
        Writer nw = Writer.nullWriter();
        nw.close();
        boolean thrown = false;
        try {
            nw.append((CharSequence) "x");
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "append after close must throw IOException");
    }

    public static void main(String[] args) throws IOException {
        testAppendWritesStringAndReturnsThis();
        testAppendNullWritesNullString();
        testAppendAccumulates();
        testNullWriterAppendOpenReturnsThis();
        testNullWriterAppendAfterCloseThrows();
    }
}

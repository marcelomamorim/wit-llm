/*
 * @test
 * @summary Tests observable behavior of Writer.append(CharSequence, int, int)
 * @run main WriterWitupTest
 */

import java.io.IOException;
import java.io.Writer;

public class WriterWitupTest {

    static class CollectingWriter extends Writer {
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

    static void testNullWriterReturnsThis() throws IOException {
        Writer nw = Writer.nullWriter();
        Writer r = nw.append("abc", 0, 3);
        check(r == nw, "nullWriter append(csq,start,end) must return this");
    }

    static void testNullWriterNullCsqAccepted() throws IOException {
        Writer nw = Writer.nullWriter();
        Writer r = nw.append(null, 5, 1);
        check(r == nw, "nullWriter append with null csq must return this");
    }

    static void testNullWriterInvalidRange() throws IOException {
        Writer nw = Writer.nullWriter();
        boolean thrown = false;
        try {
            nw.append("abc", 2, 1);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "nullWriter append with start > end must throw IndexOutOfBoundsException");
    }

    static void testNullWriterClosedThrows() throws IOException {
        Writer nw = Writer.nullWriter();
        nw.close();
        boolean thrown = false;
        try {
            nw.append("abc", 0, 3);
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "nullWriter append after close must throw IOException");
    }

    static void testDefaultAppendSubrange() throws IOException {
        CollectingWriter w = new CollectingWriter();
        w.append("abcd", 1, 3);
        check(w.sb.toString().equals("bc"),
              "default append(csq,start,end) must write the subsequence [1,3): got " + w.sb);
    }

    static void testDefaultAppendNullWritesNull() throws IOException {
        CollectingWriter w = new CollectingWriter();
        w.append(null, 0, 4);
        check(w.sb.toString().equals("null"),
              "default append(null,0,4) must write \"null\": got " + w.sb);
    }

    public static void main(String[] args) throws IOException {
        testNullWriterReturnsThis();
        testNullWriterNullCsqAccepted();
        testNullWriterInvalidRange();
        testNullWriterClosedThrows();
        testDefaultAppendSubrange();
        testDefaultAppendNullWritesNull();
    }
}

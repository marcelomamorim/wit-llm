/*
 * @test
 * @summary Verifies Writer.append(CharSequence) delegates to write and returns this
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

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        CollectingWriter w = new CollectingWriter();

        Writer r = w.append("abc");
        check(r == w, "append(CharSequence) must return this writer");
        check(w.sb.toString().equals("abc"), "append(\"abc\") must write \"abc\"");

        r = w.append("");
        check(r == w, "append(\"\") must return this writer");
        check(w.sb.toString().equals("abc"), "append(\"\") must not change output");

        r = w.append(new StringBuilder("xy"));
        check(r == w, "append(StringBuilder) must return this writer");
        check(w.sb.toString().equals("abcxy"), "append(StringBuilder) must write its content");

        r = w.append((CharSequence) null);
        check(r == w, "append(null) must return this writer");
        check(w.sb.toString().equals("abcxynull"), "append(null) must write \"null\"");
    }
}

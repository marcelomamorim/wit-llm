/*
 * @test
 * @summary Behavioral tests for Writer.write(String) using a minimal concrete Writer subclass
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

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    public static void main(String[] args) throws Exception {
        CollectingWriter w = new CollectingWriter();
        w.write("abc");
        check("abc".equals(w.sb.toString()), "write(String) should emit all characters");

        w.write("");
        check("abc".equals(w.sb.toString()), "write(\"\") should emit no characters");

        CollectingWriter w2 = new CollectingWriter();
        try {
            w2.write((String) null);
            check(false, "write(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            check(w2.sb.length() == 0, "null input must not emit characters");
        }

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}

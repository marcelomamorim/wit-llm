/*
 * @test
 * @summary Tests Formatter.flush delegation to Flushable appendables and FormatterClosedException after close
 * @run main FormatterWitupTest
 */

import java.io.Flushable;
import java.io.IOException;
import java.util.Formatter;
import java.util.FormatterClosedException;

public class FormatterWitupTest {

    static class CountingAppendable implements Appendable, Flushable {
        int flushCount;
        StringBuilder sb = new StringBuilder();

        public Appendable append(CharSequence csq) throws IOException {
            sb.append(csq);
            return this;
        }

        public Appendable append(CharSequence csq, int start, int end) throws IOException {
            sb.append(csq, start, end);
            return this;
        }

        public Appendable append(char c) throws IOException {
            sb.append(c);
            return this;
        }

        public void flush() throws IOException {
            flushCount++;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testFlushDelegatesToFlushable() {
        CountingAppendable a = new CountingAppendable();
        Formatter f = new Formatter(a);
        f.flush();
        check(a.flushCount == 1, "flush should delegate once, was " + a.flushCount);
        f.flush();
        check(a.flushCount == 2, "second flush should delegate, was " + a.flushCount);
    }

    static void testFlushNonFlushableDoesNotThrow() {
        Formatter f = new Formatter(new StringBuilder());
        f.flush();
        f.format("x");
        f.flush();
    }

    static void testFlushAfterCloseThrows() {
        Formatter f = new Formatter(new StringBuilder());
        f.close();
        boolean thrown = false;
        try {
            f.flush();
        } catch (FormatterClosedException e) {
            thrown = true;
        }
        check(thrown, "flush after close should throw FormatterClosedException");
    }

    static void testFlushAfterCloseDoesNotDelegate() {
        CountingAppendable a = new CountingAppendable();
        Formatter f = new Formatter(a);
        f.close();
        try {
            f.flush();
        } catch (FormatterClosedException e) {
            // expected
        }
        check(a.flushCount == 0, "flush after close must not delegate, was " + a.flushCount);
    }

    public static void main(String[] args) {
        testFlushDelegatesToFlushable();
        testFlushNonFlushableDoesNotThrow();
        testFlushAfterCloseThrows();
        testFlushAfterCloseDoesNotDelegate();
    }
}

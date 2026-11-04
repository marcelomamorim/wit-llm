/*
 * @test
 * @summary Test java.util.Formatter.flush() behavior
 * @run main FormatterWitupTest
 */

import java.util.Formatter;
import java.io.Flushable;
import java.io.IOException;
import java.io.UncheckedIOException;

public class FormatterWitupTest {
    public static void main(String[] args) throws Exception {
        testFlushOnFlushableAppendable();
        testFlushOnNonFlushableAppendable();
        testFlushOnClosedFormatter();
    }

    // A simple Appendable that implements Flushable and tracks flush calls
    static class FlushableAppendable implements Appendable, Flushable {
        boolean flushed = false;
        boolean throwOnFlush = false;

        @Override
        public Appendable append(CharSequence csq) {
            return this;
        }

        @Override
        public Appendable append(CharSequence csq, int start, int end) {
            return this;
        }

        @Override
        public Appendable append(char c) {
            return this;
        }

        @Override
        public void flush() throws IOException {
            if (throwOnFlush) {
                throw new IOException("flush failed");
            }
            flushed = true;
        }
    }

    // A simple Appendable that does not implement Flushable
    static class NonFlushableAppendable implements Appendable {
        @Override
        public Appendable append(CharSequence csq) {
            return this;
        }

        @Override
        public Appendable append(CharSequence csq, int start, int end) {
            return this;
        }

        @Override
        public Appendable append(char c) {
            return this;
        }
    }

    // Test that flush() calls flush on Flushable Appendable and does not throw
    public static void testFlushOnFlushableAppendable() {
        FlushableAppendable fa = new FlushableAppendable();
        Formatter formatter = new Formatter(fa);
        formatter.flush();
        if (!fa.flushed) {
            throw new AssertionError("Flushable.flush() was not called by Formatter.flush()");
        }

        // Test that IOException during flush is caught and stored (no exception thrown)
        fa.flushed = false;
        fa.throwOnFlush = true;
        formatter.flush();
        // No exception expected, flushed flag remains false
        if (fa.flushed) {
            throw new AssertionError("Flushable.flush() should have thrown IOException and not set flushed flag");
        }
    }

    // Test that flush() on Formatter with non-Flushable Appendable does not throw
    public static void testFlushOnNonFlushableAppendable() {
        NonFlushableAppendable nfa = new NonFlushableAppendable();
        Formatter formatter = new Formatter(nfa);
        formatter.flush(); // should do nothing and not throw
    }

    // Test that flush() on closed Formatter throws IllegalStateException
    public static void testFlushOnClosedFormatter() {
        Formatter formatter = new Formatter();
        formatter.close();
        try {
            formatter.flush();
            throw new AssertionError("Expected IllegalStateException when flushing closed Formatter");
        } catch (IllegalStateException ise) {
            // expected
        }
    }
}

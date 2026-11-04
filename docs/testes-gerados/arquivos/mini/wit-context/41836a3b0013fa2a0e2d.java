/*
 * @test
 * @summary Test java.util.Formatter.flush() method behavior including FormatterClosedException
 * @run main FormatterWitupTest
 */

import java.util.Formatter;
import java.io.Flushable;
import java.io.IOException;

public class FormatterWitupTest {
    public static void main(String[] args) throws Exception {
        testFlushOnOpenFormatter();
        testFlushOnClosedFormatter();
    }

    private static void testFlushOnOpenFormatter() throws Exception {
        // Create a Formatter with an Appendable that is Flushable
        final TestAppendable ta = new TestAppendable();
        Formatter formatter = new Formatter(ta);

        // flush() should call flush on the underlying Flushable Appendable
        formatter.flush();

        if (!ta.flushed) {
            throw new AssertionError("flush() did not call flush on underlying Flushable");
        }

        formatter.close();
    }

    private static void testFlushOnClosedFormatter() {
        Formatter formatter = new Formatter();
        formatter.close();

        try {
            formatter.flush();
            throw new AssertionError("flush() did not throw FormatterClosedException after close");
        } catch (java.util.FormatterClosedException expected) {
            // Expected exception
        }
    }

    // Helper Appendable that implements Flushable to detect flush calls
    private static class TestAppendable implements Appendable, Flushable {
        boolean flushed = false;

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
            flushed = true;
        }
    }
}

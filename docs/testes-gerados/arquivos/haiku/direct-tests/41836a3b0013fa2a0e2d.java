/*
 * @test
 * @summary Formatter.flush() forwards to Flushable appendables, records IOException in ioException(), and throws FormatterClosedException when closed
 * @run main FormatterWitupTest
 */

import java.io.Flushable;
import java.io.IOException;

public class FormatterWitupTest {

    static class CountingAppendable implements Appendable, Flushable {
        int flushes;
        StringBuilder sb = new StringBuilder();

        public Appendable append(CharSequence csq) {
            sb.append(csq);
            return this;
        }

        public Appendable append(CharSequence csq, int start, int end) {
            sb.append(csq, start, end);
            return this;
        }

        public Appendable append(char c) {
            sb.append(c);
            return this;
        }

        public void flush() throws IOException {
            flushes++;
        }
    }

    static class FailingFlushAppendable extends CountingAppendable {
        @Override
        public void flush() throws IOException {
            throw new IOException("boom");
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testFlushableIsFlushedEachCall() {
        CountingAppendable c = new CountingAppendable();
        java.util.Formatter f = new java.util.Formatter(c);
        f.flush();
        check(c.flushes == 1, "expected 1 flush, got " + c.flushes);
        f.flush();
        check(c.flushes == 2, "expected 2 flushes, got " + c.flushes);
        check(f.ioException() == null, "ioException should be null on success");
    }

    static void testIOExceptionIsRecordedNotThrown() {
        java.util.Formatter f = new java.util.Formatter(new FailingFlushAppendable());
        f.flush();
        IOException e = f.ioException();
        check(e != null, "ioException should record the IOException from flush");
        check("boom".equals(e.getMessage()), "unexpected message: " + e.getMessage());
    }

    static void testNonFlushableAppendableIsNoOp() {
        StringBuilder sb = new StringBuilder();
        java.util.Formatter f = new java.util.Formatter(sb);
        f.flush();
        check(f.ioException() == null, "non-Flushable flush should not record exception");
    }

    static void testFlushOnClosedFormatterThrows() {
        java.util.Formatter f = new java.util.Formatter(new CountingAppendable());
        f.close();
        try {
            f.flush();
            throw new RuntimeException("expected FormatterClosedException");
        } catch (java.util.FormatterClosedException expected) {
            // expected
        }
    }

    public static void main(String[] args) {
        testFlushableIsFlushedEachCall();
        testIOExceptionIsRecordedNotThrown();
        testNonFlushableAppendableIsNoOp();
        testFlushOnClosedFormatterThrows();
    }
}

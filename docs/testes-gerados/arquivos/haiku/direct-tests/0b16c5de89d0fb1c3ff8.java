/*
 * @test
 * @summary PrintStream(OutputStream, boolean) rejects null and honors autoFlush
 * @run main PrintStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class PrintStreamWitupTest {

    static class CountingStream extends OutputStream {
        int flushCount = 0;
        ByteArrayOutputStream data = new ByteArrayOutputStream();

        @Override
        public void write(int b) {
            data.write(b);
        }

        @Override
        public void flush() {
            flushCount++;
        }
    }

    public static void main(String[] args) throws Exception {
        testNullOutputRejected();
        testAutoFlushTrueFlushesOnNewline();
        testAutoFlushFalseDoesNotFlushOnPrint();
    }

    static void testNullOutputRejected() {
        try {
            new PrintStream((OutputStream) null, true);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException e) {
            if (!"Null output stream".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }
    }

    static void testAutoFlushTrueFlushesOnNewline() {
        CountingStream s = new CountingStream();
        PrintStream ps = new PrintStream(s, true);
        ps.println("hi");
        if (s.flushCount == 0) {
            throw new RuntimeException("expected flush with autoFlush=true");
        }
        if (ps.checkError()) {
            throw new RuntimeException("unexpected error flag");
        }
        if (!s.data.toString().startsWith("hi")) {
            throw new RuntimeException("unexpected output: " + s.data.toString());
        }
    }

    static void testAutoFlushFalseDoesNotFlushOnPrint() {
        CountingStream s = new CountingStream();
        PrintStream ps = new PrintStream(s, false);
        ps.print("hi");
        if (s.flushCount != 0) {
            throw new RuntimeException("unexpected flush with autoFlush=false: " + s.flushCount);
        }
        if (!"hi".equals(s.data.toString())) {
            throw new RuntimeException("unexpected output: " + s.data.toString());
        }
    }
}

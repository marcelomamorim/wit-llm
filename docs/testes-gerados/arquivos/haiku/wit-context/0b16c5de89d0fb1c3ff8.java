/*
 * @test
 * @summary Checks PrintStream(OutputStream, boolean) null rejection and autoFlush behavior
 * @run main PrintStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class PrintStreamWitupTest {

    static final class CountingStream extends ByteArrayOutputStream {
        int flushCount;

        @Override
        public void flush() {
            flushCount++;
            super.flush();
        }
    }

    public static void main(String[] args) throws Exception {
        testNullOutputRejected();
        testAutoFlushTrueFlushesOnPrintln();
        testWritesReachUnderlyingStream();
    }

    static void testNullOutputRejected() {
        try {
            new PrintStream((OutputStream) null, true);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException e) {
            if (!"Null output stream".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage(), e);
            }
        }
    }

    static void testAutoFlushTrueFlushesOnPrintln() {
        CountingStream out = new CountingStream();
        PrintStream ps = new PrintStream(out, true);
        ps.println("hi");
        if (out.flushCount == 0) {
            throw new RuntimeException("autoFlush=true did not flush on println");
        }
        if (ps.checkError()) {
            throw new RuntimeException("unexpected error flag");
        }
    }

    static void testWritesReachUnderlyingStream() throws Exception {
        CountingStream out = new CountingStream();
        PrintStream ps = new PrintStream(out, false);
        ps.print("abc");
        ps.flush();
        String got = out.toString("US-ASCII");
        if (!"abc".equals(got)) {
            throw new RuntimeException("expected 'abc' but got '" + got + "'");
        }
        if (ps.checkError()) {
            throw new RuntimeException("unexpected error flag");
        }
    }
}

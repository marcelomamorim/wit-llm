/*
 * @test
 * @summary Test PrintStream(OutputStream, boolean) constructor behavior
 * @run main PrintStreamWitupTest
 */

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.IOException;

public class PrintStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testNonNullOutputStream();
        testAutoFlushFlag();
    }

    private static void testNonNullOutputStream() {
        try {
            new PrintStream(null, true);
            throw new RuntimeException("Expected NullPointerException for null OutputStream");
        } catch (NullPointerException npe) {
            if (!"Null output stream".equals(npe.getMessage())) {
                throw new RuntimeException("NullPointerException message mismatch", npe);
            }
        }
    }

    private static void testAutoFlushFlag() {
        final boolean[] flushed = {false};
        OutputStream os = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                // do nothing
            }
            @Override
            public void flush() throws IOException {
                flushed[0] = true;
            }
        };

        PrintStream ps = new PrintStream(os, true);
        if (!ps.checkError()) {
            // no error expected
        } else {
            throw new RuntimeException("Unexpected error flag set");
        }

        // Write a newline to trigger autoFlush
        ps.println();
        if (!flushed[0]) {
            throw new RuntimeException("Expected flush to be called on autoFlush=true");
        }

        flushed[0] = false;
        PrintStream ps2 = new PrintStream(os, false);
        ps2.println();
        if (flushed[0]) {
            throw new RuntimeException("Flush should not be called on autoFlush=false");
        }
    }
}

/* @test    @summary Test Reader.skip(long n) method for correct behavior and exception handling    @run main ReaderWitupTest_P1abbab286f21 */

import java.io.Reader;
import java.io.IOException;

public class ReaderWitupTest_P1abbab286f21 {

    public static void main(String[] args) {
        testSkipNegative();
        testSkipZero();
        testSkipPositive();
        testSkipBeyondEnd();
    }

    private static void testSkipNegative() {
        Reader reader = new Reader() {
            private boolean closed = false;
            private final Object lock = new Object();
            @Override
            public int read(char[] cbuf, int off, int len) throws IOException {
                return -1;
            }
            @Override
            public void close() throws IOException {
                closed = true;
            }
        };
        try {
            reader.skip(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative skip");
        } catch (IllegalArgumentException e) {
            // Expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e);
        }
    }

    private static void testSkipZero() {
        Reader reader = new Reader() {
            private boolean closed = false;
            private final Object lock = new Object();
            @Override
            public int read(char[] cbuf, int off, int len) throws IOException {
                return -1;
            }
            @Override
            public void close() throws IOException {
                closed = true;
            }
        };
        try {
            long skipped = reader.skip(0);
            if (skipped != 0) {
                throw new AssertionError("Skip zero should return 0, but got: " + skipped);
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e);
        }
    }

    private static void testSkipPositive() {
        Reader reader = new Reader() {
            private boolean closed = false;
            private final Object lock = new Object();
            private int readCalls = 0;
            @Override
            public int read(char[] cbuf, int off, int len) throws IOException {
                readCalls++;
                return 1; // simulate reading 1 char
            }
            @Override
            public void close() throws IOException {
                closed = true;
            }
        };
        try {
            long toSkip = 5;
            long skipped = reader.skip(toSkip);
            if (skipped != toSkip) {
                throw new AssertionError("Skipped " + skipped + " instead of " + toSkip);
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e);
        }
    }

    private static void testSkipBeyondEnd() {
        Reader reader = new Reader() {
            private boolean closed = false;
            private final Object lock = new Object();
            private int readCalls = 0;
            @Override
            public int read(char[] cbuf, int off, int len) throws IOException {
                if (readCalls >= 3) {
                    return -1; // simulate end of stream
                }
                readCalls++;
                return 1;
            }
            @Override
            public void close() throws IOException {
                closed = true;
            }
        };
        try {
            long toSkip = 10;
            long skipped = reader.skip(toSkip);
            if (skipped != 3) { // only 3 chars can be skipped before end
                throw new AssertionError("Skipped " + skipped + " chars, expected 3");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e);
        }
    }
}

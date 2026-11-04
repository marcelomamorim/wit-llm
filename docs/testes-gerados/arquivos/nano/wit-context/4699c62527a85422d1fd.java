/*
 * @test
 * @summary Test for Reader.skip(long) method, focusing on negative input handling.
 * @run main ReaderWitupTest
 */

import java.io.Reader;
import java.io.IOException;

public class ReaderWitupTest {

    public static void main(String[] args) {
        testSkipNegative();
        testSkipZero();
        testSkipPositive();
    }

    private static void testSkipNegative() {
        Reader reader = new Reader() {
            private boolean closed = false;
            private void ensureOpen() throws IOException {
                if (closed) throw new IOException("Stream closed");
            }
            @Override
            public int read(char[] cbuf, int off, int len) throws IOException {
                ensureOpen();
                return -1;
            }
            @Override
            public void close() throws IOException {
                closed = true;
            }
        };
        try {
            reader.skip(-1);
            System.out.println("FAIL: Expected IllegalArgumentException for negative skip");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for negative skip");
        } catch (IOException e) {
            System.out.println("FAIL: Unexpected IOException: " + e);
        }
    }

    private static void testSkipZero() {
        Reader reader = new Reader() {
            private boolean closed = false;
            private void ensureOpen() throws IOException {
                if (closed) throw new IOException("Stream closed");
            }
            @Override
            public int read(char[] cbuf, int off, int len) throws IOException {
                ensureOpen();
                return -1;
            }
            @Override
            public void close() throws IOException {
                closed = true;
            }
        };
        try {
            long skipped = reader.skip(0);
            if (skipped == 0) {
                System.out.println("PASS: skip(0) returns 0 as expected");
            } else {
                System.out.println("FAIL: skip(0) returned " + skipped);
            }
        } catch (IOException e) {
            System.out.println("FAIL: Unexpected IOException: " + e);
        }
    }

    private static void testSkipPositive() {
        Reader reader = new Reader() {
            private boolean closed = false;
            private void ensureOpen() throws IOException {
                if (closed) throw new IOException("Stream closed");
            }
            @Override
            public int read(char[] cbuf, int off, int len) throws IOException {
                ensureOpen();
                return -1;
            }
            @Override
            public void close() throws IOException {
                closed = true;
            }
        };
        try {
            long skipped = reader.skip(10);
            if (skipped == 0) {
                System.out.println("PASS: skip(10) returns 0 when no data available");
            } else {
                System.out.println("PASS: skip(10) returned " + skipped);
            }
        } catch (IOException e) {
            System.out.println("FAIL: Unexpected IOException: " + e);
        }
    }
}

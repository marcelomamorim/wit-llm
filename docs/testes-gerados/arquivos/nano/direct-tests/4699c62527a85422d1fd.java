/* @test    @summary Test Reader.skip(long n) method for correct behavior and exception handling    @run main ReaderWitupTest */

import java.io.Reader;
import java.io.IOException;

public class ReaderWitupTest {

    public static void main(String[] args) {
        testSkipNegative();
        testSkipZero();
        testSkipPositive();
        testSkipBeyondEnd();
    }

    private static void testSkipNegative() {
        Reader reader = new Reader() {
            @Override
            public int read(char[] cbuf, int off, int len) throws IOException {
                return -1;
            }

            @Override
            public void close() throws IOException {
            }
        };
        try {
            reader.skip(-1);
            System.out.println("FAIL: Expected IllegalArgumentException for negative skip");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for negative skip");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    private static void testSkipZero() {
        Reader reader = new Reader() {
            @Override
            public int read(char[] cbuf, int off, int len) throws IOException {
                return -1;
            }

            @Override
            public void close() throws IOException {
            }
        };
        try {
            long skipped = reader.skip(0);
            if (skipped == 0) {
                System.out.println("PASS: skip(0) returns 0 as expected");
            } else {
                System.out.println("FAIL: skip(0) returned " + skipped);
            }
        } catch (Exception e) {
            System.out.println("FAIL: Exception during skip(0): " + e);
        }
    }

    private static void testSkipPositive() {
        Reader reader = new Reader() {
            private int readCount = 0;
            @Override
            public int read(char[] cbuf, int off, int len) throws IOException {
                if (readCount < 3) {
                    readCount++;
                    return 1;
                }
                return -1;
            }

            @Override
            public void close() throws IOException {
            }
        };
        try {
            long toSkip = 2;
            long skipped = reader.skip(toSkip);
            if (skipped == toSkip) {
                System.out.println("PASS: skip(" + toSkip + ") skipped expected amount");
            } else {
                System.out.println("FAIL: skip(" + toSkip + ") skipped " + skipped);
            }
        } catch (Exception e) {
            System.out.println("FAIL: Exception during skip: " + e);
        }
    }

    private static void testSkipBeyondEnd() {
        Reader reader = new Reader() {
            private int readCount = 0;
            @Override
            public int read(char[] cbuf, int off, int len) throws IOException {
                if (readCount < 2) {
                    readCount++;
                    return 1;
                }
                return -1;
            }

            @Override
            public void close() throws IOException {
            }
        };
        try {
            long toSkip = 10;
            long skipped = reader.skip(toSkip);
            if (skipped <= toSkip) {
                System.out.println("PASS: skip(" + toSkip + ") skipped " + skipped + " which is <= requested");
            } else {
                System.out.println("FAIL: skip(" + toSkip + ") skipped more than requested: " + skipped);
            }
        } catch (Exception e) {
            System.out.println("FAIL: Exception during skip beyond end: " + e);
        }
    }
}

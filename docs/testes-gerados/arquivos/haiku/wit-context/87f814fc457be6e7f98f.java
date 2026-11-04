/*
 * @test
 * @summary Tests LineNumberReader.skip(long) for negative argument rejection, zero skip, partial skip at end of stream and normal skip position.
 * @run main LineNumberReaderWitupTest
 */

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;

public class LineNumberReaderWitupTest {

    public static void main(String[] args) throws IOException {
        testNegativeSkipThrows();
        testZeroSkip();
        testSkipPositionAndReturnValue();
        testSkipBeyondEndOfStream();
    }

    static void testNegativeSkipThrows() throws IOException {
        LineNumberReader r = new LineNumberReader(new StringReader("abc"));
        boolean thrown = false;
        try {
            r.skip(-1L);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("skip(-1) did not throw IllegalArgumentException");
        }
        int c = r.read();
        if (c != 'a') {
            throw new RuntimeException("negative skip consumed input: read returned " + c);
        }
    }

    static void testZeroSkip() throws IOException {
        LineNumberReader r = new LineNumberReader(new StringReader("abc"));
        long n = r.skip(0L);
        if (n != 0L) {
            throw new RuntimeException("skip(0) returned " + n);
        }
        if (r.read() != 'a') {
            throw new RuntimeException("skip(0) changed position");
        }
    }

    static void testSkipPositionAndReturnValue() throws IOException {
        LineNumberReader r = new LineNumberReader(new StringReader("abcdef"));
        long n = r.skip(3L);
        if (n != 3L) {
            throw new RuntimeException("skip(3) returned " + n);
        }
        if (r.read() != 'd') {
            throw new RuntimeException("unexpected character after skip(3)");
        }
    }

    static void testSkipBeyondEndOfStream() throws IOException {
        LineNumberReader r = new LineNumberReader(new StringReader("abc"));
        long n = r.skip(10L);
        if (n != 3L) {
            throw new RuntimeException("skip(10) on 3 chars returned " + n);
        }
        if (r.read() != -1) {
            throw new RuntimeException("expected end of stream after skip");
        }
    }
}

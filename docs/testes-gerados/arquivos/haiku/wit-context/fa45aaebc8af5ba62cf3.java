/*
 * @test
 * @summary Tests PushbackReader.skip(long) for negative, zero, in-range, beyond-end and closed-stream cases
 * @run main PushbackReaderWitupTest
 */

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;

public class PushbackReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testNegativeSkipThrows();
        testZeroSkipReturnsZero();
        testSkipWithinStream();
        testSkipBeyondEnd();
        testSkipAfterCloseThrowsIOException();
    }

    static void testNegativeSkipThrows() throws Exception {
        PushbackReader r = new PushbackReader(new StringReader("abc"), 2);
        try {
            r.skip(-1L);
            throw new RuntimeException("expected IllegalArgumentException for negative skip");
        } catch (IllegalArgumentException expected) {
            // expected
        }
        try {
            r.skip(Long.MIN_VALUE);
            throw new RuntimeException("expected IllegalArgumentException for Long.MIN_VALUE");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testZeroSkipReturnsZero() throws Exception {
        PushbackReader r = new PushbackReader(new StringReader("abc"));
        long skipped = r.skip(0L);
        if (skipped != 0L) {
            throw new RuntimeException("skip(0) returned " + skipped);
        }
        if (r.read() != 'a') {
            throw new RuntimeException("skip(0) consumed characters");
        }
    }

    static void testSkipWithinStream() throws Exception {
        PushbackReader r = new PushbackReader(new StringReader("abcdef"), 4);
        long skipped = r.skip(2L);
        if (skipped != 2L) {
            throw new RuntimeException("expected skip(2) to return 2, got " + skipped);
        }
        if (r.read() != 'c') {
            throw new RuntimeException("expected next char 'c' after skip(2)");
        }
    }

    static void testSkipBeyondEnd() throws Exception {
        PushbackReader r = new PushbackReader(new StringReader("abc"), 2);
        long skipped = r.skip(10L);
        if (skipped != 3L) {
            throw new RuntimeException("expected skip(10) on 3 chars to return 3, got " + skipped);
        }
        if (r.read() != -1) {
            throw new RuntimeException("expected end of stream after skipping past end");
        }
    }

    static void testSkipAfterCloseThrowsIOException() throws Exception {
        PushbackReader r = new PushbackReader(new StringReader("abc"));
        r.close();
        try {
            r.skip(1L);
            throw new RuntimeException("expected IOException for skip on closed stream");
        } catch (IOException expected) {
            // expected
        }
    }
}

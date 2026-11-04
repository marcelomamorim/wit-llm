/*
 * @test
 * @summary Verifies PushbackReader.skip(long) behavior for pushback buffer, underlying reader, negative values and closed streams
 * @run main PushbackReaderWitupTest
 */

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;

public class PushbackReaderWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSkipZeroReturnsZero() throws IOException {
        PushbackReader r = new PushbackReader(new StringReader("abc"));
        long skipped = r.skip(0L);
        check(skipped == 0L, "skip(0) should return 0, got " + skipped);
        check(r.read() == 'a', "skip(0) should not consume characters");
    }

    static void testNegativeSkipThrows() throws IOException {
        PushbackReader r = new PushbackReader(new StringReader("abc"));
        boolean thrown = false;
        try {
            r.skip(-1L);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "skip(-1) should throw IllegalArgumentException");
    }

    static void testSkipFromUnderlyingReader() throws IOException {
        PushbackReader r = new PushbackReader(new StringReader("abcdef"), 4);
        long skipped = r.skip(3L);
        check(skipped == 3L, "skip(3) should return 3, got " + skipped);
        check(r.read() == 'd', "after skip(3) next char should be 'd'");
    }

    static void testSkipWithinPushbackBuffer() throws IOException {
        PushbackReader r = new PushbackReader(new StringReader("abc"));
        r.unread('Z');
        long skipped = r.skip(1L);
        check(skipped == 1L, "skip(1) over pushed char should return 1, got " + skipped);
        check(r.read() == 'a', "after skipping pushed char next char should be 'a'");
    }

    static void testSkipExceedingPushbackBuffer() throws IOException {
        PushbackReader r = new PushbackReader(new StringReader("abcdef"), 2);
        r.unread(new char[] {'x', 'y'});
        long skipped = r.skip(5L);
        check(skipped == 5L, "skip(5) should return 5, got " + skipped);
        check(r.read() == 'd', "after skip(5) next char should be 'd'");
    }

    static void testSkipPastEndOfStream() throws IOException {
        PushbackReader r = new PushbackReader(new StringReader("ab"), 2);
        long skipped = r.skip(10L);
        check(skipped == 2L, "skip past EOF should return available count 2, got " + skipped);
        check(r.read() == -1, "after skipping to EOF read should return -1");
    }

    static void testSkipOnClosedStreamThrows() throws IOException {
        PushbackReader r = new PushbackReader(new StringReader("abc"));
        r.close();
        boolean thrown = false;
        try {
            r.skip(1L);
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "skip on closed stream should throw IOException");
    }

    public static void main(String[] args) throws IOException {
        testSkipZeroReturnsZero();
        testNegativeSkipThrows();
        testSkipFromUnderlyingReader();
        testSkipWithinPushbackBuffer();
        testSkipExceedingPushbackBuffer();
        testSkipPastEndOfStream();
        testSkipOnClosedStreamThrows();
    }
}

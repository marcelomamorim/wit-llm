/*
 * @test
 * @summary Test java.io.PushbackReader.skip(long) method behavior
 * @run main PushbackReaderWitupTest
 */

import java.io.PushbackReader;
import java.io.StringReader;
import java.io.IOException;

public class PushbackReaderWitupTest {
    public static void main(String[] args) throws Exception {
        testSkipNegative();
        testSkipZero();
        testSkipLessThanBuffer();
        testSkipMoreThanBuffer();
        testSkipWithEmptyBuffer();
    }

    private static void testSkipNegative() {
        try {
            PushbackReader pr = new PushbackReader(new StringReader("abc"), 2);
            pr.skip(-1L);
            throw new RuntimeException("Expected IllegalArgumentException for negative skip value");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }

    private static void testSkipZero() throws IOException {
        PushbackReader pr = new PushbackReader(new StringReader("abc"), 2);
        long skipped = pr.skip(0L);
        if (skipped != 0L) {
            throw new RuntimeException("Expected skip(0) to return 0 but got " + skipped);
        }
    }

    private static void testSkipLessThanBuffer() throws IOException {
        // Push back one char to fill buffer partially
        PushbackReader pr = new PushbackReader(new StringReader("abc"), 3);
        int ch = pr.read(); // read 'a'
        pr.unread(ch); // push back 'a', pos moves back
        long skipped = pr.skip(1L); // skip 1 char from buffer
        if (skipped != 1L) {
            throw new RuntimeException("Expected skip(1) to return 1 but got " + skipped);
        }
        // Next read should return 'b'
        int next = pr.read();
        if (next != 'b') {
            throw new RuntimeException("Expected next char to be 'b' but got " + (char)next);
        }
    }

    private static void testSkipMoreThanBuffer() throws IOException {
        // Push back two chars to fill buffer partially
        PushbackReader pr = new PushbackReader(new StringReader("abcde"), 3);
        int ch1 = pr.read(); // 'a'
        int ch2 = pr.read(); // 'b'
        pr.unread(new char[] {(char)ch1, (char)ch2}, 0, 2); // push back 'a','b'
        long skipped = pr.skip(3L); // skip more than buffer (2 chars) + 1 from underlying
        if (skipped < 3L) {
            throw new RuntimeException("Expected skip >= 3 but got " + skipped);
        }
        // Next read should be 'd' (since 'a','b' skipped, 'c' skipped from underlying, next is 'd')
        int next = pr.read();
        if (next != 'd') {
            throw new RuntimeException("Expected next char to be 'd' but got " + (char)next);
        }
    }

    private static void testSkipWithEmptyBuffer() throws IOException {
        PushbackReader pr = new PushbackReader(new StringReader("xyz"), 2);
        long skipped = pr.skip(2L);
        if (skipped != 2L) {
            throw new RuntimeException("Expected skip(2) to return 2 but got " + skipped);
        }
        int next = pr.read();
        if (next != 'z') {
            throw new RuntimeException("Expected next char to be 'z' but got " + (char)next);
        }
    }
}
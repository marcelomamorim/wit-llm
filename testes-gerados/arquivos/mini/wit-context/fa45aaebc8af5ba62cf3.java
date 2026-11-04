/*
 * @test
 * @summary Deterministic tests for java.io.PushbackReader.skip(long) method
 * @run main PushbackReaderWitupTest
 */

import java.io.PushbackReader;
import java.io.StringReader;
import java.io.IOException;

public class PushbackReaderWitupTest {
    public static void main(String[] args) throws Exception {
        testSkipNegativeThrows();
        testSkipZeroAndPositive();
        testSkipWithPushbackBuffer();
    }

    private static void testSkipNegativeThrows() {
        PushbackReader pr = new PushbackReader(new StringReader("abc"));
        try {
            pr.skip(-1L);
            throw new RuntimeException("Expected IllegalArgumentException for negative skip value");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }

    private static void testSkipZeroAndPositive() throws IOException {
        StringReader sr = new StringReader("abcdef");
        PushbackReader pr = new PushbackReader(sr, 3);

        // skip zero should return 0 without error
        long skipped = pr.skip(0L);
        if (skipped != 0L) {
            throw new RuntimeException("skip(0) should return 0 but returned " + skipped);
        }

        // skip positive less than buffer length
        skipped = pr.skip(2L);
        if (skipped != 2L) {
            throw new RuntimeException("skip(2) should return 2 but returned " + skipped);
        }

        // read next char should be the 3rd char in the stream
        int ch = pr.read();
        if (ch != 'c') {
            throw new RuntimeException("Expected 'c' after skipping 2 chars, got: " + (char) ch);
        }
    }

    private static void testSkipWithPushbackBuffer() throws IOException {
        StringReader sr = new StringReader("xyz");
        PushbackReader pr = new PushbackReader(sr, 2);

        // Push back two chars
        pr.unread('a');
        pr.unread('b');

        // skip 1 should skip one char from pushback buffer
        long skipped = pr.skip(1L);
        if (skipped != 1L) {
            throw new RuntimeException("skip(1) should return 1 but returned " + skipped);
        }

        // next read should return the second pushed back char
        int ch = pr.read();
        if (ch != 'a') {
            throw new RuntimeException("Expected 'a' after skipping 1 char from pushback buffer, got: " + (char) ch);
        }

        // skip more than available in pushback buffer plus underlying stream
        skipped = pr.skip(10L);
        if (skipped < 0) {
            throw new RuntimeException("skip(10) should not return negative value");
        }
    }
}

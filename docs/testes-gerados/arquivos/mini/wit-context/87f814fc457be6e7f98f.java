/*
 * @test
 * @summary Test skip(long) method of java.io.LineNumberReader for valid and invalid inputs
 * @run main LineNumberReaderWitupTest
 */

import java.io.LineNumberReader;
import java.io.StringReader;
import java.io.IOException;

public class LineNumberReaderWitupTest {
    public static void main(String[] args) throws IOException {
        testSkipNegative();
        testSkipZero();
        testSkipPositive();
    }

    private static void testSkipNegative() {
        LineNumberReader lnr = new LineNumberReader(new StringReader("abc\ndef"));
        try {
            lnr.skip(-1L);
            throw new RuntimeException("Expected IllegalArgumentException for negative skip value");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }

    private static void testSkipZero() throws IOException {
        LineNumberReader lnr = new LineNumberReader(new StringReader("abc\ndef"));
        long skipped = lnr.skip(0L);
        if (skipped != 0L) {
            throw new RuntimeException("Expected skip(0) to return 0 but got " + skipped);
        }
    }

    private static void testSkipPositive() throws IOException {
        String content = "abc\ndef\nghi";
        LineNumberReader lnr = new LineNumberReader(new StringReader(content));
        long toSkip = 4L; // skip 'abc\n'
        long skipped = lnr.skip(toSkip);
        if (skipped != toSkip) {
            throw new RuntimeException("Expected to skip " + toSkip + " chars but skipped " + skipped);
        }
        int c = lnr.read();
        if (c != 'd') {
            throw new RuntimeException("Expected next char to be 'd' after skipping, but got '" + (char)c + "'");
        }
    }
}

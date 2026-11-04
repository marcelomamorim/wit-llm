/*
 * @test
 * @summary Test java.io.LineNumberReader.skip(long) method behavior
 * @run main LineNumberReaderWitupTest
 */

import java.io.LineNumberReader;
import java.io.StringReader;
import java.io.IOException;

public class LineNumberReaderWitupTest {
    public static void main(String[] args) throws IOException {
        testSkipPositive();
        testSkipZero();
        testSkipNegative();
    }

    private static void testSkipPositive() throws IOException {
        String data = "abcdefg";
        LineNumberReader lnr = new LineNumberReader(new StringReader(data));
        long skipped = lnr.skip(3);
        if (skipped != 3) {
            throw new RuntimeException("Expected to skip 3 chars, but skipped " + skipped);
        }
        int c = lnr.read();
        if (c != 'd') {
            throw new RuntimeException("Expected next char 'd' after skipping 3 chars, but got '" + (char)c + "'");
        }
        lnr.close();
    }

    private static void testSkipZero() throws IOException {
        String data = "xyz";
        LineNumberReader lnr = new LineNumberReader(new StringReader(data));
        long skipped = lnr.skip(0);
        if (skipped != 0) {
            throw new RuntimeException("Expected to skip 0 chars, but skipped " + skipped);
        }
        int c = lnr.read();
        if (c != 'x') {
            throw new RuntimeException("Expected next char 'x' after skipping 0 chars, but got '" + (char)c + "'");
        }
        lnr.close();
    }

    private static void testSkipNegative() {
        String data = "abc";
        LineNumberReader lnr = new LineNumberReader(new StringReader(data));
        try {
            lnr.skip(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative skip value");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }
}

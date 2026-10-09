/*
 * @test
 * @summary Test BufferedReader.mark(int) for valid and invalid inputs
 * @run main BufferedReaderWitupTest_Pe822f3bcb15b
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class BufferedReaderWitupTest_Pe822f3bcb15b {
    public static void main(String[] args) throws Exception {
        testMarkWithNegativeReadAheadLimit();
        testMarkWithValidReadAheadLimit();
    }

    private static void testMarkWithNegativeReadAheadLimit() {
        Reader r = new StringReader("test");
        BufferedReader br = new BufferedReader(r);
        try {
            br.mark(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative readAheadLimit");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }

    private static void testMarkWithValidReadAheadLimit() throws IOException {
        Reader r = new StringReader("test");
        BufferedReader br = new BufferedReader(r);
        // Use a valid readAheadLimit (0 and positive)
        br.mark(0);
        br.mark(10);
        // No exception expected
    }
}

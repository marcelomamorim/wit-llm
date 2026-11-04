/*
 * @test
 * @summary Test java.io.StringReader.mark(int) for valid and invalid inputs
 * @run main StringReaderWitupTest
 */

import java.io.StringReader;
import java.io.IOException;

public class StringReaderWitupTest {
    public static void main(String[] args) throws Exception {
        testMarkWithNegativeReadAheadLimit();
        testMarkWithValidReadAheadLimit();
    }

    private static void testMarkWithNegativeReadAheadLimit() {
        StringReader sr = new StringReader("abc");
        try {
            sr.mark(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative readAheadLimit");
        } catch (IllegalArgumentException e) {
            // Expected exception
            if (!"Read-ahead limit < 0".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }

    private static void testMarkWithValidReadAheadLimit() throws IOException {
        StringReader sr = new StringReader("abc");
        sr.mark(0); // zero is valid
        int ch = sr.read();
        if (ch != 'a') {
            throw new RuntimeException("Expected first character 'a', got: " + (char) ch);
        }
        sr.mark(10); // positive value
        ch = sr.read();
        if (ch != 'b') {
            throw new RuntimeException("Expected second character 'b', got: " + (char) ch);
        }
    }
}

/*
 * @test
 * @summary Test java.io.StringReader.mark(int) method behavior
 * @run main StringReaderWitupTest
 */

import java.io.StringReader;
import java.io.IOException;

public class StringReaderWitupTest {
    public static void main(String[] args) throws Exception {
        testMarkWithValidReadAheadLimit();
        testMarkWithZeroReadAheadLimit();
        testMarkWithNegativeReadAheadLimit();
    }

    private static void testMarkWithValidReadAheadLimit() throws IOException {
        StringReader sr = new StringReader("abc");
        int firstChar = sr.read();
        if (firstChar != 'a') {
            throw new RuntimeException("Expected first char 'a', got: " + (char)firstChar);
        }
        sr.mark(10); // valid positive readAheadLimit
        int secondChar = sr.read();
        if (secondChar != 'b') {
            throw new RuntimeException("Expected second char 'b', got: " + (char)secondChar);
        }
    }

    private static void testMarkWithZeroReadAheadLimit() throws IOException {
        StringReader sr = new StringReader("xyz");
        sr.mark(0); // zero is allowed
        int ch = sr.read();
        if (ch != 'x') {
            throw new RuntimeException("Expected char 'x', got: " + (char)ch);
        }
    }

    private static void testMarkWithNegativeReadAheadLimit() {
        StringReader sr = new StringReader("test");
        try {
            sr.mark(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative readAheadLimit");
        } catch (IllegalArgumentException iae) {
            String msg = iae.getMessage();
            if (msg == null || !msg.contains("Read-ahead limit < 0")) {
                throw new RuntimeException("IllegalArgumentException message unexpected: " + msg);
            }
        } catch (IOException ioe) {
            throw new RuntimeException("Unexpected IOException thrown", ioe);
        }
    }
}

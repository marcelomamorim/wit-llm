/*
 * @test
 * @summary Test PushbackReader constructor with invalid and valid buffer sizes
 * @run main PushbackReaderWitupTest
 */

import java.io.PushbackReader;
import java.io.StringReader;
import java.io.IOException;

public class PushbackReaderWitupTest {
    public static void main(String[] args) throws IOException {
        testConstructorWithInvalidSize();
        testConstructorWithValidSize();
    }

    private static void testConstructorWithInvalidSize() {
        StringReader sr = new StringReader("");
        try {
            new PushbackReader(sr, 0);
            throw new RuntimeException("Expected IllegalArgumentException for size=0");
        } catch (IllegalArgumentException e) {
            if (!"size <= 0".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
        try {
            new PushbackReader(sr, -1);
            throw new RuntimeException("Expected IllegalArgumentException for size=-1");
        } catch (IllegalArgumentException e) {
            if (!"size <= 0".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testConstructorWithValidSize() throws IOException {
        StringReader sr = new StringReader("abc");
        PushbackReader pr = new PushbackReader(sr, 2);
        int ch = pr.read();
        if (ch != 'a') {
            throw new RuntimeException("Expected first character 'a', got: " + (char) ch);
        }
        pr.close();
    }
}

/*
 * @test
 * @summary Test PushbackReader constructor with various buffer sizes
 * @run main PushbackReaderWitupTest
 */

import java.io.PushbackReader;
import java.io.StringReader;

public class PushbackReaderWitupTest {
    public static void main(String[] args) throws Exception {
        testValidSize();
        testInvalidSizeZero();
        testInvalidSizeNegative();
    }

    private static void testValidSize() throws Exception {
        StringReader sr = new StringReader("abc");
        PushbackReader pr = new PushbackReader(sr, 5);
        if (pr == null) {
            throw new RuntimeException("PushbackReader instance is null");
        }
        // Read should return -1 since no data pushed back and underlying reader is empty after reading
        int ch = pr.read();
        // 'a' is first char, so ch should be 'a' (97)
        if (ch != 'a') {
            throw new RuntimeException("Expected 'a' but got " + ch);
        }
        pr.close();
    }

    private static void testInvalidSizeZero() {
        try {
            new PushbackReader(new StringReader(""), 0);
            throw new RuntimeException("Expected IllegalArgumentException for size=0");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for size=0", e);
        }
    }

    private static void testInvalidSizeNegative() {
        try {
            new PushbackReader(new StringReader(""), -1);
            throw new RuntimeException("Expected IllegalArgumentException for size=-1");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for size=-1", e);
        }
    }
}

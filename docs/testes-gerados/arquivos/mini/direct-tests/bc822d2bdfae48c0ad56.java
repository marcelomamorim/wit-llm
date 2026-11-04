/*
 * @test
 * @summary Test String constructor with byte array, offset, length and Charset
 * @run main StringWitupTest
 */

import java.lang.String;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StringWitupTest {
    public static void main(String[] args) {
        testNullCharsetThrows();
        testValidDecode();
        testOffsetLengthBounds();
    }

    private static void testNullCharsetThrows() {
        byte[] bytes = {65, 66, 67};
        try {
            new String(bytes, 0, 3, null);
            throw new RuntimeException("Expected NullPointerException for null charset");
        } catch (NullPointerException e) {
            if (!"charset".equals(e.getMessage())) {
                throw new RuntimeException("NullPointerException message mismatch", e);
            }
        }
    }

    private static void testValidDecode() {
        byte[] bytes = {72, 101, 108, 108, 111}; // ASCII for "Hello"
        String s = new String(bytes, 0, bytes.length, StandardCharsets.US_ASCII);
        if (!"Hello".equals(s)) {
            throw new RuntimeException("Decoded string mismatch: expected 'Hello' but got '" + s + "'");
        }
    }

    private static void testOffsetLengthBounds() {
        byte[] bytes = {65, 66, 67, 68, 69};
        // offset + length > bytes.length should throw IndexOutOfBoundsException
        try {
            new String(bytes, 3, 3, StandardCharsets.US_ASCII);
            throw new RuntimeException("Expected IndexOutOfBoundsException for invalid offset and length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        // negative offset
        try {
            new String(bytes, -1, 2, StandardCharsets.US_ASCII);
            throw new RuntimeException("Expected IndexOutOfBoundsException for negative offset");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        // negative length
        try {
            new String(bytes, 1, -1, StandardCharsets.US_ASCII);
            throw new RuntimeException("Expected IndexOutOfBoundsException for negative length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}

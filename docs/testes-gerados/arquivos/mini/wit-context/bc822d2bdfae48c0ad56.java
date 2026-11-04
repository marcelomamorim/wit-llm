/*
 * @test
 * @summary Test String constructor String(byte[], int, int, Charset) for exception and normal cases
 * @run main StringWitupTest
 */

import java.lang.String;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StringWitupTest {
    public static void main(String[] args) {
        testNullCharsetThrows();
        testInvalidOffsetLengthThrows();
        testValidConstruction();
    }

    private static void testNullCharsetThrows() {
        byte[] bytes = {65, 66, 67};
        try {
            new String(bytes, 0, 3, null);
            throw new RuntimeException("Expected NullPointerException for null charset");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testInvalidOffsetLengthThrows() {
        byte[] bytes = {65, 66, 67};
        Charset charset = StandardCharsets.UTF_8;
        // offset negative
        try {
            new String(bytes, -1, 2, charset);
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
        // length negative
        try {
            new String(bytes, 0, -1, charset);
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for negative length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
        // offset + length > bytes.length
        try {
            new String(bytes, 2, 2, charset);
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for offset+length > bytes.length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testValidConstruction() {
        byte[] bytes = {72, 101, 108, 108, 111}; // "Hello" in ASCII
        Charset charset = StandardCharsets.US_ASCII;
        String s = new String(bytes, 0, bytes.length, charset);
        if (!"Hello".equals(s)) {
            throw new RuntimeException("String content mismatch: expected 'Hello' but got '" + s + "'");
        }
    }
}

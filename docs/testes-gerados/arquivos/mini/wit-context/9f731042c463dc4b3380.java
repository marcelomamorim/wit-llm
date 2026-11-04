/*
 * @test
 * @summary Test String constructor String(byte[], Charset) for null Charset and normal usage
 * @run main StringWitupTest
 */

import java.lang.String;
import java.nio.charset.Charset;

public class StringWitupTest {
    public static void main(String[] args) {
        testNullCharset();
        testValidCharset();
    }

    private static void testNullCharset() {
        byte[] bytes = {65, 66, 67}; // ASCII for "ABC"
        Charset charset = null;
        try {
            new String(bytes, charset);
            throw new RuntimeException("Expected NullPointerException for null Charset");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testValidCharset() {
        byte[] bytes = {65, 66, 67}; // ASCII for "ABC"
        Charset charset = Charset.forName("US-ASCII");
        String s = new String(bytes, charset);
        if (!"ABC".equals(s)) {
            throw new RuntimeException("String content mismatch: expected 'ABC' but got '" + s + "'");
        }
    }
}

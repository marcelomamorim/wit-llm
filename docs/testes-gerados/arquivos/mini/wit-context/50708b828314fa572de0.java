/*
 * @test
 * @summary Test java.lang.String.getBytes(Charset) for null and valid Charset
 * @run main StringWitupTest
 */

import java.lang.String;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StringWitupTest {
    public static void main(String[] args) {
        testGetBytesWithNullCharset();
        testGetBytesWithValidCharset();
    }

    private static void testGetBytesWithNullCharset() {
        String s = "test";
        try {
            s.getBytes(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testGetBytesWithValidCharset() {
        String s = "abc";
        Charset charset = StandardCharsets.UTF_8;
        byte[] bytes = s.getBytes(charset);
        if (bytes == null || bytes.length != 3) {
            throw new RuntimeException("getBytes returned invalid byte array");
        }
        // Check content matches UTF-8 encoding of "abc"
        if (bytes[0] != 'a' || bytes[1] != 'b' || bytes[2] != 'c') {
            throw new RuntimeException("getBytes returned incorrect bytes");
        }
    }
}

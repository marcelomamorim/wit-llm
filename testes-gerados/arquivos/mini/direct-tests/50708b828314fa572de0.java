/*
 * @test
 * @summary Test String.getBytes(Charset) method behavior
 * @run main StringWitupTest
 */

import java.lang.String;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StringWitupTest {
    public static void main(String[] args) throws Exception {
        testGetBytesWithValidCharset();
        testGetBytesWithNullCharset();
    }

    private static void testGetBytesWithValidCharset() {
        String s = "abc";
        Charset utf8 = StandardCharsets.UTF_8;
        byte[] bytes = s.getBytes(utf8);
        // Expected UTF-8 bytes for "abc"
        byte[] expected = new byte[] {97, 98, 99};
        if (bytes.length != expected.length) {
            throw new RuntimeException("Length mismatch: expected " + expected.length + " but got " + bytes.length);
        }
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] != expected[i]) {
                throw new RuntimeException("Byte mismatch at index " + i + ": expected " + expected[i] + " but got " + bytes[i]);
            }
        }
    }

    private static void testGetBytesWithNullCharset() {
        String s = "abc";
        try {
            s.getBytes(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}

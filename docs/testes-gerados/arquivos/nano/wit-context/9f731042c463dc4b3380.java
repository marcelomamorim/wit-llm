/*
 * @test    @summary Test for String(byte[], Charset) constructor
 * @run main StringWitupTest
 */

import java.lang.String;
import java.nio.charset.Charset;

public class StringWitupTest {

    public static void main(String[] args) {
        testNullCharset();
        testValidBytesAndCharset();
    }

    public static void testNullCharset() {
        byte[] data = {65, 66, 67}; // "ABC"
        try {
            String s = new String(data, (Charset) null);
            // Expecting NullPointerException
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Test passes
        }
    }

    public static void testValidBytesAndCharset() {
        byte[] data = {65, 66, 67}; // "ABC"
        Charset charset = Charset.forName("US-ASCII");
        String s = new String(data, charset);
        if (!"ABC".equals(s)) {
            System.err.println("Unexpected string value: " + s);
            System.exit(1);
        }
    }
}

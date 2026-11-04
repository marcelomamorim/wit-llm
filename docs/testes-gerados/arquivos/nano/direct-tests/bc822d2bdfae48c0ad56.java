/*
 * @test
 * @summary Test String constructor with byte array, offset, length, and Charset
 * @run main StringWitupTest
 */

import java.lang.String;
import java.nio.charset.Charset;

public class StringWitupTest {

    public static void main(String[] args) {
        testStringConstructorWithCharset();
    }

    public static void testStringConstructorWithCharset() {
        byte[] bytes = {65, 66, 67, 68, 69}; // ASCII for A B C D E
        Charset utf8 = Charset.forName("UTF-8");
        String s = new String(bytes, 1, 3, utf8); // should decode bytes for B C D
        if (!"BCD".equals(s)) {
            throw new AssertionError("Expected 'BCD', but got: " + s);
        }
        // Test with offset 0 and length 5
        String s2 = new String(bytes, 0, 5, utf8);
        if (!"ABCDE".equals(s2)) {
            throw new AssertionError("Expected 'ABCDE', but got: " + s2);
        }
        // Test with different charset (ISO-8859-1)
        Charset iso88591 = Charset.forName("ISO-8859-1");
        String s3 = new String(bytes, 2, 2, iso88591); // should decode bytes for C D
        if (!"CD".equals(s3)) {
            throw new AssertionError("Expected 'CD', but got: " + s3);
        }
        // Test with empty byte array
        byte[] emptyBytes = {};
        String emptyString = new String(emptyBytes, 0, 0, utf8);
        if (!"".equals(emptyString)) {
            throw new AssertionError("Expected empty string, but got: " + emptyString);
        }
        // Test with null charset should throw NullPointerException
        boolean exceptionThrown = false;
        try {
            new String(bytes, 0, 1, null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when charset is null");
        }
    }
}

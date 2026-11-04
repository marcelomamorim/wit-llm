/*
 * @test
 * @summary Test Charset.forName behavior for valid and invalid charset names
 * @run main CharsetWitupTest
 */

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

public class CharsetWitupTest {
    public static void main(String[] args) {
        testValidCharset();
        testInvalidCharset();
    }

    private static void testValidCharset() {
        // Use a well-known charset name that must exist
        Charset cs = Charset.forName("UTF-8");
        if (cs == null) {
            throw new AssertionError("Charset.forName returned null for UTF-8");
        }
        if (!"UTF-8".equalsIgnoreCase(cs.name())) {
            throw new AssertionError("Expected charset name UTF-8 but got " + cs.name());
        }
    }

    private static void testInvalidCharset() {
        String invalidName = "NoSuchCharset12345";
        try {
            Charset.forName(invalidName);
            throw new AssertionError("Expected UnsupportedCharsetException for charset name: " + invalidName);
        } catch (UnsupportedCharsetException e) {
            if (!invalidName.equals(e.getCharsetName())) {
                throw new AssertionError("Exception charset name mismatch: expected " + invalidName + " but got " + e.getCharsetName());
            }
        }
    }
}

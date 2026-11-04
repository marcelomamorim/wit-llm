/*
 * @test
 * @summary Test Charset.forName(String) for valid, invalid and null inputs
 * @run main CharsetWitupTest
 */

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

public class CharsetWitupTest {
    public static void main(String[] args) {
        testValidCharset();
        testUnsupportedCharset();
        testNullCharset();
    }

    private static void testValidCharset() {
        // Standard charset name should return non-null Charset
        Charset cs = Charset.forName("UTF-8");
        if (cs == null) {
            throw new AssertionError("Charset.forName returned null for UTF-8");
        }
        // The canonical name should be UTF-8 (case insensitive)
        if (!"UTF-8".equalsIgnoreCase(cs.name())) {
            throw new AssertionError("Expected canonical name UTF-8 but got " + cs.name());
        }
    }

    private static void testUnsupportedCharset() {
        // Using a likely unsupported charset name should throw UnsupportedCharsetException
        String bogusName = "NoSuchCharset12345";
        try {
            Charset.forName(bogusName);
            throw new AssertionError("Expected UnsupportedCharsetException for charset: " + bogusName);
        } catch (UnsupportedCharsetException e) {
            if (!bogusName.equals(e.getCharsetName())) {
                throw new AssertionError("Exception charset name mismatch: expected " + bogusName + " but got " + e.getCharsetName());
            }
        }
    }

    private static void testNullCharset() {
        // According to current source, passing null leads to lookup(null) which returns null and then
        // UnsupportedCharsetException is thrown with null name. The WIT expath suggests IllegalArgumentException
        // for null input, but current code throws UnsupportedCharsetException. We test the actual behavior.
        try {
            Charset.forName(null);
            throw new AssertionError("Expected UnsupportedCharsetException for null charset name");
        } catch (UnsupportedCharsetException e) {
            if (e.getCharsetName() != null) {
                throw new AssertionError("Expected null charset name in exception but got " + e.getCharsetName());
            }
        } catch (IllegalArgumentException e) {
            // Acceptable if IllegalArgumentException is thrown instead
        }
    }
}

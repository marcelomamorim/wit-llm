/*
 * @test
 * @summary Test for java.nio.charset.Charset.forName(String)
 * @run main CharsetWitupTest
 */

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

public class CharsetWitupTest {

    public static void main(String[] args) {
        testValidCharsetName();
        testNullCharsetName();
        testInvalidCharsetName();
    }

    private static void testValidCharsetName() {
        String charsetName = "UTF-8";
        try {
            Charset cs = Charset.forName(charsetName);
            if (cs == null || !cs.name().equalsIgnoreCase(charsetName)) {
                throw new AssertionError("Expected charset for name: " + charsetName);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid charset name", e);
        }
    }

    private static void testNullCharsetName() {
        try {
            Charset.forName(null);
            throw new AssertionError("Expected NullPointerException for null charset name");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type for null charset name", e);
        }
    }

    private static void testInvalidCharsetName() {
        String invalidName = "InvalidCharsetName123";
        try {
            Charset.forName(invalidName);
            throw new AssertionError("Expected UnsupportedCharsetException for invalid charset name");
        } catch (UnsupportedCharsetException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type for invalid charset name", e);
        }
    }
}

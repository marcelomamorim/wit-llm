/*
 * @test
 * @summary Test java.nio.charset.Charset.forName(String) for supported and unsupported charsets
 * @run main CharsetWitupTest
 */

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

public class CharsetWitupTest {

    public static void main(String[] args) {
        testSupportedCharset("UTF-8");
        testSupportedCharset("ISO-8859-1");
        testSupportedCharset("US-ASCII");
        testUnsupportedCharset("NON-EXISTENT-CHARSET");
        testUnsupportedCharset("");
        testUnsupportedCharset(null);
    }

    static void testSupportedCharset(String charsetName) {
        try {
            Charset cs = Charset.forName(charsetName);
            if (cs == null) {
                throw new AssertionError("Expected supported charset for: " + charsetName);
            }
            if (!cs.name().equalsIgnoreCase(charsetName)) {
                // Some charsets may have different canonical names, but for test, check case-insensitive match
                System.out.println("Supported charset: " + cs.name());
            }
        } catch (Exception e) {
            throw new AssertionError("Failed to get supported charset: " + charsetName, e);
        }
    }

    static void testUnsupportedCharset(String charsetName) {
        try {
            Charset.forName(charsetName);
            throw new AssertionError("Expected UnsupportedCharsetException for: " + charsetName);
        } catch (UnsupportedCharsetException e) {
            // Expected exception
        } catch (NullPointerException e) {
            if (charsetName != null) {
                throw new AssertionError("Unexpected NullPointerException for: " + charsetName, e);
            }
            // Null input, acceptable to throw NullPointerException
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for: " + charsetName, e);
        }
    }
}

/*
 * @test
 * @summary Test javax.net.ssl.SNIHostName.createSNIMatcher(String) for exception and normal behavior
 * @run main SNIHostNameWitupTest
 */

import javax.net.ssl.SNIHostName;
import javax.net.ssl.SNIMatcher;

public class SNIHostNameWitupTest {
    public static void main(String[] args) {
        testNullRegexThrowsNPE();
        testValidRegexReturnsMatcher();
        testInvalidRegexThrowsIAE();
    }

    private static void testNullRegexThrowsNPE() {
        try {
            SNIHostName.createSNIMatcher(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testValidRegexReturnsMatcher() {
        String regex = "^example\\.com$";
        SNIMatcher matcher = SNIHostName.createSNIMatcher(regex);
        if (matcher == null) {
            throw new RuntimeException("createSNIMatcher returned null for valid regex");
        }
    }

    private static void testInvalidRegexThrowsIAE() {
        // The invalid regex here is a pattern with an unclosed character class
        String invalidRegex = "[unclosed";
        try {
            SNIHostName.createSNIMatcher(invalidRegex);
            throw new RuntimeException("Expected IllegalArgumentException not thrown for invalid regex");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}

/*
 * @test
 * @summary Witup-generated checks for SNIHostName.createSNIMatcher exception and matching behavior
 * @run main SNIHostNameWitupTest
 */

import javax.net.ssl.SNIHostName;
import javax.net.ssl.SNIMatcher;
import javax.net.ssl.StandardConstants;

public class SNIHostNameWitupTest {

    public static void main(String[] args) {
        testNullRegexThrowsNPE();
        testValidRegexReturnsHostNameMatcher();
        testMatchingAndNonMatchingHostnames();
        testInvalidRegexIsRejected();
        System.out.println("PASSED");
    }

    static void testNullRegexThrowsNPE() {
        try {
            SNIHostName.createSNIMatcher(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null regex");
    }

    static void testValidRegexReturnsHostNameMatcher() {
        SNIMatcher matcher = SNIHostName.createSNIMatcher("www[.]example[.]com");
        if (matcher == null) {
            throw new RuntimeException("createSNIMatcher returned null");
        }
        if (matcher.getType() != StandardConstants.SNI_HOST_NAME) {
            throw new RuntimeException("Unexpected matcher type: " + matcher.getType());
        }
    }

    static void testMatchingAndNonMatchingHostnames() {
        SNIMatcher matcher = SNIHostName.createSNIMatcher("www[.]example[.]com");
        if (!matcher.matches(new SNIHostName("www.example.com"))) {
            throw new RuntimeException("Expected www.example.com to match");
        }
        if (matcher.matches(new SNIHostName("mail.example.com"))) {
            throw new RuntimeException("Expected mail.example.com not to match");
        }
    }

    static void testInvalidRegexIsRejected() {
        try {
            SNIHostName.createSNIMatcher("(");
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for invalid regex");
    }
}

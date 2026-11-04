/*
 * @test
 * @summary Test javax.security.auth.x500.X500Principal.getName(String, Map) method
 * @run main javax.security.auth.x500.X500PrincipalWitupTest
 */

import javax.security.auth.x500.X500Principal;
import java.util.HashMap;
import java.util.Map;

public class X500PrincipalWitupTest {
    public static void main(String[] args) throws Exception {
        testNullOidMapThrowsNPE();
        testValidFormatsReturnExpectedName();
        testInvalidFormatThrowsIAE();
    }

    private static void testNullOidMapThrowsNPE() {
        X500Principal p = new X500Principal("CN=Test");
        try {
            p.getName(X500Principal.RFC1779, null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testValidFormatsReturnExpectedName() {
        X500Principal p = new X500Principal("CN=Test");
        Map<String, String> emptyMap = new HashMap<>();

        String name1779 = p.getName(X500Principal.RFC1779, emptyMap);
        if (name1779 == null || name1779.isEmpty()) {
            throw new RuntimeException("getName RFC1779 returned null or empty string");
        }

        String name2253 = p.getName(X500Principal.RFC2253, emptyMap);
        if (name2253 == null || name2253.isEmpty()) {
            throw new RuntimeException("getName RFC2253 returned null or empty string");
        }

        // Case-insensitive check
        String name1779Upper = p.getName("rfc1779", emptyMap);
        if (!name1779Upper.equals(name1779)) {
            throw new RuntimeException("getName RFC1779 case-insensitive check failed");
        }

        String name2253Upper = p.getName("rfc2253", emptyMap);
        if (!name2253Upper.equals(name2253)) {
            throw new RuntimeException("getName RFC2253 case-insensitive check failed");
        }
    }

    private static void testInvalidFormatThrowsIAE() {
        X500Principal p = new X500Principal("CN=Test");
        Map<String, String> emptyMap = new HashMap<>();
        try {
            p.getName("INVALID_FORMAT", emptyMap);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException iae) {
            // expected
        }

        try {
            p.getName(null, emptyMap);
            throw new RuntimeException("Expected IllegalArgumentException not thrown for null format");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}

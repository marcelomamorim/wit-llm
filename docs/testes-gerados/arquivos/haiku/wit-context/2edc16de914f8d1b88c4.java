/*
 * @test
 * @summary Witup-generated tests for X500Principal(String, Map) constructor
 * @run main X500PrincipalWitupTest
 */

import java.util.Collections;
import javax.security.auth.x500.X500Principal;

public class X500PrincipalWitupTest {

    public static void main(String[] args) {
        testNullName();
        testNullKeywordMap();
        testValidName();
        testMalformedName();
    }

    static void testNullName() {
        boolean thrown = false;
        try {
            new X500Principal(null, Collections.<String, String>emptyMap());
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null name");
    }

    static void testNullKeywordMap() {
        boolean thrown = false;
        try {
            new X500Principal("CN=Duke", null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null keywordMap");
    }

    static void testValidName() {
        X500Principal p = new X500Principal("CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US",
                Collections.<String, String>emptyMap());
        String rfc2253 = p.getName(X500Principal.RFC2253);
        check(rfc2253 != null, "getName(RFC2253) returned null");
        check(rfc2253.contains("CN=Duke"), "unexpected RFC2253 name: " + rfc2253);
    }

    static void testMalformedName() {
        boolean thrown = false;
        try {
            new X500Principal("CN", Collections.<String, String>emptyMap());
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "expected IllegalArgumentException for malformed name");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}

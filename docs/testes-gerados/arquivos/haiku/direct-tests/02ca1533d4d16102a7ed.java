/*
 * @test
 * @summary Verify X500Principal.getName(String, Map) argument validation and format handling
 * @run main X500PrincipalWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.security.auth.x500.X500Principal;

public class X500PrincipalWitupTest {

    private static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) {
        X500Principal p = new X500Principal("CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US");
        Map<String, String> oidMap = Collections.emptyMap();

        // null oidMap must throw NullPointerException
        try {
            p.getName(X500Principal.RFC2253, null);
            throw new RuntimeException("expected NullPointerException for null oidMap");
        } catch (NullPointerException expected) {
            // expected
        }

        // null format must throw IllegalArgumentException
        try {
            p.getName(null, oidMap);
            throw new RuntimeException("expected IllegalArgumentException for null format");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // unknown format must throw IllegalArgumentException
        try {
            p.getName("BOGUS", oidMap);
            throw new RuntimeException("expected IllegalArgumentException for unknown format");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // format matching is case-insensitive
        String rfc2253 = p.getName(X500Principal.RFC2253, oidMap);
        check(rfc2253 != null, "RFC2253 name must not be null");
        check(rfc2253.equals(p.getName("rfc2253", oidMap)),
              "lowercase rfc2253 must match RFC2253 output");

        String rfc1779 = p.getName(X500Principal.RFC1779, oidMap);
        check(rfc1779 != null, "RFC1779 name must not be null");
        check(rfc1779.contains("CN=Duke"), "RFC1779 output must contain CN=Duke: " + rfc1779);
        check(rfc2253.contains("CN=Duke"), "RFC2253 output must contain CN=Duke: " + rfc2253);

        // a non-null empty HashMap is accepted
        Map<String, String> emptyHash = new HashMap<>();
        check(rfc2253.equals(p.getName(X500Principal.RFC2253, emptyHash)),
              "HashMap oidMap must give same result as emptyMap");
    }
}

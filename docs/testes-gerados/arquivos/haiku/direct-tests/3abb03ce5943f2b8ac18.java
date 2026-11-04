/*
 * @test
 * @summary Tests the public single-argument X500Principal(String) constructor
 * @run main X500PrincipalWitupTest
 */

import javax.security.auth.x500.X500Principal;

public class X500PrincipalWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        X500Principal p = new X500Principal("CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US");
        check(p != null, "principal should not be null");
        check(p.getName() != null, "getName() should not return null");
        check("CN=Duke,OU=JavaSoft,O=Sun Microsystems,C=US".equals(p.getName()),
              "unexpected RFC2253 name: " + p.getName());
        check("CN=Duke,OU=JavaSoft,O=Sun Microsystems,C=US".equals(p.getName(X500Principal.RFC2253)),
              "unexpected getName(RFC2253): " + p.getName(X500Principal.RFC2253));

        X500Principal same = new X500Principal("CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US");
        check(p.equals(same), "equal DNs should be equal principals");
        check(p.hashCode() == same.hashCode(), "equal principals should have equal hash codes");

        X500Principal other = new X500Principal("cn=duke,ou=javasoft,o=sun microsystems,c=us");
        check(p.equals(other), "canonically equal DNs should be equal principals");

        X500Principal different = new X500Principal("CN=Other, C=US");
        check(!p.equals(different), "different DNs should not be equal");

        try {
            new X500Principal("invalid");
            throw new RuntimeException("expected IllegalArgumentException for malformed DN");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}

/*
 * @test
 * @summary Witup-generated tests for X500Principal.getName(String format)
 * @run main X500PrincipalWitupTest
 */

import javax.security.auth.x500.X500Principal;

public class X500PrincipalWitupTest {

    private static final String DN = "CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US";

    public static void main(String[] args) throws Exception {
        X500Principal p = new X500Principal(DN);

        String rfc2253 = p.getName(X500Principal.RFC2253);
        if (!"CN=Duke,OU=JavaSoft,O=Sun Microsystems,C=US".equals(rfc2253)) {
            throw new RuntimeException("RFC2253 mismatch: " + rfc2253);
        }

        if (!rfc2253.equals(p.getName("rfc2253"))) {
            throw new RuntimeException("RFC2253 format must be case-insensitive");
        }

        String rfc1779 = p.getName(X500Principal.RFC1779);
        if (rfc1779 == null || rfc1779.isEmpty()) {
            throw new RuntimeException("RFC1779 result must not be empty");
        }

        String canonical = p.getName(X500Principal.CANONICAL);
        if (!"cn=duke,ou=javasoft,o=sun microsystems,c=us".equals(canonical)) {
            throw new RuntimeException("CANONICAL mismatch: " + canonical);
        }

        expectIAE(p, null);
        expectIAE(p, "foo");
        expectIAE(p, "");
    }

    private static void expectIAE(X500Principal p, String format) {
        try {
            String result = p.getName(format);
            throw new RuntimeException("Expected IllegalArgumentException for format " + format + " but got " + result);
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}

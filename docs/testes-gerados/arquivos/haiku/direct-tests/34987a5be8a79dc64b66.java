/*
 * @test
 * @summary Verify X500Principal.getName(String) for RFC1779, RFC2253, CANONICAL and invalid formats
 * @run main X500PrincipalWitupTest
 */

import javax.security.auth.x500.X500Principal;

public class X500PrincipalWitupTest {

    static void check(String label, String actual, String expected) {
        if (!expected.equals(actual)) {
            throw new RuntimeException(label + ": expected [" + expected + "] but was [" + actual + "]");
        }
    }

    static void expectIAE(X500Principal p, String format) {
        try {
            String result = p.getName(format);
            throw new RuntimeException("expected IllegalArgumentException for format " + format + " but got " + result);
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    public static void main(String[] args) throws Exception {
        X500Principal p = new X500Principal("CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US");

        check("RFC2253", p.getName(X500Principal.RFC2253), "CN=Duke,OU=JavaSoft,O=Sun Microsystems,C=US");
        check("rfc2253 lowercase", p.getName("rfc2253"), "CN=Duke,OU=JavaSoft,O=Sun Microsystems,C=US");
        check("RFC1779", p.getName(X500Principal.RFC1779), "CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US");
        check("rfc1779 mixed case", p.getName("Rfc1779"), "CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US");
        check("CANONICAL", p.getName(X500Principal.CANONICAL), "cn=duke,ou=javasoft,o=sun microsystems,c=us");
        check("canonical lowercase", p.getName("canonical"), "cn=duke,ou=javasoft,o=sun microsystems,c=us");

        expectIAE(p, null);
        expectIAE(p, "");
        expectIAE(p, "FOO");
        expectIAE(p, "RFC 2253");
    }
}

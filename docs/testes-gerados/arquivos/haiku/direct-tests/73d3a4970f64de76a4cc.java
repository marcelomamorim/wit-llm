/*
 * @test
 * @summary Verifies that X500Principal.getName() returns the RFC 2253 representation
 * @run main X500PrincipalWitupTest
 */

import javax.security.auth.x500.X500Principal;

public class X500PrincipalWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        X500Principal p = new X500Principal("CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US");

        String name = p.getName();
        check("CN=Duke,OU=JavaSoft,O=Sun Microsystems,C=US".equals(name),
              "Unexpected getName(): " + name);

        check(name.equals(p.getName(X500Principal.RFC2253)),
              "getName() must equal getName(RFC2253)");

        X500Principal simple = new X500Principal("CN=Duke");
        check("CN=Duke".equals(simple.getName()),
              "Unexpected getName() for simple DN: " + simple.getName());

        X500Principal compact = new X500Principal("CN=Duke,OU=JavaSoft,O=Sun Microsystems,C=US");
        check(name.equals(compact.getName()),
              "Equivalent DN strings must produce the same getName() result");
    }
}

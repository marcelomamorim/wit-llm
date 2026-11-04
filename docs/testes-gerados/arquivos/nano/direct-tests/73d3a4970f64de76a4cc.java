/* @test    @summary Test getName() method of X500Principal for default RFC2253 format    @run main X500PrincipalWitupTest */

import javax.security.auth.x500.X500Principal;

public class X500PrincipalWitupTest {

    public static void main(String[] args) {
        testGetNameDefaultFormat();
    }

    public static void testGetNameDefaultFormat() {
        String dn = "CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US";
        X500Principal principal = new X500Principal(dn);
        String name = principal.getName();
        if (!name.equals(dn)) {
            throw new AssertionError("Expected: " + dn + ", but got: " + name);
        }
    }
}

/*
 * @test
 * @summary Test X500Principal.getName() returns RFC2253 format string
 * @run main javax.security.auth.x500.X500PrincipalWitupTest
 */

import javax.security.auth.x500.X500Principal;

public class X500PrincipalWitupTest {
    public static void main(String[] args) throws Exception {
        // Construct with a known DN string
        String dn = "CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US";
        X500Principal principal = new X500Principal(dn);

        // getName() should return the DN in RFC2253 format
        String name = principal.getName();

        if (name == null || name.isEmpty()) {
            throw new RuntimeException("getName() returned null or empty string");
        }

        // The returned name should contain the CN component
        if (!name.contains("CN=Duke")) {
            throw new RuntimeException("getName() output does not contain expected CN component: " + name);
        }

        // The returned name should contain the country component
        if (!name.contains("C=US")) {
            throw new RuntimeException("getName() output does not contain expected C component: " + name);
        }

        // Print success message
        System.out.println("Test passed: getName() returned: " + name);
    }
}

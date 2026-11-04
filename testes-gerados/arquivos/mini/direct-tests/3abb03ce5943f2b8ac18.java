/*
 * @test
 * @summary Test X500Principal(String name) constructor with valid and invalid inputs
 * @run main javax.security.auth.x500.X500PrincipalWitupTest
 */

import javax.security.auth.x500.X500Principal;

public class X500PrincipalWitupTest {
    public static void main(String[] args) throws Exception {
        testValidName();
        testEmptyName();
        testNullName();
    }

    private static void testValidName() {
        // Valid distinguished name string
        String dn = "CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US";
        X500Principal p = new X500Principal(dn);
        String name = p.getName();
        if (!name.contains("CN=Duke")) {
            throw new RuntimeException("Expected CN=Duke in name, got: " + name);
        }
    }

    private static void testEmptyName() {
        // Empty string is a valid input, should create a principal with empty name
        X500Principal p = new X500Principal("");
        String name = p.getName();
        if (!"".equals(name)) {
            throw new RuntimeException("Expected empty name, got: " + name);
        }
    }

    private static void testNullName() {
        // Passing null should throw NullPointerException
        try {
            new X500Principal(null);
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}

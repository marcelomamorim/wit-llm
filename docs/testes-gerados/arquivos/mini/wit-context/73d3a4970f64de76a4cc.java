/*
 * @test
 * @summary Test X500Principal.getName() method behavior
 * @run main javax.security.auth.x500.X500PrincipalWitupTest
 */

import javax.security.auth.x500.X500Principal;

public class X500PrincipalWitupTest {
    public static void main(String[] args) throws Exception {
        // Test getName() returns non-null string in RFC2253 format
        X500Principal p = new X500Principal("CN=Test, OU=TestUnit, O=TestOrg, C=US");
        String name = p.getName();
        if (name == null || name.isEmpty()) {
            throw new RuntimeException("getName() returned null or empty string");
        }
        // The returned name should contain the CN component
        if (!name.contains("CN=Test")) {
            throw new RuntimeException("getName() output does not contain expected CN component");
        }

        // Test that getName() returns the same as getName(X500Principal.RFC2253)
        String nameExplicit = p.getName(X500Principal.RFC2253);
        if (!name.equals(nameExplicit)) {
            throw new RuntimeException("getName() and getName(RFC2253) outputs differ");
        }

        // Test that getName(String) throws IllegalArgumentException for invalid format
        boolean threwIAE = false;
        try {
            p.getName("INVALID_FORMAT");
        } catch (IllegalArgumentException iae) {
            threwIAE = true;
        }
        if (!threwIAE) {
            throw new RuntimeException("getName() did not throw IllegalArgumentException for invalid format");
        }

        // Test that getName(null) throws IllegalArgumentException
        threwIAE = false;
        try {
            p.getName(null);
        } catch (IllegalArgumentException iae) {
            threwIAE = true;
        }
        if (!threwIAE) {
            throw new RuntimeException("getName() did not throw IllegalArgumentException for null format");
        }
    }
}

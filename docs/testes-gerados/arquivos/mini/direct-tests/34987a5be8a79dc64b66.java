/*
 * @test
 * @summary Test X500Principal.getName(String) with valid and invalid formats
 * @run main javax.security.auth.x500.X500PrincipalWitupTest
 */

import javax.security.auth.x500.X500Principal;

public class X500PrincipalWitupTest {
    public static void main(String[] args) throws Exception {
        // Use a known distinguished name string
        String dn = "CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US";
        X500Principal principal = new X500Principal(dn);

        // Test getName with RFC1779 format (case insensitive)
        String nameRFC1779 = principal.getName("RFC1779");
        if (nameRFC1779 == null || nameRFC1779.isEmpty()) {
            throw new RuntimeException("getName(RFC1779) returned null or empty");
        }

        String nameRfc1779Lower = principal.getName("rfc1779");
        if (!nameRFC1779.equals(nameRfc1779Lower)) {
            throw new RuntimeException("getName is not case insensitive for RFC1779");
        }

        // Test getName with RFC2253 format (case insensitive)
        String nameRFC2253 = principal.getName("RFC2253");
        if (nameRFC2253 == null || nameRFC2253.isEmpty()) {
            throw new RuntimeException("getName(RFC2253) returned null or empty");
        }

        String nameRfc2253Lower = principal.getName("rfc2253");
        if (!nameRFC2253.equals(nameRfc2253Lower)) {
            throw new RuntimeException("getName is not case insensitive for RFC2253");
        }

        // Test getName with CANONICAL format (case insensitive)
        String nameCanonical = principal.getName("CANONICAL");
        if (nameCanonical == null || nameCanonical.isEmpty()) {
            throw new RuntimeException("getName(CANONICAL) returned null or empty");
        }

        String nameCanonicalLower = principal.getName("canonical");
        if (!nameCanonical.equals(nameCanonicalLower)) {
            throw new RuntimeException("getName is not case insensitive for CANONICAL");
        }

        // Test getName with null format - expect IllegalArgumentException
        try {
            principal.getName(null);
            throw new RuntimeException("Expected IllegalArgumentException for null format");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test getName with invalid format - expect IllegalArgumentException
        try {
            principal.getName("INVALID_FORMAT");
            throw new RuntimeException("Expected IllegalArgumentException for invalid format");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}

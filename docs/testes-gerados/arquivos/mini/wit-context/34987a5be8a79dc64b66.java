/*
 * @test
 * @summary Test javax.security.auth.x500.X500Principal.getName(String) for valid and invalid formats
 * @run main javax.security.auth.x500.X500PrincipalWitupTest
 */

import javax.security.auth.x500.X500Principal;

public class X500PrincipalWitupTest {
    public static void main(String[] args) throws Exception {
        // Use a simple distinguished name string
        String dn = "CN=Test, OU=TestUnit, O=TestOrg, C=US";
        X500Principal principal = new X500Principal(dn);

        // Valid format RFC1779
        String nameRFC1779 = principal.getName("RFC1779");
        if (nameRFC1779 == null || nameRFC1779.isEmpty()) {
            throw new RuntimeException("getName(RFC1779) returned null or empty");
        }

        // Valid format RFC2253
        String nameRFC2253 = principal.getName("RFC2253");
        if (nameRFC2253 == null || nameRFC2253.isEmpty()) {
            throw new RuntimeException("getName(RFC2253) returned null or empty");
        }

        // Valid format CANONICAL
        String nameCanonical = principal.getName("CANONICAL");
        if (nameCanonical == null || nameCanonical.isEmpty()) {
            throw new RuntimeException("getName(CANONICAL) returned null or empty");
        }

        // Valid format case-insensitive check
        String nameLowerCase = principal.getName("rfc2253");
        if (!nameLowerCase.equals(nameRFC2253)) {
            throw new RuntimeException("getName is not case-insensitive for format RFC2253");
        }

        // Invalid format: null should throw IllegalArgumentException
        try {
            principal.getName(null);
            throw new RuntimeException("Expected IllegalArgumentException for null format");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Invalid format: unknown string should throw IllegalArgumentException
        try {
            principal.getName("UNKNOWN_FORMAT");
            throw new RuntimeException("Expected IllegalArgumentException for unknown format");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}

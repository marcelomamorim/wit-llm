/* @test    @summary Test getName(String format) method of X500Principal for null and non-standard formats    @run main X500PrincipalWitupTest */

import javax.security.auth.x500.X500Principal;
import sun.security.x509.X500Name;

public class X500PrincipalWitupTest {

    public static void main(String[] args) {
        testGetNameWithNullFormat();
        testGetNameWithRFC1779();
        testGetNameWithRFC2253();
        testGetNameWithCanonical();
        testGetNameWithInvalidFormat();
    }

    private static void testGetNameWithNullFormat() {
        X500Name x500Name = new X500Name("CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US");
        X500Principal principal = new X500Principal(x500Name);
        String result = principal.getName(null);
        if (result == null || result.isEmpty()) {
            System.out.println("PASS: getName(null) returns non-null string");
        } else {
            System.out.println("FAIL: getName(null) returns: " + result);
        }
    }

    private static void testGetNameWithRFC1779() {
        X500Name x500Name = new X500Name("CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US");
        X500Principal principal = new X500Principal(x500Name);
        String result = principal.getName(X500Principal.RFC1779);
        if (result != null && result.contains("Duke")) {
            System.out.println("PASS: getName(RFC1779) returns: " + result);
        } else {
            System.out.println("FAIL: getName(RFC1779) returns: " + result);
        }
    }

    private static void testGetNameWithRFC2253() {
        X500Name x500Name = new X500Name("CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US");
        X500Principal principal = new X500Principal(x500Name);
        String result = principal.getName(X500Principal.RFC2253);
        if (result != null && result.contains("Duke")) {
            System.out.println("PASS: getName(RFC2253) returns: " + result);
        } else {
            System.out.println("FAIL: getName(RFC2253) returns: " + result);
        }
    }

    private static void testGetNameWithCanonical() {
        X500Name x500Name = new X500Name("CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US");
        X500Principal principal = new X500Principal(x500Name);
        String result = principal.getName(X500Principal.CANONICAL);
        if (result != null && result.contains("Duke")) {
            System.out.println("PASS: getName(CANONICAL) returns: " + result);
        } else {
            System.out.println("FAIL: getName(CANONICAL) returns: " + result);
        }
    }

    private static void testGetNameWithInvalidFormat() {
        X500Name x500Name = new X500Name("CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US");
        X500Principal principal = new X500Principal(x500Name);
        try {
            principal.getName("INVALID_FORMAT");
            System.out.println("FAIL: getName(INVALID_FORMAT) did not throw exception");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: getName(INVALID_FORMAT) threw IllegalArgumentException");
        } catch (Exception e) {
            System.out.println("FAIL: getName(INVALID_FORMAT) threw unexpected exception: " + e);
        }
    }
}

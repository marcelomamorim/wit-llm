/* @test    @summary Test getName method of X500Principal with different formats    @run main X500PrincipalWitupTest */

import javax.security.auth.x500.X500Principal;
import sun.security.x509.X500Name;
import java.io.*;

public class X500PrincipalWitupTest {

    public static void main(String[] args) throws Exception {
        testGetNameRFC1779();
        testGetNameRFC2253();
        testGetNameCanonical();
        testGetNameInvalidFormat();
    }

    static void testGetNameRFC1779() {
        X500Name x500Name = new X500Name("CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US");
        X500Principal principal = new X500Principal(x500Name);
        String result = principal.getName(X500Principal.RFC1779);
        if (!result.equals("CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US")) {
            throw new AssertionError("RFC1779 format mismatch: " + result);
        }
    }

    static void testGetNameRFC2253() {
        X500Name x500Name = new X500Name("CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US");
        X500Principal principal = new X500Principal(x500Name);
        String result = principal.getName(X500Principal.RFC2253);
        if (!result.equals("CN=Duke,OU=JavaSoft,O=Sun Microsystems,C=US")) {
            throw new AssertionError("RFC2253 format mismatch: " + result);
        }
    }

    static void testGetNameCanonical() {
        X500Name x500Name = new X500Name("CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US");
        X500Principal principal = new X500Principal(x500Name);
        String result = principal.getName(X500Principal.CANONICAL);
        if (!result.equals("CN=Duke,OU=JavaSoft,O=Sun Microsystems,C=US")) {
            throw new AssertionError("Canonical format mismatch: " + result);
        }
    }

    static void testGetNameInvalidFormat() {
        X500Name x500Name = new X500Name("CN=Duke");
        X500Principal principal = new X500Principal(x500Name);
        try {
            principal.getName("InvalidFormat");
            throw new AssertionError("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}

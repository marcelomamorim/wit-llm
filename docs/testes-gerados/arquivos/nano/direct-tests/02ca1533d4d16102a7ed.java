/* @test    @summary Test getName with valid formats and null oidMap    @run main X500PrincipalWitupTest */

import javax.security.auth.x500.X500Principal;
import java.util.HashMap;
import java.util.Map;

public class X500PrincipalWitupTest {

    public static void main(String[] args) {
        testGetNameRFC1779();
        testGetNameRFC2253();
        testGetNameInvalidFormat();
        testGetNameNullOidMap();
    }

    private static void testGetNameRFC1779() {
        X500Principal principal = createSamplePrincipal();
        Map<String, String> oidMap = new HashMap<>();
        String name = principal.getName("RFC1779", oidMap);
        System.out.println("RFC1779 format: " + name);
        if (name == null || name.isEmpty()) {
            throw new AssertionError("RFC1779 name should not be null or empty");
        }
    }

    private static void testGetNameRFC2253() {
        X500Principal principal = createSamplePrincipal();
        Map<String, String> oidMap = new HashMap<>();
        String name = principal.getName("RFC2253", oidMap);
        System.out.println("RFC2253 format: " + name);
        if (name == null || name.isEmpty()) {
            throw new AssertionError("RFC2253 name should not be null or empty");
        }
    }

    private static void testGetNameInvalidFormat() {
        X500Principal principal = createSamplePrincipal();
        Map<String, String> oidMap = new HashMap<>();
        try {
            principal.getName("InvalidFormat", oidMap);
            throw new AssertionError("Expected IllegalArgumentException for invalid format");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testGetNameNullOidMap() {
        X500Principal principal = createSamplePrincipal();
        try {
            principal.getName("RFC1779", null);
            throw new AssertionError("Expected NullPointerException for null oidMap");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static X500Principal createSamplePrincipal() {
        // Using a simple distinguished name string for instantiation
        String dn = "CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US";
        return new X500Principal(dn);
    }
}

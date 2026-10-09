/* @test    @summary Test for X500Principal.getName(String, Map) method behavior and exception paths    @run main X500PrincipalWitupTest_P425281322453 */

import javax.security.auth.x500.X500Principal;
import java.util.HashMap;
import java.util.Map;

public class X500PrincipalWitupTest_P425281322453 {

    public static void main(String[] args) {
        testGetName_NullOidMap();
        testGetName_NullFormat();
        testGetName_InvalidFormat();
    }

    private static void testGetName_NullOidMap() {
        X500Principal principal = createSamplePrincipal();
        try {
            principal.getName("RFC1779", null);
            System.out.println("PASS: getName with null oidMap throws NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("PASS: getName with null oidMap throws NullPointerException");
        } catch (Exception e) {
            System.out.println("FAIL: getName with null oidMap threw unexpected exception: " + e);
        }
    }

    private static void testGetName_NullFormat() {
        X500Principal principal = createSamplePrincipal();
        Map<String, String> oidMap = new HashMap<>();
        try {
            String result = principal.getName(null, oidMap);
            System.out.println("FAIL: getName with null format did not throw exception, returned: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: getName with null format throws IllegalArgumentException");
        } catch (Exception e) {
            System.out.println("FAIL: getName with null format threw unexpected exception: " + e);
        }
    }

    private static void testGetName_InvalidFormat() {
        X500Principal principal = createSamplePrincipal();
        Map<String, String> oidMap = new HashMap<>();
        try {
            principal.getName("InvalidFormat", oidMap);
            System.out.println("FAIL: getName with invalid format did not throw exception");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: getName with invalid format throws IllegalArgumentException");
        } catch (Exception e) {
            System.out.println("FAIL: getName with invalid format threw unexpected exception: " + e);
        }
    }

    private static X500Principal createSamplePrincipal() {
        // Using a simple distinguished name string for instantiation
        return new X500Principal("CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US");
    }
}

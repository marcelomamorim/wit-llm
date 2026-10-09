/* @test    @summary Test for X500Principal.getName(String, Map) method behavior and exception paths    @run main X500PrincipalWitupTest_P652231ac5de3 */

import javax.security.auth.x500.X500Principal;
import java.util.HashMap;
import java.util.Map;

public class X500PrincipalWitupTest_P652231ac5de3 {

    public static void main(String[] args) {
        testGetName_NullOidMap();
        testGetName_NullFormat();
        testGetName_InvalidFormat();
    }

    private static void testGetName_NullOidMap() {
        X500Principal principal = createSamplePrincipal();
        try {
            principal.getName("RFC1779", null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t);
        }
    }

    private static void testGetName_NullFormat() {
        X500Principal principal = createSamplePrincipal();
        Map<String, String> oidMap = new HashMap<>();
        try {
            String result = principal.getName(null, oidMap);
            if (result == null) {
                throw new AssertionError("Result should not be null for null format");
            }
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t);
        }
    }

    private static void testGetName_InvalidFormat() {
        X500Principal principal = createSamplePrincipal();
        Map<String, String> oidMap = new HashMap<>();
        try {
            principal.getName("InvalidFormat", oidMap);
            throw new AssertionError("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t);
        }
    }

    private static X500Principal createSamplePrincipal() {
        // Using a minimal valid constructor with a simple distinguished name string
        return new X500Principal("CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US");
    }
}

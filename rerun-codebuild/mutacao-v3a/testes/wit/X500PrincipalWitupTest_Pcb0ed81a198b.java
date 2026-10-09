/* @test    @summary Test for javax.security.auth.x500.X500Principal#getName with various inputs    @run main X500PrincipalWitupTest_Pcb0ed81a198b */

import javax.security.auth.x500.X500Principal;
import java.util.HashMap;
import java.util.Map;

public class X500PrincipalWitupTest_Pcb0ed81a198b {

    public static void main(String[] args) {
        testGetNameWithNullOidMap();
        testGetNameWithNullFormat();
        testGetNameWithInvalidFormat();
    }

    public static void testGetNameWithNullOidMap() {
        try {
            X500Principal principal = createSamplePrincipal();
            String result = principal.getName("RFC1779", null);
            // Expect NullPointerException
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Test passes
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
            System.exit(1);
        }
    }

    public static void testGetNameWithNullFormat() {
        try {
            X500Principal principal = createSamplePrincipal();
            Map<String, String> oidMap = new HashMap<>();
            String result = principal.getName(null, oidMap);
            // Expect IllegalArgumentException
            System.err.println("Expected IllegalArgumentException not thrown");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Test passes
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
            System.exit(1);
        }
    }

    public static void testGetNameWithInvalidFormat() {
        try {
            X500Principal principal = createSamplePrincipal();
            Map<String, String> oidMap = new HashMap<>();
            String invalidFormat = "INVALID_FORMAT";
            String result = principal.getName(invalidFormat, oidMap);
            System.err.println("Expected IllegalArgumentException not thrown");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Test passes
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
            System.exit(1);
        }
    }

    private static X500Principal createSamplePrincipal() {
        // Creating a sample X500Principal with a dummy DN
        return new X500Principal("CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US");
    }
}

/* @test    @summary Test getName with valid formats and null oidMap    @run main X500PrincipalWitupTest_P652231ac5de3 */

import javax.security.auth.x500.X500Principal;
import java.util.HashMap;
import java.util.Map;

public class X500PrincipalWitupTest_P652231ac5de3 {

    public static void main(String[] args) {
        try {
            // Create an X500Principal with a simple DN string
            X500Principal principal = new X500Principal("CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US");

            // Prepare a dummy oidMap (not used in this test, but required by method signature)
            Map<String, String> oidMap = new HashMap<>();

            // Test getName with RFC1779 format
            String nameRFC1779 = principal.getName(X500Principal.RFC1779, oidMap);
            if (nameRFC1779 == null || !nameRFC1779.contains("CN=Duke")) {
                throw new AssertionError("Failed RFC1779 format test") ;
            }

            // Test getName with RFC2253 format
            String nameRFC2253 = principal.getName(X500Principal.RFC2253, oidMap);
            if (nameRFC2253 == null || !nameRFC2253.contains("CN=Duke")) {
                throw new AssertionError("Failed RFC2253 format test") ;
            }

            // Test getName with null format should throw IllegalArgumentException
            boolean exceptionThrown = false;
            try {
                principal.getName(null, oidMap);
            } catch (IllegalArgumentException e) {
                exceptionThrown = true;
            }
            if (!exceptionThrown) {
                throw new AssertionError("Expected IllegalArgumentException for null format") ;
            }

            // Test getName with invalid format string
            exceptionThrown = false;
            try {
                principal.getName("InvalidFormat", oidMap);
            } catch (IllegalArgumentException e) {
                exceptionThrown = true;
            }
            if (!exceptionThrown) {
                throw new AssertionError("Expected IllegalArgumentException for invalid format") ;
            }

            // Test getName with null oidMap should throw NullPointerException
            boolean npeThrown = false;
            try {
                principal.getName(X500Principal.RFC1779, null);
            } catch (NullPointerException e) {
                npeThrown = true;
            }
            if (!npeThrown) {
                throw new AssertionError("Expected NullPointerException for null oidMap") ;
            }

            System.out.println("All tests passed.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

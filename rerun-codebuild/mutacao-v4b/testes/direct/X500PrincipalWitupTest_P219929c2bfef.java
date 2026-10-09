/* @test
   @summary Tests the getName method of X500Principal with valid formats.
   @run main X500PrincipalWitupTest_P219929c2bfef
 */
import java.util.HashMap;
import java.util.Map;
import javax.security.auth.x500.X500Principal;
public class X500PrincipalWitupTest_P219929c2bfef {
    public static void main(String[] args) {
        try {
            // Create a dummy X500Principal with minimal implementation
            X500Principal principal = new X500Principal("CN=Duke, O=Sun");
            // Use reflection to access the private field thisX500Name
            java.lang.reflect.Field field = X500Principal.class.getDeclaredField("thisX500Name");
            field.setAccessible(true);
            Object x500Name = field.get(principal);
            if (x500Name == null) {
                throw new AssertionError("thisX500Name should not be null");
            }
            // Prepare a dummy oidMap
            Map<String, String> oidMap = new HashMap<>();
            // Call getName with RFC1779
            String nameRFC1779 = principal.getName(X500Principal.RFC1779, oidMap);
            if (nameRFC1779 == null || !nameRFC1779.contains("CN=Duke")) {
                throw new AssertionError("RFC1779 name mismatch");
            }
            // Call getName with RFC2253
            String nameRFC2253 = principal.getName(X500Principal.RFC2253, oidMap);
            if (nameRFC2253 == null || !nameRFC2253.contains("CN=Duke")) {
                throw new AssertionError("RFC2253 name mismatch");
            }
            // Call getName with null format should throw IllegalArgumentException
            try {
                principal.getName(null, oidMap);
                throw new AssertionError("Expected IllegalArgumentException for null format");
            } catch (IllegalArgumentException e) {
                // expected
            }
            // Call getName with invalid format should throw IllegalArgumentException
            try {
                principal.getName("InvalidFormat", oidMap);
                throw new AssertionError("Expected IllegalArgumentException for invalid format");
            } catch (IllegalArgumentException e) {
                // expected
            }
            // Call getName with null oidMap should throw NullPointerException
            try {
                principal.getName(X500Principal.RFC1779, null);
                throw new AssertionError("Expected NullPointerException for null oidMap");
            } catch (NullPointerException e) {
                // expected
            }
            System.out.println("All tests passed.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new AssertionError("Test failed due to exception", e);
        }
    }
}

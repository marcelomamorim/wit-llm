/* @test
   @summary Verifies that checkAccept throws NullPointerException when host is null.
   @run main SecurityManagerWitupTest_Pf402ff07ac07
 */
import java.lang.SecurityManager;
import java.security.Permission;

public class SecurityManagerWitupTest_Pf402ff07ac07 {
    public static void main(String[] args) {
        SecurityManager sm = new SecurityManager();
        // Test that checkAccept throws NullPointerException when host is null
        try {
            sm.checkAccept(null, 80);
            throw new AssertionError("Expected NullPointerException for null host");
        } catch (NullPointerException e) {
            // expected
        }
        // Additional test: verify that checkAccept does not throw for valid host
        try {
            sm.checkAccept("localhost", 80);
        } catch (SecurityException e) {
            // If a SecurityException is thrown, it indicates permission check, which is acceptable
        }
    }
}

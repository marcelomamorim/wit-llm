/* @test
   @summary Verifies behavior of SecurityManager.checkLink with null and valid library names.
   @run main SecurityManagerWitupTest_P3ccb798cbce8
 */
import java.lang.SecurityManager;
import java.security.Permission;

public class SecurityManagerWitupTest_P3ccb798cbce8 {
    public static void main(String[] args) {
        SecurityManager sm = new SecurityManager();
        System.setSecurityManager(sm);
        // Test with null argument, expecting NullPointerException
        try {
            sm.checkLink(null);
            throw new AssertionError("Expected NullPointerException for null argument");
        } catch (NullPointerException e) {
            // expected
        }
        // Test with a valid library name, expecting checkPermission to be called without exception
        try {
            sm.checkLink("testLib");
        } catch (SecurityException se) {
            throw new AssertionError("Unexpected SecurityException for valid library name", se);
        }
    }
}

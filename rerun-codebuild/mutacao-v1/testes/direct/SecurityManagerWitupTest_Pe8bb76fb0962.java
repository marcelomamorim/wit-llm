/*
 * @test
 * @summary Test SecurityManager.checkLink(String) for null and valid input
 * @run main SecurityManagerWitupTest_Pe8bb76fb0962
 */

import java.lang.SecurityManager;
import java.security.Permission;

public class SecurityManagerWitupTest_Pe8bb76fb0962 {

    public static void main(String[] args) {
        SecurityManager sm = new SecurityManager() {
            @Override
            public void checkPermission(Permission perm) {
                // Allow all permissions for testing
            }
        };
        System.setSecurityManager(sm);

        // Test with null argument, expecting NullPointerException
        try {
            sm.checkLink(null);
            throw new AssertionError("Expected NullPointerException for null argument");
        } catch (NullPointerException e) {
            // Expected exception
        }

        // Test with a valid library name, expecting no exception
        try {
            sm.checkLink("testLib");
        } catch (SecurityException e) {
            throw new AssertionError("Unexpected SecurityException for valid input", e);
        }
    }
}

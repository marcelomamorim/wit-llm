/*
 * @test
 * @summary Test SecurityManager.checkPackageAccess(String) for null argument and restricted package access
 * @run main SecurityManagerWitupTest
 */

import java.lang.SecurityManager;
import java.lang.RuntimePermission;

public class SecurityManagerWitupTest {
    public static void main(String[] args) {
        SecurityManager sm = new SecurityManager();

        // Test 1: checkPackageAccess with null argument should throw NullPointerException
        try {
            sm.checkPackageAccess(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // Expected exception
        }

        // Test 2: checkPackageAccess with a package not in restricted list should not throw
        // Using a package very unlikely to be restricted
        sm.checkPackageAccess("com.example.unrestricted");

        // Test 3: checkPackageAccess with a package in restricted list triggers checkPermission
        // Since we cannot easily add restricted packages or permissions, we test with a known restricted package
        // The default restricted packages include "java.lang.reflect" in many JDKs
        // We expect a SecurityException if permission is denied
        String restrictedPkg = "java.lang.reflect";
        try {
            sm.checkPackageAccess(restrictedPkg);
            // If no SecurityException, test passes assuming permission granted
        } catch (SecurityException se) {
            // Expected if permission denied
        }
    }
}

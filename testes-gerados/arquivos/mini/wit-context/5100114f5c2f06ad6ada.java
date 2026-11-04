/*
 * @test
 * @summary Test SecurityManager.checkPackageDefinition(String) for null argument and permission checks
 * @run main SecurityManagerWitupTest
 */

import java.lang.SecurityManager;
import java.lang.RuntimePermission;

public class SecurityManagerWitupTest {
    public static void main(String[] args) {
        SecurityManager sm = new SecurityManager();

        // Test that passing null throws NullPointerException
        try {
            sm.checkPackageDefinition(null);
            throw new Error("Expected NullPointerException for null package name");
        } catch (NullPointerException npe) {
            // Expected
        }

        // Test with a package name that is very unlikely to be in nonExportedPkgs or packageDefinition
        // This should not throw SecurityException if no permission is denied
        // We cannot test permission denial without a custom SecurityManager or policy,
        // so we just test that no exception is thrown for a normal package name.
        String testPkg = "com.example.testpkg";
        try {
            sm.checkPackageDefinition(testPkg);
        } catch (SecurityException se) {
            throw new Error("Unexpected SecurityException for package: " + testPkg);
        }

        // Test with empty string package name, should behave similarly
        try {
            sm.checkPackageDefinition("");
        } catch (SecurityException se) {
            throw new Error("Unexpected SecurityException for empty package name");
        }

        System.out.println("SecurityManagerWitupTest passed");
    }
}

/*
 * @test
 * @summary Test java.lang.SecurityManager.checkPackageAccess(String) behavior
 * @run main SecurityManagerWitupTest
 */

import java.lang.SecurityManager;
import java.lang.RuntimePermission;

public class SecurityManagerWitupTest {
    public static void main(String[] args) {
        SecurityManager sm = new SecurityManager();

        // Test null argument triggers NullPointerException
        try {
            sm.checkPackageAccess(null);
            throw new Error("Expected NullPointerException for null package name");
        } catch (NullPointerException npe) {
            // expected
        }

        // Test with a package name that is not restricted (likely no exception)
        // We use a package very unlikely to be restricted
        sm.checkPackageAccess("com.example.unrestricted");

        // Test with a package name that is restricted by default in JDK
        // The default restricted packages include "java.lang.reflect", "sun.reflect", etc.
        // We pick "java.lang.reflect" which is commonly restricted
        // Expect SecurityException due to RuntimePermission
        try {
            sm.checkPackageAccess("java.lang.reflect");
            throw new Error("Expected SecurityException for restricted package java.lang.reflect");
        } catch (SecurityException se) {
            // expected
            // Check that the permission name contains the package
            if (!se.getMessage().contains("accessClassInPackage.java.lang.reflect")) {
                throw new Error("SecurityException message does not contain expected permission name");
            }
        }

        // Test with a package name that is a prefix of a restricted package but not restricted itself
        // For example, if "java.lang.reflect" is restricted, "java.lang.ref" is not
        // This should not throw
        sm.checkPackageAccess("java.lang.ref");

        // Test with a package name that is a restricted package plus a suffix
        // For example, "java.lang.reflect.subpkg" should also trigger restriction
        try {
            sm.checkPackageAccess("java.lang.reflect.subpkg");
            throw new Error("Expected SecurityException for restricted subpackage java.lang.reflect.subpkg");
        } catch (SecurityException se) {
            // expected
        }

        // Test with a package name that equals a restricted package plus a dot
        // For example, if "java.lang.reflect" is restricted, "java.lang.reflect." is invalid but test anyway
        // This should throw because of the check logic
        try {
            sm.checkPackageAccess("java.lang.reflect.");
            throw new Error("Expected SecurityException for package name with trailing dot");
        } catch (SecurityException se) {
            // expected
        }

        System.out.println("All tests passed.");
    }
}

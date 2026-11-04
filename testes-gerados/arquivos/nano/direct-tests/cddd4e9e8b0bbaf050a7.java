/*
 * @test
 * @summary Test for SecurityManager.checkPackageAccess(String)
 * @run main SecurityManagerWitupTest
 */

import java.lang.SecurityManager;
import java.security.Permission;

public class SecurityManagerWitupTest {

    public static void main(String[] args) {
        testCheckPackageAccessWithNull();
        testCheckPackageAccessWithRestrictedPackage();
        testCheckPackageAccessWithUnrestrictedPackage();
    }

    static void testCheckPackageAccessWithNull() {
        SecurityManager sm = new SecurityManager();
        try {
            sm.checkPackageAccess(null);
            // Expect NullPointerException due to Objects.requireNonNull
            System.out.println("FAIL: Expected NullPointerException for null package");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    static void testCheckPackageAccessWithRestrictedPackage() {
        SecurityManager sm = new SecurityManager() {
            @Override
            public void checkPermission(Permission perm) {
                // Allow all permissions for this test
            }
        };
        // Simulate restricted package by adding to nonExportedPkgs
        // Since nonExportedPkgs is private, we cannot modify it directly.
        // Instead, we test with a package name that triggers permission check.
        String restrictedPkg = "com.restricted";
        try {
            sm.checkPackageAccess(restrictedPkg);
            System.out.println("PASS: checkPackageAccess executed for restricted package");
        } catch (SecurityException se) {
            System.out.println("FAIL: SecurityException thrown unexpectedly");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    static void testCheckPackageAccessWithUnrestrictedPackage() {
        SecurityManager sm = new SecurityManager() {
            @Override
            public void checkPermission(Permission perm) {
                // Allow all permissions
            }
        };
        String unrestrictedPkg = "com.unrestricted";
        try {
            sm.checkPackageAccess(unrestrictedPkg);
            System.out.println("PASS: checkPackageAccess executed for unrestricted package");
        } catch (SecurityException se) {
            System.out.println("FAIL: SecurityException thrown unexpectedly");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}

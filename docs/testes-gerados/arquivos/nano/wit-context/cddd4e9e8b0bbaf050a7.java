/*
 * @test
 * @summary Test for SecurityManager.checkPackageAccess(String)
 * @run main SecurityManagerWitupTest
 */

import java.lang.SecurityManager;
import java.security.Permission;

public class SecurityManagerWitupTest {

    public static void main(String[] args) {
        testNullPackage();
        testRestrictedPackage();
    }

    private static void testNullPackage() {
        SecurityManager sm = new SecurityManager();
        try {
            sm.checkPackageAccess(null);
            System.out.println("testNullPackage: Failed - no exception");
        } catch (NullPointerException e) {
            System.out.println("testNullPackage: Passed");
        } catch (Exception e) {
            System.out.println("testNullPackage: Failed - wrong exception: " + e);
        }
    }

    private static void testRestrictedPackage() {
        SecurityManager sm = new SecurityManager();
        String restrictedPkg = "com.example.restricted";
        try {
            sm.checkPackageAccess(restrictedPkg);
            System.out.println("testRestrictedPackage: Passed or no exception");
        } catch (SecurityException e) {
            System.out.println("testRestrictedPackage: SecurityException as expected");
        } catch (Exception e) {
            System.out.println("testRestrictedPackage: Failed - wrong exception: " + e);
        }
    }
}

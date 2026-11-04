/*
 * @test
 * @summary Test checkPackageDefinition method of SecurityManager
 * @run main SecurityManagerWitupTest
 */

import java.lang.SecurityManager;
import java.security.Permission;

public class SecurityManagerWitupTest {

    public static void main(String[] args) {
        testCheckPackageDefinitionWithNull();
        testCheckPackageDefinitionWithExportedPackage();
        testCheckPackageDefinitionWithNonExportedPackage();
    }

    private static void testCheckPackageDefinitionWithNull() {
        SecurityManager sm = new SecurityManager();
        try {
            sm.checkPackageDefinition(null);
            System.out.println("testCheckPackageDefinitionWithNull: FAILED (no exception)");
        } catch (NullPointerException e) {
            System.out.println("testCheckPackageDefinitionWithNull: PASSED");
        } catch (Exception e) {
            System.out.println("testCheckPackageDefinitionWithNull: FAILED (unexpected exception)" + e);
        }
    }

    private static void testCheckPackageDefinitionWithExportedPackage() {
        SecurityManager sm = new SecurityManager();
        try {
            sm.checkPackageDefinition("java");
            System.out.println("testCheckPackageDefinitionWithExportedPackage: PASSED");
        } catch (Exception e) {
            System.out.println("testCheckPackageDefinitionWithExportedPackage: FAILED (unexpected exception)" + e);
        }
    }

    private static void testCheckPackageDefinitionWithNonExportedPackage() {
        SecurityManager sm = new SecurityManager();
        try {
            sm.checkPackageDefinition("com.nonexistent");
            System.out.println("testCheckPackageDefinitionWithNonExportedPackage: PASSED");
        } catch (SecurityException e) {
            System.out.println("testCheckPackageDefinitionWithNonExportedPackage: FAILED (security exception)");
        } catch (Exception e) {
            System.out.println("testCheckPackageDefinitionWithNonExportedPackage: FAILED (unexpected exception)" + e);
        }
    }
}

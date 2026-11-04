/*
 * @test
 * @summary Test System.addOpensToAllUnnamed(Module,String) method
 * @run main SystemWitupTest
 */

import java.lang.Module;
import java.lang.reflect.Method;

public class SystemWitupTest {
    public static void main(String[] args) throws Exception {
        // Obtain the unnamed module of this class
        Module unnamedModule = SystemWitupTest.class.getModule();

        // Obtain a named module to test with
        Module javaBaseModule = String.class.getModule();

        // We will test addOpensToAllUnnamed on javaBaseModule with a package name
        String testPackage = "java.lang";

        // Create an instance of System to call the instance method
        System sys = new System();

        // Call addOpensToAllUnnamed - this should call implAddOpensToAllUnnamed on the module
        // Since implAddOpensToAllUnnamed is not public, we verify effect by reflection

        // Before calling, check if the package is open to the unnamed module
        boolean beforeOpen = isPackageOpen(javaBaseModule, testPackage, unnamedModule);

        sys.addOpensToAllUnnamed(javaBaseModule, testPackage);

        // After calling, check if the package is open to the unnamed module
        boolean afterOpen = isPackageOpen(javaBaseModule, testPackage, unnamedModule);

        if (afterOpen) {
            System.out.println("PASS: Package '" + testPackage + "' is open to unnamed module after addOpensToAllUnnamed call.");
        } else {
            System.out.println("FAIL: Package '" + testPackage + "' is NOT open to unnamed module after addOpensToAllUnnamed call.");
            throw new RuntimeException("addOpensToAllUnnamed did not open the package as expected");
        }

        // Also test with empty package name (should not throw)
        sys.addOpensToAllUnnamed(javaBaseModule, "");

        // Test with null package name - expect NullPointerException
        boolean npeThrown = false;
        try {
            sys.addOpensToAllUnnamed(javaBaseModule, null);
        } catch (NullPointerException npe) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException when package name is null");
        }

        // Test with null module - expect NullPointerException
        npeThrown = false;
        try {
            sys.addOpensToAllUnnamed(null, testPackage);
        } catch (NullPointerException npe) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException when module is null");
        }
    }

    private static boolean isPackageOpen(Module m, String pn, Module to) {
        // Use Module.isOpen(String, Module) to check if package is open
        if (pn == null) return false;
        return m.isOpen(pn, to);
    }
}

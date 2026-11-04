/*
 * @test
 * @summary Test java.lang.System.addOpens(Module m, String pn, Module other) method
 * @run main SystemWitupTest
 */

import java.lang.Module;
import java.lang.reflect.Method;

public class SystemWitupTest {
    public static void main(String[] args) throws Exception {
        // Obtain the unnamed module (this module) and the java.base module for testing
        Module thisModule = SystemWitupTest.class.getModule();
        Module javaBaseModule = String.class.getModule();

        // Use a package name that is known to exist in this module
        String packageName = "java.lang";

        // Create an instance of System to call the instance method addOpens
        System sys = new System();

        // Call addOpens to open java.lang package of thisModule to javaBaseModule
        sys.addOpens(thisModule, packageName, javaBaseModule);

        // Verify that the package is opened to the other module
        // The Module API provides isOpen(String pn, Module other) to check this
        if (!thisModule.isOpen(packageName, javaBaseModule)) {
            throw new AssertionError("Package '" + packageName + "' was not opened to java.base module");
        }

        // Also test that calling addOpens with empty package name does not throw
        sys.addOpens(thisModule, "", javaBaseModule);

        // Test that calling addOpens with null package name throws NullPointerException
        boolean npeThrown = false;
        try {
            sys.addOpens(thisModule, null, javaBaseModule);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when package name is null");
        }

        // Test that calling addOpens with null module throws NullPointerException
        npeThrown = false;
        try {
            sys.addOpens(null, packageName, javaBaseModule);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when module m is null");
        }

        // Test that calling addOpens with null other module throws NullPointerException
        npeThrown = false;
        try {
            sys.addOpens(thisModule, packageName, null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when other module is null");
        }
    }
}

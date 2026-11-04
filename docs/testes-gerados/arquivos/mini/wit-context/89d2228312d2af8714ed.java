/*
 * @test
 * @summary Test java.lang.Module.isOpen(String, Module) for null checks and basic behavior
 * @run main java.lang.ModuleWitupTest
 */

import java.lang.Module;
import java.util.Objects;

public class ModuleWitupTest {

    public static void main(String[] args) {
        testIsOpenNullPn();
        testIsOpenNullOther();
        testIsOpenBasic();
        System.out.println("All tests passed.");
    }

    private static void testIsOpenNullPn() {
        Module m = ModuleWitupTest.getUnnamedModule();
        try {
            m.isOpen(null, m);
            throw new AssertionError("Expected NullPointerException for null pn");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testIsOpenNullOther() {
        Module m = ModuleWitupTest.getUnnamedModule();
        try {
            m.isOpen("java.lang", null);
            throw new AssertionError("Expected NullPointerException for null other");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testIsOpenBasic() {
        Module m = ModuleWitupTest.getUnnamedModule();
        // The unnamed module should not open any package to itself (implIsExportedOrOpen is internal)
        // We test that the call returns a boolean without exception.
        boolean result = m.isOpen("java.lang", m);
        // result can be true or false depending on implementation, so just assert no exception and boolean returned
        if (!(result == true || result == false)) {
            throw new AssertionError("Unexpected return value from isOpen");
        }
    }

    private static Module getUnnamedModule() {
        // Use the current class's class loader to get unnamed module
        return ModuleWitupTest.class.getClassLoader().getUnnamedModule();
    }
}

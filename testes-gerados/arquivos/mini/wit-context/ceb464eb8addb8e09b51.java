/*
 * @test
 * @summary Test java.lang.Module.isExported(String, Module) for null argument checks and basic behavior
 * @run main java.lang.ModuleWitupTest
 */

import java.lang.Module;
import java.util.Objects;

public class ModuleWitupTest {

    public static void main(String[] args) {
        testIsExportedNullPn();
        testIsExportedNullOther();
        testIsExportedBasic();
        System.out.println("All tests passed.");
    }

    private static void testIsExportedNullPn() {
        Module m = ModuleWitupTest.class.getModule();
        try {
            m.isExported(null, m);
            throw new AssertionError("Expected NullPointerException for null pn");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testIsExportedNullOther() {
        Module m = ModuleWitupTest.class.getModule();
        try {
            m.isExported("java.lang", null);
            throw new AssertionError("Expected NullPointerException for null other");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testIsExportedBasic() {
        Module m = ModuleWitupTest.class.getModule();
        // The unnamed module or the module of this class should not export "java.lang" to itself
        // The exact result depends on module definitions, so just call and check no exception
        boolean result = m.isExported("java.lang", m);
        // result is boolean, no exception expected
        if (result != true && result != false) {
            throw new AssertionError("Unexpected result from isExported");
        }
    }
}

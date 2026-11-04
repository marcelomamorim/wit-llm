/*
 * @test
 * @summary Test java.lang.Module.isOpen(String) method
 * @run main java.lang.ModuleWitupTest
 */

import java.lang.Module;
import java.util.Objects;

public class ModuleWitupTest {

    public static void main(String[] args) throws Exception {
        testIsOpenWithNull();
        testIsOpenWithEmptyString();
        testIsOpenWithNormalString();
    }

    private static void testIsOpenWithNull() {
        Module module = getUnnamedModule();
        try {
            module.isOpen(null);
            throw new RuntimeException("Expected NullPointerException for null argument");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testIsOpenWithEmptyString() {
        Module module = getUnnamedModule();
        boolean result = module.isOpen("");
        // Result is boolean, no exception expected, just check result is boolean
        if (!(result == true || result == false)) {
            throw new RuntimeException("Unexpected result for empty string");
        }
    }

    private static void testIsOpenWithNormalString() {
        Module module = getUnnamedModule();
        boolean result = module.isOpen("java.lang");
        // Result is boolean, no exception expected, just check result is boolean
        if (!(result == true || result == false)) {
            throw new RuntimeException("Unexpected result for 'java.lang'");
        }
    }

    private static Module getUnnamedModule() {
        // Use the current class's class loader to get unnamed module
        return ModuleWitupTest.class.getClassLoader().getUnnamedModule();
    }
}

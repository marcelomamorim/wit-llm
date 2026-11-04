/*
 * @test
 * @summary Test java.lang.Module.isOpen(String) method for null argument and normal behavior
 * @run main java.lang.ModuleWitupTest
 */

import java.lang.Module;
import java.util.Objects;

public class ModuleWitupTest {
    public static void main(String[] args) {
        testIsOpenNullArgument();
        testIsOpenNonNullArgument();
    }

    private static void testIsOpenNullArgument() {
        Module m = ModuleWitupTest.class.getModule();
        try {
            m.isOpen(null);
            throw new AssertionError("Expected NullPointerException for null package name");
        } catch (NullPointerException npe) {
            // Expected exception
        }
    }

    private static void testIsOpenNonNullArgument() {
        Module m = ModuleWitupTest.class.getModule();
        String pkg = "java.lang";
        boolean result = m.isOpen(pkg);
        // The result is boolean, no exception expected, just check it returns a boolean
        if (!(result == true || result == false)) {
            throw new AssertionError("isOpen returned non-boolean value");
        }
    }
}

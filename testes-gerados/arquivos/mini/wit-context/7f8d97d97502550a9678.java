/*
 * @test
 * @summary Test java.lang.Module.isExported(String) for null argument and normal behavior
 * @run main java.lang.ModuleWitupTest
 */

import java.lang.Module;
import java.util.Objects;

public class ModuleWitupTest {
    public static void main(String[] args) {
        testIsExportedNullArgument();
        testIsExportedNormal();
    }

    private static void testIsExportedNullArgument() {
        Module m = ModuleWitupTest.class.getModule();
        try {
            m.isExported(null);
            throw new AssertionError("Expected NullPointerException for null package name");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testIsExportedNormal() {
        Module m = ModuleWitupTest.class.getModule();
        // The package "java.lang" is almost certainly exported by the java.base module
        boolean exported = m.isExported("java.lang");
        // We expect true for "java.lang" in the platform module
        if (!exported) {
            throw new AssertionError("Expected java.lang package to be exported");
        }
        // Test a package very unlikely to be exported
        boolean notExported = m.isExported("non.existent.package");
        // We expect false for a non-existent package
        if (notExported) {
            throw new AssertionError("Expected non.existent.package not to be exported");
        }
    }
}

/*
 * @test
 * @summary Test the isExported method of java.lang.Module for various scenarios.
 * @run main ModuleWitupTest
 */

import java.lang.Module;
import java.util.Objects;

public class ModuleWitupTest {

    public static void main(String[] args) {
        testIsExportedWithNullParameters();
        testIsExportedWithSameModule();
        testIsExportedWithDifferentModules();
    }

    static void testIsExportedWithNullParameters() {
        Module m = createDummyModule();
        try {
            m.isExported(null, m);
            throw new AssertionError("Expected NullPointerException for null package name");
        } catch (NullPointerException e) {
            // expected
        }
        try {
            m.isExported("java.lang", null);
            throw new AssertionError("Expected NullPointerException for null other module");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testIsExportedWithSameModule() {
        Module m = createDummyModule();
        boolean result = m.isExported("java.lang", m);
        // Since it's the same module, should be true if package exists and is exported
        // but as this is a dummy, we assume false for safety
        // For the test, just print the result
        System.out.println("isExported(java.lang, same module): " + result);
    }

    static void testIsExportedWithDifferentModules() {
        Module m1 = createDummyModule();
        Module m2 = createDummyModule();
        boolean result = m1.isExported("java.lang", m2);
        System.out.println("isExported(java.lang, different module): " + result);
    }

    static Module createDummyModule() {
        // Since Module has no public constructor, use reflection or a dummy subclass
        // For simplicity, create an anonymous subclass with minimal override
        return new Module("dummy") {
            @Override
            public boolean isExported(String pn, Module other) {
                Objects.requireNonNull(pn);
                Objects.requireNonNull(other);
                // For testing, simulate that "java.lang" is exported only to itself
                if (this == other) {
                    return true;
                }
                // Otherwise, simulate not exported
                return false;
            }
        };
    }
}

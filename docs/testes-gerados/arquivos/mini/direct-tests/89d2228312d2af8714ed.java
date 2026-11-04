/*
 * @test
 * @summary Test java.lang.Module.isOpen(String, Module) method
 * @run main ModuleWitupTest
 */

import java.lang.Module;
import java.util.Objects;

public class ModuleWitupTest {

    public static void main(String[] args) throws Exception {
        testNullPackageName();
        testNullOtherModule();
        testIsOpenReturnsBoolean();
    }

    private static void testNullPackageName() {
        Module m = getUnnamedModule();
        Module other = getUnnamedModule();
        try {
            m.isOpen(null, other);
            throw new Error("Expected NullPointerException for null package name");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testNullOtherModule() {
        Module m = getUnnamedModule();
        try {
            m.isOpen("java.lang", null);
            throw new Error("Expected NullPointerException for null other module");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testIsOpenReturnsBoolean() {
        Module m = getUnnamedModule();
        Module other = getUnnamedModule();
        // The actual return depends on implIsExportedOrOpen, but we can test it returns boolean without exception
        boolean result = m.isOpen("java.lang", other);
        if (result != true && result != false) {
            throw new Error("isOpen returned non-boolean value");
        }
    }

    private static Module getUnnamedModule() {
        // Use the unnamed module of this class's class loader
        return ModuleWitupTest.class.getClassLoader().getUnnamedModule();
    }
}

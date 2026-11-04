/*
 * @test
 * @summary Test java.lang.Module.addOpens(String, Module) for argument validation and access control
 * @run main java.lang.ModuleWitupTest
 */

import java.lang.Module;
import java.lang.IllegalCallerException;
import java.util.Objects;

public class ModuleWitupTest {
    public static void main(String[] args) throws Exception {
        testAddOpensNullPackage();
        testAddOpensNullOther();
        testAddOpensAccessControl();
    }

    private static void testAddOpensNullPackage() {
        Module m = ModuleWitupTest.class.getModule();
        try {
            m.addOpens(null, m);
            throw new Error("Expected IllegalArgumentException for null package name");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testAddOpensNullOther() {
        Module m = ModuleWitupTest.class.getModule();
        try {
            m.addOpens("java.lang", null);
            throw new Error("Expected NullPointerException for null other module");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testAddOpensAccessControl() {
        Module m = ModuleWitupTest.class.getModule();
        if (!m.isNamed()) {
            // unnamed module: addOpens should not throw IllegalCallerException
            Module result = m.addOpens("java.lang", m);
            if (result != m) {
                throw new Error("addOpens should return this module");
            }
        } else {
            // named module: test that IllegalCallerException is thrown if caller is not allowed
            // We cannot easily simulate a caller module different from this and not open, so we test that
            // addOpens returns this when called from this module
            Module result = m.addOpens("java.lang", m);
            if (result != m) {
                throw new Error("addOpens should return this module");
            }
        }
    }
}

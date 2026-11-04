/*
 * @test
 * @summary Verify ModuleLayer.findLoader returns the loader of a known module and rejects unknown or null names
 * @run main ModuleLayerWitupTest
 */

import java.util.Optional;

public class ModuleLayerWitupTest {

    private static void fail(String msg) {
        throw new RuntimeException(msg);
    }

    public static void main(String[] args) {
        ModuleLayer boot = ModuleLayer.boot();

        Optional<Module> base = boot.findModule("java.base");
        if (!base.isPresent()) {
            fail("java.base not found in boot layer");
        }
        ClassLoader expected = base.get().getClassLoader();
        ClassLoader actual = boot.findLoader("java.base");
        if (actual != expected) {
            fail("findLoader(java.base) differs from module class loader");
        }

        try {
            boot.findLoader("no.such.module.witup");
            fail("expected IllegalArgumentException for unknown module");
        } catch (IllegalArgumentException expectedException) {
            // expected
        }

        try {
            boot.findLoader(null);
            fail("expected NullPointerException for null name");
        } catch (NullPointerException expectedException) {
            // expected
        }
    }
}

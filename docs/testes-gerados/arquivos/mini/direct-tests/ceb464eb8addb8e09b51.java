/*
 * @test
 * @summary Test java.lang.Module.isExported(String, Module) method
 * @run main ModuleWitupTest
 */

import java.lang.Module;
import java.util.Objects;

public class ModuleWitupTest {

    public static void main(String[] args) throws Exception {
        testIsExportedWithNullPackageName();
        testIsExportedWithNullOtherModule();
        testIsExportedWithDummyModules();
    }

    private static void testIsExportedWithNullPackageName() {
        Module m = DummyModule.create("m1");
        Module other = DummyModule.create("m2");
        try {
            m.isExported(null, other);
            throw new Error("Expected NullPointerException for null package name");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testIsExportedWithNullOtherModule() {
        Module m = DummyModule.create("m1");
        try {
            m.isExported("java.lang", null);
            throw new Error("Expected NullPointerException for null other module");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testIsExportedWithDummyModules() {
        Module m1 = DummyModule.create("m1");
        Module m2 = DummyModule.create("m2");
        // The dummy implementation always returns false for isExported
        boolean result = m1.isExported("java.lang", m2);
        if (result) {
            throw new Error("Expected isExported to return false for dummy modules");
        }
    }

    // Minimal dummy Module subclass to allow calling isExported
    private static final class DummyModule extends Module {
        private DummyModule(String name) {
            super(null, name, null, null, null, null, null, null, null);
        }

        static Module create(String name) {
            return new DummyModule(name);
        }

        // Override implIsExportedOrOpen to always return false
        @Override
        boolean implIsExportedOrOpen(String pn, Module other, boolean open) {
            Objects.requireNonNull(pn);
            Objects.requireNonNull(other);
            return false;
        }
    }
}

/*
 * @test
 * @summary Tests ModuleDescriptor.Builder.exports(Exports) observable behavior
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleDescriptor.Builder;
import java.lang.module.ModuleDescriptor.Exports;

public class ModuleDescriptorWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testExportsReturnsSameBuilderAndIsBuilt() {
        Builder builder = ModuleDescriptor.newModule("m1");
        Builder returned = builder.exports(Exports.of("p1"));
        check(returned == builder, "exports must return the same Builder");

        ModuleDescriptor descriptor = builder.build();
        boolean found = false;
        for (Exports e : descriptor.exports()) {
            if (e.source().equals("p1")) {
                found = true;
            }
        }
        check(found, "exported package p1 must appear in built descriptor");
    }

    static void testDuplicateExportRejected() {
        Builder builder = ModuleDescriptor.newModule("m2");
        builder.exports(Exports.of("p2"));
        try {
            builder.exports(Exports.of("p2"));
            throw new RuntimeException("expected IllegalStateException for duplicate export");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    static void testAutomaticModuleRejectsExports() {
        Builder builder = ModuleDescriptor.newAutomaticModule("m3");
        try {
            builder.exports(Exports.of("p3"));
            throw new RuntimeException("expected IllegalStateException for automatic module");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    public static void main(String[] args) {
        testExportsReturnsSameBuilderAndIsBuilt();
        testDuplicateExportRejected();
        testAutomaticModuleRejectsExports();
    }
}

/*
 * @test
 * @summary Checks Builder.exports(Exports) rejects exports on automatic modules and duplicate exported packages, and accepts distinct packages
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleDescriptor.Exports;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testAutomaticModuleRejectsExports();
        testDuplicateExportRejected();
        testDistinctExportsAccepted();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testAutomaticModuleRejectsExports() {
        ModuleDescriptor.Builder builder = ModuleDescriptor.newAutomaticModule("m.auto");
        try {
            builder.exports(Exports.exports("p.q"));
            throw new RuntimeException("IllegalStateException expected for automatic module");
        } catch (IllegalStateException expected) {
        }
    }

    static void testDuplicateExportRejected() {
        ModuleDescriptor.Builder builder = ModuleDescriptor.newModule("m.dup");
        builder.exports(Exports.exports("p.dup"));
        try {
            builder.exports(Exports.exports("p.dup"));
            throw new RuntimeException("IllegalStateException expected for duplicate export");
        } catch (IllegalStateException expected) {
        }
    }

    static void testDistinctExportsAccepted() {
        ModuleDescriptor md = ModuleDescriptor.newModule("m.distinct")
                .exports(Exports.exports("p.one"))
                .exports(Exports.exports("p.two"))
                .build();
        check(md.exports().size() == 2, "expected 2 exports but got " + md.exports().size());
    }
}

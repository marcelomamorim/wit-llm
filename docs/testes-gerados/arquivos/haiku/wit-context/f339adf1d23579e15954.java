/*
 * @test
 * @summary Tests observable behavior of ModuleDescriptor.Builder.exports(String) through public APIs
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleDescriptor.Exports;
import java.util.Set;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testValidExportReturnsSameBuilderAndIsRecorded();
        testInvalidPackageNameThrowsIllegalArgumentException();
        testAutomaticModuleExportThrowsIllegalStateException();
    }

    static void testValidExportReturnsSameBuilderAndIsRecorded() {
        ModuleDescriptor.Builder builder = ModuleDescriptor.newModule("m1");
        ModuleDescriptor.Builder result = builder.exports("p.q");
        if (result != builder) {
            throw new RuntimeException("exports(String) must return the same Builder");
        }
        ModuleDescriptor md = builder.build();
        Set<Exports> exports = md.exports();
        if (exports.size() != 1) {
            throw new RuntimeException("expected 1 export but found " + exports.size());
        }
        Exports e = exports.iterator().next();
        if (!e.source().equals("p.q")) {
            throw new RuntimeException("unexpected exported package: " + e.source());
        }
    }

    static void testInvalidPackageNameThrowsIllegalArgumentException() {
        try {
            ModuleDescriptor.newModule("m2").exports("p..q");
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException expected for invalid package name");
    }

    static void testAutomaticModuleExportThrowsIllegalStateException() {
        try {
            ModuleDescriptor.newAutomaticModule("auto.m").exports("p.q");
        } catch (IllegalStateException expected) {
            return;
        }
        throw new RuntimeException("IllegalStateException expected for automatic module export");
    }
}

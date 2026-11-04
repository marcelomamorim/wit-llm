/*
 * @test
 * @summary Checks ModuleDescriptor.Builder.exports(Set, String) observable behavior
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleDescriptor.Exports;
import java.util.EnumSet;
import java.util.Set;

public class ModuleDescriptorWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testValidExportIsRecorded() {
        Set<Exports.Modifier> ms = EnumSet.of(Exports.Modifier.SYNTHETIC);
        ModuleDescriptor.Builder b = ModuleDescriptor.newModule("m");
        ModuleDescriptor.Builder r = b.exports(ms, "p.q");
        check(r == b, "exports must return the same builder");
        ModuleDescriptor md = b.build();
        Exports found = null;
        for (Exports e : md.exports()) {
            if (e.source().equals("p.q")) {
                found = e;
            }
        }
        check(found != null, "exported package p.q not found");
        check(found.modifiers().contains(Exports.Modifier.SYNTHETIC),
              "SYNTHETIC modifier not preserved");
        check(!found.isQualified(), "unqualified export reported as qualified");
    }

    static void testEmptyModifiersValidExport() {
        ModuleDescriptor md = ModuleDescriptor.newModule("m2")
                .exports(EnumSet.noneOf(Exports.Modifier.class), "a.b")
                .build();
        Exports found = null;
        for (Exports e : md.exports()) {
            if (e.source().equals("a.b")) {
                found = e;
            }
        }
        check(found != null, "exported package a.b not found");
        check(found.modifiers().isEmpty(), "expected no modifiers");
    }

    static void testInvalidPackageNameRejectedInStrictBuilder() {
        ModuleDescriptor.Builder b = ModuleDescriptor.newModule("m3");
        boolean thrown = false;
        try {
            b.exports(EnumSet.noneOf(Exports.Modifier.class), "p..q");
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        check(thrown, "strict builder must reject invalid package name p..q");
    }

    public static void main(String[] args) {
        testValidExportIsRecorded();
        testEmptyModifiersValidExport();
        testInvalidPackageNameRejectedInStrictBuilder();
    }
}

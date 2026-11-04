/*
 * @test
 * @summary Witup-derived checks for ModuleDescriptor.Builder.exports(Set, String) in strict mode
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleDescriptor.Exports;
import java.util.Collections;
import java.util.Set;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testValidExportReturnsBuilderAndIsPublished();
        testInvalidPackageNameRejectedInStrictMode();
        testNullPackageNameRejectedInStrictMode();
    }

    static void testValidExportReturnsBuilderAndIsPublished() {
        ModuleDescriptor.Builder b = ModuleDescriptor.newModule("m1");
        Set<Exports.Modifier> ms = Collections.<Exports.Modifier>emptySet();
        ModuleDescriptor.Builder r = b.exports(ms, "com.foo");
        if (r != b) {
            throw new RuntimeException("exports(Set, String) must return the same builder");
        }
        ModuleDescriptor md = b.build();
        boolean found = false;
        for (Exports e : md.exports()) {
            if (e.source().equals("com.foo")) {
                found = true;
            }
        }
        if (!found) {
            throw new RuntimeException("exported package com.foo not found in descriptor");
        }
    }

    static void testInvalidPackageNameRejectedInStrictMode() {
        ModuleDescriptor.Builder b = ModuleDescriptor.newModule("m2");
        Set<Exports.Modifier> ms = Collections.<Exports.Modifier>emptySet();
        try {
            b.exports(ms, "com.1bad");
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for invalid package name");
    }

    static void testNullPackageNameRejectedInStrictMode() {
        ModuleDescriptor.Builder b = ModuleDescriptor.newModule("m3");
        Set<Exports.Modifier> ms = Collections.<Exports.Modifier>emptySet();
        try {
            b.exports(ms, null);
        } catch (IllegalArgumentException | NullPointerException expected) {
            return;
        }
        throw new RuntimeException("expected exception for null package name in strict mode");
    }
}

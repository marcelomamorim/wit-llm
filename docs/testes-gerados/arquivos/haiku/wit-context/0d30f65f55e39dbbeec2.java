/*
 * @test
 * @summary Tests ModuleDescriptor.Builder.uses(String) for automatic modules, duplicate service declarations and fluent return
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleDescriptor.Builder;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testAutomaticModuleUsesThrowsIllegalStateException();
        testDuplicateUsesThrowsIllegalStateException();
        testUsesReturnsSameBuilder();
        testUsesRecordsServiceInBuiltDescriptor();
    }

    static void testAutomaticModuleUsesThrowsIllegalStateException() {
        Builder builder = ModuleDescriptor.newAutomaticModule("m1");
        try {
            builder.uses("p.S");
            throw new RuntimeException("IllegalStateException expected for automatic module");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    static void testDuplicateUsesThrowsIllegalStateException() {
        Builder builder = ModuleDescriptor.newModule("m2");
        builder.uses("p.S");
        try {
            builder.uses("p.S");
            throw new RuntimeException("IllegalStateException expected for duplicate service");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    static void testUsesReturnsSameBuilder() {
        Builder builder = ModuleDescriptor.newModule("m3");
        Builder result = builder.uses("p.S");
        if (result != builder) {
            throw new RuntimeException("uses() should return the same Builder");
        }
    }

    static void testUsesRecordsServiceInBuiltDescriptor() {
        ModuleDescriptor md = ModuleDescriptor.newModule("m4")
                .uses("p.S")
                .build();
        if (!md.uses().contains("p.S")) {
            throw new RuntimeException("service p.S not present in uses()");
        }
    }
}

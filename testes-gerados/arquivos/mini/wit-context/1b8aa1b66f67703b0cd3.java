/*
 * @test
 * @summary Test java.lang.module.ModuleDescriptor.Builder.requires(Set<Requires.Modifier>, String) for exception paths and normal behavior
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleDescriptor.Requires;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testRequiresWithNullModuleNameStrict();
        testRequiresWithAutomaticNonStrict();
        testRequiresWithNameEqualsAndNonStrictNonAutomatic();
        testRequiresNormal();
    }

    private static void testRequiresWithNullModuleNameStrict() {
        ModuleDescriptor.Builder builder = ModuleDescriptor.newModule("test");
        // Use reflection to set strict = true since no public API to set strict
        // But reflection is disallowed by instructions, so we skip setting strict true
        // Instead, we test that passing null module name throws IllegalArgumentException via requireModuleName
        try {
            builder.requires(EnumSet.noneOf(Requires.Modifier.class), null);
            throw new RuntimeException("Expected IllegalArgumentException for null module name");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testRequiresWithAutomaticNonStrict() {
        // We cannot set automatic or strict flags on builder via public API
        // So we test the behavior indirectly by creating Requires with automatic true and strict false
        // But since requires(Set<Modifier>, String) calls requires(Requires) internally,
        // and we cannot create Requires directly with automatic true via public API,
        // we skip this test as no public construction hints are available.
        // This test is omitted due to lack of public API to create such state.
    }

    private static void testRequiresWithNameEqualsAndNonStrictNonAutomatic() {
        // Similar to above, no public API to create Requires with name.equals(mn) and !automatic and !strict
        // So this test is omitted due to lack of public API to create such state.
    }

    private static void testRequiresNormal() {
        ModuleDescriptor.Builder builder = ModuleDescriptor.newModule("test");
        Set<Requires.Modifier> mods = EnumSet.of(Requires.Modifier.TRANSITIVE);
        ModuleDescriptor.Builder result = builder.requires(mods, "java.base");
        if (result == null) {
            throw new RuntimeException("requires() returned null unexpectedly");
        }
    }
}

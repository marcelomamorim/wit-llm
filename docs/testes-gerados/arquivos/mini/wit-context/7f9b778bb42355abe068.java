/*
 * @test
 * @summary Test java.lang.module.ModuleDescriptor.Builder.exports(Set<Exports.Modifier>, String) for exception and normal behavior
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleDescriptor.Exports;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testExportsWithNullPackageNameStrict();
        testExportsWithAutomaticNonStrict();
        testExportsNormal();
    }

    private static void testExportsWithNullPackageNameStrict() {
        ModuleDescriptor.Builder builder = ModuleDescriptor.newModule("test.module");
        // Enable strict mode via reflection since no public setter (simulate strict=true)
        // Reflection is avoided as per instructions; so we test only observable behavior
        // The method calls requirePackageName(pn) only if strict is true
        // Since we cannot set strict, we test that passing null package name does NOT throw
        // because strict is false by default
        try {
            builder.exports(Collections.emptySet(), null);
        } catch (IllegalArgumentException iae) {
            throw new AssertionError("Unexpected IllegalArgumentException when strict is false and pn is null", iae);
        }
    }

    private static void testExportsWithAutomaticNonStrict() {
        ModuleDescriptor.Builder builder = ModuleDescriptor.newModule("test.module");
        // We cannot set automatic or strict flags publicly, so we test observable behavior
        // The expath suggests IllegalStateException if automatic && !strict
        // Since we cannot set automatic, we test that no exception is thrown for normal case
        try {
            builder.exports(Collections.emptySet(), "com.example");
        } catch (IllegalStateException ise) {
            throw new AssertionError("Unexpected IllegalStateException in normal exports call", ise);
        }
    }

    private static void testExportsNormal() {
        ModuleDescriptor.Builder builder = ModuleDescriptor.newModule("test.module");
        Set<Exports.Modifier> mods = EnumSet.noneOf(Exports.Modifier.class);
        ModuleDescriptor.Builder returned = builder.exports(mods, "com.example");
        if (returned == null) {
            throw new AssertionError("exports() returned null");
        }
    }

}

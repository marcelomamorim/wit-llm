/*
 * @test
 * @summary Test ModuleDescriptor.Builder.exports(Set<Exports.Modifier>, String) method
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleDescriptor.Builder;
import java.lang.module.ModuleDescriptor.Exports;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public class ModuleDescriptorWitupTest {
    public static void main(String[] args) {
        testExportsWithEmptyModifiersAndValidPackage();
        testExportsWithModifiersAndValidPackage();
        testExportsWithNullModifiers();
        testExportsWithEmptyPackageNameStrictFalse();
    }

    private static void testExportsWithEmptyModifiersAndValidPackage() {
        Builder builder = ModuleDescriptor.newModule("test.module");
        Set<Exports.Modifier> emptyModifiers = Collections.emptySet();
        String packageName = "com.example";
        Builder result = builder.exports(emptyModifiers, packageName);
        if (result == null) {
            throw new AssertionError("exports returned null with empty modifiers and valid package");
        }
    }

    private static void testExportsWithModifiersAndValidPackage() {
        Builder builder = ModuleDescriptor.newModule("test.module");
        Set<Exports.Modifier> modifiers = EnumSet.of(Exports.Modifier.SYNTHETIC);
        String packageName = "com.example.sub";
        Builder result = builder.exports(modifiers, packageName);
        if (result == null) {
            throw new AssertionError("exports returned null with modifiers and valid package");
        }
    }

    private static void testExportsWithNullModifiers() {
        Builder builder = ModuleDescriptor.newModule("test.module");
        try {
            builder.exports(null, "com.example");
            throw new AssertionError("Expected NullPointerException for null modifiers");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testExportsWithEmptyPackageNameStrictFalse() {
        Builder builder = ModuleDescriptor.newModule("test.module");
        // Disable strict to allow empty package name
        setStrict(builder, false);
        Set<Exports.Modifier> modifiers = Collections.emptySet();
        Builder result = builder.exports(modifiers, "");
        if (result == null) {
            throw new AssertionError("exports returned null with empty package name and strict false");
        }
    }

    // Reflection to disable strict flag since it's private
    private static void setStrict(Builder builder, boolean value) {
        try {
            java.lang.reflect.Field strictField = builder.getClass().getDeclaredField("strict");
            strictField.setAccessible(true);
            strictField.setBoolean(builder, value);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to set strict field", e);
        }
    }
}

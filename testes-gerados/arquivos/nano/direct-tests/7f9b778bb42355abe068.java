/* @test    @summary Test ModuleDescriptor.exports with valid modifiers and package name    @run main ModuleDescriptorWitupTest */

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.lang.module.ModuleDescriptor;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testExportsWithValidModifiersAndPackage();
        testExportsWithEmptyModifiers();
        testExportsWithNullModifiers();
        testExportsWithNullPackageName();
    }

    static void testExportsWithValidModifiersAndPackage() {
        Set<ModuleDescriptor.Exports.Modifier> modifiers = EnumSet.of(ModuleDescriptor.Exports.Modifier.OPEN);
        String packageName = "com.example";
        ModuleDescriptor.Builder builder = ModuleDescriptor.newBuilder();
        try {
            ModuleDescriptor.Builder result = builder.exports(modifiers, packageName);
            if (result == null) {
                throw new AssertionError("exports() returned null");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testExportsWithValidModifiersAndPackage", e);
        }
    }

    static void testExportsWithEmptyModifiers() {
        Set<ModuleDescriptor.Exports.Modifier> modifiers = Collections.emptySet();
        String packageName = "com.test";
        ModuleDescriptor.Builder builder = ModuleDescriptor.newBuilder();
        try {
            ModuleDescriptor.Builder result = builder.exports(modifiers, packageName);
            if (result == null) {
                throw new AssertionError("exports() returned null with empty modifiers");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testExportsWithEmptyModifiers", e);
        }
    }

    static void testExportsWithNullModifiers() {
        Set<ModuleDescriptor.Exports.Modifier> modifiers = null;
        String packageName = "com.null";
        ModuleDescriptor.Builder builder = ModuleDescriptor.newBuilder();
        try {
            builder.exports(modifiers, packageName);
            throw new AssertionError("Expected NullPointerException for null modifiers");
        } catch (NullPointerException expected) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testExportsWithNullModifiers", e);
        }
    }

    static void testExportsWithNullPackageName() {
        Set<ModuleDescriptor.Exports.Modifier> modifiers = EnumSet.of(ModuleDescriptor.Exports.Modifier.OPEN);
        String packageName = null;
        ModuleDescriptor.Builder builder = ModuleDescriptor.newBuilder();
        try {
            builder.exports(modifiers, packageName);
            throw new AssertionError("Expected NullPointerException for null package name");
        } catch (NullPointerException expected) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testExportsWithNullPackageName", e);
        }
    }
}

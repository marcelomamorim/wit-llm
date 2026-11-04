/*
 * @test
 * @summary Test java.lang.module.ModuleDescriptor.Builder.exports(String) for argument validation and state
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.util.Collections;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testExportsNullPackageName();
        testExportsAutomaticModuleStrictFalse();
    }

    private static void testExportsNullPackageName() {
        ModuleDescriptor.Builder builder = ModuleDescriptor.newModule("test.module");
        try {
            builder.exports((String) null);
            throw new Error("Expected IllegalArgumentException for null package name");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testExportsAutomaticModuleStrictFalse() {
        // Create a builder for an automatic module (automatic modules have no requires, exports, etc.)
        ModuleDescriptor.Builder builder = ModuleDescriptor.newAutomaticModule("auto.module");
        try {
            // The exports(String) method calls exports(emptySet(), pn) with strict=false for automatic modules
            // According to WIT, this should throw IllegalStateException
            builder.exports("some.package");
            throw new Error("Expected IllegalStateException for exports on automatic module with strict=false");
        } catch (IllegalStateException ise) {
            // expected
        }
    }
}

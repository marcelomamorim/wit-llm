/*
 * @test
 * @summary Test ModuleDescriptor.Builder.requires(String) method
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleDescriptor.Builder;
import java.util.EnumSet;
import java.util.Set;

public class ModuleDescriptorWitupTest {
    public static void main(String[] args) {
        testRequiresWithValidModuleName();
        testRequiresWithEmptyModuleName();
        testRequiresWithNullModuleName();
    }

    private static void testRequiresWithValidModuleName() {
        Builder builder = ModuleDescriptor.newModule("test.module");
        Builder result = builder.requires("java.base");
        if (result == null) {
            throw new AssertionError("requires(String) returned null for valid module name");
        }
        // The returned builder should be the same instance (fluent API)
        if (result != builder) {
            throw new AssertionError("requires(String) did not return the same builder instance");
        }
    }

    private static void testRequiresWithEmptyModuleName() {
        Builder builder = ModuleDescriptor.newModule("test.module");
        Builder result = builder.requires("");
        if (result == null) {
            throw new AssertionError("requires(String) returned null for empty module name");
        }
        if (result != builder) {
            throw new AssertionError("requires(String) did not return the same builder instance for empty module name");
        }
    }

    private static void testRequiresWithNullModuleName() {
        Builder builder = ModuleDescriptor.newModule("test.module");
        try {
            builder.requires(null);
            throw new AssertionError("requires(String) did not throw NullPointerException for null module name");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}

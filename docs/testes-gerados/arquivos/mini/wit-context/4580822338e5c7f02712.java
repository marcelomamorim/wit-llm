/*
 * @test
 * @summary Test ModuleDescriptor.Builder.requires(String) for expected exceptions and normal behavior
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.util.EnumSet;
import java.util.Set;
import java.lang.IllegalArgumentException;
import java.lang.IllegalStateException;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testRequiresWithNullNameStrict();
        testRequiresWithSameNameNonAutomaticNonStrict();
        testRequiresWithAutomaticNonStrict();
        testRequiresNormalCall();
        System.out.println("All tests passed.");
    }

    private static void testRequiresWithNullNameStrict() {
        ModuleDescriptor.Builder builder = new ModuleDescriptor.Builder("test.module");
        builder.strict(true);
        try {
            builder.requires((String) null);
            throw new AssertionError("Expected IllegalArgumentException for null module name in strict mode");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testRequiresWithSameNameNonAutomaticNonStrict() {
        ModuleDescriptor.Builder builder = new ModuleDescriptor.Builder("test.module");
        builder.strict(false);
        builder.automatic(false);
        builder.requires("test.module"); // should throw IllegalArgumentException
        try {
            builder.requires("test.module");
            throw new AssertionError("Expected IllegalArgumentException for requires with same name, non-automatic, non-strict");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testRequiresWithAutomaticNonStrict() {
        ModuleDescriptor.Builder builder = new ModuleDescriptor.Builder("test.module");
        builder.strict(false);
        builder.automatic(true);
        try {
            builder.requires("other.module");
            throw new AssertionError("Expected IllegalStateException for automatic module in non-strict mode");
        } catch (IllegalStateException ise) {
            // expected
        }
    }

    private static void testRequiresNormalCall() {
        ModuleDescriptor.Builder builder = new ModuleDescriptor.Builder("test.module");
        builder.strict(false);
        builder.automatic(false);
        ModuleDescriptor.Builder result = builder.requires("other.module");
        if (result == null) {
            throw new AssertionError("requires(String) returned null unexpectedly");
        }
    }

}

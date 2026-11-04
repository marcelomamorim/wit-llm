/*
 * @test
 * @summary Verifies ModuleDescriptor.Builder.requires(String) returns the same builder, records the dependence, and rejects null or duplicate names
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.util.Set;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testRequiresReturnsSameBuilderAndRecordsName();
        testRequiresNullThrowsNullPointerException();
        testDuplicateRequiresThrowsIllegalStateException();
    }

    static void testRequiresReturnsSameBuilderAndRecordsName() {
        ModuleDescriptor.Builder builder = ModuleDescriptor.newModule("m1");
        ModuleDescriptor.Builder returned = builder.requires("m2");
        if (returned != builder) {
            throw new RuntimeException("requires(String) did not return the same Builder");
        }
        ModuleDescriptor descriptor = builder.build();
        Set<ModuleDescriptor.Requires> requires = descriptor.requires();
        boolean found = requires.stream().anyMatch(r -> r.name().equals("m2"));
        if (!found) {
            throw new RuntimeException("required module m2 not present in descriptor");
        }
    }

    static void testRequiresNullThrowsNullPointerException() {
        ModuleDescriptor.Builder builder = ModuleDescriptor.newModule("m1");
        try {
            builder.requires((String) null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testDuplicateRequiresThrowsIllegalStateException() {
        ModuleDescriptor.Builder builder = ModuleDescriptor.newModule("m1");
        builder.requires("m2");
        try {
            builder.requires("m2");
            throw new RuntimeException("expected IllegalStateException");
        } catch (IllegalStateException expected) {
            // expected
        }
    }
}

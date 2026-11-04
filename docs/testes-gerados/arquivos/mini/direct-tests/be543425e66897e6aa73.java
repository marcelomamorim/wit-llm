/*
 * @test
 * @summary Test ModuleDescriptor.Builder.opens(String) method
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.util.Collections;

public class ModuleDescriptorWitupTest {
    public static void main(String[] args) {
        // Create a Builder instance
        ModuleDescriptor.Builder builder = ModuleDescriptor.newModule("test.module");

        // Call opens(String) with a normal package name
        ModuleDescriptor.Builder result = builder.opens("com.example.pkg");

        if (result == null) {
            throw new AssertionError("opens(String) returned null");
        }

        // The returned builder should be the same instance (fluent API)
        if (result != builder) {
            throw new AssertionError("opens(String) did not return the same builder instance");
        }

        // Call opens(String) with empty string package name
        ModuleDescriptor.Builder resultEmpty = builder.opens("");
        if (resultEmpty == null) {
            throw new AssertionError("opens("") returned null");
        }

        // Call opens(String) with a package name containing dots
        ModuleDescriptor.Builder resultDots = builder.opens("a.b.c");
        if (resultDots == null) {
            throw new AssertionError("opens(\"a.b.c\") returned null");
        }

        // Call opens(String) with a package name containing underscore
        ModuleDescriptor.Builder resultUnderscore = builder.opens("a_b_c");
        if (resultUnderscore == null) {
            throw new AssertionError("opens(\"a_b_c\") returned null");
        }

        // Call opens(String) with a package name containing digits
        ModuleDescriptor.Builder resultDigits = builder.opens("pkg123");
        if (resultDigits == null) {
            throw new AssertionError("opens(\"pkg123\") returned null");
        }

        System.out.println("All tests passed.");
    }
}

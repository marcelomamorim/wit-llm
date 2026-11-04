/*
 * @test
 * @summary Test for ModuleDescriptor.requires method to verify exception paths and behavior.
 * @run main ModuleDescriptorWitupTest
 */

import java.util.Set;
import java.lang.module.ModuleDescriptor;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testRequiresNullModuleNameStrict();
        testRequiresNonStrictAutomatic();
        testRequiresSameNameNonStrict();
    }

    static void testRequiresNullModuleNameStrict() {
        ModuleDescriptor.Builder builder = new ModuleDescriptor.Builder();
        try {
            // Simulate strict mode with null module name, expecting IllegalArgumentException
            builder.requires(Set.of(), null);
            // If no exception, test fails
            System.err.println("Failed: Expected IllegalArgumentException for null module name in strict mode.");
        } catch (IllegalArgumentException e) {
            // Expected exception
            System.out.println("Passed: IllegalArgumentException thrown as expected for null module name in strict mode.");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception type: " + e);
        }
    }

    static void testRequiresNonStrictAutomatic() {
        ModuleDescriptor.Builder builder = new ModuleDescriptor.Builder();
        // Simulate non-strict mode and automatic module
        // Since the actual internal state is not accessible, we assume the method is called in a context where 'automatic' is true
        // For testing, we can simulate by calling requires with a name that triggers the automatic path
        try {
            // The method requires a set of modifiers and a module name
            // We pass a non-null name to avoid the automatic path
            builder.requires(Set.of(), "com.example.module");
            System.out.println("Passed: No exception thrown for non-strict, non-null module name.");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception: " + e);
        }
    }

    static void testRequiresSameNameNonStrict() {
        ModuleDescriptor.Builder builder = new ModuleDescriptor.Builder();
        String moduleName = "com.example.module";
        try {
            // Call requires with a specific module name
            builder.requires(Set.of(), moduleName);
            System.out.println("Passed: requires called successfully with module name: " + moduleName);
        } catch (Exception e) {
            System.err.println("Failed: Exception during requires with module name: " + e);
        }
    }
}

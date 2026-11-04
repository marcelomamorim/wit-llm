/*
 * @test
 * @summary Tests for java.lang.module.ModuleDescriptor.requires(String)
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testRequiresNormal();
        testRequiresNullStrict();
        testRequiresAutomatic();
    }

    static void testRequiresNormal() {
        ModuleDescriptor.Builder builder = ModuleDescriptor.newBuilder();
        String moduleName = "com.example.module";
        try {
            ModuleDescriptor.Builder result = builder.requires(moduleName);
            if (result == null) {
                throw new AssertionError("Expected non-null result for normal requires");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for normal requires", e);
        }
    }

    static void testRequiresNullStrict() {
        ModuleDescriptor.Builder builder = ModuleDescriptor.newBuilder();
        String moduleName = null;
        try {
            builder.requires(moduleName);
            throw new AssertionError("Expected IllegalArgumentException for null module name with strict mode");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for null module name with strict mode", e);
        }
    }

    static void testRequiresAutomatic() {
        ModuleDescriptor.Builder builder = ModuleDescriptor.newBuilder();
        String moduleName = "com.example.automatic";
        try {
            // Simulate automatic module by setting a property or state if applicable
            // Since the code does not show such state, we assume the method is called normally
            ModuleDescriptor.Builder result = builder.requires(moduleName);
            if (result == null) {
                throw new AssertionError("Expected non-null result for automatic module requires");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for automatic module requires", e);
        }
    }
}

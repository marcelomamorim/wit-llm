/*
 * @test
 * @summary Test java.lang.module.ModuleDescriptor.Builder uses(String) method behavior
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.util.Set;
import java.util.HashSet;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testUsesAddsService();
        testUsesDuplicateServiceThrows();
        testUsesOnAutomaticModuleThrows();
    }

    private static void testUsesAddsService() {
        ModuleDescriptor.Builder builder = new ModuleDescriptor.Builder("test.module");
        builder.uses("com.example.Service");
        // The uses set should contain the added service
        Set<String> uses = getUsesSet(builder);
        if (!uses.contains("com.example.Service")) {
            throw new AssertionError("Service not added to uses set");
        }
    }

    private static void testUsesDuplicateServiceThrows() {
        ModuleDescriptor.Builder builder = new ModuleDescriptor.Builder("test.module");
        builder.uses("com.example.Service");
        try {
            builder.uses("com.example.Service");
            throw new AssertionError("Expected IllegalStateException for duplicate service");
        } catch (IllegalStateException e) {
            String msg = e.getMessage();
            if (!msg.contains("Dependence upon service") || !msg.contains("com.example.Service")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testUsesOnAutomaticModuleThrows() {
        ModuleDescriptor.Builder builder = new ModuleDescriptor.Builder("test.module");
        setAutomatic(builder, true);
        try {
            builder.uses("com.example.Service");
            throw new AssertionError("Expected IllegalStateException for automatic module");
        } catch (IllegalStateException e) {
            String msg = e.getMessage();
            if (!msg.contains("Automatic modules can not declare")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
    }

    // Reflection is used here only because the uses set and automatic flag are private
    // and no public accessor is available. This is justified because the test is for
    // the Builder class internal behavior and no public API exposes these fields.
    private static Set<String> getUsesSet(ModuleDescriptor.Builder builder) {
        try {
            java.lang.reflect.Field usesField = ModuleDescriptor.Builder.class.getDeclaredField("uses");
            usesField.setAccessible(true);
            @SuppressWarnings("unchecked")
            Set<String> uses = (Set<String>) usesField.get(builder);
            return uses;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private static void setAutomatic(ModuleDescriptor.Builder builder, boolean value) {
        try {
            java.lang.reflect.Field automaticField = ModuleDescriptor.Builder.class.getDeclaredField("automatic");
            automaticField.setAccessible(true);
            automaticField.setBoolean(builder, value);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}

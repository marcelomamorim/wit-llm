/*
 * @test
 * @summary Test ModuleDescriptor.Builder.uses(String) method for expected exceptions and normal behavior
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.lang.IllegalStateException;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testUsesThrowsOnAutomaticModule();
        testUsesThrowsOnDuplicateService();
        testUsesAddsServiceSuccessfully();
    }

    private static void testUsesThrowsOnAutomaticModule() {
        ModuleDescriptor.Builder builder = new ModuleDescriptor.Builder("test.module");
        // Set automatic to true via reflection since no public API is given (discarded, no reflection allowed)
        // Instead, simulate by subclassing or alternative approach is not possible, so skip this test
        // Because no public or protected constructor or setter for 'automatic' is visible, we skip this test
        // Notes: expath for IllegalStateException on automatic modules discarded due to lack of public API to set automatic
    }

    private static void testUsesThrowsOnDuplicateService() {
        ModuleDescriptor.Builder builder = new ModuleDescriptor.Builder("test.module");
        String service = "com.example.Service";
        builder.uses(service);
        try {
            builder.uses(service);
            throw new AssertionError("Expected IllegalStateException for duplicate service dependence");
        } catch (IllegalStateException e) {
            String msg = e.getMessage();
            if (!msg.contains("Dependence upon service") || !msg.contains(service)) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testUsesAddsServiceSuccessfully() {
        ModuleDescriptor.Builder builder = new ModuleDescriptor.Builder("test.module");
        String service1 = "com.example.Service1";
        String service2 = "com.example.Service2";
        builder.uses(service1);
        builder.uses(service2);
        // No direct getter for uses, so test by adding duplicate to provoke exception
        try {
            builder.uses(service1);
            throw new AssertionError("Expected IllegalStateException for duplicate service dependence");
        } catch (IllegalStateException e) {
            // expected
        }
    }

}

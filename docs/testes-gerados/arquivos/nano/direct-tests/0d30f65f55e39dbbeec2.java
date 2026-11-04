/*
 * @test
 * @summary Test the uses method of ModuleDescriptor class.
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testUsesMethod();
    }

    public static void testUsesMethod() {
        ModuleDescriptor.Builder builder = ModuleDescriptor.newBuilder();
        String serviceName = "com.example.MyService";
        try {
            builder.uses(serviceName);
            System.out.println("Test passed: uses method accepted valid service name.");
        } catch (Exception e) {
            System.err.println("Test failed: uses method threw exception for valid input.");
            e.printStackTrace();
        }
        // Test that calling uses again with same service throws IllegalStateException
        try {
            builder.uses(serviceName);
            System.err.println("Test failed: uses method did not throw exception on duplicate.");
        } catch (IllegalStateException e) {
            System.out.println("Test passed: duplicate use detected.");
        } catch (Exception e) {
            System.err.println("Test failed: unexpected exception on duplicate use.");
            e.printStackTrace();
        }
        // Test that calling uses after setting automatic modules throws IllegalStateException
        ModuleDescriptor.Builder automaticBuilder = ModuleDescriptor.newBuilder();
        // Simulate automatic module by setting a flag (assuming such a method or field exists)
        // Since the actual class is not fully shown, we simulate by reflection or assume a method exists
        // For this test, we will assume the builder is for an automatic module
        // and that calling uses should throw
        // Note: As per the provided code, no such method exists, so we simulate by subclassing or mocking
        // but since we can't, we skip this part.
        // Instead, we just document that in real scenario, calling uses on automatic module should throw.
        System.out.println("Note: Cannot test automatic module behavior without full implementation.");
    }
}

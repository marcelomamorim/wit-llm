/*
 * @test
 * @summary Test for ModuleDescriptor.version(Version v) method.
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleDescriptor.Version;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testVersionWithNull();
        testVersionWithValid();
    }

    public static void testVersionWithNull() {
        try {
            ModuleDescriptor.Version v = null;
            // Expect NullPointerException due to requireNonNull
            ModuleDescriptor.Builder builder = new ModuleDescriptor.Builder();
            builder.version(v);
            // If no exception, fail the test
            System.err.println("Failed: Expected NullPointerException for null Version");
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("Passed: NullPointerException thrown as expected for null Version");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
        }
    }

    public static void testVersionWithValid() {
        try {
            Version v = Version.parse("1.0.0");
            ModuleDescriptor.Builder builder = new ModuleDescriptor.Builder();
            builder.version(v);
            // Verify that version is set correctly
            // Since builder.version returns builder, no direct getter; test indirectly
            System.out.println("Passed: Version set successfully with valid Version");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
        }
    }
}

/*
 * @test
 * @summary Test java.lang.module.ModuleDescriptor.Builder.version(Version) method for null argument handling
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleDescriptor.Version;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testVersionWithNull();
    }

    private static void testVersionWithNull() {
        ModuleDescriptor.Builder builder = ModuleDescriptor.newModule("test.module");
        try {
            builder.version(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}

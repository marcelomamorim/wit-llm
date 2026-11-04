/*
 * @test
 * @summary Test ModuleDescriptor.Builder.version(Version) method
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleDescriptor.Builder;
import java.lang.module.ModuleDescriptor.Version;

public class ModuleDescriptorWitupTest {
    public static void main(String[] args) {
        testVersionWithNonNull();
        testVersionWithNull();
    }

    private static void testVersionWithNonNull() {
        Builder builder = ModuleDescriptor.newModule("test.module");
        Version version = Version.parse("1.2.3");
        Builder returned = builder.version(version);
        if (returned != builder) {
            throw new AssertionError("version() should return the same Builder instance");
        }
        // No direct getter for version, so test by building and checking version string
        ModuleDescriptor md = builder.build();
        Version mdVersion = md.version().orElse(null);
        if (!version.equals(mdVersion)) {
            throw new AssertionError("ModuleDescriptor version not set correctly");
        }
    }

    private static void testVersionWithNull() {
        Builder builder = ModuleDescriptor.newModule("test.module.null");
        try {
            builder.version(null);
            throw new AssertionError("version(null) should throw NullPointerException");
        } catch (NullPointerException npe) {
            // expected
        }
    }
}

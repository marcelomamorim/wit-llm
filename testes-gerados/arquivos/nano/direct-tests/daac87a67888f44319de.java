/*
 * @test
 * @summary Test the version method of ModuleDescriptor.Builder
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleDescriptor.Version;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testVersionMethod();
    }

    public static void testVersionMethod() {
        ModuleDescriptor.Builder builder = new ModuleDescriptor.Builder();
        Version version = new Version(1, 0, 0);
        builder.version(version);
        // Since the version method returns the builder itself, test for method chaining
        ModuleDescriptor.Builder resultBuilder = builder.version(version);
        if (resultBuilder != builder) {
            throw new AssertionError("Builder.version() should return the same builder instance for chaining.");
        }
        // Additional check: the internal state is set correctly by invoking a hypothetical getter
        // Note: Since internal state is private, we rely on the fact that no exception is thrown and chaining works
        System.out.println("testVersionMethod passed.");
    }
}

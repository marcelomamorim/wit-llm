/*
 * @test
 * @summary Test ModuleDescriptor.Builder.exports(String) method
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.util.Collections;

public class ModuleDescriptorWitupTest {
    public static void main(String[] args) {
        // Create a Builder instance
        ModuleDescriptor.Builder builder = ModuleDescriptor.newModule("test.module");

        // Test exports with a normal package name
        ModuleDescriptor.Builder b1 = builder.exports("com.example.pkg");
        if (b1 == null) {
            throw new AssertionError("exports(String) returned null");
        }

        // Test exports with empty string package name
        ModuleDescriptor.Builder b2 = builder.exports("");
        if (b2 == null) {
            throw new AssertionError("exports(String) with empty string returned null");
        }

        // Test exports with package name containing dots
        ModuleDescriptor.Builder b3 = builder.exports("a.b.c");
        if (b3 == null) {
            throw new AssertionError("exports(String) with dotted package name returned null");
        }

        // Test exports with package name containing underscore
        ModuleDescriptor.Builder b4 = builder.exports("my_pkg");
        if (b4 == null) {
            throw new AssertionError("exports(String) with underscore package name returned null");
        }

        // Test exports with package name containing digits
        ModuleDescriptor.Builder b5 = builder.exports("pkg123");
        if (b5 == null) {
            throw new AssertionError("exports(String) with digits package name returned null");
        }

        // Test exports with package name containing uppercase letters
        ModuleDescriptor.Builder b6 = builder.exports("PkgName");
        if (b6 == null) {
            throw new AssertionError("exports(String) with uppercase letters returned null");
        }

        System.out.println("All exports(String) tests passed.");
    }
}

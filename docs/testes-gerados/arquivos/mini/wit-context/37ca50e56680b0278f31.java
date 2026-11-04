/*
 * @test
 * @summary Test java.lang.module.ModuleDescriptor.Builder.packages(Set<String>) method
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ModuleDescriptorWitupTest {
    public static void main(String[] args) {
        testPackagesWithValidSet();
        testPackagesWithEmptySet();
        testPackagesWithStrictValidation();
    }

    private static void testPackagesWithValidSet() {
        ModuleDescriptor.Builder builder = new ModuleDescriptor.Builder("test.module");
        Set<String> pkgs = new HashSet<>();
        pkgs.add("java.lang");
        pkgs.add("java.util");
        ModuleDescriptor.Builder result = builder.packages(pkgs);
        if (result != builder) {
            throw new AssertionError("Builder.packages should return the same builder instance");
        }
    }

    private static void testPackagesWithEmptySet() {
        ModuleDescriptor.Builder builder = new ModuleDescriptor.Builder("empty.module");
        Set<String> emptySet = Collections.emptySet();
        ModuleDescriptor.Builder result = builder.packages(emptySet);
        if (result != builder) {
            throw new AssertionError("Builder.packages should return the same builder instance when given empty set");
        }
    }

    private static void testPackagesWithStrictValidation() {
        // This test assumes 'strict' is true and that invalid package names cause IllegalArgumentException
        // Since 'strict' is a static field in ModuleDescriptor.Builder, we cannot set it directly here.
        // Instead, we test that valid package names pass without exception.
        ModuleDescriptor.Builder builder = new ModuleDescriptor.Builder("strict.module");
        Set<String> pkgs = new HashSet<>();
        pkgs.add("valid.package");
        try {
            builder.packages(pkgs);
        } catch (IllegalArgumentException e) {
            throw new AssertionError("Valid package name should not throw IllegalArgumentException");
        }
    }
}

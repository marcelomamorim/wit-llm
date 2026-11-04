/*
 * @test
 * @summary Test ModuleDescriptor.Builder.packages(Set<String>) method
 * @run main ModuleDescriptorWitupTest
 */

import java.util.HashSet;
import java.util.Set;
import java.util.Collections;
import java.lang.module.ModuleDescriptor;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testPackagesAddsAllValidPackages();
        testPackagesRejectsInvalidPackageNameWhenStrict();
    }

    private static void testPackagesAddsAllValidPackages() {
        ModuleDescriptor.Builder builder = ModuleDescriptor.newModule("test.module");
        Set<String> pkgs = new HashSet<>();
        pkgs.add("java.lang");
        pkgs.add("java.util");
        builder.packages(pkgs);
        Set<String> result = builder.packages();
        if (!result.containsAll(pkgs)) {
            throw new AssertionError("Builder.packages did not add all packages");
        }
    }

    private static void testPackagesRejectsInvalidPackageNameWhenStrict() {
        // The method uses Checks.requirePackageName which throws IllegalArgumentException
        // for invalid package names when strict is true.
        // We test that an invalid package name causes exception.

        ModuleDescriptor.Builder builder = ModuleDescriptor.newModule("test.module");
        Set<String> invalidPkgs = new HashSet<>();
        invalidPkgs.add("invalid-package-name!");

        try {
            builder.packages(invalidPkgs);
            throw new AssertionError("Expected IllegalArgumentException for invalid package name");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}

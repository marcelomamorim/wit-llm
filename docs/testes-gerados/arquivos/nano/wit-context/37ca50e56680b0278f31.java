/*
 * @test
 * @summary Test for ModuleDescriptor.packages(Set<String>) method.
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.util.HashSet;
import java.util.Set;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testPackagesWithNullSet();
        testPackagesWithEmptySet();
        testPackagesWithValidSet();
        testPackagesWithInvalidPackageName();
    }

    static void testPackagesWithNullSet() {
        try {
            ModuleDescriptor.Builder builder = ModuleDescriptor.newBuilder();
            builder.packages(null);
            System.out.println("testPackagesWithNullSet: Failed - no exception thrown");
        } catch (NullPointerException e) {
            System.out.println("testPackagesWithNullSet: Passed");
        } catch (Exception e) {
            System.out.println("testPackagesWithNullSet: Failed - wrong exception: " + e);
        }
    }

    static void testPackagesWithEmptySet() {
        try {
            ModuleDescriptor.Builder builder = ModuleDescriptor.newBuilder();
            builder.packages(new HashSet<>());
            System.out.println("testPackagesWithEmptySet: Passed");
        } catch (Exception e) {
            System.out.println("testPackagesWithEmptySet: Failed - exception: " + e);
        }
    }

    static void testPackagesWithValidSet() {
        try {
            Set<String> pkgs = new HashSet<>();
            pkgs.add("com.example");
            ModuleDescriptor.Builder builder = ModuleDescriptor.newBuilder();
            builder.packages(pkgs);
            System.out.println("testPackagesWithValidSet: Passed");
        } catch (Exception e) {
            System.out.println("testPackagesWithValidSet: Failed - exception: " + e);
        }
    }

    static void testPackagesWithInvalidPackageName() {
        try {
            Set<String> pkgs = new HashSet<>();
            pkgs.add("");
            ModuleDescriptor.Builder builder = ModuleDescriptor.newBuilder();
            builder.packages(pkgs);
            System.out.println("testPackagesWithInvalidPackageName: Failed - no exception thrown");
        } catch (NullPointerException e) {
            System.out.println("testPackagesWithInvalidPackageName: Passed");
        } catch (IllegalArgumentException e) {
            System.out.println("testPackagesWithInvalidPackageName: Passed");
        } catch (Exception e) {
            System.out.println("testPackagesWithInvalidPackageName: Failed - wrong exception: " + e);
        }
    }
}

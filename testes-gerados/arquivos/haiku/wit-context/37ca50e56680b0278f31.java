/*
 * @test
 * @summary Verify ModuleDescriptor.Builder.packages accepts valid package names, rejects invalid names in strict mode, and rejects null
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.util.HashSet;
import java.util.Set;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) throws Exception {
        testValidPackagesAccepted();
        testFluentReturnsSameBuilder();
        testInvalidPackageNamesRejected();
        testNullSetRejected();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testValidPackagesAccepted() {
        Set<String> pns = new HashSet<>();
        pns.add("p");
        pns.add("q.r");
        ModuleDescriptor.Builder builder = ModuleDescriptor.newModule("m");
        builder.packages(pns);
        ModuleDescriptor md = builder.build();
        Set<String> result = md.packages();
        check(result.contains("p"), "package p missing");
        check(result.contains("q.r"), "package q.r missing");
        check(result.size() == 2, "unexpected package count: " + result.size());
    }

    static void testFluentReturnsSameBuilder() {
        ModuleDescriptor.Builder builder = ModuleDescriptor.newModule("m");
        check(builder.packages(Set.of("x.y")) == builder, "packages must return this builder");
    }

    static void testInvalidPackageNamesRejected() {
        String[] invalid = { "1bad.x", "a-b" };
        for (String name : invalid) {
            ModuleDescriptor.Builder builder = ModuleDescriptor.newModule("m");
            boolean thrown = false;
            try {
                builder.packages(Set.of(name));
            } catch (IllegalArgumentException expected) {
                thrown = true;
            }
            check(thrown, "expected IllegalArgumentException for package name: " + name);
        }
    }

    static void testNullSetRejected() {
        ModuleDescriptor.Builder builder = ModuleDescriptor.newModule("m");
        boolean thrown = false;
        try {
            builder.packages(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null set");
    }
}

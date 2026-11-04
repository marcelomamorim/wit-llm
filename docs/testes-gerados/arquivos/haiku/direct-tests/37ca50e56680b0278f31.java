/*
 * @test
 * @summary Tests ModuleDescriptor.Builder.packages(Set) for strict and non-strict builders
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.util.HashSet;
import java.util.Set;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testValidPackagesAreAdded();
        testPackagesAccumulateAcrossCalls();
        testStrictRejectsInvalidPackageName();
        testNonStrictAcceptsInvalidPackageName();
        testStrictBuilderCopiesInputSet();
    }

    static void testValidPackagesAreAdded() {
        Set<String> pns = new HashSet<>();
        pns.add("p");
        pns.add("q.r");
        ModuleDescriptor md = ModuleDescriptor.newModule("m")
                .packages(pns)
                .build();
        check(md.packages().equals(pns),
              "expected packages " + pns + " but got " + md.packages());
    }

    static void testPackagesAccumulateAcrossCalls() {
        Set<String> a = new HashSet<>();
        a.add("p");
        Set<String> b = new HashSet<>();
        b.add("q");
        ModuleDescriptor md = ModuleDescriptor.newModule("m")
                .packages(a)
                .packages(b)
                .build();
        Set<String> expected = new HashSet<>();
        expected.add("p");
        expected.add("q");
        check(md.packages().equals(expected),
              "expected " + expected + " but got " + md.packages());
    }

    static void testStrictRejectsInvalidPackageName() {
        Set<String> pns = new HashSet<>();
        pns.add("p.1x");
        boolean thrown = false;
        try {
            ModuleDescriptor.newModule("m").packages(pns);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "expected IllegalArgumentException for invalid package name in strict builder");
    }

    static void testNonStrictAcceptsInvalidPackageName() {
        Set<String> pns = new HashSet<>();
        pns.add("p.1x");
        ModuleDescriptor md = ModuleDescriptor.newAutomaticModule("m")
                .packages(pns)
                .build();
        check(md.packages().contains("p.1x"),
              "expected p.1x in packages but got " + md.packages());
    }

    static void testStrictBuilderCopiesInputSet() {
        Set<String> pns = new HashSet<>();
        pns.add("p");
        ModuleDescriptor.Builder builder = ModuleDescriptor.newModule("m");
        builder.packages(pns);
        pns.add("x.y");
        ModuleDescriptor md = builder.build();
        check(!md.packages().contains("x.y"),
              "builder should not reflect later mutation of input set");
        check(md.packages().contains("p"), "expected p in packages");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}

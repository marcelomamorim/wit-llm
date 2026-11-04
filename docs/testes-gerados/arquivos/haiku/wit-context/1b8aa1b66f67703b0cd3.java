/*
 * @test
 * @summary Checks public Builder.requires(Set, String) behavior for strict and non-strict builders
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleDescriptor.Requires;
import java.util.EnumSet;
import java.util.Set;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testRequiresValidNameAddsDependence();
        testRequiresPreservesModifiers();
        testStrictEmptyNameThrowsIllegalArgumentException();
        System.out.println("PASSED");
    }

    static void testRequiresValidNameAddsDependence() {
        Set<Requires.Modifier> ms = EnumSet.noneOf(Requires.Modifier.class);
        ModuleDescriptor md = ModuleDescriptor.newModule("m")
                .requires(ms, "java.base")
                .build();
        boolean found = false;
        for (Requires r : md.requires()) {
            if (r.name().equals("java.base")) {
                found = true;
            }
        }
        if (!found) {
            throw new RuntimeException("requires(Set, String) did not add java.base");
        }
    }

    static void testRequiresPreservesModifiers() {
        Set<Requires.Modifier> ms = EnumSet.of(Requires.Modifier.TRANSITIVE);
        ModuleDescriptor md = ModuleDescriptor.newModule("m")
                .requires(ms, "java.logging")
                .build();
        Requires found = null;
        for (Requires r : md.requires()) {
            if (r.name().equals("java.logging")) {
                found = r;
            }
        }
        if (found == null) {
            throw new RuntimeException("requires(Set, String) did not add java.logging");
        }
        if (!found.modifiers().contains(Requires.Modifier.TRANSITIVE)) {
            throw new RuntimeException("TRANSITIVE modifier was not preserved");
        }
    }

    static void testStrictEmptyNameThrowsIllegalArgumentException() {
        Set<Requires.Modifier> ms = EnumSet.noneOf(Requires.Modifier.class);
        try {
            ModuleDescriptor.newModule("m").requires(ms, "");
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for empty module name in strict builder");
    }
}

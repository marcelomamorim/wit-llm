/*
 * @test
 * @summary Verifies ModuleDescriptor.Builder.requires(Set, String) stores the requires with its modifiers and rejects invalid module names in strict builders
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleDescriptor.Requires;
import java.util.Set;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testRequiresWithModifierIsStored();
        testRequiresWithEmptyModifiersIsStored();
        testInvalidModuleNameRejectedInStrictBuilder();
    }

    static void testRequiresWithModifierIsStored() {
        ModuleDescriptor.Builder b = ModuleDescriptor.newModule("m1");
        b.requires(Set.of(Requires.Modifier.TRANSITIVE), "java.sql");
        ModuleDescriptor md = b.build();
        Requires r = findRequires(md, "java.sql");
        if (r == null) {
            throw new AssertionError("requires java.sql not found");
        }
        if (!r.modifiers().contains(Requires.Modifier.TRANSITIVE)) {
            throw new AssertionError("TRANSITIVE modifier missing: " + r.modifiers());
        }
    }

    static void testRequiresWithEmptyModifiersIsStored() {
        ModuleDescriptor.Builder b = ModuleDescriptor.newModule("m2");
        b.requires(Set.of(), "java.logging");
        ModuleDescriptor md = b.build();
        Requires r = findRequires(md, "java.logging");
        if (r == null) {
            throw new AssertionError("requires java.logging not found");
        }
        if (r.modifiers().contains(Requires.Modifier.TRANSITIVE)
                || r.modifiers().contains(Requires.Modifier.STATIC)) {
            throw new AssertionError("unexpected modifiers: " + r.modifiers());
        }
    }

    static void testInvalidModuleNameRejectedInStrictBuilder() {
        ModuleDescriptor.Builder b = ModuleDescriptor.newModule("m3");
        try {
            b.requires(Set.of(), "a..b");
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new AssertionError("IllegalArgumentException expected for invalid module name");
    }

    static Requires findRequires(ModuleDescriptor md, String name) {
        for (Requires r : md.requires()) {
            if (r.name().equals(name)) {
                return r;
            }
        }
        return null;
    }
}

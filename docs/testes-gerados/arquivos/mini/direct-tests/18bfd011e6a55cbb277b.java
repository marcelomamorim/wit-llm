/*
 * @test
 * @summary Test java.lang.module.ModuleDescriptor.newModule method
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleDescriptor.Builder;
import java.lang.module.ModuleDescriptor.Modifier;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class ModuleDescriptorWitupTest {
    public static void main(String[] args) {
        testNewModuleWithEmptyModifiers();
        testNewModuleWithSingleModifier();
        testNewModuleWithAutomaticModifierAlone();
        testNewModuleWithAutomaticAndOtherModifierThrows();
    }

    private static void testNewModuleWithEmptyModifiers() {
        Set<Modifier> empty = Collections.emptySet();
        Builder b = ModuleDescriptor.newModule("test.module.empty", empty);
        if (b == null) {
            throw new AssertionError("Builder should not be null for empty modifiers");
        }
        if (!"test.module.empty".equals(b.name())) {
            throw new AssertionError("Builder name mismatch");
        }
    }

    private static void testNewModuleWithSingleModifier() {
        Set<Modifier> mods = EnumSet.of(Modifier.OPEN);
        Builder b = ModuleDescriptor.newModule("test.module.open", mods);
        if (b == null) {
            throw new AssertionError("Builder should not be null for single modifier");
        }
        if (!"test.module.open".equals(b.name())) {
            throw new AssertionError("Builder name mismatch");
        }
        if (!b.modifiers().contains(Modifier.OPEN)) {
            throw new AssertionError("Builder modifiers should contain OPEN");
        }
    }

    private static void testNewModuleWithAutomaticModifierAlone() {
        Set<Modifier> mods = EnumSet.of(Modifier.AUTOMATIC);
        Builder b = ModuleDescriptor.newModule("test.module.automatic", mods);
        if (b == null) {
            throw new AssertionError("Builder should not be null for AUTOMATIC modifier");
        }
        if (!"test.module.automatic".equals(b.name())) {
            throw new AssertionError("Builder name mismatch");
        }
        if (!b.modifiers().contains(Modifier.AUTOMATIC)) {
            throw new AssertionError("Builder modifiers should contain AUTOMATIC");
        }
    }

    private static void testNewModuleWithAutomaticAndOtherModifierThrows() {
        Set<Modifier> mods = new HashSet<>();
        mods.add(Modifier.AUTOMATIC);
        mods.add(Modifier.OPEN);
        try {
            ModuleDescriptor.newModule("test.module.invalid", mods);
            throw new AssertionError("Expected IllegalArgumentException for AUTOMATIC with other modifiers");
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("AUTOMATIC cannot be used with")) {
                throw new AssertionError("Exception message unexpected: " + msg);
            }
        }
    }
}

/*
 * @test
 * @summary Test java.lang.module.ModuleDescriptor.newModule(String, Set<Modifier>) for basic validation and behavior
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
        testNewModuleWithAutomaticModifierAlone();
        testNewModuleWithAutomaticAndOtherModifier();
    }

    private static void testNewModuleWithEmptyModifiers() {
        Set<Modifier> emptyMods = Collections.emptySet();
        Builder builder = ModuleDescriptor.newModule("test.module.empty", emptyMods);
        if (builder == null) {
            throw new AssertionError("Builder should not be null for empty modifiers");
        }
    }

    private static void testNewModuleWithAutomaticModifierAlone() {
        Set<Modifier> autoMod = EnumSet.of(Modifier.AUTOMATIC);
        Builder builder = ModuleDescriptor.newModule("test.module.automatic", autoMod);
        if (builder == null) {
            throw new AssertionError("Builder should not be null for AUTOMATIC modifier alone");
        }
    }

    private static void testNewModuleWithAutomaticAndOtherModifier() {
        Set<Modifier> mods = new HashSet<>();
        mods.add(Modifier.AUTOMATIC);
        mods.add(Modifier.OPEN);
        boolean threw = false;
        try {
            ModuleDescriptor.newModule("test.module.invalid", mods);
        } catch (IllegalArgumentException iae) {
            threw = true;
            String msg = iae.getMessage();
            if (msg == null || !msg.contains("AUTOMATIC cannot be used with")) {
                throw new AssertionError("IllegalArgumentException message unexpected: " + msg);
            }
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException when AUTOMATIC used with other modifiers");
        }
    }
}

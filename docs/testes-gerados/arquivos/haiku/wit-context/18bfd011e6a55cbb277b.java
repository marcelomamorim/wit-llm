/*
 * @test
 * @summary Witup-derived tests for ModuleDescriptor.newModule(String, Set<Modifier>)
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleDescriptor.Modifier;
import java.util.EnumSet;
import java.util.Set;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testAutomaticAloneAccepted();
        testEmptyModifiersAccepted();
        testOpenOnlyAccepted();
        testAutomaticWithOtherModifierRejected();
        testAutomaticWithOpenRejected();
        testNullModifiersThrowsNPE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testAutomaticAloneAccepted() {
        Set<Modifier> ms = EnumSet.of(Modifier.AUTOMATIC);
        ModuleDescriptor.Builder b = ModuleDescriptor.newModule("m1", ms);
        check(b != null, "newModule with AUTOMATIC alone returned null");
    }

    static void testEmptyModifiersAccepted() {
        Set<Modifier> ms = EnumSet.noneOf(Modifier.class);
        ModuleDescriptor.Builder b = ModuleDescriptor.newModule("m2", ms);
        check(b != null, "newModule with empty modifiers returned null");
    }

    static void testOpenOnlyAccepted() {
        Set<Modifier> ms = EnumSet.of(Modifier.OPEN);
        ModuleDescriptor.Builder b = ModuleDescriptor.newModule("m3", ms);
        check(b != null, "newModule with OPEN returned null");
    }

    static void testAutomaticWithOtherModifierRejected() {
        Set<Modifier> ms = EnumSet.of(Modifier.AUTOMATIC, Modifier.SYNTHETIC);
        try {
            ModuleDescriptor.newModule("m4", ms);
            throw new RuntimeException("expected IllegalArgumentException for AUTOMATIC with other modifier");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testAutomaticWithOpenRejected() {
        Set<Modifier> ms = EnumSet.of(Modifier.AUTOMATIC, Modifier.OPEN);
        try {
            ModuleDescriptor.newModule("m5", ms);
            throw new RuntimeException("expected IllegalArgumentException for AUTOMATIC with OPEN");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testNullModifiersThrowsNPE() {
        try {
            ModuleDescriptor.newModule("m6", null);
            throw new RuntimeException("expected NullPointerException for null modifiers");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}

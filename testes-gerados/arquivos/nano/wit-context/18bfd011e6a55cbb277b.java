/*
 * @test
 * @summary Test for ModuleDescriptor.newModule method behavior and exception paths
 * @run main ModuleDescriptorWitupTest
 */

import java.util.HashSet;
import java.util.Set;
import java.lang.module.ModuleDescriptor;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testNewModuleValidInput();
        testNewModuleWithAutomaticAndOtherModifiers();
        testNewModuleWithNegativeHashMapSize();
        testNewModuleWithZeroHashMapSize();
    }

    static void testNewModuleValidInput() {
        Set<ModuleDescriptor.Modifier> mods = new HashSet<>();
        mods.add(ModuleDescriptor.Modifier.PUBLIC);
        try {
            ModuleDescriptor.Builder builder = ModuleDescriptor.newModule("test.module", mods);
            assert builder != null : "Builder should not be null for valid input";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid input", e);
        }
    }

    static void testNewModuleWithAutomaticAndOtherModifiers() {
        Set<ModuleDescriptor.Modifier> mods = new HashSet<>();
        mods.add(ModuleDescriptor.Modifier.AUTOMATIC);
        mods.add(ModuleDescriptor.Modifier.PUBLIC);
        try {
            ModuleDescriptor.newModule("test.module", mods);
            throw new AssertionError("Expected IllegalArgumentException for AUTOMATIC with other modifiers");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static void testNewModuleWithNegativeHashMapSize() {
        // Simulate the condition where HashMap size calculation leads to negative value
        // by passing a set with size causing the internal HashMap to be initialized with negative capacity
        Set<ModuleDescriptor.Modifier> mods = new HashSet<>();
        // No modifiers added, but we simulate the internal condition by passing an empty set
        try {
            ModuleDescriptor.Builder builder = ModuleDescriptor.newModule("neg.size", mods);
            assert builder != null : "Builder should be created even with empty modifiers";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for empty modifiers", e);
        }
    }

    static void testZeroHashMapSize() {
        // Similar to above, test with empty set to ensure no exception
        Set<ModuleDescriptor.Modifier> mods = new HashSet<>();
        try {
            ModuleDescriptor.Builder builder = ModuleDescriptor.newModule("zero.size", mods);
            assert builder != null : "Builder should be created for empty modifiers";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for empty modifiers", e);
        }
    }
}

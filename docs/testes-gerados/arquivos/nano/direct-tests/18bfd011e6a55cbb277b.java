/* @test    @summary Test for ModuleDescriptor.newModule method behavior    @run main ModuleDescriptorWitupTest */

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleDescriptor.Builder;
import java.lang.module.ModuleDescriptor.Modifier;
import java.util.HashSet;
import java.util.Set;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testNewModuleWithValidModifiers();
        testNewModuleWithInvalidModifiers();
    }

    static void testNewModuleWithValidModifiers() {
        Set<Modifier> mods = new HashSet<>();
        mods.add(Modifier.PUBLIC);
        Builder builder = ModuleDescriptor.newModule("com.example.module", mods);
        assert builder != null : "Builder should not be null for valid modifiers";
    }

    static void testNewModuleWithInvalidModifiers() {
        Set<Modifier> mods = new HashSet<>();
        mods.add(Modifier.AUTOMATIC);
        mods.add(Modifier.PUBLIC);
        try {
            ModuleDescriptor.newModule("com.example.invalid", mods);
            assert false : "Expected IllegalArgumentException for AUTOMATIC with other modifiers";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("AUTOMATIC") : "Exception message should mention AUTOMATIC";
        }
    }
}

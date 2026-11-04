/*
 * @test
 * @summary Test ModuleDescriptor.Builder.requires(Set<Requires.Modifier>, String) method
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleDescriptor.Requires;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testRequiresWithValidModuleName();
        testRequiresWithStrictFalse();
    }

    private static void testRequiresWithValidModuleName() {
        ModuleDescriptor.Builder builder = ModuleDescriptor.newModule("test.module");
        Set<Requires.Modifier> modifiers = EnumSet.noneOf(Requires.Modifier.class);
        // Call requires with a valid module name
        ModuleDescriptor.Builder result = builder.requires(modifiers, "java.base");
        if (result == null) {
            throw new AssertionError("requires() returned null");
        }
    }

    private static void testRequiresWithStrictFalse() {
        // Create a subclass of Builder to set strict to false
        class TestBuilder extends ModuleDescriptor.Builder {
            TestBuilder(String name) {
                super(name);
                this.strict = false;
            }

            @Override
            public Builder requires(Set<Requires.Modifier> ms, String mn) {
                return super.requires(ms, mn);
            }
        }

        TestBuilder builder = new TestBuilder("test.module");
        Set<Requires.Modifier> modifiers = EnumSet.noneOf(Requires.Modifier.class);
        // When strict is false, requireModuleName is not called, so mn is unchanged
        ModuleDescriptor.Builder result = builder.requires(modifiers, "invalid module name with spaces");
        if (result == null) {
            throw new AssertionError("requires() returned null when strict is false");
        }
    }
}

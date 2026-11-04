/*
 * @test
 * @summary Test for ModuleDescriptor.requires(String)
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testRequiresMethod();
    }

    static void testRequiresMethod() {
        ModuleDescriptor.Builder builder = new ModuleDescriptor.Builder();
        String moduleName = "com.example.test";
        ModuleDescriptor.Builder resultBuilder = new ModuleDescriptor().requires(moduleName);
        if (resultBuilder == null) {
            throw new AssertionError("requires method returned null");
        }
        // Since requires returns a Builder, test that calling requires with a string does not throw and returns a Builder
        System.out.println("requires method test passed for module name: " + moduleName);
    }
}

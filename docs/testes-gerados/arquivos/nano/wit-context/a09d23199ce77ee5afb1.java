/*
 * @test
 * @summary Test for ModuleLayer.findModule(String)
 * @run main ModuleLayerWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleLayer;
import java.util.Optional;

public class ModuleLayerWitupTest {

    public static void main(String[] args) {
        testFindModuleWithNullName();
        testFindModuleWithNonExistingName();
        testFindModuleWithExistingName();
    }

    private static void testFindModuleWithNullName() {
        try {
            ModuleLayer layer = ModuleLayer.boot();
            layer.findModule(null);
            System.err.println("Expected NullPointerException for null name, but none was thrown.");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            System.err.println("Unexpected exception for null name: " + e);
            System.exit(1);
        }
    }

    private static void testFindModuleWithNonExistingName() {
        ModuleLayer layer = ModuleLayer.boot();
        String nonExistingName = "non.existing.module";
        Optional<Module> result = layer.findModule(nonExistingName);
        if (result.isPresent()) {
            System.err.println("Expected empty Optional for non-existing module, but got: " + result.get());
            System.exit(1);
        }
    }

    private static void testFindModuleWithExistingName() {
        ModuleLayer layer = ModuleLayer.boot();
        String existingName = "java.base";
        Optional<Module> result = layer.findModule(existingName);
        if (!result.isPresent()) {
            System.err.println("Expected present Optional for existing module, but got empty.");
            System.exit(1);
        }
        if (!result.get().getName().equals(existingName)) {
            System.err.println("Module name mismatch: expected " + existingName + ", but got " + result.get().getName());
            System.exit(1);
        }
    }
}

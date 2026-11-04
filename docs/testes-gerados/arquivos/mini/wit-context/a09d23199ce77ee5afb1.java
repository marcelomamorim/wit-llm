/*
 * @test
 * @summary Test java.lang.ModuleLayer.findModule(String) for null argument and basic lookup
 * @run main ModuleLayerWitupTest
 */

import java.lang.ModuleLayer;
import java.lang.Module;
import java.util.Optional;
import java.util.Objects;

public class ModuleLayerWitupTest {
    public static void main(String[] args) {
        testFindModuleNullName();
        testFindModuleEmptyLayerReturnsEmpty();
        testFindModuleReturnsOptionalModule();
    }

    private static void testFindModuleNullName() {
        ModuleLayer layer = ModuleLayer.boot();
        try {
            layer.findModule(null);
            throw new AssertionError("Expected NullPointerException for null name");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testFindModuleEmptyLayerReturnsEmpty() {
        // EMPTY_LAYER is private, so test with a new empty layer via defineModules with empty config
        // but since construction hints say no public constructors or factories, fallback to boot layer
        // and test that findModule returns empty for a non-existent module name
        ModuleLayer layer = ModuleLayer.boot();
        Optional<Module> result = layer.findModule("non.existent.module.name");
        if (result.isPresent()) {
            throw new AssertionError("Expected empty Optional for non-existent module name");
        }
    }

    private static void testFindModuleReturnsOptionalModule() {
        ModuleLayer layer = ModuleLayer.boot();
        // java.base module must exist in boot layer
        Optional<Module> baseModule = layer.findModule("java.base");
        if (!baseModule.isPresent()) {
            throw new AssertionError("Expected java.base module to be present in boot layer");
        }
        Module m = baseModule.get();
        if (!"java.base".equals(m.getName())) {
            throw new AssertionError("Module name mismatch: expected java.base but got " + m.getName());
        }
    }
}

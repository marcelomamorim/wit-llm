/*
 * @test
 * @summary Test the findModule method of ModuleLayer for existing and non-existing modules
 * @run main ModuleLayerWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleReference;
import java.lang.module.ModuleReader;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleLayer;
import java.util.Optional;
import java.util.Set;
import java.util.Collections;

public class ModuleLayerWitupTest {

    public static void main(String[] args) {
        testFindModuleExisting();
        testFindModuleNonExisting();
    }

    static void testFindModuleExisting() {
        // Obtain the boot layer which should contain 'java.base'
        ModuleLayer bootLayer = ModuleLayer.boot();
        Optional<Module> javaBaseModuleOpt = bootLayer.findModule("java.base");
        assert javaBaseModuleOpt.isPresent() : "java.base module should be present in boot layer";
        Module javaBaseModule = javaBaseModuleOpt.get();
        assert "java.base".equals(javaBaseModule.getName()) : "Module name should be java.base";
    }

    static void testFindModuleNonExisting() {
        ModuleLayer layer = ModuleLayer.boot();
        Optional<Module> nonExisting = layer.findModule("non.existent.module");
        assert !nonExisting.isPresent() : "Non-existent module should not be found";
    }
}

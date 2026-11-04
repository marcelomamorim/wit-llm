/*
 * @test
 * @summary Test java.lang.ModuleLayer.findLoader(String) method behavior
 * @run main ModuleLayerWitupTest
 */

import java.lang.ModuleLayer;
import java.lang.Module;
import java.lang.ClassLoader;
import java.util.Optional;

public class ModuleLayerWitupTest {

    public static void main(String[] args) {
        testFindLoaderKnownModule();
        testFindLoaderUnknownModule();
    }

    private static void testFindLoaderKnownModule() {
        ModuleLayer bootLayer = ModuleLayer.boot();
        // java.base module is always present in boot layer
        String moduleName = "java.base";
        ClassLoader loader = bootLayer.findLoader(moduleName);
        if (loader == null) {
            throw new RuntimeException("Expected non-null ClassLoader for module '" + moduleName + "'");
        }
        // The loader for java.base in boot layer is typically null (bootstrap loader), so accept null
        // but findLoader returns the actual loader, so test that findModule returns present
        Optional<Module> mod = bootLayer.findModule(moduleName);
        if (!mod.isPresent()) {
            throw new RuntimeException("Module '" + moduleName + "' should be present in boot layer");
        }
        if (mod.get().getClassLoader() != loader) {
            throw new RuntimeException("ClassLoader from findLoader and Module.getClassLoader() differ");
        }
    }

    private static void testFindLoaderUnknownModule() {
        ModuleLayer bootLayer = ModuleLayer.boot();
        String unknownModule = "non.existent.module";
        try {
            bootLayer.findLoader(unknownModule);
            throw new RuntimeException("Expected IllegalArgumentException for unknown module");
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains(unknownModule)) {
                throw new RuntimeException("Exception message does not mention module name");
            }
        }
    }
}

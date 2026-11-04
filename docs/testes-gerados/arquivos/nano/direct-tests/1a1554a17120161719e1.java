/*
 * @test
 * @summary Test for ModuleLayer.findLoader(String)
 * @run main ModuleLayerWitupTest
 */

import java.lang.ModuleLayer;
import java.lang.Module;
import java.util.Optional;

public class ModuleLayerWitupTest {

    public static void main(String[] args) {
        testFindLoaderKnownModule();
        testFindLoaderUnknownModule();
    }

    static void testFindLoaderKnownModule() {
        // Obtain the boot layer which contains 'java.base'
        ModuleLayer bootLayer = ModuleLayer.boot();
        // 'java.base' is always present in the boot layer
        String moduleName = "java.base";
        ClassLoader cl = null;
        try {
            cl = bootLayer.findLoader(moduleName);
            if (cl == null) {
                throw new AssertionError("Expected non-null ClassLoader for module 'java.base'");
            }
            // The class loader for 'java.base' is the bootstrap class loader, which is null
            if (cl != null) {
                throw new AssertionError("Expected null ClassLoader for 'java.base'");
            }
            System.out.println("testFindLoaderKnownModule passed");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e, e);
        }
    }

    static void testFindLoaderUnknownModule() {
        ModuleLayer layer = ModuleLayer.boot();
        String unknownModuleName = "non.existent.module";
        try {
            layer.findLoader(unknownModuleName);
            throw new AssertionError("Expected IllegalArgumentException for unknown module")
        } catch (IllegalArgumentException e) {
            String expectedMsg = "Module " + unknownModuleName + " not known to this layer";
            if (!e.getMessage().equals(expectedMsg)) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
            System.out.println("testFindLoaderUnknownModule passed");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e, e);
        }
    }
}

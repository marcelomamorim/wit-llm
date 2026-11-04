/*
 * @test
 * @summary Test java.lang.ModuleLayer.findLoader(String) for expected exceptions and normal behavior
 * @run main ModuleLayerWitupTest
 */

import java.lang.ModuleLayer;
import java.lang.Module;
import java.util.Optional;

public class ModuleLayerWitupTest {

    public static void main(String[] args) {
        testFindLoaderNullName();
        testFindLoaderUnknownModule();
        testFindLoaderKnownModule();
    }

    private static void testFindLoaderNullName() {
        ModuleLayer layer = ModuleLayer.boot();
        try {
            layer.findLoader(null);
            throw new Error("Expected NullPointerException for null name");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testFindLoaderUnknownModule() {
        ModuleLayer layer = ModuleLayer.boot();
        String unknownModule = "this.module.does.not.exist";
        try {
            layer.findLoader(unknownModule);
            throw new Error("Expected IllegalArgumentException for unknown module name");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testFindLoaderKnownModule() {
        ModuleLayer layer = ModuleLayer.boot();
        // java.base is always present in boot layer
        String knownModule = "java.base";
        ClassLoader cl = layer.findLoader(knownModule);
        if (cl == null) {
            throw new Error("ClassLoader for known module java.base should not be null");
        }
    }
}

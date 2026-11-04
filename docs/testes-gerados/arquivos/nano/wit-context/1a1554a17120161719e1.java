/*
 * @test
 * @summary Test for ModuleLayer.findLoader(String name)
 * @run main ModuleLayerWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleLayer;
import java.util.Optional;

public class ModuleLayerWitupTest {

    public static void main(String[] args) {
        testFindLoaderWithNullName();
        testFindLoaderWithUnknownModule();
        testFindLoaderWithExistingModule();
    }

    private static void testFindLoaderWithNullName() {
        try {
            ModuleLayer layer = ModuleLayer.boot();
            layer.findLoader(null);
            System.out.println("FAIL: Expected NullPointerException for null name");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null name");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for null name: " + e);
        }
    }

    private static void testFindLoaderWithUnknownModule() {
        try {
            ModuleLayer layer = ModuleLayer.boot();
            String unknownModuleName = "non.existent.module";
            layer.findLoader(unknownModuleName);
            System.out.println("FAIL: Expected IllegalArgumentException for unknown module");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: IllegalArgumentException thrown as expected for unknown module");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for unknown module: " + e);
        }
    }

    private static void testFindLoaderWithExistingModule() {
        try {
            ModuleLayer layer = ModuleLayer.boot();
            Optional<Module> javaBaseModule = layer.findModule("java.base");
            if (javaBaseModule.isPresent()) {
                ClassLoader cl = layer.findLoader("java.base");
                if (cl != null) {
                    System.out.println("PASS: Found class loader for 'java.base': " + cl);
                } else {
                    System.out.println("FAIL: Class loader for 'java.base' is null");
                }
            } else {
                System.out.println("FAIL: 'java.base' module not found in boot layer");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception during existing module test: " + e);
        }
    }
}

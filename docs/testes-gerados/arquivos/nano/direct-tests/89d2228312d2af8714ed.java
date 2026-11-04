/*
 * @test
 * @summary Test the isOpen method of java.lang.Module for various scenarios.
 * @run main ModuleWitupTest
 */

import java.lang.Module;
import java.util.Objects;

public class ModuleWitupTest {

    public static void main(String[] args) {
        testIsOpenWithSameModule();
        testIsOpenWithDifferentModules();
        testIsOpenWithNullParameters();
    }

    static void testIsOpenWithSameModule() {
        Module module = ModuleLayer.boot().modules().iterator().next();
        boolean result = module.isOpen("java.lang", module);
        System.out.println("testIsOpenWithSameModule: " + result);
        // Expect true if package is open in the same module, false otherwise.
        // Since default modules may not have open packages, result may be false.
    }

    static void testIsOpenWithDifferentModules() {
        ModuleLayer layer = ModuleLayer.boot();
        Iterator<Module> modules = layer.modules().iterator();
        if (modules.hasNext()) {
            Module m1 = modules.next();
            if (modules.hasNext()) {
                Module m2 = modules.next();
                boolean result = m1.isOpen("java.lang", m2);
                System.out.println("testIsOpenWithDifferentModules: " + result);
            } else {
                System.out.println("testIsOpenWithDifferentModules: Skipped, only one module available.");
            }
        } else {
            System.out.println("testIsOpenWithDifferentModules: No modules available.");
        }
    }

    static void testIsOpenWithNullParameters() {
        Module module = ModuleLayer.boot().modules().iterator().next();
        try {
            module.isOpen(null, module);
            System.out.println("testIsOpenWithNullParameters: Failed, expected NullPointerException for null package name.");
        } catch (NullPointerException e) {
            System.out.println("testIsOpenWithNullParameters: Passed for null package name.");
        }
        try {
            module.isOpen("java.lang", null);
            System.out.println("testIsOpenWithNullParameters: Failed, expected NullPointerException for null other module.");
        } catch (NullPointerException e) {
            System.out.println("testIsOpenWithNullParameters: Passed for null other module.");
        }
    }
}

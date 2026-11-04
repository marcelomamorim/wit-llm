/*
 * @test
 * @summary Test the isExported method of java.lang.Module for various package names.
 * @run main ModuleWitupTest
 */

import java.lang.Module;
import java.util.Objects;

public class ModuleWitupTest {

    public static void main(String[] args) {
        testIsExportedWithValidPackage();
        testIsExportedWithNull();
        testIsExportedWithEmptyString();
        testIsExportedWithNonExistentPackage();
    }

    static void testIsExportedWithValidPackage() {
        Module module = Module.class.getModule();
        String packageName = "java.lang";
        boolean result = module.isExported(packageName);
        System.out.println("isExported(\"java.lang\") = " + result);
        // Expect true if the package is exported
    }

    static void testIsExportedWithNull() {
        Module module = Module.class.getModule();
        try {
            module.isExported(null);
            System.out.println("Expected NullPointerException for null package name");
        } catch (NullPointerException e) {
            System.out.println("Caught expected NullPointerException for null package name");
        }
    }

    static void testIsExportedWithEmptyString() {
        Module module = Module.class.getModule();
        boolean result = false;
        try {
            result = module.isExported("");
        } catch (Exception e) {
            System.out.println("Exception for empty package name: " + e);
        }
        System.out.println("isExported("") = " + result);
        // Expect false or exception depending on implementation
    }

    static void testIsExportedWithNonExistentPackage() {
        Module module = Module.class.getModule();
        String packageName = "non.existent.package";
        boolean result = module.isExported(packageName);
        System.out.println("isExported(\"non.existent.package\") = " + result);
        // Expect false if package does not exist or is not exported
    }
}

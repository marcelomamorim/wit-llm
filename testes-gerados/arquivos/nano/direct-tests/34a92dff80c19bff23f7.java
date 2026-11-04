/*
 * @test
 * @summary Test the isOpen method of java.lang.Module for various package names.
 * @run main ModuleWitupTest
 */

import java.lang.Module;
import java.util.Objects;

public class ModuleWitupTest {

    public static void main(String[] args) {
        testIsOpenWithValidPackage();
        testIsOpenWithNull();
        testIsOpenWithEmptyString();
        testIsOpenWithNonExistentPackage();
    }

    static void testIsOpenWithValidPackage() {
        Module module = Module.class.getModule();
        String packageName = "java.lang";
        boolean result = module.isOpen(packageName);
        System.out.println("isOpen(\"java.lang\") = " + result);
    }

    static void testIsOpenWithNull() {
        Module module = Module.class.getModule();
        try {
            module.isOpen(null);
            System.out.println("Expected NullPointerException for null argument, but none was thrown.");
        } catch (NullPointerException e) {
            System.out.println("Caught expected NullPointerException for null argument.");
        }
    }

    static void testIsOpenWithEmptyString() {
        Module module = Module.class.getModule();
        String packageName = "";
        boolean result = false;
        try {
            result = module.isOpen(packageName);
            System.out.println("isOpen("
+ "\"\"\") = " + result);
        } catch (Exception e) {
            System.out.println("Exception thrown for empty string: " + e);
        }
    }

    static void testIsOpenWithNonExistentPackage() {
        Module module = Module.class.getModule();
        String packageName = "com.nonexistent.package";
        boolean result = module.isOpen(packageName);
        System.out.println("isOpen(\"com.nonexistent.package\") = " + result);
    }
}

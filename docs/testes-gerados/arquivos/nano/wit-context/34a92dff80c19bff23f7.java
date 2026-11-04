/*
 * @test
 * @summary Test for java.lang.Module.isOpen(String)
 * @run main ModuleWitupTest
 */

import java.lang.Module;
import java.util.Objects;

public class ModuleWitupTest {

    public static void main(String[] args) {
        testIsOpenWithNull();
        testIsOpenWithValidString();
    }

    private static void testIsOpenWithNull() {
        Module module = Module.class.getModule();
        try {
            module.isOpen(null);
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testIsOpenWithValidString() {
        Module module = Module.class.getModule();
        String testPackage = "java.lang";
        boolean result = false;
        try {
            result = module.isOpen(testPackage);
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
            System.exit(1);
        }
        // No assertion, just observe the result
        System.out.println("isOpen(" + testPackage + ") returned: " + result);
    }
}

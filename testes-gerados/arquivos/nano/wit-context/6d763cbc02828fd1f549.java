/*
 * @test
 * @summary Tests for java.lang.Module.addOpens method.
 * @run main ModuleWitupTest
 */

import java.lang.Module;
import java.lang.reflect.Method;

public class ModuleWitupTest {

    public static void main(String[] args) {
        testAddOpensNullPackage();
        testAddOpensNullModule();
    }

    private static void testAddOpensNullPackage() {
        Module module = createDummyModule();
        try {
            module.addOpens(null, createDummyModule());
            System.out.println("Failed: Expected IllegalArgumentException for null package");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: Caught expected IllegalArgumentException for null package");
        } catch (Throwable t) {
            System.out.println("Failed: Unexpected exception: " + t);
        }
    }

    private static void testAddOpensNullModule() {
        Module module = createDummyModule();
        try {
            module.addOpens("java.lang", null);
            System.out.println("Failed: Expected NullPointerException for null module");
        } catch (NullPointerException e) {
            System.out.println("Passed: Caught expected NullPointerException for null module");
        } catch (Throwable t) {
            System.out.println("Failed: Unexpected exception: " + t);
        }
    }

    private static Module createDummyModule() {
        // Using reflection to get a real Module instance for testing
        // As Module has no public constructor, get a module from a class
        return String.class.getModule();
    }
}

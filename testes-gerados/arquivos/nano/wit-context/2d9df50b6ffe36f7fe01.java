/*
 * @test
 * @summary Test for java.lang.Module.addUses(Class<?>) method.
 * @run main ModuleWitupTest
 */

import java.lang.Module;
import java.lang.IllegalCallerException;
import java.util.Objects;

public class ModuleWitupTest {

    public static void main(String[] args) {
        testAddUsesWithNullService();
        testAddUsesWithValidService();
    }

    private static void testAddUsesWithNullService() {
        Module module = createDummyModule();
        try {
            module.addUses(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testAddUsesWithValidService() {
        Module module = createDummyModule();
        Class<?> serviceClass = String.class; // Using String class as dummy service
        try {
            Module result = module.addUses(serviceClass);
            if (result == module) {
                System.out.println("PASS: addUses with valid service returned the same module");
            } else {
                System.out.println("FAIL: addUses with valid service did not return the same module");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static Module createDummyModule() {
        // Using the unnamed module for testing
        return Object.class.getModule();
    }
}

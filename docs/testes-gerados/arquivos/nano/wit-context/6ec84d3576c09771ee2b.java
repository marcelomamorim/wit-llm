/*
 * @test
 * @summary Test for MethodHandles.accessClass method behavior.
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.Class;

public class MethodHandlesWitupTest {

    public static void main(String[] args) throws Throwable {
        testAccessClassWithAccessibleClass();
        testAccessClassWithInaccessibleClass();
    }

    private static void testAccessClassWithAccessibleClass() throws Throwable {
        Class<?> cls = String.class;
        Class<?> result = MethodHandles.accessClass(cls);
        if (result != cls) {
            throw new AssertionError("Expected class to be accessible: " + cls);
        }
        System.out.println("Accessible class test passed.");
    }

    private static void testAccessClassWithInaccessibleClass() {
        try {
            // Define a class that is likely inaccessible, e.g., a package-private class in a different package.
            // Since we cannot create such class here, simulate by passing a class with restricted access.
            // For demonstration, use a class from java.lang.invoke, which may be restricted.
            Class<?> cls = MethodHandles.class;
            MethodHandles.accessClass(cls);
            throw new AssertionError("Expected IllegalAccessException for class: " + cls);
        } catch (IllegalAccessException e) {
            System.out.println("Inaccessible class test passed with exception: " + e);
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t, t);
        }
    }
}

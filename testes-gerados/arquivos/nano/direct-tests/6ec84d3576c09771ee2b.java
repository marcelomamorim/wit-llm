/*
 * @test
 * @summary Test MethodHandles.accessClass method for accessible class.
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandles;

public class MethodHandlesWitupTest {

    public static void main(String[] args) {
        testAccessClassWithAccessibleClass();
        testAccessClassWithInaccessibleClass();
    }

    private static void testAccessClassWithAccessibleClass() {
        try {
            Class<?> cls = String.class;
            Class<?> result = MethodHandles.accessClass(cls);
            if (result != cls) {
                throw new AssertionError("Expected class to be String.class");
            }
            System.out.println("testAccessClassWithAccessibleClass passed.");
        } catch (IllegalAccessException e) {
            throw new AssertionError("Unexpected IllegalAccessException", e);
        }
    }

    private static void testAccessClassWithInaccessibleClass() {
        try {
            // Assuming package-private class in the same package, simulate inaccessible class.
            // Since we cannot create such class here, we simulate by passing a class that is likely inaccessible.
            // For demonstration, use a class from a package that is not accessible, e.g., sun.reflect.Reflection.
            Class<?> cls = sun.reflect.Reflection.class;
            MethodHandles.accessClass(cls);
            throw new AssertionError("Expected IllegalAccessException for sun.reflect.Reflection");
        } catch (IllegalAccessException e) {
            System.out.println("testAccessClassWithInaccessibleClass passed.");
        } catch (SecurityException se) {
            // If security manager prevents access, consider test passed.
            System.out.println("testAccessClassWithInaccessibleClass passed with SecurityException.");
        }
    }
}

/*
 * @test
 * @summary Test MethodHandles.accessClass behavior for accessible and inaccessible classes
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;

public class MethodHandlesWitupTest {
    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        // Test with an accessible class (java.lang.String)
        Class<?> accessibleClass = String.class;
        Class<?> result = lookup.accessClass(accessibleClass);
        if (result != accessibleClass) {
            throw new Error("accessClass did not return the expected class for accessible class");
        }

        // Test with an inaccessible class: create a private class and try accessClass
        // Since we cannot create a private class here, test with a class that is not accessible from this lookup
        // For this test, use a class from a different package and expect IllegalAccessException
        // We expect IllegalAccessException to be thrown
        Class<?> inaccessibleClass = java.lang.invoke.MethodHandles.class;
        try {
            lookup.accessClass(inaccessibleClass);
            throw new Error("accessClass did not throw IllegalAccessException for inaccessible class");
        } catch (IllegalAccessException e) {
            // Expected
        }
    }
}

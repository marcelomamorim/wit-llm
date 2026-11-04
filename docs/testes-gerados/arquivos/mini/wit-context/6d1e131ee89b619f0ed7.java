/*
 * @test
 * @summary Test java.lang.reflect.AccessibleObject.getAnnotations() method
 * @run main java.lang.reflect.AccessibleObjectWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;

public class AccessibleObjectWitupTest {

    public static void main(String[] args) throws Exception {
        // Create an anonymous subclass instance since AccessibleObject is abstract
        AccessibleObject ao = new AccessibleObject() {};

        // Call getAnnotations and verify it returns an array (possibly empty, never null)
        Annotation[] annotations = ao.getAnnotations();
        if (annotations == null) {
            throw new AssertionError("getAnnotations() returned null");
        }

        // The returned array should be the same as getDeclaredAnnotations() (by contract)
        Annotation[] declaredAnnotations = ao.getDeclaredAnnotations();
        if (declaredAnnotations == null) {
            throw new AssertionError("getDeclaredAnnotations() returned null");
        }

        // Check that getAnnotations returns the same array as getDeclaredAnnotations
        if (annotations != declaredAnnotations) {
            throw new AssertionError("getAnnotations() and getDeclaredAnnotations() differ");
        }

        // If no exception thrown, test passed
        System.out.println("AccessibleObject.getAnnotations() test passed");
    }
}

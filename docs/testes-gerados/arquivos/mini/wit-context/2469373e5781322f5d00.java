/*
 * @test
 * @summary Test getDeclaredAnnotation method of AccessibleObject
 * @run main AccessibleObjectWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;

public class AccessibleObjectWitupTest {

    public static void main(String[] args) {
        // Since AccessibleObject is abstract and has no public constructors,
        // we create an anonymous subclass instance to test getDeclaredAnnotation.
        AccessibleObject ao = new AccessibleObject() {};

        // Test with null argument: getAnnotation(null) should throw NullPointerException
        try {
            ao.getDeclaredAnnotation(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test with a valid annotation class that is not present: should return null
        Annotation result = ao.getDeclaredAnnotation(Deprecated.class);
        if (result != null) {
            throw new RuntimeException("Expected null when annotation not present");
        }

        // Test with a valid annotation class that is present on the anonymous class
        Deprecated deprecatedAnnotation = ao.getClass().getAnnotation(Deprecated.class);
        // The anonymous class is not annotated with @Deprecated, so result should be null
        if (deprecatedAnnotation != null) {
            throw new RuntimeException("Unexpected Deprecated annotation found on anonymous class");
        }

        // If no exceptions thrown and checks passed, test succeeded
        System.out.println("AccessibleObject.getDeclaredAnnotation tests passed");
    }
}
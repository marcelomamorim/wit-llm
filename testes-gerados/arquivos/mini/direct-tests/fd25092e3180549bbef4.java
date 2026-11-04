/*
 * @test
 * @summary Test AccessibleObject.getDeclaredAnnotationsByType method
 * @run main AccessibleObjectWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;

public class AccessibleObjectWitupTest {

    public static void main(String[] args) throws Exception {
        testGetDeclaredAnnotationsByTypeWithNull();
        testGetDeclaredAnnotationsByTypeWithNoAnnotations();
        testGetDeclaredAnnotationsByTypeWithAnnotations();
    }

    static void testGetDeclaredAnnotationsByTypeWithNull() {
        AccessibleObject ao = new AccessibleObject() {};
        try {
            ao.getDeclaredAnnotationsByType(null);
            throw new RuntimeException("Expected NullPointerException for null argument");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testGetDeclaredAnnotationsByTypeWithNoAnnotations() {
        AccessibleObject ao = new AccessibleObject() {};
        Annotation[] result = ao.getDeclaredAnnotationsByType(Deprecated.class);
        if (result == null) {
            throw new RuntimeException("Returned null instead of empty array");
        }
        if (result.length != 0) {
            throw new RuntimeException("Expected empty array for no annotations");
        }
    }

    @Deprecated
    static class AnnotatedClass {
        // dummy class with annotation
    }

    static void testGetDeclaredAnnotationsByTypeWithAnnotations() throws Exception {
        java.lang.reflect.Method m = AnnotatedClass.class.getDeclaredMethod("toString");
        AccessibleObject ao = (AccessibleObject) m;
        Deprecated[] result = ao.getDeclaredAnnotationsByType(Deprecated.class);
        if (result == null) {
            throw new RuntimeException("Returned null instead of empty array");
        }
        // toString is not annotated with Deprecated, expect empty
        if (result.length != 0) {
            throw new RuntimeException("Expected empty array for method with no Deprecated annotation");
        }

        Deprecated[] classAnnotations = AnnotatedClass.class.getDeclaredAnnotationsByType(Deprecated.class);
        if (classAnnotations == null || classAnnotations.length == 0) {
            throw new RuntimeException("AnnotatedClass should have Deprecated annotation");
        }
    }
}

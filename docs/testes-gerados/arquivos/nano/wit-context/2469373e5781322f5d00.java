/*
 * @test
 * @summary Test for AccessibleObject.getDeclaredAnnotation method.
 * @run main AccessibleObjectWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;

public class AccessibleObjectWitupTest {

    public static void main(String[] args) {
        testGetDeclaredAnnotationReturnsAnnotation();
        testGetDeclaredAnnotationReturnsNullWhenNoAnnotation();
    }

    private static void testGetDeclaredAnnotationReturnsAnnotation() {
        class Sample {
            @Deprecated
            public void method() {}
        }
        try {
            Sample sample = new Sample();
            AccessibleObject ao = sample.getClass().getMethod("method");
            Annotation annotation = ao.getDeclaredAnnotation(Deprecated.class);
            if (annotation == null || !(annotation instanceof Deprecated)) {
                throw new AssertionError("Expected Deprecated annotation, got: " + annotation);
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static void testGetDeclaredAnnotationReturnsNullWhenNoAnnotation() {
        class Sample {
            public void method() {}
        }
        try {
            Sample sample = new Sample();
            AccessibleObject ao = sample.getClass().getMethod("method");
            Annotation annotation = ao.getDeclaredAnnotation(Deprecated.class);
            if (annotation != null) {
                throw new AssertionError("Expected null, but got: " + annotation);
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}

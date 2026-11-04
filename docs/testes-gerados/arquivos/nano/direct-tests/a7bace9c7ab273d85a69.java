/*
 * @test
 * @summary Test getDeclaredAnnotation method of java.lang.Class
 * @run main ClassWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.Class;

public class ClassWitupTest {

    public static void main(String[] args) {
        testGetDeclaredAnnotationWithExistingAnnotation();
        testGetDeclaredAnnotationWithNull();
        testGetDeclaredAnnotationWithNonExistingAnnotation();
    }

    private static void testGetDeclaredAnnotationWithExistingAnnotation() {
        Class<SampleAnnotation> annotationClass = SampleAnnotation.class;
        SampleAnnotation annotation = SampleClass.class.getDeclaredAnnotation(annotationClass);
        if (annotation == null) {
            throw new AssertionError("Expected annotation to be present");
        }
        if (annotation.value() == null || !annotation.value().equals("test")) {
            throw new AssertionError("Annotation value mismatch");
        }
    }

    private static void testGetDeclaredAnnotationWithNull() {
        try {
            SampleClass.class.getDeclaredAnnotation(null);
            throw new AssertionError("Expected NullPointerException for null argument");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testGetDeclaredAnnotationWithNonExistingAnnotation() {
        Class<NonExistingAnnotation> nonExisting = NonExistingAnnotation.class;
        Annotation result = SampleClass.class.getDeclaredAnnotation(nonExisting);
        if (result != null) {
            throw new AssertionError("Expected null for non-existing annotation");
        }
    }

    @interface SampleAnnotation {
        String value();
    }

    @SampleAnnotation("test")
    public static class SampleClass {
    }

    @interface NonExistingAnnotation {
    }
}

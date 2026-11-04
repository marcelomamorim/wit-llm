/*
 * @test
 * @summary Test java.lang.Class.getAnnotationsByType method behavior
 * @run main ClassWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ClassWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    public @interface TestAnnotation {
        String value() default "";
    }

    @TestAnnotation("classLevel")
    static class AnnotatedClass {
    }

    public static void main(String[] args) {
        // Test 1: Passing null should throw NullPointerException
        try {
            Class<?> c = AnnotatedClass.class;
            c.getAnnotationsByType(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        }

        // Test 2: Passing an annotation class that is present
        TestAnnotation[] annotations = AnnotatedClass.class.getAnnotationsByType(TestAnnotation.class);
        if (annotations == null || annotations.length != 1) {
            throw new RuntimeException("Expected exactly one TestAnnotation");
        }
        if (!"classLevel".equals(annotations[0].value())) {
            throw new RuntimeException("Annotation value mismatch");
        }

        // Test 3: Passing an annotation class that is not present
        Deprecated[] deprecatedAnnotations = AnnotatedClass.class.getAnnotationsByType(Deprecated.class);
        if (deprecatedAnnotations == null) {
            throw new RuntimeException("Returned array must not be null");
        }
        if (deprecatedAnnotations.length != 0) {
            throw new RuntimeException("Expected empty array for annotation not present");
        }

        System.out.println("All tests passed.");
    }
}

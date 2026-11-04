/*
 * @test
 * @summary Test java.lang.Class.getAnnotation method for null argument and normal behavior
 * @run main ClassWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ClassWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    public @interface TestAnnotation {
        String value() default "test";
    }

    @TestAnnotation("present")
    static class AnnotatedClass {}

    public static void main(String[] args) {
        testGetAnnotationWithNull();
        testGetAnnotationWithPresentAnnotation();
        testGetAnnotationWithAbsentAnnotation();
    }

    private static void testGetAnnotationWithNull() {
        try {
            Class<?> c = AnnotatedClass.class;
            c.getAnnotation(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testGetAnnotationWithPresentAnnotation() {
        Class<AnnotatedClass> c = AnnotatedClass.class;
        TestAnnotation ann = c.getAnnotation(TestAnnotation.class);
        if (ann == null) {
            throw new RuntimeException("Expected annotation not found");
        }
        if (!"present".equals(ann.value())) {
            throw new RuntimeException("Annotation value mismatch: " + ann.value());
        }
    }

    private static void testGetAnnotationWithAbsentAnnotation() {
        Class<AnnotatedClass> c = AnnotatedClass.class;
        Deprecated ann = c.getAnnotation(Deprecated.class);
        if (ann != null) {
            throw new RuntimeException("Expected null for absent annotation");
        }
    }
}

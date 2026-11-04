/*
 * @test
 * @summary Test getAnnotationsByType method of java.lang.Class for null argument and normal behavior
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

    @TestAnnotation("test")
    static class AnnotatedClass {}

    public static void main(String[] args) {
        testNullArgument();
        testGetAnnotationsByType();
    }

    private static void testNullArgument() {
        Class<?> clazz = AnnotatedClass.class;
        try {
            clazz.getAnnotationsByType(null);
            throw new RuntimeException("Expected NullPointerException for null argument");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testGetAnnotationsByType() {
        Class<AnnotatedClass> clazz = AnnotatedClass.class;
        TestAnnotation[] annotations = clazz.getAnnotationsByType(TestAnnotation.class);
        if (annotations == null) {
            throw new RuntimeException("Returned annotations array is null");
        }
        if (annotations.length != 1) {
            throw new RuntimeException("Expected exactly one annotation, found: " + annotations.length);
        }
        if (!"test".equals(annotations[0].value())) {
            throw new RuntimeException("Annotation value mismatch: " + annotations[0].value());
        }
    }
}

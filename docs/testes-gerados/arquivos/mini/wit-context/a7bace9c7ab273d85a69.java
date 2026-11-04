/*
 * @test
 * @summary Test getDeclaredAnnotation method of java.lang.Class for null argument and normal behavior
 * @run main ClassWitupTest
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Annotation;

public class ClassWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    public @interface TestAnnotation {
        String value() default "test";
    }

    @TestAnnotation("present")
    static class AnnotatedClass {}

    public static void main(String[] args) {
        testNullArgument();
        testAnnotationPresent();
        testAnnotationAbsent();
    }

    private static void testNullArgument() {
        try {
            Class<?> c = AnnotatedClass.class;
            c.getDeclaredAnnotation(null);
            throw new Error("Expected NullPointerException for null argument");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testAnnotationPresent() {
        Class<AnnotatedClass> c = AnnotatedClass.class;
        TestAnnotation ann = c.getDeclaredAnnotation(TestAnnotation.class);
        if (ann == null) {
            throw new Error("Expected annotation to be present");
        }
        if (!"present".equals(ann.value())) {
            throw new Error("Annotation value mismatch: " + ann.value());
        }
    }

    private static void testAnnotationAbsent() {
        Class<AnnotatedClass> c = AnnotatedClass.class;
        Deprecated ann = c.getDeclaredAnnotation(Deprecated.class);
        if (ann != null) {
            throw new Error("Expected null for absent annotation");
        }
    }
}

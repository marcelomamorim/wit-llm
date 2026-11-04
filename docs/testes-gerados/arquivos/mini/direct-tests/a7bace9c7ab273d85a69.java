/*
 * @test
 * @summary Test java.lang.Class.getDeclaredAnnotation method
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
    static class AnnotatedClass {
    }

    static class NonAnnotatedClass {
    }

    public static void main(String[] args) {
        // Test 1: Passing null should throw NullPointerException
        try {
            Class.class.getDeclaredAnnotation(null);
            throw new RuntimeException("Expected NullPointerException for null argument");
        } catch (NullPointerException expected) {
        }

        // Test 2: Class with annotation returns the annotation instance
        TestAnnotation ann = AnnotatedClass.class.getDeclaredAnnotation(TestAnnotation.class);
        if (ann == null) {
            throw new RuntimeException("Expected annotation instance but got null");
        }
        if (!"present".equals(ann.value())) {
            throw new RuntimeException("Annotation value mismatch: " + ann.value());
        }

        // Test 3: Class without annotation returns null
        TestAnnotation ann2 = NonAnnotatedClass.class.getDeclaredAnnotation(TestAnnotation.class);
        if (ann2 != null) {
            throw new RuntimeException("Expected null for non-annotated class");
        }

        // Test 4: Annotation class itself returns null (no annotation on annotation class)
        TestAnnotation ann3 = TestAnnotation.class.getDeclaredAnnotation(TestAnnotation.class);
        if (ann3 != null) {
            throw new RuntimeException("Expected null for annotation class itself");
        }
    }
}

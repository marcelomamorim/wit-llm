/*
 * @test
 * @summary Test java.lang.Class.getAnnotation method behavior
 * @run main ClassWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ClassWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    public @interface TestAnnotation {
        String value();
    }

    @TestAnnotation("testValue")
    static class AnnotatedClass {
    }

    static class NonAnnotatedClass {
    }

    public static void main(String[] args) {
        // Test 1: Passing null should throw NullPointerException
        try {
            Class<?> c = AnnotatedClass.class;
            c.getAnnotation(null);
            throw new Error("Expected NullPointerException for null argument");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test 2: Get annotation present on class
        TestAnnotation ann = AnnotatedClass.class.getAnnotation(TestAnnotation.class);
        if (ann == null) {
            throw new Error("Expected annotation to be present");
        }
        if (!"testValue".equals(ann.value())) {
            throw new Error("Annotation value mismatch: " + ann.value());
        }

        // Test 3: Get annotation not present on class returns null
        TestAnnotation ann2 = NonAnnotatedClass.class.getAnnotation(TestAnnotation.class);
        if (ann2 != null) {
            throw new Error("Expected null when annotation not present");
        }

        // Test 4: Get annotation of unrelated annotation type returns null
        Deprecated dep = AnnotatedClass.class.getAnnotation(Deprecated.class);
        if (dep != null) {
            throw new Error("Expected null for unrelated annotation type");
        }
    }
}

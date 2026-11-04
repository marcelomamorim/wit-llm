/*
 * @test
 * @summary Test Method.getAnnotation(Class) delegation to Executable.getAnnotation
 * @run main MethodWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;

public class MethodWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    public @interface TestAnnotation {
        String value();
    }

    @TestAnnotation("present")
    public void annotatedMethod() {}

    public void nonAnnotatedMethod() {}

    public static void main(String[] args) throws Exception {
        Method annotated = MethodWitupTest.class.getMethod("annotatedMethod");
        Method nonAnnotated = MethodWitupTest.class.getMethod("nonAnnotatedMethod");

        // Test that getAnnotation returns the annotation instance when present
        TestAnnotation ann = annotated.getAnnotation(TestAnnotation.class);
        if (ann == null) {
            throw new RuntimeException("Expected annotation on annotatedMethod, got null");
        }
        if (!"present".equals(ann.value())) {
            throw new RuntimeException("Annotation value mismatch: expected 'present', got '" + ann.value() + "'");
        }

        // Test that getAnnotation returns null when annotation is not present
        TestAnnotation ann2 = nonAnnotated.getAnnotation(TestAnnotation.class);
        if (ann2 != null) {
            throw new RuntimeException("Expected null on nonAnnotatedMethod, got " + ann2);
        }

        // Test that getAnnotation returns null for unrelated annotation types
        Deprecated dep = annotated.getAnnotation(Deprecated.class);
        if (dep != null) {
            throw new RuntimeException("Expected null for Deprecated annotation on annotatedMethod, got " + dep);
        }

        System.out.println("Test passed.");
    }
}

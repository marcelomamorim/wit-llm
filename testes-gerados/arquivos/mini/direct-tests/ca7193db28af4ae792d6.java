/*
 * @test
 * @summary Test java.lang.reflect.Executable.getAnnotation method behavior
 * @run main ExecutableWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.Objects;

public class ExecutableWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    public @interface TestAnnotation {
        String value() default "test";
    }

    @TestAnnotation("present")
    public void annotatedMethod() {}

    public void nonAnnotatedMethod() {}

    public static void main(String[] args) throws Exception {
        ExecutableWitupTest testInstance = new ExecutableWitupTest();

        Method annotated = ExecutableWitupTest.class.getMethod("annotatedMethod");
        Method nonAnnotated = ExecutableWitupTest.class.getMethod("nonAnnotatedMethod");

        // Test 1: Passing null to getAnnotation should throw NullPointerException
        try {
            annotated.getAnnotation(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        }

        // Test 2: getAnnotation returns annotation instance when present
        TestAnnotation ann = annotated.getAnnotation(TestAnnotation.class);
        if (ann == null) {
            throw new RuntimeException("Expected annotation instance, got null");
        }
        if (!"present".equals(ann.value())) {
            throw new RuntimeException("Annotation value mismatch");
        }

        // Test 3: getAnnotation returns null when annotation not present
        TestAnnotation ann2 = nonAnnotated.getAnnotation(TestAnnotation.class);
        if (ann2 != null) {
            throw new RuntimeException("Expected null for absent annotation, got non-null");
        }

        // Test 4: getAnnotation with unrelated annotation returns null
        Deprecated dep = annotated.getAnnotation(Deprecated.class);
        if (dep != null) {
            throw new RuntimeException("Expected null for unrelated annotation, got non-null");
        }

        System.out.println("Executable.getAnnotation tests passed.");
    }
}

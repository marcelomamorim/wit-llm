/*
 * @test
 * @summary Test java.lang.reflect.Field.getAnnotation method behavior
 * @run main FieldWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.Objects;

public class FieldWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    public @interface TestAnnotation {
        String value() default "test";
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface AnotherAnnotation {
    }

    @TestAnnotation("present")
    public int annotatedField;

    public int nonAnnotatedField;

    public static void main(String[] args) throws Exception {
        FieldWitupTest testInstance = new FieldWitupTest();

        // Test 1: Passing null to getAnnotation should throw NullPointerException
        try {
            Field.class.getMethod("getAnnotation", Class.class).invoke(
                Field.class.getDeclaredField("annotatedField"), (Object) null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (java.lang.reflect.InvocationTargetException ite) {
            if (!(ite.getCause() instanceof NullPointerException)) {
                throw new RuntimeException("Expected NullPointerException but got " + ite.getCause());
            }
        }

        // Test 2: getAnnotation returns the annotation instance when present
        Field fAnnotated = FieldWitupTest.class.getField("annotatedField");
        TestAnnotation ann = fAnnotated.getAnnotation(TestAnnotation.class);
        if (ann == null) {
            throw new RuntimeException("Expected TestAnnotation instance but got null");
        }
        if (!"present".equals(ann.value())) {
            throw new RuntimeException("Annotation value mismatch: expected 'present' but got '" + ann.value() + "'");
        }

        // Test 3: getAnnotation returns null when annotation is not present
        Field fNonAnnotated = FieldWitupTest.class.getField("nonAnnotatedField");
        TestAnnotation ann2 = fNonAnnotated.getAnnotation(TestAnnotation.class);
        if (ann2 != null) {
            throw new RuntimeException("Expected null for non-present annotation but got " + ann2);
        }

        // Test 4: getAnnotation returns null for different annotation type not present
        AnotherAnnotation ann3 = fAnnotated.getAnnotation(AnotherAnnotation.class);
        if (ann3 != null) {
            throw new RuntimeException("Expected null for non-present AnotherAnnotation but got " + ann3);
        }

        System.out.println("All tests passed.");
    }
}

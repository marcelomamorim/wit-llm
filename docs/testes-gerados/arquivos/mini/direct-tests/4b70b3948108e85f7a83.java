/*
 * @test
 * @summary Test java.lang.reflect.Field.getAnnotationsByType method
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
    public @interface RepeatableAnnotation {
        String name() default "repeat";
    }

    @TestAnnotation("field")
    @RepeatableAnnotation(name = "one")
    @RepeatableAnnotation(name = "two")
    private int annotatedField;

    private int nonAnnotatedField;

    public static void main(String[] args) throws Exception {
        FieldWitupTest testInstance = new FieldWitupTest();

        // Test 1: Passing null to getAnnotationsByType should throw NullPointerException
        Field f = FieldWitupTest.class.getDeclaredField("annotatedField");
        try {
            f.getAnnotationsByType(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        }

        // Test 2: Get annotations of type TestAnnotation on annotatedField
        TestAnnotation[] testAnns = f.getAnnotationsByType(TestAnnotation.class);
        if (testAnns == null || testAnns.length != 1 || !"field".equals(testAnns[0].value())) {
            throw new RuntimeException("Failed to get correct TestAnnotation from annotatedField");
        }

        // Test 3: Get annotations of type RepeatableAnnotation on annotatedField
        RepeatableAnnotation[] repeatAnns = f.getAnnotationsByType(RepeatableAnnotation.class);
        if (repeatAnns == null || repeatAnns.length != 2) {
            throw new RuntimeException("Failed to get correct RepeatableAnnotation array length");
        }
        boolean foundOne = false;
        boolean foundTwo = false;
        for (RepeatableAnnotation ra : repeatAnns) {
            if ("one".equals(ra.name())) foundOne = true;
            if ("two".equals(ra.name())) foundTwo = true;
        }
        if (!foundOne || !foundTwo) {
            throw new RuntimeException("RepeatableAnnotation values not found as expected");
        }

        // Test 4: Get annotations of type TestAnnotation on nonAnnotatedField should return empty array
        Field nf = FieldWitupTest.class.getDeclaredField("nonAnnotatedField");
        TestAnnotation[] emptyAnns = nf.getAnnotationsByType(TestAnnotation.class);
        if (emptyAnns == null || emptyAnns.length != 0) {
            throw new RuntimeException("Expected empty array for nonAnnotatedField annotations");
        }

        System.out.println("All tests passed.");
    }
}

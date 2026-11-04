/*
 * @test
 * @summary Test getDeclaredAnnotationsByType method of java.lang.Class
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

    @Retention(RetentionPolicy.RUNTIME)
    public @interface RepeatableAnnotation {
        String name() default "repeat";
    }

    @TestAnnotation("class")
    @RepeatableAnnotation(name = "one")
    @RepeatableAnnotation(name = "two")
    static class AnnotatedClass {
    }

    public static void main(String[] args) {
        // Test 1: Passing null should throw NullPointerException
        try {
            Class.class.getDeclaredAnnotationsByType(null);
            throw new RuntimeException("Expected NullPointerException for null argument");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test 2: Request annotations present on AnnotatedClass
        Class<AnnotatedClass> clazz = AnnotatedClass.class;

        TestAnnotation[] testAnnotations = clazz.getDeclaredAnnotationsByType(TestAnnotation.class);
        if (testAnnotations == null || testAnnotations.length != 1 || !"class".equals(testAnnotations[0].value())) {
            throw new RuntimeException("Failed to get TestAnnotation correctly");
        }

        RepeatableAnnotation[] repeatableAnnotations = clazz.getDeclaredAnnotationsByType(RepeatableAnnotation.class);
        if (repeatableAnnotations == null || repeatableAnnotations.length != 2) {
            throw new RuntimeException("Failed to get RepeatableAnnotation array correctly");
        }

        boolean foundOne = false;
        boolean foundTwo = false;
        for (RepeatableAnnotation ra : repeatableAnnotations) {
            if ("one".equals(ra.name())) {
                foundOne = true;
            } else if ("two".equals(ra.name())) {
                foundTwo = true;
            }
        }
        if (!foundOne || !foundTwo) {
            throw new RuntimeException("RepeatableAnnotation values not found as expected");
        }

        // Test 3: Request annotation type not present returns empty array
        Deprecated[] deprecatedAnnotations = clazz.getDeclaredAnnotationsByType(Deprecated.class);
        if (deprecatedAnnotations == null || deprecatedAnnotations.length != 0) {
            throw new RuntimeException("Expected empty array for annotation type not present");
        }
    }
}

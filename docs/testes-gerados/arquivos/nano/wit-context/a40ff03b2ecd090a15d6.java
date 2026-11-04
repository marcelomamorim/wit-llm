/*
 * @test
 * @summary Test for java.lang.Class.getAnnotation method, including null input handling
 * @run main ClassWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.Class;

public class ClassWitupTest {

    public static void main(String[] args) {
        testGetAnnotationWithNull();
        testGetAnnotationWithValidClass();
        testGetAnnotationWithAbsentAnnotation();
    }

    private static void testGetAnnotationWithNull() {
        Class<?> cls = String.class;
        try {
            cls.getAnnotation(null);
            System.err.println("Failed: Expected NullPointerException for null annotationClass");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected for null annotationClass");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception type: " + t);
        }
    }

    private static void testGetAnnotationWithValidClass() {
        Class<?> cls = java.lang.Deprecated.class;
        Annotation annotation = cls.getAnnotation(Deprecated.class);
        if (annotation != null && annotation.annotationType() == Deprecated.class) {
            System.out.println("Passed: getAnnotation returns correct annotation for Deprecated");
        } else {
            System.err.println("Failed: getAnnotation did not return expected Deprecated annotation");
        }
    }

    private static void testGetAnnotationWithAbsentAnnotation() {
        Class<?> cls = String.class;
        Annotation annotation = cls.getAnnotation(Override.class);
        if (annotation == null) {
            System.out.println("Passed: getAnnotation returns null for absent annotation");
        } else {
            System.err.println("Failed: getAnnotation should return null for absent annotation");
        }
    }
}

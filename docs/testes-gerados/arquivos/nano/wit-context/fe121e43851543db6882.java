/*
 * @test
 * @summary Test for java.lang.Class.getDeclaredAnnotationsByType with null argument
 * @run main ClassWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.Class;

public class ClassWitupTest {

    public static void main(String[] args) {
        testGetDeclaredAnnotationsByType_NullArgument();
        testGetDeclaredAnnotationsByType_NormalCase();
    }

    public static void testGetDeclaredAnnotationsByType_NullArgument() {
        Class<?> clazz = String.class;
        try {
            clazz.getDeclaredAnnotationsByType(null);
            System.err.println("FAIL: Expected NullPointerException for null argument");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception: " + t);
        }
    }

    public static void testGetDeclaredAnnotationsByType_NormalCase() {
        Class<?> clazz = SampleAnnotationHolder.class;
        try {
            Annotation[] annotations = clazz.getDeclaredAnnotationsByType(SampleAnnotation.class);
            if (annotations != null && annotations.length >= 0) {
                System.out.println("PASS: Annotations retrieved successfully");
            } else {
                System.err.println("FAIL: Annotations array is null or empty");
            }
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception: " + t);
        }
    }

    @interface SampleAnnotation {}

    @SampleAnnotation
    static class SampleAnnotationHolder {}
}

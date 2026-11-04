/*
 * @test
 * @summary Test for Method.getAnnotation(Class)
 * @run main MethodWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class MethodWitupTest {

    public static void main(String[] args) {
        testGetAnnotationWithNull();
        testGetAnnotationWithValidClass();
    }

    private static void testGetAnnotationWithNull() {
        try {
            Method m = String.class.getMethod("length");
            m.getAnnotation(null);
            System.err.println("Failed: Expected NullPointerException for null annotationClass");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception: " + e);
            System.exit(1);
        }
    }

    private static void testGetAnnotationWithValidClass() {
        try {
            Method m = String.class.getMethod("length");
            Annotation annotation = m.getAnnotation(Override.class);
            // Since String.length() does not have Override annotation, expect null
            if (annotation != null) {
                System.err.println("Failed: Expected null for annotation not present");
                System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception: " + e);
            System.exit(1);
        }
    }
}

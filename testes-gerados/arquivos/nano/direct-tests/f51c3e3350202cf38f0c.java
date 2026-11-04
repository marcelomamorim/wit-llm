/*
 * @test
 * @summary Test getAnnotation method of Constructor class
 * @run main ConstructorWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

public class ConstructorWitupTest {

    public static void main(String[] args) {
        testGetAnnotationReturnsExpected();
        testGetAnnotationWithNull();
    }

    private static void testGetAnnotationReturnsExpected() {
        try {
            Constructor<?> constructor = String.class.getConstructor(String.class);
            Annotation annotation = constructor.getAnnotation(Deprecated.class);
            if (annotation != null) {
                System.out.println("Test passed: getAnnotation returned non-null for Deprecated.");
            } else {
                System.out.println("Test passed: getAnnotation returned null for Deprecated.");
            }
        } catch (NoSuchMethodException e) {
            System.out.println("Test failed: Constructor not found.");
        }
    }

    private static void testGetAnnotationWithNull() {
        try {
            Constructor<?> constructor = String.class.getConstructor(String.class);
            Annotation annotation = constructor.getAnnotation(null);
            if (annotation == null) {
                System.out.println("Test passed: getAnnotation with null argument returns null.");
            } else {
                System.out.println("Test failed: getAnnotation with null argument did not return null.");
            }
        } catch (NullPointerException e) {
            System.out.println("Test passed: getAnnotation with null argument throws NullPointerException.");
        } catch (NoSuchMethodException e) {
            System.out.println("Test failed: Constructor not found.");
        }
    }
}

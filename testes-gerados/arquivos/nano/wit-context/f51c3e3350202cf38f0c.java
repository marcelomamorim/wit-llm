/*
 * @test
 * @summary Test for Constructor.getAnnotation method, including null argument handling.
 * @run main ConstructorWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

public class ConstructorWitupTest {

    public static void main(String[] args) {
        testGetAnnotationWithValidClass();
        testGetAnnotationWithNull();
    }

    private static void testGetAnnotationWithValidClass() {
        try {
            Constructor<?> constructor = String.class.getConstructor();
            Annotation annotation = constructor.getAnnotation(Deprecated.class);
            // No specific assertion; just ensure no exception and method call completes
            System.out.println("testGetAnnotationWithValidClass passed.");
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in testGetAnnotationWithValidClass", e);
        }
    }

    private static void testGetAnnotationWithNull() {
        try {
            Constructor<?> constructor = String.class.getConstructor();
            constructor.getAnnotation(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("testGetAnnotationWithNull passed.");
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in testGetAnnotationWithNull", e);
        }
    }
}

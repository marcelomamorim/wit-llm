/*
 * @test
 * @summary Test for java.lang.Class.getDeclaredAnnotation
 * @run main ClassWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.Class;

public class ClassWitupTest {

    public static void main(String[] args) {
        testNullArgument();
        testValidAnnotationRetrieval();
    }

    private static void testNullArgument() {
        Class<?> cls = String.class;
        try {
            cls.getDeclaredAnnotation(null);
            System.err.println("Failed: Expected NullPointerException for null argument");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
        }
    }

    private static void testValidAnnotationRetrieval() {
        // Since getDeclaredAnnotation requires an annotation class, create a dummy annotation
        @interface DummyAnnotation {}
        class AnnotatedClass {
            @DummyAnnotation
            public void method() {}
        }
        Class<?> cls = AnnotatedClass.class;
        DummyAnnotation annotation = cls.getDeclaredAnnotation(DummyAnnotation.class);
        if (annotation != null) {
            System.out.println("Passed: Annotation retrieved successfully");
        } else {
            System.err.println("Failed: Annotation not retrieved");
        }
    }
}

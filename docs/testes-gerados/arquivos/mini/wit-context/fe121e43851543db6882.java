/*
 * @test
 * @summary Test getDeclaredAnnotationsByType method of java.lang.Class for null argument and normal behavior
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

    @TestAnnotation("class")
    static class AnnotatedClass {}

    public static void main(String[] args) {
        testNullPointerException();
        testGetDeclaredAnnotationsByType();
    }

    private static void testNullPointerException() {
        try {
            Class<?> c = AnnotatedClass.class;
            c.getDeclaredAnnotationsByType(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testGetDeclaredAnnotationsByType() {
        Class<AnnotatedClass> c = AnnotatedClass.class;
        TestAnnotation[] annotations = c.getDeclaredAnnotationsByType(TestAnnotation.class);
        if (annotations == null) {
            throw new RuntimeException("Returned annotations array is null");
        }
        if (annotations.length == 0) {
            throw new RuntimeException("Expected at least one annotation");
        }
        boolean found = false;
        for (TestAnnotation a : annotations) {
            if ("class".equals(a.value())) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new RuntimeException("Expected annotation with value 'class' not found");
        }
    }
}

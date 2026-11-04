/*
 * @test
 * @summary Test for getDeclaredAnnotationsByType method in Class
 * @run main ClassWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.Class;

public class ClassWitupTest {
    public static void main(String[] args) {
        testGetDeclaredAnnotationsByType();
    }

    public static void testGetDeclaredAnnotationsByType() {
        Class<?> cls = SampleAnnotations.class;
        Annotation[] annotations = cls.getDeclaredAnnotationsByType(SampleAnnotation.class);
        boolean found = false;
        for (Annotation a : annotations) {
            if (a instanceof SampleAnnotation) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new AssertionError("SampleAnnotation not found in annotations");
        }
        // Test with annotation type not present
        Annotation[] noAnnotations = cls.getDeclaredAnnotationsByType(NonExistentAnnotation.class);
        if (noAnnotations.length != 0) {
            throw new AssertionError("Expected no annotations for NonExistentAnnotation");
        }
    }

    @interface SampleAnnotation {
        String value();
    }

    @SampleAnnotation("test")
    static class SampleAnnotations {
        // class with annotation
    }

    @interface NonExistentAnnotation {
        // annotation not present
    }
}
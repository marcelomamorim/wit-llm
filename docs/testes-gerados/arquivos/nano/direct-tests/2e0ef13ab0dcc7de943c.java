/*
 * @test
 * @summary Test getAnnotationsByType method of java.lang.Class
 * @run main ClassWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.Class;

public class ClassWitupTest {
    public static void main(String[] args) {
        testGetAnnotationsByType();
    }

    public static void testGetAnnotationsByType() {
        Class<SampleAnnotation> annotationClass = SampleAnnotation.class;
        Class<SampleAnnotated> clazz = SampleAnnotated.class;
        SampleAnnotated instance = new SampleAnnotated();
        Annotation[] annotations = clazz.getAnnotationsByType(annotationClass);
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
    }

    @SampleAnnotation
    static class SampleAnnotated {
    }

    @interface SampleAnnotation {
}
}
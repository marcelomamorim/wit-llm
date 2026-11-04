/*
 * @test
 * @summary Test AccessibleObject.getAnnotations() returns declared annotations
 * @run main AccessibleObjectWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

public class AccessibleObjectWitupTest {
    public static void main(String[] args) throws Exception {
        // Obtain a Method object which has known annotations
        Method m = Sample.class.getMethod("annotatedMethod");
        AccessibleObject ao = m;

        Annotation[] annotations = ao.getAnnotations();

        if (annotations == null) {
            throw new RuntimeException("getAnnotations() returned null");
        }

        boolean found = false;
        for (Annotation a : annotations) {
            if (a.annotationType() == SampleAnnotation.class) {
                found = true;
                break;
            }
        }

        if (!found) {
            throw new RuntimeException("Expected annotation SampleAnnotation not found");
        }
    }

    @interface SampleAnnotation {}

    public static class Sample {
        @SampleAnnotation
        public void annotatedMethod() {}
    }
}

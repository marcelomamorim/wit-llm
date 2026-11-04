/*
 * @test
 * @summary Tests for java.lang.reflect.Parameter.getDeclaredAnnotation
 * @run main ParameterWitupTest
 */

import java.lang.reflect.Parameter;
import java.lang.annotation.Annotation;

public class ParameterWitupTest {

    public static void main(String[] args) {
        testGetDeclaredAnnotationWithNull();
        testGetDeclaredAnnotationWithValid();
    }

    private static void testGetDeclaredAnnotationWithNull() {
        try {
            Parameter param = createSampleParameter();
            param.getDeclaredAnnotation(null);
            System.out.println("FAIL: Expected NullPointerException for null annotationClass");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testGetDeclaredAnnotationWithValid() {
        try {
            Parameter param = createSampleParameter();
            Class<? extends Annotation> annotationClass = SampleAnnotation.class;
            Annotation result = param.getDeclaredAnnotation(annotationClass);
            if (result == null) {
                System.out.println("FAIL: Expected non-null annotation");
            } else {
                System.out.println("PASS: getDeclaredAnnotation returned annotation as expected");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static Parameter createSampleParameter() {
        // Since creating a real Parameter instance is complex, use reflection to get one from a dummy method
        try {
            return DummyClass.class.getMethod("dummyMethod", String.class).getParameters()[0];
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static class SampleAnnotation extends java.lang.annotation.Annotation {
        // Dummy annotation for testing
        @Override
        public Class<? extends java.lang.annotation.Annotation> annotationType() {
            return SampleAnnotation.class;
        }
    }

    public static class DummyClass {
        public void dummyMethod(@SampleAnnotation String param) {
            // dummy method
        }
    }
}

/*
 * @test
 * @summary Tests for java.lang.reflect.Parameter.getDeclaredAnnotationsByType
 * @run main ParameterWitupTest
 */

import java.lang.reflect.Parameter;
import java.lang.annotation.Annotation;

public class ParameterWitupTest {

    public static void main(String[] args) {
        testNullAnnotationClass();
        testValidAnnotationClass();
    }

    private static void testNullAnnotationClass() {
        try {
            Parameter param = createSampleParameter();
            param.getDeclaredAnnotationsByType(null);
            System.out.println("FAIL: Expected NullPointerException for null annotationClass");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testValidAnnotationClass() {
        try {
            Parameter param = createSampleParameter();
            Annotation[] annotations = param.getDeclaredAnnotationsByType(SampleAnnotation.class);
            if (annotations != null) {
                System.out.println("PASS: getDeclaredAnnotationsByType returned annotations");
            } else {
                System.out.println("FAIL: getDeclaredAnnotationsByType returned null");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static Parameter createSampleParameter() {
        // Since creating a real Parameter instance is complex, use reflection to get a Parameter from a method
        try {
            class Dummy {
                @SampleAnnotation
                public void dummyMethod() {}
            }
            return Dummy.class.getMethod("dummyMethod").getParameters()[0];
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @interface SampleAnnotation {}
}

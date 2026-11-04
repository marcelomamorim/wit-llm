/*
 * @test
 * @summary Test getAnnotationsByType method of java.lang.reflect.Parameter
 * @run main ParameterWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

public class ParameterWitupTest {

    public static void main(String[] args) throws Exception {
        testGetAnnotationsByTypeNullArgument();
        testGetAnnotationsByTypeNonNullArgument();
    }

    // Test that getAnnotationsByType throws NullPointerException when passed null
    private static void testGetAnnotationsByTypeNullArgument() throws Exception {
        Method method = ParameterWitupTest.class.getMethod("sampleMethod", String.class);
        Parameter param = method.getParameters()[0];
        try {
            param.getAnnotationsByType(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    // Test that getAnnotationsByType returns correct annotations array for a known annotation
    private static void testGetAnnotationsByTypeNonNullArgument() throws Exception {
        Method method = ParameterWitupTest.class.getMethod("sampleMethod", String.class);
        Parameter param = method.getParameters()[0];
        Annotation[] annotations = param.getAnnotationsByType(SampleAnnotation.class);
        if (annotations == null) {
            throw new RuntimeException("Returned annotations array is null");
        }
        // The sampleMethod parameter is annotated with @SampleAnnotation
        boolean found = false;
        for (Annotation a : annotations) {
            if (a instanceof SampleAnnotation) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new RuntimeException("Expected SampleAnnotation not found in annotations");
        }
    }

    @interface SampleAnnotation {}

    // Method with a parameter annotated with @SampleAnnotation
    public void sampleMethod(@SampleAnnotation String param) {}
}

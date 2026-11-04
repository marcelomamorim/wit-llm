/*
 * @test
 * @summary Test getDeclaredAnnotationsByType method of java.lang.reflect.Parameter
 * @run main ParameterWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;

public class ParameterWitupTest {
    public static void main(String[] args) {
        // We cannot instantiate Parameter directly (no public constructor),
        // so we test getDeclaredAnnotationsByType indirectly by calling it on
        // a known Parameter instance obtained via reflection.

        try {
            // Obtain a Parameter instance from a known method
            java.lang.reflect.Method method = Sample.class.getDeclaredMethod("sampleMethod", String.class);
            Parameter[] params = method.getParameters();
            if (params.length == 0) {
                throw new RuntimeException("No parameters found on sampleMethod");
            }
            Parameter param = params[0];

            // Test 1: Passing null should throw NullPointerException
            try {
                param.getDeclaredAnnotationsByType(null);
                throw new RuntimeException("Expected NullPointerException not thrown");
            } catch (NullPointerException expected) {
                // Expected exception
            }

            // Test 2: Passing an annotation class that is not present returns empty array
            Annotation[] anns = param.getDeclaredAnnotationsByType(Deprecated.class);
            if (anns == null) {
                throw new RuntimeException("Returned array is null");
            }
            if (anns.length != 0) {
                throw new RuntimeException("Expected empty array for annotation not present");
            }

            // Test 3: Passing an annotation class that is present returns non-empty array
            Annotation[] anns2 = param.getDeclaredAnnotationsByType(MyAnnotation.class);
            if (anns2 == null || anns2.length == 0) {
                throw new RuntimeException("Expected non-empty array for present annotation");
            }

        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException("Unexpected exception during test setup", e);
        }
    }

    // Sample class with a method having a parameter annotated with MyAnnotation
    static class Sample {
        public void sampleMethod(@MyAnnotation String param) {
        }
    }

    // Custom annotation for testing
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
    @interface MyAnnotation {
    }
}

/*
 * @test
 * @summary Test getDeclaredAnnotation method of java.lang.reflect.Parameter for null argument handling and normal behavior
 * @run main ParameterWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class ParameterWitupTest {
    public static void main(String[] args) throws Exception {
        // Obtain a Method with parameters to get Parameter instances
        Method method = ParameterWitupTest.class.getMethod("sampleMethod", String.class);
        Parameter[] params = method.getParameters();
        if (params.length == 0) {
            throw new RuntimeException("No parameters found for sampleMethod");
        }
        Parameter param = params[0];

        // Test 1: getDeclaredAnnotation with null argument should throw NullPointerException
        try {
            param.getDeclaredAnnotation(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }

        // Test 2: getDeclaredAnnotation with a valid annotation class returns null (no annotation present)
        Annotation ann = param.getDeclaredAnnotation(Deprecated.class);
        if (ann != null) {
            throw new RuntimeException("Expected null for annotation not present");
        }

        // Test 3: getDeclaredAnnotation with an annotation present
        Method annotatedMethod = ParameterWitupTest.class.getMethod("annotatedMethod", String.class);
        Parameter annotatedParam = annotatedMethod.getParameters()[0];
        Deprecated depAnn = annotatedParam.getDeclaredAnnotation(Deprecated.class);
        if (depAnn == null) {
            throw new RuntimeException("Expected Deprecated annotation to be present");
        }
    }

    public void sampleMethod(String param) {
        // no annotations
    }

    public void annotatedMethod(@Deprecated String param) {
        // parameter annotated with @Deprecated
    }
}

/* @test    @summary Test getDeclaredAnnotationsByType method in Parameter class    @run main ParameterWitupTest */

import java.lang.annotation.*;
import java.lang.reflect.*;

public class ParameterWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.TYPE})
    public @interface SampleAnnotation {}

    public static class TestClass {
        public void testMethod(@SampleAnnotation String param) {}
    }

    public static void main(String[] args) throws Exception {
        Method method = TestClass.class.getMethod("testMethod", String.class);
        Parameter[] params = method.getParameters();
        if (params.length > 0) {
            Parameter param = params[0];
            SampleAnnotation[] annotations = param.getDeclaredAnnotationsByType(SampleAnnotation.class);
            if (annotations != null && annotations.length > 0) {
                System.out.println("Annotations found: " + annotations.length);
            } else {
                System.out.println("No annotations found.");
            }
        } else {
            System.out.println("No parameters found.");
        }
    }
}

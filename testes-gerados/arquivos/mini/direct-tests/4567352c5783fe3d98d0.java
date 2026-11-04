/*
 * @test
 * @summary Test getDeclaredAnnotation method of java.lang.reflect.Parameter
 * @run main ParameterWitupTest
 */

import java.lang.annotation.*;
import java.lang.reflect.*;

public class ParameterWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.PARAMETER)
    public @interface TestAnnotation {
        String value() default "";
    }

    public static void methodWithAnnotatedParam(@TestAnnotation("test") String param) {}
    public static void methodWithNonAnnotatedParam(String param) {}

    public static void main(String[] args) throws Exception {
        // Obtain Parameter instances for annotated and non-annotated parameters
        Method m1 = ParameterWitupTest.class.getMethod("methodWithAnnotatedParam", String.class);
        Parameter p1 = m1.getParameters()[0];

        Method m2 = ParameterWitupTest.class.getMethod("methodWithNonAnnotatedParam", String.class);
        Parameter p2 = m2.getParameters()[0];

        // Test getDeclaredAnnotation returns the annotation instance when present
        TestAnnotation ann1 = p1.getDeclaredAnnotation(TestAnnotation.class);
        if (ann1 == null || !"test".equals(ann1.value())) {
            throw new RuntimeException("Expected TestAnnotation with value 'test' on parameter");
        }

        // Test getDeclaredAnnotation returns null when annotation is not present
        TestAnnotation ann2 = p2.getDeclaredAnnotation(TestAnnotation.class);
        if (ann2 != null) {
            throw new RuntimeException("Expected null when annotation not present on parameter");
        }

        // Test getDeclaredAnnotation with annotation class not present on parameter
        Deprecated dep = p1.getDeclaredAnnotation(Deprecated.class);
        if (dep != null) {
            throw new RuntimeException("Expected null for annotation not present on parameter");
        }

        System.out.println("All tests passed.");
    }
}

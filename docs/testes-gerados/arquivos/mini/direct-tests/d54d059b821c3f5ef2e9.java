/*
 * @test
 * @summary Test getDeclaredAnnotationsByType method of java.lang.reflect.Parameter
 * @run main ParameterWitupTest
 */

import java.lang.annotation.*;
import java.lang.reflect.*;

public class ParameterWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.PARAMETER)
    @interface TestAnnotation {
        String value() default "";
    }

    public static void methodWithAnnotations(@TestAnnotation("a") @TestAnnotation("b") String param) {}

    public static void methodWithoutAnnotations(String param) {}

    public static void main(String[] args) throws Exception {
        // Obtain Parameter with annotations
        Method m1 = ParameterWitupTest.class.getMethod("methodWithAnnotations", String.class);
        Parameter p1 = m1.getParameters()[0];

        TestAnnotation[] anns1 = p1.getDeclaredAnnotationsByType(TestAnnotation.class);
        if (anns1 == null || anns1.length != 2) {
            throw new RuntimeException("Expected 2 TestAnnotation annotations, got " + (anns1 == null ? "null" : anns1.length));
        }
        if (!"a".equals(anns1[0].value()) && !"b".equals(anns1[1].value()) && !"b".equals(anns1[0].value()) && !"a".equals(anns1[1].value())) {
            throw new RuntimeException("Annotation values do not match expected 'a' and 'b'");
        }

        // Obtain Parameter without annotations
        Method m2 = ParameterWitupTest.class.getMethod("methodWithoutAnnotations", String.class);
        Parameter p2 = m2.getParameters()[0];

        TestAnnotation[] anns2 = p2.getDeclaredAnnotationsByType(TestAnnotation.class);
        if (anns2 == null) {
            throw new RuntimeException("Expected non-null array for no annotations");
        }
        if (anns2.length != 0) {
            throw new RuntimeException("Expected 0 annotations, got " + anns2.length);
        }

        // Test with standard annotation (Override) which is not on parameter
        Override[] overrideAnns = p1.getDeclaredAnnotationsByType(Override.class);
        if (overrideAnns == null) {
            throw new RuntimeException("Expected non-null array for no Override annotations");
        }
        if (overrideAnns.length != 0) {
            throw new RuntimeException("Expected 0 Override annotations, got " + overrideAnns.length);
        }

        System.out.println("Test passed.");
    }
}

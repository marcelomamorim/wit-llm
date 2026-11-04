/*
 * @test
 * @summary Test getAnnotationsByType method of java.lang.reflect.Parameter
 * @run main ParameterWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

public class ParameterWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    public @interface TestAnnotation {
        String value() default "";
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface AnotherAnnotation {
    }

    public static class TestClass {
        public void method(
                @TestAnnotation("a")
                @AnotherAnnotation
                String param) {
        }

        public void methodNoAnnotations(String param) {
        }
    }

    public static void main(String[] args) throws Exception {
        Method m = TestClass.class.getMethod("method", String.class);
        Parameter p = m.getParameters()[0];

        // Test getAnnotationsByType with TestAnnotation.class
        TestAnnotation[] testAnns = p.getAnnotationsByType(TestAnnotation.class);
        if (testAnns == null || testAnns.length != 1 || !"a".equals(testAnns[0].value())) {
            throw new RuntimeException("Failed to get correct TestAnnotation");
        }

        // Test getAnnotationsByType with AnotherAnnotation.class
        AnotherAnnotation[] anotherAnns = p.getAnnotationsByType(AnotherAnnotation.class);
        if (anotherAnns == null || anotherAnns.length != 1) {
            throw new RuntimeException("Failed to get correct AnotherAnnotation");
        }

        // Test getAnnotationsByType with an annotation not present
        Deprecated[] deprecatedAnns = p.getAnnotationsByType(Deprecated.class);
        if (deprecatedAnns == null || deprecatedAnns.length != 0) {
            throw new RuntimeException("Expected empty array for annotation not present");
        }

        // Test getAnnotationsByType with null argument throws NullPointerException
        boolean npeThrown = false;
        try {
            p.getAnnotationsByType(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException for null argument");
        }

        // Test getAnnotationsByType on parameter without annotations returns empty array
        Method m2 = TestClass.class.getMethod("methodNoAnnotations", String.class);
        Parameter p2 = m2.getParameters()[0];
        Deprecated[] emptyAnns = p2.getAnnotationsByType(Deprecated.class);
        if (emptyAnns == null || emptyAnns.length != 0) {
            throw new RuntimeException("Expected empty array for parameter without annotations");
        }

        System.out.println("All tests passed.");
    }
}

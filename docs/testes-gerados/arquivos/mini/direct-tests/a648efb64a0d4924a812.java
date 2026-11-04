/*
 * @test
 * @summary Test getAnnotationsByType method of Executable
 * @run main ExecutableWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

public class ExecutableWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    public @interface TestAnnotation {
        String value() default "";
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface RepeatableAnnotation {
        String name() default "";
    }

    @RepeatableAnnotation(name = "first")
    @RepeatableAnnotation(name = "second")
    public void annotatedMethod() {}

    public static void main(String[] args) throws Exception {
        ExecutableWitupTest test = new ExecutableWitupTest();
        test.testNullArgument();
        test.testNoAnnotations();
        test.testSingleAnnotation();
        test.testMultipleAnnotations();
    }

    // Helper to get Executable for annotatedMethod
    private Executable getAnnotatedMethodExecutable() throws NoSuchMethodException {
        return ExecutableWitupTest.class.getMethod("annotatedMethod");
    }

    private Executable getMethodWithoutAnnotations() throws NoSuchMethodException {
        return ExecutableWitupTest.class.getMethod("testNullArgument");
    }

    public void testNullArgument() throws Exception {
        Executable exec = getMethodWithoutAnnotations();
        try {
            exec.getAnnotationsByType(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    public void testNoAnnotations() throws Exception {
        Executable exec = getMethodWithoutAnnotations();
        Annotation[] result = exec.getAnnotationsByType(TestAnnotation.class);
        if (result == null) {
            throw new RuntimeException("Returned array is null");
        }
        if (result.length != 0) {
            throw new RuntimeException("Expected empty array for method without annotations");
        }
    }

    public void testSingleAnnotation() throws Exception {
        Method m = SingleAnnotationClass.class.getMethod("singleAnnotatedMethod");
        Executable exec = m;
        TestAnnotation[] annotations = exec.getAnnotationsByType(TestAnnotation.class);
        if (annotations == null || annotations.length != 1) {
            throw new RuntimeException("Expected exactly one TestAnnotation");
        }
        if (!"single".equals(annotations[0].value())) {
            throw new RuntimeException("Annotation value mismatch");
        }
    }

    public void testMultipleAnnotations() throws Exception {
        Executable exec = getAnnotatedMethodExecutable();
        RepeatableAnnotation[] annotations = exec.getAnnotationsByType(RepeatableAnnotation.class);
        if (annotations == null) {
            throw new RuntimeException("Returned array is null");
        }
        if (annotations.length != 2) {
            throw new RuntimeException("Expected two RepeatableAnnotation annotations");
        }
        boolean foundFirst = false;
        boolean foundSecond = false;
        for (RepeatableAnnotation a : annotations) {
            if ("first".equals(a.name())) {
                foundFirst = true;
            } else if ("second".equals(a.name())) {
                foundSecond = true;
            }
        }
        if (!foundFirst || !foundSecond) {
            throw new RuntimeException("Did not find expected annotation names");
        }
    }

    public static class SingleAnnotationClass {
        @TestAnnotation("single")
        public void singleAnnotatedMethod() {}
    }
}

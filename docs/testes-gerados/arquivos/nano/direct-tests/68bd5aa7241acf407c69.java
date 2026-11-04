/*
 * @test
 * @summary Test getAnnotation method of java.lang.reflect.Method
 * @run main MethodWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class MethodWitupTest {

    public static void main(String[] args) throws Exception {
        testGetAnnotationOnMethod();
        testGetAnnotationOnMethodWithNoAnnotation();
    }

    private static void testGetAnnotationOnMethod() throws Exception {
        Method method = SampleClass.class.getDeclaredMethod("annotatedMethod");
        Annotation annotation = method.getAnnotation(SampleAnnotation.class);
        if (annotation == null) {
            throw new AssertionError("Expected annotation not found");
        }
        if (!(annotation instanceof SampleAnnotation)) {
            throw new AssertionError("Annotation is not of expected type");
        }
        SampleAnnotation sampleAnnotation = (SampleAnnotation) annotation;
        if (!"testValue".equals(sampleAnnotation.value())) {
            throw new AssertionError("Annotation value mismatch");
        }
    }

    private static void testGetAnnotationOnMethodWithNoAnnotation() throws Exception {
        Method method = SampleClass.class.getDeclaredMethod("nonAnnotatedMethod");
        Annotation annotation = method.getAnnotation(SampleAnnotation.class);
        if (annotation != null) {
            throw new AssertionError("Expected no annotation, but found one");
        }
    }

    public static class SampleClass {
        @SampleAnnotation("testValue")
        public void annotatedMethod() {
        }
        public void nonAnnotatedMethod() {
        }
    }

    public @interface SampleAnnotation {
        String value();
    }
}

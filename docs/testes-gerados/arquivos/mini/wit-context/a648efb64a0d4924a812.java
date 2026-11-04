/*
 * @test
 * @summary Test getAnnotationsByType method of java.lang.reflect.Executable for null argument and normal usage
 * @run main ExecutableWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.reflect.Executable;
import java.util.Objects;

public class ExecutableWitupTest {

    // Minimal subclass to test abstract Executable
    static class DummyExecutable extends Executable {
        private final Annotation[] annotations;

        DummyExecutable(Annotation[] annotations) {
            this.annotations = annotations;
        }

        @Override
        byte[] getAnnotationBytes() {
            return new byte[0];
        }

        @Override
        boolean hasGenericInformation() {
            return false;
        }

        @Override
        ConstructorRepository getGenericInfo() {
            return null;
        }

        @Override
        public Class<?> getDeclaringClass() {
            return DummyExecutable.class;
        }

        @Override
        public int getModifiers() {
            return 0;
        }

        @Override
        public String getName() {
            return "dummy";
        }

        @Override
        public Class<?>[] getParameterTypes() {
            return new Class<?>[0];
        }

        @Override
        public Class<?>[] getExceptionTypes() {
            return new Class<?>[0];
        }

        @Override
        public Annotation[] getDeclaredAnnotations() {
            return annotations;
        }

        // Provide declaredAnnotations method used by getAnnotationsByType
        Annotation[] declaredAnnotations() {
            return annotations;
        }
    }

    public static void main(String[] args) {
        testNullArgument();
        testNormalUsage();
        System.out.println("ExecutableWitupTest passed");
    }

    private static void testNullArgument() {
        DummyExecutable exec = new DummyExecutable(new Annotation[0]);
        try {
            exec.getAnnotationsByType(null);
            throw new RuntimeException("Expected NullPointerException for null argument");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testNormalUsage() {
        // Use a known annotation type: Override.class
        DummyExecutable exec = new DummyExecutable(new Annotation[0]);
        // The method calls AnnotationSupport.getDirectlyAndIndirectlyPresent(declaredAnnotations(), annotationClass)
        // Since declaredAnnotations is empty, result should be empty array
        Annotation[] result = exec.getAnnotationsByType(Override.class);
        if (result == null) {
            throw new RuntimeException("getAnnotationsByType returned null instead of empty array");
        }
        if (result.length != 0) {
            throw new RuntimeException("Expected empty array for no annotations");
        }
    }
}
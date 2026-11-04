/*
 * @test
 * @summary Test java.lang.reflect.Executable.getAnnotation method
 * @run main ExecutableWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.reflect.Executable;
import java.util.Objects;

public class ExecutableWitupTest {

    // Minimal subclass to allow testing abstract Executable
    static class TestExecutable extends Executable {
        private final java.util.Map<Class<? extends Annotation>, Annotation> annotations;

        TestExecutable(java.util.Map<Class<? extends Annotation>, Annotation> annotations) {
            this.annotations = annotations;
        }

        @Override
        byte[] getAnnotationBytes() {
            return null;
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
            return TestExecutable.class;
        }

        @Override
        public int getModifiers() {
            return 0;
        }

        @Override
        public String getName() {
            return "testExecutable";
        }

        @Override
        public Annotation[] getDeclaredAnnotations() {
            return new Annotation[0];
        }

        @Override
        public java.util.Map<Class<? extends Annotation>, Annotation> declaredAnnotations() {
            return annotations;
        }
    }

    public static void main(String[] args) {
        testNullAnnotationClass();
        testAnnotationPresent();
        testAnnotationAbsent();
    }

    private static void testNullAnnotationClass() {
        TestExecutable exec = new TestExecutable(java.util.Collections.emptyMap());
        try {
            exec.getAnnotation(null);
            throw new RuntimeException("Expected NullPointerException for null annotationClass");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testAnnotationPresent() {
        Deprecated deprecated = Deprecated.class.getAnnotation(Deprecated.class);
        java.util.Map<Class<? extends Annotation>, Annotation> map = new java.util.HashMap<>();
        map.put(Deprecated.class, deprecated);
        TestExecutable exec = new TestExecutable(map);

        Deprecated result = exec.getAnnotation(Deprecated.class);
        if (result != deprecated) {
            throw new RuntimeException("Expected to get the same Deprecated annotation instance");
        }
    }

    private static void testAnnotationAbsent() {
        TestExecutable exec = new TestExecutable(java.util.Collections.emptyMap());
        Override result = exec.getAnnotation(Override.class);
        if (result != null) {
            throw new RuntimeException("Expected null when annotation is not present");
        }
    }
}

/* @test    @summary Test Executable.getAnnotation method for various annotations    @run main ExecutableWitupTest */

import java.lang.annotation.*;
import java.lang.reflect.*;

public class ExecutableWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.TYPE})
    public @interface SampleAnnotation {
        String value();
    }

    static class DummyExecutable extends Executable {
        private final Map<Class<? extends Annotation>, Annotation> annotations;

        DummyExecutable(Map<Class<? extends Annotation>, Annotation> annotations) {
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
        public int getModifiers() {
            return 0;
        }

        @Override
        public Class<?> getDeclaringClass() {
            return this.getClass();
        }

        @Override
        public String sharedToString(int modifierMask, boolean isDefault, Class<?>[] parameterTypes, Class<?>[] exceptionTypes) {
            return "";
        }

        @Override
        public Annotation[][] parseParameterAnnotations(byte[] parameterAnnotations) {
            return new Annotation[0][0];
        }

        @Override
        public boolean hasGenericInformation() {
            return false;
        }

        @Override
        public ConstructorRepository getGenericInfo() {
            return null;
        }

        @Override
        public byte[] getAnnotationBytes() {
            return new byte[0];
        }

        @Override
        public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
            Objects.requireNonNull(annotationClass);
            return annotationClass.cast(annotations.get(annotationClass));
        }
    }

    public static void main(String[] args) {
        // Prepare annotations
        SampleAnnotation sampleAnn = new SampleAnnotation() {
            @Override
            public String value() {
                return "test";
            }
            @Override
            public Class<? extends Annotation> annotationType() {
                return SampleAnnotation.class;
            }
        };

        // Map of annotations
        Map<Class<? extends Annotation>, Annotation> annMap = java.util.Collections.singletonMap(SampleAnnotation.class, sampleAnn);
        // Create dummy Executable with annotations
        Executable exec = new DummyExecutable(annMap);

        // Test getAnnotation with existing annotation
        SampleAnnotation result = exec.getAnnotation(SampleAnnotation.class);
        if (result != null && "test".equals(result.value())) {
            System.out.println("Test passed: getAnnotation returned expected annotation.");
        } else {
            System.out.println("Test failed: getAnnotation did not return expected annotation.");
        }

        // Test getAnnotation with annotation not present
        class OtherAnnotation extends Annotation {
            @Override
            public Class<? extends Annotation> annotationType() {
                return null;
            }
        }
        Annotation absent = exec.getAnnotation(OtherAnnotation.class);
        if (absent == null) {
            System.out.println("Test passed: getAnnotation returned null for absent annotation.");
        } else {
            System.out.println("Test failed: getAnnotation did not return null for absent annotation.");
        }
    }
}

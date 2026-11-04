/* @test    @summary Test getAnnotationsByType method in Executable class    @run main ExecutableWitupTest */

import java.lang.annotation.*;
import java.lang.reflect.*;

public class ExecutableWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.TYPE})
    public @interface SampleAnnotation {
        String value() default "test";
    }

    public static class DummyExecutable extends Executable {
        private final Annotation[] annotations;

        public DummyExecutable(Annotation[] annotations) {
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
        public Annotation[][] parseParameterAnnotations(byte[] parameterAnnotations) {
            return new Annotation[0][0];
        }

        @Override
        public int getModifiers() {
            return 0;
        }

        @Override
        public Class<?> getDeclaringClass() {
            return ExecutableWitupTest.class;
        }

        @Override
        public String sharedToString(int modifierMask, boolean isDefault, Class<?>[] parameterTypes, Class<?>[] exceptionTypes) {
            return "";
        }

        @Override
        public Annotation[] declaredAnnotations() {
            return annotations;
        }
    }

    public static void main(String[] args) {
        try {
            // Create an executable with a specific annotation
            SampleAnnotation sampleAnn = new SampleAnnotation() {
                @Override
                public Class<? extends Annotation> annotationType() {
                    return SampleAnnotation.class;
                }
                @Override
                public String value() {
                    return "value";
                }
            };
            Annotation[] anns = new Annotation[] { sampleAnn };
            DummyExecutable exec = new DummyExecutable(anns);

            // Test getAnnotationsByType with existing annotation
            SampleAnnotation[] result = exec.getAnnotationsByType(SampleAnnotation.class);
            if (result.length != 1 || !result[0].value().equals("value")) {
                throw new AssertionError("Failed to retrieve correct annotation");
            }

            // Test getAnnotationsByType with annotation not present
            class OtherAnnotation extends Annotation {
                @Override
                public Class<? extends Annotation> annotationType() {
                    return null;
                }
            }
            Annotation[] emptyResult = exec.getAnnotationsByType(OtherAnnotation.class);
            if (emptyResult.length != 0) {
                throw new AssertionError("Expected empty array for absent annotation");
            }

            System.out.println("All tests passed.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new AssertionError("Test failed due to exception", e);
        }
    }
}

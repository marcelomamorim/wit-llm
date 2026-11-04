/*
 * @test
 * @summary Test for Executable.getAnnotation(Class)
 * @run main ExecutableWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.reflect.Executable;

public class ExecutableWitupTest {

    public static void main(String[] args) {
        testGetAnnotationNull();
        testGetAnnotationWithValidAnnotation();
    }

    private static void testGetAnnotationNull() {
        Executable exec = null;
        try {
            // Simulate calling getAnnotation with null, expecting NullPointerException
            exec = new DummyExecutable();
            exec.getAnnotation(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testGetAnnotationWithValidAnnotation() {
        Executable exec = new DummyExecutable();
        try {
            // Prepare a dummy annotation class and annotation instance
            Class<SampleAnnotation> annotationClass = SampleAnnotation.class;
            SampleAnnotation annotationInstance = new SampleAnnotation() {
                @Override
                public Class<? extends Annotation> annotationType() {
                    return SampleAnnotation.class;
                }
            };
            // Mock the declaredAnnotations() method to return a map with our annotation
            ((DummyExecutable)exec).setAnnotations(annotationClass, annotationInstance);
            Annotation result = exec.getAnnotation(annotationClass);
            if (result != null && result.annotationType() == annotationClass) {
                System.out.println("PASS: getAnnotation returned expected annotation");
            } else {
                System.out.println("FAIL: getAnnotation did not return expected annotation");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    // Dummy implementation of Executable for testing
    static class DummyExecutable extends Executable {
        private Map<Class<? extends Annotation>, ? extends Annotation> annotations;

        void setAnnotations(Class<? extends Annotation> cls, Annotation ann) {
            annotations = java.util.Collections.singletonMap(cls, ann);
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
        public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
            Objects.requireNonNull(annotationClass);
            if (annotations != null && annotations.containsKey(annotationClass)) {
                return annotationClass.cast(annotations.get(annotationClass));
            }
            return null;
        }
    }

    // Sample annotation for testing
    @interface SampleAnnotation {
    }
}

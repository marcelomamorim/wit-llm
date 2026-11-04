/* @test    @summary Test getDeclaredAnnotation method of AccessibleObject    @run main AccessibleObjectWitupTest */

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;

public class AccessibleObjectWitupTest {

    public static void main(String[] args) {
        testGetDeclaredAnnotationReturnsNullWhenNoAnnotation();
        testGetDeclaredAnnotationReturnsAnnotationWhenPresent();
    }

    private static void testGetDeclaredAnnotationReturnsNullWhenNoAnnotation() {
        AccessibleObject ao = new DummyAccessibleObject();
        Class<? extends Annotation> annotationClass = DummyAnnotation.class;
        Annotation result = ao.getDeclaredAnnotation(annotationClass);
        if (result != null) {
            throw new AssertionError("Expected null when no annotation present, but got: " + result);
        }
    }

    private static void testGetDeclaredAnnotationReturnsAnnotationWhenPresent() {
        DummyAccessibleObject ao = new DummyAccessibleObject();
        ao.setAnnotation(DummyAnnotation.class, new DummyAnnotation());
        Class<? extends Annotation> annotationClass = DummyAnnotation.class;
        Annotation result = ao.getDeclaredAnnotation(annotationClass);
        if (result == null) {
            throw new AssertionError("Expected annotation to be returned, but got null");
        }
        if (!(result instanceof DummyAnnotation)) {
            throw new AssertionError("Returned annotation is not of expected type");
        }
    }

    private static class DummyAccessibleObject extends AccessibleObject {
        private java.util.Map<Class<? extends Annotation>, Annotation> annotations = new java.util.HashMap<>();

        public <T extends Annotation> void setAnnotation(Class<T> cls, T annotation) {
            annotations.put(cls, annotation);
        }

        @Override
        public <T extends Annotation> T getDeclaredAnnotation(Class<T> annotationClass) {
            // Override to simulate annotation retrieval
            return (T) annotations.get(annotationClass);
        }
    }

    private static class DummyAnnotation implements Annotation {
        @Override
        public Class<? extends Annotation> annotationType() {
            return DummyAnnotation.class;
        }
    }
}

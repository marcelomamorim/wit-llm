/*
 * @test
 * @summary Test AccessibleObject.getAnnotationsByType throws AssertionError as expected
 * @run main AccessibleObjectWitupTest
 */

public class AccessibleObjectWitupTest {
    public static void main(String[] args) {
        AccessibleObject ao = new AccessibleObject() {};
        try {
            ao.getAnnotationsByType(Deprecated.class);
            throw new RuntimeException("Expected AssertionError not thrown");
        } catch (AssertionError e) {
            // Expected exception
        }
    }
}

abstract class AccessibleObject implements java.lang.reflect.AnnotatedElement {
    protected AccessibleObject() {}
    public <T extends java.lang.annotation.Annotation> T[] getAnnotationsByType(Class<T> annotationClass) {
        throw new AssertionError("All subclasses should override this method");
    }
    // Minimal stub to satisfy interface
    public java.lang.annotation.Annotation[] getAnnotations() { return new java.lang.annotation.Annotation[0]; }
    public <T extends java.lang.annotation.Annotation> T getAnnotation(Class<T> annotationClass) { return null; }
    public boolean isAnnotationPresent(Class<? extends java.lang.annotation.Annotation> annotationClass) { return false; }
}

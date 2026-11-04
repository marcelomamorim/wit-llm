/*
 * @test
 * @summary Test getDeclaredAnnotationsByType method of AccessibleObject
 * @run main AccessibleObjectWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;

public class AccessibleObjectWitupTest {

    // Subclass to allow instantiation of abstract AccessibleObject
    static class TestAccessibleObject extends AccessibleObject {
        protected TestAccessibleObject() {
            super();
        }

        // Provide a dummy getAnnotationsByType to test getDeclaredAnnotationsByType
        @SuppressWarnings("unchecked")
        public <T extends Annotation> T[] getAnnotationsByType(Class<T> annotationClass) {
            if (annotationClass == null) {
                // According to standard API, passing null returns empty array or throws NPE
                // But AccessibleObject.getAnnotationsByType throws NullPointerException
                throw new NullPointerException("annotationClass is null");
            }
            // Return empty array for test
            return (T[]) java.lang.reflect.Array.newInstance(annotationClass, 0);
        }

        // Override getDeclaredAnnotationsByType to call the real method
        public <T extends Annotation> T[] getDeclaredAnnotationsByType(Class<T> annotationClass) {
            return super.getDeclaredAnnotationsByType(annotationClass);
        }
    }

    public static void main(String[] args) {
        TestAccessibleObject tao = new TestAccessibleObject();

        // Test 1: Passing null should throw NullPointerException
        try {
            tao.getDeclaredAnnotationsByType(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test 2: Passing a valid annotation class returns empty array
        Annotation[] result = tao.getDeclaredAnnotationsByType(Deprecated.class);
        if (result == null) {
            throw new RuntimeException("Returned array is null");
        }
        if (result.length != 0) {
            throw new RuntimeException("Expected empty array but got length " + result.length);
        }

        System.out.println("AccessibleObjectWitupTest passed");
    }
}

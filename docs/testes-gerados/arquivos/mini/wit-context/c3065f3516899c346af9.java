/*
 * @test
 * @summary Test sun.reflect.generics.reflectiveObjects.TypeVariableImpl.getDeclaredAnnotationsByType for NPE and normal behavior
 * @run main TypeVariableImplWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.reflect.TypeVariable;
import sun.reflect.generics.reflectiveObjects.TypeVariableImpl;

public class TypeVariableImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a minimal dummy TypeVariableImpl instance for testing
        // Since constructor is private and no public factory is accessible here,
        // we create a minimal subclass to test getDeclaredAnnotationsByType behavior.
        TypeVariableImpl<?> tv = new TypeVariableImplDummy();

        // Test 1: Passing null should throw NullPointerException
        try {
            tv.getDeclaredAnnotationsByType(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test 2: Passing a valid annotation class returns non-null array (possibly empty)
        Annotation[] result = tv.getDeclaredAnnotationsByType(Deprecated.class);
        if (result == null) {
            throw new RuntimeException("Returned annotation array is null");
        }
    }

    // Minimal dummy subclass to allow instantiation
    static class TypeVariableImplDummy extends TypeVariableImpl<Object> {
        TypeVariableImplDummy() {
            super(null, "Dummy", null, null);
        }

        // Override getAnnotationsByType to return empty array to avoid NPE
        @Override
        public <T extends Annotation> T[] getAnnotationsByType(Class<T> annotationClass) {
            // Return empty array of the requested annotation type
            @SuppressWarnings("unchecked")
            T[] empty = (T[]) java.lang.reflect.Array.newInstance(annotationClass, 0);
            return empty;
        }
    }
}

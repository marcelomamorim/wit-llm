/* @test    @summary Test getInt method throws IllegalArgumentException as per implementation    @run main UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetIntThrowsException();
    }

    private static void testGetIntThrowsException() {
        try {
            // Create a dummy instance of the class to invoke getInt
            // Since getInt always throws, the input object is irrelevant
            jdk.internal.reflect.UnsafeQualifiedStaticBooleanFieldAccessorImpl accessor = createAccessor();
            accessor.getInt(null);
            // If no exception, test fails
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }

    private static jdk.internal.reflect.UnsafeQualifiedStaticBooleanFieldAccessorImpl createAccessor() {
        // Reflection to instantiate the class with dummy parameters
        try {
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticBooleanFieldAccessorImpl");
            Constructor<?> constructor = clazz.getDeclaredConstructor(Field.class, boolean.class);
            constructor.setAccessible(true);
            return (jdk.internal.reflect.UnsafeQualifiedStaticBooleanFieldAccessorImpl) constructor.newInstance((Field) null, false);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create accessor instance", e);
        }
    }
}

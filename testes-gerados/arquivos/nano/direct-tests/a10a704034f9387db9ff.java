/* @test    @summary Test getFloat method of UnsafeQualifiedStaticObjectFieldAccessorImpl class    @run main UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetFloatThrowsException();
    }

    private static void testGetFloatThrowsException() {
        try {
            // Create a dummy instance to invoke the method
            // Since the method always throws, the input object is irrelevant
            jdk.internal.reflect.UnsafeQualifiedStaticObjectFieldAccessorImpl instance = createInstance();
            instance.getFloat(null);
            // If no exception is thrown, the test fails
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }

    private static jdk.internal.reflect.UnsafeQualifiedStaticObjectFieldAccessorImpl createInstance() {
        // Reflection to instantiate the class without invoking constructor
        try {
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticObjectFieldAccessorImpl");
            Field[] fields = clazz.getDeclaredFields();
            // Find a suitable constructor or instantiate via reflection if possible
            // Since no public constructor is available, use reflection
            java.lang.reflect.Constructor<?> constructor = clazz.getDeclaredConstructor(Field.class, boolean.class);
            constructor.setAccessible(true);
            // Pass dummy values for parameters
            return (jdk.internal.reflect.UnsafeQualifiedStaticObjectFieldAccessorImpl) constructor.newInstance(
                getDummyField(), false
            );
        } catch (Exception e) {
            throw new AssertionError("Failed to instantiate UnsafeQualifiedStaticObjectFieldAccessorImpl", e);
        }
    }

    private static Field getDummyField() {
        try {
            // Create a dummy field object for instantiation
            // Since the actual field is not used in getFloat, this is acceptable
            return null;
        } catch (Exception e) {
            throw new AssertionError("Failed to create dummy Field", e);
        }
    }
}

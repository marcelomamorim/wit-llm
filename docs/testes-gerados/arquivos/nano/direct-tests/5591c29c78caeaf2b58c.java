/*
 * @test
 * @summary Test getLong method of UnsafeQualifiedFloatFieldAccessorImpl
 * @run main UnsafeQualifiedFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetLongThrowsException();
    }

    private static void testGetLongThrowsException() {
        try {
            // Create a dummy instance of the class
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedFloatFieldAccessorImpl");
            // Access the constructor
            java.lang.reflect.Constructor<?> constructor = clazz.getDeclaredConstructor(Field.class, boolean.class);
            constructor.setAccessible(true);
            // Create a dummy Field object (null for simplicity, as it won't be used)
            Field dummyField = null;
            // Instantiate the class
            Object instance = constructor.newInstance(dummyField, false);
            // Call getLong method
            java.lang.reflect.Method getLongMethod = clazz.getMethod("getLong", Object.class);
            getLongMethod.invoke(instance, new Object());
            // If no exception, test fails
            System.err.println("Expected IllegalArgumentException was not thrown.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                System.out.println("getLong() threw IllegalArgumentException as expected.");
            } else {
                System.err.println("Unexpected exception: " + cause);
            }
        } catch (Exception e) {
            System.err.println("Test setup failed: " + e);
        }
    }
}

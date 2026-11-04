/* @test    @summary Test getDouble method throws IllegalArgumentException as specified.    @run main UnsafeQualifiedBooleanFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetDoubleThrowsException();
    }

    public static void testGetDoubleThrowsException() {
        try {
            // Create a dummy instance of the class
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedBooleanFieldAccessorImpl");
            // Obtain the method getDouble
            java.lang.reflect.Method method = clazz.getDeclaredMethod("getDouble", Object.class);
            // Make it accessible
            method.setAccessible(true);
            // Invoke the method with null (or any object, since it throws exception immediately)
            Object result = method.invoke(null, new Object());
            // If no exception, test fails
            System.err.println("Expected IllegalArgumentException was not thrown.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                System.out.println("testGetDoubleThrowsException passed.");
            } else {
                System.err.println("Unexpected exception: " + cause);
            }
        } catch (NoSuchMethodException | ClassNotFoundException | IllegalAccessException e) {
            System.err.println("Test setup failed: " + e);
        }
    }
}

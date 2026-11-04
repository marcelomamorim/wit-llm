/*
 * @test
 * @summary Test getDouble method of UnsafeQualifiedObjectFieldAccessorImpl
 * @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetDoubleThrowsException();
    }

    private static void testGetDoubleThrowsException() {
        try {
            // Create an instance of the class
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedObjectFieldAccessorImpl");
            // Get the method getDouble
            java.lang.reflect.Method method = clazz.getDeclaredMethod("getDouble", Object.class);
            method.setAccessible(true);
            // Invoke the method with null, expecting an IllegalArgumentException
            method.invoke(null, new Object());
            // If no exception, test fails
            System.err.println("Expected IllegalArgumentException was not thrown.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                System.out.println("getDouble() threw IllegalArgumentException as expected.");
            } else {
                System.err.println("Unexpected exception: " + cause);
            }
        } catch (NoSuchMethodException | ClassNotFoundException | IllegalAccessException e) {
            System.err.println("Test setup failed: " + e);
        }
    }
}

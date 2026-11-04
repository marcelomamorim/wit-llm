/*
 * @test
 * @summary Test getDouble method of UnsafeQualifiedStaticObjectFieldAccessorImpl
 * @run main UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetDoubleThrowsException();
    }

    public static void testGetDoubleThrowsException() {
        try {
            // Create an instance of the class
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticObjectFieldAccessorImpl");
            // Obtain the method getDouble
            java.lang.reflect.Method method = clazz.getDeclaredMethod("getDouble", Object.class);
            // Instantiate the class with dummy parameters
            Field dummyField = null; // Not used in getDouble, so can be null
            Object instance = clazz.getConstructor(Field.class, boolean.class).newInstance(dummyField, false);
            // Call getDouble and expect an IllegalArgumentException
            method.invoke(instance, new Object[]{null});
            // If no exception, fail the test
            System.err.println("Expected IllegalArgumentException was not thrown.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                System.out.println("getDouble correctly threw IllegalArgumentException.");
            } else {
                System.err.println("Unexpected exception: " + cause);
            }
        } catch (Exception e) {
            System.err.println("Test failed with exception: " + e);
        }
    }
}

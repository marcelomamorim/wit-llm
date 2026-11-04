/* @test    @summary Test getChar method of UnsafeQualifiedStaticFloatFieldAccessorImpl class    @run main UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetCharThrowsException();
    }

    private static void testGetCharThrowsException() {
        try {
            // Create an instance of the class
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticFloatFieldAccessorImpl");
            // Get the method object
            java.lang.reflect.Method method = clazz.getDeclaredMethod("getChar", Object.class);
            // Invoke the method with null argument
            method.invoke(clazz.newInstance(), (Object) null);
            // If no exception, test fails
            System.err.println("Expected IllegalArgumentException was not thrown.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                System.out.println("getChar() threw IllegalArgumentException as expected.");
            } else {
                System.err.println("Unexpected exception: " + cause);
            }
        } catch (Exception e) {
            System.err.println("Test failed with exception: " + e);
        }
    }
}

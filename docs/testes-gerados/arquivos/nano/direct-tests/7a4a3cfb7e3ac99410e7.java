/* @test    @summary Test getChar method of UnsafeQualifiedStaticShortFieldAccessorImpl class    @run main UnsafeQualifiedStaticShortFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticShortFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetCharThrowsException();
    }

    private static void testGetCharThrowsException() {
        try {
            // Create an instance of the class
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticShortFieldAccessorImpl");
            // Obtain the getChar method
            java.lang.reflect.Method getCharMethod = clazz.getDeclaredMethod("getChar", Object.class);
            getCharMethod.setAccessible(true);
            // Invoke the method with null argument
            getCharMethod.invoke(null, (Object) null);
            // If no exception, test fails
            System.err.println("Expected IllegalArgumentException was not thrown.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                System.out.println("getChar method correctly throws IllegalArgumentException.");
            } else {
                System.err.println("Unexpected exception: " + cause);
            }
        } catch (Exception e) {
            System.err.println("Test failed with exception: " + e);
        }
    }
}

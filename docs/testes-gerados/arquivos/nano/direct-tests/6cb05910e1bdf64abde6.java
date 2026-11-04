/* @test    @summary Test getByte method of UnsafeQualifiedStaticShortFieldAccessorImpl class    @run main UnsafeQualifiedStaticShortFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticShortFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetByteThrowsException();
    }

    private static void testGetByteThrowsException() {
        try {
            // Create an instance of the class
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticShortFieldAccessorImpl");
            // Obtain the getByte method
            java.lang.reflect.Method method = clazz.getDeclaredMethod("getByte", Object.class);
            // Make it accessible
            method.setAccessible(true);
            // Invoke the method with null argument
            method.invoke(null, (Object) null);
            // If no exception, fail the test
            System.err.println("Expected IllegalArgumentException was not thrown.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                System.out.println("getByte method correctly throws IllegalArgumentException.");
            } else {
                System.err.println("Unexpected exception: " + cause);
            }
        } catch (Exception e) {
            System.err.println("Test failed with exception: " + e);
        }
    }
}

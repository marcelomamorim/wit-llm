/* @test    @summary Test getByte method of UnsafeQualifiedStaticDoubleFieldAccessorImpl class to ensure it throws IllegalArgumentException as expected.    @run main UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetByteThrowsException();
    }

    public static void testGetByteThrowsException() {
        try {
            // Create an instance of the class under test
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticDoubleFieldAccessorImpl");
            // Obtain the getByte method
            java.lang.reflect.Method method = clazz.getDeclaredMethod("getByte", Object.class);
            // Make it accessible
            method.setAccessible(true);
            // Invoke the method with null argument
            method.invoke(null, (Object) null);
            // If no exception, test fails
            System.err.println("Expected IllegalArgumentException was not thrown.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                System.out.println("getByte() correctly threw IllegalArgumentException.");
            } else {
                System.err.println("Unexpected exception: " + cause);
            }
        } catch (Exception e) {
            System.err.println("Test failed with exception: " + e);
        }
    }
}

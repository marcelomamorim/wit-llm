/* @test    @summary Test getLong method of UnsafeStaticObjectFieldAccessorImpl class    @run main UnsafeStaticObjectFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeStaticObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetLongThrowsException();
    }

    private static void testGetLongThrowsException() {
        try {
            // Create an instance of the class
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeStaticObjectFieldAccessorImpl");
            // Obtain the getLong method
            java.lang.reflect.Method method = clazz.getDeclaredMethod("getLong", Object.class);
            method.setAccessible(true);
            // Invoke the method with null argument
            Object instance = null;
            long result = (Long) method.invoke(instance, (Object) null);
            // Since the method always throws, reaching this line indicates failure
            System.err.println("Expected exception was not thrown.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                System.out.println("getLong method correctly throws IllegalArgumentException.");
            } else {
                System.err.println("Unexpected exception cause: " + cause);
            }
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
        }
    }
}
